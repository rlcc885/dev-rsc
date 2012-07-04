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
@Table(name = "MENU")
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id"),
    @NamedQuery(name = "Menu.findByDescmenu", query = "SELECT m FROM Menu m WHERE m.descmenu = :descmenu"),
    @NamedQuery(name = "Menu.findByNivel", query = "SELECT m FROM Menu m WHERE m.nivel = :nivel"),
    @NamedQuery(name = "Menu.findByPagename", query = "SELECT m FROM Menu m WHERE m.pagename = :pagename"),
    @NamedQuery(name = "Menu.findByOrden", query = "SELECT m FROM Menu m WHERE m.orden = :orden")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpMenuSinAsignarPorPerfil",
    query = "CALL SP_MENUSINASIGNARPORPERFIL(?,:in_perfil_id)",
    resultClass = Menu.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private long id;
    @Basic(optional = false)
    @Column(name = "DESCMENU")
    private String descmenu;
    @Column(name = "NIVEL")
    private long nivel;
    @Column(name = "PAGENAME")
    private String pagename;
    @Column(name = "ORDEN")
    private long orden;
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
    @ManyToOne
    private Menu menuId;

    public Menu() {
    }

    public Menu(long id) {
        this.id = id;
    }

    public Menu(long id, String descmenu) {
        this.id = id;
        this.descmenu = descmenu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescmenu() {
        return descmenu;
    }

    public void setDescmenu(String descmenu) {
        this.descmenu = descmenu;
    }

    public long getNivel() {
        return nivel;
    }

    public void setNivel(long nivel) {
        this.nivel = nivel;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public long getOrden() {
        return orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public Menu getMenuId() {
        return menuId;
    }

    public void setMenuId(Menu menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Menu[ id=" + id + " ]";
    }
}
