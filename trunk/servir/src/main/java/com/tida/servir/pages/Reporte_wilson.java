package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import org.apache.tapestry5.annotations.OnEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



/**
 * Start page of application servir.
 */
//@IncludeJavaScriptLibrary("context:layout/custom.js")
public class Reporte_wilson extends GeneralPage
{
private int paramEntidad;

public void setParamEntidad(int paramEntidad)
{this.paramEntidad = paramEntidad;}
public int getParamEntidad()
{return paramEntidad;}

@OnEvent(value="submit" ,component="formulario")
    @SuppressWarnings("deprecation")
public void envioReporte()
{

    /*
    ----
    
    SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    
    Session session = sf.openSession() ;
    
    session.connection();
    */
     /*         
        Class.forName("oracle.jdbc.OracleDriver");
        Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.19.33:1521:bdprueba2", "rnsc", "rnsc123");
        
        
        JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("report1.jasper");

        
        
        Map<String,Object> parametros=new HashMap<>();
        parametros.put("entidad_id",paramEntidad);
        
  
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conexion);
        
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File("reporte_prueba.pdf"));
        exporter.exportReport();
       */ 
}
  
}
