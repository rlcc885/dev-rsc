package com.tida.servir.components;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.criterion.CriteriaSpecification;




/**
 *
 * @author LFL
 */
public class MeritosDemeritosEditor {
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
   
    
    @Component(id = "formulariomensajesME")
    private Form formulariomensajesME;
    @InjectComponent
    private Zone mensajesMEZone;  
       
    @InjectComponent
    private Zone meritosZone;
    
    private int elemento=0;
     
    @Parameter
    @Property
    private Trabajador actual;
    
    @Persist
    @Property
    private MeritoDemerito merito;
    
    
    //Listado de Meritos
    @InjectComponent
    private Zone listaMeritosZone;
    @Persist
    @Property
    private MeritoDemerito listaMeritos;
    
 
    @Inject
    private PropertyAccess _access;
    
    //Inicio de la carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            merito=new MeritoDemerito();
           
    }
    
    @Log
    public List<MeritoDemerito> getListadoMeritos() {
        Criteria c = session.createCriteria(MeritoDemerito.class);
        c.add(Restrictions.eq("trabajador",actual));   
        return c.list();
    }
   
     //para obtener datos de la Clase de Merito
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanClaseMerito() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("MERITOSDEMERITOSCLASE", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del Tipo de Merito
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoMerito() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSMERITO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    
    void onSelectedFromCancelME() {
        elemento=2;
    }
    
    void onSelectedFromResetME() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariomeritos() {
           
            merito.setTrabajador(actual);
            merito.setEntidad(_oi);
            session.saveOrUpdate(merito);
            envelope.setContents(helpers.Constantes.MERITO_DEMERITO_EXITO);
            merito=new MeritoDemerito();
            return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody())                             
                    .add("listaMeritosZone",listaMeritosZone.getBody())
                    .add("meritosZone", meritosZone.getBody());  
  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotonesME() {
        if(elemento==1){
            merito=new MeritoDemerito();
            return  meritosZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditarME(MeritoDemerito meri) {        
        merito=meri;
           return meritosZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminarME(MeritoDemerito meri) {
        session.delete(meri);
        return listaMeritosZone.getBody();
    }
    
    
    
    
   
}