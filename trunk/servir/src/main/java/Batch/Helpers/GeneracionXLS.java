/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;
import com.tida.servir.entities.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import org.apache.poi.hssf.usermodel.*;
/**
 *
 * @author arson
 */
public class GeneracionXLS {
    
    public static final String ERROR_GENERANDO_EL_ARCHIVO = "Error generando el archivo: ";
    
    public static String leoCampo(String valor) {        
        if (valor == null) {
            return "";
        }
        if ("".equals(valor.trim())) {
            return "";
        }
        return valor; 
    }
    
    public static String datoAuxiliarToString(DatoAuxiliar valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor.getCodigo());
    }
    private static String BoolFromEstado(Boolean estado){
        if (estado!=null) {
            if(estado)
                return "1";
            else
                return "0";
        }
        return "";
    }
    
    public List<String> generadoXLSEntidad(Entidad oi,String path,Session session){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Entidad"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Código de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Nivel de Gobierno");
            celda= fila.createCell((short)2);
            celda.setCellValue("Organización del estado");
            celda= fila.createCell((short)3);
            celda.setCellValue("Sector del Gobierno");
            celda= fila.createCell((short)4);
            celda.setCellValue("Tipo de Organismo");
            celda= fila.createCell((short)5);
            celda.setCellValue("Denominación de la Entidad");
            celda= fila.createCell((short)6);
            celda.setCellValue("Siglas de la Entidad");
            celda= fila.createCell((short)7);
            celda.setCellValue("Número de RUC");
            celda= fila.createCell((short)8);
            celda.setCellValue("Tipo de Vía");
            celda= fila.createCell((short)9);
            celda.setCellValue("Descripción de la Vía");
            celda= fila.createCell((short)10);
            celda.setCellValue("Tipo de Zona");
            celda= fila.createCell((short)11);
            celda.setCellValue("Descripción de la Zona");
            celda= fila.createCell((short)12);
            celda.setCellValue("Departamento");
            celda= fila.createCell((short)13);
            celda.setCellValue("Provincia");
            celda= fila.createCell((short)14);
            celda.setCellValue("Distrito");
            celda= fila.createCell((short)15);
            celda.setCellValue("Email Institucional");
            celda= fila.createCell((short)16);
            celda.setCellValue("URL de la Entidad");
            celda= fila.createCell((short)17);
            celda.setCellValue("Telefóno de la Entidad");
            celda= fila.createCell((short)18);
            celda.setCellValue("Tipo de Sub-Entidad");
            celda= fila.createCell((short)19);
            celda.setCellValue("Código de la Entidad (CUE) antecesora");
            //data
            fila = hoja1.createRow((short)(1));            
            celda= fila.createCell((short)0);
            celda.setCellValue(leoCampo(oi.getCue_entidad()));
            celda= fila.createCell((short)1);
            celda.setCellValue(datoAuxiliarToString(oi.getNivelGobierno()));
            celda= fila.createCell((short)2);
            celda.setCellValue(datoAuxiliarToString(oi.getOrganizacionEstado()));
            celda= fila.createCell((short)3);
            celda.setCellValue(datoAuxiliarToString(oi.getSectorGobierno()));
            celda= fila.createCell((short)4);
            celda.setCellValue(datoAuxiliarToString(oi.getTipoOrganismo()));
            celda= fila.createCell((short)5);
            celda.setCellValue(leoCampo(oi.getDenominacion()));
            celda= fila.createCell((short)6);
            celda.setCellValue(leoCampo(oi.getSigla()));
            celda= fila.createCell((short)7);
            celda.setCellValue(leoCampo(oi.getRuc()));
            celda= fila.createCell((short)8);
            celda.setCellValue(datoAuxiliarToString(oi.getTipoVia()));
            celda= fila.createCell((short)9);
            celda.setCellValue(leoCampo(oi.getDescvia()));
            celda= fila.createCell((short)10);
            celda.setCellValue(datoAuxiliarToString(oi.getTipoZona()));
            celda= fila.createCell((short)11);
            celda.setCellValue(leoCampo(oi.getDescZona()));
            celda= fila.createCell((short)12);
            celda.setCellValue(datoAuxiliarToString(oi.getDepartamento()));
            celda= fila.createCell((short)13);
            celda.setCellValue(datoAuxiliarToString(oi.getProvincia()));
            celda= fila.createCell((short)14);
            celda.setCellValue(datoAuxiliarToString(oi.getDistrito()));
            celda= fila.createCell((short)15);
            celda.setCellValue(leoCampo(oi.getEmailInstitucional()));
            celda= fila.createCell((short)16);
            celda.setCellValue(leoCampo(oi.getUrlEntidad()));
            celda= fila.createCell((short)17);
            celda.setCellValue(leoCampo(oi.getTelefonoEntidad()));
            celda= fila.createCell((short)18);
            celda.setCellValue(datoAuxiliarToString(oi.getTipoSubEntidad()));
            celda= fila.createCell((short)19);
            if(oi.getEntidad()!=null && !oi.getEntidad().equals(""))
                celda.setCellValue(leoCampo(oi.getEntidad().getCue_entidad()));
            else
                celda.setCellValue("");         
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxEntidad.xls");
        }
        return errores;
    }
    public List<String> generadoXLSConcepto(List<ConceptoRemunerativo> lcr,String path,Entidad eu,Session session){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Concepto Remunerativo"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Código de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Código del Concepto Remunerativo en la Entidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Descripción");
            celda= fila.createCell((short)3);
            celda.setCellValue("Tipo Remuneración");
            celda= fila.createCell((short)4);
            celda.setCellValue("Periodicidad");
            celda= fila.createCell((short)5);
            celda.setCellValue("Concepto STD");
            celda= fila.createCell((short)6);
            celda.setCellValue("Sustento Legal");  
            
            for (int i=0;i<lcr.size();i++) {
                ConceptoRemunerativo cr=(ConceptoRemunerativo)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(eu.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getCodigo()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getDescripcion()));
                celda= fila.createCell((short)3);
                celda.setCellValue(datoAuxiliarToString(cr.getTiporemuneracion()));
                celda= fila.createCell((short)4);
                celda.setCellValue(datoAuxiliarToString(cr.getPeriodicidad()));
                celda= fila.createCell((short)5);
                celda.setCellValue(datoAuxiliarToString(cr.getTiporemuneracionstd()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getSustento_legal())); 
            }                      
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxEntidad.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSUnidadOrganica(List<UnidadOrganica> lcr,String path,Session session){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Unidad Organica"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Código de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Código interno de la Unidad Orgánica");
            celda= fila.createCell((short)2);
            celda.setCellValue("Denominación");
            celda= fila.createCell((short)3);
            celda.setCellValue("Nivel Organizacional");
            celda= fila.createCell((short)4);
            celda.setCellValue("Código interno de la Unidad Orgánica Antecesora");
            celda= fila.createCell((short)5);
            celda.setCellValue("Categoría");
            celda= fila.createCell((short)6);
            celda.setCellValue("Siglas");
            celda= fila.createCell((short)7);
            celda.setCellValue("Tipo de Vía");
            celda= fila.createCell((short)8);
            celda.setCellValue("Descripción de la Vía");
            celda= fila.createCell((short)9);
            celda.setCellValue("Tipo de Zona");
            celda= fila.createCell((short)10);
            celda.setCellValue("Descripción de la Zona");
            celda= fila.createCell((short)11);
            celda.setCellValue("Departamento");
            celda= fila.createCell((short)12);
            celda.setCellValue("Provincia");
            celda= fila.createCell((short)13);
            celda.setCellValue("Distrito");
            celda= fila.createCell((short)14);
            celda.setCellValue("Estado");
            
            for (int i=0;i<lcr.size();i++) {
                UnidadOrganica cr=(UnidadOrganica)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(cr.getEntidad().getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getCod_und_organica()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getDen_und_organica()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(String.valueOf(cr.getNivel())));
                celda= fila.createCell((short)4);
                if(cr.getUnidadorganica()!=null && !cr.getUnidadorganica().equals(""))
                    celda.setCellValue(leoCampo(cr.getUnidadorganica().getCod_und_organica()));
                else
                    celda.setCellValue("");
                
                celda= fila.createCell((short)5);
                celda.setCellValue(datoAuxiliarToString(cr.getCategoriauo()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getSigla()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(datoAuxiliarToString(cr.getTipovia()));
                celda= fila.createCell((short)8);
                celda.setCellValue(leoCampo(cr.getLocalidad()));
                celda= fila.createCell((short)9);
                celda.setCellValue(datoAuxiliarToString(cr.getTipozona()));
                celda= fila.createCell((short)10);
                celda.setCellValue(leoCampo(cr.getDesczona()));
                celda= fila.createCell((short)11);
                celda.setCellValue(datoAuxiliarToString(cr.getCod_ubi_dept()));
                celda= fila.createCell((short)12);
                celda.setCellValue(datoAuxiliarToString(cr.getCod_ubi_prov()));
                celda= fila.createCell((short)13);
                celda.setCellValue(datoAuxiliarToString(cr.getCod_ubi_dist()));
                celda= fila.createCell((short)14);
                celda.setCellValue(BoolFromEstado(cr.getEstado()));
            } 
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxEntidad.xls");
        }
        
        return errores;
    }
    private void ajustaColumnas(HSSFSheet hoja) {
        final short numeroColumnas = hoja.getRow(0).getLastCellNum();
        for (int i = 0; i < numeroColumnas; i++) {
            hoja.autoSizeColumn(i);
        }
    }
}
