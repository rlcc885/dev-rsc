package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Logger;


import helpers.Helpers;

import java.util.Date;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PrimaryKeyEncoder;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.hibernate.Criteria;
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
    
    private int elemento=0;
     
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
    private boolean bvalidausuario;
    
    @Property
    @Persist
    private boolean bfechanacimiento;
    @Property
    @Persist
    private boolean bdni;
    
    @Property
    @Persist
    private boolean bedicion;
   
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
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {

            familiarActual = new Familiar();
            bdni=false;
            bfechanacimiento=false;
            if(_usuario.getRol().getId()==2 || _usuario.getRol().getId()==3){
                bvalidausuario=true;
            }else{
                bvalidausuario=false;
            }
            
    }
    
    @Log
    public List<Familiar> getListadoFamiliares() {
        Criteria c = session.createCriteria(Familiar.class);
        c.add(Restrictions.eq("trabajador",actual));  
        return c.list();
    }
    
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
    
    void onSelectedFromCancel() {
        elemento=2;
    }
    
    void onSelectedFromReset() {
         elemento=1;
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariofamiliares() {
        
        //if(bfechanacimiento){
        if(!bedicion){
        //Codigo de Progenitor = 4
        if(familiarActual.getParentesco().getCodigo()==4){
            Criteria c1 = session.createCriteria(Familiar.class);
            c1.add(Restrictions.eq("trabajador",actual)); 
            c1.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
            listaParentescoP=c1.list();
        }
        //Codigo de Conviviente = 2
        if(familiarActual.getParentesco().getCodigo()==2){
            Criteria c2 = session.createCriteria(Familiar.class);
            c2.add(Restrictions.eq("trabajador",actual)); 
            c2.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
            listaParentescoC=c2.list();
         }
        }
        //Codigo de Conyugue = 1
//         if(familiarActual.getParentesco().getCodigo()==1){
//            Criteria c3 = session.createCriteria(Familiar.class);
//            c3.add(Restrictions.eq("trabajador",actual)); 
//            c3.add(Restrictions.eq("parentesco",familiarActual.getParentesco())); 
//            listaParentescoCY=c3.list();
//        }
         System.out.println("entro: "+bedicion);      
         if(listaParentescoP!=null && listaParentescoP.size()>0 && familiarActual.getParentesco().getCodigo()==4 && !bedicion){
                envelope.setContents("No es posible regstrar mas de un Progenitor");
                return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                        .add("familiaresZone", familiaresZone.getBody());
         }
         else if(listaParentescoC!=null && listaParentescoC.size()>0 && familiarActual.getParentesco().getCodigo()==2 && !bedicion) {
                envelope.setContents("No es posible registrar mas de un Conviviente");
                return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                        .add("familiaresZone", familiaresZone.getBody());

    //        }else if(listaParentescoCY!=null && listaParentescoCY.size()>0 && familiarActual.getParentesco().getCodigo()==1) {
    //             envelope.setContents("No es posible registrar mas de un Cónyugue");
    //             return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
    //                    .add("familiaresZone", familiaresZone.getBody());
        }else{
             
            familiarActual.setTrabajador(actual);
            familiarActual.setEntidad(_oi);
            if(valsexo!=null){
                if(valsexo.equals("MASCULINO")){
                    familiarActual.setSexo("M");            
                }
                else if(valsexo.equals("FEMENINO")){
                    familiarActual.setSexo("F");
                }
            }
            else{
                familiarActual.setSexo(null);
            }
            session.saveOrUpdate(familiarActual);
            envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
            familiarActual=new Familiar();
            valsexo=null;
            bedicion=false;
            return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
                    .add("listaFamiliaresZone", listaFamiliaresZone.getBody())
                    .add("familiaresZone", familiaresZone.getBody());
         }

        /*}else{
            if(familiarActual.getFechaNacimiento()!=null){
                    bfechanacimiento=true;
                    Date fechaactual=new Date();
                    if((fechaactual.getYear()-familiarActual.getFechaNacimiento().getYear())>=18){
                        bdni=false;
                        System.out.println("entro");
                    }              
            }
            return familiaresZone.getBody(); 
        }
            
       * 
       */
  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            familiarActual=new Familiar();
            bdni=false;
            bedicion=false;
            bfechanacimiento=false;
            valsexo=null;
            return  familiaresZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(Familiar fami) {
        
        familiarActual=fami;
          if(familiarActual.getSexo()!=null){
            if(familiarActual.getSexo().equalsIgnoreCase("M")){
                valsexo="MASCULINO";            
            }
            else if(familiarActual.getSexo().equalsIgnoreCase("F")){
                valsexo="FEMENINO";
            }
            else{
                valsexo=null;
            }
          }else{
                valsexo=null;
          }
        bedicion=true;

           return familiaresZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(Familiar fami) {
        session.delete(fami);
        
        envelope.setContents("Se realizo la elimiación satisfactoriamente");
        return new MultiZoneUpdate("mensajesFZone", mensajesFZone.getBody())                             
        .add("listaFamiliaresZone", listaFamiliaresZone.getBody());
    }
    
    
    
    
   
}
