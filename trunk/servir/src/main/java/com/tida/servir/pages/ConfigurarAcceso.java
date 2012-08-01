package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Tipoevento;
import com.tida.servir.entities.Usuario;
import helpers.Logger;
import helpers.SMTPConfig;
import java.util.Date;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 * Configurar Acceso de Usuarios
 *
 * @author ffernandez
 *
 */
public class ConfigurarAcceso extends GeneralPage {

    @Inject
    private Session session;
    @InjectComponent
    private Zone zone;
    @InjectComponent
    private Envelope envelope;
    @Property
    @SessionState
    private Usuario usuario;
    @Component(id = "formularioconfiguraracceso")
    private Form formularioconfiguraracceso;
    @Property
    @Persist
    private ConfiguracionAcceso ca;
    private int num = 0;
    @Property
    @Persist
    private String vigenciaClave;

    @Log
    @CommitAfter
    void onPrepareFromFormularioconfiguraracceso() {
        if (num == 2 || num == 3) {
        } else {
            ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
            vigenciaClave = ca.getDuracion_clave().toString();
        }
    }

    void onSelectedFromReset() {
        num = 2;
        ca = new ConfiguracionAcceso();
    }

    void onSelectedFromCancel() {
        num = 3;
        ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
    }

    void onSelectedFromSave() {
        num = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioconfiguraracceso() {
        if (num == 2) {
            return zone;
        } else if (num == 3) {
            System.out.println("cancelarrr");
            return zone;
        } else if (num == 1) {
            Date fechaactual = new Date();
            ca.setFec_actualizacion(fechaactual);
            ca.setDuracion_clave(Long.parseLong(vigenciaClave));
            session.saveOrUpdate(ca);
            envelope.setContents(helpers.Constantes.CONFIGURAR_ACCESO_EXITO);
            System.out.println("guardarrr");
            return zone;
        }
        return zone;
    }

    @Log
    @CommitAfter
    Object onProbarConexion() {
        if (SMTPConfig.sendMail("TEST","TEST",ca.getSMTP_usuario(), ca)) {
            envelope.setContents("Configuración correcta.");
        } else {
            Logger logger = new Logger();
            Tipoevento tipoeve = new Tipoevento();
            tipoeve.setId(2);
            //logger.loguearEvento(session,tipoeve,usuario.getEntidad(),usuario.getTrabajador().getId(),"Existe un error en la conexi&oacute;n con el Servidor de Correos.");
            formularioconfiguraracceso.recordError("Existe un error en la conexión con el Servidor de Correos.");
        }
        return zone.getBody();
    }
}