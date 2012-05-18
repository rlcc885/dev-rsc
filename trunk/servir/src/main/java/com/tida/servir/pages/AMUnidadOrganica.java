package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Cargo;
import helpers.Errores;
import helpers.Logger;

import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades organicas
 * 
 * @author ale
 * 
 */
public class AMUnidadOrganica extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @Persist
    private UnidadOrganica unidadOrganica;
    @Property
    @Persist
    private UnidadOrganica uoAntecesora;
    @Inject
    private Request _request;
    @Property
    private UnidadOrganica uo;
    @Property
    @Persist
    private String domicilioCP;
    @Property
    @Persist
    private String domicilioDireccion;

    @Persist
    private boolean editando;
    @InjectComponent
    private Zone listaUOZone;
    @InjectComponent
    private Zone unidadesOrganicasZone;
    @InjectComponent
    @Property
    private Zone ubigeoDomZone;
    // datos del formulario (que son persistentes)
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Property
    @SessionState
    private Usuario loggedUser;
    @Component(id = "formularioaltaunidadorganica")
    private Form formularioaltaunidadorganica;
    @Property
    @Persist
    private String errorBorrar;
    @Property
    @SessionState
    private EntidadUEjecutora entidadUE;
    @Property
    @Persist
    private Integer nivelUO;
    @Property
    @Persist
    private String localidad;
    @InjectComponent
    @Property
    private Zone nivelZone;
    @InjectComponent
    private Envelope envelope;

    @Log
    public List<UnidadOrganica> getUnidadesOrganicas() {
        Criteria c;
        c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidadUE", entidadUE));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        /*if(!c.list().isEmpty())
            unidadOrganica = (UnidadOrganica) c.list().get(0);*/
        return c.list();
    }

    public boolean getUsuarioGeneral() {
        return Helpers.esMultiOrganismo(loggedUser);
    }

    public boolean getNoUsuarioGeneral() {
        return !getUsuarioGeneral();
    }
    /*public Object onValueChanged(Object o){
    if(o != null) {
    if(o.getClass().equals(EntidadUEjecutora.class)) {
    Criteria c;
    c = session.createCriteria(Organo.class);
    c.add(Restrictions.like("entidadUE", (EntidadUEjecutora) o));
    c.add(Restrictions.ne("estado", Organo.ESTADO_BAJA));
    List<Organo> list = c.list();
    entidadUE = (EntidadUEjecutora) o;
    _beans = new GenericSelectModel<Organo>(list,Organo.class,"den_organo","id",_access);
    
    if (c.list().size() > 0)
    _org = (Organo) c.list().get(0);
    //return selectZone.getBody();
    return todasZonas();
    }
    
    if(o.getClass().equals(Organo.class)) {
    _org = (Organo) o;
    System.out.println("------------------------Cambiando _org="+ _org.getDen_organo());
    
    return zonas();
    } else {
    System.out.println("------------------------No es ni organo ni Organismo");
    }
    } else {
    System.out.println("------------------------ el dato es nulo !");
    }
    System.out.println("------------------------ Sale por el final !");
    return zonas();
    
    }*/
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
     * Hasta acá para levantar combo de organos
     */
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody()).add("unidadesOrganicasZone", unidadesOrganicasZone.getBody()).add("nivelZone", nivelZone.getBody()).add("listaUOZone", listaUOZone.getBody());

        return mu;
    }
    
    private MultiZoneUpdate zonasUOAntecesora() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody())
                .add("nivelZone", nivelZone.getBody());

        return mu;
    }

    @Log
    void cargoDatos() {
        ubigeoDomicilio = new Ubigeo();
        //System.out.println("------------unidadORGANICAZ "+unidadOrganica.getCod_und_organica());
        ubigeoDomicilio.setDepartamento(unidadOrganica.getCod_ubi_dept());
        ubigeoDomicilio.setProvincia(unidadOrganica.getCod_ubi_prov());
        ubigeoDomicilio.setDistrito(unidadOrganica.getCod_ubi_dist());
        nivelUO = unidadOrganica.getNivel();
        uoAntecesora = unidadOrganica.getUoAntecesora();
    }

    void onCpChanged() {
        domicilioCP = _request.getParameter("param");
    }

    void onDomChanged() {
        domicilioDireccion = _request.getParameter("param");
    }

    public boolean getEsBorrable() {
        /*
         * Buscamos;
         * Cargos
         */

        Criteria c;
        c = session.createCriteria(Cargo.class);
        c.add(Restrictions.eq("und_organica", uo));
        c.add(Restrictions.ne("estado", Cargo.ESTADO_BAJA));
        // no quiero las que estén en baja.
        if (c.list().size() > 0) {
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

    public List<String> getTipoActividad() {
        return Helpers.getValorTablaAuxiliar("TipoActividad", session);
    }

    /*
    public GenericSelectModel<EntidadUEjecutora> getBeansEUE(){
    Criteria c;
    c = session.createCriteria(UnidadOrganica.class);
    c.add(Restrictions.eq("entidadUE", entidadUE));
    c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
    
    return new GenericSelectModel<EntidadUEjecutora>(c.list(),EntidadUEjecutora.class,"denominacion","id",_access);
    }
     */
    @Log
    public GenericSelectModel<UnidadOrganica> getbeansUO() {

        Criteria c;
        c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.eq("entidadUE", entidadUE));
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        c.add(Restrictions.eq("nivel", nivelUO - 1));


        return new GenericSelectModel<UnidadOrganica>(c.list(), UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    @Log
    @CommitAfter
    Object onSuccessFromformNivelUO() {

        
        if(uoAntecesora != null){
            System.out.println("------------- unidad organica "+uoAntecesora.getCod_und_organica());
            ubigeoDomicilio.setDepartamento(uoAntecesora.getCod_ubi_dept());
            ubigeoDomicilio.setDistrito(uoAntecesora.getCod_ubi_dist());
            ubigeoDomicilio.setProvincia(uoAntecesora.getCod_ubi_prov());
        }
        
        if (getPuedeEditar()) {
            System.out.println("--------- entré iuci");
            //onUbigeoEntidadUOAntecesora();
            return new MultiZoneUpdate("ubigeoDomZone", ubigeoDomZone.getBody()).add("nivelZone", nivelZone.getBody());
        }
        return nivelZone.getBody();
    }

    public Boolean getNivelUno() {
        return nivelUO == 1;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaunidadorganica() {
        errorBorrar = null;
        Criteria c;
        c = session.createCriteria(UnidadOrganica.class);

        if (editando) {
            c.add(Restrictions.ne("id", unidadOrganica.getId()));
        } else {
            unidadOrganica.setEntidadUE(entidadUE);
            unidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
        }

        c.add(Restrictions.like("cod_und_organica", unidadOrganica.getCod_und_organica()));
        c.add(Restrictions.eq("entidadUE", unidadOrganica.getEntidadUE()));
        if (c.list().size() > 0) {
            formularioaltaunidadorganica.recordError(Errores.ERROR_COD_UND_ORG_UNICA);
            formularioaltaunidadorganica.recordError("Código ya existente: "
                    + ((UnidadOrganica) c.list().get(0)).getCod_und_organica());
            formularioaltaunidadorganica.recordError("Denominacion: "
                    + ((UnidadOrganica) c.list().get(0)).getDen_und_organica());
            formularioaltaunidadorganica.recordError("Código: "
                    + ((UnidadOrganica) c.list().get(0)).getCod_und_organica());
            formularioaltaunidadorganica.recordError("Estado: "
                    + ((UnidadOrganica) c.list().get(0)).getEstado());
            return zonas();
        }
        unidadOrganica.setNivel(nivelUO);
        unidadOrganica.setCod_ubi_dept(ubigeoDomicilio.getDepartamento());
        unidadOrganica.setCod_ubi_dist(ubigeoDomicilio.getDistrito());
        unidadOrganica.setCod_ubi_prov(ubigeoDomicilio.getProvincia());
        unidadOrganica.setUoAntecesora(uoAntecesora);
        session.saveOrUpdate(unidadOrganica);
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_MODIFICACION : Logger.CODIGO_OPERACION_ALTA), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        editando = false;
        session.flush();
        onActionFromReset();
        setupUbigeos();
        formularioaltaunidadorganica.clearErrors();

        envelope.setContents(helpers.Constantes.UNIDAD_ORGANICA_EXITO);
        return zonas();
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
        onActionFromReset();
        setupUbigeos();
        return zonas();// La/a zona a actualizar
    }

    @Log
    @SetupRender
    private void setupUbigeos() {
        if (nivelUO == null) {
            nivelUO = 1;
        }

        if (unidadOrganica != null) {
            if (ubigeoDomicilio == null) {
                ubigeoDomicilio = new Ubigeo();
            }
            ubigeoDomicilio.setDepartamento(entidadUE.getCod_ubi_dept());
            ubigeoDomicilio.setProvincia(entidadUE.getCod_ubi_prov());
            ubigeoDomicilio.setDistrito(entidadUE.getCod_ubi_dist());
        }

        if (editando != false) {
            onUbigeoEntidadUOAntecesora();
        }
    }

    @Log
    void onPrepareFromformularioaltaunidadorganica() {
        errorBorrar = null;
    }

    @Log
    void onUbigeoEntidadUOAntecesora() {
        if (unidadOrganica != null) {
            // cargamos el ubigeo de la entidad
            ubigeoDomicilio.setDepartamento(unidadOrganica.getCod_ubi_dept());
            ubigeoDomicilio.setProvincia(unidadOrganica.getCod_ubi_prov());
            ubigeoDomicilio.setDistrito(unidadOrganica.getCod_ubi_dist());            
        } else {
            if (uoAntecesora != null) {
                System.out.println("----------- antecesora pas null "+uoAntecesora);
                ubigeoDomicilio.setDepartamento(uoAntecesora.getCod_ubi_dept());
                ubigeoDomicilio.setDistrito(uoAntecesora.getCod_ubi_dist());
                ubigeoDomicilio.setProvincia(uoAntecesora.getCod_ubi_prov());                
            }else{
                System.out.println("----------- antecesora null "+uoAntecesora);
                ubigeoDomicilio.setDepartamento(entidadUE.getCod_ubi_dept());
                ubigeoDomicilio.setDistrito(entidadUE.getCod_ubi_dist());
                ubigeoDomicilio.setProvincia(entidadUE.getCod_ubi_prov());
            }

        }
        
    }

    /*
     * reset del formulario (borrar  objeto)
     */
    @Log
    void onActionFromReset() {
        unidadOrganica = new UnidadOrganica();
        ubigeoDomicilio = new Ubigeo();
        nivelUO = 1;
        uoAntecesora = null;
        editando = false;
        onUbigeoEntidadUOAntecesora();
    }

    /*
    Cargar desde los parámetros
     */
    @Log
    void onActivate() {

        if (unidadOrganica == null) {
            System.out.println("----------- unidad organica null");
            onActionFromReset();
        }


    }

    @Log
    void onActivate(UnidadOrganica uo) {
        if (uo == null) {
            ubigeoDomicilio = new Ubigeo();
            onActionFromReset();
        } else {
            unidadOrganica = uo;
            editando = true;
            cargoDatos();
        }
    }
}
