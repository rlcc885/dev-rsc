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
    private UnidadOrganica uo;
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
    private GenericSelectModel<UnidadOrganica> _beanUOrganicas;
    @Inject
    private PropertyAccess _access;
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

    private int num=0,num2=0,num3=0;
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
    private Zone nivelcargo;
    
    //perfiles
    @Property
    @Persist
    private boolean aselect;
    @Property
    @Persist
    private boolean ainsert;
    @Property
    @Persist
    private boolean aupdate;
    @Property
    @Persist
    private boolean adelete;
     /*@Property
    @Persist
    private Ocupacional ocupacional;*/
    
    @Log
    @SetupRender
    private void inicio() {
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento",loggedUser.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();        
        if(result.isEmpty()){
            System.out.println(String.valueOf("Vacio:"));
            
        }
        else{
            UsuarioAcceso usu = (UsuarioAcceso) result.get(0);
            if(usu.getAccesoselect()==1)
                aselect=false;
            else
                aselect=true;
            if(usu.getAccesoreport()==1)
                ainsert=false;
            else
                ainsert=true;
            if(usu.getAccesoupdate()==1)
                aupdate=false;
            else
                aupdate=true;
            if(usu.getAccesodelete()==1)
                adelete=false;
            else
                adelete=true;
           
//            aselect=(usu.getAccesoselect()!=0);
//            ainsert=(usu.getAccesoreport()!=0);
//            aupdate=(usu.getAccesoupdate()!=0);
//            adelete=(usu.getAccesodelete()!=0);
            System.out.println(String.valueOf("Convirtio:"+aselect+ainsert+aupdate+adelete));            
        }
        
        
    }
    @Log
    @CommitAfter
    Object retorno(){        
        return Alerta.class;
    }
    
    void onSelectedFromClear() {        
        num2=2;
        nivel=null; 
        valsituacioncap=null;
        bdcargo=null;        
        bregimengruponivel = new RegimenGrupoNivel();
    }
    
    void onSelectedFromMuestra() {        
        num2=3;
        mostrar=true; 
        nivel=null; 
        valsituacioncap=null;
        bdcargo=null;        
        bregimengruponivel = new RegimenGrupoNivel();
        uo=null;
//        cargo = new Cargoxunidad();
//        editando = false;
//        regimengruponivel = new RegimenGrupoNivel();
        num3=2;
    }
    
    void onSelectedFromSave() {        
        num=1;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariofiltrocargo() {
        if(num2==2){
           reto=zonasFiltros();
        }
        else if(num2==3){
            return new MultiZoneUpdate("filtrosZone", filtrosZone.getBody()).add("listaCargo", listaCargo.getBody())
                .add("BOcupacionalesZone", BOcupacionalesZone.getBody()).add("nivelUOZone", nivelUOZone.getBody()).add("nivelcargo", nivelcargo.getBody());
        }            
        else{
            mostrar=true;
            num3=1;
            //envelope.setContents(String.valueOf(nivel)+bdcargo+String.valueOf(uo)+String.valueOf(valsituacioncap));
            reto=zonasDatos(); 
            
        }        
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
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));        
        c.add(Restrictions.eq("entidad", entidadUE));
        if(nivel!=null){
            c.add(Restrictions.eq("nivel", nivel));
        }
        list = c.list();
        _beanUOrganicas = new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
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
    public List<Cargoxunidad> getCargos() {
        Criteria c = session.createCriteria(Cargoxunidad.class);
        c.createAlias("unidadorganica", "unidadorganica"); 
        c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        if(num3==2){
            
        }
        else{  
            if(nivel!= null){
                c.add(Restrictions.eq("unidadorganica.nivel", nivel));     
            }
            if (uo != null && !uo.equals("")) {
                c.add(Restrictions.eq("unidadorganica", uo));
            }
            if (bdcargo != null && !bdcargo.equals("")) {
                c.add(Restrictions.disjunction().add(Restrictions.like("den_cargo", bdcargo + "%").ignoreCase()).add(Restrictions.like("den_cargo", bdcargo.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("den_cargo", bdcargo.replaceAll("n", "ñ") + "%").ignoreCase()));
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
        //uo=cargo.getUnidadorganica();
        cargoDatos();
        errorBorrar = null;
        editando = true;
        //envelope.setContents(String.valueOf(uo)+String.valueOf(cargo.getUnidadorganica()));
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
        num=2;     
        cargo = new Cargoxunidad();
        editando = false;
        regimengruponivel = new RegimenGrupoNivel();
    }
    
    void onSelectedFromCancel() {        
        num=3;     
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
            
                      
        }
        else if(num==3){
        
        }else{
            if (!editando) {
                if(ainsert){
                    _altaForm.recordError("Usted no tiene acceso para registrar datos");
                }
            }
            else{
                if(aupdate){
                    _altaForm.recordError("Usted no tiene acceso para actualizar datos"); 
                }
            }
            // Seguimos sólo si hay puestos disponibles.
            Criteria c;
            c = session.createCriteria(Cargoxunidad.class);
//            if (!editando) {
////                if(uo==null){
////                    uo = cargo.getUnidadorganica();
////                }
//                // Es porque estoy en uno nuevo
//                
//            } else {
//                c.add(Restrictions.ne("id", cargo.getId()));
//            }
            if (editando) {
                    c.add(Restrictions.ne("id", cargo.getId()));
            }
            c.add(Restrictions.like("cod_cargo", cargo.getCod_cargo()));
            c.createAlias("unidadorganica", "unidadorganica");
            c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
            //c.add(Restrictions.like("und_organica", uo));
            if (c.list().size() > 0) {
                _altaForm.recordError(Errores.ERROR_COD_CARGO_UNICO);
                    
            }
            else{
                c = session.createCriteria(Cargoxunidad.class);
                if (editando) {
                    c.add(Restrictions.ne("id", cargo.getId()));
                }

                c.add(Restrictions.like("den_cargo", cargo.getDen_cargo()));
                c.createAlias("unidadorganica", "unidadorganica");
                c.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
                //c.add(Restrictions.like("und_organica", uo));
                if (c.list().size() > 0) {
                    _altaForm.recordError(Errores.ERROR_DEN_CARGO_UNICO);

                }
                
            }
//            Criteria cx = session.createCriteria(Cargoxunidad.class);            
//            cx.add(Restrictions.like("den_cargo", cargo.getDen_cargo()));
//            cx.createAlias("unidadorganica", "unidadorganica");
//            cx.add(Restrictions.eq("unidadorganica.entidad", entidadUE ));
//            if (cx.list().size() > 0) {
//                _altaForm.recordError(Errores.ERROR_DEN_CARGO_UNICO);                    
//            }
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
        uo = null;
        return nivelUOZone.getBody();
    }

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
    @CommitAfter
    Object onSuccessFromformOcupacional() {
        return OcupacionalesZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioaltacargo() {

        if(num==2){
            
        }
        else if(num==3){
        
        }else if(num==1){
        errorBorrar = null;
        if (!editando) {
            //cargo.setUnidadorganica(uo);
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

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody()) //.add("selectZone",selectZone.getBody())
                .add("OcupacionalesZone", OcupacionalesZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("listaCargo", listaCargo.getBody()).add("OcupacionalesZone", OcupacionalesZone.getBody())
                .add("abmZone", abmZone.getBody());
        return mu;

    }
    @Log 
    private MultiZoneUpdate zonasFiltros() {
        MultiZoneUpdate mu;
        mu = new MultiZoneUpdate("filtrosZone", filtrosZone.getBody())
                .add("BOcupacionalesZone", BOcupacionalesZone.getBody()).add("nivelUOZone", nivelUOZone.getBody()).add("nivelcargo", nivelcargo.getBody());
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
