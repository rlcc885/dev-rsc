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
@Table(name = "RSC_ROL")
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findById", query = "SELECT r FROM Rol r WHERE r.id = :id"),
    @NamedQuery(name = "Rol.findByIdLow", query = "SELECT r FROM Rol r WHERE r.id <= :id"),
    @NamedQuery(name = "Rol.findByCodrol", query = "SELECT r FROM Rol r WHERE r.codrol = :codrol"),
    @NamedQuery(name = "Rol.findByDescrol", query = "SELECT r FROM Rol r WHERE r.descrol = :descrol")})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private long id;
    @Column(name = "CODROL")
    private String codrol;
    @Column(name = "DESCROL")
    private String descrol;

    public Rol() {
    }

    public Rol(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodrol() {
        return codrol;
    }

    public void setCodrol(String codrol) {
        this.codrol = codrol;
    }

    public String getDescrol() {
        return descrol;
    }

    public void setDescrol(String descrol) {
        this.descrol = descrol;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Rol[ id=" + id + " ]";
    }
    
}
