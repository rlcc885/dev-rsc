package com.tida.servir.components;


import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
import java.util.ArrayList;
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

	@SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;

    @Property    
    @Parameter
    private Cargoxunidad actual;

    @Property
    @Parameter
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
    
    @Component(id = "formulariodatos")
    private Form formulariodatos;

    @InjectComponent
    private Zone datosDeCargoZone;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Envelope envelope;
    
    private int elemento=0;
    @InjectComponent
    private Zone muestraZone;
    
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
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodatosdecargoasignado(){
        if(elemento==2){
            envelope.setContents("Primero");
        }
        else{
            envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
        }
        
        return datosDeCargoZone.getBody();
    }
    
     void onSelectedFromSave() {        
         elemento=2;
    }     
                 
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobotones(){
        
        if(actual_asignado.getFec_fin()==null){
           
                formulariodatos.recordError("Debe ingresar el motivo de Cese");
                return muestraZone.getBody();

        }
        envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
        //envelope.setContents(String.valueOf(actual_asignado.getFec_fin())+String.valueOf(actual_asignado.getFec_inicio()));   
        return datosDeCargoZone.getBody();
    }
    
//    @Log
//    void onValidateFromFormulariobotones() {
//        if(actual_asignado.getFec_fin()!=null){
//            if(actual_asignado.getMotivo_cese()==null)
//                formularioDatosDeCargoAsignado.recordError("Debe ingresar el motivo de Cese");
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
