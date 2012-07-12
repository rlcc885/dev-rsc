package com.tida.servir.pages;

import annotations.XHR;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.LkBusquedaEvento;
import com.tida.servir.entities.LkBusquedaTrabajador;
import com.tida.servir.entities.Evento;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class Alerta  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
//    @Property
//    private Trabajador e;
    @Property
    @SessionState
    private Entidad entidadUE;
//    @Property
//    private Entidad_BK oi;
    @Property
    @SessionState
    private Usuario _usuario;
//    @InjectComponent
//    private Envelope envelope;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    private GenericSelectModel<Entidad_BK> _beans;
    
//    @InjectComponent
//    private Zone listasistemas;
        @InjectComponent
    private Zone listaentidad;
    @Property
    @Persist
    private Evento e;
    @Property
    private LkBusquedaTrabajador lt;
    @Property
    @Persist
    private boolean mostrars;
    @Property
    @Persist
    private boolean mostrare;
    @Property
    @Persist
    private boolean mostrarse;
//    @Property
//    @Persist
//    private LkBusquedaEvento le;
    @Property
    @Persist
    private LkBusquedaEvento le;  
    @Property
    @Persist
    private BusquedaEvento la;  
    
    @Log
    @SetupRender
    private void inicio() {
        if(_usuario.getRol().getId()==4){
            mostrars=true;
        }
        if(_usuario.getRol().getId()==2){
            mostrare=true;
        }
        if(_usuario.getRol().getId()==3){
            mostrarse=true;
        }
        if(_usuario.getRol().getId()==1){
            mostrars=false;
            mostrare=false;
            mostrarse=false;
        }
        
    }
    
//    @Property
//    private Entidad_BK entidad_nueva;
    
    
//    public List<Entidad_BK> getEntidadesUEjecutoras() {
//        Criteria c;
//        c = session.createCriteria(Entidad_BK.class);
//        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
//        c.add(Restrictions.eq("verificado", Constantes.VERIFICADO));
//        return c.list();
//    }
//
//
//    public List<Trabajador> getEmpleados() {
//        Criteria c = session.createCriteria(CargoAsignado.class);
//        c.createAlias("trabajador", "trabajador");
//        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
//        c.add(Restrictions.eq("trabajador.verificado", Constantes.VERIFICADO));
//        c.setProjection(Projections.distinct(Projections.property("trabajador")));
//        return c.list();
//    }
 
    @Log
    public List<BusquedaEvento> getEventos() {
//        Criteria c = session.createCriteria(BusquedaEvento.class);
//        c.add(Restrictions.eq("estadoevento",false));        
//        return c.list();
        Query query = session.getNamedQuery("callSpEventoAcceso");
        query.setParameter("in_rol_id",2);
        query.setParameter("in_tipoevento_id",1);
        query.setParameter("in_perfil_id","");        
        List result = query.list();

        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
//            System.out.println(stock.getDescmenu()+stock.getActivo().toString());
        }
        return result;
    }
    
    @Log
    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("validado", true));
        c.add(Restrictions.eq("identidad",entidadUE.getId()));
        return c.list();
    }
    
    @Log
    @CommitAfter  
    Object onActionFromEditar(Evento pri) {
        pri.setEstadoevento(false);
        session.saveOrUpdate(pri);
        //session.flush();
        return listaentidad.getBody();
    }
    
    @Log
    Object onActionFromEditarenti(Cargoxunidad c) {
        return listaentidad.getBody();
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
   
 
//     public String getSelectionRow() {
//        if (actual != null) {
//            if (e.getId() == actual.getId()) {
//                return "selected";
//            }
//            return "normal";
//        }
//        return "";
//
//    }
    
    @Log
    @SetupRender
    void initializeValue()
    {
        
//        if (entidadUE == null){
//                //Cargamos alguna entidad
//                Criteria c = session.createCriteria(Entidad_BK.class);
//                c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
//                entidad_nueva = entidadUE = (Entidad_BK)c.list().get(0);
//
//         }
        
    }
    
       
}