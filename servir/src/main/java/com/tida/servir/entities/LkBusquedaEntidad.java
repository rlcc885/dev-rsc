/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBusquedaEntidad implements Serializable {
    @Id
    private long id;
    private String denominacion;
    private String cue_entidad;
    private String nivelgobierno;
    private String sectorgobierno;
    private String organizacionestado;
    private String tipoorganismo;
    private String estado;
    private String departamento;
    private String provincia;
    private String distrito;
    
    
    public LkBusquedaEntidad() {
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
    
    public String getCue_entidad() {
        return cue_entidad;
    }

    public void setCue_entidad(String cue_entidad) {
        this.cue_entidad = cue_entidad;
    }
        
    public String getNivelgobierno() {
        return nivelgobierno;
    }

    public void setNivelgobierno(String nivelgobierno) {
        this.nivelgobierno = nivelgobierno;
    }
        
    public String getSectorgobierno() {
        return sectorgobierno;
    }

    public void setSectorgobierno(String sectorgobierno) {
        this.sectorgobierno = sectorgobierno;
    }
    
    public String getOrganizacionestado() {
        return organizacionestado;
    }

    public void setOrganizacionestado(String organizacionestado) {
        this.organizacionestado = organizacionestado;
    }
      public String getTipoorganismo() {
        return tipoorganismo;
    }

    public void setTipoorganismo(String tipoorganismo) {
        this.tipoorganismo = tipoorganismo;
    }
      public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
      public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
      public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
      public String geDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
   
}
