/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Constantes;
import helpers.Reportes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
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

    @Inject
   private Context context;
   

//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.puedeEscribir(_usuario, _oi);
//    }

    
    public boolean getEvaluodesasignar() {
        return ca.getFec_fin() == null;
    }
    
    void setupRender() {
        if (actual == null) {
            actual = new Trabajador();
        } 
    }
 
//
//    StreamResponse onActionFromReportetrabajos(Long idLegajo) {
//        Reportes rep = new Reportes();
//        Map<String, Object> parametros = new HashMap<String, Object>();
//        parametros.put("MandatoryParameter_LegajoID", idLegajo); 
//        return rep.callReporte(Reportes.REPORTE.A1, Reportes.TIPO.PDF,  parametros ,context);
//        
//    }

    public List<CargoAsignado> getCargosAsignados() {
          Criteria c = session.createCriteria(CargoAsignado.class);
          c.createAlias("legajo", "legajo");
          c.add(Restrictions.eq("trabajador", actual));
          c.add(Restrictions.eq("legajo.entidad", _oi));
          c.add(Restrictions.ne("estado", CargoAsignado.ESTADO_BAJA));
          c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
          return c.list();
          
    }
/*
    void onActionFromReportetrabajos() {
        PentahoTest pt = new PentahoTest();
        pt.prueba();
    }
 * */

}
