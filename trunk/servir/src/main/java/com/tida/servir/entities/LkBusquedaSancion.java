/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mallco
 */
@Entity
@Table(name = "LKBUSQUEDASANCION")
public class LkBusquedaSancion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id_sancion;
    private Long entidad_id;
    private String apellidopat_persona;
    private String apellidomat_persona;
    private String nombres_persona;
    private Long tipo_doc_persona;
    private String apellidopat_trabajador;
    private String apellidomat_trabajador;
    private String nombres_trabajador;
    private Long tipo_doc_trabajador;
    private String tipo_doc_identidad;
    private String entidad_subentidad;
    private String direccion_entidad;
    private Long id_tipo_sancion;
    private String tipo_sancion;
    private String nro_doc_persona;
    private String nro_doc_trabajador;
    private Long categoria_sancion_id;
    private Long id_reg_laboral;
    private String tiempo_Restante;
    private String estado;
    private String estado_id;
    private String b_datos_persona;
    private String b_datos_trabajador;
    private Boolean estrabajador;
    private Boolean veditar;
    private Boolean veditar_anular;
    private String cargo;
    private String tipo_doc_notifica;
    private String tipo_doc_sanciona;
    @Column(name = "DESCRIPCION_DOC_NOTIFICA")
    private String descripcionDocNotifica;
    @Column(name = "FECHA_DOC_NOTIFICA")
    @Temporal(TemporalType.DATE)
    private Date fechaDocNotifica;
    @Column(name = "AUTORIDAD_NOTIFICA")
    private String autoridadNotifica;
    @Column(name = "DESCRIPCION_DOC_SANCIONA")
    private String descripcionDocSanciona;
    @Column(name = "FECHA_DOC_SANCIONA")
    @Temporal(TemporalType.DATE)
    private Date fechaDocSanciona;
    @Column(name = "AUTORIDAD_SANCIONA")
    private String autoridadSanciona;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "CAUSA_DESTITUCION")
    private String causaDestitucion;
    @Column(name = "INI_INHABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iniInhabilitacion;
    @Column(name = "FIN_INHABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finInhabilitacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
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

    public Boolean getVeditar_anular() {
        return veditar_anular;
    }

    public void setVeditar_anular(Boolean veditar_anular) {
        this.veditar_anular = veditar_anular;
    }

    
    
    public LkBusquedaSancion() {
    }

    public String getTiempo_Restante() {
        return tiempo_Restante;
    }

    public void setTiempo_Restante(String tiempo_Restante) {
        this.tiempo_Restante = tiempo_Restante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_sancion() {
        return id_sancion;
    }

    public void setId_sancion(Long id_sancion) {
        this.id_sancion = id_sancion;
    }

    public Long getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(Long entidad_id) {
        this.entidad_id = entidad_id;
    }

    public String getApellidopat_persona() {
        return apellidopat_persona;
    }

    public void setApellidopat_persona(String apellidopat_persona) {
        this.apellidopat_persona = apellidopat_persona;
    }

    public String getApellidomat_persona() {
        return apellidomat_persona;
    }

    public void setApellidomat_persona(String apellidomat_persona) {
        this.apellidomat_persona = apellidomat_persona;
    }

    public String getNombres_persona() {
        return nombres_persona;
    }

    public void setNombres_persona(String nombres_persona) {
        this.nombres_persona = nombres_persona;
    }

    public Long getTipo_doc_persona() {
        return tipo_doc_persona;
    }

    public void setTipo_doc_persona(Long tipo_doc_persona) {
        this.tipo_doc_persona = tipo_doc_persona;
    }



    public String getApellidopat_trabajador() {
        return apellidopat_trabajador;
    }

    public void setApellidopat_trabajador(String apellidopat_trabajador) {
        this.apellidopat_trabajador = apellidopat_trabajador;
    }

    public String getApellidomat_trabajador() {
        return apellidomat_trabajador;
    }

    public void setApellidomat_trabajador(String apellidomat_trabajador) {
        this.apellidomat_trabajador = apellidomat_trabajador;
    }

    public String getNombres_trabajador() {
        return nombres_trabajador;
    }

    public void setNombres_trabajador(String nombres_trabajador) {
        this.nombres_trabajador = nombres_trabajador;
    }

    public Long getTipo_doc_trabajador() {
        return tipo_doc_trabajador;
    }

    public void setTipo_doc_trabajador(Long tipo_doc_trabajador) {
        this.tipo_doc_trabajador = tipo_doc_trabajador;
    }

    public String getTipo_doc_identidad() {
        return tipo_doc_identidad;
    }

    public void setTipo_doc_identidad(String tipo_doc_identidad) {
        this.tipo_doc_identidad = tipo_doc_identidad;
    }

   public String getEntidad_subentidad() {
        return entidad_subentidad;
    }

    public void setEntidad_subentidad(String entidad_subentidad) {
        this.entidad_subentidad = entidad_subentidad;
    }

    public String getDireccion_entidad() {
        return direccion_entidad;
    }

    public void setDireccion_entidad(String direccion_entidad) {
        this.direccion_entidad = direccion_entidad;
    }

    public Long getId_tipo_sancion() {
        return id_tipo_sancion;
    }

    public void setId_tipo_sancion(Long id_tipo_sancion) {
        this.id_tipo_sancion = id_tipo_sancion;
    }

    public String getTipo_sancion() {
        return tipo_sancion;
    }

    public void setTipo_sancion(String tipo_sancion) {
        this.tipo_sancion = tipo_sancion;
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

    public Long getCategoria_sancion_id() {
        return categoria_sancion_id;
    }

    public void setCategoria_sancion_id(Long categoria_sancion_id) {
        this.categoria_sancion_id = categoria_sancion_id;
    }

    public Long getId_reg_laboral() {
        return id_reg_laboral;
    }

    public void setId_reg_laboral(Long id_reg_laboral) {
        this.id_reg_laboral = id_reg_laboral;
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


    public String getTipo_doc_notifica() {
        return tipo_doc_notifica;
    }

    public void setTipo_doc_notifica(String tipo_doc_notifica) {
        this.tipo_doc_notifica = tipo_doc_notifica;
    }

    public String getTipo_doc_sanciona() {
        return tipo_doc_sanciona;
    }

    public void setTipo_doc_sanciona(String tipo_doc_sanciona) {
        this.tipo_doc_sanciona = tipo_doc_sanciona;
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }



    public String getDescripcionDocNotifica() {
        return descripcionDocNotifica;
    }

    public void setDescripcionDocNotifica(String descripcionDocNotifica) {
        this.descripcionDocNotifica = descripcionDocNotifica;
    }

    public Date getFechaDocNotifica() {
        return fechaDocNotifica;
    }

    public void setFechaDocNotifica(Date fechaDocNotifica) {
        this.fechaDocNotifica = fechaDocNotifica;
    }

    public String getAutoridadNotifica() {
        return autoridadNotifica;
    }

    public void setAutoridadNotifica(String autoridadNotifica) {
        this.autoridadNotifica = autoridadNotifica;
    }

    public String getDescripcionDocSanciona() {
        return descripcionDocSanciona;
    }

    public void setDescripcionDocSanciona(String descripcionDocSanciona) {
        this.descripcionDocSanciona = descripcionDocSanciona;
    }

    public Date getFechaDocSanciona() {
        return fechaDocSanciona;
    }

    public void setFechaDocSanciona(Date fechaDocSanciona) {
        this.fechaDocSanciona = fechaDocSanciona;
    }

    public String getAutoridadSanciona() {
        return autoridadSanciona;
    }

    public void setAutoridadSanciona(String autoridadSanciona) {
        this.autoridadSanciona = autoridadSanciona;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCausaDestitucion() {
        return causaDestitucion;
    }

    public void setCausaDestitucion(String causaDestitucion) {
        this.causaDestitucion = causaDestitucion;
    }

    public Date getIniInhabilitacion() {
        return iniInhabilitacion;
    }

    public void setIniInhabilitacion(Date iniInhabilitacion) {
        this.iniInhabilitacion = iniInhabilitacion;
    }

    public Date getFinInhabilitacion() {
        return finInhabilitacion;
    }

    public void setFinInhabilitacion(Date finInhabilitacion) {
        this.finInhabilitacion = finInhabilitacion;
    }
    
}