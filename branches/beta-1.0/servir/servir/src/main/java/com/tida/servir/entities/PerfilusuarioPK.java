/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Jurguen Zambrano
 */
@Embeddable
public class PerfilusuarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    @Basic(optional = false)
    @Column(name = "PERFIL_ID")
    private Long perfilId;

    public PerfilusuarioPK() {
    }

    public PerfilusuarioPK(Long usuarioId, Long perfilId) {
        this.usuarioId = usuarioId;
        this.perfilId = perfilId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        hash += (perfilId != null ? perfilId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilusuarioPK)) {
            return false;
        }
        PerfilusuarioPK other = (PerfilusuarioPK) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        if ((this.perfilId == null && other.perfilId != null) || (this.perfilId != null && !this.perfilId.equals(other.perfilId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.PerfilusuarioPK[ usuarioId=" + usuarioId + ", perfilId=" + perfilId + " ]";
    }
    
}
