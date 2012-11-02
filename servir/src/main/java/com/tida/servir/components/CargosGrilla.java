/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.pages.Busqueda;
import helpers.Constantes;
import helpers.Reportes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ale
 */

public class CargosGrilla {
    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;

    @Parameter
    @Property
    private Trabajador actual;

    @Parameter
    @Property
    private Boolean busqueda; // si no es una búsqueda, sólo puede eliminar.
    

    @Property
    @Persist
    private CargoAsignado ca;

    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario _usuario;

    @Property
    @SessionState
    private Entidad _oi;
    
    @SessionState
    private UsuarioAcceso usua;

    @Inject
    private Context context;
    
    @InjectComponent
    private Zone evaluacionesZone; 
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean veliminar;
    private int elemento=0;
    
    public boolean getEvaluodesasignar() {
        return ca.getFec_fin() == null;
    }
    
    void setupRender() {
        if (actual == null) {
            actual = new Trabajador();
        } 
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
        }
    }
    @Log
    public List<CargoAsignado> getCargosAsignados() {
          Criteria c = session.createCriteria(CargoAsignado.class);
          c.createAlias("legajo", "legajo");
          c.add(Restrictions.eq("trabajador", actual));
          c.add(Restrictions.eq("legajo.entidad", _oi));
          c.addOrder(Order.desc("fec_inicio"));
   //       c.add(Restrictions.ne("estado", CargoAsignado.ESTADO_BAJA));
          System.out.println("NROREGX"+c.list().size());
          c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
          nroregistros = Integer.toString(c.list().size());
          return c.list();
          
    }
    public Boolean getEstadoCargo(){
        System.out.println("MODESTADOX");
    if (ca.getEstado()==Boolean.FALSE || ca.getCargoxunidad().getDen_cargo().equals("SIN CARGO")){
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
    
    }
    @Persist
    @Property
    private String nroregistros;
    
    @Log
   Object  onSelectedFromCancel() {
        if (_usuario.getRolid() == 1) {
                return "TrabajadorLaboral";
            } else {
                return Busqueda.class;
            }
        
        
    }
}