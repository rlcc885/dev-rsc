/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "LKESTADOUSUARIO")
@NamedQueries({
    @NamedQuery(name = "LkEstadoUsuario.findAll", query = "SELECT l FROM LkEstadoUsuario l"),
    @NamedQuery(name = "LkEstadoUsuario.findById", query = "SELECT l FROM LkEstadoUsuario l WHERE l.id = :id"),
    @NamedQuery(name = "LkEstadoUsuario.findByDescestadousuario", query = "SELECT l FROM LkEstadoUsuario l WHERE l.descestadousuario = :descestadousuario")})
public class LkEstadoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Column(name = "DESCESTADOUSUARIO")
    private String descestadousuario;

    public LkEstadoUsuario() {
    }

    public LkEstadoUsuario(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescestadousuario() {
        return descestadousuario;
    }

    public void setDescestadousuario(String descestadousuario) {
        this.descestadousuario = descestadousuario;
    }
   
}
