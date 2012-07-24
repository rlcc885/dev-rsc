package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_EVALUACION")
public class EvaluacionPersonal {

    @NonVisual
    private Long id;
    @Validate("required")
    private DatoAuxiliar tipoevaluacion;
    @Validate("required")
    private DatoAuxiliar motivoevaluacion;
    @Validate("required")
    private String calificacion;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fec_desde;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fec_hasta;
    private CargoAsignado cargoasignado;
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoevaluacion() {
        return tipoevaluacion;
    }

    public void setTipoevaluacion(DatoAuxiliar tipoevaluacion) {
        this.tipoevaluacion = tipoevaluacion;
    }
    
    @ManyToOne
    public DatoAuxiliar getMotivoevaluacion() {
        return motivoevaluacion;
    }

    public void setMotivoevaluacion(DatoAuxiliar motivoevaluacion) {
        this.motivoevaluacion = motivoevaluacion;
    }
    
    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        if (calificacion != null) {
            this.calificacion = calificacion.trim().toUpperCase();
        }
    }
    
    
    public Date getFec_desde() {
        return fec_desde;
    }

    public void setFec_desde(Date fec_desde) {
        this.fec_desde = fec_desde;
    }
    
    public Date getFec_hasta() {
        return fec_hasta;
    }

    public void setFec_hasta(Date fec_hasta) {
         this.fec_hasta = fec_hasta;
    }


    @ManyToOne(optional = false)
    public CargoAsignado getCargoasignado() {
        return cargoasignado;
    }

    public void setCargoasignado(CargoAsignado cargoasignado) {
        this.cargoasignado = cargoasignado;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EvaluacionPersonal other = (EvaluacionPersonal) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
