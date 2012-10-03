package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioAcceso;
import helpers.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
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
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_LEGAJO_LABORAL);
        return null;
    }
    // inicio de pagina
    @Log
    @SetupRender
    private void inicio() {
        logueo();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        if(actual==null)
            query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        else
            query.setParameter("in_pagename", "BUSQUEDA");
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