/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import helpers.ServicioReniec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ale
 */
public class ABMSancion  extends GeneralPage
{
    @Property
    @SessionState
    private Entidad eue;
    @Property
    @SessionState
    private Usuario usuario;
    @Inject
    private Request _request;
    @Inject
    private ComponentResources _resources;
    @Inject
    private Session session;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    private Envelope envelope;    
    @Component(id = "formsancion")
    private Form formsancion;
    @Component(id = "formvalidacion")
    private Form formvalidacion;
    @Property
    @Persist
    private UsuarioAcceso usua;
    
    //campos de la zona modal
    @Property
    @Persist
    private String bdenoentidad;
    @Property
    @Persist
    private String bnomtrabajador;
    @Property
    @Persist
    private String bnomautoridad;
    @Property
    @Persist
    private LkBusquedaEntidad rowentidad;
    @Property
    @Persist
    private LkBusquedaTrabajadorSan btrabajador;
    @Property
    @Persist
    private LkBusquedaFuncionario bfuncionario;
    @Property
    @Persist
    private Funcionario nuevofuncionario;
    @Property
    @Persist
    private String bnomtrabaautoridad;
    @Property
    @Persist
    private LkBusquedaTrabajadorAuto btrabajadorauto;
    
    //campos de datos del sancionado
    @Property
    @Persist
    private boolean bestrabajador;
    @Property
    @Persist
    private DatoAuxiliar bdocidentidad;
    @Property
    @Persist
    private String bentidad;
    @Property
    @Persist
    private String bpuesto;
    @Property
    @Persist
    private DatoAuxiliar bregimen;
    @Property
    @Persist
    private Sancion nuevasancion; 
    @Property
    @Persist
    private Persona_Sancion nuevapersona; 
    @Property
    @Persist
    private String bestadopuesto;
    @Property
    @Persist
    private Entidad entidadbusqueda;
    
    //datos de la sancion
    @Property
    @Persist
    private DatoAuxiliar categoriasancion;
    @Property
    @Persist
    private TipoSancion tiposancion;
    @Property
    @Persist
    private String fechadocnot;
    @Property
    @Persist
    private String autoridadnot;
    @Property
    @Persist
    private String fechadocsan;
    @Property
    @Persist
    private String autoridadsan;
    @Property
    @Persist
    private String fecinicio;
    @Property
    @Persist
    private String fecfin;
    @Persist
    @Property
    private Date fecha_inicio;
    @Persist
    @Property
    private Date fecha_fin;
    @Persist
    @Property
    private Date fecha_docnot;
    @Persist
    @Property
    private Date fecha_docsan;
    @Property
    @Persist
    private String ayudadia;
    @Property
    @Persist
    private String ayudames;
    @Property
    @Persist
    private String ayudaanio;
    
    //zonas
    @InjectComponent
    private Zone busquedaZone;
    @InjectComponent
    private Zone validacionZone;
    @InjectComponent
    private Zone sancionZone;
    @InjectComponent
    private Zone tiposancionZone;
    @InjectComponent
    private Zone busquedamodalZone;
    @InjectComponent
    private Zone inhabilitacionZone;
    @InjectComponent
    private Zone autoridadmodalZone;
    @InjectComponent
    private Zone autoridadsanZone;
    @InjectComponent
    private Zone autoridadnotZone;
     
    //validaciones
    @Property
    @Persist
    private Boolean bmostrar;
    @Property
    @Persist
    private Boolean bmostrarrol;
    @Property
    @Persist
    private Boolean mostrarentidad;
    @Property
    @Persist
    private Boolean mostrarlista;
    @Property
    @Persist
    private Boolean mostrarfecha;
    @Property
    @Persist
    private Boolean mostrarnuevof;
    @Property
    @Persist
    private Boolean mostrardocu;
    @Property
    @Persist
    private Boolean autoridad;
    private int elemento=0;
    @Property
    @Persist
    private Boolean editando;
    @Property
    @Persist
    private Boolean veditar;
    @Property
    @Persist
    private Boolean vregistrar;
    @Property
    @Persist
    private Boolean vsuspender;
    @Property
    @Persist
    private Boolean mostrarautoridad;
    @Property
    @Persist
    private Boolean ocultar;
    
    @PageActivationContext
    private Sancion modificasancion;
    
    @Log
    public Sancion getModificasancion() {
        return modificasancion;
    }

    public void setModificasancion(Sancion modificasancion) {
        this.modificasancion = modificasancion;
    }
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SANCION);
        return null;
    }
    
    // inicio de la pagina
    @Log
    @SetupRender
    void inicio(){
        diascate="0";
        logueo();
        editando=false;
        bmostrar=false;
        mostrarfecha=false;
        nuevasancion=new Sancion();
        nuevofuncionario=new Funcionario();
        nuevapersona=new Persona_Sancion();
        limpiarbusqueda();
        limpiarsancion();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
            usua = (UsuarioAcceso) result.get(0);
            if (usua.getAccesoupdate() == 1) {
                veditar = true;
            }
            if (usua.getAccesoreport() == 1) {
                vregistrar = true;
            }
        }        
        if(usuario.getRolid()==2){
            bmostrarrol=false;
            bmostrar=true;
            mostrardocu=true;
            bestrabajador=true;
        }
        else{
            bmostrarrol=true;
        }
        System.out.println("llegooooo"+modificasancion);
        if(modificasancion!=null){
            System.out.println("llegooooo"+modificasancion.getId());
            nuevasancion=modificasancion;
            if (nuevasancion.getEstrabajador()==Boolean.FALSE){
            nuevapersona = nuevasancion.getPersona();
            }
            modificasancion=null;
            mostrar();
            editando=true;
            if(nuevasancion.getTipo_sancion().getTipoInhabilitacion().getCodigo()==2)
                vsuspender=false;
            else
                vsuspender=true;
        }
        mostrarlista=false;
        
    }
    
//    @Log
//    Object onActivate(){
//        return zonasDatos();
//    }
    @Log
    void mostrar(){
        System.out.println("MOSTRARX");
        limpiarbusqueda();
        limpiarsancion();
        if(nuevasancion.getEstrabajador()){
            bmostrar=true;
            mostrardocu=true;
            bestrabajador=true;
            bdocidentidad=nuevasancion.getTrabajador().getDocumentoidentidad();
            nuevapersona.setNroDocumento(nuevasancion.getTrabajador().getNroDocumento());
            nuevapersona.setNombres(nuevasancion.getTrabajador().getNombres());            
            nuevapersona.setApellidoPaterno(nuevasancion.getTrabajador().getApellidoPaterno());            
            nuevapersona.setApellidoMaterno(nuevasancion.getTrabajador().getApellidoMaterno());
            bregimen=nuevasancion.getCargoasignado().getCargoxunidad().getRegimenlaboral();
            bpuesto=nuevasancion.getCargoasignado().getCargoxunidad().getDen_cargo();
            bentidad=nuevasancion.getTrabajador().getEntidad().getDenominacion();
            if(nuevasancion.getCargoasignado().getEstado())
                bestadopuesto="Activo";
           else
                bestadopuesto="Inactivo";
        }
        else{
            bmostrar=false;
            mostrardocu=false;
            bdocidentidad=nuevasancion.getPersona().getDocumentoidentidad();
            System.out.println("IDX"+nuevasancion.getPersona().getId());
            Criteria c = session.createCriteria(Persona_Sancion.class);
            c.add(Restrictions.eq("id", nuevasancion.getPersona().getId()));
            nuevapersona = (Persona_Sancion)c.uniqueResult();
            nuevapersona.setNroDocumento(nuevasancion.getPersona().getNroDocumento());
            nuevapersona.setNombres(nuevasancion.getPersona().getNombres());            
            nuevapersona.setApellidoPaterno(nuevasancion.getPersona().getApellidoPaterno());            
            nuevapersona.setApellidoMaterno(nuevasancion.getPersona().getApellidoMaterno());
        }
        categoriasancion=nuevasancion.getCategoria_sancion();
        tiposancion=nuevasancion.getTipo_sancion();
        if(tiposancion.getTipoInhabilitacion().getCodigo()==1){
            int diastiposamax=(tiposancion.getTiempoMaxAnios()*365)+(tiposancion.getTiempoMaxMeses()*30)+(tiposancion.getTiempoMaxDias());
            int diastiposamin=(tiposancion.getTiempoMinAnios()*365)+(tiposancion.getTiempoMinMeses()*30)+(tiposancion.getTiempoMinDias());
            if(diastiposamax==diastiposamin){
                mostrarfecha=true;
                diascate=String.valueOf(tiposancion.getTiempoMaxAnios()*365+tiposancion.getTiempoMaxMeses()*30+tiposancion.getTiempoMaxDias());
                System.out.println("aquiiiii"+diascate);
            }
        }
        else{
            mostrarfecha=true;
        }       
        if (nuevasancion.getFecha_docnot()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocnot = formatoDeFecha.format(nuevasancion.getFecha_docnot());
        }
        if (nuevasancion.getFecha_docsan()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocsan = formatoDeFecha.format(nuevasancion.getFecha_docsan());
        }
        if (nuevasancion.getFechaini_inha()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fecinicio = formatoDeFecha.format(nuevasancion.getFechaini_inha());
        }
        if (nuevasancion.getFechafin_inha()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fecfin = formatoDeFecha.format(nuevasancion.getFechafin_inha());
        }
        autoridadnot=nuevasancion.getAutoridadnot().getApellidoPaterno()+" "+nuevasancion.getAutoridadnot().getApellidoMaterno()+" "+nuevasancion.getAutoridadnot().getNombres();
        autoridadsan=nuevasancion.getAutoridadsan().getApellidoPaterno()+" "+nuevasancion.getAutoridadsan().getApellidoMaterno()+" "+nuevasancion.getAutoridadsan().getNombres();
    }
    
//    @Log
//    public GenericSelectModel<TipoSancion> getBuscarTipoSancion() {
//        
//    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidadauto() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }   
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanregimenlaboral() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<TipoSancion> getBeantiposancion() {
        List<Lk_Tipo_Sancion> list;
        Criteria c1 = session.createCriteria(TipoSancion.class);
        if (bregimen==null && categoriasancion==null){
            return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);        
        }
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class); 
        if(bregimen!=null){
            c.add(Restrictions.eq("reg_laboral", bregimen.getId()));
        }
        if(categoriasancion!=null){
            c.add(Restrictions.eq("categoria", categoriasancion.getId()));
        }
        c.setProjection(Projections.distinct(Projections.property("id_tipo")));
        
        if (!c.list().isEmpty()){            
            c1.add(Restrictions.in("id", c.list()));
            return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);        
        }else{        
        c1.add(Restrictions.isNull("id"));}
        list = c.list();
        return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);
        
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeancategoriasancion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipodocnot() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipodocsan() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    Object onBuscarenti(){
        mostrarentidad=true;
        mostrarlista=false;
        bdenoentidad=null;
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("busquedamodalZone",busquedamodalZone.getBody());
    }
    Object onBuscartraba(){
        mostrarentidad=false;
        mostrarlista=false;
        bnomtrabajador=null;
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("busquedamodalZone",busquedamodalZone.getBody());
    }
    
    Object onBuscarpersona(){
      formvalidacion.clearErrors();
      if(bdocidentidad==null){
          envelope.setContents("Tiene que ingresar Tipo Documento");
          return zonasDatos();
      }
      if(nuevapersona.getNroDocumento()==null){
          envelope.setContents("Tiene que ingresar Numero de Documento");
          return zonasDatos();
      }
      if(bdocidentidad.getCodigo()!=1){
          envelope.setContents("La consulta solo se permite para DNI");
          return zonasDatos();
      }     
      
      List<Trabajador> busqueda=getListaTrabajador(nuevapersona.getNroDocumento());
      if(busqueda.size()>0){
          envelope.setContents("Persona ya Registrada como Trabajador");
          return zonasDatos();
      }      
      List<Persona_Sancion> busqueda_persona=getListaPersona(nuevapersona.getNroDocumento());
      if(busqueda_persona.size()>0){
          nuevapersona.setNombres(busqueda_persona.get(0).getNombres());
          nuevapersona.setApellidoPaterno(busqueda_persona.get(0).getApellidoPaterno());
          nuevapersona.setApellidoMaterno(busqueda_persona.get(0).getApellidoMaterno());
          nuevasancion.setPersona(busqueda_persona.get(0));
          nuevasancion.setTrabajador(null);
          nuevasancion.setCargoasignado(null);
          envelope.setContents("Persona ya Registrada");          
          return zonasDatos();
      }
      Criteria c;
      // VERIFICACION DE LOS PARAMETROS CON RESPECTO AL NRO DE PETICIONES TOTALES
       c = session.createCriteria(ConfiguracionAcceso.class);
       ConfiguracionAcceso parametro =  (ConfiguracionAcceso)c.uniqueResult();
       System.out.println("NRO CONSULTAS - EN TOTAL "+parametro.getNroConsultasActuales());
       if (parametro.getNroConsultasActuales()==null ||parametro.getNroConsultasActuales()==0){
            envelope.setContents("Se superaron el # de consultas al service por el dia de hoy");
            return zonasDatos();    
       }
       
    // VERIFICACION DE LOS PARAMETROS CON RESPECTO AL NRO DE PETICIONES (ENTIDAD)        
       System.out.println("NRO CONSULTAS - PARA LA ENTIDAD "+eue.getPeticiones_ws_Reniec());
       if (usuario.getEntidad().getPeticiones_ws_Reniec()==null || usuario.getEntidad().getPeticiones_ws_Reniec()== 0){ 
            envelope.setContents("Se superaron el # de consultas al service para la entidad por el dia de hoy");
            return zonasDatos();           
        }
      
        try {
            ServicioReniec sre=new ServicioReniec();
            sre.obtenerToken();
            if(sre.validarToken(session)){
                List<String> listare= sre.obtenerResultado(nuevapersona.getNroDocumento());
                // DISMINUCION DE NRO DE PETICIONES
                usuario.getEntidad().setPeticiones_ws_Reniec(usuario.getEntidad().getPeticiones_ws_Reniec()-1);
                session.saveOrUpdate(usuario.getEntidad());
                parametro.setNroConsultasActuales(parametro.getNroConsultasActuales()-1);
                session.saveOrUpdate(parametro);
                
                if (sre.validarEstadoConsulta(listare.get(0),session)){
                    nuevapersona.setNombres(listare.get(4));
                    nuevapersona.setApellidoPaterno(listare.get(1));
                    nuevapersona.setApellidoMaterno(listare.get(2));
                  //  nuevapersona=new Persona_Sancion();                    
                    nuevapersona.setDireccion(listare.get(11));
                    if(listare.get(13).equals("1")){
                        nuevapersona.setSexo("M");
                    }
                    else if(listare.get(13).equals("2")){
                        nuevapersona.setSexo("F");
                    }
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
                    nuevapersona.setFecha_nacimiento(formatoFecha.parse(listare.get(14)));
                }else{
                    envelope.setContents(sre.mensajeError);//ERROR EN CONSULTA
//                    System.out.println("errorrrrr"+bnumerodocumento);
//                    System.out.println(treniec.mensajeError);
                } 
                        return zonasDatos();
            }else{
                envelope.setContents(sre.mensajeError);//ERROR TOKEN
//                System.out.println(treniec.mensajeError);
            }
                    return zonasDatos();
        }catch (Exception ex) {
            System.out.println(ex.getCause());
                    return zonasDatos();
        }
        
    //    return zonasDatos();
    }
    
    @Log
    public List<Trabajador> getListaTrabajador(String documento) {
         Criteria c = session.createCriteria(Trabajador.class);
         c.add(Restrictions.eq("nroDocumento", documento));
         return c.list();
    }
    
    @Log
    public List<Persona_Sancion> getListaPersona(String documento) {
         Criteria c = session.createCriteria(Persona_Sancion.class);
         c.add(Restrictions.eq("nroDocumento", documento));
         return c.list();
    }
    
//    @Log
//    void onSelectedFromBuscartraba() {        
//        mostrarentidad=false;
//        System.out.println("aquiiiiiiiii"+mostrarentidad);
//        elemento=2;
//    }
//    
//    @Log
//    void onSelectedFromBuscarpersona(){
//        elemento=3;
//    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusqueda() {
      if(elemento==1){
          mostrarlista=false;
          bdenoentidad=null;
           return zonasDatos();
      }  
      else if(elemento==2){
          mostrarlista=false;
          bnomtrabajador=null;
          return zonasDatos();
      }
      else if(elemento==3){
          
      }
      return busquedaZone.getBody();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformvalidacion() {
        nuevapersona=new Persona_Sancion(); 
      if(bestrabajador){
          bmostrar=true;
          mostrardocu=true;
      }
      else{
          bmostrar=false;
          mostrardocu=false;
          bregimen=null;
          bpuesto=null;
          bestadopuesto=null;
          nuevasancion.setTrabajador(null);
          nuevasancion.setCargoasignado(null);
      }
      return zonasDatos();
    }
    
    @Log
    Object onBuscarautoridadnot(){  
        mostrarnuevof=false;
        autoridad=true;
        return  autoridadnotZone.getBody();
    }
    
    @Log
    Object onBuscarautoridadsan(){  
        mostrarnuevof=false;
        autoridad=false;
        return new MultiZoneUpdate("autoridadsanZone", autoridadsanZone.getBody());
    }

     @Log
     public List<LkBusquedaEntidad> getListadoEntidades() {
         Criteria c = session.createCriteria(LkBusquedaEntidad.class);
         c.add(Restrictions.eq("estado", true));
         if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
         }
         nroentidad = Integer.toString(c.list().size());
         return c.list();
    }
     
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedaentidad() {   
        mostrarlista=true;
        return busquedamodalZone.getBody();
    }
     
    @Persist
    @Property
    private String nroentidad; 
    @Persist
    @Property
    private String nrotrabajador;
    @Persist
    @Property
    private String nroautoridad;
    
    @Log
    public List<LkBusquedaTrabajadorSan> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajadorSan.class);
        if (bnomtrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador","%"+ bnomtrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if(usuario.getRolid()==2){
            c.add(Restrictions.eq("entidad_id", eue.getId()));
        }
        else{
            if(entidadbusqueda!=null){
                c.add(Restrictions.eq("entidad_id", entidadbusqueda.getId()));
            }
        }
        nrotrabajador = Integer.toString(c.list().size());
        return c.list();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedatrabajador(){
        if(bnomtrabajador!=null && !bnomtrabajador.equals("")){
            mostrarlista=true;
        }
        else{
            mostrarlista=false;
        }
        return busquedamodalZone.getBody();
    }
    
    @Log
    Object onActionFromSeleccionarentidad(Entidad enti) {
        entidadbusqueda=enti;
        bentidad = enti.getDenominacion();        
        return busquedaZone.getBody();
    }
    
    @Log
    Object onActionFromSeleccionaTrabajador(LkBusquedaTrabajadorSan btra) {
        limpiarbusqueda();
        bdocidentidad=btra.getDocumentoidentidad();
        nuevapersona.setNroDocumento(btra.getNrodocumento());
        nuevapersona.setNombres(btra.getNombres());
        nuevapersona.setApellidoPaterno(btra.getApellidoPaterno());
        nuevapersona.setApellidoMaterno(btra.getApellidoMaterno());
        bregimen=btra.getRegimenlaboral();
        bpuesto=btra.getDen_cargo();
        bestadopuesto=btra.getEstadocargo();        
        nuevasancion.setCargoasignado((CargoAsignado) session.load(CargoAsignado.class, btra.getId()));
        nuevasancion.setTrabajador((Trabajador) session.load(Trabajador.class, btra.getTrabajador_id()));
        bentidad=nuevasancion.getTrabajador().getEntidad().getDenominacion();
        nuevasancion.setPersona(null);
        calcular(Integer.parseInt(btra.getTiempo_dias()));
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("sancionZone", sancionZone.getBody());
    }
    
    @Log
    public List<LkBusquedaFuncionario> getFuncionarios() {
        Criteria c = session.createCriteria(LkBusquedaFuncionario.class);
        if (bnomautoridad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad + "%").ignoreCase()).add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad.replaceAll("n", "ñ") + "%").ignoreCase()));
        } 
        nroautoridad=Integer.toString(c.list().size());
        return c.list();
    }
    
    @Persist
    @Property
    private String nrotrabaauto;
    
    @Log
    public List<LkBusquedaTrabajadorAuto> getTrabajadoresAuto() {
//        List<LkBusquedaTrabajadorAuto> cre=null;
//        Criteria c; 
//        if (bnomtrabaautoridad != null) {
//            c= session.createCriteria(LkBusquedaTrabajadorAuto.class);
//            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("n", "ñ") + "%").ignoreCase()));
//            nrotrabaauto=Integer.toString(c.list().size());
//            cre=c.list();
//        }
//        else{
//            cre=null;
//        }
//        return cre;
        Criteria c = session.createCriteria(LkBusquedaTrabajadorAuto.class);
        if (bnomtrabaautoridad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }     
        nrotrabaauto=Integer.toString(c.list().size());
        return c.list();
    }
    
    @Log
    Object onActionFromSeleccionaTrabajadorAuto(LkBusquedaTrabajadorAuto traauto) {
        nuevofuncionario.setApellidoMaterno(traauto.getApellidoMaterno());
        nuevofuncionario.setApellidoPaterno(traauto.getApellidoPaterno());
        nuevofuncionario.setNombres(traauto.getNombres());
        nuevofuncionario.setNroDocumento(traauto.getNroDocumento());    
        nuevofuncionario.setDocumentoidentidad(traauto.getDocumentoidentidad());
        return autoridadmodalZone.getBody();
    }
    
    @Log
    void onSelectedFromNuevautoridad(){
        elemento=4;
    }   
    
    @Log
    Object onActionFromSeleccionaFuncionario(LkBusquedaFuncionario fun) {        
        Funcionario sele=new Funcionario();
        sele=(Funcionario) session.load(Funcionario.class, fun.getId()); 
        if(autoridad){
            autoridadnot=fun.getNombrefuncionario();
            nuevasancion.setAutoridadnot(sele);            
        }
        else{
            autoridadsan=fun.getNombrefuncionario();
            nuevasancion.setAutoridadsan(sele); 
        }              
        return new MultiZoneUpdate("autoridadsanZone", autoridadsanZone.getBody()).add("autoridadnotZone", autoridadnotZone.getBody());
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedaautoridad(){
        if(elemento==4){
            mostrarnuevof=true;
            mostrarautoridad=false;
        }else{
            mostrarlista=true;
        }
        
        return autoridadmodalZone.getBody();
    }
    
    @Log
    Object onBuscartrabaauto(){  
        System.out.println("aquiiiiiii"+bnomtrabaautoridad);
        if(bnomtrabaautoridad!=null && !bnomtrabaautoridad.equals("")){
            mostrarautoridad=true;
        }
        else{
            mostrarautoridad=false;
        }
        return autoridadmodalZone.getBody();
    }
    
    @Log
    Object onCancelmodal3(){  
        mostrarnuevof=false;
        return autoridadmodalZone.getBody();
    }
    @Log
    Object onResetmodal(){  
        nuevofuncionario=new Funcionario();
        return autoridadmodalZone.getBody();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformnuevaautoridad(){
        Criteria c = session.createCriteria(LkBusquedaFuncionario.class);
        c.add(Restrictions.eq("nroDocumento", nuevofuncionario.getNroDocumento()));
        if(!c.list().isEmpty()){
            return autoridadmodalZone.getBody();
        }
        session.saveOrUpdate(nuevofuncionario);
        session.flush();
        nuevofuncionario=new Funcionario();
        mostrarnuevof=false;
        return autoridadmodalZone.getBody();
    }
    
    
    
    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("validacionZone", validacionZone.getBody()).add("sancionZone", sancionZone.getBody());
        return mu;

    }   
        
    void limpiarbusqueda(){
        bdocidentidad=null;
        nuevapersona=new Persona_Sancion();
        bregimen=null;
        bpuesto=null;
        bestadopuesto=null;
        bentidad=null;
    }
    
    void limpiarsancion(){
        categoriasancion=null;
        tiposancion=null;
        autoridadnot=null;
        autoridadsan=null;
        fechadocnot=null;
        fechadocsan=null;
        fecinicio=null;
        fecfin=null;  
        if(usuario.getRolid()==2){
            bestrabajador=true;
        }
        else{
            bestrabajador=false;
        }
        ayudadia=null;
        ayudames=null;
        ayudaanio=null;
    }
    
    void calcular(int dias){
//        System.out.println("aquiiiiiii"+dias);     
        int anio=(dias/365);
        int mes=((dias%365)/30);
        int dia=((dias%365)%30);
        nuevasancion.setTiem_ser_anio(String.valueOf(anio));
        nuevasancion.setTiem_ser_mes(String.valueOf(mes));
        nuevasancion.setTiem_ser_dia(String.valueOf(dia));     
    }
    
    @Log
    Object onReset(){
        nuevasancion=new Sancion();
        limpiarbusqueda();
        limpiarsancion();
        return zonasDatos();
    }
    @Log
    Object onCancel(){
        return "ConsultaSanciones";
    }
    
    void onSelectedFromCalc() {
        elemento=1;       
        if (fechadocnot != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docnot = (Date) formatoDelTexto.parse(fechadocnot);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            fecha_inicio=calcularfechainicio();
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fecinicio = formatoDeFecha.format(fecha_inicio);
            fecfin = formatoDeFecha.format(calcularfecha());
        }       
        
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformsancion(){ 
        
        formsancion.clearErrors();
        if(elemento==1){
            return new MultiZoneUpdate("sancionZone", sancionZone.getBody());
        }else{
        
            // VALIDACION DEL DOCUMENTO DE IDENTIDAD
        if (bdocidentidad.getCodigo()==1){
            if(nuevapersona.getNroDocumento().length()>8){ 
                envelope.setContents("El número de documento debe tener 8 dígitos (y solo números)");   return zonasDatos();}
            try { Integer.parseInt(nuevapersona.getNroDocumento());} catch (NumberFormatException ex) {
                envelope.setContents("El número de documento debe tener 8 dígitos (y solo números)");  return zonasDatos(); }      
        }
            
            
        if (fechadocnot != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docnot = (Date) formatoDelTexto.parse(fechadocnot);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
            if(tiposancion.getTipoInhabilitacion().getCodigo()==1){
                fecha_inicio=calcularfechainicio();
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
                fecinicio = formatoDeFecha.format(fecha_inicio);
                int diastiposamax=(tiposancion.getTiempoMaxAnios()*365)+(tiposancion.getTiempoMaxMeses()*30)+(tiposancion.getTiempoMaxDias());
                int diastiposamin=(tiposancion.getTiempoMinAnios()*365)+(tiposancion.getTiempoMinMeses()*30)+(tiposancion.getTiempoMinDias());
                if(diastiposamax==diastiposamin){
                    fecha_fin=calcularfechafin();
                    formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
                    fecfin = formatoDeFecha.format(fecha_fin);
                    System.out.println("finnnnnn"+fecha_fin);
                }
            }
        }
        
        
        if(bestrabajador){
            if(nuevasancion.getTrabajador()==null){
                envelope.setContents("Tiene que seleccionar un Trabajador");
                return zonasDatos();
            }
        }
        else{
            if(!validarpersona()){
                return zonasDatos();
            }
        }
        if(autoridadnot==null){
            envelope.setContents("Tiene que ingresar la Autoridad que notifica");
            return zonasDatos();
        }
        if(autoridadsan==null){
            envelope.setContents("Tiene que ingresar la Autoridad que notifica");
            return zonasDatos();
        }
        if (fechadocnot != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docnot = (Date) formatoDelTexto.parse(fechadocnot);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        
        if(tiposancion.getTipoInhabilitacion().getCodigo()==1){
            int diastiposamax=(tiposancion.getTiempoMaxAnios()*365)+(tiposancion.getTiempoMaxMeses()*30)+(tiposancion.getTiempoMaxDias());
            int diastiposamin=(tiposancion.getTiempoMinAnios()*365)+(tiposancion.getTiempoMinMeses()*30)+(tiposancion.getTiempoMinDias());
            //System.out.println("aquiiiii-"+calcularperiodo()+"-"+diastiposamax+"-"+diastiposamin);
            
            fecha_inicio=calcularfechainicio();
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fecinicio = formatoDeFecha.format(fecha_inicio);            
            if(diastiposamax==diastiposamin){
                //calculo fecha automatica
                fecha_fin=calcularfechafin();
                System.out.println("finnnnnn"+fecha_fin);
            }else{
                if(calcularperiodo()>diastiposamin && calcularperiodo()<diastiposamax){  

                }
                else{
                    envelope.setContents("El Periodo de Inhabilitación debe ser mayor a : "+String.valueOf(diastiposamin)+" días y menor a : "+String.valueOf(diastiposamax)+" dias");
                    return zonasDatos();
                }
            }
        }       
        
        if (fechadocsan != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docsan = (Date) formatoDelTexto.parse(fechadocsan);
                if(fecha_docsan.after(fecha_docnot)){
                   envelope.setContents("La Fecha de Documento que Sanciona debe ser menor a la Fecha de Notificación");
                   return zonasDatos();
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }   
        
        if (fecinicio != null) {
            
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_inicio = (Date) formatoDelTexto.parse(fecinicio);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }            
            if(fecha_inicio.before(new Date()) || fecha_inicio.equals(new Date())){
                nuevasancion.setSancion_estado(getEstados((long) 1).get(0));
            }
            else{
                nuevasancion.setSancion_estado(getEstados((long) 2).get(0));
            }            
        }else{
            nuevasancion.setSancion_estado(getEstados((long) 2).get(0));
        }
        if (fecfin != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_fin = (Date) formatoDelTexto.parse(fecfin);
                if(fecha_fin.before(fecha_inicio)) {
                   envelope.setContents("La Fecha de Fin no puede ser menor a la Fecha de Inicio");
                   return zonasDatos();
                }                
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
            
        
        if(bestrabajador){           
            nuevasancion.setEstrabajador(true);
        }else{
            
            if(nuevasancion.getPersona()!=null){
//                System.out.println("PERSONA "+modificasancion.getPersona().getId()+" - "+nuevapersona.getId());
                nuevasancion.setEstrabajador(false);
                session.saveOrUpdate(nuevapersona);  
                session.flush();
            }
            else{
                List<Trabajador> busqueda=getListaTrabajador(nuevapersona.getNroDocumento());
                if(busqueda.size()>0){
                    envelope.setContents("Persona ya Registrada como Trabajador");
                    return zonasDatos();
                }                
                nuevapersona.setDocumentoidentidad(bdocidentidad);
                nuevasancion.setEstrabajador(false);                
                session.saveOrUpdate(nuevapersona);            
                session.flush();
                nuevasancion.setPersona(nuevapersona);
            }            
        }
        

//        TipoSancion tiposa=new TipoSancion();
//        tiposa=(TipoSancion) session.load(TipoSancion.class, tiposancion.getId_tipo());
        nuevasancion.setCategoria_sancion(categoriasancion);
        nuevasancion.setFecha_docnot(fecha_docnot);
        nuevasancion.setFecha_docsan(fecha_docsan);
        nuevasancion.setFechafin_inha(fecha_fin);
        nuevasancion.setFechaini_inha(fecha_inicio);
        nuevasancion.setTipo_sancion(tiposancion);       
        session.saveOrUpdate(nuevasancion);
        session.flush(); 
        new Logger().loguearOperacion(session, usuario, String.valueOf(nuevasancion.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SANCION);
        if(editando){
            envelope.setContents(helpers.Constantes.SANCION_MODIFICADA_EXITO);
        }
        else{
            envelope.setContents(helpers.Constantes.SANCION_CREADA_EXITO);
        }
        nuevasancion=new Sancion();
        limpiarbusqueda();
        limpiarsancion();
        return zonasDatos();
        }
    }  
    
    @Log
    public List<DatoAuxiliar> getEstados(long cod) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(Restrictions.eq("codigo", cod));
        return c.list();
    }
    
    int calcularperiodo(){   
        String consulta ="SELECT 1 ID,to_number(to_date('"+fecfin+"','dd/mm/yyyy') - to_date('"+fecinicio+"','dd/mm/yyyy')) DIAS from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkConsultaPeriodo.class);  
        List result = query.list();        
        LkConsultaPeriodo lkcondos = (LkConsultaPeriodo) result.get(0);
        return lkcondos.getDias();
    }
    Date calcularfecha(){
        int dias,meses,anios;
        if(ayudadia==null) dias=0;
        else dias=Integer.parseInt(ayudadia);
        if(ayudames==null) meses=0;
        else meses=Integer.parseInt(ayudames);
        if(ayudaanio==null) anios=0;
        else anios=Integer.parseInt(ayudaanio);
        int totaldias=dias+meses*30+anios*365;
        String consulta ="SELECT 1 ID,to_date('"+fecinicio+"','dd/mm/yyyy') + to_number('"+totaldias+"') FECHA  from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkSumaFecha.class);  
        List result = query.list();        
        LkSumaFecha lkcondos = (LkSumaFecha) result.get(0);
        return lkcondos.getFecha();
    }
    Date calcularfechainicio(){
//        String consulta ="SELECT 1 ID,to_date('"+fechadocnot+"','dd/mm/yyyy') + to_number('"+1+"') FECHA  from dual";
//        Query query =session.createSQLQuery(consulta).addEntity(LkSumaFecha.class);  
//        List result = query.list();        
//        LkSumaFecha lkcondos = (LkSumaFecha) result.get(0);
        Calendar c1 = Calendar.getInstance(); 
        c1.setTime(fecha_docnot);
        c1.add(Calendar.DATE,1);
        return c1.getTime();
    }
    Date calcularfechafin(){
        String consulta ="SELECT 1 ID,to_date('"+fecinicio+"','dd/mm/yyyy') + to_number('"+Integer.parseInt(diascate) +"') FECHA  from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkSumaFecha.class);  
        List result = query.list();        
        LkSumaFecha lkcondos = (LkSumaFecha) result.get(0);
        System.out.println("fechafinnnnnnnnn"+fecinicio+diascate);
        return lkcondos.getFecha();
    }
    boolean validarpersona(){
        boolean vali=true;
        if(bdocidentidad==null){
            envelope.setContents("Tiene que ingresar Tipo Documento");
            vali=false;
        }
        if(nuevapersona.getNroDocumento()==null){
            envelope.setContents("Tiene que ingresar Numero Documento");
            vali=false;
        }
        if(nuevapersona.getNombres()==null){
            envelope.setContents("Tiene que ingresar el Nombre de la Persona");
            vali=false;
        }
        if(nuevapersona.getApellidoPaterno()==null){
            envelope.setContents("Tiene que ingresar el Apellido Paterno");
            vali=false;
        }
        if(nuevapersona.getApellidoMaterno()==null){
            envelope.setContents("Tiene que ingresar el Apellido Materno");
            vali=false;
        }
        List<Trabajador> busqueda=getListaTrabajador(nuevapersona.getNroDocumento());
        return vali;
    }
    
    @Log
    void onBnomtrabaautoridadChanged() {
        bnomtrabaautoridad=_request.getParameter("param");
    }
    
    @Log
    void onBnumerodocumentoChanged() {
        nuevapersona.setNroDocumento(_request.getParameter("param"));
    }
    
    @Log
    void onBnombresChanged() {
        nuevapersona.setNombres(_request.getParameter("param"));
    }
    
    @Log
    void onBapaternoChanged() {
        nuevapersona.setApellidoPaterno(_request.getParameter("param"));
    }
    
    @Log
    void onBamaternoChanged() {
        nuevapersona.setApellidoMaterno(_request.getParameter("param"));
    }
    
    @Log
    Object onValueChangedFromCategoria_sancion(DatoAuxiliar dato) {
           return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());
    }
    @Persist
    @Property
    private String diascate;
    @Log
    Object onValueChangedFromTipo_sancion(TipoSancion dato) {
           if(dato!=null){
               if(dato.getTipoInhabilitacion().getCodigo()==2){
                    mostrarfecha=true;
                    fecinicio=null;
                    fecfin=null;
                    diascate="0";
                } 
                else{
                    mostrarfecha=false;
                    diascate="0";
                    if(dato.getTiempoMaxAnios()==dato.getTiempoMinAnios() && dato.getTiempoMaxMeses()==dato.getTiempoMinMeses() &&dato.getTiempoMaxDias()==dato.getTiempoMinDias()){
                        mostrarfecha=true;
                        fecinicio=null;
                        fecfin=null;
                        diascate=String.valueOf(dato.getTiempoMaxAnios()*365+dato.getTiempoMaxMeses()*30+dato.getTiempoMaxDias());
                    }
                }
           }else{
               mostrarfecha=true;
           }
           
           return new MultiZoneUpdate("inhabilitacionZone", inhabilitacionZone.getBody());
    } 
    
    @Log
    Object onValueChangedFromDocumento_identidad(DatoAuxiliar dato){
        if(dato!=null){
            if(dato.getCodigo()==1)
                mostrardocu=true;        
            else
                mostrardocu=false;
        }
        else{
            mostrardocu=false;
        }
        limpiarbusqueda();        
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody());
    } 

}
