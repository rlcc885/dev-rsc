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
@Table(name = "RSC_ESTUDIOS")
public class Estudios {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @GeneratedValue
    @NonVisual
    private Long id;
//    @ManyToOne(optional = false)    
    private String denominacion;   
    @ManyToOne
    private DatoAuxiliar tipoestudio;
    @ManyToOne
    private DatoAuxiliar centroestudio;
    private String otrocentroestudio;
    @ManyToOne    
    private DatoAuxiliar pais;
    @ManyToOne
    private DatoAuxiliar departamento;
    @ManyToOne
    private DatoAuxiliar provincia;
    @ManyToOne
    private DatoAuxiliar distrito;
    private String colegio;
    private String colegiatura;
    private Boolean estudiando;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    private Boolean agregadotrabajador;
    private Boolean validado;
    private Trabajador trabajador;
    private Entidad entidad;
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
    public DatoAuxiliar getTipoestudio() {
        return tipoestudio;
    }

    public void setTipoestudio(DatoAuxiliar tipoestudio) {
        this.tipoestudio = tipoestudio;
    }
    
    public DatoAuxiliar getCentroestudio() {
        return centroestudio;
    }

    public void setOtrocentroestudio(DatoAuxiliar centroestudio) {
        this.centroestudio = centroestudio;
    }
    
    public String getOtrocentroestudio() {
        return otrocentroestudio;
    }
   
    public DatoAuxiliar getPais() {
        return pais;
    }

    public void setPais(DatoAuxiliar pais) {
        this.pais = pais;
    }

    public DatoAuxiliar getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DatoAuxiliar departamento) {
        this.departamento = departamento;
    }
    
    public DatoAuxiliar getProvincia() {
        return provincia;
    }

    public void setProvincia(DatoAuxiliar provincia) {
        this.provincia = provincia;
    }
    
    public DatoAuxiliar getDistrito() {
        return distrito;
    }

    public void setDistrito(DatoAuxiliar distrito) {
        this.distrito = distrito;
    }
    
    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    } 
    
    public String getColegiatura() {
        return colegiatura;
    }

    public void setColegiatura(String colegiatura) {
        this.colegiatura = colegiatura;
    } 
    
    public Boolean getEstudiando() {
        return estudiando;
    }

    public void setEstudiando(Boolean estudiando) {
        this.estudiando = estudiando;
    }   
    
    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }   
    
    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }     
    
    public Boolean getAgregadotrabajador() {
        return agregadotrabajador;
    }

    public void setAgregadotrabajador(Boolean agregadotrabajador) {
        this.agregadotrabajador = agregadotrabajador;

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
    
}
