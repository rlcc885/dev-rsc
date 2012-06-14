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
import org.apache.tapestry5.annotations.Persist;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Usuario;
import java.util.Date;

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
    
    
    private int num=0;

    @Log
    @CommitAfter
    void onPrepareFromFormularioconfiguraracceso()
    {
        if(num==2){   
        }
        else{
            ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
        }
    	
    }
    void onSelectedFromReset() {
        
        num=2;
        ca=new ConfiguracionAcceso();

    }
    void onSelectedFromCancel() {
        
        num=3;

    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioconfiguraracceso()
    {
        if(num==2){     
            
        }else if(num==3){
            return "Inicio";
        } 
        else{
            Date fechaactual=new Date();
            ca.setFec_actualizacion(fechaactual);
            session.saveOrUpdate(ca);    	
        }
        return zone;
    }

}