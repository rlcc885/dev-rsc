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
public class CambioUOEntidad extends GeneralPage{


    @Inject
    private Session session;

    @Property
    @SessionState
    private Entidad entidadUE;
    
    @Property
    @SessionState
    private Usuario usuario;

//    @Property
//    @Persist
//    private Integer nivelOrigen;
//
//    @Property
//    @Persist
//    private Integer nivelDestino;

    @Property
    @Persist
    private UnidadOrganica uoOrigen;

    @Property
    @Persist
    private UnidadOrganica uoDestino;

//    @Property
//    @Persist
//    private Entidad entidadDestino;

    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicasOrigen;

    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicasDestino;


    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Zone EOrigenZone;

//    @Property
//    @InjectComponent
//    private Zone NivelDestinoZone;

//    @Property
//    @InjectComponent
//    private Zone UOOrigenNivelZone;

//    @InjectComponent
//    private Zone UODestinoZone;
    
    @InjectComponent
    private Zone EDestiZone;

//    @InjectComponent
//    private Zone UOChangeZone;

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
    @Property
    @Persist
    private boolean mostrarUO;  
    
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
    @Property
    @Persist
    private boolean migras;    
    @Property
    @Persist
    private Integer nivelo;
    @Property
    @Persist
    private Integer niveld;
      
    @Log
    @SetupRender
    private void inicio() {
        migras=false;
        if(usuario.getRolid()==3){
            mostrarUO=true;
        }
        else{
            mostrarUO=false;
        }
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformOpciones(){
        if(migras){
            migras=true;
        }
        else{
            migras=false;
        }
        return EDestiZone.getBody();
    }
        
    @Log
    @CommitAfter
    Object onSuccessFromformEOrigen(){
        entixo=true; 
        System.out.println("Opcionnnnn"+migras);
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody())                             
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
        uoOrigen=null;
        return EOrigenZone.getBody();  
    }
    
    @Log
    Object onActionFromSelec (Entidad enti2) {        
        entidad2 = enti2;
        entidad_destino=entidad2.getDenominacion();
//        entidad2.setId(entid.getId());
        entixd=false;
        uoDestino=null;
        return EDestiZone.getBody();  
    }
    
    void onSelectedFromCancelar() {
        num=3;        
    }
    
    void onSelectedFromReset() {        
        num=2;   
        migras=true;        
        entid=null;
        uoDestino=null;
        entidad_destino=null;
        entidad2=null;
        if(entio!=null){
            entio=null;
            entidad1=null;
            List<LkBusquedaUnidad> list;
            Criteria c = session.createCriteria(LkBusquedaUnidad.class);
            c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
            c.add(Restrictions.eq("entidadId", entidadUE.getId() ));        
            list = c.list();
            _beanUOrganicasOrigen = new GenericSelectModel<LkBusquedaUnidad>(list,LkBusquedaUnidad.class,"denominacion","id",_access);       
        }
        uoOrigen=null;
        entidad_origen=null;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormBotones() {
    if(num==2){
        return zonasDatos();
    }
    else if(num==3){
        return CambioEntidad.class;
    }
    else{
        formBotones.clearErrors();
        if(migras){//migrar
            if(uoOrigen==null){
                formBotones.recordError("Debe seleccionar U. Orgánica Origen");
                return botonZone.getBody();
            }
            if(entidad2==null){
                formBotones.recordError("Debe seleccionar Entidad Destino");
                return botonZone.getBody();
            }
            if(entidad1==null){ //validar x usuario 
                if(entidadUE==entidad2){
                    formBotones.recordError("La Entidad Origen debe ser diferente a la Entidad Destino");
                }
//                    Helpers.migrarUOBase(uoOrigen, entidadUE, entidad2, session);              
                envelope.setContents("Unidad Orgánica Migrada Correctamente1");
            }
            else{      
//                     Helpers.migrarUOBase(uoOrigen, entidad1, entidad2, session);
                if(entidad1==entidad2){
                    formBotones.recordError("La Entidad Origen debe ser diferente a la Entidad Destino");
                }
                    envelope.setContents("Unidad Orgánica Migrada Correctamente");
            }           

        }
        else{//fusionar                   
            if(uoOrigen==null){
                formBotones.recordError("Debe seleccionar U. Orgánica Origen");
                return botonZone.getBody();
            }
            if(uoDestino==null){
                formBotones.recordError("Debe seleccionar U. Orgánica Destino");
                return botonZone.getBody();
            }            
            if(uoOrigen.getId()==uoDestino.getId()){
                formBotones.recordError("La U. Orgánica Origen no debe ser igual a la U. Orgánica Destino");
                return botonZone.getBody();
            }
            if(entidad1==null){ //validar x usuario                
//                  Helpers.fusionarUOBase(uoOrigen, entidadUE, entidad2, uoDestino, session);
                envelope.setContents("Unidad Orgánica Fusionada Correctamente1");

            }
            else{                  
//                        Helpers.fusionarUOBase(uoOrigen, entidad1, entidad2, uoDestino, session);
                envelope.setContents("Unidad Orgánica Fusionada Correctamente");

            }            
        }

    }
    onSelectedFromReset();
//    envelope.setContents(String.valueOf(entidad1)+String.valueOf(uoOrigen)+String.valueOf(entidad2)+String.valueOf(uoDestino));
    return zonasDatos();
    }

    void ejecutar(Entidad eo,Entidad ed,UnidadOrganica uoo,UnidadOrganica uod){
        Query query = session.getNamedQuery("callSpFucionMigracion");
        query.setParameter("as_entidad_id_origen", eo.getId());
        query.setParameter("as_entidad_id_destino", ed.getId());
        query.setParameter("an_unidad_origen", uoo.getId());
        query.setParameter("an_unidad_destino", uod.getId());
        List result = query.list();
        session.flush();
    }
    
    
    public List<Integer> getBeanNivel(Entidad eUE, Integer first){
        List<Integer> nivel = new LinkedList<Integer>();
        Integer nivelMax = 0;

        nivelMax = Helpers.maxNivelUO(eUE, session);

        for(int i=first; i <= nivelMax; i++){
            // Es mas uno porque agregamos hasta un nivel mas
            nivel.add(i);
        }

        return nivel; // nivel 0 van asociadas a las entidades directamente
    }

    @Log
    public List<Integer> getBeanNivelOrigen(){
        if(entidad1==null){
            return getBeanNivel(entidadUE, 1);
        }
        else{
            return getBeanNivel(entidad1, 1);
        }
        
    }

    @Log
    public List<Integer> getBeanNivelDestino(){
        if(entidad2==null){
            return getBeanNivel(entidadUE,1);
        }
        else{
            return getBeanNivel(entidad2, 1);
        }

    }
     
    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicasOrigen(){
        List<LkBusquedaUnidad> list;
        Criteria c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));        
        if (nivelo != null) {
            c.add(Restrictions.eq("nivel", nivelo));
        }
        if(entidad1==null){
            c.add(Restrictions.eq("entidadId", entidadUE.getId() ));
        }
        else{
            c.add(Restrictions.eq("entidadId", entidad1.getId() ));
        }        
        list = c.list();
        _beanUOrganicasOrigen = new GenericSelectModel<LkBusquedaUnidad>(list,LkBusquedaUnidad.class,"denominacion","id",_access);       
        return _beanUOrganicasOrigen;
    }

    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicasDestino(){  
        List<LkBusquedaUnidad> list;
        Criteria c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));   
        
        if(migras){
            if (niveld != null) {
                c.add(Restrictions.eq("nivel", niveld-1));
            }
        }
        else{
           if (niveld != null) {
              c.add(Restrictions.eq("nivel", niveld));
           } 
        }
        
        if(entidad2==null){
            c.add(Restrictions.eq("entidadId", entidadUE.getId() ));
        }
        else{
            c.add(Restrictions.eq("entidadId", entidad2.getId()));
        }    
        list = c.list();        
        return new GenericSelectModel<LkBusquedaUnidad>(list,LkBusquedaUnidad.class,"denominacion","id",_access);
    }
    
    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("EDestiZone", EDestiZone.getBody())                                
                    .add("botonZone", botonZone.getBody());
        return mu;

    }

    @Log
    Object onValueChangedFromUo_nivelo(Integer dato) {
        if (dato != null) {
            nivelo = dato;
        }
        return EOrigenZone.getBody();
    }
    
    @Log
    Object onValueChangedFromUo_niveld(Integer dato) {
        if (dato != null) {
            niveld = dato;
        }
        return EDestiZone.getBody();
    }
}
