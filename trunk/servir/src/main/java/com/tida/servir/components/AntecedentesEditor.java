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
import org.hibernate.Query;
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
    private int elemento = 0;
    @Parameter
    @Property
    private Trabajador actual;
    @Persist
    @Property
    private Ant_Laborales ant_Laborales;
    //Listado de experiencia laboral
    @InjectComponent
    private Zone listaAntLoboralZone;
    @Persist
    @Property
    private Ant_Laborales listaantlaborales;
    @Persist
    @Property
    private Boolean editando;
    //validaciones
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Property
    @Persist
    private boolean bvalidausuario;
    @Property
    @SessionState
    private UsuarioAcceso usua;

    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
        ant_Laborales = new Ant_Laborales();
        bvalidausuario = false;
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vbotones = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                bvalidausuario = true;
            }
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                bvalidausuario = true;
            }
        }
        if (usua.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                bvalidausuario = true;
            }
        }
        editando = false;
    }

    @Log
    public List<Ant_Laborales> getListadoAntLaborales() {
        Criteria c = session.createCriteria(Ant_Laborales.class);
        c.add(Restrictions.eq("trabajador", actual));
        return c.list();
    }

    void onSelectedFromCancel() {
        elemento = 2;
    }

    void onSelectedFromReset() {
        elemento = 1;
        if (usua.getAccesoreport() == 0) {
            vformulario = false;
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioantlaboral() {
        if (ant_Laborales.getFec_egreso().before(ant_Laborales.getFec_ingreso()) || ant_Laborales.getFec_egreso().equals(ant_Laborales.getFec_ingreso())) {
            envelope.setContents("Las fecha de ingreso debe ser menor a la fecha de egreso");

        } else {
            Logger logger = new Logger();
            ant_Laborales.setTrabajador(actual);
            ant_Laborales.setEntidad(_oi);
            if (!editando) {
                //guardando
                if (_usuario.getRol().getId() == 1) {
                    ant_Laborales.setAgregadoTrabajador(true);
                } else {
                    ant_Laborales.setAgregadoTrabajador(false);
                }
            } else {//editando
                if (usua.getAccesoreport() == 0) {
                    vformulario = false;
                }
            }

            session.saveOrUpdate(ant_Laborales);
            session.flush();

            if (!editando) {
                logger.loguearEvento(session, logger.MODIFICACION_EXPERIENCIA, actual.getEntidad().getId(), actual.getId(), logger.MOTIVO_PERSONALES_EXPERIENCIA, ant_Laborales.getId());
            }
            if (ant_Laborales.getValidado() != null) {
                if (ant_Laborales.getValidado() == true) {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + ant_Laborales.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_EXPERIENCIA + "' and tabla_id='" + ant_Laborales.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }
            editando = false;
            envelope.setContents(helpers.Constantes.ANT_LABORAL_EXITO);
            ant_Laborales = new Ant_Laborales();
        }
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody()).add("listaAntLoboralZone", listaAntLoboralZone.getBody()).add("antLaboralZone", antLaboralZone.getBody());

    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        System.out.println("1: " + elemento);
        if (elemento == 1) {
            ant_Laborales = new Ant_Laborales();
            editando = false;
            return antLaboralZone.getBody();
        } else if (elemento == 2) {
            return "Busqueda";
        } else {
            return this;
        }

    }

    @Log
    Object onActionFromEditar(Ant_Laborales antLab) {
        ant_Laborales = antLab;
        editando = true;
        vformulario = true;
        vdetalle = false;
        vbotones = true;
        return antLaboralZone.getBody();
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar(Ant_Laborales antLab) {
        session.delete(antLab);
        envelope.setContents("Se realizo la elimiación satisfactoriamente");
        return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody()).add("listaAntLoboralZone", listaAntLoboralZone.getBody());
    }

    @Log
    Object onActionFromDetalle(Ant_Laborales antLab) {
        ant_Laborales = antLab;
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        return antLaboralZone.getBody();
    }

    @Log
    Object onActionFromDetalles(Ant_Laborales antLab) {
        ant_Laborales = antLab;
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        return antLaboralZone.getBody();
    }
}
