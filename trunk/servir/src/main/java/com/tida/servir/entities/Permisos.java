/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import javax.persistence.Table;

/**
 * Define permisos según el usuario
 * @author ale
 */
@Table(name = "RSC_PERMISOS")
public class Permisos {

    /**
     * Dado un usuario ver si tiene permisos de escritura. Es sólo usuario porque no 
     * hay permiso de lecto-escritura por página
     * @param u
     * @return
     */
    public static boolean puedeEscribir(Usuario u, Entidad eue) {
        if (u.getTipo_usuario().equals(Usuario.OPERADORLECTURALOCAL)
                || u.getTipo_usuario().equals(Usuario.OPERADORANALISTA) ) {
            return false;
        }
//TODO JZM 01/07/2012 Revisar este código
//        if (u.getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)) {
//            if (eue.def_servir == null)
//                return true; // Permitimos carga via servir si no se especifica otra cosa
//            return eue.def_servir;
//        }
        return true; // El resto puede escribir
    }
    
    public static boolean puedeEscribirBK(Usuario u, Entidad eue) {
        if (u.getTipo_usuario().equals(Usuario.OPERADORLECTURALOCAL)
                || u.getTipo_usuario().equals(Usuario.OPERADORANALISTA) ) {
            return false;
        }
/*
        if (u.getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)) {
            if (eue.def_servir == null)
                return true; // Permitimos carga via servir si no se especifica otra cosa
            return eue.def_servir;
        }
        * 
        */
        return true; // El resto puede escribir
    }

    public static String paginaInicial(Usuario u) {
        if (u.getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)
         || u.getTipo_usuario().equals(Usuario.OPERADORANALISTA)) {
           // return "CambioEntidad";
            return "Alerta";
        }

        if ( u.getTipo_usuario().equals(Usuario.ADMINLOCAL) ||
                u.getTipo_usuario().equals(Usuario.ADMINGRAL)) {
            //return "ABMUsuario";
            return "Alerta";
        }

        if (u.getTipo_usuario().equals(Usuario.ADMINSISTEMA)) {
            //return "ABMDatoAuxiliar";
            return "Alerta";
        }

        if (u.getTipo_usuario().equals(Usuario.OPERADORABMLOCAL)
                || u.getTipo_usuario().equals(Usuario.OPERADORLECTURALOCAL)) {
            //return "Busqueda";
            //return "ABMDatoAuxiliar";
            return "Alerta";
        }
        
        if(u.getTipo_usuario().equals(Usuario.TRABAJADOR)){
            return "TrabajadorEditar";
        }

        System.out.println("Usuario sin ningun rol");
        return null; // Si no tiene ningún rol....

    }
}