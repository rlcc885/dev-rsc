/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WriteException;
import Batch.Tratamiento;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;


/**
 *
 * @author Morgan
 */
public class TratamientoXLS {
    
    /**
     * Genera todos los XLS. 
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return - errores - Lista de errores generados
     */
    public static List<String> generarXLS(Tratamiento myTratamiento) {
        //antes de todo hay que guardar la entidadUE
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getEntidadesUE(), myTratamiento.getSession());
        
        List<String> errores = new LinkedList<String>();
        if(!myTratamiento.getIsConcepto().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.trataminentoConceptoRemunerativo(myTratamiento));
        }
        if(!myTratamiento.getIsUnidadO().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.trataminentoUnidadOrganica(myTratamiento));
        }
        if(!myTratamiento.getIsCargo().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoCargo(myTratamiento));
        }
        if(!myTratamiento.getIsTrabajador().getLt().isEmpty() && !myTratamiento.getIsLegajo().getLt().isEmpty() && !myTratamiento.getIsCargoA().getLt().isEmpty()){
            System.out.println("------ tratamiento legajo cargo asignado trabajador");
            errores.addAll(TratamientoXLS.tratamientoTrabajadorLegajoCargoAsignado(myTratamiento));
        }
        if(!myTratamiento.getIsFamiliar().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoFamiliar(myTratamiento));
        }
        if(!myTratamiento.getIsTitulo().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoTitulo(myTratamiento));
        }
        if(!myTratamiento.getIsCertificacion().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoCertificacion(myTratamiento));
        }
        if(!myTratamiento.getIsCurso().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoCurso(myTratamiento));
        }
        if(!myTratamiento.getIsAntecedent().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoAntecedentLaboral(myTratamiento));
        }
        if(!myTratamiento.getIsMeritoDemerito().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoMeritoDemerito(myTratamiento));
        }
        if(!myTratamiento.getIsProduccion().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoProduccionIntelectual(myTratamiento));
        }
        if(!myTratamiento.getIsRemuneracion().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoRemuneracionPersonal(myTratamiento));
        }
        if(!myTratamiento.getIsEvaluacion().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoEvaluacion(myTratamiento));
        }
        if(!myTratamiento.getIsAusencia().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoAusenciaLicencia(myTratamiento));
        }
        if(!myTratamiento.getIsConstancia().getLt().isEmpty()){
            errores.addAll(TratamientoXLS.tratamientoConstanciaDocumental(myTratamiento));
        }

        return errores;
    }

    
    private static void saveLista (List lo, Session s) {

         for (Object elem : lo) {

           try {
               s.saveOrUpdate(elem);
           }
           catch(NonUniqueObjectException e) {
               s.merge(elem);
           }
       }
    }
    /**
     * Genera los xls de los conceptos remunerativos
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> trataminentoConceptoRemunerativo(Tratamiento myTratamiento) {
        InformeXLS informeConcepto = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getConceptoRemunerativo(), myTratamiento.getSession());
        
        try {
            informeConcepto.creadorXLSConcepto(myTratamiento.getIsConcepto(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CONCEPTOS_REMUNERATIVOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CONCEPTOS_REMUNERATIVOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return errores;
    }

    /**
     * Genera los xls de los unidades organicas
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @param errores - lista de errores que hay que devolver
     * @return errores - Lista de errores
     */
    public static List<String> trataminentoUnidadOrganica(Tratamiento myTratamiento) {
        InformeXLS informeUnidadO = new InformeXLS();
        List<String> errores = new LinkedList<String>();
                
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)){
            saveLista(myTratamiento.getUnidadOrganica(), myTratamiento.getSession());
        }
        
        try {
            informeUnidadO.creadorXLSUnidadOrganica(myTratamiento.getIsUnidadO(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.UNIDADES_ORGANICAS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.UNIDADES_ORGANICAS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;
    }

    /**
     * Genera los xls de los cargos
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoCargo(Tratamiento myTratamiento) {
        InformeXLS informeCargo = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getCargo(), myTratamiento.getSession());
        
        try {
            informeCargo.creadorXLSCargo(myTratamiento.getIsCargo(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CARGOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CARGOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los trabajadores, legajos y cargos asignados
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoTrabajadorLegajoCargoAsignado(Tratamiento myTratamiento) {
        List<String> errores = new LinkedList<String>();
        InformeXLS informeTrabajador = new InformeXLS();

        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getTrabajador(), myTratamiento.getSession());
        
        try {
            informeTrabajador.creadorXLSTrabajador(myTratamiento.getIsTrabajador(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.TRABAJADORES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.TRABAJADORES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        InformeXLS informeLegajo = new InformeXLS();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getLegajo(), myTratamiento.getSession());
        
        try {
            informeLegajo.creadorXLSLegajo(myTratamiento.getIsLegajo(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.LEGAJOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.LEGAJOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        InformeXLS informeCargoAsignado = new InformeXLS();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getCargoAsignado(), myTratamiento.getSession());
        
        try {
            informeCargoAsignado.creadorXLSCargoAsignado(myTratamiento.getIsCargoA(), myTratamiento.getPath());
            
        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CARGOS_ASIGNADOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CARGOS_ASIGNADOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }           
        
        return errores;
    }

    /**
     * Genera los xls de los familiares
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoFamiliar(Tratamiento myTratamiento) {
        InformeXLS informeFamiliar = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getFamiliar(), myTratamiento.getSession());
        
        try {
            informeFamiliar.creadorXLSFamiliar(myTratamiento.getIsFamiliar(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.FAMILIARES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.FAMILIARES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los titulos
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoTitulo(Tratamiento myTratamiento) {
        InformeXLS informeTitulo = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getTitulo(), myTratamiento.getSession());
        
        try {
            informeTitulo.creadorXLSTitulo(myTratamiento.getIsTitulo(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.TITULOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.TITULOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los certificaciones
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoCertificacion(Tratamiento myTratamiento) {
        InformeXLS informeCertificacion = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getCertificacion(), myTratamiento.getSession());
        
        try {
            informeCertificacion.creadorXLSCertificacion(myTratamiento.getIsCertificacion(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CERTIFICACIONES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CERTIFICACIONES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los cursos
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoCurso(Tratamiento myTratamiento) {
        InformeXLS informeCurso = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getCurso(), myTratamiento.getSession());
        
        try {
            informeCurso.creadorXLSCurso(myTratamiento.getIsCurso(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CURSOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CURSOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los antecedentes laborales
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoAntecedentLaboral(Tratamiento myTratamiento) {
        InformeXLS informeAntecedentLaboral = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getAntLaborale(), myTratamiento.getSession());
        
        try {
            informeAntecedentLaboral.creadorXLSAntecedentLaboral(myTratamiento.getIsAntecedent(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.ANTECEDENTES_LABORALES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.ANTECEDENTES_LABORALES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;    
    }

    /**
     * Genera los xls de los meritos demeritos
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoMeritoDemerito(Tratamiento myTratamiento) {
        InformeXLS informeMeritoDemerito = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getMeritoDemerito(), myTratamiento.getSession());
        
        try {
            informeMeritoDemerito.creadorXLSMeritoDemerito(myTratamiento.getIsMeritoDemerito(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.MERITOS_DEMERITOS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.MERITOS_DEMERITOS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;   
    }

    /**
     * Genera los xls de los produciones intelectuales
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoProduccionIntelectual(Tratamiento myTratamiento) {
        InformeXLS informeProduccion = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        if(myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO))
            saveLista(myTratamiento.getProducionIntelectual(), myTratamiento.getSession());
        
        try {
            informeProduccion.creadorXLSProduccionIntelectual(myTratamiento.getIsProduccion(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;   
    }

    /**
     * Genera los xls de los constancias documentales
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoConstanciaDocumental(Tratamiento myTratamiento) {
        InformeXLS informeConstancia = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        try {
            informeConstancia.creadorXLSConstanciaDocumental(myTratamiento.getIsConstancia(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return errores;   
    }

    /**
     * Genera los xls de los evaluaciones
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoEvaluacion(Tratamiento myTratamiento) {
        InformeXLS informeEvaluacion = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        try {
            informeEvaluacion.creadorXLSEvaluacion(myTratamiento.getIsEvaluacion(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.EVALUACIONES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.EVALUACIONES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        return errores;
    }

    /**
     * Genera los xls de los ausencias licencias
     * @param myTratamiento - Clase de tratamiento de csv y generación de objetos persistentes
     * @return errores - Lista de errores
     */
    public static List<String> tratamientoAusenciaLicencia(Tratamiento myTratamiento) {
        InformeXLS informeAusencia = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        try {
            informeAusencia.creadorXLSAusLicPersonal(myTratamiento.getIsAusencia(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.AUSENCIAS_LICENCIAS);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.AUSENCIAS_LICENCIAS);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }    

        return errores;
    }

    public static List<String> tratamientoRemuneracionPersonal(Tratamiento myTratamiento) {
        InformeXLS informeRemuneracion = new InformeXLS();
        List<String> errores = new LinkedList<String>();
        
        try {
            informeRemuneracion.creadorXLSRemuneracionPersonal(myTratamiento.getIsRemuneracion(), myTratamiento.getPath());

        } catch (IOException ex) {
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
            errores.add(helpers.Errores.ERROR_CREANDO_XLS_DE + helpers.Constantes.REMUNERACIONES_PERSONALES);
            return errores;
        } catch (WriteException ex) {
            errores.add(helpers.Errores.ERROR_ESCRIBIENDO_XLS_DE + helpers.Constantes.REMUNERACIONES_PERSONALES);
            Logger.getLogger(TratamientoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return errores;
    }
    
}
