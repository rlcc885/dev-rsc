/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBatchUnidadOrga implements Serializable {
    @Id
    @NonVisual
    private long id;

    public String getCategoriauo() {
        return categoriauo;
    }

    public void setCategoriauo(String categoriauo) {
        this.categoriauo = categoriauo;
    }

    public String getCod_ubi_dept() {
        return cod_ubi_dept;
    }

    public void setCod_ubi_dept(String cod_ubi_dept) {
        this.cod_ubi_dept = cod_ubi_dept;
    }

    public String getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(String cod_ubi_dist) {
        this.cod_ubi_dist = cod_ubi_dist;
    }

    public String getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(String cod_ubi_prov) {
        this.cod_ubi_prov = cod_ubi_prov;
    }

    public String getCod_und_organica() {
        return cod_und_organica;
    }

    public void setCod_und_organica(String cod_und_organica) {
        this.cod_und_organica = cod_und_organica;
    }

    public String getCodunidada() {
        return codunidada;
    }

    public void setCodunidada(String codunidada) {
        this.codunidada = codunidada;
    }

    public String getCue_entidad() {
        return cue_entidad;
    }

    public void setCue_entidad(String cue_entidad) {
        this.cue_entidad = cue_entidad;
    }

    public String getDen_und_organica() {
        return den_und_organica;
    }

    public void setDen_und_organica(String den_und_organica) {
        this.den_und_organica = den_und_organica;
    }

    public String getDescvia() {
        return descvia;
    }

    public void setDescvia(String descvia) {
        this.descvia = descvia;
    }

    public String getDesczona() {
        return desczona;
    }

    public void setDesczona(String desczona) {
        this.desczona = desczona;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipovia() {
        return tipovia;
    }

    public void setTipovia(String tipovia) {
        this.tipovia = tipovia;
    }

    public String getTipozona() {
        return tipozona;
    }

    public void setTipozona(String tipozona) {
        this.tipozona = tipozona;
    }
    private Long entidad;
    private String cod_und_organica;
    private String den_und_organica;
    private String descvia;
    private String cod_ubi_dist; //ubigeo distrito
    private String cod_ubi_prov; //ubigeo provincia
    private String cod_ubi_dept; //ubigeo depto
    private String sigla;
    private Integer nivel;
    private String categoriauo;
    private String tipovia;
    private String tipozona;
    private String desczona;
    private String codunidada;
    private String cue_entidad;

    
    public LkBatchUnidadOrga() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}