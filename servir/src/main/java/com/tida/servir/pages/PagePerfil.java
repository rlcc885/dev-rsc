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
    @Property
    private String descPerfil;
    @Property
    private boolean mostrarEdit;
    @Property
    private boolean mostrarNew;
    @Persist
    @Property
    private boolean mostrarPermiso;
    @Persist
    @Property
    private boolean mostrarEditPermiso;
    @Persist
    @Property
    private boolean mostrarNewPermiso;
    @Persist
    @Property
    private boolean mostrarNuevoPermiso;
    @InjectComponent
    private Zone listaZone;
    @InjectComponent
    private Zone editZone;
    @InjectComponent
    private Zone newZone;
    @InjectComponent
    private Zone newPermisoZone;
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
    @Persist
    @Property
    private String errorMessage;

    public PagePerfil() {
    }

    String onActivate() {
        // Validar Acceso a la página
        /*
         * if (!userExists) { return "Index"; } return null;
         */
        mostrarNew = true;
        mostrarPermiso = false;
        mostrarNewPermiso = false;
        mostrarEditPermiso = false;
        return null;
    }

    public boolean isEliminaPerfil() {
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
            errorMessage = "El Perfil no puede ser eliminado porque existen usuarios asignados al perfill.";
        } else {
            if (lperfil.getMenuCollection().size() > 0) {
                errorMessage = "El Perfil no puede ser eliminado porque existen opciones de menu asociados.";
            } else {
                try {
                    session.delete(lperfil);

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
        perfil = lperfil;

        descPerfil = lperfil.getDescperfil();

        mostrarNew = false;
        mostrarEdit = true;
        mostrarPermiso = false;
        mostrarNewPermiso = false;
        mostrarNuevoPermiso = false;
        return zonasTotal();
    }
    // CREA PERFIL

    @Log
    @CommitAfter
    Object onNuevoPerfil() {
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        mostrarNew = false;
        mostrarEdit = true;
        mostrarEditPermiso = false;
        return zonasTotal();
    }
    // GUARDA PERFIL

    @Log
    @CommitAfter
    Object onGuardaPerfil(Perfil lPerfil) {
        System.out.println(lPerfil.getDescperfil());

        session.saveOrUpdate(lPerfil);
        mostrarNew = true;
        mostrarEdit = false;
        mostrarPermiso = false;
        mostrarNewPermiso = false;
        mostrarEditPermiso = false;
        mostrarNuevoPermiso = false;
        return zonasTotal();
    }
    // CANCELA OPERACION EN PERFIL

    @Log
    @CommitAfter
    Object onCancelaPerfil() {
        mostrarNew = true;
        mostrarEdit = false;
        mostrarPermiso = false;
        mostrarNewPermiso = false;
        mostrarEditPermiso = false;
        mostrarNuevoPermiso = false;
        return PagePerfil.class;
    }

    // Si pulsa el enlace de PERMISOS
    @Log
    @CommitAfter
    Object onPermisoPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = true;
        mostrarEdit = false;
        mostrarPermiso = true;
        mostrarNewPermiso = true;
        return zonasTotal();
    }
    // Si elimina un PERMISO del perfil

    @Log
    @CommitAfter
    Object onEliminaPermiso(MenuPorPerfil lPermiso) {
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.load(Menuperfil.class, menuperfilpk);
        session.delete(permiso);
        mostrarPermiso = true;
        mostrarNewPermiso = true;
        return zonasPermiso();
    }

    @Log
    @CommitAfter
    Object onEditaPermiso(MenuPorPerfil lPermiso) {
        Criteria c = session.createCriteria(Menu.class);
        c.add(Restrictions.eq("id", lPermiso.getMenuId()));
        menu = (Menu) c.list().get(0);
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.load(Menuperfil.class, menuperfilpk);

        mostrarPermiso = true;
        mostrarNewPermiso = false;
        mostrarEditPermiso = true;
        mostrarNuevoPermiso = false;
        return zonasPermiso();
    }

    @Log
    @CommitAfter
    Object onNuevoPermiso() {
        permiso = new Menuperfil();
        mostrarPermiso = true;
        mostrarNewPermiso = false;
        mostrarEditPermiso = false;
        mostrarNuevoPermiso = true;
        return zonasPermiso();
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        System.out.println(perfil.getDescperfil());
        session.saveOrUpdate(perfil);
        return "PagePerfil";
    }

    void onSelectedFromResetNewPermiso() {
        cancelaNewPermiso = true;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoInputForm() {
        if (!cancelaNewPermiso) {
            MenuperfilPK menuperfilpk = new MenuperfilPK();
            menuperfilpk.setMenuId(menu.getId());
            menuperfilpk.setPerfilId(perfil.getId());
            permiso.setMenuperfilPK(menuperfilpk);
            session.save(permiso);
        }
        mostrarPermiso = true;
        mostrarNewPermiso = true;
        mostrarEditPermiso = false;
        mostrarNuevoPermiso = false;
        cancelaNewPermiso = false;
        return zonasPermiso();
    }

    void onSelectedFromResetEditPermiso() {
        cancelaEditPermiso = true;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoEditForm() {
        if (!cancelaEditPermiso) {
            session.saveOrUpdate(permiso);
        }
        mostrarPermiso = true;
        mostrarNewPermiso = true;
        mostrarEditPermiso = false;
        mostrarNuevoPermiso = false;
        cancelaEditPermiso = false;
        return zonasPermiso();
    }

    void onActionFromresetNuevoPermiso() {
        System.out.println("onActionFromresetNuevoPermiso");
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        //add("listaZone", listaZone.getBody()).
        mu = new MultiZoneUpdate("editZone", editZone.getBody()).add("listaZone", listaZone.getBody()).add("newZone", newZone.getBody()).add("listaPermisoZone", listaPermisoZone.getBody()).add("newPermisoZone", newPermisoZone.getBody()).add("editPermisoZone", editPermisoZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasPermiso() {
        MultiZoneUpdate mu;
        //add("listaZone", listaZone.getBody()).
        mu = new MultiZoneUpdate("listaPermisoZone", listaPermisoZone.getBody()).add("newPermisoZone", newPermisoZone.getBody()).add("editPermisoZone", editPermisoZone.getBody());
        return mu;
    }
}
