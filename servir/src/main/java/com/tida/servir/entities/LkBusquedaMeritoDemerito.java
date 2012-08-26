/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBusquedaMeritoDemerito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Long clasemeritodemerito_id;
    private Long tipomeritodemerito_id;
    private Long tipodocumento_id;
    private String motivo;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Long entidad_id;
    private Long trabajador_id;
    private String descclasemeritodemerito;
    private String desctipomeritodemerito;
    private String desctipodocumento;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTipomeritodemerito_id() {
        return tipomeritodemerito_id;
    }

    public void setTipomeritodemerito_id(long tipomeritodemerito_id) {
        this.tipomeritodemerito_id = tipomeritodemerito_id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getTipodocumento_id() {
        return tipodocumento_id;
    }

    public void setTipodocumento_id(long tipodocumento_id) {
        this.tipodocumento_id = tipodocumento_id;
    }

    public long getClasemeritodemerito_id() {
        return clasemeritodemerito_id;
    }

    public void setClasemeritodemerito_id(long clasemeritodemerito_id) {
        this.clasemeritodemerito_id = clasemeritodemerito_id;
    }

    public long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(long trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public String getDescclasemeritodemerito() {
        return descclasemeritodemerito;
    }

    public void setDescclasemeritodemerito(String descclasemeritodemerito) {
        this.descclasemeritodemerito = descclasemeritodemerito;
    }

    public String getDesctipodocumento() {
        return desctipodocumento;
    }

    public void setDesctipodocumento(String desctipodocumento) {
        this.desctipodocumento = desctipodocumento;
    }

    public String getDesctipomeritodemerito() {
        return desctipomeritodemerito;
    }

    public void setDesctipomeritodemerito(String desctipomeritodemerito) {
        this.desctipomeritodemerito = desctipomeritodemerito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkBusquedaMeritoDemerito)) {
            return false;
        }
        LkBusquedaMeritoDemerito other = (LkBusquedaMeritoDemerito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.LkBusquedaMeritoDemerito[ id=" + id + " ]";
    }
    
}
