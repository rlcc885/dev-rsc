/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

/**
 *
 * @author mallco
 */

@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpSancionRegimen",
    query = "CALL SP_SANCIONREGIMEN(?,:reg_id)",
    resultClass = LkSancionRegimen.class,
    hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    }) 
})

@Entity
@Table(name = "LKSANCIONREGIMEN")
public class LkSancionRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String tiposancion;
    @Basic(optional = false)
    @Column(name = "TIPOSANCION_ID")
    private Long tiposancionId;
    private String categoria;
    @Basic(optional = false)
    @Column(name = "REGLABORAL_ID")
    private Long reglaboralId;
    private Boolean opcion;

    public LkSancionRegimen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiposancion() {
        return tiposancion;
    }

    public void setTiposancion(String tiposancion) {
        this.tiposancion = tiposancion;
    }

    public Long getTiposancionId() {
        return tiposancionId;
    }

    public void setTiposancionId(Long tiposancionId) {
        this.tiposancionId = tiposancionId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getReglaboralId() {
        return reglaboralId;
    }

    public void setReglaboralId(Long reglaboralId) {
        this.reglaboralId = reglaboralId;
    }

    public Boolean getOpcion() {
        return opcion;
    }

    public void setOpcion(Boolean opcion) {
        this.opcion = opcion;
    }
    
}
