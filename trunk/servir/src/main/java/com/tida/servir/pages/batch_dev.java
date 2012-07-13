/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


import Batch.Helpers.CreadorDesdeCsv;
import Batch.Helpers.LineasArchivos;
import Batch.Helpers.OrigenArchivos;
import Batch.Helpers.TipoProceso;
import Batch.Helpers.TratamientoXLS;
import Batch.Helpers.Unzip;
import Batch.Tratamiento;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

/**
 *
 * @author Morgan
 */
public class batch_dev  extends GeneralPage {
    private final String STARTPATH = "ArchivosCSV";
    private final String ARCHIVO_UPLOAD_DIFFERENTE_ZIP= "Por favor necesita de ingresar un archivo que tiene un extension .zip o .ZIP!";

    @Property
    @Persist
    private String paraDescargar;
    
    @Persist
    private String lugarArchivo ;
    
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
    private String origenArchivos;
    @Property
    private String tipoProceso;
    
   
    /*@Property
    @Persist
    private String carpeta;*/
    
    @Property
    @Persist
    private UploadedFile file;


    @Property
    @SessionState
    private Entidad_BK _entidadUE;

    public List<String> getOrigenesArchivos() {
        List<String> org = new ArrayList<String>();
        if(!_usuario.getTipo_usuario().equals(Usuario.OPERADORABMLOCAL)){
            org.add(OrigenArchivos.CARGA_INICIAL_ORGANISMOS);
            org.add(OrigenArchivos.CARGA_MASIVA_ORGANISMOS);
        }
        org.add(OrigenArchivos.CARGA_MASIVA_ORGANISMO);
        
        //org.add(OrigenArchivos.PROCESO_OFFLINE);

        return org;
    }

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
    
    public List<String> getTiposProceso() {
        List<String> tipos = new ArrayList<String>();
        tipos.add(TipoProceso.SOLO_VALIDACION);
        tipos.add(TipoProceso.VALIDACION_Y_PROCESAMIENTO);

        return tipos;
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
    private Tratamiento myTratamiento;


    private boolean continuar;
    private boolean cancelar;


    void onActivate() {
        if (etapaInicio == null) {
            etapaInicio = true;
            etapaConfirmacion = false;
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
        myTratamiento.setTipoProceso(tipoProceso);
        //errores = myTratamiento.generacionListDesdeCSV();
        
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioconfirmacion.recordError(error);
            }
            return this;
        }
        
        etapaInicio = true;
        etapaConfirmacion = false;

        System.out.println("--------------------GENERACION XLS----------------------");

        errores = TratamientoXLS.generarXLS(myTratamiento);
        
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioconfirmacion.recordError(error);
            }
            return this;
        }
        
        
        etapaInicio = true;
        etapaConfirmacion = false;
        
        Unzip.zippeXLS(lugarArchivo, nombreArchivo);

        respuestaOk = true;
        
        
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
                lugarArchivo = STARTPATH + "/" + nombreArchivo + "-" +sdf.format(date)+ "-"+aleatorio+"/";
                paraDescargar = lugarArchivo + nombreArchivo + "XLS.zip";
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
       
        System.out.println("-------------------------- Etapa Inicio -------------");
        
//        try {
//            myTratamiento = new Tratamiento(lugarArchivo, origenArchivos,  session, errores, tipoProceso, _usuario);
//        } catch (IOException ex) {
//            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
//            formularioprocesobatch.recordError("Error al leer el archivo de Entidades / U.Ejecutoras.");
//            return this;
//        } catch (ParseException ex) {
//            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
//            formularioprocesobatch.recordError("Error al parsear el archivo csv de Entidades / U.Ejecutoras.");
//            return this;
//        }
        
        //verificacion tipo usuario y carga massiva
        if(!_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)){
            if(myTratamiento.getOrigenArchivo().equals(OrigenArchivos.CARGA_MASIVA_ORGANISMO)){
                //errores = CreadorDesdeCsv.verificacionEntidadPuedeProcesarEstaArchivo(myTratamiento.getEntidadesUE(), _entidadUE);
                if (errores.size() > 0 ) { // hay errores
                    formularioprocesobatch.recordError("Hay errores al procesar el archivo csv de Entidades / U.Ejecutoras.");
                    for(String error: errores){
                        formularioprocesobatch.recordError(error);
                        return this;
                    }
                }
            }
        }else{
            if(myTratamiento.getOrigenArchivo().equals(OrigenArchivos.CARGA_MASIVA_ORGANISMO)){
                //errores = CreadorDesdeCsv.verificacionProcesarCargaMassiva(myTratamiento.getEntidadesUE());
                if (errores.size() > 0 ) { // hay errores
                    formularioprocesobatch.recordError("Hay errores al procesar el archivo csv de Entidades / U.Ejecutoras.");
                    for(String error: errores){
                        formularioprocesobatch.recordError(error);
                        return this;
                    }
                }
            }
        }

        /*
         * Los csv son consistentes entre ellos�
         * Ahora buscamos que sean consistentes con la base de datos.
        */

        lla = myTratamiento.getCantLineasArchivos(errores);

        if (errores.size() > 0 ) { // hay errores
            formularioprocesobatch.recordError("Error procesando csv.");
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
    
   
}
