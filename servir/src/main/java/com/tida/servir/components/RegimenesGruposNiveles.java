/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.RegimenGrupoNivel;
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
public class RegimenesGruposNiveles {
     public String getControlName() {
        return "RegimenGrupoNivel";
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
    private RegimenGrupoNivel regimengruponivel;

    
    @Parameter
    //@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Zone _zone;


    @Parameter(defaultPrefix = BindingConstants.LITERAL)
   @Property
    private String _zoneRegGruNiv;
    
    
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
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxReg;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxGru;

    @Property
    @Persist
    private GenericSelectModel<DatoAuxiliar> _beanDatoAuxNiv;

    @Inject
    private PropertyAccess _access;


    @Log
    @SetupRender
    void initializeValue()
    {
        if (regimengruponivel == null ){
            regimengruponivel = new RegimenGrupoNivel();
        }

        if (_beanDatoAuxReg == null) {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
            _beanDatoAuxReg = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);

        }
        if (regimengruponivel.getRegimen() == null) {
            
            regimengruponivel.setGrupo(null);
            regimengruponivel.setNivelRemunerativo(null);

        }
        List<DatoAuxiliar> lrgn = new ArrayList<DatoAuxiliar>();

        DatoAuxiliar d;
       if (regimengruponivel.getRegimen() != null) {
            lrgn = Helpers.getDatoAuxiliar("GrupoOcupacional", "REGIMENLABORAL",
                    regimengruponivel.getRegimen().getCodigo(), session);
        } else {
            regimengruponivel.setGrupo(null);
            regimengruponivel.setNivelRemunerativo(null);
        }
        _beanDatoAuxGru = new GenericSelectModel<DatoAuxiliar>(lrgn,DatoAuxiliar.class,"valor","codigo",_access);

        lrgn = new ArrayList<DatoAuxiliar>();
        if (regimengruponivel.getGrupo()!= null) {
            lrgn = Helpers.getDatoAuxiliar("NivelRemunerativo", "GRUPOOCUPACIONAL",
                    regimengruponivel.getGrupo().getCodigo(), session);
        }  else {
            regimengruponivel.setNivelRemunerativo(null);
        }
        _beanDatoAuxNiv = new GenericSelectModel<DatoAuxiliar>(lrgn,DatoAuxiliar.class,"valor","codigo",_access);
        
    }

    @Log
    public Object onValueChanged(Object dato) {
        // es una tabla auxiliar (regimengruponivel)
        DatoAuxiliar ub = (DatoAuxiliar) dato;
        if(ub != null) {
            if (ub.getNombreTabla().equals("REGIMENLABORAL")) {
                // Cargo la provincia
                /*
                 * Esto lo hago desde el SetupRender

                List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("UBProvincia", ub.getNombreTabla(),
                            ub.getCodigo(), session);

                _beanDatoAuxGru = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);
                _beanDatoAuxReg = new GenericSelectModel<DatoAuxiliar>(new ArrayList<DatoAuxiliar>(),DatoAuxiliar.class,"valor","codigo",_access);

                 * */
                regimengruponivel.setGrupo(null); // pongo la primera para que no sea nulo
                regimengruponivel.setNivelRemunerativo(null); // puede ser nlo porque es nulo en _bean


            }
            if (ub.getNombreTabla().equals("GRUPOOCUPACIONAL")) {
                // Cargo el distrito
                /*
                 * Esto lo hago desde el setuprender

                List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("UBDistrito", ub.getNombreTabla(),
                            ub.getCodigo(), session);


                _beanDatoAuxReg = new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);
                 * */

                regimengruponivel.setNivelRemunerativo(null); // pongo la primera para que no sea nulo

            }
        }
        return _zone.getBody();
        //return _zone;
    }

}
