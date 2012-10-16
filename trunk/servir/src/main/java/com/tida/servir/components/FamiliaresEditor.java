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
import org.hibernate.criterion.Projections;
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
        public Boolean getEsTrabajador(){
         if (_usuario.getRolid() == 1 && listafamiliar.getParentesco().getCodigo()==3)
         {return Boolean.FALSE;}
            return Boolean.TRUE;
        }
        
    @Log
    void resetRegistro() {
        familiarActual = new Familiar();
        editando = false;
        nuevafecha = "";
        valEstadoCivil = null;
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

    void onSelectedFromGuardar() {
        System.out.println("onSelectedFromGuardar");
        elemento = 3;
    }

    @Log
    Object onReset() {
        resetRegistro();
        formulariomensajesf.clearErrors();
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
    private Boolean validaciones(){
 
        if(valEstadoCivil!=null && (familiarActual.getParentesco().getCodigo() == 1 && valEstadoCivil.getCodigo()==1)){
       formulariomensajesf.recordError("El estado civil de un Conyuge no puede ser Soltero/a");
            return Boolean.FALSE;
        }
            // validacion fecha de nacimiento
        if (familiarActual.getFechaNacimiento().after(new Date())){
            formulariomensajesf.recordError("La fecha de nacimiento debe ser menor a la fecha actual");                         
            return Boolean.FALSE;
        }
        if (_usuario.getRolid() == 1) {
            if (familiarActual.getParentesco().getCodigo() == 1 || familiarActual.getParentesco().getCodigo() == 3) {
       formulariomensajesf.recordError("No puede agregar ese tipo de pariente (Hijo / Conyuge)");
            return Boolean.FALSE;
            }
        }
        // NUEVAS VALIDACIONES DNI
        if (familiarActual.getTipoDocumento().getCodigo()==1){
            if(familiarActual.getNroDocumento().length()>8){ 
       formulariomensajesf.recordError("El número de documento debe tener 8 dígitos (y solo números)");   
            return Boolean.FALSE;
            }
            try { Integer.parseInt(familiarActual.getNroDocumento());} catch (NumberFormatException ex) {
       formulariomensajesf.recordError("El número de documento debe tener 8 dígitos (y solo números)"); 
            return Boolean.FALSE;
            }            
        }
        
        Criteria c = session.createCriteria(Familiar.class);
            c.add(Restrictions.eq("parentesco", familiarActual.getParentesco()));
            c.add(Restrictions.eq("trabajador", actual));
            if (editando){
            c.add(Restrictions.ne("id", idVerificacion));
            }
            c.setProjection(Projections.rowCount());
            int q2 =  Integer.parseInt(c.uniqueResult().toString());
            
            if (q2 > 0 && familiarActual.getParentesco().getCodigo() != 3) {
       formulariomensajesf.recordError("No es posible registrar mas de un Pariente que no sea un hijo");
            return Boolean.FALSE;
            }         

           
           if (familiarActual.getParentesco().getCodigo()==3)
            {  
                  if(familiarActual.getFechaNacimiento().before(actual.getFechaNacimiento())){
       formulariomensajesf.recordError("la fecha de nacimiento del hijo no debe ser mayor a la del Trabajador");
                  return Boolean.FALSE;
                  }
            }

            if (familiarActual.getParentesco().getCodigo()==4 || familiarActual.getParentesco().getCodigo()==5)
            {  
                  if(familiarActual.getFechaNacimiento().after(actual.getFechaNacimiento())){
       formulariomensajesf.recordError("la fecha de nacimiento del padre/madre no debe ser mayor a la del Trabajador");
                  return Boolean.FALSE;
                  }
            }

             System.out.println("1er "+familiarActual.getFechaNacimiento()+actual.getFechaNacimiento());          
           
            if (actual.getEstadocivil()!=null && actual.getEstadocivil().getCodigo()==1)
            {
                if (familiarActual.getEstadoCivil()!=null && (familiarActual.getParentesco().getCodigo()==1  && familiarActual.getEstadoCivil().getCodigo()==2)){                
                formulariomensajesf.recordError("No se puede agregar un conyuge a un trabajador soltero");    
                    return Boolean.FALSE;                
                }
            }        
             System.out.println("1er "+familiarActual.getParentesco());
                    
        return Boolean.TRUE;
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariofamiliares() {
        formulariomensajesf.clearErrors();
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
        }
   //     else {
   //         familiarActual.setSexo(null);
   //     }
        
        if (valEstadoCivil != null)
        {
          familiarActual.setEstadoCivil(valEstadoCivil);  
        }
    //    else {familiarActual.setEstadoCivil(null);
     //   }
         


            
            
        System.out.println("FAMILIAR - "+familiarActual.getParentesco());
//        if(elemento==3){
        Logger logger = new Logger();
    //    String consulta = "SELECT COUNT(*) FROM RSC_FAMILIAR F JOIN RSC_DATOAUXILIAR DA ON (F.PARENTESCO_ID = DA.ID)"
    //            + "WHERE DA.CODIGO = " + familiarActual.getParentesco().getCodigo() + " AND F.TRABAJADOR_ID = '" + actual.getId() + "'";

        if (validaciones()==Boolean.FALSE){
            return actualizar();
        }
  
            
        if (!editando) { // Si no edita, está insertando
            //Codigo de Progenitor = 4
       /*     if (familiarActual.getParentesco().getCodigo() == 4) {
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
            */
            
            if (_usuario.getRolid() == 1) { // Si es trabajador 
                familiarActual.setAgregadoTrabajador(true);
                familiarActual.setValidado(false);
            }

            Criteria c = session.createCriteria(Familiar.class);
            c.add(Restrictions.eq("nroDocumento", familiarActual.getNroDocumento()));

                List<Familiar> familiares = c.list();
            if (!familiares.isEmpty()) {
             //   envelope.setContents("nro de dni duplicado");
                // VALIDACION DE PODER INGRESAR EL MISMO FAMILIAR CON OTRO TRABAJADOR
                    for (Familiar f : familiares){
                        if (f.getTrabajador().getEntidad() == actual.getEntidad()){
                           formulariomensajesf.recordError("no se puede asignar el mismo familiar en la misma entidad");
                           return actualizar();
                        }
                    }
                   Familiar  familiarTemporal = familiares.get(0);
                   
                   SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                    String  fecha1 = formatoDelTexto.format(familiarActual.getFechaNacimiento());
                    String  fecha2 = formatoDelTexto.format(familiarTemporal.getFechaNacimiento());
                    System.out.println("FECHAX "+fecha1+"  "+fecha2);
                    if (!familiarActual.getNombres().equals(familiarTemporal.getNombres()) ||
                         familiarActual.getTipoDocumento()!=familiarTemporal.getTipoDocumento()||
                         !fecha1.equals(fecha2)||
                        !familiarActual.getSexo().equals(familiarTemporal.getSexo())||
                        !familiarActual.getApellidoMaterno().equals(familiarTemporal.getApellidoMaterno()) || 
                        !familiarActual.getApellidoPaterno().equals(familiarTemporal.getApellidoPaterno()))
                    {
                      System.out.println(" DATOS - "+familiarActual.getNombres()+familiarActual.getTipoDocumento().getValor()+familiarActual.getFechaNacimiento()+familiarActual.getSexo()+familiarActual.getApellidoMaterno()+familiarActual.getApellidoPaterno());  
                      System.out.println(" DATOS - "+familiarTemporal.getNombres()+familiarTemporal.getTipoDocumento().getValor()+familiarTemporal.getFechaNacimiento()+familiarTemporal.getSexo()+familiarTemporal.getApellidoMaterno()+familiarTemporal.getApellidoPaterno());
                      formulariomensajesf.recordError("los datos del familiar son incorrectos");
                       return actualizar();                        
                    }
                 //   formulariomensajesf.recordError("nro de dni duplicado"); 
                 //   return actualizar();
            }
        } 

            System.out.println("FFFF  "+valEstadoCivil);
            familiarActual.setEstadoCivil(valEstadoCivil);
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
            valEstadoCivil = null;
            return actualizar();
        
    }

    private MultiZoneUpdate actualizar() {
        //return new MultiZoneUpdate("listaFamiliaresZone", listaFamiliaresZone.getBody()).add("mensajesFZone", mensajesFZone.getBody());
        return new MultiZoneUpdate("listaFamiliaresZone", listaFamiliaresZone.getBody()).add("familiaresZone", familiaresZone.getBody()).add("mensajesFZone", mensajesFZone.getBody());
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
        bvalidausuario = false;
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
