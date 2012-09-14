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
public class LkBatchConstancia implements Serializable {
    @Id
    @NonVisual
    private long id;

    public String getCod_cargo() {
        return cod_cargo;
    }

    public void setCod_cargo(String cod_cargo) {
        this.cod_cargo = cod_cargo;
    }

    public Long getCargoasignadoid() {
        return cargoasignadoid;
    }

    public void setCargoasignadoid(Long cargoasignadoid) {
        this.cargoasignadoid = cargoasignadoid;
    }

    public Date getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(Date fec_inicio) {
        this.fec_inicio = fec_inicio;
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


    private Long cargoasignadoid;
    private String tipodocumento;
    private String nrodocumento;
    private String cod_cargo;
    @Temporal(TemporalType.DATE)
    private Date fec_inicio;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public String getCategoriaconstancia() {
        return categoriaconstancia;
    }

    public void setCategoriaconstancia(String categoriaconstancia) {
        this.categoriaconstancia = categoriaconstancia;
    }

    public Boolean getEntrego() {
        return entrego;
    }

    public void setEntrego(Boolean entrego) {
        this.entrego = entrego;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getTipoconstancia() {
        return tipoconstancia;
    }

    public void setTipoconstancia(String tipoconstancia) {
        this.tipoconstancia = tipoconstancia;
    }
    private String categoriaconstancia;
    private String tipoconstancia;
    private Boolean obligatorio;
    private Boolean entrego;
    
    public LkBatchConstancia() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}