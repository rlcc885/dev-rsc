/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

//import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Arson
 */
@Embeddable
public class SancionRegimenPK implements Serializable {
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ID_TIPO_SANCION")
    private Long idTipoSancion;
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ID_REG_LABORAL")
    private Long idRegLaboral;

    public SancionRegimenPK() {
    }

    public SancionRegimenPK(Long idTipoSancion, Long idRegLaboral) {
        this.idTipoSancion = idTipoSancion;
        this.idRegLaboral = idRegLaboral;
    }

    public Long getIdTipoSancion() {
        return idTipoSancion;
    }

    public void setIdTipoSancion(Long idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }

    public Long getIdRegLaboral() {
        return idRegLaboral;
    }

    public void setIdRegLaboral(Long idRegLaboral) {
        this.idRegLaboral = idRegLaboral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSancion != null ? idTipoSancion.hashCode() : 0);
        hash += (idRegLaboral != null ? idRegLaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SancionRegimenPK)) {
            return false;
        }
        SancionRegimenPK other = (SancionRegimenPK) object;
        if ((this.idTipoSancion == null && other.idTipoSancion != null) || (this.idTipoSancion != null && !this.idTipoSancion.equals(other.idTipoSancion))) {
            return false;
        }
        if ((this.idRegLaboral == null && other.idRegLaboral != null) || (this.idRegLaboral != null && !this.idRegLaboral.equals(other.idRegLaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.SancionRegimenPK[ idTipoSancion=" + idTipoSancion + ", idRegLaboral=" + idRegLaboral + " ]";
    }
    
}
