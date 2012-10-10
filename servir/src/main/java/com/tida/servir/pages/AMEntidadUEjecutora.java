package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades ejecutoras
 *
 * @author LFL
 */
public class AMEntidadUEjecutora extends GeneralPage {

    @Inject
    private Session session;
    @Inject
    private Request _request;
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    @InjectComponent
    private Zone mensajesZone;
    @Component(id = "formDatos")
    private Form formDatos;
    @Component(id = "formOtrosDatos")
    private Form formOtrosDatos;
    @Component(id = "formUbicacion")
    private Form formUbicacion;
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @Persist
    private Ubigeo ubigeoEntidadUE;
    @Property
    @Persist
    private boolean bsector;
    @Property
    @Persist
    private boolean btipoorganismo;
    //Entidad Origen
    @Component(id = "formularioEntidadOrigen")
    private Form formularioEntidadOrigen;
    @Persist
    @Property
    private String bdenoentidad;
    @Property
    @Persist
    private boolean mostrar;
    @Persist
    @Property
    private String entidad_origen;
    //Trabajador
    @Persist
    @Property
    private String nombreTrabajador;
    @Persist
    @Property
    private String titular;
    @Persist
    @Property
    private String jefeOGA;
    @Persist
    @Property
    private String jefeRRHH;
    @Property
    @Persist
    private boolean btitular;
    @Property
    @Persist
    private boolean bjefeOGA;
    @Property
    @Persist
    private boolean bjefeRRHH;
    @Property
    @Persist
    private boolean btitulari;
    @Property
    @Persist
    private boolean bjefeOGAi;
    @Property
    @Persist
    private boolean bjefeRRHHi;
    //Busqueda Entidad Zone
    @InjectComponent
    @Property
    private Zone busquedacombosZone;
    @Component(id = "formBusqueda")
    private Form formBusqueda;
    @Property
    @Persist
    private DatoAuxiliar bnivelGobierno;
    @Property
    @Persist
    private DatoAuxiliar borganizacionEstado;
    @Property
    @Persist
    private DatoAuxiliar bsectorGobierno;
    //
    @Property
    @Persist
    private DatoAuxiliar tipOrganismo;
    
    // cambios
//    @Property
//    @Persist
//    private DatoAuxiliar btipoOrganismo;
    @Property
    @Persist
    private Ubigeo ubigeobusEntidadUE;
    @Persist
    @Property
    private String busdenominacion;
    @Persist
    @Property
    private String busestado;
    //Busqueda SubEntidad Zone
    @InjectComponent
    @Property
    private Zone busquedasubcombosZone;
    @Component(id = "formulariosubcombosbusqueda")
    private Form formulariosubcombosbusqueda;
    @Property
    @Persist
    private DatoAuxiliar btiposubentidad;
    @Property
    @Persist
    private Ubigeo ubigeobusSubEntidadUE;
    @Persist
    @Property
    private String bussubdenominacion;
    @Persist
    @Property
    private String bussubestado;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private boolean mostrarFiltros;
    @Persist
    @Property
    private String mostrarEsconder;
    @InjectComponent
    private Envelope envelope;
    // OBJETOS USADOS EN LA CLASE
    @InjectComponent
    private Zone listaentidadZone;
    @Persist
    @Property
    private LkBusquedaEntidad listaentidad;
    @InjectComponent
    @Property
    private Zone EOrigenZone;
    @InjectComponent
    @Property
    private Zone zoneDatos;
    @InjectComponent
    @Property
    private Zone ubigeoEntidadZone;
    @InjectComponent
    @Property
    private Zone zoneOtrosDatos;
    @InjectComponent
    private Zone TitularZone;
    @InjectComponent
    private Zone JefeRRHHZone;
    @InjectComponent
    private Zone JefeOGAZone;
    @InjectComponent
    @Property
    private Zone botonesZone;
    @InjectComponent
    private Zone busZone2;
    @InjectComponent
    private Zone trabajadorZone;
    @InjectComponent
    private Zone busZone;
    @InjectComponent
    private Zone entiZone;
    @Component(id = "formulariotitular")
    private Form formulariotitular;
    @Component(id = "formulariojeferrhh")
    private Form formulariojeferrhh;
    @Component(id = "formulariojefeoga")
    private Form formulariojefeoga;
    @Component(id = "formulariobotones")
    private Form formulariobotones;
    @Inject
    private Request request;
    @Property
    @Persist
    private Entidad entidadUE;
    @Property
    @Persist
    private boolean bMuestraSector;
    @Property
    @Persist
    private boolean bMuestraSectorEdicion;
    @Property
    @Persist
    private boolean bSeleccionaPersonal;
    @Property
    @Persist
    private boolean bSeleccionaEntidad;
    @Property
    @Persist
    private boolean bessubentidad;
    @Property
    @Persist
    private boolean noCambiaSubentidad;

    @Property
    @Persist
    private boolean bCancelFormulario;
    @Property
    @Persist
    private boolean bBuscaEntidad;
    @Property
    @Persist
    private boolean bBuscaSubEntidad;
    @Property
    @Persist
    private boolean bResetFormulario;
    @Persist
    @Property
    private Entidad rowEntidad;
    @Property
    @Persist
    private LkBusquedaTrabajador titulart;
    @Property
    @Persist
    private LkBusquedaTrabajador jeferrhht;
    @Property
    @Persist
    private LkBusquedaTrabajador jefeogat;
    @Property
    @Persist
    private UsuarioAcceso usua;
    @Inject
    private ComponentResources _resources;
    //validaciones
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vval_essub;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean arroja;
    @Persist
    @Property
    private Boolean editando;
    @PageActivationContext
    private Entidad entidadalerta;
    @Property
    @Persist
    private Boolean opcion_limpiar;

    @Log
    public Entidad getEntidadalerta() {
        return entidadalerta;
    }

    public void setEntidadalerta(Entidad entidadalerta) {
        this.entidadalerta = entidadalerta;
    }
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ENTIDAD);
        return null;
    }
    
    // incio de la pagina
    @Log
    void setupRender() {
        logueo();
        formulariomensajes.clearErrors();
        entidadUE = new Entidad();
        entidadUE.setEsSubEntidad(false);
        ubigeoEntidadUE = new Ubigeo();
        titular = null;
        jefeRRHH = null;
        jefeOGA = null;
        titulart = new LkBusquedaTrabajador();
        jeferrhht = new LkBusquedaTrabajador();
        jefeogat = new LkBusquedaTrabajador();
        //accesos
        vbotones = false;
        vformulario = false;
        vdetalle = false;
        editando = false;
        opcion_limpiar = false;
//        vval_essub=true;
        //validacion de alerta
        if (entidadalerta != null) {
            entidadUE = entidadalerta;
            String hql = "update RSC_EVENTO set estadoevento=1 where tipoevento_id='" + Logger.MODIFICACION_ENTIDADES + "' and tabla_id='" + entidadUE.getId() + "' and estadoevento=0";
            System.out.println("iniciooooo" + Logger.MODIFICACION_ENTIDADES + "-" + entidadUE.getId());
            Query query = session.createSQLQuery(hql);
            int rowCount = query.executeUpdate();
            seteo();
            entidadalerta = null;
        }
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
            arroja = true;
        } else {
            usua = (UsuarioAcceso) result.get(0);
            if (usua.getAccesoupdate() == 1) {
                veditar = true;
                vbotones = true;
            }
            if (usua.getAccesodelete() == 1) {
                veliminar = true;
            }
            if (usua.getAccesoreport() == 1) {
                vformulario = true;
                vbotones = true;
            }
        }
    }

    // carga nueva entidad
    @Log
    void nuevoRegistro() {
        formulariomensajes.clearErrors();
        entidadUE = new Entidad();
        entidad_origen = "";
        titular = "";
        jefeRRHH = "";
        jefeOGA = "";
        bessubentidad = false;
        //
        noCambiaSubentidad = false;
    }

    // cargar datos
    @Log
    void seteo() {
        titular = null;
        jefeRRHH = null;
        jefeOGA = null;
        titulart = new LkBusquedaTrabajador();
        jeferrhht = new LkBusquedaTrabajador();
        jefeogat = new LkBusquedaTrabajador();
        if (entidadUE.getOrganizacionEstado() != null) {
            if (entidadUE.getOrganizacionEstado().getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        } else {
            bMuestraSectorEdicion = false;
        }
        if (entidadUE.getEsSubEntidad()) {
            bessubentidad = true;
            //
            noCambiaSubentidad = true;
            //
            entidad_origen = entidadUE.getEntidad().getDenominacion();
        } else {
            
            bessubentidad = false;
            //
            noCambiaSubentidad = false;
            //
        }
        if (entidadUE.getDepartamento() != null) {
            ubigeoEntidadUE.setDepartamento(entidadUE.getDepartamento());
        }
        if (entidadUE.getProvincia() != null) {
            ubigeoEntidadUE.setProvincia(entidadUE.getProvincia());
        }
        if (entidadUE.getDistrito() != null) {
            ubigeoEntidadUE.setDistrito(entidadUE.getDistrito());
        }
        if (entidadUE.getTitular() != null) {
            titular = entidadUE.getTitular().getApellidoPaterno() + " " + entidadUE.getTitular().getApellidoMaterno() + ", " + entidadUE.getTitular().getNombres();
        }
        if (entidadUE.getJefeRRHH() != null) {
            jefeRRHH = entidadUE.getJefeRRHH().getApellidoPaterno() + " " + entidadUE.getJefeRRHH().getApellidoMaterno() + ", " + entidadUE.getJefeRRHH().getNombres();
        }
        if (entidadUE.getJefeOga() != null) {
            jefeOGA = entidadUE.getJefeOga().getApellidoPaterno() + " " + entidadUE.getJefeOga().getApellidoMaterno() + ", " + entidadUE.getJefeOga().getNombres();
        }
    }

    // cargar combos
    @Log
    //para obtener datatos del Nivel Gobierno
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Sector Gobierno
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();
        if (bnivelGobierno != null) {
            list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", bnivelGobierno.getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstadoEdicion() {
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();
        if (entidadUE.getNivelGobierno() != null) {
            list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", entidadUE.getNivelGobierno().getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Organismo
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Via
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Zona
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Via Sub Entidad
    public GenericSelectModel<DatoAuxiliar> getSubTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Zona Sub Entidad
    public GenericSelectModel<DatoAuxiliar> getSubTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Entidad
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    // seleccion de boton
    @Log
    void onSelectedFromBusreset() {
        bnivelGobierno = null;
        bsectorGobierno = null;
        borganizacionEstado = null;
        tipOrganismo = null;
        busdenominacion = "";
        ubigeobusEntidadUE = new Ubigeo();
        btiposubentidad = null;
        bussubdenominacion = "";
        ubigeobusSubEntidadUE = new Ubigeo();
        bBuscaEntidad = false;
        busestado=null;
        bMuestraSector = false;
    }

    @Log
    void onSelectedFromBusenviar() {
        bBuscaEntidad = true;
        bBuscaSubEntidad=false;
    }

    // formulario de busqueda
    @Log
    @CommitAfter
    Object onSuccessFromformBusqueda() {
        if (!bBuscaEntidad) {
            return new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody());
        } else {
            return listaentidadZone.getBody();
        }
    }

    @Log
    void onSelectedFromBussubreset() {
        bnivelGobierno = null;
        bsectorGobierno = null;
        borganizacionEstado = null;
        tipOrganismo = null;
        busdenominacion = "";
        ubigeobusEntidadUE = new Ubigeo();
        btiposubentidad = null;
        bussubdenominacion = "";
        ubigeobusSubEntidadUE = new Ubigeo();
        bBuscaSubEntidad = false;
        bussubestado=null;
    }

    @Log
    void onSelectedFromBussubenviar() {
        bBuscaSubEntidad = true;
        bBuscaEntidad = false;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariosubcombosbusqueda() {
        if (!bBuscaSubEntidad) {
            return new MultiZoneUpdate("busquedasubcombosZone", busquedasubcombosZone.getBody());
        } else {
            return listaentidadZone.getBody();
        }
    }

    @Log
    void resetFormulario(Entidad entidad) {
        formulariomensajes.clearErrors();
        entidadUE = (Entidad) session.load(Entidad.class, entidad.getId());
        if (entidadUE.getOrganizacionEstado() != null) {
            if (entidadUE.getOrganizacionEstado().getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        } else {
            bMuestraSectorEdicion = false;
        }
        if (entidadUE.getEsSubEntidad()) {
            bessubentidad = true;
            //
            noCambiaSubentidad = true;
            //
            entidad_origen = entidadUE.getEntidad().getDenominacion();
        } else {
            bessubentidad = false;
            //
            noCambiaSubentidad = false;
            //
        }
        if (entidadUE.getDepartamento() != null) {
            ubigeoEntidadUE.setDepartamento(entidadUE.getDepartamento());
        }
        if (entidadUE.getProvincia() != null) {
            ubigeoEntidadUE.setProvincia(entidadUE.getProvincia());
        }
        if (entidadUE.getDistrito() != null) {
            ubigeoEntidadUE.setDistrito(entidadUE.getDistrito());
        }
        if (entidadUE.getTitular() != null) {
            titular = entidad.getTitular().getApellidoPaterno() + " " + entidadUE.getTitular().getApellidoMaterno() + ", " + entidadUE.getTitular().getNombres();
        }
        if (entidadUE.getJefeRRHH() != null) {
            jefeRRHH = entidadUE.getJefeRRHH().getApellidoPaterno() + " " + entidadUE.getJefeRRHH().getApellidoMaterno() + ", " + entidadUE.getJefeRRHH().getNombres();
        }
        if (entidadUE.getJefeOga() != null) {
            jefeOGA = entidadUE.getJefeOga().getApellidoPaterno() + " " + entidadUE.getJefeOga().getApellidoMaterno() + ", " + entidadUE.getJefeOga().getNombres();
        }
    }

    // accion de boton
    @Log
    void onSelectedFromReset() {
        bResetFormulario = true;
        editando = false;
    }

    @Log
    void onSelectedFromCancel() {
        bCancelFormulario = true;
        editando = false;
    }

    @Log
    void onSelectedFromguardarentidad()
    {
        insertarentidad = true;
    }

    // fromulario principal
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
                
        formulariomensajes.clearErrors();

        if(insertarentidad) {
            entidadUE.setEstado(true);

            entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());

            entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
            entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
            if (entidadUE.getEsSubEntidad() == null)
            {entidadUE.setEsSubEntidad(false);
            }
            String x = ruc_anterior;   
            Criteria c = session.createCriteria(Entidad.class);
       if (!editando)    
       {  
           c.add(Restrictions.eq("ruc",entidadUE.getRuc()));
            if (entidadUE.getEsSubEntidad()) 
            {

            } 
            else 
            {
                    if (!c.list().isEmpty()) {
                     formulariomensajes.recordError("RUC duplicado");
                return actualizarZonas();
                     }
            }
       }
       else  // modificacion
       {
         c.add(Restrictions.ne("ruc", x));
         c.add(Restrictions.eq("ruc",entidadUE.getRuc()));
         
         if (entidadUE.getEsSubEntidad()) {
         }else{
              if (!c.list().isEmpty())
                {
                    formulariomensajes.recordError("RUC duplicado");
                return actualizarZonas();
                }
         }
       }
            
            if (entidadUE.getEsSubEntidad()) {
                if (entidadUE.getTipoSubEntidad() == null) {
                    envelope.setContents("Debe seleccionar Tipo de Sub Entidad.");
                return actualizarZonas();
                }
            }

            if (entidadUE.getNivelGobierno() == null) {
                envelope.setContents("Debe ingresar el Nivel de Gobierno");
                return actualizarZonas();
            }

            if (entidadUE.getOrganizacionEstado() == null) {
                envelope.setContents("Debe ingresar la Organizacion Estado");
                return actualizarZonas();
            }

            if (entidadUE.getOrganizacionEstado().getCodigo() == 5) {
                if (entidadUE.getSectorGobierno() == null) {
                    envelope.setContents("Debe ingresar el Sector");
                return actualizarZonas();
                }
                if (entidadUE.getTipoOrganismo() == null) {
                    envelope.setContents("Debe ingresar el Tipo de Organismo");
                return actualizarZonas();
                }
            }

            if (entidadUE.getDenominacion() == null) {
                envelope.setContents("Debe ingresar el nombre de la Entidad ");
                return actualizarZonas();
            }
            if (entidadUE.getRuc() == null) {
                formulariomensajes.recordError("Debe ingresar el Ruc ");
                 //envelope.setContents("Debe ingresar el Ruc ");
                return actualizarZonas();
            }
            
            if (usua.getAccesoreport() == 0 && usua.getAccesoupdate() == 1) {
                vformulario = false;
            }
            // VERIFICACION QUE ES UNA CREACION
            System.out.println("CODXZ  "+entidadUE.getId()+"    "+entidadUE.getCue_entidad());
            boolean crearCodigoServir = false;
            if (entidadUE.getCue_entidad() == null || entidadUE.getCue_entidad().length()==0){crearCodigoServir = true;}
            
            session.saveOrUpdate(entidadUE);

            // GENERACION DEL CODIGO SERVIR           
            if (crearCodigoServir==true){
            NumberFormat formatter = new DecimalFormat("0000");
            String nuevoCUE;

            if (String.valueOf(entidadUE.getId()).length()<4){ nuevoCUE = formatter.format(entidadUE.getId());}
            else{ nuevoCUE = String.valueOf(entidadUE.getId()).substring(0, 4);}
            String codSERVIR = "RSC"+nuevoCUE;
            System.out.println(codSERVIR);
            entidadUE.setCue_entidad(codSERVIR);
            session.saveOrUpdate(entidadUE);        
            }
            
            session.flush();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ENTIDAD);
            insertarentidad = false;
            editando = false;
            if (editando) {
                if (usua.getAccesoreport() == 0) {
                    vformulario = false;
                }
                new Logger().loguearEvento(session, Logger.MODIFICACION_ENTIDADES, entidadUE.getId(), _usuario.getTrabajador().getId(), _usuario.getId(), Logger.MOTIVO_MODIFICACION_ENTIDADES, entidadUE.getId());
            } else {
                new Logger().loguearEvento(session, Logger.MODIFICACION_ENTIDADES, entidadUE.getId(), _usuario.getTrabajador().getId(), _usuario.getId(), Logger.MOTIVO_REGISTRO_ENTIDADES, entidadUE.getId());
            }
            envelope.setContents(this.mensaje_accion());
            // OPCION DE LIMPIAR CAMPOS (OPCIONAL)
            opcion_limpiar = false;
            
        }

        else if (bResetFormulario) {
            if (opcion_limpiar.equals(true)) {
                resetFormulario(entidadUE);
            } else {// entidadUE = new Entidad();
                this.limpiar_formulario();
            }

            opcion_limpiar = false;

            editando = false;
            bResetFormulario = false;
            return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).
                    add("zoneDatos", zoneDatos.getBody()).
                    add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).
                    add("zoneOtrosDatos", zoneOtrosDatos.getBody()).
                    add("TitularZone", TitularZone.getBody()).
                    add("JefeRRHHZone", JefeRRHHZone.getBody()).
                    add("JefeOGAZone", JefeOGAZone.getBody()).add("botonesZone",botonesZone.getBody());
        } 
        else if (bCancelFormulario) {
            bCancelFormulario = false;
            editando = false;
            nuevoRegistro();
            if (!vbotones) {
                vformulario = false;
            } else {
                
                System.out.println("XXX");
//                return "AMEntidadUEjecutora";
             return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).
                    add("zoneDatos", zoneDatos.getBody()).
                    add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).
                    add("zoneOtrosDatos", zoneOtrosDatos.getBody()).
                    add("TitularZone", TitularZone.getBody()).
                    add("JefeRRHHZone", JefeRRHHZone.getBody()).
                    add("JefeOGAZone", JefeOGAZone.getBody()).add("botonesZone",botonesZone.getBody());
               
            }
        }  

        nuevoRegistro();
        return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).
                add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).
                add("TitularZone", TitularZone.getBody()).
                add("JefeRRHHZone", JefeRRHHZone.getBody()).
                add("JefeOGAZone", JefeOGAZone.getBody()).
                add("mensajesZone", mensajesZone.getBody()).
                add("botonesZone", botonesZone.getBody()).
                add("zoneOtrosDatos", zoneOtrosDatos.getBody()).
                add("listaentidadZone", listaentidadZone.getBody()).
                add("EOrigenZone",EOrigenZone.getBody());
    }

    @Log
    public Object actualizarZonas()
    {
        return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).
                add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).
                add("TitularZone", TitularZone.getBody()).
                add("JefeRRHHZone", JefeRRHHZone.getBody()).
                add("JefeOGAZone", JefeOGAZone.getBody()).
                add("mensajesZone", mensajesZone.getBody());
    }

    
    @Log
    public void onActivate(Entidad eue) {
        entidadUE = eue;
        mostrarEsconder = "Ocultar";
        mostrarFiltros = true;
    }
    @Persist
    private String ruc_anterior;
    
    //accion de editar entidad
    @Log
    Object onEditarSeleccion(Entidad entidad) {
        entidadUE = entidad;
        seteo();//renato agregado
        editando = true;
        if(entidad.getRuc()!=null){
            ruc_anterior = entidad.getRuc();
        }
        vformulario = true;
         if (entidadUE.getEsSubEntidad()) {
            bessubentidad = true;
            //
            noCambiaSubentidad = true;
            //
            if(entidadUE.getEntidad() != null){
                if(entidadUE.getEntidad().getDenominacion().toString() != null){
                    entidad_origen = entidadUE.getEntidad().getDenominacion().toString();
                }
            }
        } else {
            bessubentidad = false;
            //
            noCambiaSubentidad = false;
            //
        }
        vbotones = true;
        opcion_limpiar = true;
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("zoneOtrosDatos", zoneOtrosDatos.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()).add("botonesZone", botonesZone.getBody());
    }

    // ver detalle de entidad
    @Log
    Object onActionFromDetalle(Entidad entidad) {
        entidadUE = entidad;
        seteo();
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        bessubentidad = true;
        //
        noCambiaSubentidad = true;
        //
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("zoneOtrosDatos", zoneOtrosDatos.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody()).add("botonesZone", botonesZone.getBody());
    }

    // eliminar entidad
    @Log
    @CommitAfter
    Object onActionFromEliminarSeleccion(Entidad enti1) {
        if (enti1.getEstado()) {
            enti1.setEstado(false);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(enti1.getId()),Logger.CODIGO_OPERACION_DELETE,Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ENTIDAD);
            session.saveOrUpdate(enti1);
            envelope.setContents("Entidad Eliminada");
        } else {
            enti1.setEstado(true);
            session.saveOrUpdate(enti1);
            envelope.setContents("Entidad Revertida");
        }
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody()).add("listaentidadZone", listaentidadZone.getBody());
    }

    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.like("esSubEntidad",false));
        if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" +bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion","%"+ bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        
        return c.list();
    }


    
    // cargar entidades
    @Log
    public List<LkBusquedaEntidad> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (bBuscaEntidad) {
            //Entidad
            if (bnivelGobierno != null) {
                c.add(Restrictions.eq("nivelgobierno", bnivelGobierno.getValor()));
            }
            if (bsectorGobierno != null) {
                c.add(Restrictions.eq("sectorgobierno", bsectorGobierno.getValor()));
            }
            if (borganizacionEstado != null) {
                c.add(Restrictions.eq("organizacionestado", borganizacionEstado.getValor()));
            }
            if (tipOrganismo != null) {
                c.add(Restrictions.eq("tipoorganismo", tipOrganismo.getValor()));
            }
            if (busdenominacion != null) {
                c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + busdenominacion + "%").ignoreCase()).
                        add(Restrictions.like("denominacion", "%" + busdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).
                        add(Restrictions.like("denominacion", "%" + busdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));
            }
            if (ubigeobusEntidadUE.getDepartamento() != null) {
                c.add(Restrictions.eq("departamento", ubigeobusEntidadUE.getDepartamento().getValor()));
            }
            if (ubigeobusEntidadUE.getProvincia() != null) {
                c.add(Restrictions.eq("provincia", ubigeobusEntidadUE.getProvincia().getValor()));
            }
            if (ubigeobusEntidadUE.getDistrito() != null) {
                c.add(Restrictions.eq("distrito", ubigeobusEntidadUE.getDistrito().getValor()));
            }
            if (busestado != null) {
                if (busestado.equalsIgnoreCase("Activo")) {
                    c.add(Restrictions.eq("estado", true));
                } else {
                    c.add(Restrictions.eq("estado", false));
                }
            }
            c.add(Restrictions.eq("essubentidad", false));
        }
        if (bBuscaSubEntidad) {
            //Sub Entidad
            if (btiposubentidad != null) {
                c.add(Restrictions.eq("tiposubentidad", btiposubentidad.getValor()));
            }
            if (bussubdenominacion != null) {
                c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bussubdenominacion + "%").ignoreCase()).
                        add(Restrictions.like("denominacion", "%" + bussubdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).
                        add(Restrictions.like("denominacion", "%" + bussubdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));
            }
            if (ubigeobusSubEntidadUE.getDepartamento() != null) {
                c.add(Restrictions.eq("departamento", ubigeobusSubEntidadUE.getDepartamento().getValor()));
            }
            if (ubigeobusSubEntidadUE.getProvincia() != null) {
                c.add(Restrictions.eq("provincia", ubigeobusSubEntidadUE.getProvincia().getValor()));
            }
            if (ubigeobusSubEntidadUE.getDistrito() != null) {
                c.add(Restrictions.eq("distrito", ubigeobusSubEntidadUE.getDistrito().getValor()));
            }
            if (bussubestado != null) {
                if (bussubestado.equalsIgnoreCase("Activo")) {
                    c.add(Restrictions.eq("estado", true));
                } else {
                    c.add(Restrictions.eq("estado", false));
                }
            }
//            if (bessubentidad) {
//                c.add(Restrictions.eq("essubentidad", bessubentidad));
//            }
            c.add(Restrictions.eq("essubentidad", true));
        }

        nroregistros = Integer.toString(c.list().size());

        return c.list();
    }

    @Persist
    @Property
    private String nroregistros;
    
    // accion de botones
    @Log
    void onSelectedFromBuscarTitular() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    @Log
    void onSelectedFromBuscarJefeRRHH() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    @Log
    void onSelectedFromBuscarJefeOGA() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    @Log
    void onSelectedFromBuscarEntidad() {
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = true;
        mostrar = false;
    }

    
    @Property
    @Persist
    private boolean insertarentidad;
    @Log
    @CommitAfter
    Object onSuccessFromFormularioEntidadOrigen() {
        if (entidadUE.getEsSubEntidad()) {
            bessubentidad = true;
            //
            noCambiaSubentidad = false;
            //
        } else {
            bessubentidad = false;
            //
            noCambiaSubentidad = false;
            //
        }
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody()).
                add("busZone2", busZone2.getBody()).
                add("trabajadorZone", trabajadorZone.getBody()).
                add("zoneDatos", zoneDatos.getBody()).
                add("EOrigenZone", EOrigenZone.getBody()).
                add("ubigeoEntidadZone", ubigeoEntidadZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariotitular() {
        btitulari = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody()).
                add("busZone", busZone.getBody()).
                add("entiZone", entiZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariojeferrhh() {
        bjefeRRHHi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody()).
                add("busZone", busZone.getBody()).
                add("entiZone", entiZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariojefeoga() {
        bjefeOGAi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody()).
                add("busZone", busZone.getBody()).
                add("entiZone", entiZone.getBody());
    }

    void onSelectedFromCancelFormFindTrabajador() {
        btitulari = false;
        bjefeOGAi = false;
        bjefeRRHHi = false;
        bSeleccionaPersonal = false;
    }

    @Log
    Object onSuccessFromFormFindTrabajador() {
        if (bjefeOGAi || bjefeRRHHi || btitulari) {
            mostrar = true;
        } else {
            mostrar = false;
        }
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody()).
                add("busZone", busZone.getBody()).
                add("entiZone", entiZone.getBody());
    }

    @Log
    Object onActionFromCancelFormFindEntidad() {
        mostrar = false;
        zonasPopup();
        return request.isXHR() ? EOrigenZone.getBody() : null;
    }

    @Log
    Object onSuccessFromFormFindEntidad() {
        System.err.println("onSuccessFromFormFindEntidad");
        mostrar = true;
        return zonasPopup();
    }

    @Log
    Object onActionFromSeleccionaEntidad(Entidad entidad) 
    {
        System.err.println("onActionFromSeleccionaEntidad");
        if (entidad != null) {
            entidad_origen = entidad.getDenominacion();
            entidadUE.setEntidad(entidad);
            entidadUE.setEsSubEntidad(true);
            entidadUE.setTipoVia(entidad.getTipoVia());
            entidadUE.setTipoZona(entidad.getTipoZona());
            entidadUE.setDescvia(entidad.getDescvia());
            entidadUE.setDescZona(entidad.getDescZona());
            
            /* no tomar en cuenta al crear/modificar subentidad
             * 
            entidadUE.setNivelGobierno(entidad.getNivelGobierno());
            entidadUE.setOrganizacionEstado(entidad.getOrganizacionEstado());
            entidadUE.setSectorGobierno(entidad.getSectorGobierno());
            entidadUE.setTipoOrganismo(entidad.getTipoOrganismo());
            * 
            */
            //
            if (!editando)
            {
            entidadUE.setNivelGobierno(null);
            entidadUE.setOrganizacionEstado(null);
            entidadUE.setSectorGobierno(null);
            entidadUE.setTipoOrganismo(null);            
            }
            else
            {
           /* entidadUE.setNivelGobierno(entidad.getNivelGobierno());
            entidadUE.setOrganizacionEstado(entidad.getOrganizacionEstado());
            entidadUE.setSectorGobierno(entidad.getSectorGobierno());
            entidadUE.setTipoOrganismo(entidad.getTipoOrganismo());*/
            }
            //
            
            noCambiaSubentidad = false;
            
            ubigeoEntidadUE.setDepartamento(entidad.getDepartamento());
            ubigeoEntidadUE.setProvincia(entidad.getProvincia());
            ubigeoEntidadUE.setDistrito(entidad.getDistrito());
        }
        mostrar = false;
        bSeleccionaEntidad = false;
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("zoneDatos", zoneDatos.getBody()).
                add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).
                add("zoneOtrosDatos", zoneOtrosDatos.getBody());
    }

    // cargar trabajadores
    @Log
    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("entidad_id",entidadUE.getId()));
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador","%"+ nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
    

    @Log
    Object onActionFromSeleccionaTitular(Trabajador traba) {
        if (traba != null) {
            titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            entidadUE.setTitular(traba);
        }
        btitulari = false;
        mostrar = false;
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = false;
        return TitularZone.getBody();
    }

    @Log
    Object onActionFromSeleccionaJefeRRHH(Trabajador traba) {
        if (traba != null) {
            jefeRRHH = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            entidadUE.setJefeRRHH(traba);
        }
        bjefeRRHHi = false;
        mostrar = false;
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = false;
        return JefeRRHHZone.getBody();
    }

    @Log
    Object onActionFromSeleccionaJefeOGA(Trabajador traba) {
        if (traba != null) {
            jefeOGA = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
            entidadUE.setJefeOga(traba);
        }
        bjefeOGAi = false;
        mostrar = false;
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = false;
        return JefeOGAZone.getBody();
    }

    @Log
    void onDenominacionChanged() {
        entidadUE.setDenominacion(_request.getParameter("param"));
    }

    @Log
    void onSiglaChanged() {
        entidadUE.setSigla(_request.getParameter("param"));
    }

    @Log
    void onRucChanged() {
        entidadUE.setRuc(_request.getParameter("param"));
    }

    @Log
    void onCue_EntidadChanged() {
        entidadUE.setCue_entidad(_request.getParameter("param"));
    }
    
    @Log
    void onCue_ruffChanged() {
        entidadUE.setCue_rufe(_request.getParameter("param"));
    }

    @Log
    void onDireccionChanged() {
        entidadUE.setDescvia(_request.getParameter("param"));
    }

    @Log
    void onDescZonaChanged() {
        entidadUE.setDescZona(_request.getParameter("param"));
    }

    @Log
    void onEmailInstitucionalChanged() {
        entidadUE.setEmailInstitucional(_request.getParameter("param"));
    }

    @Log
    void onUrlEntidadChanged() {
        entidadUE.setUrlEntidad(_request.getParameter("param"));
    }

    @Log
    void onTelefonoEntidadChanged() {
        entidadUE.setTelefonoEntidad(_request.getParameter("param"));
    }

    // evento de cambio en los campos
    @Log
    Object onValueChangedFromBnivelGobierno(DatoAuxiliar dato) {
        return request.isXHR() ? new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromborganizacionEstado(DatoAuxiliar dato) {
        if (dato != null) {
            if (dato.getCodigo() == 5) {
                bMuestraSector = true;
            } else {
                bMuestraSector = false;
            }
        }
        return request.isXHR() ? new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromNivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            entidadUE.setOrganizacionEstado(null);
        }
        return request.isXHR() ? new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()) : null;
    }

    @Log
    Object onValueChangedFromOrganizacionEdicion(DatoAuxiliar dato) {
        if (dato != null) {
            if (dato.getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        }
        return request.isXHR() ? new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()) : null;
    }

    @Log
    Object onValueChangedFromBtiposubentidad(DatoAuxiliar dato) {
        return request.isXHR() ? new MultiZoneUpdate("busquedasubcombosZone", busquedasubcombosZone.getBody()) : null;
    }

    @Log
    void onBussubdenominacionChanged() {
        bussubdenominacion = _request.getParameter("param");
    }
    
    @Log
    void onBusdenominacionChanged() {
        busdenominacion = _request.getParameter("param");
    }

    // limpiar formulario
    private void limpiar_formulario() {
        bCancelFormulario = true;
        editando = false;
        entidadUE = new Entidad();
        entidadUE.setEsSubEntidad(false);
        entidad_origen = null;
        titular = null;
        jefeRRHH = null;
        jefeOGA = null;
        bessubentidad = false;
        //
        noCambiaSubentidad = false;
        //
        ubigeoEntidadUE.setDepartamento(null);
        ubigeoEntidadUE.setProvincia(null);
        ubigeoEntidadUE.setDistrito(null);
    }

    private String mensaje_accion() {
        String tipo = "";
        String modo = "";
        if (entidadUE.getEsSubEntidad()) {
            tipo = "Subentidad";
        } else {
            tipo = "Entidad";
        }
        if (opcion_limpiar) {
            modo = "modificada";
        } else {
            modo = "creada";
        }
        return tipo + " " + modo + " Exitosamente";
    }

    @Log
    private MultiZoneUpdate zonasPopup() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("busZone2", busZone2.getBody()).
                add("trabajadorZone", trabajadorZone.getBody()).
                add("busZone", busZone.getBody()).
                add("entiZone", entiZone.getBody());
        return mu;
    }

 @Log   
 public Boolean getTieneSubentidad(){

     Criteria c = session.createCriteria(LkBusquedaEntidad.class);
     c.add(Restrictions.eq("subId", listaentidad.getSubId()));
     if (!c.list().isEmpty())
     {
         return true;
     }  
     return false;
 }
 
}
