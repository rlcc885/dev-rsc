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
 * @author Jurguen Zambrano
 */
@Entity
public class LkBusquedaTrabajador implements Serializable {

    @Id
    private long id;
    private String nombretrabajador;
    private Boolean validado;
    private String entidad;
    private String unidadorganica;
    private String cargo;
    private String estadocargo;
    private String nrodocumento;
    private Boolean estado;
    private Long entidad_id;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private Long documentoidentidad_id;
    private String descdocumentoidentidad;
    private String sexo;
    private Long tipodiscapacidad_id;
    private Long estadocivil_id;
    private Long regimenlaboral_id;
    private Long unidadorganica_id;
    private Long nivelinstruccion_id;
    private Long formacionprofesional_id;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;

    public long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LkBusquedaTrabajador() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombretrabajador() {
        return nombretrabajador;
    }

    public void setNombretrabajador(String nombretrabajador) {
        this.nombretrabajador = nombretrabajador;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getUnidadorganica() {
        return unidadorganica;
    }

    public void setUnidadorganica(String unidadorganica) {
        this.unidadorganica = unidadorganica;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstadocargo() {
        return estadocargo;
    }

    public void setEstadocargo(String estadocargo) {
        this.estadocargo = estadocargo;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Long getDocumentoidentidad_id() {
        return documentoidentidad_id;
    }

    public void setDocumentoidentidad_id(Long documentoidentidad_id) {
        this.documentoidentidad_id = documentoidentidad_id;
    }

    public String getDescdocumentoidentidad() {
        return descdocumentoidentidad;
    }

    public void setDescdocumentoidentidad(String descdocumentoidentidad) {
        this.descdocumentoidentidad = descdocumentoidentidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public long getTipodiscapacidad_id() {
        return tipodiscapacidad_id;
    }

    public void setTipodiscapacidad_id(long tipodiscapacidad_id) {
        this.tipodiscapacidad_id = tipodiscapacidad_id;
    }

    public long getEstadocivil_id() {
        return estadocivil_id;
    }

    public void setEstadocivil_id(long estadocivil_id) {
        this.estadocivil_id = estadocivil_id;
    }

    public long getRegimenlaboral_id() {
        return regimenlaboral_id;
    }

    public void setRegimenlaboral_id(long regimenlaboral_id) {
        this.regimenlaboral_id = regimenlaboral_id;
    }

    public long getUnidadorganica_id() {
        return unidadorganica_id;
    }

    public void setUnidadorganica_id(long unidadorganica_id) {
        this.unidadorganica_id = unidadorganica_id;
    }

    public long getNivelinstruccion_id() {
        return nivelinstruccion_id;
    }

    public void setNivelinstruccion_id(long nivelinstruccion_id) {
        this.nivelinstruccion_id = nivelinstruccion_id;
    }

    public long getFormacionprofesional_id() {
        return formacionprofesional_id;
    }

    public void setFormacionprofesional_id(long formacionprofesional_id) {
        this.formacionprofesional_id = formacionprofesional_id;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }
    
}
