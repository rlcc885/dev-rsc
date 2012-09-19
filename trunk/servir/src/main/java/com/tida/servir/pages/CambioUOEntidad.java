/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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

    @Property
    @Persist
    private LkBusquedaUnidad uoOrigen;

    @Property
    @Persist
    private LkBusquedaUnidad uoDestino;

    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicasOrigen;

    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicasDestino;


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
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        return null;
    }
    // inicio de pagina
    @Log
    @SetupRender
    private void inicio() {
        logueo();
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
    
    // accion de botones del formulario
    @Log
    Object onActionFromEditar(Entidad enti1) {        
        entidad1=enti1;
        entidad_origen=entidad1.getDenominacion();
        entixo=false;
        uoOrigen=null;
        return EOrigenZone.getBody();  
    }
    
    @Log
    Object onActionFromSeleccionar(Entidad enti2) {        
        entidad2 = enti2;
        entidad_destino=entidad2.getDenominacion();
        entixd=false;
        uoDestino=null;
        return EDestiZone.getBody();  
    }
    
    void onSelectedFromCancelar() {
        num=3;        
    }
    
    void onSelectedFromAceptar() {
        num=1;        
    }
    
    void onSelectedFromReset() {        
        num=2;   
        migras=true;        
        entid=null;
        uoDestino=null;
        entidad_destino=null;
        entidad2=null;
        entidad1=null;
        entio=null;
        migras=false;
//        if(entio!=null){
//            entio=null;
//            entidad1=null;
//            List<LkBusquedaUnidad> list;
//            Criteria c = session.createCriteria(LkBusquedaUnidad.class);
//            c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
//            c.add(Restrictions.eq("entidadId", entidadUE.getId() ));        
//            list = c.list();
//            _beanUOrganicasOrigen = new GenericSelectModel<LkBusquedaUnidad>(list,LkBusquedaUnidad.class,"denominacion","id",_access);       
//        }
        uoOrigen=null;
        entidad_origen=null;
    }
    
    // formulario principal
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
        
        if(uoOrigen==null){
            formBotones.recordError("Debe seleccionar U. Orgánica Origen");
            return botonZone.getBody();
        }
        if(uoDestino==null){
            formBotones.recordError("Debe seleccionar U. Orgánica Destino");
            return botonZone.getBody();
        }   
        System.out.println("destinooooo"+uoOrigen.getId()+"-"+uoDestino.getId());
        if(uoOrigen.getId().equals(uoDestino.getId())){
            formBotones.recordError("La U. Orgánica Origen no debe ser igual a la U. Orgánica Destino");
            System.out.println("aquiiierrorrrr");
            return botonZone.getBody();
        }
        if(migras){//migrar                    
            
            if(entidad2==null){
                formBotones.recordError("Debe seleccionar Entidad Destino");
                return botonZone.getBody();
            }
            if(entidad1==null){ //validar x usuario 
                if(entidadUE.getEntidad()==entidad2.getEntidad()){
                    formBotones.recordError("La Entidad Origen debe ser diferente a la Entidad Destino");
                    return botonZone.getBody();
                }
                else{
                    ejecutar(entidadUE,entidad2,uoOrigen,uoDestino,2);
                    envelope.setContents(helpers.Constantes.MIGRACION_EXITO);                    
                }

            }
            else{      

                if(entidad1.getEntidad()==entidad2.getEntidad()){
                    formBotones.recordError("La Entidad Origen debe ser diferente a la Entidad Destino");
                    return botonZone.getBody();
                }
                else{
                    ejecutar(entidad1,entidad2,uoOrigen,uoDestino,2);
                    envelope.setContents(helpers.Constantes.MIGRACION_EXITO);
                }
                    
            }           

        }
        else{//fusionar 
            if(entidad1==null){ //validar x usuario                
                ejecutar(entidadUE,entidadUE,uoOrigen,uoDestino,1);
                envelope.setContents(helpers.Constantes.FUSION_EXITO);

            }
            else{                  
                ejecutar(entidad1,entidad2,uoOrigen,uoDestino,1);
                envelope.setContents(helpers.Constantes.FUSION_EXITO);

            }            
        }

    }
    onSelectedFromReset();
    return zonasDatos();
    }

    void ejecutar(Entidad eo,Entidad ed,LkBusquedaUnidad uoo,LkBusquedaUnidad uod,int tipo){
        System.out.println("aquiiiii"+eo.getId()+"-"+ed.getId()+"-"+uoo.getId()+"-"+uod.getId()+"-"+tipo);
        Query query = session.getNamedQuery("callSpFucionMigracion");
        query.setParameter("as_entidad_id_origen", eo.getId());
        query.setParameter("as_entidad_id_destino", ed.getId());
        query.setParameter("an_unidad_origen", uoo.getId());
        query.setParameter("an_unidad_destino", uod.getId());
        query.setParameter("an_tipo_proceso", tipo);
        List result = query.list();
        session.flush();
        new Logger().loguearOperacion(session, usuario, "", Logger.CODIGO_OPERACION_EXECUTE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_MIGRACION_FUSION);

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

    // cargar combos    
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

    // evento de cambio de valor en los campos
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
