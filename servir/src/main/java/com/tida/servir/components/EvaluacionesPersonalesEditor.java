package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.ArrayList;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;


/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *
 */
public class EvaluacionesPersonalesEditor {

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
   
    @Parameter
    @Property
    private Trabajador actual;
    
    @Component(id = "formulariomensajese")
    private Form formulariomensajese;
    @InjectComponent
    private Zone mensajesEZone;  
       
    @InjectComponent
    private Zone evaluacionesZone;
    
    private int elemento=0;
 
    @Property
    @Persist
    private EvaluacionPersonal evaluacion;
    
    @Property
    @Persist
    private boolean bvalidausuario;
    
   
    //Listado de evaluaciones
    @InjectComponent
    private Zone listaEvaluacionZone;
    @Persist
    @Property
    private EvaluacionPersonal listaEvaluacion;
    
    @Persist
    @Property
    private CargoAsignado cargoasignado;
    
    @Inject
    private PropertyAccess _access;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            evaluacion = new EvaluacionPersonal();
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
    }
    
   @Log
   public CargoAsignado getCargosAsignados() {
       Criteria c = session.createCriteria(CargoAsignado.class);
         c.createAlias("legajo", "legajo");
         c.add(Restrictions.eq("trabajador", actual));
         c.add(Restrictions.eq("legajo.entidad", _oi));
         c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
         List result = c.list();
         cargoasignado=(CargoAsignado) result.get(0);
         
       return cargoasignado;
   }
      
    
    @Log
    public List<EvaluacionPersonal> getListadoEvaluciones() {
        Criteria c = session.createCriteria(EvaluacionPersonal.class);
        return c.list();
    }
    
     //para obtener datos del Tipo de Evaluacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoevalucion() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOEVALUACIONES", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del Motivo de Evaluacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanMotivoevaluacion() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("MOTIVOEVALUACION", null, 0, session);
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
    Object onSuccessFromFormularioevaluaciones() {
        evaluacion.setCargoasignado(getCargosAsignados());
        session.saveOrUpdate(evaluacion);
        envelope.setContents(helpers.Constantes.EVALUACION_EXITO);
        evaluacion=new EvaluacionPersonal();
        return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                .add("evaluacionesZone", evaluacionesZone.getBody());

  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            evaluacion=new EvaluacionPersonal();   
            return  evaluacionesZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(EvaluacionPersonal evalu) {        
        evaluacion=evalu;
           return evaluacionesZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(EvaluacionPersonal evalu) {
        session.delete(evalu);
        return listaEvaluacionZone.getBody();
    }
    
}
