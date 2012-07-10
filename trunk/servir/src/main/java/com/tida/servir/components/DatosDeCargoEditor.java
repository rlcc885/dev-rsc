package com.tida.servir.components;


import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.pages.Busqueda;
import helpers.Constantes;
import helpers.Helpers;
import helpers.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Session;


/**
 *
 *	Clase que maneja el TAB del editor de datos personales.
 *  
 */
public class DatosDeCargoEditor {

	//@SuppressWarnings("unused")
//    @Parameter(required = true, principal = true, autoconnect = true)
//    @Property
//    private String _zone;
    @SuppressWarnings("unused")
    @Property    
    @Parameter(required = true, principal = true, autoconnect = true)
    private Cargoxunidad actual;

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, principal = true, autoconnect = true)
    private CargoAsignado actual_asignado;
    
    @Property
    @SessionState
    private Entidad _oi;
	
    /*@Property
    private String vinculoTipo;*/
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    @Inject
    private Session session;
    
    @Component(id = "formulariodatosdecargoasignado")
    private Form formulariodatosdecargoasignado;

    @InjectComponent
    private Zone datosDeCargoZone;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Envelope envelope;
    
    private int elemento=0;
    
    @Property
    @Persist
    private String valmotivo;
    @Persist
    @Property
    private Date valfec_inicio;
    @Persist
    @Property
    private Date valfec_fin;
    @Property
    @Persist
    private DatoAuxiliar valtipovinculo;
    
    @Log
    @SetupRender
    private void inicio() {
        valmotivo=actual_asignado.getMotivo_cese();
        valfec_inicio=actual_asignado.getFec_inicio();
        valfec_fin=actual_asignado.getFec_fin();
        valtipovinculo=actual_asignado.getTipovinculo();        
    }
    
    
    public boolean getNoEditable() {
        return !getEditable();
    }

    public boolean getEditable() {
       return Permisos.puedeEscribir(_usuario, _oi);
    }

    public Integer getCantPuestosOcupados(){

        return Helpers.getCantPuestosOcupadosCargo(session, actual);
    }

    public boolean getAsignadoBaja() {
        /*
         * TODO JZM revisar linea de codigo
        if(actual_asignado.getEstado() == null)
            return actual_asignado.getEstado().equals(Constantes.ESTADO_BAJA);
        else return false;
        * 
        */
        if(actual_asignado.getEstado() == false)
            return false;
        else return true;
    }
    
//    @Log
//    public String getVinculoTipo(){
//        return actual_asignado.getTipoVinculo();
//    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoVinculo() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);
        
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVÍNCULO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
        
//    public List<String> getVinculos() {
//    	return Helpers.getValorTablaAuxiliar("TipoVínculo", session);
//    }    
    
    public List<String> getEstados() {
    	ArrayList<String> estados = new ArrayList<String>();
        /*
         * TODO JZM verificar linea de codigo
        
        estados.add(Constantes.ESTADO_ACTIVO);
        estados.add(Constantes.ESTADO_BAJA);
        */
        
        estados.add("Activo");
        estados.add("Baja");
        
        return estados;
    }    
    
    void onSelectedFromSave() {        
        elemento=1;   
    }
    
    void onSelectedFromCancel() {        
        elemento=2;   
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodatosdecargoasignado(){
        if(elemento==2){
            return Busqueda.class;
        }
        else{         
            if(valfec_fin!=null){
                if(valmotivo != null && !valmotivo.equals("")){
                    if (valfec_fin.before(valfec_inicio)) {
                        formulariodatosdecargoasignado.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return datosDeCargoZone.getBody();
                    }
                    if(valfec_inicio.after(new Date())) {
                        formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                        return datosDeCargoZone.getBody();
                    }
                    registrar(false);
                    return datosDeCargoZone.getBody();
                }
                else{
                    formulariodatosdecargoasignado.recordError("Debe ingresar Motivo Cese");
                    return datosDeCargoZone.getBody();
                }       
            } 
            if(valmotivo != null && !valmotivo.equals("")){        
                if(valfec_fin!=null){
                    if (valfec_fin.before(valfec_inicio)) {
                        formulariodatosdecargoasignado.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return datosDeCargoZone.getBody();
                    }
                    if(valfec_inicio.after(new Date())) {
                        formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                        return datosDeCargoZone.getBody();
                    }
                    registrar(false);
                    return datosDeCargoZone.getBody();
                }
                else{
                    formulariodatosdecargoasignado.recordError("Debe seleccionar Fecha Finalización");
                    return datosDeCargoZone.getBody();
                }       
            }
            if(valfec_inicio.after(new Date())) {
                formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                return datosDeCargoZone.getBody();
            }
            registrar(true);
        }
        return datosDeCargoZone.getBody();
    }
    
    void registrar(Boolean e){
       actual_asignado.setMotivo_cese(valmotivo);
       actual_asignado.setFec_inicio(valfec_inicio);
       actual_asignado.setFec_fin(valfec_fin);
       actual_asignado.setTipovinculo(valtipovinculo);
       actual_asignado.setEstado(e);
       session.saveOrUpdate(actual_asignado);
       //new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_MODIFICACION : Logger.CODIGO_OPERACION_ALTA), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
       session.flush();
       formulariodatosdecargoasignado.clearErrors();
       envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
    }
    
//     void onSelectedFromSave() {        
//         elemento=2;
//    }     
                 
//    @Log
//    @CommitAfter
//    Object onSuccessFromFormulariobotones(){
//        
////        if(actual_asignado.getMotivo_cese()==null){           
////                formulariodatosdecargoasignado.recordError("Debe ingresar el motivo de Cese");
////                return datosDeCargoZone.getBody();
////
////        }
//        envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
//        //envelope.setContents(String.valueOf(actual_asignado.getFec_fin())+String.valueOf(actual_asignado.getFec_inicio()));   
//        return datosDeCargoZone.getBody();
//    }
//    
//    @Log
//    void onValidateFromformulariodatosdecargoasignado() {
//        if(valmotivo==null){            
//                formulariodatosdecargoasignado.recordError("Debe ingresar el motivo de Cese");
//        }
//        envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
//    }
     
    
/*	@Log
	Object onValidateFromformularioDatosDeCargoAsignado()
	{
        if (actual_asignado.getFec_fin().before(actual_asignado.getFec_inicio())) {
            formularioDatosDeCargoAsignado.recordError("Las fechas de fin no pueden ser menores a las de inicio");
            return this;
        }
  	  	if(actual_asignado.getFec_inicio().after(new Date())) {
  	  		formularioDatosDeCargoAsignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
			return this;
		}
		
		return this;
	}
*/
}