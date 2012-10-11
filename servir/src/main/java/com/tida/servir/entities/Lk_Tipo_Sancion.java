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
 * @author mcribillero
 */
@Entity
public class Lk_Tipo_Sancion implements Serializable {
    @Id
    @NonVisual
    private long id;

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getReg_laboral() {
        return reg_laboral;
    }

    public void setReg_laboral(Long reg_laboral) {
        this.reg_laboral = reg_laboral;
    }
    private String descripcion;
    @Column(name = "ID_REG_LABORAL")
    private Long reg_laboral;
    private Long categoria;
    private long codigo;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }    
    
    public Lk_Tipo_Sancion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name = "TIEMPO_MIN_ANIOS")
    private Integer tiempoMinAnios;
    @Column(name = "TIEMPO_MIN_MESES")
    private Integer tiempoMinMeses;
    @Column(name = "TIEMPO_MIN_DIAS")
    private Integer tiempoMinDias;
    @Column(name = "TIEMPO_MAX_ANIOS")
    private Integer tiempoMaxAnios;
    @Column(name = "TIEMPO_MAX_MESES")
    private Integer tiempoMaxMeses;
    @Column(name = "TIEMPO_MAX_DIAS")
    private Integer tiempoMaxDias;
    @Column(name = "ID_TIPO_SANCION" )    
    private Long id_tipo;
    @Column(name = "TIEMPO_VISUALIZA_DIAS")
    private Integer tiempoVisualizaDias;
    @Column(name = "TIEMPO_VISUALIZA_ANIOS")
    private Integer tiempoVisualizaAnios;
    @Column(name = "TIEMPO_VISUALIZA_MESES")
    private Integer tiempoVisualizaMeses;
    
    
    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }
        
    public Integer getTiempoMaxAnios() {
        return tiempoMaxAnios;
    }

    public void setTiempoMaxAnios(Integer tiempoMaxAnios) {
        this.tiempoMaxAnios = tiempoMaxAnios;
    }

    public Integer getTiempoMaxDias() {
        return tiempoMaxDias;
    }

    public void setTiempoMaxDias(Integer tiempoMaxDias) {
        this.tiempoMaxDias = tiempoMaxDias;
    }

    public Integer getTiempoMaxMeses() {
        return tiempoMaxMeses;
    }

    public void setTiempoMaxMeses(Integer tiempoMaxMeses) {
        this.tiempoMaxMeses = tiempoMaxMeses;
    }

    public Integer getTiempoMinAnios() {
        return tiempoMinAnios;
    }

    public void setTiempoMinAnios(Integer tiempoMinAnios) {
        this.tiempoMinAnios = tiempoMinAnios;
    }

    public Integer getTiempoMinDias() {
        return tiempoMinDias;
    }

    public void setTiempoMinDias(Integer tiempoMinDias) {
        this.tiempoMinDias = tiempoMinDias;
    }

    public Integer getTiempoMinMeses() {
        return tiempoMinMeses;
    }

    public void setTiempoMinMeses(Integer tiempoMinMeses) {
        this.tiempoMinMeses = tiempoMinMeses;
    }

    public Integer getTiempoVisualizaAnios() {
        return tiempoVisualizaAnios;
    }

    public void setTiempoVisualizaAnios(Integer tiempoVisualizaAnios) {
        this.tiempoVisualizaAnios = tiempoVisualizaAnios;
    }

    public Integer getTiempoVisualizaDias() {
        return tiempoVisualizaDias;
    }

    public void setTiempoVisualizaDias(Integer tiempoVisualizaDias) {
        this.tiempoVisualizaDias = tiempoVisualizaDias;
    }

    public Integer getTiempoVisualizaMeses() {
        return tiempoVisualizaMeses;
    }

    public void setTiempoVisualizaMeses(Integer tiempoVisualizaMeses) {
        this.tiempoVisualizaMeses = tiempoVisualizaMeses;
    }
    
    
}