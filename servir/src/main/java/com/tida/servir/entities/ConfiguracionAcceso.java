package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Configuracion de Acceso de Usuarios
 *
 * @author ffernandez
 * 
 */
@Entity
@Table(name = "RSC_CONFIGURACIONACCESO")
public class ConfiguracionAcceso implements Serializable {
    private Long id;
    private Long duracion_clave;
    private Long intentos_bloqueo;
    private Long edad_minima;
    private String SMTP_servidor;
    private String SMTP_usuario;
    private String SMTP_clave;
    private String SMTP_puerto;
    @Temporal(TemporalType.DATE)
    private Date fec_actualizacion;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ConfiguracionAcceso other = (ConfiguracionAcceso) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Long getEdad_minima() {
        return edad_minima;
    }

    public void setEdad_minima(Long edad_minima) {
        this.edad_minima = edad_minima;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuracion_clave() {
        return duracion_clave;
    }

    public void setDuracion_clave(Long duracion_clave) {
        this.duracion_clave = duracion_clave;
    }

    public Long getIntentos_bloqueo() {
        return intentos_bloqueo;
    }

    public void setIntentos_bloqueo(Long intentos_bloqueo) {
        this.intentos_bloqueo = intentos_bloqueo;
    }

    public String getSMTP_servidor() {
        return SMTP_servidor;
    }

    public void setSMTP_servidor(String sMTP_servidor) {
        SMTP_servidor = sMTP_servidor;
    }

    public String getSMTP_usuario() {
        return SMTP_usuario;
    }

    public void setSMTP_usuario(String sMTP_usuario) {
        SMTP_usuario = sMTP_usuario;
    }

    public String getSMTP_clave() {
        return SMTP_clave;
    }

    public void setSMTP_clave(String sMTP_clave) {
        SMTP_clave = sMTP_clave;
    }

    public String getSMTP_puerto() {
        return SMTP_puerto;
    }

    public void setSMTP_puerto(String sMTP_puerto) {
        SMTP_puerto = sMTP_puerto;
    }
    
    public Date getFec_actualizacion() {
        return fec_actualizacion;
    }

    public void setFec_actualizacion(Date Fec_actualizacion) {
        fec_actualizacion = Fec_actualizacion;
    }
}