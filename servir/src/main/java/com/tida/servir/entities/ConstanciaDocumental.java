/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author LFL
 */
@Entity
@Table(name = "RSC_CONSTANCIADOCUMENTAL")
public class ConstanciaDocumental {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_CONSTANCIADOC_ID_SEQ", allocationSize = 1)
    @NonVisual
    private Long id;
    @ManyToOne
    private DatoAuxiliar categoriaconstancia;
    @ManyToOne
    private DatoAuxiliar tipoconstancia;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fecha; 
    @ManyToOne(optional = false)
    private Legajo legajo;    
    private Boolean obligatorio;
    @Validate("required")
    private Boolean entrego;
    @ManyToOne(optional = false)
    private CargoAsignado cargoasignado;
    
        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
        public DatoAuxiliar getCategoriaconstancia() {
        return categoriaconstancia;
    }

    public void setCategoriaconstancia(DatoAuxiliar categoriaconstancia) {
        this.categoriaconstancia = categoriaconstancia;
    }
 
        public DatoAuxiliar getTipoconstancia() {
        return tipoconstancia;
    }

    public void setTipoconstancia(DatoAuxiliar tipoconstancia) {
        this.tipoconstancia = tipoconstancia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


        public Legajo getLegajo() {
        return legajo;
    }

    public void setLegajo(Legajo legajo) {
        this.legajo = legajo;
    }

    
    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }
    
    public Boolean getEntrego() {
        return entrego;
    }

    public void setEntrego(Boolean entrego) {
        this.entrego = entrego;
    }
    @ManyToOne
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
        final ConstanciaDocumental other = (ConstanciaDocumental) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    
}
