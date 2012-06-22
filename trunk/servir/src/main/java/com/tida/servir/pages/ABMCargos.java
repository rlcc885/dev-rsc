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

import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import helpers.Errores;
import com.tida.servir.entities.Entidad_BK;
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
    private Cargoxunidad cargo;
    @Property
    @Persist
    private Integer nivel;
    @Property
    private Cargoxunidad c;
    @Property
    @Persist
    private UnidadOrganica uo;
    @Property
    @SessionState
    private Entidad_BK entidadUE;
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

    private int num=0,num2=0;
    private Object reto;
    @Property
    @Persist
    private boolean mostrar;
    @Property
    @Persist
    private boolean limpio;
    @InjectComponent
    @Property
    private Zone filtrosZone;
    @InjectComponent
    @Property
    private Zone BOcupacionalesZone;
    @Persist
    @Property
    private String bdenocargo;
    @Persist
    @Property
    private DatoAuxiliar valsituacioncap;
    @Property
    @Persist
    private RegimenGrupoNivel bregimengruponivel;
    @Persist
    private GenericSelectModel<UnidadOrganica> _beanUOrganicas2;
    /*@Property
    @Persist
    private Ocupacional ocupacional;*/
    
    void onSelectedFromLimpia() {        
        num2=2;     
        cargo = new Cargoxunidad();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
        nivel=null;       
        valsituacioncap=null;
        bdenocargo=null;
        uo=null;
        limpio=false;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariofiltrocargo() {
        if(num2==2){
            reto=ABMCargos.class;
        }
        else{
            mostrar=true;
            limpio=true;
            cargo = new Cargoxunidad();
            editando = false;
            regimengruponivel = new RegimenGrupoNivel();
            reto=zonasDatos();            
            
        }        
        return reto;
    }
    
    
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
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas2() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidad", entidadUE));
        if(nivel!= null){
            c.add(Restrictions.eq("nivel", nivel));
        }
        list = c.list();
        _beanUOrganicas2 = new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
        return _beanUOrganicas2;
    }
    
    @Log
    public List<Cargoxunidad> getCargos() {
        Criteria c = session.createCriteria(Cargoxunidad.class);
        c.createAlias("unidadorganica", "unidadorganica");
 
        c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        if(nivel!= null){
           c.add(Restrictions.eq("unidadorganica.nivel", nivel));     
        }
        if (uo != null && !uo.equals("")) {
            c.add(Restrictions.eq("unidadorganica", uo));
        }
        if (bdenocargo != null && !bdenocargo.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("den_cargo", bdenocargo + "%").ignoreCase()).add(Restrictions.like("den_cargo", bdenocargo.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("den_cargo", bdenocargo.replaceAll("n", "ñ") + "%").ignoreCase()));
        }
        if (valsituacioncap != null && !valsituacioncap.equals("")) {
            c.add(Restrictions.like("situacioncap", valsituacioncap));
        }
        if (bregimengruponivel.getRegimen() != null && !bregimengruponivel.getRegimen().equals("")) {
            c.createAlias("regimenlaboral", "regimenlaboral");
            c.add(Restrictions.like("regimenlaboral", bregimengruponivel.getRegimen()));
        }
        if (bregimengruponivel.getGrupo() != null && !bregimengruponivel.getGrupo().equals("")) {
            c.createAlias("grupoOcupacional", "grupoOcupacional");
            c.add(Restrictions.like("grupoOcupacional", bregimengruponivel.getGrupo()));
        }
        if (bregimengruponivel.getNivelRemunerativo() != null && !bregimengruponivel.getNivelRemunerativo().equals("")) {
            c.createAlias("nivelRemunerativo", "nivelRemunerativo");  
            c.add(Restrictions.like("nivelRemunerativo", bregimengruponivel.getNivelRemunerativo()));
        } 
        return c.list();
    }

    /*@InjectComponent
    private Zone selectZone;*/
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
    Object onActionFromEditar(Cargoxunidad c) {
        cargo = (Cargoxunidad) session.load(Cargoxunidad.class, c.getId());
        //   familiarActual = f;
        uo=cargo.getUnidadorganica();
        cargoDatos();
        errorBorrar = null;
        editando = true;
        //uo=cargo.getUnidadorganica();
        //System.out.println("uo en actionfromeditar "+uo+" getpuedeeditar "+getPuedeEditar() );
        return zonasDatos();
    }

    @Log
    void cargoDatos() {
        regimengruponivel = new RegimenGrupoNivel();

        regimengruponivel.setNivelRemunerativo(cargo.getNivelRemunerativo());
        regimengruponivel.setGrupo(cargo.getGrupoOcupacional());
        regimengruponivel.setRegimen(cargo.getRegimenlaboral());

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

        if (uo == null) {
            editando = false;
        }

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
            uo = cargo.getUnidadorganica();
            nivel = uo.getNivel();           


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
    Cargo onPassivate()
    {
    return cargo;
    }
     */
    @Log
    @CommitAfter
    Object onBorrarDato(Cargoxunidad dato) {
        errorBorrar = null;
        Criteria c;
        c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("cargo", dato));
        c.add(Restrictions.like("estado", Cargoxunidad.ESTADO_ALTA));

        if (c.list().size() > 0) {
            errorBorrar = Errores.ERROR_BORRAR_CARGO;
        } else {
            dato.setEstado(Cargoxunidad.ESTADO_BAJA);
            session.saveOrUpdate(dato);
            session.flush();
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
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
        num=2;     
        cargo = new Cargoxunidad();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
    }
    
    void onSelectedFromCancelar() {        
        num=2;     
        cargo = new Cargoxunidad();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
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

        if(num==2){
            
                      
        }else{
            // Seguimos sólo si hay puestos disponibles.

            if (!editando) {
                if(uo==null){
                    uo = cargo.getUnidadorganica();
                }
                // Es porque estoy en uno nuevo
                Criteria c = session.createCriteria(Cargoxunidad.class);
                c.add(Restrictions.like("cod_cargo", cargo.getCod_cargo()));
                c.createAlias("unidadorganica", "unidadorganica");
                c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
                //c.add(Restrictions.like("und_organica", uo));
                if (c.list().size() > 0) {
                    _altaForm.recordError(Errores.ERROR_COD_CARGO_UNICO);
                                   }
            } else {
                //_altaForm.recordError("editandooo");
                // editando
                /*if(Helpers.getCantPuestosOcupadosCargo(session, c) > c.getCtd_puestos_total() ){
                _altaForm.recordError(Errores.ERROR_CTD_PUESTOS_MAY_TOTALES);
                }*/
            }
            envelope.setContents(helpers.Constantes.CARGO_EXITO);
        }

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
        c.add(Restrictions.eq("entidad", entidadUE));
        formNivelUOCargo.recordError(String.valueOf(nivel));
        formNivelUOCargo.recordError(String.valueOf(entidadUE));
        list = c.list();
        _beanUOrganicas = new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
        uo = null;
        return nivelCargoZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromformUOCargo() {
        cargo = new Cargoxunidad();
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
        if(num==2){
            
        }else{
        errorBorrar = null;
        if (!editando) {
            cargo.setUnidadorganica(uo);
            cargo.setCtd_puestos_total(Cargoxunidad.CANT_DEFAULT);
        }


        cargo.setNivelRemunerativo(regimengruponivel.getNivelRemunerativo());
        cargo.setGrupoOcupacional(regimengruponivel.getGrupo());
        cargo.setRegimenlaboral(regimengruponivel.getRegimen());

        cargo.setEstado(Cargoxunidad.ESTADO_ALTA);
//        session.saveOrUpdate(cargo);
        session.merge(cargo);
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(cargo.getId()), (editando ? Logger.CODIGO_OPERACION_ALTA : Logger.CODIGO_OPERACION_MODIFICACION), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        cargo = new Cargoxunidad();
        regimengruponivel = new RegimenGrupoNivel();

        editando = false;
        //return abmZone.getBody();
        }
        return zonasDatos();

    }

    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("selectUOZone", selectUOZone.getBody()).add("abmZone", abmZone.getBody()) //.add("selectZone",selectZone.getBody())
                .add("OcupacionalesZone", OcupacionalesZone.getBody()).add("filtrosZone", filtrosZone.getBody())
                .add("BOcupacionalesZone", BOcupacionalesZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody()).add("OcupacionalesZone", OcupacionalesZone.getBody())
                .add("filtrosZone", filtrosZone.getBody()).add("BOcupacionalesZone", BOcupacionalesZone.getBody());
        return mu;

    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getbeanDatoSituacionCAP() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);
        
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SITUACIONCAP", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
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
