package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_REMUNERACIONPERSONAL")
public class RemuneracionPersonal implements Serializable {

    @NonVisual
    private Long id;
    @Validate("required")
    private String importe;
    @Validate("required")
    private Long conceptoremunerativo_id;
    private Long cargoasignado_id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_REMPERSONAL_ID_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getConceptoremunerativo_id() {
        return conceptoremunerativo_id;
    }

    public void setConceptoremunerativo_id(long conceptoremunerativo_id) {
        this.conceptoremunerativo_id = conceptoremunerativo_id;
    }

    public long getCargoasignado_id() {
        return cargoasignado_id;
    }

    public void setCargoasignado_id(long cargoasignado_id) {
        this.cargoasignado_id = cargoasignado_id;
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
