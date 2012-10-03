/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RNSDD_SUSPENSION")
public class Suspension implements Serializable {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RNSDD_SUSPENSION_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private long id;    
    @ManyToOne
    private Sancion sancion;
    @ManyToOne
    private Entidad entidad_ini;
    @ManyToOne
    private DatoAuxiliar tipo_documentoini;
    private String numdocini;
    @Temporal(TemporalType.DATE)
    private Date fecha_docini;
    @ManyToOne
    private DatoAuxiliar tipo_documentonoti;
    private String numdocnoti;
    @Temporal(TemporalType.DATE)
    private Date fecha_docnoti;
    private String juzgadonoti;
    @ManyToOne
    private Entidad entidad_fin;
    @ManyToOne
    private DatoAuxiliar tipo_documentofin;
    private String numdocfin;
    @Temporal(TemporalType.DATE)
    private Date fecha_docfin;
    @ManyToOne
    private DatoAuxiliar tipo_documentonotf;
    private String numdocnotf;
    @Temporal(TemporalType.DATE)
    private Date fecha_docnotf;
    private String juzgadonotf;
    private String observacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Entidad getEntidad_fin() {
        return entidad_fin;
    }

    public void setEntidad_fin(Entidad entidad_fin) {
        this.entidad_fin = entidad_fin;
    }

    public Entidad getEntidad_ini() {
        return entidad_ini;
    }

    public void setEntidad_ini(Entidad entidad_ini) {
        this.entidad_ini = entidad_ini;
    }

    public Date getFecha_docfin() {
        return fecha_docfin;
    }

    public void setFecha_docfin(Date fecha_docfin) {
        this.fecha_docfin = fecha_docfin;
    }

    public Date getFecha_docini() {
        return fecha_docini;
    }

    public void setFecha_docini(Date fecha_docini) {
        this.fecha_docini = fecha_docini;
    }

    public Date getFecha_docnotf() {
        return fecha_docnotf;
    }

    public void setFecha_docnotf(Date fecha_docnotf) {
        this.fecha_docnotf = fecha_docnotf;
    }

    public Date getFecha_docnoti() {
        return fecha_docnoti;
    }

    public void setFecha_docnoti(Date fecha_docnoti) {
        this.fecha_docnoti = fecha_docnoti;
    }

    public String getJuzgadonotf() {
        return juzgadonotf;
    }

    public void setJuzgadonotf(String juzgadonotf) {
        this.juzgadonotf = juzgadonotf;
    }

    public String getJuzgadonoti() {
        return juzgadonoti;
    }

    public void setJuzgadonoti(String juzgadonoti) {
        this.juzgadonoti = juzgadonoti;
    }

    public String getNumdocfin() {
        return numdocfin;
    }

    public void setNumdocfin(String numdocfin) {
        this.numdocfin = numdocfin;
    }

    public String getNumdocini() {
        return numdocini;
    }

    public void setNumdocini(String numdocini) {
        this.numdocini = numdocini;
    }

    public String getNumdocnotf() {
        return numdocnotf;
    }

    public void setNumdocnotf(String numdocnotf) {
        this.numdocnotf = numdocnotf;
    }

    public String getNumdocnoti() {
        return numdocnoti;
    }

    public void setNumdocnoti(String numdocnoti) {
        this.numdocnoti = numdocnoti;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Sancion getSancion() {
        return sancion;
    }

    public void setSancion(Sancion sancion) {
        this.sancion = sancion;
    }

    public DatoAuxiliar getTipo_documentofin() {
        return tipo_documentofin;
    }

    public void setTipo_documentofin(DatoAuxiliar tipo_documentofin) {
        this.tipo_documentofin = tipo_documentofin;
    }

    public DatoAuxiliar getTipo_documentoini() {
        return tipo_documentoini;
    }

    public void setTipo_documentoini(DatoAuxiliar tipo_documentoini) {
        this.tipo_documentoini = tipo_documentoini;
    }

    public DatoAuxiliar getTipo_documentonotf() {
        return tipo_documentonotf;
    }

    public void setTipo_documentonotf(DatoAuxiliar tipo_documentonotf) {
        this.tipo_documentonotf = tipo_documentonotf;
    }

    public DatoAuxiliar getTipo_documentonoti() {
        return tipo_documentonoti;
    }

    public void setTipo_documentonoti(DatoAuxiliar tipo_documentonoti) {
        this.tipo_documentonoti = tipo_documentonoti;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Suspension other = (Suspension) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}
