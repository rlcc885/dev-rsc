package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import helpers.Helpers;
import helpers.Reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;


@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class ReportesEntidades  extends GeneralPage
{
    @Inject
    private Session session;

    @Property
    @SessionState
    private Usuario loggedUser;

    @Property
    @SessionState
    private Entidad_BK entidadUE;

   @Property
    @Persist
    private String nivel_gobierno;

    @Property
    @Persist
    private String pliego;

    @Property
    @Persist
    private String sector;

    @InjectComponent
    @Property
    private Zone PliegoZone;

    @InjectComponent
    @Property
    private Zone SectorZone;



    @Inject
    private Context context;


    @Property
    @Persist
    private List<String> _beanNivelGobierno;




    @Inject
    private PropertyAccess _access;

    @Log
    public List<String> getBeanDatoAuxSector() {
        List<String> retList = null;
        if (!getEsLocal())
            retList =  Helpers.getValorTablaAuxiliar("SectorGobierno", session);
        else
            retList =  Helpers.getValorTablaAuxiliar("UBDepartamento", session);

        if (retList == null)
            return new ArrayList<String>();
        else
            return retList;

    }

    @Log
    public List<String> getBeanDatoAuxPliego() {
        List<String> retList = null;

        if (!getEsLocal())
            retList = Helpers.getValorTablaAuxiliar("Pliego", session);
        else {

            // Obtenemos el dato auxiliar del depto.
            DatoAuxiliar depto =  Helpers.getDatoAuxiliar("UBDepartamento", sector,  session);
            if (depto != null) {
                retList = Helpers.getValorTablaAuxiliar("UBProvincia", session,
                    "UBDepartamento", depto.getCodigo());
            }
        }

        if (retList == null)
            return new ArrayList<String>();
        else
            return retList;
    }


    StreamResponse onReporteNivel(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.C5;

        parametros.put("MandatoryParameter_NivelGobierno", nivel_gobierno);

        if(reporte.equals("C5")) rep_type = Reportes.REPORTE.C5;
        if(reporte.equals("C6")) rep_type = Reportes.REPORTE.C6;
        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

        }


    StreamResponse onReporteSector() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.C4;

        parametros.put("MandatoryParameter_SectorGobierno", sector);

        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

        }


    StreamResponse onReportePliego() {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.C3;

        parametros.put("MandatoryParameter_Pliego", pliego);
        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

        }


    Object onSuccessFromformNivel() {
        sector = null;
        pliego = null;
        return new MultiZoneUpdate("SectorZone", SectorZone.getBody())
                .add("PliegoZone", PliegoZone.getBody());

    }


    Object onSuccessFromformSector() {
        pliego = null;
        return  PliegoZone.getBody();

    }


    @Log
    public boolean getEsLocal() {

        return nivel_gobierno.equals("NIVEL LOCAL");
    }


    @Log
    @SetupRender
    void initializeValue()
    {
        _beanNivelGobierno = Helpers.getValorTablaAuxiliar("NivelGobierno", session);
    }
}
