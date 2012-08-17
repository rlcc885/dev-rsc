/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import com.tida.servir.entities.*;

import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
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
    
    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private String valfec_hasta;
    @Persist
    @Property
    private Date fecha_desde;
    @Persist
    @Property
    private Date fecha_hasta;
    
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
        valfec_hasta=null;
        valfec_desde=null;
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vbotones = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                bvalidausuario = true;
            }
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                bvalidausuario = true;
            }
        }
        if (usua.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
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
        
        if(valfec_desde!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_desde = (Date)formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }
          
            if(valfec_hasta!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_hasta = (Date)formatoDelTexto.parse(valfec_hasta);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }
            
            ant_Laborales.setFec_ingreso(fecha_desde);
            ant_Laborales.setFec_egreso(fecha_hasta);
        
        if (ant_Laborales.getFec_egreso().before(ant_Laborales.getFec_ingreso()) || ant_Laborales.getFec_egreso().equals(ant_Laborales.getFec_ingreso())) {
            envelope.setContents("Las fecha de ingreso debe ser menor a la fecha de egreso");

        } else {
            Logger logger = new Logger();
            ant_Laborales.setTrabajador(actual);
            ant_Laborales.setEntidad(_oi);
            if (!editando) {
                //guardando
                if (_usuario.getRolid() == 1) {
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
            valfec_hasta=null;
            valfec_desde=null;
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
            valfec_hasta=null;
            valfec_desde=null;
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
        
        if(ant_Laborales.getFec_ingreso()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(ant_Laborales.getFec_ingreso());
        }
        if(ant_Laborales.getFec_egreso()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(ant_Laborales.getFec_egreso());
        }
        
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
        envelope.setContents("Antecedentes Laborales eliminados exitosamente.");
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
