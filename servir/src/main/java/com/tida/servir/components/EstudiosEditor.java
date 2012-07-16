package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;

import helpers.Logger;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.tida.servir.pages.Busqueda;


/**
 *
 * @author ale
 */
public class EstudiosEditor {
//Parameters of component

    @Parameter
    @Property
    private Trabajador actual;

    @Property
    @SessionState
    private Entidad _oi;

//    @Component(id = "titulosEdit")
//    private Form _form;

    @InjectComponent
    private Envelope envelope;
        
    @Property
    @Persist
    private Estudios estudio;
    @Property
    @Persist
    private LkBusquedaEstudios listaestu;

//    @Persist
//    private boolean entradaTituloGrid;

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
    private Zone listaZone;
    @InjectComponent
    @Property
    private Zone primerZone;   
    @InjectComponent
    @Property
    private Zone segundoZone;  
    @InjectComponent
    @Property
    private Zone tercerZone;
    @Component(id = "formlistaestudios")
    private Form formlistaestudios;
    @Inject
    private Request _request;
        
    //campos
    @Property
    @Persist
    private String valdenominacion;
    @Persist
    @Property
    private DatoAuxiliar valtipoestudio;
    @Persist
    @Property
    private DatoAuxiliar valcentroestudio;
    @Property
    @Persist
    private String valotrocentro;
    @Persist
    @Property
    private DatoAuxiliar valpais;
    @Property
    @Persist
    private Ubigeo ubigeoDomicilio;
    @Property
    @Persist
    private String valcolegio;
    @Property
    @Persist
    private String valcolegiatura;
    @Persist
    @Property
    private Date valfec_desde;
    @Persist
    @Property
    private Date valfec_hasta;
    @Persist
    @Property
    private Boolean valestudiando;
    @Persist
    @Property
    private Boolean valrevisado; 
    
    //validaciones
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vfechahasta;
    @Persist
    @Property
    private Boolean votro;
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
    private int elemento=0;
    
    
//    @Property
//    @SessionState
//    private DatoAuxiliar valtipoestudio;
//    @Property
//    @SessionState
//    private DatoAuxiliar valcentroestudio;
//    @Property
//    @SessionState
//    private String valotrocentro;
//    @Property
//    @SessionState
//    private DatoAuxiliar pais;
    


//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.puedeEscribir(_usuario, _oi);
//    }
    

    
//    public List<String> getValorTablaAuxiliar(String tabla) {
//    	// TODO: este codigo esta duplicado
//    	Criteria c = session.createCriteria(DatoAuxiliar.class);
//    	c.add(Restrictions.eq("nombreTabla", tabla));
//    	c.setProjection(Projections.property("valor"));
//        return c.list();
//    }
//
//    public List<String> getNiveles() {
//    	return getValorTablaAuxiliar("NivelTitulo");
//    }
    
    @Log
    public List<LkBusquedaEstudios> getEstudios() {
        Criteria c = session.createCriteria(LkBusquedaEstudios.class);
        c.add(Restrictions.eq("trabajador", actual.getId()));        
        return c.list();
    }
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoestudios() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOESTUDIO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getCentroestudios() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CENTROESTUDIO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    } 
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getPaises() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("PAISES", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    void onDenChanged() {
        valdenominacion = _request.getParameter("param");
    }

    void onOtroChanged() {
        valotrocentro = _request.getParameter("param");
    }
    
    void onColeChanged() {
        valcolegio = _request.getParameter("param");
    }
    
    void onColegChanged() {
        valcolegiatura = _request.getParameter("param");
    }

    @Inject
    private Session session;
    
    @Log
    @CommitAfter
    Object onSuccessFromformularioaltaestudio(){
        if(valcentroestudio!=null){
            if(valcentroestudio.getCodigo()==999999){
                votro=false;                
            }
            else{
                votro=true;
                valotrocentro=null;
            }
        }
        else{
            votro=true;
        }
        return primerZone.getBody();
    }
        
    @Log
    @CommitAfter
    Object onSuccessFromformulariodos(){
        if(valestudiando){
            vfechahasta=true;
            valfec_hasta=null;
        }
        else{
            vfechahasta=false;
        }
        return tercerZone.getBody();
    }
    
//    @Log
//    void onValidateFromformulariobotones() {
//        //formlistaestudios.recordError("Debe ingresar la Denominación");
//        
//        
//        
//    }
    
    void onSelectedFromSave() {        
        elemento=1;   
    }
    void onSelectedFromReset(){
        limpiar();
        editando=false;
        estudio=new Estudios();
        if(usua.getAccesoreport()==0){
            vformulario=false;
        } 
        elemento=2;
    }
    
    void onSelectedFromCancel() {        
        elemento=3;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariobotones(){
        
        if(elemento==3){
            return Busqueda.class;
        }
        else if(elemento==2){
            
        }
        else if(elemento==1){
            if(valdenominacion==null){
                formlistaestudios.recordError("Debe ingresar la Denominación");
                return listaZone.getBody();
            }
            if(valtipoestudio==null){
                formlistaestudios.recordError("Debe seleccionar el Tipo de Estudio");
                return listaZone.getBody();
            }
            if(valcentroestudio==null){
                formlistaestudios.recordError("Debe seleccionar el Centro de Estudio");
                return listaZone.getBody();
            }
            if(valfec_desde==null){
                formlistaestudios.recordError("Debe ingresar Fecha de Inicio");
                return listaZone.getBody();
            }
            if(valestudiando!=null){
                System.out.println("Editooooooooo");
                if(valestudiando==false){
                    if(valfec_hasta==null){
                        formlistaestudios.recordError("Debe ingresar Fecha de Fin");
                        return listaZone.getBody();
                    }
                    if (valfec_desde.after(valfec_hasta)) {
                        formlistaestudios.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return listaZone.getBody();
                    }  
                }
            }
            else{
                if(valfec_hasta==null){
                        formlistaestudios.recordError("Debe ingresar Fecha de Fin");
                        return listaZone.getBody();
                    }
                if (valfec_desde.after(valfec_hasta)) {
                        formlistaestudios.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return listaZone.getBody();
                    } 
            }

            if(editando){
                //editando
                if(usua.getAccesoreport()==0){
                    vformulario=false;
                } 
            }
            else{//guardando
                estudio = new Estudios();
                System.out.println("Trabajadorrr"+actual);
                estudio.setTrabajador(actual);        
                estudio.setEntidad(_usuario.getTrabajador().getEntidad());
                estudio.setValidado(false);
                if(valestudiando==null){
                    estudio.setEstudiando(false);
                }
                if(_usuario.getRol().getId()==1){
                    estudio.setAgregadotrabajador(true);
                }
                else{
                    estudio.setAgregadotrabajador(false);
                }
            }
            if(vrevisado==true){
                if(valestudiando==null){
                    estudio.setValidado(false);
                }
                else{
                    estudio.setValidado(valrevisado);
                }
                
            }
            seteo();
            session.saveOrUpdate(estudio); 
            editando = false; 
            limpiar();
            formlistaestudios.clearErrors();
            envelope.setContents("Estudios del Trabajador Modificados Exitosamente");
        }
        
        
        
        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody())                             
                    .add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody()); 
    }
    
    void mostrar(){        
        valdenominacion=estudio.getDenominacion();
        valtipoestudio=estudio.getTipoestudio();
        valcentroestudio=estudio.getCentroestudio();
        valotrocentro=estudio.getOtrocentroestudio();
        valpais=estudio.getPais();
        if (ubigeoDomicilio == null) {
            ubigeoDomicilio = new Ubigeo();
        }
        ubigeoDomicilio.setDepartamento(estudio.getDepartamento());
        ubigeoDomicilio.setProvincia(estudio.getProvincia());
        ubigeoDomicilio.setDistrito(estudio.getDistrito());
        valcolegio=estudio.getColegio();
        valcolegiatura=estudio.getColegiatura();
        valfec_desde=estudio.getFechainicio();
        valfec_hasta=estudio.getFechafin();
        valestudiando=estudio.getEstudiando();        
    }
    
    @Log
    Object onActionFromEditar(Estudios estu) {
        estudio=estu;
        vformulario=true;
        editando=true;
        vdetalle=false;
        vbotones=true;
        mostrar();
        if(valestudiando!=null){
            if(valestudiando){
            vfechahasta=true;
            }
            else{
                vfechahasta=false;
            }
        }
        else{
            vfechahasta=false;
        }
        if(valcentroestudio!=null){
            if(valcentroestudio.getCodigo()==9999999){
                votro=false;
            }
            else{
                votro=true;
            }
        }
        else{
            votro=true;
        }

        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody())                             
                    .add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody()); 
    }
    
    @Log
    Object onActionFromDetalle(Estudios estu) {
        estudio=estu;
        mostrar();        
        vdetalle=true;
        vfechahasta=true;
        votro=true;
        vbotones=false;
        vformulario=true;
        return new MultiZoneUpdate("primerZone", primerZone.getBody())                             
                    .add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody()); 
    }
    
    @Log
    Object onActionFromDetalles(Estudios estu) {
        estudio=estu;
        mostrar();        
        vdetalle=true;
        vfechahasta=true;
        votro=true;
        vbotones=false;
        vformulario=true;
        return new MultiZoneUpdate("primerZone", primerZone.getBody())                             
                    .add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody()); 
    }
    
    @Log
    @CommitAfter
    Object onBorrarDato(Estudios dato) {
        session.delete(dato);
        envelope.setContents("Estudio del Trabajador Eliminado");
        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody())                             
                    .add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody()); 
    }
    
    @Log
    @SetupRender
    private void inicio() {
        vrevisado=false;
        if(usua.getAccesoupdate()==1){
            veditar=true;
            vbotones=true;
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                vrevisado=true;
            }
        }
        if(usua.getAccesodelete()==1){
            veliminar=true; 
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                vrevisado=true;
            }
        }
        if(usua.getAccesoreport()==1){
            vformulario=true;
            vbotones=true;
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                vrevisado=true;
            }
        }
        
        votro=true;
        editando=false;
        limpiar();
    }
    
    void seteo(){
        estudio.setDenominacion(valdenominacion);
        estudio.setTipoestudio(valtipoestudio);
        estudio.setCentroestudio(valcentroestudio);
        estudio.setOtrocentroestudio(valotrocentro);
        estudio.setPais(valpais);
        estudio.setDepartamento(ubigeoDomicilio.getDepartamento());
        estudio.setProvincia(ubigeoDomicilio.getProvincia());
        estudio.setDistrito(ubigeoDomicilio.getDistrito());
        estudio.setColegio(valcolegio);
        estudio.setColegiatura(valcolegiatura);
        estudio.setFechainicio(valfec_desde);
        estudio.setFechafin(valfec_hasta);
        estudio.setEstudiando(valestudiando);
        estudio.setValidado(valrevisado);
    }
    
    void limpiar(){
        estudio=new Estudios();
        valdenominacion=null;
        valtipoestudio=null;
        valcentroestudio=null;
        valotrocentro=null;
        valpais=null;
        ubigeoDomicilio=null;
        valcolegio=null;
        valcolegiatura=null;
        valfec_desde=null;
        valfec_hasta=null;
        valestudiando=null;  
        valrevisado=null;
    }
    

    

  
//  @CommitAfter
//  public Object onSuccess()
//  {
//      for(Titulo tit : actual.titulos) {
//    	  
//          if(tit.getFec_emision().after(new Date())) {
//            Logger logger = new Logger();
//            logger.loguearError(session, _usuario, tit.getId().toString(),
//                            Logger.CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL,
//                            Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TITULO);
//
//            _form.recordError(Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL);
//            return this;
//          }
//    }
//      
//      //es el usuario trabajador o no
//      /*if(_usuario.getTipo_usuario().equals("")){
//          titulo.setAgregadoTrabajador(Boolean.TRUE);
//      }*/
//        _form.clearErrors();
//        envelope.setContents(helpers.Constantes.TITULO_EXITO);
//
//	return this;
//  }

//     Object onFailure() {
//          return this;
//    }
     
// 	@Log
//	Object onValidateFormtitulosEdit()
//	{
//		if(titulo.getFec_emision().after(new Date())) {
//                        Logger logger = new Logger();
//    			logger.loguearError(session, _usuario, titulo.getId().toString(),
//    					Logger.CODIGO_ERROR_FECHA_EMISION_PREVIA_ACTUAL,
//    					Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL, Logger.TIPO_OBJETO_TITULO);
//
//			_form.recordError(Errores.ERROR_FECHA_EMISION_PREVIA_ACTUAL);
//		}
//		return this;
//	}


//  @CommitAfter
//  Object onAddRow()
//  {
//    Titulo tit = new Titulo();
//    if(actual.getTitulos() == null){
//        actual.setTitulos(new ArrayList<Titulo>());
//    }
//    tit.setTrabajador(actual);
//    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
//        titulo.setAgregadoTrabajador(Boolean.TRUE);
//    actual.getTitulos().add(tit);
//    session.saveOrUpdate(actual);
//	new Logger().loguearOperacion(session, _usuario, String.valueOf(tit.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TITULO);
//    return tit;
//  }

//  @CommitAfter
//  void onRemoveRow(Titulo tit)
//  {
//    actual.getTitulos().remove(tit);
//    session.delete(tit);
//    new Logger().loguearOperacion(session, _usuario, String.valueOf(tit.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TITULO);
//  }
  
}
