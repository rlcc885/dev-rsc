package com.tida.servir.components;

import helpers.Errores;
import helpers.Logger;

import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.FormacionProfesional;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.entities.Usuario;

import helpers.Helpers;

import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 *	Clase que maneja el TAB del editor de datos personales.
 *  
 */
public class FamiliaresEditor {

    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;
    @Property
    @Persist
    private Familiar familiarActual;
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @Parameter
    @Property
    private Trabajador actual;
    @Property
    private Familiar f;
    @InjectComponent
    private Envelope envelope;
    @Component(id = "formulariofamiliares")
    private Form formulariofamiliares;

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
        return Permisos.puedeEscribir(_usuario, _oi);
    }

    public List<Familiar> getFamiliares() {

        Criteria c = session.createCriteria(Familiar.class);
        c.add(Restrictions.eq("trabajador", actual));
        return c.list();
        // return session.createCriteria(Familiar.class).list();
    }

    @Log
    void onValidateFromformulariofamiliares() {
        if (familiarActual.getFechaNacimiento().after(new Date())) {
            Logger logger = new Logger();
            logger.loguearError(session, _usuario, String.valueOf(familiarActual.getId()),
                    Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
                    Errores.ERROR_FECHA_PREVIA_ACTUAL, Logger.TIPO_OBJETO_FAMILIAR);

            formulariofamiliares.recordError(Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL);
        }
    }

    @Log
    Object onFailureFromformulariofamiliares() {
        return zonas();
    }

    void cargoDatos() {
        domicilioCP = familiarActual.getDomicilioCodigoPostal();
        domicilioDireccion = familiarActual.getDomicilioDireccion();
        pais = familiarActual.getPais();
        ubigeoNacimiento = new Ubigeo();

        ubigeoDomicilio = new Ubigeo();
        ubigeoNacimiento.setDepartamento(familiarActual.getCod_ubi_dept());


        ubigeoNacimiento.setProvincia(familiarActual.getCod_ubi_prov());
        ubigeoNacimiento.setDistrito(familiarActual.getCod_ubi_dist());

        ubigeoDomicilio.setDepartamento(familiarActual.getCod_dom_dept());


        ubigeoDomicilio.setProvincia(familiarActual.getCod_dom_prov());
        ubigeoDomicilio.setDistrito(familiarActual.getCod_dom_dist());


    }
    /*
     * reset del formulario
     */

    Object onActionFromReset() {
        familiarActual = new Familiar();
        borrarDatos();
        editando = false;
        return zonas();
    }

    void onPrepareFromformulariofamiliares() {
        borrarForm = false;
        if (familiarActual == null) {
            familiarActual = new Familiar();
        }

        //cargoDatos();

    }

    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("ubigeoDomZoneFam", ubigeoDomZoneFam.getBody()).add("ubigeoNacZoneFam", ubigeoNacZoneFam.getBody()) //.add("formacionZone", formacionZone.getBody())
                .add("familiaresZone", familiaresZone.getBody());

        return mu;
    }
    @InjectComponent
    private Zone familiaresZone;

    /*@InjectComponent
    @Property
    private Zone formacionZone;*/
    @Property
    @Persist
    private boolean editando;
    @InjectComponent
    @Property
    private Zone ubigeoNacZoneFam;
    @InjectComponent
    @Property
    private Zone ubigeoDomZoneFam;
    //Datos del formulario (que son persistentes)
    @Property
    @Persist
    private Ubigeo ubigeoNacimiento;
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Property
    @Persist
    private FormacionProfesional formacionProfesional;

    Object onActionFromEditar(Familiar f) {
        familiarActual = (Familiar) session.load(Familiar.class, f.getId());
        //   familiarActual = f;
        cargoDatos();
        editando = true;
        return zonas();
    }

    void borrarDatos() {
        familiarActual = new Familiar();
        ubigeoNacimiento = new Ubigeo();
        formacionProfesional = new FormacionProfesional();
        cargoDatosDefault(actual);
        editando = false;
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar(Familiar f) {

        //f.getTrabajador().remove(actual);
        actual.getFamiliares().remove(f);
        session.delete(f);
        session.saveOrUpdate(actual);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(f.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_FAMILIAR);
        borrarDatos();
        return zonas();
    }

    public PrimaryKeyEncoder<Long, Familiar> getEncoder() {
        return new PrimaryKeyEncoder<Long, Familiar>() {

            public Long toKey(Familiar value) {
                return value.getId();
            }

            public void prepareForKeys(List<Long> keys) {
            }

            public Familiar toValue(Long key) {
                return (Familiar) session.get(Familiar.class, key);
            }

            public Class<Long> getKeyType() {
                return Long.class;
            }
        };
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

    /**
     * Ve si se está en Perú. Si es así se pregunta por los datos de localización/Ubigeos
     * @return
     */
    public boolean getEsPeru() {
        if (pais != null) {
            return pais.contains("PER");
        } else {
            return false;
        }
    }
    @Property
    @Persist
    private String pais;

    public Object onChangeOfPais() {
        pais = _request.getParameter("param");
        if (!pais.contains("PER")) {
            ubigeoNacimiento = new Ubigeo();
        }
        return ubigeoNacZoneFam.getBody();
    }

    @Log
    @CommitAfter
    public Object onSuccessFromformulariofamiliares() {
        if (!borrarForm) {

            if ((!editando)) {

                Criteria c = session.createCriteria(Familiar.class);
                //c.add(Restrictions.eq("trabajador", actual));

                if (familiarActual.getTipoDocumento().equals("DNI") || familiarActual.getTipoDocumento().contains("Carnet ext")) {
                    c.add(Restrictions.eq("tipoDocumento", familiarActual.getTipoDocumento()));

                    c.add(Restrictions.eq("nroDocumento", familiarActual.getNroDocumento()));
                    if (c.list().size() > 0) {
                        Logger logger = new Logger();
                        logger.loguearError(session, _usuario, String.valueOf(familiarActual.getId()),
                                Logger.CODIGO_ERROR_DUPLICADO,
                                Errores.ERROR_FAMILIAR_DUPLICADO, Logger.TIPO_OBJETO_FAMILIAR);

                        formulariofamiliares.recordError(Errores.ERROR_FAMILIAR_DUPLICADO);
                        return this;
                    }

                }
                familiarActual.setTrabajador(actual);
            } else {
                //    familiarActual.setTrabajador(actual);
//            session.saveOrUpdate(actual);
            }
            familiarActual.setPais(pais);
            familiarActual.setCod_ubi_dept(ubigeoNacimiento.getDepartamento());
            familiarActual.setCod_ubi_dist(ubigeoNacimiento.getDistrito());
            familiarActual.setCod_ubi_prov(ubigeoNacimiento.getProvincia());
            familiarActual.setCod_dom_dept(ubigeoDomicilio.getDepartamento());
            familiarActual.setCod_dom_dist(ubigeoDomicilio.getDistrito());
            familiarActual.setCod_dom_prov(ubigeoDomicilio.getProvincia());
            familiarActual.setDomicilioCodigoPostal(domicilioCP);
            familiarActual.setDomicilioDireccion(domicilioDireccion);
            session.saveOrUpdate(familiarActual);
            //session.merge(familiarActual);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(familiarActual.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_FAMILIAR);
            //            session.flush();
            editando = false;
            familiarActual = new Familiar();
        }
        borrarDatos();
        formulariofamiliares.clearErrors();
        envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
        return zonas();

    }

    Object onSuccessFromFormUbiNac() {
        return ubigeoNacZoneFam.getBody();
    }
    @Property
    private boolean borrarForm;

    void onSelectedFromReset() {
        borrarForm = true;
        borrarDatos();
        editando = false;
    }

    public List<String> getParentesco() {
        return Helpers.getValorTablaAuxiliar("GradoParentesco", session);
    }

    public List<String> getPaises() {
        return Helpers.getValorTablaAuxiliar("Paises", session);
    }

    public List<String> getNacionalidad() {
        return Helpers.getValorTablaAuxiliar("Nacionalidades", session);
    }

    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }

    public List<String> getEstadoCivil() {
        return Helpers.getValorTablaAuxiliar("EstadoCivil", session);
    }

    public List<String> getSexo() {
        return Helpers.getValorTablaAuxiliar("Sexo", session);
    }

    public List<String> getGrupoSanguineo() {
        return Helpers.getValorTablaAuxiliar("GrupoSanguineo", session);
    }

    public List<String> getNivelInstruccion() {
        return Helpers.getValorTablaAuxiliar("NivelTitulo", session);
    }

    public List<String> getTipoDiscapacidad() {
        return Helpers.getValorTablaAuxiliar("TipoDiscapacidad", session);
    }

    public List<String> getRegimenPensionario() {
        return Helpers.getValorTablaAuxiliar("RegPensionarios", session);
    }

    @Log
    @SetupRender
    private void setupCombos() {
        borrarDatos();
   /*
        familiarActual = new Familiar();
        if (ubigeoNacimiento == null) {
            ubigeoNacimiento = new Ubigeo();
        }

        if (formacionProfesional == null) {
            formacionProfesional = new FormacionProfesional();
        }

        if (familiarActual != null) {
            if (familiarActual.getNacionalidad() == null) {
                familiarActual.nacionalidad = "PERÚ";
            }
            if (familiarActual.getPais() == null) {
                pais = "PERÚ";
            } else {
                pais = familiarActual.getPais();
            }
            pais = familiarActual.getPais();
            domicilioCP = familiarActual.getDomicilioCodigoPostal();
            domicilioDireccion = familiarActual.getDomicilioDireccion();
            if (ubigeoDomicilio == null) {
                ubigeoDomicilio = cargoUbigeoTrabajador(actual);
            }
            ubigeoDomicilio.setDepartamento(familiarActual.getCod_dom_dept());
            ubigeoDomicilio.setProvincia(familiarActual.getCod_dom_prov());
            ubigeoDomicilio.setDistrito(familiarActual.getCod_dom_dist());


            if (ubigeoNacimiento == null) {
                ubigeoNacimiento = new Ubigeo();
            }
            ubigeoNacimiento.setDepartamento(familiarActual.getCod_ubi_dept());
            ubigeoNacimiento.setProvincia(familiarActual.getCod_ubi_prov());
            ubigeoNacimiento.setDistrito(familiarActual.getCod_ubi_dist());

            if (formacionProfesional == null) {
                formacionProfesional = new FormacionProfesional();
            }
        } else {
            familiarActual = new Familiar();

            cargoDatosDefault(actual);
        }
 */
    }

    public void cargoDatosDefault(Trabajador t) {
        if(t.getNacionalidad() == null)
            familiarActual.setNacionalidad("PERÚ");
        else
            familiarActual.setNacionalidad(t.getNacionalidad());

        if(t.getPais() == null)
            pais = "PERÚ";
        else
//            pais = t.getPais();
        ubigeoDomicilio = cargoUbigeoTrabajador(t);
        domicilioCP = t.getDomicilioCodigoPostal();
        domicilioDireccion = t.getDomicilioDireccion();
    }

    public Ubigeo cargoUbigeoTrabajador(Trabajador t) {
        Ubigeo nuevoUbigeo = new Ubigeo();
        nuevoUbigeo.setDepartamento(t.getCod_dom_dept());
        nuevoUbigeo.setDistrito(t.getCod_dom_dist());
        nuevoUbigeo.setProvincia(t.getCod_dom_prov());

        return nuevoUbigeo;
    }
}
