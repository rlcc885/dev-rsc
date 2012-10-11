package com.tida.servir.pages;

import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.LkBusquedaTrabajador;
import com.tida.servir.entities.Evento;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;

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
    @Inject
    private ComponentResources _resources;
    @InjectComponent
    private Envelope envelope;
    @SessionState
    @Property
    private UsuarioAcceso usua;
    
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
    @InjectComponent
    private Zone mensajesZone;
    @Component(id ="formularioMensajes")
    private Form formularioMensajes;
    //validaciones
    @Property
    @Persist
    private boolean mostrarlista;
    @Property
    @Persist
    private boolean entidadsele;
    @Persist
    private List<LkBusquedaSuspension> busqueda;
    @Property
    @Persist
    private boolean editando;
    @Property
    @Persist
    private boolean vregistrar;
    @Property
    @Persist
    private boolean veditar;
    
    @Log
    public Sancion getModificasancion() {
        return modificasancion;
    }

    public void setModificasancion(Sancion modificasancion) {
        this.modificasancion = modificasancion;
    }
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, _usuario, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SUSPENSION);
        return null;
    }
    
    // inicio de la pagina
    @Log
    @SetupRender
    private void inicio() {
        logueo();  
        nuevasuspension=new Suspension();
        busqueda=getBeanSuspension();
        editando=false;
        if(busqueda.size()>0){
            nuevasuspension=(Suspension) session.load(Suspension.class, busqueda.get(0).getId());
            mostrar();
            bentidadinicio=busqueda.get(0).getEntidadini();
            bentidadfin=busqueda.get(0).getEntidadfin();
            editando=true;
        }
        else{ 
            nuevasuspension.setSancion_id(modificasancion.getId());
        }       
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
        }
        if (usua.getAccesoreport() == 1) {
            vregistrar = true;
        }        
    }
    
    void mostrar(){
        if (nuevasuspension.getFecha_docini()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocini = formatoDeFecha.format(nuevasuspension.getFecha_docini());
        }
        if (nuevasuspension.getFecha_docnoti()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocnoti = formatoDeFecha.format(nuevasuspension.getFecha_docnoti());
        }
        if (nuevasuspension.getFecha_docfin()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocfin = formatoDeFecha.format(nuevasuspension.getFecha_docfin());
        }
        if (nuevasuspension.getFecha_docnotf()!= null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            fechadocnotf = formatoDeFecha.format(nuevasuspension.getFecha_docnotf());
        }
    }
    
    @Log
    public List<LkBusquedaSuspension>  getBeanSuspension() {
        Criteria c = session.createCriteria(LkBusquedaSuspension.class);
        c.add(Restrictions.eq("sancion_id", modificasancion.getId()));         
        return c.list();
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
            nuevasuspension.setEntidad_ini_id(enti.getId());
            bentidadinicio=enti.getDenominacion();
        }
        else{
            nuevasuspension.setEntidad_fin_id(enti.getId()); 
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
        if(getBeanSuspension().size()>0){
            nuevasuspension=(Suspension) session.load(Suspension.class, busqueda.get(0).getId());
        }
        else{
            nuevasuspension.setSancion_id(modificasancion.getId());
        }    
        bentidadinicio=null;
        fechadocini=null;
        fechadocnoti=null;        
        fechadocfin=null;
        fechadocnotf=null;
        bentidadfin=null;
        formularioMensajes.clearErrors();
        return suspensionZone.getBody();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformaltasuspension(){
        formularioMensajes.clearErrors();
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
                if(nuevasuspension.getFecha_docnoti().before(modificasancion.getFechaini_inha())){
                   formularioMensajes.recordError("La Fecha de Inicio debe ser mayor a la Fecha de Inicio de la Sancion");
                   return new MultiZoneUpdate("suspensionZone", suspensionZone.getBody()).add("mensajesZone",mensajesZone.getBody());
                }
                if(nuevasuspension.getFecha_docnoti().after(modificasancion.getFechafin_inha())){
                   formularioMensajes.recordError("La Fecha de Inicio debe ser menor a la Fecha de Fin de la Sancion");
                   return new MultiZoneUpdate("suspensionZone", suspensionZone.getBody()).add("mensajesZone",mensajesZone.getBody());
                }
                if(nuevasuspension.getFecha_docini().after(nuevasuspension.getFecha_docnoti())) {
                   formularioMensajes.recordError("La Fecha de Notificación debe ser menor a la Fecha de Inicio");
                   return new MultiZoneUpdate("suspensionZone", suspensionZone.getBody()).add("mensajesZone",mensajesZone.getBody());
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        modificasancion.setSancion_estado(getEstados((long)3).get(0));
        if (fechadocfin != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasuspension.setFecha_docfin((Date) formatoDelTexto.parse(fechadocfin));                              
            } catch (ParseException ex){
                ex.printStackTrace();
            }
        }
        if (fechadocnotf != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {                
                nuevasuspension.setFecha_docnotf((Date) formatoDelTexto.parse(fechadocnotf));
                if(nuevasuspension.getFecha_docfin().after(nuevasuspension.getFecha_docnotf())) {
                   formularioMensajes.recordError("La Fecha de Notificación debe ser menor a la Fecha de Fin");
                   return new MultiZoneUpdate("suspensionZone", suspensionZone.getBody()).add("mensajesZone",mensajesZone.getBody());
                }                
                if (nuevasuspension.getFecha_docnoti().after(nuevasuspension.getFecha_docnotf())){
                    formularioMensajes.recordError("La fecha de inicio de la notificación debe ser menor a la fecha de fin");
                    return new MultiZoneUpdate("mensajesZone", mensajesZone.getBody()).add("suspensionZone",suspensionZone.getBody());     

                }                
                modificasancion.setFechafin_inha(calcularfecha()); 
                modificasancion.setSancion_estado(getEstados((long)1).get(0));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
              
        session.saveOrUpdate(nuevasuspension);
        session.flush();
        envelope.setContents(helpers.Constantes.SUSPENSION_EXITO);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevasuspension.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_SUSPENSION);        
        session.saveOrUpdate(modificasancion);
        session.flush();        
        return new MultiZoneUpdate("suspensionZone", suspensionZone.getBody()).add("mensajesZone",mensajesZone.getBody());
    }
    
    int calcularperiodoprimero(){   
        String consulta ="SELECT 1 ID,to_number(to_date('"+fechadocnotf+"','dd/mm/yyyy') - to_date('"+fechadocnoti+"','dd/mm/yyyy')) DIAS from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkConsultaPeriodo.class);  
        List result = query.list();        
        LkConsultaPeriodo lkcondos = (LkConsultaPeriodo) result.get(0);
        return lkcondos.getDias();
    }
    Date calcularfecha(){
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechafin = formatoDeFecha.format(modificasancion.getFechafin_inha());
        String consulta ="SELECT 1 ID,to_date('"+fechafin+"','dd/mm/yyyy') + to_number('"+calcularperiodoprimero()+"') FECHA  from dual";
        Query query =session.createSQLQuery(consulta).addEntity(LkSumaFecha.class);  
        List result = query.list();        
        LkSumaFecha lkcondos = (LkSumaFecha) result.get(0);
        return lkcondos.getFecha();
    }
    
    
    @Log
    public List<DatoAuxiliar> getEstados(long cod) {
        Criteria c = session.createCriteria(DatoAuxiliar.class);        
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(Restrictions.eq("codigo", cod));
        return c.list();
    }
    
}