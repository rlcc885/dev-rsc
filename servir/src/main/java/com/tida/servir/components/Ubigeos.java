/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class Ubigeos {

    @Parameter(required = true)
    @Property
    private Ubigeo ubigeo;
    @Parameter
    private Zone _zone;
    @Parameter
    private boolean disabled;
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zoneName;
    @Parameter(defaultPrefix = "literal")
    private String label;
    @Inject
    private ComponentResources resources;
    @Inject
    private ComponentDefaultProvider defaultProvider;
    @Inject
    private Session session;
    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxDist;
    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxProv;
    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxDepto;
    @Inject
    private PropertyAccess _access;

    public String getControlName() {
        return "Ubigeos";
    }

    public boolean isRequired() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getElementName() {
        return null;
    }

    public String getLabel() {
        return label;
    }

    String defaultLabel() {
        return defaultProvider.defaultLabel(resources);
    }

    public String getClientId() {
        return resources.getId();
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Log
    @SetupRender
    void initializeValue() {
        if (ubigeo == null) {
            ubigeo = new Ubigeo();
        }
        if (_beanDatoAuxDepto == null) {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("UBDEPARTAMENTO", null, 0, session);
            _beanDatoAuxDepto = new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
        }
        if (ubigeo.getDepartamento() == null) {
            ubigeo.setProvincia(null);
            ubigeo.setDistrito(null);
        }

        List<DatoAuxiliar> lub = new ArrayList<DatoAuxiliar>();

        //DatoAuxiliar d;
        if (ubigeo.getDepartamento() != null) {
            lub = Helpers.getDatoAuxiliar("UBPROVINCIA", "UBDEPARTAMENTO", ubigeo.getDepartamento().getCodigo(), session);
        } else {
            ubigeo.setProvincia(null);
            ubigeo.setDistrito(null);
        }

        _beanDatoAuxProv = new GenericSelectModel<DatoAuxiliar>(lub, DatoAuxiliar.class, "valor", "codigo", _access);

        lub = new ArrayList<DatoAuxiliar>();
        if (ubigeo.getProvincia() != null) {
            lub = Helpers.getDatoAuxiliar("UBDISTRITO", "UBPROVINCIA", ubigeo.getProvincia().getCodigo(), session);
        } else {
            ubigeo.setDistrito(null);
        }
        _beanDatoAuxDist = new GenericSelectModel<DatoAuxiliar>(lub, DatoAuxiliar.class, "valor", "codigo", _access);
    }

    @Log
    public Object onValueChanged(Object dato) {
        DatoAuxiliar ub = (DatoAuxiliar) dato;
        if (ub != null) {
            if (ub.getNombreTabla().equals("UBDEPARTAMENTO")) {
                ubigeo.setProvincia(null); // pongo la primera para que no sea nulo
                ubigeo.setDistrito(null); // puede ser nlo porque es nulo en _bean
            }
            if (ub.getNombreTabla().equals("UBPROVINCIA")) {
                ubigeo.setDistrito(null); // pongo la primera para que no sea nulo
            }
        }
        return _zone.getBody();
    }
}