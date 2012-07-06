/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class Tipoevento implements Serializable {
    @Id
    private long id;
    private String desctipoevento;    
    
    public Tipoevento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesctipoevento() {
        return desctipoevento;
    }

    public void setDesctipoevento(String desctipoevento) {
        this.desctipoevento = desctipoevento;
    }   

   
}
