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
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;
import org.apache.tapestry5.annotations.Log;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ale
 */
public class TransferenciaMasivaTrabajadores  extends GeneralPage {
    
    private final String STARTPATH = "ArchivosCSV/";

    @Component(id = "formulariodescargarzip")
    private Form formulariodescargarzip;

    private List<String> errores = new LinkedList<String>();
    
    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Entidad _entidadUE;
    
    private LkBusquedaCargo uo;
    
    @Property
    @Persist
    private String archivoDescargar;
    
    @Property
    @Persist
    private boolean respuestaOk;

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
//        InformeXLS myXLS = new InformeXLS();
//        List<Trabajador> ltrabajador = new LinkedList<Trabajador>();
//        List<ConstanciaDocumental> lcd = new LinkedList<ConstanciaDocumental>();
        
        //archivo a descargar
        Date date = new Date();
        String nombreArchivo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        nombreArchivo = "ArchivosCSV Renato - "+sdf.format(date);
        String newlocation = STARTPATH + "GeneracionCSV/" + nombreArchivo +"/";
        archivoDescargar = newlocation + nombreArchivo +".zip";                
        
        System.out.println("------------- new location "+newlocation+" starpath "+STARTPATH);

        File f = new File(newlocation);
        if (!f.exists()) {
            f.mkdirs();
        }
        errores=geXLS.generadoXLSEntidad(_entidadUE, newlocation+"xxxENTIDAD.xls", session);

        Criteria criteriaConcepto = session.createCriteria(ConceptoRemunerativo.class);
        criteriaConcepto.add(Restrictions.eq("entidad_id", _entidadUE.getId()));

        if (!criteriaConcepto.list().isEmpty()) {
            //generacion archivo concepto.csv
            errores = geXLS.generadoXLSConcepto(criteriaConcepto.list(), newlocation+"xxxCONCEPTO.xls",_entidadUE, session);
        }
        
        Criteria criteriaUnidad = session.createCriteria(LkBatchUnidadOrga.class);
        criteriaUnidad.add(Restrictions.eq("entidad", _entidadUE.getId()));
        if (!criteriaUnidad.list().isEmpty()) {
//                //generacion archivo unidad organica.csv
                System.out.println("UNIDAD ORGANICA");
            errores = geXLS.generadoXLSUnidadOrganica(criteriaUnidad.list(), newlocation + "xxxUNIDADORGANICA.xls", session);
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
                errores = geXLS.generadoXLSCargo(lcargo, newlocation + "xxxCARGO.xls", session,_entidadUE);
            }
        Criteria criteriaCargoAsignado = session.createCriteria(LkBatchCargoAsignado.class);
        criteriaCargoAsignado.add(Restrictions.eq("entidadid", _entidadUE.getId()));
        if (!criteriaCargoAsignado.list().isEmpty()) {
//                //generacion archivo unidad organica.csv
            System.out.println("cargo asignado");
            errores = geXLS.generadoXLSCargoAsignado(criteriaCargoAsignado.list(), newlocation + "xxxCARGOASIGNADO.xls", session,_entidadUE);
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
                errores = geXLS.generadoXLSRemuneraciones(lremuneracion, newlocation + "xxxREMUNERACION.xls", session,_entidadUE);
            }
            if(!levaluacion.isEmpty()){
                System.out.println("EVALUACIONPERSONAL");
                errores = geXLS.generadoXLSEvaluaciones(levaluacion, newlocation + "xxxEVALUACION.xls", session,_entidadUE);
            }
            if(!lconstancia.isEmpty()){
                System.out.println("CONSTANCIA");
                errores = geXLS.generadoXLSConstancias(lconstancia, newlocation + "xxxCONSTANCIA.xls", session,_entidadUE);
            }  
        }
        
        Criteria criteriaTrabajador = session.createCriteria(LkBatchTrabajador.class);
        criteriaTrabajador.add(Restrictions.eq("entidadid", _entidadUE.getId()));
        if (!criteriaTrabajador.list().isEmpty()) {
            //generacion archivo unidad organica.csv
            System.out.println("TRABAJADOR");
            errores = geXLS.generadoXLSTrabajador(criteriaTrabajador.list(), newlocation + "xxxTRABAJADOR.xls", session,_entidadUE);
        } 
 
        
        //zip los CSV en un archivo ZIP
//        errores = Unzip.zippe(newlocation, nombreArchivo +".zip");
//        if (errores.size() > 0 ) { // hay errores
//            for(String error: errores){
//                formulariodescargarzip.recordError(error);
//            }
//            return this;
//        }
        
        

        return this;

    }
}
