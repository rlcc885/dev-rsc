/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jurguen
 */
@Entity
@Table(name = "RSC_ORGANIGRAMA")
//@NamedQueries({
//    @NamedQuery(name = "Organigrama.findAll", query = "SELECT o FROM Organigrama o")})
public class Organigrama implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ENTIDAD_ID")
    private Long entidadId;
    @Column(name = "NIVEL")
    private Integer nivel;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Column(name = "UNIDADORGANICA_ID")
    private Long unidadOrganicaId;

    public Organigrama() {
    }

    public long getUnidadorganicaId() {
        return unidadOrganicaId;
    }

    public void setUnidadorganicaId(long unidadorganicaId) {
        this.unidadOrganicaId = unidadorganicaId;
    }

    public Organigrama(Long id) {
        this.id = id;
    }

    public long getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(long entidadId) {
        this.entidadId = entidadId;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organigrama)) {
            return false;
        }
        Organigrama other = (Organigrama) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Organigrama[ id=" + id + " ]";
    }
}
