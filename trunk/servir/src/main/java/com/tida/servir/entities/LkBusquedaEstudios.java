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
public class LkBusquedaEstudios implements Serializable {
    @Id
    @NonVisual
    private long id;
    private String denominacion;
    private String tipo_estudio;
    private String centro_estudio;
    private Boolean estado;
    @NonVisual
    private Boolean validado;
    @NonVisual
    private long trabajador; 

    
    public LkBusquedaEstudios() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
     public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
    public String getTipo_estudio() {
        return tipo_estudio;
    }

    public void setTipo_estudio(String tipo_estudio) {
        this.tipo_estudio = tipo_estudio;
    }
    
    public String getCentro_estudio() {
        return centro_estudio;
    }

    public void setCentro_estudio(String centro_estudio) {
        this.centro_estudio = centro_estudio;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }
    
    //@ManyToOne(optional = false)
    public long getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(long trabajador) {
        this.trabajador = trabajador;
    }
    
   
}
