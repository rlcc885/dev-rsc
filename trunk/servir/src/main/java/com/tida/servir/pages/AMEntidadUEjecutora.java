package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.ConceptoRemunerativo;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Entidad;
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
 *
 * @author LFL
 */
public class AMEntidadUEjecutora extends GeneralPage {

    @Inject
    private Session session;
    @Property
    @Persist
    private Entidad entidadUE;
    @Property
    @Persist
    private boolean editando;
    @Property
    private Entidad oi;
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
    @Inject
    private PropertyAccess _access;
 /*   @Property
    @Persist
    private String nivelgobierno;
    @Property
    @Persist
    private String organizacionestado;
    @Property
    @Persist
    private String sectorgobierno;
    @Property
    @Persist
    private String tipoorganismo;
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
    private Zone nivelOrganizacionSectorZone;
 */   @InjectComponent
    private Envelope envelope;

   
   //para la busqueda de entidades
   
    public List<Entidad> getEntidadesUEjecutoras() {
        Criteria c;
        c = session.createCriteria(Entidad.class);
        c.add(Restrictions.ne("estado", Entidad.ESTADO_BAJA));
        return c.list();
    }
    
    //para obtener datatos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanDatoAuxNivel() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Sector Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanDatoAuxSector() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos de la Organizacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanDatoAuxOrganizacion() {
             List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Organismo
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanDatoAuxTipoOrganismo() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
     @Log
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

     
      /*
    @Log
    Object onSuccessFromformNivSecPli() {
        entidadUE.getNivelGobierno().setValor(nivelgobierno);
        entidadUE.getOrganizacionEstado().setValor(organizacionestado);
        entidadUE.getSectorGobierno().setValor(sectorgobierno);
        entidadUE.getTipoOrganismo().setValor(tipoorganismo);
        return nivelOrganizacionSectorZone.getBody();
    }

     * 
    */
    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
        return Permisos.puedeEscribirBK(_usuario, entidadUE);
    }
   
  /*  
    @Log
    public boolean getEsLocal() {
        if (nivelgobierno == null) {
            nivelgobierno = new String();
        }
        System.out.println("Es local:" + nivelgobierno.equals("NIVEL LOCAL"));
        return nivelgobierno.equals("NIVEL LOCAL");
    }

    @Log
    @CommitAfter
    Object onBorrarDato(Entidad dato) {
        dato.setEstado(Entidad.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        return this;// La/a zona a actualizar
    }
    
    @Log
    public boolean getEsBorrable() {
       

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
*/
    @Log
    void onValidateFromformularioaltaentidaduejecutoras() {

        Criteria c = session.createCriteria(Entidad.class);
        if (editando) {
            c.add(Restrictions.ne("id", entidadUE.getId()));
        }

        c.add(Restrictions.eq("cue_entidad", entidadUE.getCue_entidad()));

        if (c.list().size() > 0) {
            formularioaltaentidaduejecutoras.recordError(Errores.ERROR_ORGANISMO_REPETIDO);
        }
/*
        if (nivelgobierno == null || nivelgobierno.trim().length() == 0) {
            formularioaltaentidaduejecutoras.recordError(Errores.ERROR_NIVEL_OBLIGATORIO);
        }

        if (!nivelgobierno.contains("NIVEL LOCAL")) {

            if (organizacionestado == null || organizacionestado.trim().length() == 0) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_PLIEGO_OBLIGATORIO);
            }
            
            if (sectorgobierno == null || sectorgobierno.trim().length() == 0) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_SECTOR_OBLIGATORIO);
            }
            
            if (tipoorganismo == null || tipoorganismo.trim().length() == 0) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_PLIEGO_OBLIGATORIO);
            }
        } else {
            if (ubigeoEntidadUE.getDepartamento() == null) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_DEPARTAMENTO_OBLIGATORIO);
            }

            if (ubigeoEntidadUE.getProvincia() == null) {
                formularioaltaentidaduejecutoras.recordError(Errores.ERROR_PROVINCIA_OBLIGATORIO);
            }
        }
        * 
        */
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaentidaduejecutoras() {
 
        entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
        entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
        entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
/*
        if (nivelgobierno.contains("NIVEL LOCAL")) {
            if (entidadUE.getDepartamento() != null) {
                sectorgobierno = ubigeoEntidadUE.getDepartamento().getValor();
            } else {
                sectorgobierno = "";
            }
            
            entidadUE.getSectorGobierno().setValor(sectorgobierno);
        }
        * 
        */
        session.saveOrUpdate(entidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
/*
        if (!editando) {

            entidadUE.setEstado(Entidad.ESTADO_ALTA);
            UnidadOrganica nuevaUnidadOrganica = new UnidadOrganica();
            nuevaUnidadOrganica.setEntidad(entidadUE);
            nuevaUnidadOrganica.setCod_und_organica(UnidadOrganica.CODIGO_DEFAULT);
            nuevaUnidadOrganica.setDen_und_organica(UnidadOrganica.NOMBRE_DEFAULT);
            nuevaUnidadOrganica.setEstado(UnidadOrganica.ESTADO_ALTA);
            nuevaUnidadOrganica.setCue(UnidadOrganica.CUE_DEFAULT);
            nuevaUnidadOrganica.setNivel(UnidadOrganica.NIVEL_DEFAULT);
            nuevaUnidadOrganica.setSigla(UnidadOrganica.SIGLA_DEFAULT);
            session.save(nuevaUnidadOrganica);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevaUnidadOrganica.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
            //System.out.println("Creada Unidad Organica. Id: " + nuevaUnidadOrganica.getId());

            Cargoxunidad nuevoCargo = new Cargoxunidad();
            nuevoCargo.setUnd_organica(nuevaUnidadOrganica);
            nuevoCargo.setCod_cargo(Cargoxunidad.CODIGO_DEFAULT);
            nuevoCargo.setDen_cargo(Cargoxunidad.DEN_DEFAULT);
            nuevoCargo.setEstado(Cargoxunidad.ESTADO_ALTA);
            nuevoCargo.setCtd_puestos_total(Cargoxunidad.CANT_MAX);
=======
            Cargoxunidad nuevoCargo = new Cargoxunidad();
            nuevoCargo.setUnidadorganica(nuevaUnidadOrganica);
            nuevoCargo.setCod_cargo(Cargoxunidad.CODIGO_DEFAULT);
            nuevoCargo.setDen_cargo(Cargoxunidad.DEN_DEFAULT);
            nuevoCargo.setEstado(Cargoxunidad.ESTADO_ALTA);
            nuevoCargo.setCtd_puestos_total(Cargoxunidad.CANT_MAX);
>>>>>>> .r72
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);

            //nuevoCargo.setClasificacion_funcional(list.get(0)); // Le asignamos un clasificador funcional
            session.save(nuevoCargo);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(nuevoCargo.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
            //System.out.println("Creado Cargo. Id: " + nuevoCargo.getId());

        } else {
            session.merge(entidadUE);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_MODIFICACION, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        }

*/

        //System.out.println("departamento apres  " + entidadUE.getCod_ubi_dept().getValor());

        entidadUE = new Entidad();
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        editando = false;
        return this;
    }

    /*
     * reset del formulario (borrar objeto)
     */
    @Log
    void onActionFromReset() {
        editando = false;
        entidadUE = new Entidad();
  /*      nivelgobierno = null;
        sectorgobierno = null;
        organizacionestado = null;
        tipoorganismo = null;
        
        */

        ubigeoEntidadUE = new Ubigeo();
    }
    /*
     * Cargar desde los parámetros
     */

    @Log
    @SetupRender
    private void setupCombos() {
        //System.out.println("=======================Estoy aca,");
        if (entidadUE != null) {
            /*
            nivelgobierno = entidadUE.getNivelGobierno().getValor();
            sectorgobierno = entidadUE.getSectorGobierno().getValor();
            organizacionestado = entidadUE.getOrganizacionEstado().getValor();
            tipoorganismo = entidadUE.getTipoOrganismo().getValor();
            */
            if (ubigeoEntidadUE == null) {
                ubigeoEntidadUE = new Ubigeo();
            }
            ubigeoEntidadUE.setDepartamento(entidadUE.getDepartamento());
            ubigeoEntidadUE.setProvincia(entidadUE.getProvincia());
            ubigeoEntidadUE.setDistrito(entidadUE.getDistrito());
        } else {
            //System.out.println("=======================Estoy aca, no hay Entidad");
            entidadUE = new Entidad();
        }
        /*
        if (nivelgobierno == null) {
            nivelgobierno = new String();
        }
        * 
        */

    }

    @Log
    public void onActivate(Entidad eue) {
        entidadUE = eue;
        editando = true;
    }
}