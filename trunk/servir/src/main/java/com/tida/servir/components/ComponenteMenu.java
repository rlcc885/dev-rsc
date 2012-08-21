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

    
    public Object onSalir()
    {
     _usuario=null;
     return "Index";
    }
    
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