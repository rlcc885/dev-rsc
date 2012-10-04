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
    private Entidad entidad2;
    @Persist
    @Property
    private Long entidad_origen_id;

    @Property
    @Persist
    private Anulacion anulacion;
    @PageActivationContext
    private Sancion modificasancion;
    private int elemento=0;
    
    @Log
    @SetupRender
    private void inicio() {
        anulacion=new Anulacion(); 
//        modificasancion.setSancion(modificasancion);
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
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
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
    
    @Log
    Object onActionFromSeleccionar(Entidad enti2) {        
        entidad2 = enti2;
        entidad_origen=entidad2.getDenominacion();
        entidad_origen_id = entidad2.getId();
        return busZone2.getBody();  
    }
    
    Object onBuscarentidad(){
         //return busZone.getBody();
          return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("busZone", busZone.getBody());
      } 
    
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
    void onSelectedFromSave(){ 
        elemento=1;
    } 
     
    @Log
    @CommitAfter
    Object onSuccessFromFormularioAnularSancion(){
        if(elemento==1){
             if(entidad_origen_id==null)
         {
             formularioanularsancion.recordError("Tiene que seleccionar una Entidad");
                return busZone2.getBody();
         }
         if(bnumeroDocumento_not==null){
             formularioanularsancion.recordError("Falta el Numero de documento de Notificacion");
             return busZone2.getBody();
         }
         if(bnumeroDocumento2==null){
             formularioanularsancion.recordError("Falta el Numero de documento");
             return busZone2.getBody();
         }
         if(fechadocnot==null){
             formularioanularsancion.recordError("Falta la Fecha del Documento de Notificacion");
             return busZone2.getBody();
         }
         if(fechadoc==null){
             formularioanularsancion.recordError("Falta la Fecha del Documento ");
             return busZone2.getBody();
         }
//         System.out.println(anulacion);
         
         if(fechadocnot!= null){
             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                anulacion.setFecha_doc_not((Date) formatoDelTexto.parse(fechadocnot));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
         }
         if(fechadoc != null){
             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                anulacion.setFecha_doc_san((Date) formatoDelTexto.parse(fechadoc));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
         }
         
          System.out.println("juzgado    "+juzgado_not);
          System.out.println("observaciones    "+observaciones);
          System.out.println("modificasancion   "+modificasancion);
          System.out.println("bdocumentoidentidad_not   "+bdocumentoidentidad_not.getId());
          System.out.println("bdocumentoidentidad2      "+bdocumentoidentidad2.getId());
          System.out.println("bnumeroDocumento_not     "+bnumeroDocumento_not);
          System.out.println("bnumeroDocumento2     "+bnumeroDocumento2);
          System.out.println(entidad2);
          
         
         //anulacion.setFecha_doc_not(fechadoc_not);
        
        // (fecha_doc);
         anulacion.setJuzgado(juzgado_not);
         anulacion.setObservaciones(observaciones);
         anulacion.setId_sancion(modificasancion);
         anulacion.setId_tipo_doc_not(bdocumentoidentidad_not.getId());
         anulacion.setId_tipo_doc_san(bdocumentoidentidad2.getId());
         anulacion.setNumero_doc_not(bnumeroDocumento_not);
         anulacion.setNumero_doc_san(bnumeroDocumento2);
         anulacion.setId_entidad(entidad2);
         session.saveOrUpdate(anulacion);            
         session.flush();
         
         modificasancion.setSancion_estado(getEstados().get(0));
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
}
