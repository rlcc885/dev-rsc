/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.LkBusquedaEntidad;
//import com.tida.servir.entities.Usuario;
//import com.tida.servir.entities.RscRol;
import com.tida.servir.entities.UnidadOrganica;
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
    
//    @Property
//    @SessionState
//    private Usuario usuario;
//    
//    @Property
//    @Persist
//    private RscRol rolx;

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
    private GenericSelectModel<UnidadOrganica> _beanUOrganicasOrigen;

    @Persist
    private GenericSelectModel<UnidadOrganica> _beanUOrganicasDestino;


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

    
//    @Component(id = "formUOFusionar")
//    private Form formUOFusionar;
    
    @Persist
    @Property
    private String opcion;
    
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
    private boolean mostrarUOD;
    
//    @Property
//    @Persist
//    private boolean mostrarUO;
    
    
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
    private Object retorno;
    
    @Property
    @Persist
    private Entidad entidad1;
    
    @Property
    @Persist
    private Entidad entidad2;
    
    @Log
    public List<String> getBopciones(){
        List<String> mod = new LinkedList<String>();
        mod.add("MIGRAR");
        mod.add("FUSIONAR");
        return mod; 
    }
    
//    @Log
//    @SetupRender
//    private void inicio() {
//        if(usuario.getRol().getId()==3){
//            mostrarUO=true;
//        }
//        else if(usuario.getRol().getId()==2){
//            mostrarUO=false;
//        }
//        
//    }
    
    
//    @Log
//    @CommitAfter
//    Object onSuccessFromformOpciones(){
//        
//        
//        return new MultiZoneUpdate("UOOrigenZone",UOOrigenZone.getBody())
//                    .add("UOChangeZone", UOChangeZone.getBody());
//    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformEOrigen(){
        entixo=true; 
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
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));      
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
        mostrarUOD=true;
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
        opcion=null;        
        entid=null;
        uoDestino=null;
        entidad_destino=null;
        entidad2=null;
        if(entio!=null){
            entio=null;
            entidad1=null;
            List<UnidadOrganica> list;
            Criteria c = session.createCriteria(UnidadOrganica.class);
            c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
            c.add(Restrictions.eq("entidad", entidadUE ));        
            list = c.list();
            _beanUOrganicasOrigen = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);       
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
        if (opcion != null && !opcion.equals("")) {
           if(opcion.equalsIgnoreCase("MIGRAR")){//migrar
                if(uoOrigen==null){
                    formBotones.recordError("Debe seleccionar U. Orgánica Origen");
                    return botonZone.getBody();
                }
                if(entidad2==null){
                    formBotones.recordError("Debe seleccionar Entidad Destino");
                    return botonZone.getBody();
                }
                if(entidad1==null){ //validar x usuario
                    Helpers.migrarUOBase(uoOrigen, entidadUE, entidad2, session);
                    session.flush();
                    formBotones.clearErrors();                    
                    envelope.setContents("Unidad Orgánica Migrada Correctamente");
                }
                else{
                     Helpers.migrarUOBase(uoOrigen, entidad1, entidad2, session);
                     session.flush();
                     formBotones.clearErrors();
                     envelope.setContents("Unidad Orgánica Migrada Correctamente");
                }           

           }
           else if(opcion.equalsIgnoreCase("FUSIONAR")){//fusionar                   
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
                    if(entidadUE.getId()==entidad2.getId()){
                        Helpers.fusionarUOBase(uoOrigen, entidadUE, entidad2, uoDestino, session);
                        session.flush();
                        formBotones.clearErrors();
                        envelope.setContents("Unidad Orgánica Fusionada Correctamente1");
                    }
                    else{
                        formBotones.recordError("La Entidad Origen debe ser igual a la Entidad Destino");
                        return botonZone.getBody();
                    }
                }
                else{
                    if(entidad1.getId()==entidad2.getId()){
                        Helpers.fusionarUOBase(uoOrigen, entidad1, entidad2, uoDestino, session);
                        session.flush();
                        formBotones.clearErrors();
                        envelope.setContents("Unidad Orgánica Fusionada Correctamente");
                    }
                    else{
                        formBotones.recordError("La Entidad Origen debe ser igual a la Entidad Destino");                    
                        return botonZone.getBody(); 
                    }
                }            
            }
           
        }
        else{
           formBotones.recordError("Debe seleccionar una Opción");
           return botonZone.getBody(); 
        }
    }
    onSelectedFromReset();
//    envelope.setContents(String.valueOf(entidad1)+String.valueOf(uoOrigen)+String.valueOf(entidad2)+String.valueOf(uoDestino));
    return zonasDatos();
    }

//    @Log
//    public boolean getHayNivelOrigen() {
//        return !(nivelOrigen == null) ;
//    }
//
//    @Log
//    public boolean getHayNivelDestino() {
//        return !(nivelDestino == null)&& (nivelDestino > 0);
//    }


//    public boolean getHayEntidadDestino() {
//        return entidadDestino != null;
//    }
//
//    @Log
//    public boolean getActivoSubmit() {
//        boolean salida = getHayEntidadDestino() && (uoOrigen != null);
//        return salida;
//    }

    /**
     * El parámetro first es porque la unidad origen no puede ser nivel 0, pero si la destino
     * @param eUE
     * @param first
     * @return
     */
//    public List<Integer> getBeanNivel(Entidad_BK eUE, Integer first){
//        List<Integer> nivel = new LinkedList<Integer>();
//        Integer nivelMax = 0;
//
//        nivelMax = Helpers.maxNivelUO(eUE, session);
//
//        for(int i=first; i <= nivelMax; i++){
//            // Es mas uno porque agregamos hasta un nivel mas
//            nivel.add(i);
//        }
//
//        return nivel; // nivel 0 van asociadas a las entidades directamente
//    }

//    @Log
//    public List<Integer> getBeanNivelOrigen(){
//        return getBeanNivel(entidadUE, 1);
//    }
//
//    @Log
//    public List<Integer> getBeanNivelDestino(){
//        return getBeanNivel(entidadDestino, 0); // las Entidades (sin uo) pueden ser válidas, luego hay de nivel 0
//
//    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicasOrigen(){
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
        //c.add(Restrictions.eq("nivel", nivelOrigen));
        if(entidad1==null){
            c.add(Restrictions.eq("entidad", entidadUE ));
        }
        else{
            c.add(Restrictions.eq("entidad", entidad1 ));
        }        
        list = c.list();
        _beanUOrganicasOrigen = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);       
        return _beanUOrganicasOrigen;
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicasDestino(){  
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));        
        c.add(Restrictions.eq("entidad", entidad2));
        list = c.list();        
        return new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
    }

//    @Log
//    @CommitAfter
//    Object onSuccessFromformNivelUOOrigen(){
//        List<UnidadOrganica> list;
//        Criteria c = session.createCriteria(UnidadOrganica.class);
//        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
//        c.add(Restrictions.eq("nivel", nivelOrigen));
//        c.add(Restrictions.eq("entidad", entidadUE ));
//        list = c.list();
//        _beanUOrganicasOrigen = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
//        uoOrigen = null;
//        return new MultiZoneUpdate("EOrigenZone",EOrigenZone.getBody())
//                    .add("UOChangeZone", UOChangeZone.getBody());
//    }

//    @Log
//    @CommitAfter
//    Object onSuccessFromFormNivelUODestino(){
//
//        List<UnidadOrganica> list;
//        Criteria c = session.createCriteria(UnidadOrganica.class);
//        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
//        c.add(Restrictions.eq("nivel", nivelDestino));
//        c.add(Restrictions.eq("entidad", entidadDestino ));
//        list = c.list();
//        _beanUOrganicasDestino = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
//        uoDestino = null;
//        return new MultiZoneUpdate("UODestinoZone",UODestinoZone.getBody())
//                    .add("UOChangeZone", UOChangeZone.getBody());
//    }

//    Object onSuccessFromformUODestino() {
//        return UOChangeZone.getBody();
//    }

//    Object onSuccessFromFormEOrigen() {
//        return EOrigenZone.getBody();
//    }


//    @CommitAfter
//    Object onSuccessFromFormUOChange() {
////        Helpers.migrarUOBase(uoOrigen, entidadUE, entidadDestino, session);
//        session.flush();
//        uoOrigen = null;
//        uoDestino = null;
////        nivelOrigen = null;
////        nivelDestino = null;
//        envelope.setContents("Unidad Orgánica migrada exitosamente");
//        return new MultiZoneUpdate("UOChangeZone",UOChangeZone.getBody())                                
//                    .add("EOrigenZone", EOrigenZone.getBody())
//                    .add("EDestiZone", EDestiZone.getBody());
//                    //.add("UOOrigenNivelZone", UOOrigenNivelZone.getBody());
//
//    }


//    @CommitAfter
//    Object onSuccessFromFormUOFusionar() {
////        Helpers.fusionarUOBase(uoOrigen, entidadUE, entidadDestino, uoDestino, session);
//        session.flush();
//        uoOrigen = null;
//        uoDestino = null;
////        nivelOrigen = null;
////        nivelDestino = null;
//        envelope.setContents("Unidad Orgánica fusionada exitosamente");
//        return new MultiZoneUpdate("UOChangeZone",UOChangeZone.getBody())                                      
//                    .add("EOrigenZone", EOrigenZone.getBody())
//                    .add("EDestiZone", EDestiZone.getBody());
//                    //.add("UOOrigenNivelZone", UOOrigenNivelZone.getBody());
//
//    }
    
    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("EDestiZone", EDestiZone.getBody())                                
                    .add("botonZone", botonZone.getBody());
        return mu;

    }


//    @SetupRender
//    void initValues() {
//        entidadDestino = null;
//    }
}
