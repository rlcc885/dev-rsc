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
public class LkBatchTrabajador implements Serializable {
    @Id
    @NonVisual
    private long id;
    private String tipodocumento;
    private String nroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String sexo;
    private String estadocivil;
    private String pais;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;  
    private String emailPersonal;
    private String emailLaboral;
    private String nroRUC;
    private String telefonocelular;
    private String telefonofijo;
    private String codigoOSCE;
    private String nivelinstruccion;
    private String formacionprofesional;
    private String formacionInfAdicional;
    private String tipovia;
    private String domicilioDireccion;
    private String tipozona;
    private String domicilioCodigoPostal;
    private String cod_dom_dept;
    private String cod_dom_dist;
    private String cod_dom_prov;
    private String tipodiscapacidad;
    private Integer nroCertificadoCONADIS;
    private String esSalud;
    private String gruposanguineo;
    private String sistemapensionario;
    private String regimenpensionario;
    private String numregimenpensionario;
    private Boolean eps;
    private String nombreeps;
    private Boolean recibepension;
    private String emergenciaNombre;
    private String emergenciaDomicilio;
    private String emergenciaTelefonoAlternativo1;
    private String emergenciaTelefonoAlternativo2;
    private String cod_legajo;
    private Long entidadid;

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

    public String getCod_dom_dept() {
        return cod_dom_dept;
    }

    public void setCod_dom_dept(String cod_dom_dept) {
        this.cod_dom_dept = cod_dom_dept;
    }

    public String getCod_dom_dist() {
        return cod_dom_dist;
    }

    public void setCod_dom_dist(String cod_dom_dist) {
        this.cod_dom_dist = cod_dom_dist;
    }

    public String getCod_dom_prov() {
        return cod_dom_prov;
    }

    public void setCod_dom_prov(String cod_dom_prov) {
        this.cod_dom_prov = cod_dom_prov;
    }

    public String getCod_legajo() {
        return cod_legajo;
    }

    public void setCod_legajo(String cod_legajo) {
        this.cod_legajo = cod_legajo;
    }

    public String getCodigoOSCE() {
        return codigoOSCE;
    }

    public void setCodigoOSCE(String codigoOSCE) {
        this.codigoOSCE = codigoOSCE;
    }

    public String getDomicilioCodigoPostal() {
        return domicilioCodigoPostal;
    }

    public void setDomicilioCodigoPostal(String domicilioCodigoPostal) {
        this.domicilioCodigoPostal = domicilioCodigoPostal;
    }

    public String getDomicilioDireccion() {
        return domicilioDireccion;
    }

    public void setDomicilioDireccion(String domicilioDireccion) {
        this.domicilioDireccion = domicilioDireccion;
    }

    public String getEmailLaboral() {
        return emailLaboral;
    }

    public void setEmailLaboral(String emailLaboral) {
        this.emailLaboral = emailLaboral;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getEmergenciaDomicilio() {
        return emergenciaDomicilio;
    }

    public void setEmergenciaDomicilio(String emergenciaDomicilio) {
        this.emergenciaDomicilio = emergenciaDomicilio;
    }

    public String getEmergenciaNombre() {
        return emergenciaNombre;
    }

    public void setEmergenciaNombre(String emergenciaNombre) {
        this.emergenciaNombre = emergenciaNombre;
    }

    public String getEmergenciaTelefonoAlternativo1() {
        return emergenciaTelefonoAlternativo1;
    }

    public void setEmergenciaTelefonoAlternativo1(String emergenciaTelefonoAlternativo1) {
        this.emergenciaTelefonoAlternativo1 = emergenciaTelefonoAlternativo1;
    }

    public String getEmergenciaTelefonoAlternativo2() {
        return emergenciaTelefonoAlternativo2;
    }

    public void setEmergenciaTelefonoAlternativo2(String emergenciaTelefonoAlternativo2) {
        this.emergenciaTelefonoAlternativo2 = emergenciaTelefonoAlternativo2;
    }

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Boolean getEps() {
        return eps;
    }

    public void setEps(Boolean eps) {
        this.eps = eps;
    }

    public String getEsSalud() {
        return esSalud;
    }

    public void setEsSalud(String esSalud) {
        this.esSalud = esSalud;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFormacionInfAdicional() {
        return formacionInfAdicional;
    }

    public void setFormacionInfAdicional(String formacionInfAdicional) {
        this.formacionInfAdicional = formacionInfAdicional;
    }

    public String getFormacionprofesional() {
        return formacionprofesional;
    }

    public void setFormacionprofesional(String formacionprofesional) {
        this.formacionprofesional = formacionprofesional;
    }

    public String getGruposanguineo() {
        return gruposanguineo;
    }

    public void setGruposanguineo(String gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getNivelinstruccion() {
        return nivelinstruccion;
    }

    public void setNivelinstruccion(String nivelinstruccion) {
        this.nivelinstruccion = nivelinstruccion;
    }

    public String getNombreeps() {
        return nombreeps;
    }

    public void setNombreeps(String nombreeps) {
        this.nombreeps = nombreeps;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getNroCertificadoCONADIS() {
        return nroCertificadoCONADIS;
    }

    public void setNroCertificadoCONADIS(Integer nroCertificadoCONADIS) {
        this.nroCertificadoCONADIS = nroCertificadoCONADIS;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNroRUC() {
        return nroRUC;
    }

    public void setNroRUC(String nroRUC) {
        this.nroRUC = nroRUC;
    }

    public String getNumregimenpensionario() {
        return numregimenpensionario;
    }

    public void setNumregimenpensionario(String numregimenpensionario) {
        this.numregimenpensionario = numregimenpensionario;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getRecibepension() {
        return recibepension;
    }

    public void setRecibepension(Boolean recibepension) {
        this.recibepension = recibepension;
    }

    public String getRegimenpensionario() {
        return regimenpensionario;
    }

    public void setRegimenpensionario(String regimenpensionario) {
        this.regimenpensionario = regimenpensionario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSistemapensionario() {
        return sistemapensionario;
    }

    public void setSistemapensionario(String sistemapensionario) {
        this.sistemapensionario = sistemapensionario;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTipodiscapacidad() {
        return tipodiscapacidad;
    }

    public void setTipodiscapacidad(String tipodiscapacidad) {
        this.tipodiscapacidad = tipodiscapacidad;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getTipovia() {
        return tipovia;
    }

    public void setTipovia(String tipovia) {
        this.tipovia = tipovia;
    }

    public String getTipozona() {
        return tipozona;
    }

    public void setTipozona(String tipozona) {
        this.tipozona = tipozona;
    }
    
    public LkBatchTrabajador() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}