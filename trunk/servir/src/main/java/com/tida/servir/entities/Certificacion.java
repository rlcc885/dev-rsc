/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ale
 */

@Entity
@Table(name = "RSC_CERTIFICACION")
public class Certificacion
{
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;

//    @ManyToOne(optional = false)
    private Trabajador trabajador;

    private String denominacion;
    private String especialidad;
    private String entidad_certificante;
    private String lugar_emision;

    @Temporal(TemporalType.DATE)
    private Date fec_emision;
    
    private Boolean agregadoTrabajador;
    private Boolean validado;

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    }




    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getEntidad_certificante() {
        return entidad_certificante;
    }

    public void setEntidad_certificante(String entidad_certificante) {
        this.entidad_certificante = entidad_certificante;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFec_emision() {
        return fec_emision;
    }

    public void setFec_emision(Date fecha_emision) {
        this.fec_emision = fecha_emision;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Certificacion other = (Certificacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLugar_emision() {
        return lugar_emision;
    }

    public void setLugar_emision(String lugar_emision) {
        this.lugar_emision = lugar_emision;
    }

    @ManyToOne(optional = false)
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }


    
}

