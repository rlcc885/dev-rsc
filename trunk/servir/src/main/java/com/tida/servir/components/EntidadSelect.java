/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class EntidadSelect {

   // @InjectComponent
    //private Envelope envelope;
    @Inject
    private Session session;
    @Inject
    private PropertyAccess _access;
    
    
    @Property
    @Parameter(required = false)
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
    private Zone NivelZone;
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
    
    private int elemento=0;
    /*
    
    @Property
    @Parameter(required = false)
    private Boolean vistaCorta;
    @Parameter(required = false)
    //@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Zone _zone;
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
    @Property
    private String _zoneName;

    @Property
    @Persist
    private String pliego;
    @Property
    @Persist
    private String sector;
    @InjectComponent
    @Property
    private Zone PliegoZone;
    @InjectComponent
    @Property
    private Zone SectorZone;
    @InjectComponent
    @Property
    private Zone UnidadEjecutoraZone;
    @Property
    @Persist
    private Entidad_BK entidad_nueva;

    @Property
    @Persist
    private List<String> _beanNivelGobierno;

    
    
    //Para inicializar valores
    @Log
    @SetupRender
    void initializeValue() {
        
        /*
        _beanNivelGobierno = Helpers.getValorTablaAuxiliar("NIVELGOBIERNO", session);
        if (entidad != null) {
            nivelgobierno = entidad.getNivelgobierno();
            sector = entidad.getSector_gobierno();
            pliego = entidad.getPliego();
            entidad_nueva = entidad;
        } else {
            if (!vistaCorta) {
                //Cargamos alguna entidad
                Criteria c = session.createCriteria(Entidad_BK.class);
                c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
                entidad_nueva = entidad = (Entidad_BK) c.list().get(0);
                nivelgobierno = entidad.getNivelgobierno();
                sector = entidad.getSector_gobierno();
                pliego = entidad.getPliego();
            }
        }
        /*
         * if (nivel_gobierno == null) { if(_beanNivelGobierno.size() > 0) { //
         * nivel_gobierno = _beanNivelGobierno.get(0); }
         *
         * }
         * if (sector == null) { List<String> sectores = getBeanDatoAuxSector();
         * if(sectores.size() > 0) { // sector = sectores.get(0); } } if (pliego
         * == null) { List<String> pliegos = getBeanDatoAuxPliego();
         * if(pliegos.size() > 0) { // pliego = pliegos.get(0); } }
         */
        /*
         * if (entidad == null) { List<EntidadUEjecutora> entidades =
         * searchEntidades(); if (entidades.size() > 0) //entidad =
         * entidad_nueva = entidades.get(0); else { // tiene que haber al menos
         * alguna entidad cargada // Criteria c =
         * session.createCriteria(EntidadUEjecutora.class); //
         * c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA)); //
         * entidad = entidad_nueva = (EntidadUEjecutora) c.list().get(0);
         *
         * }
         * }
         *
         */

   // }
    
    //para obtener datos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    
   //para obtener datos de la Organizacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
             List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", null, 0, session);
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
        if(snivelGobierno!=null){
           c.add(Restrictions.eq("nivelgobierno_id", snivelGobierno.getId()));
        }
        /*c.add(Restrictions.eq("nivelgobierno", nivelgobierno));
        if (sector != null) {
            if (sector.trim().length() > 0) {
                c.add(Restrictions.eq("sector_gobierno", sector));
            }
        }
        if (pliego != null) {
            if (pliego.trim().length() > 0) {
                c.add(Restrictions.eq("pliego", pliego));
            }
        }
        */    
        return c.list();
    }
    
     @Log
    public GenericSelectModel<Entidad> getSubEntidades() {

        return new GenericSelectModel<Entidad>(searchSubEntidades(), Entidad.class, "denominacion", "id", _access);

    }
    
    @Log
    private List<Entidad> searchSubEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.eq("estado", false));
        //c.add(Restrictions.ne("essubentidad", true));
        /*c.add(Restrictions.eq("nivelgobierno", nivelgobierno));
        if (sector != null) {
            if (sector.trim().length() > 0) {
                c.add(Restrictions.eq("sector_gobierno", sector));
            }
        }
        if (pliego != null) {
            if (pliego.trim().length() > 0) {
                c.add(Restrictions.eq("pliego", pliego));
            }
        }
        */    
        return c.list();
    } 
    
    //para obtener datos del Tipo Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    Object onSuccessFromformNivelGobierno() {
        borganizacionestado=true;
        return new MultiZoneUpdate("OrganizacionZone", OrganizacionZone.getBody())
                .add("EntidadZone", EntidadZone.getBody()); 
    }
    
    Object onSuccessFromformOrganizacionEstado() {
        if(sorganizacionestado.getValor().equalsIgnoreCase("PODER EJECUTIVO")){
            bsectorgobierno=true;
            return SectorZone.getBody();
        }else{
            bsectorgobierno=false;
            return SectorZone.getBody();
        }
            

    }
    
    Object onSuccessFromformSectorGobierno() {
        btipoorganismo=true;
        return TipoOrganismoZone.getBody();

    }
    
    Object onSuccessFromformEsSubEntidad() {
        bessubentidad=true;
        return new MultiZoneUpdate("TipoSubEntidadZone", TipoSubEntidadZone.getBody())
                .add("SubEntidadZone", SubEntidadZone.getBody());
    }
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    /*
    public String getControlName() {
        return "EntidadSelect";
    }

    public boolean isRequired() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getElementName() {
        return null;
    }

    @Log
    public List<String> getBeanDatoAuxSector() {
        List<String> retList = null;
        if (!getEsLocal()) {
            retList = Helpers.getValorTablaAuxiliar("SECTORGOBIERNO", session);
        } else {
            retList = Helpers.getValorTablaAuxiliar("UBDEPARTAMENTO", session);
        }

        if (retList == null) {
            return new ArrayList<String>();
        } else {
            return retList;
        }

    }

    @Log
    public List<String> getBeanDatoAuxPliego() {
        List<String> retList = null;

        if (!getEsLocal()) {
            retList = Helpers.getValorTablaAuxiliar("PLIEGO", session);
        } else {

            // Obtenemos el dato auxiliar del depto.
            DatoAuxiliar depto = Helpers.getDatoAuxiliar("UBDEPARTAMENTO", sector, session);
            if (depto != null) {
                retList = Helpers.getValorTablaAuxiliar("UBPROVINCIA", session,
                        "UBDEPARTAMENTO", depto.getCodigo());
            }
        }

        if (retList == null) {
            return new ArrayList<String>();
        } else {
            return retList;
        }
    }

    @Log
    private List<Entidad_BK> searchEntidades() {
        Criteria c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
        c.add(Restrictions.eq("nivelgobierno", nivelgobierno));
        if (sector != null) {
            if (sector.trim().length() > 0) {
                c.add(Restrictions.eq("sector_gobierno", sector));
            }
        }
        if (pliego != null) {
            if (pliego.trim().length() > 0) {
                c.add(Restrictions.eq("pliego", pliego));
            }
        }

        return c.list();
    }

    @Log
    public GenericSelectModel<Entidad_BK> getEntidadesUEjecutoras() {

        return new GenericSelectModel<Entidad_BK>(searchEntidades(), Entidad_BK.class, "denominacion", "id", _access);

    }
    
    

    

    @Log
    Object onSuccessFromCambiarEntidadFrm() {
        entidad = entidad_nueva;
        envelope.setContents("Entidad /U. Ejecutora seleccionada");
        if (_zone != null) {
            return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add(_zoneName, _zone.getBody());
        } else {
            return UnidadEjecutoraZone.getBody();
        }
    }

    Object onSuccessFromformNivel() {
        sector = null;
        pliego = null;
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add("SectorZone", SectorZone.getBody()).add("PliegoZone", PliegoZone.getBody());

    }

    Object onSuccessFromformSector() {
        pliego = null;
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add("PliegoZone", PliegoZone.getBody());

    }

    Object onSuccessFromformPliego() {
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody());
    }

    @Log
    public boolean getEsLocal() {

        return nivelgobierno.equals("NIVEL LOCAL");
    }
    * 
    */
}
