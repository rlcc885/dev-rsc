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
 * Clase que maneja el ABM de cargos
 *
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
    @Persist
    private boolean editando;
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
    
    // loguear operación de entrada a pagina
    @CommitAfter
    Object logueo(){
        new Logger().loguearOperacion(session, loggedUser, "", Logger.CODIGO_OPERACION_SELECT, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        return null;
    }
    
    // inicio de la pagina
    @Log
    void setupRender() {
        onSelectedFromClear();
        mostrar = true;
        vbotones = false;
        vformulario = false;
        vdetalle = false;
        //RESTRICCION
        vdetalle2 = false;
        logueo();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", loggedUser.getLogin());
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

    // seleccion del boton
    void onSelectedFromClear() {
        num2 = 2;
        nivel = null;
        valsituacioncap = null;
        bdcargo = null;
        bregimengruponivel = new RegimenGrupoNivel();
        uo = null;
    }

    void onSelectedFromMuestra() {
        num2 = 3;
        mostrar = true;
        nivel = null;
        valsituacioncap = null;
        bdcargo = null;
        bregimengruponivel = new RegimenGrupoNivel();
        uo = null;
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
        } else {
            mostrar = true;
            num3 = 1;
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

    
    // cargar los combos
    @Log
    public GenericSelectModel<LkBusquedaUnidad> getBeanUOrganicas() {
        Criteria c;
        c = session.createCriteria(LkBusquedaUnidad.class);
        c.add(Restrictions.eq("entidadId", entidadUE.getId()));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        if (nivel != null) {
            c.add(Restrictions.eq("nivel", nivel));
        }
        _beanUOrganicas = new GenericSelectModel<LkBusquedaUnidad>(c.list(), LkBusquedaUnidad.class, "denominacion", "id", _access);
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
    public GenericSelectModel<DatoAuxiliar> getbeanDatoSituacionCAP() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SITUACIONCAP", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getCodFunCargo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
    }

    @Log
    public List<LkBusquedaCargo> getCargos() {
        String consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
                + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
                + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
                + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
                + "WHERE S1.ESTADO=1 AND S2.ESTADO=1 AND S1.UNIDADORGANICA_ID IS NOT NULL AND S2.ENTIDAD_ID='" + entidadUE.getId() + "'";
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
        
             nroregistros = Integer.toString(query.list().size());
   
        return query.list();
    }

    @Persist
    @Property
    private String nroregistros;

    
    @Log
    public List<String> getRegimen() {
        return Helpers.getValorTablaAuxiliar("RegimenLaboralContractual", session);
    }


    
    //accion de boton de formulario
    @Log
    Object onActionFromEditar(Cargoxunidad c) {
        cargo = (Cargoxunidad) session.load(Cargoxunidad.class, c.getId());
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
        vNoedita = false;
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

    // estado de la pagina
    @Log
    void onActivate() {

        if (cargo == null) {
            cargo = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;            
        }

        if (regimengruponivel == null) {
            regimengruponivel = new RegimenGrupoNivel();
        }
    }

    @Log
    void onActivate(Cargoxunidad c) {
        if (c == null) {
            c = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;
            cargo = c;
        } else {
            cargo = c;

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

    // eliminar cargo
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
            envelope.setContents("Cargo Eliminado");
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()),Logger.CODIGO_OPERACION_DELETE,Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        }
        onReset();
        return zonasDatos();// La/a zona a actualizar
    }
    
    // validaciones
    @Log
    public boolean getEsBorrable() {
        
        Criteria c;
        c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("cargoxunidad", lkcargo));
        c.add(Restrictions.like("estado", Cargoxunidad.ESTADO_ALTA));
        
        if (c.list().size() > 0) {
            return false;
        }

        return true;
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

    @Log
    Object onSuccessFromformOcupacional() {
        return OcupacionalesZone.getBody();
    }

    @Log
    void resetCargo() {
        cargo = new Cargoxunidad();
        vNoedita = true;
        vdetalle2 = false;
        regimengruponivel = new RegimenGrupoNivel();
        cantidadPuestos = null;
    }

    // limpiar formulario
    @Log
    Object onReset(){
        resetCargo();
        cantidadPuestos = null;
        return new MultiZoneUpdate("abmZone",abmZone.getBody()).add("OcupacionalesZone",OcupacionalesZone.getBody());        
    }

    // cancelar accion
    @Log
    Object onCancel(){
        if(vdetalle){
            vformulario=false; 
            if (usua.getAccesoreport() == 1) {
                vformulario=true;
                vdetalle=false;
                vbotones=true;
                vNoedita=true;
                cargo = new Cargoxunidad();
                editando = false;
                regimengruponivel = new RegimenGrupoNivel();
                cantidadPuestos = null;
            }
        }
        else{
            resetCargo();
        }
        return new MultiZoneUpdate("abmZone",abmZone.getBody()).add("OcupacionalesZone",OcupacionalesZone.getBody());
    }

    // formulario principal
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
                    + "WHERE S2.ENTIDAD_ID='" + entidadUE.getId() + "' AND UPPER(S1.COD_CARGO)=UPPER('" + cargo.getCod_cargo() + "')";
            if (editando) {
                consulta += ("AND S1.ID!='" + cargo.getId() + "'");
            }
            Query query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
            if (query.list().size() > 0) {
                formmensaje.recordError(Errores.ERROR_COD_CARGO_UNICO);
                return zonasDatos();
            } else {
                consulta = "SELECT S1.ID,S1.DEN_CARGO DENOMINACION,S3.VALOR SITUCAP,S4.VALOR REGLABO,S2.DEN_UND_ORGANICA UNIDADORGA FROM RSC_CARGOXUNIDAD S1 "
                        + "JOIN RSC_UNIDADORGANICA S2 ON S2.ID=S1.UNIDADORGANICA_ID "
                        + "LEFT JOIN RSC_DATOAUXILIAR S3 ON S3.ID=S1.SITUACIONCAP_ID "
                        + "LEFT JOIN RSC_DATOAUXILIAR S4 ON S4.ID=S1.REGIMENLABORAL_ID "
                        + "WHERE S2.ENTIDAD_ID='" + entidadUE.getId() + "' AND S1.UNIDADORGANICA_ID='"+cargo.getUnidadorganica().getId()+"' AND UPPER(S1.DEN_CARGO)=UPPER('" + cargo.getDen_cargo() + "')";
                if (editando) {
                    consulta += ("AND S1.ID!='" + cargo.getId() + "'");
                }
                query = session.createSQLQuery(consulta).addEntity(LkBusquedaCargo.class);
                if (query.list().size() > 0) {
                    formmensaje.recordError(Errores.ERROR_DEN_CARGO_UNICO);
                    return zonasDatos();
                }
            }

	if (editando)
	{
            consulta = "SELECT COUNT(*) FROM RSC_CARGOASIGNADO S0 JOIN RSC_CARGOXUNIDAD S1 ON (S0.CARGOXUNIDAD_ID = S1.ID)"
             + "JOIN RSC_UNIDADORGANICA S2 ON (S2.ID = S1.UNIDADORGANICA_ID)"
             + "WHERE UPPER(S1.DEN_CARGO)=UPPER('" + cargo.getDen_cargo() + "')"
             + "AND S2.ENTIDAD_ID='" + entidadUE.getId() + "'  AND S1.ID ='" + cargo.getId() + "'";       
                         
            query = session.createSQLQuery(consulta);
            String x = query.list().get(0).toString();
           int disponibles = Integer.parseInt(x);
            if (Integer.parseInt(cantidadPuestos) < disponibles)    
            {
               formmensaje.recordError("LA CANTIDAD DE PUESTOS ES INFERIOR A LOS YA ASIGNADOS");
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
            session.saveOrUpdate(cargo);
            session.flush();
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(cargo.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
            cargo = new Cargoxunidad();
            regimengruponivel = new RegimenGrupoNivel();
            mostrar = true;
            editando = false;
            formmensaje.clearErrors();
            envelope.setContents(helpers.Constantes.CARGO_EXITO);
        }
        //RESTRICCION
        vdetalle2 = false;
        return zonasDatos();

    }

    // actualizacion de zonas
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
        return mu;

    }


    void onDenoChanged() {
        bdcargo = _request.getParameter("param");
    }

    Object onValueChangedFromUnidadorganica_nivel(Integer dato) {
        nivel = dato;
        uo = null;
        return nivelUOZone.getBody();
    }

}
