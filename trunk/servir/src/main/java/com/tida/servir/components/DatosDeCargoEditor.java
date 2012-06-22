package com.tida.servir.components;


import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Usuario;
import helpers.Constantes;
import helpers.Helpers;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
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
    private Entidad_BK _oi;
	
    /*@Property
    private String vinculoTipo;*/
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    @Inject
    private Session session;
    
    @Component(id = "formulariodatosdecargoasignado")
    private Form formularioDatosDeCargoAsignado;

    @InjectComponent
    private Zone datosDeCargoZone;


    @InjectComponent
    private Envelope envelope;
    
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
    
    @Log
    public String getVinculoTipo(){
        return actual_asignado.getTipoVinculo();
    }
    
    public List<String> getVinculos() {
    	return Helpers.getValorTablaAuxiliar("TipoVínculo", session);
    }    
    
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
        envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);
        return datosDeCargoZone.getBody();
    }

    
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
