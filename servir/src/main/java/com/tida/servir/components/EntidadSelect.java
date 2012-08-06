/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class EntidadSelect {

    @InjectComponent
    private Envelope envelope;
    @Property
    @Parameter(required = false)
    private Boolean vistaCorta;
    @Parameter(required = false)
    private Zone _zone;
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
    @Property
    private String _zoneName;
    @InjectComponent
    @Property
    private Zone UnidadEjecutoraZone;
    @Inject
    private Session session;
    @Inject
    private PropertyAccess _access;
    @Property
    @SessionState
    private Entidad entidad;
    @Property
    @Persist
    private DatoAuxiliar snivelGobierno;
    @Property
    @Persist
    private DatoAuxiliar sorganizacionestado;
    @Property
    @Persist
    private DatoAuxiliar ssectorGobierno;
    @Property
    @Persist
    private DatoAuxiliar stipoOrganismo;
    @Property
    @Persist
    private Entidad sentidad;
    @Property
    @Persist
    private Entidad ssubentidad;
    @Property
    @Persist
    private DatoAuxiliar stipoSubEntidad;
    @Property
    @Persist
    private boolean sessubentidad;

    @InjectComponent
    @Property
    private Zone OrganizacionZone;
    @InjectComponent
    @Property
    private Zone SectorZone;
    @InjectComponent
    @Property
    private Zone TipoOrganismoZone;
    @InjectComponent
    @Property
    private Zone EntidadZone;
    @InjectComponent
    @Property
    private Zone SubEntidadZone;
    @InjectComponent
    @Property
    private Zone EsSubEntidadZone;
    @InjectComponent
    @Property
    private Zone TipoSubEntidadZone;
    @InjectComponent
    @Property
    private Zone botonesZone;
    @Property
    @Persist
    private boolean borganizacionestado;
    @Property
    @Persist
    private boolean bsectorgobierno;
    @Property
    @Persist
    private boolean btipoorganismo;
    @Property
    @Persist
    private boolean bessubentidad;
    private int elemento = 0;
    @Inject
    private Request request;
    @Component(id = "formulariobotones")
    private Form formulariobotones;
    

    //Para inicializar valores
    @Log
    @SetupRender
    void initializeValue() {
        borganizacionestado = false;
        bsectorgobierno = false;
        btipoorganismo = false;
        bessubentidad = false;
        elemento = 0;
        snivelGobierno = null;
        sorganizacionestado = null;
        ssectorGobierno = null;
        stipoOrganismo = null;
        sentidad = null;
        ssubentidad=null;
        if(entidad.getEntidad()==null){
            sentidad=entidad;
            bessubentidad=false;
            sessubentidad=false;
            System.out.println("entiii"+sentidad);
        }
        else{
            ssubentidad=entidad;
            sentidad=null;
            bessubentidad=true;
            sessubentidad=true;
            System.out.println("sentiii"+ssubentidad+"-"+sentidad);
        }

        //Si se requiere que se seteen en los combos los valores de la entidad seleccionada por defecto
        /*
         * if (entidad != null) { snivelGobierno = entidad.getNivelGobierno();
         * sorganizacionestado= entidad.getOrganizacionEstado(); ssectorGobierno
         * = entidad.getSectorGobierno(); stipoOrganismo =
         * entidad.getTipoOrganismo(); sentidad = entidad; } else { if
         * (!vistaCorta) { //Cargamos alguna entidad Criteria c =
         * session.createCriteria(Entidad.class);
         * c.add(Restrictions.ne("estado", true)); sentidad = entidad =
         * (Entidad) c.list().get(0); snivelGobierno =
         * entidad.getNivelGobierno(); sorganizacionestado=
         * entidad.getOrganizacionEstado(); ssectorGobierno =
         * entidad.getSectorGobierno(); stipoOrganismo =
         * entidad.getTipoOrganismo(); } }
         *
         */

    }

    //para obtener datos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
//        DatoAuxiliar todos = new DatoAuxiliar();
//        todos.setId(9999999999L);
//        todos.setValor("TODOS");
//        todos.setCodigo(9999999999L);
//        list.add(todos);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos de la Organizacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", snivelGobierno.getCodigo(), session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del Sector Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del Tipo Organismo
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<Entidad> getEntidades() {
        return new GenericSelectModel<Entidad>(searchEntidades(), Entidad.class, "denominacion", "id", _access);
    }

    @Log
    private List<Entidad> searchEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.eq("estado", true));
        if (snivelGobierno != null) {
            c.add(Restrictions.eq("nivelGobierno", snivelGobierno));
        }
        if (sorganizacionestado != null) {
            c.add(Restrictions.eq("organizacionEstado", sorganizacionestado));
        }
        if (ssectorGobierno != null) {
            c.add(Restrictions.eq("sectorGobierno", ssectorGobierno));
        }
        if (stipoOrganismo != null) {
            c.add(Restrictions.eq("tipoOrganismo", stipoOrganismo));
        }
        c.add(Restrictions.eq("esSubEntidad",false));
        return c.list();
    }

    @Log
    public GenericSelectModel<Entidad> getSubEntidades() {
        return new GenericSelectModel<Entidad>(searchSubEntidades(), Entidad.class, "denominacion", "id", _access);
    }

    @Log
    private List<Entidad> searchSubEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.eq("estado", true));
        c.add(Restrictions.eq("esSubEntidad", true));
        if (stipoSubEntidad != null) {
            c.add(Restrictions.eq("tipoSubEntidad", stipoSubEntidad));
        }
//        if (sentidad != null) {
//            c.add(Restrictions.eq("entidad", sentidad));
//        }
        //c.addOrder(Order.asc("denominacion"));
        //c.add(Restrictions.eq("essubentidad", 1));
        return c.list();
    }

    //para obtener datos del Tipo Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    Object onValueChangedFromSnivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            borganizacionestado = false;
        } else {
            borganizacionestado = true;
        }
        sorganizacionestado = null;
        return request.isXHR() ? new MultiZoneUpdate("OrganizacionZone", OrganizacionZone.getBody()).add("EntidadZone", EntidadZone.getBody()) : null;
    }

    Object onValueChangedFromSorganizacionestado(DatoAuxiliar dato) {
        if (dato == null) {
            bsectorgobierno = false;
        } else {
            if (dato.getValor().equalsIgnoreCase("PODER EJECUTIVO")) {
                bsectorgobierno = true;
            } else {
                bsectorgobierno = false;
                btipoorganismo = false;
            }
        }
        ssectorGobierno = null;
        return request.isXHR() ? new MultiZoneUpdate("SectorZone", SectorZone.getBody()).add("EntidadZone", EntidadZone.getBody()).add("TipoOrganismoZone", TipoOrganismoZone.getBody()) : null;
    }
    
    Object onValueChangedFromSsectorgobierno(DatoAuxiliar dato){
        if (dato == null) {
            btipoorganismo = false;
        }else{
            btipoorganismo = true;
        }
        stipoOrganismo = null;
        return new MultiZoneUpdate("TipoOrganismoZone", TipoOrganismoZone.getBody()).add("EntidadZone", EntidadZone.getBody());
    }
    
    Object onValueChangedStipoOrganismo(DatoAuxiliar dato){
        return EntidadZone.getBody();
    }

    Object onSuccessFromformEntidad() {
        return new MultiZoneUpdate("EsSubEntidadZone", EsSubEntidadZone.getBody()).add("TipoSubEntidadZone", TipoSubEntidadZone.getBody()).add("SubEntidadZone", SubEntidadZone.getBody());
    }

    Object onSuccessFromformEsSubEntidad() {
        if (bessubentidad) {
            bessubentidad = false;
        } else {
            bessubentidad = true;
        }
        return new MultiZoneUpdate("TipoSubEntidadZone", TipoSubEntidadZone.getBody()).add("SubEntidadZone", SubEntidadZone.getBody());
    }

    Object onSuccessFromformTipoSubEntidad() {
        return SubEntidadZone.getBody();
    }

    void onSelectedFromCancel() {
        elemento = 2;
    }

    void onSelectedFromReset() {
        elemento = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        if (elemento == 1) {
            return "CambioEntidad";
        } else if (elemento == 2) {
            return "Alerta";
        } else {
            if (bessubentidad) {
                if(ssubentidad==null){
                    formulariobotones.recordError("Tiene que seleccionar una Sub-Entidad");
                    return UnidadEjecutoraZone.getBody();
                }
                else{
                    entidad = ssubentidad;
                }
            } else {
                if(sentidad==null){
                    formulariobotones.recordError("Tiene que seleccionar una Entidad");
                    return UnidadEjecutoraZone.getBody();
                }
                else{
                    entidad = sentidad;
                }                
            }
//            if(entidad!=null && !entidad.equals("")){
//                System.out.println("entroooo"+entidad+"--");
//            }
//            else{
//                System.out.println("aquiiiiiiiiii"+entidad);
//                formulariobotones.recordError("Tiene que seleccionar una entidad");
//                return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add(_zoneName, _zone.getBody());
//            }
            envelope.setContents("Entidad /U. Ejecutora Seleccionada");
            if (_zone != null) {
                return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add(_zoneName, _zone.getBody());
            } else {
                return UnidadEjecutoraZone.getBody();
            }
        }
    }
}