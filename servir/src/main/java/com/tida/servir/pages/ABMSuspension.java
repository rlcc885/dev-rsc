package com.tida.servir.pages;

import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.LkBusquedaTrabajador;
import com.tida.servir.entities.Evento;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import helpers.Helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;

import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class ABMSuspension  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Property
    @SessionState
    private Usuario _usuario;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    @Property
    @Persist
    private Suspension nuevasuspension;
    @PageActivationContext
    private Sancion modificasancion;
    
    //campos modal
    @Property
    @Persist
    private String bdenoentidad;
    @Property
    @Persist
    private LkBusquedaEntidad rowentidad;
    @InjectComponent
    private Zone busquedamodalZone;
    
    //campos formularios
    @Property
    @Persist
    private String bentidadinicio;
    @Property
    @Persist
    private String fechadocini;
    @Property
    @Persist
    private String fechadocnoti;
    @Property
    @Persist
    private String bentidadfin;
    @Property
    @Persist
    private String fechadocfin;
    @Property
    @Persist
    private String fechadocnotf;
    @InjectComponent
    private Zone suspensionZone;
    @InjectComponent
    private Zone entidadiniZone;
    @InjectComponent
    private Zone entidadfinZone;
    
    //validaciones
    @Property
    @Persist
    private boolean mostrarlista;
    @Property
    @Persist
    private boolean entidadsele;
    
    @Log
    public Sancion getModificasancion() {
        return modificasancion;
    }

    public void setModificasancion(Sancion modificasancion) {
        this.modificasancion = modificasancion;
    }
    
    // inicio de la pagina
    @Log
    @SetupRender
    private void inicio() {
        nuevasuspension=new Suspension();       
    }
    
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipo_documentoini() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipo_documentonoti() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    } 
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipo_documentofin() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipo_documentonotf() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }    
    
    @Log
     public List<LkBusquedaEntidad> getListadoEntidades() {
         Criteria c = session.createCriteria(LkBusquedaEntidad.class);
         if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
         }
         return c.list();
    }
    
    Object onBuscarenti(){
        mostrarlista=false;
        entidadsele=true;
        bdenoentidad=null;
        return entidadiniZone.getBody();
    }
    
    Object onBuscarentifin(){
        mostrarlista=false;
        entidadsele=false;
        bdenoentidad=null;
        return entidadfinZone.getBody();          
    }
    
    @Log
    Object onActionFromSeleccionarentidad(Entidad enti) {               
        if(entidadsele){
            nuevasuspension.setEntidad_ini(enti);
            bentidadinicio=enti.getDenominacion();
        }
        else{
            nuevasuspension.setEntidad_fin(enti); 
            bentidadfin=enti.getDenominacion();
        }
        return new MultiZoneUpdate("entidadiniZone", entidadiniZone.getBody()).add("entidadfinZone", entidadfinZone.getBody());
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedaentidad() {   
        mostrarlista=true;
        return busquedamodalZone.getBody();
    }
    
    Object onReset(){
        nuevasuspension=new Suspension();
        bentidadinicio=null;
        fechadocini=null;
        fechadocnoti=null;        
        fechadocfin=null;
        fechadocnotf=null;
        bentidadfin=null;
        return suspensionZone.getBody();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformaltasuspension(){
        if (fechadocini != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasuspension.setFecha_docini((Date) formatoDelTexto.parse(fechadocini));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if (fechadocnoti != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasuspension.setFecha_docnoti((Date) formatoDelTexto.parse(fechadocnoti));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if (fechadocfin != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasuspension.setFecha_docfin((Date) formatoDelTexto.parse(fechadocfin));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if (fechadocnotf != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasuspension.setFecha_docnotf((Date) formatoDelTexto.parse(fechadocnotf));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        session.saveOrUpdate(nuevasuspension);
        onReset();
        return suspensionZone.getBody();
    }
    
}