package com.tida.servir.components;

import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;

import helpers.Errores;
import helpers.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *  
 */
public class RemuneracionesPersonalesEditor {

	@SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;


    @Property
    @SessionState
    private Entidad _entidadUE;

    @Property
    @Parameter
    private CargoAsignado actual_asignado;
   
    @Property   
    private RemuneracionPersonal remuneracion;

    @Inject
    private Session session;
    
    @Component(id = "formularioremuneracionespersonales")
    private Form formularioRemuneracionesPersonales;

    @InjectComponent
    private Envelope envelope;
    
    @Property
    @SessionState
    private Usuario _usuario;


    @Property
    @Parameter(required=false)
    private Boolean readOnly;

    public boolean getNoEditable() {
        return !getEditable();
    }
    
    public boolean getEditable() {
        if(readOnly != null) {
            return ((!readOnly) && Permisos.puedeEscribir(_usuario, _entidadUE)  );
        } else {
            return Permisos.puedeEscribir(_usuario, _entidadUE);
        }
    }

    // Me indica si puedo agregar por cantidad de conceptos habilitados.
    /*
    public boolean getAgregable() {
        return getConceptosHabilitados().size()>0 ;
    }
*/
  public PrimaryKeyEncoder<Long, RemuneracionPersonal> getEncoder()
  {
    return new PrimaryKeyEncoder<Long, RemuneracionPersonal>()
    {
      public Long toKey(RemuneracionPersonal value)
      {
        return value.getId();
      }

      public void prepareForKeys(List<Long> keys)
      {
      }

      public RemuneracionPersonal toValue(Long key)
      {
        return (RemuneracionPersonal) session.get(RemuneracionPersonal.class, key);
      }

            public Class<Long> getKeyType() {
                return Long.class;
            }
    };
  }


  @CommitAfter
  public Object onSuccess()
  {
      // busco si hay repetidos.
	  int cantidadConceptosRemunerativosTotales = 0;
      for(RemuneracionPersonal rem : actual_asignado.getRemuneracionesPersonales()) {
          if (rem.getConceptoRemunerativo().getDescripcion().trim().equalsIgnoreCase("Total remuneración (anual)") ||
              rem.getConceptoRemunerativo().getDescripcion().trim().equalsIgnoreCase("Total remuneración (mensual)")) {
        	  cantidadConceptosRemunerativosTotales++;
          }
      }
      if( cantidadConceptosRemunerativosTotales>0 && actual_asignado.getRemuneracionesPersonales().size()>1 ){
          formularioRemuneracionesPersonales.recordError(Errores.ERROR_CONCEPTO_EXCLUYENTES);
              return this;
      }
      Set<ConceptoRemunerativo> set = new HashSet<ConceptoRemunerativo>();
      for(RemuneracionPersonal rem : actual_asignado.getRemuneracionesPersonales()) {
          set.add(rem.getConceptoRemunerativo());
      }
        if(set.size() < actual_asignado.getRemuneracionesPersonales().size()){
            /* Hay duplicados */
          formularioRemuneracionesPersonales.recordError(Errores.ERROR_REM_CONC_EXCLUYENTES );
              return this;
        }


      envelope.setContents(helpers.Constantes.REMUNERACION_EXITO);
    return this;
  }

    Object onFailure() {
          return this;
    }
    
  @CommitAfter
  Object onAddRow()
  {
    RemuneracionPersonal rem = new RemuneracionPersonal();
    if (actual_asignado.getRemuneracionesPersonales() == null) {
        actual_asignado.setRemuneracionesPersonales(new ArrayList<RemuneracionPersonal>());
    }
    actual_asignado.getRemuneracionesPersonales().add(rem);
    session.saveOrUpdate(actual_asignado);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(rem.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_REMUNERACION_PERSONAL);
    return rem;
  }

  @CommitAfter
  void onRemoveRow(RemuneracionPersonal rem)
  {
    actual_asignado.getRemuneracionesPersonales().remove(rem);

    session.delete(rem);
        
    session.saveOrUpdate(actual_asignado);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(rem.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_REMUNERACION_PERSONAL);
  }
  
  @Inject
  private PropertyAccess _access;

  public GenericSelectModel<ConceptoRemunerativo> getConceptosRemunerativos(){
      Criteria c = session.createCriteria(ConceptoRemunerativo.class);
      c.add(Restrictions.eq("entidadUE", _entidadUE ));


      List<ConceptoRemunerativo> list = c.list();

      return new GenericSelectModel<ConceptoRemunerativo>(list,ConceptoRemunerativo.class,"descripcion","id",_access);
  }
/*
  private List<ConceptoRemunerativo> getConceptosHabilitados() {
      //Busco los conceptos remunerativos que ya están utilizados (para luego removerlos de las opciones.
      ArrayList<ConceptoRemunerativo> lc = new ArrayList<ConceptoRemunerativo>();
        for(RemuneracionPersonal rem : actual_asignado.getRemuneracionesPersonales()) {
            lc.add(rem.getConceptoRemunerativo());
          }


      Criteria c = session.createCriteria(ConceptoRemunerativo.class);
      c.add(Restrictions.eq("entidadUE", _entidadUE ));


      List<ConceptoRemunerativo> list = c.list();
      list.removeAll(lc);
      return list;
  }
 *
 */
}