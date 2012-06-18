/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import java.util.Date;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Usuario;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Entidad;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author ale
 */

@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
public class CambiarClave  extends GeneralPage {
	
    @Inject
    private Session session;

    @InjectComponent
	private Zone zone;

    @InjectComponent
    private Envelope envelope;

    @Property
    @SessionState
    private Usuario _usuario;

    @Component(id = "formulariocambioclave")
    private Form formulariocambioclave;

    @Property
    private String oldPass;

    @Property
    private String newPass1;

    @Property
    private String newPass2;
    
    @Property
    private String verifi;
    
    @Persist
    private boolean cambioForzado;
 
    private int num=0;
    
     public Boolean getNoEsAdmSystema(){
        if((_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)) ||(_usuario.getTipo_usuario().equals(Usuario.ADMINGRAL)))
            return Boolean.FALSE;
        
        return Boolean.TRUE;
    }

    @Property
    @SessionState
    private Entidad _entidadUE;
        
    public boolean isCambioForzado() {
		System.out.println("---> isCambioForzado: " + cambioForzado);
		return cambioForzado;
	}

	public void flagCambioForzado(String motivoDelCambioForzado) {
		System.out.println("---> flagCambioForzado: " + motivoDelCambioForzado);
		this.cambioForzado = true;
        formulariocambioclave.recordError(motivoDelCambioForzado);
	}

        public boolean getFormDisplay() {

            return envelope.getContents().length() ==0;
        }
        
         void onSelectedFromReset() {
        
            num=2;

        }
        
            @Log
    @CommitAfter
    Object onSuccessFromFormularioCambioClave()
    {
        if(num==2){     
            return "CambiarClave";
        }else {
        if (!oldPass.equals( _usuario.getMd5Clave())) {
             formulariocambioclave.recordError("Clave actual ingresada incorrecta.");
            //envelope.setContents("Clave actual ingresada incorrecta.");
        	return zone;
        }

        if(oldPass.equals(newPass1)) {
            formulariocambioclave.recordError("La nueva clave debe ser diferente a la actual.");
            //envelope.setContents("Clave actual ingresada incorrecta.");
            return zone;
        }

		if (!newPass1.equals(newPass2) ) {
                formulariocambioclave.recordError("Las claves ingresadas deben ser iguales.");
	//		envelope.setContents("Las claves ingresadas deben ser iguales.");
            return zone;
        }

        
        envelope.setContents("Clave modificada con éxito.");
        if(this.cambioForzado)
        	envelope.setContents("Clave modificada con éxito. <a href='/servir'>Reingresar al sistema.</a>");
        // formulariocambioclave.recordError("Clave modificada con éxito");
        _usuario.setMd5Clave(newPass1);
        
        _usuario.setUltimo_cambio_clave(new Date());
        
        session.saveOrUpdate(_usuario);
        }
    	return zone;
    	// return this;
    }

}
