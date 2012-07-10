package helpers;

import java.util.Date;

import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.Log_Accesos;
import com.tida.servir.entities.Log_Operaciones;
import com.tida.servir.entities.Log_Tab_Usuario;
import com.tida.servir.entities.Log_Errores;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import org.apache.tapestry5.annotations.*;

import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;


public class Logger
{
	public static final String LOGIN_STATUS_OK = "OK";
	public static final String LOGIN_STATUS_ERROR = "ERROR";

	public static final String LOGIN_OK = "INGRESO EXITOSO";
	//public static final String LOGIN_MOTIVO_RECHAZO_ERROR = "USUARIO/CLAVE INCORRECTO";
	public static final String LOGIN_MOTIVO_RECHAZO_USERLOCKED = "USUARIO BLOQUEDAO";
    public static final String LOGIN_MOTIVO_RECHAZO_USERLOW = "USUARIO DADO DE BAJA";
    public static final String LOGIN_MOTIVO_RECHAZO_USERNOEXIST = "USUARIO NO EXISTE";
    public static final String LOGIN_MOTIVO_RECHAZO_PASSWORDFAIL = "ERROR INGRESO DE CLAVE";
    public static final String LOGIN_MOTIVO_RECHAZO_PASSWORDCHANGE = "CAMBIO DE CLAVE";
    public static final String LOGIN_MOTIVO_RECHAZO_PASSWORDEXPIRED = "EXPIRACIÓN DE CLAVE";
    public static final String LOGIN_MOTIVO_RECHAZO_PASSWORFIRST = "PRIMER INGRESO";
	
	public static final String CODIGO_ERROR_USUARIO_UNICO = "1";
	public static final String CODIGO_ERROR_USUARIO_EXISTE = "2";
	public static final String CODIGO_ERROR_MAIL_EXISTE = "3";
	public static final String CODIGO_ERROR_FECHA_EGRESO_PREVIA_ACTUAL = "4";
	public static final String CODIGO_ERROR_FECHA_EGRESO_PREVIA_INGRESO = "5";
	public static final String CODIGO_ERROR_FECHA_HASTA_PREVIA_DESDE = "6";
	public static final String CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL = "7";
	public static final String CODIGO_ERROR_FECHA_PREVIA_ACTUAL = "8";
	public static final String CODIGO_ERROR_FECHA_DICTADO_PREVIA_ACTUAL = "9";
	public static final String CODIGO_ERROR_EDAD_MAYOR_18 = "10";
	public static final String CODIGO_ERROR_DUPLICADO = "11";
	
	public static final String USUARIO_TIPO_OPERACION_EDICION = "EDICION DE USUARIO";
	public static final String USUARIO_TIPO_OPERACION_CREACION = "CREACION DE USUARIO";
	
	public static final String TIPO_OBJETO_USUARIO = "Usuario";
	public static final String TIPO_OBJETO_ANT_LABORALES = "Ant_Laborales";
	public static final String TIPO_OBJETO_PUBLICACION = "Publicacion";
	public static final String TIPO_OBJETO_AUSLICPERSONAL = "AusLicPersonal";
	public static final String TIPO_OBJETO_CERTIFICACION = "Certificacion";
	public static final String TIPO_OBJETO_CONSTANCIA_DOCUMENTAL = "Constancia Documental";
	public static final String TIPO_OBJETO_CURSO = "Curso";
	public static final String TIPO_OBJETO_TRABAJADOR = "Trabajador";
	public static final String TIPO_OBJETO_FAMILIAR = "Familiar";
	public static final String TIPO_OBJETO_EVALUACION = "Evaluacion Personal";
	public static final String TIPO_OBJETO_MERITO_DEMERITO = "Meritos / Demeritos";
	public static final String TIPO_OBJETO_ORGANO = "Organo";
	public static final String TIPO_OBJETO_CARGO_ASIGNADO = "Cargo Asignado";
	public static final String TIPO_OBJETO_REMUNERACION_PERSONAL = "Remuneracion Personal";
	public static final String TIPO_OBJETO_TITULO = "Titulo";
	public static final String TIPO_OBJETO_CARGO = "Cargo";
	public static final String TIPO_OBJETO_LEGAJO = "Legajo";
	public static final String TIPO_OBJETO_CONCEPTO_REMUNERATIVO = "Concepto Remunerativo";
	public static final String TIPO_OBJETO_ORGANISMO_INFORMANTE = "Organismo Informante";
	public static final String TIPO_OBJETO_UNIDAD_ORGANICA = "Unidad Organica";
	public static final String TIPO_OBJETO_UNIDAD_EJECUTORA = "Unidad Ejecutora";
	public static final String TIPO_OBJETO_ENTIDAD_UNIDAD_EJECUTORA = "Entidad Unidad Ejecutora";
	
	public static final String CODIGO_OPERACION_ALTA         = "1";
	public static final String CODIGO_OPERACION_MODIFICACION = "2";
	public static final String CODIGO_OPERACION_BAJA         = "3";

	public static final String RESULTADO_OPERACION_OK    = "OK";
	public static final String RESULTADO_OPERACION_ERROR = "ERROR";
    
	
	@Inject
	private HttpServletRequest requestGlobal;

	@Log
    @CommitAfter
	public void loguearError(Session session, Usuario usuario, String id_obj_ope, String cod_error,
							 String info_adicional, String tipo_objeto) {
		Log_Errores l = new Log_Errores();
		
		l.setUsuario(usuario);
		l.setFecha(new Date());
		l.setId_obj_ope(id_obj_ope);
		l.setCod_error(cod_error);
		l.setInfo_adicional(info_adicional);
		l.setTipo_objeto(tipo_objeto);
		
		session.persist(l);
		//session.flush();
	}


	@Log
    @CommitAfter
	public void loguearOperacion(Session session, Usuario usuario, String id_obj_ope, String cod_operacion,
								 String res_operacion, String tipo_objeto) {
		Log_Operaciones l = new Log_Operaciones();
		
		l.setUsuario(usuario);
		l.setFecha(new Date());
		l.setId_obj_ope(id_obj_ope);
		l.setCod_operacion(cod_operacion);
		l.setRes_operacion(res_operacion);
		l.setTipo_objeto(tipo_objeto);
		
		session.persist(l);
		//session.flush();
	}

	
	@Log
    @CommitAfter
	public void loguearOperacionUsuario(Session session, Usuario usuario, String tipo_operacion, Usuario id_usu_ope) {
		Log_Tab_Usuario l = new Log_Tab_Usuario();
		
		l.setUsuario(usuario);
		l.setTipo_operacion(tipo_operacion);
                l.setId_usu_ope(id_usu_ope);
		
		l.setFecha(new Date());
		
		session.persist(l);
		//session.flush();
	}

	
	@Log
    @CommitAfter
	public void loguearAcceso(Session session, Usuario usuario, String status, String motivo_rechazo, String ip_address) {
		Log_Accesos l = new Log_Accesos();
		
		l.setUsuario(usuario);
		l.setStatus(status);
		l.setMotivo_rechazo(motivo_rechazo);
		l.setIp(ip_address);
		l.setFecha(new Date());
		// FIXME: l.setIp(requestGlobal.getRemoteAddr());
		
		session.persist(l);
		//session.flush();
	}
}