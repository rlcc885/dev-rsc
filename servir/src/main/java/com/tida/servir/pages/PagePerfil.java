/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
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
    private Boolean editaPerfil;
    @Persist
    @Property
    private boolean mostrarNew;
    @Persist
    @Property
    private boolean editPermiso;
    @InjectComponent
    private Zone listaZone;
    @InjectComponent
    private Zone editZone;
    @InjectComponent
    private Zone listaPermisoZone;
    @InjectComponent
    private Zone editPermisoZone;
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
    @Persist
    @Property
    private boolean bcontrolTotal;
    @Persist
    @Property
    private boolean vNoeditaperfil;
    @Persist
    @Property
    private String fechaCreacion;
    @InjectComponent
    private Envelope envelope;
    @InjectComponent
    @Property
    private Zone mensajesEZone;
    @Component(id = "formulariomensajese")
    private Form formulariomensajese;

    // inicio de pagina
    @Log
    void setupRender() {
        if (editaPerfil == null) {
            editaPerfil = false;
        }
        nuevoPerfil();
        vNoeditaperfil = false;
        formulariomensajese.clearErrors();
    }

    @Log
    void nuevoPerfil() {
        mostrarNew = true;
        perfil = new Perfil();
        perfil.setFechacreacion(new Date());
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaCreacion = formatoDeFecha.format(perfil.getFechacreacion());
        perfil.setEstado(true);
        formulariomensajese.clearErrors();
        errorMessageSavePerfil = "";
    }

    @Log
    String onActivate() {
        return null;
    }

    @Log
    public boolean isEliminaPerfil() {
        if (rowPerfil.getId() > 8) {
            return true;
        } else {
            return false;
        }
    }

    // cargar combos de formulario
    @Log
    public GenericSelectModel<Menu> getBeanOpciones() {
        List<Menu> list;
        System.out.println(perfil.getId());
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
        nroregistros = Integer.toString(lista.size());        
        return lista;
    }

    @Persist
    @Property
    private String nroregistros;
    
    @Log
    public Boolean getPerfilAsignado(){
        System.out.println(rowPerfil);
//        Criteria c = session.createCriteria(Perfilporusuario.class);
//        c.add(Restrictions.eq("perfilId", rowPerfil.getId()));
       if (rowPerfil.getUsuarioCollection()!=null){ 
        if(rowPerfil.getUsuarioCollection().size() > 0){
//        if(c.list().isEmpty()){
            return false;
        }
       }
        //   rowPerfil;
        return true;
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
        System.out.println("PERFILX - "+lperfil.getDescperfil()+" "+lperfil.getId());
        formulariomensajese.clearErrors();
        if (lperfil.getUsuarioCollection().size() > 0) {
            formulariomensajese.recordError("El Perfil no puede ser eliminado porque existen usuarios asignados al perfil.");
        } else {
          //  if (lperfil.getMenuCollection().size() > 0) {
          //      formulariomensajese.recordError("El Perfil no puede ser eliminado porque existen opciones de menu asociados.");
          //  } else {
                try {
                    Criteria c = session.createCriteria(MenuPorPerfil.class);
                    c.add(Restrictions.eq("perfilId", lperfil.getId()));                            
                    List<MenuPorPerfil> lista = c.list();
                    if (!c.list().isEmpty())
                    {
                    for (MenuPorPerfil men : lista)
                      {
                        Menuperfil perfilE = new Menuperfil(men.getMenuId(), men.getPerfilId());
                        session.delete(perfilE);
                      }
                    }
                    session.delete(lperfil);
                    envelope.setContents("Perfil Eliminado satisfactoriamente.");

                    nuevoPerfil();
                } catch (Exception e) {
                    formulariomensajese.recordError("Otro error que pueda suceder :) ");
                }
          //  }
        }
      //  return this;
      return new MultiZoneUpdate("listaZone", listaZone.getBody()).add("mensajesEZone",mensajesEZone.getBody());  
    }
    // EDITA EL PERFIL

    @Log
    Object onActionFromEditaPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = true;
        errorMessageSavePerfil = "";
        formulariomensajese.clearErrors();
        editaPerfil = true;
        if (perfil.getId() <= 8) {
            vNoeditaperfil = true;
        } else {
            vNoeditaperfil = false;
        }
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaCreacion = formatoDeFecha.format(perfil.getFechacreacion());
        return zonasTotal();
    }

    // Si pulsa el enlace de PERMISOS
    @Log
    Object onActionFromPermisoPerfil(Perfil lperfil) {
        perfil = lperfil;
        mostrarNew = false;
        errorMessageSavePerfil = "";
        okMessageSavePerfil = "";
        formulariomensajese.clearErrors();
        editaPerfil = false;
        nuevoPermiso();
        return zonasTotal();
    }

    // Si elimina un PERMISO del perfil
    @Log
    @CommitAfter
    Object onEliminaPermiso(MenuPorPerfil lPermiso) {
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.get(Menuperfil.class, menuperfilpk);
        session.delete(permiso);
        nuevoPermiso();
        return zonasTotal();
    }

    @Log
    Object onEditaPermiso(MenuPorPerfil lPermiso) {
        Criteria c = session.createCriteria(Menu.class);
        c.add(Restrictions.eq("id", lPermiso.getMenuId()));
        menu = (Menu) c.list().get(0);
        MenuperfilPK menuperfilpk = new MenuperfilPK(lPermiso.getMenuId(), lPermiso.getPerfilId());
        permiso = (Menuperfil) session.get(Menuperfil.class, menuperfilpk);
        editPermiso = true;
        if (perfil.getId() <= 8) {
            vNoeditaperfil = true;
        } else {
            vNoeditaperfil = false;
        }
        return editPermisoZone.getBody();
    }

    @Log
    Object onReset() {
        nuevoPerfil();
        return editZone.getBody();
    }

    void onSelectedFromCancel() {
        editaPerfil = false;
        editPermiso = false;
        bCancelFormulario = true;
        vNoeditaperfil = false;
    }

    void nuevoPermiso() {
        editPermiso = false;
        permiso = new Menuperfil();
        menu = new Menu();
        menu.setId(0);
        errorMessageSavePerfil = "";
        okMessageSavePerfil = "";
        formulariomensajese.clearErrors();
    }

    @Log
    @CommitAfter
    Object onSuccessFromPerfilInputForm() {
        errorMessage = "";
        if (perfil.getDescperfil() == null || perfil.getDescperfil().isEmpty()) {
            formulariomensajese.recordError("Ingrese descripción del perfil.");
            return new MultiZoneUpdate ("editZone",editZone.getBody()).add("mensajesEZone", mensajesEZone.getBody());
        }
        Criteria c = session.createCriteria(Perfil.class);
        c.add(Restrictions.eq("descperfil", perfil.getDescperfil().toUpperCase()));
        c.add(Restrictions.ne("id", perfil.getId()));
        if (c.list().size() > 0) {
            formulariomensajese.recordError("Ya existe un perfil con la misma descripción.");
            return new MultiZoneUpdate ("editZone",editZone.getBody()).add("mensajesEZone", mensajesEZone.getBody());
        }
        //okMessageSavePerfil = "Perfil creado / modificado satisfactoriamente.";
        envelope.setContents("Perfil creado / modificado satisfactoriamente.");
        perfil.setDescperfil(perfil.getDescperfil().toUpperCase());
        session.saveOrUpdate(perfil);
        nuevoPerfil();
        return zonasTotal();
    }

    @Log
    @CommitAfter
    Object onResetNewPermiso() {
        cancelaNewPermiso = true;
        editaPerfil = false;
        mostrarNew = true;
        nuevoPerfil();
        return zonasTotal();
    }

    @Log
    @CommitAfter
    void onSelectedFromSaveNewPermiso() {
        bcontrolTotal = false;
    }

    // formulario principal
    @Log
    @CommitAfter
    Object onSuccessFromPermisoInputForm() {
        if (!editPermiso) {
            MenuperfilPK menuperfilpk = new MenuperfilPK();
            menuperfilpk.setMenuId(menu.getId());
            menuperfilpk.setPerfilId(perfil.getId());
            permiso.setMenuperfilPK(menuperfilpk);
        }
        session.saveOrUpdate(permiso);
        nuevoPermiso();
        return zonasTotal();
    }

    // actualizacion de zonas
    @Log
    private MultiZoneUpdate zonasTotal() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("listaZone", listaZone.getBody()).add("editZone", editZone.getBody()).
                add("listaPermisoZone", listaPermisoZone.getBody()).
                add("editPermisoZone", editPermisoZone.getBody()).
                add("mensajesEZone", mensajesEZone.getBody());
        return mu;
    }
}