package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.pages.Busqueda;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class ConstanciasDocumentalesEditor {
//Parameters of component

    @Inject
    private Session session;
    @Parameter
    @Property
    private Trabajador actual;
    @Property
    @SessionState
    private Entidad _oi;
    @InjectComponent
    private Envelope envelope;
    @Persist
    @Property
    private ConstanciaDocumental constancia;
    @Persist
    @Property
    private ConstanciaDocumental documento;
    @Property
    @SessionState
    private Usuario _usuario;
    @Inject
    private PropertyAccess _access;
    @Property
    @SessionState
    private UsuarioAcceso usua;
    //zonas
    @InjectComponent
    private Zone listaDocumentosZone;
    @InjectComponent
    @Property
    private Zone tercerZone;
    @InjectComponent
    @Property
    private Zone mensajesZone;
    @Component(id = "formularioMensajes")
    private Form formularioMensajes;
    @Inject
    private Request _request;
    @Persist
    @Property
    private DatoAuxiliar valcategoriaconstancia;
    @Persist
    @Property
    private DatoAuxiliar valtipoconstancia;
//    @Persist
//    @Property
//    private DatoAuxiliar valcargoasignado;
    @Property
    @Persist
    private String valobligatorio;
    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;
    @Persist
    @Property
    private Boolean valentregado;
    @Persist
    @Property
    private Boolean opcionADM;
    //validaciones
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean editando;
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean vbotones;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vrevisado;
    @Persist
    @Property
    private Boolean vNoedita;
    private int elemento = 0;
    @Persist
    @Property
    private CargoAsignado cargoasignado;
    @Persist
    @Property
    private Legajo lega;
    @Log
    public List<ConstanciaDocumental> getListadoDocumentos() {
        Criteria c2 = session.createCriteria(ConstanciaDocumental.class);
        c2.add(Restrictions.eq("cargoasignado",getCargosAsignados()));
        c2.add(Restrictions.eq("legajo",buscarlegajo()));
        c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        nroregistros = Integer.toString(c2.list().size());    
        return c2.list();
    }
    
    @Persist
    @Property
    private String nroregistros;
        
    @Log
    public Legajo buscarlegajo(){
         Criteria c1 = session.createCriteria(Legajo.class);  
         c1.add(Restrictions.eq("trabajador", actual));
         c1.add(Restrictions.eq("entidad", _oi));
         List result = c1.list();
         lega=(Legajo) result.get(0);
         return lega;
         
   }
    @Log
   public CargoAsignado getCargosAsignados() {
       Criteria c3 = session.createCriteria(CargoAsignado.class);
         c3.add(Restrictions.eq("trabajador", actual));
         List result2 = c3.list();
         cargoasignado=(CargoAsignado) result2.get(0);
         
       return cargoasignado;
   }

    @InjectComponent
    private Zone primerZone;

    @Inject
    private Request request;
    
       @Log
    //para obtener datatos del Nivel Gobierno
    public GenericSelectModel<DatoAuxiliar> getBeanCategoria() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIACONSTANCIA", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    @Log
    //para obtener datatos de la Organizacion
    public GenericSelectModel<DatoAuxiliar> getBeanTipoDocumento(){
        List<DatoAuxiliar> list = new ArrayList<DatoAuxiliar>();

        if (valcategoriaconstancia != null) 
        { 
            list = Helpers.getDatoAuxiliar("DATOCONSTANCIA", "CATEGORIACONSTANCIA", valcategoriaconstancia.getCodigo(), session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    Object onValueChangedFromvalcategoriaconstancia(DatoAuxiliar dato) {
     //   valcategoriaconstancia = dato;
     //** if (dato != null){valcategoriaconstancia = dato;}   
        return request.isXHR() ? new MultiZoneUpdate("primerZone", primerZone.getBody()) : null;
    }

    @Log
    Object onValueChangedFromvaltipoconstancia(DatoAuxiliar dato) {
     //   if (dato != null) {valtipoconstancia = dato;}
        return request.isXHR() ? new MultiZoneUpdate("primerZone", primerZone.getBody()) : null;
    }

//    @Log
//    Object onValueChangedFromBeanCategoria(DatoAuxiliar dato) {
//   //     if (dato == null) {
//    //       valtipoconstancia =  null;
//   //     }
//        return request.isXHR() ? new MultiZoneUpdate("primerZone", primerZone.getBody()) : null;
//    }

    @Log
    Object onSuccessFromFormularioDocumento()
    {
        return primerZone.getBody();               
    }
    
    @Log
    void onSelectedFromReset() {            
        elemento = 2;
        if(vdetalle){
            vformulario = false;
            vNoedita=false;
            if (usua.getAccesoreport() == 1) {
                vformulario=true;
                vdetalle=false;
                vbotones=true;
                limpiar();
                formularioMensajes.clearErrors();
                editando = false;
                constancia = new ConstanciaDocumental();
                vNoedita=true;
            }
        }
        else{
            if (usua.getAccesoreport() == 0) {
                vformulario=false;
                vdetalle=false;
                vbotones=false;
                vNoedita=false;
            }
            else{
                limpiar();
                formularioMensajes.clearErrors();
                editando = false;
                constancia = new ConstanciaDocumental();  
            }            
        }
    }

    @Log
    void onSelectedFromSave() {
        elemento = 1;
    }

    @Log
    void onSelectedFromCancel() {
        elemento = 3;
    }


    
    @Log
    @CommitAfter
    Object onSuccessFromformulariobotones() throws ParseException {     
   this.seguimiento();
        if (valfec_desde != null) 
        {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_desde = (Date) formatoDelTexto.parse(valfec_desde);
            } catch (ParseException ex) {}
        }
        
        
        if (elemento == 3) 
        {
            if (_usuario.getRolid() == 1) {
                return "TrabajadorPersonal";
            } else {
                return Busqueda.class;
            }
        } 
        else if (elemento == 2) 
        {
            return new MultiZoneUpdate("tercerZone", tercerZone.getBody()).
                    add("mensajesZone", mensajesZone.getBody()).add("primerZone",primerZone.getBody());
        } 
        else if (elemento == 1) 
        {
            formularioMensajes.clearErrors();
            Logger logger = new Logger();
            if (fecha_desde == null) 
            {
                formularioMensajes.recordError("Tiene que ingresar Fecha de Inicio");
                return mensajesZone.getBody();
            }
            if (fecha_desde.after(new Date())) 
            {
                formularioMensajes.recordError("La fecha debe ser previa a la fecha actual.");
                return mensajesZone.getBody();
            }
            if (valtipoconstancia == null)
            {
                formularioMensajes.recordError("Tiene que ingresar el tipo de documento.");
                return mensajesZone.getBody();                
            }
            if (valcategoriaconstancia == null)
            {
                formularioMensajes.recordError("Tiene que ingresar la categoria al cual pertenece el documento.");
                return mensajesZone.getBody();                
            }
            if (editando) 
            {
                //editando
                if (usua.getAccesoreport() == 0) 
                {
                    vformulario = false;
                    vbotones=false;
                    vNoedita=false;
                }
            } 
            else 
            {//guardando
              
                constancia = new ConstanciaDocumental();
            }
            if (vrevisado == true) 
            {
                if (valentregado == null) 
                {
                    constancia.setEntrego(false);
                } 
                else 
                {
                    constancia.setEntrego(valentregado);
                }

            }
            seteo();
            if(!editando)
            {
            constancia.setLegajo(buscarlegajo());
            constancia.setCargoasignado(getCargosAsignados());
            }
            
            session.saveOrUpdate(constancia);
            session.flush();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(constancia.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_DOCUMENTO);    

     // INICIO acciones de auditoria        
            if (!editando) 
            {   
                logger.loguearEvento(session, Logger.MODIFICACION_DOCUMENTOS, actual.getEntidad().getId(), actual.getId(), _usuario.getId(), Logger.MOTIVO_DOCUMENTOS_DOCUMENTOS, constancia.getId());
           //     logger.loguearEvento(session, Logger.MODIFICACION_DOCUMENTOS, actual.getEntidad().getId(),actual.getId(), Logger.MOTIVO_DOCUMENTOS_DOCUMENTOS, constancia.getId());
            }
            
            if (valentregado != null) 
            {
                if (valentregado == true) 
                {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + constancia.getCargoasignado().getTrabajador().getId() + "' and tipoevento_id='" + Logger.MODIFICACION_DOCUMENTOS + "' and tabla_id='" + constancia.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }
     // FIN acciones de auditoria       
            editando = false;
            limpiar();
            envelope.setContents("Documento creado / modificado con exito");
        }

        return new MultiZoneUpdate("tercerZone", tercerZone.getBody()).
                add("mensajesZone", mensajesZone.getBody()).
                add("listaDocumentosZone", listaDocumentosZone.getBody()).add("primerZone", primerZone.getBody());
    }

    @Log
    Object onActionFromEditar(ConstanciaDocumental cons) {
        constancia = cons;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
      //  vNoedita=true;
        mostrar();
        return actualizarZonas();
    }

    @Log
    Object onActionFromDetalle(ConstanciaDocumental cons) {
        constancia = cons;
        mostrar();
        vdetalle = true;
        vbotones = false;
  //cambios:
            if (usua.getAccesoupdate() !=1){}
            else{vNoedita=true;}
  //---------          
  //      vNoedita=true;
        vformulario = true;
        return actualizarZonas();
    }

    @Log
    Object onActionFromDetalles(ConstanciaDocumental cons) {
        constancia = cons;
        mostrar();
        vdetalle = true;
        vbotones = false;
  //cambios:
            if (usua.getAccesoreport() != 1){vNoedita=false;}
            else{vNoedita=true;}
  //---------  
  //          vNoedita=true;
        vformulario = true;
        return actualizarZonas();
    }

    @Log
    @CommitAfter
    Object onBorrarDato(ConstanciaDocumental dato) {
        new Logger().loguearOperacion(session, _usuario, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_DOCUMENTO);        
        Query query = session.createSQLQuery("DELETE FROM RSC_CONSTANCIADOCUMENTAL WHERE ID = '"+dato.getId()+"'");
        int resultado = query.executeUpdate();
        session.flush();
        envelope.setContents("Documento del Trabajador Eliminado");
        return new MultiZoneUpdate("tercerZone", tercerZone.getBody()).
                add("mensajesZone", mensajesZone.getBody()).
                add("listaDocumentosZone", listaDocumentosZone.getBody()).add("primerZone", primerZone.getBody());
    }

    @Log
//    @SetupRender
    void setupRender() {
        vrevisado = false;
        vdetalle=false;
        vformulario=false;
        vbotones=false;
        vNoedita=false;
        opcionADM=false;
        if (usua.getAccesoupdate() == 1) 
        {
            veditar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) 
            {
                vrevisado = true;
            }
        }
        if (usua.getAccesodelete() == 1) 
        {
            veliminar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) 
            {
                vrevisado = true;
            }
        }
        if (usua.getAccesoreport() == 1) 
        {
            vformulario = true;
            vbotones = true;
  //cambios:
 //           if (usua.getAccesoupdate() !=1){}
 //           else{vNoedita=true;}
  //---------          
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) 
            {
                vrevisado = true;
            }
  //cambios          
            if (usua.getAccesodelete() != 1)
            {veliminar = false;}
  //--------          
        }
        if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) 
        {
          opcionADM = true;
        }
 //--------       
        if (vformulario == true){vNoedita=true;}
        
        editando = false;
        limpiar();
    }

    @Log
    MultiZoneUpdate actualizarZonas(){
        return new MultiZoneUpdate("tercerZone", tercerZone.getBody()).
                add("listaDocumentosZone", listaDocumentosZone.getBody()).add("primerZone", primerZone.getBody());
    }
    
    @Log
    void seteo() throws ParseException {
        this.seguimiento();
        constancia.setCategoriaconstancia(valcategoriaconstancia);
        constancia.setTipoconstancia(valtipoconstancia);
        constancia.setFecha(convertirFecha(valfec_desde));
        constancia.setObligatorio(esObligatorio(valobligatorio));
        constancia.setEntrego(valentregado);
//      constancia.setCargoasignado(valcargoasignado);
    }
    
    void seguimiento()
    {
        System.out.println("***CDE: "+valcategoriaconstancia);
        System.out.println("***CDE: "+valtipoconstancia);    
        System.out.println("***CDE: "+valfec_desde);
        System.out.println("***CDE: "+valobligatorio);
        System.out.println("***CDE: "+valentregado);
    }
    
    @Log
    void mostrar() {
        valcategoriaconstancia = constancia.getCategoriaconstancia();
        valtipoconstancia = constancia.getTipoconstancia();
        valfec_desde= mostrarFecha(constancia.getFecha());
        valobligatorio = valorObligatorio(constancia.getObligatorio());
        valentregado = constancia.getEntrego();
   //   valcargoasignado = constancia.getCargoasignado();
    }
 
    @Log
    String mostrarFecha(Date fecha){
      String resultado;
      SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
      resultado = formatoDeFecha.format(fecha);
      return resultado;
    }
 
    @Log
    Date convertirFecha(String fecha) throws ParseException{
      Date resultado;
      SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
      resultado = (Date) formatoDelTexto.parse(fecha);
      return  resultado;   
   }
   
    @Log
    Boolean esObligatorio(String opcion){
        
        if (opcion == null || "".equals(opcion)){opcion = "X";}
        
        if(opcion.equalsIgnoreCase("SI"))
        {
         return Boolean.TRUE;
        }
        else if(opcion.equalsIgnoreCase("NO"))
        {
            return Boolean.FALSE;
        }
        else
        {
            return Boolean.FALSE;
        }

    }        

    @Log
    String valorObligatorio(Boolean opcion){
       if(opcion==true)
       { 
            return "SI";
        }
       else if(opcion==false)
       {
            return "NO";
        }
       else
       {
            return "";
        }   
    }
    
    @Log
    void limpiar() {
        this.seguimiento();
       constancia = new ConstanciaDocumental();
//     documento = new ConstanciaDocumental();
       valcategoriaconstancia = null;
       valtipoconstancia = null;
       valfec_desde = "";
       valentregado = false;
       valobligatorio = "";
//     valcargoasignado = null;
    }
}