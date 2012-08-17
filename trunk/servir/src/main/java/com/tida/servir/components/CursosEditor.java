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
    private UsuarioAcceso usu;
    @Inject
    private PropertyAccess _access;
    //zonas
    @InjectComponent
    private Zone listadoZone;
    @InjectComponent
    @Property
    private Zone primeraZone;
//    @InjectComponent
//    @Property
//    private Zone segundaZone;  
    @InjectComponent
    @Property
    private Zone terceraZone;
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
    private Boolean vrevisado;
    private int elemento = 0;

    @Log
    @SetupRender
    private void inicio() {
        vrevisado = false;
        if (usu.getAccesoupdate() == 1) {
            veditar = true;
            vbotones = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }
        if (usu.getAccesodelete() == 1) {
            veliminar = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }
        if (usu.getAccesoreport() == 1) {
            vformulario = true;
            vbotones = true;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }
        }
        cursos = new Curso();
        votro = true;
        editando = false;
        System.out.println("Eliminar2" + usu.getAccesodelete() + usu.getAccesoreport() + usu.getAccesoupdate());
        //limpiar();
    }

    @Log
    public List<LkBusquedaCursos> getListacur() {
        Criteria c = session.createCriteria(LkBusquedaCursos.class);
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

    void onDenChanged() {
        valdenominacion = _request.getParameter("param");
    }

    void onOtroChanged() {
        valotrocentro = _request.getParameter("param");
    }

    @Log
    Object onActionFromEditar(Curso c) {
        cursos = c;
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
        
         if(cursos.getFechainicio()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(cursos.getFechainicio());
        }
        if(cursos.getFechafin()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_hasta=formatoDeFecha.format(cursos.getFechafin());
        }

        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody()).add("terceraZone", terceraZone.getBody());
    }

    @Log
    Object onActionFromDetalle(Curso c) {
        cursos = c;
        mostrar();
        vdetalle = true;
        vfechahasta = true;
        votro = true;
        vbotones = false;
        vformulario = true;
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("terceraZone", terceraZone.getBody());
    }

    @Log
    Object onActionFromDetalles(Curso c) {
        cursos = c;
        mostrar();
        vdetalle = true;
        vfechahasta = true;
        votro = true;
        vbotones = false;
        vformulario = true;
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("terceraZone", terceraZone.getBody());
    }

    @Log
    @CommitAfter
    Object onBorrarDato(Curso dato) {
        session.delete(dato);
        envelope.setContents("Curso del Trabajador Eliminado");
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody()).add("terceraZone", terceraZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromformularioprimero() {
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
        return primeraZone.getBody();
    }

//    @Log
//    @CommitAfter
//    Object onSuccessFromformulariosegundo(){
//        if(valestudiando){
//            vfechahasta=true;
//            valfec_hasta=null;
//        }
//        else{
//            vfechahasta=false;
//        }
//        return terceraZone.getBody();
//    }
    void onSelectedFromSave() {
        elemento = 1;
    }

    void onSelectedFromReset() {
        limpiar();
        editando = false;
        cursos = new Curso();
        if (usu.getAccesoreport() == 0) {
            vformulario = false;
        }
        elemento = 2;
    }

    void onSelectedFromCancel() {
        elemento = 3;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformulariotercero() {
        
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
        } else {
            vfechahasta = false;
        }
        if (elemento == 3) {
            elemento = 0;
            if (_usuario.getRolid() == 1) {
                return "Alerta";
            } else {
                return Busqueda.class;
            }
        } else if (elemento == 2) {
            elemento = 0;
        } else if (elemento == 1) {
            elemento = 0;
            formlistacursos.clearErrors();
            Logger logger = new Logger();
            if (valdenominacion == null) {
                formlistacursos.recordError("Tiene que ingresar la Denominación");
                return listadoZone.getBody();
            }
            if (valtipoestudio == null) {
                formlistacursos.recordError("Tiene que seleccionar el Tipo de Estudio");
                return listadoZone.getBody();
            }
            if (valcentroestudio == null) {
                formlistacursos.recordError("Tiene que seleccionar el Centro de Estudio");
                return listadoZone.getBody();
            }
            if (fecha_desde == null) {
                formlistacursos.recordError("Tiene que ingresar Fecha de Inicio");
                return listadoZone.getBody();
            }
            if (fecha_desde.after(new Date())) {
                formlistacursos.recordError("La fecha de inicio debe ser previa a la fecha actual.");
                return listadoZone.getBody();
            }
            if (validando() == false) {
                return listadoZone.getBody();
            }

            if (editando) {
                //editando
                if (usu.getAccesoreport() == 0) {
                    vformulario = false;
                }
                System.out.println("fuerae" + valfuera);
                cursos.setEstudiando(valestudiando);
                cursos.setFueradelpais(valfuera);

            } else {//guardando
                //System.out.println("Trabajadorrr"+actual);                    
                cursos.setTrabajador(actual);
                cursos.setEntidad(_usuario.getTrabajador().getEntidad());
                cursos.setValidado(false);
                if (valestudiando == null) {
                    cursos.setEstudiando(false);
                } else {
                    cursos.setEstudiando(valestudiando);
                }
                if (valfuera == null) {
                    cursos.setFueradelpais(false);
                } else {
                    cursos.setFueradelpais(valfuera);
                }
                if (_usuario.getRolid() == 1) {
                    cursos.setAgregadotrabajador(true);
                } else {
                    cursos.setAgregadotrabajador(false);
                }
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
            if (!editando) {
                System.out.println("primerooooooo" + editando);
                logger.loguearEvento(session, logger.MODIFICACION_CURSOS, actual.getEntidad().getId(), actual.getId(), logger.MOTIVO_PERSONALES_CURSOS, cursos.getId());
            }
            if (valrevisado != null) {
                if (valrevisado == true) {
                    System.out.println("segundoooooo");
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
        return new MultiZoneUpdate("primeraZone", primeraZone.getBody()).add("listadoZone", listadoZone.getBody()).add("terceraZone", terceraZone.getBody());
    }

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

        System.out.println("resuuuuuu" + resultado);
        return resultado;
    }

    void mostrar() {
        valdenominacion = cursos.getDenominacion();
        valtipoestudio = cursos.getTipoestudio();
        valcentroestudio = cursos.getCentroestudio();
        valotrocentro = cursos.getOtrocentroestudio();
        fecha_desde = cursos.getFechainicio();
        fecha_hasta = cursos.getFechafin();
        valestudiando = cursos.getEstudiando();
        valrevisado = cursos.getValidado();
        valfuera = cursos.getFueradelpais();
        System.out.println("fueram" + valfuera);
    }

    void seteo() {
        cursos.setDenominacion(valdenominacion);
        cursos.setTipoestudio(valtipoestudio);
        cursos.setCentroestudio(valcentroestudio);
        cursos.setOtrocentroestudio(valotrocentro);
        cursos.setFechainicio(fecha_desde);
        cursos.setFechafin(fecha_hasta);
    }

    void limpiar() {
        cursos = new Curso();
        valdenominacion = null;
        valtipoestudio = null;
        valcentroestudio = null;
        valotrocentro = null;
        valfec_desde = null;
        valfec_hasta = null;
        valestudiando = null;
        valfuera = null;
        valrevisado = null;
        vfechahasta = false;
    }
//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//       return Permisos.puedeEscribir(_usuario, _oi);
//    }
//    public boolean getEsTrabajadorEditable(){        
//        if(curso != null){
//            if((curso.getAgregadoTrabajador() == null)){
//                return true;
//            }
//                
//            if(curso.getAgregadoTrabajador() == false){
//                return true;
//            }
//        }
//        return _usuario.getTipo_usuario().equals(Usuario.TRABAJADOR);
//    }
//  public PrimaryKeyEncoder<Long, Curso> getEncoder()
//  {
//    return new PrimaryKeyEncoder<Long, Curso>()
//    {
//      public Long toKey(Curso value)
//      {
//        return value.getId();
//      }
//
//      public void prepareForKeys(List<Long> keys)
//      {
//      }
//
//      public Curso toValue(Long key)
//      {
//        return (Curso) session.get(Curso.class, key);
//      }
//
//            public Class<Long> getKeyType() {
//                return Long.class;
//            }
//    };
//  }
//
//  @Log
//  @CommitAfter
//  public Object onSuccess()
//  {
//      for(Curso cur : actual.cursos) {
//              if(cur.getFec_emision().after(new Date())) {
//
//                  Logger logger = new Logger();
//                  logger.loguearError(session, _usuario, cur.getId().toString(),
//                      Logger.CODIGO_ERROR_FECHA_DICTADO_PREVIA_ACTUAL,
//                      Errores.ERROR_FECHA_DICTADO_PREVIA_ACTUAL, Logger.TIPO_OBJETO_CURSO);
//
//                  _form.recordError(Errores.ERROR_FECHA_DICTADO_PREVIA_ACTUAL);
//                  return this;
//              }
//	  }
//
//    envelope.setContents(helpers.Constantes.CURSO_EXITO);
//	  //_form.clearErrors();
//	  return this;
//  }
//
//  @Log
//  @CommitAfter
//  Object onAddRow()
//  {
//    Curso cur = new Curso();
//    if(actual.getCursos() == null){
//        actual.setCursos(new ArrayList<Curso>());
//    }
//    cur.setTrabajador(actual);
//    if(_usuario.getTipo_usuario().equals(Usuario.TRABAJADOR))
//        cur.setAgregadoTrabajador(Boolean.TRUE);
//    actual.getCursos().add(cur);
//    session.saveOrUpdate(actual);
//    new Logger().loguearOperacion(session, _usuario, String.valueOf(cur.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);
//    return cur;
//  }
//
//  @Log
//  @CommitAfter
//  void onRemoveRow(Curso curso)
//  {
//    actual.getCursos().remove(curso);
//    session.delete(curso);
//    new Logger().loguearOperacion(session, _usuario, String.valueOf(curso.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_CURSO);
//  }
//
//  @Log
//    Object onFailure() {
//          return this;
//    }

    /*
     * @Log Object onValidate(){
     *
     * }
     */
}
