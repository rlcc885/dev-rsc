package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
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
    @Inject
    private PropertyAccess _access;
    @PageActivationContext
    private Trabajador actual;
    @Property
    @Persist
    private Trabajador menu;
    @Property
    @Persist
    private FormacionProfesional formacionProfesional;
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;// utilizado para mensajes globales, como ser que al crear un trabajador, ya existe
    @Inject
    private ComponentResources _resources;
    @Property
    @SessionState
    private UsuarioAcceso usua;

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

    @Log
    @SetupRender
    private void inicio() {
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento", _usuario.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
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

        System.out.println("trabajaaaanulo");
    }
}