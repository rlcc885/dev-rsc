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
    
    public LkBatchCargoxUnidad() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}