package com.tida.servir.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_REMUNERACIONPERSONAL")
public class RemuneracionPersonal {

    @NonVisual
    private Long id;
    @Validate("required")
    private String importe;
    @Validate("required")
    private ConceptoRemunerativo conceptoRemunerativo;
    private CargoAsignado cargoAsignado;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public ConceptoRemunerativo getConceptoRemunerativo() {
        return conceptoRemunerativo;
    }

    public void setConceptoRemunerativo(ConceptoRemunerativo conceptoRemunerativo) {
        this.conceptoRemunerativo = conceptoRemunerativo;
    }
    
    @ManyToOne
    public CargoAsignado getCargoAsignado() {
        return cargoAsignado;
    }

    public void setCargoAsignado(CargoAsignado cargoAsignado) {
        this.cargoAsignado = cargoAsignado;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RemuneracionPersonal other = (RemuneracionPersonal) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
