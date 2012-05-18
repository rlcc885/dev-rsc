    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import helpers.Errores;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.RegimenGrupoNivel;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
import helpers.Logger;
import java.util.LinkedList;

import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.SessionState;

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
    private Cargo cargo;
    @Property
    @Persist
    private Integer nivel;
    @Property
    private Cargo c;
    @Property
    @Persist
    private UnidadOrganica uo;
    @Property
    @SessionState
    private EntidadUEjecutora entidadUE;
    @InjectComponent
    @Property
    private Zone nivelCargoZone;
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
    private GenericSelectModel<UnidadOrganica> _beanUOrganicas;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    private Zone selectUOZone;
    @InjectComponent
    private Zone abmZone;
    @Property
    @Persist
    private String errorBorrar;
    /*@Component(id = "formularioselectorgano")
    private Form _selectOrganoForm;*/
    @Persist
    private boolean editando;
    /*@Component(id = "formularioselectuo")
    private Form selectUoForm;*/
    @Component(id = "formularioaltacargo")
    private Form _altaForm;
    @Component(id = "formNivelUOCargo")
    private Form formNivelUOCargo;
    @Inject
    private Request _request;
    @Inject
    private ComponentResources _resources;
    @InjectComponent
    private Envelope envelope;

    /*@Property
    @Persist
    private Ocupacional ocupacional;*/
    @Log
    public boolean getHayNivel() {
        return !(nivel == null);
    }

    @Log
    public List<Integer> getBeanNivel() {
        List<Integer> nivel = new LinkedList<Integer>();
        Integer nivelMax = 0;

        nivelMax = Helpers.maxNivelUO(entidadUE, session);

        for (int i = 1; i <= nivelMax; i++) {
            // Es mas uno porque agregamos hasta un nivel mas
            nivel.add(i);
        }

        return nivel;
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas() {
        return _beanUOrganicas;
    }

    @Log
    public List<Cargo> getCargos() {
        Criteria c = session.createCriteria(Cargo.class);
        c.add(Restrictions.eq("und_organica", uo));
        c.add(Restrictions.ne("estado", Constantes.ESTADO_BAJA));
        return c.list();
    }

    /*@InjectComponent
    private Zone selectZone;*/
    @Log
    public List<String> getEstado() {
        List<String> estadosCargo = new LinkedList<String>();
        estadosCargo.add(Cargo.ESTADO_ALTA);
        estadosCargo.add(Cargo.ESTADO_BAJA);
        return estadosCargo;
    }

    @Log
    public List<String> getRegimen() {
        return Helpers.getValorTablaAuxiliar("RegimenLaboralContractual", session);
    }

    /*
    public GenericSelectModel<DatoAuxiliar> getCodEstCargo() {
    List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CodigoEstructuralCargo", null, 0, session);

    return new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);

    }
     */
    @Log
    public GenericSelectModel<DatoAuxiliar> getCodFunCargo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);

    }

    @Log
    public boolean getPuedeEditar() {
        //System.out.println("----------regimengruponivel nivel  "+regimengruponivel.getNivelRemunerativo());
        return Permisos.puedeEscribir(loggedUser, entidadUE);
    }

    @Log
    public boolean getNoEditable() {
        return !getPuedeEditar();
    }

    @Log
    Object onActionFromEditar(Cargo c) {
        cargo = (Cargo) session.load(Cargo.class, c.getId());
        //   familiarActual = f;
        cargoDatos();
        errorBorrar = null;
        editando = true;
        //System.out.println("uo en actionfromeditar "+uo+" getpuedeeditar "+getPuedeEditar() );
        return zonasDatos();
    }

    @Log
    void cargoDatos() {
        regimengruponivel = new RegimenGrupoNivel();

        regimengruponivel.setNivelRemunerativo(cargo.getNivelRemunerativo());
        regimengruponivel.setGrupo(cargo.getGrupoOcupacional());
        regimengruponivel.setRegimen(cargo.getReg_lab_con());

    }

    @Log
    void onPrepareFromFormularioaltacargo() {

    }

    @Log
    void onActivate() {

        if (cargo == null) {
            cargo = new Cargo();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;
        }

        if (uo == null) {
            editando = false;
        }

        if (regimengruponivel == null) {
            regimengruponivel = new RegimenGrupoNivel();
        }
        //System.out.println("---------------on activate regimengruponivel "+regimengruponivel.getGrupo());
    }

    @Log
    void onActivate(Cargo c) {
        //System.out.println("------------------------------on activate con cargo");
        if (c == null) {
            c = new Cargo();
            regimengruponivel = new RegimenGrupoNivel();
            editando = false;
            cargo = c;
        } else {
            cargo = c;
            uo = cargo.getUnd_organica();
            nivel = uo.getNivel();


            if (regimengruponivel == null) {
                regimengruponivel = new RegimenGrupoNivel();
            }
            regimengruponivel.setRegimen(c.getReg_lab_con());
            regimengruponivel.setGrupo(c.getGrupoOcupacional());
            regimengruponivel.setNivelRemunerativo(c.getNivelRemunerativo());

            errorBorrar = null;
            cargoDatos();
            editando = true;
        }


    }

    /*
    Cargo onPassivate()
    {
    return cargo;
    }
     */
    @Log
    @CommitAfter
    Object onBorrarDato(Cargo dato) {
        errorBorrar = null;
        Criteria c;
        c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("cargo", dato));
        c.add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO));

        if (c.list().size() > 0) {
            errorBorrar = Errores.ERROR_BORRAR_CARGO;
        } else {
            dato.setEstado(Cargo.ESTADO_BAJA);
            session.saveOrUpdate(dato);
            session.flush();
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        }
        onActionFromReset();
        return zonasDatos();// La/a zona a actualizar
    }

    /*
     * reset del formulario
     */
    @Log
    Object onActionFromReset() {
        cargo = new Cargo();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
        return zonasDatos();
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
    void onValidateFromFormularioaltacargo() {


        // Seguimos sólo si hay puestos disponibles.

        if (!editando) {
            // Es porque estoy en uno nuevo
            Criteria c = session.createCriteria(Cargo.class);
            c.add(Restrictions.like("cod_cargo", cargo.getCod_cargo()));
            c.createAlias("und_organica", "und_organica");
            c.add(Restrictions.eq("und_organica.entidadUE", entidadUE ));
            //c.add(Restrictions.like("und_organica", uo));
            if (c.list().size() > 0) {
                _altaForm.recordError(Errores.ERROR_COD_CARGO_UNICO);

            }
        } else {
            // editando
            /*if(Helpers.getCantPuestosOcupadosCargo(session, c) > c.getCtd_puestos_total() ){
            _altaForm.recordError(Errores.ERROR_CTD_PUESTOS_MAY_TOTALES);
            }*/
        }
        envelope.setContents(helpers.Constantes.CARGO_EXITO);

    }

    @Log
    Object onFailureFromFormularioaltacargo() {
        return this;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformNivelUOCargo() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        c.add(Restrictions.eq("nivel", nivel));
        c.add(Restrictions.eq("entidadUE", entidadUE));
        list = c.list();
        _beanUOrganicas = new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
        uo = null;
        return nivelCargoZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromformUOCargo() {
        cargo = new Cargo();
        regimengruponivel = new RegimenGrupoNivel();
        //System.out.println("uo on success from uo cargo "+uo+" getpuedeeditar "+getPuedeEditar() );

        return zonasDatos();
    }

    @Log
    @CommitAfter
    Object onSuccessFromformOcupacional() {
        return OcupacionalesZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioaltacargo() {
        errorBorrar = null;
        if (!editando) {
            cargo.setUnd_organica(uo);
            cargo.setCtd_puestos_total(Cargo.CANT_DEFAULT);
        }


        cargo.setNivelRemunerativo(regimengruponivel.getNivelRemunerativo());
        cargo.setGrupoOcupacional(regimengruponivel.getGrupo());
        cargo.setReg_lab_con(regimengruponivel.getRegimen());

        cargo.setEstado(Cargo.ESTADO_ALTA);
//        session.saveOrUpdate(cargo);
        session.merge(cargo);
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(cargo.getId()), (editando ? Logger.CODIGO_OPERACION_ALTA : Logger.CODIGO_OPERACION_MODIFICACION), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        cargo = new Cargo();
        regimengruponivel = new RegimenGrupoNivel();

        editando = false;
        //return abmZone.getBody();
        return zonasDatos();

    }

    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("selectUOZone", selectUOZone.getBody()).add("abmZone", abmZone.getBody()) //.add("selectZone",selectZone.getBody())
                .add("OcupacionalesZone", OcupacionalesZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody()).add("OcupacionalesZone", OcupacionalesZone.getBody());
        return mu;

    }

    @Log
    public List<String> getbeanDatoSituacionCAP() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        return Helpers.getValorTablaAuxiliar("SituacionCAP", session);
    }
    /*@Log
    @SetupRender
    private void setupCombos() {
    //System.out.println("=======================Estoy aca,");

    }*/

    /*
    @Log
    @SetupRender
    private void setupOcupacionales() {
    if (cargo != null) {
    cargoDatos();
    }
    }
     */
}
