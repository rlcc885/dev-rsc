/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Arson
 */
@Entity
@Table(name="RSC_ESTADO_ENTIDAD")
public class EstadoEntidad implements Serializable {

 @Id   
 @Column(name="CUE_ENTIDAD")   
 private  String cueEntidad;
 @Column(name="PROCESO")
 private  Integer  estado;
 @Column(name="TIPOPROCESO")
 private  Integer tipoProceso;

    public String getCueEntidad() {
        return cueEntidad;
    }

    public void setCueEntidad(String cueEntidad) {
        this.cueEntidad = cueEntidad;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Integer tipoProceso) {
        this.tipoProceso = tipoProceso;
    }
 

}
