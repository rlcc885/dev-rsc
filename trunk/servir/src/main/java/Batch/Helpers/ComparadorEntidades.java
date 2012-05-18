/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Morgan
 */
public class ComparadorEntidades {

    /**
     * Devuelve la linea para poner en el xls concepto
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeCodigo conceptos(ConceptoRemunerativo corigen, ConceptoRemunerativo cdestino) {
        LineaInformeCodigo lic = new LineaInformeCodigo();
        
        lic.setCodigoEntidadUE(corigen.getEntidadUE().getCodigoEntidadUE());
        lic.setCodigo(corigen.getCodigo());

        //System.out.println("CONCEPTO REMUNERATIVO 2");
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCodigo(), cdestino.getCodigo()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        //System.out.println("CONCEPTO REMUNERATIVO 4");
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDescripcion(), cdestino.getDescripcion()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        //System.out.println("CONCEPTO REMUNERATIVO 5");
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getSustento_legal(), cdestino.getSustento_legal()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getClasificacion(), cdestino.getClasificacion()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        //System.out.println("CONCEPTO REMUNERATIVO 3");
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getConceptoStd(), cdestino.getConceptoStd()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getPeriodicidad(), cdestino.getPeriodicidad()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
       // Si llego hasta aca es que no hubieron cambios
       lic.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lic;

    }
    
    /**
     * Compara 2 entidades unidad ejecutora y devueve si es una modificacion o sin cambio
     * @param eueOrigen - El que vino del csv
     * @param eueDestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static String entidadUEs(EntidadUEjecutora eueOrigen, EntidadUEjecutora eueDestino) {
        String resultado = ResultadoOperacionCSV.SIN_CAMBIO;
        
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getClas_funcional(), eueDestino.getClas_funcional());
        if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCod_mef_ue(), eueDestino.getCod_mef_ue());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCod_ubi_dept(), eueDestino.getCod_ubi_dept());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCod_ubi_dist(), eueDestino.getCod_ubi_dist());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCod_ubi_prov(), eueDestino.getCod_ubi_prov());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCorreoElectronicoInstitucional(), eueDestino.getCorreoElectronicoInstitucional());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCorreoElectronicoJefeRRHH(), eueDestino.getCorreoElectronicoJefeRRHH());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCorreoElectronicoTitular(), eueDestino.getCorreoElectronicoTitular());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCue_elemento(), eueDestino.getCue_elemento());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getCue_entidad(), eueDestino.getCue_entidad());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getDef_servir(), eueDestino.getDef_servir());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getDenominacion(), eueDestino.getDenominacion());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getDirecion(), eueDestino.getDirecion());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getEstado(), eueDestino.getEstado());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getJefeRRHHEntidad(), eueDestino.getJefeRRHHEntidad());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getLocalidad(), eueDestino.getLocalidad());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getNivel_gobierno(), eueDestino.getNivel_gobierno());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getPliego(), eueDestino.getPliego());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getProc_batch(), eueDestino.getProc_batch());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getRuc(), eueDestino.getRuc());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getSector_gobierno(), eueDestino.getSector_gobierno());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getTEJefeRRHH(), eueDestino.getTEJefeRRHH());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getTEMovilJefeRRHH(), eueDestino.getTEMovilJefeRRHH());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getTitularEntidad(), eueDestino.getTitularEntidad());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
            return resultado;
        resultado = ComparadorEntidades.comparadorDatos(eueOrigen.getTrabajadorAgregaDatos(), eueDestino.getTrabajadorAgregaDatos());
                if(resultado.equals(ResultadoOperacionCSV.MODIFICADO))
                    
            return resultado;

        // Si llego hasta aca es que no hubieron cambios
       return resultado;

    }
    
    /**
     * Devuelve la linea para poner en el xls cargo
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeCodigo cargos(Cargo corigen, Cargo cdestino) {
        LineaInformeCodigo lio = new LineaInformeCodigo();
        
        lio.setCodigoEntidadUE(corigen.getUnd_organica().getEntidadUE().getCodigoEntidadUE());
        lio.setCodigo(corigen.getCod_cargo());

        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getUnd_organica(), cdestino.getUnd_organica()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }        
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_cargo(), cdestino.getCod_cargo()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }        
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDen_cargo(), cdestino.getDen_cargo()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEstado(), cdestino.getEstado()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getReg_lab_con(), cdestino.getReg_lab_con()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getHoras_x_sem(), cdestino.getHoras_x_sem()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }

        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getClasificacion_funcional(), cdestino.getClasificacion_funcional()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
                
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getReq_hab_profesional(), cdestino.getReq_hab_profesional()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
                
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDec_jurada_byr(), cdestino.getDec_jurada_byr()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }        
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getPresupuestado_PAP(), cdestino.getPresupuestado_PAP()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }

        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCtd_puestos_total(), cdestino.getCtd_puestos_total()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
       
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getGrupoOcupacional(), cdestino.getGrupoOcupacional()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }

        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNivelRemunerativo(), cdestino.getNivelRemunerativo()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getSituacion_CAP(), cdestino.getSituacion_CAP()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
        lio.setResultado(ComparadorEntidades.comparadorDatos(corigen.getPersonasCargo(), cdestino.getPersonasCargo()));
        if(lio.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lio;
        }
        
       // Si llego hasta aca es que no hubieron cambios
       lio.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lio;

    }
    
    /**
     * Devuelve la linea para poner en el xls unidad organica
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeCodigo unidadesOrganicas(UnidadOrganica corigen, UnidadOrganica cdestino) {
        LineaInformeCodigo liuo = new LineaInformeCodigo();
        
        liuo.setCodigoEntidadUE(corigen.getEntidadUE().getCodigoEntidadUE());
        liuo.setCodigo(corigen.getCod_und_organica());
                
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_und_organica(), cdestino.getCod_und_organica()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDen_und_organica(), cdestino.getDen_und_organica()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getLocalidad(), cdestino.getLocalidad()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dist(), cdestino.getCod_ubi_dist()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_prov(), cdestino.getCod_ubi_prov()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dept(), cdestino.getCod_ubi_dept()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCue(), cdestino.getCue()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getSigla(), cdestino.getSigla()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNivel(), cdestino.getNivel()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipoActividad(), cdestino.getTipoActividad()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
        liuo.setResultado(ComparadorEntidades.comparadorDatos(corigen.getUoAntecesora(), cdestino.getUoAntecesora()));
        if(liuo.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return liuo;
        }
       // Si llego hasta aca es que no hubieron cambios
       liuo.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return liuo;
    }
    
    /**
     * Devuelve la linea para poner en el xls familiar
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento familiares(Familiar corigen, Familiar cdestino, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lif = new LineaInformeTipoNumeroDocumento();
        
        lif.setCodigoEntidadUE(codigo_organismo);
        lif.setNumeroDocumento(corigen.getNroDocumento());
        lif.setTipoDocumento(corigen.getTipoDocumento());
                
        /*lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador().getTipoDocumento(), cdestino.getTrabajador().getTipoDocumento()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador().getNroDocumento(), cdestino.getTrabajador().getNroDocumento()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }*/

        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getParentesco(), cdestino.getParentesco()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        /*lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipoDocumento(), cdestino.getTipoDocumento()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
                
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNroDocumento(), cdestino.getNroDocumento()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }*/

        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getApellidoPaterno(), cdestino.getApellidoPaterno()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getApellidoMaterno(), cdestino.getApellidoMaterno()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNombres(), cdestino.getNombres()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getSexo(), cdestino.getSexo()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDate(corigen.getFechaNacimiento(), cdestino.getFechaNacimiento()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getPais(), cdestino.getPais()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dist(), cdestino.getCod_ubi_dist()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_prov(), cdestino.getCod_ubi_prov()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dept(), cdestino.getCod_ubi_dept()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNacionalidad(), cdestino.getNacionalidad()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }

        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEstadoCivil(), cdestino.getEstadoCivil()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDomicilioDireccion(), cdestino.getDomicilioDireccion()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_dist(), cdestino.getCod_dom_dist()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_prov(), cdestino.getCod_dom_prov()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_dept(), cdestino.getCod_dom_dept()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }

        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDomicilioCodigoPostal(), cdestino.getDomicilioCodigoPostal()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEsSalud(), cdestino.getEsSalud()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getGrupoSanguineo(), cdestino.getGrupoSanguineo()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
        lif.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNivelInstruccion(), cdestino.getNivelInstruccion()));
        if(lif.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lif;
        }
        
       // Si llego hasta aca es que no hubieron cambios
       lif.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lif;
    }
    
    /**
     * Devuelve la linea para poner en el xls titulo
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento titulos(Titulo corigen, Titulo cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lit = new LineaInformeTipoNumeroDocumento();
        
        lit.setCodigoEntidadUE(codigo_organismo);
        lit.setNumeroDocumento(numeroDocumento);
        lit.setTipoDocumento(tipoDocumento);

        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNivel(), cdestino.getNivel()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDenominacion(), cdestino.getDenominacion()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEspecialidad(), cdestino.getEspecialidad()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCentro_estudios(), cdestino.getCentro_estudios()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getLugar_emision(), cdestino.getLugar_emision()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_emision(), cdestino.getFec_emision()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
                
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getColegio_profesional(), cdestino.getColegio_profesional()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNum_colegiatura(), cdestino.getNum_colegiatura()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        
        /*lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }*/
        
       // Si llego hasta aca es que no hubieron cambios
       lit.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lit;
    }
    
    /**
     * Devuelve la linea para poner en el xls certificacion
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento certificaciones(Certificacion corigen, Certificacion cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lic = new LineaInformeTipoNumeroDocumento();
        
        lic.setCodigoEntidadUE(codigo_organismo);
        lic.setNumeroDocumento(numeroDocumento);
        lic.setTipoDocumento(tipoDocumento);
        
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDenominacion(), cdestino.getDenominacion()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEspecialidad(), cdestino.getEspecialidad()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEntidad_certificante(), cdestino.getEntidad_certificante()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getLugar_emision(), cdestino.getLugar_emision()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        lic.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_emision(), cdestino.getFec_emision()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        /*lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }*/
        
       // Si llego hasta aca es que no hubieron cambios
       lic.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lic;
    }
   
    /**
     * Devuelve la linea para poner en el xls curso
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento cursos(Curso corigen, Curso cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lic = new LineaInformeTipoNumeroDocumento();
        
        lic.setCodigoEntidadUE(codigo_organismo);
        lic.setNumeroDocumento(numeroDocumento);
        lic.setTipoDocumento(tipoDocumento);
        
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDenominacion(), cdestino.getDenominacion()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCentro_estudios(), cdestino.getCentro_estudios()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getHoras(), cdestino.getHoras()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getFinanciadoEntidad(), cdestino.getFinanciadoEntidad()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }

        lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getLugar_dictado(), cdestino.getLugar_dictado()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        lic.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_emision(), cdestino.getFec_emision()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }
        
        /*lic.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lic;
        }*/
        
       // Si llego hasta aca es que no hubieron cambios
       lic.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lic;
    }
    
    /**
     * Devuelve la linea para poner en el xls antecedent laboral
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento antecedenteslaborales(Ant_Laborales corigen, Ant_Laborales cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lial = new LineaInformeTipoNumeroDocumento();
        
        lial.setCodigoEntidadUE(codigo_organismo);
        lial.setNumeroDocumento(numeroDocumento);
        lial.setTipoDocumento(tipoDocumento);
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCargo(), cdestino.getCargo()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmpresa(), cdestino.getEmpresa()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getFuncion(), cdestino.getFuncion()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
                
        lial.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_ingreso(), cdestino.getFec_ingreso()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_egreso(), cdestino.getFec_egreso()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEntidadPublica(), cdestino.getEntidadPublica()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getArea(), cdestino.getArea()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }
        
        lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getRegLaboral(), cdestino.getRegLaboral()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }

        /*lial.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lial.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lial;
        }*/
        
       // Si llego hasta aca es que no hubieron cambios
       lial.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lial;
    }
    
    /**
     * Devuelve la linea para poner en el xls merito demerito
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento meritosdemeritos(MeritoDemerito corigen, MeritoDemerito cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento limd = new LineaInformeTipoNumeroDocumento();
        
        limd.setCodigoEntidadUE(codigo_organismo);
        limd.setNumeroDocumento(numeroDocumento);
        limd.setTipoDocumento(tipoDocumento);

        limd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getClase(), cdestino.getClase()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }
        
        limd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipo(), cdestino.getTipo()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }

        limd.setResultado(ComparadorEntidades.comparadorDate(corigen.getFecha(), cdestino.getFecha()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }
        
        limd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getMotivo(), cdestino.getMotivo()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }
        
        limd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDetalle(), cdestino.getDetalle()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }
        
        /*limd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(limd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return limd;
        }*/
        
       // Si llego hasta aca es que no hubieron cambios
       limd.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return limd;
    }
    
    /**
     * Devuelve la linea para poner en el xls produccion intelectual
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento produccionesintelecuales(Publicacion corigen, Publicacion cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lip = new LineaInformeTipoNumeroDocumento();
        
        lip.setCodigoEntidadUE(codigo_organismo);
        lip.setNumeroDocumento(numeroDocumento);
        lip.setTipoDocumento(tipoDocumento);
        
        lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getClase(), cdestino.getClase()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }
         
        /*lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipo(), cdestino.getTipo()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }*/
                
        /*lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTitulo(), cdestino.getTitulo()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }*/
        
        /*lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getFecha(), cdestino.getFecha()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }*/
         
        lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDescripcion(), cdestino.getDescripcion()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }
        
        lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEntidad(), cdestino.getEntidad()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }

        /*lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }*/
            
       // Si llego hasta aca es que no hubieron cambios
       lip.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lip;
    }
    
    /**
     * Devuelve la linea para poner en el xls evaluacion personal
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento evaluacionespersonales(EvaluacionPersonal corigen, EvaluacionPersonal cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lip = new LineaInformeTipoNumeroDocumento();
        
        lip.setCodigoEntidadUE(codigo_organismo);
        lip.setNumeroDocumento(numeroDocumento);
        lip.setTipoDocumento(tipoDocumento);
         
        lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipo(), cdestino.getTipo()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }        
           
        lip.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_desde(), cdestino.getFec_desde()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }
        
        lip.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_hasta(), cdestino.getFec_hasta()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }
        
        lip.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCalificacion(), cdestino.getCalificacion()));
        if(lip.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lip;
        }

        
       // Si llego hasta aca es que no hubieron cambios
       lip.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lip;
    }
    
    /**
     * Devuelve la linea para poner en el xls remuneracion personal
     * @param corigen
     * @param cdestino
     * @param numeroDocumento
     * @param tipoDocumento
     * @param codigo_organismo
     * @return - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento remuneracionespersonales(RemuneracionPersonal corigen, RemuneracionPersonal cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lirp = new LineaInformeTipoNumeroDocumento();
        lirp.setCodigoEntidadUE(codigo_organismo);
        lirp.setNumeroDocumento(numeroDocumento);
        lirp.setTipoDocumento(tipoDocumento);
        
        lirp.setResultado(ComparadorEntidades.comparadorDatos(corigen.getConceptoRemunerativo(), cdestino.getConceptoRemunerativo()));
        if(lirp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lirp;
        }
        
        lirp.setResultado(ComparadorEntidades.comparadorDatos(corigen.getImporte(), cdestino.getImporte()));
        if(lirp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lirp;
        }
                
       // Si llego hasta aca es que no hubieron cambios
       lirp.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lirp;
    }
    
    /**
     * Devuelve la linea para poner en el xls ausencia y licencia
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento auslicpersonal(AusLicPersonal corigen, AusLicPersonal cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lialp = new LineaInformeTipoNumeroDocumento();
        
        lialp.setCodigoEntidadUE(codigo_organismo);
        lialp.setNumeroDocumento(numeroDocumento);
        lialp.setTipoDocumento(tipoDocumento);
             
        lialp.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipo(), cdestino.getTipo()));
        if(lialp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lialp;
        }
        
        lialp.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_desde(), cdestino.getFec_desde()));
        if(lialp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lialp;
        }
        
        lialp.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_hasta(), cdestino.getFec_hasta()));
        if(lialp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lialp;
        }
        
        lialp.setResultado(ComparadorEntidades.comparadorDatos(corigen.getMotivo(), cdestino.getMotivo()));
        if(lialp.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lialp;
        }
        
       // Si llego hasta aca es que no hubieron cambios
       lialp.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lialp;
    }
    
    /**
     * Devuelve la linea para poner en el xls constancia documental
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento constanciadocumental(ConstanciaDocumental corigen, ConstanciaDocumental cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento licd = new LineaInformeTipoNumeroDocumento();
        
        licd.setCodigoEntidadUE(codigo_organismo);
        licd.setNumeroDocumento(numeroDocumento);
        licd.setTipoDocumento(tipoDocumento);
        
        licd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCat_constancia(), cdestino.getCat_constancia()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
        licd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTip_constancia(), cdestino.getTip_constancia()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
        licd.setResultado(ComparadorEntidades.comparadorDate(corigen.getFecha(), cdestino.getFecha()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
        licd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNum_resolucion(), cdestino.getNum_resolucion()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
        licd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTxt_descriptivo(), cdestino.getTxt_descriptivo()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
        licd.setResultado(ComparadorEntidades.comparadorDatos(corigen.getLegajo(), cdestino.getLegajo()));
        if(licd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return licd;
        }
        
       // Si llego hasta aca es que no hubieron cambios
       licd.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return licd;
    }
    
    /**
     * Devuelve la linea para poner en el xls trabajador
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento trabajador(Trabajador corigen, Trabajador cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lit = new LineaInformeTipoNumeroDocumento();
        
        lit.setCodigoEntidadUE(codigo_organismo);
        lit.setNumeroDocumento(numeroDocumento);
        lit.setTipoDocumento(tipoDocumento);

        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getApellidoPaterno(), cdestino.getApellidoPaterno()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getApellidoMaterno(), cdestino.getApellidoMaterno()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNombres(), cdestino.getNombres()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getSexo(), cdestino.getSexo()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDate(corigen.getFechaNacimiento(), cdestino.getFechaNacimiento()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getPais(), cdestino.getPais()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dist(), cdestino.getCod_ubi_dist()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_prov(), cdestino.getCod_ubi_prov()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_ubi_dept(), cdestino.getCod_ubi_dept()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNacionalidad(), cdestino.getNacionalidad()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEstadoCivil(), cdestino.getEstadoCivil()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDomicilioDireccion(), cdestino.getDomicilioDireccion()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_dist(), cdestino.getCod_dom_dist()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_prov(), cdestino.getCod_dom_prov()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_dom_dept(), cdestino.getCod_dom_dept()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getDomicilioCodigoPostal(), cdestino.getDomicilioCodigoPostal()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEsSalud(), cdestino.getEsSalud()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getGrupoSanguineo(), cdestino.getGrupoSanguineo()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipoDiscapacidad(), cdestino.getTipoDiscapacidad()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNroCertificadoCONADIS(), cdestino.getNroCertificadoCONADIS()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNivelInstruccion(), cdestino.getNivelInstruccion()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getFormacionProfesional(), cdestino.getFormacionProfesional()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmergenciaNombre(), cdestino.getEmergenciaNombre()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmergenciaDomicilio(), cdestino.getEmergenciaDomicilio()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmergenciaTelefonos(), cdestino.getEmergenciaTelefonos()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getRegimenPensionario(), cdestino.getRegimenPensionario()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmailPersonal(), cdestino.getEmailPersonal()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEmailLaboral(), cdestino.getEmailLaboral()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getNroRUC(), cdestino.getNroRUC()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }
        lit.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCodigoOSCE(), cdestino.getCodigoOSCE()));
        if(lit.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lit;
        }        
        
       // Si llego hasta aca es que no hubieron cambios
       lit.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lit;
    }
    
    /**
     * Devuelve la linea para poner en el xls legajo
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento legajo(Legajo corigen, Legajo cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lij = new LineaInformeTipoNumeroDocumento();
        
        lij.setCodigoEntidadUE(codigo_organismo);
        lij.setNumeroDocumento(numeroDocumento);
        lij.setTipoDocumento(tipoDocumento);
        
        lij.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCod_legajo(), cdestino.getCod_legajo()));
        if(lij.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lij;
        }
        
        lij.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lij.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lij;
        }
        
       // Si llego hasta aca es que no hubieron cambios
       lij.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lij;
    }
    
    /**
     * Devuelve la linea para poner en el xls cargo asignado
     * @param corigen - El que vino del csv
     * @param cdestino - El que vino de la base de datos
     * @return  - Devuelve una linea de informacion
     */
    public static LineaInformeTipoNumeroDocumento cargoasignado(CargoAsignado corigen, CargoAsignado cdestino, String numeroDocumento, String tipoDocumento, String codigo_organismo) {
        LineaInformeTipoNumeroDocumento lica = new LineaInformeTipoNumeroDocumento();
                
        lica.setCodigoEntidadUE(codigo_organismo);
        lica.setNumeroDocumento(numeroDocumento);
        lica.setTipoDocumento(tipoDocumento);
    
        /*System.out.println("CARGO ASIGNADO 0 "+corigen.getCargo().getCod_cargo() + " destino" +cdestino.getCargo().getCod_cargo());
        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCargo(), cdestino.getCargo()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }*/
        
        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getEstado(), cdestino.getEstado()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }  
        
        lica.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_inicio(), cdestino.getFec_inicio()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }
        
        lica.setResultado(ComparadorEntidades.comparadorDate(corigen.getFec_fin(), cdestino.getFec_fin()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }
               
        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getCtd_per_superv(), cdestino.getCtd_per_superv()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }  

        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getMotivo_cese(), cdestino.getMotivo_cese()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }
        
        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTipoVinculo(), cdestino.getTipoVinculo()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }
        
        lica.setResultado(ComparadorEntidades.comparadorDatos(corigen.getTrabajador(), cdestino.getTrabajador()));
        if(lica.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)){
            return lica;
        }
            
        // Si llego hasta aca es que no hubieron cambios
        lica.setResultado(ResultadoOperacionCSV.SIN_CAMBIO);
        return lica;
    }
        
    public static String comparadorDatos(Object dato1, Object dato2){
        
        if(dato1 == null && dato2 == null){
            return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(dato1 == null){
            if(dato2 != null && dato2.equals("")){
                return ResultadoOperacionCSV.SIN_CAMBIO;
            }
        }
        
        if(dato2 == null){
            if(dato1 != null && dato1.equals("")){
                return ResultadoOperacionCSV.SIN_CAMBIO;
            }
        }
        
        if(dato1 == null){
            if(dato2 != null && !dato2.equals("")){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(dato2 == null){
            if(dato1 != null && !dato1.equals("")){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(dato1.equals("") && dato2.equals("")){
            return ResultadoOperacionCSV.SIN_CAMBIO;
        }
                
        if(dato1.equals("")){
            if(!dato2.equals("") && dato2 == null){
                return ResultadoOperacionCSV.SIN_CAMBIO;
            }
        }
        
        if(dato2.equals("")){
            if(!dato1.equals(dato2) && dato1 == null){
                return ResultadoOperacionCSV.SIN_CAMBIO;
            }
        }
        
        if(!dato2.equals(dato1)){
           return ResultadoOperacionCSV.MODIFICADO;
        }
        
        /*if((dato1 == null && dato2 == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;
        
        if(dato2 != null){
            if((dato1 == null && dato2.equals("")))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(dato1 != null){
            if((dato1.equals("") && dato2 == null))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if((dato1.equals("") && dato2.equals("")))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;
                    
        if(dato1 == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(dato2 == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
                        
        if(!dato2.equals(dato1))
           // modif
           return ResultadoOperacionCSV.MODIFICADO;*/
                        
        return ResultadoOperacionCSV.SIN_CAMBIO;
    }
    
    
    public static String comparadorDatos(Boolean dato1, Boolean dato2){
        
        if(dato1 == null && dato2 == null){
            return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(dato1 == null){
            if(dato2 != null){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(dato2 == null){
            if(dato1 != null){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(!dato2.equals(dato1)){
           return ResultadoOperacionCSV.MODIFICADO;
        }
                                
        return ResultadoOperacionCSV.SIN_CAMBIO;
    }
    
    public static String comparadorDatos(DatoAuxiliar da1, DatoAuxiliar da2){
        if(da1 == null && da2 == null){
            return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(da1 == null){
            if(da2 != null){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(da2 == null){
            if(da1 != null){
                return ResultadoOperacionCSV.MODIFICADO;
            }
        }
        
        if(!da2.getValor().equals(da1.getValor()))
           // modif
           return ResultadoOperacionCSV.MODIFICADO;
       
        /*if((da1 == null && da2 == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;
    if(da2 != null){
            if((da1 == null))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        System.out.println("--------- jusque la");
        if(da1 != null){
            if(da2 == null)
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(da1 == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(da2 == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
                    
        if((da1.getValor() == null && da2.getValor() == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;

        if(da2.getValor() != null){
            if((da1 == null && da2.getValor().equals("")))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(da1.getValor() != null){
            if((da1.getValor().equals("") && da2 == null))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        if(da1.getValor() == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(da2.getValor() == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
        
        
        if(!da2.getValor().equals(da1.getValor()))
           // modif
           return ResultadoOperacionCSV.MODIFICADO;*/
                    
        return ResultadoOperacionCSV.SIN_CAMBIO;
    }
   
    public static String comparadorDatos2(DatoAuxiliar da1, DatoAuxiliar da2){
        if((da1 == null && da2 == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;
        
        if(da1 == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(da2 == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
                    
        if((da1.getValor() == null && da2.getValor() == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;

        if(da2.getValor() != null){
            if((da1 == null && da2.getValor().equals("")))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        
        if(da1.getValor() != null){
            if((da1.getValor().equals("") && da2 == null))
                //sin modif
                return ResultadoOperacionCSV.SIN_CAMBIO;
        }
        if(da1.getValor() == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(da2.getValor() == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
        
        
        if(!da2.getValor().equals(da1.getValor()))
           // modif
           return ResultadoOperacionCSV.MODIFICADO;
                    
        return ResultadoOperacionCSV.SIN_CAMBIO;
    }
   
    public static String comparadorDate(Date date1, Date date2){
        
        if((date1 == null && date2 == null))
            //sin modif
            return ResultadoOperacionCSV.SIN_CAMBIO;
                    
        if(date1 == null)
            //modif
            return ResultadoOperacionCSV.MODIFICADO;
                        
        if(date2 == null)
           //modif
           return ResultadoOperacionCSV.MODIFICADO;
                        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(!sdf.format(date2).equals(sdf.format(date1)))
           // modif
           return ResultadoOperacionCSV.MODIFICADO;
                        
        return ResultadoOperacionCSV.SIN_CAMBIO;
        }
}
