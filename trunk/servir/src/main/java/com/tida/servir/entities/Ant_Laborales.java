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
@Table(name = "RSC_ANTECEDENTELABORAL")
public class Ant_Laborales implements Serializable {

    @NonVisual
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_ANTELABORAL_ID_SEQ", allocationSize = 1)
    private Long id;
    private String empresa;
    private String cargo;
    private String funcion;
    private String motivocese;
    @Temporal(TemporalType.DATE)
    private Date fec_ingreso;
    @Temporal(TemporalType.DATE)
    private Date fec_egreso;
    private Boolean agregadoTrabajador;
    private Boolean validado;
    @ManyToOne(optional = false)
    private Trabajador trabajador;
    @ManyToOne(optional = false)
    private Entidad entidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        if (empresa != null) {
            this.empresa = empresa.trim();
        }
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getMotivocese() {
        return motivocese;
    }

    public void setMotivocese(String motivocese) {
        this.motivocese = motivocese;
    }

    public Date getFec_egreso() {
        return fec_egreso;
    }

    public void setFec_egreso(Date fec_egreso) {
        this.fec_egreso = fec_egreso;
    }

    public Date getFec_ingreso() {
        return fec_ingreso;
    }

    public void setFec_ingreso(Date fec_ingreso) {
        this.fec_ingreso = fec_ingreso;
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
        final Ant_Laborales other = (Ant_Laborales) obj;
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
