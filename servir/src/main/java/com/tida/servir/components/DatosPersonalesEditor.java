package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Clase que maneja el TAB del editor de datos personales.
 *
 */
public class DatosPersonalesEditor {
//@SuppressWarnings("unused")

    @Property
    @Parameter(required = true, principal = true, autoconnect = true)
//    @Persist
    private Trabajador actual;
    @Inject
    private Session session;
    @Component(id = "formulariodatospersonales")
    private Form formulariodatospersonales;
    //@SuppressWarnings("unused")   
    @Property
    @SessionState
    private UsuarioAcceso usua;
    /*
     * @InjectComponent private Zone datosPersonalesZone;
     */
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
//    @InjectComponent
//    @Property
//    private Zone ubigeoNacZone;
    @InjectComponent
    @Property
    private Zone ubigeoZone;
    @InjectComponent
    @Property
    private Zone tipoPensionZone;
    @InjectComponent
    @Property
    private Zone conadisZone;
    //Datos del formulario (que son persistentes)
//    @Property
//    @Persist
//    private Ubigeo ubigeoNacimiento;
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Inject
    private ComponentResources _resources;
    @InjectComponent
    private Envelope envelope;
    @Persist
    @Property
    private DatoAuxiliar valtipovia;
    @Persist
    @Property
    private DatoAuxiliar valtipozona;
//    @Persist
//    @Property
//    private DatoAuxiliar valtipodiscapacidad;
    @Persist
    @Property
    private String valconadis;
//    @Persist
//    @Property
//    private String valessalud;
//    @Persist
//    @Property
//    private DatoAuxiliar valsistemapensionario;
//    @Persist
//    @Property
//    private DatoAuxiliar valregimenpensionario;
    @InjectComponent
    @Property
    private Zone zonaGeneral;
//    @Persist
//    @Property
//    private String valcuspp;
//    @InjectComponent
//    @Property
//    private Zone regimenZone;
    private int elemento = 0;
//    @Property
//    @Persist
//    private Trabajador actual;
@Property
    private String fechanacimiento;

    @Log
//    @SetupRender
    void setupRender() {
        if (actual.getSexo() != null) {
            if (actual.getSexo().equals("M")) {
                valsexo = "MASCULINO";
            } else if (actual.getSexo().equals("F")) {
                valsexo = "FEMENINO";
            } else {
                valsexo = null;
            }
        } else {
            valsexo = null;
        }


        domicilioCP = actual.getDomicilioCodigoPostal();
        domicilioDireccion = actual.getDomicilioDireccion();
//        if (ubigeoDomicilio == null) {
            ubigeoDomicilio = new Ubigeo();
//        }
        ubigeoDomicilio.setDepartamento(actual.getCod_dom_dept());
        ubigeoDomicilio.setProvincia(actual.getCod_dom_prov());
        ubigeoDomicilio.setDistrito(actual.getCod_dom_dist());


//        if (ubigeoNacimiento == null) {
//            ubigeoNacimiento = new Ubigeo();
//        }
//        ubigeoNacimiento.setDepartamento(actual.getCod_ubi_dept());
//        ubigeoNacimiento.setProvincia(actual.getCod_ubi_prov());
//        ubigeoNacimiento.setDistrito(actual.getCod_ubi_dist());
        valtipovia = actual.getTipovia();
        valtipozona = actual.getTipozona();
//        valtipodiscapacidad=actual.getTipodiscapacidad();
        valconadis = String.valueOf(actual.getNroCertificadoCONADIS());
//        valessalud=actual.getEsSalud();
//        valsistemapensionario=actual.getSistemapensionario();
//        valregimenpensionario=actual.getRegimenpensionario();
//        valcuspp=actual.getNumregimenpensionario();         
        validaciones();
        System.out.println("personallll");
        if (valconadis.equals("null")) {
            valconadis = null;
        }
    }

//    
//    @Log
//    void onValidateFromformulariodatospersonales() {
////        if(valconadis!=null){
////                actual.setNroCertificadoCONADIS(Integer.parseInt(valconadis));
////            }
////            else{
////                actual.setNroCertificadoCONADIS(0);
////        }
//        System.out.println("validate cayoooooooooooooo");
//        
//    }
    @Log
    Object onFailureFromformulariodatospersonales() {
        return this;
    }

//    @Log
//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    @Log
//    public boolean getEditable() {
////        if(_usuario.getRol().getId()==1 ||_usuario.getRol().getId()==2 ||_usuario.getRol().getId()==3 ){
////            
////        }
//        return Permisos.puedeEscribir(_usuario, _oi);
//    }

    @Log
    void onSelectedFromSave() {
        elemento = 1;
    }

    @Log
    void onSelectedFromCancel() {
        elemento = 3;
    }

    @Log
    void onSelectedFromBack() {
        elemento = 4;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariodatospersonales() throws ParseException {
//        if (elemento == 4) {
//            return Busqueda.class;
//        } else if (elemento == 3) {
//            return Busqueda.class;
//        } else if (elemento == 1) {
            //validaciones
//            if(actual.getFechaNacimiento()!=null){
//                if (actual.getFechaNacimiento().after(new Date())) {
//                    Logger logger = new Logger();
//                    logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
//                            Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
//                            Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TRABAJADOR);
//
//                    formulariodatospersonales.recordError(Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL);
//                    return zonaGeneral.getBody();
//                    } else {
//                        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
//                        if (getAge(actual.getFechaNacimiento(), new Date()) < ca.getEdad_minima()) {
//
//                            Logger logger = new Logger();
//                            logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
//                                    Logger.CODIGO_ERROR_EDAD_MAYOR_18,
//                                    Errores.ERROR_EDAD_MAYOR, Logger.TIPO_OBJETO_TRABAJADOR);
//
//                            formulariodatospersonales.recordError(Errores.ERROR_EDAD_MAYOR + ca.getEdad_minima());
//                            return zonaGeneral.getBody();
//                        }
//                    }
//            }

            if (actual.getEmailPersonal() != null) {
                if (!isEmail(actual.getEmailPersonal())) {
                    formulariodatospersonales.recordError("Email Personal Formato incorrecto");
                    return zonaGeneral.getBody();
                }
            }
            if (actual.getEmailLaboral() != null) {
                if (!isEmail(actual.getEmailLaboral())) {
                    formulariodatospersonales.recordError("Email Laboral Formato incorrecto");
                    return zonaGeneral.getBody();
                }
            }


            //        actual.setCod_ubi_dept(ubigeoNacimiento.getDepartamento());
            //        actual.setCod_ubi_dist(ubigeoNacimiento.getDistrito());
            //        actual.setCod_ubi_prov(ubigeoNacimiento.getProvincia());

//            actual.setTipodiscapacidad(valtipodiscapacidad);
//            actual.setEsSalud(valessalud);            
//            actual.setSistemapensionario(valsistemapensionario);
//            actual.setRegimenpensionario(valregimenpensionario);
//            actual.setNumregimenpensionario(valcuspp);

//            if(valsexo!=null){
//                if(valsexo.equals("MASCULINO")){
//                    actual.setSexo("M");
//                }
//                else{
//                    actual.setSexo("F");
//                }
//            }
            //para dni 
            List<Trabajador> lTrabajador = session.createCriteria(Trabajador.class).add(Restrictions.eq("nroDocumento", actual.getNroDocumento())).add(Restrictions.ne("id", actual.getId())).list();

            if (lTrabajador.size() > 0) {
                System.out.println("--------- id actual " + actual.getId() + " lTrabajador size " + lTrabajador.get(0).getId());
                // No pueden haber 2 trabajadores con el mismo dni
                formulariodatospersonales.recordError(Errores.ERROR_TRABAJADOR_DNI_EXISTENTE);
                formulariodatospersonales.recordError("Numero de documento: " + actual.getNroDocumento());
                return zonaGeneral.getBody();
            }
            //para email personal
            List<Trabajador> lbusqueda = session.createCriteria(Trabajador.class).add(Restrictions.like("emailPersonal", actual.getEmailPersonal())).add(Restrictions.ne("id", actual.getId())).list();
            if (lbusqueda.size() > 0) {
                formulariodatospersonales.recordError("Email Personal ya registrado");
                return zonaGeneral.getBody();
            }
            //para email laboral
            List<Trabajador> lbusqueda2 = session.createCriteria(Trabajador.class).add(Restrictions.like("emailLaboral", actual.getEmailLaboral())).add(Restrictions.ne("id", actual.getId())).list();
            if (lbusqueda2.size() > 0) {
                formulariodatospersonales.recordError("Email Laboral ya registrado");
                return zonaGeneral.getBody();
            }
            if (valconadis != null && !valconadis.equals("") && !valconadis.equals("null")) {
                actual.setNroCertificadoCONADIS(Integer.parseInt(valconadis));
            }

            actual.setCod_dom_dept(ubigeoDomicilio.getDepartamento());
            actual.setCod_dom_dist(ubigeoDomicilio.getDistrito());
            actual.setCod_dom_prov(ubigeoDomicilio.getProvincia());
            actual.setDomicilioCodigoPostal(domicilioCP);
            actual.setDomicilioDireccion(domicilioDireccion);
            actual.setTipovia(valtipovia);
            actual.setTipozona(valtipozona);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = dateFormat.parse(fechanacimiento);
            actual.setFechaNacimiento(fecha);
            session.saveOrUpdate(actual);
            session.flush();
            formulariodatospersonales.clearErrors();
            envelope.setContents(helpers.Constantes.TRABAJADOR_EDIT_EXITO);
            validaciones();
//        }
        return tresZonas();
    }

    @Log
    MultiZoneUpdate tresZonas() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("zonaGeneral", zonaGeneral.getBody());
        //.add("regimenZone", regimenZone.getBody()); "ubigeoNacZone", ubigeoNacZone.getBody()).add(
        return mu;
    }
//    @Log
//    @CommitAfter
//    Object onSuccessFromFormUbiNac() {
//        if(valtipodiscapacidad != null && !valtipodiscapacidad.equals("")){
//            if(valtipodiscapacidad.getValor().equals("NO TIENE") || valtipodiscapacidad.getValor().equals("")){
//                vconadis=true;
//                valconadis=null;
//            }        
//            else{
//                vconadis=false;
//            }
//        }
//        else{
//            vconadis=true;
//        }
//        
//        return ubigeoNacZone.getBody();
//    }
//    @Log
//    @CommitAfter
//    Object onSuccessFromFormregimen() {     
//        return regimenZone.getBody();
//    }
    @Inject
    private Request _request;
    @Property
    @Persist
    private String domicilioCP;
    @Property
    @Persist
    private String domicilioDireccion;
    @Property
    @Persist
    private String valsexo;

    @Log
    void onEpersonalChanged() {
        actual.setEmailPersonal(_request.getParameter("param"));
    }

    @Log
    void onElaboralChanged() {
        actual.setEmailLaboral(_request.getParameter("param"));
    }

    @Log
    void onRucChanged() {
        actual.setNroRUC(_request.getParameter("param"));
    }

    @Log
    void onTcelularChanged() {
        actual.setTelefonocelular(_request.getParameter("param"));
    }

    @Log
    void onTfijoChanged() {
        actual.setTelefonofijo(_request.getParameter("param"));
    }

    @Log
    void onRnpChanged() {
        actual.setCodigoOSCE(_request.getParameter("param"));
    }

    @Log
    void onOtraformacionChanged() {
        actual.setFormacionInfAdicional(_request.getParameter("param"));
    }

    @Log
    void onContactarChanged() {
        actual.setEmergenciaNombre(_request.getParameter("param"));
    }

    @Log
    void onDireccionemeChanged() {
        actual.setEmergenciaDomicilio(_request.getParameter("param"));
    }

    @Log
    void onTalternativopChanged() {
        actual.setEmergenciaTelefonoAlternativo1(_request.getParameter("param"));
    }

    @Log
    void onTalternativosChanged() {
        actual.setEmergenciaTelefonoAlternativo2(_request.getParameter("param"));
    }

    @Log
    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    @Log
    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }

    @Log
    void onConaChanged() {
        valconadis = _request.getParameter("param");
    }

    @Log
    void onEssaChanged() {
        actual.setEsSalud(_request.getParameter("param"));
    }

    @Log
    void onCusppChanged() {
        actual.setNumregimenpensionario(_request.getParameter("param"));
    }

    @Log
    void onNombreepsChanged() {
        actual.setNombreeps(_request.getParameter("param"));
    }

    // TODO: poner en common
    private static int getAge(Date bDay, Date now) {
        int res = now.getYear() - bDay.getYear();
        if ((bDay.getMonth() > now.getMonth()) || (bDay.getMonth() == now.getMonth() && bDay.getDate() > now.getDate())) {
            res--;
        }
        return res;
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getPaises() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("PAISES", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public List<String> getNacionalidades() {
        return Helpers.getValorTablaAuxiliar("Nacionalidades", session);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipovias() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipozonas() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getEstadoCivil() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ESTADOCIVIL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentoide() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public List<String> getSexos() {
        return Helpers.getValorTablaAuxiliar("SEXO", session);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getGruposanguineos() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("GRUPOSANGUINEO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelinstrucciones() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELINSTRUCCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getFormacionprofesionales() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("FORMACIONPROFESIONAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipodiscapacidades() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODISCAPACIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getRegimenpensionarios() {
        //List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGPENSIONARIOS", null, 0, session);
        List<DatoAuxiliar> list = null;
        if (actual.getSistemapensionario() != null) {
            list = Helpers.getDatoAuxiliar("REGPENSIONARIOS", "SISTEMAPENCIONARIO", actual.getSistemapensionario().getCodigo(), session);
        } else {
            list = Helpers.getDatoAuxiliar("REGPENSIONARIOS", "SISTEMAPENCIONARIO", 0, session);
        }


//        DatoAuxiliar d;
//       if (ubigeo.getDepartamento() != null) {
//            lub = Helpers.getDatoAuxiliar("UBPROVINCIA", "UBDEPARTAMENTO",
//                    ubigeo.getDepartamento().getCodigo(), session);

        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getSistemapensionarios() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SISTEMAPENSIONARIO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    /**
     * Ve si se está en Perú. Si es así se pregunta por los datos de
     * localización/Ubigeos
     *
     * @return
     */
    @Inject
    private PropertyAccess _access;

//    public Object onChangeOfPais() {
//        pais = _request.getParameter("param");
//        if (!pais.contains("PER")) {
////            ubigeoNacimiento = new Ubigeo();
//        }
//        return ubigeoNacZone.getBody();
//    }
    @Log
    public List<String> getTiposDoc() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", "TipoDocumento"));
        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
        c.setProjection(Projections.property("valor"));
        return c.list();
    }
    //validaciones
    @Property
    @Persist
    private boolean vtipodiscapacidad;
    @Property
    @Persist
    private boolean vconadis;
    @Property
    @Persist
    private boolean vessalud;
    @Property
    @Persist
    private boolean vruc;
    @Property
    @Persist
    private boolean vgruposanguineo;
    @Property
    @Persist
    private boolean vdatospersonales;
    @Property
    @Persist
    private boolean vdatosubicacion;
    @Property
    @Persist
    private boolean votros;
    @Property
    @Persist
    private boolean vemergencia;
    @Property
    @Persist
    private boolean vbotones;

    @Log
    void validaciones() {
//        valconadis=String.valueOf(actual.getNroCertificadoCONADIS());  
        vconadis = false;
        if (actual.getTipodiscapacidad() != null && !actual.getTipodiscapacidad().equals("")) {
            vtipodiscapacidad = true;
            if (actual.getTipodiscapacidad().getValor().equals("NO TIENE") || actual.getTipodiscapacidad().getValor().equals("")) {
                vconadis = true;
            } else {
                if (actual.getNroCertificadoCONADIS() != null) {
                    vconadis = true;
                }
            }
        } else {
            vtipodiscapacidad = false;
        }

        if (actual.getEsSalud() != null && !actual.getEsSalud().equals("")) {
            vessalud = true;
        } else {
            vessalud = false;
        }
        if (actual.getNroRUC() != null && !actual.getNroRUC().equals("")) {
            vruc = true;
        } else {
            vruc = false;
        }
        if (actual.getGruposanguineo() != null && !actual.getGruposanguineo().equals("")) {
            vgruposanguineo = true;
        } else {
            vgruposanguineo = false;
        }

        if (usua.getAccesoupdate() == 1) {
            if (_usuario.getRolid() == 1) {
                vdatospersonales = true;
                vdatosubicacion = false;
                votros = true;
                vemergencia = false;
            } else if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vdatospersonales = true;
                vdatosubicacion = false;
                votros = false;
                vemergencia = false;
            }
            vbotones = true;
        } else {
            vdatospersonales = true;
            vdatosubicacion = true;
            votros = true;
            vemergencia = true;
            vtipodiscapacidad = true;
            vconadis = true;
            vessalud = true;
            vruc = true;
            vgruposanguineo = true;
            vbotones = false;
            //titulo=false;
        }
    }

    @Log
    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        } else {
            return false;
        }
    }

    @Log
    Object onValueChangedFromTipodiscapacidad(DatoAuxiliar dato) {
        if (dato != null && !dato.equals("")) {
            if (dato.getValor().equals("NO TIENE") || dato.getValor().equals("")) {
                vconadis = true;
                actual.setNroCertificadoCONADIS(null);
            } else {
                vconadis = false;
            }
        } else {
            vconadis = true;
        }
        return _request.isXHR() ? new MultiZoneUpdate("conadisZone", conadisZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromSistemapen(DatoAuxiliar dato) {
        return _request.isXHR() ? new MultiZoneUpdate("tipoPensionZone", tipoPensionZone.getBody()) : null;
    }
}