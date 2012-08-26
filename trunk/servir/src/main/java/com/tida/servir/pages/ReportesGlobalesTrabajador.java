package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Errores;
import helpers.Reportes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


@Import(stylesheet ={"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class ReportesGlobalesTrabajador  extends GeneralPage
{
    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario loggedUser;
    
    @Property
    @SessionState
    private Entidad_BK entidadUE;

    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private DatoAuxiliar mensajes;

    public DatoAuxiliar getMensajes() {
        return mensajes;
    }

    public void setMensajes(DatoAuxiliar mensajes) {
        this.mensajes = mensajes;
    }

    @Property
    private List<Trabajador> trabajadoresEncontrados;

    private List<DatoAuxiliar> tiposDoc = new ArrayList();

    public List<DatoAuxiliar> getTiposDoc() {
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", "TipoDocumento"));
        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }

    @Property
    @Persist
    private Trabajador nuevo;

    @Property
    @Persist
    private Boolean muestroReportes;

    @Component(id = "formularioReporte")
	private Form formularioReporte;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Zone reportesZone;

    @Inject
    private Context context;

	@Log
	Object onSuccessFromFormularioReporte()
	{

            // busco legajos en este organismo con este trabajador.
            List<Trabajador> trabajadores=  session.createCriteria(Trabajador.class)
                .add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()))
                .add(Restrictions.like("tipoDocumento", nuevo.getDocumentoidentidad()))
                .list();


            if (trabajadores.isEmpty()) {
                formularioReporte.recordError(Errores.ERROR_TRABAJADOR_NO_EXISTENTE);
                return this;

            }

            muestroReportes = true;
            nuevo = trabajadores.get(0);
            return reportesZone;
	}
        

      void onActionFromCancelar() {
            nuevo = new Trabajador();
        }

	@Log
	void onPrepareFromformularioReporte()
	{
		if(nuevo == null) {
			nuevo = new Trabajador();
		}
	}


        StreamResponse onReporteTrabajador(String reporte) {
            Reportes rep = new Reportes();
            Map<String, Object> parametros = new HashMap<String, Object>();
            Reportes.REPORTE rep_type = Reportes.REPORTE.A2;

            parametros.put("MandatoryParameter_TrabajadorID", nuevo.getId());
            parametros.put("MandatoryParameter_EntidadUEjecutoraID", entidadUE.getId());
	  
            if(reporte.equals("A2")) rep_type = Reportes.REPORTE.A2;
            if(reporte.equals("A4-1")) rep_type = Reportes.REPORTE.A4_1;
            if(reporte.equals("A4-2")) rep_type = Reportes.REPORTE.A4_2;
            if(reporte.equals("A4-3")) rep_type = Reportes.REPORTE.A4_3;
            if(reporte.equals("A5")) rep_type = Reportes.REPORTE.A5;
	    return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

        }
}
