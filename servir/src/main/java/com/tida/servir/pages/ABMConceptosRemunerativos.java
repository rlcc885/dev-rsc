package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Usuario;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.List;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;



/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *  
 */
public class ABMConceptosRemunerativos  extends GeneralPage {


    @Inject
    private Session session;

    @Property   
    private ConceptoRemunerativo cr;

    @Property
    @Persist
    private ConceptoRemunerativo conceptoRemunerativo;

    @Property
    @SessionState
    private Usuario loggedUser;

    @Property
    @SessionState
    private EntidadUEjecutora _oi;

    @Inject
    private PropertyAccess _access;



    @InjectComponent
    private Zone listaConceptosRemunerativosZone;



        public boolean getUsuarioGeneral() {
        return Helpers.esMultiOrganismo(loggedUser);
    }

    public boolean getNoUsuarioGeneral() {
        return !getUsuarioGeneral();
    }


    public boolean getPuedeEditar() {
        return Permisos.puedeEscribir(loggedUser, _oi);
    }

    @Property
    @Persist
    private boolean editando;

    @Component(id = "formularioconceptoremunerativo")
    private Form formularioConceptoRemunerativo;



  public List<ConceptoRemunerativo> getConceptosRemunerativos() {
      Criteria c;
      c = session.createCriteria(ConceptoRemunerativo.class);
      c.add(Restrictions.eq("entidadUE", _oi));
     return c.list();
  }

  void onValidateFromFormularioConceptoRemunerativo() {
      Criteria c;
      c = session.createCriteria(ConceptoRemunerativo.class);
      c.add(Restrictions.eq("entidadUE", _oi));
      c.add(Restrictions.like("codigo", conceptoRemunerativo.getCodigo()));
      
      if (editando)
        c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));

      if (c.list().size() > 0 ) {
          formularioConceptoRemunerativo.recordError(Errores.ERROR_CONCEPTO_REPETIDO);
      }

      c = session.createCriteria(ConceptoRemunerativo.class);
      c.add(Restrictions.eq("entidadUE", _oi));
      c.add(Restrictions.like("descripcion", conceptoRemunerativo.getDescripcion()));

      if (editando)
        c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));

      if (c.list().size() > 0 ) {
          formularioConceptoRemunerativo.recordError(Errores.ERROR_CONCEPTO_DESC_REP);
      }


  }

  public List<String> getPeriodicidades() {
      return ConceptoRemunerativo.PERIODICIDADES;
  }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioConceptoRemunerativo()
    {
    	conceptoRemunerativo.setEntidadUE(_oi);
    	session.saveOrUpdate(conceptoRemunerativo);
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(conceptoRemunerativo.getId()), (editando ? Logger.CODIGO_OPERACION_ALTA : Logger.CODIGO_OPERACION_MODIFICACION), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);
        editando = false;

        conceptoRemunerativo = new ConceptoRemunerativo();


    	return this;
    }

	@Log
	void onPrepareFromFormularioConceptoRemunerativo()
	{
        if (conceptoRemunerativo==null)
            conceptoRemunerativo = new ConceptoRemunerativo();
	}

    /*
     * reset del formulario (borrar  objeto)
     */
    void onActionFromReset() {
        conceptoRemunerativo = new ConceptoRemunerativo();
    }

    /*
     * Borrar la fila
     */
 
    void onActivate(ConceptoRemunerativo c)
    {

        editando = (c !=null);
        if(c == null) 
            c = new ConceptoRemunerativo();
            
        // Le genero la sesión hibernate así puede identificarlo como igual
        //this._oi = (EntidadUEjecutora) session.load(EntidadUEjecutora.class, _oi.getId());

        this.conceptoRemunerativo = c; //(Usuario) session.load(Usuario.class, user.getId());
    }

    public List<String> getClasificaciones()
    {
    	return Helpers.getValorTablaAuxiliar("TipoRemuneracion", session);
    }
 
    public List<String> getConceptosStd()
    {
    	return Helpers.getValorTablaAuxiliar("TipoRemuneracionStd", session);
    }


    public boolean getEsBorrable() {
        /*
         * Buscamos;
         * remuneraciones que usen el concepto
         *
         */

        Criteria c;
        c = session.createCriteria(RemuneracionPersonal.class);
        c.add(Restrictions.eq("conceptoRemunerativo", cr));

        if(c.list().size() > 0) {
            return false;
        }
        return true;
    }

     @Log
     @CommitAfter
     Object onBorrarDato(ConceptoRemunerativo dato) {
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()),
                Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);

        session.delete(dato);
        return listaConceptosRemunerativosZone.getBody();// La/a zona a actualizar
    }
}
