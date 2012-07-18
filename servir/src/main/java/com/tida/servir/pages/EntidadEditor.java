package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
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
import org.apache.tapestry5.services.Request;

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
public class EntidadEditor extends GeneralPage {

    @Inject
    private Session session;
    @Inject
    private Request _request;
    @Property
    @Persist
    private Entidad entidadUE;
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
    private LkBusquedaTrabajador titulart;
    @Property
    private LkBusquedaTrabajador jeferrhht;
    @Property
    private LkBusquedaTrabajador jefeogat;
    
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
    private Zone TitularZone;
    @InjectComponent
    private Zone JefeRRHHZone;
    @InjectComponent
    private Zone JefeOGAZone;
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
    

    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
        entidadUE = new Entidad();
        entio = new Entidad();

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



    //reset del formulario (borrar objeto)
    void onSelectedFromSubreset() {
        elemento = 1;

        badmentidad = false;
    }

    void onSelectedFromSubcancel() {
        elemento = 2;

        badmentidad = false;
    }

    void onSelectedFromReset() {
        elemento = 1;

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
            elemento=3;
    }



    void onSelectedFromBussubreset() {
            bnivelGobierno=null;
            bsectorGobierno=null;
            borganizacionEstado=null;
            btipoOrganismo=null;
            btipoOrganismo2=null;
            busdenominacion="";
            ubigeobusEntidadUE=null;

            elemento=3;
    }

    void onSelectedFromCancel() {
        elemento = 2;

        badmentidad = false;
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
            entidadUE.setEmailInstitucional(emailentidad);
            entidadUE.setDescZona(desczonaentidad);
            entidadUE.setUrlEntidad(urlsEntidad);
            entidadUE.setTelefonoEntidad(telefEntidad);
            entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
            entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
            entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
            //nombreArchivo = file.getFileName().substring(0, file.getFileName().length() - 4);
            //nombreArchivo = file.getFileName() ;
            //System.out.println(nombreArchivo);
            //entidadUE.setLogotipo(nombreArchivo);
            session.saveOrUpdate(entidadUE);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
            envelope.setContents("Entidad creada exitosamente");
            return new MultiZoneUpdate("nivelOrganizacionSectorZone", nivelOrganizacionSectorZone.getBody()).add("principalZone", principalZone.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()) //                    .add("logoentidadZone", logoentidadZone.getBody())
                    .add("mensajesZone", mensajesZone.getBody());
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
    

    @Log
    @CommitAfter
    Object onSuccessFromFormulariosector() {
        btipoorganismo = true;
        return nivelOrganizacionSectorZone.getBody();

    }

    EntidadEditor onActionFromToggle_filtros() {
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
        entidadUE = enti1;
        ubigeoEntidadUE.setDepartamento(entidadUE.getDepartamento());
        ubigeoEntidadUE.setProvincia(entidadUE.getProvincia());
        ubigeoEntidadUE.setDistrito(entidadUE.getDistrito());
        titular = entidadUE.getTitular().getApellidoPaterno();
        jefeRRHH = entidadUE.getJefeRRHH().getApellidoPaterno();
        jefeOGA = entidadUE.getJefeOga().getApellidoPaterno();
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
    Object onSuccessFromFormulariobusqueda() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

   
    //Metodos de Busqueda de Trabajadores
   
    

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
   
}
