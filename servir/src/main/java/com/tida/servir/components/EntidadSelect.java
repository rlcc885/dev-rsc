/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
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

    @Property
    @Parameter(required = false)
    private EntidadUEjecutora entidad;

        

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
    private String nivel_gobierno;

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
    private EntidadUEjecutora entidad_nueva;
    
    @InjectComponent
    private Envelope envelope;

    public String getControlName() {
        return "EntidadSelect";
    }

    public boolean isRequired() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public String getElementName()
    {
        return null;
    }


    @Property
    @Persist
    private List<String> _beanNivelGobierno;


    @Inject
    private Session session;


    @Inject
    private PropertyAccess _access;

    @Log
    public List<String> getBeanDatoAuxSector() {
        List<String> retList = null;
        if (!getEsLocal())
            retList =  Helpers.getValorTablaAuxiliar("SectorGobierno", session);
        else
            retList =  Helpers.getValorTablaAuxiliar("UBDepartamento", session);

        if (retList == null)
            return new ArrayList<String>();
        else
            return retList;

    }
    
    @Log
    public List<String> getBeanDatoAuxPliego() {
        List<String> retList = null;

        if (!getEsLocal())
            retList = Helpers.getValorTablaAuxiliar("Pliego", session);
        else {

            // Obtenemos el dato auxiliar del depto.
            DatoAuxiliar depto =  Helpers.getDatoAuxiliar("UBDepartamento", sector,  session);
            if (depto != null) {
                retList = Helpers.getValorTablaAuxiliar("UBProvincia", session,
                    "UBDepartamento", depto.getCodigo());
            }
        }

        if (retList == null)
            return new ArrayList<String>();
        else
            return retList;
    }

    @Log
    private List<EntidadUEjecutora> searchEntidades() {
        Criteria c = session.createCriteria(EntidadUEjecutora.class);
    	c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
        c.add(Restrictions.eq("nivel_gobierno", nivel_gobierno));
        if (sector != null) {
            if (sector.trim().length() > 0){
                c.add(Restrictions.eq("sector_gobierno", sector));
            }
        }
        if (pliego != null) {
            if (pliego.trim().length() > 0){
                c.add(Restrictions.eq("pliego", pliego));
            }
        }
        
        return c.list();
    }

    @Log
    public GenericSelectModel<EntidadUEjecutora> getEntidadesUEjecutoras() {
    	
        return new GenericSelectModel<EntidadUEjecutora>(searchEntidades(),EntidadUEjecutora.class,"denominacion","id",_access);

    }

    @Log
    @SetupRender
    void initializeValue()
    {
        _beanNivelGobierno = Helpers.getValorTablaAuxiliar("NivelGobierno", session);
        if (entidad != null){
            nivel_gobierno = entidad.getNivel_gobierno();
            sector = entidad.getSector_gobierno();
            pliego = entidad.getPliego();
            entidad_nueva = entidad;
        } else {
            if (!vistaCorta) {
                //Cargamos alguna entidad
                Criteria c = session.createCriteria(EntidadUEjecutora.class);
                c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
                entidad_nueva = entidad = (EntidadUEjecutora)c.list().get(0);
                nivel_gobierno = entidad.getNivel_gobierno();
                sector = entidad.getSector_gobierno();
                pliego = entidad.getPliego();
            }
        }
/*
        if (nivel_gobierno == null) {
            if(_beanNivelGobierno.size() > 0) {
              //  nivel_gobierno = _beanNivelGobierno.get(0);
            }

        }
        if (sector == null) {
            List<String> sectores = getBeanDatoAuxSector();
            if(sectores.size() > 0) {
              //  sector = sectores.get(0);
            }
        }
        if (pliego == null) {
            List<String> pliegos = getBeanDatoAuxPliego();
            if(pliegos.size() > 0) {
             //   pliego = pliegos.get(0);
            }
        }
 */
 /*
        if (entidad == null) {
            List<EntidadUEjecutora> entidades = searchEntidades();
            if (entidades.size() > 0)
             //entidad = entidad_nueva = entidades.get(0);
            else {
                // tiene que haber al menos alguna entidad cargada
             //   Criteria c = session.createCriteria(EntidadUEjecutora.class);
              //  c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
              //  entidad = entidad_nueva = (EntidadUEjecutora) c.list().get(0);

            }
        }
         * 
         */

    }

    @Log
    Object onSuccessFromCambiarEntidadFrm() {
        entidad = entidad_nueva;
        envelope.setContents("Entidad /U. Ejecutora seleccionada");
        if (_zone != null) {
            return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody())
                   .add(_zoneName, _zone.getBody());
        } else
            return UnidadEjecutoraZone.getBody();
    }

    Object onSuccessFromformNivel() {
        sector = null;
        pliego = null;
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody())
                .add("SectorZone", SectorZone.getBody())
                .add("PliegoZone", PliegoZone.getBody());

    }


    Object onSuccessFromformSector() {
        pliego = null;
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody())
                .add("PliegoZone", PliegoZone.getBody());

    }

    Object onSuccessFromformPliego() {
        return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody());
    }

    @Log
    public boolean getEsLocal() {

        return nivel_gobierno.equals("NIVEL LOCAL");
    }
}
