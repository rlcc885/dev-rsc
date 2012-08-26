/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Trabajador;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class LegajosModificar  extends GeneralPage {

    @Inject
    private Session session;

    @Property
    @Persist
    private Trabajador trabajador;

    @Property
    @SessionState
    private Entidad_BK _oi;

    @Persist
    private Legajo legajo;
    
                        
    @InjectComponent
    private Zone constanciaIdentificacionPersonalFamiliarZone;

    @InjectComponent
    private Zone constanciaDatosPersonalesZone;


    @InjectComponent
    private Zone constanciaFormacionZone;

    @InjectComponent
    private Zone constanciaProduccionZone;


    @InjectComponent
    private Zone constanciaCarreraZone;


    @InjectComponent
    private Zone constanciaLicenciasZone;


    @InjectComponent
    private Zone constanciaEvaluacionesZone;

    @InjectComponent
    private Zone constanciaMeritosZone;


    @InjectComponent
    private Zone constanciaDemeritosZone;


    @InjectComponent
    private Zone constanciaBonificacionesZone;

    @InjectComponent
    private Zone constanciaRetencionesZone;

    @InjectComponent
    private Zone constanciaAntecedentesZone;

    public String getTitIdPers() {
        return "Identificación Personal/Familiar";
    }

    public String getcatIDPers() {
        //return "Identificación Personal / familiar";
        return "IDENTIFICACIÓN PERSONAL / FAMILIAR";
    }

    public String getTitDatosPers() {
        return "Datos Personales";
    }

    public String getcatDatosPers() {
        //return "Datos personales";
        return "DATOS PERSONALES";
    }

    public String getTitFormacion() {
        return "Formación";
    }

    public String getcatFormacion() {
        //return "Formación";
        return "FORMACIÓN";
    }

    public String getTitProduccion() {
        return "Producción Intelectual";
    }

    public String getcatProduccion() {
        //return "Producción intelectual";
        return "PRODUCCIÓN INTELECTUAL";
    }

    public String getTitCarrera() {
        return "Carrera Laboral";
    }

    public String getcatCarrera() {
        //return "Carrera laboral";
        return "CARRERA LABORAL";
    }

    public String getTitLicencias() {
        return "Licencias";
    }

    public String getcatLicencias() {
        //return "Licencias";
        return "LICENCIAS";
    }

    public String getTitEvaluaciones() {
        return "Evaluaciones";
    }

    public String getcatEvaluaciones() {
        //return "Evaluaciones";
        return "EVALUACIONES";
    }

    public String getTitMeritos() {
        return "Méritos";
    }

    public String getcatMeritos() {
        //return "Méritos y deméritos";
        return "MÉRITOS";
    }

    public String getTitDemeritos() {
        return "Deméritos";
    }

    public String getcatDemeritos() {
        //return "Méritos y deméritos";
        return "DEMÉRITOS";
    }

    public String getTitBonificaciones() {
        return "Bonificaciones";
    }

    public String getcatBonificaciones() {
        //return "Bonificaciones y retenciones";
        return "BONIFICACIONES Y RETENCIONES";
    }

    public String getTitRetenciones() {
        return "Retenciones";
    }

    public String getcatRetenciones() {
        //return "Bonificaciones y retenciones";
        return "BONIFICACIONES Y RETENCIONES";
    }

    public String getTitAntecedentes() {
        return "Antecedentes Laborales";
    }

    public String getcatAntecedentes() {
        //return "Antecedentes laborales";
        return "ANTECEDENTES LABORALES";
    }


    public Legajo getLegajo(){
        return legajo;
    }

    public void setLegajo(Legajo l) {
        legajo = l;
    }

    @Log
    void onActivate(Trabajador t)
    {
        trabajador = t;
         // Ahora buscamos el legajo.
        Criteria c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("entidadUE", _oi));
        c.add(Restrictions.eq("trabajador", trabajador));
        legajo = (Legajo)c.list().get(0);
    
    }
    
}
