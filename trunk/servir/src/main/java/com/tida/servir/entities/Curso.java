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
@Table(name = "RSC_CURSO")
public class Curso implements Serializable {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @NonVisual
    private Long id;
//    @ManyToOne(optional = false)    
    private String denominacion;     
    @NonVisual
    private DatoAuxiliar tipoestudio;    
    @NonVisual
    private DatoAuxiliar centroestudio;
    private String otrocentroestudio;
    private Boolean estudiando;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    private Boolean agregadotrabajador;
    private Boolean validado;
    private Boolean fueradelpais;
    @NonVisual
    private Trabajador trabajador;
    @NonVisual
    private Entidad entidad;
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    @ManyToOne
    public DatoAuxiliar getTipoestudio() {
        return tipoestudio;
    }

    public void setTipoestudio(DatoAuxiliar tipoestudio) {
        this.tipoestudio = tipoestudio;
    }
    
    @ManyToOne
    public DatoAuxiliar getCentroestudio() {
        return centroestudio;
    }

    public void setCentroestudio(DatoAuxiliar centroestudio) {
        this.centroestudio = centroestudio;
    }
    
    public String getOtrocentroestudio() {
        return otrocentroestudio;
    }
    
    public void setOtrocentroestudio(String otrocentroestudio) {
        this.otrocentroestudio = otrocentroestudio;
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
    
    public Boolean getFueradelpais() {
        return fueradelpais;
    }

    public void setFueradelpais(Boolean fueradelpais) {
        this.fueradelpais = fueradelpais;
    }

    @ManyToOne
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    @ManyToOne
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
        final Curso other = (Curso) obj;
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

    
