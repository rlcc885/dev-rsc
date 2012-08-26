/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.entities.CrearDatos;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class NuevasTablas {

        @Inject
    private Session session;


    @Component(id = "formulario")
    private Form formulario;

    public NuevasTablas() {

    }

    @CommitAfter
    Object onSuccessFromFormulario(){
//        CrearDatos.crearFormacionProfesional(session);
//        CrearDatos.crearCodigosEstructuralesCargo(session);
 //   	CrearDatos.sanearDatosAuxiliaresDeConceptosRemunerativos(session);
//        CrearDatos.ocupacionalesBasicos(session);
//        CrearDatos.crearFormacionProfesional(session);
        CrearDatos.crearDatosUE(session);
        return this;
    }

}
