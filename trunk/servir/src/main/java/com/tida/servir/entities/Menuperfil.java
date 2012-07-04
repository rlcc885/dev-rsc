/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "MENUPERFIL")
public class Menuperfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MENU_ID")
    private long menuId;
    @Basic(optional = false)
    @Column(name = "PERFIL_ID")
    private long perfilId;
    @Column(name = "ACCESOSELECT")
    private Boolean accesoselect;
    @Column(name = "ACCESOUPDATE")
    private Boolean accesoupdate;
    @Column(name = "ACCESOINSERT")
    private Boolean accesoinsert;
    @Column(name = "ACCESODELETE")
    private Boolean accesodelete;

    public Menuperfil() {
    }

    public Boolean getAccesoselect() {
        return accesoselect;
    }

    public void setAccesoselect(Boolean accesoselect) {
        this.accesoselect = accesoselect;
    }

    public Boolean getAccesoupdate() {
        return accesoupdate;
    }

    public void setAccesoupdate(Boolean accesoupdate) {
        this.accesoupdate = accesoupdate;
    }

    public Boolean getAccesoinsert() {
        return accesoinsert;
    }

    public void setAccesoinsert(Boolean accesoinsert) {
        this.accesoinsert = accesoinsert;
    }

    public Boolean getAccesodelete() {
        return accesodelete;
    }

    public void setAccesodelete(Boolean accesodelete) {
        this.accesodelete = accesodelete;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(long perfilId) {
        this.perfilId = perfilId;
    }

    
}
