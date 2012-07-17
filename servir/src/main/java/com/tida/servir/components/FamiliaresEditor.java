package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Logger;


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
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class FamiliaresEditor {

     @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
   
    
    @Component(id = "formulariomensajesf")
    private Form formulariomensajesf;
    @InjectComponent
    private Zone mensajesFZone;  
       
    @InjectComponent
    private Zone familiaresZone;
    
    private int elemento=0;
     
    @Parameter
    @Property
    private Trabajador actual;

    @Property
    @Persist
    private Familiar familiarActual;
    
    @Property
    private List<Familiar> listaParentescoP;
    @Property
    private List<Familiar> listaParentescoC;
    @Property
    private List<Familiar> listaParentescoCY;
    
    @Property
    @Persist
    private boolean bvalidausuario;
    
    @Property
    @Persist
    private boolean bfechanacimiento;
    @Property
    @Persist
    private boolean bdni;
   
    //Listado de familiares
    @InjectComponent
    private Zone listaFamiliaresZone;
    @Persist
    @Property
    private Familiar listafamiliar;
    
 
    @Inject
    private PropertyAccess _access;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            familiarActual = new Familiar();
            bdni=true;
            bfechanacimiento=false;
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
    }
    
    @Log
    public List<Familiar> getListadoFamiliares() {
        Criteria c = session.createCriteria(Familiar.class);
        c.add(Restrictions.eq("trabajador",actual));  
        return c.list();
    }
    
     //para obtener datos del Parentesco
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanParentesco() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("GRADOPARENTESCO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del sexo
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanSexo() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SEXO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del Tipo de documento
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTiposDoc() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del estado civil
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanEstadoCivil() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ESTADOCIVIL", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariofamiliares() {
        //Codigo de Progenitor = 4
        if(familiarActual.getParentesco().getCodigo()==4){
            Criteria c1 = session.createCriteria(Familiar.class);
            c1.add(Restrictions.eq("trabajador",actual)); 
            c1.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
            listaParentescoP=c1.list();
        }
        //Codigo de Conviviente = 2
        if(familiarActual.getParentesco().getCodigo()==2){
            Criteria c2 = session.createCriteria(Familiar.class);
            c2.add(Restrictions.eq("trabajador",actual)); 
            c2.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
            listaParentescoC=c2.list();
         }
        //Codigo de Conyugue = 1
         if(familiarActual.getParentesco().getCodigo()==1){
            Criteria c3 = session.createCriteria(Familiar.class);
            c3.add(Restrictions.eq("trabajador",actual)); 
            c3.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
            listaParentescoCY=c3.list();
        }
        if(listaParentescoP!=null && listaParentescoP.size()>0 && familiarActual.getParentesco().getCodigo()==4){
            envelope.setContents("No es posible regstrar mas de un Progenitor");
            return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("familiaresZone", familiaresZone.getBody());
        }else if(listaParentescoC!=null && listaParentescoC.size()>0 && familiarActual.getParentesco().getCodigo()==2) {
             envelope.setContents("No es posible registrar mas de un Conviviente");
             return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("familiaresZone", familiaresZone.getBody());
        }else if(listaParentescoCY!=null && listaParentescoCY.size()>0 && familiarActual.getParentesco().getCodigo()==1) {
             envelope.setContents("No es posible registrar mas de un Cónyugue");
             return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("familiaresZone", familiaresZone.getBody());
        }else{
            System.out.println("Entro aka FINAL");
            familiarActual.setTrabajador(actual);
            familiarActual.setEntidad(_oi);
            session.saveOrUpdate(familiarActual);
            envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
            familiarActual=new Familiar();
            return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("listaFamiliaresZone", listaFamiliaresZone.getBody())
                    .add("familiaresZone", familiaresZone.getBody());  
        }
            
            
     
       /* 
       System.out.println("entro111111");          
       if(bfechanacimiento){
            familiarActual.setTrabajador(actual);
            familiarActual.setEntidad(_oi);
            session.saveOrUpdate(familiarActual);
            envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
            familiarActual=new Familiar();
            return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("listaFamiliaresZone", listaFamiliaresZone.getBody())
                    .add("familiaresZone", familiaresZone.getBody());    
       }else{           
            if(familiarActual.getFechaNacimiento()!=null){
                    bfechanacimiento=true;
                    Date fechaactual=new Date();
                    if((fechaactual.getYear()-familiarActual.getFechaNacimiento().getYear())>=18){
                        bdni=false;
                        System.out.println("entro");
                    }              
            }
            return familiaresZone.getBody(); 
       }
       * 
       */
  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            familiarActual=new Familiar();
            bdni=true;
            bfechanacimiento=false;
            return  familiaresZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(Familiar fami) {        
        familiarActual=fami;
           return familiaresZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(Familiar fami) {
        session.delete(fami);
        return listaFamiliaresZone.getBody();
    }
    
    
    
    
    
    /*
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
  
    }

    public void cargoDatosDefault(Trabajador t) {
//        if(t.getNacionalidad() == null)
//            familiarActual.setNacionalidad("PERÚ");
//        else
//            familiarActual.setNacionalidad(t.getNacionalidad());

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
    * 
    */
}
