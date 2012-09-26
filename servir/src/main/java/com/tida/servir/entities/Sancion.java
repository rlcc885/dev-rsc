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
@Table(name = "RNSDD_SANCION")
public class Sancion implements Serializable {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RNSDD_SANCION_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private long id;    
    @ManyToOne
    private Funcionario autoridadnot;
    @ManyToOne
    private Funcionario autoridadsan;
    @ManyToOne
    private DatoAuxiliar categoria_sancion;
    private String causa;
    private Boolean estrabajador;
    @Temporal(TemporalType.DATE)
    private Date fecha_docnot;
    @Temporal(TemporalType.DATE)
    private Date fecha_docsan;
    @Temporal(TemporalType.DATE)
    private Date fechafin_inha;
    @Temporal(TemporalType.DATE)
    private Date fechaini_inha;

    public Funcionario getAutoridadnot() {
        return autoridadnot;
    }

    public void setAutoridadnot(Funcionario autoridadnot) {
        this.autoridadnot = autoridadnot;
    }

    public Funcionario getAutoridadsan() {
        return autoridadsan;
    }

    public void setAutoridadsan(Funcionario autoridadsan) {
        this.autoridadsan = autoridadsan;
    }

    public DatoAuxiliar getCategoria_sancion() {
        return categoria_sancion;
    }

    public void setCategoria_sancion(DatoAuxiliar categoria_sancion) {
        this.categoria_sancion = categoria_sancion;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public Boolean getEstrabajador() {
        return estrabajador;
    }

    public void setEstrabajador(Boolean estrabajador) {
        this.estrabajador = estrabajador;
    }

    public Date getFecha_docnot() {
        return fecha_docnot;
    }

    public void setFecha_docnot(Date fecha_docnot) {
        this.fecha_docnot = fecha_docnot;
    }

    public Date getFecha_docsan() {
        return fecha_docsan;
    }

    public void setFecha_docsan(Date fecha_docsan) {
        this.fecha_docsan = fecha_docsan;
    }

    public Date getFechafin_inha() {
        return fechafin_inha;
    }

    public void setFechafin_inha(Date fechafin_inha) {
        this.fechafin_inha = fechafin_inha;
    }

    public Date getFechaini_inha() {
        return fechaini_inha;
    }

    public void setFechaini_inha(Date fechaini_inha) {
        this.fechaini_inha = fechaini_inha;
    }

    public String getNumdocnot() {
        return numdocnot;
    }

    public void setNumdocnot(String numdocnot) {
        this.numdocnot = numdocnot;
    }

    public String getNumdocsan() {
        return numdocsan;
    }

    public void setNumdocsan(String numdocsan) {
        this.numdocsan = numdocsan;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Persona_Sancion getPersona() {
        return persona;
    }

    public void setPersona(Persona_Sancion persona) {
        this.persona = persona;
    }

    public String getTiem_ser_anio() {
        return tiem_ser_anio;
    }

    public void setTiem_ser_anio(String tiem_ser_anio) {
        this.tiem_ser_anio = tiem_ser_anio;
    }

    public String getTiem_ser_dia() {
        return tiem_ser_dia;
    }

    public void setTiem_ser_dia(String tiem_ser_dia) {
        this.tiem_ser_dia = tiem_ser_dia;
    }

    public String getTiem_ser_mes() {
        return tiem_ser_mes;
    }

    public void setTiem_ser_mes(String tiem_ser_mes) {
        this.tiem_ser_mes = tiem_ser_mes;
    }

    public DatoAuxiliar getTipo_documentonot() {
        return tipo_documentonot;
    }

    public void setTipo_documentonot(DatoAuxiliar tipo_documentonot) {
        this.tipo_documentonot = tipo_documentonot;
    }

    public DatoAuxiliar getTipo_documentosan() {
        return tipo_documentosan;
    }

    public void setTipo_documentosan(DatoAuxiliar tipo_documentosan) {
        this.tipo_documentosan = tipo_documentosan;
    }

    public TipoSancion getTipo_sancion() {
        return tipo_sancion;
    }

    public void setTipo_sancion(TipoSancion tipo_sancion) {
        this.tipo_sancion = tipo_sancion;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    private String numdocnot;
    private String numdocsan;
    private String observaciones;
    @ManyToOne
    private Persona_Sancion persona;
    private String tiem_ser_anio;
    private String tiem_ser_dia;
    private String tiem_ser_mes;
    @ManyToOne
    private DatoAuxiliar tipo_documentonot;
    @ManyToOne
    private DatoAuxiliar tipo_documentosan;
    
    @JoinColumn(name = "TIPO_SANCION_ID", referencedColumnName = "ID_TIPO_SANCION")
    @ManyToOne
    private TipoSancion tipo_sancion;
    @ManyToOne
    private Trabajador trabajador;
    @ManyToOne
    private CargoAsignado cargoasignado;   
    
    @ManyToOne
    private DatoAuxiliar sancion_estado;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @ManyToOne
    public DatoAuxiliar getSancion_estado() {
        return sancion_estado;
    }

    public void setSancion_estado(DatoAuxiliar sancion_estado) {
        this.sancion_estado = sancion_estado;
    }  
    
    @ManyToOne
    public CargoAsignado getCargoasignado() {
        return cargoasignado;
    }

    public void setCargoasignado(CargoAsignado cargoasignado) {
        this.cargoasignado = cargoasignado;
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
        Sancion other = (Sancion) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}
