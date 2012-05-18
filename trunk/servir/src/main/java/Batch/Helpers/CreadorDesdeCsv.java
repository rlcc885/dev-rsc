/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Batch.Helpers;

import Batch.Tratamiento;
import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import helpers.Errores;
import helpers.Helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Morgan
 */
public class CreadorDesdeCsv {

    Tratamiento myTratamiento;

    public Tratamiento getMyTratamiento() {
        return myTratamiento;
    }

    public CreadorDesdeCsv(Tratamiento myTratamiento) {
        this.myTratamiento = myTratamiento;
    }

    //entidadUEs
    public List<EntidadUEjecutora> csvToListEntidadUEjecutora(List<List<String>> leues, Session session, List<String> errores, String origenArchivo) {
        List<EntidadUEjecutora> leue = new LinkedList<EntidadUEjecutora>();
        EntidadUEjecutora eue;
        for (List<String> eues : leues) {
            eue = entidadUEjecutoraFromCsv(eues, errores, session, origenArchivo);
            if (eue == null) {
                continue;
            }
            leue.add(eue);
        }
        return leue;
    }

    public EntidadUEjecutora entidadUEjecutoraFromCsv(List<String> _csvOrganismo, List<String> errores, Session session, String origenArchivo) {
        EntidadUEjecutora eue = new EntidadUEjecutora();

        //verificacion numero campos archivo
        if (_csvOrganismo.size() != helpers.Constantes.CAMPOS_ENTIDADES_UNIDADES_EJECUTORAS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.ENTIDADES_UE);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvOrganismo.get(0).trim().equals("")) || (_csvOrganismo.get(1).trim().equals("")) || (_csvOrganismo.get(16).trim().equals(""))
                || (_csvOrganismo.get(17).trim().equals("")) || (_csvOrganismo.get(18).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.ENTIDADES_UE);
            return null;
        }

        eue.setCodigoEntidadUE(_csvOrganismo.get(0));
        eue.setDenominacion(_csvOrganismo.get(1));
        eue.setClas_funcional(CreadorDesdeDB.verificacionValorDatoAuxiliar("ClasFuncOrg", _csvOrganismo.get(2), errores, session));
        eue.setCue_entidad(_csvOrganismo.get(3));
        eue.setCue_elemento(_csvOrganismo.get(4));
        eue.setCod_mef_ue(CreadorDesdeCsv.toInteger(_csvOrganismo.get(5)));
        eue.setRuc(_csvOrganismo.get(6));
        eue.setDirecion(_csvOrganismo.get(7));
        eue.setLocalidad(_csvOrganismo.get(8));
        eue.setCod_ubi_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvOrganismo.get(9), errores, session));
        eue.setCod_ubi_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvOrganismo.get(10), errores, session));
        eue.setCod_ubi_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvOrganismo.get(11), errores, session));
        eue.setTitularEntidad(_csvOrganismo.get(12));
        eue.setCorreoElectronicoTitular(_csvOrganismo.get(13));
        eue.setJefeRRHHEntidad(_csvOrganismo.get(14));
        eue.setCorreoElectronicoJefeRRHH(_csvOrganismo.get(15));
        eue.setNivel_gobierno(CreadorDesdeDB.verificacionValorDatoAuxiliar("NivelGobierno", _csvOrganismo.get(16), errores, session));
        
        if(eue.getNivel_gobierno().equals("NIVEL LOCAL")) {
            DatoAuxiliar depto = CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvOrganismo.get(17), errores, session);
            DatoAuxiliar provincia = CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvOrganismo.get(18), errores, session);
            if (depto != eue.getCod_ubi_dept() || provincia != eue.getCod_ubi_prov()){
                errores.add(Errores.ERROR_UBIGEO_SECTOR_PLIEGO + " Entidad :" + eue.getCodigoEntidadUE());
            }

            eue.setSector_gobierno(CreadorDesdeDB.verificacionValorDatoAuxiliar("UBDepartamento", _csvOrganismo.get(17), errores, session));
            eue.setPliego(CreadorDesdeDB.verificacionValorDatoAuxiliar("UBProvincia", _csvOrganismo.get(18), errores, session));
        } else {
            eue.setSector_gobierno(CreadorDesdeDB.verificacionValorDatoAuxiliar("SectorGobierno", _csvOrganismo.get(17), errores, session));
            eue.setPliego(CreadorDesdeDB.verificacionValorDatoAuxiliar("Pliego", _csvOrganismo.get(18), errores, session));
        }

        eue.setCorreoElectronicoInstitucional(_csvOrganismo.get(19));
        eue.setTEJefeRRHH(_csvOrganismo.get(20));
        eue.setTEMovilJefeRRHH(_csvOrganismo.get(21));
        eue.setEstado(EntidadUEjecutora.ESTADO_ALTA);

        // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(eue.getCod_ubi_dept(), eue.getCod_ubi_prov(), eue.getCod_ubi_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Entidad :" + eue.getCodigoEntidadUE());

        }


        if (errores.size() > 0) {
            return null;
        }

        EntidadUEjecutora eueOrigen = eue;

        //verificar si ya existe la entidad UE (modificacion o alta) y guardarla en una lista
        Criteria c;
        c = session.createCriteria(EntidadUEjecutora.class);
        c.add(Restrictions.eq("codigoEntidadUE", eue.getCodigoEntidadUE()));

        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                EntidadUEjecutora eueDestino = (EntidadUEjecutora) c.list().get(0);
                if (myTratamiento.getUsuario().getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)) {
                    //verificar si puede ingresar informacion
                    if (!eueDestino.getDef_servir()) {
                        errores.add(helpers.Errores.NO_SE_PUEDE_INGRESAR_INFORMACION_EN_ESTA_ENTIDAD + eueDestino.getCodigoEntidadUE());
                        return null;
                    }

                    //verificar si puede procesar el batch
                    if (!eueDestino.getProc_batch()) {
                        errores.add(helpers.Errores.NO_SE_PUEDE_PROCESAR_BATCH_EN_ESTA_ENTIDAD + eueDestino.getCodigoEntidadUE());
                        return null;
                    }
                }

                if (ComparadorEntidades.entidadUEs(eueOrigen, eueDestino).equals(ResultadoOperacionCSV.MODIFICADO)) {
                    CopiadorEntidades.entidadUE(eueOrigen, eueDestino);
                }

                return eueDestino;
            }
        }

        //nueva entidadUE
        if (origenArchivo.equals(OrigenArchivos.CARGA_INICIAL_ORGANISMOS)) {
            eue.setEstado(EntidadUEjecutora.ESTADO_ALTA);

            UnidadOrganica nuevaUnidadOrganica = new UnidadOrganica();
            nuevaUnidadOrganica.setEntidadUE(eue);
            nuevaUnidadOrganica.setCod_und_organica(UnidadOrganica.CODIGO_DEFAULT);
            nuevaUnidadOrganica.setDen_und_organica(UnidadOrganica.NOMBRE_DEFAULT);
            nuevaUnidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
            nuevaUnidadOrganica.setCue(UnidadOrganica.CUE_DEFAULT);
            nuevaUnidadOrganica.setNivel(UnidadOrganica.NIVEL_DEFAULT);
            nuevaUnidadOrganica.setSigla(UnidadOrganica.SIGLA_DEFAULT);
            myTratamiento.getUnidadOrganica().add(nuevaUnidadOrganica);

            Cargo nuevoCargo = new Cargo();
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);
            nuevoCargo.setClasificacion_funcional(list.get(0));
            nuevoCargo.setUnd_organica(nuevaUnidadOrganica);
            nuevoCargo.setCod_cargo(Cargo.CODIGO_DEFAULT);
            nuevoCargo.setDen_cargo(Cargo.DEN_DEFAULT);
            nuevoCargo.setEstado(Cargo.ESTADO_ALTA);
            nuevoCargo.setCtd_puestos_total(Cargo.CANT_MAX);
            myTratamiento.getCargo().add(nuevoCargo);

            //se guarda sola aca para no cambiar si los campos son true si alguien modifica estos campos despues de la primera cargada
            eue.setDef_servir(Boolean.TRUE);
            eue.setProc_batch(Boolean.TRUE);
            return eue;
        }

        return eueOrigen;
    }

    //conceptos Remunerativos
    public List<ConceptoRemunerativo> csvToListConceptoRemunerativo(List<List<String>> csvConceptos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        List<ConceptoRemunerativo> lconcepto = new LinkedList<ConceptoRemunerativo>();
        ConceptoRemunerativo cr;
        for (List<String> csvConcepto : csvConceptos) {
            cr = conceptoFromCsv(csvConcepto, session, errores, origenArchivo, is);
            if (cr == null) {
                continue;
            }
            lconcepto.add(cr);
        }

        return lconcepto;
    }

    public ConceptoRemunerativo conceptoFromCsv(List<String> _csvConcepto, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        ConceptoRemunerativo concepto = new ConceptoRemunerativo();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvConcepto.get(0));
        LineaInformeCodigo lic = new LineaInformeCodigo();

        //verificacion numero campos archivo
        if (_csvConcepto.size() != helpers.Constantes.CAMPOS_CONCEPTOS_REMUNERATIVOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CONCEPTOS_REMUNERATIVOS);
            return null;
        }

        //verificacion en la base de dato despues de la verificacion en los csv
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvConcepto.get(0), session, errores);
        //System.out.println("organismo informante dans concepto remunerativo "+eue.getCod_organismo());
        }*/

        //verifica si la entidad UE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvConcepto.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CONCEPTOS_REMUNERATIVOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvConcepto.get(0).trim().equals("")) || (_csvConcepto.get(1).trim().equals("")) || (_csvConcepto.get(2).trim().equals("")) || (_csvConcepto.get(6).trim().equals(""))) {
            lic.setCodigo(_csvConcepto.get(1));
            lic.setCodigoEntidadUE(_csvConcepto.get(0));
            lic.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.CONCEPTOS_REMUNERATIVOS);
            is.getLt().add(lic);
            return null;
        }

        Integer periodicadInt;
        try {
            periodicadInt = Integer.parseInt(_csvConcepto.get(6));
        } catch (NumberFormatException e) {
            errores.add(helpers.Errores.CODIGO_PERIODICIDAD_NO_ES_UN_INT);
            return null;
        }

        if (periodicadInt > 3) {
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.ERROR_ARCHIVO_CONCEPTO_REMUNERATIVO_PERIODICIDAD);
            is.getLt().add(lic);
            return null;
        }
        String periodicidad = ConceptoRemunerativo.PERIODICIDADES.get(CreadorDesdeCsv.toInteger(_csvConcepto.get(6)));

        concepto.setEntidadUE(eue);
        concepto.setCodigo(_csvConcepto.get(1));
        concepto.setDescripcion(_csvConcepto.get(2));
        concepto.setSustento_legal(_csvConcepto.get(3));
        concepto.setClasificacion(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoRemuneracion", _csvConcepto.get(4), errores, session));
        concepto.setConceptoStd(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoRemuneracionStd", _csvConcepto.get(5), errores, session));
        concepto.setPeriodicidad(periodicidad);

        if (eue.getId() == 0) {
            //nuveo concepto por ser nueva entidad
            lic = new LineaInformeCodigo();
            lic.setCodigo(concepto.getCodigo());
            lic.setCodigoEntidadUE(concepto.getEntidadUE().getCodigoEntidadUE());
            lic.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            is.getLt().add(lic);
            return concepto;
        }

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(ConceptoRemunerativo.class);
        c.createAlias("entidadUE", "entidadUE");
        c.add(Restrictions.eq("codigo", concepto.getCodigo()));
        c.add(Restrictions.eq("entidadUE.codigoEntidadUE", eue.getCodigoEntidadUE()));
        ConceptoRemunerativo corigen = concepto;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                //System.out.println("--------- modificacion del cargo");
                ConceptoRemunerativo cdestino = (ConceptoRemunerativo) c.list().get(0);
                lic = ComparadorEntidades.conceptos(corigen, cdestino);
                if (lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.conceptoRemunerativo(corigen, cdestino);
                    }
                    is.setModificado(is.getRechazado() + 1);
                }

                if (lic.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }
                is.getLt().add(lic);
                return cdestino;

            } else {
                //nuveo concepto
                lic = new LineaInformeCodigo();
                lic.setCodigo(corigen.getCodigo());
                lic.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                lic.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(lic);
                return corigen;
            }
        }

        return concepto;
    }

    //Unidades Organicas
    public List<UnidadOrganica> csvToListUnidadOrganica(List<List<String>> csvUnidadesOrganicas, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        List<UnidadOrganica> lunidade = new LinkedList<UnidadOrganica>();
        UnidadOrganica ua;
        for (List<String> csvUnidadOrganica : csvUnidadesOrganicas) {
            ua = unidadOrganicaFromCsv(csvUnidadOrganica, session, errores, origenArchivo, is);
            if (ua == null) {
                continue;
            }
            lunidade.add(ua);
        }
        return lunidade;
    }

    public UnidadOrganica unidadOrganicaFromCsv(List<String> _csvUnidadOrganica, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        UnidadOrganica unidadorganica = new UnidadOrganica();
        UnidadOrganica unidadorganicaAntecesora = new UnidadOrganica();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvUnidadOrganica.get(0));
        LineaInformeCodigo lic = new LineaInformeCodigo();

        //verificacion numero campos archivo
        if (_csvUnidadOrganica.size() != helpers.Constantes.CAMPOS_UNIDADES_ORGANICAS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.UNIDADES_ORGANICAS);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de unidadorganica
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvUnidadOrganica.get(0), session, errores);
        }*/

        //verifica se la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvUnidadOrganica.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.UNIDADES_ORGANICAS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvUnidadOrganica.get(0).trim().equals("")) || (_csvUnidadOrganica.get(1).trim().equals("")) || (_csvUnidadOrganica.get(2).trim().equals("")) || (_csvUnidadOrganica.get(8).trim().equals("")) || (_csvUnidadOrganica.get(9).trim().equals(""))) {
            lic.setCodigo(_csvUnidadOrganica.get(1));
            lic.setCodigoEntidadUE(_csvUnidadOrganica.get(0));
            lic.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.setRechazado(is.getRechazado() + 1);
            is.getLt().add(lic);
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.UNIDADES_ORGANICAS);
            return null;
        }

        unidadorganica.setEntidadUE(eue);
        unidadorganica.setCod_und_organica(_csvUnidadOrganica.get(1));
        unidadorganica.setDen_und_organica(_csvUnidadOrganica.get(2));
        unidadorganica.setLocalidad(_csvUnidadOrganica.get(3));
        unidadorganica.setCod_ubi_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvUnidadOrganica.get(4), errores, session));
        unidadorganica.setCod_ubi_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvUnidadOrganica.get(5), errores, session));
        unidadorganica.setCod_ubi_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvUnidadOrganica.get(6), errores, session));
        unidadorganica.setCue(_csvUnidadOrganica.get(7));
        unidadorganica.setSigla(_csvUnidadOrganica.get(8));
        unidadorganica.setNivel(CreadorDesdeCsv.toInteger(_csvUnidadOrganica.get(9)));
        unidadorganica.setTipoActividad(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoActividad", _csvUnidadOrganica.get(10), errores, session));
        unidadorganicaAntecesora = getUnidadOrganicaDesdeListUnidadOrganicaCSV(myTratamiento.getUnidadOrganica(), _csvUnidadOrganica.get(11), eue);
        if (unidadorganicaAntecesora == null) {
            unidadorganicaAntecesora = CreadorDesdeDB.getUnidadOrganicaWithCodigoUnidadOrganica(_csvUnidadOrganica.get(11), eue, session, errores);
        }


        // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(unidadorganica.getCod_ubi_dept(), unidadorganica.getCod_ubi_prov(),
                unidadorganica.getCod_ubi_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Unidad Orgánica:" + unidadorganica.getCod_und_organica());

        }
        unidadorganica.setUoAntecesora(unidadorganicaAntecesora);
        //verificando que el nivel de la antecesora sea menor que lo de la unidad organica
        if (unidadorganicaAntecesora != null) {
            if (unidadorganica.getNivel() - 1 != unidadorganicaAntecesora.getNivel()) {
                is.setRechazado(is.getRechazado() + 1);
                is.getLt().add(lic);
                errores.add(helpers.Errores.NIVEL_UNIDAD_ORGANICA_ANTECESORA_MAYOR_NIVEL_UNIDAD_ORGANICA + " Unidad organica: " + unidadorganica.getCod_und_organica() + " .Unidad organica antecesora: " + unidadorganicaAntecesora.getCod_und_organica());
                return null;
            }
        }

        //verificando que hay una unidad organica antecesora si el nivel de la unidad organica esta mayor que 1
        if (unidadorganica.getNivel() > 1) {
            if (unidadorganicaAntecesora == null) {
                is.setRechazado(is.getRechazado() + 1);
                is.getLt().add(lic);
                errores.add(helpers.Errores.NO_HAY_UNIDAD_ORGANICA_ANTECESORA + " Unidad organica: " + unidadorganica.getCod_und_organica());
                return null;
            }
        }

        unidadorganica.setEstado(UnidadOrganica.ESTADO_ALTA);

        if (errores.size() > 0) {
            return null;
        }

        if (eue.getId() == 0) {
            //nuvea unidad organica
            lic = new LineaInformeCodigo();
            lic.setCodigo(unidadorganica.getCod_und_organica());
            lic.setCodigoEntidadUE(unidadorganica.getEntidadUE().getCodigoEntidadUE());
            lic.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            is.getLt().add(lic);
            return unidadorganica;
        }


        Criteria c;
        c = myTratamiento.getSession().createCriteria(UnidadOrganica.class);
        c.createAlias("entidadUE", "entidadUE");
        c.add(Restrictions.like("cod_und_organica", unidadorganica.getCod_und_organica()));
        c.add(Restrictions.like("entidadUE.codigoEntidadUE", unidadorganica.getEntidadUE().getCodigoEntidadUE()));

        UnidadOrganica uoOrigen = unidadorganica;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                UnidadOrganica uodestino = (UnidadOrganica) c.list().get(0);
                lic = ComparadorEntidades.unidadesOrganicas(uoOrigen, uodestino);
                if (lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.unidadOrganica(uoOrigen, uodestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (lic.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }
                is.getLt().add(lic);
                return uodestino;
            } else {
                //nuvea unidad organica
                lic = new LineaInformeCodigo();
                lic.setCodigo(uoOrigen.getCod_und_organica());
                lic.setCodigoEntidadUE(uoOrigen.getEntidadUE().getCodigoEntidadUE());
                lic.setResultado(ResultadoOperacionCSV.NUEVO);
                is.getLt().add(lic);
                is.setAlta(is.getAlta() + 1);
                return uoOrigen;
            }
        }
        return unidadorganica;
    }

    //cargos
    public List<Cargo> csvToListCargo(List<List<String>> csvCargos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        List<Cargo> lcargo = new LinkedList<Cargo>();
        Cargo cargo;
        for (List<String> csvCargo : csvCargos) {
            cargo = cargoFromCsv(csvCargo, session, errores, origenArchivo, is);
            if (cargo == null) {
                continue;
            }
            lcargo.add(cargo);
        }
        return lcargo;
    }

    public Cargo cargoFromCsv(List<String> _csvCargo, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeCodigo> is) {
        Cargo cargo = new Cargo();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvCargo.get(0));
        LineaInformeCodigo lic = new LineaInformeCodigo();

        //verificacion numero campos archivo
        if (_csvCargo.size() != helpers.Constantes.CAMPOS_CARGOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CARGOS);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de cargo
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvCargo.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvCargo.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CARGOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        UnidadOrganica unidadorganica = getUnidadOrganicaDesdeListUnidadOrganicaCSV(myTratamiento.getUnidadOrganica(), _csvCargo.get(1), eue);

        if (unidadorganica == null) {
            unidadorganica = CreadorDesdeDB.getUnidadOrganicaWithCodigoUnidadOrganica(_csvCargo.get(1), eue, session, errores);
            if (unidadorganica != null) {
                myTratamiento.getUnidadOrganica().add(unidadorganica);
            }
        }

        //verifica si la unidad organica existe
        if (unidadorganica == null) {
            errores.add(helpers.Errores.CODIGO_UNIDAD_ORGANICA_NO_EXISTE_01 + _csvCargo.get(1) + helpers.Errores.CODIGO_UNIDAD_ORGANICA_NO_EXISTE_02 + helpers.Constantes.CARGOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvCargo.get(0).trim().equals("")) || (_csvCargo.get(1).trim().equals("")) || (_csvCargo.get(2).trim().equals("")) || (_csvCargo.get(3).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.CARGOS);
            lic.setCodigo(_csvCargo.get(1));
            lic.setCodigoEntidadUE(_csvCargo.get(0));
            lic.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(lic);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        cargo.setUnd_organica(unidadorganica);
        cargo.setCod_cargo(_csvCargo.get(2));
        cargo.setDen_cargo(_csvCargo.get(3));
        cargo.setEstado(getEstadoCargoFromBoolStr(_csvCargo.get(4), errores));
        cargo.setReg_lab_con(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("RegimenLaboralContractual", _csvCargo.get(5), errores, session));
        cargo.setHoras_x_sem(CreadorDesdeCsv.toInteger(_csvCargo.get(6)));
        cargo.setClasificacion_funcional(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("ClasificadorFuncional", _csvCargo.get(7), errores, session));
        cargo.setReq_hab_profesional(CreadorDesdeCsv.toBoolean(_csvCargo.get(8)));
        cargo.setDec_jurada_byr(CreadorDesdeCsv.toBoolean(_csvCargo.get(9)));
        cargo.setPresupuestado_PAP(CreadorDesdeCsv.toBoolean(_csvCargo.get(10)));
        cargo.setGrupoOcupacional(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("GrupoOcupacional", _csvCargo.get(11), errores, session));
        cargo.setNivelRemunerativo(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("NivelRemunerativo", _csvCargo.get(12), errores, session));
        cargo.setSituacion_CAP(CreadorDesdeDB.verificacionValorDatoAuxiliar("SituacionCAP", _csvCargo.get(13), errores, session));
        cargo.setPersonasCargo(CreadorDesdeCsv.toBoolean(_csvCargo.get(14)));
        cargo.setCtd_puestos_total(Cargo.CANT_DEFAULT);

        if (eue.getId() == 0 || unidadorganica.getId() == 0) {
            //nuveo cargo
            lic = new LineaInformeCodigo();
            lic.setCodigo(cargo.getCod_cargo());
            lic.setCodigoEntidadUE(cargo.getUnd_organica().getEntidadUE().getCodigoEntidadUE());
            lic.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            is.getLt().add(lic);
            return cargo;
        }

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Cargo.class);
        c.createAlias("und_organica.entidadUE", "entidadUE");
        c.add(Restrictions.like("cod_cargo", cargo.getCod_cargo()));
        c.add(Restrictions.like("entidadUE.codigoEntidadUE", cargo.getUnd_organica().getEntidadUE().getCodigoEntidadUE()));

        Cargo corigen = cargo;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Cargo cdestino = (Cargo) c.list().get(0);
                lic = ComparadorEntidades.cargos(corigen, cdestino);
                if (lic.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.cargo(corigen, cdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (lic.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(lic);
                return cdestino;

            } else {
                //nuveo cargo
                lic = new LineaInformeCodigo();
                lic.setCodigo(corigen.getCod_cargo());
                lic.setCodigoEntidadUE(corigen.getUnd_organica().getEntidadUE().getCodigoEntidadUE());
                lic.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(lic);
                return corigen;
            }
        }

        return cargo;
    }

    //trabajador
    public List<Trabajador> csvToListTrabajador(List<List<String>> csvTrabajadores, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Trabajador> ltrabajador = new LinkedList<Trabajador>();
        Trabajador trabajador;

        for (List<String> csvTrabajador : csvTrabajadores) {
            trabajador = trabajadorFromCsv(csvTrabajador, session, errores, origenArchivo, is);
            if (trabajador == null) {
                continue;
            }
            ltrabajador.add(trabajador);

        }
        return ltrabajador;
    }

    public Trabajador trabajadorFromCsv(List<String> _csvTrabajador, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Trabajador trabajador = new Trabajador();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvTrabajador.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvTrabajador.size() != helpers.Constantes.CAMPOS_TRABAJADORES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.TRABAJADORES);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvTrabajador.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvTrabajador.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.TRABAJADORES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvTrabajador.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setNumeroDocumento(_csvTrabajador.get(2));
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }
        //verificacion campos AZUL obligatorios
        if ((_csvTrabajador.get(0).trim().equals("")) || (_csvTrabajador.get(1).trim().equals("")) || (_csvTrabajador.get(2).trim().equals("")) || (_csvTrabajador.get(3).trim().equals("")) || (_csvTrabajador.get(4).trim().equals("")) || (_csvTrabajador.get(5).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.TRABAJADORES);
            litnd.setNumeroDocumento(_csvTrabajador.get(2));
            litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion si numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvTrabajador.get(2), errores);

        trabajador.setTipoDocumento(tipoDocumento);
        trabajador.setNroDocumento(_csvTrabajador.get(2));
        trabajador.setApellidoPaterno(_csvTrabajador.get(3));
        trabajador.setApellidoMaterno(_csvTrabajador.get(4));
        trabajador.setNombres(_csvTrabajador.get(5));
        trabajador.setSexo(CreadorDesdeDB.verificacionValorDatoAuxiliar("Sexo", _csvTrabajador.get(6), errores, session));
        trabajador.setFechaNacimiento(CreadorDesdeCsv.stringToDate(_csvTrabajador.get(7), errores, helpers.Constantes.TRABAJADORES));
        trabajador.setPais(CreadorDesdeDB.verificacionValorDatoAuxiliar("Paises", _csvTrabajador.get(8), errores, session));
        trabajador.setCod_ubi_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvTrabajador.get(9), errores, session));
        trabajador.setCod_ubi_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvTrabajador.get(10), errores, session));
        trabajador.setCod_ubi_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvTrabajador.get(11), errores, session));
        trabajador.setNacionalidad(CreadorDesdeDB.verificacionValorDatoAuxiliar("Nacionalidades", _csvTrabajador.get(12), errores, session));
        trabajador.setEstadoCivil(CreadorDesdeDB.verificacionValorDatoAuxiliar("EstadoCivil", _csvTrabajador.get(13), errores, session));
        trabajador.setDomicilioDireccion(_csvTrabajador.get(14));
        trabajador.setCod_dom_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvTrabajador.get(15), errores, session));
        trabajador.setCod_dom_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvTrabajador.get(16), errores, session));
        trabajador.setCod_dom_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvTrabajador.get(17), errores, session));
        trabajador.setDomicilioCodigoPostal(_csvTrabajador.get(18));
        trabajador.setEsSalud(_csvTrabajador.get(19));
        trabajador.setGrupoSanguineo(CreadorDesdeDB.verificacionValorDatoAuxiliar("GrupoSanguineo", _csvTrabajador.get(20), errores, session));
        trabajador.setTipoDiscapacidad(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDiscapacidad", _csvTrabajador.get(21), errores, session));
        trabajador.setNroCertificadoCONADIS(CreadorDesdeCsv.toInteger(_csvTrabajador.get(22)));
        trabajador.setNivelInstruccion(CreadorDesdeDB.verificacionValorDatoAuxiliar("NivelInstrucción", _csvTrabajador.get(23), errores, session));
        trabajador.setFormacionProfesional(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("FormacionProfesional", _csvTrabajador.get(24), errores, session));
        trabajador.setEmergenciaNombre(_csvTrabajador.get(25));
        trabajador.setEmergenciaDomicilio(_csvTrabajador.get(26));
        trabajador.setEmergenciaTelefonos(_csvTrabajador.get(27));
        trabajador.setRegimenPensionario(CreadorDesdeDB.verificacionValorDatoAuxiliar("RegPensionarios", _csvTrabajador.get(28), errores, session));
        trabajador.setEmailPersonal(_csvTrabajador.get(29));
        trabajador.setEmailLaboral(_csvTrabajador.get(30));
        trabajador.setNroRUC(_csvTrabajador.get(31));
        trabajador.setCodigoOSCE(_csvTrabajador.get(32));

                // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(trabajador.getCod_dom_dept(), trabajador.getCod_dom_prov(),
                trabajador.getCod_dom_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Trabajador doc:" + trabajador.getTipoDocumento() +
                    " "+ trabajador.getNroDocumento());
        }
               // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(trabajador.getCod_ubi_dept(), trabajador.getCod_ubi_prov(),
                trabajador.getCod_ubi_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Trabajador doc:" + trabajador.getTipoDocumento() +
                    " "+ trabajador.getNroDocumento());

        }
        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Trabajador.class).add(Restrictions.eq("tipoDocumento", trabajador.getTipoDocumento())).add(Restrictions.eq("nroDocumento", trabajador.getNroDocumento()));

        Trabajador torigen = trabajador;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Trabajador tdestino = (Trabajador) c.list().get(0);
                litnd = ComparadorEntidades.trabajador(torigen, tdestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.trabajador(torigen, tdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return tdestino;
            } else {
                //nuveo trabajador
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return torigen;
            }
        }

        //verifica si existe en la base de dato este trabajador
        if (CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvTrabajador.get(2), session, errores) == null) {
            if (!origenArchivo.equals(OrigenArchivos.CARGA_INICIAL_ORGANISMOS)) {
                litnd.setCodigoEntidadUE(_csvTrabajador.get(0));
                litnd.setNumeroDocumento(_csvTrabajador.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02
                        + _csvTrabajador.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.TRABAJADORES);
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        return trabajador;
    }

    //legajos
    public List<Legajo> csvToListLegajo(List<List<String>> csvLegajos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) {
        List<Legajo> llegajo = new LinkedList<Legajo>();
        Legajo legajo;

        for (List<String> csvLegajo : csvLegajos) {
            legajo = legajoFromCsv(csvLegajo, session, errores, origenArchivo, is);
            if (legajo == null) {
                continue;
            }

            // Chequeamos que el legajo de un trabajador y en una entidad no esté 2 veces (con distinto código)
            for (Legajo legCheck : llegajo) {
                if (legajo.getEntidadUE().getCodigoEntidadUE().equals(legCheck.getEntidadUE().getCodigoEntidadUE())
                        && legajo.getTrabajador().getTipoDocumento().equals(legCheck.getTrabajador().getTipoDocumento())
                        && legajo.getTrabajador().getNroDocumento().equals(legCheck.getTrabajador().getNroDocumento())
                        && (!legajo.getCod_legajo().equals(legCheck.getCod_legajo()))) {
                    errores.add(Errores.LEGAJO_MULTIPLE_MISMO_TRABAJADOR_ENTIDAD + " Código de legajo: " + legajo.getCod_legajo() );
                    continue;
                }
            }
            llegajo.add(legajo);
        }

        return llegajo;
    }

    public Legajo legajoFromCsv(List<String> _csvLegajo, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) {
        Legajo legajo = new Legajo();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvLegajo.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvLegajo.size() != helpers.Constantes.CAMPOS_LEGAJOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.LEGAJOS);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvLegajo.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvLegajo.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.LEGAJOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvLegajo.get(2), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvLegajo.get(0));
                litnd.setNumeroDocumento(_csvLegajo.get(3));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvLegajo.get(3), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvLegajo.get(3));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvLegajo.get(3), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //si el trabajador no existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvLegajo.get(0));
            litnd.setNumeroDocumento(_csvLegajo.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + _csvLegajo.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvLegajo.get(3) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.LEGAJOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvLegajo.get(0).trim().equals("")) || (_csvLegajo.get(1).trim().equals("")) || (_csvLegajo.get(2).trim().equals("")) || (_csvLegajo.get(3).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.LEGAJOS);
            litnd.setCodigoEntidadUE(_csvLegajo.get(0));
            litnd.setNumeroDocumento(_csvLegajo.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }
       


        legajo.setEntidadUE(eue);
        legajo.setCod_legajo(_csvLegajo.get(1));
        legajo.setTrabajador(trabajador);


        if (errores.size() > 0) {
            return null;
        }

                // Verifico si ya hay un legajo distinto al que se quiere ingresar en la base de datos.
        Criteria c;
        c = myTratamiento.getSession().createCriteria(Legajo.class)
                .createAlias("trabajador", "trabajador")
                .add(Restrictions.eq("trabajador.nroDocumento", trabajador.getNroDocumento()))
                .add(Restrictions.eq("trabajador.tipoDocumento", trabajador.getTipoDocumento()))
                .add(Restrictions.eq("entidadUE", eue))
                .add(Restrictions.ne("cod_legajo", legajo.getCod_legajo()));

        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                errores.add(Errores.LEGAJO_MULTIPLE_MISMO_TRABAJADOR_ENTIDAD + " Código de legajo: " + legajo.getCod_legajo() );
                return null;

            }
        }


        if (eue.getId() == 0) {
            litnd = new LineaInformeTipoNumeroDocumento();
            litnd.setNumeroDocumento(trabajador.getNroDocumento());
            litnd.setTipoDocumento(trabajador.getTipoDocumento());
            litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            is.getLt().add(litnd);
            return legajo;
        }


        c = myTratamiento.getSession().createCriteria(Legajo.class)
                .createAlias("trabajador", "trabajador")
                .add(Restrictions.eq("trabajador.nroDocumento", trabajador.getNroDocumento()))
                .add(Restrictions.eq("trabajador.tipoDocumento", trabajador.getTipoDocumento()))
                .add(Restrictions.eq("entidadUE", eue))
                .add(Restrictions.eq("cod_legajo", legajo.getCod_legajo()));

        Legajo lorigen = legajo;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Legajo ldestino = (Legajo) c.list().get(0);
                litnd = ComparadorEntidades.legajo(lorigen, ldestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.legajo(lorigen, ldestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }
                is.getLt().add(litnd);
                return ldestino;
            } else {
                //nuveo legajo
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return lorigen;
            }
        }

        return legajo;
    }

    //cargoAsignadoFromCsv
    public List<CargoAsignado> csvToListCargoAsignado(List<List<String>> csvCargosAsignado, Session session, List<String> errores, String origenArchivo, String tipoProceso, boolean yaEntrada, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<CargoAsignado> lcargoasignado = new LinkedList<CargoAsignado>();
        CargoAsignado ca;

        for (List<String> csvCargoAsignado : csvCargosAsignado) {
            ca = cargoAsignadoFromCsv(csvCargoAsignado, session, errores, origenArchivo, tipoProceso, yaEntrada, is);
            if (ca == null) {
                continue;
            }
            lcargoasignado.add(ca);
        }
        return lcargoasignado;
    }

    public CargoAsignado cargoAsignadoFromCsv(List<String> _csvCargosAsignado, Session session, List<String> errores, String origenArchivo, String tipoProceso, boolean yaEntrada, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        CargoAsignado cargoasignado = new CargoAsignado();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvCargosAsignado.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();


        //verificacion numero campos archivo
        if (_csvCargosAsignado.size() != helpers.Constantes.CAMPOS_CARGOS_ASIGNADOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CARGOS_ASIGNADOS);
            return null;
        }
        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvCargosAsignado.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvCargosAsignado.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CARGOS_ASIGNADOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvCargosAsignado.get(2), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvCargosAsignado.get(0));
                litnd.setNumeroDocumento(_csvCargosAsignado.get(3));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                is.setRechazado(is.getRechazado() + 1);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvCargosAsignado.get(3), errores);

        Cargo cargo = getCargoDesdeListCargoCSV(myTratamiento.getCargo(), _csvCargosAsignado.get(1), eue);

        //verificacion en la base de dato despues de la verificacion en la lista de cargo
        if (cargo == null) {
            cargo = CreadorDesdeDB.getCargoWithCodigoCargoCodigoEntidadUE(_csvCargosAsignado.get(1), _csvCargosAsignado.get(0), session, errores);
            if (cargo != null) {
                myTratamiento.getCargo().add(cargo);
            }
        }

        //verifica si el cargo existe
        if (cargo == null) {
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CARGO_NO_EXISTE_01 + _csvCargosAsignado.get(1) + helpers.Errores.CARGO_NO_EXISTE_01 + helpers.Constantes.CARGOS_ASIGNADOS);
            return null;
        }

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvCargosAsignado.get(3));

        //verificacion en la base de dato despues de la verificacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvCargosAsignado.get(3), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //para generar el csv (trabajador no existe)
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvCargosAsignado.get(0));
            litnd.setNumeroDocumento(_csvCargosAsignado.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvCargosAsignado.get(3) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.CARGOS_ASIGNADOS);
            return null;
        }

        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verificacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        //verifica si el legajo existe
        if (legajo == null) {
            errores.add(helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_02
                    + _csvCargosAsignado.get(3) + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_03 + _csvCargosAsignado.get(0)
                    + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_04 + helpers.Constantes.CARGOS_ASIGNADOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvCargosAsignado.get(0).trim().equals("")) || (_csvCargosAsignado.get(1).trim().equals("")) || (_csvCargosAsignado.get(2).trim().equals("")) || (_csvCargosAsignado.get(3).trim().equals("")) ||
                (_csvCargosAsignado.get(4).trim().equals("")) || (_csvCargosAsignado.get(5).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.CARGOS_ASIGNADOS);
            litnd.setCodigoEntidadUE(_csvCargosAsignado.get(0));
            litnd.setNumeroDocumento(_csvCargosAsignado.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        cargoasignado.setCargo(cargo);
        cargoasignado.setTrabajador(trabajador);
        cargoasignado.setLegajo(legajo);
        cargoasignado.setEstado(getEstadoCAFromBoolStr(_csvCargosAsignado.get(4), errores));
        cargoasignado.setFec_inicio(CreadorDesdeCsv.stringToDate(_csvCargosAsignado.get(5), errores, helpers.Constantes.CARGOS_ASIGNADOS));
        cargoasignado.setFec_fin(CreadorDesdeCsv.stringToDate(_csvCargosAsignado.get(6), errores, helpers.Constantes.CARGOS_ASIGNADOS));
        cargoasignado.setCtd_per_superv(CreadorDesdeCsv.toInteger(_csvCargosAsignado.get(7)));
        cargoasignado.setMotivo_cese(_csvCargosAsignado.get(8));
        cargoasignado.setTipoVinculo(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoVínculo", _csvCargosAsignado.get(9), errores, session));

        //si hay una fecha de fin y el estado esta alta, tira un error
        if (!_csvCargosAsignado.get(6).trim().equals("") && _csvCargosAsignado.get(4).trim().equals(helpers.Constantes.ESTADO_ACTIVO)) {
            errores.add(helpers.Errores.ESTADO_ALTO_FECHA_FIN);
        }


        if (cargoasignado.getFec_fin() != null && cargoasignado.getFec_fin().before(cargoasignado.getFec_inicio())) {
            errores.add(Errores.ERROR_FECHA_HASTA_PREVIA_DESDE + helpers.Constantes.CARGOS_ASIGNADOS);
        }
        if (errores.size() > 0) {
            return null;
        }

        if (cargo.getId() == 0 || legajo.getid() == 0) {
            //nuveo cargo asignado
            litnd = new LineaInformeTipoNumeroDocumento();
            litnd.setNumeroDocumento(trabajador.getNroDocumento());
            litnd.setTipoDocumento(trabajador.getTipoDocumento());
            litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            is.getLt().add(litnd);
            return cargoasignado;
        }

        //verifica si el cargo ya no esta asignado
        /*if (Helpers.getCantPuestosOcupadosCargo(session, cargo) > cargo.getCtd_puestos_total()) {
            is.setRechazado(is.getRechazado() + 1);
            errores.add(Errores.ERROR_CARGO_OCUPADO + " Cargo: " + cargo.getCod_cargo());
            return null;
        }*/

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(CargoAsignado.class)
                .createAlias("cargo", "cargo")
                .createAlias("legajo", "legajo")
                .add(Restrictions.eq("cargo.cod_cargo", cargoasignado.getCargo().getCod_cargo()))
                .add(Restrictions.eq("legajo.cod_legajo", cargoasignado.getLegajo().getCod_legajo()));
        
        CargoAsignado caOrigen = cargoasignado;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                CargoAsignado caDestino = (CargoAsignado) c.list().get(0);
                litnd = ComparadorEntidades.cargoasignado(caOrigen, caDestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.cargoAsignado(caOrigen, caDestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }
                is.getLt().add(litnd);
                return caDestino;

            } else {
                
                //verifica si el cargo ya no esta asignado
                if (Helpers.getCantPuestosOcupadosCargo(session, cargo) > cargo.getCtd_puestos_total()) {
                    is.setRechazado(is.getRechazado() + 1);
                    errores.add(Errores.ERROR_CARGO_OCUPADO + " Cargo: " + cargo.getCod_cargo());
                    return null;
                }
                
                //nuveo cargo asignado
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return caOrigen;
            }
        }

        return cargoasignado;
    }

    //familiares
    public List<Familiar> csvToListFamiliar(List<List<String>> csvFamiliares, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Familiar> lfamiliar = new LinkedList<Familiar>();
        Familiar familiar;
        for (List<String> csvFamiliar : csvFamiliares) {
            familiar = familiarFromCsv(csvFamiliar, session, errores, origenArchivo, is);
            if (familiar == null) {
                continue;
            }
            lfamiliar.add(familiar);
        }
        return lfamiliar;
    }

    public Familiar familiarFromCsv(List<String> _csvFamiliar, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Familiar familiar = new Familiar();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvFamiliar.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvFamiliar.size() != helpers.Constantes.CAMPOS_FAMILIARES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.FAMILIARES);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de familiares
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvFamiliar.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvFamiliar.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.FAMILIARES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvFamiliar.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvFamiliar.get(0));
                litnd.setNumeroDocumento(_csvFamiliar.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvFamiliar.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvFamiliar.get(2));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvFamiliar.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvFamiliar.get(0));
            litnd.setNumeroDocumento(_csvFamiliar.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvFamiliar.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.FAMILIARES);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvFamiliar.get(0).trim().equals("")) || (_csvFamiliar.get(1).trim().equals("")) || (_csvFamiliar.get(2).trim().equals("")) || (_csvFamiliar.get(3).trim().equals("")) || (_csvFamiliar.get(4).trim().equals("")) || (_csvFamiliar.get(5).trim().equals("")) || (_csvFamiliar.get(6).trim().equals("")) || (_csvFamiliar.get(7).trim().equals("")) || (_csvFamiliar.get(8).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.FAMILIARES);
            litnd.setCodigoEntidadUE(_csvFamiliar.get(0));
            litnd.setNumeroDocumento(_csvFamiliar.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la base de dato
        Legajo legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(myTratamiento.getSession(), trabajador, errores, eue);

        //verificacion en el CSV despues de la verifiacion en la base de dato
        if (legajo == null) {
            legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }


        //verificacion si numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvFamiliar.get(5), errores);
        familiar.setTrabajador(trabajador);
        familiar.setParentesco(CreadorDesdeDB.verificacionValorDatoAuxiliar("GradoParentesco", _csvFamiliar.get(3), errores, session));
        familiar.setTipoDocumento(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvFamiliar.get(4), errores, session));
        familiar.setNroDocumento(_csvFamiliar.get(5));
        familiar.setApellidoPaterno(_csvFamiliar.get(6));
        familiar.setApellidoMaterno(_csvFamiliar.get(7));
        familiar.setNombres(_csvFamiliar.get(8));
        familiar.setSexo(CreadorDesdeDB.verificacionValorDatoAuxiliar("Sexo", _csvFamiliar.get(9), errores, session));
        familiar.setFechaNacimiento(CreadorDesdeCsv.stringToDate(_csvFamiliar.get(10), errores, helpers.Constantes.FAMILIARES));
        familiar.setPais(CreadorDesdeDB.verificacionValorDatoAuxiliar("Paises", _csvFamiliar.get(11), errores, session));
        familiar.setCod_ubi_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvFamiliar.get(12), errores, session));
        familiar.setCod_ubi_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvFamiliar.get(13), errores, session));
        familiar.setCod_ubi_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvFamiliar.get(14), errores, session));
        familiar.setNacionalidad(CreadorDesdeDB.verificacionValorDatoAuxiliar("Nacionalidades", _csvFamiliar.get(15), errores, session));
        familiar.setEstadoCivil(CreadorDesdeDB.verificacionValorDatoAuxiliar("EstadoCivil", _csvFamiliar.get(16), errores, session));
        familiar.setDomicilioDireccion(_csvFamiliar.get(17));
        familiar.setCod_dom_dist(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDistrito", _csvFamiliar.get(18), errores, session));
        familiar.setCod_dom_prov(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBProvincia", _csvFamiliar.get(19), errores, session));
        familiar.setCod_dom_dept(CreadorDesdeDB.verificacionCodigoDatoAuxiliar("UBDepartamento", _csvFamiliar.get(20), errores, session));
        familiar.setDomicilioCodigoPostal(_csvFamiliar.get(21));
        familiar.setEsSalud(_csvFamiliar.get(22));
        familiar.setGrupoSanguineo(CreadorDesdeDB.verificacionValorDatoAuxiliar("GrupoSanguineo", _csvFamiliar.get(23), errores, session));
        familiar.setNivelInstruccion(CreadorDesdeDB.verificacionValorDatoAuxiliar("NivelInstrucción", _csvFamiliar.get(24), errores, session));

                // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(familiar.getCod_dom_dept(), familiar.getCod_dom_prov(),
                familiar.getCod_dom_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Familiar doc:" + familiar.getTipoDocumento() +
                    " "+ familiar.getNroDocumento());

        }
               // Chequeo cascada de ubigeo
        if(!Helpers.isUbigeoValido(familiar.getCod_ubi_dept(), familiar.getCod_ubi_prov(),
                familiar.getCod_ubi_dist(), session)) {
            errores.add(Errores.ERROR_CASCADA_UBIGEO + " Familiar doc:" + familiar.getTipoDocumento() +
                    " "+ familiar.getNroDocumento());
        }

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Familiar.class).add(Restrictions.eq("tipoDocumento", familiar.getTipoDocumento())).add(Restrictions.eq("nroDocumento", familiar.getNroDocumento()));
        Familiar forigen = familiar;

        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Familiar fdestino = (Familiar) c.list().get(0);
                litnd = ComparadorEntidades.familiares(forigen, fdestino, eue.getCodigoEntidadUE());
                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.familiar(forigen, fdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }
                is.getLt().add(litnd);
                return fdestino;
            } else {
                //nuvea familiar
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(forigen.getNroDocumento());
                litnd.setTipoDocumento(forigen.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return forigen;
            }
        }

        return familiar;
    }

    //titulos
    public List<Titulo> CsvToListTitulo(List<List<String>> csvTitulos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Titulo> ltitulo = new LinkedList<Titulo>();
        Titulo titulo;
        for (List<String> csvTitulo : csvTitulos) {
            titulo = tituloFromCsv(csvTitulo, session, errores, origenArchivo, is);
            if (titulo == null) {
                continue;
            }
            ltitulo.add(titulo);
        }
        return ltitulo;
    }

    public Titulo tituloFromCsv(List<String> _csvTitulo, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Titulo titulo = new Titulo();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvTitulo.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvTitulo.size() != helpers.Constantes.CAMPOS_TITULOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.TITULOS);
            return null;
        }

        //verificacion en la base de dato despues de la verificacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvTitulo.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvTitulo.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.TITULOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvTitulo.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvTitulo.get(0));
                litnd.setNumeroDocumento(_csvTitulo.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvTitulo.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvTitulo.get(2));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvTitulo.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvTitulo.get(0));
            litnd.setNumeroDocumento(_csvTitulo.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvTitulo.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.TITULOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvTitulo.get(0).trim().equals("")) || (_csvTitulo.get(1).trim().equals("")) ||
                (_csvTitulo.get(2).trim().equals("")) || (_csvTitulo.get(3).trim().equals("")) ||
                (_csvTitulo.get(4).trim().equals("")) || (_csvTitulo.get(6).trim().equals("")) ||
                (_csvTitulo.get(7).trim().equals("")) || (_csvTitulo.get(8).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.TITULOS);
            litnd.setCodigoEntidadUE(_csvTitulo.get(0));
            litnd.setNumeroDocumento(_csvTitulo.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la base de dato
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        titulo.setTrabajador(trabajador);
        titulo.setNivel(CreadorDesdeDB.verificacionValorDatoAuxiliar("NivelTitulo", _csvTitulo.get(3), errores, session));
        titulo.setDenominacion(_csvTitulo.get(4));
        titulo.setEspecialidad(_csvTitulo.get(5));
        titulo.setCentro_estudios(_csvTitulo.get(6));
        titulo.setLugar_emision(_csvTitulo.get(7));
        titulo.setFec_emision(CreadorDesdeCsv.stringToDate(_csvTitulo.get(8), errores, helpers.Constantes.TITULOS));
        titulo.setColegio_profesional(_csvTitulo.get(9));
        titulo.setNum_colegiatura(_csvTitulo.get(10));


        
        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Titulo.class).add(Restrictions.eq("fec_emision", titulo.getFec_emision())).add(Restrictions.eq("trabajador", trabajador)).add(Restrictions.eq("denominacion", titulo.getDenominacion()));

        Titulo torigen = titulo;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Titulo tdestino = (Titulo) c.list().get(0);
                litnd = ComparadorEntidades.titulos(torigen, tdestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());
                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.titulo(torigen, tdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return tdestino;
            } else {
                //nuveo titulo
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return torigen;
            }

        }

        return titulo;
    }

    //certificaciones
    public List<Certificacion> csvToListCertificacion(List<List<String>> csvCertificacions, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Certificacion> lcertificacion = new LinkedList<Certificacion>();
        Certificacion certificacion;
        for (List<String> csvCertificacion : csvCertificacions) {
            certificacion = certificacionFromCsv(csvCertificacion, session, errores, origenArchivo, is);
            if (certificacion == null) {
                continue;
            }
            lcertificacion.add(certificacion);

        }
        return lcertificacion;
    }

    public Certificacion certificacionFromCsv(List<String> _csvCertificacion, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Certificacion certificacion = new Certificacion();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvCertificacion.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvCertificacion.size() != helpers.Constantes.CAMPOS_CERTIFICACIONES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CERTIFICACIONES);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvCertificacion.get(0), session, errores);
        }*/

        //verifica si la unidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvCertificacion.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CERTIFICACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvCertificacion.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvCertificacion.get(0));
                litnd.setNumeroDocumento(_csvCertificacion.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvCertificacion.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvCertificacion.get(2));

        //verificacion en la base de dato despues de la verificacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvCertificacion.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvCertificacion.get(0));
            litnd.setNumeroDocumento(_csvCertificacion.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvCertificacion.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.CERTIFICACIONES);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvCertificacion.get(0).trim().equals("")) || (_csvCertificacion.get(1).trim().equals("")) || (_csvCertificacion.get(2).trim().equals("")) || (_csvCertificacion.get(3).trim().equals("")) || (_csvCertificacion.get(5).trim().equals("")) || (_csvCertificacion.get(6).trim().equals("")) || (_csvCertificacion.get(7).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + " Certificaciones!");
            litnd.setCodigoEntidadUE(_csvCertificacion.get(0));
            litnd.setNumeroDocumento(_csvCertificacion.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la base de dato
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verificacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        certificacion.setTrabajador(trabajador);
        certificacion.setDenominacion(_csvCertificacion.get(3));
        certificacion.setEspecialidad(_csvCertificacion.get(4));
        certificacion.setEntidad_certificante(_csvCertificacion.get(5));
        certificacion.setLugar_emision(_csvCertificacion.get(6));
        certificacion.setFec_emision(CreadorDesdeCsv.stringToDate(_csvCertificacion.get(7), errores, helpers.Constantes.CERTIFICACIONES));

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Certificacion.class).add(Restrictions.eq("fec_emision", certificacion.getFec_emision())).add(Restrictions.eq("trabajador", trabajador)).add(Restrictions.eq("denominacion", certificacion.getDenominacion()));

        Certificacion corigen = certificacion;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Certificacion cdestino = (Certificacion) c.list().get(0);
                litnd = ComparadorEntidades.certificaciones(corigen, cdestino, trabajador.getNroDocumento(),
                        trabajador.getTipoDocumento(), eue.getCodigoEntidadUE());
                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.certificacion(corigen, cdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return cdestino;
            } else {
                //nuvea linea
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return corigen;
            }

        }

        return certificacion;
    }

    //cursos
    public List<Curso> csvToListCurso(List<List<String>> csvCursos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Curso> lcurso = new LinkedList<Curso>();
        Curso curso;
        for (List<String> csvCurso : csvCursos) {
            curso = cursoFromCsv(csvCurso, session, errores, origenArchivo, is);
            if (curso == null) {
                continue;
            }
            lcurso.add(curso);

        }
        return lcurso;
    }

    public Curso cursoFromCsv(List<String> _csvCurso, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Curso curso = new Curso();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvCurso.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvCurso.size() != helpers.Constantes.CAMPOS_CURSOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CURSOS);
            return null;
        }

        //verificacion en la base de dato despues de la verificacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvCurso.get(0), session, errores);
        }*/

        //verifica si la entidadUE informante existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvCurso.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CURSOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvCurso.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvCurso.get(0));
                litnd.setNumeroDocumento(_csvCurso.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvCurso.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvCurso.get(2));

        //verificacion en la base de dato despues de la verificacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvCurso.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvCurso.get(0));
            litnd.setNumeroDocumento(_csvCurso.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvCurso.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.CURSOS);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvCurso.get(0).trim().equals("")) || (_csvCurso.get(1).trim().equals("")) || (_csvCurso.get(2).trim().equals("")) || (_csvCurso.get(3).trim().equals("")) || (_csvCurso.get(4).trim().equals("")) || (_csvCurso.get(6).trim().equals("")) || (_csvCurso.get(7).trim().equals("")) || (_csvCurso.get(8).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + " Cursos!");
            litnd.setCodigoEntidadUE(_csvCurso.get(0));
            litnd.setNumeroDocumento(_csvCurso.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la base de dato
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        curso.setTrabajador(trabajador);
        curso.setDenominacion(_csvCurso.get(3));
        curso.setCentro_estudios(_csvCurso.get(4));
        curso.setHoras(CreadorDesdeCsv.toFloat(_csvCurso.get(5)));
        curso.setFinanciadoEntidad(CreadorDesdeCsv.toBoolean(_csvCurso.get(6)));
        curso.setLugar_dictado(_csvCurso.get(7));
        curso.setFec_emision(CreadorDesdeCsv.stringToDate(_csvCurso.get(8), errores, helpers.Constantes.CURSOS));

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Curso.class).add(Restrictions.eq("fec_emision", curso.getFec_emision())).add(Restrictions.eq("trabajador", trabajador)).add(Restrictions.eq("denominacion", curso.getDenominacion()));

        Curso corigen = curso;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Curso cdestino = (Curso) c.list().get(0);
                litnd = ComparadorEntidades.cursos(corigen, cdestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.curso(corigen, cdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return cdestino;
            } else {
                //nuvea curso
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return corigen;
            }

        }

        return curso;
    }

    //antecedentes laborales
    public List<Ant_Laborales> csvToListAnt_Laborales(List<List<String>> csvAnt_Laborales, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Ant_Laborales> lant_laborale = new LinkedList<Ant_Laborales>();
        Ant_Laborales al;
        for (List<String> csvAnt_Laboral : csvAnt_Laborales) {
            al = ant_laboralFromCsv(csvAnt_Laboral, session, errores, origenArchivo, is);
            if (al == null) {
                continue;
            }
            lant_laborale.add(al);

        }
        return lant_laborale;
    }

    public Ant_Laborales ant_laboralFromCsv(List<String> _csvAnt_Laboral, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Ant_Laborales ant_laborale = new Ant_Laborales();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvAnt_Laboral.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvAnt_Laboral.size() != helpers.Constantes.CAMPOS_ANTECEDENTES_LABORALES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.ANTECEDENTES_LABORALES);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvAnt_Laboral.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvAnt_Laboral.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.ANTECEDENTES_LABORALES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvAnt_Laboral.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvAnt_Laboral.get(0));
                litnd.setNumeroDocumento(_csvAnt_Laboral.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvAnt_Laboral.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvAnt_Laboral.get(2));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvAnt_Laboral.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvAnt_Laboral.get(0));
            litnd.setNumeroDocumento(_csvAnt_Laboral.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvAnt_Laboral.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.ANTECEDENTES_LABORALES);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvAnt_Laboral.get(0).trim().equals("")) || (_csvAnt_Laboral.get(1).trim().equals("")) || 
                (_csvAnt_Laboral.get(2).trim().equals("")) || (_csvAnt_Laboral.get(3).trim().equals("")) ||
                (_csvAnt_Laboral.get(4).trim().equals("")) || (_csvAnt_Laboral.get(5).trim().equals("")) ||
                (_csvAnt_Laboral.get(6).trim().equals("")) || (_csvAnt_Laboral.get(7).trim().equals(""))) {
            litnd.setCodigoEntidadUE(_csvAnt_Laboral.get(0));
            litnd.setNumeroDocumento(_csvAnt_Laboral.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.ANTECEDENTES_LABORALES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la lista de legajo
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        ant_laborale.setTrabajador(trabajador);
        ant_laborale.setCargo(_csvAnt_Laboral.get(3));
        ant_laborale.setEmpresa(_csvAnt_Laboral.get(4));
        ant_laborale.setFuncion(_csvAnt_Laboral.get(5));
        ant_laborale.setFec_ingreso(CreadorDesdeCsv.stringToDate(_csvAnt_Laboral.get(6), errores, helpers.Constantes.ANTECEDENTES_LABORALES));
        ant_laborale.setFec_egreso(CreadorDesdeCsv.stringToDate(_csvAnt_Laboral.get(7), errores, helpers.Constantes.ANTECEDENTES_LABORALES));
        ant_laborale.setEntidadPublica(CreadorDesdeCsv.toBoolean(_csvAnt_Laboral.get(8)));
        ant_laborale.setArea(_csvAnt_Laboral.get(9));
        ant_laborale.setRegLaboral(_csvAnt_Laboral.get(10));


        if (ant_laborale.getFec_egreso().before(ant_laborale.getFec_ingreso())) {
            errores.add(Errores.ERROR_FECHA_EGRESO_PREVIA_INGRESO + helpers.Constantes.ANTECEDENTES_LABORALES);
        }


        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Ant_Laborales.class)
                .add(Restrictions.eq("trabajador", trabajador))
                .add(Restrictions.eq("fec_ingreso", ant_laborale.getFec_ingreso()));

        Ant_Laborales aorigen = ant_laborale;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Ant_Laborales adestino = (Ant_Laborales) c.list().get(0);
                litnd = ComparadorEntidades.antecedenteslaborales(aorigen, adestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.ant_laborales(aorigen, adestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return adestino;
            } else {
                //nuvea antecedent
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return aorigen;
            }

        }

        return ant_laborale;
    }

    //meritos demeritos
    public List<MeritoDemerito> csvToListMeritoDemerito(List<List<String>> csvMeritoDemeritos, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<MeritoDemerito> lmeritodemerito = new LinkedList<MeritoDemerito>();
        MeritoDemerito md;
        for (List<String> csvMeritoDemerito : csvMeritoDemeritos) {
            md = meritodemeritoFromCsv(csvMeritoDemerito, session, errores, origenArchivo, is);
            if (md == null) {
                continue;
            }
            lmeritodemerito.add(md);

        }
        return lmeritodemerito;
    }

    public MeritoDemerito meritodemeritoFromCsv(List<String> _csvMeritoDemerito, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        MeritoDemerito meritodemerito = new MeritoDemerito();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvMeritoDemerito.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvMeritoDemerito.size() != helpers.Constantes.CAMPOS_MERITOS_DEMERITOS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.MERITOS_DEMERITOS);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvMeritoDemerito.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvMeritoDemerito.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.MERITOS_DEMERITOS);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvMeritoDemerito.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvMeritoDemerito.get(0));
                litnd.setNumeroDocumento(_csvMeritoDemerito.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvMeritoDemerito.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvMeritoDemerito.get(2));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvMeritoDemerito.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvMeritoDemerito.get(0));
            litnd.setNumeroDocumento(_csvMeritoDemerito.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvMeritoDemerito.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.MERITOS_DEMERITOS);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvMeritoDemerito.get(0).trim().equals("")) || (_csvMeritoDemerito.get(1).trim().equals("")) || (_csvMeritoDemerito.get(2).trim().equals("")) || (_csvMeritoDemerito.get(3).trim().equals("")) || (_csvMeritoDemerito.get(4).trim().equals("")) || (_csvMeritoDemerito.get(5).trim().equals("")) || (_csvMeritoDemerito.get(6).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.MERITOS_DEMERITOS);
            litnd.setCodigoEntidadUE(_csvMeritoDemerito.get(0));
            litnd.setNumeroDocumento(_csvMeritoDemerito.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la lista de legajo
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verificacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String clase = null;
        String tipo = null;

        if (_csvMeritoDemerito.get(3).trim().equals("1")) {
            clase = MeritoDemerito.CLASE_MERITO;
            tipo = "TiposMerito";
        }

        if (_csvMeritoDemerito.get(3).trim().equals("2")) {
            clase = MeritoDemerito.CLASE_DEMERITO;
            tipo = "TiposDemerito";
        }

        if (clase == null) {
            errores.add(helpers.Errores.CODIGO_CLASE_MERITO_DEMERITO_NO_EXISTE);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        meritodemerito.setTrabajador(trabajador);
        meritodemerito.setClase(clase);
        meritodemerito.setTipo(CreadorDesdeDB.verificacionValorDatoAuxiliar(tipo, _csvMeritoDemerito.get(4), errores, session));
        meritodemerito.setFecha(CreadorDesdeCsv.stringToDate(_csvMeritoDemerito.get(5), errores, helpers.Constantes.MERITOS_DEMERITOS));
        meritodemerito.setMotivo(_csvMeritoDemerito.get(6));
        meritodemerito.setDetalle(_csvMeritoDemerito.get(7));

        if (errores.size() > 0) {
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(MeritoDemerito.class).add(Restrictions.eq("tipo", meritodemerito.getTipo())).add(Restrictions.eq("trabajador", trabajador)).add(Restrictions.eq("fecha", meritodemerito.getFecha()));

        MeritoDemerito mdorigen = meritodemerito;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                MeritoDemerito mddestino = (MeritoDemerito) c.list().get(0);
                litnd = ComparadorEntidades.meritosdemeritos(mdorigen, mddestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.meritoDemerito(mdorigen, mddestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return mddestino;
            } else {
                //nuvea merito demerito
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return mdorigen;
            }
        }

        return meritodemerito;
    }

    //produccion intelectual
    public List<Publicacion> csvToListProduccionIntelectual(List<List<String>> csvProduccionesIntelectuales, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        List<Publicacion> lproduccionintelectual = new LinkedList<Publicacion>();
        Publicacion pi;
        for (List<String> csvProduccionIntelectual : csvProduccionesIntelectuales) {
            pi = produccionintelectualFromCsv(csvProduccionIntelectual, session, errores, origenArchivo, is);
            if (pi == null) {
                continue;
            }
            lproduccionintelectual.add(pi);
        }
        return lproduccionintelectual;
    }

    public Publicacion produccionintelectualFromCsv(List<String> _csvProductionIntelectual, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        Publicacion produccionintelectual = new Publicacion();
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvProductionIntelectual.get(0));
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvProductionIntelectual.size() != helpers.Constantes.CAMPOS_PRODUCIONES_INTELECTUALES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            return null;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvProductionIntelectual.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvProductionIntelectual.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvProductionIntelectual.get(1), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvProductionIntelectual.get(0));
                litnd.setNumeroDocumento(_csvProductionIntelectual.get(2));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return null;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvProductionIntelectual.get(2), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvProductionIntelectual.get(2));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvProductionIntelectual.get(2), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvProductionIntelectual.get(0));
            litnd.setNumeroDocumento(_csvProductionIntelectual.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvProductionIntelectual.get(2) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            return null;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvProductionIntelectual.get(0).trim().equals("")) || (_csvProductionIntelectual.get(1).trim().equals("")) || (_csvProductionIntelectual.get(2).trim().equals("")) || (_csvProductionIntelectual.get(3).trim().equals("")) || (_csvProductionIntelectual.get(4).trim().equals("")) || (_csvProductionIntelectual.get(5).trim().equals("")) || (_csvProductionIntelectual.get(6).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.PRODUCIONES_INTELECTUALES);
            litnd.setCodigoEntidadUE(_csvProductionIntelectual.get(0));
            litnd.setNumeroDocumento(_csvProductionIntelectual.get(2));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        //verifica si existe en el registro el legajo del trabajador en la lista de legajo
        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verificacion en la liste de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }


        if (legajo == null) {
            errores.add(helpers.Errores.NO_HAY_UN_LEGAJO_QUE_TENGA_ESTE_ENTIDAD_UEJECUTORA + eue.getCodigoEntidadUE() + helpers.Errores.Y_ESTE_TRABAJADOR + trabajador.getNombres() + " !");
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        String clase = null;
        String tipo = null;

        if (_csvProductionIntelectual.get(3).trim().equals("1")) {
            clase = Publicacion.CLASE_PUBLICACION;
            tipo = "TiposPublicacion";
        }

        if (_csvProductionIntelectual.get(3).trim().equals("2")) {
            clase = Publicacion.CLASE_INVESTIGACION;
            tipo = "TiposTrabajosInvestigacion";
        }

        if (clase == null) {
            errores.add(helpers.Errores.CODIGO_CLASE_PRODUCION_INTELECTUAL_NO_EXISTE);
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        produccionintelectual.setClase(clase);
        produccionintelectual.setTipo(CreadorDesdeDB.verificacionValorDatoAuxiliar(tipo, _csvProductionIntelectual.get(4), errores, session));
        produccionintelectual.setTitulo(_csvProductionIntelectual.get(5));
        produccionintelectual.setFecha(CreadorDesdeCsv.stringToDate(_csvProductionIntelectual.get(6), errores, helpers.Constantes.PRODUCIONES_INTELECTUALES));
        produccionintelectual.setDescripcion(_csvProductionIntelectual.get(7));
        produccionintelectual.setEntidad(CreadorDesdeCsv.toBoolean(_csvProductionIntelectual.get(8)));
        produccionintelectual.setTrabajador(trabajador);

        if (errores.size() > 0) {
            is.setRechazado(is.getRechazado() + 1);
            return null;
        }

        Criteria c;
        c = myTratamiento.getSession().createCriteria(Publicacion.class).add(Restrictions.eq("tipo", produccionintelectual.getTipo())).add(Restrictions.eq("trabajador", trabajador)).add(Restrictions.eq("fecha", produccionintelectual.getFecha()));

        Publicacion porigen = produccionintelectual;
        if (c.list() != null) {
            if (!c.list().isEmpty()) {
                //modificacion
                Publicacion pdestino = (Publicacion) c.list().get(0);
                litnd = ComparadorEntidades.produccionesintelecuales(porigen, pdestino,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    // quiero guardar en el objeto de la base, los datos del objeto del csv
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.produccionIntelectual(porigen, pdestino);
                    }
                    is.setModificado(is.getModificado() + 1);
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    //hacemos nada
                    is.setCambio(is.getCambio() + 1);
                }

                is.getLt().add(litnd);
                return pdestino;
            } else {
                //nuvea produccion
                litnd = new LineaInformeTipoNumeroDocumento();
                litnd.setNumeroDocumento(trabajador.getNroDocumento());
                litnd.setTipoDocumento(trabajador.getTipoDocumento());
                litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
                litnd.setResultado(ResultadoOperacionCSV.NUEVO);
                is.setAlta(is.getAlta() + 1);
                is.getLt().add(litnd);
                return porigen;
            }

        }

        return produccionintelectual;
    }

    //remuneraciones personales
    public void listRemuneracionPersonalVerificacionesErrores(List<List<String>> csvRemuneracionPersonales, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) {

        for (List<String> csvRemuneracionPersonal : csvRemuneracionPersonales) {
            remuneracionpersonalVerificacionesErrores(csvRemuneracionPersonal, session, errores, origenArchivo, is);
        }

        return;
    }

    public void remuneracionpersonalVerificacionesErrores(List<String> _csvRemuneracionPersonal, Session session, List<String> errores, String origenArchivo, InformeSalida<LineaInformeTipoNumeroDocumento> is) {
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvRemuneracionPersonal.get(0));
        RemuneracionPersonal remuneracionpersonal = new RemuneracionPersonal();
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();
        List<RemuneracionPersonal> lEval;

        //verificacion numero campos archivo
        if (_csvRemuneracionPersonal.size() != helpers.Constantes.CAMPOS_REMUNERACIONES_PERSONALES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.REMUNERACIONES_PERSONALES);
            return;
        }

        //verificacion en la base de dato despues de la verificacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvRemuneracionPersonal.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvRemuneracionPersonal.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Cargo cargo = getCargoDesdeListCargoCSV(myTratamiento.getCargo(), _csvRemuneracionPersonal.get(1), eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de cargo
        if (cargo == null) {
            cargo = CreadorDesdeDB.getCargoWithCodigoCargoCodigoEntidadUE(_csvRemuneracionPersonal.get(1), _csvRemuneracionPersonal.get(0), session, errores);
            if (cargo != null) {
                myTratamiento.getCargo().add(cargo);
            }
        }

        //verifica si existe en el registro el cargo
        if (cargo == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_NO_EXISTE_01 + _csvRemuneracionPersonal.get(1) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_02 + _csvRemuneracionPersonal.get(0) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_03 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvRemuneracionPersonal.get(2), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvRemuneracionPersonal.get(0));
                litnd.setNumeroDocumento(_csvRemuneracionPersonal.get(3));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return;
            }
        }

        //verificacion campos AZUL obligatorios
        if ((_csvRemuneracionPersonal.get(0).trim().equals("")) || (_csvRemuneracionPersonal.get(1).trim().equals("")) || (_csvRemuneracionPersonal.get(2).trim().equals("")) || (_csvRemuneracionPersonal.get(3).trim().equals("")) || (_csvRemuneracionPersonal.get(4).trim().equals("")) || (_csvRemuneracionPersonal.get(5).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.REMUNERACIONES_PERSONALES);
            litnd.setCodigoEntidadUE(_csvRemuneracionPersonal.get(0));
            litnd.setNumeroDocumento(_csvRemuneracionPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvRemuneracionPersonal.get(3), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvRemuneracionPersonal.get(3));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvRemuneracionPersonal.get(3), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvRemuneracionPersonal.get(0));
            litnd.setNumeroDocumento(_csvRemuneracionPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvRemuneracionPersonal.get(3) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        //verifica si existe en el registro el legajo del trabajador
        if (legajo == null) {
            errores.add(helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_01 + trabajador.getTipoDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_02
                    + trabajador.getNroDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_03 + eue.getCodigoEntidadUE()
                    + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_04 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        CargoAsignado cargoasignado = getCargoAsignadoDesdeListCargoAsignadoCSV(myTratamiento.getCargoAsignado(), legajo, cargo);

        //verificacion en la base de dato despues de la verifiacion en la lista de cargo asignado
        if (cargoasignado == null) {
            cargoasignado = CreadorDesdeDB.getCargoAsignadoWithLegajoCargo(session, cargo, legajo, errores);
            if (cargoasignado != null) {
                myTratamiento.getCargoAsignado().add(cargoasignado);
            }
        }

        //verifica si existe en el registro el cargo asignado con el ID legajo y el ID cargo
        if (cargoasignado == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_01 + cargo.getCod_cargo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_02 + legajo.getCod_legajo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_03 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        ConceptoRemunerativo cr = getConceptoRemunerativoDesdeListConceptoRemunerativoCSV(myTratamiento.getConceptoRemunerativo(), _csvRemuneracionPersonal.get(4), eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de concepto remunerativo
        if (cr == null) {
            cr = CreadorDesdeDB.getConceptoRemunerativoWithCodigoConceptoRemunerativo(_csvRemuneracionPersonal.get(0), _csvRemuneracionPersonal.get(4), myTratamiento.getSession(), errores);
            if (cr != null) {
                myTratamiento.getConceptoRemunerativo().add(cr);
            }
        }

        if (cr == null) {
            errores.add(helpers.Errores.CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_01 + _csvRemuneracionPersonal.get(1) + helpers.Errores.CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_02 + _csvRemuneracionPersonal.get(0) + helpers.Errores.CODIGO_CONCEPTO_REMUNERATIVO_NO_EXISTE_03 + helpers.Constantes.REMUNERACIONES_PERSONALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        //si se procesa la carga masiva de una entidadUE
        if ((origenArchivo.equals(OrigenArchivos.CARGA_MASIVA_ORGANISMO))) {
            //si la entidadUE no admite proceso de batch
            if (!eue.getProc_batch()) {
                errores.add(helpers.Errores.ESTE_ENTIDADUE_NO_PERMITE_PROCESO_BATCH + eue.getCodigoEntidadUE());
                is.setRechazado(is.getRechazado() + 1);
                return;
            }
        }

        String operacion = null;

        remuneracionpersonal.setConceptoRemunerativo(cr);
        remuneracionpersonal.setImporte(CreadorDesdeCsv.toDouble(_csvRemuneracionPersonal.get(5)));

        if (errores.size() > 0) {
            return;
        }

        lEval = cargoasignado.getRemuneracionesPersonales();

        List<RemuneracionPersonal> lEvalIgual = CreadorDesdeCsv.dameIgualRemuneracion(lEval, remuneracionpersonal);

        if (!lEvalIgual.isEmpty()) {
            for (RemuneracionPersonal rp : lEvalIgual) {

                litnd = ComparadorEntidades.remuneracionespersonales(remuneracionpersonal, rp,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    is.setModificado(is.getModificado() + 1);
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.remuneracionPersonal(remuneracionpersonal, rp);
                    }
                    operacion = helpers.Logger.CODIGO_OPERACION_MODIFICACION;
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    is.setCambio(is.getCambio() + 1);
                }
            }
        } else {
            litnd.setCodigoEntidadUE(_csvRemuneracionPersonal.get(0));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setNumeroDocumento(_csvRemuneracionPersonal.get(3));
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                session.saveOrUpdate(cr);
                if (lEval == null) {
                    lEval = new LinkedList<RemuneracionPersonal>();
                    lEval.add(remuneracionpersonal);
                    cargoasignado.setRemuneracionesPersonales(lEval);
                } else {

                    lEval.add(remuneracionpersonal);

                }
            }
            operacion = helpers.Logger.CODIGO_OPERACION_ALTA;
        }

        is.getLt().add(litnd);

        return;
    }

    //evaluaciones
    public void listEvaluacionPersonalVerificacionesErrores(List<List<String>> csvEvaluacionesPersonales, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {

        for (List<String> csvEvaluacionPersonal : csvEvaluacionesPersonales) {
            evaluacionpersonalVerificacionesErrores(csvEvaluacionPersonal, session, origenArchivo, errores, is);
        }

        return;
    }

    public void evaluacionpersonalVerificacionesErrores(List<String> _csvEvaluacionPersonal, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvEvaluacionPersonal.get(0));
        EvaluacionPersonal evaluacionpersonal = new EvaluacionPersonal();
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();
        List<EvaluacionPersonal> lEval;

        //verificacion numero campos archivo
        if (_csvEvaluacionPersonal.size() != helpers.Constantes.CAMPOS_EVALUACIONES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.EVALUACIONES);
            return;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvEvaluacionPersonal.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvEvaluacionPersonal.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.EVALUACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Cargo cargo = getCargoDesdeListCargoCSV(myTratamiento.getCargo(), _csvEvaluacionPersonal.get(1), eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de cargo
        if (cargo == null) {
            cargo = CreadorDesdeDB.getCargoWithCodigoCargoCodigoEntidadUE(_csvEvaluacionPersonal.get(1), _csvEvaluacionPersonal.get(0), session, errores);
            if (cargo != null) {
                myTratamiento.getEvaluacionPersonal().add(evaluacionpersonal);
            }
        }

        //verifica si existe en el registro el cargo
        if (cargo == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_NO_EXISTE_01 + _csvEvaluacionPersonal.get(1) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_02 + _csvEvaluacionPersonal.get(0) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_03 + helpers.Constantes.EVALUACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvEvaluacionPersonal.get(2), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvEvaluacionPersonal.get(0));
                litnd.setNumeroDocumento(_csvEvaluacionPersonal.get(3));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvEvaluacionPersonal.get(3), errores);

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvEvaluacionPersonal.get(3));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajador
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvEvaluacionPersonal.get(3), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvEvaluacionPersonal.get(0));
            litnd.setNumeroDocumento(_csvEvaluacionPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvEvaluacionPersonal.get(3) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.EVALUACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        //verifica si el legajo existe
        if (legajo == null) {
            errores.add(helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_01 + trabajador.getTipoDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_02
                    + trabajador.getNroDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_03 + eue.getCodigoEntidadUE()
                    + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_04 + helpers.Constantes.EVALUACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        CargoAsignado cargoasignado = getCargoAsignadoDesdeListCargoAsignadoCSV(myTratamiento.getCargoAsignado(), legajo, cargo);

        //verificacion en la base de dato despues de la verifiacion en la lista
        if (cargoasignado == null) {
            cargoasignado = CreadorDesdeDB.getCargoAsignadoWithLegajoCargo(session, cargo, legajo, errores);
            if (cargoasignado != null) {
                myTratamiento.getCargoAsignado().add(cargoasignado);
            }
        }

        //verifica si existe en el registro el cargo asignado con el ID legajo y el ID cargo
        if (cargoasignado == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_01 + cargo.getCod_cargo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_02 + legajo.getCod_legajo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_03 + helpers.Constantes.EVALUACIONES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvEvaluacionPersonal.get(0).trim().equals("")) || (_csvEvaluacionPersonal.get(1).trim().equals("")) || (_csvEvaluacionPersonal.get(2).trim().equals("")) || (_csvEvaluacionPersonal.get(3).trim().equals("")) || (_csvEvaluacionPersonal.get(4).trim().equals("")) || (_csvEvaluacionPersonal.get(5).trim().equals("")) || (_csvEvaluacionPersonal.get(6).trim().equals("")) || (_csvEvaluacionPersonal.get(7).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.EVALUACIONES);
            litnd.setCodigoEntidadUE(_csvEvaluacionPersonal.get(0));
            litnd.setNumeroDocumento(_csvEvaluacionPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String operacion = null;
        evaluacionpersonal.setTipo(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoEvaluaciones", _csvEvaluacionPersonal.get(4), errores, myTratamiento.getSession()));
        evaluacionpersonal.setFec_desde(CreadorDesdeCsv.stringToDate(_csvEvaluacionPersonal.get(5), errores, helpers.Constantes.EVALUACIONES));
        evaluacionpersonal.setFec_hasta(CreadorDesdeCsv.stringToDate(_csvEvaluacionPersonal.get(6), errores, helpers.Constantes.EVALUACIONES));
        evaluacionpersonal.setCalificacion(CreadorDesdeCsv.toInteger(_csvEvaluacionPersonal.get(7)));


        if (evaluacionpersonal.getFec_hasta().before(evaluacionpersonal.getFec_desde())) {
            errores.add(Errores.ERROR_FECHA_HASTA_PREVIA_DESDE + helpers.Constantes.EVALUACIONES);
        }

        if (errores.size() > 0) {
            return;
        }

        lEval = cargoasignado.getEvaluacionesPersonales();
        List<EvaluacionPersonal> lEvalIgual = CreadorDesdeCsv.dameIgualEvaluacion(lEval, evaluacionpersonal);

        if (!lEvalIgual.isEmpty()) {
            for (EvaluacionPersonal ep : lEvalIgual) {
                litnd = ComparadorEntidades.evaluacionespersonales(evaluacionpersonal, ep,
                        trabajador.getNroDocumento(), tipoDocumento,
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    is.setModificado(is.getModificado() + 1);

                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.evaluacionPersonal(evaluacionpersonal, ep);
                    }

                    operacion = helpers.Logger.CODIGO_OPERACION_MODIFICACION;
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    is.setCambio(is.getCambio() + 1);
                }
            }

        } else {
            litnd.setCodigoEntidadUE(_csvEvaluacionPersonal.get(0));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setNumeroDocumento(_csvEvaluacionPersonal.get(3));
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                if (lEval == null) {
                    lEval = new LinkedList<EvaluacionPersonal>();
                    lEval.add(evaluacionpersonal);
                    cargoasignado.setEvaluacionesPersonales(lEval);
                } else {
                    lEval.add(evaluacionpersonal);
                }
            }
            operacion = helpers.Logger.CODIGO_OPERACION_ALTA;
        }

        is.getLt().add(litnd);

        return;
    }

    //ausencias y licencias
    public void csvToListAusLicPersonal(List<List<String>> csvAusLicPersonales, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {

        for (List<String> csvAusLicPersonal : csvAusLicPersonales) {
            auslicpersonalFromCsv(csvAusLicPersonal, session, origenArchivo, errores, is);
        }
        return;
    }

    public void auslicpersonalFromCsv(List<String> _csvAusLicPersonal, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvAusLicPersonal.get(0));
        AusLicPersonal ausencialicencia = new AusLicPersonal();
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();
        List<AusLicPersonal> lEval = new LinkedList<AusLicPersonal>();

        //verificacion numero campos archivo
        if (_csvAusLicPersonal.size() != helpers.Constantes.CAMPOS_AUSENCIAS_LICENCIAS) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.AUSENCIAS_LICENCIAS);
            return;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvAusLicPersonal.get(0), session, errores);
        }*/

        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvAusLicPersonal.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.AUSENCIAS_LICENCIAS);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String tipoDocumento = CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoDocumento", _csvAusLicPersonal.get(2), errores, session);
        if (tipoDocumento != null) {
            if (tipoDocumento.trim().equals("")) {
                litnd.setCodigoEntidadUE(_csvAusLicPersonal.get(0));
                litnd.setNumeroDocumento(_csvAusLicPersonal.get(3));
                litnd.setTipoDocumento(tipoDocumento);
                litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
                is.getLt().add(litnd);
                errores.add(helpers.Errores.TIPO_DOCUMENTO_NO_EXISTE + tipoDocumento + " !");
                is.setRechazado(is.getRechazado() + 1);
                return;
            }
        }

        //verificacion si tipoDocumento = DNI then numeroDocumento = 8 y es un numero
        CreadorDesdeCsv.rechazadoDNI(tipoDocumento, _csvAusLicPersonal.get(3), errores);

        Cargo cargo = getCargoDesdeListCargoCSV(myTratamiento.getCargo(), _csvAusLicPersonal.get(1), eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de cargo
        if (cargo == null) {
            cargo = CreadorDesdeDB.getCargoWithCodigoCargoCodigoEntidadUE(_csvAusLicPersonal.get(1), _csvAusLicPersonal.get(0), session, errores);
            if (cargo != null) {
                myTratamiento.getCargo().add(cargo);
            }
        }

        //verifica si existe en el registro el cargo
        if (cargo == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_NO_EXISTE_01 + _csvAusLicPersonal.get(1) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_02 + _csvAusLicPersonal.get(0) + helpers.Errores.CODIGO_CARGO_NO_EXISTE_03 + helpers.Constantes.AUSENCIAS_LICENCIAS);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), tipoDocumento, _csvAusLicPersonal.get(3));

        //verificacion en la base de dato despues de la verifiacion en la lista de trabajadores
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(tipoDocumento, _csvAusLicPersonal.get(3), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }
        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(_csvAusLicPersonal.get(0));
            litnd.setNumeroDocumento(_csvAusLicPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + tipoDocumento + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + _csvAusLicPersonal.get(3) + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.AUSENCIAS_LICENCIAS);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Legajo legajo = getLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), trabajador, eue);

        //verificacion en la base de dato despues de la verifiacion en la lista de legajos
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithTrabajadorEntidadUE(session, trabajador, errores, eue);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        //verifica si existe en el registro el legajo del trabajador
        if (legajo == null) {
            errores.add(helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_01 + trabajador.getTipoDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_02
                    + trabajador.getNroDocumento() + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_03 + eue.getCodigoEntidadUE()
                    + helpers.Errores.CODIGO_LEGAJO_NO_EXISTE_04 + helpers.Constantes.AUSENCIAS_LICENCIAS);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        CargoAsignado cargoasignado = getCargoAsignadoDesdeListCargoAsignadoCSV(myTratamiento.getCargoAsignado(), legajo, cargo);

        //verificacion en la base de dato despues de la verifiacion en la lista de cargos asignados
        if (cargoasignado == null) {
            cargoasignado = CreadorDesdeDB.getCargoAsignadoWithLegajoCargo(session, cargo, legajo, errores);
            if (cargoasignado != null) {
                myTratamiento.getCargoAsignado().add(cargoasignado);
            }
        }

        //verifica si existe en el registro el cargo asignado con el ID legajo y el ID cargo
        if (cargoasignado == null) {
            errores.add(helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_01 + cargo.getCod_cargo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_02 + legajo.getCod_legajo() + helpers.Errores.CODIGO_CARGO_ASIGNADO_NO_EXISTE_03 + helpers.Constantes.AUSENCIAS_LICENCIAS);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvAusLicPersonal.get(0).trim().equals("")) || (_csvAusLicPersonal.get(1).trim().equals("")) || (_csvAusLicPersonal.get(2).trim().equals("")) || (_csvAusLicPersonal.get(3).trim().equals("")) || (_csvAusLicPersonal.get(4).trim().equals("")) || (_csvAusLicPersonal.get(5).trim().equals("")) || (_csvAusLicPersonal.get(6).trim().equals(""))) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.AUSENCIAS_LICENCIAS);
            litnd.setCodigoEntidadUE(_csvAusLicPersonal.get(0));
            litnd.setNumeroDocumento(_csvAusLicPersonal.get(3));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String operacion = null;
        ausencialicencia.setTipo(CreadorDesdeDB.verificacionValorDatoAuxiliar("TipoAusencias", _csvAusLicPersonal.get(4), errores, myTratamiento.getSession()));
        ausencialicencia.setFec_desde(CreadorDesdeCsv.stringToDate(_csvAusLicPersonal.get(5), errores, helpers.Constantes.AUSENCIAS_LICENCIAS));
        ausencialicencia.setFec_hasta(CreadorDesdeCsv.stringToDate(_csvAusLicPersonal.get(6), errores, helpers.Constantes.AUSENCIAS_LICENCIAS));
        ausencialicencia.setMotivo(_csvAusLicPersonal.get(7));


        if (ausencialicencia.getFec_hasta().before(ausencialicencia.getFec_desde())) {
            errores.add(Errores.ERROR_FECHA_HASTA_PREVIA_DESDE + helpers.Constantes.AUSENCIAS_LICENCIAS);
        }
        
        if (errores.size() > 0) {
            return;
        }

        lEval = cargoasignado.getAusLicPersonales();

        List<AusLicPersonal> lEvalIgual = CreadorDesdeCsv.dameIgualAusencia(lEval, ausencialicencia);

        if (!lEvalIgual.isEmpty()) {
            for (AusLicPersonal alp : lEvalIgual) {
                litnd = ComparadorEntidades.auslicpersonal(ausencialicencia, alp,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());

                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    is.setModificado(is.getModificado() + 1);
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.ausLicPersonal(ausencialicencia, alp);
                    }
                    operacion = helpers.Logger.CODIGO_OPERACION_MODIFICACION;
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    is.setCambio(is.getCambio() + 1);
                }
            }
        } else {
            //nueva ausencia licencia
            litnd.setCodigoEntidadUE(_csvAusLicPersonal.get(0));
            litnd.setTipoDocumento(tipoDocumento);
            litnd.setNumeroDocumento(_csvAusLicPersonal.get(3));
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setAlta(is.getAlta() + 1);
            if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                if (lEval == null) {
                    lEval = new LinkedList<AusLicPersonal>();
                    lEval.add(ausencialicencia);
                    cargoasignado.setAusLicPersonales(lEval);
                } else {
                    lEval.add(ausencialicencia);
                }
            }
            operacion = helpers.Logger.CODIGO_OPERACION_ALTA;
        }

        is.getLt().add(litnd);

        return;
    }

    //constancias documentales
    public void listConstanciaDocumentalVerificacionesErrores(List<List<String>> csvConstanciasDocumentales, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {

        for (List<String> csvConstanciaDocumental : csvConstanciasDocumentales) {
            constanciadocumentalVerificacionesErrores(csvConstanciaDocumental, session, origenArchivo, errores, is);
        }
        return;
    }

    public void constanciadocumentalVerificacionesErrores(List<String> _csvConstanciaDocumental, Session session, String origenArchivo, List<String> errores, InformeSalida<LineaInformeTipoNumeroDocumento> is) throws ParseException {
        EntidadUEjecutora eue = getEntidadUEDesdeListEntidadUECSV(myTratamiento.getEntidadesUE(), _csvConstanciaDocumental.get(0));
        ConstanciaDocumental constanciadocumental = new ConstanciaDocumental();
        LineaInformeTipoNumeroDocumento litnd = new LineaInformeTipoNumeroDocumento();

        //verificacion numero campos archivo
        if (_csvConstanciaDocumental.size() != helpers.Constantes.CAMPOS_CONSTANCIAS_DOCUMENTALES) {
            errores.add(helpers.Errores.TAMANO_CAMPOS_ARCHIVO_DIFFERENTE + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            return;
        }

        //verificacion en la base de dato despues de la verifiacion en la lista de entidadUE
        /*if(eue == null){
        eue = CreadorDesdeDB.getEntidadUEjecutoraWithCodigoEntidadUE(_csvConstanciaDocumental.get(0), session, errores);
        }*/
        //verifica si la entidadUE existe
        if (eue == null) {
            errores.add(helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_01 + _csvConstanciaDocumental.get(0) + helpers.Errores.CODIGO_ENTIDAD_NO_EXISTE_02 + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Legajo legajo = getLegajoCodigoLegajoDesdeListLegajoCSV(myTratamiento.getLegajo(), _csvConstanciaDocumental.get(1), eue);

        //verificacion en base de dato despues de la verificacion en la lista de legajo
        if (legajo == null) {
            legajo = CreadorDesdeDB.getLegajoWithCodigoEntidadUECodigoLegajo(eue, _csvConstanciaDocumental.get(1), session, errores);
            if (legajo != null) {
                myTratamiento.getLegajo().add(legajo);
            }
        }

        //verifica si el legajo existe
        if (legajo == null) {
            errores.add(helpers.Errores.LEGAJO_NO_EXISTE_01 + _csvConstanciaDocumental.get(1) + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        Trabajador trabajador = getTrabajadorDesdeListTrabajadorCSV(myTratamiento.getTrabajador(), legajo.getTrabajador().getTipoDocumento(), legajo.getTrabajador().getNroDocumento());

        //verificacion en la base de dato despues de la verificacion en la lista de trabajadores
        if (trabajador == null) {
            trabajador = CreadorDesdeDB.getTrabajadorWithTipoNumeroDocumento(legajo.getTrabajador().getTipoDocumento(), legajo.getTrabajador().getNroDocumento(), session, errores);
            if (trabajador != null) {
                myTratamiento.getTrabajador().add(trabajador);
            }

        }

        //verifica si el trabajador existe
        if (trabajador == null) {
            litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
            litnd.setNumeroDocumento(trabajador.getNroDocumento());
            litnd.setTipoDocumento(trabajador.getTipoDocumento());
            litnd.setResultado(ResultadoOperacionCSV.TRABAJADOR_NO_EXISTE);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            errores.add(helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_01 + trabajador.getTipoDocumento() + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_02 + trabajador.getNroDocumento() + helpers.Errores.CODIGO_TRABAJADOR_NO_EXISTE_03 + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            return;
        }

        //verificacion campos AZUL obligatorios
        if ((_csvConstanciaDocumental.get(0).trim().length() == 0) || (_csvConstanciaDocumental.get(1).trim().length() == 0)
                || (_csvConstanciaDocumental.get(2).trim().length() == 0) || (_csvConstanciaDocumental.get(3).trim().length() == 0)
                || (_csvConstanciaDocumental.get(4).trim().length() == 0) || (_csvConstanciaDocumental.get(6).trim().length() == 0)) {
            errores.add(helpers.Errores.FALTA_CAMPOS_OBLIGATORIOS + helpers.Constantes.CONSTANCIAS_DOCUMENTALES);
            litnd.setCodigoEntidadUE(eue.getCodigoEntidadUE());
            litnd.setNumeroDocumento(trabajador.getNroDocumento());
            litnd.setTipoDocumento(trabajador.getTipoDocumento());
            litnd.setResultado(ResultadoOperacionCSV.FALTA_INFORMACION);
            is.getLt().add(litnd);
            is.setRechazado(is.getRechazado() + 1);
            return;
        }

        String operacion = null;
        constanciadocumental.setLegajo(legajo);
        constanciadocumental.setCat_constancia(CreadorDesdeDB.verificacionValorDatoAuxiliar("CategoríaConstancia", _csvConstanciaDocumental.get(2), errores, myTratamiento.getSession()));
        constanciadocumental.setTip_constancia(CreadorDesdeDB.verificacionValorDatoAuxiliar("DatoConstancia", _csvConstanciaDocumental.get(3), errores, myTratamiento.getSession()));
        constanciadocumental.setFecha(CreadorDesdeCsv.stringToDate(_csvConstanciaDocumental.get(4), errores, helpers.Constantes.CONSTANCIAS_DOCUMENTALES));
        constanciadocumental.setNum_resolucion(_csvConstanciaDocumental.get(5));
        constanciadocumental.setTxt_descriptivo(_csvConstanciaDocumental.get(6));

        if (errores.size() > 0) {
            return;
        }

        // Verifico si el tipo de constancia es consistente con la categoría de constancia
        DatoAuxiliar datoConstancia = helpers.Helpers.getDatoAuxiliar("DatoConstancia", Long.decode(_csvConstanciaDocumental.get(3)), session);
        DatoAuxiliar catConstancia = helpers.Helpers.getDatoAuxiliar("CategoríaConstancia", Long.decode(_csvConstanciaDocumental.get(2)), session);

        if(datoConstancia.getRelacionCodigo() != catConstancia.getCodigo()) {
            errores.add(Errores.ERROR_CASCADA_TIPO_CONSTANCIA + " Tipo de constancia: " + datoConstancia.getCodigo()
                    + " Cat. Constancia:" + catConstancia.getCodigo() );
        }

        
        if (errores.size() > 0) {
            return;
        }

        List<ConstanciaDocumental> lCons = legajo.getConstanciasDocumentales();

        List<ConstanciaDocumental> lConsIgual = CreadorDesdeCsv.dameIgualConstancia(lCons, constanciadocumental);

        litnd = new LineaInformeTipoNumeroDocumento();
        if (!lConsIgual.isEmpty()) {
            for (ConstanciaDocumental cd : lConsIgual) {
                litnd = ComparadorEntidades.constanciadocumental(constanciadocumental, cd,
                        trabajador.getNroDocumento(), trabajador.getTipoDocumento(),
                        eue.getCodigoEntidadUE());
                if (litnd.getResultado().equals(ResultadoOperacionCSV.MODIFICADO)) {
                    is.setModificado(is.getModificado() + 1);
                    if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                        CopiadorEntidades.constanciaDocumental(constanciadocumental, cd);
                    }
                    operacion = helpers.Logger.CODIGO_OPERACION_MODIFICACION;
                }

                if (litnd.getResultado().equals(ResultadoOperacionCSV.SIN_CAMBIO)) {
                    is.setCambio(is.getCambio() + 1);
                }
            }
        } else {
            litnd.setCodigoEntidadUE(_csvConstanciaDocumental.get(0));
            litnd.setTipoDocumento(legajo.getTrabajador().getTipoDocumento());
            litnd.setNumeroDocumento(legajo.getTrabajador().getNroDocumento());
            litnd.setResultado(ResultadoOperacionCSV.NUEVO);
            is.setRechazado(is.getAlta() + 1);

            if (myTratamiento.getTipoProceso().equals(TipoProceso.VALIDACION_Y_PROCESAMIENTO)) {
                if (lCons == null) {
                    lCons = new LinkedList<ConstanciaDocumental>();
                    lCons.add(constanciadocumental);
                    legajo.setConstanciasDocumentales(lCons);
                } else {
                    lCons.add(constanciadocumental);
                }
            }
            operacion = helpers.Logger.CODIGO_OPERACION_ALTA;
        }

        is.getLt().add(litnd);

        return;
    }

    /** Verifica si las entidadUE existan en la base de dato
     * 
     * 
     * @param eue
     * @param session
     * @param errores
     * @return 
     */
    public static boolean presenciaTablaEntidadUEjecutora(EntidadUEjecutora eue, Session session, List<String> errores) {

        if (eue == null) {
            //errores.add("la entidadUE esta null");
            return false;
        }

        Criteria c = session.createCriteria(EntidadUEjecutora.class);

        c.add(Restrictions.eq("codigoEntidadUE", eue.getCodigoEntidadUE()));

        if (c.list().isEmpty() && errores != null) {
            return false;
        }

        return c.list().size() > 0;
    }

    /*public static boolean presenciaTablaTrabajador(Trabajador tr, Session session, List<String> errores) {
    
    if (tr == null) {
    return false;
    }
    
    Criteria c = session.createCriteria(Trabajador.class)
    .add(Restrictions.eq("tipoDocumento", tr.getTipoDocumento()))
    .add(Restrictions.eq("nroDocumento", tr.getNroDocumento()));
    
    
    if (c.list().isEmpty() && errores != null) {
    errores.add("Este trabajador con este tipo " + tr.getTipoDocumento() + " y numero de documento " + tr.getNroDocumento() + " del archivo Trabajador no esta registrado en la base de dato!");
    return false;
    }
    
    return c.list().size() > 0;
    }
    
    public static boolean presenciaTablaLegajo(Legajo le, Session session, List<String> errores) {
    
    if (le == null) {
    return false;
    }
    
    Criteria c = session.createCriteria(Legajo.class).add(Restrictions.eq("cod_legajo", le.getCod_legajo()));
    
    
    if (c.list().isEmpty() && errores != null) {
    errores.add("Este legajo con este codigo de legajo " + le.getCod_legajo() + " del archivo Legajo esta registrado en la base de dato!");
    return false;
    }
    
    return c.list().size() > 0;
    }
    
    public static boolean presenciaTablaCargoAsignado(CargoAsignado ca, Session session, List<String> errores) {
    
    if (ca == null) {
    //errores.add("la entidadUE esta null");
    return false;
    }
    
    Criteria c = session.createCriteria(CargoAsignado.class).add(Restrictions.eq("cod_legajo", ca));
    
    return c.list().size() > 0;
    }*/
    public static int toInteger(String valor) {

        if ((valor == null) || (valor.trim().equals(""))) {
            return 0;
        }

        /*if(valor.startsWith("0"))
        return Integer.parseInt(valor.substring(1, valor.length()));*/

        return Integer.parseInt(valor);
    }

    public static Double toDouble(String valor) {
        Double d = null;

        if ((valor == null) || (valor.trim().equals(""))) {
            return d;
        }


        return Double.parseDouble(valor.replaceAll(",", "."));
    }

    public static boolean toBoolean(String valor) {

        if ((valor == null) || (valor.trim().equals("")) || valor.trim().equals("F")) {
            return false;
        }

        return true;
    }

    public static Float toFloat(String valor) {

        if ((valor == null) || (valor.trim().equals(""))) {
            return null;
        }

        return Float.parseFloat(valor);
    }

    public EntidadUEjecutora getEntidadUEDesdeListEntidadUECSV(List<EntidadUEjecutora> leue, String codigoOrganismo) {
        if (codigoOrganismo == null || leue == null) {
            return null;
        }

        if (codigoOrganismo.trim().equals("")) {
            return null;
        }

        for (EntidadUEjecutora eue : leue) {
            if (eue.getCodigoEntidadUE().equals(codigoOrganismo)) {
                return eue;
            }
        }
        return null;
    }

    public Trabajador getTrabajadorDesdeListTrabajadorCSV(List<Trabajador> lt, String tipoDocumento, String numeroDocumento) {
        if (tipoDocumento == null || numeroDocumento == null || lt == null) {
            return null;
        }

        if (tipoDocumento.trim().equals("") || numeroDocumento.trim().equals("0")) {
            return null;
        }

        for (Trabajador t : lt) {
            if (t.getTipoDocumento().equals(tipoDocumento) && t.getNroDocumento().equals(numeroDocumento)) {
                return t;
            }
        }
        return null;
    }

    public Legajo getLegajoDesdeListLegajoCSV(List<Legajo> ll, Trabajador trabajador, EntidadUEjecutora eue) {
        if (trabajador == null || ll == null || eue == null) {
            return null;
        }

        if (eue.getCodigoEntidadUE().trim().equals("")) {
            return null;
        }

        for (Legajo l : ll) {
            if (l.getTrabajador().getTipoDocumento().equals(trabajador.getTipoDocumento()) && l.getTrabajador().getNroDocumento().equals(trabajador.getNroDocumento())
                    && l.getEntidadUE().getCodigoEntidadUE().equals(eue.getCodigoEntidadUE())) {
                return l;
            }
        }
        return null;
    }

    public Legajo getLegajoCodigoLegajoDesdeListLegajoCSV(List<Legajo> ll, String codigoLegajo, EntidadUEjecutora eue) {
        if (codigoLegajo == null || ll == null || eue == null) {
            return null;
        }

        if (eue.getCodigoEntidadUE().equals("") || codigoLegajo.trim().equals("")) {
            return null;
        }

        for (Legajo l : ll) {
            if (l.getCod_legajo().equals(codigoLegajo) && l.getEntidadUE().getCodigoEntidadUE().equals(eue.getCodigoEntidadUE())) {
                return l;
            }
        }
        return null;
    }

    public CargoAsignado getCargoAsignadoDesdeListCargoAsignadoCSV(List<CargoAsignado> lca, Legajo legajo, Cargo cargo) {
        if (legajo == null || lca == null || cargo == null) {
            return null;
        }

        if (legajo.getCod_legajo() == null || cargo.getCod_cargo() == null) {
            return null;
        }

        for (CargoAsignado ca : lca) {
            if (ca.getLegajo().getCod_legajo().equals(legajo.getCod_legajo()) && ca.getCargo().getCod_cargo().equals(cargo.getCod_cargo())) {
                return ca;
            }
        }
        return null;
    }

    public UnidadOrganica getUnidadOrganicaDesdeListUnidadOrganicaCSV(List<UnidadOrganica> lua, String codigoUnidadOrganica, EntidadUEjecutora eue) {
        if (codigoUnidadOrganica == null || lua == null || eue == null) {
            return null;
        }

        if (codigoUnidadOrganica.trim().equals("")) {
            return null;
        }

        for (UnidadOrganica ua : lua) {
            if (ua.getCod_und_organica().equals(codigoUnidadOrganica) && ua.getEntidadUE().getCodigoEntidadUE().equals(eue.getCodigoEntidadUE())) {
                return ua;
            }
        }
        return null;
    }

    public Cargo getCargoDesdeListCargoCSV(List<Cargo> lc, String codigoCargo, EntidadUEjecutora eue) {
        if (codigoCargo == null || lc == null || eue == null) {
            return null;
        }

        if (codigoCargo.trim().equals("")) {
            return null;
        }

        for (Cargo c : lc) {
            if (c.getCod_cargo().equals(codigoCargo) && c.getUnd_organica().getEntidadUE().getCodigoEntidadUE().equals(eue.getCodigoEntidadUE())) {
                return c;
            }
        }
        return null;
    }

    public ConceptoRemunerativo getConceptoRemunerativoDesdeListConceptoRemunerativoCSV(List<ConceptoRemunerativo> lcr, String codigoConceptoRemunerativo, EntidadUEjecutora eue) {
        if (codigoConceptoRemunerativo == null || lcr == null || eue == null) {
            return null;
        }

        if (codigoConceptoRemunerativo.trim().equals("")) {
            return null;
        }

        for (ConceptoRemunerativo cr : lcr) {
            if (cr.getCodigo().equals(codigoConceptoRemunerativo) && cr.getEntidadUE().getCodigoEntidadUE().equals(eue.getCodigoEntidadUE())) {
                return cr;
            }
        }
        return null;
    }

    public static void rechazadoDNI(String tipoDocumento, String numeroDocumento, List<String> errores) {
        int nroDocumento = 0;
     //   if (tipoDocumento.trim().equals("DNI")) {
            try {
                nroDocumento = Integer.parseInt(numeroDocumento);
            } catch (Exception e) { // no es un int
                errores.add(helpers.Errores.ESTE_NUMERO_DE_DOCUMENTO_NO_ES_UN_INT + nroDocumento);
                return;
            }

            if (numeroDocumento.length() != 8) {// el string no tiene 8 digits
                errores.add(helpers.Errores.ESTE_NUMERO_DE_DOCUMENTO_TIENE_MAS_QUE_8_DIGITS + numeroDocumento);
                return;
            }
       // }
    }

    private String getEstadoCargoFromBoolStr(String estado, List<String> errores) {
        if (!((estado == null) || (estado.trim().equals("")))) {
            if (estado.trim().equals("1")) {
                return Cargo.ESTADO_ALTA;
            }

            if (estado.trim().equals("0")) {
                return Cargo.ESTADO_BAJA;
            }
            // si llego hasta aca el codigo no es valido
            errores.add(helpers.Errores.CODIGO_NO_ENCONTRADO + "Cargo");

        }
        return Cargo.ESTADO_ALTA;
    }

    private String getEstadoCAFromBoolStr(String estado, List<String> errores) {
        if (!((estado == null) || (estado.trim().equals("")))) {
            if (estado.trim().equals("1")) {
                return helpers.Constantes.ESTADO_ACTIVO;
            }

            if (estado.trim().equals("0")) {
                return helpers.Constantes.ESTADO_BAJA;
            }
            // si llego hasta aca el codigo no es valido
            errores.add(helpers.Errores.CODIGO_NO_ENCONTRADO + "Cargo Asignado");

        }
        return helpers.Constantes.ESTADO_ACTIVO;
    }

    /**
     * 
     * @param lep: una lista de evaluaciones personales
     * @param ep: un objeto evaluacion personal
     * @return evalIgual: una lista de evaluaciones personales iguales a ep
     */
    public static List<EvaluacionPersonal> dameIgualEvaluacion(List<EvaluacionPersonal> lep, EvaluacionPersonal ep) {
        List<EvaluacionPersonal> evalIgual = new LinkedList<EvaluacionPersonal>();

        if ((lep == null) || (ep == null)) {
            return evalIgual;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (EvaluacionPersonal eValp : lep) {
            if (eValp.getFec_desde() != null && ep.getFec_desde() != null) {
                if (format.format(eValp.getFec_desde()).equals(format.format(ep.getFec_desde()))) {
                    evalIgual.add(eValp);
                }
            }
        }
        return evalIgual;
    }

    /**
     * 
     * @param lep: una lista de remuneraciones personales
     * @param ep: un objecto remuneracion personal
     * @return evalIgual: una lista de remuneraciones personales iguales a rp
     */
    public static List<RemuneracionPersonal> dameIgualRemuneracion(List<RemuneracionPersonal> lrp, RemuneracionPersonal remuneracionPersonal) {
        List<RemuneracionPersonal> remuIgual = new LinkedList<RemuneracionPersonal>();

        if ((lrp == null) || (remuneracionPersonal == null)) {
            return remuIgual;
        }

        for (RemuneracionPersonal rp : lrp) {
            if (rp.getConceptoRemunerativo() != null) {
                if (rp.getConceptoRemunerativo().getCodigo().equals(remuneracionPersonal.getConceptoRemunerativo().getCodigo())) {
                        //&& rp.getImporte().equals(remuneracionPersonal.getImporte())) {
                    remuIgual.add(rp);
                }
            }
        }

        return remuIgual;
    }

    /** 
     * 
     * @param lep: una lista de ausencias licencias
     * @param ep : un objecto ausencia y licencia
     * @return evalIgual: una lista de ausencias y licencias eguales a ep
     */
    public static List<AusLicPersonal> dameIgualAusencia(List<AusLicPersonal> lep, AusLicPersonal ep) {
        List<AusLicPersonal> ausIgual = new LinkedList<AusLicPersonal>();

        if ((lep == null) || (ep == null)) {
            return ausIgual;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (AusLicPersonal eValp : lep) {
            if (eValp.getFec_desde() != null && ep.getFec_desde() != null) {
                if (format.format(eValp.getFec_desde()).equals(format.format(ep.getFec_desde()))) {
                    ausIgual.add(eValp);
                }
            }
        }
        return ausIgual;
    }

    public static List<ConstanciaDocumental> dameIgualConstancia(List<ConstanciaDocumental> lcd, ConstanciaDocumental cd) {
        List<ConstanciaDocumental> consIgual = new LinkedList<ConstanciaDocumental>();

        if ((lcd == null) || (cd == null)) {
            return consIgual;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (ConstanciaDocumental cDocumental : lcd) {
            if (cDocumental.getFecha() != null && cd.getFecha() != null && cDocumental.getCat_constancia() != null && cd.getCat_constancia() != null) {
                if (format.format(cDocumental.getFecha()).equals(format.format(cd.getFecha())) && (cDocumental.getCat_constancia().equals(cd.getCat_constancia()))) {
                    consIgual.add(cDocumental);
                }
            }
        }
        return consIgual;
    }

    public static List<String> verificacionProcesarCargaMassiva(List<EntidadUEjecutora> leue) {
        List<String> errores = new LinkedList<String>();
        String codigoEntidadUE = "";

        if (leue.isEmpty()) {
            errores.add(helpers.Errores.NO_HAY_ENTIDADUE_EN_EL_ARCHIVO_ORGAN1);
            return errores;
        }

        codigoEntidadUE = leue.get(0).getCodigoEntidadUE();

        //verificar si hay una sola entidad en el archivo ORGAN1 y que la entidadUE eue existe en el archivo
        for (EntidadUEjecutora entidadUE : leue) {
            if (!codigoEntidadUE.equals(entidadUE.getCodigoEntidadUE())) {
                errores.add(helpers.Errores.HAY_MAS_UNA_ENTIDAD_ARCHIVO);
                return errores;
            }
        }

        return errores;
    }

    public static List<String> verificacionEntidadPuedeProcesarEstaArchivo(List<EntidadUEjecutora> leue, EntidadUEjecutora eue) {
        List<String> errores = new LinkedList<String>();
        String codigoEntidadUE = "";

        if (leue.isEmpty() || eue == null || eue.getCodigoEntidadUE().trim().equals("")) {
            errores.add(helpers.Errores.NO_HAY_ENTIDADUE_EN_EL_ARCHIVO_ORGAN1);
            return errores;
        }

        codigoEntidadUE = eue.getCodigoEntidadUE();

        //verificar si hay una sola entidad en el archivo ORGAN1 y que la entidadUE eue existe en el archivo
        for (EntidadUEjecutora entidadUE : leue) {
            if (!codigoEntidadUE.equals(entidadUE.getCodigoEntidadUE())) {
                errores.add(helpers.Errores.NO_SE_PUEDE_PROCESAR_ESTE_ARCHIVO_CON_ESTA_ENTIDAD + eue.getDenominacion());
                return errores;
            }
        }

        return errores;
    }

    public enum Month {

        ENERO(0),
        FEBRERO(1),
        MARZO(2),
        ABRIL(3),
        MAYO(4),
        JUNIO(5),
        JULIO(6),
        AGOSTO(7),
        SEPTIEMBRE(8),
        OCTUBRE(9),
        NOVIEMBRE(10),
        DICIEMBRE(11);

        private Month(int order) {
            this.order = order;
        }
        private int order;

        public int getOrder() {
            return order;
        }
    }

    public static Date stringToDate(String sDate, List<String> errores, String nombreArchivo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        Calendar c = Calendar.getInstance();
        String[] dateTmp;

        if (sDate.trim().length() == 0) {
            return date;
        }

        try {
            sdf.setLenient(false);
            date = sdf.parse(sDate);
            if (!sdf.format(date).equals(sDate)) {
                errores.add(Errores.ERROR_PARSEANDO_TIPO_DATE01 + sDate + Errores.ERROR_PARSEANDO_TIPO_DATE02 + nombreArchivo);
            }
        } catch (ParseException ex) {
            //Logger.getLogger(CreadorDesdeCsv.class.getName()).log(Level.SEVERE, null, ex);

            errores.add(Errores.ERROR_PARSEANDO_TIPO_DATE01 + sDate + Errores.ERROR_PARSEANDO_TIPO_DATE02 + nombreArchivo);
            return null;
        }

        dateTmp = sDate.split("/");

        c.setLenient(false);
        c.setTimeZone(TimeZone.getDefault());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.AM_PM, Calendar.AM);

        try {
            c.setTime(date);

            c.set(Calendar.DATE, Integer.parseInt(dateTmp[0]));
            c.set(Calendar.MONTH, getCalendarMonth(Integer.parseInt(dateTmp[1])));
            c.set(Calendar.YEAR, Integer.parseInt(dateTmp[2]));
            date = c.getTime();
            
        } catch (Exception e) {
            errores.add(Errores.ERROR_FECHA_INVALIDA + sDate + Errores.ERROR_PARSEANDO_TIPO_DATE02 + nombreArchivo );
            return null;
        }
        return date;
    }

    public static int getCalendarMonth(Integer monthInt) {
   
        int month = monthInt -1;
        switch (month) {
            case 0:
            return Calendar.JANUARY;
            case 1:
            return Calendar.FEBRUARY;
            case 2:
            return Calendar.MARCH;
            case 3:
            return Calendar.APRIL;
            case 4:
            return Calendar.MAY;
            case 5:
            return Calendar.JUNE;
            case 6:
            return Calendar.JULY;
            case 7:
            return Calendar.AUGUST;
            case 8:
            return Calendar.SEPTEMBER;
            case 9:
            return Calendar.OCTOBER;
            case 10:
            return Calendar.NOVEMBER;
            case 11:
            return Calendar.DECEMBER;
            default:
                return -1;
        }
    }
}
