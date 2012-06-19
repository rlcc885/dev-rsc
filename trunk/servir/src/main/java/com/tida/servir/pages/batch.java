/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


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

import java.io.IOException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author Morgan
 */
public class batch  extends GeneralPage {
    private final String STARTPATH = "ArchivosCSV/";

    private List<String> errores = new LinkedList<String>();
    
    @Component(id = "formularioconfirmacion")
    private Form formularioconfirmacion;

    @Component(id = "formularioprocesobatch")
    private Form formularioprocesobatch;

    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    @Property
    private String origenArchivos;
    
    @Property
    private String tipoProceso;
    
    @Property
    @Persist
    private String carpeta;

    @Property
    private String path;

    @Property
    @SessionState
    private Entidad_BK _entidadUE;

    
    public List<String> getOrigenesArchivos() {
        List<String> org = new ArrayList<String>();
        org.add(OrigenArchivos.CARGA_INICIAL_ORGANISMOS);
        org.add(OrigenArchivos.CARGA_MASIVA_ORGANISMO);
        org.add(OrigenArchivos.CARGA_MASIVA_ORGANISMOS);
        //org.add(OrigenArchivos.PROCESO_OFFLINE);

        return org;
    }
    
    public Boolean getNoEsAdmSystema(){
        if(_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA))
            return Boolean.FALSE;
        
        return Boolean.TRUE;
    }
    
    public List<String> getCarpetas() {
        List<String> carpetas = new LinkedList<String>();
        File dir = new File(STARTPATH);

    
       for(File file : dir.listFiles()) {
           if (file.isDirectory()) 
               carpetas.add(file.getName());
       }
       return carpetas;    
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

        //System.out.println("--------------------CARGAMENTO ARCHIVOS: Etapa confirmacion----------------------");
        
        //cargo de los archivos en su objecto proprio
        if(cancelar) {
            etapaInicio = true;
            etapaConfirmacion = false;

            return this;
        }
        
        //tratamiento
        myTratamiento.setTipoProceso(tipoProceso);
        errores = myTratamiento.generacionListDesdeCSV();
        
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

        respuestaOk = true;
        return this;
    }


    @CommitAfter
    Object onSuccessFromformularioprocesobatch(){
        respuestaOk = false;
        lla = new LinkedList<LineasArchivos>();
        path = STARTPATH + carpeta + "/";
        
        //System.out.println("-------------------------- Etapa Unzip -------------");

        //unzip el archivo
        errores = Unzip.deZippe(path);
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formularioprocesobatch.recordError(error);
            }
            return this;
        }
       
        //System.out.println("-------------------------- Etapa Inicio -------------");
        try {
            myTratamiento = new Tratamiento(path, origenArchivos,  session, errores, tipoProceso, _usuario);
        } catch (IOException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
            formularioprocesobatch.recordError("Error al leer el archivo de Entidades Unidades Ejecutoras (ORGAN1).");
            return this;
        } catch (ParseException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
            formularioprocesobatch.recordError("Error al parsear el archivo csv de Entidades Unidades Ejecutoras (ORGAN1).");
            return this;
        }

        if (errores.size() > 0 ) { // hay errores
            formularioprocesobatch.recordError(helpers.Errores.HAY_ERRORES_PROCESAR_ARCHIVO_ENTIDADUE);
            for(String error: errores){
                formularioprocesobatch.recordError(error);
                return this;
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
