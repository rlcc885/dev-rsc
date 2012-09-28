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
 * @author arson
 */
public class Anulacion implements Serializable {
    @Id
    @NonVisual
    @Column(name = "ID")
    private Long id;  
    private Long id_sancion;
    private Long id_tipo_doc_san;
    private Long id_tipo_doc_not;
    private Long numero_doc_san;
    private Long numero_doc_not;
    private Long fecha_doc_san;
    private Long fecha_doc_not;
    private String juzgado;
    private String observaciones;

    public Long getFecha_doc_not() {
        return fecha_doc_not;
    }

    public void setFecha_doc_not(Long fecha_doc_not) {
        this.fecha_doc_not = fecha_doc_not;
    }

    public Long getFecha_doc_san() {
        return fecha_doc_san;
    }

    public void setFecha_doc_san(Long fecha_doc_san) {
        this.fecha_doc_san = fecha_doc_san;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(Long id_sancion) {
        this.id_sancion = id_sancion;
    }

    public Long getId_tipo_doc_not() {
        return id_tipo_doc_not;
    }

    public void setId_tipo_doc_not(Long id_tipo_doc_not) {
        this.id_tipo_doc_not = id_tipo_doc_not;
    }

    public Long getId_tipo_doc_san() {
        return id_tipo_doc_san;
    }

    public void setId_tipo_doc_san(Long id_tipo_doc_san) {
        this.id_tipo_doc_san = id_tipo_doc_san;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public Long getNumero_doc_not() {
        return numero_doc_not;
    }

    public void setNumero_doc_not(Long numero_doc_not) {
        this.numero_doc_not = numero_doc_not;
    }

    public Long getNumero_doc_san() {
        return numero_doc_san;
    }

    public void setNumero_doc_san(Long numero_doc_san) {
        this.numero_doc_san = numero_doc_san;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    } 
}