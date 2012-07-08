/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;



import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;

import org.hibernate.Session;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.*;

/**
 *
 * @author ale
 */
public class CargosAsignadosModificar  extends GeneralPage {
    @Inject
    private Session session;

    @PageActivationContext
    private CargoAsignado ca;

    @Property
    @SessionState
    private Entidad_BK _oi;

    public CargoAsignado getCa() {
        return ca;
    }

    public void setCa(CargoAsignado ca) {
        this.ca = ca;
    }

  
    private Trabajador trabajador;

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }


    
    // zonas relacionadas al ABM de Cargos
    

//    @InjectComponent
//    private Zone remuneracionesPersonalesZone;
//
//
//    @InjectComponent
//    private Zone evaluacionesPersonalesZone;
//
//    @InjectComponent
//    private Zone ausLicPersonalesZone;


     @SetupRender
    void initializeValue()
    {
           trabajador = ca.getTrabajador();
    }
    
}
