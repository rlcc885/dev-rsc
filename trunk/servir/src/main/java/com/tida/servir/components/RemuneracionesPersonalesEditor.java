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
    @Persist
    @Property
    private ConceptoRemunerativo conceptoremun;
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
    }

    @Log
    public GenericSelectModel<ConceptoRemunerativo> getBeanConceptoRemunerativo() {
        List<ConceptoRemunerativo> list;
        Criteria c = session.createCriteria(ConceptoRemunerativo.class);
        c.add(Restrictions.eq("entidad", _oi));
        list = c.list();
        return new GenericSelectModel<ConceptoRemunerativo>(list, ConceptoRemunerativo.class, "descripcion", "id", _access);
    }

    @Log
    public List<RemuneracionPersonal> getListadoRemuneraciones() {
        Criteria c = session.createCriteria(RemuneracionPersonal.class);
        c.add(Restrictions.eq("cargoAsignado", getCargosAsignados()));
        return c.list();
    }

    @Log
    public CargoAsignado getCargosAsignados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("legajo", "legajo");
        c.add(Restrictions.eq("trabajador", actual));
        c.add(Restrictions.eq("legajo.entidad", _oi));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List result = c.list();
        cargoAsignado = (CargoAsignado) result.get(0);

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
    //@CommitAfter    
    Object onSuccess() {
        if (elemento == 1) {
            remuneracion = new RemuneracionPersonal();
            conceptoremun = null;
            return new MultiZoneUpdate("remuneracionesZone", remuneracionesZone.getBody())
                    .add("mensajesCRZone", mensajesCRZone.getBody());
        } else if (elemento == 2) {
            return "Busqueda";
        } else if (elemento == 3) {
            System.out.println("aaaa111: " + remuneracion.getImporte());
            //remuneracion.setConceptoRemunerativo(conceptoremun);
            //System.out.println("aaaa222: "+remuneracion.getConceptoRemunerativo().getDescripcion());

            //remuneracion.setCargoAsignado(getCargosAsignados());

            //System.out.println("aaaa333: "+remuneracion.getCargoAsignado().getId());

            //session.saveOrUpdate(remuneracion);
            envelope.setContents(helpers.Constantes.REMUNERACION_EXITO);
            remuneracion = new RemuneracionPersonal();
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
        envelope.setContents("Se realizo la elimiación satisfactoriamente");
        return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody());
    }
}