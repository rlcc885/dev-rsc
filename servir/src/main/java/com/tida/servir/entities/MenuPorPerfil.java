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
@Table(name = "RSC_MENUPORPERFIL")
@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpMenuPorPerfil",
    query = "CALL SP_MENUPORPERFIL(?,:in_perfil_id)",
    resultClass = MenuPorPerfil.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})

public class MenuPorPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "MENU_ID")
    private long menuId;
    @Column(name = "PERFIL_ID")
    private long perfilId;
    @Column(name = "DESCMENU")
    private String descmenu;
    @Column(name = "ACCESOSELECT")
    private boolean accesoselect;
    @Column(name = "ACCESOUPDATE")
    private boolean accesoupdate;
    @Column(name = "ACCESOINSERT")
    private boolean accesoinsert;
    @Column(name = "ACCESODELETE")
    private boolean accesodelete;
    @Column(name = "ROW_NUM")
    private long rownum;

    public MenuPorPerfil() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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