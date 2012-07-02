package com.tida.servir.components;

import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Usuario;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;


/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *
 */
public class EvaluacionesPersonalesEditor {

	@SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;

	@Property
    @SessionState
    private Usuario _usuario;

    @Property
    @Parameter
    private CargoAsignado actual_asignado;

    @Property
    @SessionState
    private Entidad _oi;


    @Property
    private EvaluacionPersonal evaluacion;

    @Inject
    private Session session;

    @Component(id = "formularioevaluacionespersonales")
    private Form formularioEvaluacionesPersonales;

    @InjectComponent
    private Envelope envelope;


    @Property
    @Parameter(required=false)
    private Boolean readOnly;
    

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
        if(readOnly != null) {
            return ((!readOnly) && Permisos.puedeEscribir(_usuario, _oi) );
        } else {
            return Permisos.puedeEscribir(_usuario, _oi);
        }
    }
    
    public List<String>  getTiposEvaluacion(){
        return Helpers.getValorTablaAuxiliar("TipoEvaluaciones", session);
    }


  public PrimaryKeyEncoder<Long, EvaluacionPersonal> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, EvaluacionPersonal>()
    {
      public Long toKey(EvaluacionPersonal value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public EvaluacionPersonal toValue(Long key)
      {
        return (EvaluacionPersonal) session.get(EvaluacionPersonal.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

  @CommitAfter
  public Object onSuccess()
  {  
	  for(EvaluacionPersonal e : actual_asignado.getEvaluacionesPersonales()) {
		  if (e.getFec_hasta().before(e.getFec_desde())) {

			  Logger logger = new Logger();
			  logger.loguearError(session, _usuario, e.getId().toString(),
					  Logger.CODIGO_ERROR_FECHA_HASTA_PREVIA_DESDE,
					  Errores.FECHA_DESDE_HASTA_COMPARACION, Logger.TIPO_OBJETO_EVALUACION);

			  formularioEvaluacionesPersonales.recordError(Errores.FECHA_DESDE_HASTA_COMPARACION);
			  return this;
		  }
		  if(e.getFec_desde().after(new Date())) {
			  Logger logger = new Logger();
			  logger.loguearError(session, _usuario, e.getId().toString(),
					  Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
					  Errores.ERROR_FECHA_DESDE_PREVIA_ACTUAL, Logger.TIPO_OBJETO_EVALUACION);


			  formularioEvaluacionesPersonales.recordError(Errores.ERROR_FECHA_DESDE_PREVIA_ACTUAL);
			  return this;
		  }
	  }

          envelope.setContents(helpers.Constantes.EVALUACION_EXITO);
	  formularioEvaluacionesPersonales.clearErrors();
	  return this;
  }

  @CommitAfter
  Object onAddRow()
  {
    EvaluacionPersonal eval = new EvaluacionPersonal();
    if (actual_asignado.getEvaluacionesPersonales() == null) {
        actual_asignado.setEvaluacionesPersonales(new ArrayList<EvaluacionPersonal>());
    }
    actual_asignado.getEvaluacionesPersonales().add(eval);
    session.saveOrUpdate(actual_asignado);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(eval.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_EVALUACION);
    return eval;
  }

  @CommitAfter
  void onRemoveRow(EvaluacionPersonal eval)
  {
    actual_asignado.getEvaluacionesPersonales().remove(eval);

    session.delete(eval);

    session.saveOrUpdate(actual_asignado);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(eval.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_EVALUACION);
  }

  Object onFailure() {
          return this;
  }
  
  @Log
  void onActivate(){
      System.out.println("actual_asignado " + actual_asignado.getId() + "actual_asignado.evaluacionesPersonales "+actual_asignado.getEvaluacionesPersonales().size());
  }

}
