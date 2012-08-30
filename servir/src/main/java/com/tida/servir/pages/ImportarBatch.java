/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Query;

/**
 *
 * @author ale
 */
public class ImportarBatch extends GeneralPage{


    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Usuario usuario;
    @Property
    @SessionState
    private Entidad entidadUE;    

    @Inject
    private PropertyAccess _access;

    @Inject
    private Request _request;

    @Inject
    private ComponentResources _resources;
    
    @Persist
    @Property
    private String tipocarga;
    @Persist
    @Property
    private String tipoproceso;
    private UploadedFile file;
    
    @Log
    @SetupRender
    private void inicio() {

    }


}
