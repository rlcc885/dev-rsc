/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import java.util.ArrayList;

/**
 *
 * @author Morgan
 */
public class InformeSalida<T> {

    private ArrayList<T> lt;
    private Integer alta;
    private Integer modificado;
    private Integer cambio;

    private Integer rechazado;
    private Integer falta;

    
    public InformeSalida() {
        this.lt = new ArrayList<T>();
        this.alta = 0;
        this.modificado = 0;
        this.cambio = 0;
        this.rechazado = 0;
        this.falta = 0;
    }
    
    public Integer getAlta() {
        return alta;
    }

    public void setAlta(Integer alta) {
        this.alta = alta;
    }

    public Integer getCambio() {
        return cambio;
    }

    public void setCambio(Integer cambio) {
        this.cambio = cambio;
    }

    public Integer getFalta() {
        return falta;
    }

    public void setFalta(Integer falta) {
        this.falta = falta;
    }

    public ArrayList<T> getLt() {
        return lt;
    }

    public void setLt(ArrayList<T> lt) {
        this.lt = lt;
    }

    public Integer getModificado() {
        return modificado;
    }

    public void setModificado(Integer modificado) {
        this.modificado = modificado;
    }

    public Integer getRechazado() {
        return rechazado;
    }

    public void setRechazado(Integer rechazado) {
        this.rechazado = rechazado;
    }
}
