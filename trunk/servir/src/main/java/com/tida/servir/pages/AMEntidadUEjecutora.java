package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.util.List;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades ejecutoras
 *
 * @author LFL
 */
public class AMEntidadUEjecutora extends GeneralPage {
    
    //@Persist
//    @PageActivationContext
//    private Entidad enti;
    @Inject
    private Session session;
    @Inject
    private Request _request;
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
    //@Persist
    //private String nombreArchivo;
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
    /*
    @Component(id = "formulariologoentidad")
    private Form formulariologoentidad;
    @InjectComponent
    private Zone logoentidadZone;   
     */
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
    private String nombreTrabajador;
    @InjectComponent
    private Zone trabajadorZone;
    @Property
    private LkBusquedaTrabajador subtitulart;
    @Property
    private LkBusquedaTrabajador subjeferrhht;
    @Property
    private LkBusquedaTrabajador subjefeogat;
    @Property
    private LkBusquedaTrabajador titulart;
    @Property
    private LkBusquedaTrabajador jeferrhht;
    @Property
    private LkBusquedaTrabajador jefeogat;
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
    private DatoAuxiliar btipoOrganismo2;
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
    private boolean bessubentidad;
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
    //Edicion
    @Property
    @Persist
    private boolean badmentidad;
    private int elemento = 0;
    
    
    @Persist
    @Property
    private String desczonaentidad;
    @Persist
    @Property
    private String emailentidad;
    @Persist
    @Property
    private String urlsEntidad;
    @Persist
    @Property
    private String telefEntidad;
    
    @Persist
    @Property
    private String denoSubEntidad;
    @Persist
    @Property
    private String siglaSubEntidad;
    @Persist
    @Property
    private String rucSubEntidad;
    @Persist
    @Property
    private String cueSubEntidad;
    @Persist
    @Property
    private String telefSubEntidad;
    @Persist
    @Property
    private String emailSubEntidad;
    @Persist
    @Property
    private String urslSubEntidad;    
    @Persist
    @Property
    private String desczonasubentidad;    
    @Persist
    @Property
    private String siglaEntidad;
     @Persist
    @Property
    private String cueEntidad;   

    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
//        System.out.println("Entidaddddd"+enti);
        entidadUE=new Entidad();
        subEntidadUE = new Entidad();     
        entio = new Entidad();
//        if(enti!=null){
//            if(enti.getEsSubEntidad()){
//                subEntidadUE=enti;
//                ubigeoEntidadUE=new Ubigeo();
//                obtener(2);
//            }
//            else{
//                entidadUE=enti;
//                ubigeoEntidadUE=new Ubigeo();
//                obtener(1);
//            }
////            obtener();
//            //entidadUE=enti;
//        }
      
        
        blogoentidadi = true;
        bessubentidad=false;
    }
    
//    public Entidad getEnti() {
//            return enti;
//    }
//
//    public void setEnti(Entidad enti) {
//            this.enti = enti;
//    }
//    void obtener(int a){
//        if(a==1){
//            if(enti.getCue_entidad()!=null)cueEntidad=enti.getCue_entidad();
//            if(enti.getSigla()!=null)siglaEntidad=enti.getSigla();
//            if(enti.getEmailInstitucional()!=null)emailentidad=enti.getEmailInstitucional();
//            if(enti.getUrlEntidad()!=null)urlsEntidad=enti.getUrlEntidad();
//            if(enti.getTelefonoEntidad()!=null)telefEntidad=enti.getTelefonoEntidad();
//            if(enti.getDescZona()!=null)desczonaentidad=enti.getDescZona();
//            if(enti.getDepartamento()!=null)ubigeoEntidadUE.setDepartamento(enti.getDepartamento());
//            if(enti.getProvincia()!=null)ubigeoEntidadUE.setProvincia(enti.getProvincia());
//            if(enti.getDistrito()!=null)ubigeoEntidadUE.setDistrito(enti.getDistrito());
//            if(enti.getTitular()!=null)titular = enti.getTitular().getApellidoPaterno()+" "+enti.getTitular().getApellidoMaterno()+", "+enti.getTitular().getNombres();
//            if(enti.getJefeRRHH()!=null)jefeRRHH = enti.getJefeRRHH().getApellidoPaterno()+" "+enti.getJefeRRHH().getApellidoMaterno()+", "+enti.getJefeRRHH().getNombres();
//            if(enti.getJefeOga()!=null)jefeOGA = enti.getJefeOga().getApellidoPaterno()+" "+enti.getJefeOga().getApellidoMaterno()+", "+enti.getJefeOga().getNombres();
//        
//        }
//        else{
//            
//        }
////         if(enti.getEmailInstitucional()!=null)emailentidad=enti.getEmailInstitucional();
////        if(enti.getUrlEntidad()!=null)urlsEntidad=enti.getUrlEntidad();
////        if(enti.getTelefonoEntidad()!=null)telefEntidad=enti.getTelefonoEntidad();
////        if(enti.getDescZona()!=null)desczonaentidad=enti.getDescZona();
////        if(enti.getDepartamento()!=null)ubigeoEntidadUE.setDepartamento(enti.getDepartamento());
////        if(enti.getProvincia()!=null)ubigeoEntidadUE.setProvincia(enti.getProvincia());
////        if(enti.getDistrito()!=null)ubigeoEntidadUE.setDistrito(enti.getDistrito());
////        if(enti.getTitular()!=null)titular = enti.getTitular().getApellidoPaterno()+" "+enti.getTitular().getApellidoMaterno()+", "+enti.getTitular().getNombres();
////        if(enti.getJefeRRHH()!=null)jefeRRHH = enti.getJefeRRHH().getApellidoPaterno()+" "+enti.getJefeRRHH().getApellidoMaterno()+", "+enti.getJefeRRHH().getNombres();
////        if(enti.getJefeOga()!=null)jefeOGA = enti.getJefeOga().getApellidoPaterno()+" "+enti.getJefeOga().getApellidoMaterno()+", "+enti.getJefeOga().getNombres();
//    }
    
    void onSiglaEntidadChanged() {
        siglaEntidad = _request.getParameter("param");
    }
    
    void onCueEntidadChanged() {
        cueEntidad = _request.getParameter("param");
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
    void onSelectedFromSubreset() {
        elemento = 1;
        bessubentidad = false;
        badmentidad = false;
    }

    void onSelectedFromSubcancel() {
        elemento = 2;
        bessubentidad = false;
        badmentidad = false;
    }

    void onSelectedFromReset() {
        elemento = 1;
        bessubentidad = false;
        badmentidad = false;
    }

    void onSelectedFromBusreset() {
            bnivelGobierno=null;
            bsectorGobierno=null;
            borganizacionEstado=null;
            btipoOrganismo=null;
            btipoOrganismo2=null;
            busdenominacion="";
            ubigeobusEntidadUE=null;
            btiposubentidad=null;
            bussubdenominacion="";
            ubigeobusSubEntidadUE=null;
            elemento=3;
    }

    void onSelectedFromBusenviar() {
        bessubentidad = true;
    }

    void onSelectedFromBussubreset() {
            bnivelGobierno=null;
            bsectorGobierno=null;
            borganizacionEstado=null;
            btipoOrganismo=null;
            btipoOrganismo2=null;
            busdenominacion="";
            ubigeobusEntidadUE=null;
            btiposubentidad=null;
            bussubdenominacion="";
            ubigeobusSubEntidadUE=null;
            elemento=3;
    }

    void onSelectedFromCancel() {
        elemento = 2;
        bessubentidad = false;
        badmentidad = false;
    }
    
    void onSelectedFromFile(){
        System.out.println("onSelectedFile");
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {

        if (elemento == 1) {
            return "AMEntidadUEjecutora";
        } else if (elemento == 2) {
            return "AMEntidadUEjecutora";
        } else {
            blogoentidadi = false;
            blogoentidadf = true;
            // Set Path
            //String path = globals.getServletContext().getRealPath("/layout/images") + "/";
//            String nombreArchivo = Encriptacion.encriptaEnMD5(file.getFileName().substring(0, file.getFileName().length() - 4))+file.getFileName().substring(file.getFileName().length() - 4);
//            File nuevo = new File("ArchivosCSV/" + nombreArchivo);
//            file.write(nuevo);
            System.out.println("==========================================================================");
            //System.out.println(nombreArchivo);
            //entidadUE.setLogotipo(nombreArchivo);
            entidadUE.setEstado(true);
            entidadUE.setEsSubEntidad(false);
            entidadUE.setCue_entidad(cueEntidad);
            entidadUE.setSigla(siglaEntidad);
            entidadUE.setEmailInstitucional(emailentidad);
            entidadUE.setDescZona(desczonaentidad);
            entidadUE.setUrlEntidad(urlsEntidad);
            entidadUE.setTelefonoEntidad(telefEntidad);
            entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
            entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
            entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
            entidadUE.setCue_entidad(cueEntidad);
            entidadUE.setSigla(siglaEntidad);
            //nombreArchivo = file.getFileName().substring(0, file.getFileName().length() - 4);
            //nombreArchivo = file.getFileName() ;
            //System.out.println(nombreArchivo);
            //entidadUE.setLogotipo(nombreArchivo);
            session.saveOrUpdate(entidadUE);
            //enti=new Entidad();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
            envelope.setContents("Entidad creada exitosamente");
//            enti=new Entidad();
//            entidadUE=new Entidad();
//            return "AMEntidadUEjecutora";
            
            return new MultiZoneUpdate("nivelOrganizacionSectorZone", nivelOrganizacionSectorZone.getBody()).add("principalZone", principalZone.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariosubbotones() {

        if (elemento == 1) {
            return "AMEntidadUEjecutora";
        } else if (elemento == 2) {
            return "AMEntidadUEjecutora";
        } else {
            subEntidadUE.setEstado(true);
            subEntidadUE.setEsSubEntidad(true);
            subEntidadUE.setDenominacion(denoSubEntidad);
            subEntidadUE.setSigla(siglaSubEntidad);
            subEntidadUE.setRuc(rucSubEntidad);
            subEntidadUE.setCue_entidad(cueSubEntidad);
            subEntidadUE.setTelefonoEntidad(telefSubEntidad);
            subEntidadUE.setEmailInstitucional(emailSubEntidad);
            subEntidadUE.setUrlEntidad(urslSubEntidad);
            subEntidadUE.setDescZona(desczonasubentidad);
            subEntidadUE.setDepartamento(ubigeoSubEntidadUE.getDepartamento());
            subEntidadUE.setProvincia(ubigeoSubEntidadUE.getProvincia());
            subEntidadUE.setDistrito(ubigeoSubEntidadUE.getDistrito());
            subEntidadUE.setEntidad(entio);
            entidadUE.setCue_entidad(cueEntidad);
            entidadUE.setSigla(siglaEntidad);
            // nombreArchivo = file.getFileName();
            //System.out.println(nombreArchivo);
            //entidadUE.setLogotipo(nombreArchivo);
            session.saveOrUpdate(subEntidadUE);
//            enti=new Entidad();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(subEntidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
            envelope.setContents("Sub-Entidad creada exitosamente");
            return new MultiZoneUpdate("subprincipalZone", subprincipalZone.getBody()).add("ubigeoSubEntidadZone", ubigeoSubEntidadZone.getBody()).add("subTitularZone", subTitularZone.getBody()).add("subJefeRRHHZone", subJefeRRHHZone.getBody()).add("subJefeOGAZone", subJefeOGAZone.getBody()).add("mensajesZone", mensajesZone.getBody());
        }

    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioorganizacion() {
        bsector = true;
        return nivelOrganizacionSectorZone.getBody();

    }

//    @CommitAfter
//    Object onSuccessFromFormulariologoentidad() {
//        blogoentidadi=false;
//        blogoentidadf=true;
//        System.out.println("entroeeeeeeee");
//        System.out.println(entidadUE.getLogotipo());
//        // Set Path
//        //String path = globals.getServletContext().getRealPath("/layout/images") + "/";
//        File nuevo = new File("ArchivosCSV/"+file.getFileName());
//        file.write(nuevo);
//        return this;
//        
//    }
    public String getUploadError() {

        return uploadmessage;
    }

    public Object onUploadException(FileUploadException ex) {
        uploadmessage = "Upload exception: " + ex.getMessage() + " You can only upload file less than or equal to 500kb.";

        return this;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariosector() {
        btipoorganismo = true;
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

    @Log
    Object onActionFromEditarSeleccion(Entidad enti1) {
//        enti=new Entidad();
//        entidadUE= new Entidad();
        entidadUE = enti1;        
        if(enti1.getCue_entidad()!=null)cueEntidad=enti1.getCue_entidad();
        if(enti1.getSigla()!=null)siglaEntidad=enti1.getSigla();
        if(enti1.getEmailInstitucional()!=null)emailentidad=enti1.getEmailInstitucional();
        if(enti1.getUrlEntidad()!=null)urlsEntidad=enti1.getUrlEntidad();
        if(enti1.getTelefonoEntidad()!=null)telefEntidad=enti1.getTelefonoEntidad();
        if(enti1.getDescZona()!=null)desczonaentidad=enti1.getDescZona();
        if(enti1.getDepartamento()!=null)ubigeoEntidadUE.setDepartamento(enti1.getDepartamento());
        if(enti1.getProvincia()!=null)ubigeoEntidadUE.setProvincia(enti1.getProvincia());
        if(enti1.getDistrito()!=null)ubigeoEntidadUE.setDistrito(enti1.getDistrito());
        if(enti1.getTitular()!=null)titular = enti1.getTitular().getApellidoPaterno()+" "+enti1.getTitular().getApellidoMaterno()+", "+enti1.getTitular().getNombres();
        if(enti1.getJefeRRHH()!=null)jefeRRHH = enti1.getJefeRRHH().getApellidoPaterno()+" "+enti1.getJefeRRHH().getApellidoMaterno()+", "+enti1.getJefeRRHH().getNombres();
        if(enti1.getJefeOga()!=null)jefeOGA = enti1.getJefeOga().getApellidoPaterno()+" "+enti1.getJefeOga().getApellidoMaterno()+", "+enti1.getJefeOga().getNombres();
   
        cueEntidad=entidadUE.getCue_entidad();
        siglaEntidad=entidadUE.getSigla();
        bsector = true;
        btipoorganismo = true;
        if (_usuario.getRol().getId() == 2) {
            badmentidad = true;
        }

        return new MultiZoneUpdate("nivelOrganizacionSectorZone", nivelOrganizacionSectorZone.getBody()).add("principalZone", principalZone.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody());
//                    .add("logoentidadZone", logoentidadZone.getBody()); 
    }

    @Log
    @CommitAfter
    Object onActionFromEliminarSeleccion(Entidad enti1) {
        enti1.setEstado(false);
        session.saveOrUpdate(enti1);
        return listaentidadZone.getBody();
    }

    //Metodos de Busqueda de Entidades origenes para la subentidad   
    @Log
    @CommitAfter
    Object onSuccessFromformularioentidadorigen() {
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("entiZone", entiZone.getBody());
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
        c.add(Restrictions.eq("estado", true));

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
        if (btipoOrganismo2 != null) {
            System.out.println("------------------ Tipo Organismo " + btipoOrganismo2.getValor());
            c.add(Restrictions.eq("tipoorganismo", btipoOrganismo2.getValor()));
        }
        if (busdenominacion != null) {
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
        if (bussubdenominacion != null) {
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
        /*
        if(bessubentidad){
            c.add(Restrictions.eq("essubentidad", bessubentidad));
        }
        */
        
        return c.list();
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariocombosbusqueda() {
        System.out.println("ellll" + elemento);
        if (elemento == 3) {
            System.out.println("entroeeeeeeee al elemento" + elemento);
            return new MultiZoneUpdate("busquedacombosZone", busquedacombosZone.getBody()).add("ubigeobusEntidadZone", ubigeobusEntidadZone.getBody());

        } else {
            return listaentidadZone.getBody();
        }


    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariosubcombosbusqueda() {
        System.out.println("ellll" + elemento);
        if (elemento == 3) {
            return new MultiZoneUpdate("busquedasubcombosZone", busquedasubcombosZone.getBody()).add("ubigeobusSubEntidadZone", ubigeobusSubEntidadZone.getBody());
        } else {
            return listaentidadZone.getBody();
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @Log
    Object onActionFromEditar(Entidad entix) {
        entio = entix;
        entidad_origen = entio.getDenominacion();

        subEntidadUE.setEntidad(entio);

        subEntidadUE.setTipoVia(entio.getTipoVia());
        subEntidadUE.setTipoZona(entio.getTipoZona());
        subEntidadUE.setDireccion(entio.getDireccion());
        subEntidadUE.setDescZona(entio.getDescZona());
        ubigeoSubEntidadUE.setDepartamento(entio.getDepartamento());
        ubigeoSubEntidadUE.setProvincia(entio.getProvincia());
        ubigeoSubEntidadUE.setDistrito(entio.getDistrito());
        
        subEntidadUE.setEsSubEntidad(true);
        mostrar = false;
        return new MultiZoneUpdate("EOrigenZone", EOrigenZone.getBody()).add("subprincipalZone", subprincipalZone.getBody()).add("ubigeoSubEntidadZone", ubigeoSubEntidadZone.getBody());
    }

    //Metodos de Busqueda de Trabajadores
    @Log
    @CommitAfter
    Object onSuccessFromformulariosubtitular() {
        btitular = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariosubjeferrhh() {
        bjefeRRHH = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariosubjefeoga() {
        bjefeOGA = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariotitular() {
        btitulari = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariojeferrhh() {
        bjefeRRHHi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariojefeoga() {
        bjefeOGAi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioTrabajador() {
        mostrar = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @Log
    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        System.out.println("nombress: "+ nombreTrabajador);
        c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        return c.list();
    }

    @Log
    Object onActionFromeditarsubTitular(Trabajador traba) {
        //subtitulart = traba;
        subtitular = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        subEntidadUE.setTitular(traba);
        btitular = false;
        mostrar = false;
        return subTitularZone.getBody();
    }

    @Log
    Object onActionFromeditarsubJefeRRHH(Trabajador traba) {
        //subjeferrhht = traba;
        subjefeRRHH = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        subEntidadUE.setJefeRRHH(traba);
        bjefeRRHH = false;
        mostrar = false;
        return subJefeRRHHZone.getBody();
    }

    @Log
    Object onActionFromeditarsubJefeOGA(Trabajador traba) {
        //subjefeogat = traba;
        subjefeOGA = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        subEntidadUE.setJefeOga(traba);
        bjefeOGA = false;
        mostrar = false;
        return subJefeOGAZone.getBody();
    }

    @Log
    Object onActionFromeditarTitular(Trabajador traba) {
        //titulart = traba;
        titular = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        entidadUE.setTitular(traba);
        btitulari = false;
        mostrar = false;
        return TitularZone.getBody();
    }

    @Log
    Object onActionFromeditarJefeRRHH(Trabajador traba) {
        //jeferrhht = traba;
        jefeRRHH = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        entidadUE.setJefeRRHH(traba);
        bjefeRRHHi = false;
        mostrar = false;
        return JefeRRHHZone.getBody();
    }

    @Log
    Object onActionFromeditarJefeOGA(Trabajador traba) {
        //jefeogat = traba;
        jefeOGA = traba.getApellidoPaterno()+" "+traba.getApellidoMaterno()+", "+traba.getNombres();
        entidadUE.setJefeOga(traba);
        bjefeOGAi = false;
        mostrar = false;
        return JefeOGAZone.getBody();
    }
    
    void onEmailEntChanged() {
        emailentidad = _request.getParameter("param");
    }
    
    void onUrlEntChanged() {
        urlsEntidad = _request.getParameter("param");
    }
    
    void onTelfEntChanged() {
        telefEntidad = _request.getParameter("param");
    }
    
    void onDescZonaEntChanged() {
        desczonaentidad = _request.getParameter("param");
    }
    
    void onDenoSubEntidadChanged() {
        denoSubEntidad = _request.getParameter("param");
    }
    
    void onSiglaSubEntidadChanged() {
        siglaSubEntidad = _request.getParameter("param");
    }
    
    void onRucSubEntidadChanged() {
        rucSubEntidad = _request.getParameter("param");
    }
    
    void onCueSubEntidadChanged() {
        cueSubEntidad = _request.getParameter("param");
    }
    
    void onEmailSubEntidadChanged() {
        emailSubEntidad = _request.getParameter("param");
    }
    
    void onUrslSubEntidadChanged() {
        urslSubEntidad = _request.getParameter("param");
    }
    
    void ontelefSubEntidadChanged() {
        telefSubEntidad = _request.getParameter("param");
    }
    
    void desczonasubentidadChanged() {
        desczonasubentidad = _request.getParameter("param");
    }
    
//    void onSiglaEntidadChanged() {
//        siglaEntidad = _request.getParameter("param");
//    }
//    
//    void onCueEntidadChanged() {
//        cueEntidad = _request.getParameter("param");
//    }
}
