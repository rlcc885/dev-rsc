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
    @NamedQuery(name = "UsuarioTrabajador.findById", query = "SELECT u FROM UsuarioTrabajador u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioTrabajador.findByLogin", query = "SELECT u FROM UsuarioTrabajador u WHERE u.login = :login")
})

public class UsuarioTrabajador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "DOCUMENTOIDENTIDAD_ID")
    private Long documentoidentidadid;
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
    private Long intentosFallidos;
    @Column(name = "ULTIMO_CAMBIO_CLAVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoCambioClave;
    @Column(name = "ESTADO")
    private int estado;
    @Column(name = "ESTADODESC")
    private String estadodesc;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private long id;
    //@ManyToOne
    @Column(name = "ENTIDAD_ID")
    private Long entidadid;
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Column(name = "ROL_ID")
    private Long rolid;
    @Column(name = "EMAILLABORAL")
    private String emaillaboral;
    @Column(name = "TRABAJADOR_ID")
    private Long trabajadorid;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DEN_CARGO")
    private String cargo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "OBSERVACION")
    private String observacion;
    
    public UsuarioTrabajador() {
    }

    public long getDocumentoidentidadid() {
        return documentoidentidadid;
    }

    public void setDocumentoidentidadid(long documentoidentidadid) {
        this.documentoidentidadid = documentoidentidadid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public long getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(long intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public Date getUltimoCambioClave() {
        return ultimoCambioClave;
    }

    public void setUltimoCambioClave(Date ultimoCambioClave) {
        this.ultimoCambioClave = ultimoCambioClave;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getEstadodesc() {
        return estadodesc;
    }

    public void setEstadodesc(String estadodesc) {
        this.estadodesc = estadodesc;
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

    public long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(long entidadid) {
        this.entidadid = entidadid;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
