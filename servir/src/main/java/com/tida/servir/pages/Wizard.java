package com.tida.servir.pages;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;

/**
 * Login de Usuarios
 */
public class Wizard {

    @Inject
    private Session session;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request request;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    // Entidad
    @Property
    @Persist
    private Entidad entidadUE;
    @Property
    @Persist
    private Ubigeo ubigeoEntidadUE;
    // Usuario
    @Property
    @Persist
    private ConfiguracionAcceso configuracionAcceso;
    @Property
    @Persist
    private UsuarioTrabajador usuariotrabajadoredit;
    @Property
    @Persist
    private DatoAuxiliar documentoIdentidadEdit;
//    @Component(id = "formulariologin")
//    private Form formulariologin;
    @Inject
    private Messages mensajes;
    // Zonas
    @InjectComponent
    @Property
    private Zone wizardZone;
    @InjectComponent
    @Property
    private Zone zoneOrganizacion;
    @InjectComponent
    @Property
    private Zone zoneDatos;
    // Variables
    @Persist
    @Property
    private Integer paso;
    @Persist
    @Property
    private Boolean paso01, paso02, paso03, paso04, bMuestraSectorEdicion;

    @Log
    void onActivate() {
    }

    @Log
    void setupRender() {
        paso = 1;
        paso01 = true;
        paso02 = false;
        paso03 = false;
        paso04 = false;
        bMuestraSectorEdicion = false;
        entidadUE = new Entidad();
        entidadUE.setEsSubEntidad(false);
        ubigeoEntidadUE = new Ubigeo();
        usuariotrabajadoredit = new UsuarioTrabajador();
    }

    @Log
    //para obtener datatos del Nivel Gobierno
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstadoEdicion() {
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();
        if (entidadUE.getNivelGobierno() != null) {
            list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", "NIVELGOBIERNO", entidadUE.getNivelGobierno().getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Sector Gobierno
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Organismo
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Via
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    //para obtener datatos del Tipo Zona
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentoIdentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    void onSuccessFrominputEntidad() {
        paso = 2;
        paso01 = false;
        paso02 = true;
        if (request.isXHR()) {
            ajaxResponseRenderer.addRender("wizardZone", wizardZone.getBody());
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFrominputUsuario() {
        // Grabando Entidad
        entidadUE.setEstado(true);
        entidadUE.setProc_Batch(true);
        session.save(entidadUE);
        // Grabando Usuario
        Usuario usuario = new Usuario();
        usuario.setApellidoMaterno(usuariotrabajadoredit.getApellidomaterno());
        usuario.setApellidoPaterno(usuariotrabajadoredit.getApellidopaterno());
        usuario.setDocumentoId(documentoIdentidadEdit.getId());
        usuario.setEmaillaboral(usuariotrabajadoredit.getEmaillaboral());
        usuario.setEntidad(entidadUE);
        usuario.setEstado(1);
        usuario.setFecha_creacion(new Date());
        usuario.setIntentos_fallidos(0L);
        usuario.setLogin(usuariotrabajadoredit.getLogin());
        usuario.setMd5Clave(Encriptacion.encriptaEnMD5(usuariotrabajadoredit.getMd5clave()));
        usuario.setNombres(usuariotrabajadoredit.getNombres());
        usuario.setNumeroDocumento(usuariotrabajadoredit.getNrodocumento());
        usuario.setObservacion(usuariotrabajadoredit.getObservacion());
        usuario.setRolid(2L);
        usuario.setTelefono(usuariotrabajadoredit.getTelefono());
        usuario.setUltimo_cambio_clave(new Date());
        session.save(usuario);
        // Añade Perfil Administrador de Entidad al Usuario Creado
        // Existen 8 Perfiles creados en la BD por defecto. No deben ser borrados.
        Perfilusuario permiso = new Perfilusuario();
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK();
        perfilusuariopk.setUsuarioId(usuario.getId());
        perfilusuariopk.setPerfilId(2L);
        permiso.setPerfilusuarioPK(perfilusuariopk);
        session.save(permiso);
        return "Index";
    }

//    @Log
//    @CommitAfter
//    Object onSuccessFromFormulariologin() {
//        Criteria criteriobusqueda;
//        Logger logger = new Logger();
//        configuracionAcceso = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
//
//        Query query = session.getNamedQuery("UsuarioTrabajador.findByLogin");
//        query.setParameter("login", "");
//        List c = query.list();
//
//        if (c.isEmpty()) {
////            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERNOEXIST, getIp_Adress());
////            formulariologin.recordError("Usuario no existe. Contacte a un administrador");
//            return wizardZone.getBody();
//        }
//        return null;
//    }
    @Log
    void onValueChangedFromNivelGobierno(DatoAuxiliar dato) {
        if (dato == null) {
            entidadUE.setOrganizacionEstado(null);
        }
        if (request.isXHR()) {
            ajaxResponseRenderer.addRender("zoneOrganizacion", zoneOrganizacion.getBody());
        }
    }

    @Log
    void onValueChangedFromOrganizacionEdicion(DatoAuxiliar dato) {
        if (dato != null) {
            if (dato.getCodigo() == 5) {
                bMuestraSectorEdicion = true;
            } else {
                bMuestraSectorEdicion = false;
            }
        }
        if (request.isXHR()) {
            ajaxResponseRenderer.addRender("zoneDatos", zoneDatos.getBody());
        }
    }
}
