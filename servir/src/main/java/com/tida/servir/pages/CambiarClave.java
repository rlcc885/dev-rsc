/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Encriptacion;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */

public class CambiarClave extends GeneralPage {

    @Inject
    private Session session;
    @InjectComponent
    private Zone zone;
    @InjectComponent
    private Zone mensajeZone;    
    
    @InjectComponent
    private Envelope envelope;
    @Property
    @SessionState
    private Usuario _usuario;
    @Component(id = "formulariocambioclave")
    private Form formulariocambioclave; 
    @Persist
    @Property
    private String oldPass;
    @Persist
    @Property
    private String newPass1;
    @Persist
    @Property
    private String newPass2;
    @Persist
    private boolean cambioForzado;
    
    @Property
    private String verificacion;
         
    @Component(id = "formmensaje")
    private Form formmensaje;
    
    private int elemento=0;

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

        return envelope.getContents().length() == 0;
    }

    // fromulario principal
    @Log
    @CommitAfter
    Object onSuccessFromFormularioCambioClave() {
         if(elemento==1){
            return  "CambiarClave";
        }
         else{ 
             
            if(oldPass == null){
                formulariocambioclave.recordError("Tiene que Ingresa la Clave Actual");
                return zone.getBody();
            }
            
            if(newPass1 == null){
                formulariocambioclave.recordError("Tiene que Ingresa una Clave Nueva");
                return zone.getBody();
            }
            
            if(newPass2 == null){
                formulariocambioclave.recordError("Tiene que Ingresa una Clave Nueva");
                return zone.getBody();
            }

            if (!Encriptacion.encriptaEnMD5(oldPass).equals(_usuario.getMd5Clave())) {
                verificacion="";
                formulariocambioclave.recordError("Clave actual ingresada incorrecta.");
                 return zone.getBody(); 
            }
           
            
            if (oldPass.equals(newPass1)) {
                verificacion="";
                formulariocambioclave.recordError("La nueva clave debe ser diferente a la actual.");
                return zone.getBody(); 
            }

            if (!newPass1.equals(newPass2)) {
                verificacion="";
                formulariocambioclave.recordError("Las claves ingresadas deben ser iguales.");
                return zone.getBody(); 
            }
           if(_usuario.getTrabajador()!=null){ 
            if (_usuario.getTrabajador().getNombres().indexOf((newPass1.toUpperCase()))>=0) {
                verificacion="";
                formulariocambioclave.recordError("La claves ingresada contiene el nombre del usuario.");
                return zone.getBody(); 
            }
            
            if (_usuario.getTrabajador().getApellidoMaterno().indexOf((newPass1.toUpperCase()))>=0) {
                verificacion="";
                formulariocambioclave.recordError("La clave ingresada contiene el apellido materno del usuario.");
                return zone.getBody(); 
            }
            
            if (_usuario.getTrabajador().getApellidoPaterno().indexOf((newPass1.toUpperCase()))>=0) {
                verificacion="";
                formulariocambioclave.recordError("La clave ingresada contiene el apellido paterno del usuario.");
                return zone.getBody(); 
            }
           }
            if (_usuario.getLogin().indexOf(newPass1)>=0) {
                verificacion="";
                formulariocambioclave.recordError("La clave ingresada contiene el login del usuario.");
                return zone.getBody(); 
            }
            
            envelope.setContents("Clave modificada con exito.");
            
            if (this.cambioForzado) {
                envelope.setContents("Clave modificada con exito. <a href='/servir'>Reingresar al sistema.</a>");
            }     
            _usuario.setMd5Clave(Encriptacion.encriptaEnMD5(newPass1));
            _usuario.setUltimo_cambio_clave(new Date());
            _usuario.setIntentos_fallidos(0L);
            session.saveOrUpdate(_usuario);

            return new MultiZoneUpdate("zone", zone.getBody())                             
                    .add("mensajeZone",mensajeZone.getBody()); 
         }
    }
    
     void onSelectedFromReset() {
         elemento=1;
    }
}