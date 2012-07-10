package com.tida.servir.entities;



import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import javax.persistence.*;
/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_CARGOASIGNADO")
public class CargoAsignado {
    
    public static boolean ESTADO_BAJA = false;
    public static boolean ESTADO_ALTA = true;
    
    @NonVisual
    private Long id;

    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
//  @PrimaryKeyJoinColumn
    private Trabajador trabajador;

    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
    private Cargoxunidad cargoxunidad;

    @Validate("required")
//  @OneToMany(cascade=CascadeType.ALL)
    private List<RemuneracionPersonal> remuneracionesPersonales;

    @Validate("required")
//  @OneToMany(cascade=CascadeType.ALL)
    private List<EvaluacionPersonal> evaluacionesPersonales;
    
    @Validate("required")
//   @OneToMany(cascade=CascadeType.ALL)
    private List<AusLicPersonal> ausLicPersonales;

    @Validate("required")
//    @ManyToOne(cascade = CascadeType.PERSIST)
    private Legajo legajo;

    private Boolean estado;
    
    private Boolean puestoconfianza;

    @Temporal(TemporalType.DATE)
    private Date fec_inicio;

    @Temporal(TemporalType.DATE)
    private Date fec_fin;

    private Integer ctd_per_superv;
    private String motivo_cese;
    //private String tipoVinculo;
    private DatoAuxiliar tipovinculo;

    @ManyToOne
    public DatoAuxiliar getTipovinculo() {
        return tipovinculo;
    }

    public void setTipovinculo(DatoAuxiliar tipovinculo) {
        this.tipovinculo = tipovinculo;
    }

    @OneToMany(cascade=CascadeType.ALL)
    public List<AusLicPersonal> getAusLicPersonales() {
        return ausLicPersonales;
    }

    public void setAusLicPersonales(List<AusLicPersonal> ausLicPersonales) {
        this.ausLicPersonales = ausLicPersonales;
    }

    @ManyToOne
    public Cargoxunidad getCargoxunidad() {
        return cargoxunidad;
    }

    public void setCargoxunidad(Cargoxunidad cargoxunidad) {
        this.cargoxunidad = cargoxunidad;
    }

    public Integer getCtd_per_superv() {
        return ctd_per_superv;
    }

    public void setCtd_per_superv(Integer ctd_per_superv) {
        this.ctd_per_superv = ctd_per_superv;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Boolean getPuestoConfianza() {
        return puestoconfianza;
    }

    public void setPuestoconfianza(Boolean puestoconfianza) {
        this.puestoconfianza = puestoconfianza;
    }

    @OneToMany(cascade=CascadeType.ALL)
    public List<EvaluacionPersonal> getEvaluacionesPersonales() {
        return evaluacionesPersonales;
    }

    public void setEvaluacionesPersonales(List<EvaluacionPersonal> evaluacionesPersonales) {
        this.evaluacionesPersonales = evaluacionesPersonales;
    }

    public Date getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(Date fec_fin) {
        this.fec_fin = fec_fin;
    }

    public Date getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(Date fec_inicio) {
        this.fec_inicio = fec_inicio;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Legajo getLegajo() {
        return legajo;
    }

    public void setLegajo(Legajo legajo) {
        this.legajo = legajo;
    }

    public String getMotivo_cese() {
        return motivo_cese;
    }

    public void setMotivo_cese(String motivo_cese) {
        this.motivo_cese = motivo_cese;
    }

    @OneToMany(cascade=CascadeType.ALL)
    public List<RemuneracionPersonal> getRemuneracionesPersonales() {
        return remuneracionesPersonales;
    }

    public void setRemuneracionesPersonales(List<RemuneracionPersonal> remuneracionesPersonales) {
        this.remuneracionesPersonales = remuneracionesPersonales;
    }
    
    @ManyToOne
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CargoAsignado other = (CargoAsignado) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }


}
