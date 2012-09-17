/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RNSDD_PERSONA")
public class Persona_Sancion implements Serializable {
    
    @Id    
    @Column(name = "ID")
    private long id;    
    @ManyToOne
    @Validate("required")
    private DatoAuxiliar documentoidentidad;

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getCargo_persona() {
        return cargo_persona;
    }

    public void setCargo_persona(String cargo_persona) {
        this.cargo_persona = cargo_persona;
    }
    
    @ManyToOne
    public DatoAuxiliar getDocumentoidentidad() {
        return documentoidentidad;
    }

    public void setDocumentoidentidad(DatoAuxiliar documentoidentidad) {
        this.documentoidentidad = documentoidentidad;
    }

    public String getEstado_cargo() {
        return estado_cargo;
    }

    public void setEstado_cargo(String estado_cargo) {
        this.estado_cargo = estado_cargo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }
    
    @ManyToOne
    public DatoAuxiliar getRegimenlaboral() {
        return regimenlaboral;
    }

    public void setRegimenlaboral(DatoAuxiliar regimenlaboral) {
        this.regimenlaboral = regimenlaboral;
    }

    public String getTiempo_servicio_anio() {
        return tiempo_servicio_anio;
    }

    public void setTiempo_servicio_anio(String tiempo_servicio_anio) {
        this.tiempo_servicio_anio = tiempo_servicio_anio;
    }

    public String getTiempo_servicio_dia() {
        return tiempo_servicio_dia;
    }

    public void setTiempo_servicio_dia(String tiempo_servicio_dia) {
        this.tiempo_servicio_dia = tiempo_servicio_dia;
    }

    public String getTiempo_servicio_mes() {
        return tiempo_servicio_mes;
    }

    public void setTiempo_servicio_mes(String tiempo_servicio_mes) {
        this.tiempo_servicio_mes = tiempo_servicio_mes;
    }
    @Validate("required")
    private String nroDocumento;
    @Validate("required")
    private String nombres;
    @Validate("required")
    private String apellidoPaterno;
    @Validate("required")
    private String apellidoMaterno;
    @ManyToOne
    @Validate("required")
    private DatoAuxiliar regimenlaboral;
    private String cargo_persona;
    private String tiempo_servicio_anio;
    private String tiempo_servicio_mes;
    private String tiempo_servicio_dia;
    private String estado_cargo;           
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Persona_Sancion other = (Persona_Sancion) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}
