package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author ale
 */
@Entity
public class LkBusquedaRemuneracion implements Serializable {

    private Long id;
    private String importe;
    private Long conceptoremunerativo_id;
    private Long cargoasignado_id;
    private String descripcion;

    @Id
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LkBusquedaRemuneracion other = (LkBusquedaRemuneracion) obj;
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