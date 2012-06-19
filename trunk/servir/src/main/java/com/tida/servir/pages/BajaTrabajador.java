/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Constantes;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Da de baja un cargo asignado. Permite la selección de un trabajador.
 * @author ale
 */
public class BajaTrabajador extends GeneralPage {
    @Inject
    private Session session;
    @Property
    private Trabajador actual;
    private PropertyAccess _access;
    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
    @Property
    @SessionState
    private Entidad_BK _entidadUE;

    @Property
    @SessionState
    private Usuario _usuario;

    @Component(id = "formulariobusquedasfiltros")
    private Form formulariobusquedasfiltros;

    @Persist
    @Property
    private String nroDocumento;

    @Persist
    @Property
    private String tipoDocumento;

    @InjectComponent
    private Zone cargosGrillaZone;

    public List<Trabajador> getEmpleados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.createAlias("legajo", "legajo");
        c.createAlias("cargo", "cargo");

        c.add(Restrictions.eq("legajo.entidadUE", _entidadUE));


        if (nroDocumento != null) {
            System.out.println("nroDocumento");
            c.add(Restrictions.eq("trabajador.nroDocumento", nroDocumento));
        }
        if (tipoDocumento != null && !tipoDocumento.equals("")) {
            System.out.println("tipoDocumento");
            c.add(Restrictions.like("trabajador.tipoDocumento", tipoDocumento));
        }
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        c.setProjection(Projections.distinct(Projections.property("trabajador")));

        return c.list();
    }


    public List<String> getTiposDoc() {
        return Helpers.getValorTablaAuxiliar("TipoDocumento", session);
    }

    public Object onSuccessFromFormulariobusquedasfiltros() {
        List<Trabajador> t = getEmpleados();
        if (t.size() > 0) {
            actual = t.get(0);
        } else {
            actual = null;
        }
        return cargosGrillaZone.getBody();
    }
}
