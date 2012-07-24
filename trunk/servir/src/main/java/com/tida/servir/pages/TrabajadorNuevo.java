package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import helpers.Errores;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
import helpers.Logger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;

import org.hibernate.Criteria;
import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.CriteriaSpecification;


@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class TrabajadorNuevo  extends GeneralPage
{
    
    @Inject
    private Session session;
    @Property
    @SessionState
    private Usuario loggedUser;
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    
    //Zonas
    @InjectComponent
    @Property
    private Zone trabajadorNuevoZone;
    @InjectComponent
    @Property
    private Zone unidadOrganicaZone;
    @InjectComponent
    @Property
    private Zone nuevaUnidadOrganicaZone;
    @InjectComponent
    @Property
    private Zone cargosZone;
    @InjectComponent
    @Property
    private Zone nuevoCargosZone;    
    @InjectComponent
    @Property
    private Zone botonesZone;
    @InjectComponent
    private Zone mensajesZone;  
    
    //Formularios
    @Component(id = "formulariotrabajadornuevo")
    private Form formulariotrabajadornuevo;
    @Component(id = "formulariounidadorganica")
    private Form formulariounidadorganica;
    @Component(id = "formularionuevaunidadorganica")
    private Form formularionuevaunidadorganica;
    @Component(id = "formulariocargo")
    private Form formulariocargo;
    @Component(id = "formularionuevocargo")
    private Form formularionuevocargo;    
    @Component(id = "formulariobotones")
    private Form formulariobotones;
    @Component(id = "formulariomensajes")
    private Form formulariomensajes;
    
    //Entidades
    @Property
    @SessionState
    private Entidad oi;
    
    @Property
    @Persist
    private Trabajador nuevo;
    
    @Property
    @Persist
    private UnidadOrganica unidadorganica;
    @Property
    @Persist
    private UnidadOrganica nuevaunidadorganica;
    
    @Property
    @Persist
    private Cargoxunidad cargo;
    @Property
    @Persist
    private Cargoxunidad ncargo;
    
    //Variables
    
    private List<String> tiposDoc = new ArrayList();
    @Property
    @Persist
    private boolean bUOrganica;
    @Persist
    @Property
    private String nuevaUOrganica;
    @Property
    @Persist
    private boolean bCargo;
    @Persist
    @Property
    private String nuevoCargo;
    @Property
    @Persist
    private DatoAuxiliar tipovinculo;
    
    private boolean DNI;
    @Property
    private CargoAsignado cargoAsignado;
    @Property
    @Persist
    private Legajo nuevoLegajo;
    
    
    private CargosSelectModel<Cargoxunidad> _beans;
    @Property
    @Persist
    private Date fechaingreso;

    
    @Property
    @Persist
    private Boolean unidadSeleccionada;
    @Property
    @Persist
    private boolean puestoconfianza; 
    
    @Property
    @Persist
    private Boolean bTrabajadorRegistrado;
    
    @InjectComponent
    private Envelope envelope;
    
    private int elemento=0;
    
    
    //Listado de entidades
    @InjectComponent
    private Zone listaentidadZone;
    @Persist
    @Property
    private LkBusquedaTrabajador listaentidad;

    @PageActivationContext
//    @Persist
    private Trabajador actual;
    @Property
    @Persist
    private Boolean mostrar;
    
    public Trabajador getActual() {
        return actual;
    }

    public void setActual(Trabajador actual) {
        this.actual = actual;
    }

 
    @Log
    @SetupRender
    void initializeValue() {   
        bUOrganica=false;
        bCargo=false;
        nuevo=null;
        unidadorganica=null;
        nuevaunidadorganica=null;
        cargo=null;
        ncargo=null;
        nuevaUOrganica=null;
        nuevoCargo=null;
        tipovinculo=null;
        bTrabajadorRegistrado=false;
        nuevoLegajo=null;
        mostrar=true;
        if(actual!=null){
            nuevo=actual;            
            buscarlegajo();
            mostrar=false;            
        }      
    }
    void buscarlegajo(){
        Criteria c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("trabajador", actual));
        c.add(Restrictions.eq("entidad", oi));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List result = c.list();
        nuevoLegajo=(Legajo) result.get(0);
    }

    
//    public List<String> getTiposDoc() {
//    	Criteria c = session.createCriteria(DatoAuxiliar.class);
//    	c.add(Restrictions.eq("nombreTabla", "DOCUMENTOIDENTIDAD"));
//        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
//    	c.setProjection(Projections.property("valor"));
//        return c.list();
//    }
    @Log
    public GenericSelectModel<DatoAuxiliar> getTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicas(){  
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));        
        c.add(Restrictions.eq("entidad", oi));
        list = c.list();        
        return new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
        
    }
    
    @Log
    public GenericSelectModel<Cargoxunidad> getBeanCargo(){
        List<Cargoxunidad> list;
        Criteria c = session.createCriteria(Cargoxunidad.class);
        
        //c.createAlias("unidadorganica", "unidadorganica"); 
        //c.add(Restrictions.eq("unidadorganica.entidad", oi ));
        if(unidadorganica!=null)c.add(Restrictions.eq("unidadorganica", unidadorganica));
        c.add(Restrictions.ne("estado", Cargoxunidad.ESTADO_BAJA));
        list = c.list();        
        return new GenericSelectModel<Cargoxunidad>(list,Cargoxunidad.class,"den_cargo","id",_access);
    }
    
    void onSelectedFromAgregarUO() {
         elemento=5;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormularionuevaunidadorganica() {
          if(elemento==5){
                nuevaunidadorganica=new UnidadOrganica();
   
                nuevaunidadorganica.setDen_und_organica(nuevaUOrganica);
                nuevaunidadorganica.setNivel(1);
                nuevaunidadorganica.setEntidad(oi);
                nuevaunidadorganica.setEstado(UnidadOrganica.ESTADO_ALTA);
                session.saveOrUpdate(nuevaunidadorganica);
                envelope.setContents(helpers.Constantes.EUE_EXITO);
                envelope.setContents("Se creo la Unidad Organica con éxito.");
                elemento=0;
                bUOrganica=false;
          }else{
            if(bUOrganica){
                bUOrganica=false;
            }else{
                bUOrganica = true;
            }
          }

          return new MultiZoneUpdate("nuevaUnidadOrganicaZone", nuevaUnidadOrganicaZone.getBody())
                .add("mensajesZone", mensajesZone.getBody())
                .add("unidadOrganicaZone", unidadOrganicaZone.getBody());
    }
    
    void onSelectedFromAgregarCargo() {
         elemento=6;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariounidadorganica() {
          return new MultiZoneUpdate("nuevaUnidadOrganicaZone", nuevaUnidadOrganicaZone.getBody())
                .add("cargosZone", cargosZone.getBody());

    }
    
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormularionuevocargo() {
        if(elemento==6){
                ncargo=new Cargoxunidad();
                ncargo.setDen_cargo(nuevoCargo);
                ncargo.setCod_cargo("C9999");
                ncargo.setUnidadorganica(unidadorganica);
                ncargo.setEstado(UnidadOrganica.ESTADO_ALTA);
                session.saveOrUpdate(ncargo);
                
                envelope.setContents(helpers.Constantes.EUE_EXITO);
                envelope.setContents("Se creo el Cargo con éxito.");
                elemento=0;
                bCargo=false;
          }else{    
                if(bCargo){
                    bCargo=false;
                }else{
                    bCargo = true;
                }
            }

          return new MultiZoneUpdate("nuevoCargosZone", nuevoCargosZone.getBody())
                .add("mensajesZone", mensajesZone.getBody())
                .add("cargosZone", cargosZone.getBody());

    }
    
    @Log
    void onPrepareFromformulariotrabajadornuevo()
    {
        if(nuevo == null) {
            nuevo = new Trabajador();
        }
          System.out.println("entro y gravo "+fechaingreso);
        System.out.println("entro y gravo2 "+puestoconfianza);
        //trabajadorTieneCargoOtraEntidad = false;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariotrabajadornuevo()
    {
        System.out.println("entro y gravo "+fechaingreso);
        System.out.println("entro y gravo2 "+puestoconfianza);
        return trabajadorNuevoZone.getBody();
    }
         
    public boolean getDNI(){
        if(nuevo.getTipodiscapacidad().equals("DNI"))
            return true;
        
        return false;
    }

    
    //para obtener datos del tipo de vinculo
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoVinculo() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVÍNCULO", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() throws ParseException {
        
        if(elemento==1){
            return "TrabajadorNuevo";
        }else if(elemento==2){
            return "Alerta";
        }else{
            if(getListadoEntidades().size()>0 && actual==null){
                 envelope.setContents(helpers.Constantes.EUE_EXITO);
                 envelope.setContents("El trabajador ya se encuentra de alta en otra entidad.");
                 bTrabajadorRegistrado=true;
                 return new MultiZoneUpdate("listaentidadZone", listaentidadZone.getBody())
                .add("mensajesZone", mensajesZone.getBody());
            }else{
                //Guardar Cargo Asignado 
                cargoAsignado = new CargoAsignado();                 
                if(actual!=null){
                    cargoAsignado.setTrabajador(nuevo);
                    cargoAsignado.setLegajo(nuevoLegajo);
                }else{
                    //Guardar Trabajador
                    nuevo.setEntidad(oi);  
                    session.saveOrUpdate(nuevo);
                    //Guardar Legajo
                    nuevoLegajo = new Legajo();
                    nuevoLegajo.setEntidad(oi);
                    nuevoLegajo.setTrabajador(nuevo);
                    nuevoLegajo.setCod_legajo("L9999");
                    session.saveOrUpdate(nuevoLegajo);
                    cargoAsignado.setTrabajador(nuevo);
                    cargoAsignado.setLegajo(nuevoLegajo);
                }                            
                  cargoAsignado.setEstado(Constantes.ESTADO_ACTIVO);            
                  cargoAsignado.setFec_inicio(fechaingreso);
                  cargoAsignado.setTipovinculo(tipovinculo);
                  cargoAsignado.setCargoxunidad(cargo);                
                  cargoAsignado.setPuestoconfianza(puestoconfianza);
                  session.saveOrUpdate(cargoAsignado);
                  envelope.setContents(helpers.Constantes.EUE_EXITO);
                  envelope.setContents("Alta del trabajador se realizo satisfactoriamente.");
                  actual=null;
                  return Busqueda.class;
            }
        }        
    }
    
    
    @Log
    public List<Evento> getListadoEntidades() {
        Criteria c = session.createCriteria(LkBusquedaTrabajador.class);
        c.add(Restrictions.eq("nrodocumento",nuevo.getNroDocumento()));                     
        return c.list();
    }
    
     void onDNIChanged() {
        nuevo.setNroDocumento(_request.getParameter("param"));
    }
     
     void onNombreChanged() {
        nuevo.setNombres(_request.getParameter("param"));
    }
     void onApePatChanged() {
       nuevo.setApellidoPaterno(_request.getParameter("param"));
    }
     void onApeMatChanged() {
       nuevo.setApellidoMaterno(_request.getParameter("param"));
    }
     void onFechaIngresoChanged() {
       System.out.println("entro y gravo 333 "+_request.getParameter("param"));
     
    }
      
 
     
}
