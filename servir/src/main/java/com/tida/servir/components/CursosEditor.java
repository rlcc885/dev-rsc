package com.tida.servir.components;

import com.tida.servir.entities.Curso;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;

import helpers.Errores;
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
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;




/**
 *
 * @author ale
 */
public class CursosEditor {
//Parameters of component
@SuppressWarnings("unused")
@Parameter(defaultPrefix = BindingConstants.LITERAL)
@Property
private String _zone;

@Parameter
@Property
private Trabajador actual;

@Property
@SessionState
private Usuario _usuario;

@Property
@SessionState
private Entidad_BK _oi;

@Component(id = "cursosEdit")
private Form _form;

@Property
private Curso curso;

@Inject
private Session session;

@InjectComponent
private Envelope envelope; 
        
    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }
    
    public boolean getEsTrabajadorEditable(){        
        if(curso != null){
            if((curso.getAgregadoTrabajador() == null)){
                return true;
            }
                
            if(curso.getAgregadoTrabajador() == false){
                return true;
            }
        }
        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
    }
    
  public PrimaryKeyEncoder<Long, Curso> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, Curso>()
    {
      public Long toKey(Curso value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public Curso toValue(Long key)
      {
        return (Curso) session.get(Curso.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

  @Log
  @CommitAfter
  public Object onSuccess()
  {
      for(Curso cur : actual.cursos) {
              if(cur.getFec_emision().after(new Date())) {

                  Logger logger = new Logger();
                  logger.loguearError(session, _usuario, cur.getId().toString(),
                      Logger.CODIGO_ERROR_FECHA_DICTADO_PREVIA_ACTUAL,
                      Errores.ERROR_FECHA_DICTADO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_CURSO);

                  _form.recordError(Errores.ERROR_FECHA_DICTADO_PREVIA_ACTUAL);
                  return this;
              }
	  }

    envelope.setContents(helpers.Constantes.CURSO_EXITO);
	  //_form.clearErrors();
	  return this;
  }

  @Log
  @CommitAfter
  Object onAddRow()
  {
    Curso cur = new Curso();
    if(actual.getCursos() == null){
        actual.setCursos(new ArrayList<Curso>());
    }
    cur.setTrabajador(actual);
    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
        cur.setAgregadoTrabajador(Boolean.TRUE);
    actual.getCursos().add(cur);
    session.saveOrUpdate(actual);
    new Logger().loguearOperacion(session, _usuario, String.valueOf(cur.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);
    return cur;
  }

  @Log
  @CommitAfter
  void onRemoveRow(Curso curso)
  {
    actual.getCursos().remove(curso);
    session.delete(curso);
    new Logger().loguearOperacion(session, _usuario, String.valueOf(curso.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);
  }

  @Log
    Object onFailure() {
          return this;
    }

  /*@Log
    Object onValidate(){
    	  
    }*/
}
