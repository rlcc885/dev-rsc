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
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "RSC_EVENTO")
public class Evento implements Serializable {

    public static boolean ESTADO_BAJA = false;
    public static boolean ESTADO_ALTA = true;
    @Id    
    private long id;
    private String descevento;
    @ManyToOne
    private Entidad entidad;
//    private Trabajador trabajador;
   // private Tipoevento tipoevento;
    private Boolean estadoevento;
    @Temporal(TemporalType.DATE)
    private Date fechaevento;

    
    public Evento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getDescevento() {
        return descevento;
    }

    public void setDescevento(String descevento) {
        this.descevento = descevento;
    }
    
    @ManyToOne
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    
//    @ManyToOne
//    public Trabajador getTrabajador() {
//        return trabajador;
//    }
//
//    public void setTrabajador(Trabajador trabajador) {
//        this.trabajador = trabajador;
//    }
    
//    @ManyToOne
//    public Tipoevento getTipoevento() {
//        return tipoevento;
//    }
//
//    public void setTipoevento(Tipoevento tipoevento) {
//        this.tipoevento = tipoevento;
//    }
    
    public Boolean getEstadoevento() {
        return estadoevento;
    }

    public void setEstadoevento(Boolean estadoevento) {
        this.estadoevento = estadoevento;
    }
    
    public Date getFechaevento() {
        return fechaevento;
    }

    public void setFechaevento(Date fechaevento) {
        this.fechaevento = fechaevento;
    }   
   
   
}
