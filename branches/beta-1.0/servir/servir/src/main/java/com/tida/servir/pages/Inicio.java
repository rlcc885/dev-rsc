package com.tida.servir.pages;

import annotations.XHR;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import helpers.Constantes;

import helpers.Helpers;
import com.tida.servir.services.SelectIdModelFactory;
import helpers.Errores;
import helpers.Logger;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;

import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;

import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class Inicio  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
    @Property
    private Trabajador e;
    @Property
    @Persist
    private Entidad_BK entidadUE;
    @Property
    private Entidad_BK oi;
    @Property
    @SessionState
    private Usuario _usuario;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    private GenericSelectModel<Entidad_BK> _beans;
    
    @InjectComponent
    private Zone empleadoszone;
    
    @Property
    private Entidad_BK entidad_nueva;
    
    
    public List<Entidad_BK> getEntidadesUEjecutoras() {
        Criteria c;
        c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
        c.add(Restrictions.eq("verificado", Constantes.VERIFICADO));
        return c.list();
    }


    public List<Trabajador> getEmpleados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        c.add(Restrictions.eq("trabajador.verificado", Constantes.VERIFICADO));
        c.setProjection(Projections.distinct(Projections.property("trabajador")));
        return c.list();
    }
 
    /*
    @Log
    void onActionFromReset() {
        entidadUE = new EntidadUEjecutora();
        e=new Trabajador();
    }

    @Log
    public void onActivate(EntidadUEjecutora eue,Trabajador t) {
        entidadUE = eue;
         e = t;
        //editando = true;
    }
    */
    
    // TODO revisar JZM
//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.muestraGrillaEntidad(_usuario);
//    }
   
 
     public String getSelectionRow() {
        if (actual != null) {
            if (e.getId() == actual.getId()) {
                return "selected";
            }
            return "normal";
        }
        return "";

    }
    
    @Log
    @SetupRender
    void initializeValue()
    {
        
        if (entidadUE == null){
                //Cargamos alguna entidad
                Criteria c = session.createCriteria(Entidad_BK.class);
                c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
                entidad_nueva = entidadUE = (Entidad_BK)c.list().get(0);

         }
        
    }
    
       
}