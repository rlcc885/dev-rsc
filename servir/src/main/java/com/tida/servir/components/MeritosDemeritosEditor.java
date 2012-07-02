package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;

import helpers.Errores;
import helpers.Logger;

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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;




/**
 *
 * @author ale
 */
public class MeritosDemeritosEditor {
//Parameters of component
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;

    @Parameter
    @Property
    private Trabajador actual;

    @Parameter
    @Property
    private String clase;

    @Property
    @SessionState
    private Usuario _usuario;

    @Property
    @SessionState
    private Entidad _oi;
    
    @Component(id = "meritosdemeritosEdit")
    private Form _form;

    @Property
    private MeritoDemerito meritoDemerito;
    
    @Inject
    private Session session;
    
    @InjectComponent
    private Envelope envelope;


    @Property
    @Parameter(required=false)
    private Boolean readOnly;

    public Boolean getEsTrabajador(){
        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR) || getNoEditable();
    }

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

    public List<String> getValorTablaAuxiliar(String tabla) 
    {
    	// TODO: este codigo esta duplicado
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", tabla));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }

  public PrimaryKeyEncoder<Long, MeritoDemerito> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, MeritoDemerito>()
    {
      public Long toKey(MeritoDemerito value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public MeritoDemerito toValue(Long key)
      {
        return (MeritoDemerito) session.get(MeritoDemerito.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

@Log
  public List<MeritoDemerito> getMeritosDemeritos() {
    Criteria c = session.createCriteria(MeritoDemerito.class);
    c.add(Restrictions.like("clase", clase));
    c.add(Restrictions.eq("trabajador", actual));
    return c.list();
  }

  @Log
   public List<String> getTipos() {
      //if (clase.equals("Mérito")){
      if (meritoDemerito.getClase().equals(MeritoDemerito.CLASE_MERITO)){
        return helpers.Helpers.getValorTablaAuxiliar("TiposMerito", session);
      }
      else{
        return helpers.Helpers.getValorTablaAuxiliar("TiposDemerito", session);
      }

  }

  @CommitAfter
  public Object onSuccess()
  {
      for(MeritoDemerito mer : actual.meritosdemeritos) {
    	  if(mer.getClase().equals(clase)) {
              if(mer.getFecha().after(new Date())) {
                        Logger logger = new Logger();
			logger.loguearError(session, _usuario, mer.getId().toString(),
					Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
					Errores.ERROR_FECHA_PREVIA_ACTUAL, Logger.TIPO_OBJETO_MERITO_DEMERITO);

  			_form.recordError(Errores.ERROR_FECHA_PREVIA_ACTUAL);
  			return this;
  		  }
          }
    }
      envelope.setContents(helpers.Constantes.MERITO_DEMERITO_EXITO);
	_form.clearErrors();
	return this;
  }

  Object onFailure() {
          return this;
  }
  
  @CommitAfter
  Object onAddRow()
  {
    MeritoDemerito meritodemerito = new MeritoDemerito();
    meritodemerito.setTrabajador(actual);
    meritodemerito.setClase(clase);
    actual.getMeritosdemeritos().add(meritodemerito);
    session.saveOrUpdate(actual);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(meritodemerito.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_MERITO_DEMERITO);
    return meritodemerito;
  }

  @CommitAfter
  void onRemoveRow(MeritoDemerito meritoDemerito)
  {
      //actual.getMeritosdemeritos().remove(meritoDemerito);
    session.delete(meritoDemerito);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(meritoDemerito.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_MERITO_DEMERITO);
  }


}