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
@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpFucionMigracion",
        query = "CALL SP_MIGRACIONFUCION(?,:as_entidad_id_origen,:as_entidad_id_destino,:an_unidad_origen,:an_unidad_destino,:an_tipo_proceso)",
        resultClass = LkBusquedaCursos.class,
        hints = {
            @QueryHint(name = "org.hibernate.callable", value = "true")
    }),
    @NamedNativeQuery(name = "callSpTransferencia",
    query = "CALL SP_TRANSFERENCIA(?,:as_entidad_id_origen,:as_entidad_id_destino)",
    resultClass = LkBusquedaCursos.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
public class LkBusquedaCursos implements Serializable {
    @Id
    @NonVisual
    private long id;
    private String denominacion;
    private String tipo_curso;
    private String centro_estudio;
    private Boolean estado;
    private Boolean validado;
    @NonVisual
    private long trabajador; 

    
    public LkBusquedaCursos() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
     public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
    public String getTipo_curso() {
        return tipo_curso;
    }

    public void setTipo_curso(String tipo_curso) {
        this.tipo_curso = tipo_curso;
    }
    
    public String getCentro_estudio() {
        return centro_estudio;
    }

    public void setCentro_estudio(String centro_estudio) {
        this.centro_estudio = centro_estudio;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }
    
    //@ManyToOne(optional = false)
    public long getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(long trabajador) {
        this.trabajador = trabajador;
    }
    
   
}
