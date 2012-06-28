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
 * @author 
 */
@Entity
public class Entidad implements Serializable {

    public static String ESTADO_BAJA = "Baja";
    public static String ESTADO_ALTA = "Alta";
    public static String ESTADO_ssss = "Alta";

    //antes datos de organismo informante
    @NonVisual
    private long id;
    private long entidad_id;
    String cue_entidad;

    private DatoAuxiliar nivelGobierno;   

    private DatoAuxiliar organizacionEstado;  

    private DatoAuxiliar sectorGobierno;    

    private DatoAuxiliar tipoOrganismo;    
    @Validate("required")
    private String denominacion;
    private String sigla;

    private String ruc;
    Boolean proc_Batch;
    private DatoAuxiliar tipoVia;
    private String direccion;
    private DatoAuxiliar tipozona;

    private String descZona;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar distrito;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar provincia;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar departamento;
    String emailInstitucional;
    String urlEntidad;
    String telefonoEntidad;
    String logotipo;
    //@NonVisual
    private Trabajador titular;
    //@NonVisual
    private Trabajador jefeRRHH;
    //@NonVisual
    private Trabajador jefeOga;
    @NonVisual
    private DatoAuxiliar tipoSubEntidad;
    @NonVisual
    private Entidad entidad;
    Boolean esSubEntidad;      
    @NonVisual
    private String validado;
    @NonVisual
    String estado;

   
    public String getCue_entidad() {
        return cue_entidad;
    }

    public void setCue_entidad(String cue_entidad) {
        this.cue_entidad = cue_entidad;
    }
    
    public String getValidado() {
        return validado;
    }

    public void setValidado(String validado) {
        this.validado = validado;
    }

    @ManyToOne
    public DatoAuxiliar getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DatoAuxiliar departamento) {
        this.departamento = departamento;
    }

    @ManyToOne
    public DatoAuxiliar getDistrito() {
        return distrito;
    }

    public void setDistrito(DatoAuxiliar distrito) {
        this.distrito = distrito;
    }

    @ManyToOne
    public DatoAuxiliar getProvincia() {
        return provincia;
    }

    public void setProvincia(DatoAuxiliar provincia) {
        this.provincia = provincia;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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
    
     public long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public Boolean getProc_Batch() {
        return proc_Batch;
    }

    public void setProc_Batch(Boolean proc_batch) {
        this.proc_Batch = proc_batch;
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
    public DatoAuxiliar getNivelGobierno() {
        return nivelGobierno;
    }

    public void setNivelGobierno(DatoAuxiliar nivelGobierno) {
        this.nivelGobierno = nivelGobierno;
    }

    @ManyToOne
    public DatoAuxiliar getOrganizacionEstado() {
        return organizacionEstado;
    }

    public void setOrganizacionEstado(DatoAuxiliar organizacionEstado) {
        this.organizacionEstado = organizacionEstado;
    }

    @ManyToOne
    public DatoAuxiliar getSectorGobierno() {
        return sectorGobierno;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoOrganismo() {
        return tipoOrganismo;
    }
    
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoZona() {
        return tipozona;
    }

    public void setTipoZona(DatoAuxiliar tipozona) {
        this.tipozona = tipozona;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(DatoAuxiliar tipoVia) {
        this.tipoVia = tipoVia;
    }
   
    @ManyToOne
    public DatoAuxiliar getTipoSubEntidad() {
        return tipoSubEntidad;
    }

    public void setTipoSubEntidad(DatoAuxiliar tipoSubEntidad) {
        this.tipoSubEntidad = tipoSubEntidad;
    }
    
    public String getDescZona() {
        return descZona;
    }

    public void setDescZona(String desczona) {
        this.descZona = desczona;
    }
    
    @ManyToOne
    public Trabajador getTitular() {
        return titular;
    }

    public void setTitular(Trabajador titular) {
        this.titular = titular;
    }
    
    @ManyToOne
    public Trabajador getJefeRRHH() {
        return jefeRRHH;
    }

    public void setJefeRRHH(Trabajador jeferrhh) {
        this.jefeRRHH = jeferrhh;
    }
    
    @ManyToOne
    public Trabajador getJefeOga() {
        return jefeOga;
    }

    public void setJefeOga(Trabajador jefeOga) {
        this.jefeOga = jefeOga;
    }
    
    @ManyToOne   
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Boolean getEsSubEntidad() {
        return esSubEntidad;
    }

    public void setEsSubEntidad(Boolean esSubEntidad) {
        this.esSubEntidad = esSubEntidad;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public void setSectorGobierno(DatoAuxiliar sectorGobierno) {
        this.sectorGobierno = sectorGobierno;
    }

    public String getTelefonoEntidad() {
        return telefonoEntidad;
    }

    public void setTelefonoEntidad(String telefonoEntidad) {
        this.telefonoEntidad = telefonoEntidad;
    }

    public void setTipoOrganismo(DatoAuxiliar tipoOrganismo) {
        this.tipoOrganismo = tipoOrganismo;
    }

    public String getUrlEntidad() {
        return urlEntidad;
    }

    public void setUrlEntidad(String urlEntidad) {
        this.urlEntidad = urlEntidad;
    }

}