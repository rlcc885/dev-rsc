package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;

import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Reportes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.StreamResponse;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades organicas
 * 
 * @author ale
 * 
 */
public class ReportesEstructura extends GeneralPage {

    @Inject
    private Session session;

    @Property
    @Persist
    private UnidadOrganica unidadOrganica;

    @Property
    @Persist
    private Request _request;

    @Property
    @Persist
    private UnidadOrganica uo;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Zone listaUOZone;

    @Property
    @SessionState
    private Usuario loggedUser;

    @Property
    @SessionState
    private EntidadUEjecutora entidadUE;

    @Inject
    private Context context;

    @Log
    public GenericSelectModel<UnidadOrganica> getUnidadesOrganicas() {
        Criteria c;
        c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidadUE", entidadUE));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        return new GenericSelectModel<UnidadOrganica>(c.list(), UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    public boolean getUsuarioGeneral() {
        return Helpers.esMultiOrganismo(loggedUser);
    }

    public boolean getNoUsuarioGeneral() {
        return !getUsuarioGeneral();
    }


    StreamResponse onReporteUO(Long idUO, String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.A3;

        parametros.put("MandatoryParameter_UnidadOrganicaID", idUO);

        if(reporte.equals("A3")) rep_type = Reportes.REPORTE.A3;
        if(reporte.equals("A6")) rep_type = Reportes.REPORTE.A6;
        if(reporte.equals("A7")) rep_type = Reportes.REPORTE.A7;
        if(reporte.equals("C1")) rep_type = Reportes.REPORTE.C1;
        if(reporte.equals("C7")) rep_type = Reportes.REPORTE.C7;
        if(reporte.equals("C8")) rep_type = Reportes.REPORTE.C8;
        if(reporte.equals("D2")) rep_type = Reportes.REPORTE.D2;
	if(reporte.equals("A8")) rep_type = Reportes.REPORTE.A8;
        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

    }

    StreamResponse onReporteEntidad(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.C2;

        parametros.put("MandatoryParameter_EntidadUEjecutoraID", entidadUE.getId());

        if(reporte.equals("C2")) rep_type = Reportes.REPORTE.C2;
        if(reporte.equals("C12")) rep_type = Reportes.REPORTE.C12_PDF;
        if(reporte.equals("D4")) rep_type = Reportes.REPORTE.D4_PDF;
        if(reporte.equals("C9")) rep_type = Reportes.REPORTE.C9;
        if(reporte.equals("C10")) rep_type = Reportes.REPORTE.C10;
        if(reporte.equals("D1")) rep_type = Reportes.REPORTE.D1;
        if(reporte.equals("D3")) rep_type = Reportes.REPORTE.D3;
        if(reporte.equals("D5")) rep_type = Reportes.REPORTE.D5;
	if(reporte.equals("A3-UE")) rep_type = Reportes.REPORTE.A3_UE;
	if(reporte.equals("A6-UE")) rep_type = Reportes.REPORTE.A6_UE;
	if(reporte.equals("A7-UE")) rep_type = Reportes.REPORTE.A7_UE;
	if(reporte.equals("A8-UE")) rep_type = Reportes.REPORTE.A8_UE;
        if(reporte.equals("LUE1")) rep_type = Reportes.REPORTE.LUE1;
        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

    }

    StreamResponse onReporteResumen(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.D44;

        parametros.put("MandatoryParameter_EntidadUEjecutoraID", entidadUE.getId());

        if(reporte.equals("D44")) rep_type = Reportes.REPORTE.D44;

       // return rep.callReporteEspecial(rep_type, Reportes.TIPO.EXCEL,  parametros ,context);
	 return rep.callReporte(rep_type, Reportes.TIPO.EXCEL,  parametros ,context);

    }

    @Log
    Object onSuccessFromInstruccion() {
        return listaUOZone.getBody();
    }

    StreamResponse onReporteRemuneraciones(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.C1212;

        parametros.put("MandatoryParameter_EntidadUEjecutoraID", entidadUE.getId());

        if(reporte.equals("C12")) rep_type = Reportes.REPORTE.C1212;
	 return rep.callReporte(rep_type, Reportes.TIPO.EXCEL,  parametros ,context);
      //  return rep.callReporteEspecial(rep_type, Reportes.TIPO.EXCEL,  parametros ,context);

    }

}