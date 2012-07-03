/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.entities.MenuPorPerfil;
import com.tida.servir.entities.Menuperfil;
import com.tida.servir.entities.Perfil;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jurguen Zambrano
 */
public class PagePerfil {

    @Inject
    private Session session;
    @Persist
    @Property
    private Perfil perfil;
    @Property
    private Perfil rowPerfil;
    @Persist
    @Property
    private Menuperfil permiso;
    @Property
    private MenuPorPerfil rowPermiso;
    
    @Property
    private boolean mostrarEdit = false;
    @Property
    private boolean mostrarNew = true;
    
    @Property
    private boolean mostrarPermiso = false;
    @Property
    private boolean mostrarEditPermiso = false;
    @Property
    private boolean mostrarNewPermiso = false;
    
    @InjectComponent
    private Zone listaZone;
    @InjectComponent
    private Zone editZone;
    @InjectComponent
    private Zone newZone;
    
//    @InjectComponent
//    private Zone newPermisoZone;
//    @InjectComponent
//    private Zone editPermisoZone;
//    @InjectComponent
//    private Zone listapermisoZone;
    
    
    @Component(id = "perfilinputform")
    private Form perfilinputform;

    public PagePerfil() {
    }

    String onActivate() {
        // Validar Acceso a la página
        /*
         * if (!userExists) { return "Index"; } return null;
         */
        return null;
    }

    public List<Perfil> getAllPerfiles() {
        Criteria criterios;
        List<Perfil> lista = null;
        criterios = session.createCriteria(Perfil.class);
        lista = criterios.list();
        return lista;
    }

//    public List<MenuPorPerfil> getAllPermisos() {
//        List<MenuPorPerfil> lista = null;
//        Query query = session.getNamedQuery("callSpMenuPorPerfil");
//        query.setParameter("in_perfil_id", perfil.getId());
//
//        lista = query.list();
//        return lista;
//    }

    @Log
    @CommitAfter
    Object onEliminaPerfil(Perfil lperfil) {
        session.delete(lperfil);
        return listaZone.getBody();
    }

    @Log
    @CommitAfter
    Object onEditaPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = false;
        mostrarEdit = true;
        return zonasTotal();
    }

    @Log
    @CommitAfter
    Object onNuevoPerfil() {
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        mostrarNew = false;
        mostrarEdit = true;
        return zonasTotal();
    }

//    @Log
//    @CommitAfter
//    Object onPermisoPerfil(Perfil lperfil) {
//        perfil = lperfil;
//        mostrarNew = false;
//        mostrarEdit = false;
//        mostrarPermiso = true;
//        return zonasPermiso();
//    }
//
//    @Log
//    @CommitAfter
//    Object onEliminaPermiso(MenuPorPerfil lPermiso) {
//        Criteria c = session.createCriteria(Menuperfil.class);
//        c.add(Restrictions.eq("menuid", lPermiso.getMenuId()));
//        c.add(Restrictions.eq("perfilid", lPermiso.getPerfilId()));
//        Menuperfil menuperfil = (Menuperfil) c.list().get(0);
//        session.delete(menuperfil);
//        return listapermisoZone.getBody();
//    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        session.saveOrUpdate(perfil);
        return "PagePerfil";
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoInputForm() {
        session.saveOrUpdate(perfil);
        return "PagePerfil";
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        //add("listaZone", listaZone.getBody()).
        mu = new MultiZoneUpdate("editZone", editZone.getBody()).add("newZone", newZone.getBody());

        return mu;
    }

//    @Log
//    private MultiZoneUpdate zonasPermiso() {
//        MultiZoneUpdate mu;
//        //add("listaZone", listaZone.getBody()).
//        mu = new MultiZoneUpdate("listapermisoZone", listapermisoZone.getBody());
//
//        return mu;
//    }
}
