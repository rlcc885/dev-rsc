package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;




/**
 *
 * @author ale
 */
public class PublicacionesEditor {
   
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
   
    
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    @InjectComponent
    private Zone mensajesZone;  
    
    @InjectComponent
    private Zone proIntelectualZone;
    
    private int elemento=0;
     
    @Parameter
    @Property
    private Trabajador actual;

    @Persist
    @Property
    private Publicacion publicacion;
    
    @Property
    @Persist
    private boolean bvalidausuario;
   
    //Listado de experiencia laboral
    @InjectComponent
    private Zone listaProIntelectualZone;
    @Persist
    @Property
    private Publicacion listaprointelectual;
    
 
    @Inject
    private PropertyAccess _access;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            publicacion = new Publicacion();
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
    }
    
    @Log
    public List<Ant_Laborales> getListadoProIntelectual() {
        Criteria c = session.createCriteria(Publicacion.class);
        c.add(Restrictions.eq("trabajador",actual));  
        return c.list();
    }
    
     //para obtener datatos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanClasePublicacion() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CLASEPRODUCCIONINTELECTUAL", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Sector Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipo() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSPUBLICACION", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormularioprointelectual() {
      
        publicacion.setTrabajador(actual);
        publicacion.setEntidad(_oi);
        session.saveOrUpdate(publicacion);
        envelope.setContents(helpers.Constantes.PROD_INTELECTUAL_EXITO);

        //return mensajesZone.getBody();
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody())                             
                .add("listaProIntelectualZone", listaProIntelectualZone.getBody());   
        
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            publicacion=new Publicacion();;
            return  proIntelectualZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(Publicacion publi) {        
        publicacion=publi;
           return proIntelectualZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(Publicacion publi) {
        session.delete(publi);
           return listaProIntelectualZone.getBody();
    }
    
    
    


}
