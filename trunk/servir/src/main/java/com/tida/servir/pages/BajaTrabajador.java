/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
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
    private DatoAuxiliar tipoDocumento;

    @InjectComponent
    private Zone cargosGrillaZone;

    public List<Trabajador> getEmpleados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.createAlias("legajo", "legajo");
        c.createAlias("cargo", "cargo");

        c.add(Restrictions.eq("legajo.entidadUE", _entidadUE));


        if (nroDocumento != null) {
            c.add(Restrictions.eq("trabajador.nroDocumento", nroDocumento));
        }
//        if (tipoDocumento != null ) {
//            c.add(Restrictions.like("trabajador.documentoidentidad", tipoDocumento));
//        }
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        c.setProjection(Projections.distinct(Projections.property("trabajador")));

        return c.list();
    }


    //para obtener datos del Tipo de documento
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTiposDoc() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
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