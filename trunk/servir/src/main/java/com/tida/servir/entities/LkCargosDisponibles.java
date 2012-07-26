/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author LFL
 */
@Entity
public class LkCargosDisponibles implements Serializable {
    @Id
    private long id;
    private String den_cargo;
    private int uoid; 
    private Boolean estado;
    private Boolean resultado;
    

    public LkCargosDisponibles() {
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDen_cargo() {
        return den_cargo;
    }

    public void setDen_cargo(String den_cargo) {
        this.den_cargo = den_cargo;
    }
    
 
    public int getUoid() {
        return uoid;
    }

    public void setUoid(int uoid) {
        this.uoid = uoid;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }
   
}
