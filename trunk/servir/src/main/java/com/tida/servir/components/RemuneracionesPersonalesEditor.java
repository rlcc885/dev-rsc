package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Clase que maneja el TAB del editor de Remuneraciones.
 *
 */
public class RemuneracionesPersonalesEditor {

    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private Request _request;
    @Component(id = "formulariomensajesCR")
    private Form formulariomensajesCR;
    @InjectComponent
    private Zone mensajesCRZone;
    @InjectComponent
    private Zone remuneracionesZone;
    private int elemento = 0;
    @Parameter
    @Property
    private Trabajador actual;
    @Persist
    @Property
    private RemuneracionPersonal remuneracion;
    @Persist
    @Property
    private CargoAsignado cargoAsignado;
//    @Persist
//    @Property
//    private ConceptoRemunerativo conceptoremun;
    //Listado de Remuneraciones
    @InjectComponent
    private Zone listaRemuneracionesZone;
    @Persist
    @Property
    private RemuneracionPersonal listaRemuneraciones;
    @Inject
    private PropertyAccess _access;

    //Inicio de la carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
        remuneracion = new RemuneracionPersonal();
        listaRemuneraciones = new RemuneracionPersonal();
        getCargosAsignados();
        elemento = 0;
    }

    @Log
    public GenericSelectModel<ConceptoRemunerativo> getBeanConceptoRemunerativo() {
        List<ConceptoRemunerativo> list1;
        Criteria c1 = session.createCriteria(ConceptoRemunerativo.class);
        c1.add(Restrictions.eq("entidad", _oi));
        list1 = c1.list();
        return new GenericSelectModel<ConceptoRemunerativo>(list1, ConceptoRemunerativo.class, "descripcion", "id", _access);
    }

    @Log
    public List<RemuneracionPersonal> getListadoRemuneraciones() {
        Criteria c2 = session.createCriteria(RemuneracionPersonal.class);
        c2.add(Restrictions.eq("cargoAsignado", cargoAsignado));
        return c2.list();
    }

    @Log
    public CargoAsignado getCargosAsignados() {
        Criteria c3 = session.createCriteria(CargoAsignado.class);
        c3.add(Restrictions.eq("trabajador", actual));
        List result3 = c3.list();
        cargoAsignado = (CargoAsignado) result3.get(0);

        return cargoAsignado;
    }

    @Log
    void onSelectedFromCancelCR() {
        elemento = 2;
    }

    @Log
    void onSelectedFromResetCR() {
        elemento = 1;
    }

    @Log
    void onSelectedFromGuardarCR() {
        elemento = 3;

    }

    @Log
    @CommitAfter
    Object onSuccess() {
        if (elemento == 1) {
            remuneracion = new RemuneracionPersonal();
            elemento = 0;
            //conceptoremun = null;
            return new MultiZoneUpdate("remuneracionesZone", remuneracionesZone.getBody())
                    .add("mensajesCRZone", mensajesCRZone.getBody());
        } else if (elemento == 2) {
            elemento = 0;
            return "Busqueda";
        } else if (elemento == 3) {
            System.out.println("aaaa111: " + remuneracion.getImporte());

            remuneracion.setCargoAsignado(getCargosAsignados());

            session.saveOrUpdate(remuneracion);
            envelope.setContents(helpers.Constantes.REMUNERACION_EXITO);
            remuneracion = new RemuneracionPersonal();
            elemento = 0;
            return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                    .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody())
                    .add("remuneracionesZone", remuneracionesZone.getBody());
        } else {
            return this;
        }

    }

    @Log
    Object onActionFromEditarCR(RemuneracionPersonal remu) {
        remuneracion = remu;
        return new MultiZoneUpdate("remuneracionesZone", remuneracionesZone.getBody());
    }

    @Log
    @CommitAfter
    Object onActionFromEliminarCR(RemuneracionPersonal remu) {
        session.delete(remu);
        envelope.setContents("Remuneraciones personales elimanadas exitosamente.");
        return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody());
    }
}