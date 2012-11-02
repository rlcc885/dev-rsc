/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


import Batch.Helpers.*;
import Batch.Tratamiento;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import helpers.ReporteBatch;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Morgan
 */
public class batch_dev  extends GeneralPage {
    @Persist
    private  String STARTPATH;
    private final String ARCHIVO_UPLOAD_DIFFERENTE_ZIP= "Por favor necesita de ingresar un archivo que tiene un extension .zip o .ZIP!";

    @Property
    @Persist
    private String paraDescargar;
    
    @Persist
    private String lugarArchivo ;
    @Persist
    private String lugarEliminar ;
    
    @Persist
    private String nombreArchivo;
    
    @Property
    @Persist
    private File copied;

    @Property
    @SessionState
    private Usuario _usuario;
    
    private List<String> errores = new LinkedList<String>();
    
    @Component(id = "formularioconfirmacion")
    private Form formularioconfirmacion;

    @Component(id = "formularioprocesobatch")
    private Form formularioprocesobatch;

    @Inject
    private Session session;
    @Property
    @Persist
    private String origenArchivos;
    
   
    /*@Property
    @Persist
    private String carpeta;*/
    
    @Property
    @Persist
    private UploadedFile file;

    //sdsdsd
    @Property
    @SessionState
    private Entidad _entidadUE;    

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String message;


    /*Object onUploadException(FileUploadException ex)
    {
        message = "Upload exception: " + ex.getMessage();
        System.out.println(message);
        return this;
    }*/
    
    
    StreamResponse onActionFromReturnStreamResponse() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
                                File fileADescargar = new File(paraDescargar);
                                
                                try {
                                    inputStream = new FileInputStream(fileADescargar);
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                try {
                                    response.setHeader("Content-Type", "application/x-zip");
                                    response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                    response.setHeader("Content-Length", "" + inputStream.available());
				}
				catch (IOException e) {
			            Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			
			@Override
			public String getContentType() {
				return "application/x-zip";
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

		};
	}    
    
    StreamResponse onActionFromReturnstream() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
                                File fileADescargar;
                                if(_usuario.getRolid()==2)
                                     fileADescargar= new File(STARTPATH+"FormatosAdmEntidad.zip");
                                else
                                     fileADescargar= new File(STARTPATH+"FormatosAdmServir.zip");
                                
                                try {
                                    inputStream = new FileInputStream(fileADescargar);
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                try {
                                    response.setHeader("Content-Type", "application/x-zip");
                                    response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                    response.setHeader("Content-Length", "" + inputStream.available());
				}
				catch (IOException e) {
			            Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			
			@Override
			public String getContentType() {
				return "application/x-zip";
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

		};
	}   
    StreamResponse onActionFromReturnstreamtabla() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
                                File fileADescargar = new File(STARTPATH+"TablasAuxiliaresRSC.xls");
                                
                                try {
                                    inputStream = new FileInputStream(fileADescargar);
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                try {
                                    response.setHeader("Content-Type", "application/x-zip");
                                    response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                    response.setHeader("Content-Length", "" + inputStream.available());
				}
				catch (IOException e) {
			            Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			
			@Override
			public String getContentType() {
				return "application/x-zip";
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

		};
	}   

    @Property
    @Persist
    private List<LineasArchivos> lla;

    @Property
    private LineasArchivos la;

    @Property
    @Persist
    private Boolean etapaInicio;

    @Property
    @Persist
    private Boolean etapaConfirmacion;
    
    
    @Persist
    private ValidacionXLS myTratamiento;


    private boolean continuar;
    private boolean cancelar;

@Persist
@Property
private Boolean iniciarProceso;
@Persist
@Property
private Boolean procesoFin;
@Property
@Persist
private EstadoEntidad estado;
@Property
@Persist
private Boolean procesoExitoso;
    @Log
    @SetupRender
    private void inicio() {        
        STARTPATH=getRuta().get(0).getRuta_final();
    }
    
    @Log
    public List<ConfiguracionAcceso> getRuta() {
        Criteria c = session.createCriteria(ConfiguracionAcceso.class);        
        return c.list();
    } 


    void onActivate() {
        if (etapaInicio == null) {
            etapaInicio = true;
            etapaConfirmacion = false;
        }
        // PROCESO EJECUTANDOSE
       Criteria c = session.createCriteria(EstadoEntidad.class);
       c.add(Restrictions.eq("estado",1));
//        c.add(Restrictions.eq("estado", file));
       estado = (EstadoEntidad)c.uniqueResult();
      procesoExitoso = false;
       iniciarProceso=true;
       procesoFin = true;
       
       if(!c.list().isEmpty()){
           // PROCESO EJECUTANDOSE
           iniciarProceso=false;
       }
       
       c = session.createCriteria(EstadoEntidad.class);
       c.add(Restrictions.eq("cueEntidad", _entidadUE.getCue_entidad()));
       System.out.println("ent "+_entidadUE.getCue_entidad());
       c.add(Restrictions.eq("estado", 2));
       //MOSTRAR LOS REPORTES
       if (c.list().isEmpty()){
       procesoFin=false;
       }else{
       Query q = session.createSQLQuery("SELECT * FROM LKERRORESBATCH");
          if (q.list().isEmpty()){
          procesoFin = false;
          procesoExitoso = true;
          }
       }
       
    }
    
    void onSelectedFromContinuar() {
        continuar=true;
    }

    void onSelectedFromCancelar() {
        cancelar = true;
    }

    @Property
    @Persist
    private boolean respuestaOk;
    
    
    @CommitAfter
    Object onSuccessFromformularioconfirmacion(){

        System.out.println("--------------------CARGAMENTO ARCHIVOS: Etapa confirmacion----------------------");
        
        //cargo de los archivos en su objecto proprio
        if(cancelar) {
            etapaInicio = true;
            etapaConfirmacion = false;

            return this;
        }
        
        //tratamiento
//        myTratamiento.setTipoProceso(tipoProceso);
//        errores = myTratamiento.generacionListDesdeCSV();
        
        try{
            myTratamiento.listArchivosDepositorioCopied(lugarArchivo);
//            System.out.println("aquiiiii"+lugarEliminar);
//            File f = new File(lugarEliminar); 
//            f.delete();                  
        }catch(Exception e){

        }
        
        Query query = session.getNamedQuery("callSpProcesoBatch");
        query.setParameter("as_cue_entidad", _entidadUE.getCue_entidad());
        query.setParameter("an_tipoproceso", Integer.parseInt(origenArchivos));
        List result = query.list();
        session.flush();
        
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioconfirmacion.recordError(error);
            }
            return this;
        }
        
        etapaInicio = true;
        etapaConfirmacion = false;

        System.out.println("--------------------GENERACION XLS----------------------");

//        errores = TratamientoXLS.generarXLS(myTratamiento);
        
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioconfirmacion.recordError(error);
            }
            return this;
        }
        
        
        etapaInicio = true;
        etapaConfirmacion = false;
        
//        Unzip.zippeXLS(lugarArchivo, nombreArchivo);

//        respuestaOk = true;
        
        
        return this;
    }


    @CommitAfter
    Object onSuccessFromformularioprocesobatch(){
        respuestaOk = false;
        lla = new LinkedList<LineasArchivos>();
        
        System.out.println("-------------------------- Etapa Unzip -------------");
        
        if(file.getFileName().endsWith("zip") || (file.getFileName().endsWith("ZIP"))){
            Date date = new Date();
            int aleatorio = (int) (Math.random() * 1000 + 1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            nombreArchivo = file.getFileName().substring(0, file.getFileName().length() - 4);
            lugarArchivo = STARTPATH +"SUBIDA TXT/"+ nombreArchivo + "-" +sdf.format(date)+ "-"+aleatorio+"/";
            lugarEliminar=STARTPATH +"SUBIDA TXT/"+ nombreArchivo + "-" +sdf.format(date)+ "-"+aleatorio;
//            lugarArchivo="C:/CARGA/file/";
            paraDescargar = lugarArchivo + nombreArchivo + "TXT.zip";
            String archivoXLS = lugarArchivo + file.getFileName();
            copied = new File(lugarArchivo);
            if (!copied.exists()) {
                copied.mkdirs();
            }

            File nuevo = new File(archivoXLS);
            file.write(nuevo);

        }else{
            errores.add(ARCHIVO_UPLOAD_DIFFERENTE_ZIP);
        }

        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioprocesobatch.recordError(error);
            }
        }
        
        //unzip el archivo
        errores = Unzip.deZippe(lugarArchivo);
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioprocesobatch.recordError(error);
            }
            return this;
        }
        
        System.out.println("-------------------------- Etapa Inicio -------------"+origenArchivos);
        
        try {
            myTratamiento = new ValidacionXLS(lugarArchivo, origenArchivos,  session, errores, _usuario,_entidadUE,Integer.parseInt(origenArchivos));
        } catch (IOException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
            formularioprocesobatch.recordError("Error al leer el archivo de Entidades / U.Ejecutoras.");
            return this;
        } catch (ParseException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
            formularioprocesobatch.recordError("Error al parsear el archivo csv de Entidades / U.Ejecutoras.");
            return this;
        }
        
        //verificacion tipo usuario y carga massiva
//        if(!_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)){
//            if(myTratamiento.getOrigenArchivo().equals(OrigenArchivos.CARGA_MASIVA_ORGANISMO)){
//                errores = CreadorDesdeCsv.verificacionEntidadPuedeProcesarEstaArchivo(myTratamiento.getEntidadesUE(), _entidadUE);
//                if (errores.size() > 0 ) { // hay errores
//                    formularioprocesobatch.recordError("Hay errores al procesar el archivo csv de Entidades / U.Ejecutoras.");
//                    for(String error: errores){
//                        formularioprocesobatch.recordError(error);
//                        return this;
//                    }
//                }
//            }
//        }else{
//            if(myTratamiento.getOrigenArchivo().equals(OrigenArchivos.CARGA_MASIVA_ORGANISMO)){
//                errores = CreadorDesdeCsv.verificacionProcesarCargaMassiva(myTratamiento.getEntidadesUE());
//                if (errores.size() > 0 ) { // hay errores
//                    formularioprocesobatch.recordError("Hay errores al procesar el archivo csv de Entidades / U.Ejecutoras.");
//                    for(String error: errores){
//                        formularioprocesobatch.recordError(error);
//                        return this;
//                    }
//                }
//            }
//        }

        /*
         * Los csv son consistentes entre ellos�
         * Ahora buscamos que sean consistentes con la base de datos.
        */

        lla = myTratamiento.getCantLineasArchivos(errores,_entidadUE,Integer.parseInt(origenArchivos));
        
        if (errores.size() > 0 ) { // hay errores
            formularioprocesobatch.recordError("Error procesando TXT.");
            for(String error: errores){
                formularioprocesobatch.recordError(error);
            }
            return this;

        }        
        etapaInicio = false;
        etapaConfirmacion = true;
        return this;

    }
    
   
    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
    

    
    @Property
    @Persist
    private ReporteBatch.Formatos formato ;

    private String tipoReporte="";

    @OnEvent(value="submit" ,component="reporte") 
    public StreamResponse getReport() throws ClassNotFoundException, SQLException,  IOException, JRException 
    {
    //PARAMETROS DEL REPORTE    
    HashMap<String,Object> parametros = new HashMap<String,Object>();
    parametros.put("EntidadId", _entidadUE.getCue_entidad());
    //    parametros.put("EntidadId", 1);

    // NOMBRE DEL REPORTE
    tipoReporte = "ReporteErrores.jasper";
    
    System.out.println("REPORTE: "+tipoReporte+"PARAMETROS: "+parametros.toString()+"FORMATO:"+formato.toString());

    
    ReporteBatch reportes= new ReporteBatch();
    return  reportes.getReporte(tipoReporte, parametros, formato);
    
    }
    
}
