package com.tida.servir.components;

import com.tida.servir.entities.*;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Response;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

@Import(stylesheet = {"context:layout/menu.css", "context:layout/menu-2.css"})
public class ComponenteMenu {

    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad entidad;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @Inject
    private ComponentClassResolver componentClassResolver;
    @Inject
    private Context context;
    @Inject
    private ComponentResources resources;
    @Inject
    private Session session;
    @Property
    private UsuarioAcceso opcion;

    public Object onSalir() {
        _usuario = null;
        entidad = null;
        return "Index";
    }

    public List getOpcionesMenu() {
        Query query = session.getNamedQuery("callSpUsuarioAcceso");
        query.setParameter("in_login", usuarioTrabajador.getLogin());
        query.setParameter("in_menuid", opcion.getId());
        query.setParameter("in_pagename", resources.getPageName());

        List result = query.list();

        return result;
    }

    public List getOpcionesMenuPrincipal() {
        Query query = session.getNamedQuery("callSpUsuarioAcceso");
        query.setParameter("in_login", usuarioTrabajador.getLogin());
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

    public String getNombreUsuario() {
        return usuarioTrabajador.getApellidopaterno() + " " + usuarioTrabajador.getApellidomaterno() + ", " + usuarioTrabajador.getNombres() + " - " + usuarioTrabajador.getDenominacion();
    }
    
    @Log
    public List<ConfiguracionAcceso> getRuta() {
        Criteria c = session.createCriteria(ConfiguracionAcceso.class);        
        return c.list();
    }
    
    StreamResponse onActionFromReturnStreamResponse() {
        return new StreamResponse() {

            InputStream inputStream;

            @Override
            public void prepareResponse(Response response) {
                String STARTPATH=getRuta().get(0).getRuta_final();
//                try {
////                    reportesPath = context.getRealFile("/").getCanonicalPath();
//                } catch (IOException ex) {
//                    Logger.getLogger(ComponenteMenu.class.getName()).log(Level.SEVERE, null, ex);
//                }
                File fileADescargar = new File(STARTPATH + "Manual del RSC.pdf");

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