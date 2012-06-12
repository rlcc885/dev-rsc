package com.tida.servir.components;

import com.tida.servir.entities.Usuario;
import helpers.Accesos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Response;

@IncludeStylesheet({"context:layout/menu.css", "context:layout/menu-2.css"})
public class Menu {
   @Inject
   private ComponentClassResolver componentClassResolver;



    @Inject
    private Context context;
    
    @Inject
    private ComponentResources resources;


    @Property
    @SessionState
    private Usuario _usuario;


        private EnumMap<Accesos.MENUPADRE, List<Accesos.MENUHIJO>> menues;

        @Property
        @Persist
        private Accesos.MENUPADRE currentPadre;


        @Property
        @Persist
        private Accesos.MENUHIJO currentHijo;

        public Set<Accesos.MENUPADRE> getPagesMenuPadre(){
            return menues.keySet();
        }

        public List<Accesos.MENUHIJO> getPagesMenuHijos() {
            return menues.get(currentPadre);
        }

        public String getCurrentPadrePageName() {
            return Accesos.getPagePadreName(currentPadre);
        }

        public String getCurrentHijoPageName() {

            return Accesos.getPageHijoName(currentHijo);
        }

        public boolean getTieneHijos(){
            return menues.get(currentPadre) != null;
        }

        public String getPagePadreName() {
            return Accesos.getNombrePadre(currentPadre);
        }


        public String getPageHijoName() {
            return Accesos.getNombreHijo(currentHijo);
        }

        public String getIsActivePadre(){
            List<Accesos.MENUHIJO> hijos = menues.get(currentPadre);
            Boolean esta = false;

            if (hijos !=null) {
                for(Accesos.MENUHIJO hijo: hijos) {
                    esta = esta || ((Accesos.getPageHijoName(hijo)).equalsIgnoreCase(resources.getPageName()));
                }
            }
            return esta ? "selectedPadre" : null;
        }

        public String getIsActiveHijo(){
            return (getCurrentHijoPageName().equalsIgnoreCase(resources.getPageName())) ? "selectedHijo" : null;
        }

        
	@Property
	private boolean cambiarClave;
        
        @Persist
        private String nombres;       
        
        public String getNombres(){
            return _usuario.getNombres();
        }
        
        @Persist
        private String apellidos;       
        
        public String getApellidos(){
            return _usuario.getApellidos();
        }
        
        @Persist
        private String tipoUsuario;
        
        
        public String getTipoUsuario(){
            return _usuario.getTipo_usuario();
        }




   @SetupRender
   void cargoDatos() {
        System.out.println("------------------------Página: " +resources.getPageName());
       menues = new EnumMap<Accesos.MENUPADRE, List<Accesos.MENUHIJO>>(Accesos.MENUPADRE.class);
       List<Accesos.MENUHIJO> menuhijo;
/*
*/

          if(_usuario.getTipo_usuario().equals(Usuario.ADMINGRAL)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministrarUsuarios);
            menuhijo.add(Accesos.MENUHIJO.ReportesUsuarios);
            menues.put(Accesos.MENUPADRE.Usuarios, menuhijo);
          }

          if(_usuario.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministrarUsuarios);
            menuhijo.add(Accesos.MENUHIJO.ReportesUsuarios);
            menues.put(Accesos.MENUPADRE.Usuarios, menuhijo);
           }

          if(_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)) {

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.Inicio);
            menuhijo.add(Accesos.MENUHIJO.AdministracionEntidades);
            menuhijo.add(Accesos.MENUHIJO.ReportesEntidades);
            menues.put(Accesos.MENUPADRE.Entidades, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministracionTablas);
            menues.put(Accesos.MENUPADRE.TablasAuxiliares, menuhijo);
                      
            menues.put(Accesos.MENUPADRE.DeteccionIntrusion, null);
            menues.put(Accesos.MENUPADRE.Parametros, null);
            menues.put(Accesos.MENUPADRE.ProcesoBatch, null);
          }


          if (_usuario.getTipo_usuario().equals(Usuario.OPERADORABMSERVIR)) {

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.Inicio);
            menuhijo.add(Accesos.MENUHIJO.SeleccionEntidades);
            menuhijo.add(Accesos.MENUHIJO.DescargaArchivoCSV);
            menues.put(Accesos.MENUPADRE.Entidades, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.UnidadesOrganicas);
            menuhijo.add(Accesos.MENUHIJO.Cargos);
            menuhijo.add(Accesos.MENUHIJO.CambioUOEntidad);
            menuhijo.add(Accesos.MENUHIJO.ReportesEstructura);
            menuhijo.add(Accesos.MENUHIJO.ReportesNivel);
            menues.put(Accesos.MENUPADRE.Organigrama, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AltaTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ModificacionTrabajador);
            menuhijo.add(Accesos.MENUHIJO.BajaTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ReportesTrabajador);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministracionConceptos);
            menues.put(Accesos.MENUPADRE.ConceptosRemunerativos, menuhijo);

            menues.put(Accesos.MENUPADRE.ProcesoBatchUpload, null);
          }

          if(_usuario.getTipo_usuario().equals(Usuario.OPERADORABMLOCAL)) {

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.UnidadesOrganicas);
            menuhijo.add(Accesos.MENUHIJO.Cargos);
            menuhijo.add(Accesos.MENUHIJO.ReportesEstructura);
            menuhijo.add(Accesos.MENUHIJO.ReportesNivel);
            menuhijo.add(Accesos.MENUHIJO.DescargaArchivoCSV);
            menues.put(Accesos.MENUPADRE.Organigrama, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AltaTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ModificacionTrabajador);
            menuhijo.add(Accesos.MENUHIJO.BajaTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ReportesTrabajador);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);

            menues.put(Accesos.MENUPADRE.ProcesoBatchUpload, null);
           }

          if(_usuario.getTipo_usuario().equals(Usuario.OPERADORLECTURALOCAL)) {

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.UnidadesOrganicas);
            menuhijo.add(Accesos.MENUHIJO.Cargos);
            menuhijo.add(Accesos.MENUHIJO.ReportesEstructura);
            menuhijo.add(Accesos.MENUHIJO.ReportesNivel);
            menues.put(Accesos.MENUPADRE.Organigrama, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.ModificacionTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ReportesTrabajador);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);
           }

          if(_usuario.getTipo_usuario().equals(Usuario.OPERADORANALISTA)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.SeleccionEntidades);
            menues.put(Accesos.MENUPADRE.Entidades, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.UnidadesOrganicas);
            menuhijo.add(Accesos.MENUHIJO.Cargos);
            menuhijo.add(Accesos.MENUHIJO.ReportesEstructura);
            menuhijo.add(Accesos.MENUHIJO.ReportesNivel);
            menues.put(Accesos.MENUPADRE.Organigrama, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.ModificacionTrabajador);
            menuhijo.add(Accesos.MENUHIJO.ReportesTrabajador);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministracionConceptos);
            menues.put(Accesos.MENUPADRE.ConceptosRemunerativos, menuhijo);
           }

       
       if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.TrabajadorPage);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);
       }

       menues.put(Accesos.MENUPADRE.CambiarClave, null);
       menues.put(Accesos.MENUPADRE.Salir, null);
   }

   
       
    StreamResponse onActionFromReturnStreamResponse() {
        return new StreamResponse() {

            InputStream inputStream;

            @Override
            public void prepareResponse(Response response) {
                String reportesPath = "";
                try {
                    reportesPath = context.getRealFile("/").getCanonicalPath();
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                File fileADescargar = new File(reportesPath+"/Manual del RNSC.pdf");

                try {
                    inputStream = new FileInputStream(fileADescargar);
                } catch (FileNotFoundException ex) {
//                                    Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    response.setHeader("Content-Type", "application/x-pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileADescargar.getName());
                    response.setHeader("Content-Length", "" + inputStream.available());
                } catch (IOException e) {
                    // Ignore the exception in this simple example.
                }
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public InputStream getStream() throws IOException {
                return inputStream;
            }
        };
    }


}