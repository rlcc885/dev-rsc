/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

/**
 * Define el objeto de niveles ocupacionales
 * @author ale
 */
public class Ocupacional {

    private DatoAuxiliar grupoOcupacional;
    private DatoAuxiliar categoriaOcupacional;

    public DatoAuxiliar getCategoriaOcupacional() {
        return categoriaOcupacional;
    }

    public void setCategoriaOcupacional(DatoAuxiliar CategoriaOcupacional) {
        this.categoriaOcupacional = CategoriaOcupacional;
    }

    public DatoAuxiliar getGrupoOcupacional() {
        return grupoOcupacional;
    }

    public void setGrupoOcupacional(DatoAuxiliar GrupoOcupacional) {
        this.grupoOcupacional = GrupoOcupacional;
    }
}
