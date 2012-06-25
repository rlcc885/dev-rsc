package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */
@Entity
public class Curso implements Serializable {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @Id
    @GeneratedValue
    @NonVisual
    private Long id;
//    @ManyToOne(optional = false)
    @ManyToOne(optional = false)
    private Trabajador trabajador;
    private String denominacion;
    private String centro_estudios;
    private Float horas;
    private Boolean financiadoEntidad;
    private String lugar_dictado;
    @Temporal(TemporalType.DATE)
    private Date fec_emision;
    private Boolean agregadoTrabajador;
    private Boolean validado;

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    }

    public String getCentro_estudios() {
        return centro_estudios;
    }

    public void setCentro_estudios(String centro_estudios) {
        this.centro_estudios = centro_estudios;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Date getFec_emision() {
        return fec_emision;
    }

    public void setFec_emision(Date fec_emision) {
        this.fec_emision = fec_emision;
    }

    public Float getHoras() {
        return horas;
    }

    public void setHoras(Float horas) {
        this.horas = horas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFinanciadoEntidad() {
        return financiadoEntidad;
    }

    public void setFinanciadoEntidad(Boolean financiadoEntidad) {
        this.financiadoEntidad = financiadoEntidad;
    }

    public String getLugar_dictado() {
        return lugar_dictado;
    }

    public void setLugar_dictado(String lugar_dictado) {
        this.lugar_dictado = lugar_dictado;
    }

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
        final Curso other = (Curso) obj;
        if (this.trabajador != other.trabajador && (this.trabajador == null || !this.trabajador.equals(other.trabajador))) {
            return false;
        }
        if ((this.denominacion == null) ? (other.denominacion != null) : !this.denominacion.equals(other.denominacion)) {
            return false;
        }
        if ((this.centro_estudios == null) ? (other.centro_estudios != null) : !this.centro_estudios.equals(other.centro_estudios)) {
            return false;
        }
        if (this.horas != other.horas && (this.horas == null || !this.horas.equals(other.horas))) {
            return false;
        }
        if (this.financiadoEntidad != other.financiadoEntidad && (this.financiadoEntidad == null || !this.financiadoEntidad.equals(other.financiadoEntidad))) {
            return false;
        }
        if ((this.lugar_dictado == null) ? (other.lugar_dictado != null) : !this.lugar_dictado.equals(other.lugar_dictado)) {
            return false;
        }
        if (this.fec_emision != other.fec_emision && (this.fec_emision == null || !this.fec_emision.equals(other.fec_emision))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.trabajador != null ? this.trabajador.hashCode() : 0);
        hash = 47 * hash + (this.denominacion != null ? this.denominacion.hashCode() : 0);
        hash = 47 * hash + (this.centro_estudios != null ? this.centro_estudios.hashCode() : 0);
        hash = 47 * hash + (this.horas != null ? this.horas.hashCode() : 0);
        hash = 47 * hash + (this.financiadoEntidad != null ? this.financiadoEntidad.hashCode() : 0);
        hash = 47 * hash + (this.lugar_dictado != null ? this.lugar_dictado.hashCode() : 0);
        hash = 47 * hash + (this.fec_emision != null ? this.fec_emision.hashCode() : 0);
        return hash;
    }
}
