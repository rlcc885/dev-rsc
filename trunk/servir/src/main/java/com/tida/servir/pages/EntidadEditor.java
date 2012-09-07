package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
import helpers.Helpers;
import helpers.Logger;
import java.io.File;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades ejecutoras
 *
 * @author LFL
 */
public class EntidadEditor extends GeneralPage {

    @Inject
    private Session session;
    @SessionState
    private Usuario loggedUser;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Inject
    private Request _request;
    @Property
    @SessionState
    private Entidad entidadSession;
    @Property
    @Persist
    private Entidad entidadUE;
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    @InjectComponent
    private Zone mensajesZone;
    @Component(id = "formularioentidad")
    private Form formularioentidad;
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
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    @Property
    private Zone principalZone;
    @InjectComponent
    @Property
    private Zone botonesZone;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private ApplicationGlobals globals;
    @Property
    @Persist
    private boolean mostrar;
    @Property
    private UploadedFile file;
    @Component(id = "formulariologoentidad")
    private Form formulariologoentidad;
    @Property
    private boolean bMuestraSectorEdicion;
    
    @Persist
    @Property
    private String entidad_origen;
    
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Inject
    private ComponentResources _resources;
    @Persist
    private UsuarioAcceso usua;

    public void onActivate() {
        entidadUE = entidadSession;
    }

    public void onActivate(Entidad eue) {
        entidadUE = eue;
    }
    
    /*
     * inicio de la carga de pagina
     * veditar : empieza en true, es utilizado en el .tml para controlar el acceso de edicion a los campos.
     */

    // inicio de pagina
    @SetupRender
    private void inicio() {
        veditar=true;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login",usuarioTrabajador.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();        
        if(result.isEmpty()){
            System.out.println(String.valueOf("Vacio:"));
        }
        else{
            usua = (UsuarioAcceso) result.get(0);
            if(usua.getAccesoupdate()==1){
                veditar=false;
                vbotones=true;
            }
            if(usua.getAccesodelete()==1){
                veliminar=true; 
            }
            if(usua.getAccesoreport()==1){
                vformulario=true;
                vbotones=true; 
            }
        }
        
        entidad_origen="";        
        
        File copied;
        copied = new File(globals.getServletContext().getRealPath("/") + "images/logotipo/"+entidadUE.getLogotipo());
        if (!copied.exists()) {
            entidadUE.setLogotipo(null);
        }
        if (entidadUE.getOrganizacionEstado() != null) {
            if (entidadUE.getOrganizacionEstado().getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        } else {
            bMuestraSectorEdicion = false;
        }
        ubigeoEntidadUE = new Ubigeo();
        if (entidadUE.getDepartamento() != null) {
            ubigeoEntidadUE.setDepartamento(entidadUE.getDepartamento());
        }
        if (entidadUE.getProvincia() != null) {
            ubigeoEntidadUE.setProvincia(entidadUE.getProvincia());
        }
        if (entidadUE.getDistrito() != null) {
            ubigeoEntidadUE.setDistrito(entidadUE.getDistrito());
        }
        if (entidadUE.getTitular() != null) {
            titular = entidadUE.getTitular().getApellidoPaterno() + " " + entidadUE.getTitular().getApellidoMaterno() + ", " + entidadUE.getTitular().getNombres();
        }
        if (entidadUE.getJefeRRHH() != null) {
            jefeRRHH = entidadUE.getJefeRRHH().getApellidoPaterno() + " " + entidadUE.getJefeRRHH().getApellidoMaterno() + ", " + entidadUE.getJefeRRHH().getNombres();
        }
        if (entidadUE.getJefeOga() != null) {
            jefeOGA = entidadUE.getJefeOga().getApellidoPaterno() + " " + entidadUE.getJefeOga().getApellidoMaterno() + ", " + entidadUE.getJefeOga().getNombres();
        }
        
        if (entidadUE.getEsSubEntidad()==true)
        {
         entidad_origen = entidadUE.getEntidad().getDenominacion();            
        }
        else{entidad_origen="";}

    }

    @CommitAfter
    Object onSuccessFromFormulariologoentidad() {
        File copied;
        if (file == null) {
            formulariologoentidad.recordError("Seleccione imagen a subir.");
            return this;
        }
        String path = globals.getServletContext().getRealPath("/") + "images/logotipo/";
        String nombreArchivo = Encriptacion.encriptaEnMD5( String.valueOf(entidadUE.getId()) ) + file.getFileName().substring(file.getFileName().length() - 4);
        File nuevo = new File(path + nombreArchivo);
        copied = new File(path);
        if (!copied.exists()) {
            copied.mkdirs();
        }
        file.write(nuevo);
        entidadUE.setLogotipo(nombreArchivo);
        session.saveOrUpdate(entidadUE);
        return this;
    }

    
    // cargar combos
    //para obtener datatos del Nivel Gobierno
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Sector Gobierno
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Organismo
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Via
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Zona
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    @Log
    //para obtener datatos del Tipo Entidad
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    
    void onSelectedFromCancelBuscador() {
        btitulari = false;
        bjefeOGAi = false;
        bjefeRRHHi = false;
        System.out.println("onSelectedFromCancelBuscador");
    }

    // formulario principal
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
        entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
        entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
        session.saveOrUpdate(entidadUE);
        envelope.setContents("Entidad modificada exitosamente");
        return new MultiZoneUpdate("principalZone", principalZone.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()).add("mensajesZone", mensajesZone.getBody());
    }

    //Metodos de Busqueda de Trabajadores
    @CommitAfter
    Object onSuccessFromformulariotitular() {
        btitulari = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromformulariojeferrhh() {
        bjefeRRHHi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromformulariojefeoga() {
        bjefeOGAi = true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    @CommitAfter
    Object onSuccessFromFormularioTrabajador() {
        if (bjefeOGAi || bjefeRRHHi || btitulari) {
            mostrar = true;
        } else {
            mostrar = false;
        }
        System.out.println("onSuccessFromFormularioTrabajador");
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class); 
        c.add(Restrictions.eq("entidad_id", entidadUE.getId()));
        System.out.println("nombress: " + nombreTrabajador);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }

    // accion de boton seleccionado
    Object onActionFromeditarTitular(Trabajador traba) {
        titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
        entidadUE.setTitular(traba);
        btitulari = false;
        mostrar = false;
        return TitularZone.getBody();
    }

    Object onActionFromeditarJefeRRHH(Trabajador traba) {
        jefeRRHH = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
        entidadUE.setJefeRRHH(traba);
        bjefeRRHHi = false;
        mostrar = false;
        return JefeRRHHZone.getBody();
    }

    Object onActionFromeditarJefeOGA(Trabajador traba) {
        jefeOGA = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
        entidadUE.setJefeOga(traba);
        bjefeOGAi = false;
        mostrar = false;
        return JefeOGAZone.getBody();
    }

    // eventos de cambio de valor en los campos
    void onSiglaChanged() {
        entidadUE.setSigla(_request.getParameter("param"));
    }

    void onDireccionEntChanged() {
        entidadUE.setDescvia(_request.getParameter("param"));
    }

    void onDescZonaEntChanged() {
        entidadUE.setDescZona(_request.getParameter("param"));
    }

    void onEmailInstitucionalChanged() {
        entidadUE.setEmailInstitucional(_request.getParameter("param"));
    }

    void onUrlEntidadChanged() {
        entidadUE.setUrlEntidad(_request.getParameter("param"));
    }

    void onTelefonoEntidadChanged() {
        entidadUE.setTelefonoEntidad(_request.getParameter("param"));
    }
}
