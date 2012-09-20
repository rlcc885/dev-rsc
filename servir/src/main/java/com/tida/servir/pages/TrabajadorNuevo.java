package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

//@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class TrabajadorNuevo extends GeneralPage {

    @Inject
    private Session session;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @SessionState
    @Property
    private Usuario _usuario;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    //Seguridad
    @Property
    @Persist
    private UsuarioAcceso usua;
    @Persist
    @Property
    private Boolean vformulario;
    @Inject
    private ComponentResources _resources;
    //Zonas
    @InjectComponent
    @Property
    private Zone trabajadorNuevoZone;
    @InjectComponent
    @Property
    private Zone nuevaUnidadZone;
    @InjectComponent
    @Property
    private Zone nuevoCargoZone;
    @InjectComponent
    @Property
    private Zone cargosZone;
    @InjectComponent
    private Zone mensajesZone;
    //Formularios
    @Component(id = "formulariotrabajadornuevo")
    private Form formulariotrabajadornuevo;
    @Component(id = "formularionuevaunidadorganica")
    private Form formularionuevaunidadorganica;
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    //Entidades
    @Property
    @SessionState
    private Entidad oi;
    @Property
    @Persist
    private Trabajador nuevo;
    @Property
    @Persist
    private LkBusquedaUnidad unidadorganica;
    @Property
    @Persist
    private UnidadOrganica nuevaunidadorganica;
    @Property
    @Persist
    private LkCargosDisponibles cargo;
    @Property
    @Persist
    private Cargoxunidad cargo2;
    //Variables
    private List<String> tiposDoc = new ArrayList();
    @Persist
    @Property
    private String nuevaUOrganica;
    @Persist
    @Property
    private String nuevoCargo;
    @Property
    @Persist
    private DatoAuxiliar tipovinculo;
    private boolean DNI;
    @Property
    private CargoAsignado cargoAsignado;
    @Property
    @Persist
    private Legajo nuevoLegajo;
    private CargosSelectModel<Cargoxunidad> _beans;
    @Property
    @Persist
    private String fechaingreso;
    @Property
    @Persist
    private Boolean unidadSeleccionada;
    @Property
    @Persist
    private boolean puestoconfianza;
    @Property
    @Persist
    private Boolean bTrabajadorRegistrado;
    @InjectComponent
    private Envelope envelope;
    private int elemento = 0;
    //Listado de entidades
    @InjectComponent
    private Zone listaentidadZone;
    @Persist
    @Property
    private LkBusquedaTrabajador listaentidad;
    @Property
    @Persist
    private Boolean mostrar;
    @Property
    @Persist
    private Boolean creaNuevaUnidad;
    @Property
    @Persist
    private Boolean creaNuevoCargo;
    @Property
    @Persist
    private DatoAuxiliar regimenla;
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ALTA_TRABAJADOR);
        return null;
    }

    // inicio de la pagina
    @Log
    @SetupRender
    void initializeValue() {
        logueo();
        unidadorganica = null;
        nuevaunidadorganica = new UnidadOrganica();
        cargo = new LkCargosDisponibles();
        cargo2 = new Cargoxunidad();
        nuevaUOrganica = "";
        nuevoCargo = "";
        tipovinculo = null;
        bTrabajadorRegistrado = false;
        nuevoLegajo = new Legajo();
        mostrar = true;
        creaNuevaUnidad = false;
        creaNuevoCargo = false;
        puestoconfianza = false;
        fechaingreso = "";
        nuevo = new Trabajador();
        regimenla=null;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", usuarioTrabajador.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));

        } else {
            usua = (UsuarioAcceso) result.get(0);

            if (usua.getAccesoreport() == 1) {
                vformulario = true;

            }

        }
    }


    // cargar combos
    @Log
    public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicas() {
        Criteria c;
        c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.eq("entidadId", oi.getId()));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        return new GenericSelectModel<LkBusquedaUnidad>(c.list(), LkBusquedaUnidad.class, "denominacion", "id", _access);

    }

    @Log
    public GenericSelectModel<LkCargosDisponibles> getBeanCargo() {
        List<LkCargosDisponibles> list;
        Criteria c = session.createCriteria(LkCargosDisponibles.class);

        if (unidadorganica != null) {
            c.add(Restrictions.eq("uoid", unidadorganica.getId()));
        }
        if (oi != null) {
            c.add(Restrictions.eq("entidadid", oi.getId()));
        }
        c.add(Restrictions.eq("estado", true));
        c.add(Restrictions.eq("resultado", true));
        list = c.list();
        return new GenericSelectModel<LkCargosDisponibles>(list, LkCargosDisponibles.class, "den_cargo", "id", _access);
    }


    // formulario de creacion de nueva UO
    @Log
    @CommitAfter
    Object onSuccessFromFormularionuevaunidadorganica() {
        System.out.println("entroo aaa" + nuevaUOrganica);
        if (nuevaUOrganica == null) {
            formulariotrabajadornuevo.recordError("No puede agregar una Unidad Organica vacia.");
        } else {
            System.out.println("entroo ccc");
            nuevaunidadorganica = new UnidadOrganica();
            nuevaunidadorganica.setDen_und_organica(nuevaUOrganica);
            nuevaunidadorganica.setNivel(1);
            nuevaunidadorganica.setEntidad(oi);
            nuevaunidadorganica.setEstado(UnidadOrganica.ESTADO_ALTA);
            session.saveOrUpdate(nuevaunidadorganica);
            envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_EXITO);
        }

        return new MultiZoneUpdate("nuevaUnidadZone", nuevaUnidadZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
    }


    // formulario de creacion de nuevo cargo
    @Log
    @CommitAfter
    Object onSuccessFromFormularionuevocargo() {
        if (unidadorganica == null) {
            formulariotrabajadornuevo.recordError("Debe seleccionar una Unidad Organica.");
        } else {
        if (nuevoCargo != null) {            
            Cargoxunidad ncargo;
            ncargo = new Cargoxunidad();
            System.out.println("11111111: " + nuevoCargo);
            ncargo.setDen_cargo(nuevoCargo);
            ncargo.setCod_cargo("C9999");
            nuevaunidadorganica.setId(unidadorganica.getId());
            ncargo.setUnidadorganica(nuevaunidadorganica);
            ncargo.setEstado(UnidadOrganica.ESTADO_ALTA);
            ncargo.setRegimenlaboral(regimenla);
            ncargo.setCtd_puestos_total(1);
            System.out.println("entroooo: ");
            session.saveOrUpdate(ncargo);
            System.out.println("2222222: ");
            cargo = (LkCargosDisponibles) session.get(LkCargosDisponibles.class,ncargo.getId());
            envelope.setContents(helpers.Constantes.CARGO_EXITO);
        } else {
            formulariotrabajadornuevo.recordError("No puede agregar un cargo vacio");

        }
        }

        return new MultiZoneUpdate("nuevoCargoZone", nuevoCargoZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
    }


    @Log
    public boolean getDNI() {
        if (nuevo.getTipodiscapacidad().equals("DNI")) {
            return true;
        }

        return false;
    }

    //para obtener datos del tipo de vinculo
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoVinculo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVINCULO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

@Log
    Object onReset() {
        unidadorganica = null;
        cargo = null;
        tipovinculo = null;
        nuevo = new Trabajador();
        fechaingreso = "";
        return trabajadorNuevoZone.getBody();
    }



    // formulario principal
    @Log
    @CommitAfter
    Object onSuccessFromFormulariotrabajadornuevo() throws ParseException {

        if (getListadoEntidades().size() > 0) {
            envelope.setContents(helpers.Constantes.EUE_EXITO);
            envelope.setContents("El trabajador ya se encuentra de alta en otra entidad.");
            bTrabajadorRegistrado = true;
            return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        } else {
            if (nuevo.getDocumentoidentidad() == null) {
                envelope.setContents("Debe ingresar el Tipo de Documento.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (nuevo.getNroDocumento() == null) {
                envelope.setContents("Debe ingresar el Nro. de Documento.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (nuevo.getNombres() == null) {
                envelope.setContents("Debe ingresar el Nombre del Trabajador.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (nuevo.getApellidoMaterno() == null) {
                envelope.setContents("Debe ingresar el Apellido Materno del Trabajador");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (nuevo.getApellidoPaterno() == null) {
                envelope.setContents("Debe ingresar el Apellido Paterno del Trabajador.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (cargo == null) {
                envelope.setContents("Debe ingresar el Cargo.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (tipovinculo == null) {
                envelope.setContents("Debe ingresar el Tipo de Vinculo.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else if (fechaingreso == null || fechaingreso.equalsIgnoreCase("")) {
                envelope.setContents("Debe ingresar la fecha de Ingreso.");
                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                        .add("mensajesZone", mensajesZone.getBody());
            } else {
                //Guardar Cargo Asignado 
                cargoAsignado = new CargoAsignado();
                    Usuario usuarionuevo=new Usuario();
                    cargo2=(Cargoxunidad)session.load(Cargoxunidad.class, cargo.getId());
                    if(getListadoTrabajadores().size()>0){
                        //Trabajador ya existe
                       nuevo=(Trabajador) session.load(Trabajador.class, getListadoTrabajadores().get(0).getId());
                       if(cargo2.getRegimenlaboral()!=null){
                         if(cargo2.getRegimenlaboral().getFlg_creausuario()==true){ 
                           if(getListadoUsuario().size()>0){
                               usuarionuevo=(Usuario) session.load(Usuario.class, getListadoUsuario().get(0).getId());
                               usuarionuevo.setEntidad(oi);
                               usuarionuevo.setEstado(1);
                               usuarionuevo.setFecha_creacion(new Date());
                           }
                           else{
                               seteanuevousuario(usuarionuevo);                               
                           }                           
                         }
                         session.saveOrUpdate(usuarionuevo);
                       }                        
                    }
                    else{
                        if(cargo2.getRegimenlaboral()!=null){
                            if(cargo2.getRegimenlaboral().getFlg_creausuario()==true){                            
                                seteanuevousuario(usuarionuevo);
                                session.saveOrUpdate(usuarionuevo);
                            }
                        }                       
                        
                    }
                    nuevo.setEntidad(oi);
                    session.saveOrUpdate(nuevo); 
                    new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevo.getId()), Logger.CODIGO_OPERACION_INSERT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TRABAJADOR);
                    //Guardar Legajo
                    nuevoLegajo = new Legajo();
                    nuevoLegajo.setEntidad(oi);
                    nuevoLegajo.setTrabajador(nuevo);
                    nuevoLegajo.setCod_legajo("L9999");
                    session.saveOrUpdate(nuevoLegajo);
                    cargoAsignado.setTrabajador(nuevo);
                    cargoAsignado.setLegajo(nuevoLegajo);
//                }
                cargoAsignado.setEstado(Constantes.ESTADO_ACTIVO);

                if (fechaingreso != null) {
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha;
                    try {
                        fecha = (Date) formatoDelTexto.parse(fechaingreso);
                        cargoAsignado.setFec_inicio(fecha);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }

                cargoAsignado.setTipovinculo(tipovinculo);
                cargoAsignado.setCargoxunidad(cargo2);
                cargoAsignado.setPuestoconfianza(puestoconfianza);
                session.saveOrUpdate(cargoAsignado);
                //creando usuario nuevo                

                envelope.setContents(helpers.Constantes.EUE_EXITO);
                new Logger().loguearOperacion(session, _usuario, String.valueOf(cargoAsignado.getId()), Logger.CODIGO_OPERACION_INSERT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO_ASIGNADO);
                envelope.setContents("Alta del trabajador se realizo satisfactoriamente.");
                return Busqueda.class;
            }
        }
    }

    void seteanuevousuario(Usuario usuarionuevo){
        usuarionuevo.setEstado(1);
        usuarionuevo.setIntentos_fallidos(0L);
        usuarionuevo.setLogin(nuevo.getNroDocumento());
        usuarionuevo.setEntidad(oi);
        usuarionuevo.setRolid(1L);
        usuarionuevo.setTrabajador(nuevo);   
        usuarionuevo.setFecha_creacion(new Date());
        usuarionuevo.setMd5Clave(Encriptacion.encriptaEnMD5(nuevo.getNroDocumento()));
    }
    
    @Log
    public List<Evento> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("nrodocumento", nuevo.getNroDocumento()));
        c.add(Restrictions.eq("estado", true));
        return c.list();
    }
    
    @Log
    public List<LkBusquedaTrabajador> getListadoTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("nrodocumento", nuevo.getNroDocumento()));
        return c.list();
    }
    
    @Log
    public List<UsuarioTrabajador> getListadoUsuario() {
        Query query = session.getNamedQuery("UsuarioTrabajador.findByLogin");
        query.setParameter("login", nuevo.getNroDocumento());
        return query.list();
    }
            
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanregimen() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
    }
        
    // evento de valor cambiados en los campos
    @Log
    void onDNIChanged() {
        nuevo.setNroDocumento(_request.getParameter("param"));
    }

    @Log
    void onNombreChanged() {
        nuevo.setNombres(_request.getParameter("param"));
    }

    @Log
    void onApePatChanged() {
        nuevo.setApellidoPaterno(_request.getParameter("param"));
    }

    @Log
    void onApeMatChanged() {
        nuevo.setApellidoMaterno(_request.getParameter("param"));
    }

    @Log
    Object onValueChangedFromBunidadorganica() {
        return _request.isXHR() ? new MultiZoneUpdate("cargosZone", cargosZone.getBody()) : null;
    }
}
