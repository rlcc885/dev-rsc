/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//prueb
package Batch;
// Esto es un comentario
import Batch.Helpers.CreadorDesdeCsv;
import Batch.Helpers.InformeSalida;
import Batch.Helpers.LineaInformeCodigo;
import Batch.Helpers.LineaInformeTipoNumeroDocumento;
import Batch.Helpers.LineasArchivos;
import Batch.Helpers.ListLineaEntidadUE;
import Batch.Helpers.OrigenArchivos;
import au.com.bytecode.opencsv.CSVReader;
import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.EvaluacionPersonal;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.RemuneracionPersonal;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Morgan
 */
public final class Tratamiento {

    private List<List<String>> ORGAN1 = new LinkedList<List<String>>();
    private List<List<String>> BASICO1 = new LinkedList<List<String>>();
    private List<List<String>> BASICO2 = new LinkedList<List<String>>();
    private List<List<String>> BASICO3 = new LinkedList<List<String>>();
    private List<List<String>> BASICO4 = new LinkedList<List<String>>();
    private List<List<String>> BASICO5 = new LinkedList<List<String>>();
    private List<List<String>> BASICO6 = new LinkedList<List<String>>();
    private List<List<String>> PPAL1 = new LinkedList<List<String>>();
    private List<List<String>> PPAL2 = new LinkedList<List<String>>();
    private List<List<String>> PPAL3 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA1 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA2 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA3 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA4 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA5 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA6 = new LinkedList<List<String>>();
    private List<List<String>> COMPTRA7 = new LinkedList<List<String>>();
    private List<List<String>> COMPCAR1 = new LinkedList<List<String>>();
    private List<List<String>> COMPCAR2 = new LinkedList<List<String>>();
    private List<List<String>> COMPCAR3 = new LinkedList<List<String>>();
    private List<List<String>> COMPLEG1 = new LinkedList<List<String>>();
    
    private List<Entidad_BK> _entidadesUEjecutora = new LinkedList<Entidad_BK>();
    private List<UnidadOrganica> _unidadOrganica = new LinkedList<UnidadOrganica>();
    private List<Cargoxunidad> _cargo = new LinkedList<Cargoxunidad>();
    private List<ConceptoRemunerativo> _conceptoRemunerativo = new LinkedList<ConceptoRemunerativo>();
    private List<Trabajador> _trabajador  = new LinkedList<Trabajador>();
    private List<Legajo> _legajo  = new LinkedList<Legajo>();
    private List<CargoAsignado> _cargoAsignado = new LinkedList<CargoAsignado>();
    private List<Familiar> _familiar = new LinkedList<Familiar>();
    private List<Titulo> _titulo = new LinkedList<Titulo>();
    private List<Certificacion> _certificacion = new LinkedList<Certificacion>();
    private List<Curso> _curso = new LinkedList<Curso>();
    private List<Ant_Laborales> _antLaborale  = new LinkedList<Ant_Laborales>();
    private List<MeritoDemerito> _meritoDemerito = new LinkedList<MeritoDemerito>();
    private List<Publicacion> _producionIntelectual  = new LinkedList<Publicacion>();
    private List<RemuneracionPersonal> _remuneracionPersonal = new LinkedList<RemuneracionPersonal>();
    private List<EvaluacionPersonal> _evaluacionPersonal  = new LinkedList<EvaluacionPersonal>();
    private List<AusLicPersonal> _ausLicPersonal = new LinkedList<AusLicPersonal>();
    private List<ConstanciaDocumental> _constanciaDocumentale  = new LinkedList<ConstanciaDocumental>();

    //informe de cada archivo xls
    private InformeSalida<LineaInformeCodigo> isConcepto = new InformeSalida<LineaInformeCodigo>();
    private InformeSalida<LineaInformeCodigo> isUnidadO = new InformeSalida<LineaInformeCodigo>();
    private InformeSalida<LineaInformeCodigo> isCargo = new InformeSalida<LineaInformeCodigo>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isTrabajador = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isLegajo = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isCargoA = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isFamiliar = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isTitulo = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isCertificacion = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isCurso = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isAntecedent = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isProduccion = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isMeritoDemerito = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isRemuneracion = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isEvaluacion = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isAusencia = new InformeSalida<LineaInformeTipoNumeroDocumento>();
    private InformeSalida<LineaInformeTipoNumeroDocumento> isConstancia = new InformeSalida<LineaInformeTipoNumeroDocumento>();

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsAntecedent() {
        return isAntecedent;
    }

    public void setIsAntecedent(InformeSalida<LineaInformeTipoNumeroDocumento> isAntecedent) {
        this.isAntecedent = isAntecedent;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsAusencia() {
        return isAusencia;
    }

    public void setIsAusencia(InformeSalida<LineaInformeTipoNumeroDocumento> isAusencia) {
        this.isAusencia = isAusencia;
    }

    public InformeSalida<LineaInformeCodigo> getIsCargo() {
        return isCargo;
    }

    public void setIsCargo(InformeSalida<LineaInformeCodigo> isCargo) {
        this.isCargo = isCargo;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsCargoA() {
        return isCargoA;
    }

    public void setIsCargoA(InformeSalida<LineaInformeTipoNumeroDocumento> isCargoA) {
        this.isCargoA = isCargoA;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsCertificacion() {
        return isCertificacion;
    }

    public void setIsCertificacion(InformeSalida<LineaInformeTipoNumeroDocumento> isCertificacion) {
        this.isCertificacion = isCertificacion;
    }

    public InformeSalida<LineaInformeCodigo> getIsConcepto() {
        return isConcepto;
    }

    public void setIsConcepto(InformeSalida<LineaInformeCodigo> isConcepto) {
        this.isConcepto = isConcepto;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsConstancia() {
        return isConstancia;
    }

    public void setIsConstancia(InformeSalida<LineaInformeTipoNumeroDocumento> isConstancia) {
        this.isConstancia = isConstancia;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsCurso() {
        return isCurso;
    }

    public void setIsCurso(InformeSalida<LineaInformeTipoNumeroDocumento> isCurso) {
        this.isCurso = isCurso;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsEvaluacion() {
        return isEvaluacion;
    }

    public void setIsEvaluacion(InformeSalida<LineaInformeTipoNumeroDocumento> isEvaluacion) {
        this.isEvaluacion = isEvaluacion;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsFamiliar() {
        return isFamiliar;
    }

    public void setIsFamiliar(InformeSalida<LineaInformeTipoNumeroDocumento> isFamiliar) {
        this.isFamiliar = isFamiliar;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsLegajo() {
        return isLegajo;
    }

    public void setIsLegajo(InformeSalida<LineaInformeTipoNumeroDocumento> isLegajo) {
        this.isLegajo = isLegajo;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsMeritoDemerito() {
        return isMeritoDemerito;
    }

    public void setIsMeritoDemerito(InformeSalida<LineaInformeTipoNumeroDocumento> isMeritoDemerito) {
        this.isMeritoDemerito = isMeritoDemerito;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsProduccion() {
        return isProduccion;
    }

    public void setIsProduccion(InformeSalida<LineaInformeTipoNumeroDocumento> isProduccion) {
        this.isProduccion = isProduccion;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsRemuneracion() {
        return isRemuneracion;
    }

    public void setIsRemuneracion(InformeSalida<LineaInformeTipoNumeroDocumento> isRemuneracion) {
        this.isRemuneracion = isRemuneracion;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsTitulo() {
        return isTitulo;
    }

    public void setIsTitulo(InformeSalida<LineaInformeTipoNumeroDocumento> isTitulo) {
        this.isTitulo = isTitulo;
    }

    public InformeSalida<LineaInformeTipoNumeroDocumento> getIsTrabajador() {
        return isTrabajador;
    }

    public void setIsTrabajador(InformeSalida<LineaInformeTipoNumeroDocumento> isTrabajador) {
        this.isTrabajador = isTrabajador;
    }

    public InformeSalida<LineaInformeCodigo> getIsUnidadO() {
        return isUnidadO;
    }

    public void setIsUnidadO(InformeSalida<LineaInformeCodigo> isUnidadO) {
        this.isUnidadO = isUnidadO;
    }
    
    public List<Ant_Laborales> getAntLaborale() {
        return _antLaborale;
    }

    public List<AusLicPersonal> getAusLicPersonal() {
        return _ausLicPersonal;
    }

    public List<Cargoxunidad> getCargo() {
        return _cargo;
    }

    public List<CargoAsignado> getCargoAsignado() {
        return _cargoAsignado;
    }

    public List<ConceptoRemunerativo> getConceptoRemunerativo() {
        return _conceptoRemunerativo;
    }

    public List<EvaluacionPersonal> getEvaluacionPersonal() {
        return _evaluacionPersonal;
    }

    public List<Familiar> getFamiliar() {
        return _familiar;
    }

    public List<MeritoDemerito> getMeritoDemerito() {
        return _meritoDemerito;
    }

    public List<Entidad_BK> getEntidadesUE() {
        return _entidadesUEjecutora;
    }

    public List<Publicacion> getProducionIntelectual() {
        return _producionIntelectual;
    }

    public List<RemuneracionPersonal> getRemuneracionPersonal() {
        return _remuneracionPersonal;
    }

    public List<Titulo> getTitulo() {
        return _titulo;
    }

    public List<Trabajador> getTrabajador() {
        return _trabajador;
    }

    public List<Certificacion> getCertificacion() {
        return _certificacion;
    }

    public List<ConstanciaDocumental> getConstanciaDocumentale() {
        return _constanciaDocumentale;
    }

    public List<UnidadOrganica> getUnidadOrganica() {
        return _unidadOrganica;
    }

    public List<Curso> getCurso() {
        return _curso;
    }

    public List<Legajo> getLegajo() {
        return _legajo;
    }

    public void setAntLaborale(List<Ant_Laborales> _antLaborale) {
        this._antLaborale = _antLaborale;
    }

    public void setAusLicPersonal(List<AusLicPersonal> _ausLicPersonal) {
        this._ausLicPersonal = _ausLicPersonal;
    }

    public void setCargo(List<Cargoxunidad> _cargo) {
        this._cargo = _cargo;
    }

    public void setCargoAsignado(List<CargoAsignado> _cargoAsignado) {
        this._cargoAsignado = _cargoAsignado;
    }

    public void setCertificacion(List<Certificacion> _certificacion) {
        this._certificacion = _certificacion;
    }

    public void setConceptoRemunerativo(List<ConceptoRemunerativo> _conceptoRemunerativo) {
        this._conceptoRemunerativo = _conceptoRemunerativo;
    }

    public void setConstanciaDocumentale(List<ConstanciaDocumental> _constanciaDocumentale) {
        this._constanciaDocumentale = _constanciaDocumentale;
    }

    public void setCurso(List<Curso> _curso) {
        this._curso = _curso;
    }

    public void setEvaluacionPersonal(List<EvaluacionPersonal> _evaluacionPersonal) {
        this._evaluacionPersonal = _evaluacionPersonal;
    }

    public void setFamiliar(List<Familiar> _familiar) {
        this._familiar = _familiar;
    }

    public void setLegajo(List<Legajo> _legajo) {
        this._legajo = _legajo;
    }

    public void setMeritoDemerito(List<MeritoDemerito> _meritoDemerito) {
        this._meritoDemerito = _meritoDemerito;
    }

    public void setEntidadesUE(List<Entidad_BK> _entidadesUE) {
        this._entidadesUEjecutora = _entidadesUE;
    }

    public void setProducionIntelectual(List<Publicacion> _producionIntelectual) {
        this._producionIntelectual = _producionIntelectual;
    }

    public void setRemuneracionPersonal(List<RemuneracionPersonal> _remuneracionPersonal) {
        this._remuneracionPersonal = _remuneracionPersonal;
    }

    public void setTitulo(List<Titulo> _titulo) {
        this._titulo = _titulo;
    }

    public void setTrabajador(List<Trabajador> _trabajador) {
        this._trabajador = _trabajador;
    }

    public void setUnidadOrganica(List<UnidadOrganica> _unidadOrganica) {
        this._unidadOrganica = _unidadOrganica;
    }

    public List<List<String>> getBASICO1() {
        return BASICO1;
    }

    public void setBASICO1(List<List<String>> BASICO1) {
        this.BASICO1 = BASICO1;
    }

    public List<List<String>> getBASICO2() {
        return BASICO2;
    }

    public void setBASICO2(List<List<String>> BASICO2) {
        this.BASICO2 = BASICO2;
    }

    public List<List<String>> getBASICO3() {
        return BASICO3;
    }

    public void setBASICO3(List<List<String>> BASICO3) {
        this.BASICO3 = BASICO3;
    }

    public List<List<String>> getBASICO4() {
        return BASICO4;
    }

    public void setBASICO4(List<List<String>> BASICO4) {
        this.BASICO4 = BASICO4;
    }

    public List<List<String>> getBASICO5() {
        return BASICO5;
    }

    public void setBASICO5(List<List<String>> BASICO5) {
        this.BASICO5 = BASICO5;
    }

    public List<List<String>> getBASICO6() {
        return BASICO6;
    }

    public void setBASICO6(List<List<String>> BASICO6) {
        this.BASICO6 = BASICO6;
    }

    public List<List<String>> getCOMPCAR1() {
        return COMPCAR1;
    }

    public void setCOMPCAR1(List<List<String>> COMPCAR1) {
        this.COMPCAR1 = COMPCAR1;
    }

    public List<List<String>> getCOMPCAR2() {
        return COMPCAR2;
    }

    public void setCOMPCAR2(List<List<String>> COMPCAR2) {
        this.COMPCAR2 = COMPCAR2;
    }

    public List<List<String>> getCOMPCAR3() {
        return COMPCAR3;
    }

    public void setCOMPCAR3(List<List<String>> COMPCAR3) {
        this.COMPCAR3 = COMPCAR3;
    }

    public List<List<String>> getCOMPLEG1() {
        return COMPLEG1;
    }

    public void setCOMPLEG1(List<List<String>> COMPLEG1) {
        this.COMPLEG1 = COMPLEG1;
    }

    public List<List<String>> getCOMPTRA1() {
        return COMPTRA1;
    }

    public void setCOMPTRA1(List<List<String>> COMPTRA1) {
        this.COMPTRA1 = COMPTRA1;
    }

    public List<List<String>> getCOMPTRA2() {
        return COMPTRA2;
    }

    public void setCOMPTRA2(List<List<String>> COMPTRA2) {
        this.COMPTRA2 = COMPTRA2;
    }

    public List<List<String>> getCOMPTRA3() {
        return COMPTRA3;
    }

    public void setCOMPTRA3(List<List<String>> COMPTRA3) {
        this.COMPTRA3 = COMPTRA3;
    }

    public List<List<String>> getCOMPTRA4() {
        return COMPTRA4;
    }

    public void setCOMPTRA4(List<List<String>> COMPTRA4) {
        this.COMPTRA4 = COMPTRA4;
    }

    public List<List<String>> getCOMPTRA5() {
        return COMPTRA5;
    }

    public void setCOMPTRA5(List<List<String>> COMPTRA5) {
        this.COMPTRA5 = COMPTRA5;
    }

    public List<List<String>> getCOMPTRA6() {
        return COMPTRA6;
    }

    public void setCOMPTRA6(List<List<String>> COMPTRA6) {
        this.COMPTRA6 = COMPTRA6;
    }

    public List<List<String>> getCOMPTRA7() {
        return COMPTRA7;
    }

    public void setCOMPTRA7(List<List<String>> COMPTRA7) {
        this.COMPTRA7 = COMPTRA7;
    }

    public List<List<String>> getORGAN1() {
        return ORGAN1;
    }

    public void setORGAN1(List<List<String>> ORGAN1) {
        this.ORGAN1 = ORGAN1;
    }

    public List<List<String>> getPPAL1() {
        return PPAL1;
    }

    public void setPPAL1(List<List<String>> PPAL1) {
        this.PPAL1 = PPAL1;
    }

    public List<List<String>> getPPAL2() {
        return PPAL2;
    }

    public void setPPAL2(List<List<String>> PPAL2) {
        this.PPAL2 = PPAL2;
    }

    public List<List<String>> getPPAL3() {
        return PPAL3;
    }

    public void setPPAL3(List<List<String>> PPAL3) {
        this.PPAL3 = PPAL3;
    }
    
    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }
    
    
    private ArrayList<String> misCSVs = new ArrayList<String>();
    public ArrayList<String> archivos = new ArrayList<String>();
    
    private int rechazadoOrgano = 0;
    String path;
    String origenArchivo;
    String tipoProceso;
    Session session;
    CreadorDesdeCsv cdc;
    List<String> errores;
    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getRechazadoOrgano() {
        return rechazadoOrgano;
    }

    public void setRechazadoOrgano(int rechazadoOrgano) {
        this.rechazadoOrgano = rechazadoOrgano;
    }

    public ArrayList<String> getMisCSVs() {
        return misCSVs;
    }

    public String getOrigenArchivo() {
        return origenArchivo;
    }

    public String getPath() {
        return path;
    }

    public Session getSession() {
        return session;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public CreadorDesdeCsv getCdc() {
        return cdc;
    }

//    public Tratamiento(String path, String origenArchivo, Session session, List<String> errores, String tipoProceso, Usuario usuario) throws IOException, ParseException {
//        this.path = path;
//        this.origenArchivo = origenArchivo;
//        this.session = session;
//        this.errores = errores;
//        this.tipoProceso = tipoProceso;
//        this.usuario = usuario;
//
//        archivos.add("ORGAN1");
//        archivos.add("BASICO6");
//        archivos.add("BASICO5");
//        archivos.add("BASICO4");
//        archivos.add("BASICO1");
//        archivos.add("BASICO2");
//        archivos.add("BASICO3");
//        archivos.add("PPAL1");
//        archivos.add("PPAL2");
//        archivos.add("PPAL3");
//        archivos.add("COMPTRA1");
//        archivos.add("COMPTRA2");
//        archivos.add("COMPTRA3");
//        archivos.add("COMPTRA4");
//        archivos.add("COMPTRA5");
//        archivos.add("COMPTRA6");
//        archivos.add("COMPTRA7");
//        archivos.add("COMPCAR1");
//        archivos.add("COMPCAR2");
//        archivos.add("COMPCAR3");
//        archivos.add("COMPLEG1");
//
//        //temano de misCSVs = 21 (hay 21 archivos CSV)
//        for (int k = 0; k < 21; k++) {
//            misCSVs.add("");
//        }
//
//        //ingresar los archivos en objecto Java
//        errores = listArchivosDepositorioClassified(path);
//
//
//        if (misCSVs.get(0).equals("")) {
//            errores.add("Archivo ORGAN1 (Entidad Unidad Ejecutora) no encontrado");
//            return;
//        }
//
//        try {
//            ORGAN1 = cargarCSVEntidadUEToArrayList(path.concat(misCSVs.get(0).concat(".csv")));
//
//        } catch (FileNotFoundException ex) {
//            errores.add("No se encuentra el archivo de Entidad Unidad Ejecutora.");
//            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        } catch (IOException ex) {
//            errores.add("Error al leer el archivo de Entidad Unidad Ejecutora.");
//            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//
//        cdc = new CreadorDesdeCsv(this);
//
//        _entidadesUEjecutora = cdc.csvToListEntidadUEjecutora(ORGAN1, session, errores, origenArchivo);
//
//        //verifica si los organismos informantes estan definidos en la base de dato
//        //si no se trata de una carga inicial
////        if (!origenArchivo.equals(OrigenArchivos.CARGA_INICIAL_ORGANISMOS)) {
////            for (Entidad_BK _entidaduejecutora : _entidadesUEjecutora) {
////                //si no hay en la base de dato el organismo informante, se informa este problema y borra este organismo
////                if (!CreadorDesdeCsv.presenciaTablaEntidadUEjecutora(_entidaduejecutora, session, errores)) {
////                    errores.add("Esta entidad unidad ejecutora " + _entidaduejecutora.getCodigoEntidadUE() + " no esta definida en la base de dato!");
////                    if (!_entidadesUEjecutora.isEmpty()) {
////                        _entidadesUEjecutora.remove(_entidaduejecutora);
////                    }
////                }
////            }
////        }
//    }

//    public List<String> generacionListDesdeCSV() {
//                
//        if (!misCSVs.get(1).equals("")) {
//            try {
//                BASICO6 = cargarCSVToArrayList(path.concat(misCSVs.get(1).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CONCEPTOS_REMUNERATIVOS, isConcepto.getRechazado());
//                _conceptoRemunerativo = cdc.csvToListConceptoRemunerativo(BASICO6, session, errores, origenArchivo, isConcepto);
//                System.out.println("----------------------- BASICO6 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("No se encontro el archivo BASICO6");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo BASICO6");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(5).equals("")) {
//            try {
//                BASICO2 = cargarCSVToArrayList(path.concat(misCSVs.get(5).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.UNIDADES_ORGANICAS, isUnidadO.getRechazado());
//                _unidadOrganica = cdc.csvToListUnidadOrganica(BASICO2, session, errores, origenArchivo, isUnidadO);
//                System.out.println("----------------------- BASICO2 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo BASICO2 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo BASICO2");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(6).equals("")) {
//            try {
//                BASICO3 = cargarCSVToArrayList(path.concat(misCSVs.get(6).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CARGOS, isCargo.getRechazado());
//                _cargo = cdc.csvToListCargo(BASICO3, session, errores, origenArchivo, isCargo);
//                System.out.println("----------------------- BASICO3 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo BASICO3 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo BASICO3");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        //necesitamos los 3 archivos (Trabajadores y Legajos y CargosAsignados) juntos 
//        if ((!misCSVs.get(7).equals("")) && (!misCSVs.get(8).equals("")) && (!misCSVs.get(9).equals(""))) {
//            try {
//                PPAL1 = cargarCSVToArrayList(path.concat(misCSVs.get(7).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.TRABAJADORES, isTrabajador.getRechazado());
//                PPAL2 = cargarCSVToArrayList(path.concat(misCSVs.get(8).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.LEGAJOS, isLegajo.getRechazado());
//                PPAL3 = cargarCSVToArrayList(path.concat(misCSVs.get(9).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CARGOS_ASIGNADOS, isCargoA.getRechazado());
//
//                _trabajador = cdc.csvToListTrabajador(PPAL1, session, errores, origenArchivo, isTrabajador);
//                _legajo = cdc.csvToListLegajo(PPAL2, session, errores, origenArchivo, isLegajo);
//                _cargoAsignado = cdc.csvToListCargoAsignado(PPAL3, session, errores, origenArchivo, tipoProceso, false, isCargoA);
//                    
//                //verificacion que si existe un trabajador existe tambien en legajo y cargo asignado (codigo EntidadUE, tipo y numero documento)
//                //Tratamiento.ver();
//                
//            } catch (FileNotFoundException ex) {
//                errores.add(ex.toString());
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                errores.add(ex.toString());
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ParseException ex) {
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        } else {
//            //ponemos los valores 7 8 9 de la list que contiene el nombre de los archivos a 0 para no hacer el tratamiento
//            for (int i = 7; i < 10; i++) {
//                misCSVs.set(i, "");
//            }
//        }
//
//        if (!misCSVs.get(10).equals("")) {
//            try {
//                COMPTRA1 = cargarCSVToArrayList(path.concat(misCSVs.get(10).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.FAMILIARES, isFamiliar.getRechazado());
//                _familiar = cdc.csvToListFamiliar(COMPTRA1, session, errores, origenArchivo, isFamiliar);
//
//                System.out.println("----------------------- COMPTRA1 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA1 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA1");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA1");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(11).equals("")) {
//            try {
//                COMPTRA2 = cargarCSVToArrayList(path.concat(misCSVs.get(11).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.TITULOS, isTitulo.getRechazado());
//                _titulo = cdc.CsvToListTitulo(COMPTRA2, session, errores, origenArchivo, isTitulo);
//                System.out.println("----------------------- COMPTRA2 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA2 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA2");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA2");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(12).equals("")) {
//            try {
//                COMPTRA3 = cargarCSVToArrayList(path.concat(misCSVs.get(12).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CERTIFICACIONES, isCertificacion.getRechazado());
//                _certificacion = cdc.csvToListCertificacion(COMPTRA3, session, errores, origenArchivo, isCertificacion);
//                System.out.println("----------------------- COMPTRA3 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA3 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA3");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA3");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(13).equals("")) {
//            try {
//                COMPTRA4 = cargarCSVToArrayList(path.concat(misCSVs.get(13).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CURSOS, isCurso.getRechazado());
//                _curso = cdc.csvToListCurso(COMPTRA4, session, errores, origenArchivo, isCurso);
//
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA4 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA4");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA4");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(14).equals("")) {
//            try {
//                COMPTRA5 = cargarCSVToArrayList(path.concat(misCSVs.get(14).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.ANTECEDENTES_LABORALES, isAntecedent.getRechazado());
//                _antLaborale = cdc.csvToListAnt_Laborales(COMPTRA5, session, errores, origenArchivo, isAntecedent);
//
//                System.out.println("----------------------- COMPTRA4 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA5 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA5");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA5");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(15).equals("")) {
//            try {
//                COMPTRA6 = cargarCSVToArrayList(path.concat(misCSVs.get(15).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.MERITOS_DEMERITOS, isMeritoDemerito.getRechazado());
//                _meritoDemerito = cdc.csvToListMeritoDemerito(COMPTRA6, session, errores, origenArchivo, isMeritoDemerito);
//
//                System.out.println("----------------------- COMPTRA6 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA6 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA6");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA6");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(16).equals("")) {
//            try {
//                COMPTRA7 = cargarCSVToArrayList(path.concat(misCSVs.get(16).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.PRODUCIONES_INTELECTUALES, isProduccion.getRechazado());
//                _producionIntelectual = cdc.csvToListProduccionIntelectual(COMPTRA7, session, errores, origenArchivo,isProduccion);
//
//                System.out.println("----------------------- COMPTRA7 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPTRA7 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPTRA7");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPTRA7");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//        
//        if (!misCSVs.get(17).equals("")) {
//            try {
//                COMPCAR1 = cargarCSVToArrayList(path.concat(misCSVs.get(17).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.REMUNERACIONES_PERSONALES, isRemuneracion.getRechazado());
//                cdc.listRemuneracionPersonalVerificacionesErrores(COMPCAR1, session, origenArchivo, errores, isRemuneracion);
//
//                System.out.println("----------------------- COMPCAR1 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPCAR1 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPCAR1 ");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//        }
//    }
//        
//        if (!misCSVs.get(18).equals("")) {
//            try {
//                COMPCAR2 = cargarCSVToArrayList(path.concat(misCSVs.get(18).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.EVALUACIONES, isEvaluacion.getRechazado());
//                cdc.listEvaluacionPersonalVerificacionesErrores(COMPCAR2, session, origenArchivo, errores, isEvaluacion);
//
//                System.out.println("----------------------- COMPTRA2 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPCAR2 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPCAR2 ");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPCAR2");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//        
//        if (!misCSVs.get(19).equals("")) {
//            try {
//                COMPCAR3 = cargarCSVToArrayList(path.concat(misCSVs.get(19).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.AUSENCIAS_LICENCIAS, isAusencia.getRechazado());
//                cdc.csvToListAusLicPersonal(COMPCAR3, session, origenArchivo, errores, isAusencia);
//
//                System.out.println("----------------------- COMPTRA3 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPCAR3 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPCAR3 ");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPCAR3");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//
//        if (!misCSVs.get(20).equals("")) {
//            try {
//                COMPLEG1 = cargarCSVToArrayList(path.concat(misCSVs.get(20).concat(".csv")), _entidadesUEjecutora, helpers.Constantes.CONSTANCIAS_DOCUMENTALES, isConstancia.getRechazado());
//                cdc.listConstanciaDocumentalVerificacionesErrores(COMPLEG1, session, origenArchivo, errores, isConstancia);
//
//                System.out.println("----------------------- COMPLEG1 ");
//            } catch (FileNotFoundException ex) {
//                errores.add("Archivo COMPLEG1 no encontrado");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (IOException ex) {
//                errores.add("Error leyendo archivo COMPLEG1");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            } catch (ParseException ex) {
//                errores.add("Error parseando archivo COMPLEG1");
//                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
//                return errores;
//            }
//        }
//        return errores;
//    }

    //verificacion si existe un registro en el archivo Trabajador existe lo mismo en Legajo y Cargo
    /*public static List<String> claveTrabajadorLegajoCargo(List<List<String>> lltrabajador, List<List<String>> lllegajo, List<List<String>> llcargo) {
        boolean trabajadorExisteLegajo = false;
        boolean trabajadorExisteCargo = false;
        boolean legajoExisteTrabajador = false;
        boolean cargoExisteTrabajador = false;
        List<String> errores = new LinkedList<String>();
        List<String> ltrabajadorAGuardar = new LinkedList<String>();
        List<String> llegajoAGuardar = new LinkedList<String>();
        List<String> lcargoasignadoAGuardar = new LinkedList<String>();
        List<Integer> trabajadorToDelete = new LinkedList<Integer>();
        List<Integer> cargoAToDelete = new LinkedList<Integer>();
        List<Integer> legajoToDelete = new LinkedList<Integer>();
        
        //trabajador en legajo y cargo
        for (List<String> ltrabajador : lltrabajador) {
            for (List<String> llegajo : lllegajo) {
                if ((ltrabajador.get(0).equals(llegajo.get(0))) && (ltrabajador.get(1).equals(llegajo.get(2))) && (ltrabajador.get(2).equals(llegajo.get(3)))) {
                    //existe en el legajo
                    trabajadorExisteLegajo = true;
                    break;
                }
                trabajadorExisteLegajo = false;
            }
            if (trabajadorExisteLegajo == false) {
                errores.add("El trabajador definido con su codigo de entidad unidad ejecutora " + ltrabajador.get(0) + " y su tipo de documento " + ltrabajador.get(1) + " y su numero de documento " + ltrabajador.get(2) + " no existe en el archivo Legajos");
                trabajadorToDelete.add(lltrabajador.indexOf(ltrabajador));
            }

            for (List<String> lcargo : llcargo) {
                if ((ltrabajador.get(0).equals(lcargo.get(0))) && (ltrabajador.get(1).equals(lcargo.get(2))) && (ltrabajador.get(2).equals(lcargo.get(3)))) {
                    //existe en el cargo 
                    trabajadorExisteCargo = true;
                    break;
                }
                trabajadorExisteCargo = false;
            }
            if (trabajadorExisteCargo == false) {
                errores.add("El trabajador definido con su codigo de entidad unidad ejecutora " + ltrabajador.get(0) + " y su tipo de documento " + ltrabajador.get(1) + " y su numero de documento " + ltrabajador.get(2) + " no existe en el archivo Cargos");
                trabajadorToDelete.add(lltrabajador.indexOf(ltrabajador));
            }
        }
        
        for(int i = trabajadorToDelete.size(); i>=1; i--){
            lltrabajador.remove(i);
        }

        //legajo en trabajador
        for (List<String> listlegajo : lllegajo) {
            for (List<String> listtrabajador : lltrabajador) {
                if ((listlegajo.get(0).equals(listtrabajador.get(0))) && (listlegajo.get(2).equals(listtrabajador.get(1))) && (listlegajo.get(3).equals(listtrabajador.get(2)))) {
                    legajoExisteTrabajador = true;
                    break;
                }
                legajoExisteTrabajador = false;
            }
            if (legajoExisteTrabajador == false) {
                errores.add("El legajo definido con su codigo de entidad unidad ejecutora " + listlegajo.get(0) + " y su tipo de documento " + listlegajo.get(2) + " y su numero de documento " + listlegajo.get(3) + " no existe en el archivo Trabajadores");
                legajoToDelete.add(lllegajo.indexOf(listlegajo));
            }
        }

        for(int i = legajoToDelete.size(); i>=1; i--){
            lllegajo.remove(i);
        }
        
        //cargo en trabajador
        for (List<String> licargo : llcargo) {
            for (List<String> litrabajador : lltrabajador) {
                if ((licargo.get(0).equals(litrabajador.get(0))) && (licargo.get(2).equals(litrabajador.get(1))) && (licargo.get(3).equals(litrabajador.get(2)))) {
                    cargoExisteTrabajador = true;
                    break;
                }
                cargoExisteTrabajador = false;
            }
            if (cargoExisteTrabajador == false) {
                errores.add("El cargo definido con su codigo de entidad unidad ejecutora " + licargo.get(0) + " y su tipo de documento " + licargo.get(2) + " y su numero de documento " + licargo.get(3) + " no existe en el archivo Trabajadores");
                cargoAToDelete.add(llcargo.indexOf(licargo));
            }
        }

        for(int i = cargoAToDelete.size(); i>=1; i--){
            llcargo.remove(i);
        }
        
        return errores;
    }*/

    public List<String> listArchivosDepositorioClassified(String path) throws IOException {
        File directorio = new File(path);
        File[] list = directorio.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].getName().endsWith(".CSV") || list[i].getName().endsWith(".csv")) {
                    misCSVs.set(compareStringDepositorioToArrayListArchivos(list[i].getName().substring(0, (list[i].getName().length()) - 4), archivos, errores), list[i].getName().substring(0, (list[i].getName().length()) - 4));
                }
            }
        } else {
            errores.add(directorio + " : Problema con el directorio");
        }
        return errores;

    }

    public static int compareStringDepositorioToArrayListArchivos(String depositorio, ArrayList archivo, List<String> errores) {

        int posicion = 0;

        posicion = archivo.indexOf(depositorio);
        if (posicion == -1) {
            errores.add("Error en el nombre del archivo.");
            return 0;
        }
        
        return posicion;
    }

    //ingresar datos del archivo CSV en un list list string que contiene todos los campos del CSV
    public List<List<String>> cargarCSVToArrayList(String file, List<Entidad_BK> leue, String tipoDato, int rechazado) throws FileNotFoundException, IOException {
        List<List<String>> documento = new LinkedList<List<String>>();
        ArrayList<String> linea;
        boolean entrada;
        boolean hayEntidadUE;

        CSVReader reader = new CSVReader(new FileReader(file), '|');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length > 2) {
                entrada = true;
                hayEntidadUE = false;
                linea = new ArrayList<String>();
                linea.addAll(Arrays.asList(nextLine));

                for (Entidad_BK eue : leue) {
                    if ((linea.get(0).equals(eue.getCodigoEntidadUE()))) {
                        hayEntidadUE = true;
                        entrada = false;
                    } 
                }
                
                if(hayEntidadUE == true){
                    documento.add(linea);
                }else{
                    errores.add("La entidad unidad ejecutora " + linea.get(0) + " del archivo " + tipoDato + " no existe en el archivo Entidad Unidad Ejecutora.");
                }
                
                if (tipoDato.equals("Conceptos remunerativos") && entrada == true) {
                    isConcepto.setRechazado(isConcepto.getRechazado()+1);
                }

                if (tipoDato.equals("Unidades Organicas") && entrada == true) {
                    isUnidadO.setRechazado(isUnidadO.getRechazado()+1);
                }

                if (tipoDato.equals("Cargos") && entrada == true) {
                    isCargo.setRechazado(isCargo.getRechazado()+1);
                }

                if (tipoDato.equals("Trabajadores") && entrada == true) {
                    isTrabajador.setRechazado(isTrabajador.getRechazado()+1);
                }

                if (tipoDato.equals("Legajos") && entrada == true) {
                    isLegajo.setRechazado(isLegajo.getRechazado()+1);
                }

                if (tipoDato.equals("Cargos Asignados") && entrada == true) {
                    isCargoA.setRechazado(isCargoA.getRechazado()+1);
                }

                if (tipoDato.equals("Familiares") && entrada == true) {
                    isFamiliar.setRechazado(isFamiliar.getRechazado()+1);
                }

                if (tipoDato.equals("Titulos") && entrada == true) {
                    isTitulo.setRechazado(isTitulo.getRechazado()+1);
                }

                if (tipoDato.equals("Certificaciones") && entrada == true) {
                    isCertificacion.setRechazado(isCertificacion.getRechazado()+1);
                }

                if (tipoDato.equals("Cursos") && entrada == true) {
                    isCurso.setRechazado(isCurso.getRechazado()+1);
                }

                if (tipoDato.equals("Antecedentes Laborales") && entrada == true) {
                    isAntecedent.setRechazado(isAntecedent.getRechazado()+1);
                }

                if (tipoDato.equals("Meritos Demeritos") && entrada == true) {
                    isMeritoDemerito.setRechazado(isMeritoDemerito.getRechazado()+1);
                }

                if (tipoDato.equals("Produciones Intelectuales") && entrada == true) {
                    isProduccion.setRechazado(isProduccion.getRechazado()+1);
                }

                if (tipoDato.equals("Remuneraciones Personales") && entrada == true) {
                    isRemuneracion.setRechazado(isRemuneracion.getRechazado()+1);
                }

                if (tipoDato.equals("Evaluaciones") && entrada == true) {
                    isEvaluacion.setRechazado(isEvaluacion.getRechazado()+1);
                }

                if (tipoDato.equals("Ausencias y Licencias") && entrada == true) {
                    isAusencia.setRechazado(isAusencia.getRechazado()+1);
                }

                if (tipoDato.equals(helpers.Constantes.CONSTANCIAS_DOCUMENTALES) && entrada == true) {
                    isConstancia.setRechazado(isConstancia.getRechazado()+1);
                }
            }
        }

        return documento;
    }

    //creacion ListCogido para tener el organimo informante en un list que puedo usar despues para buscar el organismo informante
    public static void getCodigoEntidadUE(List<String> listCodigo, String file) throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length > 2) {
                listCodigo.add(nextLine[0]);
            }
        }
    }

    //ingresar datos de la entidadUE en un list de list de string que contiene todos los campos del CSV    
    public static List<List<String>> cargarCSVEntidadUEToArrayList(String file) throws FileNotFoundException, IOException {
        List<List<String>> documento = new LinkedList<List<String>>();
        ArrayList<String> linea;

        CSVReader reader = new CSVReader(new FileReader(file), '|');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length > 2) {
                linea = new ArrayList<String>();
                linea.addAll(Arrays.asList(nextLine));
                documento.add(linea);
            }
        }
        return documento;
    }


    public List<LineasArchivos> getCantLineasArchivos(List<String> errores) {
        //recuperacion Lineas de cada archivos
        List<LineasArchivos> llo = new LinkedList<LineasArchivos>();
        try {
            llo = ListLineaEntidadUE.getListLineaEntidadUE(path, misCSVs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
            errores.add("Error buscando archivo: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
            errores.add("Error procesando archivo: " + ex.getMessage());
        }
        return llo;
    }
}
