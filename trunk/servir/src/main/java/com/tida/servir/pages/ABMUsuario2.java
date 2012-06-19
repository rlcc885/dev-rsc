package com.tida.servir.pages;

import com.tida.servir.entities.CrearDatos;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.hibernate.Criteria;



/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *  
 */
public class ABMUsuario2 {


    @Inject
    private Session session;


    @Property   
    private Usuario usuario;

    @Property
    private Usuario u;


    @Property
    @SessionState
    private Usuario loggedUser;

    @Property
    @SessionState
    private Entidad_BK _oi;


	@Component(id = "formulariousuario")
	private Form formularioUsuario;

    public ABMUsuario2() {
    
    }
	
    
    public List<String>  getTiposUsuarios(){
        //FIXME TODO Obtener desde la base
        List<String> tc = new ArrayList<String>();

            tc.add(Usuario.ADMINGRAL);
        return tc;
    }

    public List<String>  getEstadosUsuarios(){
        //FIXME TODO Obtener desde la base
        List<String> tc = new ArrayList<String>();
        tc.add("Activo");
        tc.add("Borrado");
        return tc;
    }


  public List<Usuario> getUsuarios () {
      Criteria c;
      c = session.createCriteria(Usuario.class);
     return c.list();
  }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioUsuario()
    {
//        usuario.setEntidadUE(_oi);
        // Seteo algún oi

            CrearDatos.crearDatosBasicos(session); // Creo algunos organismos, etc
            CrearDatos.crearDatosAuxiliares(session); // Creo las tablas básicas de datos auxiliares
    //        usuario.setEntidadUE((EntidadUEjecutora) session.createCriteria(EntidadUEjecutora.class).list().get(0));
        
        session.saveOrUpdate(usuario);

        //usuario = new Usuario();
        //session.saveOrUpdate(usuario)
    	return this;
    }

	@Log
	void onPrepareFromFormularioUsuario()
	{
            if (usuario==null)
                usuario = new Usuario();
	}

        /*
         * reset del formulario (borrar  objeto)
         */
        void onActionFromReset() {
            usuario = new Usuario();
        }

        /*
         * Borrar la fila
         */
        /*
        void onActionFromBorrar(UnidadEjecutora ue) {
            // FIXME TODO Ver en qué condiciones borrar una UnidadEjecutora y en cuáles no.
            System.out.println("UE a Borrar " + ue.denominacion_ue);
            session.delete("UnidadEjecutora", ue);

        }
        */
        /*
         Cargar desde los parámetros
         */
        void onActivate(Usuario user)
        {
            if (user==null)
                user = new Usuario();

            this.usuario = user;
        }

        Usuario onPassivate()
        {
            return null;
        }


}
