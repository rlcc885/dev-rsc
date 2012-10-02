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
public class LkBatchCargoxUnidad implements Serializable {
    @Id
    @NonVisual
    private long id;
    private Long unidadorganicaid;
    private String cod_cargo;
    private String den_cargo;
    private String regimenlaboral;
    private Boolean presupuestado_PAP;
    private Boolean esorganico;
    private Integer ctd_puestos_total;
    private Boolean supervisapersonal;
    private Boolean dec_jurada_byr;
    private Boolean estado;
    private String grupoOcupacional;
    private String nivelRemunerativo;
    private String situacioncap;
    private String cod_und_organica;

    public String getCod_cargo() {
        return cod_cargo;
    }

    public void setCod_cargo(String cod_cargo) {
        this.cod_cargo = cod_cargo;
    }

    public String getCod_und_organica() {
        return cod_und_organica;
    }

    public void setCod_und_organica(String cod_und_organica) {
        this.cod_und_organica = cod_und_organica;
    }

    public String getDen_cargo() {
        return den_cargo;
    }

    public void setDen_cargo(String den_cargo) {
        this.den_cargo = den_cargo;
    }

    public Boolean getEsorganico() {
        return esorganico;
    }

    public void setEsorganico(Boolean esorganico) {
        this.esorganico = esorganico;
    }

    public String getGrupoOcupacional() {
        return grupoOcupacional;
    }

    public void setGrupoOcupacional(String grupoOcupacional) {
        this.grupoOcupacional = grupoOcupacional;
    }

    public String getNivelRemunerativo() {
        return nivelRemunerativo;
    }

    public void setNivelRemunerativo(String nivelRemunerativo) {
        this.nivelRemunerativo = nivelRemunerativo;
    }

    public Boolean getPresupuestado_PAP() {
        return presupuestado_PAP;
    }

    public void setPresupuestado_PAP(Boolean presupuestado_PAP) {
        this.presupuestado_PAP = presupuestado_PAP;
    }

    public String getRegimenlaboral() {
        return regimenlaboral;
    }

    public void setRegimenlaboral(String regimenlaboral) {
        this.regimenlaboral = regimenlaboral;
    }

    public String getSituacioncap() {
        return situacioncap;
    }

    public void setSituacioncap(String situacioncap) {
        this.situacioncap = situacioncap;
    }

    public Long getUnidadorganicaid() {
        return unidadorganicaid;
    }

    public void setUnidadorganicaid(Long unidadorganicaid) {
        this.unidadorganicaid = unidadorganicaid;
    }

    public Integer getCtd_puestos_total() {
        return ctd_puestos_total;
    }

    public void setCtd_puestos_total(Integer ctd_puestos_total) {
        this.ctd_puestos_total = ctd_puestos_total;
    }

    public Boolean getDec_jurada_byr() {
        return dec_jurada_byr;
    }

    public void setDec_jurada_byr(Boolean dec_jurada_byr) {
        this.dec_jurada_byr = dec_jurada_byr;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getSupervisapersonal() {
        return supervisapersonal;
    }

    public void setSupervisapersonal(Boolean supervisapersonal) {
        this.supervisapersonal = supervisapersonal;
    }
    
    
    
    public LkBatchCargoxUnidad() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}