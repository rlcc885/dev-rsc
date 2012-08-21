package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * Conceptos Remunerativos
 */
@Entity
@Table(name = "RSC_CONCEPTOREMUNERATIVO")
//@Table(name="CON_REMUNERATIVOS")
public class ConceptoRemunerativo implements Serializable {
    public static List<String> PERIODICIDADES = Arrays.asList(new String[]{"Mensual", "Semestral", "Anual", "Única vez"});
    @NonVisual
    @Id
    @GeneratedValue
    private Long id;
//    private String clasificacion;
    private String codigo;
//    private String conceptoStd;
    private String descripcion;
//    private String periodicidad;
    private String sustento_legal;
    @ManyToOne
    private DatoAuxiliar tiporemuneracion;
    @ManyToOne
    private DatoAuxiliar tiporemuneracionstd;
    @ManyToOne
    private Entidad entidad;
    @ManyToOne
    private DatoAuxiliar periodicidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidadUE) {
        this.entidad = entidadUE;
    }

//    public String getClasificacion() {
//        return clasificacion;
//    }
//
//    public void setClasificacion(String clasificacion) {
//        this.clasificacion = clasificacion;
//    }

//    public String getConceptoStd() {
//        return conceptoStd;
//    }
//
//    public void setConceptoStd(String conceptoStd) {
//        this.conceptoStd = conceptoStd;
//    }
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

//    public String getPeriodicidad() {
//        return periodicidad;
//    }
//
//    public void setPeriodicidad(String periodicidad) {
//        this.periodicidad = periodicidad;
//    }
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