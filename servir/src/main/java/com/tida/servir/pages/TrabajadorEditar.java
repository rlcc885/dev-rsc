/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Morgan
 */

package com.tida.servir.pages;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.MeritoDemerito;
import java.util.List;

import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Constantes;
import java.util.ArrayList;
import org.apache.tapestry5.PersistenceConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;


/**
 * Clase que maneja la pagina de modificacion de un Trabajador
 */
public class TrabajadorEditar extends GeneralPage{
    /**
     * Listas necesarias para los modelos.
     */

    private List<String> pais = new ArrayList<String>();
    private List<String> nacionalidad = new ArrayList<String>();
    private List<String> estadoCivil = new ArrayList<String>();
    private List<String> tipoDiscapacidad = new ArrayList<String>();

    @Property
    @SessionState
    private Entidad _oi;
    	
    @Property
    @Persist
    private Trabajador actual;
	/*
     * Código de grillas
     */

     @Property
     @Persist
     private CargoAsignado _ca;

    // Grilla de Antecedentes laborales
    @InjectComponent
    private Zone antecedentesZone;

    @InjectComponent
    private Zone publicacionesZone;

    @InjectComponent
    private Zone trabajosZone;

    @InjectComponent
    private Zone meritosZone;

    @InjectComponent
    private Zone demeritosZone;

    // Datos del Cargo Asignado.

    @InjectComponent
    private Zone remuneracionesPersonalesZone;


    @InjectComponent
    private Zone evaluacionesPersonalesZone;


    @InjectComponent
    private Zone ausLicPersonalesZone;


    @Persist(PersistenceConstants.FLASH)
    private String mensajes;// utilizado para mensajes globales, como ser que al crear un trabajador, ya existe

    /**
     * Hasta acá
     */
    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario _usuario;

    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }
    
    public TrabajadorEditar()
    {
    }

    /*
     * Hasta acá código de grillas
     */
    
    @Inject
    private PropertyAccess _access;
    
    public List<String> getPais(){
        return pais;
    }
    
    public List<String> getEstadoCivil() {
        return estadoCivil;
    }

    public List<String> getNacionalidad() {
        return nacionalidad;
    }

 
    public List<String> getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }
    
    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String msg) {
        mensajes = msg;
    }

    public boolean getHaymensajes () {
        return mensajes != null;
    }

    /*
    @SetupRender
    void loadTrabajador() {
        actual = (Trabajador) session.load(Trabajador.class, _usuario.getTrabajador().getId());
       
    }
    */

    @Log
    void onActivate() {
        actual = (Trabajador) session.load(Trabajador.class, _usuario.getTrabajador().getId());

        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.createAlias("legajo", "legajo");
        c.createAlias("cargo", "cargo");
        c.add(Restrictions.eq("legajo.entidadUE", _usuario.getEntidad()));
        c.add(Restrictions.eq("trabajador", actual));
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        if (c.list().size() > 0)
            _ca = (CargoAsignado) c.list().get(0);
        else _ca = null;
    }
//    public String getClasePublicacion() {
////        return Publicacion.CLASE_PUBLICACION;
//    }
//
//    public String getClaseTrabajo() {
////      return Publicacion.CLASE_INVESTIGACION;
//    }

//    public String getClaseMeritos() {
//        return MeritoDemerito.CLASE_MERITO;
//    }
//
//    public String getClaseDeMeritos() {
//        return MeritoDemerito.CLASE_DEMERITO;
//    }

    public List<String> getValorTablaAuxiliar(String tabla) 
    {
    	// TODO: este codigo esta duplicado
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", tabla));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }

    public boolean getHayCargosAsignados() {
        return _ca != null;
    }
}
