/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
//    @Property
//    private String descPerfil;
//    @Property
//    @Persist
//    private boolean mostrarEdit;
    @Persist
    @Property
    private boolean mostrarNew;
    @Persist
    @Property
    private boolean mostrarPermiso;
    @Persist
    @Property
    private boolean mostrarEditPermiso;
//    @Persist
//    @Property
//    private boolean mostrarNewPermiso;
    @Persist
    @Property
    private boolean mostrarNuevoPermiso;
    @InjectComponent
    private Zone listaZone;
    @InjectComponent
    private Zone editZone;
//    @InjectComponent
//    private Zone newZone;
//    @InjectComponent
//    private Zone newPermisoZone;
    @InjectComponent
    private Zone editPermisoZone;
    @InjectComponent
    private Zone listaPermisoZone;
//    @Component(id = "perfilinputform")
//    private Form perfilinputform;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private Menu menu;
    @Property
    private long menuSeleccionado;
    @Property
    private GenericSelectModel<Menu> _selOpcion;
    @Persist
    @Property
    private boolean cancelaEditPermiso;
    @Persist
    @Property
    private boolean cancelaNewPermiso;
//    @Persist
    @Property
    private String errorMessage;
    @Property
    private String errorMessageSavePerfil;
    @Property
    @Persist
    private boolean bCancelFormulario;
    @Property
    @Persist
    private boolean bResetFormulario;

    public PagePerfil() {
    }

    @Log
    void SetupRender() {
        System.out.println("SetupRender");
        mostrarNew = true;
        //mostrarEdit = false;
        mostrarPermiso = false;
        mostrarNuevoPermiso = false;
        mostrarEditPermiso = false;
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        perfil.setEstado(true);
    }

    @Log
    String onActivate() {
        // Validar Acceso a la página
        /*
         * if (!userExists) { return "Index"; } return null;
         */
         return null;
    }

    public boolean isEliminaPerfil() {
        //|| rowPerfil.getMenuCollection().isEmpty() || rowPerfil.getUsuarioCollection().isEmpty()
        if (rowPerfil.getId() > 8) {
            return true;
        } else {
            return false;
        }
    }

    public GenericSelectModel<Menu> getBeanOpcionesTodo() {
        List<Menu> list;
        list = Helpers.getOpcionesDelMenu(0, session);
        _selOpcion = new GenericSelectModel<Menu>(list, Menu.class, "descmenu", "id", _access);
        return _selOpcion;
    }

    public GenericSelectModel<Menu> getBeanOpciones() {
        List<Menu> list;
        list = Helpers.getOpcionesDelMenu(perfil.getId(), session);
        _selOpcion = new GenericSelectModel<Menu>(list, Menu.class, "descmenu", "id", _access);
        return _selOpcion;
    }

    public List<Perfil> getAllPerfiles() {
        Criteria criterios;
        List<Perfil> lista = null;
        criterios = session.createCriteria(Perfil.class);
        criterios.addOrder(Order.asc("descperfil"));
        lista = criterios.list();
        return lista;
    }

    public List<MenuPorPerfil> getAllPermisos() {
        List<MenuPorPerfil> lista = null;
        Query query = session.getNamedQuery("callSpMenuPorPerfil");
        query.setParameter("in_perfil_id", perfil.getId());
        lista = query.list();
        return lista;
    }

    @Log
    @CommitAfter
    Object onEliminaPerfil(Perfil lperfil) {
        if (lperfil.getUsuarioCollection().size() > 0) {
            errorMessage = "El Perfil no puede ser eliminado porque existen usuarios asignados al perfil.";
        } else {
            if (lperfil.getMenuCollection().size() > 0) {
                errorMessage = "El Perfil no puede ser eliminado porque existen opciones de menu asociados.";
            } else {
                try {
                    session.delete(lperfil);
                    nuevoPerfil();
                    mostrarPermiso = false;
                    mostrarNuevoPermiso = false;
                    mostrarEditPermiso = false;
                } catch (Exception e) {
                    errorMessage = "Otro error que pueda suceder :) ";
                }
            }
        }
        return zonasTotal();
    }
    // EDITA EL PERFIL

    @Log
    @CommitAfter
    Object onEditaPerfil(Perfil lperfil) {
        System.out.println("onEditaPerfil");
        perfil = lperfil;
        mostrarNew = false;
        mostrarPermiso = false;
        mostrarNuevoPermiso = false;
        return zonasTotal();
    }
    // CREA PERFIL

//    @Log
//    @CommitAfter
//    Object onNuevoPerfil() {
//        perfil = new Perfil();
//        perfil.setFechacreacion(new Date());
////        mostrarNew = false;
//        mostrarEdit = true;
//        mostrarEditPermiso = false;
//        return zonasTotal();
//    }
    // GUARDA PERFIL
//    @Log
//    @CommitAfter
//    Object onGuardaPerfil(Perfil lPerfil) {
//        System.out.println("onGuardaPerfil");
//
//        session.saveOrUpdate(lPerfil);
////        mostrarNew = true;
////        mostrarEdit = false;
//        mostrarPermiso = false;
//        mostrarNewPermiso = false;
//        mostrarEditPermiso = false;
//        mostrarNuevoPermiso = false;
//        return zonasTotal();
//    }
    // CANCELA OPERACION EN PERFIL
//    @Log
//    @CommitAfter
//    Object onCancelaPerfil() {
////        mostrarNew = true;
////        mostrarEdit = false;
//        System.out.println("onCancelaPerfil");
//        mostrarPermiso = false;
////        mostrarNewPermiso = false;
//        mostrarEditPermiso = false;
//        mostrarNuevoPermiso = false;
//        return PagePerfil.class;
//    }
    // Si pulsa el enlace de PERMISOS
    @Log
    @CommitAfter
    Object onPermisoPerfil(Perfil lperfil) {
        System.out.println("onPermisoPerfil");
        perfil = lperfil;
        mostrarPermiso = true;
        nuevoPermiso();
        return zonasTotal();
    }
    // Si elimina un PERMISO del perfil

    @Log
    @CommitAfter
    Object onEliminaPermiso(MenuPorPerfil lPermiso) {
        System.out.println("onEliminaPermiso");
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.load(Menuperfil.class, menuperfilpk);
        session.delete(permiso);
        mostrarPermiso = true;
//        mostrarNewPermiso = true;
        nuevoPermiso();
        return zonasPermiso();
    }

    @Log
    @CommitAfter
    Object onEditaPermiso(MenuPorPerfil lPermiso) {
        System.out.println("onEditaPermiso");
        Criteria c = session.createCriteria(Menu.class);
        c.add(Restrictions.eq("id", lPermiso.getMenuId()));
        menu = (Menu) c.list().get(0);
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.load(Menuperfil.class, menuperfilpk);

        mostrarPermiso = true;
        mostrarEditPermiso = true;
        mostrarNuevoPermiso = false;
        return zonasPermiso();
    }

    void onSelectedFromReset() {
        System.out.println("onSelectedFromReset");
        bResetFormulario = true;
    }

    void onSelectedFromCancel() {
        System.out.println("onSelectedFromCancel");
        mostrarPermiso = false;
        mostrarNuevoPermiso = false;
        mostrarEditPermiso = false;
        bCancelFormulario = true;
    }

    void nuevoPermiso() {
        System.out.println("nuevoPermiso");
        mostrarNuevoPermiso = true;
        mostrarEditPermiso = false;
        permiso = new Menuperfil();
    }

    void nuevoPerfil() {
        System.out.println("nuevoPerfil");
        mostrarNew = true;
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        perfil.setEstado(true);
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        System.out.println("onSuccessFromPerfilInputForm");
        if (bCancelFormulario || bResetFormulario) {
            bCancelFormulario = false;
            bResetFormulario = false;
            nuevoPerfil();
            return zonasTotal();
        } else {
            List<Perfil> lista = null;
            Query query = session.getNamedQuery("Perfil.findByDescperfil");
            query.setParameter("descperfil", perfil.getDescperfil().toUpperCase());
            if (query.list().size()>0){
                errorMessageSavePerfil = "Ya existe un perfil con la misma descripción.";
                return editZone.getBody();
            }
            
            perfil.setDescperfil(perfil.getDescperfil().toUpperCase());
            if (mostrarNew) {
                perfil.setFechacreacion(new Date());
            }
            session.saveOrUpdate(perfil);
            //perfil = new Perfil();
            if (mostrarNew) {
                mostrarPermiso = true;
                nuevoPermiso();
                return zonasPermiso();
            } else {
                return zonasTotal();
            }
        }
    }

    @Log
    @CommitAfter
    void onSelectedFromResetNewPermiso() {
        System.out.println("onSelectedFromResetNewPermiso");
        cancelaNewPermiso = true;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoInputForm() {
        System.out.println("onSuccessFromPermisoInputForm");
        if (!cancelaNewPermiso) {
            MenuperfilPK menuperfilpk = new MenuperfilPK();
            menuperfilpk.setMenuId(menu.getId());
            menuperfilpk.setPerfilId(perfil.getId());
            permiso.setMenuperfilPK(menuperfilpk);
            session.save(permiso);
        }
        mostrarPermiso = true;
        nuevoPermiso();
        cancelaNewPermiso = false;
        return zonasPermiso();
    }

    void onSelectedFromResetEditPermiso() {
        System.out.println("onSelectedFromResetEditPermiso");
        cancelaEditPermiso = true;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoEditForm() {
        System.out.println("onSuccessFromPermisoEditForm");
        if (!cancelaEditPermiso) {
            session.saveOrUpdate(permiso);
        }
        mostrarPermiso = true;
        nuevoPermiso();
        cancelaEditPermiso = false;
        return zonasPermiso();
    }

    void onActionFromresetNuevoPermiso() {
        System.out.println("onActionFromresetNuevoPermiso");
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("editZone", editZone.getBody()).
                add("listaPermisoZone", listaPermisoZone.getBody()).
                add("listaZone", listaZone.getBody()).
                add("editPermisoZone", editPermisoZone.getBody());
        return mu;
    }

    @Log
    private MultiZoneUpdate zonasPermiso() {
        MultiZoneUpdate mu;
        //add("listaZone", listaZone.getBody()).
        mu = new MultiZoneUpdate("listaPermisoZone", listaPermisoZone.getBody()).
                add("listaZone", listaZone.getBody()).
                add("editPermisoZone", editPermisoZone.getBody());
        return mu;
    }
}