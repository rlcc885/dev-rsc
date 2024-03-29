package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class PublicacionesEditor {

    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @SessionState
    private UsuarioAcceso usua;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
    @Component(id = "formulariomensajespi")
    private Form formulariomensajespi;
    @InjectComponent
    private Zone mensajesPIZone;
    @InjectComponent
    private Zone proIntelectualZone;
    private int elemento = 0;
    @Parameter
    @Property
    private Trabajador actual;
    @Persist
    @Property
    private Publicacion publicacion;
    //Listado de produccion intelectual
    @InjectComponent
    private Zone listaProIntelectualZone;
    @Persist
    @Property
    private Publicacion listaprointelectual;
    @Inject
    private PropertyAccess _access;
    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;
    // Variables para el manejo de los permisos
    @Property
    @Persist
    private boolean bvalidausuario;
    @Persist
    @Property
    private Boolean editando;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vguardar;
    @Persist
    @Property
    private Boolean vinserta;
    @Persist
    @Property
    private Boolean vformulario;
//modificacion******
    @Persist
    @Property
    private Boolean opcMod;
    @Persist
    @Property
    private Boolean vrevisado;
    
    @Log
    void setupRender() {
        // No mover la inicializacion de variables
        resetRegistro();
    }

    @Log
    public void accesos() {
        vrevisado= false;
        bvalidausuario = false;
        vinserta = false;
        veditar = false;
        veliminar = false;
        vdetalle = true;
        vguardar = false;//opcMod=false;
        vformulario = false;
  //modificacion
   //     opcInsertar = true;
        
        if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
            bvalidausuario = true;
        }
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vdetalle = false;
            vguardar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }            
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
            // modificacion
            //if(usua.getAccesoupdate() != 1){vdetalle=false;}
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }            
        }
        if (usua.getAccesoreport() == 1) {
            vformulario=true;
            vinserta = true;
            vguardar = true;
            vdetalle = false;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }            
        }
        
        if (!editando){opcMod=false;}
        if (usua.getAccesoupdate()==1 && vinserta==false && opcMod==false){vguardar=false;}
   //modificacion
  //      if (vinserta == true){opcInsetar = false;}
       // if (usua.getAccesoupdate() == 1 && vinserta==false){opcInsertar=false;}
    }
    @Persist
    @Property
    private String nroregistros;
    
    @Log
    public List<Publicacion> getListadoProIntelectual() {
        Criteria c = session.createCriteria(Publicacion.class);
        c.add(Restrictions.eq("trabajador", actual));
        nroregistros = Integer.toString(c.list().size());
        return c.list();
    }

    //para obtener datos de la clase de publicacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanClasePublicacion() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CLASEPRODUCCIONINTELECTUAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del tipo de publicacion
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipo() {        
        List<DatoAuxiliar> list = null;
        if (publicacion.getClasePublicacion() != null) {
            list = Helpers.getDatoAuxiliar("TIPOSPRODUCCIONINTELECTUAL", "CLASEPRODUCCIONINTELECTUAL", publicacion.getClasePublicacion().getCodigo(), session);
        }else {
            list = Helpers.getDatoAuxiliar("TIPOSPRODUCCIONINTELECTUAL", "CLASEPRODUCCIONINTELECTUAL", 0, session);
        }
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    void resetRegistro() {
        publicacion = new Publicacion();
        editando = false;
        valfec_desde = "";
        accesos();//opcInsertar=true;
    }

    @Log
    Object onReset() {
        resetRegistro();
        return proIntelectualZone.getBody();
    }

    @Log
    Object onCancel() {
        System.out.println(_usuario.getRolid());
        if (_usuario.getRolid() == 1) { // Si es trabajador 
            return "TrabajadorPersonal";
        } else {
            return "Busqueda";
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioprointelectual() {
        formulariomensajespi.clearErrors();
        if (valfec_desde != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_desde = (Date) formatoDelTexto.parse(valfec_desde);
                if(fecha_desde.after(new Date())){
                    formulariomensajespi.recordError("La Fecha de la Producción debe ser menor a la Actual");
                    return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody()).add("proIntelectualZone", proIntelectualZone.getBody());
                }
                publicacion.setFecha(fecha_desde);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        Logger logger = new Logger();
        publicacion.setTrabajador(actual);
        publicacion.setEntidad(_oi);

        if (!editando) { // Si no edita, está insertando
            if (_usuario.getRolid() == 1) { // Si es trabajador 
                publicacion.setAgregadoTrabajador(true);
                publicacion.setValidado(false);
            }
        }

        session.saveOrUpdate(publicacion);
        session.flush();
        new Logger().loguearOperacion(session, _usuario, String.valueOf(publicacion.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_PRODUCCION_INTELECTUAL);    

        if (_usuario.getRolid() == 1) {
            logger.loguearEvento(session, logger.MODIFICACION_PRODUCCION, actual.getEntidad().getId(), actual.getId(), _usuario.getId(), logger.MOTIVO_PERSONALES_PRODUCCION, publicacion.getId());
        }
        if (publicacion.getValidado() != null) {
            if (publicacion.getValidado() == true) {
                String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + publicacion.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_PRODUCCION + "' and tabla_id='" + publicacion.getId() + "' and estadoevento=0";
                Query query = session.createSQLQuery(hql);
                int rowCount = query.executeUpdate();
                session.flush();
            }
        }

        envelope.setContents(helpers.Constantes.PROD_INTELECTUAL_EXITO);
        resetRegistro();
        return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody()).add("proIntelectualZone", proIntelectualZone.getBody());

    }

    @Log
    Object onActionFromEditar(Publicacion publi) {
        publicacion = publi;
        if (publicacion.getFecha() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde = formatoDeFecha.format(publicacion.getFecha());
        }
//modificacion
        opcMod=true;
        accesos();//opcInsertar=true;
        opcMod=false;
        editando = true;
        vformulario = true;
        //
        vguardar=true;
        return proIntelectualZone.getBody();
    }

    @Log
    Object onActionFromEditar2(Publicacion publi)
    {
        return onActionFromEditar(publi);
    }
    
    @Log
    Object onActionFromDetalle(Publicacion publi) {
//        publicacion = publi;
//        if (publicacion.getFecha() != null) {
//            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
//            valfec_desde = formatoDeFecha.format(publicacion.getFecha());
//        }
//        accesos();
     //   opcInsertar= true;
        Object objeto = onActionFromEditar(publi);
        vdetalle = true;
        vguardar = false;
        vformulario= true;
        editando = false;
        if (vinserta){}
        else{vinserta=false;}
        //modificaciones
     //   vinserta=false;
        
        return objeto;
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar(Publicacion publi) {
        new Logger().loguearOperacion(session, _usuario, String.valueOf(publi.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_PRODUCCION_INTELECTUAL);
        formulariomensajespi.clearErrors();
        session.delete(publi);
        envelope.setContents("Produción Intelectual eliminada exitosamente.");
        resetRegistro();
        accesos();
        return new MultiZoneUpdate("mensajesPIZone", mensajesPIZone.getBody()).add("listaProIntelectualZone", listaProIntelectualZone.getBody()).
                add("proIntelectualZone", proIntelectualZone.getBody());
    }

//    @Log
//    Object onActionFromEditar2(Publicacion publi) {
//        return onActionFromEditar(publi);
//    }
//    @Log
//    Object onActionFromDetalle2(Publicacion publi) {
//        return onActionFromDetalle(publi);
//    }
    @Log
    Object onActionFromDetalle3(Publicacion publi) {
        return onActionFromDetalle(publi);
    }
//    @Log
//    Object onActionFromEliminar2(Publicacion publi) {
//        return onActionFromEliminar(publi);
//    }
    @Log
    Object onValueChangedFromclasepublicacion(DatoAuxiliar dato) {
        return proIntelectualZone.getBody();
    }
}