package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Clase que maneja el TAB del editor de Remuneraciones.
 *
 */
public class ABMConceptosRemunerativos extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private LkBusquedaConRemunerativo cr;
    @Property
    @Persist
    private ConceptoRemunerativo conceptoRemunerativo;
    @SessionState
    private Usuario loggedUser;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private PropertyAccess _access;
    @Inject  
    private ComponentResources _resources;
    @Property
    @Persist
    private UsuarioAcceso usua;
    @InjectComponent
    private Zone listaConceptosRemunerativosZone;
    @InjectComponent
    private Zone formZone;
    @InjectComponent
    private Zone mensajes;
    @Property
    @Persist
    private boolean editando;
    @Component(id = "formmensajes")
    private Form formmensajes;
    @InjectComponent
    private Envelope envelope;
    private int elemento=0;
    
    //validaciones
    @Persist
    @Property
    private Boolean vNoedita;
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
    
    @Persist
    @Property
    private String sustento_legal;
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, loggedUser, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);
        return null;
    }
    
    // inicio de pagina
    @SetupRender
    public void inicio() {        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", loggedUser.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        vbotones = false;
        vNoedita = false;
        veditar = false;
        vbotones = false;
        veliminar = false;
        vformulario = false;
        vdetalle=false;
        logueo();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
            usua = (UsuarioAcceso) result.get(0);
            if (usua.getAccesoupdate() == 1) {
                veditar = true;
                vbotones = true;
            }
            if (usua.getAccesodelete() == 1) {
                veliminar = true;
            }
            if (usua.getAccesoreport() == 1) {
                vformulario = true;
                vbotones = true;
            }
        }
        conceptoRemunerativo = new ConceptoRemunerativo();
    }
    
    
    public List<LkBusquedaConRemunerativo> getConceptosRemunerativos() {
        Criteria c;
        c = session.createCriteria(LkBusquedaConRemunerativo.class);
        c.add(Restrictions.eq("entidad", _oi.getId()));
        c.addOrder(Order.asc("descripcion"));

        nroregistros = Integer.toString(c.list().size());
                
        return c.list();
    }
    
    @Persist
    @Property
    private String nroregistros;
    

    // accion de boton seleccionado    
    void onSelectedFromSave() {
        elemento = 1;
    }

    void onSelectedFromReset() {

        formmensajes.clearErrors();
        elemento = 2;
        conceptoRemunerativo = new ConceptoRemunerativo();
    }
    
    void onSelectedFromCancel() {
        elemento = 3; 
        if(vdetalle){
            vformulario=false; 
            if (usua.getAccesoreport() == 1) {
                vformulario=true;
                vdetalle=false;
                vbotones=true;
                vNoedita=false;
                conceptoRemunerativo=new ConceptoRemunerativo();
            } 
        }
        else{
            conceptoRemunerativo=new ConceptoRemunerativo();
            vNoedita=false;
        }
    }

    // formulario principal
    @Log
    @CommitAfter
    Object onSuccessFromFormularioConceptoRemunerativo() {
        formmensajes.clearErrors();
        if(elemento==2 ){
        }
        else if(elemento==3){
        }
        else{
            formmensajes.clearErrors();
            if(!validando()){
                return zonas();
            }
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+conceptoRemunerativo.getCodigo());
            if(conceptoRemunerativo.getCodigo() == null){
                formmensajes.recordError("Debe Ingresar un Codigo");
                return zonas();
            }
            if(conceptoRemunerativo.getDescripcion()== null){
                formmensajes.recordError("Debe Ingresar una Descripcion");
                return zonas();
            }
            if(conceptoRemunerativo.getSustento_legal() == null){
                formmensajes.recordError("Debe Ingresar un Sustento Legal");
                return zonas();
            }
            conceptoRemunerativo.setEntidad_id(_oi.getId());
            session.saveOrUpdate(conceptoRemunerativo);
            session.flush();
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(conceptoRemunerativo.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);
            if(editando){
                envelope.setContents(helpers.Constantes.CONREMUNERATIVO_EDIT_EXITO);
                if (usua.getAccesoreport() == 0) {
                        vformulario = false;
                }
            }
            else{
                envelope.setContents(helpers.Constantes.CONREMUNERATIVO_EXITO);
            }
            editando = false;
            vNoedita=false;
            conceptoRemunerativo = new ConceptoRemunerativo();
        }
        
        return zonas();
    }

    boolean validando(){
      boolean fin=true;
      Criteria c;
      
        if(conceptoRemunerativo.getCodigo() != null){
            c = session.createCriteria(LkBusquedaConRemunerativo.class);
            c.add(Restrictions.eq("entidad", _oi.getId()));
            c.add(Restrictions.disjunction().add(Restrictions.like("codigo", conceptoRemunerativo.getCodigo()).ignoreCase()));
            if (editando) {
                c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));
            }
            if (c.list().size() > 0) {
                formmensajes.recordError("Código de Concepto Remunerativo ya Existente");
                return false;
            }
        }
        
        c = session.createCriteria(LkBusquedaConRemunerativo.class);
        c.add(Restrictions.eq("entidad", _oi.getId()));
        c.add(Restrictions.disjunction().add(Restrictions.like("descripcion", conceptoRemunerativo.getDescripcion()).ignoreCase()));
        if (editando) {
            c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));
        }
        if (c.list().size() > 0) {
            formmensajes.recordError("Descripción de Concepto Remunerativo ya Existente");
            return false;
        }
      return fin;
    }
    

    // editar concepto remunerativo
    @Log
    Object onActionFromEditar(ConceptoRemunerativo concepto) {
        conceptoRemunerativo = concepto;
        editando = true; 
        vformulario = true;
        vdetalle = false;
        vbotones = true;
        vNoedita = true;
        return zonas();
    }
    
    // ver detalle de concepto remunerativo
   @Log
    Object onActionFromDetalle(ConceptoRemunerativo concepto) {
        conceptoRemunerativo = concepto; 
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        vNoedita = true;
        return zonas();
    }


   // cargar combos
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOREMUNERACION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getConceptosStd() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOREMUNERACIONSTD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getPeriodicidades() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("PERIODICIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    public boolean getEsBorrable() {
        /*
         * Buscamos; remuneraciones que usen el concepto
         *
         */

        Criteria c;
        c = session.createCriteria(RemuneracionPersonal.class);
        c.add(Restrictions.eq("conceptoremunerativo.id", cr.getId()));

        if (c.list().size() > 0) {
            return false;
        }
        return true;
    }

    // accion de borrar concepto remunerativo
    @Log
    @CommitAfter
    Object onBorrarDato(ConceptoRemunerativo dato) {
        session.delete(dato);
        envelope.setContents("Concepto Remunerativo Eliminado");
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);
        return zonas();// La/a zona a actualizar
    }
    
    // actualizar zonas
    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("listaConceptosRemunerativosZone", listaConceptosRemunerativosZone.getBody()).
                add("mensajes", mensajes.getBody()).
                add("formZone", formZone.getBody());
        return mu;
    }
}
