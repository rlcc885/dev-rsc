package com.tida.servir.pages;

import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
import helpers.Logger;
import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.*;
import org.hibernate.Criteria;
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
    @SessionState(create = false)
    private Entidad_BK eue;
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
    private GenericSelectModel<Entidad_BK> _beanOrganismos;
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

    public GenericSelectModel<Entidad_BK> getBeanOrganismos() {
        List<Entidad_BK> list;
        Criteria c;
        //System.out.println("-------------------------------------entrada bean organismo");
        c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));

        list = c.list();

        _beanOrganismos = new GenericSelectModel<Entidad_BK>(list, Entidad_BK.class, "denominacion", "id", _access);

        return _beanOrganismos;
    }

    void onPrepareFromFormulariologin() {
        //  usuario = null; // Para que haga un logout
    }

    @CommitAfter
    Object onSuccessFromFormulariologin() {
        Criteria c = session.createCriteria(Usuario.class);
        //Encriptacion crypt = Encriptacion();
        c.add(Restrictions.eq("login", login));
        c.add(Restrictions.eq("md5Clave", Encriptacion.encriptaEnMD5(clave)));
        c.add(Restrictions.eq("estado", Usuario.ESTADOACTIVO));

        //System.out.println(login + " ------------------------------------------------ " + clave + " c? " + c.list().size());

        if (c.list().isEmpty()) {
            c = session.createCriteria(Usuario.class);
            c.add(Restrictions.eq("login", login));
            c.add(Restrictions.eq("md5Clave", clave));
            c.add(Restrictions.eq("estado", Usuario.ESTADOBLOQUEADO));
            if (!c.list().isEmpty()) {
                formulariologin.recordError("Usuario Bloqueado. Contacte a un administrador");
                Logger logger = new Logger();
                logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_BLOQUEADO, getIp_Adress());

            } else {

                formulariologin.recordError("Usuario o Clave incorrecto/a.");

                // incrementar el contador del bloqueo si puso mal la clave
                // (si es que el usuario existe)
                Criteria cu = session.createCriteria(Usuario.class);
                cu.add(Restrictions.eq("login", login));
                if (cu.list().size() == 1) {

                    Usuario u = (Usuario) cu.list().get(0);
                    if (u.getIntentos_fallidos() != null) {
                        u.setIntentos_fallidos(u.getIntentos_fallidos() + 1);
                    } else {
                        u.setIntentos_fallidos(1L);
                    }
                    session.saveOrUpdate(u);
                }
                Logger logger = new Logger();
                logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_ERROR, getIp_Adress());

            }


            return this;
        }

        usuario = (Usuario) c.list().get(0); // Guardamos la sesión


        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
        Date now = new Date();

        long maxIntentos = 3;
        if (ca.getIntentos_bloqueo() != null && ca.getIntentos_bloqueo() > 0) {
            maxIntentos = ca.getIntentos_bloqueo();
        }

        long intentosFallidos = 0;
        if (usuario.getIntentos_fallidos() != null && usuario.getIntentos_fallidos() > 0) {
            intentosFallidos = usuario.getIntentos_fallidos();
        }

        if (intentosFallidos >= maxIntentos) {
            formulariologin.recordError("Demasiados Intentos Fallidos, el Usuario ha sido bloqueado.");

            Logger logger = new Logger();
            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_BLOQUEADO, getIp_Adress());

            return this;
        }

        Logger logger = new Logger();
        logger.loguearAcceso(session, usuario, Logger.LOGIN_STATUS_OK, Logger.LOGIN_MOTIVO_RECHAZO_OK, getIp_Adress());

        if (usuario.getUltimo_cambio_clave() != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(usuario.getUltimo_cambio_clave());
            c1.add(Calendar.DATE, ca.getDuracion_clave().intValue());

            if (c1.getTime().before(new Date())) {
                // la clave expiro
                cambioClave.flagCambioForzado("Su clave ha expirado");
                return cambioClave;
            }
        } else {
            // el ultimo cambio de clave es null
            cambioClave.flagCambioForzado("Usted nunca ha cambiado su clave, debe hacerlo ahora.");
            return cambioClave;
        }

        usuario.setIntentos_fallidos(0L);
        session.saveOrUpdate(usuario);

        /*
         * if (Helpers.esMultiOrganismo(usuario)) { administrador = true;
         *
         * // Seleccionar el organismo return organismosZone.getBody(); } else {
         * eue = usuario.getEntidadUE(); return Permisos.paginaInicial(usuario);
         * }
         *
         */
        eue = usuario.getEntidadUE();

        /*
         * if (eue == null){ // tiene que haber al menos alguna entidad cargada
         * c = session.createCriteria(EntidadUEjecutora.class);
         * c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA)); eue
         * = (EntidadUEjecutora) c.list().get(0);
         *
         * }
         *
         */
        return Permisos.paginaInicial(usuario);
    }

    private void clearCacheData() {
        org.apache.tapestry5.services.Session sessionService = request.getSession(false);
        if (sessionService != null) {
            List<String> names = sessionService.getAttributeNames();
            List<String> toKeep = Arrays.asList(new String[]{
                        "sso:" + Entidad_BK.class.getName(),
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
        String ip_Adress = "";
        try {
            ip_Adress = requestGlobal.getHTTPServletRequest().getRemoteAddr();
        } catch (Exception e) {
            //error
        }
        return ip_Adress;
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
