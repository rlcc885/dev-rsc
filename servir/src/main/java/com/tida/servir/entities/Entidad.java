/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author 
 */
@Entity
@Table(name = "RSC_ENTIDAD")
public class Entidad implements Serializable {
    public static String ESTADO_BAJA = "Baja";
    public static String ESTADO_ALTA = "Alta";
    public static String ESTADO_ssss = "Alta";

    //antes datos de organismo informante
    @NonVisual
    private long id;
    String cue_entidad;
    @Validate("required")
    private DatoAuxiliar nivelGobierno;   
    @Validate("required")
    private DatoAuxiliar organizacionEstado;  
    @Validate("required")
    private DatoAuxiliar sectorGobierno;    
    @Validate("required")
    private DatoAuxiliar tipoOrganismo;    
    @Validate("required")
    private String denominacion;
    private String sigla;

    private String ruc;
    Boolean proc_Batch;
    private DatoAuxiliar tipoVia;
    private String descvia;
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
    @NonVisual
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
    Boolean estado;
    private String cue_rufe;

    @NonVisual
    private Integer peticiones_ws_Reniec;

    public Integer getPeticiones_ws_Reniec() {
        return peticiones_ws_Reniec;
    }

    public void setPeticiones_ws_Reniec(Integer peticiones_ws_Reniec) {
        this.peticiones_ws_Reniec = peticiones_ws_Reniec;
    }


    

    public String getCue_rufe() {
        return cue_rufe;
    }

    public void setCue_rufe(String cue_rufe) {
        this.cue_rufe = cue_rufe;
    }
    
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

    public String getDescvia() {
        return descvia;
    }

    public void setDescvia(String descvia) {
        this.descvia = descvia;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_ENTIDAD_ID_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    @Transient
    public Boolean getTienelogotipo() {
        if (this.logotipo == null){
            return false;
        }
        return true;
    }
  
}