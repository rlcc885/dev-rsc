/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

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
        
    @Id
    @GeneratedValue
    private long id;
    private String descevento;
//    @ManyToOne
//    @ManyToOne
    private Long entidad;
    private long trabajador_id;
    
    private long tipoevento_id;
    private Boolean estadoevento;
    @Temporal(TemporalType.DATE)
    private Date fechaevento;
    private Long tabla_id;
      
    public String getDescevento() {
        return descevento;
    }

    public void setDescevento(String descevento) {
        this.descevento = descevento;
    }
    
        public long getEntidad() {
        return entidad;
    }

    public void setEntidad(long entidad) {
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
    
    public Long getTabla_id() {
        return tabla_id;
    }

    public void setTabla_id(Long tabla_id) {
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
