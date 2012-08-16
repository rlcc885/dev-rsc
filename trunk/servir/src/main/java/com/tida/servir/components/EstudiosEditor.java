package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.pages.Busqueda;
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
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class EstudiosEditor {
//Parameters of component

    @Inject
    private Session session;
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
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;
    @Persist
    @Property
    private Date fecha_hasta;
    @Persist
    @Property
    private String valfec_hasta;
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
    private int elemento = 0;

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

    @Log
    void onDenChanged() {
        valdenominacion = _request.getParameter("param");
    }

    @Log
    void onOtroChanged() {
        valotrocentro = _request.getParameter("param");
    }

    @Log
    void onColeChanged() {
        valcolegio = _request.getParameter("param");
    }

    @Log
    void onColegChanged() {
        valcolegiatura = _request.getParameter("param");
    }

    @Log
    //@CommitAfter
    Object onSuccessFromformularioaltaestudio() {
        if (valcentroestudio != null) {
            if (valcentroestudio.getCodigo() == 999999) {
                votro = false;
            } else {
                votro = true;
                valotrocentro = null;
            }
        } else {
            votro = true;
        }
        return primerZone.getBody();
    }

    @Log
    void onSelectedFromSave() {
        elemento = 1;
    }

    @Log
    void onSelectedFromReset() {
        limpiar();
        editando = false;
        estudio = new Estudios();
        if (usua.getAccesoreport() == 0) {
            vformulario = false;
        }
        elemento = 2;
    }

    @Log
    void onSelectedFromCancel() {
        elemento = 3;
    }

    @Log
    void onSelectedFromEstudiando() {
        elemento = 4;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariobotones() {
         
          if(valfec_desde!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_desde = (Date)formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }
          
            if(valfec_hasta!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_hasta = (Date)formatoDelTexto.parse(valfec_hasta);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }
        
        
        if (valestudiando != null) {
            if (valestudiando) {
                vfechahasta = true;
                valfec_hasta = null;
            } else {
                vfechahasta = false;
            }
        }
        if (elemento == 3) {
            if (_usuario.getRol().getId() == 1) {
                return "Alerta";
            } else {
                return Busqueda.class;
            }
        } else if (elemento == 2) {
        } else if (elemento == 1) {
            formlistaestudios.clearErrors();
            Logger logger = new Logger();
            if (valdenominacion == null) {
                formlistaestudios.recordError("Tiene que ingresar la Denominación");
                return listaZone.getBody();
            }
            if (valtipoestudio == null) {
                formlistaestudios.recordError("Tiene que seleccionar el Tipo de Estudio");
                return listaZone.getBody();
            }
            if (valcentroestudio == null) {
                formlistaestudios.recordError("Tiene que seleccionar el Centro de Estudio");
                return listaZone.getBody();
            }
            if (fecha_desde == null) {
                formlistaestudios.recordError("Tiene que ingresar Fecha de Inicio");
                return listaZone.getBody();
            }
            if (fecha_desde.after(new Date())) {
                formlistaestudios.recordError("La fecha de inicio debe ser previa a la fecha actual.");
                return listaZone.getBody();
            }
            if (validando() == false) {
                return listaZone.getBody();
            }
            if (editando) {
                //editando
                if (usua.getAccesoreport() == 0) {
                    vformulario = false;
                }
                estudio.setEstudiando(valestudiando);
            } else {//guardando

                estudio = new Estudios();
//                System.out.println("Trabajadorrr"+actual);
                estudio.setTrabajador(actual);
                estudio.setEntidad(_usuario.getTrabajador().getEntidad());
                estudio.setValidado(false);
                if (valestudiando == null) {
                    estudio.setEstudiando(false);
                } else {
                    estudio.setEstudiando(valestudiando);
                }
                if (_usuario.getRol().getId() == 1) {
                    estudio.setAgregadotrabajador(true);
                } else {
                    estudio.setAgregadotrabajador(false);
                }

            }
            if (vrevisado == true) {
                //System.out.println("aquiiii"+valrevisado);
                if (valrevisado == null) {
                    estudio.setValidado(false);
                } else {
                    estudio.setValidado(valrevisado);
                }

            }
            seteo();
            session.saveOrUpdate(estudio);
            session.flush();
            if (!editando) {
                logger.loguearEvento(session, logger.MODIFICACION_ESTUDIOS, actual.getEntidad().getId(), actual.getId(), logger.MOTIVO_PERSONALES_ESTUDIOS, estudio.getId());
            }
            if (valrevisado != null) {
                if (valrevisado == true) {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + estudio.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_ESTUDIOS + "' and tabla_id='" + estudio.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }
            editando = false;
            limpiar();
            formlistaestudios.clearErrors();
            envelope.setContents("Estudios del Trabajador Modificados Exitosamente");
        }

        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody()).add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody());
    }

    @Log
    boolean validando() {
        boolean resultado = true;
        if (valestudiando != null) {
            if (valestudiando == false) {

                if (valfec_hasta == null) {
                    formlistaestudios.recordError("Debe ingresar Fecha de Fin");
                    resultado = false;
                } else if (fecha_hasta.after(new Date())) {
                    formlistaestudios.recordError("La fecha de fin debe ser previa a la fecha actual.");
                    resultado = false;
                } else if (fecha_desde.after(fecha_hasta)) {
                    formlistaestudios.recordError("La fecha de fin no pueden ser menor a la fecha de inicio");
                    resultado = false;
                } else if (fecha_desde.equals(fecha_hasta)) {
                    resultado = false;
                    formlistaestudios.recordError("Las fecha de inicio no pude ser igual a la fecha de fin");
                }
            }
        } else {
            if (valfec_hasta == null) {
                formlistaestudios.recordError("Tiene que ingresar Fecha de Fin");
                resultado = false;
            } else if (fecha_hasta.after(new Date())) {
                formlistaestudios.recordError("La fecha de fin debe ser previa a la fecha actual.");
                resultado = false;
            } else if (fecha_desde.after(fecha_hasta)) {
                formlistaestudios.recordError("La fecha de fin no pueden ser menor a la fecha de inicio");
                resultado = false;
            } else if (fecha_desde.equals(fecha_hasta)) {
                resultado = false;
                formlistaestudios.recordError("Las fecha de inicio no pude ser igual a la fecha de fin");
            }
        }

        System.out.println("resuuuuuu" + resultado);
        return resultado;
    }

    @Log
    void mostrar() {
        valdenominacion = estudio.getDenominacion();
        valtipoestudio = estudio.getTipoestudio();
        valcentroestudio = estudio.getCentroestudio();
        valotrocentro = estudio.getOtrocentroestudio();
        valpais = estudio.getPais();
        if (ubigeoDomicilio == null) {
            ubigeoDomicilio = new Ubigeo();
        }
        ubigeoDomicilio.setDepartamento(estudio.getDepartamento());
        ubigeoDomicilio.setProvincia(estudio.getProvincia());
        ubigeoDomicilio.setDistrito(estudio.getDistrito());
        valcolegio = estudio.getColegio();
        valcolegiatura = estudio.getColegiatura();
        fecha_desde = estudio.getFechainicio();
        fecha_hasta = estudio.getFechafin();
        valestudiando = estudio.getEstudiando();
        valrevisado = estudio.getValidado();
    }

    @Log
    Object onActionFromEditar(Estudios estu) {
        estudio = estu;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        mostrar();
        if (valestudiando != null) {
            if (valestudiando) {
                vfechahasta = true;
            } else {
                vfechahasta = false;
            }
        } else {
            vfechahasta = false;
        }
        if (valcentroestudio != null) {
            if (valcentroestudio.getCodigo() == 9999999) {
                votro = false;
            } else {
                votro = true;
            }
        } else {
            votro = true;
        }
        
        if(estudio.getFechainicio()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(estudio.getFechainicio());
        }
        if(estudio.getFechafin()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(estudio.getFechafin());
        }

        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody()).add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody());
    }

    @Log
    Object onActionFromDetalle(Estudios estu) {
        estudio = estu;
        mostrar();
        vdetalle = true;
        vfechahasta = true;
        votro = true;
        vbotones = false;
        vformulario = true;
        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody());
    }

    @Log
    Object onActionFromDetalles(Estudios estu) {
        estudio = estu;
        mostrar();
        vdetalle = true;
        vfechahasta = true;
        votro = true;
        vbotones = false;
        vformulario = true;
        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody());
    }

    @Log
    @CommitAfter
    Object onBorrarDato(Estudios dato) {
        session.delete(dato);
        envelope.setContents("Estudio del Trabajador Eliminado");
        return new MultiZoneUpdate("primerZone", primerZone.getBody()).add("listaZone", listaZone.getBody()).add("segundoZone", segundoZone.getBody()).add("tercerZone", tercerZone.getBody());
    }

    @Log
//    @SetupRender
    void setupRender() {
        vrevisado = false;
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vbotones = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                vrevisado = true;
            }
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                vrevisado = true;
            }
        }
        if (usua.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            if (_usuario.getRol().getId() == 2 || _usuario.getRol().getId() == 3) {
                vrevisado = true;
            }
        }

        votro = true;
        editando = false;
        limpiar();
    }

    @Log
    void seteo() {
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
        estudio.setFechainicio(fecha_desde);
        estudio.setFechafin(fecha_hasta);
        //estudio.setEstudiando(valestudiando);
    }

    @Log
    void limpiar() {
        estudio = new Estudios();
        valdenominacion = null;
        valtipoestudio = null;
        valcentroestudio = null;
        valotrocentro = null;
        valpais = null;
        ubigeoDomicilio = new Ubigeo();
        valcolegio = null;
        valcolegiatura = null;
        valfec_desde = null;
        valfec_hasta = null;
        valestudiando = null;
        valrevisado = null;
        vfechahasta = false;
        
    }
}
