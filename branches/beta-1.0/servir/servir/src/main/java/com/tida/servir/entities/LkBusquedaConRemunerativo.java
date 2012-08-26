package com.tida.servir.entities;

/**
 *
 * @author Jurguen Zambrano
 */
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;


 

@Entity
public class LkBusquedaConRemunerativo implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    @NonVisual
    private Long id;
    private String codigo;
    private String descripcion;    
    private String tipo;
    private String tipostd;
    private String perio;
    @NonVisual
    private Long entidad;
    
    public LkBusquedaConRemunerativo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipostd() {
        return tipostd;
    }

    public void setTipostd(String tipostd) {
        this.tipostd = tipostd;
    }
    
    public String getPerio() {
        return perio;
    }

    public void setPerio(String perio) {
        this.perio = perio;
    }
    
    public long getEntidad() {
        return entidad;
    }

    public void setEntidad(long entidad) {
        this.entidad = entidad;
    }
}
