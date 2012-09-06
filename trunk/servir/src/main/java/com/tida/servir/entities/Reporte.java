package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RSC_REPORTE")
public class Reporte implements Serializable {
    
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "CATEGORIA_ID")
    private Long categoria_id;
    @Column(name = "FORMATO")
    private int formato;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CODIGO")
    private String codigo;

    public Long getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Long categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
