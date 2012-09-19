/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import org.hibernate.Query;

/**
 *
 * @author ale
 */
public class TransferenciaEntidades extends GeneralPage{


    @Inject
    private Session session;

    @Property
    @SessionState
    private Entidad entidadUE;
    
    @Property
    @SessionState
    private Usuario usuario;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Zone EOrigenZone;
    
    @InjectComponent
    private Zone EDestiZone;

    @Inject
    private Request _request;

    @Inject
    private ComponentResources _resources;
    
    @Persist
    @Property
    private String entidad_origen;    
    @Persist
    @Property
    private String entidad_destino;    
    @Persist
    @Property
    private String bdenoentidad;    
    @Property
    @Persist
    private boolean mostrar;
    
    @Property
    @Persist
    private LkBusquedaEntidad entio;   
    
    @Property
    @Persist
    private LkBusquedaEntidad entid;
    
    @Property
    @Persist
    private boolean entixo;    
    @Property
    @Persist
    private boolean entixd; 
    
    @InjectComponent
    private Zone entiZone;    
    @InjectComponent
    private Zone busZone;    
    @InjectComponent
    private Envelope envelope;    
    @Component(id = "formBotones")
    private Form formBotones;
    
    @InjectComponent
    private Zone botonZone;
    
    private int num=0,num2=0;
    
    @Property
    @Persist
    private Entidad entidad1;    
    @Property
    @Persist
    private Entidad entidad2;    
    @Property
    @Persist
    private LkBusquedaCursos lkcu;    
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TRANSFERENCIA);
        return null;
    }
    
    @Log
    @SetupRender
    private void inicio() {
        limpiar();
        logueo();
    }
    
        
    @Log
    @CommitAfter
    Object onSuccessFromformEOrigen() {
        entixo=true;        
        return new MultiZoneUpdate("EDestiZone", EDestiZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    
    @Log
    @CommitAfter
    Object onSuccessFromformEDestino() {
        entixd=true;        
        return new MultiZoneUpdate("EDestiZone", EDestiZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    void onSelectedFromCancel() {        
        num2=2;
        entixo=false;
        entixd=false;
        mostrar=false;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        if(num2==2){
            
        }
        else{
           mostrar=true;
        }
        
        return new MultiZoneUpdate("busZone", busZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (bdenoentidad != null && !bdenoentidad.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()));
        }        
        return c.list();
    }
    
    @Log
    Object onActionFromEditar(Entidad enti1) {        
        //entio = enti1;        
//        entidad1.setId(entio.getId());
        entidad1=enti1;
        entidad_origen=entidad1.getDenominacion();
        entixo=false;
        return EOrigenZone.getBody();  
    }
    
    @Log
    Object onActionFromSelec (Entidad enti2) {        
        entidad2 = enti2;
        entidad_destino=entidad2.getDenominacion();
//        entidad2.setId(entid.getId());
        entixd=false;
        return EDestiZone.getBody();  
    }
    
    void onSelectedFromCancelar() {
        num=3;        
    }
    
    void onSelectedFromReset() {        
        num=2;          
        limpiar();
    }
    void limpiar(){
        entidad1=null;
        entidad_destino=null;
        entidad2=null;
        entidad_origen=null;
    }
    @Log
    @CommitAfter
    Object onSuccessFromFormBotones() {
    if(num==2){
        return zonasDatos();
    }
    else if(num==3){
        return Alerta.class;
    }
    else{
        formBotones.clearErrors();
        if(entidad1==null){
            formBotones.recordError("Tiene que seleccionar Entidad/Sub Entidad Origen");
            return zonasDatos();
        }
        if(entidad2==null){
            formBotones.recordError("Tiene que seleccionar Entidad/Sub Entidad Destino");
            return zonasDatos();
        }
        if(entidad1.getId()==entidad2.getId()){
            formBotones.recordError("La Entidad/Sub Entidad Origen tiene que ser diferente a la de Destino");
            return zonasDatos();
        }
//        System.out.println("priiiiii"+entidad1.getId()+"-"+entidad2.getId());
        ejecutar(entidad1,entidad2);
        envelope.setContents(helpers.Constantes.TRANSFERENCIA_EXITO);
        limpiar();
    }

    return zonasDatos();
    }

    void ejecutar(Entidad eo,Entidad ed){
        System.out.println("aquiiiii"+eo.getId()+"-"+ed.getId());
        Query query = session.getNamedQuery("callSpTransferencia");
        query.setParameter("as_entidad_id_origen", eo.getId());
        query.setParameter("as_entidad_id_destino", ed.getId());
        List result = query.list();
        session.flush();
        new Logger().loguearOperacion(session, usuario, "", Logger.CODIGO_OPERACION_EXECUTE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TRANSFERENCIA);
    }
    
    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("EDestiZone", EDestiZone.getBody())                                
                    .add("botonZone", botonZone.getBody());
        return mu;

    }

}
