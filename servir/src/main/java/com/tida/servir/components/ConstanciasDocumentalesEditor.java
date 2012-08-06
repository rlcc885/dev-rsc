/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import java.util.Date;

import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;

import java.util.ArrayList;
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



import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author LFL
 */
public class ConstanciasDocumentalesEditor {
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
   
    
    @Component(id = "formulariomensajesDT")
    private Form formulariomensajesDT;
    @InjectComponent
    private Zone mensajesDTZone;  
       
    @InjectComponent
    private Zone documentosZone;
    
    private int elemento=0;
     
    @Parameter
    @Property
    private Trabajador actual;
    
    @Persist
    @Property
    private ConstanciaDocumental constancia;
    

    @Persist
    private GenericSelectModel<CargoAsignado> _cargoasignado;
    
   
    //Listado de familiares
    @InjectComponent
    private Zone listaDocumentosZone;
    @Persist
    @Property
    private ConstanciaDocumental listaDocumentos;
    
    @Persist
    @Property
    private CargoAsignado cargoasignado;
    
    private Legajo lega;
    @Persist
    @Property
    private String bobligatorio;
    @Persist
    @Property
    private String bentrego;
 
    @Inject
    private PropertyAccess _access;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            constancia=new ConstanciaDocumental();
            bentrego="";
            bobligatorio="";
           
    }
    
    public Legajo buscarlegajo(){
         Criteria c = session.createCriteria(Legajo.class);  
         c.add(Restrictions.eq("trabajador", actual));
         c.add(Restrictions.eq("entidad", _oi));
         c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
         List result = c.list();
         lega=(Legajo) result.get(0);
         return lega;
         
   }
    
    
    @Log
    public List<ConstanciaDocumental> getListadoDocumentos() {
        System.out.println("aaa "+buscarlegajo().getCod_legajo());
        Criteria c = session.createCriteria(ConstanciaDocumental.class);
        c.add(Restrictions.eq("entrego",true));
        c.add(Restrictions.eq("legajo",buscarlegajo()));
         System.out.println("aaa "+c.list().size());
        return c.list();
    }
   
    @Log
   public CargoAsignado getCargosAsignados() {
       Criteria c = session.createCriteria(CargoAsignado.class);
         c.createAlias("legajo", "legajo");
         c.add(Restrictions.eq("trabajador", actual));
         c.add(Restrictions.eq("legajo.entidad", _oi));
         c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
         List result = c.list();
         cargoasignado=(CargoAsignado) result.get(0);
         
       return cargoasignado;
   }
     //para obtener datos de la Categoria
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanCategoria() {        
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("CATEGORIACONSTANCIA", null, 0, session);
            return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    //para obtener datos del TipoDocumento
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanTipoDocumento() {
            List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DATOCONSTANCIA", null, 0, session);
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
    Object onSuccessFromFormulariodocumentos() {
            System.out.println("aaa "+buscarlegajo().getCod_legajo());
            constancia.setLegajo(buscarlegajo());
            constancia.setCargoasignado(getCargosAsignados());
            if(bentrego.equalsIgnoreCase("SI")){
                constancia.setEntrego(Boolean.TRUE);
            }else{
                constancia.setEntrego(Boolean.FALSE);
            }
            if(bobligatorio.equalsIgnoreCase("SI")){
                constancia.setObligatorio(Boolean.TRUE);
            }else{
                constancia.setObligatorio(Boolean.FALSE);
            }
            session.saveOrUpdate(constancia);
            envelope.setContents(helpers.Constantes.FAMILIAR_EXITO);
            constancia=new ConstanciaDocumental();
            bentrego="";
            bobligatorio="";
            return new MultiZoneUpdate("mensajesDTZone", mensajesDTZone.getBody())                             
                    .add("listaDocumentosZone", listaDocumentosZone.getBody())
                    .add("documentosZone", documentosZone.getBody());  
  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            constancia=new ConstanciaDocumental();
            bentrego="";
            bobligatorio="";
            return  documentosZone.getBody();
        }else if(elemento==2){
            return "Busqueda";
        }else{    
           return this;
        }
        
    }
    
    @Log
    Object onActionFromEditar(ConstanciaDocumental consta) {        
        constancia=consta;
           return documentosZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(ConstanciaDocumental consta) {
        session.delete(consta);
         envelope.setContents("Se realizo la elimiación satisfactoriamente");
        return new MultiZoneUpdate("mensajesDTZone", mensajesDTZone.getBody())                             
                    .add("listaDocumentosZone", listaDocumentosZone.getBody());

    }
    
    
    
    
   
}
