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
public class CursosEditor {
//Parameters of component
//    @SuppressWarnings("unused")
//    @Parameter(defaultPrefix = BindingConstants.LITERAL)
//    @Property
//    private String _zone;

    @Parameter
    @Property
    private Trabajador actual;
    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
//    @Component(id = "cursosEdit")
//    private Form _form;
    @Property
    @Persist
    private Curso cursos;
    @Property
    @Persist
    private LkBusquedaCursos listacurso;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
    @Property
    @SessionState
    private UsuarioAcceso usua;
    @Inject
    private PropertyAccess _access;
    //zonas
    @InjectComponent
    private Zone listadoZone;
    @InjectComponent
    @Property
    private Zone primeraZone;
    @InjectComponent
    @Property
    private Zone mensajescurso;
//    @InjectComponent
//    @Property
//    private Zone segundaZone;
//    @InjectComponent
//    @Property
//    private Zone terceraZone;
    @InjectComponent
    @Property
    private Zone centroZone;
    @Component(id = "formlistacursos")
    private Form formlistacursos;
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
    private String valfec_desde;
    @Persist
    @Property
    private String valfec_hasta;
    @Persist
    @Property
    private Date fecha_desde;
    @Persist
    @Property
    private Date fecha_hasta;
    @Persist
    @Property
    private Boolean valestudiando;
    @Persist
    @Property
    private Boolean valfuera;
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
    private Boolean vNoedita;
    @Persist
    @Property
    private Boolean vrevisado;
    private int elemento = 0;
    
    @Log
    void setupRender() {
        vrevisado = false;
        vdetalle = false;
        vformulario = false;
        vbotones = false;
        vNoedita = false;
        veditar=false;
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }
        if (usua.getAccesodelete() == 1) {
            veliminar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }
        if (usua.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            vNoedita = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }

        votro = true;
        editando = false;
        cursos = new Curso();
    }

    @Log
    public List<LkBusquedaCursos> getListacur() {
        Criteria c = session.createCriteria(LkBusquedaCursos.class);
        c.add(Restrictions.eq("trabajador", actual.getId()));
        nroregistros = Integer.toString(c.list().size());
        return c.list();
    }

    @Persist
    @Property
    private String nroregistros;
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoestudios() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOCURSO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    public GenericSelectModel<DatoAuxiliar> getCentroestudios() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CENTROESTUDIO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    @Log
    Object onActionFromEditar(Curso c) {
        limpiar();
        cursos = c;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        vNoedita = true;
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
            if (valcentroestudio.getCodigo() == 999999) {
                votro = false;
            } else {
                votro = true;
            }
        } else {
            votro = true;
        }

        if (cursos.getFechainicio() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde = formatoDeFecha.format(cursos.getFechainicio());
        }
        if (cursos.getFechafin() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta = formatoDeFecha.format(cursos.getFechafin());
        }

        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody());
    }
    
    @Log
    Object onActionFromEditardos(Curso c) {
        limpiar();
        cursos = c;
        vformulario = true;
        editando = true;
        vdetalle = false;
        vbotones = true;
        vNoedita = true;
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
            if (valcentroestudio.getCodigo() == 999999) {
                votro = false;
            } else {
                votro = true;
            }
        } else {
            votro = true;
        }

        if (cursos.getFechainicio() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde = formatoDeFecha.format(cursos.getFechainicio());
        }
        if (cursos.getFechafin() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta = formatoDeFecha.format(cursos.getFechafin());
        }

        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody());
    }
    
    @Log
    Object onActionFromDetalle(Curso c) {
        limpiar();
        cursos = c;
        mostrar();
        
        vfechahasta = true;
        votro = true;
        vbotones = false;
        vformulario = true;
        vNoedita = true;

        if (cursos.getFechainicio() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde = formatoDeFecha.format(cursos.getFechainicio());
        }
        if (cursos.getFechafin() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta = formatoDeFecha.format(cursos.getFechafin());
        }
        vdetalle = true;
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody());
    }

    @Log
    Object onActionFromDetalles(Curso c) {
        return onActionFromDetalle(c);
    }

    @Log
    @CommitAfter
    Object onBorrarDato(Curso dato) {        
        new Logger().loguearOperacion(session, _usuario, String.valueOf(dato.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);
        session.delete(dato);
        envelope.setContents("Curso del Trabajador Eliminado");
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody());//.add("terceraZone", terceraZone.getBody());
    }

    @Log
    void onSelectedFromSave() {
        elemento = 1;
    }

    @Log
    void onSelectedFromReset() {
        elemento = 2;
        if (vdetalle) {
            vformulario = false;
            vNoedita = false;
            if (usua.getAccesoreport() == 1) {
                vformulario = true;
                vdetalle = false;
                vbotones = true;
                limpiar();
                formlistacursos.clearErrors();
                editando = false;
                cursos = new Curso();
                vNoedita = true;
            }
        } else {
            if (usua.getAccesoreport() == 0) {
                vformulario = false;
                vdetalle = false;
                vbotones = false;
                vNoedita = false;
            } else {
                limpiar();
                formlistacursos.clearErrors();
                editando = false;
                cursos = new Curso();
            }
        }
    }

    @Log
    void onSelectedFromCancel() {
        elemento = 3;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioprimero() {
        if (valfec_desde != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_desde = (Date) formatoDelTexto.parse(valfec_desde);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        if (valfec_hasta != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha_hasta = (Date) formatoDelTexto.parse(valfec_hasta);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        
        System.out.println(valestudiando);
        System.out.println(valfec_hasta);
        System.out.println(fecha_hasta);
        if (valestudiando != null) {
            if (valestudiando) {
                vfechahasta = true;
                valfec_hasta = null;
                fecha_hasta = null;
//            } else {
//                vfechahasta = false;
            }
//        } else {
//            vfechahasta = false;
        }

        if (elemento == 3) {
            elemento = 0;
            if (_usuario.getRolid() == 1) {
                return "TrabajadorPersonal";
            } else {
                return Busqueda.class;
            }
        } else if (elemento == 2) {
            elemento = 0;
        } else if (elemento == 1) {
            elemento = 0;
            formlistacursos.clearErrors();
            Logger logger = new Logger();
            if (valtipoestudio == null) {
                formlistacursos.recordError("Tiene que seleccionar el Tipo de Estudio");
                return mensajescurso.getBody();
            }
            if (valdenominacion == null) {
                formlistacursos.recordError("Tiene que ingresar la Denominación");
                return mensajescurso.getBody();
            }
            if (valcentroestudio == null) {
                formlistacursos.recordError("Tiene que seleccionar el Centro de Estudio");
                return mensajescurso.getBody();
            } else {
                if (valcentroestudio.getCodigo() == 999999) {
                    if ("".equals(valotrocentro) || valotrocentro == null) {
                        formlistacursos.recordError("Ingrese denominación del Centro de Estudio");
                        return mensajescurso.getBody();
                    }
                }
            }
            if (fecha_desde == null) {
                formlistacursos.recordError("Tiene que ingresar Fecha de Inicio");
                return mensajescurso.getBody();
            }
            if (fecha_desde.after(new Date())) {
                formlistacursos.recordError("La fecha de inicio debe ser previa a la fecha actual.");
                return mensajescurso.getBody();
            }
            if (validando() == false) {
                return mensajescurso.getBody();
            }

            if (!editando) {
                cursos.setTrabajador(actual);
                cursos.setEntidad(_oi);
                cursos.setValidado(false);
                if (_usuario.getRolid() == 1) {
                    cursos.setAgregadotrabajador(true);
                } else {
                    cursos.setAgregadotrabajador(false);
                }
            } else {
                if (usua.getAccesoreport() == 0) {
                    vformulario = false;
                    vbotones = false;
                    vNoedita = false;
                }
            }
            if (valestudiando == null) {
                cursos.setEstudiando(false);
            } else {
                cursos.setEstudiando(valestudiando);
                cursos.setFechafin(null);
            }
            if (valfuera == null) {
                cursos.setFueradelpais(false);
            } else {
                cursos.setFueradelpais(valfuera);
            }
            if (vrevisado == true) {
                if (valrevisado == null) {
                    cursos.setValidado(false);
                } else {
                    cursos.setValidado(valrevisado);
                }

            }
            seteo();
            session.saveOrUpdate(cursos);
            session.flush();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(cursos.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);    
            if (!editando) {
                logger.loguearEvento(session, logger.MODIFICACION_CURSOS, actual.getEntidad().getId(), actual.getId(), _usuario.getId(), logger.MOTIVO_PERSONALES_CURSOS, cursos.getId());
            }
            if (valrevisado != null) {
                if (valrevisado == true) {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + cursos.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_CURSOS + "' and tabla_id='" + cursos.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }
            editando = false;
            limpiar();
            formlistacursos.clearErrors();
            envelope.setContents("Cursos del Trabajador Modificados Exitosamente");
        }
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody()).
                //                add("terceraZone", terceraZone.getBody()).
                add("mensajescurso", mensajescurso.getBody());
    }

    @Log
    boolean validando() {
        boolean resultado = true;
        if (valestudiando != null) {
            if (valestudiando == false) {
                if (valfec_hasta == null) {
                    formlistacursos.recordError("Debe ingresar Fecha de Fin");
                    resultado = false;
                    elemento = 0;
                } else if (fecha_hasta.after(new Date())) {
                    formlistacursos.recordError("La fecha de fin debe ser previa a la fecha actual.");
                    resultado = false;
                    elemento = 0;
                } else if (fecha_desde.after(fecha_hasta)) {
                    formlistacursos.recordError("La fecha de fin no pueden ser menor a la fecha de inicio");
                    resultado = false;
                    elemento = 0;
                } else if (fecha_desde.equals(fecha_hasta)) {
                    resultado = false;
                    elemento = 0;
                    formlistacursos.recordError("Las fecha de inicio no pude ser igual a la fecha de fin");
                }
            }
        } else {
            if (valfec_hasta == null) {
                formlistacursos.recordError("Tiene que ingresar Fecha de Fin");
                resultado = false;
                elemento = 0;
            } else if (fecha_hasta.after(new Date())) {
                formlistacursos.recordError("La fecha de fin debe ser previa a la fecha actual.");
                resultado = false;
                elemento = 0;
            } else if (fecha_desde.after(fecha_hasta)) {
                formlistacursos.recordError("La fecha de fin no pueden ser menor a la fecha de inicio");
                resultado = false;
                elemento = 0;
            } else if (fecha_desde.equals(fecha_hasta)) {
                resultado = false;
                elemento = 0;
                formlistacursos.recordError("Las fecha de inicio no pude ser igual a la fecha de fin");
            }
        }
        return resultado;
    }

    @Log
    void mostrar() {
        valdenominacion = cursos.getDenominacion();
        valtipoestudio = cursos.getTipocurso();
        valcentroestudio = cursos.getCentroestudio();
        valotrocentro = cursos.getOtrocentroestudio();
        fecha_desde = cursos.getFechainicio();
        fecha_hasta = cursos.getFechafin();
        valestudiando = cursos.getEstudiando();
        valrevisado = cursos.getValidado();
        valfuera = cursos.getFueradelpais();
    }

    @Log
    void seteo() {
        cursos.setDenominacion(valdenominacion);
        cursos.setTipocurso(valtipoestudio);
        cursos.setCentroestudio(valcentroestudio);
        cursos.setOtrocentroestudio(valotrocentro);
        cursos.setFechainicio(fecha_desde);
        cursos.setFechafin(fecha_hasta);
    }

    @Log
    void limpiar() {
        cursos = new Curso();
        valdenominacion = null;
        valtipoestudio = null;
        valcentroestudio = null;
        valotrocentro = "";
        valfec_desde = null;
        valfec_hasta = null;
        valestudiando = null;
        valfuera = null;
        valrevisado = null;
        vfechahasta = false;
    }

    @Log
    Object onValueChangedFromCentroestudio(DatoAuxiliar dato) {
        if (dato != null) {
            if (dato.getCodigo() == 999999) {
                votro = false;
            } else {
                votro = true;
                valotrocentro = "";
            }
        } else {
            votro = true;
        }
        return new MultiZoneUpdate("centroZone", centroZone.getBody());
    }
}
