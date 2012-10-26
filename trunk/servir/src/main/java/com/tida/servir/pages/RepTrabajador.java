package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Reportes;
import helpers.ReportesFormulario;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.services.ResponseImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
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
    @Persist
    private boolean mostrarFiltrosSancion;
    
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
    private String nombresTrabajador;
    
    @Property
    @InjectComponent
    private Zone busZone;
    
    @Persist
    @Property
    private String apepatTrabajador;
    
    @Persist
    @Property
    private String apematTrabajador;

    @Property
    @InjectComponent
    private Zone trabaZone;
    
    @Property
    @InjectComponent
    private Zone entidadZone;
    
    @Property
    @Persist
    private String entidadTrabajador;
    
    @Property
    @Persist
    private String entidadTx;
    
    @Property
    @Persist
    private String entidad_ape;
    
    @Property
    @Persist
    private String entidad_apes;
    
    @Persist
    @Property
    private String nombreEntidad;
    
    @Persist
    @Property
    private String nombreEntidadTrabajador;

    @Property
    @InjectComponent
    private Zone entiZone;
    
    @Property
    @InjectComponent
    private Zone entiTraZone;
    
    @Property
    @Persist
    private Trabajador titulart;

    @Persist
    @Property
    private Entidad listaentidad;
    
    @Persist
    @Property
    private Usuario u;

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
    private boolean sancionLink;
    
    @Property
    @Persist
    private boolean generarDisabled;
    
    @Property
    @Persist
    private ReportesFormulario.TIPO type;
    
    @Property
    @Persist
    private ReportesFormulario.TIPO excel;
    
    @Property
    @Persist
    private ReportesFormulario.TIPO pdf;
    
    private static final long TRABAJADOR = 0;
    
    private static final long ENTIDAD = 1;
    
    private static final long USUARIO = 2;
    
    private static final long GOBIERNO = 3;
    
    private static final long SANCION = 4;
    
    private static final long NINGUNO = 5;
    
    @Property
    @Persist
    private Integer nivelo;
    
    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicasOrigen;
    
    @Property
    @InjectComponent
    private Zone usuarioZone;
    
    @Property
    @Persist
    private String usuarioTx;
    
    @Property
    @InjectComponent
    private Zone usuaZone;
    
    @Persist
    @Property
    private String nombreUsuario;
    
    @Persist
    @Property
    private String apePaUsuario;
    
    @Persist
    @Property
    private String apeMaUsuario;
    
    @Property
    @InjectComponent
    private Zone gobiernoZone;
    
    @Property
    @Persist
    private DatoAuxiliar snivelGobierno;
    
    @Property
    @Persist
    private DatoAuxiliar sorganizacionestado;
    
    @Property
    @Persist
    private boolean organizacionBool;
    
    @Property
    @Persist
    private boolean sectorBool;
    
    @Property
    @Persist
    private boolean organoBool;
    
    @Property
    @Persist
    private DatoAuxiliar ssectorGobierno;
    
    @Property
    @Persist
    private Trabajador _trabajadorRep;
    
    @Property
    @Persist
    private Entidad _entidadRep;
    
    @Property
    @Persist
    private Entidad entidadTraba;
    
    @Property
    @Persist
    private Usuario _usuarioRep;
        
    @Property
    @Persist
    private LkBusquedaUnidad unidadRep;
    
    @Property
    @Persist
    private boolean bEntidad;
    
    @Property
    @Persist
    private boolean bessubentidad;
    
    @Property
    @Persist
    private boolean bessubentidad2;
    
    @Property
    @Persist
    private DatoAuxiliar stipoOrganismo;
    
    @Property
    @Persist
    private DatoAuxiliar stipoSubEntidad;
    
    @Property
    @Persist
    private boolean mostrarEntidadTrabajador;
    
    @Property
    @Persist
    private boolean mostrarTrabajadorS;
    
    @Property
    @Persist
    private boolean visualEntidad;
    
    @Property
    @Persist
    private String fechaingresode;
    
    @Property
    @Persist
    private String fechaingresoha;
    
    @Property
    @InjectComponent
    private Zone sancionZone;
    
    @Property
    @Persist
    private DatoAuxiliar valdocumentousu;
    
    @Property
    @Persist
    private String nroDocumentoUsu;
    
    @Property
    @Persist
    private String loginUsu;
    
    @Property
    @Persist
    private DatoAuxiliar valdocumentotra;
    
    @Property
    @Persist
    private String nroDocumentoTra;
    
    @Persist
    @Property
    private String bEstado;
    
    @Persist
    @Property
    private Perfil bselectRol;
    
    @Property
    @Persist
    private StreamResponse report;
    
    @Property
    @Persist
    private boolean showLinkReport;
    
    @Log
    void setupRender() {
        vselect = true;

        mostrar = false;
        bEntidad = true;
        categoria = "";
        visualEntidad = true;
        mostrarTrabajadorS = true;
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        mostrarFiltrosSancion = false;
        generarDisabled = false;
        organizacionBool = false;
        entidadTraba = null;

        excel = ReportesFormulario.TIPO.EXCEL;
        pdf = ReportesFormulario.TIPO.PDF;
        type = pdf;
        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio"));
        } else {
            usu = (UsuarioAcceso) result.get(0);
            vselect = (usu.getAccesoselect() != 0);
            
            switch ((int)usu.getRolid()) {//usu.getNivel()
                case 3://1://Administrador SERVIR
                    trabajadorLink = entidadLink = gobiernoLink = true;
                    break;
                case 2://Administrador de Entidad
                    trabajadorLink = entidadLink = true;
                    break;
                case 4://3://Administrador del Sistema
                    usuarioLink = true;
                    break;
                case 6://4://Consultas  SERVIR
                    trabajadorLink = entidadLink = gobiernoLink = true;
                    break;
                case 5://Consultas Entidad
                    trabajadorLink = entidadLink  = true;
                    break;
                case 1://6://Trabajador
                    trabajadorLink  = true;
                    break;
                case 7://Contraloría
                    gobiernoLink  = true;
                    break;
                case 8://Órgano de Control Institucional
                    break;
            }
        }
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", snivelGobierno != null ? "NIVELGOBIERNO" : null, 
                snivelGobierno != null ? snivelGobierno.getCodigo() : 0
                , session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<Perfil> getRolUsuario() {
        List<Perfil> list;
        Criteria c = session.createCriteria(Perfil.class);
        list = c.list();
        return new GenericSelectModel<Perfil>(list, Perfil.class, "descperfil", "id", _access);
    }
    
    @Log
    Object onActionFromSeleccionaTitular(Trabajador traba) {
        if (traba != null) {
            titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            _trabajadorRep = traba;
        }
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("trabaZone", trabaZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }

    @Log
    Object onActionFromSeleccionaEntidadTrabajador(Entidad enti) {
        if (enti != null) {
            entidadTrabajador = enti.getDenominacion();
            entidadTraba = enti;
        }
        mostrarEntidadTrabajador = false;
        mostrarTrabajadorS = true;
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiTraZone", entiTraZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }
    
    @Log
    Object onActionFromSeleccionaEntidad(Entidad enti) {
        if (enti != null) {
            entidadTx = enti.getDenominacion();
            _entidadRep = enti;
        }
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
    }
    
    @Log
    Object onActionFromSeleccionaUsuario(Usuario traba) {
        if (traba != null) {
            usuarioTx = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            _usuarioRep = traba;
        }
        mostrar = false;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("usuaZone", usuaZone.getBody()).add("tipoReporteZone", tipoReporteZone.getBody());
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
    Object onSuccessFromFormFindEntidadTrabajador() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiTraZone", entiTraZone.getBody());
    }
    
    @Log
    Object onSuccessFromFormFindUsuario() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("usuaZone", usuaZone.getBody());
    }
    
    @Log
    Object onSuccessFromFormularioReporte() {
        bessubentidad = bessubentidad2;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody());
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
    Object onSelectedFromBuscarEntidadTrabajador() {
        mostrarEntidadTrabajador = true;
        mostrarTrabajadorS = false;
        return new MultiZoneUpdate("busZone", busZone.getBody());
    }
    
    @Log
    Object onSelectedFromBuscarUsuario() {
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
    Object onSelectedFromCancelFormFindUsuario() {
        mostrar = false;
        return new MultiZoneUpdate("usuaZone", usuaZone.getBody());
    }
    
    @Log
    Object onSelectedFromCancelFormFindEntidadTrabajador() {
        mostrarEntidadTrabajador = false;
        mostrarTrabajadorS = true;
        mostrar = false;
        return new MultiZoneUpdate("entiTraZone", entiTraZone.getBody());
    }
    
     @Log
    public List<Trabajador> getTrabajadores() {
        Criteria c = session.createCriteria(Trabajador.class);
        boolean ok = true;
        if (nombresTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombres", nombresTrabajador + "%").ignoreCase()).add(Restrictions.like("nombres", nombresTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombres", nombresTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (apepatTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidoPaterno", apepatTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apepatTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apepatTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (apematTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidoMaterno", apematTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", apematTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", apematTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (valdocumentotra != null) {
            c.add(Restrictions.eq("documentoidentidad", valdocumentotra));
            ok = false;
        }
        if (nroDocumentoTra != null && !nroDocumentoTra.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nroDocumento", nroDocumentoTra + "%").ignoreCase()));
            ok = false;
        }
        if (entidadTraba != null)
            c.add(Restrictions.eq("entidad", entidadTraba));
        if (ok) return null;
        return c.list();
    }
    
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        if (nombreEntidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + nombreEntidad + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" + nombreEntidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" + nombreEntidad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    @Log
    public List<Entidad> getEntidadesTra() {
        Criteria c = session.createCriteria(Entidad.class);
        if (nombreEntidadTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + nombreEntidadTrabajador + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" + nombreEntidadTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" + nombreEntidadTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentotra() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar_td("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentousu() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar_td("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public List<Usuario> getUsuarios() {
        Criteria c = session.createCriteria(Usuario.class);
        boolean ok = true;
        if (nombreUsuario != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombres", nombreUsuario + "%").ignoreCase()).add(Restrictions.like("nombres", nombreUsuario.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombres", nombreUsuario.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (apePaUsuario != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidoPaterno", apePaUsuario + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apePaUsuario.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apePaUsuario.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (apeMaUsuario != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidoMaterno", apeMaUsuario + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", apeMaUsuario.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", apeMaUsuario.replaceAll("n", "ñ") + "%").ignoreCase()));
            ok = false;
        }
        if (valdocumentousu != null) {
            c.add(Restrictions.eq("documentoId", valdocumentousu.getId()));
            ok = false;
        }
        if (nroDocumentoUsu != null && !nroDocumentoUsu.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("numeroDocumento", nroDocumentoUsu + "%").ignoreCase()));
            ok = false;
        }
        if (loginUsu != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("login", loginUsu + "%").ignoreCase()));
            ok = false;
        }
        if (bselectRol != null && bEstado != null) {
            c.add(Restrictions.disjunction().add(Restrictions.eq("rolid", Long.valueOf(bselectRol.getId()))));
            c.add(Restrictions.disjunction().add(Restrictions.eq("estado", Long.valueOf(bEstado))));
            ok = false;
        }
        if (ok) return null;
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
        else if (mostrarFiltrosSancion)
            c.add(Restrictions.eq("categoria_id", RepTrabajador.SANCION));
        else
            c.add(Restrictions.eq("categoria_id", RepTrabajador.NINGUNO));
        return new GenericSelectModel<Reporte>(c.list(), Reporte.class, "nombre", "id", _access);
    }

    @Log
    StreamResponse onVerReporte() {
        return report;
    }
    
    @Log
    Object onGenerarReporte() {
        ReportesFormulario rep = new ReportesFormulario();
        Map<String, Object> parametros;
        try {
            if (tipoReporteSelect != null && type != null)
                parametros = retornarParametros(tipoReporteSelect.getCategoria_id()); 
            else throw new Exception ("Error en tipo reporte o formato reporte");
            
            report = rep.callReporte(tipoReporteSelect, type, parametros, session);
            showLinkReport = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            report = null;
            showLinkReport = false;
        }
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody());
    }

    Map<String, Object> retornarParametros(long idcat)throws Exception {
        Map<String, Object> parametros = new HashMap<String, Object>();
        if(mostrarFiltrosTrabajador) {
            if (_trabajadorRep == null) throw new Exception("Error en categoría Trabajador");
            parametros.put("MandatoryParameter_TrabajadorID", _trabajadorRep.getId());
            if (tipoReporteSelect.getFormato() == 0)
                parametros.put("MandatoryParameter_EntidadID", entidadTraba.getId());
        }
        if(mostrarFiltrosEntidad) {
            if (_entidadRep == null) throw new Exception("Error en categoría Entidad");
            parametros.put("MandatoryParameter_EntidadUEjecutoraID", _entidadRep.getId());
            parametros.put("MandatoryParameter_UnidadOrganicaID", unidadRep.getId());
        }
        if(mostrarFiltrosUsuario) {
            if (_usuarioRep == null) throw new Exception("Error en categoría Sistema");
            parametros.put("MandatoryParameter_UsuarioID", _usuarioRep.getId());
            if (fechaingresode == null) fechaingresode = "";
            if (fechaingresoha == null) fechaingresoha = "";
            parametros.put("MandatoryParameter_FechaDesde", fechaingresode);
            parametros.put("MandatoryParameter_FechaHasta", fechaingresoha);
        }
        if(mostrarFiltrosGobierno) {
            throw new Exception("Error en categoría Consolidados");
        }
        if(mostrarFiltrosSancion) {
            throw new Exception("Error en categoría Sanciones");
        }
        return parametros;
    }
    
    @Log
    Object onActionFromMostrarTrabajador() {
        categoria = "Trabajador";
        if (usu.getRolid() == 3 || usu.getRolid() == 6) bEntidad = false;
        if (usu.getRolid() == 2 || usu.getRolid() == 1 || usu.getRolid() == 5) {
            visualEntidad = false;
            if (_entidadUE != null) { entidadTrabajador = _entidadUE.getDenominacion(); entidadTraba = _entidadUE; }
        }
        mostrarFiltrosTrabajador = true;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        mostrarFiltrosSancion = false;
        generarDisabled = true;
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarEntidad() {
        categoria = "Entidad";
        if (usu.getRolid() == 2 || usu.getRolid() == 5)
            if (_entidadUE != null) { entidadTx = _entidadUE.getDenominacion(); _entidadRep = _entidadUE; }
        if (usu.getRolid() == 3 || usu.getRolid() == 6) bEntidad = false;
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = true;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        mostrarFiltrosSancion = false;
        generarDisabled = true;
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarUsuario() {
        categoria = "Sistema";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = true;
        mostrarFiltrosGobierno = false;
        mostrarFiltrosSancion = false;
        generarDisabled = true;
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarGobierno() {
        categoria = "Consolidados";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = true;
        mostrarFiltrosSancion = false;
        generarDisabled = true;
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarSancion() {
        categoria = "Sanciones";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        mostrarFiltrosSancion = true;
        generarDisabled = true;
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onValueChangedFromUo_nivelo(Integer dato) {
        if (dato != null) {
            nivelo = dato;
            unidadRep = null;
        }
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody());
    }
    
    @Log
    Object onValueChangedFromSTipoSubEntidad() {
        return new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody());
    }
    
    @Log
    Object onValueChangedFromTipoReporteSelect() {
        showLinkReport = false;
        return new MultiZoneUpdate("tipoReporteZone",tipoReporteZone.getBody());
    }
    
    @Log
    public List<Integer> getBeanNivelOrigen(){
        if(_entidadRep == null)
            return getBeanNivel(_entidadUE, 1);
        else
            return getBeanNivel(_entidadRep, 1);
    }
    
    public List<Integer> getBeanNivel(Entidad eUE, Integer first){
        List<Integer> nivel = new LinkedList<Integer>();
        Integer nivelMax = Helpers.maxNivelUO(eUE, session);
        for(int i=first; i <= nivelMax; i++){
            nivel.add(i);
        }
        return nivel;
    }
    
    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicasOrigen(){
        List<LkBusquedaUnidad> list;
        Criteria c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));        
        if (nivelo != null) {
            c.add(Restrictions.eq("nivel", nivelo));
        }
        if(_entidadRep==null){
            c.add(Restrictions.eq("entidadId", _entidadUE.getId() ));
        }
        else{
            c.add(Restrictions.eq("entidadId", _entidadRep.getId() ));
        }        
        list = c.list();
        _beanUOrganicasOrigen = new GenericSelectModel<LkBusquedaUnidad>(list,LkBusquedaUnidad.class,"denominacion","id",_access);       
        return _beanUOrganicasOrigen;
    }
    
    public String getEntidadZoneId() { 
        return entidadZone.getClientId(); 
    }
    
    public String getGobiernoZoneId() { 
        return gobiernoZone.getClientId(); 
    }
    
    @Log
    Object onValueChangedFromSnivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            organizacionBool = false;
            sectorBool = false;
            organoBool = false;
        } else {
            organizacionBool = true;
            sectorBool = false;
            organoBool = false;
        }
        sorganizacionestado = null;
        ssectorGobierno = null;
        stipoOrganismo = null;
        return request.isXHR() ? new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromSorganizacionestado(DatoAuxiliar dato) {
        if (dato == null) {
            sectorBool = false;
            organoBool = false;
        } else {
            if (dato.getValor().equalsIgnoreCase("PODER EJECUTIVO")) {
                sectorBool = true;
                organoBool = false;
            } else {
                sectorBool = false;
                organoBool = false;
            }
        }
        ssectorGobierno = null;
        stipoOrganismo = null;
        return request.isXHR() ? new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromSsectorgobierno(DatoAuxiliar dato) {
        if (dato == null) {
            organoBool = false;
        } else {
            organoBool = true;
        }
        stipoOrganismo = null;
        return new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody());
    }

    @Log
    Object onValueChangedFromStipoOrganismo(DatoAuxiliar dato) {
        return new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody());
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    Object onSelectedFromReset() {
        if (usu.getRolid() == 3  || usu.getRolid() == 6) {
            entidadTrabajador = ""; 
            entidadTraba = null;
            entidadTx = "";
            _entidadRep = null;
        }
        showLinkReport = false;
        titular = "";
        _trabajadorRep = null;
        nivelo = null;
        unidadRep = null;
        usuarioTx = "";
        _usuarioRep = null;
        fechaingresode = "";
        fechaingresoha = "";
        snivelGobierno = null;
        sorganizacionestado = null;
        ssectorGobierno = null;
        stipoOrganismo = null;
        stipoSubEntidad = null;
        organizacionBool = false;
        sectorBool = false;
        organoBool = false;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody());
    }
}