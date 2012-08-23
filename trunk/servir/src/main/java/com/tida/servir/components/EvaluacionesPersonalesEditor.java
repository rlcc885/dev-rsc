package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    
   
    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private String valfec_hasta;
    @Persist
    @Property
    private Date fecha_desde;
    @Persist
    @Property
    private Date fecha_hasta;
    
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
    
    @Persist
    @Property
    private Boolean vdetalle;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            evaluacion = new EvaluacionPersonal();
            valfec_desde=null;
            valfec_hasta=null;
            if(_usuario.getRolid()==2 || _usuario.getRolid()==3){
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
        System.out.println("aaaaaaaaaaaaaa"+cargoasignado);
        c.add(Restrictions.eq("cargoasignado",cargoasignado));
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
        formulariomensajese.clearErrors();
        if(valfec_desde!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_desde = (Date)formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }else{
                  formulariomensajese.recordError("Debe ingresar una fecha de Ingreso");  
                  return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                    .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                    .add("evaluacionesZone", evaluacionesZone.getBody());
            }   
          
            if(valfec_hasta!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_hasta = (Date)formatoDelTexto.parse(valfec_hasta);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }else{
                  formulariomensajese.recordError("Debe ingresar una fecha de Egreso");  
                  return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                    .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                    .add("evaluacionesZone", evaluacionesZone.getBody());
            } 
            
            evaluacion.setFec_desde(fecha_desde);
            evaluacion.setFec_hasta(fecha_hasta);
        if (evaluacion.getFec_hasta().before(evaluacion.getFec_desde()) || evaluacion.getFec_desde().equals(evaluacion.getFec_hasta())) {
            //envelope.setContents("Las fecha de ingreso debe ser menor a la fecha de egreso");
            formulariomensajese.recordError("Las fecha de ingreso debe ser menor a la fecha de egreso");
        } else {
        evaluacion.setCargoasignado(getCargosAsignados());
        session.saveOrUpdate(evaluacion);
        envelope.setContents(helpers.Constantes.EVALUACION_EXITO);
        evaluacion=new EvaluacionPersonal();
        valfec_desde=null;
        valfec_hasta=null;
        }
        return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                .add("evaluacionesZone", evaluacionesZone.getBody());
        

  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            evaluacion=new EvaluacionPersonal();
            valfec_desde=null;
            valfec_hasta=null;
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
        
        if(evaluacion.getFec_desde()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(evaluacion.getFec_desde());
        }
        if(evaluacion.getFec_hasta()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(evaluacion.getFec_hasta());
        }
        
           return evaluacionesZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(EvaluacionPersonal evalu) {
        session.delete(evalu);
        if(_usuario.getRolid()==2 || _usuario.getRolid()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
        envelope.setContents("Evaluacion personal eliminada exitosamente.");
        return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
        .add("listaEvaluacionZone", listaEvaluacionZone.getBody());
       
    }
    
}
