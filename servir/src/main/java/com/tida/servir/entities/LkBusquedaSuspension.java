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
public class LkBusquedaSuspension implements Serializable {
    @NonVisual
    @Id
    private long id;    
    private long sancion_id;    
    private String entidadini;
    private String entidadfin;
    @Temporal(TemporalType.DATE)
    private Date fechafin_inha;
    @Temporal(TemporalType.DATE)
    private Date fechaini_inha;
    
    public LkBusquedaSuspension() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSancion_id() {
        return sancion_id;
    }

    public void setSancion_id(long sancion_id) {
        this.sancion_id = sancion_id;
    }

    public String getEntidadfin() {
        return entidadfin;
    }

    public void setEntidadfin(String entidadfin) {
        this.entidadfin = entidadfin;
    }

    public String getEntidadini() {
        return entidadini;
    }

    public void setEntidadini(String entidadini) {
        this.entidadini = entidadini;
    }

    public Date getFechafin_inha() {
        return fechafin_inha;
    }

    public void setFechafin_inha(Date fechafin_inha) {
        this.fechafin_inha = fechafin_inha;
    }

    public Date getFechaini_inha() {
        return fechaini_inha;
    }

    public void setFechaini_inha(Date fechaini_inha) {
        this.fechaini_inha = fechaini_inha;
    }   
    
}
