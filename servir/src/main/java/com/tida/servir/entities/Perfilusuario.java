/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "RSC_PERFILUSUARIO")
@NamedQueries({
    @NamedQuery(name = "Perfilusuario.findAll", query = "SELECT p FROM Perfilusuario p"),
    @NamedQuery(name = "Perfilusuario.findByUsuarioId", query = "SELECT p FROM Perfilusuario p WHERE p.perfilusuarioPK.usuarioId = :usuarioId"),
    @NamedQuery(name = "Perfilusuario.findByPerfilId", query = "SELECT p FROM Perfilusuario p WHERE p.perfilusuarioPK.perfilId = :perfilId"),
    @NamedQuery(name = "Perfilusuario.findByFechacreacion", query = "SELECT p FROM Perfilusuario p WHERE p.fechacreacion = :fechacreacion")})
public class Perfilusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PerfilusuarioPK perfilusuarioPK;
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

    public Perfilusuario() {
    }

    public Perfilusuario(PerfilusuarioPK perfilusuarioPK) {
        this.perfilusuarioPK = perfilusuarioPK;
    }

    public Perfilusuario(Long usuarioId, Long perfilId) {
        this.perfilusuarioPK = new PerfilusuarioPK(usuarioId, perfilId);
    }

    public PerfilusuarioPK getPerfilusuarioPK() {
        return perfilusuarioPK;
    }

    public void setPerfilusuarioPK(PerfilusuarioPK perfilusuarioPK) {
        this.perfilusuarioPK = perfilusuarioPK;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilusuarioPK != null ? perfilusuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfilusuario)) {
            return false;
        }
        Perfilusuario other = (Perfilusuario) object;
        if ((this.perfilusuarioPK == null && other.perfilusuarioPK != null) || (this.perfilusuarioPK != null && !this.perfilusuarioPK.equals(other.perfilusuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Perfilusuario[ perfilusuarioPK=" + perfilusuarioPK + " ]";
    }
    
}
