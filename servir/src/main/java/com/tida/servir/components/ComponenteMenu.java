package com.tida.servir.components;

import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Usuario;
import com.tida.servir.entities.UsuarioAcceso;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Response;
import org.hibernate.Query;
import org.hibernate.Session;

@IncludeStylesheet({"context:layout/menu.css", "context:layout/menu-2.css"})
public class ComponenteMenu {

    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad entidad;
    @Inject
    private ComponentClassResolver componentClassResolver;
    @Inject
    private Context context;
    @Inject
    private ComponentResources resources;
    /*
    private EnumMap<Accesos.MENUPADRE, List<Accesos.MENUHIJO>> menues;
    @Property
    @Persist
    private Accesos.MENUPADRE currentPadre;
    @Property
    @Persist
    private Accesos.MENUHIJO currentHijo;
    @Property
    private boolean cambiarClave;
    @Persist
    private String nombres;
    @Persist
    private String apellidos;
    @Persist
    private String tipoUsuario;
    */
    @Inject
    private Session session;
    @Property
    private UsuarioAcceso opcion;

    public List getOpcionesMenu() {
        Query query = session.getNamedQuery("callSpUsuarioAcceso");
        query.setParameter("in_nrodocumento", _usuario.getTrabajador().getNroDocumento());
        query.setParameter("in_menuid", opcion.getId());
        query.setParameter("in_pagename", resources.getPageName());

        List result = query.list();

        return result;
    }
    
    public List getOpcionesMenuPrincipal() {
        Query query = session.getNamedQuery("callSpUsuarioAcceso");
        query.setParameter("in_nrodocumento", _usuario.getTrabajador().getNroDocumento());
        query.setParameter("in_menuid", 0);
        query.setParameter("in_pagename", resources.getPageName());

        List result = query.list();
        for (int i = 0; i < result.size(); i++) {
            UsuarioAcceso stock = (UsuarioAcceso) result.get(i);
            //System.out.println(stock.getDescmenu()+stock.getActivo().toString());
        }
        return result;
    }

    public boolean getTieneHijo() {
        if (opcion.getHijo() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getClase() {
        if (opcion.getNivel() == 0) {
            if (opcion.getActivo() == 1) {
                return "selectedPadre";
            } else {
                return "";
            }
        } else {
            if (opcion.getActivo() == 1) {
                return "selectedHijo";
            } else {
                return "";
            }
        }
    }
    
    public String getNombreUsuario(){
        return _usuario.getTrabajador().getApellidoPaterno()+" "+_usuario.getTrabajador().getApellidoMaterno()+", "+_usuario.getTrabajador().getNombres()+" - "+_usuario.getTrabajador().getEntidad().getDenominacion();
    }
/*
    public Set<Accesos.MENUPADRE> getPagesMenuPadre() {
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

    public boolean getTieneHijos() {
        return menues.get(currentPadre) != null;
    }

    public String getPagePadreName() {
        return Accesos.getNombrePadre(currentPadre);
    }

    public String getPageHijoName() {
        return Accesos.getNombreHijo(currentHijo);
    }

    public String getIsActivePadre() {
        List<Accesos.MENUHIJO> hijos = menues.get(currentPadre);
        Boolean esta = false;

        if (hijos != null) {
            for (Accesos.MENUHIJO hijo : hijos) {
                esta = esta || ((Accesos.getPageHijoName(hijo)).equalsIgnoreCase(resources.getPageName()));
            }
        }
        return esta ? "selectedPadre" : null;
    }

    public String getIsActiveHijo() {
        return (getCurrentHijoPageName().equalsIgnoreCase(resources.getPageName())) ? "selectedHijo" : null;
    }

    public String getNombres() {
        return _usuario.getNombres();
    }

    public String getApellidos() {
        return _usuario.getApellidos();
    }

    public String getTipoUsuario() {
        return _usuario.getTipo_usuario();
    }

    @SetupRender
    void cargoDatos() {

        System.out.println("------------------------Página: " + resources.getPageName());

        menues = new EnumMap<Accesos.MENUPADRE, List<Accesos.MENUHIJO>>(Accesos.MENUPADRE.class);
        List<Accesos.MENUHIJO> menuhijo;

        if (_usuario.getTipo_usuario().equals(Usuario.ADMINGRAL)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministrarUsuarios);
            menuhijo.add(Accesos.MENUHIJO.ReportesUsuarios);
            menues.put(Accesos.MENUPADRE.Usuarios, menuhijo);
        }

        if (_usuario.getTipo_usuario().equals(Usuario.ADMINLOCAL)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.AdministrarUsuarios);
            menuhijo.add(Accesos.MENUHIJO.ReportesUsuarios);
            menues.put(Accesos.MENUPADRE.Usuarios, menuhijo);
        }

        if (_usuario.getTipo_usuario().equals(Usuario.ADMINSISTEMA)) {

            menuhijo = new ArrayList<Accesos.MENUHIJO>();
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

        if (_usuario.getTipo_usuario().equals(Usuario.OPERADORABMLOCAL)) {

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

        if (_usuario.getTipo_usuario().equals(Usuario.OPERADORLECTURALOCAL)) {

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

        if (_usuario.getTipo_usuario().equals(Usuario.OPERADORANALISTA)) {
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


        if (_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR)) {
            menuhijo = new ArrayList<Accesos.MENUHIJO>();
            menuhijo.add(Accesos.MENUHIJO.TrabajadorPage);
            menues.put(Accesos.MENUPADRE.Trabajadores, menuhijo);
        }

        menues.put(Accesos.MENUPADRE.CambiarClave, null);
        menues.put(Accesos.MENUPADRE.Salir, null);
    }
*/
    StreamResponse onActionFromReturnStreamResponse() {
        return new StreamResponse() {

            InputStream inputStream;

            @Override
            public void prepareResponse(Response response) {
                String reportesPath = "";
                try {
                    reportesPath = context.getRealFile("/").getCanonicalPath();
                } catch (IOException ex) {
                    Logger.getLogger(ComponenteMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                File fileADescargar = new File(reportesPath + "/Manual del RNSC.pdf");

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