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
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class MeritosDemeritosEditor {

    @Property
    @SessionState
    private Usuario _usuario;
    @SessionState
    private UsuarioAcceso usua;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
    @InjectComponent
    private Zone mensajesMEZone;
    @InjectComponent
    private Zone claseZone;
    @InjectComponent
    private Zone listaMeritosZone;
    @InjectComponent
    private Zone tipoZone;
    private int elemento = 0;
    @Component(id = "formularioclase")
    private Form formularioclase;
    @Component(id = "formulariomensajesME")
    private Form formulariomensajesME;
    @Persist
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;
    @Parameter
    @Property
    private Trabajador actual;
    @Persist
    @Property
    private MeritoDemerito merito;
    @Property
    @Persist
    private boolean btipo;
    //Listado de Meritos
    @Persist
    @Property
    private LkBusquedaMeritoDemerito listaMeritos;
    @Inject
    private PropertyAccess _access;
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
    private String clase;
    @Persist
    @Property
    private Boolean vformulario;

    //Inicio de la carga de la pagina
    @Log
    void setupRender() {
        // No mover la inicializacion de variables
        resetRegistro();
    }

    @Log
    void resetRegistro() {
        merito = new MeritoDemerito();
        editando = false;
        valfec_desde = "";
        btipo = true;
        clase = "1";
        accesos();
    }

    @Log
    public void accesos() {
        bvalidausuario = false;
        vinserta = false;
        veditar = false;
        veliminar = false;
        vdetalle = true;
        vguardar = false;
        vformulario = false;
        if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
            bvalidausuario = true;
        }
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vdetalle = false;
            vguardar = true;
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
        }
        if (usua.getAccesoreport() == 1) {
            vinserta = true;
            vguardar = true;
            vdetalle = false;
            vformulario = true;
        }
    }

    @Log
    public String getFecha() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(listaMeritos.getFecha());
    }

    @Log
    public List<LkBusquedaMeritoDemerito> getListadoMeritos() {
        Criteria c = session.createCriteria(LkBusquedaMeritoDemerito.class);
        c.add(Restrictions.eq("trabajador_id", actual.getId()));
        nroregistros = Integer.toString(c.list().size());
        return c.list();
    }

    @Persist
    @Property
    private String nroregistros;

    @Persist
    @Property
    private String nroSanciones;

    @Log
    public List<LkBusquedaSancion> getListadoSanciones(){
        
        Criteria c = session.createCriteria(DatoAuxiliar.class);
        c.add(Restrictions.eq("nombreTabla", "CATEGORIASANCION"));
        c.add(Restrictions.eq("codigo", Long.parseLong("2")));
        DatoAuxiliar categoriaSancion = (DatoAuxiliar)c.uniqueResult();
    System.out.println("ERRORX1");    
        
        c = session.createCriteria(DatoAuxiliar.class);
        Criterion criterionVigente = Restrictions.eq("codigo", Long.parseLong("1"));
        Criterion criterionNoVigente = Restrictions.eq("codigo", Long.parseLong("2"));
        LogicalExpression expression = Restrictions.or(criterionVigente, criterionNoVigente);
        c.add(Restrictions.eq("nombreTabla", "ESTADOSANCION"));
        c.add(expression);
//        c.setProjection(Projections.distinct(Projections.property("id")));
        List<DatoAuxiliar> estadosanciones = c.list();
    System.out.println("ERRORX2");    
        
        CargoAsignado cargoactual = new CargoAsignado();
    /*    for (CargoAsignado ca : actual.getCargosAsignados()){
            if (ca.getEstado()==Boolean.TRUE){
            cargoactual = ca;
            }
        }
    */    
        c = session.createCriteria(CargoAsignado.class);
        c.add(Restrictions.eq("trabajador", actual));
        c.add(Restrictions.eq("estado", Boolean.TRUE));
        cargoactual = (CargoAsignado)c.uniqueResult();
        
    System.out.println("ERRORX3");    
        
        c = session.createCriteria(Sancion.class);
        c.add(Restrictions.eq("categoria_sancion", categoriaSancion));
        c.add(Restrictions.in("sancion_estado", estadosanciones));
        c.add(Restrictions.eq("cargoasignado",cargoactual));
//        c.add(Restrictions.eq("cargoasignado", actual.getc))
        c.setProjection(Projections.distinct(Projections.property("id")));
        List<Long> listasancion = c.list();
    System.out.println("ERRORX4");    
   
        
  //      System.out.println("sanciones"+listasan.toString());
        c = session.createCriteria(LkBusquedaSancion.class);
        c.add(Restrictions.eq("nro_doc_trabajador", actual.getNroDocumento()));
        if (!listasancion.isEmpty()){
        c.add(Restrictions.in("id_sancion", listasancion));
        }else{return null;}
     //   c.add(Restrictions.in("estado_id", estadosanciones));
     //   c.add(Restrictions.in("id_tipo_sancion",tiposanciones));
    System.out.println("ERRORX5");    
        
        nroSanciones = Integer.toString(c.list().size());
        return c.list();
    }
    

    //para obtener datos de la Clase de Merito
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanClaseMerito() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("MERITOSDEMERITOSCLASE", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del Tipo de Merito
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoMerito() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSMERITO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del Tipo de DMerito
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoDemerito() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOSDEMERITO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    //para obtener datos del documento

    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoDocumento() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPODOCUMENTO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    Object onReset() {
        resetRegistro();
        return claseZone.getBody();
    }

    @Log
    Object onCancel() {
        System.out.println(_usuario.getRolid());
        if (_usuario.getRolid() == 1) { // Si es trabajador 
            return "meritos";
        } else {
            return "Busqueda";
        }
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormularioclase() {
        formulariomensajesME.clearErrors();
        if (merito.getTipodocumento() == null) {
            formulariomensajesME.recordError("Debe ingresar el Documento");
            //envelope.setContents("Debe ingresar la Clase");
            return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody());
        }

        if (merito.getTipomeritodemerito() == null) {
            formulariomensajesME.recordError("Debe ingresar el Tipo");
            return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody());
        } else {

            if (valfec_desde != null) {
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fecha_desde = (Date) formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            } else {
                formulariomensajesME.recordError("Debe de Ingresar una Fecha");
                return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody());
            }

            if (fecha_desde.after(new Date())){
                formulariomensajesME.recordError("La fecha debe ser menor a la fecha actual");
                return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody());            
            }
            if (_usuario.getRolid() == 1) {
                formulariomensajesME.recordError("Ud, no tiene permisos para Insertar Datos");
//            envelope.setContents("Ud, no tiene permisos para Insertar Datos");
                return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody());
            }
            merito.setClasemeritodemerito_id(Long.parseLong(clase));
            merito.setFecha(fecha_desde);
            merito.setTrabajador_id(actual.getId());
            merito.setEntidad_id(_oi.getId());
            session.saveOrUpdate(merito);
            envelope.setContents(helpers.Constantes.MERITO_DEMERITO_EXITO);
            new Logger().loguearOperacion(session, _usuario, String.valueOf(merito.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_MERITO_DEMERITO);    
            resetRegistro();
            return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("listaMeritosZone", listaMeritosZone.getBody()).add("claseZone", claseZone.getBody());
        }
    }

    @Log
    public String getFechaMD() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(listaMeritos.getFecha());
    }

    @Log
    Object onActionFromEditarME(MeritoDemerito meri) {
        merito = meri;
        if (merito.getFecha() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde = formatoDeFecha.format(merito.getFecha());
        }
        if (merito.getClasemeritodemerito_id() == 1) {
            btipo = true;
        } else {
            btipo = false;
        }
        clase = String.valueOf(merito.getClasemeritodemerito_id());
        accesos();
        vformulario = true;
        editando = true;
        return claseZone.getBody();
    }

    @Log
    @CommitAfter
    Object onActionFromEliminarME(MeritoDemerito meri) {
        new Logger().loguearOperacion(session, _usuario, String.valueOf(meri.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_MERITO_DEMERITO);
        formulariomensajesME.clearErrors();
        session.delete(meri);
        envelope.setContents("Meritos demeritos eliminadas exitosamente.");
        resetRegistro();
        accesos();
        return new MultiZoneUpdate("mensajesMEZone", mensajesMEZone.getBody()).add("claseZone", claseZone.getBody()).add("listaMeritosZone", listaMeritosZone.getBody());
    }

    @Log
    Object onActionFromDetalleME(MeritoDemerito meri) {
        Object objeto = onActionFromEditarME(meri);
        vdetalle = true;
        vguardar = false;
        editando = false;
        vformulario = true;
        return objeto;
    }

    @Log
    Object onValueChangedFromClase(Long dato) {
        if (dato != null) {
            merito.setClasemeritodemerito_id(dato);
            if (dato == 1) {
                btipo = true;
            } else {
                btipo = false;
            }
        }
        merito.setTipomeritodemerito(null);
        return tipoZone.getBody();
    }
}