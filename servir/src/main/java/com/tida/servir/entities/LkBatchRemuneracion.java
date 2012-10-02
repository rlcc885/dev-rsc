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
public class LkBatchRemuneracion implements Serializable {
    @Id
    @NonVisual
    private long id;

    public String getCod_cargo() {
        return cod_cargo;
    }

    public void setCod_cargo(String cod_cargo) {
        this.cod_cargo = cod_cargo;
    }

    public Long getCargoasignadoid() {
        return cargoasignadoid;
    }

    public void setCargoasignadoid(Long cargoasignadoid) {
        this.cargoasignadoid = cargoasignadoid;
    }

    public Date getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(Date fec_inicio) {
        this.fec_inicio = fec_inicio;
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

    public String getCod_und_organica() {
        return cod_und_organica;
    }

    public void setCod_und_organica(String cod_und_organica) {
        this.cod_und_organica = cod_und_organica;
    }  
    

    private Long cargoasignadoid;
    private String tipodocumento;
    private String nrodocumento;
    private String cod_cargo;
    @Temporal(TemporalType.DATE)
    private Date fec_inicio;
    private String cod_und_organica;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
    private String codigo;
    private String importe;
    
    public LkBatchRemuneracion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}