/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import helpers.Reportes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.hibernate.Session;

/**
 * Da de baja un cargo asignado. Permite la selección de un trabajador.
 * @author ale
 */
public class DeteccionIntrusion extends GeneralPage {
    @Inject
    private Session session;
    @Property
    @SessionState
    private Entidad_BK _entidadUE;

    @Property
    @SessionState
    private Usuario _usuario;

    @Persist
    @Property
    private Date fechaDesde;

    @Persist
    @Property
    private Date fechaHasta;

    /*
    @InjectComponent
    @Property
    private Zone reportesZone;
    */
    @Inject
    private Context context;

    Object onSuccessFromformulariodeteccion() {
        //System.out.println("------------ hayFechas:" + ((fechaDesde != null ) &&(fechaHasta != null)));
        //return reportesZone.getBody();
        return null;
    }

    public boolean getHayFechas() {
        //System.out.println("------------ hayFechas:" + ((fechaDesde != null ) &&(fechaHasta != null)));
        return ((fechaDesde != null ) &&(fechaHasta != null));
    }
    
     public Boolean getNoEsAdmSystema(){
        if(_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA))
            return Boolean.FALSE;
        
        return Boolean.TRUE;
    }

    StreamResponse onReporteTrazabilidad() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.B4;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        parametros.put("MandatoryParameter_FechaDesde", sdf.format(fechaDesde));
        parametros.put("MandatoryParameter_FechaHasta", sdf.format(fechaHasta));

        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros, context);
    }

    @SetupRender
    void setupFechas() {
        fechaDesde = new Date();
        fechaHasta = new Date();

    }
}
