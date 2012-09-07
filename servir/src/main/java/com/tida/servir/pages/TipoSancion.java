/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioAcceso;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.List;
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
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author mallco
 */
public class TipoSancion 
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

    //inicio de pagina 
    @Log
    void setupRender() {
  /*      vdetalle=false;
        vformulario=false;
        vbotones=false;
        vNoedita=false;
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
        limpiar();*/
        
     vdetalle = false;   //ELIMINAR
     vformulario = true; //ELIMINAR
     vbotones = true;   //ELIMINAR
     vNoedita = true;   //ELIMINAR
    }



    @Log
    Object onActionFromEditar(/*ConstanciaDocumental cons*/) {
     //   constancia = cons;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        mostrar();
        return actualizarZonas();
    }

    @Log
    Object onActionFromDetalle(/*ConstanciaDocumental cons*/) {
        //constancia = cons;
        mostrar();
        vdetalle = true;
        vbotones = false;
        if (usua.getAccesoupdate() ==1){vNoedita=true;}
        vformulario = true;
        return actualizarZonas();
    }

    @Log
    Object onActionFromDetalles(/*ConstanciaDocumental cons*/) {
        return onActionFromDetalle(/*cons*/);
    }
    
    @Log
    @CommitAfter
    Object onBorrarDato(/*Estudios dato*/) {
    //    session.delete(dato);
    //    envelope.setContents("Estudio del Trabajador Eliminado");
        return actualizarZonas();
    }
	
    

    
    @Log
    Object onReset()
    {
 /*       if(vdetalle)
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
        //        constancia = new ConstanciaDocumental();
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
           //     constancia = new ConstanciaDocumental();  
            }            
        }
 */       
      return actualizarZonas();  
    }

    @Log
    Object onCancel()
    {
            if (_usuario.getRolid() == 1) {
           //     return "TrabajadorPersonal";
            } else {
             //   return Busqueda.class;
            } 
                return "TrabajadorPersonal";    
    }

    
     @Log
 //   @CommitAfter
    Object onSuccessFromFormularioTipoSancion() 
     {     
                 
           formularioMensajes.clearErrors();
           if(editando) 
            {
                if (usua.getAccesoreport() == 0) 
                {
                    vformulario = false;
                    vbotones=false;
                    vNoedita=false;
                }
            } 
            else 
            {
             //   constancia = new ConstanciaDocumental();
            }

           //***
           //LUGAR DE LA LOGICA DEL NEGOCIO
           
    /*       if (validarCamposNulos()==false)
           {
               return actualizarZonas();
           }*/
           
           //***
           
            seteo();
                       
         //   session.saveOrUpdate(constancia);
        //    session.flush();

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
            envelope.setContents("Documento creado / modificado con exito");         
        
        
        return actualizarZonas();
     }
     
     
    
     Object actualizarZonas()
     {
         return new MultiZoneUpdate("listaTipoSancionZone",listaTipoSancionZone.getBody()).
                                add("tipoSancionZone",tipoSancionZone.getBody()).
                                add("mensajesZone",mensajesZone.getBody());
    
     }
     
     
     // datos de la clase
     @Property
     @Persist
     private String valdescripcion;
     @Property
     @Persist
     private DatoAuxiliar  valcategoria;
     @Property
     @Persist
     private DatoAuxiliar  valregimen;
     @Property
     @Persist
     private DatoAuxiliar  valtipo;
     @Property
     @Persist
     private String      valdias;
     @Property
     @Persist
     private String      valmeses;
     @Property
     @Persist
     private String     valanos;
     @Property
     @Persist
     private String      valtiempo;
     @Property
     @Persist
     private String      valobservacion;
     
     @Inject
     private PropertyAccess _access;
/*    
     @Persist
     @Property
     private ConstanciaDocumental tsancion;
	
     @Log
     public List<ConstanciaDocumental> getListadoTipoSanciones() {
        Criteria c2 = session.createCriteria(ConstanciaDocumental.class);
        c2.add(Restrictions.eq("cargoasignado",getCargosAsignados()));
        c2.add(Restrictions.eq("legajo",buscarlegajo()));
        c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return c2.list();
    }
	*/
    @Log
    //para obtener datatos de la categoria de la sancion
    public GenericSelectModel<DatoAuxiliar> getBeanCategoria() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    @Log
    //para obtener datatos del regimen laboral
    public GenericSelectModel<DatoAuxiliar> getBeanRegimen() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
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
    }

    @Log
    void mostrar(){
    }

    @Log
    void limpiar(){
        
    valdescripcion="";
    valcategoria=null;
    valregimen=null;
    valtipo=null;
    valdias="";
    valmeses="";
    valanos="";
    valtiempo="";
    valobservacion="";
     
    }
    
 /*   boolean validarCamposNulos()
    {
      if (valdescripcion==null || "".equals(valdescripcion)){
          formularioMensajes.recordError(valmeses);
          return false;}  
      if (valcategoria==null){return false;}
      if (valregimen==null){return false;}
      if (valtipo==null){return false;}
      if (valtiempo==null || "".equals(valtiempo)){return false;}  

    return true;
    }*/
}

