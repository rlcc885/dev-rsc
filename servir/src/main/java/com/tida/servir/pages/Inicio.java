package com.tida.servir.pages;

import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import helpers.Constantes;

import java.util.List;
import org.apache.tapestry5.annotations.*;

import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class Inicio  extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
    @Property
    private Trabajador e;
    @Property
    @Persist
    private Entidad_BK entidadUE;
    @Property
    private Entidad_BK oi;
    @Property
    @SessionState
    private Usuario _usuario;
    @InjectComponent
    private Envelope envelope;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    private GenericSelectModel<Entidad_BK> _beans;
    
    @InjectComponent
    private Zone empleadoszone;
    
    @Property
    private Entidad_BK entidad_nueva;
    
    //cargar datos de entidades
    public List<Entidad_BK> getEntidadesUEjecutoras() {
        Criteria c;
        c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
        c.add(Restrictions.eq("verificado", Constantes.VERIFICADO));
        return c.list();
    }

    //cargar datos de empleados
    public List<Trabajador> getEmpleados() {
        Criteria c = session.createCriteria(CargoAsignado.class);
        c.createAlias("trabajador", "trabajador");
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));
        c.add(Restrictions.eq("trabajador.verificado", Constantes.VERIFICADO));
        c.setProjection(Projections.distinct(Projections.property("trabajador")));
        return c.list();
    }
 
    // obtener fila
     public String getSelectionRow() {
        if (actual != null) {
            if (e.getId() == actual.getId()) {
                return "selected";
            }
            return "normal";
        }
        return "";

    }
    
    // incio de pagina
    @Log
    @SetupRender
    void initializeValue()
    {
        
        if (entidadUE == null){
                //Cargamos alguna entidad
                Criteria c = session.createCriteria(Entidad_BK.class);
                c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA));
                entidad_nueva = entidadUE = (Entidad_BK)c.list().get(0);

         }
        
    }
    
       
}