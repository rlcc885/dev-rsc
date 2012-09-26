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
 * @author Jurguen Zambrano
 */
@Entity
public class LkBusquedaTrabajadorSan implements Serializable {

    @Id
    private long id;
    private Long trabajador_id;
    private String nombretrabajador; 
    private String nrodocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;  
    private String nombres;
    private Long entidad_id;
    private String den_cargo;
    private String estadocargo;
    private String regimenvalor;
    @ManyToOne
    private DatoAuxiliar regimenlaboral;
    @ManyToOne
    private DatoAuxiliar documentoidentidad;
    @Temporal(TemporalType.DATE)
    private Date fec_inicio;
    @Temporal(TemporalType.DATE)
    private Date fec_fin;
    private String tiempo_dias;

    public Long getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Long trabajador_id) {
        this.trabajador_id = trabajador_id;
    } 
    
    public String getTiempo_dias() {
        return tiempo_dias;
    }

    public void setTiempo_dias(String tiempo_dias) {
        this.tiempo_dias = tiempo_dias;
    }    
    
    public Date getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(Date fec_fin) {
        this.fec_fin = fec_fin;
    }

    public Date getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(Date fec_inicio) {
        this.fec_inicio = fec_inicio;
    }
    
    @ManyToOne
    public DatoAuxiliar getDocumentoidentidad() {
        return documentoidentidad;
    }

    public void setDocumentoidentidad(DatoAuxiliar documentoidentidad) {
        this.documentoidentidad = documentoidentidad;
    }
    
    public String getDen_cargo() {
        return den_cargo;
    }

    public void setDen_cargo(String den_cargo) {
        this.den_cargo = den_cargo;
    }

    public String getEstadocargo() {
        return estadocargo;
    }

    public void setEstadocargo(String estadocargo) {
        this.estadocargo = estadocargo;
    }
    
    @ManyToOne
    public DatoAuxiliar getRegimenlaboral() {
        return regimenlaboral;
    }

    public void setRegimenlaboral(DatoAuxiliar regimenlaboral) {
        this.regimenlaboral = regimenlaboral;
    }

    public String getRegimenvalor() {
        return regimenvalor;
    }

    public void setRegimenvalor(String regimenvalor) {
        this.regimenvalor = regimenvalor;
    }

    
    
    public Long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(Long entidad_id) {
        this.entidad_id = entidad_id;
    }
    

    public LkBusquedaTrabajadorSan() {
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
    
}
