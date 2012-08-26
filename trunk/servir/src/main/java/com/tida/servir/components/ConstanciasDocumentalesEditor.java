/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.components;

import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
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
    @Property
    private String valfec_desde;
    @Persist
    @Property
    private Date fecha_desde;

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
    @Persist
    @Property
    private Legajo lega;
    @Persist
    @Property
    private String bobligatorio;
    @Persist
    @Property
    private String bentrego;
    
        @Property
    @Persist
    private boolean beditar;
 
    @Inject
    private PropertyAccess _access;
    
    //Inicio de lac carga de la pagina
    @Log
    @SetupRender
    private void inicio() {
            constancia=new ConstanciaDocumental();
            listaDocumentos=new ConstanciaDocumental();
            bentrego="";
            bobligatorio="";
            beditar=false;
            valfec_desde=null;
           
    }
    
    public Legajo buscarlegajo(){
         Criteria c1 = session.createCriteria(Legajo.class);  
         c1.add(Restrictions.eq("trabajador", actual));
         c1.add(Restrictions.eq("entidad", _oi));
         List result = c1.list();
         lega=(Legajo) result.get(0);
         return lega;
         
   }
    
    
    @Log
    public List<ConstanciaDocumental> getListadoDocumentos() {
        Criteria c2 = session.createCriteria(ConstanciaDocumental.class);
        //c.add(Restrictions.eq("entrego",true));
        c2.add(Restrictions.eq("legajo",buscarlegajo()));
        c2.add(Restrictions.eq("cargoasignado",getCargosAsignados()));
        return c2.list();
    }
   
    @Log
   public CargoAsignado getCargosAsignados() {
       Criteria c3 = session.createCriteria(CargoAsignado.class);
         c3.add(Restrictions.eq("trabajador", actual));
         List result2 = c3.list();
         cargoasignado=(CargoAsignado) result2.get(0);
         
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
        if(!beditar){
            constancia.setLegajo(buscarlegajo());
            constancia.setCargoasignado(getCargosAsignados());
        }
            
            if(bentrego.equalsIgnoreCase("SI")){
                constancia.setEntrego(Boolean.TRUE);
            }else if(bentrego.equalsIgnoreCase("NO")){
                constancia.setEntrego(Boolean.FALSE);
            }else{
                constancia.setEntrego(null);
            }
            if(bobligatorio.equalsIgnoreCase("SI")){
                constancia.setObligatorio(Boolean.TRUE);
            }else if(bobligatorio.equalsIgnoreCase("NO")){
                constancia.setObligatorio(Boolean.FALSE);
            }else{
                 constancia.setObligatorio(null);
            }
            
            if(valfec_desde!=null){
                SimpleDateFormat  formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                try {
                fecha_desde = (Date)formatoDelTexto.parse(valfec_desde);
                } catch (ParseException ex) {
                ex.printStackTrace();
                }
            }
            constancia.setFecha(fecha_desde);
            
            
            session.saveOrUpdate(constancia);
            envelope.setContents(helpers.Constantes.CONSTANCIAS_DOCUMENTALES_EXITO);
            constancia=new ConstanciaDocumental();
            valfec_desde=null;
            bentrego="";
            bobligatorio="";
            beditar=false;
            return new MultiZoneUpdate("mensajesDTZone", mensajesDTZone.getBody())                             
                    .add("listaDocumentosZone", listaDocumentosZone.getBody())
                    .add("documentosZone", documentosZone.getBody());  
  
    }
    
    @Log
    @CommitAfter    
    Object onSuccessFromFormulariobotones() {
        if(elemento==1){
            constancia=new ConstanciaDocumental();
            valfec_desde=null;
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
        
        if(constancia.getFecha()!=null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            valfec_desde=formatoDeFecha.format(constancia.getFecha());
        }
        
        beditar=true;
        if(constancia.getEntrego()==true){ 
            bentrego="SI";
        }else if(constancia.getEntrego()==false){
            bentrego="NO";
        }else{
            bentrego="";
        }
        if(constancia.getObligatorio()==true){ 
            bobligatorio="SI";
        }else if(constancia.getObligatorio()==false){
            bobligatorio="NO";
        }else{
            bobligatorio="";
        }
           return documentosZone.getBody(); 
    }
    
    @Log
    @CommitAfter        
    Object onActionFromEliminar(ConstanciaDocumental consta) {
        session.delete(consta);
         envelope.setContents("Constancias Documentales eliminadas exitosamente.");
        return new MultiZoneUpdate("mensajesDTZone", mensajesDTZone.getBody())                             
                    .add("listaDocumentosZone", listaDocumentosZone.getBody());

    }
    
    
    
    
   
}
