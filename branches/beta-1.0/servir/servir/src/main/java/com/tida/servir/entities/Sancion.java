/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

import java.util.Date;

/**
 *
 * @author Morgan
 */
public class Sancion {
    
    private String nombres;
    private String apellidos;
    private String institucion;
    private String tipoSancion;
    private String inabilitacion;
    private Date finInabilitacion;

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFinInabilitacion() {
        return finInabilitacion;
    }

    public void setFinInabilitacion(Date finInabilitacion) {
        this.finInabilitacion = finInabilitacion;
    }

    public String getInabilitacion() {
        return inabilitacion;
    }

    public void setInabilitacion(String inabilitacion) {
        this.inabilitacion = inabilitacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

}
