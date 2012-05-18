/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;


import Batch.Helpers.AusLicPersonalCSV;
import Batch.Helpers.CreadorDesdeDB;
import Batch.Helpers.EvaluacionPersonalCSV;
import Batch.Helpers.InformeXLS;
import Batch.Helpers.RemuneracionPersonalCSV;
import Batch.Helpers.Unzip;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Ant_Laborales;
import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Certificacion;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.ConstanciaDocumental;
import com.tida.servir.entities.Curso;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Familiar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.MeritoDemerito;
import com.tida.servir.entities.Publicacion;
import com.tida.servir.entities.Titulo;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Morgan
 */
public class DescargaArchivoCSV  extends GeneralPage {
    private final String STARTPATH = "ArchivosCSV/";

    @Component(id = "formulariodescargarzip")
    private Form formulariodescargarzip;

    private List<String> errores = new LinkedList<String>();
    
    @Inject
    private Session session;
    
    @Property
    @SessionState
    private EntidadUEjecutora _entidadUE;
    
    @Property
    @Persist
    private String archivoDescargar;
    
    @Property
    @Persist
    private boolean respuestaOk;
    
    void onActivate() {
    }
    
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

    @CommitAfter
    Object onSuccessFromformulariodescargarzip(){
        respuestaOk = true;        
        InformeXLS myXLS = new InformeXLS();
        List<Trabajador> ltrabajador = new LinkedList<Trabajador>();
        List<ConstanciaDocumental> lcd = new LinkedList<ConstanciaDocumental>();
        //archivo a descargar
        Date date = new Date();
        String nombreArchivo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        nombreArchivo = "ArchivosCSV - "+sdf.format(date);
        String newlocation = STARTPATH + "GeneracionCSV/" + nombreArchivo +"/";
        archivoDescargar = newlocation + nombreArchivo +".zip";                
        
        //System.out.println("------------- new location "+newlocation+" starpath "+STARTPATH);

        File f = new File(newlocation);
        if (!f.exists()) {
            f.mkdirs();
        }

        Criteria criteriaEntidadUE = session.createCriteria(EntidadUEjecutora.class);
        criteriaEntidadUE.add(Restrictions.eq("id", _entidadUE.getId()));

        if (!criteriaEntidadUE.list().isEmpty()) {
            //generacion archivo organismos informantes.csv
            System.out.println("ENTIDAD UE");
            errores = myXLS.creadorCSVEntidadUE(_entidadUE, newlocation + "ORGAN1.csv", session);

            Criteria criteriaConcepto = session.createCriteria(ConceptoRemunerativo.class);
            criteriaConcepto.add(Restrictions.eq("entidadUE", _entidadUE));

            if (!criteriaConcepto.list().isEmpty()) {
                //generacion archivo concepto.csv
                System.out.println("CONCEPTO");
                errores = myXLS.creadorCSVConcepto(criteriaConcepto.list(), newlocation + "BASICO6.csv", session);
            }

                Criteria criteriaUnidadOrganica = session.createCriteria(UnidadOrganica.class);
                criteriaUnidadOrganica.add(Restrictions.eq("entidadUE", _entidadUE));

                if (!criteriaUnidadOrganica.list().isEmpty()) {
                    //generacion archivo unidad organica.csv
                    System.out.println("UNIDAD ORGANICA");
                    errores = myXLS.creadorCSVUnidadOrganica(criteriaUnidadOrganica.list(), newlocation + "BASICO2.csv", session);

                    List<UnidadOrganica> luo = new LinkedList<UnidadOrganica>();
                    luo.addAll(criteriaUnidadOrganica.list());
                    List<Cargo> lcargo = new LinkedList<Cargo>();
                    for(UnidadOrganica uo: luo){
                        Criteria criteriaCargo = session.createCriteria(Cargo.class);

                        criteriaCargo.add(Restrictions.eq("und_organica", uo));
                        lcargo.addAll(criteriaCargo.list());
                    }
                    
                        if (!lcargo.isEmpty()) {
                            //generacion archivo cargo.csv
                            System.out.println("CARGO");
                            errores = myXLS.creadorCSVCargo(lcargo, newlocation + "BASICO3.csv", session);
                        }

                  }
            
            List<Legajo> listlegajo = session.createCriteria(Legajo.class)
                    .add(Restrictions.eq("entidadUE", _entidadUE))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            
            if (!listlegajo.isEmpty()) {
                //generacion archivo legajo.csv
                System.out.println("LEGAJO ");
                errores = myXLS.creadorCSVLegajo(listlegajo, newlocation + "PPAL2.csv", session);
                for (Legajo l:listlegajo) {
                    
                    ltrabajador.add(l.getTrabajador());
                    for (ConstanciaDocumental cd : l.getConstanciasDocumentales()) {
                        lcd.add(cd);
                    }
                }

                //generacion archivo constancia documental.csv
                if (!lcd.isEmpty()) {
                    System.out.println("CONSTANCIA DOCUMENTAL");
                    errores = myXLS.creadorCSVConstanciaDocumental(lcd, newlocation + "COMPLEG1.csv", session);
                }

                if (!ltrabajador.isEmpty()) {
                    System.out.println("TRABAJADOR");
                    //generacion archivo trabajador.csv
                    errores = myXLS.creadorCSVTrabajador(ltrabajador, newlocation + "PPAL1.csv", _entidadUE, session);

                    Criteria criteriaFamiliar;
                    Criteria criteriaTitulo;
                    Criteria criteriaCertificacion;
                    Criteria criteriaCurso;
                    Criteria criteriaAntecedent;
                    Criteria criteriaMerito;
                    Criteria criteriaPublicacion;

                    List<Familiar> lf = new LinkedList<Familiar>();
                    List<Titulo> lt = new LinkedList<Titulo>();
                    List<Certificacion> lc = new LinkedList<Certificacion>();
                    List<Curso> lcur = new LinkedList<Curso>();
                    List<Ant_Laborales> lal = new LinkedList<Ant_Laborales>();
                    List<MeritoDemerito> lmd = new LinkedList<MeritoDemerito>();
                    List<Publicacion> lp = new LinkedList<Publicacion>();

                    for (int i = 0; i < ltrabajador.size(); i++) {
                        criteriaFamiliar = session.createCriteria(Familiar.class);
                        criteriaTitulo = session.createCriteria(Titulo.class);
                        criteriaCertificacion = session.createCriteria(Certificacion.class);
                        criteriaCurso = session.createCriteria(Curso.class);
                        criteriaAntecedent = session.createCriteria(Ant_Laborales.class);
                        criteriaMerito = session.createCriteria(MeritoDemerito.class);
                        criteriaPublicacion = session.createCriteria(Publicacion.class);
                        criteriaFamiliar.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaTitulo.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaCertificacion.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaCurso.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaAntecedent.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaMerito.add(Restrictions.eq("trabajador", ltrabajador.get(i)));
                        criteriaPublicacion.add(Restrictions.eq("trabajador", ltrabajador.get(i)));

                        lf.addAll(criteriaFamiliar.list());
                        lt.addAll(criteriaTitulo.list());
                        lc.addAll(criteriaCertificacion.list());
                        lcur.addAll(criteriaCurso.list());
                        lal.addAll(criteriaAntecedent.list());
                        lmd.addAll(criteriaMerito.list());
                        lp.addAll(criteriaPublicacion.list());

                    }

                    //familiar
                    if (!lf.isEmpty()) {
                        //generacion archivo familiar.csv
                        errores = myXLS.creadorCSVFamiliar(lf, newlocation + "COMPTRA1.csv", _entidadUE, session);
                    }

                    //titulo
                    if (!lt.isEmpty()) {
                        System.out.println("TITULO");
                        //generacion archivo titulos.csv
                        errores = myXLS.creadorCSVTitulo(lt, newlocation + "COMPTRA2.csv", _entidadUE, session);
                    }

                    //certificacion
                    if (!lc.isEmpty()) {
                        System.out.println("CERTIFICACION");
                        //generacion archivo certificacion.csv
                        errores = myXLS.creadorCSVCertificacion(lc, newlocation + "COMPTRA3.csv", _entidadUE, session);
                    }

                    //curso
                    if (!lcur.isEmpty()) {
                        System.out.println("CURSO");
                        //generacion archivo curso.csv
                        errores = myXLS.creadorCSVCurso(lcur, newlocation + "COMPTRA4.csv", _entidadUE, session);
                    }

                    //antecedent laboral
                    if (!lal.isEmpty()) {
                        System.out.println("ANTECEDENT");
                        //generacion archivo antecedent.csv
                        errores = myXLS.creadorCSVAntecdentLaboral(lal, newlocation + "COMPTRA5.csv", _entidadUE, session);
                    }

                    //merito demerito
                    if (!lmd.isEmpty()) {
                        System.out.println("MERITO");
                        //generacion archivo merito demerito.csv
                        errores = myXLS.creadorCSVMeritoDemerito(lmd, newlocation + "COMPTRA6.csv", _entidadUE, session);
                    }

                    //produccion intelectual
                    if (!lp.isEmpty()) {
                        System.out.println("PRODUCION");
                        //generacion archivo produccion intelectual.csv
                        errores = myXLS.creadorCSVProduccionIntelectual(lp, newlocation + "COMPTRA7.csv", _entidadUE, session);
                    }

                    List<CargoAsignado> lca = new LinkedList<CargoAsignado>();
                        for (Legajo legajo:listlegajo) {
                            Criteria criteriaCargoAsignado;
                            criteriaCargoAsignado = session.createCriteria(CargoAsignado.class);
                            criteriaCargoAsignado
                                    .add(Restrictions.eq("legajo", legajo))
                                    .add(Restrictions.eq("estado", helpers.Constantes.ESTADO_ACTIVO))
                                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                            lca.addAll(criteriaCargoAsignado.list());
                        }                       
                    
                    List<RemuneracionPersonalCSV> lrpcsv = new LinkedList<RemuneracionPersonalCSV>();
                    List<EvaluacionPersonalCSV> lepcsv = new LinkedList<EvaluacionPersonalCSV>();
                    List<AusLicPersonalCSV> lalpcsv = new LinkedList<AusLicPersonalCSV>();

                    if (!lca.isEmpty()) {
                        System.out.println("CARGO ASIGNADO");
                        //generacion archivo cargo asignado.csv
                        errores = myXLS.creadorCSVCargoAsignado(lca, newlocation + "PPAL3.csv", session);
                        for (int i = 0; i < lca.size(); i++) {
                            CargoAsignado ca = (CargoAsignado) lca.get(i);
                            lrpcsv.addAll(CreadorDesdeDB.generarParaCSVRemuneracionPersonal(ca, _entidadUE));
                            lepcsv.addAll(CreadorDesdeDB.generarParaCSVEvaluacionPersonal(ca, _entidadUE));
                            lalpcsv.addAll(CreadorDesdeDB.generarParaCSVAusLicPersonal(ca, _entidadUE));
                        }
                        //generacion archivo remuneracion personal.csv
                        if (!lrpcsv.isEmpty()) {
                            errores = myXLS.creadorCSVRemuneracionPersonal(lrpcsv, newlocation + "COMPCAR1.csv", session);
                        }

                        //generacion archivo evaluacion personal.csv
                        if (!lepcsv.isEmpty()) {
                            errores = myXLS.creadorCSVEvaluacion(lepcsv, newlocation + "COMPCAR2.csv", session);
                        }

                        //generacion archivo ausencia licencia personal.csv
                        if (!lalpcsv.isEmpty()) {
                            errores = myXLS.creadorCSVAusenciaLicencia(lalpcsv, newlocation + "COMPCAR3.csv", session);
                        }
                    }
                }
            }
        }
        
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
