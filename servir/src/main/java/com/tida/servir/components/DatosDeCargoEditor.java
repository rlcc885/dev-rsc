package com.tida.servir.components;


import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import com.tida.servir.pages.Busqueda;
import helpers.Constantes;
import helpers.Helpers;
import helpers.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 *	Clase que maneja el TAB del editor de datos personales.
 *  
 */
public class DatosDeCargoEditor {

	//@SuppressWarnings("unused")
//    @Parameter(required = true, principal = true, autoconnect = true)
//    @Property
//    private String _zone;
    @SuppressWarnings("unused")
    @Property    
    @Parameter(required = true, principal = true, autoconnect = true)
    private Cargoxunidad actual;

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, principal = true, autoconnect = true)
    private CargoAsignado actual_asignado;
    
    @Property
    @SessionState
    private Entidad _oi;
	
    /*@Property
    private String vinculoTipo;*/
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    @Inject
    private Session session;
    
    @Component(id = "formulariodatosdecargoasignado")
    private Form formulariodatosdecargoasignado;

    @InjectComponent
    private Zone datosDeCargoZone;

    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Envelope envelope;
    
    private int elemento=0;
    
    @Property
    @Persist
    private String valmotivo;
    @Persist
    @Property
    private String valfec_inicio;
    @Persist
    @Property
    private String valfec_fin;
    @Persist
    @Property
    private Date fecha_inicio;
    @Persist
    @Property
    private Date fecha_fin;
    @Property
    @Persist
    private DatoAuxiliar valtipovinculo;
    
    @Log
    @SetupRender
    private void inicio() {
        limpiar();
        valmotivo=actual_asignado.getMotivo_cese();
//        valfec_inicio=actual_asignado.getFec_inicio();
//        valfec_fin=actual_asignado.getFec_fin();
        if (actual_asignado.getFec_inicio() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_inicio = formatoDeFecha.format(actual_asignado.getFec_inicio());
        }
        if (actual_asignado.getFec_fin() != null) {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_fin = formatoDeFecha.format(actual_asignado.getFec_fin());
        }
        valtipovinculo=actual_asignado.getTipovinculo();        
    }
    
//    public boolean getAsignadoBaja() {
//        /*
//         * TODO JZM revisar linea de codigo
//        if(actual_asignado.getEstado() == null)
//            return actual_asignado.getEstado().equals(Constantes.ESTADO_BAJA);
//        else return false;
//        * 
//        */
//        if(actual_asignado.getEstado() == false)
//            return false;
//        else return true;
//    }
    

    @Log
    public GenericSelectModel<DatoAuxiliar> getTipoVinculo() {
        //System.out.println("uo on getbean dato situacion CAO "+uo+" getpuedeeditar "+getPuedeEditar() );
        //return Helpers.getValorTablaAuxiliar("SituacionCAP", session);
        
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("TIPOVINCULO", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }       

    void limpiar(){
        valtipovinculo=null;
        valfec_fin=null;
        valfec_inicio=null;
        valmotivo=null;        
        
    }
    void onSelectedFromSave() {        
        elemento=1;   
    }
    
    void onSelectedFromCancel() {        
        elemento=2;   
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformulariodatosdecargoasignado(){
        if(elemento==2){
            return Busqueda.class;
        }
        else{
            if (valfec_inicio != null) {
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fecha_inicio = (Date) formatoDelTexto.parse(valfec_inicio);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            if (valfec_fin!= null) {
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fecha_fin = (Date) formatoDelTexto.parse(valfec_fin);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            if(valtipovinculo==null){
                formulariodatosdecargoasignado.recordError("Tiene que seleccionar Tipo de Vinculo.");
                return datosDeCargoZone.getBody();
            }
            if(valfec_fin!=null){
                if(valmotivo != null && !valmotivo.equals("")){
                    if (fecha_fin.before(fecha_inicio)) {
                        formulariodatosdecargoasignado.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return datosDeCargoZone.getBody();
                    }
                    if(fecha_inicio.after(new Date())) {
                        formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                        return datosDeCargoZone.getBody();
                    }
                    String hql = "update rnsc.RSC_USUARIO set ESTADO=0 where login='" + actual_asignado.getTrabajador().getNroDocumento()  + "'";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
//                    registrar(false);
                    return datosDeCargoZone.getBody();
                }
                else{
                    formulariodatosdecargoasignado.recordError("Debe ingresar Motivo Cese");
                    return datosDeCargoZone.getBody();
                }       
            } 
            if(valmotivo != null && !valmotivo.equals("")){        
                if(valfec_fin!=null){
                    if (fecha_fin.before(fecha_inicio)) {
                        formulariodatosdecargoasignado.recordError("Las fechas de fin no pueden ser menores a las de inicio");
                        return datosDeCargoZone.getBody();
                    }
                    if(fecha_inicio.after(new Date())) {
                        formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                        return datosDeCargoZone.getBody();
                    }
                    String hql = "update rnsc.RSC_USUARIO set ESTADO=0 where login='" + actual_asignado.getTrabajador().getNroDocumento()  + "'";
                    Query query = session.createSQLQuery(hql);
                    int rowCount = query.executeUpdate();
//                    registrar(false);
                    return datosDeCargoZone.getBody();
                }
                else{
                    formulariodatosdecargoasignado.recordError("Tiene que ingresar Fecha de Finalización");
                    return datosDeCargoZone.getBody();
                }       
            }
            if(fecha_inicio.after(new Date())) {
                formulariodatosdecargoasignado.recordError("La fecha de fin debe ser previa a la fecha actual.");
                return datosDeCargoZone.getBody();
            }
            registrar(true);
        }
        return datosDeCargoZone.getBody();
    }
    
    @CommitAfter
    void registrar(Boolean e){
        
       actual_asignado.setMotivo_cese(valmotivo);
       actual_asignado.setFec_inicio(fecha_inicio);
       actual_asignado.setFec_fin(fecha_fin);
       actual_asignado.setTipovinculo(valtipovinculo);
       actual_asignado.setEstado(e);
       session.saveOrUpdate(actual_asignado);
       //new Logger().loguearOperacion(session, loggedUser, String.valueOf(unidadOrganica.getId()), (editando ? Logger.CODIGO_OPERACION_MODIFICACION : Logger.CODIGO_OPERACION_ALTA), Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_UNIDAD_ORGANICA);
       session.flush();
       formulariodatosdecargoasignado.clearErrors();
       if(!e){
           envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_BAJA);
       }
       else{
           envelope.setContents(helpers.Constantes.CARGO_ASIGNADO_EXITO);   
       }
       
    }
    
//    @CommitAfter
//    Object bajausuario(Boolean e){            
//       if(!e){         
//         if(getListadoUsuario().size()>0){
//            session.flush();
//            String hql = "update RSC_USUARIO set ESTADO=0 where login='" + getListadoUsuario().get(0).getLogin()  + "'";
//            Query query = session.createSQLQuery(hql);
//            int rowCount = query.executeUpdate();
//            session.flush();
//         }
//       }
//       return null;
//    }
    
//    @Log
//    public List<UsuarioTrabajador> getListadoUsuario() {
//        Query query = session.getNamedQuery("UsuarioTrabajador.findByLogin");
//        query.setParameter("login", actual_asignado.getTrabajador().getNroDocumento());
//        session.flush();
//        return query.list();
//    }
    

}
