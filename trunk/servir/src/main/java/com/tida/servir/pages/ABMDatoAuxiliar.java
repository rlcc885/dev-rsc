package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.LkDatoauxiliar;
import helpers.Errores;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioAcceso;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import com.tida.servir.services.GenericSelectModel;
import java.util.ArrayList;
import java.util.Map;
import org.apache.tapestry5.ComponentResources;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
/**
 *
 *	Clase que maneja las tablas auxiliares
 * Si una tabla tiene hijos, montamos el componente de subtabla.
 * Hay que dar la posibilidad de generar hijos cuando no los tiene.
 *  
 */
public class ABMDatoAuxiliar extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario _user;
    @Property
    @Persist
    private String tabla;
    /**
     * Utilizado por la Grilla para discernir cada elemento
     */
    @Property
    private DatoAuxiliar t;

    /*
     * Utilizado como dato para agregar
     */
    @Property
    @Persist
    private DatoAuxiliar da;

    /*
     * Indica si hay una tabla seleccionada
     */
    @Property
    @Persist
    private boolean hayTabla;
    @Property
    @Persist
    private boolean agregarConcepto;
    @Property
    @Persist
    private List<String> errores;
    @Property
    private String e;
    @Component(id = "formulariosTablasSinRelacion")
    private Form formulariosTablasSinRelacion;
    @Component(id = "formulariosTablasConRelacion")
    private Form formulariosTablasConRelacion;
    // Guardamos los parámetros de la búsqueda para cuando borramos, retornarlo
    @Persist
    private String tablaRelacion;
    @Persist
    private long relacionCodigo;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private Request request;
    @InjectComponent
    private Zone seleccionTablaZone;
    @InjectComponent
    private Zone listaRegistrosTablaZone;
    @InjectComponent
    private Zone mensajesZone;
    @InjectComponent
    private Zone tablasAuxiliaresZone;
    @Persist
    @Property
    private DatoAuxiliar nuevoRegistro;
    @Persist
    @Property
    private Boolean editando;
    @Property
    @SessionState
    private Usuario _usuario;    
    @Inject  
    private ComponentResources _resources;
    @Property
    @SessionState
    private UsuarioAcceso usua;
    @Property
    @Persist
    private Boolean opcEliminar;
    @Property
    @Persist
    private Boolean opcModificar;
    @Property
    @Persist
    private Boolean opcInsertar;
    public ABMDatoAuxiliar() {
    }
   
    void setuprender(){
        
    nuevoRegistro = new DatoAuxiliar();
    
    if ((relacionada == null) && (noRelacionada == null)){
       relacionada = Boolean.FALSE;noRelacionada = Boolean.FALSE;
    }        
    
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
//        query.setParameter("in_pagename", "TIPOSANCION");
        List result = query.list();
        
 //       opcEliminar = Boolean.FALSE;        
 //       opcModificar = Boolean.FALSE;        
 //       opcInsertar = Boolean.FALSE;
        
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
           usua = (UsuarioAcceso) result.get(0);
                permisos();
//        if (usua.getAccesoupdate() == 1) {opcModificar = Boolean.TRUE;}
//        if (usua.getAccesodelete() == 1) {opcEliminar  = Boolean.TRUE;}
//        if (usua.getAccesoreport() == 1) {opcInsertar  = Boolean.TRUE;}

        }   
    }
    
    private void permisos(){
        opcEliminar = Boolean.FALSE;        
        opcModificar = Boolean.FALSE;        
        opcInsertar = Boolean.FALSE;
    
        if (usua.getAccesoupdate() == 1) {opcModificar = Boolean.TRUE;}
        if (usua.getAccesodelete() == 1) {opcEliminar  = Boolean.TRUE;}
        if (usua.getAccesoreport() == 1) {opcInsertar  = Boolean.TRUE;}
    
    }
    
    @Property
    @Persist
    private String tablaActual;
    
    
    @Persist
    private DatoAuxiliar tablaTemporal;
    
    
    @Log
    public List<String> getTablasAuxiliares() {
        tablaRelacion = null;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.setProjection(Projections.distinct(Projections.property("nombreTabla")));
        c.add(Restrictions.ne("editable", Boolean.FALSE));
        c.addOrder(Order.asc("nombreTabla"));
        return c.list();
    }
    
    @Persist
    @Property
    private  Boolean relacionada;
    @Persist
    @Property
    private  Boolean noRelacionada;
    
    
    @Log
    public Object onValueChangedFromTablaSeleccionada(String nombreTabla) {
        editando = Boolean.FALSE;
        relacionada = Boolean.FALSE;
        noRelacionada = Boolean.FALSE;
        nuevoRegistro = new DatoAuxiliar();
        formularioMensajes.clearErrors();
        valDatoRelacionado = null;
        tablaRelacionada = null;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla",nombreTabla ));
        tablaTemporal = (DatoAuxiliar)c.list().get(0);
        if (tablaTemporal.getTablaRelacion()== null || tablaTemporal.getTablaRelacion().length()==0){
        noRelacionada = Boolean.TRUE;        
        }
        else{
        relacionada = Boolean.TRUE;        
        }
        permisos();
        return dosZones();
   }
    
    
    private MultiZoneUpdate dosZones() {
        return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody());
    }

    @Property
    @Component(id = "formularioMensajes")
    private Form formularioMensajes;

    @Persist
    @Property
    private LkDatoauxiliar tabla1;
    
    @Log
    public List<LkDatoauxiliar> getTablaAux1(){
    Criteria c = session.createCriteria(LkDatoauxiliar.class);
    c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
    c.add(Restrictions.isNull("tablaRelacion"));
    c.addOrder(Order.asc("codigo"));
    return c.list();
    }

    @Property
    @Persist
    private LkDatoauxiliar tabla2;

    @Log
    public List<LkDatoauxiliar> getTablaAux2(){
    Criteria c = session.createCriteria(LkDatoauxiliar.class);
    c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
    c.addOrder(Order.asc("codigo"));
    return c.list();
    }    

    
    public Object onEditarDato(LkDatoauxiliar dato){
        editando = Boolean.TRUE;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("id", dato.getId()));
        nuevoRegistro = (DatoAuxiliar)c.uniqueResult();
        opcInsertar = Boolean.TRUE;
        return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody());
    }
    
    public Object onEditarDato2(LkDatoauxiliar dato){
        editando = Boolean.TRUE;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("id", dato.getId()));
        nuevoRegistro = (DatoAuxiliar)c.uniqueResult();
        tablaRelacionada = nuevoRegistro.getTablaRelacion();
        c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("id", dato.getRelacionId()));
        valDatoRelacionado = (DatoAuxiliar)c.uniqueResult();
        opcInsertar = Boolean.TRUE;
        return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody());
    }
    
    @Log
    @CommitAfter
    public Object onBorrarDato(LkDatoauxiliar dato){
        nuevoRegistro.setId(dato.getId());
        session.delete(nuevoRegistro);
        envelope.setContents("Registro eliminado con exito");
        return new MultiZoneUpdate("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("mensajesZone", mensajesZone.getBody());
    }
    
    @Log
    @CommitAfter
    public Object onBorrarDato2(LkDatoauxiliar dato){
        nuevoRegistro.setId(dato.getId());
        session.delete(nuevoRegistro);
        envelope.setContents("Registro eliminado con exito");
        return new MultiZoneUpdate("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("mensajesZone", mensajesZone.getBody());
    }

    @Log
    public Object onCancel(){
    return "Alerta";
    }

    @Log
    public Object onCancel2(){
    return "Alerta";
    }
    
   @Log
   @CommitAfter        
   Object onSuccessFromFormulariosTablasSinRelacion(){
       formularioMensajes.clearErrors();
       Criteria c = session.createCriteria(DatoAuxiliar.class);
       c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
       
       if (Boolean.FALSE.equals(editando)){
            Criterion c1 =Restrictions.eq("valor", nuevoRegistro.getValor());
            Criterion c2 = Restrictions.eq("codigo", nuevoRegistro.getCodigo());           
            c.add(Restrictions.or(c1, c2));
       }else{
            c.add(Restrictions.eq("valor", nuevoRegistro.getValor()));
            c.add(Restrictions.ne("id", nuevoRegistro.getId()));
       }
       
       if (!c.list().isEmpty()){
          formularioMensajes.recordError("Codigo y/o Descripcion duplicada");
           return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("mensajesZone", mensajesZone.getBody());
       }
       
       if (!editando){
       nuevoRegistro.setNombreTabla(tablaTemporal.getNombreTabla());
       nuevoRegistro.setEditable(tablaTemporal.getEditable());
       nuevoRegistro.setRelacionCodigo(tablaTemporal.getRelacionCodigo());
       }
       session.saveOrUpdate(nuevoRegistro);
       System.out.println("TABLAX --id-- "+nuevoRegistro.getId());
       System.out.println("TABLAX --codigo-- "+nuevoRegistro.getCodigo());       
       System.out.println("TABLAX --valor-- "+nuevoRegistro.getValor());
       System.out.println("TABLAX --nombretabla-- "+nuevoRegistro.getNombreTabla());
       System.out.println("TABLAX --tablarelacion-- "+nuevoRegistro.getTablaRelacion());
       System.out.println("TABLAX --editable-- "+nuevoRegistro.getEditable());

       nuevoRegistro = new DatoAuxiliar();
       editando = Boolean.FALSE;
       valDatoRelacionado = null;
       tablaRelacionada = null;
       permisos();
       envelope.setContents("Registro creado/modificado con exito");
       return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("mensajesZone", mensajesZone.getBody());
   }

   @Log
   @CommitAfter        
   Object onSuccessFromFormulariosTablasConRelacion(){

       formularioMensajes.clearErrors();
       Criteria c = session.createCriteria(DatoAuxiliar.class);
       c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
       
       if (Boolean.FALSE.equals(editando)){
            Criterion c1 =Restrictions.eq("valor", nuevoRegistro.getValor());
            Criterion c2 = Restrictions.eq("codigo", nuevoRegistro.getCodigo());           
            c.add(Restrictions.or(c1, c2));
       }else{
            c.add(Restrictions.eq("valor", nuevoRegistro.getValor()));
            c.add(Restrictions.ne("id", nuevoRegistro.getId()));
       }
       
       if (!c.list().isEmpty()){
          formularioMensajes.recordError("Codigo y/o Descripcion duplicada");
           return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("mensajesZone", mensajesZone.getBody());
       }
       
       if ( Boolean.FALSE == editando){
       nuevoRegistro.setNombreTabla(tablaTemporal.getNombreTabla());
       nuevoRegistro.setEditable(tablaTemporal.getEditable());
       }
       nuevoRegistro.setRelacionCodigo(valDatoRelacionado.getCodigo());
       
       session.saveOrUpdate(nuevoRegistro);
       System.out.println("TABLAX --id-- "+nuevoRegistro.getId());
       System.out.println("TABLAX --codigo-- "+nuevoRegistro.getCodigo());       
       System.out.println("TABLAX --valor-- "+nuevoRegistro.getValor());
       System.out.println("TABLAX --nombretabla-- "+nuevoRegistro.getNombreTabla());
       System.out.println("TABLAX --tablarelacion-- "+nuevoRegistro.getTablaRelacion());
       System.out.println("TABLAX --editable-- "+nuevoRegistro.getEditable());
       nuevoRegistro = new DatoAuxiliar();
       editando = Boolean.FALSE;
       valDatoRelacionado = null;
       tablaRelacionada = null;
       permisos();
       envelope.setContents("Registro creado/modificado con exito");
       return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody()).add("mensajesZone", mensajesZone.getBody());       
   }
    
     @Inject
     private PropertyAccess _access;
   
    
    @Log
    public List<String> getTablaRelacion() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
        c.setProjection(Projections.distinct(Projections.property("tablaRelacion")));
        return c.list();
    }
    

    @Persist
    private String tablaRelacionada;
    @Log
    public Object onValueChangedFromtablarelacion2(String campo){
        tablaRelacionada = campo;
        return new MultiZoneUpdate("tablasAuxiliaresZone",tablasAuxiliaresZone.getBody());
    }
    @Property
    @Persist
    private DatoAuxiliar valDatoRelacionado;
    
    public GenericSelectModel<DatoAuxiliar> getDatoRelacionadoBean(){
        System.out.println("XXXXSXSXSX"+tablaRelacionada);
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", tablaRelacionada));
        c.addOrder(Order.asc("valor"));
        return new GenericSelectModel<DatoAuxiliar>(c.list(), DatoAuxiliar.class, "valor", "id", _access);
    }
}
