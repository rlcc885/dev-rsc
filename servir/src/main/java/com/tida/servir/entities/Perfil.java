/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "RSC_PERFIL")
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id"),
    @NamedQuery(name = "Perfil.findByDescperfil", query = "SELECT p FROM Perfil p WHERE p.descperfil = :descperfil"),
    @NamedQuery(name = "Perfil.findByFechacreacion", query = "SELECT p FROM Perfil p WHERE p.fechacreacion = :fechacreacion")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpPerfilSinAsignarPorUsuario",
    query = "CALL SP_PERFILSINASIGNARPORUSUARIO(?,:in_usuario_id)",
    resultClass = Perfil.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
public class Perfil implements Serializable {

    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    
    @JoinTable(name = "RSC_PERFILUSUARIO", joinColumns = {
        @JoinColumn(name = "PERFIL_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @JoinTable(name = "RSC_MENUPERFIL", joinColumns = {
        @JoinColumn(name = "PERFIL_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Menu> menuCollection;
    
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_PERFIL_ID_SEQ", allocationSize=1 )
    @Column(name = "ID")
    private long id;
    @Column(name = "DESCPERFIL")
    private String descperfil;

    public Perfil() {
    }

    public Perfil(long id) {
        this.id = id;
    }

    public Perfil(long id, Date fechacreacion) {
        this.id = id;
        this.fechacreacion = fechacreacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescperfil() {
        return descperfil;
    }

    public void setDescperfil(String descperfil) {
        this.descperfil = descperfil;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Perfil[ id=" + id + " ]";
    }

//    public List<Menuperfil> getMenuperfilList() {
//        return menuperfilList;
//    }
//
//    public void setMenuperfilList(List<Menuperfil> menuperfilList) {
//        this.menuperfilList = menuperfilList;
//    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }
    
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }
}
