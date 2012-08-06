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
    @Property
    @Persist
    private Usuario usuario;
    @Property
    private UsuarioTrabajador u;
    @Property
    @Persist
    private UsuarioTrabajador usuariotrabajador;
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
    @Property
    @Persist
    private Rol rscrol;
    @Property
    @Persist
    private LkEstadoUsuario lkEstadoUsuario;
    @Persist
    private GenericSelectModel<Rol> _RscRol;
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
    @Component(id = "formulariobusqueda")
    private Form formulariobusqueda;
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
    private boolean primeraVez;
    @Persist
    @Property
    private boolean editaUsuario;
    @Persist
    @Property
    private boolean cancelaEditUsuario;
    @Persist
    @Property
    private boolean botonPerfil;
    @Persist
    @Property
    private boolean newPerfil;

    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }

    public GenericSelectModel<Rol> getRolUsuario() {
        List<Rol> list;
        if (usuario.getRol() == null) {
            list = Helpers.getRolUSuario(1, session);
        } else {
            list = Helpers.getRolUSuario(loggedUser.getRol().getId(), session);
        }
        _RscRol = new GenericSelectModel<Rol>(list, Rol.class, "descrol", "id", _access);
        return _RscRol;
    }

    public GenericSelectModel<LkEstadoUsuario> getEstadoUsuario() {
        List<LkEstadoUsuario> list;
        list = Helpers.getEstadoUsuario(session);
        return new GenericSelectModel<LkEstadoUsuario>(list, LkEstadoUsuario.class, "descestadousuario", "id", _access);
    }

    public GenericSelectModel<Perfil> getSelectPerfiles() {
        List<Perfil> list;
        list = Helpers.getPerfilesSinAsignarPorUsuario(usuario.getId(), session);
        return new GenericSelectModel<Perfil>(list, Perfil.class, "descperfil", "id", _access);
    }

    public List<String> getTiposUsuarios() {
        List<String> tc = new ArrayList<String>();
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

        if (loggedUser.getRol().getId() > 1 && this.primeraVez) {
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
            }
            listaUsuarios = c.list();
        }
        return listaUsuarios;
    }

    public List<Perfilporusuario> getAllPerfiles() {
        List<Perfilporusuario> lista = null;
        Query query = session.getNamedQuery("Perfilporusuario.findByUsuarioId");
        query.setParameter("usuarioId", usuario.getId());
        lista = query.list();
        return lista;
    }

    StreamResponse onActionFromReporteUsuario(Long userID) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_UsuarioID", userID);
        return rep.callReporte(Reportes.REPORTE.B5, Reportes.TIPO.PDF, parametros, context);
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("editarUsuarioZone", editarUsuarioZone.getBody()).add("tabla_usuario", tabla_usuario.getBody()).add("perfilZone", perfilZone.getBody());
        return mu;
    }

    Object onSuccessFromformulariobusqueda() {
        primeraVez = true;
        editaUsuario = false;
        botonPerfil = false;
        newPerfil = false;
        return zonasTotal();
    }

    public boolean getMuestroOrganismos() {
        return ((tipoUsuario.equals(Usuario.ADMINLOCAL)) && (loggedUser.getTipo_usuario().equals(Usuario.ADMINGRAL)));
    }

    public boolean getEsTrabajador() {
        return tipoUsuario.equals(Usuario.TRABAJADOR);
    }

    void onPrepareFromFormularioUsuario() {
        if (loggedUser.getEntidad() != null) {
            entidad = loggedUser.getEntidad();
        }
    }

    void onSelectedFromSave() {
    }

    void onSelectedFromCancel() {
        cancelaEditUsuario = true;
        newPerfil = false;
    }

    @Log
    @CommitAfter
    Object onNewPerfil() {
        botonPerfil = false;
        newPerfil = true;
        permiso = new Perfilusuario();
        return perfilZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioUsuario() {
        if (!cancelaEditUsuario) {
            ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
            String password = null;
            if (blanquearIntentosFallidos) {
                usuario.setIntentos_fallidos(0L);
            }
            if (reinitialisarpassword) {
                SecureRandom random = new SecureRandom();
                password = new BigInteger(50, random).toString(32);
                usuario.setMd5Clave(Encriptacion.encriptaEnMD5(password));
                usuario.setClave(password);
                String subject = "Datos de acceso al sistema Servir";
                String body = String.format("Identificación de Usuario: %s<br />Clave: %s", usuario.getTrabajador().getDocumentoidentidad().getCodigo() + usuario.getTrabajador().getNroDocumento(), password);
                if (SMTPConfig.sendMail(subject, body, usuario.getTrabajador().getEmailLaboral(), ca)) {
                    System.out.println("Envío Correcto");
                } else {
                    Logger logger = new Logger();
                    logger.loguearEvento(session, logger.ERROR_SERVIDOR_DE_CORREO, usuario.getEntidad(), usuario.getTrabajador().getId(), Logger.CORREO_FAIL_RESET_PASSWORD);
                }
            }
            usuario.setEstado(lkEstadoUsuario.getId());
            usuario.setRol(rscrol);
            session.saveOrUpdate(usuario);
            envelope.setContents(helpers.Constantes.USUARIO_EXITO);
            usuario = createNewUsuario();
        }
        primeraVez = true;
        cancelaEditUsuario = false;
        editaUsuario = false;
        return zonasTotal();
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK();
        perfilusuariopk.setUsuarioId(usuario.getId());
        perfilusuariopk.setPerfilId(perfil.getId());
        permiso.setPerfilusuarioPK(perfilusuariopk);
        session.save(permiso);
        botonPerfil = true;
        editaUsuario = true;
        newPerfil = false;
        primeraVez = true;
        return zonasTotal();
    }

    private Usuario createNewUsuario() {
        Usuario usuario_local = new Usuario();
        usuario_local.setIntentos_fallidos(0L);
        tipoDocumento = null;
        nroDocumento = null;
        return usuario_local;
    }

    /*
     * reset del formulario
     */
    @Log
    @CommitAfter
    Object onActionFromEliminaPerfil(Perfilporusuario lPermiso) {
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK(lPermiso.getUsuarioId(), lPermiso.getPerfilId());
        permiso = (Perfilusuario) session.load(Perfilusuario.class, perfilusuariopk);
        session.delete(permiso);
        primeraVez = true;
        editaUsuario = true;
        botonPerfil = true;
        newPerfil = false;
        return zonasTotal();
    }
    /*
     * Cargar desde los parámetros
     */

    void onActivate() throws Exception {
        System.out.println("onActivate");

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

    void setupRender() {
        primeraVez = false;
        editaUsuario = false;
        botonPerfil = false;
        newPerfil = false;
        System.out.println("setupRender");
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int anyo = c.get(Calendar.YEAR);
        System.out.println("hoy es:  " + dia + "/" + mes + "/" + anyo);
    }

    @Log
    @CommitAfter
    Object onEditaUsuario(UsuarioTrabajador lusuariotrabajador) {
        usuario = (Usuario) session.load(Usuario.class, lusuariotrabajador.getTrabajadorid());
        rscrol = usuario.getRol();
        lkEstadoUsuario = Helpers.getEstadoUsuario(usuario.getEstado(), session);
        editaUsuario = true;
        botonPerfil = true;
        primeraVez = true;
        usuariotrabajador = lusuariotrabajador;
        return zonasTotal();
    }

    Usuario onPassivate() {
        return null;
    }

    private boolean usuarioDeOrganismo() {
        return (tipoUsuario.equals(Usuario.ADMINLOCAL)
                || tipoUsuario.equals(Usuario.OPERADORLECTURALOCAL)
                || tipoUsuario.equals(Usuario.OPERADORABMLOCAL)
                || tipoUsuario.equals(Usuario.TRABAJADOR));

    }

    public boolean getUsuarioDeOrganismo() {
        return this.usuarioDeOrganismo();
    }
}
