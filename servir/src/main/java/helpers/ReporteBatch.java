/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.annotation.Resource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author arson
 */
public class ReporteBatch 
{
    
    public enum Formatos{PDF,EXCEL}
    
    private Formatos formato ;
    private ByteArrayOutputStream out;
    private byte[] archivo = null;
    private InputStream input = null;

    //private int parametro;
    //private String tipoReporte = "ReporteB"; 
 
    public StreamResponse getReporte(String reporteEntrada,HashMap<String,Object> parametrosReporte,final Formatos formatoSalida) throws ClassNotFoundException, SQLException, JRException, IOException
{   
    formato = formatoSalida;
            
    getInputStreamReporte(reporteEntrada,parametrosReporte);
    
return new StreamResponse() {

            public String getContentType() {
                if (formatoSalida.equals(Formatos.PDF))
                {return "application/pdf";}
                else
                {return "application/vnd.ms-excel";}
                 
            }
            public InputStream getStream() {return input;}
            public void prepareResponse(Response response) {
                response.setHeader("Cache-Control", "cache");
            response.setHeader("Content-Disposition", "inline;filename=reporte."+formato); 
            }
        };    
}

    private void getInputStreamReporte(String reporteEntrada,HashMap<String,Object> parametros) throws ClassNotFoundException, SQLException, JRException, IOException
{
Class.forName("oracle.jdbc.OracleDriver");
Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.16.19.33:1521/bdprueba2", "rnsc", "rnsc123");


//System.out.println("REPORTEX");
//System.out.println(this.getClass().getResource("/report1.jasper").toString());
//System.out.println();

//
String x = this.getClass().getResource("/"+reporteEntrada).toString();
x = x.replaceAll("file:/", "");
System.out.println(x);

//



//JasperReport Jreporte = (JasperReport) JRLoader.loadObjectFromFile("C://Reporte01/"+reporteEntrada+".jasper");
JasperReport Jreporte = (JasperReport)JRLoader.loadObjectFromFile(x);

System.out.println("ARCHIVOX"+Jreporte.toString());System.getProperties();

    /*
    HashMap parametros = new HashMap();
    parametros.put("entidad_id",parametro);
    */

//    if (parametros != null){
//        parametros.put("ruta","C://Reporte01");
//     }

    System.out.println("INICIO DE LA CARGA ");
    JRExporter exporter ;
    JasperPrint jasperPrint = JasperFillManager.fillReport(Jreporte, parametros, connection);

    out = new ByteArrayOutputStream();
    
    System.out.println("fORMATO DEL REPORTE ");
    
    if (formato ==null){formato = Formatos.PDF;}
    
    if (formato == Formatos.PDF)
    { 
    exporter = new JRPdfExporter();
    exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
    exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, out);
    exporter.exportReport();
    }
    else if (formato == Formatos.EXCEL)
    {
    exporter = new JRXlsExporter(); 
    exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
    exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
    exporter.exportReport();
    }

    archivo = out.toByteArray();
    out.close();
    input = new ByteArrayInputStream(archivo);
    
    }
    
 
}
