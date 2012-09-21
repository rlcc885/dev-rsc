package com.tida.servir.pages;

import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.LkBusquedaTrabajador;
import com.tida.servir.entities.Evento;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import java.util.List;
import org.apache.tapestry5.annotations.*;

import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author LFL
 */
public class ABMSuspension  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Property
    @SessionState
    private Usuario _usuario;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    @Property
    @Persist
    private Suspension nuevasuspension;
    
    //campos formularios
    @Property
    @Persist
    private String bentidadinicio;
    // inicio de la pagina
    @Log
    @SetupRender
    private void inicio() {
        nuevasuspension=new Suspension();       
    }
    
    
       
}