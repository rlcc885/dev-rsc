package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
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
import org.hibernate.Query;
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
    @SessionState
    private Usuario loggedUser;
    @Property
    @Persist
    private Usuario usuario;
    @Property
    @SessionState
    private Entidad entidad;
//    @Property
//    @Persist
//    private Usuario usuario;
    @Property
    private UsuarioTrabajador u;
    @Property
    @Persist
    private Perfilporusuario perfilporusuario;
    @Property
    private Perfilporusuario rowPerfil;
    @Persist
    @Property
    private Perfil perfil;
    @Persist
    @Property
    private Perfilusuario permiso;
    @Persist
    private GenericSelectModel<LkEstadoUsuario> _lkEstadoUsuario;
    @Inject
    private PropertyAccess _access;
//    @InjectComponent
//    @Property
//    private Zone mensajes;
    @Component(id = "formmensaje")
    private Form formmensaje;
    @Component(id = "formularioCuenta")
    private Form formularioCuenta;
    @Component(id = "formularioPersonal")
    private Form formularioPersonal;
    @Component(id = "formulariobusqueda")
    private Form formulariobusqueda;
    @Component(id = "formularioRol")
    private Form formularioRol;
    @Component(id = "formularioEntidad")
    private Form formularioEntidad;
    @Property
    private boolean blanquearIntentosFallidos;
    @Property
    @Persist
    private String tipoUsuario;
    @InjectComponent
    private Zone editarUsuarioZone;
    @InjectComponent
    private Zone tabla_usuario;
    @InjectComponent
    private Zone perfilZone;
    @Inject
    private Request _request;
    @Property
    private Boolean reinitialisarpassword;
    @Property
    @Persist
    private Entidad bEntidad;
    @Inject
    private Context context;
    @InjectComponent
    private Envelope envelope;
    @Persist
    @Property
    private boolean editaUsuario;
    @Persist
    @Property
    private boolean noEditaUsuario;
    @Persist
    @Property
    private boolean cancelaEditUsuario;
//    @Persist
//    @Property
//    private boolean newPerfil;
    @Persist
    @Property
    private String bidentificacionBusqueda;
    @Property
    @Persist
    private DatoAuxiliar bDocumentoIdentidad;
    @Property
    @Persist
    private UsuarioTrabajador usuariotrabajadoredit;
    @Property
    @Persist
    private DatoAuxiliar documentoIdentidadEdit;
//    @Property
//    @Persist
//    private LkEstadoUsuario estadoUsuarioEdit;
    @Property
    @Persist
    private Rol rolUsuarioEdit;
    @Persist
    @Property
    private String bNumeroDocumento;
    @Persist
    @Property
    private String bNombres;
    @Persist
    @Property
    private String bApellidoPaterno;
    @Persist
    @Property
    private String bApellidoMaterno;
    @Persist
    @Property
    private String bEstado;
    @Persist
    @Property
    private String bdenoentidad;
    @Persist
    @Property
    private String bNombreEntidad;
    @Persist
    @Property
    private Perfil bselectPerfil;
    @Property
    @Persist
    private Rol bselectRol;
    @Property
    @Persist
    private boolean mostrar;
    @Persist
    @Property
    private Entidad rowEntidad;
    @InjectComponent
    private Zone busZone;
    @InjectComponent
    private Zone entiZone;
    @InjectComponent
    private Zone zonaFormularioBusqueda;
    @InjectComponent
    private Zone idValidaLogin;
    @InjectComponent
    private Zone formularioEntidadZone;
    @Persist
    @Property
    private boolean bBuscarReset;
    @Persist
    @Property
    private boolean bBuscarSubmit;
    @Persist
    @Property
    private boolean bLoginValido;
    @Persist
    @Property
    private boolean muestraEditorUsuario;
    @Persist
    @Property
    private boolean muestraValidaLogin;
    @Inject
    private Request request;
    @Persist
    @Property
    long intentosFallidos;
    @Persist
    @Property
    private String nombreEntidadEdit;
    @Persist
    @Property
    private boolean seleccionaEntidadUsuario;
    @Persist
    @Property
    private boolean bseleccionaEntidadUsuario;
    @Persist
    @Property
    private boolean noEditaLoginUsuario;
    @Persist
    @Property
    private Entidad entidadUsuario;
    @Persist
    @Property
    private String numeroDNIanterior;

    @Log
    void setupRender() {
        muestraEditorUsuario = true;
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int anyo = c.get(Calendar.YEAR);
        System.out.println("hoy es:  " + dia + "/" + mes + "/" + anyo);
        resetBuscar();
        // primera vez, se setea la entidad
        bEntidad = entidad;
        bNombreEntidad = bEntidad.getDenominacion();
        resetUsuario();
        if (loggedUser.getRolid() <= 2) {
            bseleccionaEntidadUsuario = false;
        } else {
            bseleccionaEntidadUsuario = true;
        }
    }

    @Log
    public void resetBuscar() {
        bDocumentoIdentidad = null;
        bNumeroDocumento = null;
        bNombres = null;
        bApellidoPaterno = null;
        bApellidoMaterno = null;
        bEntidad = null;
        bNombreEntidad = null;
        bidentificacionBusqueda = null;
        bEstado = null;
        bselectPerfil = null;
    }

    @Log
    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }

    @Log
    public GenericSelectModel<Rol> getRolUsuario() {
        List<Rol> list;
        if (loggedUser.getRolid() == null) {
            list = Helpers.getRolUSuario(1, session);
        } else {
            list = Helpers.getRolUSuario(loggedUser.getRolid(), session);
        }
        return new GenericSelectModel<Rol>(list, Rol.class, "descrol", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentoIdentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<LkEstadoUsuario> getEstadoUsuario() {
        List<LkEstadoUsuario> list;
        list = Helpers.getEstadoUsuario(session);
        return new GenericSelectModel<LkEstadoUsuario>(list, LkEstadoUsuario.class, "descestadousuario", "id", _access);
    }

    @Log
    public GenericSelectModel<Perfil> getSelectPerfilesSinAsignar() {
        List<Perfil> list;
        list = Helpers.getPerfilesSinAsignarPorUsuario(usuariotrabajadoredit.getId(), session);
        return new GenericSelectModel<Perfil>(list, Perfil.class, "descperfil", "id", _access);
    }

    @Log
    public GenericSelectModel<Perfil> getSelectPerfiles() {
        List<Perfil> list;
        Criteria c = session.createCriteria(Perfil.class);
        list = c.list();
        return new GenericSelectModel<Perfil>(list, Perfil.class, "descperfil", "id", _access);
    }

    @Log
    public List<UsuarioTrabajador> getUsuarios() {
        Criteria c;

        c = session.createCriteria(UsuarioTrabajador.class);

        if (loggedUser.getRolid() <= 2) { // Si es administrador de Entidad, sólo puede ver su información
            bEntidad = entidad;
        }
        if (bidentificacionBusqueda != null && !bidentificacionBusqueda.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("login", "%" + bidentificacionBusqueda + "%").ignoreCase()).
                    add(Restrictions.like("login", "%" + bidentificacionBusqueda.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("login", "%" + bidentificacionBusqueda.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (bDocumentoIdentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.eq("documentoidentidadid", bDocumentoIdentidad.getId())));
        }
        if (bEstado != null) {
            c.add(Restrictions.disjunction().add(Restrictions.eq("estado", bEstado)));
        }
        if (bselectPerfil != null) {
            c.add(Restrictions.disjunction().add(Restrictions.eq("rolid", bselectPerfil.getId())));
        }
        if (bNumeroDocumento != null && !bNumeroDocumento.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nrodocumento", "%" + bNumeroDocumento + "%").ignoreCase()).
                    add(Restrictions.like("nrodocumento", "%" + bNumeroDocumento.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("nrodocumento", "%" + bNumeroDocumento.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (bApellidoPaterno != null && !bApellidoPaterno.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidopaterno", "%" + bApellidoPaterno + "%").ignoreCase()).
                    add(Restrictions.like("apellidopaterno", "%" + bApellidoPaterno.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("apellidopaterno", "%" + bApellidoPaterno.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (bApellidoMaterno != null && !bApellidoMaterno.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("apellidomaterno", "%" + bApellidoMaterno + "%").ignoreCase()).
                    add(Restrictions.like("apellidomaterno", "%" + bApellidoMaterno.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("apellidomaterno", "%" + bApellidoMaterno.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (bNombres != null && !bNombres.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("nombres", "%" + bNombres + "%").ignoreCase()).
                    add(Restrictions.like("nombres", "%" + bNombres.replaceAll("ñ", "n") + "%").ignoreCase()).
                    add(Restrictions.like("nombres", "%" + bNombres.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (bEntidad != null) {
            c.add(Restrictions.eq("entidadid", bEntidad.getId()));
        }
        List<UsuarioTrabajador> listaUsuarios = c.list();
        return listaUsuarios;
    }

    @Log
    public List<Perfilporusuario> getAllPerfiles() {
        List<Perfilporusuario> lista = null;
        Query query = session.getNamedQuery("Perfilporusuario.findByUsuarioId");
        query.setParameter("usuarioId", usuariotrabajadoredit.getId());
        lista = query.list();
        return lista;
    }

    @Log
    Object onSuccessFromFormularioCuenta() {
        List<Perfilporusuario> lista = null;
        Query query = session.getNamedQuery("UsuarioTrabajador.findByLogin");
        query.setParameter("login", usuariotrabajadoredit.getLogin());
        lista = query.list();
        muestraValidaLogin = true;
        if (lista.isEmpty()) {
            bLoginValido = true;
        } else {
            bLoginValido = false;
        }
        return request.isXHR() ? idValidaLogin.getBody() : null;
    }

    @Log
    void onSuccessFromFormularioRol() {
        return;
    }

    @Log
    void onSelectedFromBuscarEntidadUsuario() {
        seleccionaEntidadUsuario = true;
        mostrar = false;
    }

    @Log
    Object onSuccessFromFormularioEntidad() {
        return new MultiZoneUpdate("formularioEntidadZone", formularioEntidadZone.getBody()).add("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @Log
    StreamResponse onActionFromReporteUsuario(Long userID) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", userID);
        return rep.callReporte(Reportes.REPORTE.B5, Reportes.TIPO.PDF, parametros, context);
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("editarUsuarioZone", editarUsuarioZone.getBody()).add("perfilZone", perfilZone.getBody());
        return mu;
    }

    @Log
    void onSelectedFromBuscarSubmit() {
        bBuscarSubmit = true;
    }

    @Log
    void onSelectedFromBuscarReset() {
        bBuscarReset = true;
    }

    @Log
    void onSelectedFromBuscarEntidad() {
        seleccionaEntidadUsuario = false;
        mostrar = false;
        bBuscarSubmit = false;
    }

    @Log
    Object onSuccessFromFormularioBusqueda() {
        if (bBuscarReset) {
            bBuscarReset = false;
            resetBuscar();
            return zonaFormularioBusqueda.getBody();
        }
        if (bBuscarSubmit) {
            return new MultiZoneUpdate("tabla_usuario", tabla_usuario.getBody());
        } else {
            return null;
        }
    }

    @Log
    public boolean getEsTrabajador() {
        return tipoUsuario.equals(Usuario.TRABAJADOR);
    }

    @Log
    void onSelectedFromSave() {
    }

    @Log
    void resetPerfil() {
        muestraEditorUsuario = false;
        editaUsuario = false;
        permiso = new Perfilusuario();
    }

    @Log
    void resetUsuario() {
        usuario = new Usuario();
        usuariotrabajadoredit = new UsuarioTrabajador();
        usuariotrabajadoredit.setFechacreacion(new Date());
        usuariotrabajadoredit.setEstado(1);
        perfil = new Perfil();
        editaUsuario = false;
        noEditaUsuario = false;
        muestraValidaLogin = false;
        bLoginValido = false;
        intentosFallidos = 0;
        entidadUsuario = null;
        rolUsuarioEdit = null;
        nombreEntidadEdit = "";
        formmensaje.clearErrors();
        noEditaLoginUsuario = false;
        if (loggedUser.getRolid() <= 2) {
            seleccionaEntidadUsuario = false;
            entidadUsuario = entidad;
            nombreEntidadEdit = entidadUsuario.getDenominacion();
        } else {
            seleccionaEntidadUsuario = true;
        }
    }

    @Log
    Object onReset() {
        resetUsuario();
        return zonasTotal();
    }

    @Log
    Object onCancel() {
        resetUsuario();
        return zonasTotal();
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioPersonal() {
        // Si el login de usuario no es válido
        Boolean errores = false;
        formmensaje.clearErrors();
        if (usuariotrabajadoredit.getLogin() == null) {
            formmensaje.recordError("Ingrese Login de Usuario.");
            errores = true;
        } else {
            if (!bLoginValido) {
                formmensaje.recordError("Login de Usuario duplicado.");
                errores = true;
            }
        }
        if (rolUsuarioEdit == null) {
            formmensaje.recordError("Seleccione Rol del Usuario.");
            errores = true;
        }
        if (entidadUsuario == null) {
            formmensaje.recordError("Seleccione Entidad del Usuario.");
            errores = true;
        }

        if (!noEditaUsuario) {
            // Busca usuarios con el mismo DNI ingresado
            System.out.println("DNI anterior: '"+numeroDNIanterior +"' DNI ingresado: '"+ usuariotrabajadoredit.getNrodocumento()+"'");
            if (!numeroDNIanterior.equals(usuariotrabajadoredit.getNrodocumento())) {
                Query query = session.getNamedQuery("UsuarioTrabajador.findByNrodocumento");
                query.setParameter("nrodocumento", usuariotrabajadoredit.getNrodocumento());
                if (query.list().size() > 0) {
                    formmensaje.recordError("Ya existe un usuario con el DNI ingresado.");
                    errores = true;
                }
            }
        }
        if (errores) {
            return zonasTotal();
        }
//        if (bLoginValido) {
        ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
        String password = null;
        String subject = null;
        String body = null;
        String correo = null;
        SecureRandom random = new SecureRandom();
        boolean enviacorreo = false;
        password = new BigInteger(50, random).toString(32);

        if (usuariotrabajadoredit.getTrabajadorid() != 0) {
//            usuario = (Usuario) session.get(Usuario.class, usuariotrabajadoredit.getTrabajadorid());
            body = String.format("Identificación de Usuario: %s<br />Clave: %s", usuario.getTrabajador().getDocumentoidentidad().getCodigo() + usuario.getTrabajador().getNroDocumento(), password);
            correo = usuario.getTrabajador().getEmailLaboral();
            usuariotrabajadoredit.setTrabajadorid(0L);
        } else {
            body = String.format("Identificación de Usuario: %s<br />Clave: %s", usuariotrabajadoredit.getLogin(), password);
            correo = usuariotrabajadoredit.getEmaillaboral();
        }

        if (editaUsuario) {
            if (reinitialisarpassword) {
                subject = "Datos de acceso al sistema Servir";
                usuario.setMd5Clave(Encriptacion.encriptaEnMD5(password));
                enviacorreo = true;
            }
        } else {
            subject = "Datos de acceso al sistema Servir";
            usuario.setIntentos_fallidos(0L);
            usuario.setMd5Clave(Encriptacion.encriptaEnMD5(password));
            enviacorreo = true;
        }

        if (blanquearIntentosFallidos) {
            usuario.setIntentos_fallidos(0L);
        }

        if (enviacorreo) {
            if (SMTPConfig.sendMail(subject, body, correo, ca)) {
                System.out.println("Envío Correcto");
            } else {
                Logger logger = new Logger();
                logger.loguearEvento(session, logger.ERROR_SERVIDOR_DE_CORREO, usuario.getEntidad().getId(), usuario.getTrabajador().getId(), usuario.getId(), Logger.CORREO_FAIL_RESET_PASSWORD, 0);
            }
        }
        usuario.setEntidad(entidadUsuario);
        usuario.setApellidoMaterno(usuariotrabajadoredit.getApellidomaterno());
        usuario.setApellidoPaterno(usuariotrabajadoredit.getApellidopaterno());
        usuario.setNombres(usuariotrabajadoredit.getNombres());
        usuario.setDocumentoId(documentoIdentidadEdit.getId());
        usuario.setLogin(usuariotrabajadoredit.getLogin());
        usuario.setEstado(usuariotrabajadoredit.getEstado());
        usuario.setRolid(rolUsuarioEdit.getId());
        usuario.setNumeroDocumento(usuariotrabajadoredit.getNrodocumento());
        usuario.setEmaillaboral(usuariotrabajadoredit.getEmaillaboral());
        usuario.setTelefono(usuariotrabajadoredit.getTelefono());
        usuario.setFecha_creacion(usuariotrabajadoredit.getFechacreacion());
        usuario.setObservacion(usuariotrabajadoredit.getObservacion());

        session.saveOrUpdate(usuario);
        envelope.setContents(helpers.Constantes.USUARIO_EXITO);
        resetUsuario();
//        }
        return zonasTotal();
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        Perfilusuario permiso = new Perfilusuario();
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK();
        perfilusuariopk.setUsuarioId(usuario.getId());
        perfilusuariopk.setPerfilId(perfil.getId());
        permiso.setPerfilusuarioPK(perfilusuariopk);
        session.save(permiso);
        editaUsuario = true;
        return zonasTotal();
    }

    @Log
    private Usuario createNewUsuario() {
        Usuario usuario_local = new Usuario();
        usuario_local.setIntentos_fallidos(0L);
        return usuario_local;
    }

    /*
     * reset del formulario
     */
    @Log
    @CommitAfter
    Object onActionFromEliminaPerfil(Perfilporusuario lPermiso) {
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK(lPermiso.getUsuarioId(), lPermiso.getPerfilId());
        permiso = (Perfilusuario) session.get(Perfilusuario.class, perfilusuariopk);
        session.delete(permiso);
        editaUsuario = true;
        return zonasTotal();
    }
    /*
     * Cargar desde los parámetros
     */

    @Log
    void onActivate() {
//        Document pdfDocument = new Document();
//        URL oracle = new URL("http://www.oracle.com/");
//        URLConnection conn = oracle.openConnection();
//        /*
//         * Reader htmlreader = new BufferedReader(new InputStreamReader(
//         * oracle.openStream() ));
//         */
//        Reader htmlreader = new BufferedReader(new InputStreamReader(
//                new FileInputStream("c://grafico.html")));
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PdfWriter.getInstance(pdfDocument, baos);
//        pdfDocument.open();
//        StyleSheet styles = new StyleSheet();
//        styles.loadTagStyle("body", "font", "Bitstream Vera Sans");
//        ArrayList arrayElementList = (ArrayList) HTMLWorker.parseToList(htmlreader, styles);
//        for (int i = 0; i < arrayElementList.size(); ++i) {
//            Element e = (Element) arrayElementList.get(i);
//            pdfDocument.add(e);
//        }
//        pdfDocument.close();
//        byte[] bs = baos.toByteArray();
//        String pdfBase64 = Base64.encodeBytes(bs); //output
//        File pdfFile = new File("pdfExample.pdf");
//        FileOutputStream out = new FileOutputStream(pdfFile);
//        out.write(bs);
////        out.close();
//
//        // codigo valido para html pero invalido para XHTML
//        // no esta cerrando el tag img
//        String html = new String("<img scr='img.gif'>");
//        //--
//        ByteArrayInputStream b = new ByteArrayInputStream(html.getBytes());
//        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
//
//        Tidy tidy = new Tidy();
//        tidy.setXHTML(true); // queremos que la salida sea xhtml
//        tidy.parse(b, out2);
//        System.out.println(out2.toString());
//        
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        imageGenerator.loadUrl("http://dl.dropbox.com/u/4151695/html/jOrgChart/example/example.html");
//        imageGenerator.saveAsImage("hello-world.png");
//        imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
//
//        File inputFile = new File("hola.xhtml");
//        OutputStream os = new FileOutputStream(new File("xhtmlToPdf_holaMundo.pdf"));
//
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocument(inputFile);
//        renderer.layout();
//        renderer.createPDF(os);
//
//        os.close();
//        out.close();
    }

    @Log
    Object onEditaUsuario(UsuarioTrabajador lusuariotrabajador) {
        usuario = (Usuario) session.get(Usuario.class, lusuariotrabajador.getId());
        rolUsuarioEdit = (Rol) session.get(Rol.class, lusuariotrabajador.getRolid());
        documentoIdentidadEdit = (DatoAuxiliar) session.get(DatoAuxiliar.class, lusuariotrabajador.getDocumentoidentidadid());
        numeroDNIanterior = lusuariotrabajador.getNrodocumento();
        if (lusuariotrabajador.getTrabajadorid() == 0) {
            noEditaUsuario = false;
            entidadUsuario = (Entidad) session.get(Entidad.class, lusuariotrabajador.getEntidadid());
            nombreEntidadEdit = entidadUsuario.getDenominacion();
        } else {
            entidadUsuario = usuario.getTrabajador().getEntidad();
            nombreEntidadEdit = usuario.getTrabajador().getEntidad().getDenominacion();
            noEditaUsuario = true;
        }

        usuariotrabajadoredit = lusuariotrabajador;
        intentosFallidos = usuariotrabajadoredit.getIntentosFallidos();
        muestraEditorUsuario = true;
        editaUsuario = true;
        bLoginValido = true;
        noEditaLoginUsuario = true;
        return zonasTotal();
    }

    @Log
    Object onEditaPerfil(UsuarioTrabajador lusuariotrabajador) {
        usuariotrabajadoredit = lusuariotrabajador;
        resetPerfil();
        return zonasTotal();
    }

    @Log
    private boolean usuarioDeOrganismo() {
        return (tipoUsuario.equals(Usuario.ADMINLOCAL)
                || tipoUsuario.equals(Usuario.OPERADORLECTURALOCAL)
                || tipoUsuario.equals(Usuario.OPERADORABMLOCAL)
                || tipoUsuario.equals(Usuario.TRABAJADOR));
    }

    @Log
    public boolean getUsuarioDeOrganismo() {
        return this.usuarioDeOrganismo();
    }

    @Log
    Object onSuccessFromFormFindEntidad() {
        mostrar = true;
        return new MultiZoneUpdate("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @Log
    Object onActionFromSeleccionaEntidad(Entidad entidad) {
        if (entidad != null) {
            bNombreEntidad = entidad.getDenominacion();
            bEntidad = entidad;
        }
        mostrar = false;
        return zonaFormularioBusqueda.getBody();
    }

    @Log
    Object onActionFromSeleccionaEntidadUsuario(Entidad entidad) {
        if (entidad != null) {
            nombreEntidadEdit = entidad.getDenominacion();
            entidadUsuario = entidad;
        }
        mostrar = false;
        return new MultiZoneUpdate("formularioEntidadZone", formularioEntidadZone.getBody()).add("busZone", busZone.getBody()).add("entiZone", entiZone.getBody());
    }

    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        if (bdenoentidad != null) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()));
            //.add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", "%" + bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()))
        }
        return c.list();
    }
}
