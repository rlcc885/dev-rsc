/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;
import com.tida.servir.entities.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static String BoolFrom(Boolean estado){
        if (estado!=null) {
            if(estado)
                return "1";
            else
                return "0";
        }
        return "";
    }
    public static String datetoString(Date valor) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");        
        if (valor == null) {
            return "";
        }
        return sdf.format(valor);
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
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Nivel de Gobierno");
            celda= fila.createCell((short)2);
            celda.setCellValue("Organizacion del estado");
            celda= fila.createCell((short)3);
            celda.setCellValue("Sector del Gobierno");
            celda= fila.createCell((short)4);
            celda.setCellValue("Tipo de Organismo");
            celda= fila.createCell((short)5);
            celda.setCellValue("Denominacion de la Entidad");
            celda= fila.createCell((short)6);
            celda.setCellValue("Siglas de la Entidad");
            celda= fila.createCell((short)7);
            celda.setCellValue("Numero de RUC");
            celda= fila.createCell((short)8);
            celda.setCellValue("Tipo de Via");
            celda= fila.createCell((short)9);
            celda.setCellValue("Descripción de la Via");
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
            celda.setCellValue("Telefono de la Entidad");
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
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxENTIDAD.xls");
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
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Codigo del Concepto Remunerativo en la Entidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Descripcion");
            celda= fila.createCell((short)3);
            celda.setCellValue("Tipo Remuneracion");
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
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxCONCEPTO.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSUnidadOrganica(List<LkBatchUnidadOrga> lcr,String path,Session session){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Unidad Organica"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Codigo interno de la Unidad Organica");
            celda= fila.createCell((short)2);
            celda.setCellValue("Denominacion");
            celda= fila.createCell((short)3);
            celda.setCellValue("Nivel Organizacional");
            celda= fila.createCell((short)4);
            celda.setCellValue("Codigo interno de la Unidad Organica Antecesora");
            celda= fila.createCell((short)5);
            celda.setCellValue("Categoria");
            celda= fila.createCell((short)6);
            celda.setCellValue("Siglas");
            celda= fila.createCell((short)7);
            celda.setCellValue("Tipo de Via");
            celda= fila.createCell((short)8);
            celda.setCellValue("Descripcion de la Via");
            celda= fila.createCell((short)9);
            celda.setCellValue("Tipo de Zona");
            celda= fila.createCell((short)10);
            celda.setCellValue("Descripcion de la Zona");
            celda= fila.createCell((short)11);
            celda.setCellValue("Departamento");
            celda= fila.createCell((short)12);
            celda.setCellValue("Provincia");
            celda= fila.createCell((short)13);
            celda.setCellValue("Distrito");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchUnidadOrga cr= (LkBatchUnidadOrga)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(cr.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getCod_und_organica()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getDen_und_organica()));
                celda= fila.createCell((short)3);
                if(cr.getNivel()!=null)
                    celda.setCellValue(leoCampo(String.valueOf(cr.getNivel())));
                else
                    celda.setCellValue("");                
                celda= fila.createCell((short)4);
                celda.setCellValue(leoCampo(cr.getCodunidada()));                
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getCategoriauo()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getSigla()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(leoCampo(cr.getTipovia()));
                celda= fila.createCell((short)8);
                celda.setCellValue(leoCampo(cr.getDescvia()));
                celda= fila.createCell((short)9);
                celda.setCellValue(leoCampo(cr.getTipozona()));
                celda= fila.createCell((short)10);
                celda.setCellValue(leoCampo(cr.getDesczona()));
                celda= fila.createCell((short)11);
                celda.setCellValue(leoCampo(cr.getCod_ubi_dept()));
                celda= fila.createCell((short)12);
                celda.setCellValue(leoCampo(cr.getCod_ubi_prov()));
                celda= fila.createCell((short)13);
                celda.setCellValue(leoCampo(cr.getCod_ubi_dist()));
            } 
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxUNIDADORGANICA.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSCargo(List<LkBatchCargoxUnidad> lcr,String path,Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("CargoxUnidad"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Codigo interno de la Unidad Orgánica");
            celda= fila.createCell((short)2);
            celda.setCellValue("Codigo del Cargo en la Entidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Denominacion");
            celda= fila.createCell((short)4);
            celda.setCellValue("Situacion en el CAP");
            celda= fila.createCell((short)5);
            celda.setCellValue("Regimen Laboral");
            celda= fila.createCell((short)6);
            celda.setCellValue("Grupo Ocupacional");
            celda= fila.createCell((short)7);
            celda.setCellValue("Nivel Remunerativo");
            celda= fila.createCell((short)8);
            celda.setCellValue("Es Organico");
            celda= fila.createCell((short)9);
            celda.setCellValue("Presupuestado en el PAP");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchCargoxUnidad cr=(LkBatchCargoxUnidad)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getCod_und_organica()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getCod_cargo()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getDen_cargo()));
                celda= fila.createCell((short)4);    
                celda.setCellValue(leoCampo(cr.getSituacioncap()));              
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getRegimenlaboral()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getGrupoOcupacional()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(leoCampo(cr.getNivelRemunerativo()));
                celda= fila.createCell((short)8);
                celda.setCellValue(BoolFrom(cr.getEsorganico()));
                celda= fila.createCell((short)9);
                celda.setCellValue(BoolFrom(cr.getPresupuestado_PAP()));
            }
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxCARGO.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSCargoAsignado(List<LkBatchCargoAsignado> lcr,String path,Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Cargo Asignado"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Tipo de documento de identidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Numero de documento de identidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Codigo interno de la Unidad Organica");
            celda= fila.createCell((short)4);
            celda.setCellValue("Codigo del cargo");
            celda= fila.createCell((short)5);
            celda.setCellValue("Fecha de inicio en el cargo");
            celda= fila.createCell((short)6);
            celda.setCellValue("Fecha de cese en el cargo");
            celda= fila.createCell((short)7);
            celda.setCellValue("Tipo de Vinculo");
            celda= fila.createCell((short)8);
            celda.setCellValue("Motivo del cese en el cargo");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchCargoAsignado cr=(LkBatchCargoAsignado)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getTipodocumento()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getNrodocumento()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getCod_und_organica()));
                celda= fila.createCell((short)4);    
                celda.setCellValue(leoCampo(cr.getCod_cargo()));              
                celda= fila.createCell((short)5);
                celda.setCellValue(datetoString(cr.getFec_inicio()));
                celda= fila.createCell((short)6);
                celda.setCellValue(datetoString(cr.getFec_fin()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(leoCampo(cr.getTipovinculo()));
                celda= fila.createCell((short)8);
                celda.setCellValue(leoCampo(cr.getMotivo_cese()));
            }
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxCARGOASIGNADO.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSEvaluaciones(List<LkBatchEvaluacion> lcr,String path,Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Cargo Asignado"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Tipo de documento de identidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Numero de documento de identidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Codigo del cargo");
            celda= fila.createCell((short)4);
            celda.setCellValue("Fecha de inicio en el cargo");
            celda= fila.createCell((short)5);
            celda.setCellValue("Tipo Evaluacion");
            celda= fila.createCell((short)6);
            celda.setCellValue("Calificacion");
            celda= fila.createCell((short)7);
            celda.setCellValue("Motivo evaluacion");
            celda= fila.createCell((short)8);
            celda.setCellValue("Fecha Desde");
            celda= fila.createCell((short)9);
            celda.setCellValue("Fecha Hasta");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchEvaluacion cr=(LkBatchEvaluacion)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getTipodocumento()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getNrodocumento()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getCod_cargo()));
                celda= fila.createCell((short)4);    
                celda.setCellValue(datetoString(cr.getFec_inicio()));               
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getTipoevaluacion()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getCalificacion()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(leoCampo(cr.getMotivoevaluacion()));
                celda= fila.createCell((short)8);
                celda.setCellValue(datetoString(cr.getFec_desde()));
                celda= fila.createCell((short)9);
                celda.setCellValue(datetoString(cr.getFec_hasta()));
            }
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxEVALUACION.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSRemuneraciones(List<LkBatchRemuneracion> lcr,String path, Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Cargo Asignado"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Tipo de documento de identidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Numero de documento de identidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Codigo del cargo");
            celda= fila.createCell((short)4);
            celda.setCellValue("Fecha de inicio");
            celda= fila.createCell((short)5);
            celda.setCellValue("Importe");
            celda= fila.createCell((short)6);
            celda.setCellValue("Codigo del Concepto Remunerativo");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchRemuneracion cr=(LkBatchRemuneracion)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getTipodocumento()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getNrodocumento()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getCod_cargo()));
                celda= fila.createCell((short)4);    
                celda.setCellValue(datetoString(cr.getFec_inicio()));              
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getImporte()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getCodigo()));
            }
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxREMUNERACION.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSConstancias(List<LkBatchConstancia> lcr,String path,Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Cargo Asignado"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
            celda.setCellValue("Tipo de documento de identidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Numero de documento de identidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Codigo del cargo");
            celda= fila.createCell((short)4);
            celda.setCellValue("Fecha de inicio en el cargo");
            celda= fila.createCell((short)5);
            celda.setCellValue("Categoria de la Constancia");
            celda= fila.createCell((short)6);
            celda.setCellValue("Tipo de Constancia Documental");
            celda= fila.createCell((short)7);
            celda.setCellValue("Fecha de entrega");
            celda= fila.createCell((short)8);
            celda.setCellValue("Es Obligatorio");
            celda= fila.createCell((short)9);
            celda.setCellValue("Documento Entregado");
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchConstancia cr=(LkBatchConstancia)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getTipodocumento()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getNrodocumento()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getCod_cargo()));
                celda= fila.createCell((short)4);    
                celda.setCellValue(datetoString(cr.getFec_inicio()));              
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getCategoriaconstancia()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getTipoconstancia()));                 
                celda= fila.createCell((short)7);
                celda.setCellValue(datetoString(cr.getFecha()));
                celda= fila.createCell((short)8);
                celda.setCellValue(BoolFrom(cr.getObligatorio()));
                celda= fila.createCell((short)9);
                celda.setCellValue(BoolFrom(cr.getEntrego()));
            }
            
            ajustaColumnas(hoja1);
            //generar
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxCONSTANCIA.xls");
        }
        
        return errores;
    }
    
    public List<String> generadoXLSTrabajador(List<LkBatchTrabajador> lcr,String path,Session session,Entidad ue){
        List<String> errores = new LinkedList<String>();
        try{
            HSSFWorkbook objWB = new HSSFWorkbook();
            HSSFSheet hoja1 = objWB.createSheet("Entidad"); 
            //titulos
            HSSFRow fila = hoja1.createRow((short)(0));
            HSSFCell celda;
            celda= fila.createCell((short)0);
            celda.setCellValue("Codigo de la Entidad(CUE)");
            celda= fila.createCell((short)1);
             celda.setCellValue("Tipo de documento de identidad");
            celda= fila.createCell((short)2);
            celda.setCellValue("Numero de documento de identidad");
            celda= fila.createCell((short)3);
            celda.setCellValue("Nombres ");
            celda= fila.createCell((short)4);
            celda.setCellValue("Apellido Paterno");
            celda= fila.createCell((short)5);
            celda.setCellValue("Apellido Materno");
            celda= fila.createCell((short)6);
            celda.setCellValue("Sex");
            celda= fila.createCell((short)7);
            celda.setCellValue("Estado Civil");
            celda= fila.createCell((short)8);
            celda.setCellValue("Pais de Nacimiento");
            celda= fila.createCell((short)9);
            celda.setCellValue("Fecha de Nacimiento");
            celda= fila.createCell((short)10);
            celda.setCellValue("Correo Personal");
            celda= fila.createCell((short)11);
            celda.setCellValue("Correo Laboral");
            celda= fila.createCell((short)12);
            celda.setCellValue("Numero de RUC");
            celda= fila.createCell((short)13);
            celda.setCellValue("Numero telefono de Celular");
            celda= fila.createCell((short)14);
            celda.setCellValue("Numero de telefono fijo");
            celda= fila.createCell((short)15);
            celda.setCellValue("RNP - OSCE");
            celda= fila.createCell((short)16);
            celda.setCellValue("Nivel de Instruccion");
            celda= fila.createCell((short)17);
            celda.setCellValue("Formacion Profesional");
            celda= fila.createCell((short)18);
            celda.setCellValue("Otra Informacion de Formacion Profesional");
            celda= fila.createCell((short)19);
            celda.setCellValue("Tipo de Via");
            celda= fila.createCell((short)20);
            celda.setCellValue("Nombre de la via");
            celda= fila.createCell((short)21);
            celda.setCellValue("Tipo de Zona");
            celda= fila.createCell((short)22);
            celda.setCellValue("Nombre de la Zona");
            celda= fila.createCell((short)23);
            celda.setCellValue("Departamento");
            celda= fila.createCell((short)24);
            celda.setCellValue("Provincia");
            celda= fila.createCell((short)25);
            celda.setCellValue("Distrito");
            celda= fila.createCell((short)26);
            celda.setCellValue("Tipo de Discapacidad");
            celda= fila.createCell((short)27);
            celda.setCellValue("Numero de certidicado del CONADIS");
            celda= fila.createCell((short)28);
            celda.setCellValue("Codigo de ESSALUD");
            celda= fila.createCell((short)29);
            celda.setCellValue("Grupo Sanguineo");
            celda= fila.createCell((short)30);
            celda.setCellValue("Sistema Pensionario");
            celda= fila.createCell((short)31);
            celda.setCellValue("Organizacion Pensionario");
            celda= fila.createCell((short)32);
            celda.setCellValue("Numero de CUSPP o Codigo ONP");
            celda= fila.createCell((short)33);
            celda.setCellValue("Indicador. Tiene EPS.");
            celda= fila.createCell((short)34);
            celda.setCellValue("Nombre de EPS");
            celda= fila.createCell((short)35);
            celda.setCellValue("Indicador. Recibe Pensión.");
            celda= fila.createCell((short)36);
            celda.setCellValue("Nombre de contacto en caso de emergencia");
            celda= fila.createCell((short)37);
            celda.setCellValue("Direccion del contacto");
            celda= fila.createCell((short)38);
            celda.setCellValue("Primer Telefono del Contacto");
            celda= fila.createCell((short)39);
            celda.setCellValue("Segundo Telefono del Contacto");
            celda= fila.createCell((short)40);
            celda.setCellValue("Codigo del Legajo en la Entidad"); 
            
            for (int i=0;i<lcr.size();i++) {
                LkBatchTrabajador cr=(LkBatchTrabajador)lcr.get(i);
                fila = hoja1.createRow((short)(i+1));            
                celda= fila.createCell((short)0);
                celda.setCellValue(leoCampo(ue.getCue_entidad()));
                celda= fila.createCell((short)1);
                celda.setCellValue(leoCampo(cr.getTipodocumento()));
                celda= fila.createCell((short)2);
                celda.setCellValue(leoCampo(cr.getNroDocumento()));
                celda= fila.createCell((short)3);
                celda.setCellValue(leoCampo(cr.getNombres()));
                celda= fila.createCell((short)4);
                celda.setCellValue(leoCampo(cr.getApellidoPaterno()));
                celda= fila.createCell((short)5);
                celda.setCellValue(leoCampo(cr.getApellidoMaterno()));
                celda= fila.createCell((short)6);
                celda.setCellValue(leoCampo(cr.getSexo()));
                celda= fila.createCell((short)7);
                celda.setCellValue(leoCampo(cr.getEstadocivil()));
                celda= fila.createCell((short)8);
                celda.setCellValue(leoCampo(cr.getPais()));
                celda= fila.createCell((short)9);
                celda.setCellValue(datetoString(cr.getFechaNacimiento()));
                celda= fila.createCell((short)10);
                celda.setCellValue(leoCampo(cr.getEmailPersonal()));
                celda= fila.createCell((short)11);
                celda.setCellValue(leoCampo(cr.getEmailLaboral()));
                celda= fila.createCell((short)12);
                celda.setCellValue(leoCampo(cr.getNroRUC()));
                celda= fila.createCell((short)13);
                celda.setCellValue(leoCampo(cr.getTelefonocelular()));
                celda= fila.createCell((short)14);
                celda.setCellValue(leoCampo(cr.getTelefonofijo()));
                celda= fila.createCell((short)15);
                celda.setCellValue(leoCampo(cr.getCodigoOSCE()));
                celda= fila.createCell((short)16);
                celda.setCellValue(leoCampo(cr.getNivelinstruccion()));
                celda= fila.createCell((short)17);
                celda.setCellValue(leoCampo(cr.getFormacionprofesional()));
                celda= fila.createCell((short)18);
                celda.setCellValue(leoCampo(cr.getFormacionInfAdicional()));
                celda= fila.createCell((short)19);
                celda.setCellValue(leoCampo(cr.getTipovia()));
                celda= fila.createCell((short)20);
                celda.setCellValue(leoCampo(cr.getDomicilioDireccion()));
                celda= fila.createCell((short)21);
                celda.setCellValue(leoCampo(cr.getTipozona()));
                celda= fila.createCell((short)22);
                celda.setCellValue(leoCampo(cr.getDomicilioCodigoPostal()));
                celda= fila.createCell((short)23);
                celda.setCellValue(leoCampo(cr.getCod_dom_dept()));
                celda= fila.createCell((short)24);
                celda.setCellValue(leoCampo(cr.getCod_dom_prov()));
                celda= fila.createCell((short)25);
                celda.setCellValue(leoCampo(cr.getCod_dom_dist()));
                celda= fila.createCell((short)26);
                celda.setCellValue(leoCampo(cr.getTipodiscapacidad()));
                celda= fila.createCell((short)27);
                if(cr.getNroCertificadoCONADIS()!=null)
                    celda.setCellValue(leoCampo(String.valueOf(cr.getNroCertificadoCONADIS())));
                else
                    celda.setCellValue("");
                celda= fila.createCell((short)28);
                celda.setCellValue(leoCampo(cr.getEsSalud()));
                celda= fila.createCell((short)29);
                celda.setCellValue(leoCampo(cr.getGruposanguineo()));
                celda= fila.createCell((short)30);
                celda.setCellValue(leoCampo(cr.getSistemapensionario()));
                celda= fila.createCell((short)31);
                celda.setCellValue(leoCampo(cr.getRegimenpensionario()));
                celda= fila.createCell((short)32);
                celda.setCellValue(leoCampo(cr.getNumregimenpensionario()));
                celda= fila.createCell((short)33);
                celda.setCellValue(BoolFrom(cr.getEps()));
                celda= fila.createCell((short)34);
                celda.setCellValue(leoCampo(cr.getNombreeps()));
                celda= fila.createCell((short)35);
                celda.setCellValue(BoolFrom(cr.getRecibepension()));
                celda= fila.createCell((short)36);
                celda.setCellValue(leoCampo(cr.getEmergenciaNombre()));
                celda= fila.createCell((short)37);
                celda.setCellValue(leoCampo(cr.getEmergenciaDomicilio()));
                celda= fila.createCell((short)38);
                celda.setCellValue(leoCampo(cr.getEmergenciaTelefonoAlternativo1()));
                celda= fila.createCell((short)39);
                celda.setCellValue(leoCampo(cr.getEmergenciaTelefonoAlternativo2()));
                celda= fila.createCell((short)40);
                celda.setCellValue(leoCampo(cr.getCod_legajo()));          
            }
      
            ajustaColumnas(hoja1);
            //generar            
            File objFile = new File(path);
            FileOutputStream archivoSalida = new FileOutputStream(objFile);
            objWB.write(archivoSalida);
            archivoSalida.close();
        }catch(Exception e){
            errores.add(ERROR_GENERANDO_EL_ARCHIVO + "xxxTRABAJADOR.xls");
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
