package com.tida.servir.pages;

import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioTrabajador;
import com.tida.servir.services.GenericSelectModel;
import helpers.Encriptacion;
import helpers.Logger;
import helpers.SMTPConfig;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Login de Usuarios
 */
public class RecuperarClave {

    @Inject
    private Session session;
    @Property
    private Usuario usuario;
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Property
    @Persist
    private ConfiguracionAcceso configuracionAcceso;
    @Persist
    @Property
    private boolean administrador;
    @Property
    private String clave;
    @Property
    private String correolectronico;
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
    @Inject
    private ComponentResources _resources;
    @InjectPage
    private CambiarClavePrimera cambioClave;
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
    
    private boolean _cancelar = false;

    public boolean getMuestroSubmit() {
        return !administrador;
    }

    void onSelectedFrombotonCancelar() {
        System.out.println("SELECCIONA PRIMERO ===========================================================");
        _cancelar = true;
    }

    void onPrepareFromFormulariologin() {
        //  usuario = null; // Para que haga un logout
    }

    @CommitAfter
    Object onSuccessFromFormulariologin() throws InterruptedException {
        if (_cancelar){
            return "index";
        } else {
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
            logger.loguearEvento(session, logger.ACCESOS, usuarioTrabajador.getEntidadid(), usuarioTrabajador.getTrabajadorid(), usuarioTrabajador.getId(),Logger.LOGIN_MOTIVO_RECHAZO_USERLOCKED,0);
            formulariologin.recordError("Usuario Bloqueado. Contacte a un administrador");
            return this;
        }

        if (usuarioTrabajador.getEstado() == 0) { // Si esta inactivo el usuario
            logger.loguearEvento(session, logger.ACCESOS, usuarioTrabajador.getEntidadid(), usuarioTrabajador.getTrabajadorid(), usuarioTrabajador.getId(),Logger.LOGIN_MOTIVO_RECHAZO_USERLOW,0);
            formulariologin.recordError("Usuario dado de baja. Contacte a un administrador");
            return this;
        }

        Criteria cq = session.createCriteria(Usuario.class);
        cq.add(Restrictions.eq("id", usuarioTrabajador.getId()));

        usuario = (Usuario) cq.list().get(0); // Guardamos la sesión

        SecureRandom random = new SecureRandom();
        clave = new BigInteger(50, random).toString(32);
        usuario.setMd5Clave(Encriptacion.encriptaEnMD5(clave));
        //usuario.setClave(password);
        //sendPasswordByEMail(usuario.getTrabajador().getEmailLaboral(), usuario.getLogin(), password);
        String subject = "Datos de acceso al sistema Servir";
        String body = String.format("Identificación de Usuario: %s<br />Clave: %s", usuario.getTrabajador().getNroDocumento(), clave);

        if (SMTPConfig.sendMail(subject, body, usuario.getTrabajador().getEmailLaboral(), configuracionAcceso)) {
            usuario.setIntentos_fallidos(0L);
            session.saveOrUpdate(usuario);
            System.out.println("envío Correcto");
            administrador = true;
            //formulariologin.recordError("La nueva contraseña ha sido enviado a su correo electrónico laboral. Verifique su bandeja de entrada.");
        } else {
            System.out.println("envío Fallido");
            logger.loguearEvento(session, logger.ACCESOS, usuarioTrabajador.getEntidadid(), usuarioTrabajador.getTrabajadorid(), usuarioTrabajador.getId(),Logger.LOGIN_MOTIVO_ERROR_RECUPERAR_CLAVE,0);
//            logger.loguearAcceso(session, null, Logger.LOGIN_STATUS_ERROR, Logger.LOGIN_MOTIVO_RECHAZO_USERLOW, getIp_Adress());
            formulariologin.recordError("Hubo un problema al enviar la nueva contraseña a su correo electrónico. Intente más tarde.");
        }
        
        return this;
        }
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

    void onActivate() {
        System.out.println("CARGA LA PAGINA=============>>>>");
        System.out.println(administrador);
        clearCacheData();
    }

    public String getIp_Adress() {
        //String ip_Adress = "";
        String ip_Adress = requestGlobal.getHTTPServletRequest().getHeader("X-Forwarded-For");
        if (ip_Adress != null) {
            return ip_Adress;
        } else {
            return requestGlobal.getHTTPServletRequest().getRemoteAddr();
        }
        /*
         * try { ip_Adress =
         * requestGlobal.getHTTPServletRequest().getRemoteAddr(); } catch
         * (Exception e) { //error }
         */
        //return ip_Adress;
    }
}
