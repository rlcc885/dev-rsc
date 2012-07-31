/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_MERITODEMERITO")
public class MeritoDemerito {
    @Id
    @GeneratedValue
    @NonVisual
    private Long id;
    @ManyToOne
    private DatoAuxiliar clasemeritodemerito;
    @ManyToOne
    private DatoAuxiliar tipomeritodemerito;
    @Validate("required")
    private String motivo; 
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fecha; 
    @ManyToOne
    private Entidad entidad;
    @ManyToOne
    private Trabajador trabajador;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
        public DatoAuxiliar getClasemeritodemerito() {
        return clasemeritodemerito;
    }

    public void setClasemeritodemerito(DatoAuxiliar clasemeritodemerito) {
        this.clasemeritodemerito = clasemeritodemerito;
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

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
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
