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
    private String nombretrabajador; 
    private String nrodocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private Long documentoidentidad_id;
    private String descdocumentoidentidad;    
    private Long entidad_id;

    public Long getDocumentoidentidad_id() {
        return documentoidentidad_id;
    }

    public void setDocumentoidentidad_id(Long documentoidentidad_id) {
        this.documentoidentidad_id = documentoidentidad_id;
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

    public String getDescdocumentoidentidad() {
        return descdocumentoidentidad;
    }

    public void setDescdocumentoidentidad(String descdocumentoidentidad) {
        this.descdocumentoidentidad = descdocumentoidentidad;
    }   
    
}
