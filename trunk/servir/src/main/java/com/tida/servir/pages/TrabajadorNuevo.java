package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.*;
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
    private Zone datosPersonalesZone;
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

    @InjectComponent
    private Zone zoneApellidos;
    //Formularios
    @Component(id = "formulariotrabajadornuevo")
    private Form formulariotrabajadornuevo;
    @Component(id = "formularionuevaunidadorganica")
    private Form formularionuevaunidadorganica;
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    @Component(id = "formularioDatos")
    private Form formularioDatos;
    @Component(id = "formularionuevocargo")
    private Form formularionuevocargo;
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
    private String codigoUOrganica;
    @Persist
    @Property
    private String nuevoCargo;
    @Persist
    @Property
    private String codigoCargo;
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
    @Property
    @Persist
    private String fechacaducidad;
    @Property
    @Persist
    private boolean disabledFechaCaducidad;
    @Property
    @Persist
    private boolean disabledZoneApellidos;
    @Property
    @Persist
    private boolean mostrarUO;
    @Property
    @Persist
    private boolean mostrarCargo;
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo() {
      //  new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ALTA_TRABAJADOR);
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
        codigoUOrganica="";
        nuevoCargo = "";
        codigoCargo="";
        tipovinculo = null;
        bTrabajadorRegistrado = false;
        nuevoLegajo = new Legajo();
        mostrar = true;
        creaNuevaUnidad = false;
        creaNuevoCargo = false;
        puestoconfianza = false;
        fechaingreso = "";
        fechacaducidad = "";
        disabledFechaCaducidad = true;
        disabledZoneApellidos = false;
        nuevo = new Trabajador();
        nuevo.setConsultado_WS(false);
        regimenla = null;
        formulariomensajes.clearErrors();
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
        mostrarUO=UO();
        mostrarCargo=Cargoss();
    }
    
    boolean UO(){
        boolean retu=false;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", usuarioTrabajador.getLogin());
        query.setParameter("in_pagename", "AMUNIDADORGANICA");
        List result = query.list();
        if (!result.isEmpty()) {
            UsuarioAcceso usuasss = (UsuarioAcceso) result.get(0);
            if(usuasss.getAccesoreport()==1){
                retu=true;
            }            
        }
        return retu;
    }
    
    boolean Cargoss(){
        boolean retu=false;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", usuarioTrabajador.getLogin());
        query.setParameter("in_pagename", "ABMCARGOS");
        List result = query.list();
        if (!result.isEmpty()) {
            UsuarioAcceso usuasss = (UsuarioAcceso) result.get(0);
            if(usuasss.getAccesoreport()==1){
                retu=true;
            }
        }
        return retu;
    }
    
    // cargar combos
    @Log
    public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
  //      List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        List<Long> lista = new ArrayList<Long>();
        lista.add(new Long(1));lista.add(new Long(2));
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla","DOCUMENTOIDENTIDAD"));
        c.add(Restrictions.in("codigo", lista));
        return new GenericSelectModel<DatoAuxiliar>(c.list(), DatoAuxiliar.class, "valor", "id", _access);
//        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
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
        formularionuevaunidadorganica.clearErrors();
        System.out.println("entroo aaa" + nuevaUOrganica);
        Criteria c= session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("cod_und_organica", codigoUOrganica).ignoreCase()));
        c.add(Restrictions.eq("entidad",oi));
        if (c.list().size() > 0){
             formularionuevaunidadorganica.recordError("Codigo de unidad organica ya Existente");
             return new MultiZoneUpdate("nuevaUnidadZone", nuevaUnidadZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
        }
        System.out.println("entroo ccc");
        nuevaunidadorganica = new UnidadOrganica();
        nuevaunidadorganica.setDen_und_organica(nuevaUOrganica);
        nuevaunidadorganica.setNivel(1);
        nuevaunidadorganica.setEntidad(oi);
        nuevaunidadorganica.setEstado(UnidadOrganica.ESTADO_ALTA);
        nuevaunidadorganica.setCod_und_organica(codigoUOrganica);
        session.saveOrUpdate(nuevaunidadorganica);
        codigoUOrganica=null;
        nuevaUOrganica=null;
        return new MultiZoneUpdate("nuevaUnidadZone", nuevaUnidadZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
    }

    // formulario de creacion de nuevo cargo
    @Log
    @CommitAfter
    Object onSuccessFromFormularionuevocargo() {
        if (unidadorganica == null) {
            formularionuevocargo.recordError("Debe seleccionar una Unidad Organica.");
        } else {
            Criteria c= session.createCriteria(Cargoxunidad.class);
            c.add(Restrictions.disjunction().add(Restrictions.like("cod_cargo", codigoCargo).ignoreCase()));
            c.createAlias("unidadorganica", "unidadorganica");
            c.add(Restrictions.eq("unidadorganica.entidad", oi ));
            if (c.list().size() > 0){
             formularionuevocargo.recordError("Codigo de Cargo ya Existente");
             return new MultiZoneUpdate("nuevoCargoZone", nuevoCargoZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
            }
            Cargoxunidad ncargo;
            ncargo = new Cargoxunidad();
//            System.out.println("11111111: " + nuevoCargo);
            ncargo.setDen_cargo(nuevoCargo);
            ncargo.setCod_cargo(codigoCargo);
            nuevaunidadorganica.setId(unidadorganica.getId());
            ncargo.setUnidadorganica(nuevaunidadorganica);
            ncargo.setEstado(UnidadOrganica.ESTADO_ALTA);
            ncargo.setRegimenlaboral(regimenla);
            ncargo.setCtd_puestos_total(1);
            session.saveOrUpdate(ncargo);
            cargo = (LkCargosDisponibles) session.get(LkCargosDisponibles.class, ncargo.getId());       
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
        formulariomensajes.clearErrors();
        unidadorganica = null;
        cargo = null;
        tipovinculo = null;
        nuevo = new Trabajador();
        nuevo.setConsultado_WS(false);
        fechaingreso = "";
        fechacaducidad = "";
        disabledFechaCaducidad = true;
        disabledZoneApellidos = false;
//        return trabajadorNuevoZone.getBody();
        return new MultiZoneUpdate("trabajadorNuevoZone", trabajadorNuevoZone.getBody()).add("datosPersonalesZone", datosPersonalesZone.getBody()).add("mensajesZone", mensajesZone.getBody());
    }

    // formulario principal
    @Log
    @CommitAfter
    Object onSuccessFromFormulariotrabajadornuevo() throws ParseException {
       Boolean asignarPerfil = Boolean.FALSE;
        if (getListadoEntidades().size() > 0) {
            envelope.setContents(helpers.Constantes.EUE_EXITO);
            envelope.setContents("El trabajador ya se encuentra de alta en otra entidad.");
            bTrabajadorRegistrado = true;
            return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody()).add("mensajesZone", mensajesZone.getBody());
        } else {
            
        Criteria c2 = session.createCriteria(LkBusquedaTrabajador.class);
        c2.add(Restrictions.eq("nrodocumento", nuevo.getNroDocumento()));
        c2.add(Restrictions.eq("estado", false));
        
//        if(c2.list().isEmpty()){
//            if (getFechaAntiguoCargo()== Boolean.FALSE){
//            formulariomensajes.recordError("La fecha de ingreso debe ser mayor a la fecha de finalizacion del cargo anterior.");
//            return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody()).add("mensajesZone", mensajesZone.getBody());            }
//        }
        
                // VALIDACION DE LA EXISTENCIA DEL TRABAJADOR CON DISTINTO TIPO DE DOCUMENTO
                if (nuevo.getDocumentoidentidad().getCodigo()!=1){
                Criteria c = session.createCriteria(Trabajador.class);
                c.add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()));
                if (!c.list().isEmpty()){
                    Trabajador temp = (Trabajador)c.uniqueResult();
                    if (!temp.getApellidoMaterno().equals(nuevo.getApellidoMaterno())||!temp.getApellidoPaterno().equals(nuevo.getApellidoPaterno())||!temp.getNombres().equals(nuevo.getNombres())){
                    formulariomensajes.recordError("Datos del Trabajador incorrecto");
                    
                    }
                    else{nuevo = temp;}
                    
                }
                }
                if (fechaingreso != null) {
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha;
                    try {
                        fecha = (Date) formatoDelTexto.parse(fechaingreso);
                        if (getListadoAutoridadSan().size()>0) {
                            if (fecha.before(getListadoAutoridadSan().get(0).getFec_fin()) || fecha.equals(getListadoAutoridadSan().get(0).getFec_fin())){
                                envelope.setContents("La fecha de inicio en el cargo debe ser diferente y mayor a la fecha de finalizacion del ultimo cargo");
                                return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody()).add("mensajesZone", mensajesZone.getBody());
                            }else{

                            }
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                
                
                
                
                //Guardar Cargo Asignado 
                cargoAsignado = new CargoAsignado();
                Usuario usuarionuevo = new Usuario();
                cargo2 = (Cargoxunidad) session.load(Cargoxunidad.class, cargo.getId());
                if (getListadoTrabajadores().size() > 0) {
                    
                    //Trabajador ya existe
                    nuevo = (Trabajador) session.load(Trabajador.class, getListadoTrabajadores().get(0).getId());
                    
                    if (cargo2.getRegimenlaboral() != null) {
                        if (cargo2.getRegimenlaboral().getFlg_creausuario() == true) {
                                                        asignarPerfil = Boolean.TRUE;
                            if (getListadoUsuario(nuevo.getId()).size() > 0) {
                                //usuario ya existe                                
                                usuarionuevo = (Usuario) session.load(Usuario.class, getListadoUsuario(nuevo.getId()).get(0).getId());
                                usuarionuevo.setEntidad(oi);
                                usuarionuevo.setEstado(1);
                                usuarionuevo.setFecha_creacion(new Date());
                            } else {
                                //usuario nuevo                                
                                seteanuevousuario(usuarionuevo);
                            }
                            session.saveOrUpdate(usuarionuevo);
                        }                        
                    }
                } else {
                    
                    //trabajador no existe
                    //trabajador tiene usuario
                    if (getListadoUsuariodos().size() > 0) {
                        //eliminar usuario                        
                        Usuario usuarioeliminar = (Usuario) session.load(Usuario.class, getListadoUsuariodos().get(0).getId());
                        //eliminar perfiles del usuario
                        if(getListadoPerfil().size() > 0){
                            for(int i=0;i<getListadoPerfil().size();i++){                            
                                Perfilporusuario perfileliminar = (Perfilporusuario) session.load(Perfilporusuario.class, getListadoPerfil().get(i).getId());
                                session.delete(perfileliminar);
                            }
                        }    
                        session.delete(usuarioeliminar);
                    }
                    if (cargo2.getRegimenlaboral() != null) {
                        if (cargo2.getRegimenlaboral().getFlg_creausuario() == true) {
                                                        asignarPerfil = Boolean.TRUE;
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

                if (cargoAsignado.getFec_inicio().after(new Date())){
                    formulariomensajes.recordError("La fecha de inicio debe ser menor a la fecha del sistema");
                 return new MultiZoneUpdate("trabajadorNuevoZone",trabajadorNuevoZone.getBody()).add("mensajesZone",mensajesZone.getBody());
                }
                
                cargoAsignado.setTipovinculo(tipovinculo);
                cargoAsignado.setCargoxunidad(cargo2);
                cargoAsignado.setPuestoconfianza(puestoconfianza);
                session.saveOrUpdate(cargoAsignado);
                //creando usuario nuevo                

                                if (asignarPerfil){
                            System.out.println("TRABAJADORX1");                            
                            long num = Long.parseLong("6");                            
                            Criteria c = session.createCriteria(Perfil.class);
                            c.add(Restrictions.eq("id", num));
                            Perfil perfilTrabajador = (Perfil)c.uniqueResult();
                            System.out.println("PERFIL ENCONTRADO");                            
                            PerfilusuarioPK perfilPk = new PerfilusuarioPK(usuarionuevo.getId(),perfilTrabajador.getId());
                            Perfilusuario perfilAsignado = new Perfilusuario(perfilPk);
                            perfilAsignado.setFechacreacion(new Date());
                            session.saveOrUpdate(perfilAsignado);
                            System.out.println("USUARIO Y PERFIL CREADO");
                }
                                
                envelope.setContents(helpers.Constantes.EUE_EXITO);
                new Logger().loguearOperacion(session, _usuario, String.valueOf(cargoAsignado.getId()), Logger.CODIGO_OPERACION_INSERT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO_ASIGNADO);
                if(getSancionados().size()>0){
                    envelope.setContents("El Trabajador registrado tiene Sanciones");
                    onReset();
                    return new MultiZoneUpdate("trabajadorNuevoZone", trabajadorNuevoZone.getBody()).add("datosPersonalesZone", datosPersonalesZone.getBody()).add("mensajesZone", mensajesZone.getBody());
                }
                envelope.setContents("Alta del trabajador se realizo satisfactoriamente.");
                return Busqueda.class;
            //}
        //return new MultiZoneUpdate("trabajadorNuevoZone", trabajadorNuevoZone.getBody()).add("datosPersonalesZone", datosPersonalesZone.getBody()).add("mensajesZone", mensajesZone.getBody());            
        }
    }

    void seteanuevousuario(Usuario usuarionuevo) {
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
    public List<LkBusquedaTrabajadorAuto> getListadoTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajadorAuto.class);
        c.add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()));
        return c.list();
    }

    @Log
    public List<UsuarioTrabajador> getListadoUsuario(long idtrabajador) {
        Query query = session.getNamedQuery("UsuarioTrabajador.findByTrabajador");
        query.setParameter("trabajadorid", idtrabajador);
        return query.list();
    }
    @Log
    public List<LkBusquedaSancionadosSinRegLab> getSancionados() {
        Criteria c = session.createCriteria(LkBusquedaSancionadosSinRegLab.class);
        c.add(Restrictions.eq("nro_doc_trabajador", nuevo.getNroDocumento()));
        c.add(Restrictions.eq("estado", "VIGENTE"));
        return c.list();
    }
    
    @Log
    public List<UsuarioTrabajador> getListadoUsuariodos() {
        Query query = session.getNamedQuery("UsuarioTrabajador.findByNrodocumentousuario");
        query.setParameter("nrodocumentousuario", nuevo.getNroDocumento());
        return query.list();
    }
    
    @Log
    public List<UsuarioTrabajador> getListadoPerfil() {
        Query query = session.getNamedQuery("UsuarioTrabajador.findByNrodocumentousuario");
        query.setParameter("nrodocumentousuario", nuevo.getNroDocumento());
        return query.list();
    }
    
    @Log
    public List<LkBusquedaTrabajadorSan> getListadoAutoridadSan() {
        List<LkBusquedaTrabajadorSan> lista = null;
        Criteria c = session.createCriteria(LkBusquedaTrabajadorSan.class);
        c.add(Restrictions.eq("nrodocumento", nuevo.getNroDocumento()));
        c.add(Restrictions.eq("estadocargo", "INACTIVO").ignoreCase());
        c.addOrder(org.hibernate.criterion.Order.desc("fec_fin"));
        return c.list();
    }
    
    @Log
    public List<Perfilporusuario> getAllPerfiles(long usuid) {
        List<Perfilporusuario> lista = null;
        Criteria c = session.createCriteria(Perfilporusuario.class);
        c.add(Restrictions.eq("usuarioId", usuid));
        return c.list();
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanregimen() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
    }

    // evento de valor cambiados en los campos


    @Log
    void onNombreChanged() {
        nuevo.setNombres(_request.getParameter("param"));
    }

    @Log
    void onApePatChanged() {
        nuevo.setApellidoPaterno(_request.getParameter("param"));
    }
    @Log
    void onNroDocChanged() {
        nuevo.setNroDocumento(_request.getParameter("param"));
    }

    @Log
    void onApeMatChanged() {
        nuevo.setApellidoMaterno(_request.getParameter("param"));
    }

    @Log
    Object onValueChangedFromBunidadorganica() {
        return _request.isXHR() ? new MultiZoneUpdate("cargosZone", cargosZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromTipoDocumento(DatoAuxiliar dato) {

        if (dato==null||dato.getCodigo() != 1){
            disabledFechaCaducidad = true;
            disabledZoneApellidos = false;        
        }
        else{
            disabledFechaCaducidad = false;
            disabledZoneApellidos = true;            
        }
        
        return _request.isXHR() ? new MultiZoneUpdate("datosPersonalesZone", datosPersonalesZone.getBody()) : null;
    }
    
 //   @Log
 //   Object onFindReniec() {
       
    /*    try {
            ServicioReniec treniec = new ServicioReniec();
            treniec.obtenerToken();
            if (treniec.validarToken()==true){
                System.out.println("BUSCAR DNI");
                //  Buscamos DNI
                List<String> result = treniec.obtenerResultado("09399938");
                if (treniec.validarEstadoConsulta(result.get(0))==true){
                    treniec.cargarTrabajador(result);                            
                }
                else{
                    envelope.setContents(treniec.mensajeError);//ERROR EN CONSULTA
                System.out.println(treniec.mensajeError);
                }                
            }else{
                envelope.setContents(treniec.mensajeError);//ERROR TOKEN
                System.out.println(treniec.mensajeError);
            }

        } catch (Exception ex) {
            System.out.println(ex.getCause());
        }*/
  //      return _request.isXHR() ? new MultiZoneUpdate("zoneApellidos", zoneApellidos.getBody()) : null;
 //   }
    @Log
    @CommitAfter
    public Object onSuccessFromformulariodatos(){
    formulariomensajes.clearErrors();
    DatoAuxiliar temporalTipoDNI = nuevo.getDocumentoidentidad();
    // VALIDACION DEL DOCUMENTO DE IDENTIDAD
        if (temporalTipoDNI.getCodigo()==1){
            if(nuevo.getNroDocumento().length()>8){ 
                formulariomensajes.recordError("El número de documento debe tener 8 dígitos (y solo números)");   return actualizarZonas();}
            try { Integer.parseInt(nuevo.getNroDocumento());} catch (NumberFormatException ex) {
                formulariomensajes.recordError("El número de documento debe tener 8 dígitos (y solo números)"); return actualizarZonas();}            
        }

    
    // BUSCAMOS EN LA DB
    Criteria c = session.createCriteria(Trabajador.class);
    c.add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()));
    if (!c.list().isEmpty()){
    //VERFICACION DE LA EXISTENCIA - ASIGNAMOS AL ACTUAL
    nuevo = (Trabajador)c.uniqueResult(); //*************
    }
    else{
    System.out.println(_usuario.getEntidad().getDenominacion()+" "+_usuario.getEntidad().getPeticiones_ws_Reniec());

   // VERIFICACION DE LOS PARAMETROS CON RESPECTO AL NRO DE PETICIONES TOTALES
        c = session.createCriteria(ConfiguracionAcceso.class);
       ConfiguracionAcceso parametro =  (ConfiguracionAcceso)c.uniqueResult();
       System.out.println("NRO CONSULTAS - EN TOTAL "+parametro.getNroConsultasActuales());
       if (parametro.getNroConsultasActuales()==null ||parametro.getNroConsultasActuales()==0){
            formulariomensajes.recordError("Se superaron el # de consultas al service por el dia de hoy");
            return actualizarZonas();        
       }
       
    // VERIFICACION DE LOS PARAMETROS CON RESPECTO AL NRO DE PETICIONES (ENTIDAD)        
       System.out.println("NRO CONSULTAS - PARA LA ENTIDAD "+_usuario.getEntidad().getPeticiones_ws_Reniec());
       if (_usuario.getEntidad().getPeticiones_ws_Reniec()==null || _usuario.getEntidad().getPeticiones_ws_Reniec()== 0){ 
            formulariomensajes.recordError("Se superaron el # de consultas al service para la entidad por el dia de hoy");
            return actualizarZonas();            
        }
        


    // BUSQUEDA EN EL WEB SERVICE
        try {
            ServicioReniec treniec = new ServicioReniec();
            treniec.obtenerToken();
            if (treniec.validarToken(session)==true){
                  System.out.println("BUSCAR DNI");
                  //  Buscamos DNI
                  List<String> result = treniec.obtenerResultado(nuevo.getNroDocumento());
                  
                      // DISMINUCION DE NRO DE PETICIONES
                      _usuario.getEntidad().setPeticiones_ws_Reniec(_usuario.getEntidad().getPeticiones_ws_Reniec()-1);
                      session.saveOrUpdate(_usuario.getEntidad());                    
                      parametro.setNroConsultasActuales(parametro.getNroConsultasActuales()-1);
                      session.saveOrUpdate(parametro);

                  if (treniec.validarEstadoConsulta(result.get(0),session)==true){                      
                  // VALIDACIONES PRE CARGA DE LA ENTIDAD 
                        Date fechaInicial,fechaWS;
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); 
                        fechaInicial = formatoFecha.parse(fechacaducidad);
                        System.out.println("EXC "+result.get(1)+result.get(18)+result.get(14));
                        
                        if (!result.get(18).equals("")){
                            // validacion si la persona no tiene fecha de caducidad (si ha fallecido la persona)
                            formatoFecha = new SimpleDateFormat("yyyyMMdd");  
                            fechaWS = formatoFecha.parse(result.get(18));
                            System.out.println("EXC "+fechaInicial);
                            System.out.println("EXC "+fechaWS);
                        
                            if (!fechaInicial.equals(fechaWS)){
                                formulariomensajes.recordError("Fecha de Caducidad Incorrecta"); // MENSAJES DE ERROR                         
                                return actualizarZonas();
                            }
                        }
                        else{
                             formulariomensajes.recordError("No se puede registrar a una persona que ha fallecido"); // MENSAJES DE ERROR                         
                             return actualizarZonas();                            
                        }

                  //ASIGNACION DEL USUARIO DEL WS A ENTIDAD
                      treniec.cargarTrabajador(result,session);
                      nuevo = treniec.getTrabajadorWS(); //****************
                      nuevo.setDocumentoidentidad(temporalTipoDNI);
                      nuevo.setPais(getPais(193).get(0));

                  }
                  else{
                      formulariomensajes.recordError(treniec.mensajeError); // MENSAJES DE ERROR EN CONSULTA
                      System.out.println(treniec.mensajeError);
                      return actualizarZonas();
                  }                
            }
            else{
                formulariomensajes.recordError(treniec.mensajeError); // MENSAJES DE ERROR TOKEN 
                System.out.println(treniec.mensajeError);
                return actualizarZonas();
            }

          } catch (Exception ex) {
              System.out.println(ex.toString());
          }        
    }
    
    return actualizarZonas();
    }
    
    @Log
    public List<DatoAuxiliar> getPais(long cod) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "PAISES"));
        c.add(Restrictions.eq("codigo", cod));
        return c.list();
    }
    
    @Log
    private MultiZoneUpdate actualizarZonas(){

        return new MultiZoneUpdate("datosPersonalesZone", datosPersonalesZone.getBody()).add("mensajesZone", mensajesZone.getBody());
    }
    
//        public Boolean getFechaAntiguoCargo(){
//        Criteria c = session.createCriteria(CargoAsignado.class);
//        c.add(Restrictions.eq("trabajador", nuevo));
//        c.add(Restrictions.eq("estado", false));
//        if (c.list().isEmpty()){return Boolean.TRUE;}
//        c.setProjection(Projections.max("fec_fin"));
//        Date fecha_fin = (Date)c.uniqueResult();
//        
//        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
//        Date fecha1 = new Date();
//                    try {
//                        fecha1 = (Date) formatoDelTexto.parse(fechaingreso);
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//            
//           System.out.println("FECHAX2 "+fecha1+" - "+fecha_fin);         
//                
//           if (fecha1.after(fecha_fin)){
//               System.out.println("FECHAXX TRUE");
//           return Boolean.TRUE;           
//           }else{
//           return Boolean.FALSE;
//           }
//    }
        
        
}
