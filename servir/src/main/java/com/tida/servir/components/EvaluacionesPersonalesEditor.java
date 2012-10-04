package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.pages.Busqueda;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.hibernate.Criteria;
import org.hibernate.Query;
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
    @Inject
    private ComponentResources _resources;
   
    @Parameter
    @Property
    private Trabajador actual;
    
    @Component(id = "formulariomensajese")
    private Form formulariomensajese;
    @InjectComponent
    private Zone mensajesEZone;  
       
    @InjectComponent
    private Zone evaluacionesZone2;
    
    private int elemento=0;
 
    @Property
    @Persist
    private EvaluacionPersonal evaluacion;
    
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
    @Property
    @SessionState
    private UsuarioAcceso usua;
    
    @Inject
    private PropertyAccess _access;
    //validaciones
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vNoedita;
    @Persist
    @Property
    private Boolean editando;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
        evaluacion = new EvaluacionPersonal();
        valfec_desde=null;
        valfec_hasta=null;
        
        vdetalle=false;
        vformulario=false;
        vbotones=false;
        vNoedita=false;
        editando=false;
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
        }
        if (usua.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            vNoedita=true; 
        }
        getCargosAsignados();
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
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        nroregistros = Integer.toString(c.list().size());        
        return c.list();
    }

    @Persist
    @Property
    private String nroregistros;
    
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
        elemento=3;

    }
    
    void onSelectedFromReset() {
        System.out.println("seleccion RESETX");
        elemento=2;        
        if (vdetalle) {
            vformulario = false;
            vNoedita = false;
            if (usua.getAccesoreport() == 1) {
                vformulario = true;
                vdetalle = false;
                vbotones = true;
                evaluacion=new EvaluacionPersonal();
                valfec_desde=null;
                valfec_hasta=null;
                editando = false;
                vNoedita = true;
                
            }
        } else {
            if (usua.getAccesoreport() == 0) {
                vformulario = false;
                vdetalle = false;
                vbotones = false;
                vNoedita = false;
            } else {
                editando = false;
                evaluacion=new EvaluacionPersonal();
                valfec_desde=null;
                valfec_hasta=null;
            }
        }
         
    }
    
//  void validar_campos()
//  {
//    if (valfec_desde == null){}
//    if (valfec_hasta == null){}
//  }
    @Log
    @CommitAfter    
    Object onSuccessFromFormularioevaluaciones() {
        if(elemento==3){
            if (_usuario.getRolid() == 1) {
                return "TrabajadorLaboral";
            } else {
                return Busqueda.class;
            }
        }
        else if(elemento==2){

        }else{          
        
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
                    .add("evaluacionesZone2", evaluacionesZone2.getBody());
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
                    .add("evaluacionesZone2", evaluacionesZone2.getBody());
            } 
            if (fecha_desde.after(new Date())){
                  formulariomensajese.recordError("La fecha de Ingreso debe ser menor a la fecha actual");  
                  return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                    .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                    .add("evaluacionesZone2", evaluacionesZone2.getBody());            
            }
            evaluacion.setFec_desde(fecha_desde);
            evaluacion.setFec_hasta(fecha_hasta);
            if (evaluacion.getFec_hasta().before(evaluacion.getFec_desde()) || evaluacion.getFec_desde().equals(evaluacion.getFec_hasta())) {
                //envelope.setContents("Las fecha de ingreso debe ser menor a la fecha de egreso");
                formulariomensajese.recordError("Las fecha de ingreso debe ser menor a la fecha de egreso");
            } else {
                if(editando){
                   if (usua.getAccesoreport() == 0) {
                        vformulario = false;
                        vbotones = false;
                        vNoedita = false;
                   } 
                }
                evaluacion.setCargoasignado(cargoasignado);
                session.saveOrUpdate(evaluacion);
                new Logger().loguearOperacion(session, _usuario, String.valueOf(evaluacion.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_EVALUACION);    
                envelope.setContents(helpers.Constantes.EVALUACION_EXITO);
                evaluacion=new EvaluacionPersonal();
                valfec_desde=null;
                valfec_hasta=null;
                editando=false;
            }
        }
        return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
                .add("listaEvaluacionZone", listaEvaluacionZone.getBody())
                .add("evaluacionesZone2", evaluacionesZone2.getBody());
    }
    
    @Log
    Object onActionFromEditar(EvaluacionPersonal evalu) {        
        evaluacion=evalu;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        vNoedita = true;
        if(evaluacion.getFec_desde()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(evaluacion.getFec_desde());
        }
        if(evaluacion.getFec_hasta()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(evaluacion.getFec_hasta());
        }
        
           return  new MultiZoneUpdate("evaluacionesZone2",evaluacionesZone2.getBody()).add("listaEvaluacionZone", listaEvaluacionZone.getBody()); 
    }
    
    @Log
    Object onActionFromDetalle(EvaluacionPersonal evalu) {
        evaluacion=evalu; 
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        vNoedita = true;
        if(evaluacion.getFec_desde()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(evaluacion.getFec_desde());
        }
        if(evaluacion.getFec_hasta()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(evaluacion.getFec_hasta());
        }
        return  new MultiZoneUpdate("evaluacionesZone2",evaluacionesZone2.getBody()).add("listaEvaluacionZone", listaEvaluacionZone.getBody()); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(EvaluacionPersonal evalu) {        
        new Logger().loguearOperacion(session, _usuario, String.valueOf(evalu.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ESTUDIO);
        session.delete(evalu);
        envelope.setContents("Evaluacion personal eliminada exitosamente.");
        return new MultiZoneUpdate("mensajesEZone", mensajesEZone.getBody())                             
        .add("listaEvaluacionZone", listaEvaluacionZone.getBody());
       
    }
    
}
