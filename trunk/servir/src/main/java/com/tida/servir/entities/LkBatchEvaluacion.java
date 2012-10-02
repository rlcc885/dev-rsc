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
public class LkBatchEvaluacion implements Serializable {
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
    private String cod_und_organica; // Código de la unidad organica

    public String getCod_und_organica() {
        return cod_und_organica;
    }

    public void setCod_und_organica(String cod_und_organica) {
        this.cod_und_organica = cod_und_organica;
    }
    
         public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFec_desde() {
        return fec_desde;
    }

    public void setFec_desde(Date fec_desde) {
        this.fec_desde = fec_desde;
    }

    public Date getFec_hasta() {
        return fec_hasta;
    }

    public void setFec_hasta(Date fec_hasta) {
        this.fec_hasta = fec_hasta;
    }

    public String getMotivoevaluacion() {
        return motivoevaluacion;
    }

    public void setMotivoevaluacion(String motivoevaluacion) {
        this.motivoevaluacion = motivoevaluacion;
    }

    public String getTipoevaluacion() {
        return tipoevaluacion;
    }

    public void setTipoevaluacion(String tipoevaluacion) {
        this.tipoevaluacion = tipoevaluacion;
    }
    private String tipoevaluacion;
    private String motivoevaluacion;
    private String calificacion;
    @Temporal(TemporalType.DATE)
    private Date fec_desde;
    @Temporal(TemporalType.DATE)
    private Date fec_hasta;
    
    public LkBatchEvaluacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}