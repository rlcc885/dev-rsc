/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.FormacionProfesional;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
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
public class FormacionProfesionalEditor {
    public String getControlName() {
        return "FormacionProfesional";
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
    private FormacionProfesional formacionProfesional;

    
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
    private GenericSelectModel<DatoAuxiliar> _beanFormacion;

    /*@Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanFormacion2;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanFormacion3;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanFormacion4;*/

    @Inject
    private PropertyAccess _access;


    @Log
    @SetupRender
    void initializeValue()
    {
        if (formacionProfesional == null ){
            formacionProfesional = new FormacionProfesional();
        }

        if (_beanFormacion == null) {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("FormacionProfesional", null, 0, session);

            _beanFormacion = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);

        }
        /*if (formacionProfesional.getFormacion() == null) {
            formacionProfesional.setFormacion2(null);
            formacionProfesional.setFormacion3(null);
            formacionProfesional.setFormacion4(null);

        }
        List<DatoAuxiliar> lub = new ArrayList<DatoAuxiliar>();

        DatoAuxiliar d;
       if (formacionProfesional.getFormacion() != null) {
            lub = Helpers.getDatoAuxiliar("FormacionProfesional2", "FormacionProfesional",
                    formacionProfesional.getFormacion().getCodigo(), session);
        } 
        _beanFormacion2 = new GenericSelectModel<DatoAuxiliar>(lub,DatoAuxiliar.class,"valor","codigo",_access);

        lub = new ArrayList<DatoAuxiliar>();
        if (formacionProfesional.getFormacion2() != null) {
            lub = Helpers.getDatoAuxiliar("FormacionProfesional3", "FormacionProfesional2",
                    formacionProfesional.getFormacion2().getCodigo(), session);
        }  else {
            formacionProfesional.setFormacion3(null);
            formacionProfesional.setFormacion4(null);
        }
        _beanFormacion3 = new GenericSelectModel<DatoAuxiliar>(lub,DatoAuxiliar.class,"valor","codigo",_access);

        lub = new ArrayList<DatoAuxiliar>();
        if (formacionProfesional.getFormacion3() != null) {
            lub = Helpers.getDatoAuxiliar("FormacionProfesional4", "FormacionProfesional3",
                    formacionProfesional.getFormacion3().getCodigo(), session);
        }  else {
            formacionProfesional.setFormacion4(null);
        }


        _beanFormacion4 = new GenericSelectModel<DatoAuxiliar>(lub,DatoAuxiliar.class,"valor","codigo",_access);*/
        
    }

    @Log
    public Object onValueChanged(Object dato) {
        // es una tabla auxiliar (ubigeo)

        /*DatoAuxiliar ub = (DatoAuxiliar) dato;
        if(ub != null) {
            if (ub.getNombreTabla().equals("FormacionProfesional")) {
                formacionProfesional.setFormacion2(null); // pongo la primera para que no sea nulo
                formacionProfesional.setFormacion3(null); // pongo la primera para que no sea nulo
                formacionProfesional.setFormacion4(null); // pongo la primera para que no sea nulo
                _beanFormacion2 = null;
                _beanFormacion3 = null;
                _beanFormacion4 = null;
            }
            if (ub.getNombreTabla().equals("FormacionProfesional2")) {
                formacionProfesional.setFormacion3(null); // pongo la primera para que no sea nulo
                formacionProfesional.setFormacion4(null); // pongo la primera para que no sea nulo
                _beanFormacion3 = null;
                _beanFormacion4 = null;
            }
            if (ub.getNombreTabla().equals("FormacionProfesional3")) {
                formacionProfesional.setFormacion4(null); // pongo la primera para que no sea nulo
                _beanFormacion4 = null;
            }
        }*/
        return _zone.getBody();
        //return _zone;
    }
}