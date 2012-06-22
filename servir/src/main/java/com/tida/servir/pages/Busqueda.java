package com.tida.servir.pages;

import annotations.XHR;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import helpers.Constantes;

import helpers.Helpers;
import com.tida.servir.services.SelectIdModelFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;

import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;

import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Start page of application servir.
 */
//@IncludeJavaScriptLibrary("context:layout/custom.js")
public class Busqueda extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
    @Property
    private String no_implementado;
    @Inject
    private PropertyAccess _access;
    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
    @Property
    @SessionState
    private Entidad_BK _entidadUE;
    @Property
    @SessionState
    private Usuario _usuario;
    @InjectPage
    private TrabajadorNuevo trabajadorNuevo;

    public List<String> getSexos() {
        return Helpers.getValorTablaAuxiliar("Sexo", session);
    }

    public List<String> getDiscapacidades() {
        return Helpers.getValorTablaAuxiliar("TipoDiscapacidad", session);
    }

    public List<String> getNivelesInstruccion() {
        return Helpers.getValorTablaAuxiliar("Nivel Instrucción", session);
    }

    public List<String> getEstado() {
        List<String> estadosCargo = new LinkedList<String>();
        /*
         * TODO JZM verificar linea de codigo
         */
//        estadosCargo.add(Cargoxunidad.ESTADO_ALTA);
//        estadosCargo.add(Cargoxunidad.ESTADO_BAJA);
        
        estadosCargo.add("Alta");
        estadosCargo.add("Baja");
        
        return estadosCargo;
    }

    public GenericSelectModel<DatoAuxiliar> getRegimenContratacion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("RegimenLaboralContractual", null, 0, session);

        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);

    }


    public GenericSelectModel<DatoAuxiliar> getCodFunCargo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);

        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);

    }
    @Component(id = "formulariobusquedasfiltros")
    private Form formulariobusquedasfiltros;

    public List<Trabajador> getEmpleados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.createAlias("legajo", "legajo");
        c.createAlias("cargo", "cargo");

        c.add(Restrictions.eq("legajo.entidadUE", _entidadUE));

        // busquedasI
        if (apellidoPaterno != null && !apellidoPaterno.equals("")) {

            c.add(Restrictions.disjunction().add(Restrictions.like("trabajador.apellidoPaterno", apellidoPaterno + "%").ignoreCase()).add(Restrictions.like("trabajador.apellidoPaterno", apellidoPaterno.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("trabajador.apellidoPaterno", apellidoPaterno.replaceAll("n", "ñ") + "%").ignoreCase()));

        }
        if (apellidoMaterno != null && !apellidoMaterno.equals("")) {

            c.add(Restrictions.disjunction().add(Restrictions.like("trabajador.apellidoMaterno", apellidoMaterno + "%").ignoreCase()).add(Restrictions.like("trabajador.apellidoMaterno", apellidoMaterno.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("trabajador.apellidoMaterno", apellidoMaterno.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (nombres != null && !nombres.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("trabajador.nombres", nombres + "%").ignoreCase()).add(Restrictions.like("trabajador.nombres", nombres.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("trabajador.nombres", nombres.replaceAll("n", "ñ") + "%").ignoreCase()));
        }

        if (nroDocumento != null) {
            System.out.println("------------------ empleados nroDocumento " + nroDocumento);
            c.add(Restrictions.eq("trabajador.nroDocumento", nroDocumento));
        }
        if (tipoDocumento != null && !tipoDocumento.equals("")) {
            c.add(Restrictions.like("trabajador.tipoDocumento", tipoDocumento));
        }

        if (codigoLegajoInstitucion != null && !codigoLegajoInstitucion.equals("")) {
            c.add(Restrictions.eq("legajo.cod_legajo", codigoLegajoInstitucion));
        }
        // filtros
        if (sexo != null && !sexo.equals("")) {

            c.add(Restrictions.like("trabajador.sexo", sexo));
        }

        if (fechadenacimientomenora != null && checkfechadenacimientomenora) {
            fechadenacimientomenora.setHours(23);
            fechadenacimientomenora.setMinutes(59);
            fechadenacimientomenora.setSeconds(59);
            c.add(Restrictions.le("trabajador.fechaNacimiento", fechadenacimientomenora));

        }
        if (fechadenacimientomayora != null && checkfechadenacimientomayora) {
            c.add(Restrictions.ge("trabajador.fechaNacimiento", fechadenacimientomayora));
        }

        if (valTipoDiscapacidad != null && !valTipoDiscapacidad.equals("")) {
            c.add(Restrictions.like("trabajador.tipoDiscapacidad", valTipoDiscapacidad));
        }

        if (valNivelInstruccion != null && !valNivelInstruccion.equals("")) {
            c.add(Restrictions.like("trabajador.nivelInstruccion", valNivelInstruccion));
        }


        if (valRegimenContratacion != null) {
            c.add(Restrictions.eq("cargo.reg_lab_con", valRegimenContratacion));
        }


        if (valhorassemanalesmayora != null) {
            c.add(Restrictions.ge("cargo.horas_x_sem", valhorassemanalesmayora));
        }

        if (valhorassemanalesmenora != null) {
            c.add(Restrictions.le("cargo.horas_x_sem", valhorassemanalesmenora));
        }


        if (valcodigofuncionaldelcargo != null) {
            c.add(Restrictions.eq("cargo.clasificacion_funcional", valcodigofuncionaldelcargo));
        }

        if (valhabilitacionprofesional != null && checkhabilitacionprofesional) {
            c.add(Restrictions.eq("cargo.req_hab_profesional", valhabilitacionprofesional));
        }

        if (valdeclaracion != null && checkdeclaracion) {
            c.add(Restrictions.eq("cargo.dec_jurada_byr", valdeclaracion));
        }
        if (valconfianza != null && checkconfianza) {
            c.add(Restrictions.eq("cargo.cargo_confianza", valconfianza));
        }

        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        c.setProjection(Projections.distinct(Projections.property("trabajador")));
        Trabajador trabajador = new Trabajador();
        //System.out.println("-------------------- trabajador "+trabajador.getNroDocumento());
        return c.list();
    }
    private boolean resetBusquedas = false;

    void onSelectedFromBorrarBusquedas() {
        resetBusquedas = true;
        //reseteo el formulario

    }

    Busqueda onActionFromToggle_filtros() {
        if (mostrarFiltros) {
            mostrarFiltros = false;
            mostrarEsconder = "Mostrar";
        } else {
            mostrarFiltros = true;
            mostrarEsconder = "Oscultar";
        }
        return this;
    }

    public Object onSuccess() {
        if (resetBusquedas) {

            apellidoPaterno = null;
            apellidoMaterno = null;
            nombres = null;
            nroDocumento = null;
            tipoDocumento = null;
            codigoLegajoInstitucion = null;
            sexo = null;
            fechadenacimientomenora = null;
            fechadenacimientomayora = null;
            valTipoDiscapacidad = null;
            valNivelInstruccion = null;
            valcodigofuncionaldelcargo = null;
            valRegimenContratacion = null;
            valhorassemanalesmayora = null;
            valhorassemanalesmenora = null;
            checkfechadenacimientomayora = false;
            checkfechadenacimientomenora = false;
            checkhabilitacionprofesional = false;
            checkdeclaracion = false;
            checkconfianza = false;
            valhabilitacionprofesional = false;
            valdeclaracion = false;
            valconfianza = false;
        }
        formulariobusquedasfiltros.clearErrors();
        if (resetBusquedas) {
            resetBusquedas = false;
            //return getTodasZones(); // actualizo toda la pantalla
            return Busqueda.class;
        } else {
            return getEmpleadosZones(); // actualizo los trabajadores
        }
    }

    @Inject
	private SelectIdModelFactory selectIdModelFactory;
    
    @Property
	@SuppressWarnings("unused")
	private SelectModel tipoDocumentosIdModel;
    
    void onPrepareForRender() {
		// Get all persons - ask business service to find them (from the database)
		List<DatoAuxiliar> datoAuxiliar = Helpers.getValoresTablaAuxiliar("TipoDocumento", session);

		tipoDocumentosIdModel = selectIdModelFactory.create(datoAuxiliar, "valor", "id");
	}
    
    @CommitAfter
    void onPrepare() {
        
    }
    @Persist
    @Property
    private Trabajador e;
    @Persist
    @Property
    private Cargoxunidad c;
    @Persist
    @Property
    private boolean mostrarFiltros;
    @Persist
    @Property
    private String mostrarEsconder;
    @Persist
    @Property
    private boolean yaEntrada;
    //
    // Datos para la busqueda
    //
    @Persist
    @Property
    private String apellidoPaterno;
    @Persist
    @Property
    private String apellidoMaterno;
    @Persist
    @Property
    private String nombres;
    @Persist
    @Property
    private String nroDocumento;
    @Persist
    @Property
    private String tipoDocumento;
    @Persist
    @Property
    private String codigoLegajoInstitucion;
    //
    // Datos para los filtros
    //
    @Persist
    @Property
    private String sexo;
    @Persist
    @Property
    private Boolean checkfechadenacimientomayora;
    @Persist
    @Property
    private Boolean checkfechadenacimientomenora;
    @Persist
    @Property
    private Boolean checkhabilitacionprofesional;
    @Persist
    @Property
    private Boolean checkdeclaracion;
    @Persist
    @Property
    private Boolean checkconfianza;
    @Persist
    @Property
    private Date fechadenacimientomenora;
    @Persist
    @Property
    private Date fechadenacimientomayora;
    @Property
    @Persist
    private String valTipoDiscapacidad;
    @Property
    @Persist
    private String valNivelInstruccion;
    @Property
    @Persist
    private DatoAuxiliar valRegimenContratacion;
    @Property
    @Persist
    private Integer valhorassemanalesmayora;
    @Property
    @Persist
    private Integer valhorassemanalesmenora;
    @Property
    @Persist
    private DatoAuxiliar valcodigofuncionaldelcargo;
    @Property
    @Persist
    private Boolean valhabilitacionprofesional;
    @Property
    @Persist
    private Boolean valdeclaracion;
    @Property
    @Persist
    private Boolean valconfianza;
    @Inject
    private Request request;

    public List<String> getTiposDiscapacidad() {
        return Helpers.getValorTablaAuxiliar("TipoDiscapacidad", session);
    }
/*
    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }
*/
    public List<String> getNivelInstruccion() {
        return Helpers.getValorTablaAuxiliar("NivelInstrucción", session);
    }


    /*
     * Para armar la zona dinámica
     */
    @InjectComponent
    private Zone cargosGrillaZone;
    @InjectComponent
    private Zone empleadoszone;
    @InjectComponent
    private Zone filtrosZone;

    Object onToCargo(Trabajador persona) {
        actual = persona;
        if (!request.isXHR()) {
            return this;
        }

        return cargosGrillaZone.getBody();
    }

    @XHR
    Object onToUpdateCargosGrilla() {
        if (actual != null) {
            if (!request.isXHR()) {
                return this;
            }

            return cargosGrillaZone.getBody();
        }
        return null;
    }

    private MultiZoneUpdate getZones() {
        return new MultiZoneUpdate("cargosGrillaZone", cargosGrillaZone.getBody());
    }

    private MultiZoneUpdate getEmpleadosZones() {
        return new MultiZoneUpdate("cargosGrillaZone", cargosGrillaZone.getBody()).add("empleadoszone", empleadoszone.getBody());
    }

    private MultiZoneUpdate getTodasZones() {
        return new MultiZoneUpdate("cargosGrillaZone", cargosGrillaZone.getBody()).add("empleadoszone", empleadoszone.getBody()).add("filtrosZone", filtrosZone.getBody());
    }

    public String getSelectionRow() {
        if (actual != null) {
            if (e.id == actual.id) {
                return "selected";
            }
            return "normal";
        }
        return "";

    }

    void onActivate() {
        if (yaEntrada == false) {
            yaEntrada = true;
            mostrarEsconder = "Ocultar";
            mostrarFiltros = true;
        }
    }

    Object onFailure() {
        return this;
    }
    /*
     * Fin para armar la zona dinámica
     *
     */
}
