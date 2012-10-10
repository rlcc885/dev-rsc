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

public class FamiliaresEditor {

    @Property
    @SessionState
    private Usuario _usuario;
    @Property
    @SessionState
    private Entidad _oi;
    @Inject
    private Session session;
    @InjectComponent
    private Envelope envelope;
    @Component(id = "formulariomensajesf")
    private Form formulariomensajesf;
    @InjectComponent
    private Zone mensajesFZone;
    @InjectComponent
    private Zone familiaresZone;
    private int elemento = 0;
    @Parameter
    @Property
    private Trabajador actual;
    @Property
    @Persist
    private Familiar familiarActual;
    @Property
    private List<Familiar> listaParentescoP;
    @Property
    private List<Familiar> listaParentescoC;
    @Property
    private List<Familiar> listaParentescoCY;
    @Property
    @Persist
    private boolean bdni;
    //Listado de familiares
    @InjectComponent
    private Zone listaFamiliaresZone;
    @Persist
    @Property
    private Familiar listafamiliar;
    @Property
    @Persist
    private String valsexo;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private String nuevafecha;
//*****************************
    @Persist
    @Property
    private Boolean vdetalle;
    @Persist
    @Property
    private Boolean vformulario;
    @Persist
    @Property
    private Boolean veliminar;
    @Persist
    @Property
    private Boolean veditar;
    @Persist
    @Property
    private Boolean vguardar;
    @Property
    @Persist
    private boolean bvalidausuario;
    @Persist
    @Property
    private Boolean vinserta;
    @SessionState
    private UsuarioAcceso usua;
    @Persist
    @Property
    private Boolean editando;
    @Persist
    @Property
    private Date fecha;
    //Inicio de lac carga de la pagina
    @Persist
    @Property
    private Boolean vrevisado;
    
    @Log
    void setupRender() {
        // No mover la inicializacion de variables
        editando = false;
        resetRegistro();
        //accesos();
    }

    @Log
    public void accesos() {
        bvalidausuario = false;
        vformulario = false;
        vinserta = false;
        veditar = false;
        veliminar = false;
        vdetalle = true;
        bdni = true;
        vguardar = false;
        if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
            bvalidausuario = true;
        }
        if (usua.getAccesoupdate() == 1) {
            veditar = true;
            vdetalle = false;
            bdni = false;
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
            vinserta = true;
            vformulario = true;
            vguardar = true;
            vdetalle = false;
            bdni = false;
            if (_usuario.getRolid() == 2 || _usuario.getRolid() == 3) {
                vrevisado = true;
            }

        }
    }

    @Log
    void resetRegistro() {
        familiarActual = new Familiar();
        editando = false;
        nuevafecha = "";
//        vguardar = true;
//        vdetalle = false;
        bdni = false;
        valsexo = null;
        formulariomensajesf.clearErrors();
        accesos();
    }

    @Log
    public List<Familiar> getListadoFamiliares() {
        Criteria c = session.createCriteria(Familiar.class);
        c.add(Restrictions.eq("trabajador", actual));
        nroregistros = Integer.toString(c.list().size());
        return c.list();
    }
    
    @Persist
    @Property
    private String nroregistros;
    
    //para obtener datos del Parentesco
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanParentesco() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("GRADOPARENTESCO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del sexo
    @Log
    public List<String> getBeanSexo() {
        return Helpers.getValorTablaAuxiliar("SEXO", session);
    }

    //para obtener datos del Tipo de documento
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTiposDoc() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

    //para obtener datos del estado civil
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanEstadoCivil() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("ESTADOCIVIL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }

//    void onSelectedFromCancel() {
//        elemento = 2;
//    }

//    void onSelectedFromReset() {
//        elemento = 1;
//    }

    void onSelectedFromGuardar() {
        System.out.println("onSelectedFromGuardar");
        elemento = 3;
    }

    @Log
    Object onReset() {
        resetRegistro();
        return familiaresZone.getBody();
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

    @Property
    @Persist
    private DatoAuxiliar valEstadoCivil;
    @Log
    @CommitAfter
    Object onSuccessFromFormulariofamiliares() {

        if (nuevafecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fecha = (Date) formatoDelTexto.parse(nuevafecha);
                familiarActual.setFechaNacimiento(fecha);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        if (valsexo != null) {
            if (valsexo.equals("MASCULINO")) {
                familiarActual.setSexo("M");
            } else if (valsexo.equals("FEMENINO")) {
                familiarActual.setSexo("F");
            }
        } else {
            familiarActual.setSexo(null);
        }
        
        if (valEstadoCivil != null)
        {
          familiarActual.setEstadoCivil(valEstadoCivil);  
        }
        else {familiarActual.setEstadoCivil(null);
        }
        
//        if(elemento==3){
        Logger logger = new Logger();
        String consulta = "SELECT COUNT(*) FROM RSC_FAMILIAR F JOIN RSC_DATOAUXILIAR DA ON (F.PARENTESCO_ID = DA.ID)"
                + "WHERE DA.CODIGO = " + familiarActual.getParentesco().getCodigo() + " AND F.TRABAJADOR_ID = '" + actual.getId() + "'";

        if (!editando) { // Si no edita, está insertando
            //Codigo de Progenitor = 4
            if (familiarActual.getParentesco().getCodigo() == 4) {
                Criteria c1 = session.createCriteria(Familiar.class);
                c1.add(Restrictions.eq("trabajador", actual));
                c1.add(Restrictions.eq("parentesco", familiarActual.getParentesco()));
                listaParentescoP = c1.list();
            }
            //Codigo de Conviviente = 2
            if (familiarActual.getParentesco().getCodigo() == 2) {
                Criteria c2 = session.createCriteria(Familiar.class);
                c2.add(Restrictions.eq("trabajador", actual));
                c2.add(Restrictions.eq("parentesco", familiarActual.getParentesco()));
                listaParentescoC = c2.list();
            }
            if (_usuario.getRolid() == 1) { // Si es trabajador 
                familiarActual.setAgregadoTrabajador(true);
                familiarActual.setValidado(false);
            }
            if (_usuario.getRolid() == 1) {
                if (familiarActual.getParentesco().getCodigo() == 1 || familiarActual.getParentesco().getCodigo() == 3) {
                    envelope.setContents("No puede agregar ese tipo de pariente (Hijo / Conyuge)");
                    return actualizar();
                }
            }

            Query q1 = session.createSQLQuery(consulta);
            int numFamDupl = Integer.parseInt(q1.list().get(0).toString());
            if (numFamDupl > 0 && familiarActual.getParentesco().getCodigo() != 3) {
                envelope.setContents("No es posible registrar mas de un Pariente que no sea un hijo");
                return actualizar();
            }

            Criteria c = session.createCriteria(Familiar.class);
            c.add(Restrictions.eq("nroDocumento", familiarActual.getNroDocumento()));

            if (!c.list().isEmpty()) {
                envelope.setContents("nro de dni duplicado");
                return actualizar();
            }
        } else {
            if (_usuario.getRolid() == 1) {
                if (familiarActual.getParentesco().getCodigo() == 1 || familiarActual.getParentesco().getCodigo() == 3) {
                    envelope.setContents("No puede agregar ese tipo de pariente (Hijo / Conyuge)");
                    return actualizar();
                }
            }
            Query q1 = session.createSQLQuery(consulta + "AND F.ID !='" + idVerificacion + "'");
            int numFamDupl = Integer.parseInt(q1.list().get(0).toString());
            if (numFamDupl > 0 && familiarActual.getParentesco().getCodigo() != 3) {
                envelope.setContents("No es posible registrar mas de un Pariente que no sea un hijo");
                return actualizar();
            }
            Criteria c = session.createCriteria(Familiar.class);
            c.add(Restrictions.eq("nroDocumento", familiarActual.getNroDocumento()));
            c.add(Restrictions.ne("id", idVerificacion));
            if (!c.list().isEmpty()) {
                envelope.setContents("nro de dni duplicado");
                return actualizar();
            }
        }

       if(valEstadoCivil!=null)     
      {
        if(familiarActual.getParentesco().getCodigo() == 1 && valEstadoCivil.getCodigo()==1)
        {
            envelope.setContents("El estado civil de un Conyuge no puede ser Soltero/a");
            return actualizar();
        }
      }
        // NUEVAS VALIDACIONES DNI
        if (familiarActual.getTipoDocumento().getCodigo()==1){
            if(familiarActual.getNroDocumento().length()>8){ 
                envelope.setContents("El número de documento debe tener 8 dígitos (y solo números)");   return actualizar();}
            try { Integer.parseInt(familiarActual.getNroDocumento());} catch (NumberFormatException ex) {
                envelope.setContents("El número de documento debe tener 8 dígitos (y solo números)"); return actualizar();}            
        }
        
        if (nuevafecha == null || nuevafecha.equalsIgnoreCase("")) {
            envelope.setContents("Debe ingresar la fecha");
            return actualizar();
        }else{
            // validacion fecha de nacimiento
            if (familiarActual.getFechaNacimiento().after(new Date())){
                   formulariomensajesf.recordError("La fecha de nacimiento debe ser menor a la fecha actual");            
                   return actualizar();
            }
            // validaciones con respecto al progenitor
        /*    if (familiarActual.getParentesco().getCodigo()==1){
                   if (familiarActual.getFechaNacimiento().after(actual.getFechaNacimiento())){
                   formulariomensajesf.recordError("La fecha de nacimiento del progenitor debe ser menor a la del trabajador");
                   }

                    
            }*/
            
            familiarActual.setTrabajador(actual);
            familiarActual.setEntidad(_oi);
            session.saveOrUpdate(familiarActual);
            session.flush();
            new Logger().loguearOperacion(session, _usuario, String.valueOf(familiarActual.getId()), (editando ? Logger.CODIGO_OPERACION_UPDATE : Logger.CODIGO_OPERACION_INSERT), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_FAMILIAR);    
            if (!editando) {
                logger.loguearEvento(session, logger.MODIFICACION_FAMILIAR, actual.getEntidad().getId(), actual.getId(), _usuario.getId(), logger.MOTIVO_FAMILIARES, familiarActual.getId());
            }
            if (familiarActual.getValidado() != null) {
                if (familiarActual.getValidado() == true) {
                    String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + familiarActual.getTrabajador().getId() + "' and tipoevento_id='" + logger.MODIFICACION_FAMILIAR + "' and tabla_id='" + familiarActual.getId() + "' and estadoevento=0";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
                    session.flush();
                }
            }
            editando = false;
            envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
            familiarActual = new Familiar();
            nuevafecha = "";
            valsexo = null;

            return actualizar();
        }
    }

    private MultiZoneUpdate actualizar() {
        //return new MultiZoneUpdate("listaFamiliaresZone", listaFamiliaresZone.getBody()).add("mensajesFZone", mensajesFZone.getBody());
        return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody()).add("listaFamiliaresZone", listaFamiliaresZone.getBody()).add("familiaresZone", familiaresZone.getBody());
    }
    @Persist
    private Long idVerificacion;

    @Log
    Object onActionFromEditar2(Familiar fam){
    return onActionFromEditar(fam);
    }
    
    @Log
    Object onActionFromEditar(Familiar fami) {
        familiarActual = fami;
        idVerificacion = familiarActual.getId();
        if (familiarActual.getFechaNacimiento() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            nuevafecha = formatoDeFecha.format(familiarActual.getFechaNacimiento());
        }

        if (familiarActual.getSexo() != null) {
            if (familiarActual.getSexo().equalsIgnoreCase("M")) {
                valsexo = "MASCULINO";
            } else if (familiarActual.getSexo().equalsIgnoreCase("F")) {
                valsexo = "FEMENINO";
            } else {
                valsexo = null;
            }
        } else {
            valsexo = null;
        }
        valEstadoCivil = familiarActual.getEstadoCivil();
        
        editando = true;
        accesos();
        vformulario=true;
        vguardar = true;
        return familiaresZone.getBody();
    }

    Object onActionFromDetalle(Familiar fami) {
        familiarActual = fami;
        if (familiarActual.getFechaNacimiento() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            nuevafecha = formatoDeFecha.format(familiarActual.getFechaNacimiento());
        }

        if (familiarActual.getSexo() != null) {
            if (familiarActual.getSexo().equalsIgnoreCase("M")) {
                valsexo = "MASCULINO";
            } else if (familiarActual.getSexo().equalsIgnoreCase("F")) {
                valsexo = "FEMENINO";
            } else {
                valsexo = null;
            }
        } else {
            valsexo = null;
        }
        valEstadoCivil = familiarActual.getEstadoCivil();
        editando = false;
        accesos();
        vdetalle = true;vformulario=true;
        bdni = true;
        vguardar = false;
        return familiaresZone.getBody();
    }

    @Log
    @CommitAfter
    Object onActionFromEliminar(Familiar fami) {
        
        new Logger().loguearOperacion(session, _usuario, String.valueOf(fami.getId()), Logger.CODIGO_OPERACION_DELETE, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_FAMILIAR);
        session.delete(fami);
        envelope.setContents("Familiar eliminado exitosamente.");
        resetRegistro();
        accesos();
        return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody()).add("listaFamiliaresZone", listaFamiliaresZone.getBody()).add("familiaresZone", familiaresZone.getBody());
    }

    @Log
    Object onActionFromDetalle3(Familiar fami) {
        return onActionFromDetalle(fami);
    }

}
