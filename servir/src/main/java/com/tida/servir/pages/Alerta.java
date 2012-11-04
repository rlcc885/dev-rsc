package com.tida.servir.pages;

import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.LkBusquedaTrabajador;
import com.tida.servir.entities.Evento;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import helpers.Logger;
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
public class Alerta  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
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
    
    @InjectComponent
    private Zone listaentidad;
    @InjectComponent
    private Zone listaservir;
    @InjectComponent
    private Zone listasistemas;
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
    @Property
    @Persist
    private BusquedaEvento eventoservir1; 
    @Property
    @Persist
    private BusquedaEvento eventoservir2;
    @Property
    @Persist
    private BusquedaEvento eventoservir3;
    @Property
    @Persist
    private BusquedaEvento eventoentidad;
    @Property
    @Persist
    private BusquedaEvento eventoentidad2;
    @Property
    @Persist
    private BusquedaEvento eventosistemas;
    @Property
    @Persist
    private String pagina;
    
    // inicio de la pagina
    @Log
    @SetupRender
    private void inicio() {
        if(_usuario.getRolid()==4){
            mostrars=true;
        }
        if(_usuario.getRolid()==2){
            mostrare=true;
        }
        if(_usuario.getRolid()==3){
            mostrarse=true;
        }
        if(_usuario.getRolid()==1){
            mostrars=false;
            mostrare=false;
            mostrarse=false;
        }        
    }
    @Property    
    @Persist
    private String nroeventos;
    @Property    
    @Persist
    private String nrotrabajadoresservir;
    @Property    
    @Persist
    private String nrotrabajadores;
    @Property    
    @Persist
    private String nroeventossis;
    @Property    
    @Persist
    private String nroeventosoliser;
    @Property    
    @Persist
    private String nroeventosolienti;
    
    @Log
    public List<BusquedaEvento> getEventos() {
        Query query = session.getNamedQuery("callSpEventoAcceso");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_tipoevento_id",6);
        query.setParameter("in_perfil_id",""); 
        query.setParameter("in_entidad_id","");        
        List result = query.list();
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nroeventos = Integer.toString(result.size());
        return result;
    }
    
    @Log
    public List<BusquedaEvento> getTrabajadoresservir() {
        Query query = session.getNamedQuery("callSpEventoAcceso");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_perfil_id",6); 
        query.setParameter("in_tipoevento_id","");        
        query.setParameter("in_entidad_id","");  
        List result = query.list();
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nrotrabajadoresservir = Integer.toString(result.size());
        return result;
    }
    
    @Log
    public List<LkBusquedaTrabajador> getTrabajadores() {
        Query query = session.getNamedQuery("callSpEventoAcceso");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_tipoevento_id","");
        query.setParameter("in_perfil_id",6);
        query.setParameter("in_entidad_id",entidadUE.getId());  
        List result = query.list();
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nrotrabajadores = Integer.toString(result.size());
        return result;
    }    
    
    @Log
    public List<BusquedaEvento> getEventossistemas() {
        Query query = session.getNamedQuery("callSpEventoAcceso");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_tipoevento_id","");
        query.setParameter("in_perfil_id",""); 
        query.setParameter("in_entidad_id","");  
        List result = query.list(); 
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nroeventossis = Integer.toString(result.size());
        return result;
    }
    
    @Log
    public List<BusquedaEvento> getSolicitudesservir() {
        Query query = session.getNamedQuery("callSpEventoSolicitud");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_tipoevento_id",Logger.SOLICITUD_SANCION); 
        query.setParameter("in_entidad_id","");  
        List result = query.list(); 
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nroeventosoliser = Integer.toString(result.size());
        return result;
    }
    
    @Log
    public List<BusquedaEvento> getSolicitudesentidad() {
        Query query = session.getNamedQuery("callSpEventoSolicitud");
        query.setParameter("in_rol_id",_usuario.getRolid());
        query.setParameter("in_tipoevento_id",Logger.SOLICITUD_SANCION);
        query.setParameter("in_entidad_id",entidadUE.getId());  
        List result = query.list(); 
        
        for (int i = 0; i < result.size(); i++) {
            BusquedaEvento usu = (BusquedaEvento) result.get(i);
        }
        nroeventosolienti = Integer.toString(result.size());
        return result;
    }
   
    // editar el evento
    @Log
    @CommitAfter  
    Object onActionFromEditar(Evento ev2) {
        ev2.setEstadoevento(true);
        session.saveOrUpdate(ev2);
        return listasistemas.getBody();
    }  

    public String getSelectionRow() {
        if (actual != null) {
            if (eventoentidad.getTrabajadorid() == actual.getId()) {
                return "selected";
            }
            return "normal";
        }
        return "";

    }
    
    
    @Log
    @SetupRender
    void initializeValue(){        
    }
    
       
}