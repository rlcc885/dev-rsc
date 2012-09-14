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
@Table(name = "RNSDD_SANCION")
public class Sancion2 implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PERSONA_ID")
    private BigInteger personaId;
    @Column(name = "TIPO_DOCUMENTO_ID")
    private BigInteger tipoDocumentoId;
    @Lob
    @Column(name = "DESCRIPCION")
    private Serializable descripcion;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Lob
    @Column(name = "DOCUMENTO_NOTIF")
    private Serializable documentoNotif;
    @Lob
    @Column(name = "CAUSA_DESTITUCION")
    private Serializable causaDestitucion;
    @Lob
    @Column(name = "OBSERVACIONES")
    private Serializable observaciones;
    @Column(name = "FECHAINI_INHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainiInha;
    @Column(name = "FECHAFIN_INHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinInha;
    @Lob
    @Column(name = "DOC_ARCHIVO")
    private Serializable docArchivo;
    @Column(name = "FECHA_NOTIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNotif;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "USUARIO_MODIFICA_ID")
    private BigInteger usuarioModificaId;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "USUARIO_ELIMINA_ID")
    private BigInteger usuarioEliminaId;
    @Column(name = "USUARIO_REGISTRA_ID")
    private BigInteger usuarioRegistraId;
    @Column(name = "ESTADO_REGISTRO")
    private Character estadoRegistro;
    @Column(name = "FLAG_DOC")
    private Character flagDoc;
    @Column(name = "SANCION_ESTADO")
    private BigInteger sancionEstado;
    @Column(name = "DOC_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docFecha;
    @Column(name = "ESTRABAJADOR")
    private Short estrabajador;
    @JoinColumn(name = "TRABAJADOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private Trabajador trabajadorId;
    @JoinColumn(name = "DOCUMENTO_ID", referencedColumnName = "ID")
    @ManyToOne
    private DatoAuxiliar documentoId;
    @JoinColumn(name = "TIPO_SANCION", referencedColumnName = "ID_TIPO_SANCION")
    @ManyToOne
    private TipoSancion tipoSancion;

    public Sancion2() {
    }

    public Sancion2(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getPersonaId() {
        return personaId;
    }

    public void setPersonaId(BigInteger personaId) {
        this.personaId = personaId;
    }

    public BigInteger getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(BigInteger tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public Serializable getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Serializable descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Serializable getDocumentoNotif() {
        return documentoNotif;
    }

    public void setDocumentoNotif(Serializable documentoNotif) {
        this.documentoNotif = documentoNotif;
    }

    public Serializable getCausaDestitucion() {
        return causaDestitucion;
    }

    public void setCausaDestitucion(Serializable causaDestitucion) {
        this.causaDestitucion = causaDestitucion;
    }

    public Serializable getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Serializable observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechainiInha() {
        return fechainiInha;
    }

    public void setFechainiInha(Date fechainiInha) {
        this.fechainiInha = fechainiInha;
    }

    public Date getFechafinInha() {
        return fechafinInha;
    }

    public void setFechafinInha(Date fechafinInha) {
        this.fechafinInha = fechafinInha;
    }

    public Serializable getDocArchivo() {
        return docArchivo;
    }

    public void setDocArchivo(Serializable docArchivo) {
        this.docArchivo = docArchivo;
    }

    public Date getFechaNotif() {
        return fechaNotif;
    }

    public void setFechaNotif(Date fechaNotif) {
        this.fechaNotif = fechaNotif;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigInteger getUsuarioModificaId() {
        return usuarioModificaId;
    }

    public void setUsuarioModificaId(BigInteger usuarioModificaId) {
        this.usuarioModificaId = usuarioModificaId;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public BigInteger getUsuarioEliminaId() {
        return usuarioEliminaId;
    }

    public void setUsuarioEliminaId(BigInteger usuarioEliminaId) {
        this.usuarioEliminaId = usuarioEliminaId;
    }

    public BigInteger getUsuarioRegistraId() {
        return usuarioRegistraId;
    }

    public void setUsuarioRegistraId(BigInteger usuarioRegistraId) {
        this.usuarioRegistraId = usuarioRegistraId;
    }

    public Character getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Character estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Character getFlagDoc() {
        return flagDoc;
    }

    public void setFlagDoc(Character flagDoc) {
        this.flagDoc = flagDoc;
    }

    public BigInteger getSancionEstado() {
        return sancionEstado;
    }

    public void setSancionEstado(BigInteger sancionEstado) {
        this.sancionEstado = sancionEstado;
    }

    public Date getDocFecha() {
        return docFecha;
    }

    public void setDocFecha(Date docFecha) {
        this.docFecha = docFecha;
    }

    public Short getEstrabajador() {
        return estrabajador;
    }

    public void setEstrabajador(Short estrabajador) {
        this.estrabajador = estrabajador;
    }

    public Trabajador getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Trabajador trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public DatoAuxiliar getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(DatoAuxiliar documentoId) {
        this.documentoId = documentoId;
    }

    public TipoSancion getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(TipoSancion tipoSancion) {
        this.tipoSancion = tipoSancion;
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
        if (!(object instanceof Sancion)) {
            return false;
        }
        Sancion2 other = (Sancion2) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.Sancion[ id=" + id + " ]";
    }
    
}
