/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_MERITODEMERITO")
public class MeritoDemerito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_MERITODEMERITO_ID_SEQ", allocationSize = 1)
    @NonVisual
    private Long id;
    private Long clasemeritodemerito_id;
    @ManyToOne
    private DatoAuxiliar tipomeritodemerito;
    @ManyToOne
    private DatoAuxiliar tipodocumento;
    @Validate("required")
    private String motivo;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Long entidad_id;
    private Long trabajador_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DatoAuxiliar getTipomeritodemerito() {
        return tipomeritodemerito;
    }

    public void setTipomeritodemerito(DatoAuxiliar tipomeritodemerito) {
        this.tipomeritodemerito = tipomeritodemerito;
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

    public DatoAuxiliar getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(DatoAuxiliar tipodocumento) {
        this.tipodocumento = tipodocumento;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MeritoDemerito other = (MeritoDemerito) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
