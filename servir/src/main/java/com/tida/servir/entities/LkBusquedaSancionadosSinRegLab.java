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
public class LkBusquedaSancionadosSinRegLab implements Serializable{
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
    private Long id_tipo_sancion;
    private String b_datos_persona;
    private String b_datos_trabajador;
    private Boolean estrabajador;
    private Boolean veditar;
    private Boolean veditar_anular;
    private String cargo;
    private String tipo_doc_notifica;
    private String descripcion_doc_notifica;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_doc_notifica;
    private String autoridad_notifica;
    private String descripcion_doc_sanciona;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_doc_sanciona;
    private String autoridad_sanciona;
    private String observaciones;
    private String causa_destitucion; 
    private String direccion_entidad;
    @NonVisual
    private String tipo_doc_identidad;

    public Boolean getVeditar_anular() {
        return veditar_anular;
    }

    public void setVeditar_anular(Boolean veditar_anular) {
        this.veditar_anular = veditar_anular;
    }

    
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

    public String getCategoria_sancion_id() {
        return categoria_sancion_id;
    }

    public void setCategoria_sancion_id(String categoria_sancion_id) {
        this.categoria_sancion_id = categoria_sancion_id;
    }

    public Long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(Long entidad_id) {
        this.entidad_id = entidad_id;
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

    public String getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(String estado_id) {
        this.estado_id = estado_id;
    }

    public Boolean getEstrabajador() {
        return estrabajador;
    }

    public void setEstrabajador(Boolean estrabajador) {
        this.estrabajador = estrabajador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(Long id_sancion) {
        this.id_sancion = id_sancion;
    }

    public Long getId_tipo_sancion() {
        return id_tipo_sancion;
    }

    public void setId_tipo_sancion(Long id_tipo_sancion) {
        this.id_tipo_sancion = id_tipo_sancion;
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

    public String getTiempo_restante() {
        return tiempo_restante;
    }

    public void setTiempo_restante(String tiempo_restante) {
        this.tiempo_restante = tiempo_restante;
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

    public String getTipo_sancion() {
        return tipo_sancion;
    }

    public void setTipo_sancion(String tipo_sancion) {
        this.tipo_sancion = tipo_sancion;
    }

    public Boolean getVeditar() {
        return veditar;
    }

    public void setVeditar(Boolean veditar) {
        this.veditar = veditar;
    }

    public String getAutoridad_notifica() {
        return autoridad_notifica;
    }

    public void setAutoridad_notifica(String autoridad_notifica) {
        this.autoridad_notifica = autoridad_notifica;
    }

    public String getAutoridad_sanciona() {
        return autoridad_sanciona;
    }

    public void setAutoridad_sanciona(String autoridad_sanciona) {
        this.autoridad_sanciona = autoridad_sanciona;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCausa_destitucion() {
        return causa_destitucion;
    }

    public void setCausa_destitucion(String causa_destitucion) {
        this.causa_destitucion = causa_destitucion;
    }

    public String getDescripcion_doc_notifica() {
        return descripcion_doc_notifica;
    }

    public void setDescripcion_doc_notifica(String descripcion_doc_notifica) {
        this.descripcion_doc_notifica = descripcion_doc_notifica;
    }

    public String getDescripcion_doc_sanciona() {
        return descripcion_doc_sanciona;
    }

    public void setDescripcion_doc_sanciona(String descripcion_doc_sanciona) {
        this.descripcion_doc_sanciona = descripcion_doc_sanciona;
    }

    public String getDireccion_entidad() {
        return direccion_entidad;
    }

    public void setDireccion_entidad(String direccion_entidad) {
        this.direccion_entidad = direccion_entidad;
    }

    public Date getFecha_doc_notifica() {
        return fecha_doc_notifica;
    }

    public void setFecha_doc_notifica(Date fecha_doc_notifica) {
        this.fecha_doc_notifica = fecha_doc_notifica;
    }

    public Date getFecha_doc_sanciona() {
        return fecha_doc_sanciona;
    }

    public void setFecha_doc_sanciona(Date fecha_doc_sanciona) {
        this.fecha_doc_sanciona = fecha_doc_sanciona;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo_doc_notifica() {
        return tipo_doc_notifica;
    }

    public void setTipo_doc_notifica(String tipo_doc_notifica) {
        this.tipo_doc_notifica = tipo_doc_notifica;
    }

    public String getTipo_doc_ientidad() {
        return tipo_doc_identidad;
    }

    public void setTipo_doc_ientidad(String tipo_doc_ientidad) {
        this.tipo_doc_identidad = tipo_doc_ientidad;
    }
    
}