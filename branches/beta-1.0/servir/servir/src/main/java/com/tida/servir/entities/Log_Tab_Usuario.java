package com.tida.servir.entities;

import java.util.Date;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Log_Tab_Usuario {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Log_Tab_Usuario other = (Log_Tab_Usuario) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipoOperacion) {
        tipo_operacion = tipoOperacion;
    }

    @ManyToOne
    public Usuario getId_usu_ope() {
        return id_usu_ope;
    }

    public void setId_usu_ope(Usuario idUsuOpe) {
        id_usu_ope = idUsuOpe;
    }
    @NonVisual
    private long id;
    @Validate("required")
    private Usuario usuario;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    public Date fecha;
    @Validate("required")
    public String tipo_operacion;
    @Validate("required")
    public Usuario id_usu_ope;
}
