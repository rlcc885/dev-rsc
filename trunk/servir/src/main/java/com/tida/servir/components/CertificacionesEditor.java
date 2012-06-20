package com.tida.servir.components;

import com.tida.servir.entities.Certificacion;
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
public class CertificacionesEditor {
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

    @InjectComponent
    private Envelope envelope;
        
    @Property
    @SessionState
    private Entidad_BK _oi;

    @Component(id = "certificacionesEdit")
    private Form _form;

    @Property
    private Certificacion certificacion;

    @Inject
    private Session session;


    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }
    
    public boolean getEsTrabajadorEditable(){
        if(certificacion != null){
            if((certificacion.getAgregadoTrabajador() == null)){
                return true;
            }
                
            if(certificacion.getAgregadoTrabajador() == false){
                return true;
            }
        }
        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
    }

    public PrimaryKeyEncoder<Long, Certificacion> getEncoder()
    {
    	return new PrimaryKeyEncoder<Long, Certificacion>()
    	{
    		public Long toKey(Certificacion value)
    		{
    			return value.getId();
    		}

    		public void prepareForKeys(List<Long> keys)
    		{
    		}

    		public Certificacion toValue(Long key)
    		{
    			return (Certificacion) session.get(Certificacion.class, key);
    		}

    		public Class<Long> getKeyType() {
    			return Long.class;
    		}
    	};
    }

    @CommitAfter
    public Object onSuccess()
    {
    	for(Certificacion cer : actual.certificaciones) {

    		if(cer.getFec_emision().after(new Date())) {
    			Logger logger = new Logger();
    			logger.loguearError(session, _usuario, cer.getId().toString(),
    					Logger.CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL,
    					Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL, Logger.TIPO_OBJETO_CERTIFICACION);

    			_form.recordError(Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL);
    			return this;
    		}
    	}
    	_form.clearErrors();
        envelope.setContents(helpers.Constantes.CERTIFICACION_EXITO);
        
    	return this;
    }

    @Log
    Object onValidateFormcertificacionesEdit()
    {

    	return this;
    }
  
    @CommitAfter
    Object onAddRow()
    {
    	Certificacion cert = new Certificacion();
        if(actual.getCertificaciones()  == null){
            actual.setCertificaciones(new ArrayList<Certificacion>());
        }
    	cert.setTrabajador(actual);
        if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
            cert.setAgregadoTrabajador(Boolean.TRUE);
    	actual.getCertificaciones().add(cert);
    	session.saveOrUpdate(actual);
    	new Logger().loguearOperacion(session, _usuario, String.valueOf(cert.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CERTIFICACION);
    	return cert;
    }

    @CommitAfter
    void onRemoveRow(Certificacion cert)
    {
        actual.getCertificaciones().remove(cert);
    	session.delete(cert);
    	new Logger().loguearOperacion(session, _usuario, String.valueOf(cert.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CERTIFICACION);
    }

     Object onFailure() {
          return this;
    }
     

}
