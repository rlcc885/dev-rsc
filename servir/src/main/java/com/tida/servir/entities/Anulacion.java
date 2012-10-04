/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author arson
 */
@Entity
@Table(name = "RNSDD_ANULACION")
public class Anulacion implements Serializable {
    
    @Id
    @NonVisual
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RNSDD_ANULARSANCION_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_SANCION", referencedColumnName = "ID")
    @ManyToOne
    private Sancion id_sancion;
    private Long id_tipo_doc_san;
    private Long id_tipo_doc_not;
    private String numero_doc_san;
    private String numero_doc_not;
    
    @Temporal(TemporalType.DATE)
    private Date fecha_doc_san;
    
    @Temporal(TemporalType.DATE)
    private Date fecha_doc_not;
    
    private String juzgado;
    private String observaciones;
    @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID")
    @ManyToOne    
    private Entidad id_entidad;

    public Date getFecha_doc_not() {
        return fecha_doc_not;
    }

    public void setFecha_doc_not(Date fecha_doc_not) {
        this.fecha_doc_not = fecha_doc_not;
    }

    public Date getFecha_doc_san() {
        return fecha_doc_san;
    }

    public void setFecha_doc_san(Date fecha_doc_san) {
        this.fecha_doc_san = fecha_doc_san;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sancion getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(Sancion id_sancion) {
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

    public String getNumero_doc_not() {
        return numero_doc_not;
    }

    public void setNumero_doc_not(String numero_doc_not) {
        this.numero_doc_not = numero_doc_not;
    }

    public String getNumero_doc_san() {
        return numero_doc_san;
    }

    public void setNumero_doc_san(String numero_doc_san) {
        this.numero_doc_san = numero_doc_san;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Entidad getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(Entidad id_entidad) {
        this.id_entidad = id_entidad;
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
        Anulacion other = (Anulacion) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}