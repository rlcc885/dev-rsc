package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import helpers.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Clase que maneja la pagina de modificacion de un Trabajador
 */
public class TrabajadorPersonal extends GeneralPage {

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

    public List<String> getPais() {
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
    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario _usuario;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Inject
    private PropertyAccess _access;
    @PageActivationContext
    private Trabajador actual;
    @Property
    @Persist
    private Trabajador menu;
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;// utilizado para mensajes globales, como ser que al crear un trabajador, ya existe
    @Inject
    private ComponentResources _resources;
    @Property
    @SessionState
    private UsuarioAcceso usua;
//    @Property
//    @Persist
//    private Boolean vfamiliar;
//    @Property
//    @Persist
//    private Boolean vlaboral;
//    @Property
//    @Persist
//    private Boolean vdocumento;

    public TrabajadorPersonal() {
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

    public boolean getHaymensajes() {
        return mensajes != null;
    }
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_LEGAJO_PERSONAL);
        return null;
    }
    @Property
    @Persist
    private Boolean mostrar;
    // inicio de pagina
    @Log
    @SetupRender
    private void inicio() {
        logueo();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        if(actual==null){
            mostrar=false;
            query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        }
        else{
            mostrar=true;
            query.setParameter("in_pagename", "BUSQUEDA");
        }
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
            
            usua = (UsuarioAcceso) result.get(0);
        }
        if (actual == null) {
            menu = _usuario.getTrabajador();
            System.out.println("menuuu");
        } else {
            menu = actual;
            actual = null;
            System.out.println("actualll");
        }

    }
    
//    void verificarventanas(){
//        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
//        query.setParameter("in_login", _usuario.getLogin());
//        if(actual==null)
//            query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
//        else
//            query.setParameter("in_pagename", "BUSQUEDA");
//        List result = query.list();
//        if (!result.isEmpty()) {
//            System.out.println(String.valueOf("Vacio:"));
//        }
//    }
}