package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Usuario implements Serializable {

    public static final String ESTADOBORRADO = "Borrado";
    public static final String ESTADOACTIVO = "Activo";
    public static final String ESTADOBLOQUEADO = "Bloqueado";
    public static final String ADMINGRAL = "Administrador de usuarios general";
    public static final String ADMINLOCAL = "Administrador de usuarios de un entidad U.Ejecutora";
    public static final String ADMINSISTEMA = "Administrador sistema";
    public static final String OPERADORABMSERVIR = "Operador ABM de SERVIR";
    public static final String OPERADORABMLOCAL = "Operador ABM de entidad U.Ejecutora";
    public static final String OPERADORLECTURALOCAL = "Operador lectura de entidad U.Ejecutora";
    public static final String OPERADORANALISTA = "Operador lectura general - analista";
    public static final String TRABAJADOR = "Trabajador";
    private static final long serialVersionUID = 1L;
//       @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//       @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    @GeneratedValue
    @NonVisual
    private Long id;
//	@PrimaryKeyJoinColumn
    @ManyToOne
    @Validate("required")
    private Entidad_BK entidad;
    private String login; //el login del usuario
    private String tipo_usuario;
//    private String email;
    private int estado; // Si está desactivado(Falta confirme mail),
    // Si venció el passwd, etc.
    private String md5Clave; // Poner el hash.
//    private String nombres;
//    private String apellidos;
    @NonVisual
    @Temporal(TemporalType.DATE)
    private Date ultimo_cambio_clave;
    @NonVisual
    private Long intentos_fallidos;
    @ManyToOne
    @Validate("required")
    private Trabajador trabajador;
    private String clave;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Long getIntentos_fallidos() {
        return intentos_fallidos;
    }

    public void setIntentos_fallidos(Long intentos_fallidos) {
        this.intentos_fallidos = intentos_fallidos;
    }

    public Date getUltimo_cambio_clave() {
        return ultimo_cambio_clave;
    }

    public void setUltimo_cambio_clave(Date ultimo_cambio_clave) {
        this.ultimo_cambio_clave = ultimo_cambio_clave;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMd5Clave() {
        return md5Clave;
    }

    public void setMd5Clave(String md5Clave) {
        this.md5Clave = md5Clave;
    }

    public Entidad_BK getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad_BK entidadUE) {
        this.entidad = entidadUE;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

//    public String getApellidos() {
//        return apellidos;
//    }
//
//    public void setApellidos(String apellidos) {
//        this.apellidos = apellidos;
//    }
//
//    public String getNombres() {
//        return nombres;
//    }
//
//    public void setNombres(String nombres) {
//        this.nombres = nombres;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
