/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import Batch.Helpers.*;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ale
 */
public class ExportacionBatch  extends GeneralPage {
    @Persist    
    private String STARTPATH;

    @Component(id = "formulariodescargarzip")
    private Form formulariodescargarzip;

    private List<String> errores = new LinkedList<String>();
    
    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Entidad _entidadUE;
    
    @Property
    @SessionState
    private Usuario _logged;
    
    private LkBusquedaCargo uo;
    
    @Property
    @Persist
    private String archivoDescargar;
    
    @Property
    @Persist
    private boolean respuestaOk;
    
    @SetupRender
    void inicio(){
        STARTPATH=getRuta().get(0).getRuta_final();
    }     
    
    @Log
    public List<ConfiguracionAcceso> getRuta() {
        Criteria c = session.createCriteria(ConfiguracionAcceso.class);        
        return c.list();
    }
    
    StreamResponse onActionFromReturnStreamResponse() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
                                File fileADescargar = new File(archivoDescargar);
                                
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
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodescargarzip(){
        respuestaOk = true;     
        GeneracionXLS geXLS=new GeneracionXLS();        
        //archivo a descargar
        Date date = new Date();
        String nombreArchivo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        nombreArchivo = "ArchivosXLS - "+sdf.format(date);
        String newlocation = STARTPATH + "GeneracionXLS/" + nombreArchivo +"/";
        archivoDescargar = newlocation + nombreArchivo +".zip";                
        
        System.out.println("------------- new location "+newlocation+" starpath "+STARTPATH);

        File f = new File(newlocation);
        if (!f.exists()) {
            f.mkdirs();
        }
//        if(_logged.getRolid()!=2){
            errores=geXLS.generadoXLSEntidad(_entidadUE, newlocation+_entidadUE.getCue_entidad()+"ENTIDAD.xls", session);
//        }
        Criteria criteriaConcepto = session.createCriteria(ConceptoRemunerativo.class);
        criteriaConcepto.add(Restrictions.eq("entidad_id", _entidadUE.getId()));

        if (!criteriaConcepto.list().isEmpty()) {
            //generacion archivo concepto.csv
            if(_logged.getRolid()!=2){
                errores = geXLS.generadoXLSConcepto(criteriaConcepto.list(), newlocation+_entidadUE.getCue_entidad()+"CONCEPTO.xls",_entidadUE, session);
            }
        }
        
        Criteria criteriaUnidad = session.createCriteria(LkBatchUnidadOrga.class);
        criteriaUnidad.add(Restrictions.eq("entidad", _entidadUE.getId()));
        if (!criteriaUnidad.list().isEmpty()) {
//                //generacion archivo unidad organica.csv
                System.out.println("UNIDAD ORGANICA");
            errores = geXLS.generadoXLSUnidadOrganica(criteriaUnidad.list(), newlocation +_entidadUE.getCue_entidad()+ "UNIDADORGANICA.xls", session);
        }
            List<LkBatchUnidadOrga> luo = new LinkedList<LkBatchUnidadOrga>();
            luo.addAll(criteriaUnidad.list());
            List<LkBatchCargoxUnidad> lcargo = new LinkedList<LkBatchCargoxUnidad>();
            for (LkBatchUnidadOrga uo : luo) {
                Criteria criteriaCargo = session.createCriteria(LkBatchCargoxUnidad.class);
                criteriaCargo.add(Restrictions.eq("unidadorganicaid", uo.getId()));
                lcargo.addAll(criteriaCargo.list());
            }
            if (!lcargo.isEmpty()) {
                //generacion archivo cargo.csv
                System.out.println("CARGO");
                errores = geXLS.generadoXLSCargo(lcargo, newlocation+_entidadUE.getCue_entidad() + "CARGO.xls", session,_entidadUE);
            }
        Criteria criteriaCargoAsignado = session.createCriteria(LkBatchCargoAsignado.class);
        criteriaCargoAsignado.add(Restrictions.eq("entidadid", _entidadUE.getId()));
        if (!criteriaCargoAsignado.list().isEmpty()) {
//                //generacion archivo unidad organica.csv
            System.out.println("cargo asignado");
            errores = geXLS.generadoXLSCargoAsignado(criteriaCargoAsignado.list(), newlocation+_entidadUE.getCue_entidad() + "CARGOASIGNADO.xls", session,_entidadUE);
            List<LkBatchCargoAsignado> lcargoasignado = new LinkedList<LkBatchCargoAsignado>();
            lcargoasignado.addAll(criteriaCargoAsignado.list());
            
            List<LkBatchEvaluacion> levaluacion = new LinkedList<LkBatchEvaluacion>();
            List<LkBatchRemuneracion> lremuneracion = new LinkedList<LkBatchRemuneracion>();
            List<LkBatchConstancia> lconstancia= new LinkedList<LkBatchConstancia>();
            
            
            for(LkBatchCargoAsignado casi: lcargoasignado){               
                Criteria criteriaRemuneracion= session.createCriteria(LkBatchRemuneracion.class);                
                criteriaRemuneracion.add(Restrictions.eq("cargoasignadoid", casi.getId()));
                lremuneracion.addAll(criteriaRemuneracion.list());
                Criteria criteriaEvaluacion= session.createCriteria(LkBatchEvaluacion.class);                
                criteriaEvaluacion.add(Restrictions.eq("cargoasignadoid", casi.getId()));
                levaluacion.addAll(criteriaEvaluacion.list());
                Criteria criteriaConstancia= session.createCriteria(LkBatchConstancia.class);                
                criteriaConstancia.add(Restrictions.eq("cargoasignadoid", casi.getId()));
                lconstancia.addAll(criteriaConstancia.list());                
            }
            
            if(!lremuneracion.isEmpty()){
                System.out.println("REMUNERACIONPERSONAL");
                if(_logged.getRolid()!=2){
                    errores = geXLS.generadoXLSRemuneraciones(lremuneracion, newlocation+_entidadUE.getCue_entidad() + "REMUNERACION.xls", session,_entidadUE);
                }
            }
            if(!levaluacion.isEmpty()){
                System.out.println("EVALUACIONPERSONAL");
                errores = geXLS.generadoXLSEvaluaciones(levaluacion, newlocation +_entidadUE.getCue_entidad()+ "EVALUACION.xls", session,_entidadUE);
            }
            if(!lconstancia.isEmpty()){
                System.out.println("CONSTANCIA");
                errores = geXLS.generadoXLSConstancias(lconstancia, newlocation +_entidadUE.getCue_entidad()+ "CONSTANCIA.xls", session,_entidadUE);
            }  
        }
        
        Criteria criteriaTrabajador = session.createCriteria(LkBatchTrabajador.class);
        criteriaTrabajador.add(Restrictions.eq("entidadid", _entidadUE.getId()));
        if (!criteriaTrabajador.list().isEmpty()) {
            //generacion archivo unidad organica.csv
            System.out.println("TRABAJADOR");
            errores = geXLS.generadoXLSTrabajador(criteriaTrabajador.list(), newlocation +_entidadUE.getCue_entidad()+ "TRABAJADOR.xls", session,_entidadUE);
            List<LkBatchTrabajador> ltrabajador = new LinkedList<LkBatchTrabajador>();
            ltrabajador.addAll(criteriaTrabajador.list());
            
            List<LkBatchEstudio> lestudio = new LinkedList<LkBatchEstudio>();
            List<LkBatchCurso> lcurso = new LinkedList<LkBatchCurso>();
            List<LkBatchAnteceLabo> lantecedente = new LinkedList<LkBatchAnteceLabo>();
            List<LkBatchPublicacion> lproduccion = new LinkedList<LkBatchPublicacion>();
            List<LkBatchFamiliar> lfamiliar = new LinkedList<LkBatchFamiliar>();
            List<LkBatchMeritoDeme> ldemerito = new LinkedList<LkBatchMeritoDeme>();
            
            for(LkBatchTrabajador tra: ltrabajador){
                Criteria criteriaCurso= session.createCriteria(LkBatchCurso.class);                
                criteriaCurso.add(Restrictions.eq("trabajador_id", tra.getId()));
                criteriaCurso.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                lcurso.addAll(criteriaCurso.list());
                
                Criteria criteriaEstudio= session.createCriteria(LkBatchEstudio.class);                
                criteriaEstudio.add(Restrictions.eq("trabajador_id", tra.getId()));
                criteriaEstudio.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                lestudio.addAll(criteriaEstudio.list());               
                
                
//                System.out.println("aquiiii cursooo"+criteriaCurso.list().size());
                
                Criteria criteriaAntecedente= session.createCriteria(LkBatchAnteceLabo.class);                
                criteriaAntecedente.add(Restrictions.eq("trabajador_id", tra.getId()));
                criteriaAntecedente.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                lantecedente.addAll(criteriaAntecedente.list());
                
                Criteria criteriaProduccion= session.createCriteria(LkBatchPublicacion.class);                
                criteriaProduccion.add(Restrictions.eq("trabajador_id", tra.getId()));
                criteriaProduccion.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                lproduccion.addAll(criteriaProduccion.list());
                
                Criteria criteriaFamiliar= session.createCriteria(LkBatchFamiliar.class);                
                criteriaFamiliar.add(Restrictions.eq("trabajadorid", tra.getId()));
                criteriaFamiliar.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                lfamiliar.addAll(criteriaFamiliar.list());
                
                Criteria criteriaDemerito= session.createCriteria(LkBatchMeritoDeme.class);                
                criteriaDemerito.add(Restrictions.eq("trabajador_id", tra.getId()));
                criteriaDemerito.add(Restrictions.eq("entidadid", _entidadUE.getId()));
                ldemerito.addAll(criteriaDemerito.list());
                
                
            }
            if(!lestudio.isEmpty()){
                System.out.println("ESTUDIOS");
                errores = geXLS.generadoXLSEstudios(lestudio, newlocation +_entidadUE.getCue_entidad()+ "ESTUDIO.xls", session,_entidadUE);
            }
            if(!lcurso.isEmpty()){
                System.out.println("CURSOS");
                errores = geXLS.generadoXLSCursos(lcurso, newlocation +_entidadUE.getCue_entidad()+ "CURSO.xls", session,_entidadUE);
            }
            if(!lantecedente.isEmpty()){
                System.out.println("ANTECEDENTES");
                errores = geXLS.generadoXLSAntecedentes(lantecedente, newlocation +_entidadUE.getCue_entidad()+ "ANTECEDENTE.xls", session,_entidadUE);
            }
            if(!lproduccion.isEmpty()){
                System.out.println("PRODUCCION");
                errores = geXLS.generadoXLSPublicaciones(lproduccion, newlocation +_entidadUE.getCue_entidad()+ "PRODUCCION.xls", session,_entidadUE);
            }
            if(!lfamiliar.isEmpty()){
                System.out.println("FAMILIARES");
                errores = geXLS.generadoXLSFamiliares(lfamiliar, newlocation +_entidadUE.getCue_entidad()+ "FAMILIAR.xls", session,_entidadUE);
            }
            if(!ldemerito.isEmpty()){
                System.out.println("DEMERITO");
                errores = geXLS.generadoXLSMeritos(ldemerito, newlocation +_entidadUE.getCue_entidad()+ "DEMERITO.xls", session,_entidadUE);
            }
            
            
        } 
 
        
        //zip los CSV en un archivo ZIP
        errores = Unzip.zippe(newlocation, nombreArchivo +".zip");
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formulariodescargarzip.recordError(error);
            }
            return this;
        }
        return this;

    }
}
