package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja la segunda pagina en la creacion de un Trabajador
 */
@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
public class AsignarNuevoCargo extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @SessionState
    private Entidad_BK _entidadUE;
    @InjectPage
    private Busqueda busqueda;
    @Property
    @PageActivationContext
    private Trabajador trabajador;
    @Component(id = "formularioasignar")
    private Form formAsignar;
    @Component(id = "formulariounidadorganica")
    private Form formulariounidadorganica;
    @Property
    private Date fec_inicio;
    @Property
    private CargoAsignado cargoAsignado;
    @Property
    private Integer ctd_per_superv;
    @Property
    @SessionState
    private Usuario _usuario;
    @Persist
    private Cargoxunidad cargo;
    @Property
    @Persist
    private UnidadOrganica unidadorganica;
    @Property
    private String tipoVinculo;
    @InjectComponent
    @Property
    private Zone unidadOrganicaZone;
    @InjectComponent
    @Property
    private Zone cargosZone;
    @Property
    @Persist
    private Boolean unidadSeleccionada;
    @Inject
    private PropertyAccess _access;
    //private GenericSelectModel<Cargo> _beans;


    public Cargoxunidad getcargo() {
        return cargo;
    }

    @Log
    public List<String> getBeansTipoVinculo() {
        return Helpers.getValorTablaAuxiliar("TipoVínculo", session);
    }

    public CargosSelectModel<Cargoxunidad> getBeans() {
        Criteria c = session.createCriteria(Cargoxunidad.class)
                .add(Restrictions.eq("und_organica", unidadorganica))
                .add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));

        List<Cargoxunidad> list = c.list();
        
        cargo = (Cargoxunidad) c.list().get(0);
            
        return new CargosSelectModel<Cargoxunidad>(list, Cargoxunidad.class, "cod_cargo", "den_cargo", "id", _access);
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeansUO() {
        Criteria c = session.createCriteria(UnidadOrganica.class)
                .add(Restrictions.eq("entidadUE", _entidadUE))
                .add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));

        List<UnidadOrganica> list = c.list();
     
        return new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    public void setCargo(Cargoxunidad cargo) {
        this.cargo = cargo;
    }

    public AsignarNuevoCargo() {
    }

    @Log
    void onValidateFromformularioasignar() {
        if (Helpers.getCantPuestosOcupadosCargo(session, cargo) >= cargo.getCtd_puestos_total()) {
            formAsignar.recordError(Errores.ERROR_CARGO_OCUPADO);
        }

        if (fec_inicio.after(new Date())) {
            Logger logger = new Logger();
            logger.loguearError(session, _usuario, "0",
                    Logger.CODIGO_ERROR_FECHA_PREVIA_ACTUAL,
                    Errores.ERROR_FECHA_PREVIA_ACTUAL,
                    Logger.TIPO_OBJETO_CARGO_ASIGNADO);

            formAsignar.recordError(Errores.ERROR_FECHA_PREVIA_ACTUAL);

        }

    }

    @Log
    Object onFailureFromformularioasignar() {
        return this;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioasignar() {

        cargoAsignado = new CargoAsignado();
        cargoAsignado.setCargoxunidad(cargo);

        //Buscamos el legajo de este trabajador en este oi (hay uno solo)
        Criteria crit = session.createCriteria(Legajo.class);
        crit.add(Restrictions.eq("entidadUE", _entidadUE));
        crit.add(Restrictions.eq("trabajador", trabajador));

        cargoAsignado.setLegajo((Legajo) crit.list().get(0)); //tomo el primer
        cargoAsignado.setFec_inicio(fec_inicio);
        cargoAsignado.setCtd_per_superv(ctd_per_superv);
        cargoAsignado.setTrabajador(trabajador);
        cargoAsignado.setEstado(Constantes.ESTADO_ACTIVO);
        cargoAsignado.setTipoVinculo(tipoVinculo);
        session.merge(cargoAsignado);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(cargoAsignado.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO_ASIGNADO);

        return Busqueda.class;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariounidadorganica() {
        Criteria c = session.createCriteria(Cargoxunidad.class)
        .add(Restrictions.eq("und_organica", unidadorganica))
        .add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        
        if(c.list().isEmpty()) {
            unidadSeleccionada = false;
            cargo = null;
        } else {
            unidadSeleccionada = true;
            cargo = (Cargoxunidad)c.list().get(0);
        }
 
        return cargosZone.getBody();
    }
}
