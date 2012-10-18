/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arson
 */
@Entity
@Table(name = "LK_OPCELIMINAR_DATOAUX")
public class LkOpcDatoAuxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String tabla;

    public LkOpcDatoAuxiliar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
    
}
