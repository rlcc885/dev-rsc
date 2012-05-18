/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.entities;

/**
 *
 * @author Morgan
 */
public class RegimenGrupoNivel {
    
    private DatoAuxiliar regimen;
    private DatoAuxiliar grupo;
    private DatoAuxiliar nivelRemunerativo;

    public DatoAuxiliar getNivelRemunerativo() {
        return nivelRemunerativo;
    }

    public void setNivelRemunerativo(DatoAuxiliar nivelRemunerativo) {
        this.nivelRemunerativo = nivelRemunerativo;
    }

    public DatoAuxiliar getGrupo() {
        return grupo;
    }

    public void setGrupo(DatoAuxiliar grupo) {
        this.grupo = grupo;
    }

    public DatoAuxiliar getRegimen() {
        return regimen;
    }

    public void setRegimen(DatoAuxiliar regimen) {
        this.regimen = regimen;
    }
    
}
