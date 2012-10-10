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
    
    public LkBusquedaSuspension() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    } 
   
    
    
}
