/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class ABMSancion  extends GeneralPage
{
    @Property
    @SessionState
    private Entidad eue;

    @Inject
    private Session session;
    @Property
    @Persist
    private String mensaje;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private DatoAuxiliar btipodocumento;
    
    
    
    // inicio de la pagina
    
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeantipodocumento() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar_td("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

}
