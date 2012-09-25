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

@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpCalcularPeriodo",
        query = "CALL SP_CALCULARPERIODO(?,:fecha_ini,:fecha_fin)",
        resultClass = LkConsultaPeriodo.class,
        hints = {
            @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
public class LkConsultaPeriodo implements Serializable {    
    
    public LkConsultaPeriodo() {
    }
    
    private int dias;

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
    
    
    
}