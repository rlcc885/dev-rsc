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

@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpMenuPorPerfil",
    query = "CALL SP_MENUPORPERFIL(?,:in_perfil_id)",
    resultClass = MenuPorPerfil.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
@Entity
public class MenuPorPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MENU_ID")
    private long menuId;
    @Id
    @Basic(optional = false)
    @Column(name = "PERFIL_ID")
    private long perfilId;
    @Column(name = "DESCMENU")
    private String descmenu;
    @Column(name = "ACCESOSELECT")
    private long accesoselect;
    @Column(name = "ACCESOUPDATE")
    private long accesoupdate;
    @Column(name = "ACCESOINSERT")
    private long accesoinsert;
    @Column(name = "ACCESODELETE")
    private long accesodelete;
    @Column(name = "ROWNUM")
    private long rownum;

    public MenuPorPerfil() {
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

    public String getDescmenu() {
        return descmenu;
    }

    public void setDescmenu(String descmenu) {
        this.descmenu = descmenu;
    }

    public long getRownum() {
        return rownum;
    }

    public void setRownum(long rownum) {
        this.rownum = rownum;
    }
    
}