package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Reportes;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.RadioGroup;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Start page of application servir.
 */
//@IncludeJavaScriptLibrary("context:layout/custom.js")
public class RepTrabajador extends GeneralPage {

    @Inject
    private Session session;
    
    @Inject
    private PropertyAccess _access;
    
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;

    @Log
    public String getMensajes() {
        return mensajes;
    }

    @Log
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
  
    @Property
    @SessionState
    private Entidad _entidadUE;
    
    @Property
    @SessionState
    private Usuario _usuario;

    @Inject
    private Request request;

    @Inject
    private ComponentResources _resources;

    @Property
    @Persist
    private UsuarioAcceso usu;
 
    @Property
    @Persist
    private Boolean vselect;

    @Persist
    @Property
    private boolean mostrarFiltrosTrabajador;

    @Persist
    @Property
    private boolean mostrarFiltrosEntidad;

    @Persist
    @Property
    private boolean mostrarFiltrosUsuario;

    @Persist
    @Property
    private boolean mostrarFiltrosGobierno;
    
    @Property
    @InjectComponent
    private Zone categoriasZone;
    
    @Property
    @InjectComponent
    private Zone tipoReporteZone;

    @Property
    @Persist
    private boolean radio;

    @Property
    @Persist
    private DatoAuxiliar tipoReporteSelect;
    
    @Property
    @Persist
    private String categoria;

    @Inject
    private Context context;
    
    @Property
    @InjectComponent
    private Zone trabajadorZone;
    
    @Property
    @Persist
    private String titular;
    
    @Property
    @Persist
    private String trabajador_ape;
    
    @Property
    @InjectComponent
    private Zone busZone;
    
    @Persist
    @Property
    private String nombreTrabajador;

    @Property
    @Persist
    private LkBusquedaTrabajador titulart;

    @Property
    @InjectComponent
    private Zone trabaZone;
    
    @Property
    @Persist
    private boolean mostrar;
    
    @Property
    @InjectComponent
    private Zone categoriaZone;
    
    @Log
    void setupRender() {
        vselect = true;

        mostrar = false;
        radio = false;
        categoria = "";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
            usu = (UsuarioAcceso) result.get(0);
            vselect = (usu.getAccesoselect() != 0);
        }
    }

    @Log
    Object onActionFromSeleccionaTitular(Trabajador traba) {
        if (traba != null) {
            titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            _entidadUE.setTitular(traba);
        }
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("trabaZone", trabaZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }

    @Log
    Object onSuccessFromFormFindTrabajador() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("trabaZone", trabaZone.getBody());
    }

    @Log
    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoReportes() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOREPORTE", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    StreamResponse onSuccessFromFormularioReporte() {
        if (mostrarFiltrosTrabajador)
            generarTrabajador();
        
        Long userId = new Long("9");
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", userId);
        StreamResponse report = rep.callReporte(Reportes.REPORTE.B5, Reportes.TIPO.PDF, parametros, context);
        return report;
    }
    
    @Log
    void generarTrabajador() {
        if (radio)
            System.out.println("PDF");
        else
            System.out.println("EXCEL");
    }
    
    @Log
    Object onActionFromMostrarTrabajador() {
        categoria = "Trabajador";
        mostrarFiltrosTrabajador = true;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;        
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarEntidad() {
        categoria = "Entidad/Unidad Organica";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = true;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarUsuario() {
        categoria = "Usuario";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = true;
        mostrarFiltrosGobierno = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarGobierno() {
        categoria = "Nivel de Gobierno/Sector/Organización del Estado";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = true;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
}
