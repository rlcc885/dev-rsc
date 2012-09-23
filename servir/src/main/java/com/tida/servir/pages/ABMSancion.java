/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
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
    private int elemento=0;
    
    // inicio de la pagina
    @SetupRender
    void inicio(){
        bmostrar=false;
        mostrarfecha=false;
        nuevasancion=new Sancion();
        nuevofuncionario=new Funcionario();
        if(usuario.getRolid()==2){
            bmostrarrol=false;
            bmostrar=true;
        }
        else{
            bmostrarrol=true;
            
        }
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidad() {
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
        return new GenericSelectModel<Lk_Tipo_Sancion>(list, Lk_Tipo_Sancion.class, "descripcion", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeancategoriasancion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipodocnot() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipodocsan() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    @Log
    void onSelectedFromBuscarenti() {
        mostrarentidad=true;
        System.out.println("aquiiiiiiiii"+mostrarentidad);
        elemento=1;
    }
    @Log
    void onSelectedFromBuscartraba() {        
        mostrarentidad=false;
        System.out.println("aquiiiiiiiii"+mostrarentidad);
        elemento=2;
    }
    
    @Log
    void onSelectedFromBuscarpersona(){
        elemento=3;
    }
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
      }
      else{
          bmostrar=false;
      }
      return zonasDatos();
    }
    
    @Log
    Object onBuscarautoridadnot(){  
        mostrarnuevof=false;
        return sancionZone.getBody();
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
        nuevasancion.setTiem_ser_dia(String.valueOf(btra.getTiempo_dias()));
        if(btra.getFec_fin()!=null){
            calcular(btra.getFec_inicio(),btra.getFec_fin());
        }
        else{
            java.util.Date fechaactual = new Date();
            calcular(btra.getFec_inicio(),fechaactual);
        }
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
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("validacionZone", validacionZone.getBody()).add("sancionZone", sancionZone.getBody())
                .add("busquedamodalZone",busquedamodalZone.getBody());
        return mu;

    }
    
    @Log
    Object onValueChangedFromCategoria_sancion(DatoAuxiliar dato) {
           return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());
    }
    
    @Log
    Object onValueChangedFromTipo_sancion(Lk_Tipo_Sancion dato) {
           if(dato.getCodigo()==3){
               mostrarfecha=true;
           } 
           else{
               mostrarfecha=false;
           }
           return new MultiZoneUpdate("inhabilitacionZone", inhabilitacionZone.getBody());
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
    
    void calcular(Date inicio,Date fin){
//        long fechaInicial = inicio.getTime(); //Tanto fecha inicial como fecha final son Date. 
//        long fechaFinal = fin.getTime(); 
//        long diferencia = fechaInicial - fechaFinal; 
//        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24)); 
//        System.out.println("diasssssssss"+inicio+"-"+fin+"-"+dias);
    }
    

    
   
    

}
