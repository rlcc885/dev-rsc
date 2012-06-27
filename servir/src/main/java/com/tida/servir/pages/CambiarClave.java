/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import helpers.Encriptacion;
import java.util.Date;
import org.apache.tapestry5.annotations.*;
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
public class CambiarClave extends GeneralPage {

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
    @Persist
    private boolean cambioForzado;

    public Boolean getNoEsAdmSystema() {
        if ((_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)) || (_usuario.getTipo_usuario().equals(Usuario.ADMINGRAL))) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
    @Property
    @SessionState
    private Entidad_BK _entidadUE;

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

    @Log
    @CommitAfter
    Zone onSuccessFromFormularioCambioClave() {
        if (!Encriptacion.encriptaEnMD5(oldPass).equals(_usuario.getMd5Clave())) {
            formulariocambioclave.recordError("Clave actual ingresada incorrecta.");
            //envelope.setContents("Clave actual ingresada incorrecta.");
            return zone;
        }

        if (oldPass.equals(Encriptacion.encriptaEnMD5(newPass1))) {
            formulariocambioclave.recordError("La nueva clave debe ser diferente a la actual.");
            //envelope.setContents("Clave actual ingresada incorrecta.");
            return zone;
        }

        if (!newPass1.equals(newPass2)) {
            formulariocambioclave.recordError("Las claves ingresadas deben ser iguales.");
            //		envelope.setContents("Las claves ingresadas deben ser iguales.");
            return zone;
        }


        envelope.setContents("Clave modificada con éxito.");
        if (this.cambioForzado) {
            envelope.setContents("Clave modificada con éxito. <a href='/servir'>Reingresar al sistema.</a>");
        }
        // formulariocambioclave.recordError("Clave modificada con éxito");
        _usuario.setMd5Clave(Encriptacion.encriptaEnMD5(newPass1));
        _usuario.setClave(newPass1);
        _usuario.setUltimo_cambio_clave(new Date());
        _usuario.setIntentos_fallidos(0L);
        session.saveOrUpdate(_usuario);

        return zone;
        // return this;
    }
}
