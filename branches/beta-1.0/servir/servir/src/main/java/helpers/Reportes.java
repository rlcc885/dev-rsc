/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author ale
 */
public class Reportes {

    public static String ERROR_TYPE = "ERROR";
    public static String OUT_TYPE = "ERROR";

    public static enum TIPO { PDF, EXCEL };
    public static String[] REPORTE_TIPO = { "PDF", "EXCEL" };
    public static enum REPORTE { A1, A2, A3, A4_1, A4_2, A4_3, A5, A6, A7,
                                A3_UE, A6_UE, A7_UE, // REPORTES NUEVOS MAFI 	
				A8_UE, A8, // REPORTES NUEVOS MAFI 
				LUE1, L1, L2, L3, L4, L5, L6, L7, L9, L11,  //REPORTE NUEVOS MAFI
				D44, C1212, //REPORTE MAFI
				D66, D77, D88, // REPORTES MAFI
				B2, B3, B4, B5,	    
                                C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C12_PDF,
                                D1, D2, D3, D4, D4_PDF, D5, D6, D6_PDF, D7, D7_PDF, D8, D8_PDF, remuneraciones,
                                Reporte2 };
    private static String[] REPORTE_NOMBRE = {"A1.prpt", "A2.prpt", "A3.prpt", "A4-1.prpt", "A4-2.prpt", "A4-3.prpt", "A5.prpt",
                                            "A6.prpt", "A7.prpt",
					    "A3-UE.prpt", "A6-UE.prpt", "A7-UE.prpt", // reportes nuevo MAFI
					    "A8.prpt", "A8-UE.prpt", // reportes nuevos MAFI
					    "LUE1.prpt", "L1.prpt", "L2.prpt", "L3.prpt", "L4.prpt", "L5.prpt", "L6.prpt", "L7.prpt", "L9.prpt", "L11.prpt", // REPORTES NUEVOS MAFI
					    "D44.prpt", "C1212.prpt", //nuevo mafi
					    "D66.prpt", "D77.prpt", "D88.prpt",// reportes mafi
                                            "B2.prpt", "B3.prpt", "B4.prpt", "B5.prpt",
                                            "C1.prpt", "C2.prpt", "C3.prpt", "C4.prpt", "C5.prpt",
                                            "C6.prpt", "C7.prpt", "C8.prpt", "C9.prpt", "C10.prpt",
                                            "C11.prpt", "C12.prpt",  "C12.prpt",
                                            "D1.prpt", "D2.prpt", "D3.prpt","D4.prpt", "D5.prpt",
                                            "D6.prpt", "D6.prpt", "D7.prpt", "D7.prpt", "D8.prpt", "D8.prpt",
                                            "Sample1.prpt"};
    private static String[] REPORTE_NOMBRE_OUT = {"A1.pdf", "A2.pdf", "A3.pdf", "A4-1.pdf", "A4-2.pdf", "A4-3.pdf", "A5.pdf",
                                            "A6.pdf", "A7.pdf",
					    "A3-UE.pdf", "A6-UE.pdf", "A7-UE.pdf", // reportes nuevos MAFI 
					    "A8.pdf", "A8-UE.pdf", // reportes nuevos MAFI
					    "LUE1.pdf", "L1.xls", "L2.xls", "L3.xls", "L4.xls", "L5.xls", "L6.xls", "L7.xls", "L9.xls", "L11.xls", //REPORTES NUEVOS MAFI
					    "D44.xls","C1212.xls", //reporte mafi
					    "D66.xls", "D77.xls", "D88.xls",// reportes mafi
                                            "B2.pdf", "B3.pdf", "B4.pdf", "B5.pdf",
                                            "C1.pdf", "C2.pdf", "C3.pdf", "C4.pdf", "C5.pdf",
                                            "C6.pdf", "C7.pdf", "C8.pdf", "C9.pdf", "C10.pdf",
                                            "C11.pdf", "C12.csv", "C12.pdf",
                                            "D1.pdf", "D2.pdf", "D3.pdf","D4.csv","D4.pdf", "D5.pdf",
                                            "D6.csv", "D6.pdf", "D7.csv", "D7.pdf", "D8.csv", "D8.pdf",
                                            "Sample1.pdf"};
    private static String JRE = "java "; // set with the defined jre
    private static String REPORTE_EXEC = "Reporte.jar"; // set with the defined jre
    private static String[] REPORTE_ESPECIAL_EXEC = {"A1", "A2", "A3", "A4_1", "A4_2", "A4_3", // no se usan
                                "A5", "A6", "A7", "B2", "B3", "B4", "B5", // No se usan
                                "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", // no se usan
                                "C12.jar", "C12",
                                "D1", "D2", "D3", // no se usan
                                "D4.jar","D4",
                                "D5", // no se usan
                                "D6.jar", "D6", "D7.jar", "D7", "D8.jar", "D8"} ; // set with the defined jre
    private static String REPORTE_DIR = "reportes/";
    private static String ERROR_FILE = "errores-reportes.log";
    public String reportesPath;
    public REPORTE rep;
    public TIPO rep_type;
    Map<String, Object> parametros;
    public Process p;

    
    public StreamResponse callReporte(REPORTE reporte, TIPO tipoRep, Map<String, Object> params,
            Context context) {
        rep = reporte;
        rep_type = tipoRep;
        parametros = params;
        try {
            reportesPath = context.getRealFile("/").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new StreamResponse() {

            InputStream istream;
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            String errorsb = new String();


            @Override
            public void prepareResponse(Response response) {
                    String params = "";
                    // Generate the report definition
/*
                    if(rep_type == TIPO.PDF ) {
                        response.setHeader("Content-Type", "application/pdf");
                    } else {
                        response.setHeader("Content-Type", "application/xls");
                    }
  */
                    //response.setHeader("Content-Type", "application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=" + REPORTE_NOMBRE_OUT[rep.ordinal()]);
                try {
                    //istream = getReportStream(rep);
                    //p = Runtime.getRuntime().exec("/"+ JRE + " " + REPORTE_DIR + REPORTE_EXEC + " " + REPORTE_DIR + REPORTE_NOMBRE[rep.ordinal()]);
                    //p = Runtime.getRuntime().exec(JRE + " " + REPORTE_DIR + REPORTE_EXEC + " " + REPORTE_DIR + REPORTE_NOMBRE[rep.ordinal()]);

                    reportesPath += "/" + REPORTE_DIR;
                    for(String param: parametros.keySet()) {
                        Long l = new Long(1);
                        if(parametros.get(param).getClass().equals(String.class))
                            params += param + " " + URLEncoder.encode((String)parametros.get(param), "UTF-8") + " ";
                        else
                            params += param + " " + URLEncoder.encode(((Long) parametros.get(param)).toString(), "UTF-8") + " ";

                    }
                    System.out.println("---------------- Ejecutado: "+ JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_EXEC + " " + REPORTE_NOMBRE[rep.ordinal()] +
                            " " + REPORTE_TIPO[rep_type.ordinal()] + " " + params);
                    p = Runtime.getRuntime().exec(JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_EXEC + " " + REPORTE_NOMBRE[rep.ordinal()] +
                            " " + REPORTE_TIPO[rep_type.ordinal()] + " " + params );

                    DataGobbler outputGobbler = new DataGobbler(p.getInputStream(), sb, OUT_TYPE);
                    outputGobbler.start();

                    StringGobbler errorGobbler = new StringGobbler(p.getErrorStream(), errorsb, ERROR_TYPE);
                    errorGobbler.start();
                    
                    try {
                        p.waitFor();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    
                } catch (IOException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }

                
            }

            @Override
            public String getContentType() {
                if (rep_type == TIPO.PDF)
                    return "application/pdf";
                else
                    return "application/xls";
            }

            @Override
            public InputStream getStream() throws IOException {

                  /*
                  File f = new File(reportesPath + "/" + ERROR_FILE);
                  InputStream errorStream= p.getErrorStream();
                  OutputStream out=new FileOutputStream(f, true);
                  byte buf[]=new byte[1024];
                  int len;
                  while((len=errorStream.read(buf))>0)
                      out.write(buf,0,len);
                  out.close();
                  errorStream.close();
                   *
                   */
                 return new ByteArrayInputStream(sb.toByteArray());
                 //return p.getInputStream();
            }
        };

    }

    public StreamResponse callReporteEspecial(REPORTE reporte, TIPO tipoRep, Map<String, Object> params,
            Context context) {
        rep = reporte;
        rep_type = tipoRep;
        parametros = params;
        try {
            reportesPath = context.getRealFile("/").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new StreamResponse() {

            InputStream istream;
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            String errorsb = new String();

            @Override
            public void prepareResponse(Response response) {
                    String params = "";
                    // Generate the report definition

           
                    //response.setHeader("Content-Type", "application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=" + REPORTE_NOMBRE_OUT[rep.ordinal()]);
                try {
                    //istream = getReportStream(rep);
                    //p = Runtime.getRuntime().exec("/"+ JRE + " " + REPORTE_DIR + REPORTE_EXEC + " " + REPORTE_DIR + REPORTE_NOMBRE[rep.ordinal()]);
                    //p = Runtime.getRuntime().exec(JRE + " " + REPORTE_DIR + REPORTE_EXEC + " " + REPORTE_DIR + REPORTE_NOMBRE[rep.ordinal()]);

                    reportesPath += "/" + REPORTE_DIR;
                    for(String param: parametros.keySet()) {
                        Long l = new Long(1);
                        if(parametros.get(param).getClass().equals(String.class))
                            params += " " + URLEncoder.encode((String)parametros.get(param), "UTF-8") ;
                        else
                            params += " " + URLEncoder.encode(((Long) parametros.get(param)).toString(), "UTF-8");
                    }

                    System.out.println("----------- Ejecutado: "+ JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_ESPECIAL_EXEC[rep.ordinal()] + " "+ URLEncoder.encode((String)reportesPath, "UTF-8")+  params);
                    p = Runtime.getRuntime().exec(JRE + " -cp " + reportesPath + "lib -jar " +
                            reportesPath +  REPORTE_ESPECIAL_EXEC[rep.ordinal()]  + " "+ URLEncoder.encode((String)reportesPath, "UTF-8")+ params);

                    DataGobbler outputGobbler = new DataGobbler(p.getInputStream(), sb, OUT_TYPE);
                    outputGobbler.start();

                    StringGobbler errorGobbler = new StringGobbler(p.getErrorStream(), errorsb, ERROR_TYPE);
                    errorGobbler.start();
                    
                    try {
                        p.waitFor();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }


            }

            @Override
            public String getContentType() {
                    return "application/csv";
            }

            @Override
            public InputStream getStream() throws IOException {

                 return new ByteArrayInputStream(sb.toByteArray());
                 /*
                File f = new File(reportesPath + "/" + ERROR_FILE);
                  InputStream errorStream= p.getErrorStream();
                  OutputStream out=new FileOutputStream(f, true);
                  byte buf[]=new byte[1024];
                  int len;
                  while((len=errorStream.read(buf))>0)
                      out.write(buf,0,len);
                  out.flush();
                  out.close();
                  errorStream.close();
                return p.getInputStream();
                //return p.getErrorStream();
*/

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
            while ( (line = br.read()) != -1)
            {
                //os += (char) line;
                //osr.append((char)line);
//                System.out.println(type + ">" + line);
            }
            
        } catch (IOException ioe)
            {
            ioe.printStackTrace();
            }

        // envio al archivo de error.
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

