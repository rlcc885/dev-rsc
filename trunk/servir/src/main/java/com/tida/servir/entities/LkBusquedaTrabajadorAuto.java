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
public class LkBusquedaTrabajadorAuto implements Serializable {

    @Id
    private long id;
    private String nombretrabajador;
    private String nroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    @ManyToOne
    private DatoAuxiliar documentoidentidad;
    
    public LkBusquedaTrabajadorAuto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @ManyToOne
    public DatoAuxiliar getDocumentoidentidad() {
        return documentoidentidad;
    }

    public void setDocumentoidentidad(DatoAuxiliar documentoidentidad) {
        this.documentoidentidad = documentoidentidad;
    }

    public String getNombretrabajador() {
        return nombretrabajador;
    }

    public void setNombretrabajador(String nombretrabajador) {
        this.nombretrabajador = nombretrabajador;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    
}
