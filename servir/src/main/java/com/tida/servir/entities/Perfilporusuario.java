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
@Table(name = "RSC_PERFILPORUSUARIO")
@NamedQueries({
    @NamedQuery(name = "Perfilporusuario.findAll", query = "SELECT p FROM Perfilporusuario p"),
    @NamedQuery(name = "Perfilporusuario.findByUsuarioId", query = "SELECT p FROM Perfilporusuario p WHERE p.usuarioId = :usuarioId"),
    @NamedQuery(name = "Perfilporusuario.findByPerfilId", query = "SELECT p FROM Perfilporusuario p WHERE p.perfilId = :perfilId"),
    @NamedQuery(name = "Perfilporusuario.findByDescperfil", query = "SELECT p FROM Perfilporusuario p WHERE p.descperfil = :descperfil")})

public class Perfilporusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROW_NUM")
    private long rownum;
    @Column(name = "USUARIO_ID")
    private long usuarioId;
    @Basic(optional = false)
    @Column(name = "PERFIL_ID")
    private long perfilId;
    @Column(name = "DESCPERFIL")
    private String descperfil;

    public Perfilporusuario() {
    }

    public long getRownum() {
        return rownum;
    }

    public void setRownum(long rownum) {
        this.rownum = rownum;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(long perfilId) {
        this.perfilId = perfilId;
    }

    public String getDescperfil() {
        return descperfil;
    }

    public void setDescperfil(String descperfil) {
        this.descperfil = descperfil;
    }
    
}