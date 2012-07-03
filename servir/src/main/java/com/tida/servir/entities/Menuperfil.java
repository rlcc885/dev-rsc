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
    private long accesoselect;
    @Column(name = "ACCESOUPDATE")
    private long accesoupdate;
    @Column(name = "ACCESOINSERT")
    private long accesoinsert;
    @Column(name = "ACCESODELETE")
    private long accesodelete;

    public Menuperfil() {
    }

    public long getAccesoselect() {
        return accesoselect;
    }

    public void setAccesoselect(long accesoselect) {
        this.accesoselect = accesoselect;
    }

    public long getAccesoupdate() {
        return accesoupdate;
    }

    public void setAccesoupdate(long accesoupdate) {
        this.accesoupdate = accesoupdate;
    }

    public long getAccesoinsert() {
        return accesoinsert;
    }

    public void setAccesoinsert(long accesoinsert) {
        this.accesoinsert = accesoinsert;
    }

    public long getAccesodelete() {
        return accesodelete;
    }

    public void setAccesodelete(long accesodelete) {
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
