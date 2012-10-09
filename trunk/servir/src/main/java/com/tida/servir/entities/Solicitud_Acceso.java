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
@Table(name = "RNSDD_SOLICITUD_ACCESO")
public class Solicitud_Acceso implements Serializable {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RNSDD_SOLICITUD_ACCESO_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private long id;    
    @NonVisual
    @ManyToOne
    private Trabajador trabajador; 
    private String num_resolucion;
    @Temporal(TemporalType.DATE)
    private Date fec_resolucion;
    private String documento;
    private Boolean estado;
    @ManyToOne
    private Perfil perfil;
    
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFec_resolucion() {
        return fec_resolucion;
    }

    public void setFec_resolucion(Date fec_resolucion) {
        this.fec_resolucion = fec_resolucion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum_resolucion() {
        return num_resolucion;
    }

    public void setNum_resolucion(String num_resolucion) {
        this.num_resolucion = num_resolucion;
    }
    
    @ManyToOne
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    @ManyToOne
    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
        Solicitud_Acceso other = (Solicitud_Acceso) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}
