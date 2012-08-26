package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioAcceso;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Clase que maneja la pagina de modificacion de un Trabajador
 */
public class Meritos extends GeneralPage {

    /**
     * Listas necesarias para los modelos.
     */
    private List<String> pais = new ArrayList<String>();
    private List<String> nacionalidad = new ArrayList<String>();
    private List<String> estadoCivil = new ArrayList<String>();
    private List<String> tipoDiscapacidad = new ArrayList<String>();
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
    @Property
    @SessionState
    private UsuarioAcceso usua;
    @Inject
    private ComponentResources _resources;

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
            actual = _usuario.getTrabajador();
        }
    }

    public Meritos() {
    }

    public Trabajador getActual() {
        return actual;
    }

    public void setActual(Trabajador actual) {
        this.actual = actual;
    }
}