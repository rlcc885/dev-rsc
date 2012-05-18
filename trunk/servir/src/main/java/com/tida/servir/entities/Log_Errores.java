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
public class Log_Errores {

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
        Log_Errores other = (Log_Errores) obj;
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

    public String getId_obj_ope() {
        return id_obj_ope;
    }

    public void setId_obj_ope(String idObjOpe) {
        id_obj_ope = idObjOpe;
    }

    public String getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(String tipoObjeto) {
        tipo_objeto = tipoObjeto;
    }

    public String getCod_error() {
        return cod_error;
    }

    public void setCod_error(String codError) {
        cod_error = codError;
    }

    public String getInfo_adicional() {
        return info_adicional;
    }

    public void setInfo_adicional(String infoAdicional) {
        info_adicional = infoAdicional;
    }
    @NonVisual
    private long id;
    @Validate("required")
    private Usuario usuario;
    @Validate("required")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Validate("required")
    private String id_obj_ope;
    @Validate("required")
    private String cod_error;
    @Validate("required")
    private String info_adicional;
    @Validate("required")
    private String tipo_objeto;
}
