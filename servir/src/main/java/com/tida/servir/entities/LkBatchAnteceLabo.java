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
public class LkBatchAnteceLabo implements Serializable {
    @Id
    @NonVisual
    private long id; 

    public Long getEntidadid() {
        return entidadid;
    }

    public void setEntidadid(Long entidadid) {
        this.entidadid = entidadid;
    }

    public Long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Long trabajador_id) {
        this.trabajador_id = trabajador_id;
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
    private Long trabajador_id;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getFec_egreso() {
        return fec_egreso;
    }

    public void setFec_egreso(Date fec_egreso) {
        this.fec_egreso = fec_egreso;
    }

    public Date getFec_ingreso() {
        return fec_ingreso;
    }

    public void setFec_ingreso(Date fec_ingreso) {
        this.fec_ingreso = fec_ingreso;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getMotivocese() {
        return motivocese;
    }

    public void setMotivocese(String motivocese) {
        this.motivocese = motivocese;
    }   
    
    private String cargo;
    private String empresa;
    @Temporal(TemporalType.DATE)
    private Date fec_ingreso;
    @Temporal(TemporalType.DATE)
    private Date fec_egreso;
    private String funcion;
    private String motivocese;
    
    public LkBatchAnteceLabo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}