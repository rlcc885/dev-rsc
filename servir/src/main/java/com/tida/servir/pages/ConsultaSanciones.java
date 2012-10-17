/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Miller Cribillero
 * @date 17/09/2012 18:00
 * 
 */
public class ConsultaSanciones extends GeneralPage {
    @Inject
    private Session session;
    @SessionState
    private Usuario loggedUser;
    @InjectComponent
    private Envelope envelope;
    
    @Component(id = "formmensaje")
    private Form formmensaje;
//    @Component(id = "formulariosatosSancion")
//    private Form formulariosatosSancion;
    @InjectComponent
    @Property
    private Zone mensajeZone;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @SessionState
    @Property
    private UsuarioAcceso usua;
    @InjectComponent
    @Property
    private Zone consultaSancionesZone;
    @InjectComponent
    @Property
    private Zone tiposancionZone;
    @InjectComponent
    @Property
    private Zone busZone2;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Persist
    @Property
    private String entidad_origen; 
    @Property
    @Persist
    private Trabajador nuevo;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    @Property
    private Zone busZone;
    @InjectComponent
    @Property
    private Zone listaConsultaSancionZone;
    @Persist
    @Property
    private String bnombres;
    @Persist
    @Property
    private String bapellidoPaterno;
    @Persist
    @Property
    private String bapellidoMaterno;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad2;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad_not;
    @Persist
    @Property
    private String bnumeroDocumento;
    @Persist
    @Property
    private String bnumeroDocumento2;
    @Persist
    @Property
    private String bnumeroDocumento_not;
    @Persist
    @Property
    private String juzgado_not;
    @Persist
    @Property
    private String observaciones;
    @Property
    @SessionState
    private Usuario _usuario;   
    @Persist
    @Property
    private String bdenoentidad;
    @Persist
    @Property
    private Long entidad_origen_id;
    @Persist
    @Property
    private Boolean esVigente;
    @Persist
    @Property
    private Boolean esNoVigente;
    @Persist
    @Property
    private Boolean esHistorica;
    @Persist
    @Property
    private Boolean esAnulada;
    @Persist
    @Property
    private Boolean esSuspendida;
    @Persist
    @Property
    private Boolean esTrabajador;
    @Persist
    @Property
    private Boolean mostrartodo;
    @Persist
    @Property
    private Boolean bmostrar;
    @Persist
    @Property
    private Boolean mostrar_reglab;
    @Persist
    @Property
    private Boolean v_editar;
    @Persist
    @Property
    private Boolean entidadsubentidad;
    
    @Property
    @Persist
    private DatoAuxiliar bregimenLaboral;
    @Persist
    @Property
    private DatoAuxiliar bcategoriaSancion;
    @Persist
    @Property
    private Lk_Tipo_Sancion btipoSancion;
          
    @Property
    @Persist
    private LkBusquedaEntidad entio;
    @Property
    @Persist
    private LkBusquedaSancionados cs;
    
    @Property
    @Persist
    private LkBusquedaSancionadosSinRegLab cs_sinreglab;
    
    @Component(id = "formconsultaSancion")
    private Form formconsultaSancion;
    @Component(id = "formularioconsultasanciones")
    private Form formularioconsultasanciones;
    @Component(id = "formularioanularsancion")
    private Form formularioanularsancion;
  
    @Property
    @Persist
    private Entidad entidad2;  
    @Property
    @Persist
    private Anulacion anulacion;  
    private int elemento=0;
    private int anular=0;
    
    @Property
    @Persist
    private String errorBorrar;
    
    @Property
    @Persist
    private Sancion sancion;
    @Property
    @Persist
    private String fechadoc;
    @Property
    @Persist
    private String fechadocnot;
    @Property
    @Persist
    private String nro_sanciones;
    @Property
    @Persist
    private String nro_sanciones_sinreglab;
    @Inject  
    private ComponentResources _resources;
    @Property
    @Persist
    private Long filtro_entidad;
    
    @Log
    @SetupRender
    void initializeValue() {
        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
//        query.setParameter("in_pagename", "TIPOSANCION");
        List result = query.list();
        
        
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
           usua = (UsuarioAcceso) result.get(0);
         }
        nuevo = new Trabajador();
        entidad_origen= "";
        entidad_origen_id=null;
        bapellidoPaterno="";
        bapellidoMaterno="";
        bnombres="";
        bnumeroDocumento="";
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
//        btipoSancion = null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false;
        mostrar_reglab=false;
        
        if(loggedUser.getRolid()==2){ //Administrador de Entidad
             entidadsubentidad=true;
        }else{
            entidadsubentidad=false;
        }
            
        if(loggedUser.getRolid()==3){ //administrador de Servir
            bmostrar=true;
        }else{
            bmostrar=false;
            filtro_entidad = loggedUser.getEntidad().getId();
        }  
                
        if(usua.getAccesoupdate() == 1){ 
             v_editar = true;
        }else{
             v_editar = false;
        }
            
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getRegimenLaboral() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
      public GenericSelectModel<DatoAuxiliar> getCategoriaSancion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
      public GenericSelectModel<Lk_Tipo_Sancion> getTipoSancion() {
        List<Lk_Tipo_Sancion> list;
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class); 
        if(bregimenLaboral!=null){
            c.add(Restrictions.eq("reg_laboral", bregimenLaboral.getId()));
        }
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria", bcategoriaSancion.getId()));
        }
        list = c.list();
        return new GenericSelectModel<Lk_Tipo_Sancion>(list, Lk_Tipo_Sancion.class, "descripcion", "id_tipo", _access);
    }
      
    @Log
    public List<LkBusquedaSancionadosSinRegLab> getBusquedaSancionadosSinRegLab(){
        Criteria c;
        c = session.createCriteria(LkBusquedaSancionadosSinRegLab.class);
        if(entidad_origen_id!=null){
            c.add(Restrictions.eq("entidad_id",entidad_origen_id));
        }else if(filtro_entidad != null){
            c.add(Restrictions.eq("entidad_id",filtro_entidad));
        }
        if(bnombres!=null){
             c.add(Restrictions.or(Restrictions.like("nombres_trabajador","%"+bnombres+"%"),Restrictions.like("nombres_persona","%"+bnombres+"%")));
        }
        if(bapellidoPaterno!=null || bapellidoMaterno!=null){
            c.add(Restrictions.or(Restrictions.like("apellidos_trabajador","%"+bapellidoPaterno+" "+bapellidoMaterno+"%"),Restrictions.like("apellidos_persona","%"+bapellidoPaterno+" "+bapellidoMaterno+"%")));
        }
        if(bdocumentoidentidad !=null){
            c.add(Restrictions.or(Restrictions.eq("tipo_doc_trabajador",bdocumentoidentidad.getId().toString()),Restrictions.eq("tipo_doc_persona",bdocumentoidentidad.getId().toString())));
        }
        if(bnumeroDocumento != null){
            c.add(Restrictions.or(Restrictions.like("nro_doc_trabajador","%"+bnumeroDocumento+"%"),Restrictions.eq("nro_doc_persona","%"+bnumeroDocumento+"%")));
        }
        if(esSuspendida==true){
            c.add(Restrictions.eq("estado_id", "3"));
        }
        if(esAnulada==true){
            c.add(Restrictions.eq("estado_id", "4"));
        }    
        if(esHistorica==true){
            c.add(Restrictions.eq("estado_id", "5"));
        }
        if(esNoVigente==true){
            c.add(Restrictions.eq("estado_id", "2"));
        }
        if(esVigente==true){
            c.add(Restrictions.eq("estado_id", "1"));
        }
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria_sancion_id", bcategoriaSancion.getId().toString()));
        }
        if(btipoSancion!=null){
            c.add(Restrictions.eq("id_tipo_sancion", btipoSancion.getId()));
        }
        List result = c.list();
        nro_sanciones_sinreglab = Integer.toString(result.size());
        
        return c.list();
      }
    
    @Log
    public List<LkBusquedaSancionados> getBusquedaSancionados(){
        Criteria c;
        c = session.createCriteria(LkBusquedaSancionados.class);
        if(entidad_origen_id!=null){
            c.add(Restrictions.eq("entidad_id",entidad_origen_id));
        }else if(filtro_entidad != null){
            c.add(Restrictions.eq("entidad_id",filtro_entidad));
        }
        if(bnombres!=null){
             c.add(Restrictions.or(Restrictions.like("nombres_trabajador","%"+bnombres+"%"),Restrictions.like("nombres_persona","%"+bnombres+"%")));
        }
        if(bapellidoPaterno!=null || bapellidoMaterno!=null){
            c.add(Restrictions.or(Restrictions.like("apellidos_trabajador","%"+bapellidoPaterno+" "+bapellidoMaterno+"%"),Restrictions.like("apellidos_persona","%"+bapellidoPaterno+" "+bapellidoMaterno+"%")));
        }
        if(bdocumentoidentidad !=null){
            c.add(Restrictions.or(Restrictions.eq("tipo_doc_trabajador",bdocumentoidentidad.getId().toString()),Restrictions.eq("tipo_doc_persona",bdocumentoidentidad.getId().toString())));
        }
        if(bnumeroDocumento != null){
            c.add(Restrictions.or(Restrictions.eq("nro_doc_trabajador","%"+bnumeroDocumento.toString()+"%"),Restrictions.eq("nro_doc_persona","%"+bnumeroDocumento.toString()+"%")));
        }
        if(esSuspendida==true){
            c.add(Restrictions.eq("estado_id", "3"));
        }
        if(esAnulada==true){
            c.add(Restrictions.eq("estado_id", "4"));
        }    
        if(esHistorica==true){
            c.add(Restrictions.eq("estado_id", "5"));
        }
        if(esNoVigente==true){
            c.add(Restrictions.eq("estado_id", "2"));
        }
        if(esVigente==true){
            c.add(Restrictions.eq("estado_id", "1"));
        }
        if(bregimenLaboral!=null){
            c.add(Restrictions.eq("id_reg_laboral", bregimenLaboral.getId().toString()));
        }
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria_sancion_id", bcategoriaSancion.getId().toString()));
        }
        if(btipoSancion!=null){
            c.add(Restrictions.eq("id_tipo_sancion", btipoSancion.getId()));
        }
        
        List result = c.list();
        nro_sanciones = Integer.toString(result.size());
        
        return c.list();
      }
    
     Object onBuscarpersona(){
         return new MultiZoneUpdate("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone",busZone.getBody());
//          return consultaSancionesZone.getBody();
     }
     
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        return busZone.getBody();
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
    Object onValueChangedFromCategoriaSancion(DatoAuxiliar dato) {
        return tiposancionZone.getBody();
    }     
    
    @Log
    Object onValueChangedFromRegimenLaboral(DatoAuxiliar dato) {
        return tiposancionZone.getBody();
    }     
    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioConsultaSanciones() {
        if(elemento == 1)   {
             if(bregimenLaboral!=null){
                 mostrar_reglab=true;
             }else{
                 mostrar_reglab=false;
             }
            return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
        } else if (elemento == 2){
                mostrar_reglab=false;
//            cs = new LkBusquedaSancionados();
            return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
        } else if (elemento == 3){
              mostrar_reglab=false;
            return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
        }
        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
         // return listaConsultaSancionZone.getBody();
    }
    
    @Log
    void onSelectedFromBuscarSubmit() {
        elemento = 1;
    }
    
    @Log
    void onSelectedFromMuestra() {
        elemento = 2;
        entidad_origen= null;
        entidad_origen_id=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bnombres=null;
        bnumeroDocumento=null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false;
        
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
        btipoSancion = null;
    }
//    @Log
//    void onSelectedFromLimpiar() {
//        elemento = 3;
//        entidad_origen= null;
//        entidad_origen_id=null;
//        bapellidoPaterno=null;
//        bapellidoMaterno=null;
//        bnombres=null;
//        bnumeroDocumento=null;
//        esSuspendida=false;
//        esAnulada=false;
//        esHistorica=false;
//        esNoVigente=false;
//        esVigente=false; 
//        bdocumentoidentidad = null;
//        bregimenLaboral = null;
//        bcategoriaSancion = null;
//        btipoSancion = null;
//    }  
    
    @Log
    Object onLimpiar() {    
        entidad_origen= null;
        entidad_origen_id=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bnombres=null;
        bnumeroDocumento=null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false; 
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
        btipoSancion = null;
        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
    }
    
     
    @Log
    Object onActionFromSeleccionar(Entidad enti2) {        
        entidad2 = enti2;
        entidad_origen=entidad2.getDenominacion();
        entidad_origen_id = entidad2.getId();
        return consultaSancionesZone.getBody();  
    }
    
//    @Log
//    Object onActionFromAnular(LkBusquedaSancionados cs) {        
//        
//         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
//                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone2",busZone2.getBody());  
//    }
//    @Log
//    Object onActionFromAnular_SinRegLab(LkBusquedaSancionadosSinRegLab cs_sinreglab) {        
//        
//         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
//                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone2",busZone2.getBody());  
//    }
    
    @Log
    Object onActionFromCancel1() {        

         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
    }
        
    @Log
    @CommitAfter
    Object onSuccessFromFormularioAnularSancion(){
        if(anular==1){
            return busZone2.getBody();
        }
        return busZone2.getBody();
    }
            
    @Log
    @CommitAfter
    Object onBorrarDato(UnidadOrganica dato) {
        errorBorrar = null;
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        dato.setEstado(UnidadOrganica.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        envelope.setContents("Sancion Eliminada");

        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
    }
    
    @Log
    Object onActionFromSave(Anulacion anulacion) {        
        
        
        return new MultiZoneUpdate("consultaSancionesZone",consultaSancionesZone.getBody()); 
    }
    public Boolean getSancionAnulada(){
        System.out.println("EST5ADOX  "+cs.getEstado_id());
        if (Integer.valueOf(cs.getEstado_id())==4){
        return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    public Boolean getSancionAnulada_sinregla(){
        if (Integer.valueOf(cs_sinreglab.getEstado_id())==4){
        return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
  
}
