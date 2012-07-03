/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "DESCMENU")
    private String descmenu;
    @Column(name = "NIVEL")
    private Short nivel;
    @Column(name = "PAGENAME")
    private String pagename;
    @Column(name = "ORDEN")
    private Short orden;
    
//    @OneToMany(mappedBy = "menuId")
//    private List<Menu> menuList;
    
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
    @ManyToOne
    private Menu menuId;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
//    private List<Menuperfil> menuperfilList;

    public Menu() {
    }

    public Menu(BigDecimal id) {
        this.id = id;
    }

    public Menu(BigDecimal id, String descmenu) {
        this.id = id;
        this.descmenu = descmenu;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescmenu() {
        return descmenu;
    }

    public void setDescmenu(String descmenu) {
        this.descmenu = descmenu;
    }

    public Short getNivel() {
        return nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

//    public List<Menu> getMenuList() {
//        return menuList;
//    }
//
//    public void setMenuList(List<Menu> menuList) {
//        this.menuList = menuList;
//    }

    public Menu getMenuId() {
        return menuId;
    }

    public void setMenuId(Menu menuId) {
        this.menuId = menuId;
    }

//    public List<Menuperfil> getMenuperfilList() {
//        return menuperfilList;
//    }
//
//    public void setMenuperfilList(List<Menuperfil> menuperfilList) {
//        this.menuperfilList = menuperfilList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Menu[ id=" + id + " ]";
    }
    
}
