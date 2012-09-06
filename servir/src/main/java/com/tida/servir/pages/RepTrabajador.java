package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Reportes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
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
    private Reporte tipoReporteSelect;
    
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
    @InjectComponent
    private Zone trabaZone;
    
    @Property
    @InjectComponent
    private Zone entidadZone;
    
    @Property
    @Persist
    private String entidadTx;
    
    @Property
    @Persist
    private String entidad_ape;
    
    @Persist
    @Property
    private String nombreEntidad;

    @Property
    @InjectComponent
    private Zone entiZone;
    
    @Property
    @Persist
    private LkBusquedaTrabajador titulart;

    @Persist
    @Property
    private LkBusquedaEntidad listaentidad;

    @Property
    @Persist
    private boolean mostrar;
    
    @Property
    @InjectComponent
    private Zone categoriaZone;
    
    @Property
    @Persist
    private boolean trabajadorLink;
    
    @Property
    @Persist
    private boolean entidadLink;
    
    @Property
    @Persist
    private boolean usuarioLink;
    
    @Property
    @Persist
    private boolean gobiernoLink;
    
    @Property
    @Persist
    private boolean generarDisabled;
    
    @Property
    @Persist
    private Reportes.TIPO type;
    
    @Property
    @Persist
    private Reportes.TIPO excel;
    
    @Property
    @Persist
    private Reportes.TIPO pdf;
    
    private static final long TRABAJADOR = 0;
    private static final long ENTIDAD = 1;
    private static final long USUARIO = 2;
    private static final long GOBIERNO = 3;
    private static final long NINGUNO = 4;
    
    @Log
    void setupRender() {
        vselect = true;

        mostrar = false;
        categoria = "";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        generarDisabled = true;
        
        excel = Reportes.TIPO.EXCEL;
        pdf = Reportes.TIPO.PDF;
        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
            usu = (UsuarioAcceso) result.get(0);
            vselect = (usu.getAccesoselect() != 0);
            
            switch (usu.getNivel()) {
                case 1:
                    trabajadorLink = entidadLink = gobiernoLink = true;
                    break;
                case 2:
                    trabajadorLink = entidadLink = true;
                    break;
                case 3:
                    usuarioLink = true;
                    break;
                case 4:
                    trabajadorLink = entidadLink = gobiernoLink = true;
                    break;
                case 5:
                    trabajadorLink = entidadLink  = true;
                    break;
                case 6:
                    trabajadorLink  = true;
                    break;
                case 7:
                    gobiernoLink  = true;
                    break;
            }
        }
    }

    @Log
    Object onActionFromSeleccionaTitular(Trabajador traba) {
        if (traba != null) {
            titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            _entidadUE.setTitular(traba);
        }
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("trabaZone", trabaZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }

    @Log
    Object onActionFromSeleccionaEntidad(Entidad enti) {
        if (enti != null) {
            entidadTx = enti.getDenominacion();
            _entidadUE.setEntidad(enti);
        }
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }
    
    @Log
    Object onSuccessFromFormFindTrabajador() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("trabaZone", trabaZone.getBody());
    }

    @Log
    Object onSuccessFromFormFindEntidad() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @Log
    Object onSelectedFromBuscarTitular() {
        return new MultiZoneUpdate("busZone", busZone.getBody());
    }

    @Log
    Object onSelectedFromBuscarEntidad() {
        return new MultiZoneUpdate("busZone", busZone.getBody());
    }
    
    @Log
    Object onSelectedFromCancelFormFindTrabajador() {
        mostrar = false;
        return new MultiZoneUpdate("trabaZone", trabaZone.getBody());
    }
    
    @Log
    Object onSelectedFromCancelFormFindEntidad() {
        mostrar = false;
        return new MultiZoneUpdate("entiZone", entiZone.getBody());
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
    public List<LkBusquedaEntidad> getEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (nombreEntidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", nombreEntidad + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    @Log
    public GenericSelectModel<Reporte> getTipoReportes() {
        Criteria c = session.createCriteria(Reporte.class);
        if (mostrarFiltrosTrabajador)
            c.add(Restrictions.eq("categoria_id", RepTrabajador.TRABAJADOR));
        else if (mostrarFiltrosEntidad)
            c.add(Restrictions.eq("categoria_id", RepTrabajador.ENTIDAD));
        else if (mostrarFiltrosUsuario)
            c.add(Restrictions.eq("categoria_id", RepTrabajador.USUARIO));
        else if (mostrarFiltrosGobierno)
            c.add(Restrictions.eq("categoria_id", RepTrabajador.GOBIERNO));
        else
            c.add(Restrictions.eq("categoria_id", RepTrabajador.NINGUNO));
        return new GenericSelectModel<Reporte>(c.list(), Reporte.class, "nombre", "id", _access);
    }
    
    @Log
    void onSelectedFromGenerarReporte() {
        if (tipoReporteSelect != null) {
            if (mostrarFiltrosTrabajador)
                generarTrabajador();
            if (mostrarFiltrosEntidad)
                generarEntidad();
            if (mostrarFiltrosUsuario)
                generarUsuario();
            if (mostrarFiltrosGobierno)
                generarGobierno();
        }
    }

    @Log
    StreamResponse generarTrabajador() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", _entidadUE.getTitular().getId());
        //tipoReporteSelect.getCodigo()
        StreamResponse report = rep.callReporte(Reportes.REPORTE.B5, type, parametros, context);
        return report;
    }
    
    @Log
    StreamResponse generarEntidad() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", _entidadUE.getTitular().getId());
        StreamResponse report = rep.callReporte(Reportes.REPORTE.B5, type, parametros, context);
        return report;
    }
    
    @Log
    StreamResponse generarUsuario() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", _entidadUE.getTitular().getId());
        StreamResponse report = rep.callReporte(Reportes.REPORTE.B5, type, parametros, context);
        return report;
    }
    
    @Log
    StreamResponse generarGobierno() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", _entidadUE.getTitular().getId());
        StreamResponse report = rep.callReporte(Reportes.REPORTE.B5, type, parametros, context);
        return report;
    }
    
    @Log
    Object onActionFromMostrarTrabajador() {
        categoria = "Trabajador";
        mostrarFiltrosTrabajador = true;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        generarDisabled = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarEntidad() {
        categoria = "Entidad/Unidad Organica";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = true;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        generarDisabled = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarUsuario() {
        categoria = "Usuario";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = true;
        mostrarFiltrosGobierno = false;
        generarDisabled = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarGobierno() {
        categoria = "Nivel de Gobierno/Sector/Organizacion del Estado";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = true;
        generarDisabled = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
}