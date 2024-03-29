/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author arson
 */
public class AnularSancion extends GeneralPage {
    @Persist
    @Property
    private String bdenoentidad;
    @Inject
    private Session session;
    @SessionState
    private Usuario loggedUser;
    @InjectComponent
    private Envelope envelope; 
    @Component(id = "formularioanularsancion")
    private Form formularioanularsancion;
    @Component(id = "formmensaje")
    private Form formmensaje;
    @Persist
    @Property
    private String entidad_origen;
    @InjectComponent
    @Property
    private Zone busZone2;
    @InjectComponent
    @Property
    private Zone mensajeZone;
    @InjectComponent
    @Property
    private Zone busZone;
     @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad2;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad_not;
    @Persist
    @Property
    private String bnumeroDocumento;
    @Persist
    @Property
    private String bnumeroDocumento2;
    @Persist
    @Property
    private String bnumeroDocumento_not;
    @Persist
    @Property
    private String observaciones;
    @Property
    @Persist
    private String fechadoc;
    @Property
    @Persist
    private Date fecha_doc;
    @Property
    @Persist
    private String fechadocnot;
    @Property
    @Persist
    private Date fechadoc_not;
     @Persist
    @Property
    private String juzgado_not;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private LkBusquedaEntidad entio;

    @Property
    @Persist
    private Anulacion anulacion;
    @PageActivationContext
    private Sancion modificasancion;
    private int elemento=0;
    @Property
    @Persist
    private String texto;
    
    @Log
    @SetupRender
    private void inicio() {
        onLimpiar();        
        anulacion=new Anulacion(); 
        anulacion.setId_sancion(modificasancion);
//        modificasancion.setSancion(modificasancion);
        if(modificasancion.getEstrabajador()){
            texto="Trabajador Sancionado: "+modificasancion.getTrabajador().getApellidoPaterno()+" "+modificasancion.getTrabajador().getApellidoMaterno()+" "+modificasancion.getTrabajador().getNombres();
            texto+=" / Entidad que Sanciona: "+modificasancion.getCargoasignado().getLegajo().getEntidad().getDenominacion();
        }else{
            texto="Persona Sancionada: "+modificasancion.getPersona().getApellidoPaterno()+" "+modificasancion.getPersona().getApellidoMaterno()+" "+modificasancion.getPersona().getNombres();
        }
    }
     
    @Log
    public Sancion getModificasancion() {
        return modificasancion;
    }
    
     public void setModificasancion(Sancion modificasancion) {
        this.modificasancion = modificasancion;
    }
     
     @Log
      public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
     
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (bdenoentidad != null && !bdenoentidad.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()));
        }        
        return c.list();
    }
    
//    @Log
//    Object onActionFromSeleccionar(Entidad enti2) {        
//        entidad2 = enti2;
//        entidad_origen=entidad2.getDenominacion();
//        entidad_origen_id = entidad2.getId();
//        return busZone2.getBody();  
//    }
    
//    Object onBuscarentidad(){
//         //return busZone.getBody();
//          return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("busZone", busZone.getBody());
//      } 
    
    @Log
    Object onLimpiar() {        
        entidad_origen=null;
        bnumeroDocumento_not=null;
        observaciones=null;
        juzgado_not=null;
        bnumeroDocumento2=null;
        fechadoc=null;
        fechadocnot=null;
        return busZone2.getBody();  
    }
     @Log
     Object onCancel1() {
         return "ConsultaSanciones";
     }
    
    @Log
    void onSelectedFromSave(){ 
        elemento=1;
    } 
     
    @Log
    @CommitAfter
    Object onSuccessFromFormularioAnularSancion(){
        formmensaje.clearErrors();
        if(elemento==1){
             if(entidad_origen==null)
         {
             formularioanularsancion.recordError("Tiene que ingresar una Entidad");
                return busZone2.getBody();
         }
         if(bnumeroDocumento_not==null){
             envelope.setContents("Falta el Numero de documento de Notificacion");
             return busZone2.getBody();
         }
         if(bnumeroDocumento2==null){
             envelope.setContents("Falta el Numero de documento");
             return busZone2.getBody();
         }
         if(fechadocnot==null){
             envelope.setContents("Falta la Fecha del Documento de Notificacion");
             return busZone2.getBody();
         }
         if(fechadoc==null){
             envelope.setContents("Falta la Fecha del Documento ");
             return busZone2.getBody();
         }
        if(fechadocnot!= null){
             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fechadoc_not =((Date) formatoDelTexto.parse(fechadocnot));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        
        if(fechadoc != null){
             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                anulacion.setFecha_doc_san((Date) formatoDelTexto.parse(fechadoc));
                if(anulacion.getFecha_doc_san().after(new Date())){
                    formmensaje.recordError("La fecha del Documento de Anulación debe ser menor a la Actual");
                    return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        
        if(fechadoc_not.after(new Date())){
            formmensaje.recordError("La fecha del Documento de Notificacion debe ser menor a la Actual");
            return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
        }
        
        if(anulacion.getFecha_doc_san().after(fechadoc_not)){
            formmensaje.recordError("La fecha del Documento de Anulación debe ser menor al Documento de Notificacion");
            return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
        }
        System.out.println("EFEX4");          
        if(fechadoc_not.after(new Date())){
            formmensaje.recordError("La fecha del Documento de Notificacion debe ser menor a la Actual");
            return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
        }
        System.out.println("EFEX3"+modificasancion.getFechafin_inha());
        if (modificasancion.getFechafin_inha()!=null){
         if(fechadoc_not.after(modificasancion.getFechafin_inha())){
             formmensaje.recordError("La fecha del Documento de Notificacion no puede ser menor a la Fecha Final de la Sancion");
             return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
         }
        }
        System.out.println("EFEX2");  
         
         if(fechadoc_not.before(modificasancion.getFechaini_inha())){
             formmensaje.recordError("La fecha del Documento de Notificacion no puede ser menor a la Fecha Inicial de la Sancion");
             return new MultiZoneUpdate ("busZone2",busZone2.getBody()).add("mensajeZone",mensajeZone.getBody());
         }
        System.out.println("EFEX1");  
         
//        if (entidad_origen!=null){
////            System.out.println("Tiene entidad de inicio");
////            Criteria c1 = session.createCriteria(Entidad.class);
////            c1.add(Restrictions.like("denominacion",'%'+entidad_origen+'%'));
////            if (!c1.list().isEmpty()){
////                Entidad entidadIniAnulacion = (Entidad)c1.list().get(0);
////             anulacion.setId_entidad(entidadIniAnulacion);   
////            }
//        }
         
         //anulacion.setFecha_doc_not(fechadoc_not);
        
        // (fecha_doc);
         anulacion.setFecha_doc_not(fechadoc_not);
         anulacion.setJuzgado(juzgado_not);
         anulacion.setId_sancion(modificasancion);
         anulacion.setId_tipo_doc_not(bdocumentoidentidad_not.getId());
         anulacion.setId_tipo_doc_san(bdocumentoidentidad2.getId());
         anulacion.setNumero_doc_not(bnumeroDocumento_not);
         anulacion.setNumero_doc_san(bnumeroDocumento2);
         anulacion.setEntidad(entidad_origen);
         session.saveOrUpdate(anulacion);            
         session.flush();
         if(fechadoc_not.before(new Date()) || fechadoc_not.equals(new Date())){
             modificasancion.setSancion_estado(getEstados().get(0));
         }else{
             modificasancion.setSancion_estado(getEstado_v().get(0));
         }
         
         session.saveOrUpdate(modificasancion);   
         session.flush();
         //modificasancion
         envelope.setContents("Sancion Anulada con Exito");
         onLimpiar();
         return "ConsultaSanciones";
        }
         return "ConsultaSanciones";
     }
    
    @Log
    public List<DatoAuxiliar> getEstados() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(Restrictions.eq("codigo", (long) 4));
        return c.list();
    }
    @Log
    public List<DatoAuxiliar> getEstado_v() {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(Restrictions.eq("codigo", (long) 1));
        return c.list();
    }
}
