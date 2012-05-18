package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;

import helpers.Constantes;
import helpers.Logger;

import java.util.Date;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;

/**
 * Clase que maneja las unidades ejecutoras
 * @author ale
 */
public class DesasignarCargo extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @PageActivationContext
    @Persist
    private CargoAsignado ca;
    @Property
    @SessionState
    private EntidadUEjecutora _oi;

    @Property
    private Trabajador t;
    @Component(id = "formulariodesasignar")
    private Form formDesasignar;
    @Component(id = "formularioconfirmaciondesasignacion")
    private Form formConfirmar;
    @InjectComponent
    private Zone motivoZone;
    @InjectComponent
    private Zone confirmarZone;
    @Property
    @Persist
    private Date fec_fin;
    @Property
    @Persist
    private String motivo_cese;
    @Property
    private boolean equivoco;
    @Property
    private boolean cancelar;
    @Property
    private boolean desasignar;
    @Property
    @SessionState
    private Usuario _usuario;

    void onSelectedFromDesasignarEquivoco() {
        equivoco = true;
        desasignar = true;
    }

    void onSelectedFromCancelar() {
        cancelar = true;
    }

    private MultiZoneUpdate dosZones() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("confirmarZone", confirmarZone.getBody()).add("motivoZone", motivoZone.getBody());
        return mu;
    }

    @Log
    Object onSuccessFromFormulariodesasignar() {

        if (cancelar) {
            return "Busqueda";
        }

        if (equivoco) {
            motivo_cese = "Asignación por equivocación";
        } else {
            if (motivo_cese == null || motivo_cese.trim().equals("")) {
                formDesasignar.recordError("Debe ingresar un motivo");
                return this;
            }
        }
        
        formDesasignar.clearErrors();
        desasignar = true;
        return dosZones();

    }

    public boolean getNoDesasignar() {
        return !desasignar;
    }

    Object onFailureFromFormulariodesasignar() {
        return this;
    }

    void onSelectedFromCancelarDesasignar() {
        cancelar = true;
        System.out.println("--- Seleccionó no está seguro !");
    }


    @Log
    @CommitAfter
    Object onSuccessFromFormularioConfirmacionDesasignacion() {
        if (cancelar == true) {
            desasignar = false;
            fec_fin = new Date();
            return Busqueda.class;

        }

        ca.setFec_fin(fec_fin);
        ca.setEstado(Constantes.ESTADO_BAJA);
        ca.setMotivo_cese(motivo_cese);
        session.saveOrUpdate(ca);
        fec_fin = new Date();
        new Logger().loguearOperacion(session, _usuario, String.valueOf(ca.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO_ASIGNADO);
        desasignar = false;
        return Busqueda.class;

    }

    void onActivate(CargoAsignado c) {
        ca = c;
        t = c.getTrabajador();
        //fec_fin = new Date();
        desasignar = false;
    }

    CargoAsignado onPassivate() {
        return null;
    }
}