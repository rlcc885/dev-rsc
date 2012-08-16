package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class PublicacionesEditor {

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
    @Component(id = "formulariomensajespi")
    private Form formulariomensajespi;
    @InjectComponent
    private Zone mensajesPIZone;
    @InjectComponent
    private Zone proIntelectualZone;
    private int elemento = 0;
    @Parameter
    @Property
    private Trabajador actual;
    @Persist
    @Property
    private Publicacion publicacion;
    @Property
    @Persist
    private boolean bvalidausuario;
    //Listado de produccion intelectual
    @InjectComponent
    private Zone listaProIntelectualZone;
    @Persist
    @Property
    private Publicacion listaprointelectual;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private Boolean editando;

    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
        publicacion = new Publicacion();
        valfec_desde=null;
        if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
            bvalidausuario = true;
        } else {
            bvalidausuario = false;
        }
        editando = false;
    }

    @Log
    public List<Publicacion> getListadoProIntelectual() {
        Criteria c = session.createCriteria(Publicacion.class);
        c.add(Restrictions.eq("trabajador", actual));
        return c.list();
    }

    //para obtener datos de la clase de publicacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanClasePublicacion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CLASEPRODUCCIONINTELECTUAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del tipo de publicacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSPUBLICACION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    void onSelectedFromCancel() {
        elemento = 2;
    }

    void onSelectedFromReset() {
        elemento = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioprointelectual() {
        if(valfec_desde!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_desde = (Date)formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
        }
        publicacion.setFecha(fecha_desde);
        Logger logger = new Logger();
        publicacion.setTrabajador(actual);
        publicacion.setEntidad(_oi);
        if (!editando) {
            //guardando
            if (_usuario.getRol().getId() == 1) {
                publicacion.setAgregadoTrabajador(true);
            } else {
                publicacion.setAgregadoTrabajador(false);
            }
        }

        session.saveOrUpdate(publicacion);
        session.flush();

        if (!editando) {
            logger.loguearEvento(session, logger.MODIFICACION_PRODUCCION, actual.getEntidad().getId(), actual.getId(), logger.MOTIVO_PERSONALES_PRODUCCION, publicacion.getId());
        }
        if (publicacion.getValidado() != null) {
            if (publicacion.getValidado() == true) {
                String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + publicacion.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_PRODUCCION + "' and tabla_id='" + publicacion.getId() + "' and estadoevento=0";
                Query query = session.createSQLQuery(hql);
                int rowCount = query.executeUpdate();
                session.flush();
            }
        }

        editando = false;
        envelope.setContents(helpers.Constantes.PROD_INTELECTUAL_EXITO);
        publicacion = new Publicacion();
        valfec_desde=null;
        return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody()).add("proIntelectualZone", proIntelectualZone.getBody());

    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones() {
        if (elemento == 1) {
            publicacion = new Publicacion();
            editando = false;
            valfec_desde=null;
            return proIntelectualZone.getBody();
        } else if (elemento == 2) {
            return "Busqueda";
        } else {
            return this;
        }

    }

    @Log
    Object onActionFromEditar1(Publicacion publi) {
        publicacion = publi;
        
        if(publicacion.getFecha()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(publicacion.getFecha());
        }
                
        editando = true;
        return proIntelectualZone.getBody();
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar1(Publicacion publi) {
        session.delete(publi);
        envelope.setContents("Produciones Intelectuales eliminadas exitosamente.");
        return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody());

    }
    
    @Log
    Object onActionFromEditar2(Publicacion publi) {
        publicacion = publi;
        
        if(publicacion.getFecha()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(publicacion.getFecha());
        }
                
        editando = true;
        return proIntelectualZone.getBody();
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar2(Publicacion publi) {
        session.delete(publi);
        envelope.setContents("Produciones Intelectuales eliminadas exitosamente.");
        return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody());

    }
}
