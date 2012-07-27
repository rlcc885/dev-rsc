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
public class LkBusquedaTrabajador implements Serializable {
    @Id
    private long id;
    private String nombretrabajador;
    private Boolean validado;
    private String entidad;
    private String unidadorganica;
    private String cargo;
    private String estadocargo;
    private String nrodocumento;
    private long entidad_id;

    public long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(long entidad_id) {
        this.entidad_id = entidad_id;
    }

    
    public LkBusquedaTrabajador() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getNombretrabajador() {
        return nombretrabajador;
    }

    public void setNombretrabajador(String nombretrabajador) {
        this.nombretrabajador = nombretrabajador;
    }
    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }
    
    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }
    public String getUnidadorganica() {
        return unidadorganica;
    }

    public void setUnidadorganica(String unidadorganica) {
        this.unidadorganica = unidadorganica;
    }
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getEstadocargo() {
        return estadocargo;
    }

    public void setEstadocargo(String estadocargo) {
        this.estadocargo = estadocargo;
    }
    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }
    
}
