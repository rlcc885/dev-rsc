/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.UbigeoSectorPliego;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.hibernate.Session;
/**
 *
 * @author Morgan
 */
public class UbigeosCorto {
    
    public String getControlName() {
        return "UbigeosNivelSectorPliego";
    }

    public boolean isRequired() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public String getElementName()
    {
        return null;
    }


    @Parameter(required = true)
    @Property
    private UbigeoSectorPliego ubigeoSectorPliego;

    
    @Parameter
    //@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Zone _zone;


    @Parameter(defaultPrefix = BindingConstants.LITERAL)
   @Property
    private String _zoneName;
    
    
       @Parameter (defaultPrefix = "literal")
    private String label;
    public String getLabel()
    {
        return label;

    }

    @Inject
    private ComponentResources resources;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    String defaultLabel()
    {
        return defaultProvider.defaultLabel(resources);
    }


    public String getClientId()
    {
        return resources.getId();
    }

    @Parameter
    private boolean disabled;
    public boolean isDisabled()
    {
        return disabled;
    }

    @Environmental
    private ValidationTracker tracker;


    @Inject
    private Session session;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxPliego;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxSector;

    @Inject
    private PropertyAccess _access;


    @Log
    @SetupRender
    void initializeValue()
    {

        if (ubigeoSectorPliego == null ){
            ubigeoSectorPliego = new UbigeoSectorPliego();
        }

        if (_beanDatoAuxSector == null) {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SectorGobierno", null, 0, session);

            _beanDatoAuxSector = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);

        }
        if (ubigeoSectorPliego.getSector() == null) {
            ubigeoSectorPliego.setPliego(null);

        }
        List<DatoAuxiliar> lub = new ArrayList<DatoAuxiliar>();

        DatoAuxiliar d;
       if (ubigeoSectorPliego.getSector() != null) {
            lub = Helpers.getDatoAuxiliar("Pliego", null, ubigeoSectorPliego.getSector().getCodigo(), session);
        } else {
            ubigeoSectorPliego.setPliego(null);
        }

        _beanDatoAuxPliego = new GenericSelectModel<DatoAuxiliar>(lub,DatoAuxiliar.class,"valor","codigo",_access);

    }

    @Log
    public Object onValueChanged(Object dato) {
        // es una tabla auxiliar (ubigeoSectorPliego)
        DatoAuxiliar ub = (DatoAuxiliar) dato;
        if(ub != null) {
            if (ub.getNombreTabla().equals("SectorGobierno")) {
                // Cargo la provincia

                ubigeoSectorPliego.setPliego(null); // pongo la primera para que no sea nulo

            }
        }
        return _zone.getBody();
        //return _zone;
    }

}
