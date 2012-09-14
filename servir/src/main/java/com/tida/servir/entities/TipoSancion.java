/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Arson
 */
@Entity
@Table(name = "RNSDD_TIPO_SANCION")
public class TipoSancion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SANCION")
    private Long id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "INCLUYE_INHABILITACION")
    private Character incluyeInhabilitacion;
    @Column(name = "TIEMPO_MAX_ANIOS")
    private Integer tiempoMaxAnios;
    @Column(name = "TIEMPO_MAX_MESES")
    private Integer tiempoMaxMeses;
    @Column(name = "TIEMPO_MAX_DIAS")
    private Integer tiempoMaxDias;
    @Column(name = "TIEMPO_VISUALIZA_MESES")
    private Integer tiempoVisualizaMeses;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "ID_USUARIO_REGISTRA")
    private Integer idUsuarioRegistra;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "ID_USUARIO_MODIFICA")
    private Integer idUsuarioModifica;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ID_USUARIO_ELIMINA")
    private Integer idUsuarioElimina;
    @Column(name = "ESTADO_REGISTRO")
    private Character estadoRegistro;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "TIEMPO_VISUALIZA_DIAS")
    private Integer tiempoVisualizaDias;
    @Column(name = "TIEMPO_VISUALIZA_ANIOS")
    private Integer tiempoVisualizaAnios;
    @Column(name = "TIEMPO_MIN_ANIOS")
    private Integer tiempoMinAnios;
    @Column(name = "TIEMPO_MIN_MESES")
    private Integer tiempoMinMeses;
    @Column(name = "TIEMPO_MIN_DIAS")
    private Integer tiempoMinDias;
   /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoSancion")
    private List<SancionRegimen> sancionRegimenList;
    */
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "ID")
    @ManyToOne
    private DatoAuxiliar categoriasancion;
    @JoinColumn(name = "TIPO_INHABILITACION", referencedColumnName = "ID")
    @ManyToOne
    private DatoAuxiliar tipoInhabilitacion;

    public TipoSancion() {
    }

    public TipoSancion(Long idTipoSancion) {
        this.id = idTipoSancion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idTipoSancion) {
        this.id = idTipoSancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getIncluyeInhabilitacion() {
        return incluyeInhabilitacion;
    }

    public void setIncluyeInhabilitacion(Character incluyeInhabilitacion) {
        this.incluyeInhabilitacion = incluyeInhabilitacion;
    }

    public Integer getTiempoMaxAnios() {
        return tiempoMaxAnios;
    }

    public void setTiempoMaxAnios(Integer tiempoMaxAnios) {
        this.tiempoMaxAnios = tiempoMaxAnios;
    }

    public Integer getTiempoMaxMeses() {
        return tiempoMaxMeses;
    }

    public void setTiempoMaxMeses(Integer tiempoMaxMeses) {
        this.tiempoMaxMeses = tiempoMaxMeses;
    }

    public Integer getTiempoMaxDias() {
        return tiempoMaxDias;
    }

    public void setTiempoMaxDias(Integer tiempoMaxDias) {
        this.tiempoMaxDias = tiempoMaxDias;
    }

    public Integer getTiempoVisualizaMeses() {
        return tiempoVisualizaMeses;
    }

    public void setTiempoVisualizaMeses(Integer tiempoVisualizaMeses) {
        this.tiempoVisualizaMeses = tiempoVisualizaMeses;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(Integer idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getIdUsuarioModifica() {
        return idUsuarioModifica;
    }

    public void setIdUsuarioModifica(Integer idUsuarioModifica) {
        this.idUsuarioModifica = idUsuarioModifica;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Integer getIdUsuarioElimina() {
        return idUsuarioElimina;
    }

    public void setIdUsuarioElimina(Integer idUsuarioElimina) {
        this.idUsuarioElimina = idUsuarioElimina;
    }

    public Character getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Character estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getTiempoVisualizaDias() {
        return tiempoVisualizaDias;
    }

    public void setTiempoVisualizaDias(Integer tiempoVisualizaDias) {
        this.tiempoVisualizaDias = tiempoVisualizaDias;
    }

    public Integer getTiempoVisualizaAnios() {
        return tiempoVisualizaAnios;
    }

    public void setTiempoVisualizaAnios(Integer tiempoVisualizaAnios) {
        this.tiempoVisualizaAnios = tiempoVisualizaAnios;
    }

    public Integer getTiempoMinAnios() {
        return tiempoMinAnios;
    }

    public void setTiempoMinAnios(Integer tiempoMinAnios) {
        this.tiempoMinAnios = tiempoMinAnios;
    }

    public Integer getTiempoMinMeses() {
        return tiempoMinMeses;
    }

    public void setTiempoMinMeses(Integer tiempoMinMeses) {
        this.tiempoMinMeses = tiempoMinMeses;
    }

    public Integer getTiempoMinDias() {
        return tiempoMinDias;
    }

    public void setTiempoMinDias(Integer tiempoMinDias) {
        this.tiempoMinDias = tiempoMinDias;
    }
/*
    @XmlTransient
    public List<SancionRegimen> getSancionRegimenList() {
        return sancionRegimenList;
    }

    public void setSancionRegimenList(List<SancionRegimen> sancionRegimenList) {
        this.sancionRegimenList = sancionRegimenList;
    }
*/
    public DatoAuxiliar getCategoriaSancion() {
        return categoriasancion;
    }

    public void setCategoriaSancion(DatoAuxiliar categoria) {
        this.categoriasancion = categoria;
    }

    public DatoAuxiliar getTipoInhabilitacion() {
        return tipoInhabilitacion;
    }

    public void setTipoInhabilitacion(DatoAuxiliar tipoInhabilitacion) {
        this.tipoInhabilitacion = tipoInhabilitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSancion)) {
            return false;
        }
        TipoSancion other = (TipoSancion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.TipoSancion[ idTipoSancion=" + id + " ]";
    }
    
}