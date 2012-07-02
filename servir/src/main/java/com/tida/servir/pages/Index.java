package com.tida.servir.pages;


import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioTrabajador;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
import helpers.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Login de Usuarios
 */
public class Index {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario usuario;
    @Property
    @SessionState
    private Entidad eue;
    /*
     * @Property @SessionState private UsuarioAcceso usuarioAcceso;
     *
     */
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Property
    @Persist
    private ConfiguracionAcceso configuracionAcceso;
    @Property
    private boolean administrador = false;
    @Property
    private String clave;
    @Property
    private String login;
    @InjectComponent
    private Zone organismosZone;
    @Inject
    private Request request;
    @Component(id = "formulariologin")
    private Form formulariologin;
    @Property
    /*
     * @Component(id = "formularioorganismos") private Form
     * formularioOrganismos;
     *
     */
    @Component(id = "clave")
    private PasswordField passwordField;
    @Inject
    private ComponentResources _resources;
    @InjectPage
    private CambiarClave cambioClave;
    @Inject
    private ComponentClassResolver componentClassResolver;
    @Persist
    private GenericSelectModel<Entidad> _beanOrganismos;
    @Inject
    private PropertyAccess _access;
    @Inject
    private RequestGlobals requestGlobal;
    @Inject
    private Messages mensajes;
    @Inject
    private Context context;

    public String getOlvidoClave() {
        return mensajes.get("olvidoClave");
    }

    public String getNuevoUsuario() {
        return mensajes.get("nuevoUsuario");
    }

    public boolean getMuestroSubmit() {
        return !administrador;
    }

    public GenericSelectModel<Entidad> getBeanOrganismos() {
        List<Entidad> list;
        Criteria c;
        //System.out.println("-------------------------------------entrada bean organismo");
        c = session.createCriteria(Entidad.class);
        c.add(Restrictions.ne("estado", Entidad.ESTADO_BAJA));

        list = c.list();

        _beanOrganismos = new GenericSelectModel<Entidad>(list, Entidad.class, "denominacion", "id", _access);

        return _beanOrganismos;
    }

    void onPrepareFromFormulariologin() {
        //  usuario = null; // Para que haga un logout
    }

    @CommitAfter
    Object onSuccessFromFormulariologin() {
        Logger logger = new Logger();
        configuracionAcceso = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);

        Query query = session.getNamedQuery("UsuarioTrabajador.findByNrodocumento");
        query.setParameter("nrodocumento", login);
        List c = query.list();

        if (c.isEmpty()) {
            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERNOEXIST, getIp_Adress());
            formulariologin.recordError("Usuario no existe. Contacte a un administrador");
            return this;
        }

        usuarioTrabajador = (UsuarioTrabajador) c.get(0);

        if (usuarioTrabajador.getEstado() == 2) { // Si esta inactivo el usuario
            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERLOCKED, getIp_Adress());
            formulariologin.recordError("Usuario Bloqueado. Contacte a un administrador");
            return this;
        }

        if (usuarioTrabajador.getEstado() == 0) { // Si esta inactivo el usuario
            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERLOW, getIp_Adress());
            formulariologin.recordError("Usuario dado de baja. Contacte a un administrador");
            return this;
        }

        Criteria cq = session.createCriteria(Usuario.class);
        cq.add(Restrictions.eq("id", usuarioTrabajador.getId()));
        usuario = (Usuario) cq.list().get(0); // Guardamos la sesión

        if (!usuarioTrabajador.getMd5clave().equals(Encriptacion.encriptaEnMD5(clave))) {
            usuario.setIntentos_fallidos(usuario.getIntentos_fallidos() + 1);
            if (usuario.getIntentos_fallidos() >= configuracionAcceso.getIntentos_bloqueo()) {
                usuario.setEstado(2);
                logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERLOCKED, getIp_Adress());
                formulariologin.recordError("Demasiados Intentos Fallidos. el Usuario ha sido bloqueado.");
            } else {
                logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_PASSWORDFAIL, getIp_Adress());
                formulariologin.recordError("Clave incorrecta.");
            }
            session.saveOrUpdate(usuario);
            return this;
        }

        if (usuario.getUltimo_cambio_clave() != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(usuario.getUltimo_cambio_clave());
            c1.add(Calendar.DATE, configuracionAcceso.getDuracion_clave().intValue());
            if (c1.getTime().before(new Date())) {
                logger.loguearAcceso(session, usuario, Logger.LOGIN_STATUS_OK, Logger.LOGIN_MOTIVO_RECHAZO_PASSWORDEXPIRED, getIp_Adress());
                cambioClave.flagCambioForzado("Su clave ha expirado");
                return cambioClave;
            }
        } else {
            logger.loguearAcceso(session, usuario, Logger.LOGIN_STATUS_OK, Logger.LOGIN_MOTIVO_RECHAZO_PASSWORFIRST, getIp_Adress());
            cambioClave.flagCambioForzado("Usted nunca ha cambiado su clave, debe hacerlo ahora.");
            return cambioClave;
        }

        usuario.setIntentos_fallidos(0L);
        session.saveOrUpdate(usuario);

        /*
         * if (Helpers.esMultiOrganismo(usuario)) { administrador = true;
         *
         * // Seleccionar el organismo return organismosZone.getBody(); } else
         * { eue = usuario.getEntidadUE(); return
         * Permisos.paginaInicial(usuario); }
         *
         */
        eue = usuarioTrabajador.getEntidad();

        logger.loguearAcceso(session, usuario, Logger.LOGIN_STATUS_OK, Logger.LOGIN_OK, getIp_Adress());
        return Permisos.paginaInicial(usuario);
    }

    private void clearCacheData() {
        org.apache.tapestry5.services.Session sessionService = request.getSession(false);
        if (sessionService != null) {
            List<String> names = sessionService.getAttributeNames();
            List<String> toKeep = Arrays.asList(new String[]{
                        "sso:" + Entidad.class.getName(),
                        "sso:" + Usuario.class.getName()
                    });
            for (String name : names) {
                if (!toKeep.contains(name)) {
                    sessionService.setAttribute(name, null);
                }
            }
        }
    }

    /*
     * Object onSuccessFromFormularioOrganismos() { // el match del eue ya lo
     * hace desde el tml return Permisos.paginaInicial(usuario); }
     */
    void onActivate() {
        clearCacheData();
        /*
         *
         * List<String> paginas; paginas.add("ABMUsuario");
         * paginas.add("ABMDatoAuxiliar"); paginas.add("AMOrganismoInformante");
         * paginas.add("AMUnidadEjecutora"); paginas.add("TrabajadorNuevo");
         * paginas.add("AMOrgano"); paginas.add("AMUnidadOrganica");
         * paginas.add("ABMCargos"); paginas.add("Busqueda");
         * paginas.add("CambiarClave");
         *
         * paginas.add("ABMConceptosRemunerativos");
         * paginas.add("AsignarNuevoCargo");
         * paginas.add("CargosAsignadosModificar"); paginas.add("CreateCargo");
         * paginas.add("DesasignarCargo"); paginas.add("TrabajadorModificar");
         * paginas.add("TrabajadorNuevo");
         * paginas.add("TransferenciaMasivaTrabajadores"); paginas.add("batch");
         *
         * paginas = componentClassResolver.getPageNames();
         *
         *
         * for(String page: paginas) {
         *
         * System.out.println("--------------------Pagina: "+ page); Page clase
         * = pl.loadPage(page, Locale.ENGLISH);
         * System.out.println("--------------------Clase Pagina cargada: "+
         * clase); clase.discardPersistentFieldChanges();
         *
         * }
         * // Todos pueden cambiar su clave
         *
         */

    }

    public String getIp_Adress() {
        //String ip_Adress = "";
        String ip_Adress = requestGlobal.getHTTPServletRequest().getHeader("X-Forwarded-For"); 
        if (ip_Adress != null) 
            return ip_Adress; 
        else 
            return requestGlobal.getHTTPServletRequest().getRemoteAddr();
        /*
        try {
            ip_Adress = requestGlobal.getHTTPServletRequest().getRemoteAddr();
        } catch (Exception e) {
            //error
        }
        */
        //return ip_Adress;
    }

    StreamResponse onActionFromReturnStreamResponse() {
        return new StreamResponse() {

            InputStream inputStream;

            @Override
            public void prepareResponse(Response response) {
                String reportesPath = "";
                try {
                    reportesPath = context.getRealFile("/").getCanonicalPath();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
                }

                File fileADescargar = new File(reportesPath + "/Manual del RNSC.pdf");

                try {
                    inputStream = new FileInputStream(fileADescargar);
                } catch (FileNotFoundException ex) {
//                                    Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    response.setHeader("Content-Type", "application/x-pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileADescargar.getName());
                    response.setHeader("Content-Length", "" + inputStream.available());
                } catch (IOException e) {
                    // Ignore the exception in this simple example.
                }
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public InputStream getStream() throws IOException {
                return inputStream;
            }
        };
    }
}
