/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion; 
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import java.util.Date;

/**
 *
 * @author Morgan
 */
public class CopiadorEntidades {

    public static void entidadUE(Entidad_BK origen, Entidad_BK destino) {
        destino.setClas_funcional(origen.getClas_funcional());
        destino.setCod_mef_ue(origen.getCod_mef_ue());
        destino.setCod_ubi_dept(origen.getCod_ubi_dept());
        destino.setCod_ubi_dist(origen.getCod_ubi_dist());
        destino.setCod_ubi_prov(origen.getCod_ubi_prov());
        destino.setCorreoElectronicoInstitucional(CopiadorEntidades.verifacionNull(origen.getCorreoElectronicoInstitucional()));
        destino.setCorreoElectronicoJefeRRHH(CopiadorEntidades.verifacionNull(origen.getCorreoElectronicoJefeRRHH()));
        destino.setCorreoElectronicoTitular(CopiadorEntidades.verifacionNull(origen.getCorreoElectronicoTitular()));
        destino.setCue_elemento(CopiadorEntidades.verifacionNull(origen.getCue_elemento()));
        destino.setCue_entidad(CopiadorEntidades.verifacionNull(origen.getCue_entidad()));
        destino.setDenominacion(CopiadorEntidades.verifacionNull(origen.getDenominacion()));
        destino.setDirecion(CopiadorEntidades.verifacionNull(origen.getDirecion()));
        destino.setEstado(CopiadorEntidades.verifacionNull(origen.getEstado()));
        destino.setJefeRRHHEntidad(CopiadorEntidades.verifacionNull(origen.getJefeRRHHEntidad()));
        destino.setLocalidad(CopiadorEntidades.verifacionNull(origen.getLocalidad()));
        destino.setNivel_gobierno(CopiadorEntidades.verifacionNull(origen.getNivel_gobierno()));
        destino.setPliego(CopiadorEntidades.verifacionNull(origen.getPliego()));
        destino.setRuc(CopiadorEntidades.verifacionNull(origen.getRuc()));
        destino.setSector_gobierno(CopiadorEntidades.verifacionNull(origen.getSector_gobierno()));
        destino.setTEJefeRRHH(CopiadorEntidades.verifacionNull(origen.getTEJefeRRHH()));
        destino.setTEMovilJefeRRHH(CopiadorEntidades.verifacionNull(origen.getTEMovilJefeRRHH()));
        destino.setTitularEntidad(CopiadorEntidades.verifacionNull(origen.getTitularEntidad()));
        destino.setTrabajadorAgregaDatos(CopiadorEntidades.verifacionNull(origen.getTrabajadorAgregaDatos()));
    }
    
    public static void unidadOrganica(UnidadOrganica origen, UnidadOrganica destino) {
        System.out.println("------------------- Ubigeo origen: " + origen.getCod_ubi_dept());

        destino.setCod_ubi_dept(origen.getCod_ubi_dept());
        System.out.println("------------------- Ubigeo origen despues: " + origen.getCod_ubi_dept());
        System.out.println("------------------- Ubigeo destino: " + destino.getCod_ubi_dept());

        destino.setCod_ubi_dist(origen.getCod_ubi_dist());
        destino.setCod_ubi_prov(origen.getCod_ubi_prov());
        if(origen.getCargos()!= null)
            destino.setCargos(origen.getCargos());
        destino.setEstado(CopiadorEntidades.verifacionNull(origen.getEstado()));
        destino.setNivel(CopiadorEntidades.verifacionNull(origen.getNivel()));
        destino.setCod_und_organica(CopiadorEntidades.verifacionNull(origen.getCod_und_organica()));
        destino.setDen_und_organica(CopiadorEntidades.verifacionNull(origen.getDen_und_organica()));
        destino.setLocalidad(CopiadorEntidades.verifacionNull(origen.getLocalidad()));
        destino.setEntidad(origen.getEntidad());
        destino.setCue(CopiadorEntidades.verifacionNull(origen.getCue()));
        destino.setSigla(CopiadorEntidades.verifacionNull(origen.getSigla()));
        //destino.setTipoActividad(CopiadorEntidades.verifacionNull(origen.getTipoActividad()));
        destino.setUnidadorganica(origen.getUnidadorganica());
    }
    
    public static void cargo(Cargoxunidad origen, Cargoxunidad destino) {
        destino.setCod_cargo(CopiadorEntidades.verifacionNull(origen.getCod_cargo()));
        //destino.setClasificacion_funcional(origen.getClasificacion_funcional());
        destino.setCtd_puestos_total(CopiadorEntidades.verifacionNull(origen.getCtd_puestos_total()));
        destino.setPresupuestado_PAP(CopiadorEntidades.verifacionNull(origen.getPresupuestado_PAP()));
        //destino.setSituacion_CAP(CopiadorEntidades.verifacionNull(origen.getSituacion_CAP()));
        destino.setDec_jurada_byr(CopiadorEntidades.verifacionNull(origen.getDec_jurada_byr()));
        destino.setDen_cargo(CopiadorEntidades.verifacionNull(origen.getDen_cargo()));
        destino.setEstado(CopiadorEntidades.verifacionNull(origen.getEstado()));
        //destino.setHoras_x_sem(CopiadorEntidades.verifacionNull(origen.getHoras_x_sem()));
        destino.setRegimenlaboral(origen.getRegimenlaboral());
        //destino.setReq_hab_profesional(CopiadorEntidades.verifacionNull(origen.getReq_hab_profesional()));
        destino.setUnidadorganica(origen.getUnidadorganica());
        destino.setGrupoOcupacional(origen.getGrupoOcupacional());
        destino.setNivelRemunerativo(origen.getNivelRemunerativo());
        //destino.setPersonasCargo(CopiadorEntidades.verifacionNull(origen.getPersonasCargo()));
    }
    
    public static void conceptoRemunerativo(ConceptoRemunerativo origen, ConceptoRemunerativo destino) {
        destino.setEntidad(origen.getEntidad());
        destino.setCodigo(CopiadorEntidades.verifacionNull(origen.getCodigo()));
        destino.setDescripcion(CopiadorEntidades.verifacionNull(origen.getDescripcion()));
        destino.setConceptoStd(CopiadorEntidades.verifacionNull(origen.getConceptoStd()));
        destino.setPeriodicidad(CopiadorEntidades.verifacionNull(origen.getPeriodicidad()));
        destino.setClasificacion(CopiadorEntidades.verifacionNull(origen.getClasificacion()));
        destino.setSustento_legal(CopiadorEntidades.verifacionNull(origen.getSustento_legal()));
    }
    
    public static void trabajador(Trabajador origen, Trabajador destino) {
        if(origen.getAnt_Laborales() != null)
            destino.setAnt_Laborales(origen.getAnt_Laborales());
        destino.setApellidoMaterno(CopiadorEntidades.verifacionNull(origen.getApellidoMaterno()));
        destino.setApellidoPaterno(CopiadorEntidades.verifacionNull(origen.getApellidoPaterno()));
        if(origen.getCargosAsignados() != null)
            destino.setCargosAsignados(origen.getCargosAsignados());
        if(origen.getCertificaciones() != null)
            destino.setCertificaciones(origen.getCertificaciones());
        if(origen.getCursos() != null)
            destino.setCursos(origen.getCursos());
        destino.setCod_dom_dept(origen.getCod_dom_dept());
        destino.setCod_dom_dist(origen.getCod_dom_dist());
        destino.setCod_dom_prov(origen.getCod_dom_prov());
//        destino.setCod_ubi_dept(origen.getCod_ubi_dept());
//        destino.setCod_ubi_dist(origen.getCod_ubi_dist());
//        destino.setCod_ubi_prov(origen.getCod_ubi_prov());
        destino.setCodigoOSCE(CopiadorEntidades.verifacionNull(origen.getCodigoOSCE()));
        destino.setDomicilioCodigoPostal(CopiadorEntidades.verifacionNull(origen.getDomicilioCodigoPostal()));
        destino.setDomicilioDireccion(CopiadorEntidades.verifacionNull(origen.getDomicilioDireccion()));
        destino.setEmailLaboral(CopiadorEntidades.verifacionNull(origen.getEmailLaboral()));
        destino.setEmailPersonal(CopiadorEntidades.verifacionNull(origen.getEmailPersonal()));
        destino.setEmergenciaDomicilio(CopiadorEntidades.verifacionNull(origen.getEmergenciaDomicilio()));
        destino.setEmergenciaNombre(CopiadorEntidades.verifacionNull(origen.getEmergenciaNombre()));
//        destino.setEmergenciaTelefonos(CopiadorEntidades.verifacionNull(origen.getEmergenciaTelefonos()));
        destino.setEmergenciaTelefonoAlternativo1(CopiadorEntidades.verifacionNull(origen.getEmergenciaTelefonoAlternativo1()));
        destino.setEmergenciaTelefonoAlternativo2(CopiadorEntidades.verifacionNull(origen.getEmergenciaTelefonoAlternativo2()));
        destino.setEsSalud(CopiadorEntidades.verifacionNull(origen.getEsSalud()));
        //destino.setEstadocivil(CopiadorEntidades.verifacionNull(origen.getEstadocivil()));
        destino.setFechaNacimiento(CopiadorEntidades.verifacionNull(origen.getFechaNacimiento()));
        destino.setFormacionprofesional(origen.getFormacionprofesional());
        if(origen.getFamiliares() != null)
            destino.setFamiliares(origen.getFamiliares());
        destino.setFormacionInfAdicional(CopiadorEntidades.verifacionNull(origen.getFormacionInfAdicional()));
//        destino.setGrupoSanguineo(CopiadorEntidades.verifacionNull(origen.getGrupoSanguineo()));
        if(origen.getMeritosdemeritos() != null)
            destino.setMeritosdemeritos(origen.getMeritosdemeritos());
//        destino.setNacionalidad(CopiadorEntidades.verifacionNull(origen.getNacionalidad()));
        //destino.setNivelInstruccion(CopiadorEntidades.verifacionNull(origen.getNivelinstruccion()));
        destino.setNombres(CopiadorEntidades.verifacionNull(origen.getNombres()));
        destino.setNroCertificadoCONADIS(CopiadorEntidades.verifacionNull(origen.getNroCertificadoCONADIS()));
        destino.setNroRUC(CopiadorEntidades.verifacionNull(origen.getNroRUC()));
 //       destino.setPais(CopiadorEntidades.verifacionNull(origen.getPais()));
        if(origen.getPublicaciones() != null)
            destino.setPublicaciones(origen.getPublicaciones());
  //      destino.setRegimenPensionario(CopiadorEntidades.verifacionNull(origen.getRegimenPensionario()));
        destino.setSexo(CopiadorEntidades.verifacionNull(origen.getSexo()));
        //destino.setTipodiscapacidad(CopiadorEntidades.verifacionNull(origen.getTipodiscapacidad()));
//        if(origen.getTitulos() != null)
//            destino.setTitulos(origen.getTitulos());
    }
    
    public static void legajo(Legajo origen, Legajo destino){
        //destino.setCod_legajo(CopiadorEntidades.verifacionNull(origen.getCod_legajo()));
        //destino.setEntidadUE(CopiadorEntidades.verifacionNull(origen.getEntidadUE()));
        destino.setTrabajador(origen.getTrabajador());
        if(origen.getConstanciasDocumentales() != null)
            destino.setConstanciasDocumentales(origen.getConstanciasDocumentales());
    }
    
    public static void cargoAsignado(CargoAsignado origen, CargoAsignado destino){
        if(origen.getAusLicPersonales() != null)
            destino.setAusLicPersonales(origen.getAusLicPersonales());
        destino.setCargoxunidad(origen.getCargoxunidad());
//        destino.setCtd_per_superv(CopiadorEntidades.verifacionNull(origen.getCtd_per_superv()));
        destino.setEstado(CopiadorEntidades.verifacionNull(origen.getEstado()));
        if(origen.getEvaluacionesPersonales() != null)
            destino.setEvaluacionesPersonales(origen.getEvaluacionesPersonales());
        destino.setFec_fin(CopiadorEntidades.verifacionNull(origen.getFec_fin()));
        destino.setFec_inicio(CopiadorEntidades.verifacionNull(origen.getFec_inicio()));
        destino.setLegajo(origen.getLegajo());
        destino.setMotivo_cese(CopiadorEntidades.verifacionNull(origen.getMotivo_cese()));
        if(origen.getRemuneracionesPersonales() != null)
            destino.setRemuneracionesPersonales(origen.getRemuneracionesPersonales());
        //destino.setTipovinculo(CopiadorEntidades.verifacionNull(origen.getTipovinculo()));
        destino.setTrabajador(origen.getTrabajador());
    }
    
    public static void familiar(Familiar origen, Familiar destino){
        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
        destino.setApellidoMaterno(CopiadorEntidades.verifacionNull(origen.getApellidoMaterno()));
        destino.setApellidoPaterno(CopiadorEntidades.verifacionNull(origen.getApellidoPaterno()));
//        destino.setCod_dom_dept(origen.getCod_dom_dept());
//        destino.setCod_dom_dist(origen.getCod_dom_dist());
//        destino.setCod_dom_prov(origen.getCod_dom_prov());
//        destino.setCod_ubi_dept(origen.getCod_ubi_dept());
//        destino.setCod_ubi_dist(origen.getCod_ubi_dist());
//        destino.setCod_ubi_prov(origen.getCod_ubi_prov());
//        destino.setDomicilioCodigoPostal(CopiadorEntidades.verifacionNull(origen.getDomicilioCodigoPostal()));
//        destino.setDomicilioDireccion(CopiadorEntidades.verifacionNull(origen.getDomicilioDireccion()));
//        destino.setEsSalud(CopiadorEntidades.verifacionNull(origen.getEsSalud()));
//        destino.setEstadoCivil(CopiadorEntidades.verifacionNull(origen.getEstadoCivil()));
//        destino.setFechaNacimiento(CopiadorEntidades.verifacionNull(origen.getFechaNacimiento()));
//        destino.setGrupoSanguineo(CopiadorEntidades.verifacionNull(origen.getGrupoSanguineo()));
//        destino.setNacionalidad(CopiadorEntidades.verifacionNull(origen.getNacionalidad()));
//        destino.setNivelInstruccion(CopiadorEntidades.verifacionNull(origen.getNivelInstruccion()));
        destino.setNombres(CopiadorEntidades.verifacionNull(origen.getNombres()));
//        destino.setNroDocumento(CopiadorEntidades.verifacionNull(origen.getNroDocumento()));
//        destino.setPais(CopiadorEntidades.verifacionNull(origen.getPais()));
//        destino.setParentesco(CopiadorEntidades.verifacionNull(origen.getParentesco()));
//        destino.setSexo(CopiadorEntidades.verifacionNull(origen.getSexo()));
//        destino.setTipoDocumento(CopiadorEntidades.verifacionNull(origen.getTipoDocumento()));
        destino.setTrabajador(origen.getTrabajador());
    }
    
    public static void titulo(Titulo origen, Titulo destino){
        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
        destino.setCentro_estudios(CopiadorEntidades.verifacionNull(origen.getCentro_estudios()));
        //destino.setClasificacion(CopiadorEntidades.verifacionNull(origen.getClasificacion()));
        destino.setColegio_profesional(CopiadorEntidades.verifacionNull(origen.getColegio_profesional()));
        destino.setDenominacion(CopiadorEntidades.verifacionNull(origen.getDenominacion()));
        destino.setEspecialidad(CopiadorEntidades.verifacionNull(origen.getEspecialidad()));
        destino.setFec_emision(CopiadorEntidades.verifacionNull(origen.getFec_emision()));
        destino.setLugar_emision(CopiadorEntidades.verifacionNull(origen.getLugar_emision()));
        destino.setNivel(CopiadorEntidades.verifacionNull(origen.getNivel()));
        destino.setNum_colegiatura(CopiadorEntidades.verifacionNull(origen.getNum_colegiatura()));
        destino.setTrabajador(origen.getTrabajador());
        destino.setValidado(CopiadorEntidades.verifacionNull(origen.getValidado()));
    }
    
    public static void certificacion(Certificacion origen, Certificacion destino){
        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
        destino.setDenominacion(CopiadorEntidades.verifacionNull(origen.getDenominacion()));
        destino.setEntidad_certificante(CopiadorEntidades.verifacionNull(origen.getEntidad_certificante()));
        destino.setEspecialidad(CopiadorEntidades.verifacionNull(origen.getEspecialidad()));
        destino.setFec_emision(CopiadorEntidades.verifacionNull(origen.getFec_emision()));
        destino.setLugar_emision(CopiadorEntidades.verifacionNull(origen.getLugar_emision()));
        destino.setTrabajador(origen.getTrabajador());
        destino.setValidado(CopiadorEntidades.verifacionNull(origen.getValidado()));
    }
    
    public static void curso(Curso origen, Curso destino){
//        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
//        destino.setCentro_estudios(CopiadorEntidades.verifacionNull(origen.getCentro_estudios()));
//        destino.setDenominacion(CopiadorEntidades.verifacionNull(origen.getDenominacion()));
//        destino.setFec_emision(CopiadorEntidades.verifacionNull(origen.getFec_emision()));
//        destino.setFinanciadoEntidad(CopiadorEntidades.verifacionNull(origen.getFinanciadoEntidad()));
//        destino.setHoras((Float) CopiadorEntidades.verifacionNull(origen.getHoras()));
//        destino.setLugar_dictado(CopiadorEntidades.verifacionNull(origen.getLugar_dictado()));
        destino.setTrabajador(origen.getTrabajador());
        destino.setValidado(CopiadorEntidades.verifacionNull(origen.getValidado()));
    }
    
    public static void ant_laborales(Ant_Laborales origen, Ant_Laborales destino){
        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
//        destino.setArea(CopiadorEntidades.verifacionNull(origen.getArea()));
        destino.setCargo(CopiadorEntidades.verifacionNull(origen.getCargo()));
        destino.setEmpresa(CopiadorEntidades.verifacionNull(origen.getEmpresa()));
//        destino.setEntidadPublica(CopiadorEntidades.verifacionNull(origen.getEntidadPublica()));
        destino.setFec_egreso(CopiadorEntidades.verifacionNull(origen.getFec_egreso()));
        destino.setFec_ingreso(CopiadorEntidades.verifacionNull(origen.getFec_ingreso()));
        destino.setFuncion(CopiadorEntidades.verifacionNull(origen.getFuncion()));
//        destino.setRegLaboral(CopiadorEntidades.verifacionNull(origen.getRegLaboral()));
        destino.setTrabajador(origen.getTrabajador());
        destino.setValidado(CopiadorEntidades.verifacionNull(origen.getValidado()));
    }
    
    public static void meritoDemerito(MeritoDemerito origen, MeritoDemerito destino){
        destino.setClase(CopiadorEntidades.verifacionNull(origen.getClase()));
        destino.setDetalle(CopiadorEntidades.verifacionNull(origen.getDetalle()));
        destino.setFecha(CopiadorEntidades.verifacionNull(origen.getFecha()));
        destino.setMotivo(CopiadorEntidades.verifacionNull(origen.getMotivo()));
        destino.setTipo(CopiadorEntidades.verifacionNull(origen.getTipo()));
        destino.setTrabajador(origen.getTrabajador());
    }
    
    public static void produccionIntelectual(Publicacion origen, Publicacion destino){
        destino.setAgregadoTrabajador(CopiadorEntidades.verifacionNull(origen.getAgregadoTrabajador()));
//        destino.setClase(CopiadorEntidades.verifacionNull(origen.getClase()));
//        destino.setDescripcion(CopiadorEntidades.verifacionNull(origen.getDescripcion()));
//        destino.setEntidad(CopiadorEntidades.verifacionNull(origen.getEntidad()));
//        destino.setFecha(CopiadorEntidades.verifacionNull(origen.getFecha()));
//        destino.setTipo(CopiadorEntidades.verifacionNull(origen.getTipo()));
        destino.setTitulo(CopiadorEntidades.verifacionNull(origen.getTitulo()));
        destino.setTrabajador(origen.getTrabajador());
        destino.setValidado(CopiadorEntidades.verifacionNull(origen.getValidado()));
    }
    
    public static void remuneracionPersonal(RemuneracionPersonal origen, RemuneracionPersonal destino){
        destino.setConceptoRemunerativo(origen.getConceptoRemunerativo());
        destino.setImporte((Double) CopiadorEntidades.verifacionNull(origen.getImporte()));
    }
    
    public static void evaluacionPersonal(EvaluacionPersonal origen, EvaluacionPersonal destino){
        destino.setCalificacion(CopiadorEntidades.verifacionNull(origen.getCalificacion()));
        destino.setFec_desde(CopiadorEntidades.verifacionNull(origen.getFec_desde()));
        destino.setFec_hasta(CopiadorEntidades.verifacionNull(origen.getFec_hasta()));
        destino.setTipo(CopiadorEntidades.verifacionNull(origen.getTipo()));
    }
    
    public static void ausLicPersonal(AusLicPersonal origen, AusLicPersonal destino){
        destino.setFec_desde(CopiadorEntidades.verifacionNull(origen.getFec_desde()));
        destino.setFec_hasta(CopiadorEntidades.verifacionNull(origen.getFec_hasta()));
        destino.setMotivo(CopiadorEntidades.verifacionNull(origen.getMotivo()));
        destino.setTipo(CopiadorEntidades.verifacionNull(origen.getTipo()));
    }
    
    public static void constanciaDocumental(ConstanciaDocumental origen, ConstanciaDocumental destino){
        destino.setCat_constancia(CopiadorEntidades.verifacionNull(origen.getCat_constancia()));
        destino.setFecha(CopiadorEntidades.verifacionNull(origen.getFecha()));
        destino.setLegajo(origen.getLegajo());
        destino.setNum_resolucion(CopiadorEntidades.verifacionNull(origen.getNum_resolucion()));
        destino.setTip_constancia(CopiadorEntidades.verifacionNull(origen.getTip_constancia()));
        destino.setTxt_descriptivo(CopiadorEntidades.verifacionNull(origen.getTxt_descriptivo()));
    }
    
    public static String verifacionNull(String dato){
        
        if(dato == null)
            return "";
        
        return dato;
    }
    
    public static Date verifacionNull(Date dato){
        if(dato == null)
            return new Date();
        
        return dato;
    }
    
    public static Integer verifacionNull(Integer dato){
        
        if(dato == null)
            return 0;
        
        return dato;
    }
     
      public static Boolean verifacionNull(Boolean dato){
        if(dato == null)
            return false;
        
        return dato;
    }
      
   /*public static List<CargoAsignado> verifacionNull(List<CargoAsignado> dato){
    List<CargoAsignado> lca = new LinkedList<CargoAsignado>();

    if(dato == null)
        return lca;

    return dato;
    }*/
   
   public static Number verifacionNull(Number dato){
    Number number = new Number() {

            @Override
            public int intValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public long longValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public float floatValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public double doubleValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    
    if(dato == null)
        return number;

    return dato;
    }
}
