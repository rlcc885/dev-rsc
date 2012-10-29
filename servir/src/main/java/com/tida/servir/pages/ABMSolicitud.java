/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import helpers.SMTPConfig;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class ABMSolicitud 
{    
    @Inject
    private Session session;   
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    @InjectComponent
    private Envelope envelope;  
    
    //campos
    @Property
    @Persist
    private Trabajador trabajador;
    @Property
    @Persist
    private  Solicitud_Acceso nuevasolicitud;
    @Property
    @Persist
    private String bentidad;  
    @Property
    @Persist
    private DatoAuxiliar bdocumentoidentidad;  
    @Property
    @Persist
    private String bnroDocumento;  
    @Property
    @Persist
    private String bapellidoPaterno;  
    @Property
    @Persist
    private String bapellidoMaterno;  
    @Property
    @Persist
    private String bnombres;  
    @Property
    @Persist
    private String bemailLaboral;  
    @Property
    @Persist
    private String btelefonofijo;      
    @Property
    @Persist
    private String bcargo;
    @Component(id = "formSolicitud")
    private Form formSolicitud;
    @Component(id = "formsubida")
    private Form formsubida;
    @InjectComponent
    private Zone solicitudZone;
    @Property
    @Persist
    private String fecharesolu;
    @Property
    @Persist
    private UploadedFile file;
    
    //campos modal
    @Property
    @Persist
    private String bdenoentidad;
    @Property
    @Persist
    private LkBusquedaEntidad rowentidad;
    @InjectComponent
    private Zone busquedamodalZone;
    @Property
    @Persist
    private Entidad selecentidad;
    @Property
    @Persist
    private boolean mostrarlista;
    @Persist
    private String nombreArchivo;
    @Persist
    private String lugarArchivo ;    
    @Property
    @Persist
    private File copied;
    @Property
    @Persist
    private Boolean procesoFin;
    @Property
    @Persist
    private Boolean etapaInicio;
    @Property
    @Persist
    private Boolean respuestaOk;
    @Inject
    private ApplicationGlobals globals;
    @Persist
    private long traba;
    @Persist 
    private long entidad;
    @Property
    @Persist
    private Perfil bperfil;
    @Persist    
    private String STARTPATH;
    // inicio de la pagina
    @Log
    @SetupRender
    private void inicio() {
        nuevasolicitud=new Solicitud_Acceso();
        STARTPATH=getRuta().get(0).getRuta_final();
    }
    
    @Log
    public List<ConfiguracionAcceso> getRuta() {
        Criteria c = session.createCriteria(ConfiguracionAcceso.class);        
        return c.list();
    }
    
    void onActivate() {
        if (etapaInicio == null) {
            etapaInicio = true;
            procesoFin = false;
        }        
    }
    
    @Log
    public com.tida.servir.services.GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new com.tida.servir.services.GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public com.tida.servir.services.GenericSelectModel<Perfil> getBeanperfil() {
        List<Perfil> list;
        Criteria c = session.createCriteria(Perfil.class);
        c.add(Restrictions.in( "id", new Long[] { 9L, 10L} ));
        list = c.list();
        return new com.tida.servir.services.GenericSelectModel<Perfil>(list, Perfil.class, "descperfil", "id", _access);
    }
    
    @Log
    public List<LkBusquedaTrabajador> getTrabajador(String nrodocumento) throws ParseException {
        Criteria criterio = session.createCriteria(LkBusquedaTrabajador.class);
        criterio.add(Restrictions.eq("estado", true));
        criterio.add(Restrictions.eq("entidad_id", selecentidad.getId()));
        criterio.add(Restrictions.eq("nrodocumento",nrodocumento));
        return criterio.list();
    }
    @Log
     public List<LkBusquedaEntidad> getListadoEntidades() {
         Criteria c = session.createCriteria(LkBusquedaEntidad.class);
         c.add(Restrictions.eq("estado", true));
         if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));
         }
         return c.list();
    }
    
    @Log
    Object onActionFromSeleccionarentidad(Entidad enti) {         
        bentidad=enti.getDenominacion();
        selecentidad=enti;
        entidad=enti.getId();
        return new MultiZoneUpdate("solicitudZone", solicitudZone.getBody());
    }
    @Log
    @CommitAfter
    Object onSuccessFromformbusquedaentidad() {   
        mostrarlista=true;
        return busquedamodalZone.getBody();
    }
    
    
    Object onBuscartraba(){
        formSolicitud.clearErrors();
        bnombres=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bemailLaboral=null;
        btelefonofijo=null;
        bcargo=null;
        if(selecentidad==null){
            formSolicitud.recordError("Tiene que seleccionar la Entidad");
            return solicitudZone.getBody();
        }
        if(bdocumentoidentidad==null){
            formSolicitud.recordError("Tiene que ingresar Tipo de Documento");
            return solicitudZone.getBody();
        }
        if(bnroDocumento==null){
            formSolicitud.recordError("Tiene que ingresar Numero de Documento");
            return solicitudZone.getBody();
        }
        try{
            List<LkBusquedaTrabajador> busqueda=getTrabajador(bnroDocumento);
            if(busqueda.size()>0){
                bnombres=busqueda.get(0).getNombres();
                bapellidoPaterno=busqueda.get(0).getApellidoPaterno();
                bapellidoMaterno=busqueda.get(0).getApellidoMaterno();
                bemailLaboral=busqueda.get(0).getEmailLaboral();
                btelefonofijo=busqueda.get(0).getTelefonofijo();
                bcargo=busqueda.get(0).getCargo();
                trabajador=(Trabajador) session.load(Trabajador.class, busqueda.get(0).getId());
                traba=trabajador.getId();
            }else{
                formSolicitud.recordError("Trabajador Inactivo o no Registrado en la Entidad Seleccionada");
                return solicitudZone.getBody();
            }
        }catch(Exception ex){
            
        }
        return solicitudZone.getBody();
    }
    
    @Log
    void onBnroDocumentoChanged() {
        bnroDocumento=_request.getParameter("param");
    }
    Object onReset() {
        bnombres=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bemailLaboral=null;
        btelefonofijo=null;
        bcargo=null;
        bentidad=null;
        bdocumentoidentidad=null;
        bnroDocumento=null;
        nuevasolicitud=new Solicitud_Acceso();
        trabajador=null;
        selecentidad=null;
        fecharesolu=null;
        return this;
    }
    
    Object onCancel() {
        return "Index";
    }
    
    Object onCancelar() {
        etapaInicio = true;
        procesoFin = false;
        return this;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformSolicitud(){
        if(trabajador==null){
                formSolicitud.recordError("Tiene que Buscar un Trabajador");
                return solicitudZone.getBody();
            }
            etapaInicio = false;
            procesoFin = true;
            respuestaOk=false;
        return this;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformsubida(){ 
        formsubida.clearErrors();
        if (fecharesolu != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                nuevasolicitud.setFec_resolucion((Date) formatoDelTexto.parse(fecharesolu));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } 
        if(nuevasolicitud.getFec_resolucion().after(new Date())){
            formsubida.recordError("La Fecha de Resolucion debe ser menor a la Actual");
            return this;
        }
        Date date = new Date();
        int aleatorio = (int) (Math.random() * 1000 + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        nombreArchivo = file.getFileName().substring(0, file.getFileName().length() - 4);
//        lugarArchivo = STARTPATH + "/" + nombreArchivo + "-" +sdf.format(date)+ "-"+aleatorio+"/";
//        String archivoSOlI = lugarArchivo + file.getFileName();           
//        System.out.println("aquiiiii"+segunda);
//        copied = new File(lugarArchivo);
//        if (!copied.exists()) {
//            copied.mkdirs();
//        }
//        File nuevo = new File(archivoSOlI);
//        file.write(nuevo);
        String path = STARTPATH+"documentosolicitud/";
        String nombreArchivos = file.getFileName().substring(0, file.getFileName().length() - 4)+String.valueOf(sdf.format(date)+ "-"+aleatorio) + file.getFileName().substring(file.getFileName().length() - 4);
        File nuevo = new File(path + nombreArchivos);
        copied = new File(path);
        if (!copied.exists()) {
            copied.mkdirs();
        }
        file.write(nuevo);
        nuevasolicitud.setDocumento(path + nombreArchivos);
        nuevasolicitud.setTrabajador(trabajador);
        nuevasolicitud.setEstado(false);    
        nuevasolicitud.setPerfil(bperfil);              
        session.saveOrUpdate(nuevasolicitud);
        session.flush();
        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
        new Logger().loguearEvento(session, Logger.SOLICITUD_SANCION, entidad, traba, 0, Logger.MOTIVO_SOLICITUD_SANCION, nuevasolicitud.getId());        
        
        if (SMTPConfig.sendMail("Datos de acceso al Modulo de Sanciones - Servir", "Por favor espere la aprobación de su solicitud con un mensaje en su bandeja del correo registrado en el Sistema", bemailLaboral, ca)) {
            System.out.println("Envío Correcto");
        } else{                
                Logger logger = new Logger();
                logger.loguearEvento(session, logger.ERROR_SERVIDOR_DE_CORREO, entidad, traba,0, Logger.CORREO_FAIL_RESET_PASSWORD, 0); 
        }
        
        bnombres=null;        
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bemailLaboral=null;
        btelefonofijo=null;
        bcargo=null;
        bentidad=null;
        bdocumentoidentidad=null;
        bnroDocumento=null;
        nuevasolicitud=new Solicitud_Acceso();
        trabajador=null;
        selecentidad=null;
        fecharesolu=null;
        etapaInicio = true;
        procesoFin = false;
        respuestaOk=true;
        return "Index";
    }
    
}
