package com.tida.servir.components;

import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.entities.Usuario;
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
    private Entidad _oi;
    @InjectComponent
    @Property
    private Zone ubigeoNacZone;
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
    private DatoAuxiliar valtipodiscapacidad;
    @Persist
    @Property
    private String valconadis;
    @Persist
    @Property
    private String valessalud;
    @InjectComponent
    @Property
    private Zone zonaGeneral;
    private int elemento=0;
    
    
    @Log
    @SetupRender
    private void inicio() {
        if(actual.getSexo().equals("M")){
            valsexo="MASCULINO";            
        }
        else if(actual.getSexo().equals("F")){
            valsexo="FEMENINO";
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
        valtipodiscapacidad=actual.getTipodiscapacidad();
        valconadis=String.valueOf(actual.getNroCertificadoCONADIS());
        valessalud=actual.getEsSalud();   
         if(valconadis.equals("null")){
            valconadis="0";
        }
             
    }
    
    
    
    
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
    void onSelectedFromSave() {        
        elemento=1;   
    }
    void onSelectedFromReset(){     
        elemento=2;
    }
    
    void onSelectedFromCancel() {        
        elemento=3;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodatospersonales() {
        if(elemento==3){
            
        }
        else if(elemento==2){
            
        }
        else if(elemento==1){
    //        actual.setCod_ubi_dept(ubigeoNacimiento.getDepartamento());
    //        actual.setCod_ubi_dist(ubigeoNacimiento.getDistrito());
    //        actual.setCod_ubi_prov(ubigeoNacimiento.getProvincia());
            actual.setCod_dom_dept(ubigeoDomicilio.getDepartamento());
            actual.setCod_dom_dist(ubigeoDomicilio.getDistrito());
            actual.setCod_dom_prov(ubigeoDomicilio.getProvincia());
            actual.setDomicilioCodigoPostal(domicilioCP);
            actual.setDomicilioDireccion(domicilioDireccion);
            actual.setTipovia(valtipovia);
            actual.setTipozona(valtipozona);
            actual.setTipodiscapacidad(valtipodiscapacidad);
            actual.setEsSalud(valessalud);
            actual.setNroCertificadoCONADIS(Integer.parseInt(valconadis));
            if(valsexo.equals("MASCULINO")){
                actual.setSexo("M");
            }
            else{
                actual.setSexo("F");
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
  
            session.saveOrUpdate(actual);
            formulariodatospersonales.clearErrors();
            envelope.setContents(helpers.Constantes.TRABAJADOR_EDIT_EXITO);
        }
        return tresZonas();
        //return datosPersonalesZone.getBody();
        //return tresZonas();
    }

    MultiZoneUpdate tresZonas() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("ubigeoNacZone", ubigeoNacZone.getBody()).add("ubigeoDomZone", ubigeoDomZone.getBody()).add("zonaGeneral", zonaGeneral.getBody());
        return mu;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormUbiNac() {
        if(valtipodiscapacidad != null && !valtipodiscapacidad.equals("")){
            if(valtipodiscapacidad.getValor().equals("NO TIENE") || valtipodiscapacidad.getValor().equals("")){
                vconadis=true;
                valconadis=null;
            }        
            else{
                vconadis=false;
            }
        }
        else{
            vconadis=true;
            valconadis=null;
        }
        
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
        valconadis = _request.getParameter("param");
    }
    
    void onEssaChanged() {
        valessalud = _request.getParameter("param");
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
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELINSTRUCCIÓN", null, 0, session);
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
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGPENSIONARIOS", null, 0, session);
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
    
    
    void validaciones(){
        if(valtipodiscapacidad != null && !valtipodiscapacidad.equals("")){
            vtipodiscapacidad=true;
            if(valtipodiscapacidad.getValor().equals("NO TIENE") || valtipodiscapacidad.getValor().equals("")){
                vconadis=true;                
            }        
            else{
                vconadis=false;
            }
        }
        else{
            vconadis=true;            
        }
        if(valessalud!=null && !valessalud.equals("")){
            vessalud=true;
        }
        else{
            vessalud=false;
        }
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
    }
}