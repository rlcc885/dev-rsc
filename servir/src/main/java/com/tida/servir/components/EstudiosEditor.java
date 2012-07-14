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
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


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
    private Estudios listaestu;

//    @Persist
//    private boolean entradaTituloGrid;

    @Property
    @SessionState
    private Usuario _usuario;
    @Inject
    private PropertyAccess _access;
    
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
    private Boolean vfechahasta;
    
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
    public List<Estudios> getEstudios() {
        Criteria c = session.createCriteria(Estudios.class);
        c.add(Restrictions.eq("trabajador", actual));        
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
    
    

    @Inject
    private Session session;

    @Log
    @CommitAfter
    Object onSuccessFromformulariodos(){
        if(valestudiando){
            vfechahasta=true;
        }
        else{
            vfechahasta=false;
        }
        return tercerZone.getBody();
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariobotones(){
        envelope.setContents(String.valueOf(valfec_desde)+String.valueOf(valfec_hasta));
        return listaZone.getBody();
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
