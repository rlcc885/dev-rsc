/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Jurguen Zambrano
 */
@Embeddable
public class MenuperfilPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "MENU_ID")
    private BigInteger menuId;
    @Basic(optional = false)
    @Column(name = "PERFIL_ID")
    private BigInteger perfilId;

    public MenuperfilPK() {
    }

    public MenuperfilPK(BigInteger menuId, BigInteger perfilId) {
        this.menuId = menuId;
        this.perfilId = perfilId;
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public BigInteger getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(BigInteger perfilId) {
        this.perfilId = perfilId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        hash += (perfilId != null ? perfilId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuperfilPK)) {
            return false;
        }
        MenuperfilPK other = (MenuperfilPK) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        if ((this.perfilId == null && other.perfilId != null) || (this.perfilId != null && !this.perfilId.equals(other.perfilId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.MenuperfilPK[ menuId=" + menuId + ", perfilId=" + perfilId + " ]";
    }
    
}
