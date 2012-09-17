/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.tida.servir.entities.ConexionSanciones;
import com.tida.servir.entities.Sancion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author Morgan
 */
public class Sanciones {
    
    public static ResultSet results;

    public static Boolean tieneSanciones(String tipoDocumento, String nroDocumento, Session session, List<String> errores) {

        ConexionSanciones cs = (ConexionSanciones) session.load(ConexionSanciones.class, 1L);
        try
        {
            Class.forName(cs.getDriver());
            Connection con = DriverManager.getConnection(cs.getServidor()+cs.getTablespace(),cs.getUsuario(),cs.getPassword());  
            PreparedStatement ps = con.prepareStatement(cs.getRequest());
            ps.setString(1, tipoDocumento);
            ps.setString(2, nroDocumento);
            /* para lo del peru
            ps.setString(1, cs.getTabla_rns_persona());
            ps.setString(2, cs.getTabla_rns_funcionario_persona());
            ps.setString(3, tipoDocumento);
            ps.setString(4, cs.getTabla_rns_funcionario_persona());
            ps.setString(5, nroDocumento);
            ps.setString(6, cs.getTabla_adm_institucion());
            ps.setString(7, cs.getTabla_rns_sancion());
            */
            results = ps.executeQuery();
            
            if(results.next()){
                return Boolean.TRUE;
            }

        /*select per.* from ? per join ? fup on per.id_tipo_doc_identif = ? join ? fup on per.nro_doc_identif = ? join ? ins on fup.id_institucion = ins.id_institucion join ? san on per.id_persona = san.id_persona left join adm_estado_sancion est on est.cod_estado_sancion = per.cod_estado_sancion left join adm_tipo_sancion tip on tip.id_tipo_sancion = san.id_tipo_sancion where 1 = 1 and cod_estado_sancion='S'*/

        /*select per.*
        from RNS_PERSONA per
        join RNS_FUNCIONARIO_PERSONA fup on per.id_tipo_doc_identif = tipoDocumento
        join RNS_FUNCIONARIO_PERSONA fup on per.nro_doc_identif = nroDocumento
        join ADM_INSTITUCION ins on fup.id_institucion = ins.id_institucion
        join RNS_SANCION san on per.id_persona = san.id_persona
        left join adm_estado_sancion est on est.cod_estado_sancion = per.cod_estado_sancion
        left join adm_tipo_sancion tip on tip.id_tipo_sancion = san.id_tipo_sancion
        where 1 = 1 and cod_estado_sancion='S'
         */


        }
        catch (Exception e)
        {
            System.out.println("--------------error conexion sancion "+e);
        } 

        return Boolean.FALSE;
    }

//    public static Sancion muestroSanciones(String tipoDocumento, String nroDocumento, Session session, List<String> errores) {
//        Sancion sancion = new Sancion();
//        
//        try {
//            sancion.setApellidos(results.getString(3));
//            sancion.setFinInabilitacion(results.getDate(7));
//            sancion.setInabilitacion(results.getString(6));
//            sancion.setInstitucion(results.getString(4));
//            sancion.setNombres(results.getString(2));
//            sancion.setTipoSancion(results.getString(5));
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Sanciones.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//        return sancion;
//    }
//
//    public static String formatoEnvelope(Sancion sancion) {
//        if(sancion == null)
//            return "Error en el formato de sancion!";
//        
//        String pantalla = null;
//
//        pantalla = "Apellidos: "
//                + sancion.getApellidos() + ". Nombres: "
//                + sancion.getNombres() + ". Institucion: "
//                + sancion.getInstitucion() + ". Tipo de sancion: "
//                + sancion.getTipoSancion() + ". Inhabilitacion: "
//                + sancion.getInabilitacion() + ". Fin de inhabilitacion: "
//                + sancion.getFinInabilitacion();
//
//        return pantalla;
//    }
}
