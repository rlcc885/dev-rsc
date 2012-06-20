/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
public class Entidad implements Serializable {

    public static String ESTADO_BAJA = "Baja";
    public static String ESTADO_ALTA = "Alta";
    public static String ESTADO_xxx = "Alta";
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    //antes datos de organismo informante
    @NonVisual
    private long id;
    //@Validate("required")
//    @PrimaryKeyJoinColumn
    //private UnidadEjecutora unidadEjecutora;
    //@Validate("required")
    String estado;
    @Validate("required")
    String clas_funcional;
    String cue_entidad;
    String cue_elemento;
    Boolean def_servir; //Servir Ingresa la información
    Boolean pliego_rep; //Visualiza reportes de pliegos
    Boolean sector_rep; //Visualiza reportes de sector
    //@Validate("required")
    //String cod_organismo;
    //@Validate("required")
    //private String tipo_email;
    String correoElectronicoInstitucional;
    //antes datos de unidad ejecutora
    private int cod_mef_ue;
    @Validate("required")
    private String denominacion;
    @Validate("required")
    private String nivel_gobierno;
    @Validate("required")
    private String sector_gobierno;
    @Validate("required")
    private String pliego;
    private String ruc;
    //nuevo campo
    private String localidad;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_dist;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_prov;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_dept;
    private String direcion;
    private String titularEntidad;
    private String correoElectronicoTitular;
    private String jefeRRHHEntidad;
    private String correoElectronicoJefeRRHH;
    private String TEJefeRRHH;
    private String TEMovilJefeRRHH;
    @Validate("required")
    private String codigoEntidadUE;
    private boolean trabajadorAgregaDatos;
    
    //@Column(name = "IDCLASFUNCIONAL")
    private DatoAuxiliar clasificadorFuncional;
    
    //private DatoAuxiliar idNivelGobierno;

    public Boolean getPliego_rep() {
        return pliego_rep;
    }

    public void setPliego_rep(Boolean pliego_rep) {
        this.pliego_rep = pliego_rep;
    }

    public Boolean getSector_rep() {
        return sector_rep;
    }

    public void setSector_rep(Boolean sector_rep) {
        this.sector_rep = sector_rep;
    }

    public boolean getTrabajadorAgregaDatos() {
        return trabajadorAgregaDatos;
    }

    public void setTrabajadorAgregaDatos(boolean trabajadorAgregaDatos) {
        this.trabajadorAgregaDatos = trabajadorAgregaDatos;
    }
    Boolean proc_batch; // Admite proceso Batch

    public String getClas_funcional() {
        return clas_funcional;
    }

    public void setClas_funcional(String clas_funcional) {
        this.clas_funcional = clas_funcional;
    }

    /*
     * public String getCod_organismo() { return cod_organismo; }
     *
     * public void setCod_organismo(String cod_organismo) { if (cod_organismo !=
     * null) { this.cod_organismo = cod_organismo.trim(); }
    }
     */
    public String getCue_entidad() {
        return cue_entidad;
    }

    public void setCue_entidad(String cue_entidad) {
        this.cue_entidad = cue_entidad;
    }

    public String getCue_elemento() {
        return cue_elemento;
    }

    public void setCue_elemento(String cue_elemento) {
        this.cue_elemento = cue_elemento;
    }

    public String getTEJefeRRHH() {
        return TEJefeRRHH;
    }

    public void setTEJefeRRHH(String TEJefeRRHH) {
        this.TEJefeRRHH = TEJefeRRHH;
    }

    public String getTEMovilJefeRRHH() {
        return TEMovilJefeRRHH;
    }

    public void setTEMovilJefeRRHH(String TEMovilJefeRRHH) {
        this.TEMovilJefeRRHH = TEMovilJefeRRHH;
    }

    public int getCod_mef_ue() {
        return cod_mef_ue;
    }

    public void setCod_mef_ue(int cod_mef_ue) {
        this.cod_mef_ue = cod_mef_ue;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_dept() {
        return cod_ubi_dept;
    }

    public void setCod_ubi_dept(DatoAuxiliar cod_ubi_dept) {
        this.cod_ubi_dept = cod_ubi_dept;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(DatoAuxiliar cod_ubi_dist) {
        this.cod_ubi_dist = cod_ubi_dist;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(DatoAuxiliar cod_ubi_prov) {
        this.cod_ubi_prov = cod_ubi_prov;
    }

    public String getCorreoElectronicoInstitucional() {
        return correoElectronicoInstitucional;
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }

    public String getCorreoElectronicoJefeRRHH() {
        return correoElectronicoJefeRRHH;
    }

    public void setCorreoElectronicoJefeRRHH(String correoElectronicoJefeRRHH) {
        this.correoElectronicoJefeRRHH = correoElectronicoJefeRRHH;
    }

    public String getCorreoElectronicoTitular() {
        return correoElectronicoTitular;
    }

    public void setCorreoElectronicoTitular(String correoElectronicoTitular) {
        this.correoElectronicoTitular = correoElectronicoTitular;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDirecion() {
        return direcion;
    }

    public void setDirecion(String direcion) {
        this.direcion = direcion;
    }

    public String getJefeRRHHEntidad() {
        return jefeRRHHEntidad;
    }

    public void setJefeRRHHEntidad(String jefeRRHHEntidad) {
        this.jefeRRHHEntidad = jefeRRHHEntidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNivel_gobierno() {
        return nivel_gobierno;
    }

    public void setNivel_gobierno(String nivel_gobierno) {
        this.nivel_gobierno = nivel_gobierno;
    }

    public String getPliego() {
        return pliego;
    }

    public void setPliego(String pliego) {
        this.pliego = pliego;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getSector_gobierno() {
        return sector_gobierno;
    }

    public void setSector_gobierno(String sector_gobierno) {
        this.sector_gobierno = sector_gobierno;
    }

    public String getTitularEntidad() {
        return titularEntidad;
    }

    public void setTitularEntidad(String titularEntidad) {
        this.titularEntidad = titularEntidad;
    }

    public String getCodigoEntidadUE() {
        return codigoEntidadUE;
    }

    public void setCodigoEntidadUE(String codigoEntidadUE) {
        this.codigoEntidadUE = codigoEntidadUE;
    }
    /*
     * public String getDen_organismo() { return den_organismo; }
     *
     * public void setDen_organismo(String den_organismo) { if (den_organismo !=
     * null) { this.den_organismo = den_organismo.trim(); }
    }
     */

    public Boolean getDef_servir() {
        return def_servir;
    }

    public void setDef_servir(Boolean def_servir) {
        this.def_servir = def_servir;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getProc_batch() {
        return proc_batch;
    }

    public void setProc_batch(Boolean proc_batch) {
        this.proc_batch = proc_batch;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entidad other = (Entidad) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    @ManyToOne
    public DatoAuxiliar getClasificadorFuncional() {
        return clasificadorFuncional;
    }

    public void setClasificadorFuncional(DatoAuxiliar clasificadorFuncional) {
        this.clasificadorFuncional = clasificadorFuncional;
    }
    /*
    @ManyToOne
    public DatoAuxiliar getIdNivelGobierno() {
        return idNivelGobierno;
    }

    public void setIdNivelGobierno(DatoAuxiliar idNivelGobierno) {
        this.idNivelGobierno = idNivelGobierno;
    }
    */
    
    
}
