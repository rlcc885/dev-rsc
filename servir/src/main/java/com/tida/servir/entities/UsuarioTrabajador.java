/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "RSC_USUARIOTRABAJADOR")

@NamedQueries({
    @NamedQuery(name = "UsuarioTrabajador.findAll", query = "SELECT u FROM UsuarioTrabajador u"),
    @NamedQuery(name = "UsuarioTrabajador.findByNrodocumento", query = "SELECT u FROM UsuarioTrabajador u WHERE u.nrodocumento = :nrodocumento"),
    @NamedQuery(name = "UsuarioTrabajador.findByNombres", query = "SELECT u FROM UsuarioTrabajador u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "UsuarioTrabajador.findByApellidopaterno", query = "SELECT u FROM UsuarioTrabajador u WHERE u.apellidopaterno = :apellidopaterno"),
    @NamedQuery(name = "UsuarioTrabajador.findByApellidomaterno", query = "SELECT u FROM UsuarioTrabajador u WHERE u.apellidomaterno = :apellidomaterno"),
    @NamedQuery(name = "UsuarioTrabajador.findByApellidos", query = "SELECT u FROM UsuarioTrabajador u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "UsuarioTrabajador.findByMd5clave", query = "SELECT u FROM UsuarioTrabajador u WHERE u.md5clave = :md5clave"),
    @NamedQuery(name = "UsuarioTrabajador.findByIntentosFallidos", query = "SELECT u FROM UsuarioTrabajador u WHERE u.intentosFallidos = :intentosFallidos"),
    @NamedQuery(name = "UsuarioTrabajador.findByUltimoCambioClave", query = "SELECT u FROM UsuarioTrabajador u WHERE u.ultimoCambioClave = :ultimoCambioClave"),
    @NamedQuery(name = "UsuarioTrabajador.findByEstado", query = "SELECT u FROM UsuarioTrabajador u WHERE u.estado = :estado"),
    @NamedQuery(name = "UsuarioTrabajador.findById", query = "SELECT u FROM UsuarioTrabajador u WHERE u.id = :id")
})

public class UsuarioTrabajador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NRODOCUMENTO")
    private String nrodocumento;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOPATERNO")
    private String apellidopaterno;
    @Column(name = "APELLIDOMATERNO")
    private String apellidomaterno;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "MD5CLAVE")
    private String md5clave;
    @Column(name = "INTENTOS_FALLIDOS")
    private int intentosFallidos;
    @Column(name = "ULTIMO_CAMBIO_CLAVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoCambioClave;
    @Column(name = "ESTADO")
    private Short estado;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private long id;
    @ManyToOne
    private Entidad entidad;
    @Column(name = "ROL_ID")
    private long rolid;
    @Column(name = "EMAILLABORAL")
    private String emaillaboral;
    @Column(name = "TRABAJADOR_ID")
    private Long trabajadorid;
    
    public UsuarioTrabajador() {
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getMd5clave() {
        return md5clave;
    }

    public void setMd5clave(String md5clave) {
        this.md5clave = md5clave;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public Date getUltimoCambioClave() {
        return ultimoCambioClave;
    }

    public void setUltimoCambioClave(Date ultimoCambioClave) {
        this.ultimoCambioClave = ultimoCambioClave;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public long getRolid() {
        return rolid;
    }

    public void setRolid(long rolid) {
        this.rolid = rolid;
    }

    public String getEmaillaboral() {
        return emaillaboral;
    }

    public void setEmaillaboral(String emaillaboral) {
        this.emaillaboral = emaillaboral;
    }

    public Long getTrabajadorid() {
        return trabajadorid;
    }

    public void setTrabajadorid(Long trabajadorid) {
        this.trabajadorid = trabajadorid;
    }
    
}
