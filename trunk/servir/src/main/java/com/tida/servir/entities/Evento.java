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
    public static Integer CANT_DEFAULT = 1;
        
    private long id;
    private String descevento;
    @ManyToOne
    private Entidad entidad;
    private long trabajador_id;
    
    private long tipoevento_id;
    private Boolean estadoevento;
    @Temporal(TemporalType.DATE)
    private Date fechaevento;
    private long tabla_id;
      
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
    
//    public Trabajador getTrabajador() {
//        return trabajador;
//    }
//
//    public void setTrabajador(Trabajador trabajador) {
//        this.trabajador = trabajador;
//    }
    public long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(long trabajador_id) {
        this.trabajador_id = trabajador_id;
    }
    
    public long getTabla_id() {
        return tabla_id;
    }

    public void setTabla_id(long tabla_id) {
        this.tabla_id = tabla_id;
    }
    
    public long getTipoevento_id() {
        return tipoevento_id;
    }

    public void setTipoevento_id(long tipoevento_id) {
        this.tipoevento_id = tipoevento_id;
    }
    
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
   
    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
   
}
