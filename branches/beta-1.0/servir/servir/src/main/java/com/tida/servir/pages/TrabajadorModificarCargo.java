/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class TrabajadorModificarCargo  extends GeneralPage {
    @Inject
    private Session session;
    /*
    @PageActivationContext
    private CargoAsignado cargoAsignado;
*/
}
