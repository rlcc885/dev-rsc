/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Usuario;
import org.apache.tapestry5.PersistenceConstants;
import org.hibernate.Session;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
/**
 *
 * @author ale
 */
public class InformacionUO extends GeneralPage {
    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario loggedUser;

    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;


    @SetupRender
    private void setupWebService() {
        /*
        try {
            //FIXME TODO Usar el locator
            IntegracionFacadeBean_Service server = new IntegracionFacadeBean_Service(new URL("http://190.41.207.58:8090/rufe-ear-rufe-negocio/IntegracionFacadeBean?wsdl"), new QName("http://facade.integracion.rufe.pcm.gob.pe/", "IntegracionFacadeBean"));
            //FIXME TODO Usar el _ServicePortType

            //EntidadResultsDTO entidad = port.Entidad();

        } catch (MalformedURLException ex) {
            mensajes = "Problema obteniendo webservice<br/> Consulte con un administrador <br/> Error: <br/>" + ex.getLocalizedMessage();
            Logger.getLogger(InformacionUO.class.getName()).log(Level.SEVERE, null, ex);
        }
*/

     }
}
