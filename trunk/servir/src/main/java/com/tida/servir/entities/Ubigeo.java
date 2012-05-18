/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

/**
 * Clase que abstrae una ubicacion geográfica sin pais
 * @author ale
 */

public class Ubigeo {

    private DatoAuxiliar departamento;
    private DatoAuxiliar provincia;
    private DatoAuxiliar distrito;

    public DatoAuxiliar getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DatoAuxiliar departamento) {
        this.departamento = departamento;
    }

    public DatoAuxiliar getDistrito() {
        return distrito;
    }

    public void setDistrito(DatoAuxiliar distrito) {
        this.distrito = distrito;
    }

    public DatoAuxiliar getProvincia() {
        return provincia;
    }

    public void setProvincia(DatoAuxiliar provincia) {
        this.provincia = provincia;
    }

    
}
