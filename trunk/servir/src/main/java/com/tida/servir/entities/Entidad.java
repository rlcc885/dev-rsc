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
    public static String ESTADO_ssss = "Alta";

    //antes datos de organismo informante
    @NonVisual
    private long id;
    String cue_entidad;
    @Validate("required")
    private DatoAuxiliar nivelgobierno;   
    @Validate("required")
    private DatoAuxiliar organizacionestado;  
    @Validate("required")
    private DatoAuxiliar sectorgobierno;    
    @Validate("required")
    private DatoAuxiliar tipoorganismo;    
    @Validate("required")
    private String denominacion;
    private String sigla;
    private String abreviatura;
    private String ruc;
    Boolean proc_batch;
    private DatoAuxiliar tipovia;
    private String direcion;
    private DatoAuxiliar tipozona;
    private String desczona;
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
    String telefonoentidad;
    String logotipo;
    @NonVisual
    private Trabajador titular;
    @NonVisual
    private Trabajador jeferrhh;
    @NonVisual
    private Trabajador jefeoga;
    @NonVisual
    private DatoAuxiliar tiposubentidad;
    @NonVisual
    private Entidad entidad;
    Boolean essubentidad;      
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

    public String getDirecion() {
        return direcion;
    }

    public void setDirecion(String direcion) {
        this.direcion = direcion;
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
    public DatoAuxiliar getNivelGobierno() {
        return nivelgobierno;
    }

    public void setNivelGobierno(DatoAuxiliar nivelgobierno) {
        this.nivelgobierno = nivelgobierno;
    }

    @ManyToOne
    public DatoAuxiliar getOrganizacionEstado() {
        return organizacionestado;
    }

    public void setOrganizacionEstado_id(DatoAuxiliar organizacionestado) {
        this.organizacionestado = organizacionestado;
    }

    @ManyToOne
    public DatoAuxiliar getSectorGobierno() {
        return sectorgobierno;
    }

    public void setSectorGobierno_id(DatoAuxiliar sectorgobierno) {
        this.sectorgobierno = sectorgobierno;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoOrganismo() {
        return tipoorganismo;
    }

    public void setTipoOrganismo_id(DatoAuxiliar tipoorganismo) {
        this.tipoorganismo = tipoorganismo;
    }
    
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
        return tipovia;
    }

    public void setTipoVia(DatoAuxiliar tipovia) {
        this.tipovia = tipovia;
    }
   
    @ManyToOne
    public DatoAuxiliar getTipoSubEntidad() {
        return tiposubentidad;
    }

    public void setTipoSubEntidad(DatoAuxiliar tiposubentidad) {
        this.tiposubentidad = tiposubentidad;
    }
    
    public String getDescZona() {
        return desczona;
    }

    public void setDescZona(String desczona) {
        this.desczona = desczona;
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
        return jeferrhh;
    }

    public void setJefeRRHH(Trabajador jeferrhh) {
        this.jeferrhh = jeferrhh;
    }
    
    @ManyToOne
    public Trabajador getJefeOGA() {
        return jefeoga;
    }

    public void setJefeOGA(Trabajador jefeoga) {
        this.jefeoga = jefeoga;
    }
    
    @ManyToOne   
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
}
