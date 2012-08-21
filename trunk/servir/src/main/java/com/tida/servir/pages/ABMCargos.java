    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class ABMCargos extends GeneralPage {

    @Inject
    private Session session;
    // Levantamos de la sesión tapestry el organismo informante
    @Property
    @Persist
    private Cargoxunidad cargo;
    @Property
    @Persist
    private Integer nivel;
    @Property
    private Cargoxunidad c;
    @Property
    @Persist
    private LkBusquedaUnidad uo;
    @Property
    @SessionState
    private Entidad entidadUE;
//    @InjectComponent
//    @Property
//    private Zone nivelCargoZone;
    @Property
    @SessionState
    private Usuario loggedUser;
    @InjectComponent
    @Property
    private Zone OcupacionalesZone;
    @Property
    @Persist
    private RegimenGrupoNivel regimengruponivel;
    @Persist
    private GenericSelectModel<LkBusquedaUnidad> _beanUOrganicas;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    private Zone abmZone;
    @Property
    @Persist
    private String errorBorrar;
    @Property
    @Persist
    private String cantidadPuestos;
    /*
     * @Component(id = "formularioselectorgano") private Form _selectOrganoForm;
     */
    @Persist
    private boolean editando;
    /*
     * @Component(id = "formularioselectuo") private Form selectUoForm;
     */
//    @Component(id = "formularioaltacargo")
//    private Form _altaForm;
    @Component(id = "formmensaje")
    private Form formmensaje;
    @Inject
    private Request _request;
    @Inject
    private ComponentResources _resources;
    @InjectComponent
    private Envelope envelope;
    private int num = 0, num2 = 0, num3 = 0;
    private Object reto;
    @Property
    @Persist
    private boolean mostrar;
    @InjectComponent
    @Property
    private Zone filtrosZone;
    @InjectComponent
    @Property
    private Zone BOcupacionalesZone;
    @Persist
    @Property
    private String bdcargo;
    @Persist
    @Property
    private DatoAuxiliar valsituacioncap;
    @Property
    @Persist
    private RegimenGrupoNivel bregimengruponivel;
    @Persist
    private GenericSelectModel<UnidadOrganica> _beanUOrganicas2;
    @InjectComponent
    @Property
    private Zone listaCargo;
    @InjectComponent
    @Property
    private Zone nivelUOZone;
    @InjectComponent
    @Property
    private Zone mensajeZone;
    @Property
    @Persist
    private UsuarioAcceso usua;
    //validaciones
    //validaciones
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vdetalle2; // restriccion del campo denominacion y codigo
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
    private Boolean vNoedita; //Nos sirve para ocultar los botones de Cancelar y Limpiar Formulario
    /*
     * @Property @Persist private Ocupacional ocupacional;
     */
    @Property
    @Persist
    private LkBusquedaCargo lkcargo;

    @Log
    @SetupRender
    private void inicio() {
        onSelectedFromClear();
        onSelectedFromReset();
        mostrar = true;
        vbotones = false;
        vformulario = false;
        vdetalle = false;
        //RESTRICCION
        vdetalle2 = false;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento", loggedUser.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
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
        // Utilizado para ocultar o mostrar los botones de cancelar y limpiar formulario.
        vNoedita = true;
    }

    @Log
    @CommitAfter
    Object retorno() {
        return Alerta.class;
    }

    void onSelectedFromClear() {
        num2 = 2;
        nivel = null;
        valsituacioncap = null;
        bdcargo = null;
        bregimengruponivel = new RegimenGrupoNivel();
    }

    void onSelectedFromMuestra() {
        num2 = 3;
        mostrar = true;
        nivel = null;
        valsituacioncap = null;
        bdcargo = null;
        bregimengruponivel = new RegimenGrupoNivel();
        uo = null;
//        cargo = new Cargoxunidad();
//        editando = false;
//        regimengruponivel = new RegimenGrupoNivel();
        num3 = 2;
    }

    void onSelectedFromSave() {
        num = 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormulariofiltrocargo() {
        if (num2 == 2) {
            reto = zonasFiltros();
        } else if (num2 == 3) {
            return new MultiZoneUpdate("filtrosZone", filtrosZone.getBody()).add("listaCargo", listaCargo.getBody()).add("BOcupacionalesZone", BOcupacionalesZone.getBody()).add("nivelUOZone", nivelUOZone.getBody());
            //.add("nivelcargo", nivelcargo.getBody());
        } else {
            mostrar = true;
            num3 = 1;
            //envelope.setContents(String.valueOf(nivel)+bdcargo+String.valueOf(uo)+String.valueOf(valsituacioncap));
            reto = zonasDatos();

        }
        // RESTRICCION
        vdetalle2 = false;
        return reto;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformBOcupacional() {
        return BOcupacionalesZone.getBody();
    }

    @Log
    public boolean getHayNivel() {
        return !(nivel == null);
    }

    @Log
    public List<Integer> getBeanNivel() {
        List<Integer> niv = new LinkedList<Integer>();
        Integer nivelMax = 0;

        nivelMax = Helpers.maxNivelUO(entidadUE, session);

        for (int i = 1; i <= nivelMax; i++) {
            // Es mas uno porque agregamos hasta un nivel mas
            niv.add(i);
        }

        return niv;
    }

    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicas() {
        String consulta = "SELECT S1.id, S1.den_und_organica denominacion, S1.sigla, S1.nivel,"
                + " S1.unidadorganica_id, S1.CATEGORIAUO_ID, T1.DESCCATEGORIAUO,"
                + " S1.ENTIDAD_ID, S1.ESTADO FROM    rsc_unidadorganica S1 LEFT JOIN lkcategoriauo T1"
                + " ON (T1.categoriauo_id = s1.categoriauo_id) WHERE S1.ESTADO!=0 AND S1.ENTIDAD_ID='" + entidadUE.getId() + "'";
        if (nivel != null) {
            consulta += " AND S1.NIVEL='" + nivel + "'";
        }
        consulta += "ORDER BY(DENOMINACION)";
        List<LkBusquedaUnidad> list;
        Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaUnidad.class);
        list = query.list();
        _beanUOrganicas = new GenericSelectModel<LkBusquedaUnidad>(list, LkBusquedaUnidad.class, "denominacion", "id", _access);
        return _beanUOrganicas;
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas2() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidad", entidadUE));
        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        list = c.list();
        _beanUOrganicas2 = new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
        return _beanUOrganicas2;
    }

    @Log
    public List<LkBusquedaCargo> getCargos() {
        String consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
                + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
                + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
                + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
                + "WHERE S1.ESTADO=1 AND S1.UNIDADORGANICA_ID IS NOT NULL AND S2.ENTIDAD_ID='" + entidadUE.getId() + "'";
//        Criteria c = session.createCriteria(Cargoxunidad.class);
//        c.createAlias("unidadorganica", "unidadorganica"); 
//        c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
//        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        if (num3 == 2) {
        } else {
            if (nivel != null) {
                consulta += " AND S2.NIVEL='" + nivel + "'";
            }
            if (uo != null && !uo.equals("")) {
                consulta += " AND S1.UNIDADORGANICA_ID='" + uo.getId() + "'";
            }
            if (bdcargo != null && !bdcargo.equals("")) {
                consulta += " AND UPPER(S1.DEN_CARGO) LIKE '%' || UPPER('" + bdcargo + "')||'%'";
            }
            if (valsituacioncap != null && !valsituacioncap.equals("")) {
                consulta += " AND S1.SITUACIONCAP_ID='" + valsituacioncap.getId() + "'";
            }
            if (bregimengruponivel.getRegimen() != null && !bregimengruponivel.getRegimen().equals("")) {
                consulta += " AND S1.REGIMENLABORAL_ID='" + bregimengruponivel.getRegimen().getId() + "'";
            }
            if (bregimengruponivel.getGrupo() != null && !bregimengruponivel.getGrupo().equals("")) {
                consulta += " AND S1.GRUPOOCUPACIONAL_ID='" + bregimengruponivel.getGrupo().getId() + "'";
            }
            if (bregimengruponivel.getNivelRemunerativo() != null && !bregimengruponivel.getNivelRemunerativo().equals("")) {
                consulta += " AND S1.NIVELREMUNERATIVO_ID='" + bregimengruponivel.getNivelRemunerativo().getId() + "'";
            }
        }
        consulta += " ORDER BY(DENOMINACION)";
        Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
        return query.list();
    }

    /*
     * @InjectComponent private Zone selectZone;
     */
//    @Log
//    public List<String> getEstado() {
//        List<String> estadosCargo = new LinkedList<String>();
//        estadosCargo.add(Cargoxunidad.ESTADO_ALTA);
//        estadosCargo.add(Cargoxunidad.ESTADO_BAJA);
//        return estadosCargo;
//    }
    @Log
    public List<String> getRegimen() {
        return Helpers.getValorTablaAuxiliar("RegimenLaboralContractual", session);
    }

    /*
     * public GenericSelectModel<DatoAuxiliar> getCodEstCargo() {
     * List<DatoAuxiliar> list =
     * Helpers.getDatoAuxiliar("CodigoEstructuralCargo", null, 0, session);
     *
     * return new
     * GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);
     *
     * }
     */
    @Log
    public GenericSelectModel<DatoAuxiliar> getCodFunCargo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
    }

    @Log
    Object onActionFromEditar(Cargoxunidad c) {
        cargo = (Cargoxunidad) session.load(Cargoxunidad.class, c.getId());
        //   familiarActual = f;
        //uo=cargo.getUnidadorganica();
        cargoDatos();
        errorBorrar = null;
        editando = true;
        vformulario = true;
        editando = true;
        vdetalle = false;
        // RESTRICCION CAMPOS
        vdetalle2 = true;
        vbotones = true;
        vNoedita = false;
        //envelope.setContents(String.valueOf(uo)+String.valueOf(cargo.getUnidadorganica()));
        //uo=cargo.getUnidadorganica();
        //System.out.println("uo en actionfromeditar "+uo+" getpuedeeditar "+getPuedeEditar() );
        return zonasDatos();
    }

    @Log
    Object onActionFromDetalle(Cargoxunidad c) {
        cargo = (Cargoxunidad) session.load(Cargoxunidad.class, c.getId());
        vdetalle = true;
        cargoDatos();
        errorBorrar = null;
        vbotones = false;
        vformulario = true;
        return zonasDatos();
    }


    @Log
    void cargoDatos() {
        regimengruponivel = new RegimenGrupoNivel();
        regimengruponivel.setNivelRemunerativo(cargo.getNivelRemunerativo());
        regimengruponivel.setGrupo(cargo.getGrupoOcupacional());
        regimengruponivel.setRegimen(cargo.getRegimenlaboral());
        cantidadPuestos = String.valueOf(cargo.getCtd_puestos_total());

    }
    
    @Log
    void onPrepareFromFormularioaltacargo() {
    }

    @Log
    void onActivate() {

        if (cargo == null) {
            cargo = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;
        }

//        if (uo == null) {
//            editando = false;
//        }

        if (regimengruponivel == null) {
            regimengruponivel = new RegimenGrupoNivel();
        }
        //System.out.println("---------------on activate regimengruponivel "+regimengruponivel.getGrupo());
    }

    @Log
    void onActivate(Cargoxunidad c) {
        //System.out.println("------------------------------on activate con cargo");
        if (c == null) {
            c = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;
            cargo = c;
        } else {
            cargo = c;
//            uo = cargo.getUnidadorganica();
//            nivel = uo.getNivel();           


            if (regimengruponivel == null) {
                regimengruponivel = new RegimenGrupoNivel();
            }

            regimengruponivel.setRegimen(c.getRegimenlaboral());
            regimengruponivel.setGrupo(c.getGrupoOcupacional());
            regimengruponivel.setNivelRemunerativo(c.getNivelRemunerativo());

            errorBorrar = null;
            cargoDatos();
            editando = true;
        }


    }

    /*
     * Cargo onPassivate() { return cargo; }
     */
    @Log
    @CommitAfter
    Object onBorrarDato(Cargoxunidad dato) {
        errorBorrar = null;
        Criteria c;
        c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("cargoxunidad", dato));
        c.add(Restrictions.like("estado", Cargoxunidad.ESTADO_ALTA));

        if (c.list().size() > 0) {
            errorBorrar = Errores.ERROR_BORRAR_CARGO;
        } else {
            dato.setEstado(Cargoxunidad.ESTADO_BAJA);
            session.saveOrUpdate(dato);
            session.flush();
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
            envelope.setContents("Cargo Eliminado");
        }
        onSelectedFromReset();
        return zonasDatos();// La/a zona a actualizar
    }

    /*
     * reset del formulario
     */
//    @Log
//    Object onActionFromReset() {
//        cargo = new Cargoxunidad();
//        editando = false;
//        regimengruponivel = new RegimenGrupoNivel();
//        return zonasDatos();
//    }
    void onSelectedFromReset() {
        num = 2;
        cargo = new Cargoxunidad();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
    }

    void onSelectedFromCancel() {
        num = 3;
        if (!vbotones) {
            vformulario = false;
        } else {
            cargo = new Cargoxunidad();
            editando = false;
            regimengruponivel = new RegimenGrupoNivel();
        }

    }

    @Log
    public Integer getCantPuestosOcupados() {

        return Helpers.getCantPuestosOcupadosCargo(session, cargo);
    }

    @Log
    public boolean getEditando() {
        return editando;
    }

    @Log
    Object onFailureFromFormularioaltacargo() {
        return this;
    }

//    @Log
//    @CommitAfter
//    Object onSuccessFromformNivelUOCargo() {        
//        uo = null;
//        return nivelUOZone.getBody();
//    }
//    @Log
//    @CommitAfter
//    Object onSuccessFromformUOCargo() {
//        cargo = new Cargoxunidad();
//        regimengruponivel = new RegimenGrupoNivel();
//        //System.out.println("uo on success from uo cargo "+uo+" getpuedeeditar "+getPuedeEditar() );
//
//        return zonasDatos();
//    }
    @Log
    Object onSuccessFromformOcupacional() {
        return OcupacionalesZone.getBody();
    }
    @Log
    void resetCargo() {
        cargo = new Cargoxunidad();
        vNoedita = true;
        vdetalle2 = false;
    }
    @Log
    Object onReset(){
        resetCargo();
        return abmZone.getBody();
    }
    @Log
    Object onCancel(){
        resetCargo();
        return abmZone.getBody();
    }
    @Log
    @CommitAfter
    Object onSuccessFromFormularioaltacargo() {
        if (num == 2) {
        } else if (num == 3) {
        } else if (num == 1) {
            formmensaje.clearErrors();
            //validaciones      
            String consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
                    + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
                    + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
                    + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
                    + "WHERE S2.ENTIDAD_ID='" + entidadUE.getId() + "' AND S1.COD_CARGO='" + cargo.getCod_cargo() + "'";
            if (editando) {
                consulta += ("AND S1.ID!='" + cargo.getId() + "'");
            }
            Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
//            if (editando) {
//                    c.add(Restrictions.ne("id", cargo.getId()));
//            }
//            c.add(Restrictions.like("cod_cargo", cargo.getCod_cargo()));
//            c.createAlias("unidadorganica", "unidadorganica");
//            c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
//            //c.add(Restrictions.like("und_organica", uo));
            if (query.list().size() > 0) {
                formmensaje.recordError(Errores.ERROR_COD_CARGO_UNICO);
                return zonasDatos();
            } else {
//                Criteria c;
//                c = session.createCriteria(Cargoxunidad.class);
//                c = session.createCriteria(Cargoxunidad.class);
//                if (editando) {
//                    c.add(Restrictions.ne("id", cargo.getId()));
//                }
//
//                c.add(Restrictions.like("den_cargo", cargo.getDen_cargo()));
//                c.createAlias("unidadorganica", "unidadorganica");
//                c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
                //c.add(Restrictions.like("und_organica", uo));
                consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
                        + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
                        + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
                        + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
                        + "WHERE S2.ENTIDAD_ID='" + entidadUE.getId() + "' AND UPPER(S1.DEN_CARGO)=UPPER('" + cargo.getDen_cargo() + "')";
                if (editando) {
                    consulta += ("AND S1.ID!='" + cargo.getId() + "'");
                }
                query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
                if (query.list().size() > 0) {
                    formmensaje.recordError(Errores.ERROR_DEN_CARGO_UNICO);
                    return zonasDatos();
                }
            }

            if (cargo.getEsorganico() == null) {
                formmensaje.recordError("Tiene que seleccionar una opción para ¿Es Orgánico?.");
                return zonasDatos();
            }
            if (cargo.getPresupuestado_PAP() == null) {
                formmensaje.recordError("Tiene que seleccionar una opción para ¿Presupuestado en PAP?.");
                return zonasDatos();
            }
            if (cargo.getDec_jurada_byr() == null) {
                formmensaje.recordError("Tiene que seleccionar una opción para ¿Presenta Declaración Jurada de Bienes y Rentas?.");
                return zonasDatos();
            }
            if (regimengruponivel.getRegimen() == null) {
                formmensaje.recordError("Tiene que seleccionar un Régimen Laboral/Contractual.");
                return zonasDatos();
            }
            if (regimengruponivel.getRegimen().getCodigo() != 3) {
                if (regimengruponivel.getGrupo() == null) {
                    formmensaje.recordError("Tiene que seleccionar un Grupo Ocupacional.");
                    return zonasDatos();
                }
                if (regimengruponivel.getNivelRemunerativo() == null) {
                    formmensaje.recordError("Tiene que seleccionar un Nivel Remunerativo.");
                    return zonasDatos();
                }
            }

            errorBorrar = null;
            if (!editando) {
                //cargo.setUnidadorganica(uo);
                //cargo.setCtd_puestos_total(Cargoxunidad.CANT_DEFAULT);            
            } else {
                if (usua.getAccesoreport() == 0) {
                    vformulario = false;
                }
            }

            cargo.setNivelRemunerativo(regimengruponivel.getNivelRemunerativo());
            cargo.setGrupoOcupacional(regimengruponivel.getGrupo());
            cargo.setRegimenlaboral(regimengruponivel.getRegimen());
            cargo.setCtd_puestos_total(Integer.parseInt(cantidadPuestos));
            cargo.setEstado(Cargoxunidad.ESTADO_ALTA);
//        session.saveOrUpdate(cargo);
            session.merge(cargo);
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(cargo.getId()), (editando ? Logger.CODIGO_OPERACION_ALTA : Logger.CODIGO_OPERACION_MODIFICACION), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
            cargo = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            mostrar = true;
//            if(editando){
//                formmensaje.clearErrors();
//                envelope.setContents(helpers.Constantes.CARGO_EDIT_EXITO);
//            }else{
//                formmensaje.clearErrors();
//                envelope.setContents(helpers.Constantes.CARGO_EXITO);
//            }
                editando = false;
                formmensaje.clearErrors();
                envelope.setContents(helpers.Constantes.CARGO_EXITO);
            //return abmZone.getBody();
        }
        //RESTRICCION
        vdetalle2 = false;
        return zonasDatos();

    }

    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody()) //.add("selectZone",selectZone.getBody())
                .add("OcupacionalesZone", OcupacionalesZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("listaCargo", listaCargo.getBody()).add("OcupacionalesZone", OcupacionalesZone.getBody()).add("abmZone", abmZone.getBody()).add("mensajeZone", mensajeZone.getBody());
        return mu;

    }

    @Log
    private MultiZoneUpdate zonasFiltros() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("filtrosZone", filtrosZone.getBody()).add("BOcupacionalesZone", BOcupacionalesZone.getBody()).add("nivelUOZone", nivelUOZone.getBody());
        //.add("nivelcargo", nivelcargo.getBody());
        return mu;

    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getbeanDatoSituacionCAP() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);

        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SITUACIONCAP", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    /*
     * @Log @SetupRender private void setupCombos() {
     * //System.out.println("=======================Estoy aca,");
     *
     * }
     */

    /*
     * @Log @SetupRender private void setupOcupacionales() { if (cargo != null)
     * { cargoDatos(); } }
     */
    void onDenoChanged() {
        bdcargo = _request.getParameter("param");
    }

    Object onValueChangedFromUnidadorganica_nivel(Integer dato) {
        nivel = dato;
        uo = null;
        return nivelUOZone.getBody();
    }
//    @Log
//    @CommitAfter
//    Object onMostrartodos() {
//        num2=3;
//        mostrar=true; 
//        nivel=null; 
//        valsituacioncap=null;
//        bdcargo=null;        
//        bregimengruponivel = new RegimenGrupoNivel();
//        uo=null;
//        num3=2;
//        return listaCargo.getBody();        
//    }
}
