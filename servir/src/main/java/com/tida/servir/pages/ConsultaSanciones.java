/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import Batch.Helpers.GeneracionXLS;
import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Response;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Miller Cribillero
 * @date 17/09/2012 18:00
 * 
 */
public class ConsultaSanciones extends GeneralPage {
    @Inject
    private Session session;
    @SessionState
    private Usuario loggedUser;
    @InjectComponent
    private Envelope envelope;
    
    @Component(id = "formmensaje")
    private Form formmensaje;
//    @Component(id = "formulariosatosSancion")
//    private Form formulariosatosSancion;
    @InjectComponent
    @Property
    private Zone mensajeZone;
    @SessionState
    @Property
    private UsuarioTrabajador usuarioTrabajador;
    @SessionState
    @Property
    private UsuarioAcceso usua;
    @InjectComponent
    @Property
    private Zone consultaSancionesZone;
    @InjectComponent
    @Property
    private Zone tiposancionZone;
    @InjectComponent
    @Property
    private Zone busZone2;
    @Property
    @SessionState
    private Entidad entidadUE;
    @Persist
    @Property
    private String entidad_origen; 
    @Property
    @Persist
    private Trabajador nuevo;
    @Inject
    private PropertyAccess _access;
    @InjectComponent
    @Property
    private Zone busZone;
    @InjectComponent
    @Property
    private Zone listaConsultaSancionZone;
    @Persist
    @Property
    private String bnombres;
    @Persist
    @Property
    private String bapellidoPaterno;
    @Persist
    @Property
    private String bapellidoMaterno;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad2;
    @Persist
    @Property
    private DatoAuxiliar bdocumentoidentidad_not;
    @Persist
    @Property
    private String bnumeroDocumento;
    @Persist
    @Property
    private String bnumeroDocumento2;
    @Persist
    @Property
    private String bnumeroDocumento_not;
    @Persist
    @Property
    private String juzgado_not;
    @Persist
    @Property
    private String observaciones;
    @Property
    @SessionState
    private Usuario _usuario;   
    @Persist
    @Property
    private String bdenoentidad;
    @Persist
    @Property
    private Long entidad_origen_id;
    @Persist
    @Property
    private Boolean esVigente;
    @Persist
    @Property
    private Boolean esNoVigente;
    @Persist
    @Property
    private Boolean esHistorica;
    @Persist
    @Property
    private Boolean esAnulada;
    @Persist
    @Property
    private Boolean esSuspendida;
    @Persist
    @Property
    private Boolean esTrabajador;
    @Persist
    @Property
    private Boolean mostrartodo;
    @Persist
    @Property
    private Boolean bmostrar;
    @Persist
    @Property
    private Boolean mostrar_reglab;
    @Persist
    @Property
    private Boolean v_editar;
    @Persist
    @Property
    private Boolean entidadsubentidad;
    
    @Property
    @Persist
    private DatoAuxiliar bregimenLaboral;
    @Persist
    @Property
    private DatoAuxiliar bcategoriaSancion;
    @Persist
    @Property
    private TipoSancion btipoSancion;
          
    @Property
    @Persist
    private LkBusquedaEntidad entio;
    @Property
    @Persist
    private LkBusquedaSancion cs;
    
    @Property
    @Persist
    private LkBusquedaSancion cs_sinreglab;
    
    @Component(id = "formconsultaSancion")
    private Form formconsultaSancion;
    @Component(id = "formularioconsultasanciones")
    private Form formularioconsultasanciones;
    @Component(id = "formularioanularsancion")
    private Form formularioanularsancion;
  
    @Property
    @Persist
    private Entidad entidad2;  
    @Property
    @Persist
    private Anulacion anulacion;  
    private int elemento=0;
    private int anular=0;
    
    @Property
    @Persist
    private String errorBorrar;
    
    @Property
    @Persist
    private Sancion sancion;
    @Property
    @Persist
    private String fechadoc;
    @Property
    @Persist
    private String fechadocnot;
    @Property
    @Persist
    private String nro_sanciones;
    @Property
    @Persist
    private String nro_sanciones_sinreglab;
    @Inject  
    private ComponentResources _resources;

    @Property
    @Persist
    private Boolean vexportar;
    
    @Log
    @SetupRender
    void initializeValue() {
        STARTPATH=getRuta().get(0).getRuta_final();
        vexportar=false;
        Query query = session.getNamedQuery("callSpUsuarioAccesoPagina");
        query.setParameter("in_login", _usuario.getLogin());
        query.setParameter("in_pagename", _resources.getPageName().toUpperCase());
//        query.setParameter("in_pagename", "TIPOSANCION");
        List result = query.list();
        
        
        if (result.isEmpty()) {
            System.out.println(String.valueOf("Vacio:"));
        } else {
           usua = (UsuarioAcceso) result.get(0);
         }
        nuevo = new Trabajador();
        entidad_origen= "";
        entidad_origen_id=null;
        bapellidoPaterno="";
        bapellidoMaterno="";
        bnombres="";
        bnumeroDocumento="";
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
//        btipoSancion = null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false;
        mostrar_reglab=false;
        
        if(loggedUser.getRolid()==2){ //Administrador de Entidad
             entidadsubentidad=true;
        }else{
            entidadsubentidad=false;
        }
            
        if(loggedUser.getRolid()==3){ //administrador de Servir
            bmostrar=true;
        }else{
            bmostrar=false;
        }  
                
        if(usua.getAccesoupdate() == 1){ 
             v_editar = true;
        }else{
             v_editar = false;
        }
        mostrarLista = Boolean.FALSE;
                
    }
    
    @Log
    public List<ConfiguracionAcceso> getRuta() {
        Criteria c = session.createCriteria(ConfiguracionAcceso.class);        
        return c.list();
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<Long> lista = new ArrayList<Long>();
        lista.add(new Long(1));lista.add(new Long(2));
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla","DOCUMENTOIDENTIDAD"));
        c.add(Restrictions.in("codigo", lista));
        return new GenericSelectModel<DatoAuxiliar>(c.list(), DatoAuxiliar.class, "valor", "id", _access);
    }
    
      @Log
      public GenericSelectModel<DatoAuxiliar> getRegimenLaboral() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
      public GenericSelectModel<DatoAuxiliar> getCategoriaSancion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIASANCION", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
      
      @Log
    @SuppressWarnings("unchecked")
      public GenericSelectModel<TipoSancion> getTipoSancion() {
        List<Lk_Tipo_Sancion> list;
        Criteria c1 = session.createCriteria(TipoSancion.class);
        
        if (bregimenLaboral==null&&bcategoriaSancion==null){
        return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);        
        }
        
        Criteria c = session.createCriteria(Lk_Tipo_Sancion.class); 
        if(bregimenLaboral!=null){
            c.add(Restrictions.eq("reg_laboral", bregimenLaboral.getId()));
        }
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria", bcategoriaSancion.getId()));
        }
        c.setProjection(Projections.distinct(Projections.property("id_tipo")));
       
        if (!c.list().isEmpty()){            
        c1.add(Restrictions.in("id", c.list()));
        return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);
        
        }
        else{
        
        c1.add(Restrictions.isNull("id"));}
        
        list = c.list();
        return new GenericSelectModel<TipoSancion>(c1.list(), TipoSancion.class, "descripcion", "id", _access);
    }
@Persist
private Boolean mostrarLista;

    @Log
    public List<LkBusquedaSancion> getBusquedaSancionadosSinRegLab(){
        Criteria c;

        if  (mostrarLista == Boolean.FALSE){
            System.out.println("FALSEX");
        nro_sanciones_sinreglab = "0";
        listaSancion = new ArrayList<LkBusquedaSancion>();
        return listaSancion;
        }
        
        c = session.createCriteria(LkBusquedaSancion.class);
        if (loggedUser.getRolid()==2){ // adm entidad
            c.add(Restrictions.eq("entidad_id", loggedUser.getEntidad().getId()));
        }
        else if(loggedUser.getRolid()==3){ // adm servir
        if(entidad_origen_id!=null){
            c.add(Restrictions.eq("entidad_id",entidad_origen_id));
        }        
        }
        
        if(bnombres!=null){
             c.add(Restrictions.or(Restrictions.like("nombres_trabajador","%"+bnombres+"%").ignoreCase(),Restrictions.like("nombres_persona","%"+bnombres+"%").ignoreCase()));
        }
        
        if(bapellidoPaterno!=null){
            c.add(Restrictions.or(Restrictions.like("apellidopat_trabajador","%"+bapellidoPaterno+"%").ignoreCase(),Restrictions.like("apellidopat_persona","%"+bapellidoPaterno+"%").ignoreCase()));
        }else if (bapellidoMaterno!=null){
            c.add(Restrictions.or(Restrictions.like("apellidomat_trabajador","%"+bapellidoMaterno+"%").ignoreCase(),Restrictions.like("apellidomat_persona","%"+bapellidoMaterno+"%").ignoreCase()));
        }
        
        if(bdocumentoidentidad !=null){
            c.add(Restrictions.or(Restrictions.eq("tipo_doc_persona",bdocumentoidentidad.getId()),Restrictions.eq("tipo_doc_trabajador",bdocumentoidentidad.getId())));
        }
        if(bnumeroDocumento != null){
            c.add(Restrictions.or(Restrictions.like("nro_doc_persona","%"+bnumeroDocumento+"%").ignoreCase(),Restrictions.eq("nro_doc_trabajador","%"+bnumeroDocumento+"%").ignoreCase()));
        }


        List<String> estados = new ArrayList<String>();
        
        if(esSuspendida==true){  estados.add("3");}
        if(esAnulada==true){     estados.add("4");}    
        if(esHistorica==true){   estados.add("5");}
        if(esNoVigente==true){   estados.add("2");}
        if(esVigente==true){     estados.add("1");}

        if (!estados.isEmpty()){
        c.add(Restrictions.in("estado_id", estados));
        }
        
        if(bregimenLaboral!=null){
            c.add(Restrictions.eq("id_reg_laboral", bregimenLaboral.getId()));
        }
        
        if(bcategoriaSancion!=null){
            c.add(Restrictions.eq("categoria_sancion_id", bcategoriaSancion.getId()));
        }
        
        if(btipoSancion!=null){
            c.add(Restrictions.eq("id_tipo_sancion", btipoSancion.getId()));
        }

        nro_sanciones_sinreglab = Integer.toString(c.list().size());

        listaSancion = c.list();

        return listaSancion;     
    }

    @Property
    @Persist
    private List<LkBusquedaSancion> listaSancion;
    
    
     Object onBuscarpersona(){
         return new MultiZoneUpdate("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone",busZone.getBody());
//          return consultaSancionesZone.getBody();
     }
     
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        return busZone.getBody();
    }
    
     @Log
    public List<Entidad> getEntidades() {
        Criteria c = session.createCriteria(LkBusquedaEntidad.class);
        if (bdenoentidad != null && !bdenoentidad.equals("")) {
            c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", "%" + bdenoentidad + "%").ignoreCase()));
        }        
        return c.list();
    }
      
    @Log
    Object onValueChangedFromCategoriaSancion(DatoAuxiliar dato) {
        return tiposancionZone.getBody();
    }     
    
    @Log
    Object onValueChangedFromRegimenLaboral(DatoAuxiliar dato) {
        return tiposancionZone.getBody();
    }     
    
    @Persist
    private String STARTPATH;
    
    @Property
    @Persist
    private String archivoDescargar;
    private List<String> errores = new LinkedList<String>();
    @Persist
    @Property
    private Boolean bmostrarexportar;
    
    @Log
    @CommitAfter
    Object onSuccessFromFormularioConsultaSanciones() {
        vexportar=false;
        if(elemento == 1)   {
             if(bregimenLaboral!=null){
                 mostrar_reglab=true;
 
             }else{
                 mostrar_reglab=false;
              
             }
             bmostrarexportar=true;
            return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("exportarZone",exportarZone.getBody());
        } else if (elemento == 2){
                mostrar_reglab=false;
//            cs = new LkBusquedaSancionados();
            return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("exportarZone",exportarZone.getBody());
        } 
        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
         // return listaConsultaSancionZone.getBody();
    }
    
    StreamResponse onActionFromReturnStreamResponse() {
                
		return new StreamResponse() {
			InputStream inputStream;

                    @Override
                    public void prepareResponse(Response response) {
                            File fileADescargar = new File(archivoDescargar);

                            try {
                                inputStream = new FileInputStream(fileADescargar);
                            } catch (FileNotFoundException ex){
                                java.util.logging.Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {
                                response.setHeader("Content-Type", "application/x-zip");
                                response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                response.setHeader("Content-Length", "" + inputStream.available());
                            }
                            catch (IOException e) {
                                java.util.logging.Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
                            }
                    }

                    @Override
                    public String getContentType() {
                            return "application/x-zip";
                    }

                    @Override
                    public InputStream getStream() throws IOException {
                            return inputStream;
                    }

            };
    }
    
    @Log
    void onSelectedFromBuscarSubmit() {
        mostrarLista = Boolean.TRUE;
        elemento = 1;
    }
    
    @Log
    void onSelectedFromMuestra() {
        mostrarLista = Boolean.TRUE;
        elemento = 2;
        entidad_origen= null;
        entidad_origen_id=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bnombres=null;
        bnumeroDocumento=null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false;
        
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
        btipoSancion = null;
    }
    
   @InjectComponent
   @Property
    private Zone exportarZone;
   
    Object onExportar() {
        vexportar=true;
         mostrar_reglab=false;              
              //Exportando en Excel
              GeneracionXLS geXLS=new GeneracionXLS();                 
              archivoDescargar = STARTPATH  +"CONSULTASANCIONES.xls";  
                File f = new File(STARTPATH);
                if (!f.exists()) {
                    f.mkdirs();
                }
                File fa = new File(STARTPATH+"CONSULTASANCIONES.xls");
                if (!fa.exists()) {
                    fa.delete();
                }
//                    errores=geXLS.generadoXLSConsultaSancionadosSinRegLab(getBusquedaSancionadosSinRegLab(), STARTPATH+"CONSULTASANCIONES.xls", session);
                    errores=geXLS.generadoXLSConsultaSancionadosSinRegLab(listaSancion, STARTPATH+"CONSULTASANCIONES.xls", session);
                    System.out.println("SIN REG");
              //  }
                
            return new MultiZoneUpdate("exportarZone", exportarZone.getBody());  
//            return new MultiZoneUpdate("consultaSancionesZone",consultaSancionesZone.getBody()).add("listaConsultaSancionZone",listaConsultaSancionZone.getBody());
    }

    
    @Log
    Object onLimpiar() {    
        entidad_origen= null;
        entidad_origen_id=null;
        bapellidoPaterno=null;
        bapellidoMaterno=null;
        bnombres=null;
        bnumeroDocumento=null;
        esSuspendida=false;
        esAnulada=false;
        esHistorica=false;
        esNoVigente=false;
        esVigente=false; 
        bdocumentoidentidad = null;
        bregimenLaboral = null;
        bcategoriaSancion = null;
        btipoSancion = null;
        bmostrarexportar = Boolean.FALSE;
        mostrarLista = Boolean.FALSE;       
        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("exportarZone",exportarZone.getBody());
    }
    
     
    @Log
    Object onActionFromSeleccionar(Entidad enti2) {        
        entidad2 = enti2;
        entidad_origen=entidad2.getDenominacion();
        entidad_origen_id = entidad2.getId();
        return consultaSancionesZone.getBody();  
    }
    
//    @Log
//    Object onActionFromAnular(LkBusquedaSancionados cs) {        
//        
//         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
//                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone2",busZone2.getBody());  
//    }
//    @Log
//    Object onActionFromAnular_SinRegLab(LkBusquedaSancionadosSinRegLab cs_sinreglab) {        
//        
//         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
//                  .add("consultaSancionesZone",consultaSancionesZone.getBody()).add("busZone2",busZone2.getBody());  
//    }
    
    @Log
    Object onActionFromCancel1() {        

         return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
    }
        
    @Log
    @CommitAfter
    Object onSuccessFromFormularioAnularSancion(){
        if(anular==1){
            return busZone2.getBody();
        }
        return busZone2.getBody();
    }
            
    @Log
    @CommitAfter
    Object onBorrarDato(UnidadOrganica dato) {
        errorBorrar = null;
        new Logger().loguearOperacion(session, loggedUser, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
        dato.setEstado(UnidadOrganica.ESTADO_BAJA);
        session.saveOrUpdate(dato);
        envelope.setContents("Sancion Eliminada");

        return new MultiZoneUpdate("listaConsultaSancionZone", listaConsultaSancionZone.getBody())
                  .add("consultaSancionesZone",consultaSancionesZone.getBody());
    }
    
    @Log
    Object onActionFromSave(Anulacion anulacion) {        
        
        
        return new MultiZoneUpdate("consultaSancionesZone",consultaSancionesZone.getBody()); 
    }
   
    public Boolean getSancionAnulada(){
        System.out.println("EST5ADOX  "+cs.getEstado_id());
        if (Integer.valueOf(cs.getEstado_id())==4){
        return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    public Boolean getSancionAnulada_sinregla(){
        System.out.println("ANULADAX "+cs_sinreglab.getEstado_id());
        System.out.println("ANULADAX "+cs_sinreglab.getVeditar_anular());
        
        if (cs_sinreglab.getEstado_id().equals("4")){
        return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
  @Log  
  StreamResponse onVerReporteSancion(Long idSancion){
  ReportesFormulario repSancion = new ReportesFormulario();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("MandatoryParameter_SancionID", idSancion);
        try {  
            return repSancion.callReporteSanciones("S1",parametros, session);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
  }
  
  
  
  
}
