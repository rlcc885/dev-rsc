/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import helpers.Helpers;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Morgan
 */
public class CreadorDesdeDB {



    /** Obtener Unidad Organica con codigo Unidad Organica
     * 
     * @param codigo_unidad_ejecutora
     * @param session
     * @param errores
     * @return la unidad ejecutora buscada
     */
    /*public static UnidadEjecutora getUnidadEjecutoraWithCodigoUnidadEjecutora(int codigo_unidad_ejecutora, Session session, List<DatoAuxiliar> errores) {

        if (codigo_unidad_ejecutora == 0) {
            errores.add("El codigo unidad ejecutora del archivo Organismos Informantes esta vacio!");
            return null;
        }

        Criteria c = session.createCriteria(UnidadEjecutora.class);
        c.add(Restrictions.eq("cod_mef_ue", codigo_unidad_ejecutora));

        if (c.list().isEmpty()) {
            return null;
        }

        return (UnidadEjecutora) c.list().get(0);
    }*/

    /** Obtener Organismo Informante con Codigo Organismo Informante
     * 
     * @param codigo_entidadUE
     * @param session
     * @param errores
     * @return el ogranismo informante buscado
     */
    public static Entidad_BK getEntidadUEjecutoraWithCodigoEntidadUE(String codigo_entidadUE, Session session, List<String> errores) {

        if (codigo_entidadUE == null) {
            return null;
        }
        
        if(codigo_entidadUE.trim().equals("")){
            return null;
        }

        Criteria c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.eq("codigoEntidadUE", codigo_entidadUE));

        if (c.list().isEmpty()) {
            return null;
        }

        return (Entidad_BK) c.list().get(0);
    }

    /** Obtener Trabajador con Tipo y Numero de documento
     * 
     * @param tipoDocumento
     * @param numeroDocumento
     * @param session
     * @param errores
     * @return el trabajador buscado
     */
    public static Trabajador getTrabajadorWithTipoNumeroDocumento(String tipoDocumento, String numeroDocumento, Session session, List<String> errores) {

        if ((tipoDocumento == null) || (numeroDocumento == null)) {
            return null;
        }
        
        if ((tipoDocumento.trim().equals("")) || (numeroDocumento.trim().equals(""))) {
            return null;
        }
        
        Criteria c = session.createCriteria(Trabajador.class);
        c.add(Restrictions.eq("tipoDocumento", tipoDocumento));
        c.add(Restrictions.eq("nroDocumento", numeroDocumento));
        
        if (c.list().isEmpty()) {
            return null;
        }

        return (Trabajador) c.list().get(0);

    }

    /** Obtener Legajo con Codigo Legajo
     * 
     * @param codigo_legajo
     * @param session
     * @param errores
     * @return el legajo buscado
     */
    /*public static Legajo getLegajoWithCodigoLegajo(String codigo_legajo, Session session, List<String> errores) {

        if (codigo_legajo == null){
            return null;
        }
        
        if (codigo_legajo.trim().equals("")){
            return null;
        }

        Criteria c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("cod_legajo", codigo_legajo));

        if (c.list().isEmpty()) {
            return null;
        }

        return (Legajo) c.list().get(0);
    }*/

    
    /** obtener Legajo con Trabajador y Organismo
     * 
     * @param session
     * @param tipoDocumento
     * @param numeroDocumento
     * @param errores
     * @param codigo_entidadUE
     * @return el legajo buscado
     */
    public static Legajo getLegajoWithTrabajadorEntidadUE(Session session, Trabajador trabajador, List<String> errores, Entidad_BK entidadUE) {

        Criteria c = session.createCriteria(Legajo.class);
        c.createAlias("trabajador", "trabajador");
        c.createAlias("entidadUE", "entidadUE");
        c.add(Restrictions.eq("trabajador.tipoDocumento", trabajador.getTipoDocumento()));
        c.add(Restrictions.eq("trabajador.nroDocumento", trabajador.getNroDocumento()));
        c.add(Restrictions.eq("entidadUE.codigoEntidadUE", entidadUE.getCodigoEntidadUE()));

        if (c.list().isEmpty()) {
            return null;
        }

        return (Legajo) c.list().get(0);
    }

    /**obtener Unidad Organica con Codigo Unidad Organica
     * 
     * @param codigo_und_organica
     * @param session
     * @param errores
     * @return la unidad organica buscada
     * 
     */
    public static UnidadOrganica getUnidadOrganicaWithCodigoUnidadOrganica(String codigo_und_organica, Entidad_BK eue, Session session, List<String> errores) {

        if ((codigo_und_organica == null)){
            return null;
        }
        
        if((codigo_und_organica.trim().equals(""))) {
                return null;
        }

        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.like("cod_und_organica", codigo_und_organica));
        c.add(Restrictions.eq("entidadUE", eue));

        if (c.list().isEmpty()) {
            System.out.println("");
            return null;
        }

        return (UnidadOrganica) c.list().get(0);
    }

    /** Obtener Cargo con Codigo Cargo
     *  
     * @param codigo_cargo
     * @param session
     * @param errores
     * @return el cargo buscado
     */
    public static Cargoxunidad getCargoWithCodigoCargoCodigoEntidadUE(String codigo_cargo, String codigo_entidadUE, Session session, List<String> errores) {

        if((codigo_cargo == null)||(codigo_entidadUE == null)){
            return null;
        }
        
        if ((codigo_cargo.trim().equals("")) || (codigo_entidadUE.trim().equals(""))) {
                return null;
        }
        
        Criteria c = session.createCriteria(Cargoxunidad.class);
        c.createAlias("und_organica.entidadUE", "entidadUE");
        c.add(Restrictions.like("entidadUE.codigoEntidadUE", codigo_entidadUE));
        c.add(Restrictions.like("cod_cargo", codigo_cargo));
      
        
        if(!c.list().isEmpty()){
            return (Cargoxunidad) c.list().get(0);
        }else{
            return null;
        }
        
    }

    public static Legajo getLegajoWithCodigoEntidadUECodigoLegajo(Entidad_BK eue, String codigo_legajo, Session session, List<String> errores) {

        if ((codigo_legajo == null) || (eue == null)) {
            return null;
        }
        
        if(codigo_legajo.trim().equals("")){
            return null;
        }

        Criteria c = session.createCriteria(Legajo.class)
                .createAlias("entidadUE", "entidadUE")
                .add(Restrictions.eq("entidadUE.codigoEntidadUE", eue.getCodigoEntidadUE()))
                .add(Restrictions.eq("cod_legajo", codigo_legajo));

        if (c.list().isEmpty()) {
            return null;
        }

        return (Legajo) c.list().get(0);
    }

    /** Obtener CargoAsignado con Legajo y Cargo
     * 
     * @param session
     * @param tipoDocumento
     * @param numeroDocumento
     * @param codigoOrganismo
     * @param errores
     * @return el cargo asignado buscado
     */
    public static CargoAsignado getCargoAsignadoWithLegajoCargo(Session session, Cargoxunidad cargo, Legajo legajo, List<String> errores) {

        if (cargo == null || legajo == null) {
            return null;
        }

        if (cargo.getCod_cargo().trim().equals("") || legajo.getCod_legajo().trim().equals("")) {
            return null;
        }

        Criteria c = session.createCriteria(CargoAsignado.class)
                .createAlias("legajo", "legajo")
                .createAlias("cargo", "cargo")
                .add(Restrictions.eq("cargo.cod_cargo", cargo.getCod_cargo()))
                .add(Restrictions.eq("legajo.cod_legajo", legajo.getCod_legajo()));

        if (c.list().isEmpty()) {
            return null;
        }

        return (CargoAsignado) c.list().get(0);
    }

    /** verifica si existe el legajo buscando por codigo organismo y codigo legajo
     * 
     * @param session
     * @param codigo_legajo
     * @param errores
     * @param codigo_entidadUE
     * @return true eue false si existe eue no el legajo buscado
     */
    /*public static boolean legajoFromOrganismoCodigoLegajo(Session session, String codigo_legajo, List<String> errores, String codigo_entidadUE) {

        if (codigo_legajo == null && codigo_entidadUE == null) {
            return false;
        }
        
        if (codigo_legajo.trim().equals("") && codigo_entidadUE.trim().equals("")) {
            return false;
        }

        EntidadUEjecutora eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(codigo_entidadUE, session, errores);
        if (eue == null) {
            errores.add("Este codigo organismo " + codigo_entidadUE + " no existe en la base de dato Organismo Informante!");
            return false;
        }

        Criteria c = session.createCriteria(Legajo.class)
                .add(Restrictions.eq("organismoInformante", eue))
                .add(Restrictions.eq("cod_legajo", codigo_legajo));

        if (c.list().isEmpty()) {
            return false;
        }

        return c.list().size() > 0;
    }*/

    /** verifica si existe el legajo buscando por codigo organismo y tipo / numero de documento
     * 
     * @param session
     * @param tipoDocumento
     * @param numeroDocumento
     * @param errores
     * @param codigo_entidadUE
     * @return true eue false si existe el legajo buscado
     */
    /*public static boolean legajoFromTrabajadorOrganismo(Session session, String tipoDocumento, String numeroDocumento, List<String> errores, String codigo_entidadUE) {

        Trabajador t = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, numeroDocumento, session, errores);
        if (t == null) {
            errores.add("No hay un trabajador que corresponde a este tipo de documento " + tipoDocumento + " y a este numero de documento " + numeroDocumento + "!");
            return false;
        }

        EntidadUEjecutora eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(codigo_entidadUE, session, errores);
        if (eue == null) {
            errores.add("Este codigo organismo " + codigo_entidadUE + " no existe en la base de dato Organismo Informante!");
            return false;
        }

        Criteria c = session.createCriteria(Legajo.class)
                .add(Restrictions.eq("trabajador", t))
                .add(Restrictions.eq("organismoInformante", eue));

        if (c.list().isEmpty()) {
            return false;
        }

        return c.list().size() > 0;
    }*/

    public static ConceptoRemunerativo getConceptoRemunerativoWithCodigoConceptoRemunerativo(String codigo_entidadUE, String codigo_concepto, Session session, List<String> errores) {

        if (codigo_concepto == null || codigo_entidadUE == null) {
            return null;
        }

        if (codigo_concepto.trim().equals("") || codigo_entidadUE.trim().equals("")) {
            return null;
        }
        
        Criteria c = session.createCriteria(ConceptoRemunerativo.class);
        
        c.createAlias("entidadUE", "entidadUE")
            .add(Restrictions.like("entidadUE.codigoEntidadUE", codigo_entidadUE))
            .add(Restrictions.eq("codigo", codigo_concepto));

        if (c.list().isEmpty()) {
            return null;
        }

        return (ConceptoRemunerativo) c.list().get(0);
    }

    public static List<RemuneracionPersonalCSV> generarParaCSVRemuneracionPersonal(CargoAsignado ca, Entidad_BK eue) {
        List<RemuneracionPersonalCSV> lrpcsv = new LinkedList<RemuneracionPersonalCSV>();
        
        if(eue == null && ca == null){
            return null;
        }

        for (RemuneracionPersonal rp : ca.getRemuneracionesPersonales()) {
            RemuneracionPersonalCSV rpcsv = new RemuneracionPersonalCSV();
            rpcsv.setCodigo_entidadUE(eue.getCodigoEntidadUE());
            if(ca.getCargoxunidad() != null)
                rpcsv.setCodigo_cargo(ca.getCargoxunidad().getCod_cargo());
            else
                rpcsv.setCodigo_cargo("");
            rpcsv.setTipo_documento(ca.getTrabajador().getTipoDocumento());
            rpcsv.setNro_documento(ca.getTrabajador().getNroDocumento());
            if(rp.getConceptoRemunerativo() != null)
                rpcsv.setCodigo_concepto_remunerativo(rp.getConceptoRemunerativo().getCodigo());
            else
                rpcsv.setCodigo_concepto_remunerativo("");
            rpcsv.setImporte(rp.getImporte().toString());
            lrpcsv.add(rpcsv);
        }

        return lrpcsv;
    }

    public static List<EvaluacionPersonalCSV> generarParaCSVEvaluacionPersonal(CargoAsignado ca, Entidad_BK eue) {
        List<EvaluacionPersonalCSV> lepcsv = new LinkedList<EvaluacionPersonalCSV>();

        if(eue == null && ca == null){
            return null;
        }

        for (EvaluacionPersonal ep : ca.getEvaluacionesPersonales()) {
            EvaluacionPersonalCSV epcsv = new EvaluacionPersonalCSV();
            epcsv.setCalificacion_procentaje(ep.getCalificacion());
            if(ca.getCargoxunidad() != null)
                epcsv.setCodigo_cargo(ca.getCargoxunidad().getCod_cargo());
            else
                epcsv.setCodigo_cargo("");
            epcsv.setCodigo_entidadUE(eue.getCodigoEntidadUE());
            epcsv.setFecha_desde(ep.getFec_desde());
            epcsv.setFecha_hasta(ep.getFec_hasta());
            epcsv.setNro_documento(ca.getTrabajador().getNroDocumento());
            epcsv.setTipo(ep.getTipo());
            epcsv.setTipo_documento(ca.getTrabajador().getTipoDocumento());
            lepcsv.add(epcsv);
        }

        return lepcsv;
    }

    public static List<AusLicPersonalCSV> generarParaCSVAusLicPersonal(CargoAsignado ca, Entidad_BK eue) {
        List<AusLicPersonalCSV> lalpcsv = new LinkedList<AusLicPersonalCSV>();

        if(eue == null && ca == null){
            return null;
        }
        
        for (AusLicPersonal alp : ca.getAusLicPersonales()) {
            AusLicPersonalCSV alpcsv = new AusLicPersonalCSV();

            if(ca.getCargoxunidad() != null)
                alpcsv.setCodigo_cargo(ca.getCargoxunidad().getCod_cargo());
            else
                alpcsv.setCodigo_cargo("");
            alpcsv.setCodigEntidadUE(eue.getCodigoEntidadUE());
            alpcsv.setFecha_desde(alp.getFec_desde());
            alpcsv.setFecha_hasta(alp.getFec_hasta());
            alpcsv.setMotivo(alp.getMotivo());
            alpcsv.setNro_documento(ca.getTrabajador().getNroDocumento());
            alpcsv.setTipo(alp.getTipo());
            alpcsv.setTipo_documento(ca.getTrabajador().getTipoDocumento());
            lalpcsv.add(alpcsv);
        }

        return lalpcsv;
    }

    public static DatoAuxiliar verificacionCodigoDatoAuxiliar(String tabla, String codigo, List<String> errores, Session session) {

        Long codigoDa = null;
        
        if (codigo != null) {
            if (!codigo.trim().equals("")) {
                try{
                    codigoDa = Long.parseLong(codigo);
                }catch(NumberFormatException nfe){
                    errores.add("Tabla: " +tabla+ " Codigo enviado: "+ codigo+ ". Error: "+helpers.Errores.EL_CODIGO_DEBE_SER_NUMERICO);
                    return null;
                }
                DatoAuxiliar datoaux = Helpers.getDatoAuxiliar(tabla, codigoDa, session);
                
                
                if (datoaux != null) {
                    return datoaux;
                }else{
                    errores.add("Tabla: " +tabla+ " Codigo enviado: "+ codigoDa+ ". Error: "+helpers.Errores.CODIGO_NO_ENCONTRADO);
                }
                
            }
        }
        
        return null;
    }

    public static String verificacionValorDatoAuxiliar(String tabla, String codigo, List<String> errores, Session session) {
        String da = "";
        long cod;

        if (codigo != null) {
            if (!codigo.trim().equals("")) {
                try{
                    cod = Long.decode(codigo);
                } catch (NumberFormatException e) {
                    errores.add("Tabla: " +tabla+ " .Codigo enviado: "+ codigo+ ". Error: "+helpers.Errores.EL_CODIGO_DEBE_SER_NUMERICO);
                    return da;
                }
                DatoAuxiliar datostr = Helpers.getDatoAuxiliar(tabla, cod, session);
                if (datostr != null) {
                    try {
                        return datostr.getValor();
                    } catch (NumberFormatException e) {
                        errores.add("Tabla: " +tabla+ " .Codigo enviado: "+ codigo+ ". Error: "+helpers.Errores.EL_CODIGO_DEBE_SER_NUMERICO);
                        return da;
                    }

                } else {
                    errores.add("Tabla: " +tabla+ " Codigo enviado: "+ codigo+ ". Error: "+helpers.Errores.CODIGO_NO_ENCONTRADO);
                    return da;
                }
            }
        }

        return da;
    }
    
    public static String getCodigoFromValorDatoAuxiliar(String tabla, String valor, List<String> errores, Session session) {
        String da = "";

        if (valor != null) {
            if (!valor.trim().equals("")) {
                DatoAuxiliar datostr = Helpers.getDatoAuxiliar(tabla, valor, session);
                if (datostr != null) {
                    try {
                        return String.valueOf(datostr.getCodigo());
                    } catch (NumberFormatException e) {
                        errores.add("Tabla: " +tabla+ " Valor enviada: "+ valor+ ". Error: "+helpers.Errores.EL_CODIGO_DEBE_SER_NUMERICO);
                        return da;
                    }
                } else {
                    return da;
                }
            }
        }

        return da;
    }
}
