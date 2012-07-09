package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.Cargoxunidad;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.DatoAuxiliar;
import helpers.Errores;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Permisos;
import com.tida.servir.entities.Trabajador;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.entities.Usuario;
import com.tida.servir.services.CargosSelectModel;
import com.tida.servir.services.GenericSelectModel;
import helpers.Constantes;
import helpers.Helpers;
import helpers.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


@IncludeStylesheet({"context:layout/trabajadornuevo.css"})
/**
 * Clase que maneja la pagina de creacion de Trabajador
 */
public class TrabajadorNuevo  extends GeneralPage
{
    @Inject
    private Session session;

    @Property
    @SessionState
    private Entidad _oi;

    @Property
    @SessionState
    private Usuario loggedUser;

    @InjectPage
    private TrabajadorPersonal trabajadormodificar;

    @Component(id = "formulariotrabajadornuevolegajocargo")
    private Form formulariotrabajadornuevolegajocargo;
    
    @Component(id = "formulariounidadorganica")
    private Form formulariounidadorganica;
    
    @Property
    @Persist
    private UnidadOrganica unidadorganica;
    
    @Property
    private Legajo nuevoLegajo;

    @Property
    private CargoAsignado cargoAsignado;

    @Inject
    private PropertyAccess _access;

    private CargosSelectModel<Cargoxunidad> _beans;

    @InjectPage
    private Busqueda index;

    // Mensajes a mostrar entre paginas. Sólo por única vez
    @Persist(PersistenceConstants.FLASH)
    private String mensajes;

    @Property
    private List<Trabajador> trabajadoresEncontrados;

    private List<String> tiposDoc = new ArrayList();

    private boolean DNI;
    
    private List<String> errores;
    
    @Property
    @Persist
    private Trabajador nuevo;
    
    @Property
    @Persist
    private Legajo legajoExistente;

    /*@Property
    @Persist
    private Trabajador trabajadorExistente;*/

    @Component(id = "formulariotrabajadornuevo")
    private Form formulariotrabajadornuevo;
        
    @Property
    @Persist
    private Boolean primerPaso;
    
    @Property
    @Persist
    private Boolean trabajadorExiste;
    
    @Persist
    private Boolean trabajadorTieneCargoOtraEntidad;
    
    @Persist
    private String entidadTrabajadorTieneCargo;

    @Property
    private Date fec_inicio;

    @Property
    private Integer ctd_per_superv;
    
    @InjectComponent
    @Property
    private Zone cargosZone;
    
    @Property
    @Persist
    private Boolean unidadSeleccionada;
    
    @InjectComponent
    private Envelope envelope;
    
    public boolean getDNI(){
        if(nuevo.getTipodiscapacidad().equals("DNI"))
            return true;
        
        return false;
    }

    
    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }
    
    public List<String> getTiposDoc() {
    	Criteria c = session.createCriteria(DatoAuxiliar.class);
    	c.add(Restrictions.eq("nombreTabla", "DOCUMENTOIDENTIDAD"));
        c.add(Restrictions.ne("valor", "Partida de nacimiento (solo a menores)"));
    	c.setProjection(Projections.property("valor"));
        return c.list();
    }
    
    @Log
    public GenericSelectModel<UnidadOrganica> getBeansUO() {
        Criteria c = session.createCriteria(UnidadOrganica.class)
                .add(Restrictions.eq("entidad", _oi))
                .add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));

        List<UnidadOrganica> list = c.list();
     
        return new GenericSelectModel<UnidadOrganica>(list, UnidadOrganica.class, "den_und_organica", "id", _access);
    }

    /*

    private boolean cancelar = false;

    void onSelectedFromCancelar() {
        cancelar = true;

        //reseteo el formulario

    }

    void onSelectedFromCancelarLegajo(){
        cancelar = true;
    }
    */

    public Cargoxunidad getcargo(){
       return cargo;
    }


    public CargosSelectModel<Cargoxunidad> getBeans(){
       return _beans;
    }

    private Cargoxunidad cargo;

    public Cargoxunidad getCargo() {
        return cargo;
    }

    public void setCargo(Cargoxunidad cargo) {
        this.cargo = cargo;
    }


    public boolean getNoUsuarioGeneral() {
        return !getUsuarioGeneral();
    }

    @Log
    void onValidateFromformulariotrabajadornuevolegajocargo() {
        // Seguimos sólo si hay puestos disponibles.

        if(Helpers.getCantPuestosOcupadosCargo(session, cargo) >= cargo.getCtd_puestos_total() ){
            formulariotrabajadornuevolegajocargo.recordError(Errores.ERROR_CARGO_OCUPADO);
        }


        Date comparacion = new Date();
        comparacion.setHours(23);
        comparacion.setMinutes(59);
        comparacion.setSeconds(59);
        if (fec_inicio.after(comparacion)) {
            // si es posterior a hoy
            formulariotrabajadornuevolegajocargo.recordError(Errores.ERROR_FECHA_PREVIA_ACTUAL);
        }
    }

    @Log
    Object onFailureFromformulariotrabajadornuevolegajocargo() {
        return this;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariotrabajadornuevolegajocargo()
    {
        /*if(nuevo.getTipoDocumento().equals("DNI")){
            if(nuevo.getNroDocumento().SIZE != 8){
                return this;
            }
        }*/
        //System.out.println("----------------- trabajadorExistenteOtraEntidad success from formulario trabajador nuevo legajo cargo "+trabajadorTieneCargoOtraEntidad);
        System.out.println("--------- nuevo legajo "+nuevoLegajo.getCod_legajo()+" nuevo "+nuevo.getNombres());
        session.saveOrUpdate(nuevo);
        cargoAsignado = new CargoAsignado();
        cargoAsignado.setCargoxunidad(cargo);
        if(!trabajadorExiste) {
            nuevoLegajo.setEntidad(_oi);
            session.saveOrUpdate(nuevoLegajo);
            cargoAsignado.setLegajo(nuevoLegajo);
        } else {
            session.saveOrUpdate(legajoExistente);
            cargoAsignado.setLegajo(legajoExistente);
        }
        cargoAsignado.setCtd_per_superv(ctd_per_superv);
        cargoAsignado.setFec_inicio(fec_inicio);
        cargoAsignado.setEstado(Constantes.ESTADO_ACTIVO);
        cargoAsignado.setTrabajador(nuevo);
        session.saveOrUpdate(cargoAsignado);
        nuevo.getCargosAsignados().add(cargoAsignado);
        session.saveOrUpdate(nuevo);
        
        //System.out.println("------------------ trabajador numero "+nuevo.getNroDocumento());
        
        if(!trabajadorExiste)
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(nuevo.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_TRABAJADOR);
        else
            new Logger().loguearOperacion(session, loggedUser, String.valueOf(legajoExistente.getid()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_LEGAJO);

        primerPaso = true; // para la próxima sesión
        session.flush();
        formulariotrabajadornuevolegajocargo.clearErrors();
        entidadTrabajadorTieneCargo = null;
        trabajadorTieneCargoOtraEntidad = false;
        nuevoLegajo = null;
        nuevo = null;
        return index;
    }

    public boolean getSegundoPaso() {
        return !primerPaso;
    }

    public boolean getUsuarioGeneral() {
        return Helpers.esMultiOrganismo(loggedUser);
    }
    
    @Log
    Object onSuccessFromformulariotrabajadornuevo()
    {
    /*
        if(cancelar) {
            cancelar = false;
            return index;
        }
     * 
     */
        // busco legajos en este organismo con este trabajador.
        List<Legajo> legajos=  session.createCriteria(Legajo.class)
            .createAlias("trabajador","trabajador")
            .add(Restrictions.eq("trabajador.nroDocumento", nuevo.getNroDocumento()))
            .add(Restrictions.like("trabajador.tipoDocumento", nuevo.getTipoDocumento()))
            .add(Restrictions.like("trabajador.nombres", nuevo.getNombres()).ignoreCase())
            .add(Restrictions.like("trabajador.apellidoMaterno", nuevo.getApellidoMaterno()).ignoreCase())
            .add(Restrictions.like("trabajador.apellidoPaterno", nuevo.getApellidoPaterno()).ignoreCase())
            .add(Restrictions.eq("entidad", _oi))
            .list();

        if (legajos.size() > 0) {
            trabajadorExiste = true;

            /*formulariotrabajadornuevo.recordError(Errores.ERROR_TRABAJADOR_EXISTENTE);
            return this;*/
            primerPaso = false; // seguimos asignando legajo
            legajoExistente = legajos.get(0);
            nuevo = legajoExistente.getTrabajador();
            return this;

        }  
            
        trabajadorExiste = false;
        //busco si el trabajador ya tiene un cargo en una otra entidad o en esta, pero no esta activo
        List<CargoAsignado> CargosAsignadosTrabajador =  session.createCriteria(CargoAsignado.class)
            .createAlias("trabajador","trabajador")
            .createAlias("legajo","legajo")
            .add(Restrictions.eq("trabajador.nroDocumento", nuevo.getNroDocumento()))
            .add(Restrictions.like("trabajador.tipoDocumento", nuevo.getTipoDocumento()))
            .add(Restrictions.like("trabajador.nombres", nuevo.getNombres()).ignoreCase())
            .add(Restrictions.like("trabajador.apellidoMaterno", nuevo.getApellidoMaterno()).ignoreCase())
            .add(Restrictions.like("trabajador.apellidoPaterno", nuevo.getApellidoPaterno()).ignoreCase())
            .add(Restrictions.ne("legajo.entidad", _oi))
            .add(Restrictions.like("estado", Constantes.ESTADO_ACTIVO))
            .list();
        
        if (CargosAsignadosTrabajador.size() > 0) {
            trabajadorTieneCargoOtraEntidad = true;
            entidadTrabajadorTieneCargo = CargosAsignadosTrabajador.get(0).getLegajo().getEntidad().getDenominacion();
        }
        
         // Buscamos si ya existe un trabajador igual a ese en algún otro lado, pero no tiene un cargo asignado
        trabajadoresEncontrados=  session.createCriteria(Trabajador.class)
                .add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()))
                .add(Restrictions.like("tipoDocumento", nuevo.getTipoDocumento()))
                .add(Restrictions.like("nombres", nuevo.getNombres()).ignoreCase())
                .add(Restrictions.like("apellidoMaterno", nuevo.getApellidoMaterno()).ignoreCase())
                .add(Restrictions.like("apellidoPaterno", nuevo.getApellidoPaterno()).ignoreCase())
                .list();


        if (trabajadoresEncontrados.size() > 0) {
            primerPaso = false; // seguimos asignando legajo

            return this;

        }
        
        //Buscamos trabajadores con el mismo dni/carnet
        if (nuevo.getTipoDocumento().equals("DNI") || nuevo.getTipoDocumento().equals("Carnet extranjería")) {
            Criteria c = session.createCriteria(Trabajador.class);
            c.add(Restrictions.eq("nroDocumento", nuevo.getNroDocumento()));
            c.add(Restrictions.like("tipoDocumento", nuevo.getTipoDocumento()));
              trabajadoresEncontrados=c.list();

            if (trabajadoresEncontrados.size() > 0) {
                // No pueden haber 2 trabajadores con el mismo dni
                formulariotrabajadornuevo.recordError(Errores.ERROR_TRABAJADOR_DNI_EXISTENTE);
                formulariotrabajadornuevo.recordError("Apellido Paterno: "
                        + trabajadoresEncontrados.get(0).getApellidoPaterno());
                formulariotrabajadornuevo.recordError("Apellido Materno: "
                        + trabajadoresEncontrados.get(0).getApellidoMaterno());
                formulariotrabajadornuevo.recordError("Nombres: "
                        + trabajadoresEncontrados.get(0).getNombres());

                return this;
            }

        }
        
        primerPaso = false;

        return this;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariounidadorganica() {
        Criteria c = session.createCriteria(Cargoxunidad.class)
        .add(Restrictions.eq("unidadorganica", unidadorganica))
        .add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA));
        
        unidadSeleccionada = true;
        
        return cargosZone.getBody();
    }
    
    @Log
    void onActionFromCancelar() {
        primerPaso = true;
        nuevo = new Trabajador();
        entidadTrabajadorTieneCargo = "";
        trabajadorTieneCargoOtraEntidad = false;
        unidadSeleccionada = false;
        unidadorganica = new UnidadOrganica();
    }
    
    @Log
    void onActionFromCancelarTrabajador() {
        primerPaso = true;
        trabajadorExiste = false;
        trabajadorTieneCargoOtraEntidad = false;
        entidadTrabajadorTieneCargo = "";
        nuevo = new Trabajador();
    }

    @Log
    void onActionFromCancelarNuevo() {
        primerPaso = true;
        trabajadorTieneCargoOtraEntidad = false;
        entidadTrabajadorTieneCargo = "";
        nuevo = new Trabajador();
    }

    @Log
    void onPrepareFromformulariotrabajadornuevo()
    {
        if(nuevo == null) {
            nuevo = new Trabajador();
        }
        trabajadorTieneCargoOtraEntidad = false;
    }


    @Log
    void onPrepareFromformulariotrabajadornuevolegajocargo()
    {
        if(trabajadorTieneCargoOtraEntidad){
            formulariotrabajadornuevolegajocargo.recordError(Errores.ERROR_TRABAJADOR_TIENE_CARGO);
                formulariotrabajadornuevolegajocargo.recordError(""+entidadTrabajadorTieneCargo);
        }
        
           fec_inicio = new Date();
           List<Cargoxunidad> list = session.createCriteria(Cargoxunidad.class)
                   .createAlias("unidadorganica", "unidadorganica")
                   .add(Restrictions.eq("unidadorganica", unidadorganica))
                   //.add(Restrictions.ne("unidadorganica.estado", UnidadOrganica.ESTADO_BAJA))
                   //.add(Restrictions.ne("estado", Constantes.ESTADO_BAJA))
                   .list(); 
            _beans = new CargosSelectModel<Cargoxunidad>(list,Cargoxunidad.class,"cod_cargo", "den_cargo","id",_access);
            if(nuevoLegajo == null) {
                    nuevoLegajo = new Legajo();
                    nuevoLegajo.setEntidad(_oi);
                    nuevoLegajo.setTrabajador(nuevo);
            }
            if(cargoAsignado == null) {
                cargoAsignado = new CargoAsignado();
            }
            trabajadorTieneCargoOtraEntidad = false;
            
            //Vemos si tiene sanciones
            /*if(helpers.Sanciones.tieneSanciones(nuevo.getTipoDocumento(), nuevo.getNroDocumento(), session, errores)) {
                System.out.println("pantalla "+helpers.Sanciones.formatoEnvelope(helpers.Sanciones.muestroSanciones(nuevo.getTipoDocumento(), nuevo.getNroDocumento(), session, errores)));
                envelope.setContents(helpers.Sanciones.formatoEnvelope(helpers.Sanciones.muestroSanciones(nuevo.getTipoDocumento(), nuevo.getNroDocumento(), session, errores)));
            }*/
    }

    @Log
    public boolean getPuedeEditar() {
        //System.out.println("----------regimengruponivel nivel  "+regimengruponivel.getNivelRemunerativo());
        return Permisos.puedeEscribir(loggedUser, _oi);
    }

    @Log
    public boolean getNoEditable() {
        return !getPuedeEditar();
    }

    @Log
    void onActivate() {

        if (primerPaso == null)
            primerPaso = true;
        
        if(trabajadorExiste == null)
            trabajadorExiste = false;
        
        if(trabajadorTieneCargoOtraEntidad == null)
            trabajadorTieneCargoOtraEntidad = false;
        
        if(entidadTrabajadorTieneCargo == null)
            entidadTrabajadorTieneCargo = "";

    }
     
}
