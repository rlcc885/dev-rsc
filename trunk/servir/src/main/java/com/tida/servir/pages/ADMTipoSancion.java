/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.tida.servir.entities.TipoSancion;

/**
 *
 * @author mallco
 */
public class ADMTipoSancion 
{
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean vNoedita;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Property
    @SessionState
    private UsuarioAcceso usua;    
    @Persist
    @Property
    private Boolean vdetalle;
    @Property
    @SessionState
    private Usuario _usuario;    
    @Persist
    @Property
    private Boolean editando;
    @Component(id = "formularioMensajes")
    private Form formularioMensajes;
    @Inject
    private Session session;
    @InjectComponent
    private Zone listaTipoSancionZone;
    @InjectComponent
    private Zone tipoSancionZone;
    @InjectComponent
    private Zone mensajesZone;
    @InjectComponent
    private Envelope envelope;
    @Inject  
    private ComponentResources _resources;
         
     
     // datos de la clase
     @Property
     @Persist
     private String valdescripcion;
     @Property
     @Persist
     private DatoAuxiliar  valcategoria;
     @Property
     @Persist
     private DatoAuxiliar  valtipo;
     @Property
     @Persist
     private String      valdiasMin;
     @Property
     @Persist
     private String      valmesesMin;
     @Property
     @Persist
     private String     valaniosMin;
     @Property
     @Persist
     private String      valdiasMax;
     @Property
     @Persist
     private String      valmesesMax;
     @Property
     @Persist
     private String     valaniosMax;
     @Property
     @Persist
     private String      valtiempoD;
     @Property
     @Persist
     private String      valtiempoM;
     @Property
     @Persist
     private String      valtiempoA;
     @Property
     @Persist
     private String      valobservacion;
     
     @Inject
     private PropertyAccess _access;
    
     // VARIABLE DE LA GRID
     @Property
     @Persist
     private TipoSancion tipsancion;
    
     // OBJETO DE MANIPULACION
     @Property
     @Persist
     private TipoSancion tiposancionactual;


    
    
    //inicio de pagina 
    @Log
    void setupRender() {
        // inicio de variables
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
//        query.setParameter("in_pagename", "TIPOSANCION");
        List result = query.list();
        
        vdetalle=false;
        vformulario=false;
        vbotones=false;
        vNoedita=false;
        
        
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
           usua = (UsuarioAcceso) result.get(0);

           if (usua.getAccesoupdate() == 1) {veditar = true;}
        if (usua.getAccesodelete() == 1) {veliminar = true;}
        if (usua.getAccesoreport() == 1) 
        {
            vformulario = true;
            vbotones = true;
            if (usua.getAccesodelete() != 1){veliminar = false;}
        }
        if (vformulario == true){vNoedita=true;}
        editando = false;
        limpiar();
        }

    }

    @Log
    Object onReset()
    {
        if(vdetalle)
        {
            vformulario = false;
            vNoedita=false;
            if (usua.getAccesoreport() == 1) 
            {
                vformulario=true;
                vdetalle=false;
                vbotones=true;
                limpiar();
                formularioMensajes.clearErrors();
                editando = false;
                tiposancionactual = new TipoSancion();
                vNoedita=true;
            }
        }
        else
        {
            if (usua.getAccesoreport() == 0) 
            {
                vformulario=false;
                vdetalle=false;
                vbotones=false;
                vNoedita=false;
            }
            else
            {
                limpiar();
                formularioMensajes.clearErrors();
                editando = false;
               tiposancionactual = new TipoSancion();  
            }            
        }
        
      return actualizarZonas();  
    }

    @Log
    Object onCancel()
    {
            if (_usuario.getRolid() == 3) {
                return "Busqueda";
            } else  {
                return "TrabajadorPersonal";  
            }
    }

    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioTipoSancion() 
     {     
                 
           formularioMensajes.clearErrors();
           if(editando) 
            { if (usua.getAccesoreport() == 0) 
                {   vformulario = false;
                    vbotones=false;
                    vNoedita=false;
                }
            } 
            else {
               tiposancionactual = new TipoSancion();  
            }
            
            seteo();
                 
            session.saveOrUpdate(tiposancionactual);
            session.flush();

         /*   if (!editando) 
            {   
                logger.loguearEvento(session, Logger.MODIFICACION_DOCUMENTOS, actual.getEntidad().getId(), actual.getId(), _usuario.getId(), Logger.MOTIVO_DOCUMENTOS_DOCUMENTOS, constancia.getId());
            }
            
            if (valentregado != null) 
            {
                if (valentregado == true) 
                {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + constancia.getCargoasignado().getTrabajador().getId() + "' and tipoevento_id='" + Logger.MODIFICACION_DOCUMENTOS + "' and tabla_id='" + constancia.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }*/

            editando = false;
            limpiar();
            envelope.setContents("Tipo de sancion creado / modificado con exito");         
        
        
        return actualizarZonas();
     }
     
     
    
     Object actualizarZonas()
     {
         return new MultiZoneUpdate("listaTipoSancionZone",listaTipoSancionZone.getBody()).
                                add("tipoSancionZone",tipoSancionZone.getBody()).
                                add("mensajesZone",mensajesZone.getBody());
    
     }

    @Log
     public List<TipoSancion> getListadoTipoSanciones() {
        Criteria c2 = session.createCriteria(TipoSancion.class);
        return c2.list();
    }
	
     
    @Log
    //para obtener datatos de la categoria de la sancion
    public GenericSelectModel<DatoAuxiliar> getBeanCategoria() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    //para obtener datatos del tipo de sancion
    public GenericSelectModel<DatoAuxiliar> getBeanTipo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOINHABILITACION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    

    @Log
    void seteo(){

     tiposancionactual.setDescripcion(valdescripcion);    
     tiposancionactual.setCategoriaSancion(valcategoria);   
     tiposancionactual.setTipoInhabilitacion(valtipo);
     
     tiposancionactual.setTiempoMinAnios(Integer.parseInt(valaniosMin));
     tiposancionactual.setTiempoMinDias(Integer.parseInt(valdiasMin));
     tiposancionactual.setTiempoMinMeses(Integer.parseInt(valmesesMin));
     
     tiposancionactual.setTiempoMaxMeses(Integer.parseInt(valmesesMax));
     tiposancionactual.setTiempoMaxDias(Integer.parseInt(valdiasMax));
     tiposancionactual.setTiempoMaxAnios(Integer.parseInt(valaniosMax));
     
     tiposancionactual.setTiempoVisualizaAnios(Integer.parseInt(valtiempoA));
     tiposancionactual.setTiempoVisualizaDias(Integer.parseInt(valtiempoD));
     tiposancionactual.setTiempoVisualizaMeses(Integer.parseInt(valtiempoM));
     
     tiposancionactual.setObservaciones(valobservacion);
     
    }

    @Log
    void mostrar(){   
     valdescripcion = tiposancionactual.getDescripcion();    
     valcategoria = tiposancionactual.getCategoriaSancion();   
     valtipo = tiposancionactual.getTipoInhabilitacion();
     valaniosMin = tiposancionactual.getTiempoMinAnios().toString();
     valdiasMin = tiposancionactual.getTiempoMinDias().toString();
     valmesesMin = tiposancionactual.getTiempoMinMeses().toString();
     valmesesMax = tiposancionactual.getTiempoMaxMeses().toString();
     valdiasMax = tiposancionactual.getTiempoMaxDias().toString();
     valaniosMax = tiposancionactual.getTiempoMaxAnios().toString();
     valtiempoA = tiposancionactual.getTiempoVisualizaAnios().toString();
     valtiempoD = tiposancionactual.getTiempoVisualizaDias().toString();
     valtiempoM = tiposancionactual.getTiempoVisualizaMeses().toString();
     valobservacion = tiposancionactual.getObservaciones();             
        
    }

    @Log
    Object limpiar(){
          
     valdescripcion="";
     valcategoria= null;
     valtipo = null;
     valdiasMin = "0";
     valmesesMin = "0";
     valaniosMin = "0";
     valdiasMax = "0";
     valmesesMax = "0";
     valaniosMax = "0";
     valtiempoD = "0";
     valtiempoM = "0";
     valtiempoA = "0";
     valobservacion = "";
     
    return new MultiZoneUpdate ("tipoSancionZone",tipoSancionZone.getBody());
     
    }    
    
    @Log
    Object onActionFromEditar(TipoSancion dato) {
        
        tiposancionactual = dato;        
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        mostrar();
        
        return actualizarZonas();
    }

    @Log
    Object onActionFromDetalle(TipoSancion dato) {
        tiposancionactual = dato;
        mostrar();
        vdetalle = true;
        vbotones = false;
        if (usua.getAccesoupdate() ==1){vNoedita=true;}
        vformulario = true;
        return actualizarZonas();
    }
    
    @Log
    @CommitAfter
    Object onBorrarDato(TipoSancion dato) {
       session.delete(dato);
       envelope.setContents("Tipo de Sancion Eliminado");
       limpiar();
        return actualizarZonas();
    }
    
//    @Persist
    private String periodomaximoinhabilitacion;

    
    public String getPeriodomaximoinhabilitacion() {
       String cadena="";
       
       if(tipsancion.getTiempoMaxDias()!=0){cadena+=tipsancion.getTiempoMaxDias()+" DIAS ";}
       if(tipsancion.getTiempoMaxMeses()!=0){cadena+=tipsancion.getTiempoMaxMeses()+" MESES ";}
       if(tipsancion.getTiempoMaxAnios()!=0){cadena+=tipsancion.getTiempoMaxAnios()+" AÑOS ";}
    
    return cadena;

    }

    public void setPeriodomaximoinhabilitacion(String periodomaximoinhabilitacion) {
        this.periodomaximoinhabilitacion = periodomaximoinhabilitacion;
    }
    
    
    public boolean geteliminarSN()
    {
        
        Criteria c1 = session.createCriteria(Sancion2.class);
        c1.add(Restrictions.eq("tipoSancion", tipsancion));

        if (c1.list().isEmpty()){
          c1 = session.createCriteria(SancionRegimen.class);
          c1.add(Restrictions.eq("tipoSancion", tipsancion));
                if (c1.list().isEmpty()){
                    return true;
                }

        }
        
        
        
        return false;
    }
     
}
