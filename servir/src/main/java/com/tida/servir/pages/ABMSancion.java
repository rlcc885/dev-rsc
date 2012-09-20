/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
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
    private String bnomautoridad;
    @Property
    @Persist
    private LkBusquedaEntidad rowentidad;
    
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
    private Zone busquedamodalZone;
    //validaciones
    @Property
    @Persist
    private Boolean bmostrar;
    @Property
    @Persist
    private Boolean mostrarentidad;
    @Property
    @Persist
    private Boolean mostrarlista;
    private int elemento=0;
    
    // inicio de la pagina
    @SetupRender
    void inicio(){
        nuevasancion=new Sancion();
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
    @CommitAfter
    Object onSuccessFromformbusqueda() {
      if(elemento==1){
          mostrarlista=false;
          System.out.println("aquiiiiiiiii"+mostrarentidad);
           return zonasDatos();
      }  
      else if(elemento==2){
          mostrarlista=false;
          System.out.println("aquiiiiiiiii"+mostrarentidad);
          return zonasDatos();
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
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("busquedaZone", busquedaZone.getBody()).add("validacionZone", validacionZone.getBody()).add("sancionZone", sancionZone.getBody())
                .add("busquedamodalZone",busquedamodalZone.getBody());
        return mu;

    }
    
   
    

}
