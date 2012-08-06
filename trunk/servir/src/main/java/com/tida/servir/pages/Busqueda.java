package com.tida.servir.pages;

import annotations.XHR;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.services.SelectIdModelFactory;
import helpers.Constantes;
import helpers.Helpers;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Start page of application servir.
 */
//@IncludeJavaScriptLibrary("context:layout/custom.js")
public class Busqueda extends GeneralPage {

    @Inject
    private Session session;
    @Property
    private Trabajador actual;
    @Property
    private String no_implementado;
    @Inject
    private PropertyAccess _access;
    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
    @Property
    @SessionState
    private Entidad _entidadUE;
    @Property
    @SessionState
    private Usuario _usuario;
    @InjectPage
    private TrabajadorNuevo trabajadorNuevo;
    
        @Persist
    @Property
    private Trabajador e;
    @Persist
    @Property
    private Cargoxunidad c;
    @Persist
    @Property
    private boolean mostrarFiltros;
    @Persist
    @Property
    private String mostrarEsconder;
    @Persist
    @Property
    private boolean yaEntrada;
    //
    // Datos para la busqueda
    //
    @Persist
    @Property
    private String apellidoPaterno;
    @Persist
    @Property
    private String apellidoMaterno;
    @Persist
    @Property
    private String nombres;
    @Persist
    @Property
    private String nroDocumento;
//    @Persist
//    @Property
//    private String tipoDocumento;
    //
    // Datos para los filtros
    //
    @Persist
    @Property
    private String sexo;
    @Persist
    @Property
    private Boolean checkfechadenacimientomayora;
    @Persist
    @Property
    private Boolean checkfechadenacimientomenora;
    @Persist
    @Property
    private Boolean checkhabilitacionprofesional;
    @Persist
    @Property
    private Boolean checkdeclaracion;
    @Persist
    @Property
    private Boolean checkconfianza;
    @Persist
    @Property
    private Date fechadenacimientomenora;
    @Persist
    @Property
    private Date fechadenacimientomayora;
    @Property
    @Persist
    private DatoAuxiliar valTipoDiscapacidad;
    @Property
    @Persist
    private DatoAuxiliar valestadocivil;
    @Property
    @Persist
    private DatoAuxiliar valRegimenContratacion;
    @Property
    @Persist
    private Integer valhorassemanalesmayora;
    @Property
    @Persist
    private Integer valhorassemanalesmenora;
    @Property
    @Persist
    private DatoAuxiliar valcodigofuncionaldelcargo;
    @Property
    @Persist
    private DatoAuxiliar valregimenlaboral;
    @Property
    @Persist
    private Boolean valhabilitacionprofesional;
    @Property
    @Persist
    private Boolean valdeclaracion;
    @Property
    @Persist
    private Boolean valconfianza;
    @Inject
    private Request request;    
    @Inject
    private ComponentResources _resources;
    
    
    @Property
    @Persist
    private DatoAuxiliar valnivelinstruccion;
    @Property
    @Persist
    private DatoAuxiliar valformacionprofe;
    @Persist
    @Property
    private Date fechadeingresodesdea;
    @Persist
    @Property
    private Date fechadeingresohastaa;
    @Property
    @Persist
    private DatoAuxiliar valdocumentoide;
    @Property
    @Persist
    private UnidadOrganica valunidadorganica;
    
    @Property
    @Persist
    private UsuarioAcceso usu;
    
    @Property
    @Persist
    private Boolean vselect;
    
    @Component(id = "xxx")
    private Form xxx;
    
    @Property
    @Persist
    private LkAdminTrabajador persons;
    
    @Log
    @SetupRender
    private void inicio() {
        limpiar();
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_nrodocumento",_usuario.getTrabajador().getNroDocumento());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
        List result = query.list();        
        if(result.isEmpty()){
            System.out.println(String.valueOf("Vacio:"));            
        }
        else{
            usu = (UsuarioAcceso) result.get(0);            
            vselect=(usu.getAccesoselect()!=0);
            System.out.println("Eliminar1"+usu.getAccesodelete()+usu.getAccesoreport()+usu.getAccesoupdate());
        }
    }
    
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getFormacionprofesional() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("FORMACIONPROFESIONAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getNivelinstruccion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("NIVELINSTRUCCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }    

    public List<String> getSexos() {
        return Helpers.getValorTablaAuxiliar("SEXO", session);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getDocumentoide() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

//    public List<String> getDiscapacidades() {
//        return Helpers.getValorTablaAuxiliar("TIPODISCAPACIDAD", session);
//    }
//    
    @Log
    public GenericSelectModel<DatoAuxiliar> getEstadocivil() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ESTADOCIVIL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

//    public List<String> getEstado() {
//        List<String> estadosCargo = new LinkedList<String>();
//        /*
//         * TODO JZM verificar linea de codigo
//         */
////        estadosCargo.add(Cargoxunidad.ESTADO_ALTA);
////        estadosCargo.add(Cargoxunidad.ESTADO_BAJA);
//
//        estadosCargo.add("Alta");
//        estadosCargo.add("Baja");
//
//        return estadosCargo;
//    }
        @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas() {
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));        
        c.add(Restrictions.eq("entidad", _entidadUE));        
        list = c.list();
        return new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    public GenericSelectModel<DatoAuxiliar> getRegimenlaborales() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);
    }

    public GenericSelectModel<DatoAuxiliar> getCodFunCargo() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ClasificadorFuncional", null, 0, session);

        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "codigo", _access);

    }
    @Component(id = "formulariobusquedasfiltros")
    private Form formulariobusquedasfiltros;

    public List<LkAdminTrabajador> getEmpleados() {
        String consulta="SELECT distinct S9.ID,S2.VALOR TIPODOC,S9.NRODOCUMENTO,S9.NOMBRES,S9.APELLIDOPATERNO,S9.APELLIDOMATERNO,S1.ID CARGOASI "+
                "FROM RSC_CARGOASIGNADO S1 JOIN RSC_LEGAJO S8 ON S8.ID=S1.LEGAJO_ID "+
                "JOIN RSC_TRABAJADOR S9 ON S9.ID=S1.TRABAJADOR_ID "+
                "JOIN RSC_CARGOXUNIDAD S10 ON S10.ID=S1.CARGOXUNIDAD_ID "+   
                "LEFT JOIN RSC_DATOAUXILIAR S2 ON S2.ID = S9.DOCUMENTOIDENTIDAD_ID "+             
                "WHERE S1.ESTADO=1 AND (S8.ENTIDAD_ID='"+_entidadUE.getId()+"')";
        
        //List pri=session.createSQLQuery("select id,apellidopaterno,apellidomaterno,nombres from rsc_trabajador where entidad_id=40").list();
        //persons=(List<Trabajador>)session.createSQLQuery("select id,apellidopaterno from rsc_trabajador where entidad_id=40").list();
//        Criteria c = session.createCriteria(CargoAsignado.class); 
//        c.createAlias("trabajador", "trabajador");
//        c.createAlias("legajo", "legajo");
//        c.createAlias("cargoxunidad", "cargoxunidad");
//        c.add(Restrictions.eq("legajo.entidad", _entidadUE));
//        // busquedasI
        if (apellidoPaterno != null && !apellidoPaterno.equals("")) {
            consulta+=" AND UPPER(S9.APELLIDOPATERNO) LIKE UPPER('"+apellidoPaterno+"')||'%'";

        }
        if (apellidoMaterno != null && !apellidoMaterno.equals("")) {
            consulta+=" AND UPPER(S9.APELLIDOMATERNO) LIKE UPPER('"+apellidoMaterno+"')||'%'";
        }
        if (nombres != null && !nombres.equals("")) {
            consulta+=" AND UPPER(S9.NOMBRES) LIKE UPPER('"+nombres+"')||'%'";
        }

        if (nroDocumento != null && !nroDocumento.equals("")) {
            //System.out.println("------------------ empleados nroDocumento " + nroDocumento);
            consulta+=" AND S9.NRODOCUMENTO='"+nroDocumento+"'";
        }
        
        if (valdocumentoide != null && !valdocumentoide.equals("")) {            
            consulta+=" AND S9.DOCUMENTOIDENTIDAD_ID='"+valdocumentoide.getId()+"'";     
        }
  
        // filtros
        if (sexo != null && !sexo.equals("")) {
            if(sexo.equals("MASCULINO")){
                consulta+=" AND S9.SEXO= 'M'"; 
            }
            else if(sexo.equals("FEMENINO")){                
                consulta+=" AND S9.SEXO= 'F'"; 
            } 
        }
        
        if (valTipoDiscapacidad != null && !valTipoDiscapacidad.equals("")) {
            consulta+=" AND S9.TIPODISCAPACIDAD_ID='"+valTipoDiscapacidad.getId()+"'";
        }
        
        if (valestadocivil != null && !valestadocivil.equals("")) {
            consulta+=" AND S9.ESTADOCIVIL_ID='"+valestadocivil.getId()+"'";
        }
        
        if (valregimenlaboral != null && !valregimenlaboral.equals("")) {
            consulta+=" AND S10.REGIMENLABORAL_ID='"+valregimenlaboral.getId()+"'";
        }
        if (valunidadorganica != null && !valunidadorganica.equals("")) {
            consulta+=" AND S10.UNIDADORGANICA_ID='"+valunidadorganica.getId()+"'";
        }
        
        if (valnivelinstruccion != null && !valnivelinstruccion.equals("")) {
            consulta+=" AND S9.NIVELINSTRUCCION_ID='"+valnivelinstruccion.getId()+"'";
        }
        if (valformacionprofe != null && !valformacionprofe.equals("")) {            
            consulta+=" AND S9.FORMACIONPROFESIONAL_ID='"+valformacionprofe.getId()+"'";        
        }
  

        java.util.Date date = new java.util.Date(); 
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
        String fecha="";
        if (fechadenacimientomayora != null) {
            fecha=sdf.format(fechadenacimientomayora);
            consulta+=" AND S9.FECHANACIMIENTO>=(select to_date('"+fecha+"','dd/MM/yyyy') from dual)"; 
        }
        
        if (fechadenacimientomenora != null) {
            fecha=sdf.format(fechadenacimientomenora);
            consulta+=" AND S9.FECHANACIMIENTO<=(select to_date('"+fecha+"','dd/MM/yyyy') from dual)";
        }
        if (fechadeingresodesdea != null) {
            fecha=sdf.format(fechadeingresodesdea);
            consulta+=" AND S1.FEC_INICIO>(select to_date('"+fecha+"','dd/MM/yyyy') from dual)";
        }
        
        if (fechadeingresohastaa != null) {
            fecha=sdf.format(fechadeingresohastaa);
            consulta+=" AND S1.FEC_INICIO< (select to_date('"+fecha+"','dd/MM/yyyy') from dual)";

        }
        
        consulta+=" ORDER BY(APELLIDOPATERNO)";
//
//        c.add(Restrictions.like("estado", CargoAsignado.ESTADO_ALTA));
//        c.setProjection(Projections.distinct(Projections.property("trabajador")));
//        Trabajador trabajador = new Trabajador();
        //System.out.println("-------------------- trabajador "+trabajador.getNroDocumento());
        Query query = session.createSQLQuery(consulta).addEntity(LkAdminTrabajador.class);
        return query.list();
    }
    private boolean resetBusquedas = false;

    void onSelectedFromBorrarBusquedas() {
        resetBusquedas = true;
        //reseteo el formulario

    }

    Busqueda onActionFromToggle_filtros() {
        if (mostrarFiltros) {
            mostrarFiltros = false;
            mostrarEsconder = "Mostrar";
        } else {
            mostrarFiltros = true;
            mostrarEsconder = "Oscultar";
        }
        return this;
    }

    public Object onSuccess() {
        if (resetBusquedas) {
            limpiar();            
        }
        formulariobusquedasfiltros.clearErrors();
        if (resetBusquedas) {
            resetBusquedas = false;
            //return getTodasZones(); // actualizo toda la pantalla
            return Busqueda.class;
        } else {
            //xxx.recordError(String.valueOf(valformacionprofe));
            return getEmpleadosZones(); // actualizo los trabajadores
        }
    }
    @Inject
    private SelectIdModelFactory selectIdModelFactory;
    @Property
    @SuppressWarnings("unused")
    private SelectModel tipoDocumentosIdModel;

    void onPrepareForRender() {
        // Get all persons - ask business service to find them (from the database)
        List<DatoAuxiliar> datoAuxiliar = Helpers.getValoresTablaAuxiliar("DOCUMENTOIDENTIDAD", session);

        tipoDocumentosIdModel = selectIdModelFactory.create(datoAuxiliar, "valor", "id");
    }

    @CommitAfter
    void onPrepare() {
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTiposDiscapacidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODISCAPACIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);

    }

    /*
     * public List<String> getTiposDoc() { return
     * Helpers.getValorTablaAuxiliar("TipoDocumento", session); }
     */

    /*
     * Para armar la zona dinámica
     */
//    @InjectComponent
//    private Zone cargosGrillaZone;
    @InjectComponent
    private Zone empleadoszone;
    @InjectComponent
    private Zone filtrosZone;

//    Object onToCargo(Trabajador persona) {
//        actual = persona;
//        if (!request.isXHR()) {
//            return this;
//        }
//
//        return cargosGrillaZone.getBody();
//    }

//    @XHR
//    Object onToUpdateCargosGrilla() {
//        if (actual != null) {
//            if (!request.isXHR()) {
//                return this;
//            }
//
//            return cargosGrillaZone.getBody();
//        }
//        return null;
//    }

//    private MultiZoneUpdate getZones() {
//        return new MultiZoneUpdate("cargosGrillaZone", cargosGrillaZone.getBody());
//    }

    private MultiZoneUpdate getEmpleadosZones() {
        return new MultiZoneUpdate("empleadoszone", empleadoszone.getBody());
    }

    private MultiZoneUpdate getTodasZones() {
        return new MultiZoneUpdate("empleadoszone", empleadoszone.getBody()).add("filtrosZone", filtrosZone.getBody());
    }

    public String getSelectionRow() {
        if (actual != null) {
            if (e.getId() == actual.getId()) {
                return "selected";
            }
            return "normal";
        }
        return "";

    }

    void onActivate() {
        if (yaEntrada == false) {
            yaEntrada = true;
            mostrarEsconder = "Ocultar";
            mostrarFiltros = true;
        }
    }

    Object onFailure() {
        return this;
    }
    /*
     * Fin para armar la zona dinámica
     *
     */
    void limpiar(){
        apellidoPaterno = null;
        apellidoMaterno = null;
        nombres = null;
        nroDocumento = null;
//            tipoDocumento = null;
        sexo = null;
        fechadenacimientomenora = null;
        fechadenacimientomayora = null;
        valTipoDiscapacidad = null;
        valestadocivil = null;
        valcodigofuncionaldelcargo = null;
        valRegimenContratacion = null;
        valhorassemanalesmayora = null;
        valhorassemanalesmenora = null;
        checkfechadenacimientomayora = false;
        checkfechadenacimientomenora = false;
        checkhabilitacionprofesional = false;
        checkdeclaracion = false;
        checkconfianza = false;
        valhabilitacionprofesional = false;
        valdeclaracion = false;
        valconfianza = false;
        fechadeingresodesdea=null;
        fechadeingresohastaa=null;
        valregimenlaboral=null;
    }
}