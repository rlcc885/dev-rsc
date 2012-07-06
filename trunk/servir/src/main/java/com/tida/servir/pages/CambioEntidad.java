/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Entidad_BK;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class CambioEntidad  extends GeneralPage
{
    @Property
    @SessionState(create=false)
    private Entidad eue;

    @Inject
    private Session session;
    
    @SetupRender
    void initValues() {
        if (eue == null) {
            Criteria c = session.createCriteria(Entidad.class);
            c.add(Restrictions.ne("estado", true));
            eue = (Entidad) c.list().get(0);
        }
    }
}
