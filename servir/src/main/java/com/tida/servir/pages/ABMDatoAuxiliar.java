package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.DatoAuxiliar;


import helpers.Errores;
import com.tida.servir.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
    public ABMDatoAuxiliar() {
    }
    
    void setuprender(){
    nuevoRegistro = new DatoAuxiliar();
    relacionada = Boolean.FALSE;
    noRelacionada = Boolean.FALSE;
            
            
    }
    
    @Property
    @Persist
    private String tablaActual;
    
    @Log
    public List<String> getTablasAuxiliares() {
        tablaRelacion = null;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.setProjection(Projections.distinct(Projections.property("nombreTabla")));
      //  c.add(Restrictions.ne("editable", Boolean.FALSE));
        c.addOrder(Order.asc("nombreTabla"));
        return c.list();
    }
    
    @Persist
    private DatoAuxiliar tablaTemporal;
    @Log
    public Object onValueChangedFromTablaSeleccionada(String nombreTabla) {
        relacionada = Boolean.FALSE;
        noRelacionada = Boolean.FALSE;
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla",nombreTabla ));
        tablaTemporal = (DatoAuxiliar)c.list().get(0);
        if (tablaTemporal.getTablaRelacion()== null || tablaTemporal.getTablaRelacion().length()==0){
        noRelacionada = Boolean.TRUE;        
        }
        else{
        relacionada = Boolean.TRUE;        
        }
        return dosZones();

    }
    
    @Persist
    @Property
    private  Boolean relacionada;
    @Persist
    @Property
    private  Boolean noRelacionada;
    
    private MultiZoneUpdate dosZones() {
        return new MultiZoneUpdate("tablasAuxiliaresZone", tablasAuxiliaresZone.getBody()).add("listaRegistrosTablaZone", listaRegistrosTablaZone.getBody());
    }

    @Property
    @Persist
    private DatoAuxiliar tabla1;
    @Property
    @Persist
    private DatoAuxiliar tabla2;
    @Component(id = "formularioMensajes")
    private Form formularioMensajes;

    @Log
    public List<DatoAuxiliar> getTablaAux1(){
    Criteria c = session.createCriteria(DatoAuxiliar.class);
    c.add(Restrictions.eq("nombreTabla", tablaTemporal.getNombreTabla()));
    return c.list();
    }
    
    @Log
    public List<DatoAuxiliar> getTablaAux2(){
    return getTablaAux1();
    }    

    private List<DatoAuxiliar> obtenerTabla(String tablaRelacion, long relacionCodigo) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        if (tablaRelacion != null) {
            c.add(Restrictions.eq("tablaRelacion", tablaRelacion));
            c.add(Restrictions.eq("relacionCodigo", relacionCodigo));
        } else {
            // No hay restricciones, muestro toda la tabla
            c.add(Restrictions.eq("nombreTabla", tabla));
        }
        return c.list();
    }


}