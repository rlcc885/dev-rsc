package com.tida.servir.components;

import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.entities.Usuario;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.List;
import java.util.Date;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
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
 *
 * Clase que maneja el TAB del editor de datos personales.
 *
 */
public class DatosPersonalesEditor {

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, principal = true, autoconnect = true)
    private Trabajador actual;
    @Inject
    private Session session;
    @Component(id = "formulariodatospersonales")
    private Form formulariodatospersonales;

    /*
     * @InjectComponent private Zone datosPersonalesZone;
     */
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad_BK _oi;
    @InjectComponent
    @Property
    private Zone ubigeoNacZone;
    @InjectComponent
    @Property
    private Zone ubigeoDomZone;
    //Datos del formulario (que son persistentes)
    @Property
    @Persist
    private Ubigeo ubigeoNacimiento;
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Inject
    private ComponentResources _resources;
    @InjectComponent
    private Envelope envelope;

    @Log
    void onValidateFromformulariodatospersonales() {
        if (actual.getFechaNacimiento().after(new Date())) {
            Logger logger = new Logger();
            logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
                    Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
                    Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TRABAJADOR);

            formulariodatospersonales.recordError(Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL);
        } else {
            ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
            if (getAge(actual.getFechaNacimiento(), new Date()) < ca.getEdad_minima()) {

                Logger logger = new Logger();
                logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
                        Logger.CODIGO_ERROR_EDAD_MAYOR_18,
                        Errores.ERROR_EDAD_MAYOR, Logger.TIPO_OBJETO_TRABAJADOR);

                formulariodatospersonales.recordError(Errores.ERROR_EDAD_MAYOR + ca.getEdad_minima());
            }
        }
    }

    @Log
    Object onFailureFromformulariodatospersonales() {
        return this;
    }

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
        return Permisos.puedeEscribir(_usuario, _oi);
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariodatospersonales() {

        actual.setPais(pais);
        actual.setCod_ubi_dept(ubigeoNacimiento.getDepartamento());
        actual.setCod_ubi_dist(ubigeoNacimiento.getDistrito());
        actual.setCod_ubi_prov(ubigeoNacimiento.getProvincia());
        actual.setCod_dom_dept(ubigeoDomicilio.getDepartamento());
        actual.setCod_dom_dist(ubigeoDomicilio.getDistrito());
        actual.setCod_dom_prov(ubigeoDomicilio.getProvincia());
        actual.setDomicilioCodigoPostal(domicilioCP);
        actual.setDomicilioDireccion(domicilioDireccion);

        List<Trabajador> lTrabajador = session.createCriteria(Trabajador.class).add(Restrictions.eq("nroDocumento", actual.getNroDocumento())).add(Restrictions.ne("id", actual.getId())).list();

        if (lTrabajador.size() > 0) {
            System.out.println("--------- id actual " + actual.getId() + " lTrabajador size " + lTrabajador.get(0).getId());
            // No pueden haber 2 trabajadores con el mismo dni
            formulariodatospersonales.recordError(Errores.ERROR_TRABAJADOR_DNI_EXISTENTE);
            formulariodatospersonales.recordError("Numero de documento: "
                    + actual.getNroDocumento());
            return this;
        }

        session.saveOrUpdate(actual);

        formulariodatospersonales.clearErrors();
        envelope.setContents(helpers.Constantes.TRABAJADOR_EDIT_EXITO);
        return this;
        //return datosPersonalesZone.getBody();
        //return tresZonas();
    }

    MultiZoneUpdate tresZonas() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("ubigeoNacZone", ubigeoNacZone.getBody()).add("ubigeoDomZone", ubigeoDomZone.getBody());
        return mu;
    }

    Object onSuccessFromFormUbiNac() {
        return ubigeoNacZone.getBody();
    }
    @Inject
    private Request _request;
    @Property
    @Persist
    private String domicilioCP;
    @Property
    @Persist
    private String domicilioDireccion;

    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }

    // TODO: poner en common
    private static int getAge(Date bDay, Date now) {
        int res = now.getYear() - bDay.getYear();
        if ((bDay.getMonth() > now.getMonth()) || (bDay.getMonth() == now.getMonth() && bDay.getDate() > now.getDate())) {
            res--;
        }
        return res;
    }

    public List<String> getPaises() {
        return Helpers.getValorTablaAuxiliar("Paises", session);
    }

    public List<String> getNacionalidades() {
        return Helpers.getValorTablaAuxiliar("Nacionalidades", session);
    }

    public List<String> getEstadoCivil() {
        return Helpers.getValorTablaAuxiliar("EstadoCivil", session);
    }

    public List<String> getSexos() {
        return Helpers.getValorTablaAuxiliar("Sexo", session);
    }

    public List<String> getGrupoSanguineo() {
        return Helpers.getValorTablaAuxiliar("GrupoSanguineo", session);
    }

    public List<String> getNivelInstruccion() {
        return Helpers.getValorTablaAuxiliar("NivelInstrucción", session);
    }

    public List<String> getTipoDiscapacidad() {
        return Helpers.getValorTablaAuxiliar("TipoDiscapacidad", session);
    }

    public List<String> getRegimenPensionario() {
        return Helpers.getValorTablaAuxiliar("RegPensionarios", session);
    }
    @Property
    @Persist
    private String pais;

    /**
     * Ve si se está en Perú. Si es así se pregunta por los datos de
     * localización/Ubigeos
     *
     * @return
     */
    public boolean getEsPeru() {
        if (pais != null) {
            return pais.contains("PER");
        } else {
            return false;
        }
    }
    @Inject
    private PropertyAccess _access;

    public Object onChangeOfPais() {
        pais = _request.getParameter("param");
        if (!pais.contains("PER")) {
            ubigeoNacimiento = new Ubigeo();
        }
        return ubigeoNacZone.getBody();
    }

    @Log
    @SetupRender
    private void setupUbigeos() {
        if (actual.getNacionalidad() == null) {
            actual.setNacionalidad("PERÚ");
        }
        if (actual.getPais() == null) {
            pais = "PERÚ";
        } else {
            pais = actual.getPais();
        }

        domicilioCP = actual.getDomicilioCodigoPostal();
        domicilioDireccion = actual.getDomicilioDireccion();
        if (ubigeoDomicilio == null) {
            ubigeoDomicilio = new Ubigeo();
        }
        ubigeoDomicilio.setDepartamento(actual.getCod_dom_dept());
        ubigeoDomicilio.setProvincia(actual.getCod_dom_prov());
        ubigeoDomicilio.setDistrito(actual.getCod_dom_dist());


        if (ubigeoNacimiento == null) {
            ubigeoNacimiento = new Ubigeo();
        }
        ubigeoNacimiento.setDepartamento(actual.getCod_ubi_dept());
        ubigeoNacimiento.setProvincia(actual.getCod_ubi_prov());
        ubigeoNacimiento.setDistrito(actual.getCod_ubi_dist());

    }

    public List<String> getTiposDoc() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", "TipoDocumento"));
        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
        c.setProjection(Projections.property("valor"));
        return c.list();
    }
}
