package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
@Table(name = "RSC_LOG_OPERACIONES")
public class Log_Operaciones {

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
        Log_Operaciones other = (Log_Operaciones) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_LOG_OPERACIONES_ID_SEQ", allocationSize = 1)
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

    public String getCod_operacion() {
        return cod_operacion;
    }

    public void setCod_operacion(String codOperacion) {
        cod_operacion = codOperacion;
    }

    public String getRes_operacion() {
        return res_operacion;
    }

    public void setRes_operacion(String resOperacion) {
        res_operacion = resOperacion;
    }

    public String getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(String tipoObjeto) {
        tipo_objeto = tipoObjeto;
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
    private String cod_operacion;
    @Validate("required")
    private String res_operacion;
    @Validate("required")
    private String tipo_objeto;
}
