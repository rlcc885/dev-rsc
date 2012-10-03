/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author arson
 */
public class AnularSancion extends GeneralPage {
    @Persist
    @Property
    private String bdenoentidad;
    @Inject
    private Session session;
    @SessionState
    private Usuario loggedUser;
    @InjectComponent
    private Envelope envelope; 
    @Component(id = "formularioanularsancion")
    private Form formularioanularsancion;
    @Component(id = "formmensaje")
    private Form formmensaje;
    @Persist
    @Property
    private String entidad_origen;
    @InjectComponent
    @Property
    private Zone busZone2;
    @InjectComponent
    @Property
    private Zone busZone;
     @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad2;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad_not;
    @Persist
    @Property
    private String bnumeroDocumento;
    @Persist
    @Property
    private String bnumeroDocumento2;
    @Persist
    @Property
    private String bnumeroDocumento_not;
    @Persist
    @Property
    private String observaciones;
    @Property
    @Persist
    private String fechadoc;
    @Property
    @Persist
    private String fechadocnot;
     @Persist
    @Property
    private String juzgado_not;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private LkBusquedaEntidad entio;
    @Property
    @Persist
    private Entidad entidad2;
    @Persist
    @Property
    private Long entidad_origen_id;
     
     @Log
      public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
     
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (bdenoentidad != null && !bdenoentidad.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()));
        }        
        return c.list();
    }
    
    @Log
    Object onActionFromSeleccionar(Entidad enti2) {        
        entidad2 = enti2;
        entidad_origen=entidad2.getDenominacion();
        entidad_origen_id = entidad2.getId();
        return busZone.getBody();  
    }
    
    Object onBuscarentidad(){
         return busZone.getBody();
          //return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("busZone", busZone2.getBody());
      } 
}
