/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.entities.DirectorioReporte;
import com.tida.servir.entities.LkBusquedaCargo;
import com.tida.servir.entities.LkBusquedaUnidad;
import com.tida.servir.entities.Reporte;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Response;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author servir
 */
public class ReportesFormulario {
    public static enum TIPO { PDF, EXCEL };
    public static String ERROR_TYPE = "ERROR";
    private static String ERROR_FILE = "errores-reportes.log";
    public static String OUT_TYPE = "ERROR";
    
    private static String JRE = "java ";
    private static String REPORTE_EXEC = "Reporte.jar";
    
    public String reportesPath;
    public Reporte reporte;
    public TIPO reptipo;
    Map<String, Object> parametros;
    public Process p;
    
    public StreamResponse callReporte(Reporte report, TIPO reptip, Map<String, Object> params, Session session) throws Exception {
        reporte = report;
        reptipo = reptip;
        parametros = params;
        
        Criteria c = session.createCriteria(DirectorioReporte.class);
        List<DirectorioReporte> list = c.list();
        
        if (list.size() < 1) throw new Exception ("No se ha encontrado el directorio de los reportes.");
        
        reportesPath = list.get(list.size() - 1).getDir();

        return new StreamResponse() {

            InputStream istream;
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            String errorsb = new String();


            @Override
            public void prepareResponse(Response response) {
                    String params = "";
                    response.setHeader("Content-Disposition", "inline; filename=" + reporte.getCodigo() + (reptipo == TIPO.PDF? ".pdf":".xls"));
                try {
                    for(String param: parametros.keySet()) {
                        if(parametros.get(param).getClass().equals(String.class))
                            params += param + " " + URLEncoder.encode((String)parametros.get(param), "UTF-8") + " ";
                        else
                            params += param + " " + URLEncoder.encode(((Long) parametros.get(param)).toString(), "UTF-8") + " ";
                    }

                    System.out.println("---------------- Ejecutado: "+ JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_EXEC + " " + reporte.getCodigo() + ".prpt" + " " + (reptipo == TIPO.PDF? "PDF":"XLS") + " " + params);
                    p = Runtime.getRuntime().exec(JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_EXEC + " " + reporte.getCodigo() + ".prpt" + " " + (reptipo == TIPO.PDF? "PDF":"XLS") + " " + params );

                    DataGobbler outputGobbler = new DataGobbler(p.getInputStream(), sb, OUT_TYPE);
                    outputGobbler.start();

                    StringGobbler errorGobbler = new StringGobbler(p.getErrorStream(), errorsb, ERROR_TYPE);
                    errorGobbler.start();
                    
                    try {
                        p.waitFor();
                    } catch (Exception ex) {
                        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public String getContentType() {
                if (reptipo == TIPO.PDF)
                    return "application/pdf";
                else
                    return "application/xls";
            }

            @Override
            public InputStream getStream() throws IOException {
                 return new ByteArrayInputStream(sb.toByteArray());
            }
        };

    }
    
class DataGobbler extends Thread
{
    InputStream is;
    String type;
    OutputStream os;

    DataGobbler(InputStream is, String type)
    {
        this(is, null, type);
    }
    DataGobbler(InputStream is,  OutputStream redirect, String type)
    {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }

    public void run()
    {
        try
        {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ( (bytesRead = is.read(buffer)) != -1)
            {
                 os.write(buffer, 0, bytesRead);
            }

        } catch (IOException ioe)
            {
            ioe.printStackTrace();
            }
    }
}


class StringGobbler extends Thread
{
    InputStream is;
    String type;
    String os;

    StringGobbler(InputStream is, String type)
    {
        this(is, null, type);
    }

    StringGobbler(InputStream is, String redirect, String type)
    {
        this.is = is;
        this.os = redirect;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
           
            BufferedReader br = new BufferedReader(isr);
            int line;
            while ( (line = br.read()) != -1){}
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (type.equals(ERROR_TYPE)) {
              File f = new File(reportesPath + "/" + ERROR_FILE);
              BufferedWriter out;
                try {
                    out = new BufferedWriter(new FileWriter(f));
                    out.write(os);
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
        } else {
              File f = new File(reportesPath + "/" + "Reportes");
              BufferedWriter out;
                try {
                    out = new BufferedWriter(new FileWriter(f));
                    out.write(os);
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
}
