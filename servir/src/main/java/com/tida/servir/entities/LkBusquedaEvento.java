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
public class LkBusquedaEvento implements Serializable {
    @Id
    private long id;
    private String nombreusuario;
    private String descevento;
    private String entidaddescripcion;
    private String tipoevento;
    private Boolean estadoevento;
    @Temporal(TemporalType.DATE)
    private Date fechaevento;

    
    public LkBusquedaEvento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }
    
    public String getDescevento() {
        return descevento;
    }

    public void setDescevento(String descevento) {
        this.descevento = descevento;
    }
    
    public String getEntidaddescripcion() {
        return entidaddescripcion;
    }

    public void setEntidaddescripcion(String entidaddescripcion) {
        this.entidaddescripcion = entidaddescripcion;
    }  

    public String getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(String tipoevento) {
        this.tipoevento = tipoevento;
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
   
}
