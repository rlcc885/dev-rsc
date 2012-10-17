/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Trabajador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Arson
 */
public class ServicioReniec 
{
private Trabajador nuevo;
public String mensajeError;
private String token="";
private final String usuario = "SERVIRWS";
private final String codigo = "MjA5NDA4Nzc=";
private final String  codTxEmp = "RSC";
private final String  dniUserEmp = "09399938";


public ServicioReniec(){

}    
public Trabajador getTrabajadorWS() {
   return nuevo;
}
public ServicioReniec(String token){
    this.token = token;
}

@Log
public Boolean validarToken()
 {
     System.out.println("estado del TOKEN : "+token);
     
  if (token==null||"".equals(token)) { mensajeError="Error no identificado"; return false; }    
  if ("2".equals(token)) { mensajeError="Error en la operación"; return false; }
  if ("3".equals(token)) { mensajeError="La Consulta esta fuera del Horario Permitido";return false; }
  if ("4".equals(token)) { mensajeError="Usuario no valido";return false;  }
  if ("5".equals(token)) { mensajeError="La Consulta excedió la cantidad máxima permitida";return false; } 
  if ("6".equals(token)) { mensajeError="Usuario no existe";return false; }
  
  //OK
 return true;
 }

@Log
public void cargarTrabajador(List<String> resultado) throws ParseException{

if (validarEstadoConsulta(resultado.get(0))==true)
{
    for (int i=0;i<resultado.size();i++)
    {
    System.out.println("REGEXP "+i+" "+resultado.get(i));
    }
    
//    Criteria c = session.createCriteria(DatoAuxiliar.class);
    // caragr datos del ws a la entidad
    nuevo = new Trabajador();
    nuevo.setApellidoPaterno(resultado.get(1));
    nuevo.setApellidoMaterno(resultado.get(2));
    nuevo.setNombres(resultado.get(4));

 /*   nuevo.setCod_dom_dept(null);
    nuevo.setCod_dom_dist(null);
    nuevo.setCod_dom_prov(null);*/
    nuevo.setDomicilioDireccion(resultado.get(11));
/*    c.add(Restrictions.eq("nombreTabla","ESTADOCIVIL"));
    c.add(Restrictions.eq("codigo",Long.getLong(resultado.get(5))));
    nuevo.setEstadocivil((DatoAuxiliar)c.uniqueResult());*/
    if ((Integer.parseInt(resultado.get(13)))==1){nuevo.setSexo("M");}else{nuevo.setSexo("F");}
    SimpleDateFormat formatoFecha;
    formatoFecha = new SimpleDateFormat("yyyyMMdd");
    nuevo.setFechaNacimiento(formatoFecha.parse(resultado.get(14)));                  
    nuevo.setNroDocumento(resultado.get(20));
    // ASIGNACION DE MARCA (QUE IDENTIFIQUE QUE FUE BUSCADO POR EL WS)    
    nuevo.setConsultado_WS(Boolean.TRUE);
}    
    
    
}
@Log
public Boolean validarEstadoConsulta(String estado){

    System.err.println("estado validar consulta: "+estado);
    
    if ("NTP".equals(estado)) { mensajeError="No tienen permisos de acceso al método consulta.";return false; } 
    if ("SINV".equals(estado)) { mensajeError="Código de Sesión ingresado inválido.";return false; } 
    if ("UNL".equals(estado)) { mensajeError="Aplicación que consulta a Web Service no se ha autenticado.";return false;} 
    if ("5".equals(estado)) { mensajeError="Excedió el máximo número de consultas asignadas por día.";return false;} 
    if ("3".equals(estado)) { mensajeError="Esta consultando en un día y hora no permitido según convenio.";return false;} 
    if ("DNE".equals(estado)) { mensajeError="El DNI consultado es inválido.";return false;} 
    if ("DNV".equals(estado)) { mensajeError="El DNI del usuario de la empresa es inválido. No está autorizado a consultar.";return false;} 
    if ("0002".equals(estado)) { mensajeError="El Servidor no puede atender el requerimiento.";return false;}
    //OK
    if ("0000".equals(estado)) { return true;}

    mensajeError = "Error no identificado";
    return false;
}

private org.reniec.rel.SRELServiceService service;
private org.reniec.rel.SRELService port;

@Log
public void obtenerToken(){
    
     service = new org.reniec.rel.SRELServiceService();
     port = service.getSRELServicePort();
     // Obtenemos el token para realizar la consulta
     token = port.getSession(usuario, codigo);
}

@Log
public List<String> obtenerResultado(String dni){

      System.err.println("dni : "+dni);
      
      List<String> result = port.getRegIdentConsolidada2(token, usuario, codTxEmp, dniUserEmp, dni);
  
        System.err.println("resultado : "+result.size());
        
    return result;
}

}
