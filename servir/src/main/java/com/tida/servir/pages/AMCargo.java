package com.tida.servir.pages;

import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.GenericSelectModel;

import helpers.Logger;

import java.util.List;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;


/**
 * Clase que maneja las unidades ejecutoras
 * @author ale
 */
public class AMCargo
{
    
    
    @Inject
    private Session session;
    
    
    @Property
    private Cargoxunidad cargo;
    
    
    @Property
    private Cargoxunidad c;
    
    @Component(id = "formularioaltacargo")
    private Form formularioaltacargo;
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    public List<Cargoxunidad> getCargos()
    {
    	 return session.createCriteria(Cargoxunidad.class).list();
    }
    
    /*
     * levantamos el combo de Organismos informantes
     */
    
    
    
    @Inject
    private PropertyAccess _access;
    
    private GenericSelectModel<UnidadOrganica> _beans;
    
    
    public UnidadOrganica getUnidadOrganica(){
       return cargo.getUnidadorganica();
    }
    
    public void setUnidadOrganica(UnidadOrganica _uo){
       cargo.setUnidadorganica(_uo);
    }

    public GenericSelectModel<UnidadOrganica> getBeans(){
       return _beans;
    }

/**
 * Hasta acá para levantar combo de unidades organicas
 */
    
    public AMCargo()
    {
        List<UnidadOrganica> list = session.createCriteria(UnidadOrganica.class).list();
        
        _beans = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
    }
    
    
    @Log
    @CommitAfter
    Object onSuccessFromformularioaltacargo()
    {
		// FIXME: chequear que la ue no esté repetida, etc
    	
    	// System.out.println("Unidad organica: " + cargo.getUnd_organica());
    	UnidadOrganica uo = (UnidadOrganica) session.load(UnidadOrganica.class, cargo.getUnidadorganica().getId());
    	cargo.setUnidadorganica(uo);
        session.saveOrUpdate(cargo);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(cargo.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CARGO);
        
    	return this;
	}
        
	@Log
	void onPrepareFromformularioaltacargo()
	{
		if(cargo == null) {
			cargo = new Cargoxunidad();
		}
	}
        
        /*
         * reset del formulario (borrar  objeto)
         */
        void onActionFromReset() {
            cargo = new Cargoxunidad();
        }
        
        /*
         * Borrar la fila
         */
        
        /*
         Cargar desde los parámetros
         */
        void onActivate(Cargoxunidad c)
        {
            if (c==null)
                c = new Cargoxunidad();
            
            this.cargo = c;
        }

        Cargoxunidad onPassivate()
        {
            return cargo;
        }


}