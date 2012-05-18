package com.tida.servir.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Usuario;

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
    private Usuario _usuario;

    @Component(id = "formularioconfiguraracceso")
    private Form formularioconfiguraracceso;

    @Property
    private ConfiguracionAcceso ca;

    @Log
    @CommitAfter
    void onPrepareFromFormularioconfiguraracceso()
    {
    	ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
    }
    
    @Log
    @CommitAfter
    Zone onSuccessFromFormularioconfiguraracceso()
    {
    	session.saveOrUpdate(ca);
    	return zone;
    }

}
