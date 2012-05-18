/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import java.util.Date;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Usuario;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.ArrayList;
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
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;



import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class ConstanciasDocumentalesEditor {
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String zone;

    @Parameter
    @Property
    private Legajo legajo;

    @Parameter
    @Property
    private String titulo;

    @Property
    @SessionState
    private Usuario _usuario;

    @Property
    @SessionState
    private EntidadUEjecutora _oi;

    @Parameter
    @Property
    private String categoria;
    //legajo= "_ca.legajo" titulo="Identificación Personal/Familiar" categoria="IDFAMILIARPERSONAL"

    @Component(id = "constanciasDocumentalesForm")
    private Form _form;

    @Inject
    private Session session;

    @Property
    private ConstanciaDocumental constancia;
    
    @InjectComponent
    private Envelope envelope;


    public List<ConstanciaDocumental> getConstanciasDocumentales () {
        // Obtengo las constancias para la categoría indicada y del legajo indicado
        Criteria c = session.createCriteria(ConstanciaDocumental.class);
            c.add(Restrictions.like("cat_constancia", categoria));
            c.add(Restrictions.eq("legajo", legajo));

   	return c.list();

    }

    @Persist
    private List<String> tiposConstancia;

    public List<String>  getTiposConstancia(){
        return tiposConstancia;
    }


    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }


  public PrimaryKeyEncoder<Long, ConstanciaDocumental> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, ConstanciaDocumental>()
    {
      public Long toKey(ConstanciaDocumental value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public ConstanciaDocumental toValue(Long key)
      {
        return (ConstanciaDocumental) session.get(ConstanciaDocumental.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }

  public void onPrepare() {
  // cargo los tipos de las constancias

      DatoAuxiliar d = Helpers.getDatoAuxiliar("CategoríaConstancia", categoria, session);
      if (d!= null){
        tiposConstancia = Helpers.getValorTablaAuxiliar("DatoConstancia", session,
            "CategoríaConstancia",
            d.getCodigo());
      } else {
          tiposConstancia = new ArrayList<String>();
      }
  }

  @CommitAfter
  public Object onSuccess()
  {
	  for(ConstanciaDocumental e : getConstanciasDocumentales()) {
              if(e.getFecha() != null){
		  if (e.getFecha().after(new Date())) {
			  Logger logger = new Logger();
			  logger.loguearError(session, _usuario, e.getId().toString(),
					  Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
					  Errores.ERROR_FECHA_PREVIA_ACTUAL,
					  Logger.TIPO_OBJETO_CONSTANCIA_DOCUMENTAL);

			  _form.recordError(Errores.ERROR_FECHA_PREVIA_ACTUAL);
			  return this;
		  }
              }
	  }

          envelope.setContents(helpers.Constantes.CONSTANCIAS_DOCUMENTALES_EXITO);
	  _form.clearErrors();
	  return this;
  }

  @CommitAfter
  Object onAddRow()
  {
    ConstanciaDocumental constancia = new ConstanciaDocumental();
    constancia.setLegajo(legajo);
    constancia.setCat_constancia(categoria);
    session.saveOrUpdate(constancia);
    //legajo.getConstanciasDocumentales().add(constancia);
	//session.saveOrUpdate(legajo);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(constancia.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONSTANCIA_DOCUMENTAL);
    session.saveOrUpdate(legajo);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(constancia.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONSTANCIA_DOCUMENTAL);
    return constancia;
  }

  @Log
  @CommitAfter
  void onRemoveRow(ConstanciaDocumental constancia)
  {
    legajo.getConstanciasDocumentales().remove(constancia);
    session.merge(legajo);
    session.delete(constancia);
    new Logger().loguearOperacion(session, _usuario, String.valueOf(constancia.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONSTANCIA_DOCUMENTAL);
  }

     Object onFailure() {
          return this;
    }

}