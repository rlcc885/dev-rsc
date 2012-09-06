/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author Arson
 */
@Entity
@Table(name="RSC_TIPOSANCION")
public class TipoSancion implements Serializable
{
@NonVisual    
private long id;
@Validate("required")
private String descripcion;
@Validate("required")
private DatoAuxiliar categoria;
@Validate("required")
private DatoAuxiliar regimenlaboral;
@Validate("required")
private DatoAuxiliar tipoinhabilitacion;




    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_ENTIDAD_ID_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @ManyToOne
    public DatoAuxiliar getCategoria() {
        return categoria;
    }

    public void setCategoria(DatoAuxiliar categoria) {
        this.categoria = categoria;
    }
    
    @ManyToOne
    public DatoAuxiliar getRegimenlaboral() {
        return regimenlaboral;
    }

    public void setRegimenlaboral(DatoAuxiliar regimenlaboral) {
        this.regimenlaboral = regimenlaboral;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipoinhabilitacion() {
        return tipoinhabilitacion;
    }

    public void setTipoinhabilitacion(DatoAuxiliar tipoinhabilitacion) {
        this.tipoinhabilitacion = tipoinhabilitacion;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoSancion other = (TipoSancion) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    

}
