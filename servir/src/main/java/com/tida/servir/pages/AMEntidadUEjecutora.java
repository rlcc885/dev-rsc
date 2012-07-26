package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
    @Component(id = "formulariocombosbusqueda")
    private Form formulariocombosbusqueda;
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
    @Property
    @Persist
    private DatoAuxiliar btipoOrganismo;
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
    private int elemento = 0;
    //**************************************** OBJETOS USADOS EN LA CLASE
    //********************************************************************
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
    private boolean bMuestraSector;
    @Property
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
    @Persist
    @Property
    private Entidad rowEntidad;
    @Property
    private LkBusquedaTrabajador titulart;
    @Property
    private LkBusquedaTrabajador jeferrhht;
    @Property
    private LkBusquedaTrabajador jefeogat;

    //Inicio de lac carga de la pagina
    @SetupRender
    private void inicio() {
        entidadUE = new Entidad();
        ubigeoEntidadUE = new Ubigeo();
        titular = null;
        jefeRRHH = null;
        jefeOGA = null;
        titulart = new LkBusquedaTrabajador();
        jeferrhht = new LkBusquedaTrabajador();
        jefeogat = new LkBusquedaTrabajador();
    }

    //para obtener datatos del Nivel Gobierno
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Sector Gobierno
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();
        if (bnivelGobierno != null) {
            list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", bnivelGobierno.getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstadoEdicion() {
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();
        if (entidadUE.getNivelGobierno() != null) {
            list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", entidadUE.getNivelGobierno().getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Organismo
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Via
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Zona
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Via Sub Entidad
    public GenericSelectModel<DatoAuxiliar> getSubTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Zona Sub Entidad
    public GenericSelectModel<DatoAuxiliar> getSubTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Entidad
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //reset del formulario (borrar objeto)
    void onSelectedFromSubreset() {
        elemento = 1;
        bessubentidad = false;
    }

    void onSelectedFromSubcancel() {
        elemento = 2;
        bessubentidad = false;
    }

    void onSelectedFromReset() {
        elemento = 1;
        bessubentidad = false;
    }

    void onSelectedFromBusreset() {
        bnivelGobierno = null;
        bsectorGobierno = null;
        borganizacionEstado = null;
        btipoOrganismo = null;
        busdenominacion = "";
        ubigeobusEntidadUE = null;
        btiposubentidad = null;
        bussubdenominacion = "";
        ubigeobusSubEntidadUE = null;
        elemento = 3;
    }

    void onSelectedFromBussubenviar() {
        bessubentidad = true;
    }

    void onSelectedFromBusenviar() {
        bessubentidad = false;
    }

    void onSelectedFromBussubreset() {
        bnivelGobierno = null;
        bsectorGobierno = null;
        borganizacionEstado = null;
        btipoOrganismo = null;
        busdenominacion = "";
        ubigeobusEntidadUE = null;
        btiposubentidad = null;
        bussubdenominacion = "";
        ubigeobusSubEntidadUE = null;
        elemento = 3;
    }

    void onSelectedFromCancel() {
        elemento = 2;
        bessubentidad = false;
    }

    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        entidadUE.setEstado(true);
        entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
        entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
        entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
        if (entidadUE.getNivelGobierno() == null) {
            envelope.setContents("Debe ingresar el Nivel de Gobierno");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else if (entidadUE.getOrganizacionEstado() == null) {
            envelope.setContents("Debe ingresar la Organizacion Estado");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else if (entidadUE.getSectorGobierno() == null) {
            envelope.setContents("Debe ingresar el Sector");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else if (entidadUE.getTipoOrganismo() == null) {
            envelope.setContents("Debe ingresar el Tipo de Organismo");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else if (entidadUE.getDenominacion() == null) {
            envelope.setContents("Debe ingresar el nombre de la Entidad ");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else {
            session.saveOrUpdate(entidadUE);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
            envelope.setContents("Entidad creada exitosamente");
            return new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        }
    }

    AMEntidadUEjecutora onActionFromToggle_filtros() {
        if (mostrarFiltros) {
            mostrarFiltros = false;
            mostrarEsconder = "Mostrar";
        } else {
            mostrarFiltros = true;
            mostrarEsconder = "Ocultar";
        }
        return this;
    }

    public void onActivate(Entidad eue) {
        entidadUE = eue;
        mostrarEsconder = "Ocultar";
        mostrarFiltros = true;
    }

    //Object onActionFromEditarSeleccion(Entidad enti1) {
    Object onEditarSeleccion(Entidad entidad) {
        entidadUE = entidad;
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
            entidad_origen = entidadUE.getEntidad().getDenominacion();
        } else {
            bessubentidad = false;
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
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("zoneOtrosDatos", zoneOtrosDatos.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody());
    }

    @CommitAfter
    Object onActionFromEliminarSeleccion(Entidad enti1) {
        if (enti1.getEstado()) {
            enti1.setEstado(false);
            session.saveOrUpdate(enti1);
            envelope.setContents("Entidad/Sub Entidad Eliminada");
        } else {
            enti1.setEstado(true);
            session.saveOrUpdate(enti1);
            envelope.setContents("Entidad/Sub Entidad Revertida");
        }
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody()).add("listaentidadZone", listaentidadZone.getBody());
    }

    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    public List<LkBusquedaEntidad> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);

        //Entidad
        if (bnivelGobierno != null) {
            System.out.println("------------------ Nivel Gobierno " + bnivelGobierno.getValor());
            c.add(Restrictions.eq("nivelgobierno", bnivelGobierno.getValor()));
        }
        if (bsectorGobierno != null) {
            System.out.println("------------------ Sector Gobierno " + bsectorGobierno.getValor());
            c.add(Restrictions.eq("sectorgobierno", bsectorGobierno.getValor()));
        }
        if (borganizacionEstado != null) {
            System.out.println("------------------ Organizacion Estado " + borganizacionEstado.getValor());
            c.add(Restrictions.eq("organizacionestado", borganizacionEstado.getValor()));
        }
        if (btipoOrganismo != null) {
            System.out.println("------------------ Tipo Organismo " + btipoOrganismo.getValor());
            c.add(Restrictions.eq("tipoorganismo", btipoOrganismo.getValor()));
        }
        if (busdenominacion != null) {
            System.out.println("------------------ Enitidad " + busdenominacion);
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", busdenominacion + "%").ignoreCase()).add(Restrictions.like("denominacion", busdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", busdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (ubigeobusEntidadUE.getDepartamento() != null) {
            System.out.println("------------------ Departamento " + ubigeobusEntidadUE.getDepartamento().getValor());
            c.add(Restrictions.eq("departamento", ubigeobusEntidadUE.getDepartamento().getValor()));
        }
        if (ubigeobusEntidadUE.getProvincia() != null) {
            System.out.println("------------------ Provincia " + ubigeobusEntidadUE.getProvincia().getValor());
            c.add(Restrictions.eq("provincia", ubigeobusEntidadUE.getProvincia().getValor()));
        }
        if (ubigeobusEntidadUE.getDistrito() != null) {
            System.out.println("------------------ Distrito" + ubigeobusEntidadUE.getDistrito().getValor());
            c.add(Restrictions.eq("distrito", ubigeobusEntidadUE.getDistrito().getValor()));
        }
        if (busestado != null) {
            System.out.println("------------------ Estado" + busestado);
            if (busestado.equalsIgnoreCase("Activo")) {
                c.add(Restrictions.eq("estado", true));
            } else {
                c.add(Restrictions.eq("estado", false));
            }
        }

        //Sub Entidad
        if (btiposubentidad != null) {
            System.out.println("------------------ Tipo Sub Entidad " + btiposubentidad.getValor());
            c.add(Restrictions.eq("tiposubentidad", btiposubentidad.getValor()));
        }
        if (bussubdenominacion != null) {
            System.out.println("------------------ Sub Enitidad " + bussubdenominacion);
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bussubdenominacion + "%").ignoreCase()).add(Restrictions.like("denominacion", bussubdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bussubdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (ubigeobusSubEntidadUE.getDepartamento() != null) {
            System.out.println("------------------ Sub Departamento " + ubigeobusSubEntidadUE.getDepartamento().getValor());
            c.add(Restrictions.eq("departamento", ubigeobusSubEntidadUE.getDepartamento().getValor()));
        }
        if (ubigeobusSubEntidadUE.getProvincia() != null) {
            System.out.println("------------------ Sub Provincia " + ubigeobusSubEntidadUE.getProvincia().getValor());
            c.add(Restrictions.eq("provincia", ubigeobusSubEntidadUE.getProvincia().getValor()));
        }
        if (ubigeobusSubEntidadUE.getDistrito() != null) {
            System.out.println("------------------ Sub Distrito" + ubigeobusSubEntidadUE.getDistrito().getValor());
            c.add(Restrictions.eq("distrito", ubigeobusSubEntidadUE.getDistrito().getValor()));
        }
        if (bussubestado != null) {
            System.out.println("------------------ Sub Estado" + bussubestado);
            if (bussubestado.equalsIgnoreCase("Activo")) {
                c.add(Restrictions.eq("estado", true));
            } else {
                c.add(Restrictions.eq("estado", false));
            }
        }

        if (bessubentidad) {
            c.add(Restrictions.eq("essubentidad", bessubentidad));
        }

        return c.list();
    }

    @CommitAfter
    Object onSuccessFromformulariocombosbusqueda() {
        if (elemento == 3) {
            return new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody());
        } else {
            return listaentidadZone.getBody();
        }
    }

    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    Object onActionFromSeleccionaEntidad(Entidad entidad) {
        if (entidad != null) {
            entidadUE = entidad;
            entidad_origen = entidad.getDenominacion();
            entidadUE.setEntidad(entidad);
            entidadUE.setTipoVia(entidad.getTipoVia());
            entidadUE.setTipoZona(entidad.getTipoZona());
            entidadUE.setDireccion(entidad.getDireccion());
            entidadUE.setDescZona(entidad.getDescZona());
            entidadUE.setNivelGobierno(entidad.getNivelGobierno());
            entidadUE.setOrganizacionEstado(entidad.getOrganizacionEstado());
            entidadUE.setSectorGobierno(entidad.getSectorGobierno());
            entidadUE.setTipoOrganismo(entidad.getTipoOrganismo());
            ubigeoEntidadUE.setDepartamento(entidad.getDepartamento());
            ubigeoEntidadUE.setProvincia(entidad.getProvincia());
            ubigeoEntidadUE.setDistrito(entidad.getDistrito());
        }
        mostrar = false;
        bSeleccionaEntidad = false;
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("zoneDatos", zoneDatos.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("zoneOtrosDatos", zoneOtrosDatos.getBody());
    }

    void onSelectedFromBuscarTitular() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    void onSelectedFromBuscarJefeRRHH() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    void onSelectedFromBuscarJefeOGA() {
        bSeleccionaPersonal = true;
        bSeleccionaEntidad = false;
    }

    void onSelectedFromBuscarEntidad() {
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = true;
    }

    void onSelectedFromCancelFormularioTrabajador() {
        btitulari = false;
        bjefeOGAi = false;
        bjefeRRHHi = false;
        bSeleccionaPersonal = false;
        bSeleccionaEntidad = false;
    }

    @CommitAfter
    Object onSuccessFromformulariotitular() {
        btitulari = true;
//        bSeleccionaPersonal = true;
//        bSeleccionaEntidad = false;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody()).add("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromformulariojeferrhh() {
        bjefeRRHHi = true;
//        bSeleccionaPersonal = true;
//        bSeleccionaEntidad = false;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromformulariojefeoga() {
        bjefeOGAi = true;
//        bSeleccionaPersonal = true;
//        bSeleccionaEntidad = false;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromFormularioEntidadOrigen() {
        System.err.println("onSuccessFromFormularioEntidadOrigen");
        System.err.println(entidadUE.getEsSubEntidad());
        if (entidadUE.getEsSubEntidad()) {
            bessubentidad = true;
        } else {
            bessubentidad = false;
        }
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody()).add("zoneDatos", zoneDatos.getBody()).add("EOrigenZone", EOrigenZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromFormularioTrabajador() {
        if (bjefeOGAi || bjefeRRHHi || btitulari) {
            mostrar = true;
        } else {
            mostrar = false;
        }
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        if (nombreTrabajador != null) {
            System.out.println("nombress: " + nombreTrabajador);
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

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

    void onDenominacionChanged() {
        entidadUE.setDenominacion(_request.getParameter("param"));
    }

    void onSiglaChanged() {
        entidadUE.setSigla(_request.getParameter("param"));
    }

    void onRucChanged() {
        entidadUE.setRuc(_request.getParameter("param"));
    }

    void onCue_EntidadChanged() {
        entidadUE.setCue_entidad(_request.getParameter("param"));
    }

    void onDireccionEntChanged() {
        entidadUE.setDireccion(_request.getParameter("param"));
    }

    void onDescZonaEntChanged() {
        entidadUE.setDescZona(_request.getParameter("param"));
    }

    void onEmailInstitucionalChanged() {
        entidadUE.setEmailInstitucional(_request.getParameter("param"));
    }

    void onUrlEntidadChanged() {
        entidadUE.setUrlEntidad(_request.getParameter("param"));
    }

    void onTelefonoEntidadChanged() {
        entidadUE.setTelefonoEntidad(_request.getParameter("param"));
    }

    Object onValueChangedFromBnivelGobierno(DatoAuxiliar dato) {
        return request.isXHR() ? new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody()) : null;
    }

    Object onValueChangedFromborganizacionEstado(DatoAuxiliar dato) {
        if (dato.getCodigo() == 5) {
            bMuestraSector = true;
        } else {
            bMuestraSector = false;
        }
        return request.isXHR() ? new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody()) : null;
    }

    Object onValueChangedFromNivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            entidadUE.setOrganizacionEstado(null);
        }
        return request.isXHR() ? new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()) : null;
    }

    Object onValueChangedFromOrganizacionEdicion(DatoAuxiliar dato) {
        if (dato != null) {
            if (dato.getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        }
        System.err.println("onValueChangedFrombOrganizacionEstadoEdicion");
        return request.isXHR() ? new MultiZoneUpdate("zoneDatos", zoneDatos.getBody()) : null;
    }
}
