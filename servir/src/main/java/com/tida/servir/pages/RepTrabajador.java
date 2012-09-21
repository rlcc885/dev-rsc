package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Reportes;
import java.util.*;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.TextField;
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
    
    @Property
    @Persist
    private String usuario_ape;
    
    @Persist
    @Property
    private String nombreUsuario;
    
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
    private Entidad ssubentidad;
    
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
    private String fechaingresodesde;
    
    @Property
    @Persist
    private String fechaingresohasta;
    
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
        generarDisabled = false;
        organizacionBool = false;
        entidadTraba = null;
        
        excel = Reportes.TIPO.EXCEL;
        pdf = Reportes.TIPO.PDF;
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
                    trabajadorLink = entidadLink = gobiernoLink = usuarioLink = true;//CORREGIR: quitar usuario
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
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", snivelGobierno.getCodigo(), session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
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
    Object onSuccessFromGobiernoForm() {
        bessubentidad = !bessubentidad;
        return new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody());
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
    Object onSelectedFromCancelFormFindEntidadTrabajador() {
        mostrarEntidadTrabajador = false;
        mostrarTrabajadorS = true;
        mostrar = false;
        return new MultiZoneUpdate("entiTraZone", entiTraZone.getBody());
    }
    
    @Log
    public List<Trabajador> getTrabajadores() {
        Criteria c = session.createCriteria(Trabajador.class);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidoPaterno", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase())
                    .add(Restrictions.like("apellidoMaterno", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoMaterno", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase())
                    .add(Restrictions.like("nombres", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombres", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombres", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }else
            if (entidadTraba == null) return null;
        if (entidadTraba != null)
            c.add(Restrictions.eq("entidad", entidadTraba));
        
        return c.list();
    }
    
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        if (nombreEntidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", nombreEntidad + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    @Log
    public List<Entidad> getEntidadesTra() {
        Criteria c = session.createCriteria(Entidad.class);
        if (nombreEntidadTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", nombreEntidadTrabajador + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidadTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", nombreEntidadTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
    
    @Log
    public List<UsuarioTrabajador> getUsuarios() {
        Criteria c = session.createCriteria(Usuario.class);
        if (nombreUsuario != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombres", nombreUsuario + "%").ignoreCase()).add(Restrictions.like("nombres", nombreUsuario.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombres", nombreUsuario.replaceAll("n", "ñ") + "%").ignoreCase()));
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
    StreamResponse onActionFromGenerarReporte() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        if (tipoReporteSelect != null && type != null) {
            if (mostrarFiltrosTrabajador && _trabajadorRep != null)
                parametros.put("MandatoryParameter_TrabajadorID", _trabajadorRep.getId());
            if (mostrarFiltrosEntidad && _entidadRep != null)
                parametros.put("MandatoryParameter_EntidadID", _entidadRep.getId());
            if (mostrarFiltrosUsuario && _usuarioRep != null)
                parametros.put("MandatoryParameter_UsuarioID", _usuarioRep.getId());
            if (mostrarFiltrosGobierno && _usuarioRep != null)
                parametros.put("MandatoryParameter_UsuarioID", _usuarioRep.getId());
        } else { return null; }
        StreamResponse report = rep.callReporte(retornarReporte(tipoReporteSelect.getCodigo()), type, parametros, context);
        return report;
    }
        
    Reportes.REPORTE retornarReporte(String codigo) {
        if (codigo.equals("A2"))
            return Reportes.REPORTE.A2;
        return Reportes.REPORTE.B5;
    }
    
    @Log
    Object onActionFromMostrarTrabajador() {
        categoria = "Trabajador";
        if (usu.getRolid() == 3) bEntidad = false;
        if (usu.getRolid() == 2) {
            visualEntidad = false;
            if (_entidadUE != null) { entidadTrabajador = _entidadUE.getDenominacion(); entidadTraba = _entidadUE; }
        }
        mostrarFiltrosTrabajador = true;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        generarDisabled = true;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarEntidad() {
        categoria = "Entidad";
        if (_entidadUE != null) entidadTx = _entidadUE.getDenominacion();
        if (usu.getRolid() == 3) bEntidad = false;
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = true;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = false;
        generarDisabled = true;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarUsuario() {
        categoria = "Sistema";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = true;
        mostrarFiltrosGobierno = false;
        generarDisabled = true;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onActionFromMostrarGobierno() {
        categoria = "Consolidado";
        mostrarFiltrosTrabajador = false;
        mostrarFiltrosEntidad = false;
        mostrarFiltrosUsuario = false;
        mostrarFiltrosGobierno = true;
        generarDisabled = true;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
    
    @Log
    Object onValueChangedFromUo_nivelo(Integer dato) {
        if (dato != null) {
            nivelo = dato;
        }
        return entidadZone.getClientId();
    }
    
    @Log
    Object onValueChangedFromSTipoSubEntidad() {
        return new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody());
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
        } else {
            organizacionBool = true;
        }
        sorganizacionestado = null;
        return request.isXHR() ? new MultiZoneUpdate("gobiernoZone", gobiernoZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromSorganizacionestado(DatoAuxiliar dato) {
        if (dato == null) {
            sectorBool = false;
        } else {
            if (dato.getValor().equalsIgnoreCase("PODER EJECUTIVO")) {
                sectorBool = true;
            } else {
                sectorBool = false;
            }
        }
        ssectorGobierno = null;
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
    public GenericSelectModel<Entidad> getSubEntidades() {
        return new GenericSelectModel<Entidad>(searchSubEntidades(), Entidad.class, "denominacion", "id", _access);
    }
    
    @Log
    private List<Entidad> searchSubEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.eq("estado", true));
        c.add(Restrictions.eq("esSubEntidad", true));
        c.add(Restrictions.eq("tipoSubEntidad", stipoSubEntidad));
        return c.list();
    }
    
    @Log
    Object onSelectedFromReset() {
        entidadTrabajador = ""; 
        entidadTraba = null;
        return new MultiZoneUpdate("tipoReporteZone", tipoReporteZone.getBody()).add("categoriaZone",categoriaZone.getBody());
    }
}