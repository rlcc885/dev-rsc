package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.internal.services.UploadedFileItem;
import org.apache.tapestry5.upload.services.UploadedFile;
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
    private Entidad subEntidadUE;
    @Property
    private Entidad oi;
    @Component(id = "formularioentidad")
    private Form formularioentidad;
    @Component(id = "formulariosubentidad")
    private Form formulariosubentidad;
    @Property
    @SessionState
    private Usuario _usuario;
    @Persist
    private String nombreArchivo;
   @Property
    @Persist
    private Ubigeo ubigeoEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeoEntidadZone;

    
    //Entidad Origen
    @InjectComponent
    private Zone EOrigenZone;
    @InjectComponent
    private Zone entiZone;    
    @Persist
    @Property
    private String bdenoentidad; 
    @Property
    @Persist
    private boolean mostrar;
    @Property
    private Entidad entio;
    @InjectComponent
    private Zone busZone;
    @Persist
    @Property
    private String entidad_origen;
    
    
    //Trabajador
    @InjectComponent
    private Zone busZone2;
    @Persist
    @Property
    private String apeTrabajador; 
    @InjectComponent
    private Zone trabajadorZone;    
    @Property
    private Trabajador trabajador;
    @Persist
    @Property
    private String valida;
    @Persist
    @Property
    private String titular;
    @Persist
    @Property
    private String jefeOGA;
    @Persist
    @Property
    private String jefeRRHH;
    @InjectComponent
    private Zone TitularZone;
    @InjectComponent
    private Zone JefeRRHHZone;
    @InjectComponent
    private Zone JefeOGAZone;
    @Property
    @Persist
    private boolean btitular;
    @Property
    @Persist
    private boolean bjefeOGA;    
    @Property
    @Persist
    private boolean bjefeRRHH;
    
    
    @Property
    @Persist
    private Ubigeo ubigeoSubEntidadUE;
    @InjectComponent
    @Property
    private Zone ubigeoSubEntidadZone;
   
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private boolean mostrarFiltros;
    @Persist
    @Property
    private String mostrarEsconder;
 
    @InjectComponent
    @Property
     private Zone nivelOrganizacionSectorZone;
    @InjectComponent
    private Envelope envelope;
   
        
    @Property
    @Persist
    private UploadedFile file;
    @Property
    @Persist
    private UploadedFile file2;

    private int elemento=0;

   //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            entidadUE = new Entidad();
            subEntidadUE = new Entidad();
            entio = new Entidad();
    }
   //para la busqueda de entidades
   
    public List<Entidad> getEntidadesUEjecutoras() {
        Criteria c;
        c = session.createCriteria(Entidad.class);
        c.add(Restrictions.ne("estado", Entidad.ESTADO_BAJA));
        return c.list();
    }
    
    //para obtener datatos del Nivel Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelGobierno() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Sector Gobierno
    @Log
    public GenericSelectModel<DatoAuxiliar> getSectorGobierno() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("SECTORGOBIERNO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos de la Organizacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getOrganizacionEstado() {
             List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ORGANIZACIONESTADO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Organismo
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoOrganismo() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOORGANISMO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Via
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Zona
     @Log
    public GenericSelectModel<DatoAuxiliar> getTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
     //para obtener datatos del Tipo Via Sub Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getSubTipoVia() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datatos del Tipo Zona Sub Entidad
     @Log
    public GenericSelectModel<DatoAuxiliar> getSubTipoZona() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOZONA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datatos del Tipo Entidad
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoSubEntidad() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSUBENTIDAD", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
     
     //reset del formulario (borrar objeto)
     
    @Log
    void onSelectFromReset() {
        entidadUE = new Entidad();
        //ubigeoEntidadUE = new Ubigeo();
    }
    
    @Log
    void onSelectFromBuscarentidad() {
         elemento=1;
    }


    @Log
    void onSelectFromBuscarTitular() {
         btitular = true; 
    }
    
    @Log
    void onSelectFromBuscarjeferrhh() {
         bjefeRRHH=true;
    }
    
    @Log
    void onSelectFromBuscarjefeoga() {
         bjefeOGA=true;
    }
    

    @Log
    @CommitAfter    
    Object onSuccessFromFormularioentidad() {
     
        
        entidadUE.setDepartamento(ubigeoEntidadUE.getDepartamento());
        entidadUE.setProvincia(ubigeoEntidadUE.getProvincia());
        entidadUE.setDistrito(ubigeoEntidadUE.getDistrito());
        
        //nombreArchivo = file.getFileName() ;
        //System.out.println(nombreArchivo);
        //entidadUE.setLogotipo(nombreArchivo);
        session.saveOrUpdate(entidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(entidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        return nivelOrganizacionSectorZone.getBody();
        
    }

    @Log
    @CommitAfter    
    Object onSuccessFromFormulariosubentidad() {
 
        
        subEntidadUE.setDepartamento(ubigeoSubEntidadUE.getDepartamento());
        subEntidadUE.setProvincia(ubigeoSubEntidadUE.getProvincia());
        subEntidadUE.setDistrito(ubigeoSubEntidadUE.getDistrito());
        subEntidadUE.setEntidad(entio);
       // nombreArchivo = file.getFileName();
        //System.out.println(nombreArchivo);
        //entidadUE.setLogotipo(nombreArchivo);
        session.saveOrUpdate(subEntidadUE);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(subEntidadUE.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_ORGANISMO_INFORMANTE);
        envelope.setContents(helpers.Constantes.EUE_EXITO);
        return nivelOrganizacionSectorZone.getBody();
        
    }
    
 
    AMEntidadUEjecutora onActionFromToggle_filtros() {
        if (mostrarFiltros) {
            mostrarFiltros = false;
            mostrarEsconder = "Mostrar";
        } else {
            mostrarFiltros = true;
            mostrarEsconder = "Oscultar";
        }
        return this;
    }
   
 
    @Log
    public void onActivate(Entidad eue) {
        entidadUE = eue;
        mostrarEsconder = "Ocultar";
        mostrarFiltros = true;
    }

    
    
    
    //Metodos de Busqueda de Entidades    
    @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(Entidad.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));      
        return c.list();
    }
    
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        mostrar=true;
        return new MultiZoneUpdate("busZone", busZone.getBody())                             
                    .add("entiZone", entiZone.getBody());
    }
    
    @Log
    Object onActionFromEditar(Entidad entix) {        
        entio = entix;
        entidad_origen=entio.getDenominacion(); 
        subEntidadUE.setEntidad(entio);
        subEntidadUE.setEsSubEntidad(true); 
        mostrar=false;
        return EOrigenZone.getBody();  
    }
    
    //Metodos de Busqueda de Trabajadores
     @Log
    @CommitAfter
    Object onSuccessFromFormularioTrabajador() { 
        mostrar=true;
        btitular=true;
        bjefeRRHH=true;
        bjefeOGA=true;
        return new MultiZoneUpdate("busZone2", busZone2.getBody())                             
                    .add("trabajadorZone", trabajadorZone.getBody());
    }
     
   @Log
    public List<Trabajador> getTrabajadores() {
        Criteria c = session.createCriteria(Trabajador.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("apellidoPaterno", apeTrabajador + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apeTrabajador.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("apellidoPaterno", apeTrabajador.replaceAll("n", "ñ") + "%").ignoreCase()));      
        return c.list();
    }
   
   @Log
    Object onActionFromeditarTitular(Trabajador traba) {        
        trabajador = traba;
       
            titular=trabajador.getApellidoPaterno();
            btitular=false;
       
        mostrar=false;
        return TitularZone.getBody();  
    }
   
    @Log
    Object onActionFromeditarJefeRRHH(Trabajador traba) {        
        trabajador = traba;
       
        jefeRRHH=trabajador.getApellidoPaterno();
        bjefeRRHH=false;
        
        mostrar=false;
        return TitularZone.getBody();  
    }
    
     @Log
    Object onActionFromeditarJefeOGA(Trabajador traba) {        
        trabajador = traba;
        
            jefeOGA=trabajador.getApellidoPaterno();
            bjefeOGA=false;

        mostrar=false;
        return TitularZone.getBody();  
    }
   
   
    
}