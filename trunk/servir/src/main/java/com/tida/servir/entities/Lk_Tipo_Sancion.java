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
public class Lk_Tipo_Sancion implements Serializable {
    @Id
    @NonVisual
    @Column(name = "ID_TIPO_SANCION")
    private long id;

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getReg_laboral() {
        return reg_laboral;
    }

    public void setReg_laboral(Long reg_laboral) {
        this.reg_laboral = reg_laboral;
    }
    private String descripcion;
    @Column(name = "ID_REG_LABORAL")
    private Long reg_laboral;
    private Long categoria;
    private long codigo;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }    
    
    public Lk_Tipo_Sancion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}