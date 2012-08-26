package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad;
import java.util.List;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.UsuarioAcceso;
import com.tida.servir.entities.FormacionProfesional;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Constantes;
import java.util.ArrayList;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Query;


/**
 * Clase que maneja la pagina de modificacion de un Trabajador
 */
public class TrabajadorDocumento  extends GeneralPage
{
    /**
     * Listas necesarias para los modelos.
     */

    private List<String> pais = new ArrayList<String>();
    private List<String> nacionalidad = new ArrayList<String>();
    private List<String> estadoCivil = new ArrayList<String>();
    private List<String> tipoDiscapacidad = new ArrayList<String>();

    @Property
     @Persist
     private CargoAsignado _ca;

    public List<String> getPais(){
        return pais;
    }
    
    public List<String> getEstadoCivil() {
        return estadoCivil;
    }

    public List<String> getNacionalidad() {
        return nacionalidad;
    }

 
    public List<String> getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }


    @Property
    @SessionState
    private Entidad _oi;

    /**
     * Hasta acá
     */
    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario _usuario;
    
    @Inject
    private PropertyAccess _access;

    @PageActivationContext
    private Trabajador actual;
    
        
        /*
     * Código de grillas
     */

    // Grilla de Antecedentes laborales
//    @InjectComponent
//    private Zone antecedentesZone;
//
//    @InjectComponent
//    private Zone publicacionesZone;
//
//    @InjectComponent
//    private Zone trabajosZone;

    // Código generales de la grilla, cada uno tendrá que agregar su código acá
    // Cófigo necesario antes de procesar
//    @InjectComponent
//    private Zone meritosZone;
//
//    @InjectComponent
//    private Zone demeritosZone;
//
//    @InjectComponent
//    private Zone titulosZone;
//
//    @InjectComponent
//    private Zone certificacionesZone;
//
//    @InjectComponent
//    private Zone cursosZone;
//
//    @Property
//    @InjectComponent
//    private Zone instruccionZone;

    /*
     * Hasta acá código de grillas
     */
    @Inject
    private ComponentResources _resources;
    
    @Property
    @SessionState
    private UsuarioAcceso usua;
    
    @Property
    @Persist
    private FormacionProfesional formacionProfesional;

    @Persist(PersistenceConstants.FLASH)
    private String mensajes;// utilizado para mensajes globales, como ser que al crear un trabajador, ya existe

        @Log
    @SetupRender
    private void inicio() {
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento",_usuario.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();        
        if(result.isEmpty()){
            System.out.println(String.valueOf("Vacio:"));
            
        }
        else{
            usua = (UsuarioAcceso) result.get(0);        
        }
        if(actual==null){
            actual=_usuario.getTrabajador();
        }        
    }
    
//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.puedeEscribir(_usuario, _oi);
//    }
//    
    public TrabajadorDocumento()
    {
    }

    public Trabajador getActual() {
            return actual;
    }

    public void setActual(Trabajador actual) {
            this.actual = actual;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String msg) {
        mensajes = msg;
    }

    public boolean getHaymensajes () {
        return mensajes != null;
    }
    
//    @Component(id = "instruccion")
//    private Form formInstruccion;

//    @Log
//    @SetupRender
//    void loadFormacion() {
//        if (formacionProfesional == null)
//            formacionProfesional = new FormacionProfesional();
//        formacionProfesional.setFormacion(actual.getFormacionprofesional());
//        Criteria c = session.createCriteria(CargoAsignado.class);
//        c.createAlias("trabajador", "trabajador");
//        c.createAlias("legajo", "legajo");
//        c.createAlias("cargo", "cargo");
//        c.add(Restrictions.eq("legajo.entidadUE", _oi));
//        c.add(Restrictions.eq("trabajador", actual));
//        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
//        if (c.list().size() > 0)
//            _ca = (CargoAsignado) c.list().get(0);
//        else _ca = null;
//    }

// 
//    @CommitAfter
//    Object onSuccessFromInstruccion(){
//        actual.setTipoDocumento("01");
//        actual.setFormacionprofesional(formacionProfesional.getFormacion());
//        session.saveOrUpdate(actual);
//        return instruccionZone;
//    }

//    public String getClasePublicacion() {
//        return Publicacion.CLASE_PUBLICACION;
//    }
//
//    public String getClaseTrabajo() {
//        return Publicacion.CLASE_INVESTIGACION;
//    }
//
//
//    public String getClaseMeritos() {
//        return MeritoDemerito.CLASE_MERITO;
//    }
//
//
//    public String getClaseDeMeritos() {
//        return MeritoDemerito.CLASE_DEMERITO;
//    }

//    public List<String> getValorTablaAuxiliar(String tabla) 
//    {
//        // TODO: este codigo esta duplicado
//        Criteria c = session.createCriteria(DatoAuxiliar.class);
//        c.add(Restrictions.eq("nombreTabla", tabla));
//        c.setProjection(Projections.property("valor"));
//        return c.list();
//    }
//
//    public List<String> getNivelInstruccion() 
//    {
//        return getValorTablaAuxiliar("NivelInstrucción");
//    }
//
//
//    public boolean getHayCargosAsignados() {
//        return _ca != null;
//    }


}