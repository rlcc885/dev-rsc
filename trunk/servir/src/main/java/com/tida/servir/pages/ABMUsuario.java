package com.tida.servir.pages;

import com.sun.mail.smtp.SMTPMessage;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import helpers.Errores;
import helpers.Logger;

import com.tida.servir.entities.ConfiguracionAcceso;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
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
 *	Clase que maneja el ABM de usuarios
 *  
 */
public class ABMUsuario  extends GeneralPage {

    @Inject
    private Session session;

    @Property
    @Persist
    private Usuario usuario;

    @Property
    private Usuario u;
    
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

    @Component(id = "formTipoUsuario")
    private Form formTipoUsuario;
    
    @Component(id = "formulariobusqueda")
    private Form formulariobusqueda;
    
    @Component(id="formOrganismo")
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

    @Persist
    private GenericSelectModel<Entidad_BK> _beanOrganismos;

    @Inject
    private PropertyAccess _access;

    @Inject
    private Request _request;
    
    @Property
    private Boolean reinitialisarpassword;

    @Property
    @Persist
    private Entidad_BK entidadUE;

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
	
    public ABMUsuario() {
    }
    
    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }    
    
    public List<String>  getTiposUsuarios(){
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

    /**
     * Obtiene los estados de los usuarios de la tabla auxiliar adecuada
     * @return
     */
    public List<String>  getEstadosUsuarios(){
        List<String> tc = new ArrayList<String>();
        tc.add(Usuario.ESTADOACTIVO);
        tc.add(Usuario.ESTADOBORRADO);
        tc.add(Usuario.ESTADOBLOQUEADO);
        return tc;
    }

    public List<Usuario> getUsuarios () {
    	Criteria c;
    	c = session.createCriteria(Usuario.class);
        
        //busqueda
        if (identificacionBusqueda != null && !identificacionBusqueda.equals("")) {

            c.add(Restrictions.disjunction()
                    .add(Restrictions.like("login", identificacionBusqueda + "%").ignoreCase())
                    .add(Restrictions.like("login", identificacionBusqueda.replaceAll("ñ", "n") + "%").ignoreCase())
                    .add(Restrictions.like("login", identificacionBusqueda.replaceAll("n", "ñ") + "%").ignoreCase())
                    );
        }
        if (apellidosBusqueda != null && !apellidosBusqueda.equals("")) {

            c.add(Restrictions.disjunction()
                    .add(Restrictions.like("apellidos", apellidosBusqueda + "%").ignoreCase())
                    .add(Restrictions.like("apellidos", apellidosBusqueda.replaceAll("ñ", "n") + "%").ignoreCase())
                    .add(Restrictions.like("apellidos", apellidosBusqueda.replaceAll("n", "ñ") + "%").ignoreCase())
                    );
        }
        if (nombresBusqueda != null && !nombresBusqueda.equals("")) {
            c.add(Restrictions.disjunction()
                    .add(Restrictions.like("nombres", nombresBusqueda + "%").ignoreCase())
                    .add(Restrictions.like("nombres", nombresBusqueda.replaceAll("ñ", "n") + "%").ignoreCase())
                    .add(Restrictions.like("nombres", nombresBusqueda.replaceAll("n", "ñ") + "%").ignoreCase())
                    );
        }

      if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
         c.add(Restrictions.eq("entidadUE", loggedUser.getEntidadUE()));
         c.add(Restrictions.ne("tipo_usuario", Usuario.ADMINLOCAL));
      } else {
          // Administrador de usuarios general
        c.add(Restrictions.ne("tipo_usuario", Usuario.OPERADORABMLOCAL));
        c.add(Restrictions.ne("tipo_usuario", Usuario.OPERADORLECTURALOCAL));
        c.add(Restrictions.ne("tipo_usuario", Usuario.TRABAJADOR));

        }
        return c.list();

    }

    void onPrepareFromformTipoUsuario() {
    	if(tipoUsuario == null) {
    		tipoUsuario = "";
    	}

    	if(editando) {
    		tipoUsuario = usuario.getTipo_usuario();
    	}
    }

    StreamResponse onActionFromReporteUsuario(Long userID) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("MandatoryParameter_UsuarioID", userID);
        return rep.callReporte(Reportes.REPORTE.B5, Reportes.TIPO.PDF, parametros, context);
    }

/*
    public Object onChangeOfTipoUsuario() {

        tipoUsuario = _request.getParameter("param");
        return entidadZone.getBody();
    }
*/

    @Log
    Object onSuccessFromformTipoUsuario() {
       return new MultiZoneUpdate("editarUsuarioZone", editarUsuarioZone.getBody())
                .add("entidadZone", entidadZone.getBody());
    }
    
    Object onSuccessFromformulariobusqueda(){
        return tabla_usuario.getBody();
    }

    @Log
    Object onSuccessFromformOrganismo(){
        return editarUsuarioZone.getBody();
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("editarUsuarioZone", editarUsuarioZone.getBody())
                .add("tabla_usuario", tabla_usuario.getBody())
                .add("entidadZone", entidadZone.getBody());

        return mu;
    }
    


    public boolean getMuestroOrganismos() {
    	return ((tipoUsuario.equals(Usuario.ADMINLOCAL)) && (loggedUser.getTipo_usuario().equals(Usuario.ADMINGRAL)));
    }
    
    public boolean getEsTrabajador(){
        return tipoUsuario.equals(Usuario.TRABAJADOR);
    }
    
    public GenericSelectModel<Entidad_BK> getBeanOrganismos(){

    	List<Entidad_BK> list;
        Criteria c;
        c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA ));

    	list = c.list();

    	//entidadUE = (EntidadUEjecutora) c.list().get(0); //cargamos el valor por defecto
    	_beanOrganismos = new GenericSelectModel<Entidad_BK>(list,Entidad_BK.class,"denominacion","id",_access);
    	return _beanOrganismos;
    }


    void onPrepareFromFormularioUsuario() {
    	if (loggedUser.getEntidadUE() != null) {
            entidadUE = loggedUser.getEntidadUE();
    	}
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioUsuario()
    {
        String password = null;
    	
    	if (!editando) {
            if(tipoUsuario.equals(Usuario.TRABAJADOR)){
    		Criteria c = session.createCriteria(Trabajador.class);
    		c.add(Restrictions.eq("tipoDocumento", tipoDocumento));
    		c.add(Restrictions.eq("nroDocumento", nroDocumento));                
                if(c.list().isEmpty()) {
                    Logger logger = new Logger();
                    logger.loguearError(session, loggedUser, loggedUser.getId().toString(), 
                                                            Logger.CODIGO_ERROR_USUARIO_UNICO, 
                                                            Errores.ERROR_NO_HAY_TRABAJADOR, Logger.TIPO_OBJETO_USUARIO);
                    formularioUsuario.recordError(Errores.ERROR_NO_HAY_TRABAJADOR);
                    return this;
    		}
                Trabajador trabajador = (Trabajador)c.list().get(0);
                usuario.setApellidos(trabajador.getApellidoPaterno() + " " +trabajador.getApellidoMaterno());
                usuario.setNombres(trabajador.getNombres());
                usuario.setTrabajador(trabajador);
            }
    		Criteria c = session.createCriteria(Usuario.class);
    		c.add(Restrictions.eq("apellidos", usuario.getApellidos()));
    		c.add(Restrictions.eq("nombres", usuario.getNombres()));
    		if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
    			c.add(Restrictions.eq("entidadUE", loggedUser.getEntidadUE()));
    		}
    		if(c.list().size() > 0) {
                    Logger logger = new Logger();
                    logger.loguearError(session, loggedUser, loggedUser.getId().toString(), 
                                                            Logger.CODIGO_ERROR_USUARIO_UNICO, 
                                                            Errores.ERROR_NOMBRE_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
                    formularioUsuario.recordError(Errores.ERROR_NOMBRE_USUARIO_UNICO);
                    return this;
    		}

    		c = session.createCriteria(Usuario.class);
    		c.add(Restrictions.eq("login", usuario.getLogin()));
    		if(c.list().size() > 0) {
    			Logger logger = new Logger();
    			logger.loguearError(session, loggedUser, loggedUser.getId().toString(), 
						Logger.CODIGO_ERROR_USUARIO_EXISTE, 
						Errores.ERROR_LOGIN_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
    			formularioUsuario.recordError(Errores.ERROR_LOGIN_USUARIO_UNICO);
                        return this;
    		}

    		c = session.createCriteria(Usuario.class);
    		c.add(Restrictions.eq("email", usuario.getEmail()));
    		if (loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
    			c.add(Restrictions.eq("entidadUE", loggedUser.getEntidadUE()));
    		}
    		// Pedimos únicos mail por entidadUE
    		if(c.list().size() > 0) {
    			Logger logger = new Logger();
    			logger.loguearError(session, loggedUser, loggedUser.getId().toString(), 
						Logger.CODIGO_ERROR_MAIL_EXISTE, 
						Errores.ERROR_EMAIL_USUARIO_UNICO, Logger.TIPO_OBJETO_USUARIO);
    			formularioUsuario.recordError(Errores.ERROR_EMAIL_USUARIO_UNICO);
                        return this;
    		}
    		
    		// Seteo la password inicial
    		password = usuario.getLogin()+"123.";
    		usuario.setMd5Clave(password);

    	} else { // de editando
    		if( blanquearIntentosFallidos ){
    			usuario.setIntentos_fallidos(0L);
    		}
                
                if(reinitialisarpassword){
                    //aca falta de generar el la contrasena, de mandarla por mail con la function sendPasswordByEmail y de guardar la en la base de dato
                    password = usuario.getLogin()+"123.";
                    usuario.setMd5Clave(password);
                    sendPasswordByEMail(usuario.getEmail(), usuario.getLogin(), password);
                }
    	}
    	
    	usuario.setTipo_usuario(tipoUsuario);
    	if (usuarioDeOrganismo()) {
    		usuario.setEntidadUE(entidadUE);
    	} else {
    		usuario.setEntidadUE(null);
    	}
    	
    	if(editando) {
    		Logger logger = new Logger();
    		logger.loguearOperacionUsuario(session, usuario, Logger.USUARIO_TIPO_OPERACION_EDICION, 
    									   loggedUser);
    	} else {
    		Logger logger = new Logger();
    		logger.loguearOperacionUsuario(session, usuario, Logger.USUARIO_TIPO_OPERACION_CREACION, 
    									   loggedUser);
    		
    		usuario.setUltimo_cambio_clave(null);
    		usuario.setIntentos_fallidos(0L);
    	}

        session.saveOrUpdate(usuario);
     
        if( !editando ){
            sendPasswordByEMail(usuario.getEmail(), usuario.getLogin(), password);
        }
        
        envelope.setContents(helpers.Constantes.USUARIO_EXITO);
    	editando = false;
    	usuario = createNewUsuario();

    	return zonasTotal();
    }
    
    
    
    private void sendPasswordByEMail(String email, String loginid, String password){
	ConfiguracionAcceso ca = (ConfiguracionAcceso) session.load(ConfiguracionAcceso.class, 1L);
    	
//    	String smtpHost = "localhost";
//    	String smtpPort = "25";
//    	String accountMail = "ejemplo@gmail.com";
//    	String accountPass = "la password";
    	
    	String smtpHost    = ca.getSMTP_servidor();
    	String smtpPort    = ca.getSMTP_puerto();
    	String accountMail = ca.getSMTP_usuario();
    	String accountPass = ca.getSMTP_clave();
    	
    	String destination = email;
    	String subject     = "Datos de acceso al sistema Servir";
    	String body        = String.format("Login Id: %s\nPassword: \"%s\"  (Sin las comillas) \n", loginid, password);
    	
    	Properties props = new Properties();
    	props.setProperty("mail.smtp.host", smtpHost);
    	props.setProperty("mail.smtp.port", smtpPort);
    	props.setProperty("mail.smtp.auth", "true");
    	props.setProperty("mail.smtp.starttls.enable", "true");
    	Transport t = null;
    	try {
    		javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props);
        	Message message = new SMTPMessage(mailSession);
			message.addRecipient(RecipientType.TO, new InternetAddress(destination));
			message.setSubject(subject);
			message.setText(body);
			message.setFrom(new InternetAddress(accountMail));
			t = mailSession.getTransport("smtp");
			t.connect(accountMail, accountPass);
			t.sendMessage(message,message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( t!=null ){
				try {
					t.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    private Usuario createNewUsuario(){
    	Usuario usuario = new Usuario();
    	usuario.setIntentos_fallidos(0L);
        tipoDocumento = null;
        nroDocumento = null;
    	return usuario;
    }


        /*
         * reset del formulario
         */
        void onActionFromReset() {
            usuario = createNewUsuario();
            tipoUsuario = usuario.getTipo_usuario();
            entidadUE = usuario.getEntidadUE();
            editando = false;
        }


    /*
         Cargar desde los parámetros
     */
    void onActivate() {
        
    	if(usuario ==null) {
    		usuario = createNewUsuario();
    		editando = false;
                if (loggedUser.getEntidadUE() != null) //No soy dios
                    tipoUsuario = Usuario.OPERADORABMLOCAL;
                else {
                    tipoUsuario = Usuario.ADMINGRAL;
                }

    	} else {
                // Editando
    		if (tipoUsuario == null) {
                        if (usuario.getTipo_usuario() != null)
                            tipoUsuario = usuario.getTipo_usuario();
                        else {
                            tipoUsuario = Usuario.ADMINGRAL;
                        }
    			
    		}
                if (entidadUE == null){
                    entidadUE = usuario.getEntidadUE();
                }
    	}


    }

    void onActivate(Usuario user)
    {

    	if (user == null) {
    		user = createNewUsuario();
    		editando = false;
    	} else {
            
            if(user.getTipo_usuario().equals(Usuario.TRABAJADOR)){
                nroDocumento = user.getTrabajador().getNroDocumento();
                tipoDocumento = user.getTrabajador().getTipoDocumento();
            }
            errorBorrar = null;
            editando = true;
    	}
    	usuario = user;
    }

    Usuario onPassivate()
    {
    	return null;
    }

    private boolean usuarioDeOrganismo() {
    	return (tipoUsuario.equals(Usuario.ADMINLOCAL) ||
    			tipoUsuario.equals(Usuario.OPERADORLECTURALOCAL) ||
    			tipoUsuario.equals(Usuario.OPERADORABMLOCAL) ||
                        tipoUsuario.equals(Usuario.TRABAJADOR) );
     
    }

    
    public boolean getUsuarioDeOrganismo() {
        return this.usuarioDeOrganismo();
    }
     
    
}
