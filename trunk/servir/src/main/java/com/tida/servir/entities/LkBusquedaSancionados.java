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
@Entity
public class LkBusquedaSancionados implements Serializable{
    
    @Id
    @NonVisual
    @Column(name = "ID")
    private Long id;
    private Long id_sancion;
    @NonVisual
    @Column(name = "ENTIDAD_ID")
    private Long entidad_id;
    private String estado_id;
    private String apellidos_persona;
    private String nombres_persona;
    private String apellidos_trabajador;
    private String nombres_trabajador;
    private String entidad_subentidad;
    private String tipo_sancion;
    private String estado;
    private String tiempo_restante;
    private String tipo_doc_trabajador;
    private String tipo_doc_persona;
    private String nro_doc_persona;
    private String nro_doc_trabajador;
    private String categoria_sancion_id;
    private String id_reg_laboral;
    private Long id_tipo_sancion;
    private String b_datos_persona;
    private String b_datos_trabajador;
    private Boolean estrabajador;
    private Boolean veditar;

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

    public Long getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(Long id_sancion) {
        this.id_sancion = id_sancion;
    }

    public String getTipo_doc_persona() {
        return tipo_doc_persona;
    }

    public void setTipo_doc_persona(String tipo_doc_persona) {
        this.tipo_doc_persona = tipo_doc_persona;
    }

    public String getTipo_doc_trabajador() {
        return tipo_doc_trabajador;
    }

    public void setTipo_doc_trabajador(String tipo_doc_trabajador) {
        this.tipo_doc_trabajador = tipo_doc_trabajador;
    }

    public String getNro_doc_persona() {
        return nro_doc_persona;
    }

    public void setNro_doc_persona(String nro_doc_persona) {
        this.nro_doc_persona = nro_doc_persona;
    }

    public String getNro_doc_trabajador() {
        return nro_doc_trabajador;
    }

    public void setNro_doc_trabajador(String nro_doc_trabajador) {
        this.nro_doc_trabajador = nro_doc_trabajador;
    }

    public String getCategoria_sancion_id() {
        return categoria_sancion_id;
    }

    public void setCategoria_sancion_id(String categoria_sancion_id) {
        this.categoria_sancion_id = categoria_sancion_id;
    }

    public String getId_reg_laboral() {
        return id_reg_laboral;
    }

    public void setId_reg_laboral(String id_reg_laboral) {
        this.id_reg_laboral = id_reg_laboral;
    }

    public Long getId_tipo_sancion() {
        return id_tipo_sancion;
    }

    public void setId_tipo_sancion(Long id_tipo_sancion) {
        this.id_tipo_sancion = id_tipo_sancion;
    }

    public String getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(String estado_id) {
        this.estado_id = estado_id;
    }

    public String getB_datos_persona() {
        return b_datos_persona;
    }

    public void setB_datos_persona(String b_datos_persona) {
        this.b_datos_persona = b_datos_persona;
    }

    public String getB_datos_trabajador() {
        return b_datos_trabajador;
    }

    public void setB_datos_trabajador(String b_datos_trabajador) {
        this.b_datos_trabajador = b_datos_trabajador;
    }

    public Boolean getEstrabajador() {
        return estrabajador;
    }

    public void setEstrabajador(Boolean estrabajador) {
        this.estrabajador = estrabajador;
    }

    public Boolean getVeditar() {
        return veditar;
    }

    public void setVeditar(Boolean veditar) {
        this.veditar = veditar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
}