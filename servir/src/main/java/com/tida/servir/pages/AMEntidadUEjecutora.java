package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.internal.services.UploadedFileItem;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades ejecutoras
 *
 * @author LFL
 */
public class AMEntidadUEjecutora extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @Persist
    private Entidad entidadUE;
    @Property
    @Persist
    private Entidad subEntidadUE;
    
    @Property
    private Entidad oi;
    
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    @InjectComponent
    private Zone mensajesZone;    
        
    @Component(id = "formularionivelgobierno")
    private Form formularionivelgobierno;
    @Component(id = "formularioorganizacion")
    private Form formularioorganizacion;
    @Component(id = "formulariosector")
    private Form formulariosector;
    @Component(id = "formularioorganismo")
    private Form formularioorganismo;
    @Component(id = "formularioentidad")
    private Form formularioentidad;
    @Component(id = "formulariosubentidad")
    private Form formulariosubentidad;
    @Property
    @SessionState
    private Usuario _usuario;
    @Persist
    private String nombreArchivo;
   @Property
    @Persist
    private Ubigeo ubigeoEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeoEntidadZone;
    @Property
    @Persist
    private boolean bsector;
    @Property
    @Persist
    private boolean btipoorganismo;
    @Component(id = "formularioubigeo")
    private Form formularioubigeo;
    @Component(id = "formulariosububigeo")
    private Form formulariosububigeo;
    @Component(id = "formulariologoentidad")
    private Form formulariologoentidad;
    @InjectComponent
    private Zone logoentidadZone;   
    
    //Entidad Origen
    @InjectComponent
    private Zone EOrigenZone;
    @InjectComponent
    private Zone entiZone;
    @Component(id = "formularioentidadorigen")
    private Form formularioentidadorigen;
    @Persist
    @Property
    private String bdenoentidad; 
    @Property
    @Persist
    private boolean mostrar;
    @Persist
    @Property
    private Entidad entio;
    @InjectComponent
    private Zone busZone;
    @Persist
    @Property
    private String entidad_origen;
    
    
    //Trabajador
    @Component(id = "formulariosubtitular")
    private Form formulariosubtitular;
    @Component(id = "formulariosubjeferrhh")
    private Form formulariosubjeferrhh;
    @Component(id = "formulariosubjefeoga")
    private Form formulariosubjefeoga;
    @Component(id = "formulariosubbotones")
    private Form formulariosubbotones;
    @Component(id = "formulariotitular")
    private Form formulariotitular;
    @Component(id = "formulariojeferrhh")
    private Form formulariojeferrhh;
    @Component(id = "formulariojefeoga")
    private Form formulariojefeoga;
    @Component(id = "formulariobotones")
    private Form formulariobotones;
    @InjectComponent
    private Zone busZone2;
    @Persist
    @Property
    private String apeTrabajador; 
    @InjectComponent
    private Zone trabajadorZone;
    @Property
    private Trabajador subtitulart;
    @Property
    private Trabajador subjeferrhht;
    @Property
    private Trabajador subjefeogat;
    @Property
    private Trabajador titulart;
    @Property
    private Trabajador jeferrhht;
    @Property
    private Trabajador jefeogat;
    @Persist
    @Property
    private String subtitular;
    @Persist
    @Property
    private String subjefeOGA;
    @Persist
    @Property
    private String subjefeRRHH;
    @Persist
    @Property
    private String titular;
    @Persist
    @Property
    private String jefeOGA;
    @Persist
    @Property
    private String jefeRRHH;
    @InjectComponent
    private Zone subTitularZone;
    @InjectComponent
    private Zone subJefeRRHHZone;
    @InjectComponent
    private Zone subJefeOGAZone;
    @InjectComponent
    private Zone TitularZone;
    @InjectComponent
    private Zone JefeRRHHZone;
    @InjectComponent
    private Zone JefeOGAZone;
    @Property
    @Persist
    private boolean btitular;
    @Property
    @Persist
    private boolean bjefeOGA;    
    @Property
    @Persist
    private boolean bjefeRRHH;
    @Property
    @Persist
    private boolean btitulari;
    @Property
    @Persist
    private boolean bjefeOGAi;    
    @Property
    @Persist
    private boolean bjefeRRHHi;
    @Property
    @Persist
    private boolean blogoentidadf;
    @Property
    @Persist
    private boolean blogoentidadi;
    
    
    //Listado de entidades
    @InjectComponent
    private Zone listaentidadZone;
    @Persist
    @Property
    private LkBusquedaEntidad listaentidad;
    
    
    //Busqueda Entidad Zone
    @InjectComponent
    private Zone busquedacombosZone;
    @Component(id = "formulariocombosbusqueda")
    private Form formulariocombosbusqueda;
    @Component(id = "formulariobotonesbusqueda")
    private Form formulariobotonesbusqueda;
    @Property
    @Persist
    private DatoAuxiliar bnivelGobierno;
    @Property
    @Persist
    private DatoAuxiliar borganizacionEstado;
    @Property
    @Persist
    private DatoAuxiliar bsectorGobierno;
    @Property
    @Persist
    private DatoAuxiliar btipoOrganismo;
    @Property
    @Persist
    private Ubigeo ubigeobusEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeobusEntidadZone;
    @Component(id = "formulariobusubigeo")
    private Form formulariobusubigeo;
    @Persist
    @Property
    private String busdenominacion; 
    
    //Busqueda SubEntidad Zone
    @InjectComponent
    private Zone busquedasubcombosZone;
    @Component(id = "formulariosubcombosbusqueda")
    private Form formulariosubcombosbusqueda;
    @Component(id = "formulariosubbotonesbusqueda")
    private Form formulariosubbotonesbusqueda;
    @Property
    @Persist
    private DatoAuxiliar btiposubentidad;
    @Property
    @Persist
    private Ubigeo ubigeobusSubEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeobusSubEntidadZone;
    @Component(id = "formulariobussububigeo")
    private Form formulariobussububigeo;
    @Persist
    @Property
    private String bussubdenominacion;     
    
    @Property
    @Persist
    private Ubigeo ubigeoSubEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeoSubEntidadZone;
   
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private boolean mostrarFiltros;
    @Persist
    @Property
    private String mostrarEsconder;
 
    @InjectComponent
    @Property
     private Zone nivelOrganizacionSectorZone;
    @InjectComponent
    @Property
    private Zone principalZone;
    @InjectComponent
    @Property
     private Zone botonesZone;
    @InjectComponent
    @Property
    private Zone subprincipalZone;
    @InjectComponent
    @Property
     private Zone subbotonesZone;
    @InjectComponent
    private Envelope envelope;
   
    //Logotipo    
    @Property
    @Persist
    private UploadedFile file;
    @Property
    @Persist
    private UploadedFile file2;
    @Persist
    @Property
    private String uploadmessage;
    @Inject
    private ApplicationGlobals globals;
    

    private int elemento=0;

   //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            entidadUE = new Entidad();
            subEntidadUE = new Entidad();
            entio = new Entidad();
            blogoentidadi=true;
    }

    //para obtener datatos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Sector Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos de la Organizacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
             List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Organismo
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Via
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Zona
     @Log
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
     //para obtener datatos del Tipo Via Sub Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getSubTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Zona Sub Entidad
     @Log
    public GenericSelectModel<DatoAuxiliar> getSubTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
     
     //reset del formulario (borrar objeto)
     
    @Log
    void onSelectFromSubreset() {
           elemento=1;
    }
    
    @Log
    void onSelectFromSubcancel() {
        elemento=2;
    }
    
    @Log
    void onSelectFromReset() {
           elemento=1;
    }
    
    @Log
    void onSelectFromBusreset() {
         bnivelGobierno=null;
            bsectorGobierno=null;
            borganizacionEstado=null;
            btipoOrganismo=null;
            busdenominacion=null;
            ubigeobusEntidadUE=null;
            btiposubentidad=null;
            bussubdenominacion=null;
            ubigeobusSubEntidadUE=null;
            elemento=3;
    }
        
    @Log
    void onSelectFromBussubreset() {
         bnivelGobierno=null;
            bsectorGobierno=null;
            borganizacionEstado=null;
            btipoOrganismo=null;
            busdenominacion=null;
            ubigeobusEntidadUE=null;
            btiposubentidad=null;
            bussubdenominacion=null;
            ubigeobusSubEntidadUE=null;
            elemento=3;
             System.out.println("entroeeeeeeee al elemento" + elemento);
    }
    
    @Log
    void onSelectFromCancel() {
        elemento=2;
    }
    
    

    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        
        if(elemento==1){
            return "AMEntidadUEjecutora";
        }else if(elemento==2){
            return "AMEntidadUEjecutora";
        }else{    
        entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
        entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
        entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
        //nombreArchivo = file.getFileName().substring(0, file.getFileName().length() - 4);
        //nombreArchivo = file.getFileName() ;
        //System.out.println(nombreArchivo);
        //entidadUE.setLogotipo(nombreArchivo);
        session.saveOrUpdate(entidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        return 
        new MultiZoneUpdate("nivelOrganizacionSectorZone", nivelOrganizacionSectorZone.getBody())                             
                    .add("principalZone", principalZone.getBody())
                    .add("ubigeoEntidadZone", ubigeoEntidadZone.getBody())
                    .add("TitularZone", TitularZone.getBody())
                    .add("JefeRRHHZone", JefeRRHHZone.getBody())
                    .add("JefeOGAZone", JefeOGAZone.getBody())
                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        }
        
    }

    @Log
    @CommitAfter    
    Object onSuccessFromFormulariosubbotones() {
        
        if(elemento==1){
            return "AMEntidadUEjecutora";
        }else if(elemento==2){
            return "AMEntidadUEjecutora";
        }else{    
        subEntidadUE.setDepartamento(ubigeoSubEntidadUE.getDepartamento());
        subEntidadUE.setProvincia(ubigeoSubEntidadUE.getProvincia());
        subEntidadUE.setDistrito(ubigeoSubEntidadUE.getDistrito());
        subEntidadUE.setEntidad(entio);
       // nombreArchivo = file.getFileName();
        //System.out.println(nombreArchivo);
        //entidadUE.setLogotipo(nombreArchivo);
        session.saveOrUpdate(subEntidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(subEntidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
         envelope.setContents("Clave modificada con éxito.");
        return   new MultiZoneUpdate("subprincipalZone", subprincipalZone.getBody())                             
                    .add("ubigeoSubEntidadZone", ubigeoSubEntidadZone.getBody())
                    .add("subTitularZone", subTitularZone.getBody())
                    .add("subJefeRRHHZone", subJefeRRHHZone.getBody())
                    .add("subJefeOGAZone", subJefeOGAZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        }
        
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormularioorganizacion() {
        bsector=true;    
        return nivelOrganizacionSectorZone.getBody();
        
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariologoentidad() {
        blogoentidadi=false;
        blogoentidadf=true;
        System.out.println("entroeeeeeeee");
        System.out.println(entidadUE.getLogotipo());
        // Set Path
        //String path = globals.getServletContext().getRealPath("/layout/images") + "/";

        //File copied;

        // Get File
        //copied = new File("D:/entidad/" + file.getFileName());

        // File Saved
        //file.write(copied);
        return this;
        
    }
    
    public String getUploadError(){
        
        return uploadmessage;
    }
    
    public Object onUploadException(FileUploadException ex)
    {
        uploadmessage = "Upload exception: " + ex.getMessage() + " You can only upload file less than or equal to 500kb.";

        return this;
    }
    
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariosector() {
        btipoorganismo=true;    
        return nivelOrganizacionSectorZone.getBody();
        
    }
    
 
    AMEntidadUEjecutora onActionFromToggle_filtros() {
        if (mostrarFiltros) {
            mostrarFiltros = false;
            mostrarEsconder = "Mostrar";
        } else {
            mostrarFiltros = true;
            mostrarEsconder = "Oscultar";
        }
        return this;
    }
   
 
    @Log
    public void onActivate(Entidad eue) {
        entidadUE = eue;
        mostrarEsconder = "Ocultar";
        mostrarFiltros = true;
    }

    
    //Metodos de Busqueda de Entidades origenes para la subentidad   
    
    @Log
    @CommitAfter
    Object onSuccessFromformularioentidadorigen(){
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));      
        return c.list();
    }
    
    @Log
    public List<LkBusquedaEntidad> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
  
        //Entidad
        if (bnivelGobierno != null) {
            System.out.println("------------------ Nivel Gobierno " + bnivelGobierno.getValor());
            c.add(Restrictions.eq("nivelgobierno", bnivelGobierno.getValor()));
        }
        if (bsectorGobierno != null) {
            System.out.println("------------------ Sector Gobierno " + bsectorGobierno.getValor());
            c.add(Restrictions.eq("sectorgobierno", bsectorGobierno.getValor()));
        }
        if (borganizacionEstado != null) {
            System.out.println("------------------ Organizacion Estado " + borganizacionEstado.getValor());
            c.add(Restrictions.eq("organizacionestado", borganizacionEstado.getValor()));
        }
        if (btipoOrganismo != null) {
            System.out.println("------------------ Tipo Organismo " + btipoOrganismo.getValor());
            c.add(Restrictions.eq("tipoorganismo", btipoOrganismo.getValor()));
        }
        if (busdenominacion != null && !busdenominacion.equals("")) {
            System.out.println("------------------ Enitidad " + busdenominacion);
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", busdenominacion + "%").ignoreCase()).add(Restrictions.like("denominacion", busdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", busdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));      
        }
        if (ubigeobusEntidadUE.getDepartamento() != null) {
            System.out.println("------------------ Departamento " + ubigeobusEntidadUE.getDepartamento().getValor());
            c.add(Restrictions.eq("departamento", ubigeobusEntidadUE.getDepartamento().getValor()));
        }
        if (ubigeobusEntidadUE.getProvincia() != null) {
            System.out.println("------------------ Provincia " + ubigeobusEntidadUE.getProvincia().getValor());
            c.add(Restrictions.eq("provincia", ubigeobusEntidadUE.getProvincia().getValor()));
        }
        if (ubigeobusEntidadUE.getDistrito() != null) {
            System.out.println("------------------ Distrito" + ubigeobusEntidadUE.getDistrito().getValor());
            c.add(Restrictions.eq("distrito", ubigeobusEntidadUE.getDistrito().getValor()));
        }
        
        //Sub Entidad
        if (btiposubentidad != null) {
            System.out.println("------------------ Tipo Sub Entidad " + btiposubentidad.getValor());
            c.add(Restrictions.eq("tiposubentidad", btiposubentidad.getValor()));
        }
        if (bussubdenominacion != null && !bussubdenominacion.equals("")) {
            System.out.println("------------------ Enitidad " + bussubdenominacion);
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bussubdenominacion + "%").ignoreCase()).add(Restrictions.like("denominacion", bussubdenominacion.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bussubdenominacion.replaceAll("n", "ñ") + "%").ignoreCase()));      
        }
        if (ubigeobusSubEntidadUE.getDepartamento() != null) {
            System.out.println("------------------ Departamento " + ubigeobusSubEntidadUE.getDepartamento().getValor());
            c.add(Restrictions.eq("departamento", ubigeobusSubEntidadUE.getDepartamento().getValor()));
        }
        if (ubigeobusSubEntidadUE.getProvincia() != null) {
            System.out.println("------------------ Provincia " + ubigeobusSubEntidadUE.getProvincia().getValor());
            c.add(Restrictions.eq("provincia", ubigeobusSubEntidadUE.getProvincia().getValor()));
        }
        if (ubigeobusSubEntidadUE.getDistrito() != null) {
            System.out.println("------------------ Distrito" + ubigeobusSubEntidadUE.getDistrito().getValor());
            c.add(Restrictions.eq("distrito", ubigeobusSubEntidadUE.getDistrito().getValor()));
        }
        
        
        return c.list();
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariobotonesbusqueda(){
        System.out.println("ellll" + elemento);
        if(elemento==3){
            System.out.println("entroeeeeeeee al elemento" + elemento);
            return new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody())                             
                    .add("ubigeobusEntidadZone", ubigeobusEntidadZone.getBody());
                    
        }else{
            return listaentidadZone.getBody();
        }
        

    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariosubbotonesbusqueda(){
         System.out.println("ellll" + elemento);
         if(elemento==3){
            return new MultiZoneUpdate("busquedasubcombosZone", busquedasubcombosZone.getBody())                             
                    .add("ubigeobusSubEntidadZone", ubigeobusSubEntidadZone.getBody());
                    
        }else{
            return listaentidadZone.getBody();
        }

    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        mostrar=true;        
        return new MultiZoneUpdate("busZone", busZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    @Log
    Object onActionFromEditar(Entidad entix) {        
        entio = entix;
        entidad_origen=entio.getDenominacion(); 
        
        subEntidadUE.setEntidad(entio);
        
        subEntidadUE.setTipoVia(entio.getTipoVia());
        subEntidadUE.setTipoZona(entio.getTipoZona());
        subEntidadUE.setDireccion(entio.getDireccion());
        subEntidadUE.setDescZona(entio.getDescZona());
        ubigeoSubEntidadUE.setDepartamento(entio.getDepartamento());
        ubigeoSubEntidadUE.setProvincia(entio.getProvincia());
        ubigeoSubEntidadUE.setDistrito(entio.getDistrito());
        
        subEntidadUE.setEsSubEntidad(true); 
        mostrar=false;
             return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody())                             
                    .add("subprincipalZone", subprincipalZone.getBody())
                     .add("ubigeoSubEntidadZone", ubigeoSubEntidadZone.getBody());
    }
    
    //Metodos de Busqueda de Trabajadores
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariosubtitular() { 
        btitular=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariosubjeferrhh() { 
        bjefeRRHH=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
     
    @Log
    @CommitAfter
    Object onSuccessFromformulariosubjefeoga() { 
        bjefeOGA=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
    
     @Log
    @CommitAfter
    Object onSuccessFromformulariotitular() { 
        btitulari=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariojeferrhh() { 
        bjefeRRHHi=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
     
    @Log
    @CommitAfter
    Object onSuccessFromformulariojefeoga() { 
        bjefeOGAi=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioTrabajador() { 
        mostrar=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
     
   @Log
    public List<Trabajador> getTrabajadores() {
        Criteria c = session.createCriteria(Trabajador.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("apellidoPaterno", apeTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apeTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apeTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));      
        return c.list();
    }
   
   @Log
    Object onActionFromeditarsubTitular(Trabajador traba) {        
        subtitulart = traba;
        subtitular=subtitulart.getApellidoPaterno();
        subEntidadUE.setTitular(subtitulart);
        btitular=false;
        mostrar=false;
        return subTitularZone.getBody();  
    }
   
    @Log
    Object onActionFromeditarsubJefeRRHH(Trabajador traba) {        
        subjeferrhht = traba;   
        subjefeRRHH=subjeferrhht.getApellidoPaterno();
        subEntidadUE.setJefeRRHH(subjeferrhht);
        bjefeRRHH=false;
        mostrar=false;
        return subJefeRRHHZone.getBody();  
    }
    
     @Log
    Object onActionFromeditarsubJefeOGA(Trabajador traba) {        
        subjefeogat = traba;
        subjefeOGA=subjefeogat.getApellidoPaterno();
        subEntidadUE.setJefeOga(subjefeogat);
        bjefeOGA=false;
        mostrar=false;
        return subJefeOGAZone.getBody();  
    }
   
   @Log
    Object onActionFromeditarTitular(Trabajador traba) {        
        titulart = traba;
        titular=titulart.getApellidoPaterno();
        entidadUE.setTitular(titulart);
        btitulari=false;
        mostrar=false;
        return TitularZone.getBody();  
    }
   
    @Log
    Object onActionFromeditarJefeRRHH(Trabajador traba) {        
        jeferrhht = traba;     
        jefeRRHH=jeferrhht.getApellidoPaterno();
        entidadUE.setJefeRRHH(jeferrhht);
        bjefeRRHHi=false;    
        mostrar=false;
        return JefeRRHHZone.getBody();  
    }
    
     @Log
    Object onActionFromeditarJefeOGA(Trabajador traba) {        
        jefeogat = traba;
        jefeOGA=jefeogat.getApellidoPaterno();
        entidadUE.setJefeOga(jefeogat);
        bjefeOGAi=false;
        mostrar=false;
        return JefeOGAZone.getBody();  
    }
    
}