/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad_BK;
import helpers.Helpers;
import helpers.Reportes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Context;
import org.hibernate.Session;

/**
 *
 * @author ale
 */
public class ReportesNivel  extends GeneralPage
{
    @Property
    @SessionState
    private Entidad_BK eue;


    @Inject
    private Session session;


    @Property
    @Persist
    private String nivel_gobierno;

    @Property
    @Persist
    private String pliego;

    @Property
    @Persist
    private String sector;

    @Property
    @Persist
    private DatoAuxiliar departamento;


    @InjectComponent
    @Property
    private Zone nivelSectorPliegoZone;

    @InjectComponent
    @Property
    private Zone MuestroReportesZone;

    @Property
    @Persist
    private List<String> _beanNivelGobierno;

    @Inject
    private Context context;
    
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


    @Log
    public boolean getEsLocal() {

        return nivel_gobierno.equals("NIVEL LOCAL");
    }

    public boolean getMostrarReportes() {
        return nivel_gobierno !=null && sector != null && pliego != null;
    }

    Object onSuccessFromFormNivSecPli() {
        return new MultiZoneUpdate("nivelSectorPliegoZone", nivelSectorPliegoZone.getBody())
                    .add("MuestroReportesZone", MuestroReportesZone.getBody());
    }

    StreamResponse onreportePliego(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.A3;


        if(reporte.equals("D66")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
            parametros.put("MandatoryParameter_Pliego", pliego);
            rep_type = Reportes.REPORTE.D66;
        }

        if(reporte.equals("D77")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
            parametros.put("MandatoryParameter_SectorGobierno", sector);
/*            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_SectorGobierno", sector);
*/
          rep_type = Reportes.REPORTE.D77;
        }

        if(reporte.equals("D88")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
	     parametros.put("MandatoryParameter_Pliego", pliego);
/*
            parametros.put("MandatoryParameter_SectorGobierno", sector);
            parametros.put("MandatoryParameter_Pliego", pliego);
            parametros.put("MandatoryParameter_EntidadUEjecutoraID", eue.getId());
 */
            rep_type = Reportes.REPORTE.D88;
        }
	
	if(reporte.equals("L1")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L1;
        }
	
	if(reporte.equals("L2")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L2;
        }
	if(reporte.equals("L3")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L3;
        }
	if(reporte.equals("L4")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L4;
        }
	if(reporte.equals("L5")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L5;
        }
	if(reporte.equals("L6")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L6;
        }
	if(reporte.equals("L7")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L7;
        }
	if(reporte.equals("L9")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L9;
        }
	if(reporte.equals("L11")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            rep_type = Reportes.REPORTE.L11;
        }

      //  return rep.callReporteEspecial(rep_type, Reportes.TIPO.PDF,  parametros ,context);
	 return rep.callReporte(rep_type, Reportes.TIPO.EXCEL,  parametros ,context);

    }
    StreamResponse onreportePliegoPentaho(String reporte) {
        Reportes rep = new Reportes();
        Map<String, Object> parametros = new HashMap<String, Object>();
        Reportes.REPORTE rep_type = Reportes.REPORTE.A3;


        if(reporte.equals("D6")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
            parametros.put("MandatoryParameter_Pliego", pliego);
            rep_type = Reportes.REPORTE.D6_PDF;
        }

        if(reporte.equals("D7")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
            parametros.put("MandatoryParameter_SectorGobierno", sector);
/*            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_SectorGobierno", sector);
*/
          rep_type = Reportes.REPORTE.D7_PDF;
        }

        if(reporte.equals("D8")) {
            parametros.put("MandatoryParameter_NivelDeGobierno", nivel_gobierno);
            parametros.put("MandatoryParameter_ClasificadorFuncional", eue.getClas_funcional());
            parametros.put("MandatoryParameter_Pliego", pliego);

            /*
            parametros.put("MandatoryParameter_SectorGobierno", sector);
            parametros.put("MandatoryParameter_Pliego", pliego);
            parametros.put("MandatoryParameter_EntidadUEjecutoraID", eue.getId());
 */
            rep_type = Reportes.REPORTE.D8_PDF;
        }
     
     

        return rep.callReporte(rep_type, Reportes.TIPO.PDF,  parametros ,context);

    }
    @SetupRender
    void initValues() {
        _beanNivelGobierno = Helpers.getValorTablaAuxiliar("NivelGobierno", session);
    }
}
