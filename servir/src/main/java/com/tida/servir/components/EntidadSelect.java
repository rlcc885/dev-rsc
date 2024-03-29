/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.components;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.LkBusquedaEntidad;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
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
    @SessionState
    private Usuario _usuario;
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
    private LkBusquedaEntidad sentidad;
    @Property
    @Persist
    private Entidad ssubentidad;
    @Property
    @Persist
    private DatoAuxiliar stipoSubEntidad;
    @Property
    @Persist
    private boolean sessubentidad2;
    @Property
    @Persist
    private boolean prueba_msj;
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

// CAMBIOS EN LA ENTIDAD    
    @Property
    @Persist
    private LkBusquedaEntidad entidadPrueba;
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SELECCION_ENTIDAD);
        return null;
    }
    //Para inicializar valores
    @Log
    void setupRender() {
        logueo();
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
        ssubentidad = null;
        if (entidad.getEntidad() == null) {
            sentidad=(LkBusquedaEntidad)session.load(LkBusquedaEntidad.class, entidad.getId());
            bessubentidad = false;
            sessubentidad2 = false;
            System.out.println(sentidad.getDenominacion());
        } else {
            ssubentidad = entidad;
            bessubentidad = true;
            sessubentidad2 = true;
            System.out.println(ssubentidad.getDenominacion());
        }

        entidadPrueba = sentidad;
        sentidad = null;
        prueba_msj=false;
    }

    //para obtener datos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
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
    public GenericSelectModel<LkBusquedaEntidad> getEntidades() {
        return new GenericSelectModel<LkBusquedaEntidad>(searchEntidades(), LkBusquedaEntidad.class, "denominacion", "id", _access);
    }

    @Log
    private List<LkBusquedaEntidad> searchEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        c.add(Restrictions.eq("estado", true));
        if (snivelGobierno != null) {
            c.add(Restrictions.eq("nivelgobierno", snivelGobierno.getValor()));
        }
        if (sorganizacionestado != null) {
            c.add(Restrictions.eq("organizacionestado", sorganizacionestado.getValor()));
        }
        if (ssectorGobierno != null) {
            c.add(Restrictions.eq("sectorgobierno", ssectorGobierno.getValor()));
        }
        if (stipoOrganismo != null) {
            c.add(Restrictions.eq("tipoorganismo", stipoOrganismo.getValor()));
        }
        c.add(Restrictions.eq("essubentidad", false));
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
        return c.list();
    }

    //para obtener datos del Tipo Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    Object onValueChangedFromSnivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            borganizacionestado = false;
        } else {
            borganizacionestado = true;
        }
        sorganizacionestado = null;
        return request.isXHR() ? new MultiZoneUpdate("OrganizacionZone", OrganizacionZone.getBody()).add("EntidadZone", EntidadZone.getBody()) : null;
    }

    @Log
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

    @Log
    Object onValueChangedFromSsectorgobierno(DatoAuxiliar dato) {
        if (dato == null) {
            btipoorganismo = false;
        } else {
            btipoorganismo = true;
        }
        stipoOrganismo = null;
        return new MultiZoneUpdate("TipoOrganismoZone", TipoOrganismoZone.getBody()).add("EntidadZone", EntidadZone.getBody());
    }

    @Log
    Object onValueChangedStipoOrganismo(DatoAuxiliar dato) {
        return EntidadZone.getBody();
    }

    @Log
    Object onSuccessFromformEsSubEntidad() {
        if (bessubentidad) {
            bessubentidad = false;
        } else {
            bessubentidad = true;
        }
        return new MultiZoneUpdate("TipoSubEntidadZone", TipoSubEntidadZone.getBody()).add("SubEntidadZone", SubEntidadZone.getBody());
    }

    @Log
    Object onSuccessFromformTipoSubEntidad() {
        return SubEntidadZone.getBody();
    }

    @Log
    void onSelectedFromCancel() {
        elemento = 2;
    }

    @Log
    void onSelectedFromReset() {
        elemento = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        if (elemento == 1) {
            
          // cambios   
            sentidad = null;
          // cambios  
            
            return "CambioEntidad";
        } else if (elemento == 2) {
            
          // cambios  
            if (sentidad == null){sentidad = entidadPrueba;}
          // cambios
            
            return "Alerta";
        } else {
            if (bessubentidad) {
                if (ssubentidad == null) {
                    formulariobotones.recordError("Tiene que seleccionar una Sub-Entidad");
                    return UnidadEjecutoraZone.getBody();
                } else {
                    System.out.println("diccccc");
                    entidad = ssubentidad;
                }
            } else {
                if (sentidad == null) {
                    formulariobotones.recordError("Tiene que seleccionar una Entidad");
                    return UnidadEjecutoraZone.getBody();
                } else {
                    entidad=(Entidad)session.load(Entidad.class, sentidad.getId());
                    System.out.println("docccccc"+entidad);
                }
            }
            new Logger().loguearOperacion(session, _usuario, "",Logger.CODIGO_OPERACION_EXECUTE,Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SELECCION_ENTIDAD);
            envelope.setContents("Entidad /U. Ejecutora Seleccionada");
            System.out.println("LA ENTIDADX ES : "+sentidad);
            prueba_msj = true;
        
            if (_zone != null) {
                //return new MultiZoneUpdate("UnidadEjecutoraZone", UnidadEjecutoraZone.getBody()).add(_zoneName, _zone.getBody());
                return "CambioEntidad";
            } else {
                //return UnidadEjecutoraZone.getBody();
                if(prueba_msj==true)
                {return "CambioEntidad";
                }else{
                    return "CambioEntidad";
                }
                
            }
        }
        
    }
}