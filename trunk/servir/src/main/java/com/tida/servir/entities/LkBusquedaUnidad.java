/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
@Table(name = "LKBUSQUEDAUNIDAD", catalog = "", schema = "RNSC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkBusquedaUnidad.findAll", query = "SELECT l FROM LkBusquedaUnidad l"),
    @NamedQuery(name = "LkBusquedaUnidad.findById", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.id = :id"),
    @NamedQuery(name = "LkBusquedaUnidad.findByDenominacion", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.denominacion = :denominacion"),
    @NamedQuery(name = "LkBusquedaUnidad.findBySigla", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.sigla = :sigla"),
    @NamedQuery(name = "LkBusquedaUnidad.findByNivel", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.nivel = :nivel"),
    @NamedQuery(name = "LkBusquedaUnidad.findByUnidadorganicaId", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.unidadorganicaId = :unidadorganicaId"),
    @NamedQuery(name = "LkBusquedaUnidad.findByCategoriauoId", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.categoriauoId = :categoriauoId"),
    @NamedQuery(name = "LkBusquedaUnidad.findByDesccategoriauo", query = "SELECT l FROM LkBusquedaUnidad l WHERE l.desccategoriauo = :desccategoriauo")})
public class LkBusquedaUnidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Column(name = "SIGLA")
    private String sigla;
    @Column(name = "NIVEL")
    private Long nivel;
    @Column(name = "UNIDADORGANICA_ID")
    private Long unidadorganicaId;
    @Column(name = "CATEGORIAUO_ID")
    private BigInteger categoriauoId;
    @Column(name = "DESCCATEGORIAUO")
    private String desccategoriauo;

    public LkBusquedaUnidad() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Long getUnidadorganicaId() {
        return unidadorganicaId;
    }

    public void setUnidadorganicaId(Long unidadorganicaId) {
        this.unidadorganicaId = unidadorganicaId;
    }

    public BigInteger getCategoriauoId() {
        return categoriauoId;
    }

    public void setCategoriauoId(BigInteger categoriauoId) {
        this.categoriauoId = categoriauoId;
    }

    public String getDesccategoriauo() {
        return desccategoriauo;
    }

    public void setDesccategoriauo(String desccategoriauo) {
        this.desccategoriauo = desccategoriauo;
    }
    
}
