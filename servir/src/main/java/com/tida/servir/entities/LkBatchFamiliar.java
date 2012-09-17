/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author Jurguen Zambrano
 */
@Entity
public class LkBatchFamiliar implements Serializable {
    @Id
    @NonVisual
    private long id; 

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Long getTrabajadorid() {
        return trabajadorid;
    }

    public void setTrabajadorid(Long trabajadorid) {
        this.trabajadorid = trabajadorid;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }
    
    private String tipodocumento;
    private String nrodocumento;
    private Long entidadid;
    private Long trabajadorid;

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNroDocumentof() {
        return nroDocumentof;
    }

    public void setNroDocumentof(String nroDocumentof) {
        this.nroDocumentof = nroDocumentof;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoDocumentofa() {
        return tipoDocumentofa;
    }

    public void setTipoDocumentofa(String tipoDocumentofa) {
        this.tipoDocumentofa = tipoDocumentofa;
    }
    private String parentesco;
    private String sexo;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;        
    private String nroDocumentof;
    private String tipoDocumentofa;
    private String estadoCivil;
    
    
    public LkBatchFamiliar() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}