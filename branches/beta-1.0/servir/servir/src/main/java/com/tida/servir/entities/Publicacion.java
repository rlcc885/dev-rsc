/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_PUBLICACION")
public class Publicacion implements Serializable {

    @NonVisual
    private Long id;  
    private DatoAuxiliar clasepublicacion;
    private DatoAuxiliar tipo;
    private String titulo;
    private String descripcion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private boolean agregadoTrabajador;
    private Boolean validado;
    
    private Entidad entidad;
    private Trabajador trabajador;
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne
    public DatoAuxiliar getClasePublicacion() {
        return clasepublicacion;
    }

    public void setClasePublicacion(DatoAuxiliar clasepublicacion) {
        this.clasepublicacion = clasepublicacion;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipo() {
        return tipo;
    }

    public void setTipo(DatoAuxiliar tipo) {
        this.tipo = tipo;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    
    }
    
    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    @ManyToOne(optional = false)
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    @ManyToOne(optional = false)
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
        final Publicacion other = (Publicacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
