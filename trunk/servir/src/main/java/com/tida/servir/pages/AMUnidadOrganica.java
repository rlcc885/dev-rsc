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

    @Property
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
    private Zone unidadesOrganicasZone;
    @InjectComponent
    @Property
    private Zone ubigeoDomZone;
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
//    @InjectComponent
//    @Property
//    private Zone nivelZone;
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
//    @Property
//    @Persist
//    private boolean siuno;
    @Property
    @Persist
    private Integer bnivelUO;
    @Property
    @Persist
    private boolean mostrar;
//    @Component(id = "formlistaunidad")
//    private Form formlistaunidad;
    //@Persist
    private int num, mostra, num2;
//    @InjectComponent
//    @Property
//    private Zone nivelOrga;
    //validaciones
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

    @Log
    //@SetupRender
    void SetupRender() {
        onSelectedFromLimpia();
        onSelectedFromReset();
        unidadOrganica = new UnidadOrganica();
        ubigeoDomicilio = new Ubigeo();
        nivelUO = 1;
        uoAntecesora = null;
//        mostrar=false;
        vbotones = false;
        vformulario = false;
        vdetalle = false;
        vzona = true;
        ubicacion();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento", loggedUser.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
            //return"alerta";
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
            //return null;
        }
    }


    @Log
    public boolean getHayNivel() {
        return !(bnivelUO == null);
    }

    @Log
    public GenericSelectModel<LkBusquedaUnidad> getbbeansUO() {
        String consulta="SELECT S1.id, S1.den_und_organica denominacion, S1.sigla, S1.nivel,"+
          " S1.unidadorganica_id, S1.CATEGORIAUO_ID, T1.DESCCATEGORIAUO,"+
          " S1.ENTIDAD_ID, S1.ESTADO FROM    rsc_unidadorganica S1 LEFT JOIN lkcategoriauo T1"+
          " ON (T1.categoriauo_id = s1.categoriauo_id) WHERE S1.ESTADO=1 AND S1.ENTIDAD_ID='"+entidadUE.getId()+"'";
        if(bnivelUO!=null){
            consulta+=" AND S1.NIVEL='"+(bnivelUO-1)+"'";
        }
        consulta+="ORDER BY(DENOMINACION)";
        List<LkBusquedaUnidad> list;
        Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaUnidad.class);
        list=query.list();
        return new GenericSelectModel<LkBusquedaUnidad>(list, LkBusquedaUnidad.class, "denominacion", "id", _access);
    }

    void onSelectedFromReset() {
        num = 2;
        unidadOrganica = new UnidadOrganica();
        ubigeoDomicilio = new Ubigeo();
        nivelUO = 1;
        editando = false;
        uoAntecesora = null;

    }

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

    void onSelectedFromLimpia() {
        num2 = 2;
        bnivelUO = null;
        buoAntece = null;
        valcategoria = null;
        bdenouni = null;
        bsigla = null;
    }

    void onSelectedFromSave() {
        num = 1;
    }

    void onSelectedFromCancel() {
        num = 3;
        if (!vbotones) {
            vformulario = false;
        } else {
            unidadOrganica = new UnidadOrganica();
            ubigeoDomicilio = new Ubigeo();
            nivelUO = 1;
            editando = false;
            uoAntecesora = null;
        }

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
            //envelope.setContents(String.valueOf(uoAntecesora)+"-"+String.valueOf(nivelUO));
            mostra = 1;
            unidadOrganica = new UnidadOrganica();
            this.onSelectedFromReset();
            //formularioaltaunidadorganica.recordError(String.valueOf(buoAntece));
        }
        //ubicacion();
        return zonas();
    }

    public Boolean getBniveluno() {
        return bnivelUO == 1;
    }

//    @Log
//    Object onActionFromMuestra() {
//        mostra=2;
//        mostrar=true;
//        return listaUOZone.getBody();
//    }
//     @Log
//    void onValidateFromFormularioaltaunidadorganica() {
//        if(nivelUO!=1){
//            if(uoAntecesora==null){
//                this.formularioaltaunidadorganica.recordError("Seleccione Unidad Antecesora");   
//            }
//        }            
//        envelope.setContents(helpers.Constantes.CARGO_EXITO);
//       
//     }
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
                //c.createAlias("unidadorganica", "unidadorganica");
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
        /*
         * if(!c.list().isEmpty()) unidadOrganica = (UnidadOrganica)
         * c.list().get(0);
         */
        return c.list();
    }

    public boolean getUsuarioGeneral() {
        return Helpers.esMultiOrganismo(loggedUser);
    }

    public boolean getNoUsuarioGeneral() {
        return !getUsuarioGeneral();
    }
    /*
     * public Object onValueChanged(Object o){ if(o != null) {
     * if(o.getClass().equals(EntidadUEjecutora.class)) { Criteria c; c =
     * session.createCriteria(Organo.class);
     * c.add(Restrictions.like("entidadUE", (EntidadUEjecutora) o));
     * c.add(Restrictions.ne("estado", Organo.ESTADO_BAJA)); List<Organo> list =
     * c.list(); entidadUE = (EntidadUEjecutora) o; _beans = new
     * GenericSelectModel<Organo>(list,Organo.class,"den_organo","id",_access);
     *
     * if (c.list().size() > 0) _org = (Organo) c.list().get(0); //return
     * selectZone.getBody(); return todasZonas(); }
     *
     * if(o.getClass().equals(Organo.class)) { _org = (Organo) o;
     * System.out.println("------------------------Cambiando _org="+
     * _org.getDen_organo());
     *
     * return zonas(); } else { System.out.println("------------------------No
     * es ni organo ni Organismo"); } } else {
     * System.out.println("------------------------ el dato es nulo !"); }
     * System.out.println("------------------------ Sale por el final !");
     * return zonas();
     *
     * }
     */
    /*
     * levantamos el combo de Organos
     */
    @Inject
    private PropertyAccess _access;

    public boolean getPuedeEditar() {
        if (entidadUE == null) {
            return false;
        }
        return Permisos.puedeEscribir(loggedUser, entidadUE);
    }

    /**
     * Hasta acÃ¡ para levantar combo de organos
     */
    @Log
    void cargoDatos() {
        ubigeoDomicilio = new Ubigeo();
        //System.out.println("------------unidadORGANICAZ "+unidadOrganica.getCod_und_organica());
        ubigeoDomicilio.setDepartamento(unidadOrganica.getCod_ubi_dept());
        ubigeoDomicilio.setProvincia(unidadOrganica.getCod_ubi_prov());
        ubigeoDomicilio.setDistrito(unidadOrganica.getCod_ubi_dist());
        nivelUO = unidadOrganica.getNivel();
        uoAntecesora = unidadOrganica.getUnidadorganica();
    }

    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }

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
        // no quiero las que estÃ©n en baja.
        if (p.list().size() > 0) {
            return false;
        }

        return true;
    }

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

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoActividad() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);

        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIAUO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipovia() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);

        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipozona() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);

        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    /*
     * public GenericSelectModel<EntidadUEjecutora> getBeansEUE(){ Criteria c; c
     * = session.createCriteria(UnidadOrganica.class);
     * c.add(Restrictions.eq("entidadUE", entidadUE));
     * c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
     *
     * return new
     * GenericSelectModel<EntidadUEjecutora>(c.list(),EntidadUEjecutora.class,"denominacion","id",_access);
     * }
     */
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
//    @Log
//    public GenericSelectModel<LkBusquedaUnidad> getbeansUO() {
//        String consulta="SELECT S1.id, S1.den_und_organica denominacion, S1.sigla, S1.nivel,"+
//          " S1.unidadorganica_id, S1.CATEGORIAUO_ID, T1.DESCCATEGORIAUO,"+
//          " S1.ENTIDAD_ID, S1.ESTADO FROM    rsc_unidadorganica S1 LEFT JOIN lkcategoriauo T1"+
//          " ON (T1.categoriauo_id = s1.categoriauo_id) WHERE S1.ESTADO=1 AND S1.ENTIDAD_ID='"+entidadUE.getId()+"'"+
//          " AND S1.NIVEL='"+(nivelUO - 1)+"' AND S1.ID!='"+unidadOrganica.getId()+"'";
//        consulta+="ORDER BY(DENOMINACION)";
//        List<LkBusquedaUnidad> list;
//        Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaUnidad.class);
//        list=query.list();
//        return new GenericSelectModel<LkBusquedaUnidad>(list, LkBusquedaUnidad.class, "denominacion", "id", _access);
//    }
    

//    @Log
//    @CommitAfter
//    Object onSuccessFromformNivelUO() {
//
//        
////        if(uoAntecesora != null){
////            System.out.println("------------- unidad organica "+uoAntecesora.getCod_und_organica());
////            ubigeoDomicilio.setDepartamento(uoAntecesora.getCod_ubi_dept());
////            ubigeoDomicilio.setDistrito(uoAntecesora.getCod_ubi_dist());
////            ubigeoDomicilio.setProvincia(uoAntecesora.getCod_ubi_prov());
////        }
//        
//        if (getPuedeEditar()) {
//            System.out.println("--------- entrÃ© iuci");
//            //onUbigeoEntidadUOAntecesora();
//            return new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody()).add("nivelZone", nivelZone.getBody());
//        }
//        return nivelZone.getBody();
//    }
    public Boolean getNivelUno() {
        return nivelUO == 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaunidadorganica() {
        System.out.println("altaaa" + num);
        if (num == 2) {
        } else if (num == 3) {
        } else if (num == 4) {
            System.out.println("altassss" + num);
            return unidadesOrganicasZone.getBody();

        } else if (num == 1) {
            if (nivelUO > 1) {
                if (uoAntecesora == null) {
                    formmensaje.recordError("Tiene que seleccionar Unidad Organica Antecesora.");
                    return zonas();
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
                    c.add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()));
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
                    session.saveOrUpdate(unidadOrganica);
                    new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_MODIFICACION : Logger.CODIGO_OPERACION_ALTA), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
                    editando = false;
                    session.flush();
                    onSelectedFromReset();
//                setupUbigeos();
                    formmensaje.clearErrors();
                    envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_EXITO);
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
                c.add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()));
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
                session.saveOrUpdate(unidadOrganica);
                new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_MODIFICACION : Logger.CODIGO_OPERACION_ALTA), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
                editando = false;
                session.flush();
                onSelectedFromReset();
//            setupUbigeos();
                formmensaje.clearErrors();

                envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_EXITO);

            }

        }
        mostrar = true;
        ubicacion();
        System.out.println("plopp");
        return zonas();
    }

    void guardar_modificar() {
    }
    @Property
    private boolean borrarForm;

    @Log
    @CommitAfter
    Object onBorrarDato(UnidadOrganica dato) {
        errorBorrar = null;
        dato.setEstado(UnidadOrganica.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        onSelectedFromReset();
        envelope.setContents("Unidad Orgánica Eliminada");
//        //setupUbigeos();
//        javaScriptSupport.addScript("new Prin();");
//        if(bescon==null){
//            envelope.setContents("Borrooo--"+String.valueOf(bescon));
//        }
//        else{
//            envelope.setContents("NO Borrooo");
//        }

        return zonas();// La/a zona a actualizar
    }

//    @Log
//    @SetupRender
//    private void setupUbigeos() {
//        if (nivelUO == null) {
//            nivelUO = 1;
//        }
//
////        if (unidadOrganica != null) {
////            if (ubigeoDomicilio == null) {
////                ubigeoDomicilio = new Ubigeo();
////            }
////            ubigeoDomicilio.setDepartamento(entidadUE.getCod_ubi_dept());
////            ubigeoDomicilio.setProvincia(entidadUE.getCod_ubi_prov());
////            ubigeoDomicilio.setDistrito(entidadUE.getCod_ubi_dist());
////        }
//
//        if (editando != false) {
//           // onUbigeoEntidadUOAntecesora();
//        }
//        //ubicacion();
//    }
    @Log
    void onPrepareFromformularioaltaunidadorganica() {
        errorBorrar = null;
    }

//    @Log
//    void onUbigeoEntidadUOAntecesora() {
//        if (unidadOrganica != null) {
//            // cargamos el ubigeo de la entidad
//            ubigeoDomicilio.setDepartamento(unidadOrganica.getCod_ubi_dept());
//            ubigeoDomicilio.setProvincia(unidadOrganica.getCod_ubi_prov());
//            ubigeoDomicilio.setDistrito(unidadOrganica.getCod_ubi_dist());            
//        } else {
//            if (uoAntecesora != null) {
//                System.out.println("----------- antecesora pas null "+uoAntecesora);
//                ubigeoDomicilio.setDepartamento(uoAntecesora.getCod_ubi_dept());
//                ubigeoDomicilio.setDistrito(uoAntecesora.getCod_ubi_dist());
//                ubigeoDomicilio.setProvincia(uoAntecesora.getCod_ubi_prov());                
//            }else{
//                System.out.println("----------- antecesora null "+uoAntecesora);
//                ubigeoDomicilio.setDepartamento(entidadUE.getDepartamento());
//                ubigeoDomicilio.setDistrito(entidadUE.getDistrito());
//                ubigeoDomicilio.setProvincia(entidadUE.getProvincia());
//            }
//
//        }
//        
//    }

    /*
     * reset del formulario (borrar objeto)
     */
//    @Log
//    void onActionFromReset() {
//        unidadOrganica = new UnidadOrganica();
//        ubigeoDomicilio = new Ubigeo();
//        nivelUO = 1;
//        uoAntecesora = null;
//        editando = false;
//        //onUbigeoEntidadUOAntecesora();
//    }

    /*
     * Cargar desde los parÃ¡metros
     */
    @Log
    void onActivate() {
        if (unidadOrganica == null) {
            System.out.println("----------- unidad organica null");
            onSelectedFromReset();
            ubicacion();
            zonas();
        }
    }

    void ubicacion() {
        ubigeoDomicilio.setDepartamento(entidadUE.getDepartamento());
        ubigeoDomicilio.setDistrito(entidadUE.getDistrito());
        ubigeoDomicilio.setProvincia(entidadUE.getProvincia());
        unidadOrganica.setTipovia(entidadUE.getTipoVia());
        unidadOrganica.setTipozona(entidadUE.getTipoZona());
        unidadOrganica.setLocalidad(entidadUE.getDireccion());
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
            onSelectedFromReset();

        } else {
            unidadOrganica = uo;
            editando = true;
            cargoDatos();
        }
        ubicacion();
    }

    @Log
    Object onActionFromDetalle(UnidadOrganica uo) {
        unidadOrganica = (UnidadOrganica) session.load(UnidadOrganica.class, uo.getId());
        editando = false;
        cargoDatos();
        vdetalle = true;
        vbotones = false;
        vformulario = true;
        return zonas();
    }

    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody()).add("unidadesOrganicasZone", unidadesOrganicasZone.getBody()).add("listaUOZone", listaUOZone.getBody()).add("mensajeZone", mensajeZone.getBody());;//.add("nivelZone", nivelZone.getBody())

        return mu;
    }

    private MultiZoneUpdate zonasUOAntecesora() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody());
        //.add("nivelZone", nivelZone.getBody());

        return mu;
    }

    private MultiZoneUpdate zonasfiltros() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("nivelUOZone", nivelUOZone.getBody()).add("filtrosZone", filtrosZone.getBody()).add("listaUOZone", listaUOZone.getBody());;
//.add("nivelOrga", nivelOrga.getBody())
        return mu;
    }

//   void onSelectedFromEli(UnidadOrganica d) {        
//        errorBorrar = null;
//        d.setEstado(UnidadOrganica.ESTADO_BAJA);
//        session.saveOrUpdate(d);
//        //new Logger().loguearOperacion(session, loggedUser, String.valueOf(d.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
//        //onSelectedFromReset();
//        envelope.setContents("Unidad Orgánica Eliminada");
//    }
//   
//    @Log
//    @CommitAfter
//    Object onSuccessFromFormularioxx() { 
//        
//        return zonas();
//    }
    @Log
    Object onActionFromEditar(UnidadOrganica uo) {
//        Criteria c = session.createCriteria(UnidadOrganica.class);
//        c.add(Restrictions.eq("id", uo.getId()));
//        unidadOrganica = (UnidadOrganica) c.list().get(0);
        unidadOrganica = (UnidadOrganica) session.load(UnidadOrganica.class, uo.getId());
        editando = true;
        cargoDatos();
        vformulario = true;
        vdetalle = false;
        vbotones = true;
        if (unidadOrganica.getTipozona() != null) {
            if (unidadOrganica.getTipozona().getCodigo() == 14) {
                vzona = false;
            } else {
                vzona = true;
            }
        } else {
            vzona = true;
        }
        //envelope.setContents(String.valueOf(uo)+String.valueOf(cargo.getUnidadorganica()));
        //uo=cargo.getUnidadorganica();
        //System.out.println("uo en actionfromeditar "+uo+" getpuedeeditar "+getPuedeEditar() );
        return zonas();
    }

    Object onValueChangedFromUnidadorganica_nivel(Integer dato) {
        nivelUO = dato;
        num = 4;
        System.out.println("nivellll" + nivelUO + "-" + num);

        return unidadesOrganicasZone.getBody();
    }

    Object onValueChangedFromBunidadorganica_nivel(Integer dato) {
        if (dato != null) {
            bnivelUO = dato;
            if (bnivelUO == 1) {
                buoAntece = null;
            }
        }
        return nivelUOZone.getBody();
    }

    Object onValueChangedFromBunidadOrganica_uoa(LkBusquedaUnidad dato) {
        buoAntece = dato;
        return nivelUOZone.getBody();
    }

    Object onValueChangedFromUnidadOrganica_tipozona(DatoAuxiliar dato) {
        if (dato.getCodigo() == 14) {
            vzona = false;
        } else {
            vzona = true;
            unidadOrganica.setDesczona("");
        }
        //unidadOrganica.setTipozona(dato);

        return unidadesOrganicasZone.getBody();
    }

    Object onValueChangedFromUnidadOrganica_tipovia(DatoAuxiliar dato) {
        unidadOrganica.setTipovia(dato);
        return unidadesOrganicasZone.getBody();
    }

    Object onValueChangedFromUnidadOrganica_categoriauo(DatoAuxiliar dato) {
        unidadOrganica.setCategoriauo(dato);
        return unidadesOrganicasZone.getBody();
    }

    void onCodigoChanged() {
        unidadOrganica.setCod_und_organica(_request.getParameter("param"));
    }

    void onDenoChanged() {
        unidadOrganica.setDen_und_organica(_request.getParameter("param"));
    }

    void onSiglaChanged() {
        unidadOrganica.setSigla(_request.getParameter("param"));
    }

    void onDireChanged() {
        unidadOrganica.setLocalidad(_request.getParameter("param"));
    }

    void onDescriChanged() {
        unidadOrganica.setDesczona(_request.getParameter("param"));
    }
}
