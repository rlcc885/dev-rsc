package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Logger;
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
import org.hibernate.Query;
import org.hibernate.Session;
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
    @SessionState
    private UsuarioAcceso usuarioAcceso;
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
//    @Persist
//    @Property
//    private CargoAsignado cargoAsignado;
//    @Persist
//    @Property
//    private ConceptoRemunerativo conceptoremun;
    //Listado de Remuneraciones
    @InjectComponent
    private Zone listaRemuneracionesZone;
    @Persist
    @Property
    private LkBusquedaRemuneracion listaRemuneraciones;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private ConceptoRemunerativo conceptoseleccionado;
    // Para accesos
    @Persist
    @Property
    private Boolean editando;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vguardar;
    @Persist
    @Property
    private Boolean vinserta;
    @Persist
    @Property
    private long cargoAsignado;
    @Persist
    @Property
    private Boolean vformulario;

    @Log
    void setupRender() {
        // No mover la inicializacion de variables

        Criteria c3 = session.createCriteria(LkBusquedaTrabajador.class);
        c3.add(Restrictions.eq("id", actual.getId()));
        List<LkBusquedaTrabajador> result = c3.list();
        cargoAsignado = result.get(0).getCaid();
        editando = false;
        resetRegistro();
        accesos();
    }

    @Log
    void resetRegistro() {

        remuneracion = new RemuneracionPersonal();
        conceptoseleccionado = null;
        editando = false;
        accesos();
    }

    @Log
    public void accesos() {
        vinserta = false;
        veditar = false;
        veliminar = false;
        vdetalle = true;
        vguardar = false;
        vformulario=false;
        if (usuarioAcceso.getAccesoupdate() == 1) {
            veditar = true;
            vdetalle = false;
            vguardar = true;
        }
        if (usuarioAcceso.getAccesodelete() == 1) {
            veliminar = true;
        }
        if (usuarioAcceso.getAccesoreport() == 1) {
            vinserta = true;
            vguardar = true;
            vdetalle = false;
            vformulario=true;
        }
    }

    @Log
    Object onReset() {
        resetRegistro();
        return remuneracionesZone.getBody();
    }

    @Log
    Object onCancel() {
        System.out.println(_usuario.getRolid());
        if (_usuario.getRolid() == 1) { // Si es trabajador 
            return "Meritos";
        } else {
            return "Busqueda";
        }
    }

    @Log
    public GenericSelectModel<ConceptoRemunerativo> getBeanConceptoRemunerativo() {
        List<ConceptoRemunerativo> list1;
        Criteria c1 = session.createCriteria(ConceptoRemunerativo.class);
        c1.add(Restrictions.eq("entidad_id", _oi.getId()));
        list1 = c1.list();
        return new GenericSelectModel<ConceptoRemunerativo>(list1, ConceptoRemunerativo.class, "descripcion", "id", _access);
    }

    @Log
    public List<LkBusquedaRemuneracion> getListadoRemuneraciones() {
        Criteria c2 = session.createCriteria(LkBusquedaRemuneracion.class);
        c2.add(Restrictions.eq("cargoasignado_id", cargoAsignado));
        nroregistros = Integer.toString(c2.list().size());
        return c2.list();
    }
    @Persist
    @Property
    private String nroregistros;
    
    @Log
    @CommitAfter
    Object onSuccess() {
        int numRemuDupl=0;
        formulariomensajesCR.clearErrors();
        String consulta = "SELECT COUNT(*) FROM RSC_REMUNERACIONPERSONAL F "
                + "WHERE F.CARGOASIGNADO_ID = " + cargoAsignado + " AND F.CONCEPTOREMUNERATIVO_ID = '" + conceptoseleccionado.getId() + "'";
        System.out.println(consulta);
        Query q1 = session.createSQLQuery(consulta);
        numRemuDupl = Integer.parseInt(q1.list().get(0).toString());
            
        if(editando && numRemuDupl > 1){
            formulariomensajesCR.recordError("No es posible registrar un concepto de pago mas de una vez");
            //envelope.setContents("No es posible registrar un concepto de pago mas de una vez");
            return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody())
                .add("remuneracionesZone", remuneracionesZone.getBody());
        }else if(!editando && numRemuDupl > 0){
            formulariomensajesCR.recordError("No es posible registrar un concepto de pago mas de una vez");
            //envelope.setContents("No es posible registrar un concepto de pago mas de una vez");
            return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody())
                .add("remuneracionesZone", remuneracionesZone.getBody());
        }else{
        remuneracion.setCargoasignado_id(cargoAsignado);
        remuneracion.setConceptoremunerativo(conceptoseleccionado);
        session.saveOrUpdate(remuneracion);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(remuneracion.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_REMUNERACION_PERSONAL);    
        envelope.setContents(helpers.Constantes.REMUNERACION_EXITO);
        resetRegistro();
        return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody())
                .add("remuneracionesZone", remuneracionesZone.getBody());
        }
    }

    @Log
    Object onActionFromEditar(RemuneracionPersonal remu) {
                conceptoseleccionado = new ConceptoRemunerativo();
        System.out.println("OBSERVACION "+remu + " "+conceptoseleccionado);
        remuneracion = remu;
        remuneracion.setImporte(remuneracion.getImporte().replaceAll("\\.", ","));
        conceptoseleccionado.setId(remuneracion.getConceptoremunerativo().getId());
        accesos();
        vformulario=true;
        editando = true;
        return new MultiZoneUpdate("remuneracionesZone", remuneracionesZone.getBody());
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar(RemuneracionPersonal remu) {
        new Logger().loguearOperacion(session, _usuario, String.valueOf(remu.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_REMUNERACION_PERSONAL);
        session.delete(remu);
        resetRegistro();
        accesos();
        envelope.setContents("Remuneraciones personales elimanadas exitosamente.");
        return new MultiZoneUpdate("mensajesCRZone", mensajesCRZone.getBody())
                .add("listaRemuneracionesZone", listaRemuneracionesZone.getBody());
    }

    @Log
    Object onActionFromDetalle(RemuneracionPersonal remu) {
                conceptoseleccionado = new ConceptoRemunerativo();
        remuneracion = remu;
        
        conceptoseleccionado.setId(remuneracion.getConceptoremunerativo().getId());
        accesos();
        vdetalle = true;
        vguardar = false;
        vformulario=true;
        return remuneracionesZone.getBody();
    }
}