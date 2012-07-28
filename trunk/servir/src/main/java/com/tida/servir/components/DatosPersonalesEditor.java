package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.Query;
import com.tida.servir.pages.Busqueda;
import java.util.ArrayList;

/**
 *
 * Clase que maneja el TAB del editor de datos personales.
 *
 */
public class DatosPersonalesEditor {

    @SuppressWarnings("unused")
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
    private Zone ubigeoDomZone;
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
    
    @Persist
    @Property
    private Integer valconadis;
    @Persist
    @Property
    private String valessalud;
//    @Persist
//    @Property
//    private DatoAuxiliar valgruposanguineo;
//    @Persist
//    @Property
//    private String valnombreeps;
//    @Persist
//    @Property
//    private Boolean valeps;
//    @Persist
//    @Property
//    private Boolean valrecibepension;
//    @Persist
//    @Property
//    private DatoAuxiliar valsistemapensionario;
    @Persist
    @Property
    private DatoAuxiliar valregimenpensionario;
    @InjectComponent
    @Property
    private Zone zonaGeneral;
    @InjectComponent
    @Property
    private Zone zonaSegunda;
    @InjectComponent
    @Property
    private Zone zonaTercera;
    @Persist
    @Property
    private String valcuspp;
//    @InjectComponent
//    @Property
//    private Zone regimenZone;
    private int elemento=0;
    

        
    @Log
    @SetupRender
    private void inicio() {        
        if(actual.getSexo()!=null){
            if(actual.getSexo().equals("M")){
                valsexo="MASCULINO";            
            }
            else if(actual.getSexo().equals("F")){
                valsexo="FEMENINO";
            }
            else{
                valsexo=null;
            }
        }
        else{
            valsexo=null;
        }
        
        

        domicilioCP = actual.getDomicilioCodigoPostal();
        domicilioDireccion = actual.getDomicilioDireccion();
        if (ubigeoDomicilio == null) {
            ubigeoDomicilio = new Ubigeo();
        }
        ubigeoDomicilio.setDepartamento(actual.getCod_dom_dept());
        ubigeoDomicilio.setProvincia(actual.getCod_dom_prov());
        ubigeoDomicilio.setDistrito(actual.getCod_dom_dist());


//        if (ubigeoNacimiento == null) {
//            ubigeoNacimiento = new Ubigeo();
//        }
//        ubigeoNacimiento.setDepartamento(actual.getCod_ubi_dept());
//        ubigeoNacimiento.setProvincia(actual.getCod_ubi_prov());
//        ubigeoNacimiento.setDistrito(actual.getCod_ubi_dist());
        valtipovia=actual.getTipovia();
        valtipozona=actual.getTipozona();
        
        valconadis=actual.getNroCertificadoCONADIS();
        valessalud=actual.getEsSalud();
//        valgruposanguineo=actual.getGruposanguineo();
//        valrecibepension=actual.getRecibepension();
//        valnombreeps=actual.getNombreeps();
//        valeps=actual.getEps();
//        valsistemapensionario=actual.getSistemapensionario();
        valregimenpensionario=actual.getRegimenpensionario();
        valcuspp=actual.getNumregimenpensionario();         
        validaciones();    
        System.out.println("personallll");
//         if(valconadis.equals("null")){
//            valconadis=null;
//        }
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

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
//        if(_usuario.getRol().getId()==1 ||_usuario.getRol().getId()==2 ||_usuario.getRol().getId()==3 ){
//            
//        }
        return Permisos.puedeEscribir(_usuario, _oi);
    }
    void onSelectedFromSave() {        
        elemento=1;   
    }
    
    void onSelectedFromCancel() {        
        elemento=3;
    }
    void onSelectedFromBack(){     
        elemento=4;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodatospersonales() {
        if(elemento==4){
            return Busqueda.class;
        }
        else if(elemento==3){
            return Busqueda.class;
        }
        else if(elemento==1){
            //validaciones
            if(actual.getFechaNacimiento()!=null){
                 if (actual.getFechaNacimiento().after(new Date())) {
                    Logger logger = new Logger();
                    logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
                            Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
                            Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TRABAJADOR);

                    formulariodatospersonales.recordError(Errores.ERROR_FECHA_NACIMIENTO_PREVIA_ACTUAL);
                    return zonaGeneral.getBody();
                    } else {
                        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
                        if (getAge(actual.getFechaNacimiento(), new Date()) < ca.getEdad_minima()) {

                            Logger logger = new Logger();
                            logger.loguearError(session, _usuario, String.valueOf(actual.getId()),
                                    Logger.CODIGO_ERROR_EDAD_MAYOR_18,
                                    Errores.ERROR_EDAD_MAYOR, Logger.TIPO_OBJETO_TRABAJADOR);

                            formulariodatospersonales.recordError(Errores.ERROR_EDAD_MAYOR + ca.getEdad_minima());
                            return zonaGeneral.getBody();
                        }
                    }
            }
           
            if(actual.getEmailPersonal()!=null){
                if(!isEmail(actual.getEmailPersonal())){
                    formulariodatospersonales.recordError("Email Personal Formato incorrecto");
                    return zonaGeneral.getBody();
                }
            }
            if(actual.getEmailLaboral()!=null){
                if(!isEmail(actual.getEmailLaboral())){
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
            if(valsexo!=null){
                if(valsexo.equals("MASCULINO")){
                    actual.setSexo("M");
                }
                else{
                    actual.setSexo("F");
                }
            }
            //para dni 
            List<Trabajador> lTrabajador = session.createCriteria(Trabajador.class).add(Restrictions.eq("nroDocumento", actual.getNroDocumento())).add(Restrictions.ne("id", actual.getId())).list();

            if (lTrabajador.size() > 0) {
                System.out.println("--------- id actual " + actual.getId() + " lTrabajador size " + lTrabajador.get(0).getId());
                // No pueden haber 2 trabajadores con el mismo dni
                formulariodatospersonales.recordError(Errores.ERROR_TRABAJADOR_DNI_EXISTENTE);
                formulariodatospersonales.recordError("Numero de documento: "  + actual.getNroDocumento());
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
            
            actual.setCod_dom_dept(ubigeoDomicilio.getDepartamento());
            actual.setCod_dom_dist(ubigeoDomicilio.getDistrito());
            actual.setCod_dom_prov(ubigeoDomicilio.getProvincia());
            actual.setDomicilioCodigoPostal(domicilioCP);
            actual.setDomicilioDireccion(domicilioDireccion);
            actual.setTipovia(valtipovia);
            actual.setTipozona(valtipozona);
            actual.setNroCertificadoCONADIS(valconadis);
            
            
            actual.setEsSalud(valessalud);
//            actual.setGruposanguineo(valgruposanguineo);
//            actual.setRecibepension(valrecibepension);
//            actual.setNombreeps(valnombreeps);
//            actual.setEps(valeps);
            actual.setRegimenpensionario(valregimenpensionario);
            actual.setNumregimenpensionario(valcuspp);           
            
            session.saveOrUpdate(actual);
            session.flush();
            formulariodatospersonales.clearErrors();
            System.out.println("guardar cayoooooooooooooo");
            envelope.setContents(helpers.Constantes.TRABAJADOR_EDIT_EXITO);
            validaciones();
            
        }
        return tresZonas();
        //return datosPersonalesZone.getBody();
        //return tresZonas();
    }

    MultiZoneUpdate tresZonas() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody()).add("zonaTercera", zonaTercera.getBody())
                .add("zonaGeneral", zonaGeneral.getBody()).add("zonaSegunda", zonaSegunda.getBody());
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
    

    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }
    
    void onConaChanged() {
        valconadis = Integer.parseInt(_request.getParameter("param"));
    }
    
    void onEssaChanged() {
        valessalud = _request.getParameter("param");
    }
    
//    void onNombreepsChanged() {
//        valnombreeps = _request.getParameter("param");
//    }
    
    void onCusppChanged() {
        valcuspp= _request.getParameter("param");
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
        List<DatoAuxiliar> list=null;
        if(actual.getSistemapensionario()!=null){
            list = Helpers.getDatoAuxiliar("REGPENSIONARIOS", "SISTEMAPENCIONARIO", actual.getSistemapensionario().getCodigo(), session);
        }
        else{
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

    public List<String> getTiposDoc(){        
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

    
    
    void validaciones(){
//        valconadis=String.valueOf(actual.getNroCertificadoCONADIS());  
        vconadis=false;
        if(actual.getTipodiscapacidad() != null && !actual.getTipodiscapacidad().equals("")){
            vtipodiscapacidad=true;
            if(actual.getTipodiscapacidad().getValor().equals("NO TIENE") || actual.getTipodiscapacidad().getValor().equals("")){
                vconadis=true;                
            }        
            else{
                if(actual.getNroCertificadoCONADIS()!=null){
                    vconadis=true;
                }               
            }       
        }
        else{
            vtipodiscapacidad=false;
        }        

        if(actual.getEsSalud()!=null && !actual.getEsSalud().equals("")){
            vessalud=true;
        }
        else{
            vessalud=false;
        }
        if(actual.getNroRUC() != null && !actual.getNroRUC().equals("")){
            vruc=true;   
        }
        else{
            vruc=false;
        }
        if(actual.getGruposanguineo() != null && !actual.getGruposanguineo().equals("")){
            vgruposanguineo=true;   
        }
        else{
            vgruposanguineo=false;
        }
        
            if(usua.getAccesoupdate()==1){
                if(_usuario.getRol().getId()==1){
                    vdatospersonales=true;
                    vdatosubicacion=false;
                    votros=true;
                    vemergencia=false;            
                }
                else if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                    vdatospersonales=true;
                    vdatosubicacion=false;
                    votros=false;
                    vemergencia=false;            
                }   
                vbotones=true;
            }
            else{
                vdatospersonales=true;
                vdatosubicacion=true;
                votros=true;
                vemergencia=true;
                vtipodiscapacidad=true;
                vconadis=true;
                vessalud=true;
                vruc=true;
                vgruposanguineo=true;
                vbotones=false;
                //titulo=false;
            }      
    }
    
    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        }else{
            return false;
        }        
    }
    
//    Object onValueChangedFromTipodiscapacidad(DatoAuxiliar dato) {
//        
//    }
//    Object onValueChangedFromSistemapen(DatoAuxiliar dato) {
//        return tresZonas();
//    }
    @Log
    @CommitAfter
    Object onSuccessFromformulariosaludes() {        
        return zonaTercera.getBody();
    }
    @Log
    @CommitAfter
    Object onSuccessFromformulariosalud() {        
        if(actual.getTipodiscapacidad() != null && !actual.getTipodiscapacidad().equals("")){
            if(actual.getTipodiscapacidad().getValor().equals("NO TIENE") || actual.getTipodiscapacidad().getValor().equals("")){
                vconadis=true;
                actual.setNroCertificadoCONADIS(null);
            }        
            else{
                vconadis=false;
            }
        }
        else{
            vconadis=true;
        }        
        return zonaSegunda.getBody();
    }
}