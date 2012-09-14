/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author Arson
 */
@Entity
@Table(name = "RNSDD_TIPO_SAN_REG_LAB")
public class SancionRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SancionRegimenPK sancionRegimenPK;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "ID_REG_LABORAL", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DatoAuxiliar datoauxiliar;
    @JoinColumn(name = "ID_TIPO_SANCION", referencedColumnName = "ID_TIPO_SANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoSancion tipoSancion;

    public SancionRegimen() {
    }

    public SancionRegimen(SancionRegimenPK sancionRegimenPK) {
        this.sancionRegimenPK = sancionRegimenPK;
    }

    public SancionRegimen(Long idTipoSancion, Long idRegLaboral) {
        this.sancionRegimenPK = new SancionRegimenPK(idTipoSancion, idRegLaboral);
    }

    public SancionRegimenPK getSancionRegimenPK() {
        return sancionRegimenPK;
    }

    public void setSancionRegimenPK(SancionRegimenPK sancionRegimenPK) {
        this.sancionRegimenPK = sancionRegimenPK;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public DatoAuxiliar getDatoauxiliar() {
        return datoauxiliar;
    }

    public void setDatoauxiliar(DatoAuxiliar datoauxiliar) {
        this.datoauxiliar = datoauxiliar;
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
        hash += (sancionRegimenPK != null ? sancionRegimenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SancionRegimen)) {
            return false;
        }
        SancionRegimen other = (SancionRegimen) object;
        if ((this.sancionRegimenPK == null && other.sancionRegimenPK != null) || (this.sancionRegimenPK != null && !this.sancionRegimenPK.equals(other.sancionRegimenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tida.servir.entities.SancionRegimen[ sancionRegimenPK=" + sancionRegimenPK + " ]";
    }
    
}
