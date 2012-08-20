package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

//@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class TrabajadorNuevo extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario loggedUser;
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
    private Zone unidadOrganicaZone;
    @InjectComponent
    @Property
    private Zone nuevaUnidadZone;
    @InjectComponent
    @Property
    private Zone cargosZone;
//    @InjectComponent
//    @Property
//    private Zone nuevoCargosZone;
//    @InjectComponent
//    @Property
//    private Zone botonesZone;
    @InjectComponent
    private Zone mensajesZone;
    //Formularios
    @Component(id = "formulariotrabajadornuevo")
    private Form formulariotrabajadornuevo;
//    @Component(id = "formulariounidadorganica")
//    private Form formulariounidadorganica;
    @Component(id = "formularionuevaunidadorganica")
    private Form formularionuevaunidadorganica;
//    @Component(id = "formulariotipovinculo")
//    private Form formulariotipovinculo;
//    @Component(id = "formularionuevocargo")
//    private Form formularionuevocargo;
//    @Component(id = "formulariobotones")
//    private Form formulariobotones;
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
    private UnidadOrganica unidadorganica;
    @Property
    @Persist
    private UnidadOrganica nuevaunidadorganica;
    @Property
    @Persist
    private LkCargosDisponibles cargo;
    @Property
    @Persist
    private Cargoxunidad cargo2;
    @Property
    @Persist
    private Cargoxunidad ncargo;
    //Variables
    private List<String> tiposDoc = new ArrayList();
//    @Property
//    @Persist
//    private boolean bUOrganica;
    @Persist
    @Property
    private String nuevaUOrganica;
//    @Property
//    @Persist
//    private boolean bCargo;
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
    @PageActivationContext
//    @Persist
    private Trabajador actual;
    @Property
    @Persist
    private Boolean mostrar;

    public Trabajador getActual() {
        return actual;
    }

    public void setActual(Trabajador actual) {
        this.actual = actual;
    }

    @Log
    @SetupRender
    void initializeValue() {
//        bUOrganica = false;
//        bCargo = false;
        //nuevo=new Trabajador();
        unidadorganica = null;
        nuevaunidadorganica = new UnidadOrganica();
        cargo = new LkCargosDisponibles();
        ncargo = new Cargoxunidad();
        cargo2 = new Cargoxunidad();
        nuevaUOrganica = "";
        nuevoCargo = "";
        tipovinculo = null;
        bTrabajadorRegistrado = false;
        nuevoLegajo = new Legajo();
        mostrar = true;
        puestoconfianza = false;
        fechaingreso = "";
        if (actual != null) {
            nuevo = actual;
            buscarlegajo();
            mostrar = false;
        } else {
            nuevo = new Trabajador();
        }
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento", loggedUser.getTrabajador().getNroDocumento());
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

    @Log
    void buscarlegajo() {
        Criteria c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("trabajador", actual));
        c.add(Restrictions.eq("entidad", oi));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List result = c.list();
        nuevoLegajo = (Legajo) result.get(0);
    }

//    public List<String> getTiposDoc() {
//    	Criteria c = session.createCriteria(DatoAuxiliar.class);
//    	c.add(Restrictions.eq("nombreTabla", "DOCUMENTOIDENTIDAD"));
//        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
//    	c.setProjection(Projections.property("valor"));
//        return c.list();
//    }
    @Log
    public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        c.add(Restrictions.eq("entidad", oi));
        list = c.list();
        return new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);

    }

    @Log
    public GenericSelectModel<LkCargosDisponibles> getBeanCargo() {
        List<LkCargosDisponibles> list;
        Criteria c = session.createCriteria(LkCargosDisponibles.class);

        if (unidadorganica != null) {
            c.add(Restrictions.eq("uoid", unidadorganica.getId()));
        }
        c.add(Restrictions.eq("estado", true));
        c.add(Restrictions.eq("resultado", true));
        list = c.list();
        return new GenericSelectModel<LkCargosDisponibles>(list, LkCargosDisponibles.class, "den_cargo", "id", _access);
    }

//    void onSelectedFromAgregarUO() {
//        elemento = 5;
//    }
    @Log
    @CommitAfter
    Object onSuccessFromFormularionuevaunidadorganica() {
//        if (elemento == 5) {
        System.out.println("entroo aaa");
        System.out.println("entroo aaa" + nuevaUOrganica);
        if (nuevaUOrganica == null) {
//                System.out.println("entroo bbbb");
            //envelope.setContents("No puede agregar una Unidad Organica vacia");
            formulariotrabajadornuevo.recordError("No puede agregar una Unidad Organica vacia.");
//                elemento = 0;
//                bUOrganica = false;
//                return new MultiZoneUpdate("nuevaUnidadZone", nuevaUnidadZone.getBody())
//                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
        } else {
            System.out.println("entroo ccc");
            unidadorganica = new UnidadOrganica();
            unidadorganica.setDen_und_organica(nuevaUOrganica);
            unidadorganica.setNivel(1);
            unidadorganica.setEntidad(oi);
            unidadorganica.setEstado(UnidadOrganica.ESTADO_ALTA);
            session.saveOrUpdate(unidadorganica);
            envelope.setContents(helpers.Constantes.EUE_EXITO);
            envelope.setContents("Se creo la Unidad Organica con éxito.");
//                elemento = 0;
//                bUOrganica = false;
        }
//        } else {
//            if (bUOrganica) {
//                bUOrganica = false;
//            } else {
//                bUOrganica = true;
//            }
//        }

        return new MultiZoneUpdate("nuevaUnidadZone", nuevaUnidadZone.getBody())
                .add("trabajadorNuevoZone", trabajadorNuevoZone.getBody());
    }

//    void onSelectedFromAgregarCargo() {
//        elemento = 6;
//    }
//    @Log
//    @CommitAfter
//    Object onSuccessFromFormulariounidadorganica() {
//        return new MultiZoneUpdate("nuevaUnidadOrganicaZone", nuevaUnidadOrganicaZone.getBody())
//                .add("cargosZone", cargosZone.getBody());
//    }
//    @Log
//    Object onActionFromNuevaUnidadOrganica() {
//        bUOrganica = true;
//        bCargo = false;
//        return nuevaUnidadCargoZone.getBody();
//    }
    @Log
    @CommitAfter
    Object onSuccessFromFormularionuevocargo() {
//        if (elemento == 6) {
        if (nuevoCargo != null) {
            ncargo = new Cargoxunidad();
            ncargo.setDen_cargo(nuevoCargo);
            ncargo.setCod_cargo("C9999");
            ncargo.setUnidadorganica(unidadorganica);
            ncargo.setEstado(UnidadOrganica.ESTADO_ALTA);
            session.saveOrUpdate(ncargo);

            envelope.setContents(helpers.Constantes.EUE_EXITO);
            envelope.setContents("Se creo el Cargo con éxito.");
            elemento = 0;
//            bCargo = false;
        } else {
            envelope.setContents("No puede agregar un cargo vacio");
            elemento = 0;
//            bCargo = false;
        }
//        } else {
//            if (bCargo) {
//                bCargo = false;
//            } else {
//                bCargo = true;
//            }
//        }

        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody())
                .add("cargosZone", cargosZone.getBody());

    }

//    @Log
//    void onPrepareFromformulariotrabajadornuevo() {
//        if (nuevo == null) {
//            nuevo = new Trabajador();
//        }
//        System.out.println("entro y gravo " + fechaingreso);
//        System.out.println("entro y gravo2 " + puestoconfianza);
//        //trabajadorTieneCargoOtraEntidad = false;
//    }
//    @Log
//    @CommitAfter
//    Object onSuccessFromformulariotrabajadornuevo() {
//        System.out.println("entro y gravo " + fechaingreso);
//        System.out.println("entro y gravo2 " + puestoconfianza);
//        return trabajadorNuevoZone.getBody();
//    }
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
    void onSelectedFromCancel() {
        elemento = 2;
    }

    @Log
    void onSelectedFromReset() {

        //===13 agosto
        elemento = 1;
//        bUOrganica = false;
//        bCargo = false;
        unidadorganica = null;
        cargo = null;
        tipovinculo = null;
        nuevo = new Trabajador();
        fechaingreso = "";


    }

//    @Property
//    @InjectComponent
//    private Zone tipovinculoZone;
    // TO-DO FALTA IMPLEMENTAR
//    @Log
//    Object onSuccessFromFormularioBotones() {
//        if (elemento == 1) {
//            //   return "TrabajadorNuevo";
//            return new MultiZoneUpdate("trabajadorNuevoZone", trabajadorNuevoZone.getBody())
//                    //                .add("tipoVinculoZone", tipovinculoZone).add("cargosZone",cargosZone)
//                    .add("unidadOrganicaZone", unidadOrganicaZone);
//        } else {
//            return "Alerta";
//        }
//    }
    //===============================
    @Log
    @CommitAfter
    Object onSuccessFromFormulariotipovinculo() throws ParseException {

        //       if(elemento==1){
        //           return "TrabajadorNuevo";
        //       }else if(elemento==2){
        //           return "Alerta";
        //       }else{
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
            } else if (unidadorganica == null) {
                envelope.setContents("Debe ingresar la Unidad Organiza.");
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
                if (actual != null) {
                    cargoAsignado.setTrabajador(nuevo);
                    cargoAsignado.setLegajo(nuevoLegajo);
                } else {
                    //Guardar Trabajador
                    nuevo.setEntidad(oi);
                    session.saveOrUpdate(nuevo);
                    //Guardar Legajo
                    nuevoLegajo = new Legajo();
                    nuevoLegajo.setEntidad(oi);
                    nuevoLegajo.setTrabajador(nuevo);
                    nuevoLegajo.setCod_legajo("L9999");
                    session.saveOrUpdate(nuevoLegajo);
                    cargoAsignado.setTrabajador(nuevo);
                    cargoAsignado.setLegajo(nuevoLegajo);
                }
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

                //cargoAsignado.setFec_inicio(fechaingreso);
                cargoAsignado.setTipovinculo(tipovinculo);
                cargo2.setId(cargo.getId());
                cargoAsignado.setCargoxunidad(cargo2);
                cargoAsignado.setPuestoconfianza(puestoconfianza);
                session.saveOrUpdate(cargoAsignado);
                envelope.setContents(helpers.Constantes.EUE_EXITO);
                envelope.setContents("Alta del trabajador se realizo satisfactoriamente.");
                actual = null;
                return Busqueda.class;
            }
        }
//        }        
    }

    @Log
    public List<Evento> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("nrodocumento", nuevo.getNroDocumento()));
        c.add(Restrictions.eq("estado", true));
        return c.list();
    }

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
    Object onValueChangedFromunidadorganica() {
//        if (dato != null && !dato.equals("")) {
//            if (dato.getValor().equals("NO TIENE") || dato.getValor().equals("")) {
//                vconadis = true;
//                actual.setNroCertificadoCONADIS(null);
//            } else {
//                vconadis = false;
//            }
//        } else {
//            vconadis = true;
//        }
        return _request.isXHR() ? new MultiZoneUpdate("cargosZone", cargosZone.getBody()) : null;
    }
}
