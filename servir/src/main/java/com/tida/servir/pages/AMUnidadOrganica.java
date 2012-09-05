package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades organicas
 *
 * @author ale
 *
 */
public class AMUnidadOrganica extends GeneralPage {

    @SessionState
    private Usuario loggedUser;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Inject
    private Session session;
    @Inject
    private Request _request;
    @InjectComponent
    private Zone listaUOZone;
    @InjectComponent
    @Property
    private Zone unidadesOrganicasZone;
    @InjectComponent
    @Property
    private Zone nivelUOZone;
    @Property
    @Persist
    private UnidadOrganica unidadOrganica;
    @Property
    @Persist
    private UnidadOrganica uoAntecesora;
    @Property
    private LkBusquedaUnidad uo;
    @Property
    @Persist
    private String domicilioCP;
    @Property
    @Persist
    private String domicilioDireccion;
    @Persist
    private boolean editando;
    // datos del formulario (que son persistentes)
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Component(id = "formmensaje")
    private Form formmensaje;
    @Property
    @Persist
    private String errorBorrar;
    @Property
    @Persist
    private Integer nivelUO;
    @Property
    @Persist
    private String localidad;
    @InjectComponent
    private Envelope envelope;
    @Persist
    @Property
    private String bdenouni;
    @Persist
    @Property
    private String bsigla;
    @Persist
    @Property
    private DatoAuxiliar valcategoria;
    @Property
    @Persist
    private LkBusquedaUnidad buoAntece;
    @InjectComponent
    @Property
    private Zone filtrosZone;
    @InjectComponent
    @Property
    private Zone mensajeZone;
    @Property
    @Persist
    private UsuarioAcceso usua;
    @Property
    @Persist
    private Integer bnivelUO;
    @Property
    @Persist
    private boolean mostrar;
    private int num, mostra, num2;
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vzona;
    @Inject  
    private ComponentResources _resources;
    @Inject
    private PropertyAccess _access;
    @Property
    private boolean borrarForm;
    @Persist
    @Property
    private Boolean vNoedita; //Nos sirve para ocultar los botones de Cancelar y Limpiar Formulario

    
    // loguear operación
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, loggedUser, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        return null;
    }
    
    // inicio de pagina
    @Log
    String SetupRender() {
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", loggedUser.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        // Inicializamos las variables utilizadas
        vbotones = false;
        vNoedita = false;
        veditar = false;
        vbotones = false;
        veliminar = false;
        vformulario = false;
        logueo();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
            return "alerta";
        } else {
            usua = (UsuarioAcceso) result.get(0);
            if (usua.getAccesoupdate() == 1) {
                veditar = true;
                vbotones = true;
            }
            if (usua.getAccesodelete() == 1) {
                veliminar = true;
            }
            if (usua.getAccesoreport() == 1) {
                vformulario = true;
                vbotones = true;
            }

        }        
        resetUnidadOrganica();
        onSelectedFromLimpia();
        ubicacion();
        return null;
    }

    @Log
    public boolean getHayNivel() {
        return !(bnivelUO == null);
    }

    @Log
    public GenericSelectModel<LkBusquedaUnidad> getbbeansUO() {
        Criteria c;
        c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.eq("entidadId", entidadUE.getId()));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        if (bnivelUO != null) {
            c.add(Restrictions.eq("nivel", bnivelUO - 1));
        }        
        return new GenericSelectModel<LkBusquedaUnidad>(c.list(), LkBusquedaUnidad.class, "denominacion", "id", _access);
    }


    // accion de los botones
    @Log
    void onSelectedFromMuestra() {
        num2 = 3;
        mostra = 2;
        mostrar = true;
        bnivelUO = null;
        buoAntece = null;
        valcategoria = null;
        bdenouni = null;
        bsigla = null;
    }

    @Log
    void onSelectedFromLimpia() {
        num2 = 2;
        bnivelUO = null;
        buoAntece = null;
        valcategoria = null;
        bdenouni = null;
        bsigla = null;
    }

    @Log
    void onSelectedFromSave() {
        num = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariofiltrounidad() {
        if (num2 == 3) {
            return zonasfiltros();

        } else if (num2 == 2) {
            return zonasfiltros();
        } else {
            mostrar = true;
            editando = false;
            mostra = 1;
            unidadOrganica = new UnidadOrganica();
            resetUnidadOrganica();
        }
        return zonas();
    }

    @Log
    public Boolean getBniveluno() {
        return bnivelUO == 1;
    }
    // cargar unidades organicas
    @Log
    public List<UnidadOrganica> getUnidadesOrganicas() {
        Criteria c;
        c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.eq("entidadId", entidadUE.getId()));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        if (mostra == 2) {
        } else {
            if (bnivelUO != null) {
                c.add(Restrictions.eq("nivel", bnivelUO));
            }
            if (buoAntece != null && !buoAntece.equals("")) {
                c.add(Restrictions.eq("unidadorganicaId", buoAntece.getId()));
            }
            if (bdenouni != null && !bdenouni.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenouni + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenouni.replaceAll("Ã±", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenouni.replaceAll("n", "Ã±") + "%").ignoreCase()));
            }
            if (bsigla != null && !bsigla.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("sigla", bsigla + "%").ignoreCase()).add(Restrictions.like("sigla", bsigla.replaceAll("Ã±", "n") + "%").ignoreCase()).add(Restrictions.like("sigla", bsigla.replaceAll("n", "Ã±") + "%").ignoreCase()));
            }
            if (valcategoria != null && !valcategoria.equals("")) {
                c.add(Restrictions.like("categoriauoId", valcategoria.getId()));
            }
        }
        c.addOrder(Order.asc("denominacion"));
        return c.list();
    }

    /**
     * Hasta acÃ¡ para levantar combo de organos
     */
    @Log
    void cargoDatos() {
        ubigeoDomicilio = new Ubigeo();
        ubigeoDomicilio.setDepartamento(unidadOrganica.getCod_ubi_dept());
        ubigeoDomicilio.setProvincia(unidadOrganica.getCod_ubi_prov());
        ubigeoDomicilio.setDistrito(unidadOrganica.getCod_ubi_dist());
        nivelUO = unidadOrganica.getNivel();
        uoAntecesora = unidadOrganica.getUnidadorganica();
    }

    @Log
    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    @Log
    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }

    @Log
    public boolean getEsBorrable() {
        /*
         * Buscamos; Cargos
         */
        Criteria c;
        c = session.createCriteria(Cargoxunidad.class);
        c.add(Restrictions.eq("unidadorganica", uo));
        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        // no quiero las que estÃ©n en baja.
        if (c.list().size() > 0) {
            return false;
        }
        Criteria p;
        p = session.createCriteria(LkBusquedaUnidad.class);
        p.add(Restrictions.eq("unidadorganicaId", uo.getId()));
        p.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        // no quiero las que estÃ©n en baja.
        if (p.list().size() > 0) {
            return false;
        }

        return true;
    }

    @Log
    public List<Integer> getNivel() {
        List<Integer> nivel = new LinkedList<Integer>();
        Integer nivelMax = 0;

        nivelMax = Helpers.maxNivelUO(entidadUE, session);
        for (int i = 1; i <= nivelMax + 1; i++) {
            // Es mas uno porque agregamos hasta un nivel mas
            nivel.add(i);
        }

        return nivel;
    }

    // cargar combos
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoActividad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIAUO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipovia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipozona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //lista unidades antecesoras
    @Log
    public GenericSelectModel<UnidadOrganica> getbeansUO() {

        Criteria c;
        c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidad", entidadUE));
        c.add(Restrictions.ne("id", unidadOrganica.getId()));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        c.add(Restrictions.eq("nivel", nivelUO - 1));


        return new GenericSelectModel<UnidadOrganica>(c.list(), UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    @Log
    public Boolean getNivelUno() {
        return nivelUO == 1;
    }

    // cargar nueva unidad organica
    @Log
    void resetUnidadOrganica() {
        vNoedita = true;
        unidadOrganica = new UnidadOrganica();
        ubicacion();
        nivelUO = 1;
        uoAntecesora = null;
        mostrar = true;
        if (!veditar) {
            vformulario = false;
        }
        vdetalle = false;
        vzona = true;
        num = 2;
        editando = false;
    }

    // limpiar 
    @Log
    Object onReset() {
        resetUnidadOrganica();
        return unidadesOrganicasZone.getBody();
    }

    // cancelar accion
    @Log
    Object onCancel() {
        resetUnidadOrganica();
        return unidadesOrganicasZone.getBody();
    }

    // formulario principal
    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaunidadorganica() {
        if (num == 2) {
        } else if (num == 3) {
        } else if (num == 4) {
            return unidadesOrganicasZone.getBody();
        } else if (num == 1) {
            if (nivelUO > 1) {
                if (uoAntecesora == null) {
                    formmensaje.recordError("Tiene que seleccionar Unidad Organica Antecesora.");
                    return zonas();
                } else {
                    errorBorrar = null;
                    Criteria c;
                    //String consulta = "SELECT * FROM RSC_UNIDADORGANICA WHERE UPPER(cod_und_organica)=UPPER('" +  unidadOrganica.getCod_und_organica() + "') AND S2.ENTIDAD_ID='" + unidadOrganica.getEntidad().getId() + "'";
                    c = session.createCriteria(UnidadOrganica.class);

                    if (editando) {
                        //consulta += ("AND ID!='" + unidadOrganica.getId() + "'");
                        c.add(Restrictions.ne("id", unidadOrganica.getId()));
                    } else {
                        unidadOrganica.setEntidad(entidadUE);
                        unidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
                    }
                    c.add(Restrictions.disjunction().add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()).ignoreCase()));
                    c.add(Restrictions.eq("entidad", unidadOrganica.getEntidad()));
                    
                    //Query query = session.createSQLQuery(consulta).addEntity(UnidadOrganica.class);
                    if (c.list().size() > 0) {
                        formmensaje.recordError(Errores.ERROR_COD_UND_ORG_UNICA);
                        formmensaje.recordError("Código ya existente: " + ((UnidadOrganica) c.list().get(0)).getCod_und_organica());
                        return zonas();
                    } else {
                        c = session.createCriteria(UnidadOrganica.class);
                        if (editando) {
                            c.add(Restrictions.ne("id", unidadOrganica.getId()));
                        } else {
                            unidadOrganica.setEntidad(entidadUE);
                            unidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
                        }
                        c.add(Restrictions.disjunction().add(Restrictions.like("den_und_organica", unidadOrganica.getDen_und_organica()).ignoreCase()));
                        c.add(Restrictions.eq("entidad", unidadOrganica.getEntidad()));
                        if (c.list().size() > 0) {
                            formmensaje.recordError("Denominación existente para la entidad: "
                                    + ((UnidadOrganica) c.list().get(0)).getDen_und_organica());
                            return zonas();
                        }

                    }
                    if (!editando) {
                    } else {
                        if (usua.getAccesoreport() == 0) {
                            vformulario = false;
                        }
                    }
                    unidadOrganica.setNivel(nivelUO);
                    unidadOrganica.setCod_ubi_dept(ubigeoDomicilio.getDepartamento());
                    unidadOrganica.setCod_ubi_dist(ubigeoDomicilio.getDistrito());
                    unidadOrganica.setCod_ubi_prov(ubigeoDomicilio.getProvincia());
                    unidadOrganica.setUnidadorganica(uoAntecesora);

                    /* INICIO CAMBIO    */
                    if (editando == true) {
                        envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_MODIFICADA_EXITO);
                    } else {
                        envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_CREADA_EXITO);
                    }

                    // FIN CAMBIO */ 

                    session.saveOrUpdate(unidadOrganica);                    
                    session.flush();
                    new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
                    editando = false;
                    resetUnidadOrganica();
                    formmensaje.clearErrors();
                }
            } else {
                errorBorrar = null;
                Criteria c;
                c = session.createCriteria(UnidadOrganica.class);

                if (editando) {
                    c.add(Restrictions.ne("id", unidadOrganica.getId()));
                } else {
                    unidadOrganica.setEntidad(entidadUE);
                    unidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
                }
                //c.add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()));
                c.add(Restrictions.disjunction().add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()).ignoreCase()));
                c.add(Restrictions.eq("entidad", unidadOrganica.getEntidad()));

                if (c.list().size() > 0) {
                    formmensaje.recordError(Errores.ERROR_COD_UND_ORG_UNICA);
                    formmensaje.recordError("Código ya existente: "
                            + ((UnidadOrganica) c.list().get(0)).getCod_und_organica());
                    return zonas();
                } else {
                    c = session.createCriteria(UnidadOrganica.class);
                    if (editando) {
                        c.add(Restrictions.ne("id", unidadOrganica.getId()));
                    } else {
                        unidadOrganica.setEntidad(entidadUE);
                        unidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
                    }
                    c.add(Restrictions.like("den_und_organica", unidadOrganica.getDen_und_organica()));
                    c.add(Restrictions.eq("entidad", unidadOrganica.getEntidad()));

                    if (c.list().size() > 0) {
                        formmensaje.recordError("Denominación existente para la entidad: "
                                + ((UnidadOrganica) c.list().get(0)).getDen_und_organica());
                        return zonas();
                    }

                }
                if (!editando) {
                } else {
                    if (usua.getAccesoreport() == 0) {
                        vformulario = false;
                    }
                }
                unidadOrganica.setNivel(nivelUO);
                unidadOrganica.setCod_ubi_dept(ubigeoDomicilio.getDepartamento());
                unidadOrganica.setCod_ubi_dist(ubigeoDomicilio.getDistrito());
                unidadOrganica.setCod_ubi_prov(ubigeoDomicilio.getProvincia());
                unidadOrganica.setUnidadorganica(uoAntecesora);
                /* INICIO CAMBIO    */
                if (editando == true) {
                    envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_MODIFICADA_EXITO);
                } else {
                    envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_CREADA_EXITO);
                }

                // FIN CAMBIO */ 
                session.saveOrUpdate(unidadOrganica);                
                session.flush();
                new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);    
                editando = false;
                resetUnidadOrganica();
                formmensaje.clearErrors();
            }
        }
        mostrar = true;
        ubicacion();
        return zonas();
    }

    @Log
    void guardar_modificar() {
    }

    // eliminar unidad organica
    @Log
    @CommitAfter
    Object onBorrarDato(UnidadOrganica dato) {
        errorBorrar = null;
        dato.setEstado(UnidadOrganica.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        resetUnidadOrganica();
        envelope.setContents("Unidad Orgánica Eliminada");
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        return zonas();// La/a zona a actualizar
    }

    @Log
    void onPrepareFromformularioaltaunidadorganica() {
        errorBorrar = null;
    }

    @Log
    void onActivate() {
    }

    @Log
    void ubicacion() {
        ubigeoDomicilio = new Ubigeo();
        ubigeoDomicilio.setDepartamento(entidadUE.getDepartamento());
        ubigeoDomicilio.setDistrito(entidadUE.getDistrito());
        ubigeoDomicilio.setProvincia(entidadUE.getProvincia());
        unidadOrganica.setTipovia(entidadUE.getTipoVia());
        unidadOrganica.setTipozona(entidadUE.getTipoZona());
        unidadOrganica.setLocalidad(entidadUE.getDescvia());
        unidadOrganica.setDesczona(entidadUE.getDescZona());
        if (unidadOrganica.getTipozona() != null) {
            if (unidadOrganica.getTipozona().getCodigo() == 14) {
                vzona = false;
            } else {
                vzona = true;
            }
        } else {
            vzona = true;
        }
    }

    @Log
    void onActivate(UnidadOrganica uo) {
        if (uo == null) {
            ubigeoDomicilio = new Ubigeo();
            resetUnidadOrganica();

        } else {
            unidadOrganica = uo;
            editando = true;
            cargoDatos();
        }
        ubicacion();
    }

    // actualizacion de zonas
    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("unidadesOrganicasZone", unidadesOrganicasZone.getBody()).
                add("listaUOZone", listaUOZone.getBody()).
                add("mensajeZone", mensajeZone.getBody());
        return mu;
    }

    @Log
    private MultiZoneUpdate zonasfiltros() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("nivelUOZone", nivelUOZone.getBody()).add("filtrosZone", filtrosZone.getBody()).add("listaUOZone", listaUOZone.getBody());;
        return mu;
    }

    // editar unidad organica seleccionada
    @Log
    Object onActionFromEditar(UnidadOrganica uo) {
        unidadOrganica = (UnidadOrganica) session.get(UnidadOrganica.class, uo.getId());
        editando = true;
        cargoDatos();
        vformulario = true;
        vdetalle = false;
        vbotones = true;
        vNoedita = false;
        if (unidadOrganica.getTipozona() != null) {
            if (unidadOrganica.getTipozona().getCodigo() == 14) {
                vzona = false;
            } else {
                vzona = true;
            }
        } else {
            vzona = true;
        }
        return zonas();
    }

    // ver detalles de unidad organica seleccionada
    @Log
    Object onActionFromDetalle(UnidadOrganica uo) {
        unidadOrganica = (UnidadOrganica) session.get(UnidadOrganica.class, uo.getId());
        editando = false;
        cargoDatos();
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        vNoedita = true;
        return zonas();
    }

    // evento de cambio de valor en campos
    @Log
    Object onValueChangedFromUnidadorganica_nivel(Integer dato) {
        nivelUO = dato;
        num = 4;
        System.out.println("nivellll" + nivelUO + "-" + num);

        return unidadesOrganicasZone.getBody();
    }

    @Log
    Object onValueChangedFromBunidadorganica_nivel(Integer dato) {
        if (dato != null) {
            bnivelUO = dato;
            if (bnivelUO == 1) {
                buoAntece = null;
            }
        }
        return nivelUOZone.getBody();
    }

    @Log
    Object onValueChangedFromBunidadOrganica_uoa(LkBusquedaUnidad dato) {
        buoAntece = dato;
        return nivelUOZone.getBody();
    }

    @Log
    Object onValueChangedFromUnidadOrganica_tipozona(DatoAuxiliar dato) {
        if (dato.getCodigo() == 14) {
            vzona = false;
        } else {
            vzona = true;
            unidadOrganica.setDesczona("");
        }
        return unidadesOrganicasZone.getBody();
    }

    @Log
    Object onValueChangedFromUnidadOrganica_tipovia(DatoAuxiliar dato) {
        unidadOrganica.setTipovia(dato);
        return unidadesOrganicasZone.getBody();
    }

    @Log
    Object onValueChangedFromUnidadOrganica_categoriauo(DatoAuxiliar dato) {
        unidadOrganica.setCategoriauo(dato);
        return unidadesOrganicasZone.getBody();
    }

    @Log
    void onCodigoChanged() {
        unidadOrganica.setCod_und_organica(_request.getParameter("param"));
    }

    @Log
    void onDenoChanged() {
        unidadOrganica.setDen_und_organica(_request.getParameter("param"));
    }

    @Log
    void onSiglaChanged() {
        unidadOrganica.setSigla(_request.getParameter("param"));
    }

    @Log
    void onDireChanged() {
        unidadOrganica.setLocalidad(_request.getParameter("param"));
    }

    @Log
    void onDescriChanged() {
        unidadOrganica.setDesczona(_request.getParameter("param"));
    }
}
