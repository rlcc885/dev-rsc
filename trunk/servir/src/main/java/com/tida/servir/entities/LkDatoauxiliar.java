package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Arson
 */
@Entity
@Table(name = "LK_DATOAUXILIAR")
public class LkDatoauxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Integer codigo;
    @Column(name = "NOMBRETABLA")
    private String nombreTabla;
    private String valor;
    @Column(name = "RELACIONCODIGO")
    private Integer relacionCodigo;
    @Column(name = "TABLARELACION")
    private String tablaRelacion;
    private Boolean editable;
    @Column(name = "DATORELACIONADO")
    private String datoRelacionado;
    @Column(name = "TIENEHIJOS")
    private Boolean tieneHijos;
    private Long relacionId;

    
    
    public Long getRelacionId() {
        return relacionId;
    }

    public void setRelacionId(Long relacionId) {
        this.relacionId = relacionId;
    }
    

    public LkDatoauxiliar() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDatoRelacionado() {
        return datoRelacionado;
    }

    public void setDatoRelacionado(String datoRelacionado) {
        this.datoRelacionado = datoRelacionado;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public Integer getRelacionCodigo() {
        return relacionCodigo;
    }

    public void setRelacionCodigo(Integer relacionCodigo) {
        this.relacionCodigo = relacionCodigo;
    }

    public String getTablaRelacion() {
        return tablaRelacion;
    }

    public void setTablaRelacion(String tablaRelacion) {
        this.tablaRelacion = tablaRelacion;
    }

    public Boolean getTieneHijos() {
        return tieneHijos;
    }

    public void setTieneHijos(Boolean tieneHijos) {
        this.tieneHijos = tieneHijos;
    }
    
   
}