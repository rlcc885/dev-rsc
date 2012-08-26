package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.Usuario;
import helpers.Errores;
import helpers.Reportes;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.hibernate.criterion.Restrictions;


@Import(stylesheet ={"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class ReportesUsuarios  extends GeneralPage
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
    @Persist
    private Trabajador nuevo;

    @Property
    @Persist
    private Boolean muestroReportes;

    @Property
    @Persist
    private Date fechaDesde;

    @Property
    @Persist
    private Date fechaHasta;

    @Property
    @Persist
    private String identUsuario;

    @Property
    @Persist
    private Long selectedUserId;

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
            Criteria c = session.createCriteria(Usuario.class);
//            if(loggedUser.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
//                c.add(Restrictions.eq("entidadUE", loggedUser.getEntidad()));
//
//            }
            c.add(Restrictions.eq("login", identUsuario));

            List<Usuario> usuarios=c.list();


            if (usuarios.isEmpty()) {
                formularioReporte.recordError(Errores.ERROR_USUARIO_NO_EXISTENTE);
                return this;

            }

            if(fechaHasta.before(fechaDesde)) {
                formularioReporte.recordError(Errores.ERROR_FECHA_HASTA_PREVIA_DESDE);
                return this;
            }

            selectedUserId = usuarios.get(0).getId();
            muestroReportes = true;
            return reportesZone;
	}
        

        StreamResponse onReporteUsuarios(String reporte) {
            Reportes rep = new Reportes();
            Map<String, Object> parametros = new HashMap<String, Object>();
            Reportes.REPORTE rep_type = Reportes.REPORTE.B2;

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");


            parametros.put("MandatoryParameter_UsuarioID", selectedUserId);
            parametros.put("MandatoryParameter_FechaDesde", sdf.format(fechaDesde));
            parametros.put("MandatoryParameter_FechaHasta", sdf.format(fechaHasta));

            if(reporte.equals("B2")) rep_type = Reportes.REPORTE.B2;
            if(reporte.equals("B3")) rep_type = Reportes.REPORTE.B3;
            return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

        }
}
