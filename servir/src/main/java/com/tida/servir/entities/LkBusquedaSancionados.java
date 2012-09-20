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
 * @author mcribillero
 * @date 20/09/2012
 */
public class LkBusquedaSancionados implements Serializable{
    
    @Id
    @NonVisual
    @Column(name = "ID_SANCION")
    private long id_sancion;
    @NonVisual
    @Column(name = "ENTIDAD_ID")
    private Long entidad_id;
    private String apellidos_persona;
    private String nombres_persona;
    private String apellidos_trabajador;
    private String nombres_trabajador;
    private String entidad_subentidad;
    private String tipo_sancion;
    private String estado;
    private String tiempo_restante;

    public String getApellidos_persona() {
        return apellidos_persona;
    }

    public void setApellidos_persona(String apellidos_persona) {
        this.apellidos_persona = apellidos_persona;
    }

    public String getApellidos_trabajador() {
        return apellidos_trabajador;
    }

    public void setApellidos_trabajador(String apellidos_trabajador) {
        this.apellidos_trabajador = apellidos_trabajador;
    }

    public String getEntidad_subentidad() {
        return entidad_subentidad;
    }

    public void setEntidad_subentidad(String entidad_subentidad) {
        this.entidad_subentidad = entidad_subentidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombres_persona() {
        return nombres_persona;
    }

    public void setNombres_persona(String nombres_persona) {
        this.nombres_persona = nombres_persona;
    }

    public String getNombres_trabajador() {
        return nombres_trabajador;
    }

    public void setNombres_trabajador(String nombres_trabajador) {
        this.nombres_trabajador = nombres_trabajador;
    }

    public String getTiempo_restante() {
        return tiempo_restante;
    }

    public void setTiempo_restante(String tiempo_restante) {
        this.tiempo_restante = tiempo_restante;
    }

    public String getTipo_sancion() {
        return tipo_sancion;
    }

    public void setTipo_sancion(String tipo_sancion) {
        this.tipo_sancion = tipo_sancion;
    }

    public Long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(Long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public long getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(long id_sancion) {
        this.id_sancion = id_sancion;
    }
 
}
