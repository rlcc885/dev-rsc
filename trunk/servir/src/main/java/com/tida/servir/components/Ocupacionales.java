/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Ocupacional;
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
 * @author ale
 */
public class Ocupacionales {
    public String getControlName() {
        return "Ocupacionales";
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
    private Ocupacional ocupacional;

    
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
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxGrupoOcupacional;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxCategoriaOcupacional;

    @Inject
    private PropertyAccess _access;


    @Log
    @SetupRender
    void initializeValue()
    {

        if (ocupacional == null ){
            ocupacional = new Ocupacional();
        }

        if (_beanDatoAuxGrupoOcupacional == null) {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("GrupoOcupacional", null, 0, session);

            _beanDatoAuxGrupoOcupacional = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);

        }
        if (ocupacional.getGrupoOcupacional() == null) {
            ocupacional.setCategoriaOcupacional(null);

        }
        List<DatoAuxiliar> lub = new ArrayList<DatoAuxiliar>();

        DatoAuxiliar d;
       if (ocupacional.getGrupoOcupacional() != null) {
            lub = Helpers.getDatoAuxiliar("CategoriaOcupacional", "GrupoOcupacional",
                    ocupacional.getGrupoOcupacional().getCodigo(), session);
        } else {
            ocupacional.setCategoriaOcupacional(null);
        }

        _beanDatoAuxCategoriaOcupacional = new GenericSelectModel<DatoAuxiliar>(lub,DatoAuxiliar.class,"valor","codigo",_access);

    }

    @Log
    public Object onValueChanged(Object dato) {
        // es una tabla auxiliar 
        DatoAuxiliar ub = (DatoAuxiliar) dato;
        if(ub != null) {
            if (ub.getNombreTabla().equals("GrupoOcupacional")) {
                // Cargo la Categoría
                ocupacional.setCategoriaOcupacional(null); // pongo la primera para que no sea nulo


            }
        }
        return _zone.getBody();
        //return _zone;
    }

}