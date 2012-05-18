/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

/**
 *
 * @author Morgan
 */
public class UbigeoSectorPliego {
    
    private DatoAuxiliar sector;
    private DatoAuxiliar pliego;

    public DatoAuxiliar getPliego() {
        return pliego;
    }

    public void setPliego(DatoAuxiliar pliego) {
        this.pliego = pliego;
    }

    public DatoAuxiliar getSector() {
        return sector;
    }

    public void setSector(DatoAuxiliar sector) {
        this.sector = sector;
    }
    
}
