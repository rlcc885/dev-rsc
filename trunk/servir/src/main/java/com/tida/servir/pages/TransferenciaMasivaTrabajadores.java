/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import Batch.Helpers.*;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.hibernate.Session;
import org.apache.poi.hssf.usermodel.*;
import org.apache.tapestry5.annotations.Log;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ale
 */
public class TransferenciaMasivaTrabajadores  extends GeneralPage {
    
    private final String STARTPATH = "ArchivosCSV/";

    @Component(id = "formulariodescargarzip")
    private Form formulariodescargarzip;

    private List<String> errores = new LinkedList<String>();
    
    @Inject
    private Session session;
    
    @Property
    @SessionState
    private Entidad _entidadUE;
    
    private LkBusquedaCargo uo;
    
    @Property
    @Persist
    private String archivoDescargar;
    
    @Property
    @Persist
    private boolean respuestaOk;

    StreamResponse onActionFromReturnStreamResponse() {
		return new StreamResponse() {
			InputStream inputStream;

			@Override
			public void prepareResponse(Response response) {
                                File fileADescargar = new File(archivoDescargar);
                                
                                try {
                                    inputStream = new FileInputStream(fileADescargar);
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                try {
                                    response.setHeader("Content-Type", "application/x-zip");
                                    response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                    response.setHeader("Content-Length", "" + inputStream.available());
				}
				catch (IOException e) {
			            Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
				}
			}
			
			@Override
			public String getContentType() {
				return "application/x-zip";
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return inputStream;
			}

		};
	}
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodescargarzip(){
        respuestaOk = true;     
        GeneracionXLS geXLS=new GeneracionXLS();
        InformeXLS myXLS = new InformeXLS();
        List<Trabajador> ltrabajador = new LinkedList<Trabajador>();
        List<ConstanciaDocumental> lcd = new LinkedList<ConstanciaDocumental>();
        
        //archivo a descargar
        Date date = new Date();
        String nombreArchivo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        nombreArchivo = "ArchivosCSV Renato - "+sdf.format(date);
        String newlocation = STARTPATH + "GeneracionCSV/" + nombreArchivo +"/";
        archivoDescargar = newlocation + nombreArchivo +".zip";                
        
        System.out.println("------------- new location "+newlocation+" starpath "+STARTPATH);

        File f = new File(newlocation);
        if (!f.exists()) {
            f.mkdirs();
        }
        Criteria criteriaEntidadUE = session.createCriteria(Entidad.class);
        criteriaEntidadUE.add(Restrictions.eq("id", _entidadUE.getId()));

        if (!criteriaEntidadUE.list().isEmpty()) {
            errores=geXLS.generadoXLSEntidad(_entidadUE, newlocation+"xxxENTIDAD.xls", session);
            
            Criteria criteriaConcepto = session.createCriteria(ConceptoRemunerativo.class);
            criteriaConcepto.add(Restrictions.eq("entidad_id", _entidadUE.getId()));

            if (!criteriaConcepto.list().isEmpty()) {
                //generacion archivo concepto.csv
                errores = geXLS.generadoXLSConcepto(criteriaConcepto.list(), newlocation+"xxxCONCEPTO.xls",_entidadUE, session);
            }
            
            Criteria criteriaUnidadOrganica = session.createCriteria(UnidadOrganica.class);
            criteriaUnidadOrganica.add(Restrictions.eq("entidad", _entidadUE));

            if (!criteriaUnidadOrganica.list().isEmpty()) {
                //generacion archivo unidad organica.csv
                System.out.println("UNIDAD ORGANICA");
                errores = geXLS.generadoXLSUnidadOrganica(criteriaUnidadOrganica.list(), newlocation + "xxxUNIDADORGANICA.xls", session);

//                List<UnidadOrganica> luo = new LinkedList<UnidadOrganica>();
//                luo.addAll(criteriaUnidadOrganica.list());
//                List<Cargoxunidad> lcargo = new LinkedList<Cargoxunidad>();
//                for (UnidadOrganica uo : luo) {
//                    Criteria criteriaCargo = session.createCriteria(Cargoxunidad.class);
//
//                    criteriaCargo.add(Restrictions.eq("und_organica", uo));
//                    lcargo.addAll(criteriaCargo.list());
//                }
//                if (!lcargo.isEmpty()) {
//                    //generacion archivo cargo.csv
//                    System.out.println("CARGO");
//                    errores = myXLS.creadorCSVCargo(lcargo, newlocation + "xxxCARGO.xls", session);
//                }

            }
            
            
        }
        
        
        
        
        
//        try{
//            HSSFWorkbook objWB = new HSSFWorkbook();
//                // Creo la hoja
//            HSSFSheet hoja1 = objWB.createSheet("Unidades Organicas"); 
//
//             String consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
//                + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
//                + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
//                + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
//                + "WHERE S1.ESTADO=1 AND S1.UNIDADORGANICA_ID IS NOT NULL AND S2.ENTIDAD_ID='" + _entidadUE.getId() + "'";
//            Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
//            List result = query.list();
//            for(int i=0;i<result.size();i++){
//                uo=(LkBusquedaCargo) result.get(i);
//                HSSFRow fila = hoja1.createRow((short)(i));            
//                for(int j=0;j<2;j++){
//                    HSSFCell celda = fila.createCell((short)j);   
//                    celda.setCellType(HSSFCell.CELL_TYPE_STRING);
//                    celda.setCellValue(String.valueOf(uo.getDenominacion()));
//                }           
//            }
////            PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter("C:/libro1.xls")));
//            String strNombreArchivo = newlocation+"xxxENTIDAD.xls";
//            File objFile = new File(strNombreArchivo);
//            FileOutputStream archivoSalida = new FileOutputStream(objFile);
//            objWB.write(archivoSalida);
//            archivoSalida.close();
//        }catch(Exception e){
//            System.out.println(e.toString());
//        }
        
//        Criteria criteriaEntidadUE = session.createCriteria(EntidadUEjecutora.class);
//        criteriaEntidadUE.add(Restrictions.eq("id", _entidadUE.getId()));
//
//        if (!criteriaEntidadUE.list().isEmpty()) {
//            //generacion archivo organismos informantes.csv
//            System.out.println("ENTIDAD UE");
//            errores = myXLS.creadorCSVEntidadUE(_entidadUE, newlocation + "ORGAN1.csv", session);
//
//            Criteria criteriaConcepto = session.createCriteria(ConceptoRemunerativo.class);
//            criteriaConcepto.add(Restrictions.eq("entidadUE", _entidadUE));
//
//   
//        }
        
        
        
        
        //zip los CSV en un archivo ZIP
        errores = Unzip.zippe(newlocation, nombreArchivo +".zip");
        if (errores.size() > 0 ) { // hay errores
            for(String error: errores){
                formulariodescargarzip.recordError(error);
            }
            return this;
        }
        
        

        return this;

    }
}
