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
    @Property
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

    @SetupRender
    public void inicio() {        
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento", loggedUser.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        vbotones = false;
        vNoedita = false;
        veditar = false;
        vbotones = false;
        veliminar = false;
        vformulario = false;
        vdetalle=false;
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
        return c.list();
    }

    void onSelectedFromSave() {
        elemento = 1;
    }

    //@Log
    void onSelectedFromReset() {
//        limpiar();
//        formlistaestudios.clearErrors();
//        editando = false;
//        estudio = new Estudios();
//        if (usua.getAccesoreport() == 0) {
//            vformulario = false;
//        }
        elemento = 2;
        conceptoRemunerativo=new ConceptoRemunerativo();
    }
    
    void onSelectedFromCancel() {
        elemento = 3; 
        System.out.println("detaleeeeee"+vdetalle);
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

    @Log
    @CommitAfter
    Object onSuccessFromFormularioConceptoRemunerativo() {
        if(elemento==2 ){
        }
        else if(elemento==3){
        }
        else{
            formmensajes.clearErrors();
            if(!validando()){
                return zonas();
            }
            conceptoRemunerativo.setEntidad(_oi);
            session.saveOrUpdate(conceptoRemunerativo);
            envelope.setContents("Conceptos Remunerativos Modificados Exitosamente");
            //new Logger().loguearOperacion(session, loggedUser, String.valueOf(conceptoRemunerativo.getId()), (editando ? Logger.CODIGO_OPERACION_ALTA : Logger.CODIGO_OPERACION_MODIFICACION), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);
            if(editando){
                if (usua.getAccesoreport() == 0) {
                        vformulario = false;
                }
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
        c = session.createCriteria(LkBusquedaConRemunerativo.class);
        c.add(Restrictions.eq("entidad", _oi.getId()));
        c.add(Restrictions.like("codigo", conceptoRemunerativo.getCodigo()));
        if (editando) {
            c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));
        }
        if (c.list().size() > 0) {
            formmensajes.recordError("Código de Concepto Remunerativo ya Existente");
            return false;
        }
        c = session.createCriteria(LkBusquedaConRemunerativo.class);
        c.add(Restrictions.eq("entidad", _oi.getId()));
        c.add(Restrictions.like("descripcion", conceptoRemunerativo.getDescripcion()));

        if (editando) {
            c.add(Restrictions.ne("id", conceptoRemunerativo.getId()));
        }
        if (c.list().size() > 0) {
            formmensajes.recordError("Descripción de Concepto Remunerativo ya Existente");
            return false;
        }
      return fin;
    }
    
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
    
   @Log
    Object onActionFromDetalle(ConceptoRemunerativo concepto) {
        conceptoRemunerativo = concepto; 
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        vNoedita = true;
        return zonas();
    }
    /*
     * Borrar la fila
     */
//    void onActivate(ConceptoRemunerativo c) {
//
//        editando = (c != null);
//        if (c == null) {
//            c = new ConceptoRemunerativo();
//        }
//
//        // Le genero la sesión hibernate así puede identificarlo como igual
//        //this._oi = (EntidadUEjecutora) session.load(EntidadUEjecutora.class, _oi.getId());
//
//        this.conceptoRemunerativo = c; //(Usuario) session.load(Usuario.class, user.getId());
//    }

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
        c.add(Restrictions.eq("conceptoRemunerativo", cr));

        if (c.list().size() > 0) {
            return false;
        }
        return true;
    }

    @Log
    @CommitAfter
    Object onBorrarDato(ConceptoRemunerativo dato) {
//        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()),
//                Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CONCEPTO_REMUNERATIVO);

        session.delete(dato);
        envelope.setContents("Concepto Remunerativo Eliminado");
        return zonas();// La/a zona a actualizar
    }
    
    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("listaConceptosRemunerativosZone", listaConceptosRemunerativosZone.getBody()).
                add("mensajes", mensajes.getBody()).
                add("formZone", formZone.getBody());
        return mu;
    }
}
