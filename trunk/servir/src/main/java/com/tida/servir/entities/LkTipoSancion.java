/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mallco
 */
@Entity
@Table(name = "LKTIPOSANCION")
public class LkTipoSancion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String descripcion;
    private String categoria;
    @Column(name = "TIPOINHABILITACION")
    private String tipoinhabilitacion;
    @Column(name = "CATEGORIA_ID")
    private Long categoriaId;
    @Column(name = "TIPOINHABILITACION_ID")
    private Long tipoinhabilitacionId;
    private Integer anios;
    private Integer meses;
    private Integer dias;
    @Column(name = "OPC_ELIMINAR")
    private Boolean opcEliminar;

    public Boolean getOpcEliminar() {
        return opcEliminar;
    }

    public void setOpcEliminar(Boolean opcEliminar) {
        this.opcEliminar = opcEliminar;
    }
    
    public LkTipoSancion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoinhabilitacion() {
        return tipoinhabilitacion;
    }

    public void setTipoinhabilitacion(String tipoinhabilitacion) {
        this.tipoinhabilitacion = tipoinhabilitacion;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getTipoinhabilitacionId() {
        return tipoinhabilitacionId;
    }

    public void setTipoinhabilitacionId(Long tipoinhabilitacionId) {
        this.tipoinhabilitacionId = tipoinhabilitacionId;
    }

    public Integer getAnios() {
        return anios;
    }

    public void setAnios(Integer anios) {
        this.anios = anios;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
    
}
