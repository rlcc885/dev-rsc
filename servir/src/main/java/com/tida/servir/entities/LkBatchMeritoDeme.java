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
public class LkBatchMeritoDeme implements Serializable {
    @Id
    @NonVisual
    private long id; 

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Long trabajador_id) {
        this.trabajador_id = trabajador_id;
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
    
    private String tipodocumento;
    private String nrodocumento;
    private Long entidadid;
    private Long trabajador_id;
    @Temporal(TemporalType.DATE)
    private Date fecha;  
    private String motivo;
    private String clasemeritodemerito;
    private String tipomeritodemerito;
    private String tipodocumentomd;

    public String getClasemeritodemerito() {
        return clasemeritodemerito;
    }

    public void setClasemeritodemerito(String clasemeritodemerito) {
        this.clasemeritodemerito = clasemeritodemerito;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipodocumentomd() {
        return tipodocumentomd;
    }

    public void setTipodocumentomd(String tipodocumentomd) {
        this.tipodocumentomd = tipodocumentomd;
    }

    public String getTipomeritodemerito() {
        return tipomeritodemerito;
    }

    public void setTipomeritodemerito(String tipomeritodemerito) {
        this.tipomeritodemerito = tipomeritodemerito;
    }
    
    public LkBatchMeritoDeme() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}