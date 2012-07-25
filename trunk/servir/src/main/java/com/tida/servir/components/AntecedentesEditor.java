/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.*;

import helpers.Errores;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author ale
 */
public class AntecedentesEditor {



 

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
    private Zone antLaboralZone;
    
    private int elemento=0;
     
    @Parameter
    @Property
    private Trabajador actual;

    @Persist
    @Property
    private Ant_Laborales ant_Laborales;
    
    @Property
    @Persist
    private boolean bvalidausuario;
    
    //Listado de experiencia laboral
    @InjectComponent
    private Zone listaAntLoboralZone;
    @Persist
    @Property
    private Ant_Laborales listaantlaborales;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            ant_Laborales = new Ant_Laborales();
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
    }
    
    @Log
    public List<Ant_Laborales> getListadoAntLaborales() {
        Criteria c = session.createCriteria(Ant_Laborales.class);
        c.add(Restrictions.eq("trabajador",actual));  
        return c.list();
    }
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormularioantlaboral() {
        if (ant_Laborales.getFec_egreso().before(ant_Laborales.getFec_ingreso()) || ant_Laborales.getFec_egreso().equals(ant_Laborales.getFec_ingreso())){
            envelope.setContents("Las fecha de ingreso debe ser menor a la fecha de egreso");  
   
        }else{
          ant_Laborales.setTrabajador(actual);
            ant_Laborales.setEntidad(_oi);
            session.saveOrUpdate(ant_Laborales);
            envelope.setContents(helpers.Constantes.ANT_LABORAL_EXITO);
            ant_Laborales=new Ant_Laborales();
        }
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody())                             
                    .add("listaAntLoboralZone", listaAntLoboralZone.getBody());   
        
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
         System.out.println("1: "+elemento );
        if(elemento==1){
            ant_Laborales=new Ant_Laborales();
            return  antLaboralZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(Ant_Laborales antLab) {        
        ant_Laborales=antLab;
           return antLaboralZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(Ant_Laborales antLab) {
        session.delete(antLab);
        envelope.setContents("Se realizo la elimiación satisfactoriamente");
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody())                             
                    .add("listaAntLoboralZone", listaAntLoboralZone.getBody());
    }
   
}



