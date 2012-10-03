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
import helpers.ServicioReniec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private Session session;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    private Envelope envelope;    
    @Component(id = "formsancion")
    private Form formsancion;
    @Component(id = "formvalidacion")
    private Form formvalidacion;
    
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
    private String bnumerodocumento;
    @Property
    @Persist
    private String bnombres;
    @Property
    @Persist
    private String bapaterno;
    @Property
    @Persist
    private String bamaterno;
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
    private Lk_Tipo_Sancion tiposancion;
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
    
    @PageActivationContext
    private Sancion modificasancion;
    
    @Log
    public Sancion getModificasancion() {
        return modificasancion;
    }

    public void setModificasancion(Sancion modificasancion) {
        this.modificasancion = modificasancion;
    }
    
    // inicio de la pagina
    @Log
    @SetupRender
    void inicio(){     
        editando=false;
        bmostrar=false;
        mostrarfecha=false;
        nuevasancion=new Sancion();
        nuevofuncionario=new Funcionario();
        nuevapersona=new Persona_Sancion();
        if(usuario.getRolid()==2){
            bmostrarrol=false;
            bmostrar=true;
            mostrardocu=true;
        }
        else{
            bmostrarrol=true;            
        }
        System.out.println("llegooooo"+modificasancion);
        if(modificasancion!=null){
            System.out.println("llegooooo"+modificasancion.getId());
            nuevasancion=modificasancion;            
            modificasancion=null;
            mostrar();
            editando=true;
        }
    }
    
    @Log
    Object onActivate(){
        return zonasDatos();
    }
    
    void mostrar(){
        limpiarbusqueda();
        limpiarsancion();
        if(nuevasancion.getEstrabajador()){
            bmostrar=true;
            mostrardocu=true;
            bestrabajador=true;
            bdocidentidad=nuevasancion.getTrabajador().getDocumentoidentidad();
            bnumerodocumento=nuevasancion.getTrabajador().getNroDocumento();
            bnombres=nuevasancion.getTrabajador().getNombres();            
            bapaterno=nuevasancion.getTrabajador().getApellidoPaterno();            
            bamaterno=nuevasancion.getTrabajador().getApellidoMaterno();
            bregimen=nuevasancion.getCargoasignado().getCargoxunidad().getRegimenlaboral();
            bpuesto=nuevasancion.getCargoasignado().getCargoxunidad().getDen_cargo();
            if(nuevasancion.getCargoasignado().getEstado())
                bestadopuesto="Activo";
           else
                bestadopuesto="Inactivo";
        }
        else{
            bmostrar=false;
            mostrardocu=false;
            bdocidentidad=nuevasancion.getPersona().getDocumentoidentidad();
            bnumerodocumento=nuevasancion.getPersona().getNroDocumento();
            bnombres=nuevasancion.getPersona().getNombres();            
            bapaterno=nuevasancion.getPersona().getApellidoPaterno();            
            bamaterno=nuevasancion.getPersona().getApellidoMaterno();
        }
        categoriasancion=nuevasancion.getCategoria_sancion();
        tiposancion=(Lk_Tipo_Sancion) session.load(Lk_Tipo_Sancion.class, getBuscarTipoSancion().get(0).getId());
        
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
    
    @Log
    public List<Lk_Tipo_Sancion> getBuscarTipoSancion() {
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class);
        c.add(Restrictions.eq("id_tipo", nuevasancion.getTipo_sancion().getId()));
        return c.list();
    }
    
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
    public GenericSelectModel<Lk_Tipo_Sancion> getBeantiposancion() {
        List<Lk_Tipo_Sancion> list;
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class); 
        if(bregimen!=null){
            c.add(Restrictions.eq("reg_laboral", bregimen.getId()));
        }
        if(categoriasancion!=null){
            c.add(Restrictions.eq("categoria", categoriasancion.getId()));
        }
        list = c.list();
        return new GenericSelectModel<Lk_Tipo_Sancion>(list, Lk_Tipo_Sancion.class, "descripcion", "id_tipo", _access);
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
      List<Trabajador> busqueda=getListaTrabajador(bnumerodocumento);
      if(busqueda.size()>0){
          bnombres=busqueda.get(0).getNombres();
          bapaterno=busqueda.get(0).getApellidoPaterno();
          bamaterno=busqueda.get(0).getApellidoMaterno();
          formvalidacion.recordError("Persona ya Registrada como Trabajador");
          return zonasDatos();
      }      
      List<Persona_Sancion> busqueda_persona=getListaPersona(bnumerodocumento);
      if(busqueda_persona.size()>0){
          bnombres=busqueda_persona.get(0).getNombres();
          bapaterno=busqueda_persona.get(0).getApellidoPaterno();
          bamaterno=busqueda_persona.get(0).getApellidoMaterno();
          formvalidacion.recordError("Persona ya Registrada");
          return zonasDatos();
      }
        try {
            ServicioReniec sre=new ServicioReniec();
            sre.obtenerToken();
            if(sre.validarToken()){
                List<String> listare= sre.obtenerResultado(bnumerodocumento);
                if (sre.validarEstadoConsulta(listare.get(0))){
                    bnombres=listare.get(4);
                    bapaterno=listare.get(1);
                    bamaterno=listare.get(2);
                }else{
                    formvalidacion.recordError(sre.mensajeError);//ERROR EN CONSULTA
                    System.out.println("errorrrrr"+bnumerodocumento);
//                    System.out.println(treniec.mensajeError);
                }                
            }else{
                formvalidacion.recordError(sre.mensajeError);//ERROR TOKEN
//                System.out.println(treniec.mensajeError);
            }
            
        }catch (Exception ex) {
            System.out.println(ex.getCause());
        }
        
        return zonasDatos();
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
         if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
         }
         nroregistros = Integer.toString(c.list().size());
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
    private String nroregistros; 
    
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
        return c.list();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedatrabajador(){
        mostrarlista=true;
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
        bnumerodocumento=btra.getNrodocumento();
        bnombres=btra.getNombres();
        bapaterno=btra.getApellidoPaterno();
        bamaterno=btra.getApellidoMaterno();
        bregimen=btra.getRegimenlaboral();
        bpuesto=btra.getDen_cargo();
        bestadopuesto=btra.getEstadocargo();
        nuevasancion.setCargoasignado((CargoAsignado) session.load(CargoAsignado.class, btra.getId()));
        nuevasancion.setTrabajador((Trabajador) session.load(Trabajador.class, btra.getTrabajador_id()));
        calcular(Integer.parseInt(btra.getTiempo_dias()));
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("sancionZone", sancionZone.getBody());
    }
    
    @Log
    public List<LkBusquedaFuncionario> getFuncionarios() {
        Criteria c = session.createCriteria(LkBusquedaFuncionario.class);
        if (bnomautoridad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad + "%").ignoreCase()).add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombrefuncionario","%"+ bnomautoridad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }     
        return c.list();
    }
    
    @Log
    public List<LkBusquedaTrabajadorAuto> getTrabajadoresAuto() {
        Criteria c = session.createCriteria(LkBusquedaTrabajadorAuto.class);
        if (bnomtrabaautoridad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador","%"+ bnomtrabaautoridad.replaceAll("n", "ñ") + "%").ignoreCase()));
        }     
        return c.list();
    }
    
    @Log
    Object onActionFromSeleccionaTrabajadorAuto(LkBusquedaTrabajadorAuto traauto) {
        nuevofuncionario.setApellidoMaterno(traauto.getApellidoMaterno());
        nuevofuncionario.setApellidoPaterno(traauto.getApellidoPaterno());
        nuevofuncionario.setNombres(traauto.getNombres());
        nuevofuncionario.setNroDocumento(traauto.getNroDocumento());       
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
        }else{
            mostrarlista=true;
        }
        
        return autoridadmodalZone.getBody();
    }
    
    @Log
    Object onBuscartrabaauto(){  
        System.out.println("aquiiiiiii"+bnomtrabaautoridad);
        return autoridadmodalZone.getBody();
    }
    
    @Log
    Object onCancelmodal3(){  
        mostrarnuevof=false;
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
        bnumerodocumento=null;
        bnombres=null;
        bapaterno=null;
        bamaterno=null;
        bregimen=null;
        bpuesto=null;
        bestadopuesto=null;
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
        bestrabajador=false;
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
    void onSelectedFromReset(){
        elemento=6;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformsancion(){
     if(elemento==6){
         nuevasancion=new Sancion();
         limpiarbusqueda();
         limpiarsancion();
         return zonasDatos();
     }else{        
        if(bestrabajador){
            if(nuevasancion.getTrabajador()==null){
                formsancion.recordError("Tiene que seleccionar un Trabajador");
                return zonasDatos();
            }
        }
        else{
            if(!validarpersona()){
                return zonasDatos();
            }
        }
        if(autoridadnot==null){
            formsancion.recordError("Tiene que ingresar la Autoridad que notifica");
            return zonasDatos();
        }
        if(autoridadsan==null){
            formsancion.recordError("Tiene que ingresar la Autoridad que notifica");
            return zonasDatos();
        }
        
        if(tiposancion.getCodigo()==1){
//            if(calcularperiodo(fecinicio,fechadocnot)!=1){
//                formsancion.recordError("La Fecha de Inicio (Periodo de Inhabilitacion) debe ser mayor en un 1 día a la Fecha de Notificacion");
//                return zonasDatos();
//            }
//            if(calculardia()!=1){
//                formsancion.recordError("La Fecha de Inicio (Periodo de Inhabilitacion) debe ser mayor en un 1 día a la Fecha de Notificacion");
//                return zonasDatos();
//            }
            int diastiposamax=(tiposancion.getTiempoMaxAnios()*365)+(tiposancion.getTiempoMaxMeses()*30)+(tiposancion.getTiempoMaxDias());
            int diastiposamin=(tiposancion.getTiempoMinAnios()*365)+(tiposancion.getTiempoMinMeses()*30)+(tiposancion.getTiempoMinDias());
            System.out.println("aquiiiii-"+calcularperiodo()+"-"+diastiposamax+"-"+diastiposamin);
            if(calcularperiodo()>diastiposamin && calcularperiodo()<diastiposamax){             
            }
            else{
                formsancion.recordError("El Periodo de Inhabilitacion debe ser menor a :"+String.valueOf(diastiposamax)+" días y mayor a :"+String.valueOf(diastiposamin)+" dias");
                return zonasDatos();
            }
        }
        if(tiposancion.getCodigo()==2){
//            if(calcularperiodo(fecinicio,fechadocnot)!=1){
//                formsancion.recordError("La Fecha de Inicio (Periodo de Inhabilitacion) debe ser mayor en un 1 día a la Fecha de Notificacion");
//                return zonasDatos();
//            }
            
            int diastiposamax=(tiposancion.getTiempoMaxAnios()*365)+(tiposancion.getTiempoMaxMeses()*30)+(tiposancion.getTiempoMaxDias());
            System.out.println("aquiiiii-"+calcularperiodo()+"-"+diastiposamax);
            if(calcularperiodo()<diastiposamax){                
            }
            else{
                formsancion.recordError("El Periodo de Inhabilitacion debe ser menor a: "+String.valueOf(diastiposamax)+" días");
                return zonasDatos();
            }
        }
        
        
        if (fechadocnot != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docnot = (Date) formatoDelTexto.parse(fechadocnot);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if (fechadocsan != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_docsan = (Date) formatoDelTexto.parse(fechadocsan);
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
            if(calculardia()!=(long)1){
                formsancion.recordError("La Fecha de Inicio (Periodo de Inhabilitacion) debe ser mayor en un 1 día a la Fecha de Notificacion");
                return zonasDatos();
            }
        }
        if (fecfin != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_fin = (Date) formatoDelTexto.parse(fecfin);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
            
        
        if(bestrabajador){
            nuevasancion.setEstrabajador(true);
        }else{
            nuevasancion.setEstrabajador(false);
            nuevapersona.setDocumentoidentidad(bdocidentidad);
            nuevapersona.setApellidoMaterno(bamaterno);
            nuevapersona.setApellidoPaterno(bapaterno);
            nuevapersona.setNroDocumento(bnumerodocumento);
            session.saveOrUpdate(nuevapersona);            
            session.flush();
            nuevasancion.setPersona(nuevapersona);
        }
        TipoSancion tiposa=new TipoSancion();
        tiposa=(TipoSancion) session.load(TipoSancion.class, tiposancion.getId_tipo());
        nuevasancion.setCategoria_sancion(categoriasancion);
        nuevasancion.setFecha_docnot(fecha_docnot);
        nuevasancion.setFecha_docsan(fecha_docsan);
        nuevasancion.setFechafin_inha(fecha_fin);
        nuevasancion.setFechaini_inha(fecha_inicio);
        nuevasancion.setTipo_sancion(tiposa);
//        nuevasancion.setSancion_estado(this.getEstados().get(0));
        session.saveOrUpdate(nuevasancion);
        session.flush(); 
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
    public List<DatoAuxiliar> getEstados() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(Restrictions.eq("codigo", (long) 1));
        return c.list();
    }
    
    int calcularperiodo(){   
        String consulta ="SELECT 1 ID,to_number(to_date('"+fecfin+"','dd/mm/yyyy') - to_date('"+fecinicio+"','dd/mm/yyyy')) DIAS from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkConsultaPeriodo.class);  
        List result = query.list();        
        LkConsultaPeriodo lkcondos = (LkConsultaPeriodo) result.get(0);
        return lkcondos.getDias();
    }
    long calculardia(){
        long diferencia = ( fecha_inicio.getTime() - fecha_docnot.getTime() );
//        System.out.println("aquiiiii222"+(diferencia/(1000*60*60*24)));
        return diferencia/(1000*60*60*24);
    }
    
    boolean validarpersona(){
        boolean vali=true;
        if(bdocidentidad==null){
            formsancion.recordError("Tiene que ingresar Tipo Documento");
            vali=false;
        }
        if(bnumerodocumento==null){
            formsancion.recordError("Tiene que ingresar Numero Documento");
            vali=false;
        }
        if(bnombres==null){
            formsancion.recordError("Tiene que ingresar Nombres de la Persona");
            vali=false;
        }
        if(bapaterno==null){
            formsancion.recordError("Tiene que ingresar Apellido Paterno");
            vali=false;
        }
        if(bamaterno==null){
            formsancion.recordError("Tiene que ingresar Apellido Materno");
            vali=false;
        }        
        return vali;
    }
    
    @Log
    void onBnomtrabaautoridadChanged() {
        bnomtrabaautoridad=_request.getParameter("param");
    }
    
    @Log
    void onBnumerodocumentoChanged() {
        bnumerodocumento=_request.getParameter("param");
    }
    
    @Log
    void onBnombresChanged() {
        bnombres=_request.getParameter("param");
    }
    
    @Log
    void onBapaternoChanged() {
        bapaterno=_request.getParameter("param");
    }
    
    @Log
    void onBamaternoChanged() {
        bamaterno=_request.getParameter("param");
    }
    
    @Log
    Object onValueChangedFromCategoria_sancion(DatoAuxiliar dato) {
           return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());
    }
    
    @Log
    Object onValueChangedFromTipo_sancion(Lk_Tipo_Sancion dato) {
           if(dato!=null){
               if(dato.getCodigo()==3){
                    mostrarfecha=true;
                } 
                else{
                    mostrarfecha=false;
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
        else
            mostrardocu=false;
        
        return new MultiZoneUpdate("busquedaZone", busquedaZone.getBody());
    } 
    
   

}
