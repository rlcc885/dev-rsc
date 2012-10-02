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
public class LkBatchCargoAsignado implements Serializable {
    @Id
    @NonVisual
    private long id;

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

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Date getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(Date fec_fin) {
        this.fec_fin = fec_fin;
    }

    public Date getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(Date fec_inicio) {
        this.fec_inicio = fec_inicio;
    }

    public String getMotivo_cese() {
        return motivo_cese;
    }

    public void setMotivo_cese(String motivo_cese) {
        this.motivo_cese = motivo_cese;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getTipovinculo() {
        return tipovinculo;
    }

    public void setTipovinculo(String tipovinculo) {
        this.tipovinculo = tipovinculo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }    
    
    private Long entidadid;
    private String tipodocumento;
    private String nrodocumento;
    private String cod_und_organica;
    private String cod_cargo;
    private String tipovinculo;
    @Temporal(TemporalType.DATE)
    private Date fec_inicio;
    @Temporal(TemporalType.DATE)
    private Date fec_fin;
    private String motivo_cese;
    private Boolean estado;
    
    
    public LkBatchCargoAsignado() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}