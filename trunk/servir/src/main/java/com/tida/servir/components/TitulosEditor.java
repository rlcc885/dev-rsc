package com.tida.servir.components;

import com.tida.servir.entities.*;
import helpers.Errores;
import helpers.Logger;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
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
public class TitulosEditor {
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
    private Entidad _oi;

    @Component(id = "titulosEdit")
    private Form _form;

    @InjectComponent
    private Envelope envelope;
        
    @Property
    private Titulo titulo;

    @Persist
    private boolean entradaTituloGrid;

    @Property
    @SessionState
    private Usuario _usuario;


//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.puedeEscribir(_usuario, _oi);
//    }
    
    public boolean getEsTrabajadorEditable(){
        if(titulo != null){
            if((titulo.getAgregadoTrabajador() == null)){
                return true;
            }
                
            if(titulo.getAgregadoTrabajador() == false){
                return true;
            }
        }
//        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
        return true;
    }
    
    public List<String> getValorTablaAuxiliar(String tabla) {
    	// TODO: este codigo esta duplicado
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", tabla));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }

    public List<String> getNiveles() {
    	return getValorTablaAuxiliar("NivelTitulo");
    }

 @Inject
  private Session session;

//  public PrimaryKeyEncoder<Long, Titulo> getEncoder()
//  {
//    return new PrimaryKeyEncoder<Long, Titulo>()
//    {
//      public Long toKey(Titulo value)
//      {
//        return value.getId();
//      }
//
//      public void prepareForKeys(List<Long> keys)
//      {
//      }
//
//      public Titulo toValue(Long key)
//      {
//        return (Titulo) session.get(Titulo.class, key);
//      }
//
//            public Class<Long> getKeyType() {
//                return Long.class;
//            }
//    };
//  }

  @CommitAfter
  public Object onSuccess()
  {
//      for(Titulo tit : actual.titulos) {
//    	  
//          if(tit.getFec_emision().after(new Date())) {
//            Logger logger = new Logger();
//            logger.loguearError(session, _usuario, tit.getId().toString(),
//                            Logger.CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL,
//                            Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TITULO);
//
//            _form.recordError(Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL);
//            return this;
//          }
//    }
      
      //es el usuario trabajador o no
      /*if(_usuario.getTipo_usuario().equals("")){
          titulo.setAgregadoTrabajador(Boolean.TRUE);
      }*/
        _form.clearErrors();
        envelope.setContents(helpers.Constantes.TITULO_EXITO);

	return this;
  }

     Object onFailure() {
          return this;
    }
     
 	@Log
	Object onValidateFormtitulosEdit()
	{
		if(titulo.getFec_emision().after(new Date())) {
                        Logger logger = new Logger();
//    			logger.loguearError(session, _usuario, titulo.getId().toString(),
//    					Logger.CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL,
//    					Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TITULO);

			_form.recordError(Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL);
		}
		return this;
	}


  @CommitAfter
  Object onAddRow()
  {
    Titulo tit = new Titulo();
//    if(actual.getTitulos() == null){
//        actual.setTitulos(new ArrayList<Titulo>());
//    }
    tit.setTrabajador(actual);
//    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
        titulo.setAgregadoTrabajador(Boolean.TRUE);
//    actual.getTitulos().add(tit);
    session.saveOrUpdate(actual);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(tit.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TITULO);
    return tit;
  }

  @CommitAfter
  void onRemoveRow(Titulo tit)
  {
//    actual.getTitulos().remove(tit);
    session.delete(tit);
    new Logger().loguearOperacion(session, _usuario, String.valueOf(tit.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TITULO);
  }
  
}
