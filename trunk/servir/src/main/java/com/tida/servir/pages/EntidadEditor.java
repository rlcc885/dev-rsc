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
 * Clase que maneja las unidades ejecutoras
 *
 * @author LFL
 */
public class EntidadEditor extends GeneralPage {

    @Inject
    private Session session;
    @Inject
    private Request _request;
    //@Property
    //@Persist
    //private Entidad entidadUE;
    @Property
    @SessionState
    private Entidad entidadUE;
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
    private Zone nivelOrganizacionSectorZone;
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
    private String siglaEntidad;
    @Persist
    @Property
    private String cueEntidad;
    @Property
    @Persist
    private boolean mostrar;
    
    @Property
    @Persist
    private UploadedFile file;
    @Component(id = "formulariologoentidad")
    private Form formulariologoentidad;

    //Inicio de lac carga de la pagina
    @SetupRender
    private void inicio() {
//        entidadUE = new Entidad();
        ubigeoEntidadUE = new Ubigeo();
//        entidadUE = entidadUE;
        if (entidadUE.getCue_entidad() != null) {
            cueEntidad = entidadUE.getCue_entidad();
        }
        if (entidadUE.getSigla() != null) {
            siglaEntidad = entidadUE.getSigla();
        }
        if (entidadUE.getEmailInstitucional() != null) {
            emailentidad = entidadUE.getEmailInstitucional();
        }
        if (entidadUE.getUrlEntidad() != null) {
            urlsEntidad = entidadUE.getUrlEntidad();
        }
        if (entidadUE.getTelefonoEntidad() != null) {
            telefEntidad = entidadUE.getTelefonoEntidad();
        }
        if (entidadUE.getDescZona() != null) {
            desczonaentidad = entidadUE.getDescZona();
        }
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
    }

    @CommitAfter
    Object onSuccessFromFormulariologoentidad() {
        File copied;
        if (file == null){
            formulariologoentidad.recordError("Seleccione imagen a subir.");
            return this;
        }
        String path = globals.getServletContext().getRealPath("/") + "images/logotipo/";
        String nombreArchivo = Encriptacion.encriptaEnMD5(entidadUE.getId().toString())+file.getFileName().substring(file.getFileName().length() - 4);
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

    void onSelectedFromReset() {
        elemento = 1;
        badmentidad = false;
    }

    void onSelectedFromCancel() {
        elemento = 2;
        badmentidad = false;
    }
    
    void onSelectedFromCancelBuscador() {
//        btitulari = false;
//        bjefeOGAi = false;
//        bjefeRRHHi = false;
//        System.out.println("onSelectedFromCancelBuscador");
    }

    @CommitAfter
    Object onSuccessFromFormulariobotones() {

        if (elemento == 1) {
            return "EntidadEditor";
        } else if (elemento == 2) {
            return "Alerta";
        } else {
            entidadUE.setCue_entidad(cueEntidad);
            entidadUE.setSigla(siglaEntidad);
            entidadUE.setEmailInstitucional(emailentidad);
            entidadUE.setDescZona(desczonaentidad);
            entidadUE.setUrlEntidad(urlsEntidad);
            entidadUE.setTelefonoEntidad(telefEntidad);
            entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
            entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
            entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
            //entidadUE=entidadUE;
            session.saveOrUpdate(entidadUE);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
            envelope.setContents("Entidad modificada exitosamente");
            return new MultiZoneUpdate("nivelOrganizacionSectorZone", nivelOrganizacionSectorZone.getBody()).add("principalZone", principalZone.getBody()).add("ubigeoEntidadZone", ubigeoEntidadZone.getBody()).add("TitularZone", TitularZone.getBody()).add("JefeRRHHZone", JefeRRHHZone.getBody()).add("JefeOGAZone", JefeOGAZone.getBody()).add("mensajesZone", mensajesZone.getBody());
        }
    }

    @CommitAfter
    Object onSuccessFromFormularioorganizacion() {
        bsector = true;
        return nivelOrganizacionSectorZone.getBody();
    }

    @CommitAfter
    Object onSuccessFromFormulariosector() {
        btipoorganismo = true;
        return nivelOrganizacionSectorZone.getBody();
    }

    public void onActivate(Entidad eue) {
        //entidadUE = eue;
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
        if (bjefeOGAi || bjefeRRHHi || btitulari){
        mostrar = true;
        }else{
            mostrar = false;
        }
        System.out.println("onSuccessFromFormularioTrabajador");
        return new MultiZoneUpdate("busZone2", busZone2.getBody()).add("trabajadorZone", trabajadorZone.getBody());
    }

    public List<LkBusquedaTrabajador> getTrabajadores() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        System.out.println("nombress: " + nombreTrabajador);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
    public List<LkBusquedaTrabajador> getTrabajadores1() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        System.out.println("nombres1: " + nombreTrabajador);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
        public List<LkBusquedaTrabajador> getTrabajadores2() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        System.out.println("nombres2: " + nombreTrabajador);
        if (nombreTrabajador != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombretrabajador", nombreTrabajador + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombretrabajador", nombreTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        return c.list();
    }
    Object onActionFromeditarTitular(Trabajador traba) {
        //titulart = traba;
        titular = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
        entidadUE.setTitular(traba);
        btitulari = false;
        mostrar = false;
        return TitularZone.getBody();
    }

    Object onActionFromeditarJefeRRHH(Trabajador traba) {
        //jeferrhht = traba;
        jefeRRHH = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
        entidadUE.setJefeRRHH(traba);
        bjefeRRHHi = false;
        mostrar = false;
        return JefeRRHHZone.getBody();
    }

    Object onActionFromeditarJefeOGA(Trabajador traba) {
        //jefeogat = traba;
        jefeOGA = traba.getApellidoPaterno() + " " + traba.getApellidoMaterno() + ", " + traba.getNombres();
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

    void onSiglaEntidadChanged() {
        siglaEntidad = _request.getParameter("param");
    }

    void onCueEntidadChanged() {
        cueEntidad = _request.getParameter("param");
    }
}
