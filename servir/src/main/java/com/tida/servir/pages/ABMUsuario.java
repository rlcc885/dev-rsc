package com.tida.servir.pages;

//import com.sun.mail.smtp.SMTPMessage;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Clase que maneja el ABM de usuarios
 *
 */
public class ABMUsuario extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @Persist
    private Usuario usuario;
    @Property
    private UsuarioTrabajador u;
    @Property
    @Persist
    private Trabajador trabajador;
    @Property
    @Persist
    private RscRol rscrol;
    @Property
    @Persist
    private LkEstadoUsuario lkEstadoUsuario;
    @Persist
    private GenericSelectModel<Entidad> _beanOrganismos;
    @Persist
    private GenericSelectModel<RscRol> _RscRol;
    @Persist
    private GenericSelectModel<LkEstadoUsuario> _lkEstadoUsuario;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private String nroDocumento;
    @Property
    @Persist
    private String tipoDocumento;
    @Property
    @SessionState
    private Usuario loggedUser;
    @Component(id = "formulariousuario")
    private Form formularioUsuario;
//    @Component(id = "formTipoUsuario")
//    private Form formTipoUsuario;
    @Component(id = "formulariobusqueda")
    private Form formulariobusqueda;
    @Component(id = "formOrganismo")
    private Form formOrganismo;
    @Property
    private boolean blanquearIntentosFallidos;
    @Persist
    private boolean editando;
    @Property
    @Persist
    private String tipoUsuario;
    @Property
    @Persist
    private String errorBorrar;
    @InjectComponent
    private Zone entidadZone;
    @InjectComponent
    private Zone editarUsuarioZone;
    @InjectComponent
    private Zone tabla_usuario;
    @Inject
    private Request _request;
    @Property
    private Boolean reinitialisarpassword;
    @Property
    @Persist
    private Entidad entidad;
    @Inject
    private Context context;
    @InjectComponent
    private Envelope envelope;
    @Persist
    @Property
    private String identificacionBusqueda;
    @Persist
    @Property
    private String apellidosBusqueda;
    @Persist
    @Property
    private String nombresBusqueda;
    @Persist
    @Property
    private int primeraVez;

    public ABMUsuario() {
        System.out.println("ABMUsuario");
    }

    public List<String> getTiposDoc() {
        System.out.println("getTiposDoc");
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }

    public GenericSelectModel<RscRol> getRolUsuario() {
        List<RscRol> list;
        if (usuario.getRol() == null) {
            list = Helpers.getRolUSuario(1, session);
        } else {
            list = Helpers.getRolUSuario(loggedUser.getRol().getId(), session);
        }
        _RscRol = new GenericSelectModel<RscRol>(list, RscRol.class, "descrol", "id", _access);
        return _RscRol;
    }

    public GenericSelectModel<LkEstadoUsuario> getEstadoUsuario() {
        List<LkEstadoUsuario> list;
        list = Helpers.getEstadoUsuario(session);
        _lkEstadoUsuario = new GenericSelectModel<LkEstadoUsuario>(list, LkEstadoUsuario.class, "descestadousuario", "id", _access);
        return _lkEstadoUsuario;
    }

    public GenericSelectModel<Entidad> getBeanOrganismos() {
        List<Entidad> list;
        Criteria c;
        c = session.createCriteria(Entidad.class);
        c.add(Restrictions.ne("estado", Entidad.ESTADO_BAJA));
        System.out.println("getBeanOrganismos");
        list = c.list();

        //entidadUE = (EntidadUEjecutora) c.list().get(0); //cargamos el valor por defecto
        _beanOrganismos = new GenericSelectModel<Entidad>(list, Entidad.class, "denominacion", "id", _access);
        return _beanOrganismos;
    }

    public List<String> getTiposUsuarios() {
        List<String> tc = new ArrayList<String>();
        System.out.println("getTiposUsuarios");
        // Sólo los usuarios admin_graal pueden generar administradores generales y locales
        if (loggedUser.getTipo_usuario().equals(Usuario.ADMINGRAL)) {
            tc.add(Usuario.ADMINGRAL);
            tc.add(Usuario.ADMINLOCAL);
            tc.add(Usuario.OPERADORABMSERVIR);
            tc.add(Usuario.OPERADORANALISTA);
            tc.add(Usuario.ADMINSISTEMA);
        }

        if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
            tc.add(Usuario.OPERADORABMLOCAL);
            tc.add(Usuario.OPERADORLECTURALOCAL);
            tc.add(Usuario.TRABAJADOR);
        }

        return tc;
    }

    public List<UsuarioTrabajador> getUsuarios() {
        Criteria c;
        List<UsuarioTrabajador> listaUsuarios = null;
        System.out.println("getUsuarios");

        if (loggedUser.getRol().getId() > 1 && this.primeraVez > 0) {
            c = session.createCriteria(UsuarioTrabajador.class);

            //busqueda
            if (identificacionBusqueda != null && !identificacionBusqueda.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("nrodocumento", identificacionBusqueda + "%").ignoreCase()).add(Restrictions.like("nrodocumento", identificacionBusqueda.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nrodocumento", identificacionBusqueda.replaceAll("n", "ñ") + "%").ignoreCase()));
            }
            if (apellidosBusqueda != null && !apellidosBusqueda.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("apellidos", apellidosBusqueda + "%").ignoreCase()).add(Restrictions.like("apellidos", apellidosBusqueda.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidos", apellidosBusqueda.replaceAll("n", "ñ") + "%").ignoreCase()));
            }
            if (nombresBusqueda != null && !nombresBusqueda.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("nombres", nombresBusqueda + "%").ignoreCase()).add(Restrictions.like("nombres", nombresBusqueda.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("nombres", nombresBusqueda.replaceAll("n", "ñ") + "%").ignoreCase()));
            }

            if (loggedUser.getRol().getId() == 2) { // Si es administrador de Entidad, sólo puede ver su información
                c.add(Restrictions.eq("entidad", loggedUser.getEntidad()));
                //c.add(Restrictions.ne("tipo_usuario", Usuario.ADMINLOCAL));
                //} else {
                // Administrador de usuarios general
                //c.add(Restrictions.ne("tipo_usuario", Usuario.OPERADORABMLOCAL));
                //c.add(Restrictions.ne("tipo_usuario", Usuario.OPERADORLECTURALOCAL));
                //c.add(Restrictions.ne("tipo_usuario", Usuario.TRABAJADOR));
            }
            listaUsuarios = c.list();
        }

        return listaUsuarios;

    }
    /*
     * void onPrepareFromformTipoUsuario() {
     * System.out.println("onPrepareFromformTipoUsuario"); if (tipoUsuario ==
     * null) { tipoUsuario = ""; }
     *
     * if (editando) { tipoUsuario = usuario.getTipo_usuario(); } }
     */

    StreamResponse onActionFromReporteUsuario(Long userID) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        System.out.println("onActionFromReporteUsuario");
        parametros.put("MandatoryParameter_UsuarioID", userID);
        return rep.callReporte(Reportes.REPORTE.B5, Reportes.TIPO.PDF, parametros, context);
    }

    /*
     * public Object onChangeOfTipoUsuario() {
     *
     * tipoUsuario = _request.getParameter("param"); return
     * entidadZone.getBody(); }
     */
    /*
     * @Log Object onSuccessFromformTipoUsuario() {
     * System.out.println("onSuccessFromformTipoUsuario"); return new
     * MultiZoneUpdate("editarUsuarioZone",
     * editarUsuarioZone.getBody()).add("entidadZone", entidadZone.getBody()); }
     */
    Object onSuccessFromformulariobusqueda() {
        primeraVez++;
        System.out.println("onSuccessFromformulariobusqueda");
        return tabla_usuario.getBody();
    }

    @Log
    Object onSuccessFromformOrganismo() {
        System.out.println("onSuccessFromformOrganismo");
        return editarUsuarioZone.getBody();
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        System.out.println("zonasTotal");
        mu = new MultiZoneUpdate("editarUsuarioZone", editarUsuarioZone.getBody()).add("tabla_usuario", tabla_usuario.getBody()).add("entidadZone", entidadZone.getBody());

        return mu;
    }

    public boolean getMuestroOrganismos() {
        System.out.println("getMuestroOrganismos");
        return ((tipoUsuario.equals(Usuario.ADMINLOCAL)) && (loggedUser.getTipo_usuario().equals(Usuario.ADMINGRAL)));
    }

    public boolean getEsTrabajador() {
        System.out.println("getEsTrabajador");
        return tipoUsuario.equals(Usuario.TRABAJADOR);
    }

    void onPrepareFromFormularioUsuario() {
        System.out.println("onPrepareFromFormularioUsuario");
        if (loggedUser.getEntidad() != null) {
            entidad = loggedUser.getEntidad();
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioUsuario() {
        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
        String password = null;
        System.out.println("onSuccessFromFormularioUsuario");
        if (!editando) {
            if (tipoUsuario.equals(Usuario.TRABAJADOR)) {
                Criteria c = session.createCriteria(Trabajador.class);
                c.add(Restrictions.eq("tipoDocumento", tipoDocumento));
                c.add(Restrictions.eq("nroDocumento", nroDocumento));
                if (c.list().isEmpty()) {
                    Logger logger = new Logger();
                    logger.loguearError(session, loggedUser, loggedUser.getId().toString(),
                            Logger.CODIGO_ERROR_USUARIO_UNICO,
                            Errores.ERROR_NO_HAY_TRABAJADOR, Logger.TIPO_OBJETO_USUARIO);
                    formularioUsuario.recordError(Errores.ERROR_NO_HAY_TRABAJADOR);
                    return this;
                }
                Trabajador trabajador = (Trabajador) c.list().get(0);
//                usuario.setApellidos(trabajador.getApellidoPaterno() + " " + trabajador.getApellidoMaterno());
//                usuario.setNombres(trabajador.getNombres());
                usuario.setTrabajador(trabajador);
            }
            Criteria c = session.createCriteria(Usuario.class);
//            c.add(Restrictions.eq("apellidos", usuario.getApellidos()));
//            c.add(Restrictions.eq("nombres", usuario.getNombres()));
            if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
                c.add(Restrictions.eq("entidad", loggedUser.getEntidad()));
            }
            if (c.list().size() > 0) {
                Logger logger = new Logger();
                logger.loguearError(session, loggedUser, loggedUser.getId().toString(),
                        Logger.CODIGO_ERROR_USUARIO_UNICO,
                        Errores.ERROR_NOMBRE_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
                formularioUsuario.recordError(Errores.ERROR_NOMBRE_USUARIO_UNICO);
                return this;
            }

//            c = session.createCriteria(Usuario.class);
//            c.add(Restrictions.eq("login", usuario.getLogin()));
//            if (c.list().size() > 0) {
//                Logger logger = new Logger();
//                logger.loguearError(session, loggedUser, loggedUser.getId().toString(),
//                        Logger.CODIGO_ERROR_USUARIO_EXISTE,
//                        Errores.ERROR_LOGIN_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
//                formularioUsuario.recordError(Errores.ERROR_LOGIN_USUARIO_UNICO);
//                return this;
//            }

            c = session.createCriteria(Usuario.class);
//            c.add(Restrictions.eq("email", usuario.getEmail()));
            if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
                c.add(Restrictions.eq("entidad", loggedUser.getEntidad()));
            }
            // Pedimos únicos mail por entidadUE
            if (c.list().size() > 0) {
                Logger logger = new Logger();
                logger.loguearError(session, loggedUser, loggedUser.getId().toString(),
                        Logger.CODIGO_ERROR_MAIL_EXISTE,
                        Errores.ERROR_EMAIL_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
                formularioUsuario.recordError(Errores.ERROR_EMAIL_USUARIO_UNICO);
                return this;
            }

            // Seteo la password inicial
            //password = usuario.getLogin()+"123.";
            SecureRandom random = new SecureRandom();
            password = new BigInteger(50, random).toString(32);
            usuario.setMd5Clave(Encriptacion.encriptaEnMD5(password));
            usuario.setClave(password);

        } else { // de editando
            if (blanquearIntentosFallidos) {
                usuario.setIntentos_fallidos(0L);
            }

            if (reinitialisarpassword) {
                SecureRandom random = new SecureRandom();
                password = new BigInteger(50, random).toString(32);
                usuario.setMd5Clave(Encriptacion.encriptaEnMD5(password));
                usuario.setClave(password);
                //sendPasswordByEMail(usuario.getTrabajador().getEmailLaboral(), usuario.getLogin(), password);
                String subject = "Datos de acceso al sistema Servir";
                String body = String.format("Identificación de Usuario: %s<br />Clave: %s", usuario.getTrabajador().getNroDocumento(), password);
                //SMTPConfig.sendMail(subject,body, usuario.getTrabajador().getEmailLaboral(),ca);
                if (SMTPConfig.sendMail(subject, body, usuario.getTrabajador().getEmailLaboral(), ca)) {
                    System.out.println("envío Correcto");
                } else {
                    System.out.println("envío Fallido");
                }

            }
        }

        usuario.setEstado(lkEstadoUsuario.getId());
        usuario.setRol(rscrol);
        //usuario.setTipo_usuario(tipoUsuario);
//        if (usuarioDeOrganismo()) {
//            usuario.setEntidad(entidad);
//        } else {
//            usuario.setEntidad(null);
//        }
        if (editando) {
            Logger logger = new Logger();
            logger.loguearOperacionUsuario(session, usuario, Logger.USUARIO_TIPO_OPERACION_EDICION, loggedUser);
        } else {
            Logger logger = new Logger();
            logger.loguearOperacionUsuario(session, usuario, Logger.USUARIO_TIPO_OPERACION_CREACION, loggedUser);
            usuario.setUltimo_cambio_clave(null);
            usuario.setIntentos_fallidos(0L);
        }

        session.saveOrUpdate(usuario);

//        if (!editando) {
////            sendPasswordByEMail(usuario.getEmail(), usuario.getLogin(), password);
//        }
        envelope.setContents(helpers.Constantes.USUARIO_EXITO);
        editando = false;
        usuario = createNewUsuario();

        return zonasTotal();
    }

    private Usuario createNewUsuario() {
        Usuario usuario_local = new Usuario();
        usuario_local.setIntentos_fallidos(0L);
        System.out.println("createNewUsuario");
        tipoDocumento = null;
        nroDocumento = null;
        return usuario_local;
    }


    /*
     * reset del formulario
     */
    void onActionFromReset() {
        System.out.println("onActionFromReset");
        usuario = createNewUsuario();
        tipoUsuario = usuario.getTipo_usuario();
        entidad = usuario.getEntidad();
        editando = false;
    }


    /*
     * Cargar desde los parámetros
     */
    void onActivate() {
        System.out.println("onActivate");
        if (usuario == null) {
            usuario = createNewUsuario();
            trabajador = new Trabajador();
            rscrol = new RscRol();
            lkEstadoUsuario = new LkEstadoUsuario();
            editando = false;
            if (loggedUser.getEntidad() != null) //No soy dios
            {
                tipoUsuario = Usuario.OPERADORABMLOCAL;
            } else {
                tipoUsuario = Usuario.ADMINGRAL;
            }

        } else {
            // Editando
            if (tipoUsuario == null) {
                if (usuario.getTipo_usuario() != null) {
                    tipoUsuario = usuario.getTipo_usuario();
                } else {
                    tipoUsuario = Usuario.ADMINGRAL;
                }

            }
            if (entidad == null) {
                entidad = usuario.getEntidad();
            }
        }


    }

    void onActivate(Usuario user) {
        System.out.println("onActivate(Usuario user)");
        if (user == null) {
            user = createNewUsuario();
            editando = false;
        } else {
            System.out.println("onActivate(Usuario user)" + user.getTipo_usuario());
            if (user.getTipo_usuario().equals(Usuario.TRABAJADOR)) {
                nroDocumento = user.getTrabajador().getNroDocumento();
                tipoDocumento = user.getTrabajador().getTipoDocumento();
            }
            trabajador = user.getTrabajador();
            rscrol = user.getRol();
            lkEstadoUsuario = Helpers.getEstadoUsuario(user.getEstado(), session);
            errorBorrar = null;
            editando = true;
        }
        usuario = user;
    }

    Usuario onPassivate() {
        System.out.println("onPassivate");
        return null;
    }

    private boolean usuarioDeOrganismo() {
        System.out.println("usuarioDeOrganismo");
        return (tipoUsuario.equals(Usuario.ADMINLOCAL)
                || tipoUsuario.equals(Usuario.OPERADORLECTURALOCAL)
                || tipoUsuario.equals(Usuario.OPERADORABMLOCAL)
                || tipoUsuario.equals(Usuario.TRABAJADOR));

    }

    public boolean getUsuarioDeOrganismo() {
        System.out.println("getUsuarioDeOrganismo");
        return this.usuarioDeOrganismo();
    }
}
