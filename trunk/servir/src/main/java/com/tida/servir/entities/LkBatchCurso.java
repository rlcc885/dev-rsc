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
public class LkBatchCurso implements Serializable {
    @Id
    @NonVisual
    private long id;

    public String getCentroestudio() {
        return centroestudio;
    }

    public void setCentroestudio(String centroestudio) {
        this.centroestudio = centroestudio;
    }

    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }    

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Boolean getEstudiando() {
        return estudiando;
    }

    public void setEstudiando(Boolean estudiando) {
        this.estudiando = estudiando;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getOtrocentroestudio() {
        return otrocentroestudio;
    }

    public void setOtrocentroestudio(String otrocentroestudio) {
        this.otrocentroestudio = otrocentroestudio;
    }
 
    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getTipocurso() {
        return tipocurso;
    }

    public void setTipocurso(String tipocurso) {
        this.tipocurso = tipocurso;
    }

    public Long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Long trabajador_id) {
        this.trabajador_id = trabajador_id;
    }
    private String tipodocumento;
    private String nrodocumento;
    private Long entidadid;
    private Long trabajador_id;
    private String denominacion; 
    private String tipocurso;    
    private String centroestudio;
    private String otrocentroestudio;    
    private Boolean estudiando;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Temporal(TemporalType.DATE)
    private Date fechafin;

    public Boolean getFueradelpais() {
        return fueradelpais;
    }

    public void setFueradelpais(Boolean fueradelpais) {
        this.fueradelpais = fueradelpais;
    }
    private Boolean fueradelpais;
    
    public LkBatchCurso() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}