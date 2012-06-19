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

/**
 *
 * @author ale
 */
@Entity
public class CargoAsignado {
//	@Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//  @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;

    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
//  @PrimaryKeyJoinColumn
    private Trabajador trabajador;

    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
    private Cargo cargo;

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

    private String estado;

    @Temporal(TemporalType.DATE)
    private Date fec_inicio;

    @Temporal(TemporalType.DATE)
    private Date fec_fin;

    private Integer ctd_per_superv;
    private String motivo_cese;
    private String tipoVinculo;

    public String getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    @OneToMany(cascade=CascadeType.ALL)
    public List<AusLicPersonal> getAusLicPersonales() {
        return ausLicPersonales;
    }

    public void setAusLicPersonales(List<AusLicPersonal> ausLicPersonales) {
        this.ausLicPersonales = ausLicPersonales;
    }

    @ManyToOne
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getCtd_per_superv() {
        return ctd_per_superv;
    }

    public void setCtd_per_superv(Integer ctd_per_superv) {
        this.ctd_per_superv = ctd_per_superv;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
