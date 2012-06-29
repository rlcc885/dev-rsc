package com.tida.servir.entities;

/**
 *
 * @author Jurguen Zambrano
 */
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

/*
 * @NamedNativeQueries({ @NamedNativeQuery( name = "callSpUsuarioAcceso",
 * query="select
 * trabajador_id,nrodocumento,id,descmenu,nivel,menu_id,accesoselect,accesoupdate,accesodelete,accesoreport
 * from USUARIOACCESO where nrodocumento=:in_nrodocumento and nivel=:in_nivel",
 * resultClass = UsuarioAcceso.class, ) })
 */

@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpUsuarioAcceso",
    query = "CALL SP_USUARIOACCESO(?,:in_nrodocumento,:in_menuid,:in_pagename)",
    resultClass = UsuarioAcceso.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})

@Entity
public class UsuarioAcceso implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "TRABAJADOR_ID")
    private BigInteger trabajadorId;
    @Column(name = "NRODOCUMENTO")
    private String nrodocumento;
    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "DESCMENU")
    private String descmenu;
    @Column(name = "PAGENAME")
    private String pagename;
    @Column(name = "NIVEL")
    private Short nivel;
    @Column(name = "MENU_ID")
    private BigInteger menuId;
    @Column(name = "ACCESOSELECT")
    private Short accesoselect;
    @Column(name = "ACCESOUPDATE")
    private Short accesoupdate;
    @Column(name = "ACCESODELETE")
    private Short accesodelete;
    @Column(name = "ACCESOREPORT")
    private Short accesoreport;
    @Column(name = "HIJO")
    private Short hijo;
    @Column(name = "ACTIVO")
    private Short activo;
    @Column(name = "ROL_ID")
    private long rolid;

    public UsuarioAcceso() {
    }

    public BigInteger getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(BigInteger trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescmenu() {
        return descmenu;
    }

    public void setDescmenu(String descmenu) {
        this.descmenu = descmenu;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public Short getNivel() {
        return nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public Short getAccesoselect() {
        return accesoselect;
    }

    public void setAccesoselect(Short accesoselect) {
        this.accesoselect = accesoselect;
    }

    public Short getAccesoupdate() {
        return accesoupdate;
    }

    public void setAccesoupdate(Short accesoupdate) {
        this.accesoupdate = accesoupdate;
    }

    public Short getAccesodelete() {
        return accesodelete;
    }

    public void setAccesodelete(Short accesodelete) {
        this.accesodelete = accesodelete;
    }

    public Short getAccesoreport() {
        return accesoreport;
    }

    public void setAccesoreport(Short accesoreport) {
        this.accesoreport = accesoreport;
    }

    public Short getHijo() {
        return hijo;
    }

    public void setHijo(Short hijo) {
        this.hijo = hijo;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public long getRolid() {
        return rolid;
    }

    public void setRolid(Short rolid) {
        this.rolid = rolid;
    }
    
}
