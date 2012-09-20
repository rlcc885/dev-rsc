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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
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
 * @author Miller Cribillero
 * @date 17/09/2012 18:00
 * 
 */
public class ConsultaSanciones extends GeneralPage {
    @Inject
    private Session session;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Property
    @Persist
    private UsuarioAcceso usua;
    @InjectComponent
    @Property
    private Zone consultaSancionesZone;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Persist
    @Property
    private String entidad_origen; 
    @Property
    @Persist
    private Trabajador nuevo;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private String bnombres;
    @Persist
    @Property
    private String bapellidoPaterno;
    @Persist
    @Property
    private String bapellidoMaterno;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad;
    @Persist
    @Property
    private String bnumeroDocumento;
    @Persist
    @Property
    private String bdenoentidad;
    @Persist
    @Property
    private Boolean esVigente;
    @Persist
    @Property
    private Boolean esNoVigente;
    @Persist
    @Property
    private Boolean esHistorica;
    @Persist
    @Property
    private Boolean esAnulada;
    @Persist
    @Property
    private Boolean esSuspendida;
   
    @Persist
    @Property
    private DatoAuxiliar bregimenLaboral;
    @Persist
    @Property
    private DatoAuxiliar bcategoriaSancion;
    @Persist
    @Property
    private Lk_Tipo_Sancion btipoSancion;
          
    @Property
    @Persist
    private LkBusquedaEntidad entio;
    @Property
    @Persist
    private LkBusquedaSancionados cs;
    
    @Component(id = "formconsultaSancion")
    private Form formconsultaSancion;
    @Component(id = "formularioconsultasanciones")
    private Form formularioconsultasanciones;
    
    @Log
    @SetupRender
    void initializeValue() {
        nuevo = new Trabajador();
        
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getRegimenLaboral() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
      public GenericSelectModel<DatoAuxiliar> getCategoriaSancion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
      public GenericSelectModel<Lk_Tipo_Sancion> getTipoSancion() {
        List<Lk_Tipo_Sancion> list;
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class); 
        if(bregimenLaboral!=null){
            c.add(Restrictions.eq("reg_laboral", bregimenLaboral.getId()));
        }
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria", bcategoriaSancion.getId()));
        }
        list = c.list();
        return new GenericSelectModel<Lk_Tipo_Sancion>(list, Lk_Tipo_Sancion.class, "descripcion", "id", _access);
    }
      
    @Log
    public List<LkBusquedaSancionados> getBusquedaSancionados(){
        Criteria c;
        c = session.createCriteria(LkBusquedaSancionados.class);
//        if(entidad_origen!=null){
//            c.add(Restrictions.eq("entidad_id",entidad_origen));
//        }
        
        return c.list();
      }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        return getEntidades();
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
    Object onValueChangedFromCategoriaSancion(DatoAuxiliar dato) {
        return consultaSancionesZone.getBody();
    }     
    
    @Log
    Object onValueChangedFromRegimenLaboral(DatoAuxiliar dato) {
        return consultaSancionesZone.getBody();
    }     
    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioConsultaSanciones() {
           cs = new LkBusquedaSancionados();
          return consultaSancionesZone.getBody();
    }
}