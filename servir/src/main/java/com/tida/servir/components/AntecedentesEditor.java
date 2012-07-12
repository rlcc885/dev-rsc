/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.Entidad;
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
import org.apache.tapestry5.ajax.MultiZoneUpdate;
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
import org.hibernate.Session;


/**
 *
 * @author ale
 */
public class AntecedentesEditor {
	// parameters of component
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String zone;

    @Parameter
    @Property
    private Trabajador actual;

    @Component(id = "antLaboralesEdit")
    private Form _form;

    @Property
    private Ant_Laborales ant_Laborales;

    @Property
    @SessionState
    private Usuario _usuario;

    @Property
    @SessionState
    private Entidad _oi;

    @Inject
    private Session session;
    
    private int elemento=0;
    
    @InjectComponent
    private Envelope envelope;
    @Log
    public PrimaryKeyEncoder<Long, Ant_Laborales> getEncoder()
    {
    	return new PrimaryKeyEncoder<Long, Ant_Laborales>()
    	{
    		public Long toKey(Ant_Laborales value)
    		{
    			return value.getId();
    		}

    		public void prepareForKeys(List<Long> keys)
    		{
    		}

    		public Ant_Laborales toValue(Long key)
    		{
    			return (Ant_Laborales) session.get(Ant_Laborales.class, key);
    		}

    		public Class<Long> getKeyType() {
    			return Long.class;
    		}
    	};
    }

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }
    @Log
    public boolean getEsTrabajadorEditable(){        
        if(ant_Laborales != null){
            if((ant_Laborales.getAgregadoTrabajador() == null)){
                return true;
            }
                
            if(ant_Laborales.getAgregadoTrabajador() == false){
                return true;
            }
        }
        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
    }

  @Log
  @CommitAfter
  Object onSuccessFromAntLaboralesEdit()
  {
      for(Ant_Laborales ant : actual.getAnt_Laborales()) {
                        if((ant.getFec_egreso() != null) && (ant.getFec_ingreso() != null )){
                            if (ant.getFec_egreso().before(ant.getFec_ingreso())) {
                                    Logger logger = new Logger();
                                    logger.loguearError(session, _usuario, String.valueOf(ant.getId()), 
                                                    Logger.CODIGO_ERROR_FECHA_EGRESO_PREVIA_INGRESO, 
                                                    Errores.ERROR_FECHA_EGRESO_PREVIA_INGRESO, Logger.TIPO_OBJETO_ANT_LABORALES);
                            _form.recordError(Errores.ERROR_FECHA_EGRESO_PREVIA_INGRESO);
                            return this;
                        }
                            if(ant.getFec_egreso().after(new Date())) {
                                    Logger logger = new Logger();
                                    logger.loguearError(session, _usuario, String.valueOf(ant.getId()), 
                                                    Logger.CODIGO_ERROR_FECHA_EGRESO_PREVIA_ACTUAL, 
                                                    Errores.ERROR_FECHA_EGRESO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_ANT_LABORALES);
                                    _form.recordError(Errores.ERROR_FECHA_EGRESO_PREVIA_ACTUAL);
                                    return this;
                            }
                        }
                    }

                    _form.clearErrors();
                    envelope.setContents(helpers.Constantes.ANT_LABORAL_EXITO);
                    return this;
        
  }

  @Log
  @CommitAfter
  Object onAddRow()
  { 
    Ant_Laborales ant = new Ant_Laborales();
    if (actual.getAnt_Laborales() == null) {
        actual.setAnt_Laborales(new ArrayList<Ant_Laborales>());
    }
    ant.setTrabajador(actual);
    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
            ant.setAgregadoTrabajador(Boolean.TRUE);        
    actual.getAnt_Laborales().add(ant);
    session.saveOrUpdate(actual);
    new Logger().loguearOperacion(session, _usuario, String.valueOf(ant.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ANT_LABORALES);
    return ant;

  }

  @Log
  @CommitAfter
  void onRemoveRow(Ant_Laborales ant_Laborales)
  {
        actual.getAnt_Laborales().remove(ant_Laborales);
        session.delete(ant_Laborales);
	new Logger().loguearOperacion(session, _usuario, String.valueOf(ant_Laborales.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ANT_LABORALES);
  }

     Object onFailure() {
          return this;
    }
     
     
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        
        if(elemento==1){
            ant_Laborales=null;
            return this;
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
}



