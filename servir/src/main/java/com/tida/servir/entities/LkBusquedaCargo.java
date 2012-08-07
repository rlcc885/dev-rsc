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
public class LkBusquedaCargo implements Serializable {
    @NonVisual
    @Id
    private long id;
    private String denominacion;
    private String situcap;
    private String reglabo;
    private String unidadorga;
    
    public LkBusquedaCargo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }  
    
    public String getSitucap() {
        return situcap;
    }

    public void setSitucap(String situcap) {
        this.situcap = situcap;
    }  
    
    public String getReglabo() {
        return reglabo;
    }

    public void setReglabo(String reglabo) {
        this.reglabo = reglabo;
    }  
    
    public String getUnidadorga() {
        return unidadorga;
    }

    public void setUnidadorga(String unidadorga) {
        this.unidadorga = unidadorga;
    }
   
    
}
