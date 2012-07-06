/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "MENUPERFIL")
@NamedQueries({
    @NamedQuery(name = "Menuperfil.findAll", query = "SELECT m FROM Menuperfil m"),
    @NamedQuery(name = "Menuperfil.findByMenuId", query = "SELECT m FROM Menuperfil m WHERE m.menuperfilPK.menuId = :menuId"),
    @NamedQuery(name = "Menuperfil.findByPerfilId", query = "SELECT m FROM Menuperfil m WHERE m.menuperfilPK.perfilId = :perfilId"),
    @NamedQuery(name = "Menuperfil.findByAccesoselect", query = "SELECT m FROM Menuperfil m WHERE m.accesoselect = :accesoselect"),
    @NamedQuery(name = "Menuperfil.findByAccesoupdate", query = "SELECT m FROM Menuperfil m WHERE m.accesoupdate = :accesoupdate"),
    @NamedQuery(name = "Menuperfil.findByAccesoinsert", query = "SELECT m FROM Menuperfil m WHERE m.accesoinsert = :accesoinsert"),
    @NamedQuery(name = "Menuperfil.findByAccesodelete", query = "SELECT m FROM Menuperfil m WHERE m.accesodelete = :accesodelete")})
public class Menuperfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MenuperfilPK menuperfilPK;
    @Column(name = "ACCESOSELECT")
    private boolean accesoselect;
    @Column(name = "ACCESOUPDATE")
    private boolean accesoupdate;
    @Column(name = "ACCESOINSERT")
    private boolean accesoinsert;
    @Column(name = "ACCESODELETE")
    private boolean accesodelete;

    public Menuperfil() {
    }

    public Menuperfil(MenuperfilPK menuperfilPK) {
        this.menuperfilPK = menuperfilPK;
    }

    public Menuperfil(Long menuId, Long perfilId) {
        this.menuperfilPK = new MenuperfilPK(menuId, perfilId);
    }

    public MenuperfilPK getMenuperfilPK() {
        return menuperfilPK;
    }

    public void setMenuperfilPK(MenuperfilPK menuperfilPK) {
        this.menuperfilPK = menuperfilPK;
    }

    public boolean getAccesoselect() {
        return accesoselect;
    }

    public void setAccesoselect(boolean accesoselect) {
        this.accesoselect = accesoselect;
    }

    public boolean getAccesoupdate() {
        return accesoupdate;
    }

    public void setAccesoupdate(boolean accesoupdate) {
        this.accesoupdate = accesoupdate;
    }

    public boolean getAccesoinsert() {
        return accesoinsert;
    }

    public void setAccesoinsert(boolean accesoinsert) {
        this.accesoinsert = accesoinsert;
    }

    public boolean getAccesodelete() {
        return accesodelete;
    }

    public void setAccesodelete(boolean accesodelete) {
        this.accesodelete = accesodelete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuperfilPK != null ? menuperfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menuperfil)) {
            return false;
        }
        Menuperfil other = (Menuperfil) object;
        if ((this.menuperfilPK == null && other.menuperfilPK != null) || (this.menuperfilPK != null && !this.menuperfilPK.equals(other.menuperfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Menuperfil[ menuperfilPK=" + menuperfilPK + " ]";
    }
    
}
