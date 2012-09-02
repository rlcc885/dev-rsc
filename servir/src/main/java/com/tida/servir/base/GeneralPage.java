/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.base;

import com.tida.servir.entities.Usuario;
import com.tida.servir.pages.SesionTimeout;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;

/**
 * Página general de la que heredan las otras. Para manejar excepciones de
 * session, etc
 *
 * @author ale
 */
public class GeneralPage {

    @SessionState
    private Usuario _loggedUser;
    private boolean _loggedUserExists;
    @InjectPage
    private SesionTimeout _sesionTimeout;

    Object onActivate() {

        if (!isLoggedUserExists()) {
            return _sesionTimeout;
        }

        return null;
    }

    public Usuario getLoggedUser() {
        return _loggedUser;
    }

    public boolean isLoggedUserExists() {
        return _loggedUserExists;
    }
}
