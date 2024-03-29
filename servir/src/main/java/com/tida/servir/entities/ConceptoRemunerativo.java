package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * Conceptos Remunerativos
 */
@Entity
@Table(name = "RSC_CONCEPTOREMUNERATIVO")
//@Table(name="CON_REMUNERATIVOS")
public class ConceptoRemunerativo implements Serializable {

    @NonVisual
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_CONREMUNERATIVO_ID_SEQ", allocationSize = 1)
    private Long id;
    @NonVisual
    private String codigo;
    private String descripcion;
    private String sustento_legal;
    @ManyToOne
    private DatoAuxiliar tiporemuneracion;
    @ManyToOne
    private DatoAuxiliar tiporemuneracionstd;
    private Long entidad_id;
    @ManyToOne
    private DatoAuxiliar periodicidad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DatoAuxiliar getTiporemuneracion() {
        return tiporemuneracion;
    }

    public void setTiporemuneracion(DatoAuxiliar tiporemuneracion) {
        this.tiporemuneracion = tiporemuneracion;
    }

    public DatoAuxiliar getTiporemuneracionstd() {
        return tiporemuneracionstd;
    }

    public void setTiporemuneracionstd(DatoAuxiliar tiporemuneracionstd) {
        this.tiporemuneracionstd = tiporemuneracionstd;
    }

    public DatoAuxiliar getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(DatoAuxiliar periodicidad) {
        this.periodicidad = periodicidad;
    }

    public long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSustento_legal() {
        return sustento_legal;
    }

    public void setSustento_legal(String sustento_legal) {
        this.sustento_legal = sustento_legal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ConceptoRemunerativo other = (ConceptoRemunerativo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}