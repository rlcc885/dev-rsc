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
import org.apache.tapestry5.corelib.components.Checkbox;
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
    @Persist
    private boolean editaPerfil;
    @Persist
    @Property
    private boolean mostrarNew;
    @Persist
    @Property
    private boolean mostrarPermiso;
    @Persist
    @Property
    private boolean editPermiso;
    @InjectComponent
    private Zone listaZone;
    @InjectComponent
    private Zone editZone;
    @InjectComponent
    private Zone listaPermisoZone;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private Menu menu;
    @Property
    private long menuSeleccionado;
    @Persist
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
    @Persist
    @Property
    private String errorMessageSavePerfil;
    @Persist
    @Property
    private String okMessageSavePerfil;
    @Property
    @Persist
    private boolean bCancelFormulario;
    @Property
    @Persist
    private boolean bResetFormulario;
    @Property
    @Persist
    private boolean accesoTotal;
    @SuppressWarnings("unused")
    @Property
    private boolean selectAll = false;
    @InjectComponent
    @Property
    private Checkbox controlTotal;
    @Persist
    @Property
    private boolean bcontrolTotal;
    @Persist
    @Property
    private boolean vNoeditaperfil;
    @Persist
    @Property
    private Boolean mostrarEdicionPerfil;

    public PagePerfil() {
    }

    @Log
    void SetupRender() {
        if (mostrarEdicionPerfil == null ){
            mostrarEdicionPerfil = true;
        }
        if (!mostrarNew) {
            nuevoPerfil();
        }
    }

    @Log
    String onActivate() {
        // Validar Acceso a la página
        /*
         * if (!userExists) { return "Index"; } return null;
         */
        return null;
    }

    @Log
    public boolean isEliminaPerfil() {
        if (rowPerfil.getId() > 8 && rowPerfil.getMenuCollection().isEmpty() && rowPerfil.getUsuarioCollection().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Log
    public GenericSelectModel<Menu> getBeanOpciones() {
        List<Menu> list;
        if (editPermiso) {
            list = Helpers.getOpcionesDelMenu(0, session);
        } else {
            list = Helpers.getOpcionesDelMenu(perfil.getId(), session);
        }
        _selOpcion = new GenericSelectModel<Menu>(list, Menu.class, "descmenu", "id", _access);
        return _selOpcion;
    }

    @Log
    public List<Perfil> getAllPerfiles() {
        Criteria criterios;
        List<Perfil> lista = null;
        criterios = session.createCriteria(Perfil.class);
        criterios.addOrder(Order.asc("descperfil"));
        lista = criterios.list();
        return lista;
    }

    @Log
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
                } catch (Exception e) {
                    errorMessage = "Otro error que pueda suceder :) ";
                }
            }
        }
        return this;
    }
    // EDITA EL PERFIL

    @Log
    void onActionFromEditaPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = true;
        mostrarPermiso = false;
        errorMessageSavePerfil = null;
        okMessageSavePerfil = null;
        mostrarEdicionPerfil = true;
        if (perfil.getId()<=8){
            vNoeditaperfil = true;
        }else{
            vNoeditaperfil = false;
        }
    }

    // Si pulsa el enlace de PERMISOS
    @Log
    void onActionFromPermisoPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = true;
        mostrarPermiso = true;
        errorMessageSavePerfil = null;
        okMessageSavePerfil = null;
        mostrarEdicionPerfil = false;
        nuevoPermiso();
    }

    // Si elimina un PERMISO del perfil
    @Log
    @CommitAfter
    Object onEliminaPermiso(MenuPorPerfil lPermiso) {
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.get(Menuperfil.class, menuperfilpk);
        session.delete(permiso);
        mostrarPermiso = true;
        nuevoPermiso();
        return this;
    }

    @Log
    //void onActionFromEditaPermiso(MenuPorPerfil lPermiso) {
    Object onEditaPermiso(MenuPorPerfil lPermiso) {
        Criteria c = session.createCriteria(Menu.class);
        c.add(Restrictions.eq("id", lPermiso.getMenuId()));
        menu = (Menu) c.list().get(0);
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.get(Menuperfil.class, menuperfilpk);
        editPermiso = true;
        if (perfil.getId()<=8){
            vNoeditaperfil = true;
        }else{
            vNoeditaperfil = false;
        }
        return this;
    }

    void onSelectedFromReset() {
        bResetFormulario = true;
        mostrarPermiso = false;
        vNoeditaperfil = false;
    }

    void onSelectedFromCancel() {
        mostrarPermiso = false;
        editPermiso = false;
        bCancelFormulario = true;
    }

    void nuevoPermiso() {
        editPermiso = false;
        permiso = new Menuperfil();
        menu = new Menu();
        menu.setId(0);
        errorMessageSavePerfil = null;
        okMessageSavePerfil = null;
    }

    void nuevoPerfil() {
        mostrarNew = false;
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        perfil.setEstado(true);
        errorMessageSavePerfil = null;
        okMessageSavePerfil = null;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        errorMessage = "";
        if (bCancelFormulario || bResetFormulario) {
            bCancelFormulario = false;
            bResetFormulario = false;
            nuevoPerfil();
            return this;
        } else {
            if (perfil.getDescperfil() == null || perfil.getDescperfil().isEmpty()) {
                errorMessageSavePerfil = "Ingrese descripción del perfil.";
                return editZone;
            }
            Criteria c = session.createCriteria(Perfil.class);
            c.add(Restrictions.eq("descperfil", perfil.getDescperfil().toUpperCase()));
            c.add(Restrictions.ne("id", perfil.getId()));
            if (c.list().size() > 0) {
                errorMessageSavePerfil = "Ya existe un perfil con la misma descripción.";
                return editZone;
            }
            okMessageSavePerfil = "Perfil creado satisfactoriamente.";
            perfil.setDescperfil(perfil.getDescperfil().toUpperCase());
            session.saveOrUpdate(perfil);
            if (!mostrarNew) {
                mostrarPermiso = true;
                mostrarNew = true;
                nuevoPermiso();
                return this;
            } else {
                return listaPermisoZone;
            }
        }
    }

    @Log
    @CommitAfter
    void onSelectedFromResetNewPermiso() {
        cancelaNewPermiso = true;
    }

    @Log
    @CommitAfter
    Object onSuccessFromPermisoInputForm() {
        if (!cancelaNewPermiso) {
            if (!editPermiso) {
                MenuperfilPK menuperfilpk = new MenuperfilPK();
                menuperfilpk.setMenuId(menu.getId());
                menuperfilpk.setPerfilId(perfil.getId());
                permiso.setMenuperfilPK(menuperfilpk);
            }
            session.saveOrUpdate(permiso);
        }
        mostrarPermiso = true;
        nuevoPermiso();
        cancelaNewPermiso = false;
        return this;
    }

    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("editZone", editZone.getBody()).add("listaZone", listaZone.getBody());
        return mu;
    }

    @Log
    private MultiZoneUpdate zonasPermiso() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("listaZone", listaZone.getBody());
        return mu;
    }
}