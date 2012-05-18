package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Cargo;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Ubigeo;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.List;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Clase que maneja las unidades ejecutoras
 * @author ale
 */
public class AMEntidadUEjecutora extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @Persist
    private EntidadUEjecutora entidadUE;
    @Property
    @Persist
    private boolean editando;
    @Property
    private EntidadUEjecutora oi;
    @Component(id = "formularioaltaentidaduejecutoras")
    private Form formularioaltaentidaduejecutoras;
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @Persist
    private Ubigeo ubigeoEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeoEntidadZone;
    @Property
    @Persist
    private String nivel_gobierno;
    @Property
    @Persist
    private String pliego;
    @Property
    @Persist
    private String sector;
    @Property
    @Persist
    private DatoAuxiliar departamento;
    @Property
    @Persist
    private DatoAuxiliar distrito;
    @Property
    @Persist
    private DatoAuxiliar provincia;
    @InjectComponent
    @Property
    private Zone nivelSectorPliegoZone;
    @InjectComponent
    private Envelope envelope;

    public List<EntidadUEjecutora> getEntidadesUEjecutoras() {
        Criteria c;
        c = session.createCriteria(EntidadUEjecutora.class);
        c.add(Restrictions.ne("estado", EntidadUEjecutora.ESTADO_BAJA));
        return c.list();
    }

    /*
     * levantamos el combo de Unidades ejecutoras
     */
    /*MultiZoneUpdate tresZonas() {
    MultiZoneUpdate mu;
    mu = new MultiZoneUpdate("ubigeoEntidadZone", ubigeoEntidadZone.getBody());
    
    return mu;
    }*/
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    private GenericSelectModel<EntidadUEjecutora> _beans;

    /*private UnidadEjecutora _unidadEjecutora;

    public UnidadEjecutora getUnidadEjecutora(){
    return entidadUE.getUnidadEjecutora();
    }

    public void setUnidadEjecutora(UnidadEjecutora _unidadEjecutora){
    entidadUE.setUnidadEjecutora(_unidadEjecutora);
    }

    public GenericSelectModel<EntidadUEjecutora> getBeans(){
    return _beans;
    }*/
    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
        return Permisos.puedeEscribir(_usuario, entidadUE);
    }

    /**
     * Hasta acá para levantar combo de unidades ejecutoras
     */

    /*public GenericSelectModel<DatoAuxiliar> getBeanDatoAuxEmail() {
    List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TiposEmail", null, 0, session);
    return new GenericSelectModel<DatoAuxiliar>(list,DatoAuxiliar.class,"valor","codigo",_access);
    }*/
    public List<String> getBeanDatoAuxNivel() {
        return Helpers.getValorTablaAuxiliar("NivelGobierno", session);
    }

    public List<String> getBeanDatoAuxSector() {
        return Helpers.getValorTablaAuxiliar("SectorGobierno", session);
    }

    public List<String> getBeanDatoAuxPliego() {
        return Helpers.getValorTablaAuxiliar("Pliego", session);
    }

    @Log
    Object onSuccessFromformNivSecPli() {

        entidadUE.setNivel_gobierno(nivel_gobierno);
        entidadUE.setSector_gobierno(sector);
        entidadUE.setPliego(pliego);
        //System.out.println("--------------------------------------------on succes from form niv sec pli pliego "+pliego+" nivel " +nivel_gobierno);
        return nivelSectorPliegoZone.getBody();
    }

    @Log
    public boolean getEsLocal() {
        if (nivel_gobierno == null) {
            nivel_gobierno = new String();
        }
        System.out.println("Es local:" + nivel_gobierno.equals("NIVEL LOCAL"));
        return nivel_gobierno.equals("NIVEL LOCAL");
    }

    @Log
    @CommitAfter
    Object onBorrarDato(EntidadUEjecutora dato) {
        //solamente borrado lógico.
        dato.setEstado(EntidadUEjecutora.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        return this;// La/a zona a actualizar
    }

    @Log
    public List<String> getClasFuncOrg() {
        return Helpers.getValorTablaAuxiliar("ClasFuncOrg", session);
    }

    @Log
    public boolean getEsBorrable() {
        /*
         * Buscamos;
         * Concepto remunerativo
         *   Legajo
         *   Organo
         *   Usuario
         *
         */

        Criteria c;
        c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("entidadUE", oi));
        // no quiero las que estén en baja.
        if (c.list().size() > 0) {
            return false;
        }

        // Usuario -- en cualquier estado, ya que pueden ser reactivados
        c = session.createCriteria(Usuario.class);
        c.add(Restrictions.eq("entidadUE", oi));
        if (c.list().size() > 0) {
            return false;
        }

        //Concepto remunerativo
        c = session.createCriteria(ConceptoRemunerativo.class);
        c.add(Restrictions.eq("entidadUE", oi));
        if (c.list().size() > 0) {
            return false;
        }
        return true;
    }

    @Log
    void onValidateFromformularioaltaentidaduejecutoras() {
        Criteria c = session.createCriteria(EntidadUEjecutora.class);
        if (editando) {
            c.add(Restrictions.ne("id", entidadUE.getId()));
        }

        c.add(Restrictions.eq("codigoEntidadUE", entidadUE.getCodigoEntidadUE()));

        if (c.list().size() > 0) {
            formularioaltaentidaduejecutoras.recordError(Errores.ERROR_ORGANISMO_REPETIDO);
        }

        if (nivel_gobierno == null || nivel_gobierno.trim().length() == 0) {
            formularioaltaentidaduejecutoras.recordError(Errores.ERROR_NIVEL_OBLIGATORIO);
        }

        if (!nivel_gobierno.contains("NIVEL LOCAL")) {

            if (sector == null || sector.trim().length() == 0) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_SECTOR_OBLIGATORIO);
            }

            if (pliego == null || pliego.trim().length() == 0) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_PLIEGO_OBLIGATORIO);
            }
        } else {
            if (ubigeoEntidadUE.getDepartamento() == null ) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_DEPARTAMENTO_OBLIGATORIO);
            }

            if (ubigeoEntidadUE.getProvincia() == null ) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_PROVINCIA_OBLIGATORIO);
            }
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaentidaduejecutoras() {
        /*
        Criteria c = session.createCriteria(Usuario.class);
        c.add(Restrictions.eq("codigo_usuario", usuario.getCodigo_usuario()));
         *
         */
        entidadUE.setCod_ubi_dept(ubigeoEntidadUE.getDepartamento());
        entidadUE.setCod_ubi_dist(ubigeoEntidadUE.getDistrito());
        entidadUE.setCod_ubi_prov(ubigeoEntidadUE.getProvincia());

        if (nivel_gobierno.contains("NIVEL LOCAL")) {
            if (entidadUE.getCod_ubi_dept() != null) {
                sector = ubigeoEntidadUE.getDepartamento().getValor();
            } else {
                sector = "";
            }

            if (entidadUE.getCod_ubi_prov() != null) {
                pliego = ubigeoEntidadUE.getProvincia().getValor();
            } else {
                pliego = "";
            }

            entidadUE.setSector_gobierno(sector);
            entidadUE.setPliego(pliego);
        }
        session.saveOrUpdate(entidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);

        if (!editando) {

            entidadUE.setEstado(EntidadUEjecutora.ESTADO_ALTA);
            UnidadOrganica nuevaUnidadOrganica = new UnidadOrganica();
            nuevaUnidadOrganica.setEntidadUE(entidadUE);
            nuevaUnidadOrganica.setCod_und_organica(UnidadOrganica.CODIGO_DEFAULT);
            nuevaUnidadOrganica.setDen_und_organica(UnidadOrganica.NOMBRE_DEFAULT);
            nuevaUnidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
            nuevaUnidadOrganica.setCue(UnidadOrganica.CUE_DEFAULT);
            nuevaUnidadOrganica.setNivel(UnidadOrganica.NIVEL_DEFAULT);
            nuevaUnidadOrganica.setSigla(UnidadOrganica.SIGLA_DEFAULT);
            session.save(nuevaUnidadOrganica);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevaUnidadOrganica.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
            //System.out.println("Creada Unidad Organica. Id: " + nuevaUnidadOrganica.getId());

            Cargo nuevoCargo = new Cargo();
            nuevoCargo.setUnd_organica(nuevaUnidadOrganica);
            nuevoCargo.setCod_cargo(Cargo.CODIGO_DEFAULT);
            nuevoCargo.setDen_cargo(Cargo.DEN_DEFAULT);
            nuevoCargo.setEstado(Cargo.ESTADO_ALTA);
            nuevoCargo.setCtd_puestos_total(Cargo.CANT_MAX);
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);

            nuevoCargo.setClasificacion_funcional(list.get(0)); // Le asignamos un clasificador funcional
            session.save(nuevoCargo);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevoCargo.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
            //System.out.println("Creado Cargo. Id: " + nuevoCargo.getId());

        } else {
            session.merge(entidadUE);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_MODIFICACION, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        }



        //System.out.println("departamento apres  " + entidadUE.getCod_ubi_dept().getValor());

        entidadUE = new EntidadUEjecutora();
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        editando = false;
        return this;
    }

    /*
     * reset del formulario (borrar  objeto)
     */
    @Log
    void onActionFromReset() {
        editando = false;
        entidadUE = new EntidadUEjecutora();
        nivel_gobierno = null;
        sector = null;
        pliego = null;
        ubigeoEntidadUE = new Ubigeo();
    }
    /*
    Cargar desde los parámetros
     */

    @Log
    @SetupRender
    private void setupCombos() {
        //System.out.println("=======================Estoy aca,");
        if (entidadUE != null) {
            nivel_gobierno = entidadUE.getNivel_gobierno();
            sector = entidadUE.getSector_gobierno();
            pliego = entidadUE.getPliego();
            if (ubigeoEntidadUE == null) {
                ubigeoEntidadUE = new Ubigeo();
            }
            ubigeoEntidadUE.setDepartamento(entidadUE.getCod_ubi_dept());
            ubigeoEntidadUE.setProvincia(entidadUE.getCod_ubi_prov());
            ubigeoEntidadUE.setDistrito(entidadUE.getCod_ubi_dist());
        } else {
            //System.out.println("=======================Estoy aca, no hay Entidad");
            entidadUE = new EntidadUEjecutora();
        }
        if (nivel_gobierno == null) {
            nivel_gobierno = new String();
        }

    }

    @Log
    public void onActivate(EntidadUEjecutora eue) {
        entidadUE = eue;
        editando = true;
    }
}
