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

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_TITULO")
public class Titulo {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @NonVisual
    private Long id;
//    @ManyToOne(optional = false)
    private Trabajador trabajador;
    private String nivel;
    private String denominacion;
    private String especialidad;
    private String centro_estudios;
    private String lugar_emision;
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
    
    

    public String getCentro_estudios() {
        return centro_estudios;
    }

    public void setCentro_estudios(String centro_estudios) {
        this.centro_estudios = centro_estudios;
    }

    public String getColegio_profesional() {
        return colegio_profesional;
    }

    public void setColegio_profesional(String colegio_profesional) {
        this.colegio_profesional = colegio_profesional;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
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

    public void setFec_emision(Date fec_emision) {
        this.fec_emision = fec_emision;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNum_colegiatura() {
        return num_colegiatura;
    }

    public void setNum_colegiatura(String num_colegiatura) {
        this.num_colegiatura = num_colegiatura;
    }

    @ManyToOne(optional = false)
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    @Temporal(TemporalType.DATE)
    private Date fec_emision;
    private String colegio_profesional;
    private String num_colegiatura;
}
