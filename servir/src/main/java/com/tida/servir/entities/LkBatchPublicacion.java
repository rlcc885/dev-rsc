/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBatchPublicacion implements Serializable {
    @Id
    @NonVisual
    private long id; 

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Long trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }
    
    private String tipodocumento;
    private String nrodocumento;
    private Long entidadid;
    private Long trabajador_id;

    public String getClasepublicacion() {
        return clasepublicacion;
    }

    public void setClasepublicacion(String clasepublicacion) {
        this.clasepublicacion = clasepublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    private String descripcion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String titulo;
    private String clasepublicacion;
    private String tipo;
    
    public LkBatchPublicacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}