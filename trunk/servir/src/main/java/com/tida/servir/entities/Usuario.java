package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
@Table(name = "RSC_USUARIO")
public class Usuario implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

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
    private Entidad entidad;
    private String login; //el login del usuario
//    private String tipo_usuario;
//    private String email;
    private int estado;
    // Si venció el passwd, etc.
    private String md5Clave; // Poner el hash.
    private String nombres;
//    private String apellidos;
    @NonVisual
    @Temporal(TemporalType.DATE)
    private Date ultimo_cambio_clave;
    @NonVisual
    private Long intentos_fallidos;
    @ManyToOne
    @Validate("required")
    private Trabajador trabajador;
//    private String clave;
    @Column(name = "ROL_ID")
    private Long rolid;
    @NonVisual
    @Column(name = "FECHA_BLOQUEO", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_bloqueo;
    @Column(name = "DOCUMENTOIDENTIDAD_ID")
    private Long documentoId;
    @Column(name = "NRODOCUMENTO")
    private String numeroDocumento;
    @Column(name = "APELLIDOPATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDOMATERNO")
    private String apellidoMaterno;
    @Column(name = "EMAILLABORAL")
    private String emaillaboral;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "FECHA_CREACION", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;
    @Column(name = "OBSERVACION")
    private String observacion;

    public Long getRolid() {
        return rolid;
    }

    public void setRolid(Long rolid) {
        this.rolid = rolid;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

//    public String getClave() {
//        return clave;
//    }
//
//    public void setClave(String clave) {
//        this.clave = clave;
//    }

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

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

//    public String getTipo_usuario() {
//        return tipo_usuario;
//    }
//
//    public void setTipo_usuario(String tipo_usuario) {
//        this.tipo_usuario = tipo_usuario;
//    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha_bloqueo() {
        return fecha_bloqueo;
    }

    public void setFecha_bloqueo(Date fecha_bloqueo) {
        this.fecha_bloqueo = fecha_bloqueo;
    }

    public long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(long documentoId) {
        this.documentoId = documentoId;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmaillaboral() {
        return emaillaboral;
    }

    public void setEmaillaboral(String emaillaboral) {
        this.emaillaboral = emaillaboral;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

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