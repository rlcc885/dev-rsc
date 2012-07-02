package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Publicacion;
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
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
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
public class PublicacionesEditor {
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
    private Entidad _oi;

    @Component(id = "publicacionesEdit")
    private Form _form;

    @Property
    private Publicacion publicacion;

    
    public List<String> getValorTablaAuxiliar(String tabla) 
    {
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", tabla));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }

    
 @Inject
  private Session session;

    @Property
    @SessionState
    private Usuario _usuario;
    
    @InjectComponent
    private Envelope envelope;

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }

    public boolean getEsTrabajadorEditable(){
        if(publicacion != null){
            if((publicacion.getAgregadoTrabajador() == null)){
                return true;
            }
                
            if(publicacion.getAgregadoTrabajador() == false){
                return true;
            }
        }
        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
    }
    
  public PrimaryKeyEncoder<Long, Publicacion> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, Publicacion>()
    {
      public Long toKey(Publicacion value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public Publicacion toValue(Long key)
      {
        return (Publicacion) session.get(Publicacion.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

  public List<Publicacion> getPublicaciones() {
    Criteria c = session.createCriteria(Publicacion.class);
    c.add(Restrictions.like("clase", clase));
    c.add(Restrictions.eq("trabajador", actual));
    return c.list();
 
  //    return actual.getPublicaciones();
  }

  public List<String> getTiposPublicacion() {
      if (clase.equals(Publicacion.CLASE_PUBLICACION))
        return helpers.Helpers.getValorTablaAuxiliar("TiposPublicacion", session);
      else
        return helpers.Helpers.getValorTablaAuxiliar("TiposTrabajosInvestigacion", session);

  }
  @CommitAfter
  public Object onSuccess()
  {
      for(Publicacion pub : actual.publicaciones) {
    	  
    	  if(pub.getFecha().after(new Date())) {
              			  Logger logger = new Logger();
			  logger.loguearError(session, _usuario, pub.getId().toString(),
					  Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
					  Errores.ERROR_FECHA_PREVIA_ACTUAL,
					  Logger.TIPO_OBJETO_PUBLICACION);

			  _form.recordError(Errores.ERROR_FECHA_PREVIA_ACTUAL);

  			return this;
  		  }
    }
        _form.clearErrors();
        envelope.setContents(helpers.Constantes.PROD_INTELECTUAL_EXITO);
	return this;
  }
  
  Object onFailureFromPublicacionesEdit() {
          return this;
  }
  
  @CommitAfter
  Object onAddRow()
  {
    Publicacion pub = new Publicacion();
    if (actual.getPublicaciones() == null) {
        actual.setPublicaciones(new ArrayList<Publicacion>());
    }    
    pub.setTrabajador(actual);
    pub.setClase(clase);
    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
        pub.setAgregadoTrabajador(Boolean.TRUE);
    actual.getPublicaciones().add(pub);
    session.saveOrUpdate(actual);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(pub.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_PUBLICACION);
    return pub;
  }

  @CommitAfter
  void onRemoveRow(Publicacion pub)
  {
      actual.getPublicaciones().remove(pub);
        session.delete(pub);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(pub.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_PUBLICACION);
  }


}
