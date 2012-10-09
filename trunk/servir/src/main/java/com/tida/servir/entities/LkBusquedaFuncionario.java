/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBusquedaFuncionario implements Serializable {

    @Id
    private long id;
    private String nombrefuncionario;   
    private String nroDocumento;   
    private String cargo;   
    
    public LkBusquedaFuncionario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombrefuncionario() {
        return nombrefuncionario;
    }

    public void setNombrefuncionario(String nombrefuncionario) {
        this.nombrefuncionario = nombrefuncionario;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    
    
    
    
}
