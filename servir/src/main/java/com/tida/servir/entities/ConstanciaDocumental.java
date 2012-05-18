/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */
@Entity
public class ConstanciaDocumental {

//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;

//    @ManyToOne(optional = false)
    private Legajo legajo;

    private String cat_constancia;

    private String tip_constancia;


    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String num_resolucion;
    private String txt_descriptivo;

    public String getCat_constancia() {
        return cat_constancia;
    }

    public void setCat_constancia(String cat_constancia) {
        this.cat_constancia = cat_constancia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    public Legajo getLegajo() {
        return legajo;
    }

    public void setLegajo(Legajo legajo) {
        this.legajo = legajo;
    }

    public String getNum_resolucion() {
        return num_resolucion;
    }

    public void setNum_resolucion(String num_resolucion) {
        this.num_resolucion = num_resolucion;
    }

    public String getTip_constancia() {
        return tip_constancia;
    }

    public void setTip_constancia(String tip_constancia) {
        this.tip_constancia = tip_constancia;
    }

    public String getTxt_descriptivo() {
        return txt_descriptivo;
    }

    public void setTxt_descriptivo(String txt_descriptivo) {
        this.txt_descriptivo = txt_descriptivo;
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
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    
}
