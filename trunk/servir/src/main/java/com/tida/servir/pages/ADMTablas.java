/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
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
 * @author mallco
 */
public class ADMTablas {

@Inject    
private Session session;    
@Inject
private PropertyAccess _access;

@InjectComponent
private Zone tiposancionZone;
@InjectComponent
private Zone mensajesZone;
@Component(id = "formularioMensajes")
private Form formularioMensajes;
@InjectComponent
private Envelope envelope;
    
@Property
@Persist
private DatoAuxiliar regimenactual;
@Property
@Persist
private LkSancionRegimen sancionregimen;
@Persist
@Property
private DatoAuxiliar valregimen;

@Persist
@Property
private List<LkSancionRegimen> listaSancionesModificables;
@Persist
@Property
private List<LkSancionRegimen> listaFinal;

@Persist
@Property
private List<LkSancionRegimen> listainicial;

@Property
@Persist
private boolean opcionCarga; //TRUE: DB ,FALSE: OBJECTO

public GenericSelectModel<DatoAuxiliar> getBeanRegimen(){

    List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session) ;       
    return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
}

@Log
public Object onValueChangedFromregimenlaboral(DatoAuxiliar dato){

    System.out.println("REGIMEX :"+dato);

    opcionCarga =true;


    if (dato != null){mostrar=true;}else{mostrar=false;}
    
    return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());
}

@Log
public Object onSeleccionarTodos(){
    
    // CARGAR EL GRID CON LOS DATOS MODIFICADOS
    opcionCarga = false;
    
    for(LkSancionRegimen dato : listaSancionesModificables)
    {
        dato.setOpcion(true);
    }
    
        return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());
}

@Log
public Object onLimpiar(){

    valregimen=null;
    opcionCarga=true;
    
    return new MultiZoneUpdate("tiposancionZone", tiposancionZone.getBody());

}


@Log
@SuppressWarnings("unchecked")
public List<LkSancionRegimen> getListaTablas(){

    if(opcionCarga==false){
    System.out.println("TABLAX : OBJETO");    
    
        return cargarPorObjeto();
        
    }    
    else if (opcionCarga==true){
    System.out.println("TABLAX : PROCEDIMIENTO");
    
        if (valregimen!=null){

            opcionCarga=false;        
            return cargarPorSQL();
            
        }
    }
    
    return null;
    
}

@Log

    @SuppressWarnings("unchecked")
public List<LkSancionRegimen> cargarPorSQL(){
     
   Query query = session.getNamedQuery("callSpSancionRegimen");
   query.setParameter("reg_id", valregimen.getId());
   listaSancionesModificables = query.list();
   listainicial = query.list();
   return query.list();
}

@Log
public List<LkSancionRegimen> cargarPorObjeto(){

    return listaSancionesModificables;
}


@Persist
@Property
private Boolean mostrar;




@Persist
@Property
private Integer setup;

@Log
public void setuprender()
{

    if (setup==null||setup==1){
    System.out.println("PRIMERA CARGA");
    
    setup=2;    
    }
    else {
    System.out.println("PAGINA YA CARGADA");
    }
    
}
List<LkSancionRegimen> listafinal;

@Property
@Persist
private  boolean opcionguardar;

@Log
@CommitAfter
    @SuppressWarnings("unchecked")
//void
public Object onSuccessFromFormularioTablas(){

//***
formularioMensajes.clearErrors();

if (opcionguardar==true){
    
   Query query = session.getNamedQuery("callSpSancionRegimen");
   query.setParameter("reg_id", valregimen.getId());
   listainicial = query.list();
   
   for(LkSancionRegimen num : listaSancionesModificables)
{
    System.out.println("FINAL : "+num.getId()+" "+num.getOpcion());
}
for(LkSancionRegimen num : listainicial)
{
    System.out.println("INICIAL : "+num.getId()+" "+num.getOpcion());
}

    System.out.println("GUARDAR");

    for (int i=0;i<listaSancionesModificables.size();i++){
        
        if(listainicial.get(i).getOpcion()!=listaSancionesModificables.get(i).getOpcion()){
            System.out.println("C1:"+listainicial.get(i).getTiposancion()+listainicial.get(i).getOpcion());
            System.out.println("C2:"+listaSancionesModificables.get(i).getTiposancion()+listaSancionesModificables.get(i).getOpcion());
            if(listaSancionesModificables.get(i).getOpcion()==false){
                            
                SancionRegimen objeto = new SancionRegimen(listaSancionesModificables.get(i).getId(),valregimen.getId());
                
                session.delete(objeto);
            }
            if(listaSancionesModificables.get(i).getOpcion()==true){
                
                System.out.println(listaSancionesModificables.get(i).getId()+listaSancionesModificables.get(i).getTiposancionId()+" "+valregimen.getValor()+valregimen.getId());
                SancionRegimen objeto = new SancionRegimen(listaSancionesModificables.get(i).getTiposancionId(),valregimen.getId());
                objeto.setFechaRegistro(new Date());
                
                session.save(objeto);
            }        
        }

    }

//***    
envelope.setContents("Tipos de sancion asignados Correctamente");    

opcionCarga=true;

//****
return new MultiZoneUpdate("tiposancionZone",tiposancionZone.getBody()).add("mensajesZone", mensajesZone.getBody());

}
    

opcionguardar=false;

//***
return null;
    
}

@Log
public void onSelectedFromGuardar(){
System.out.println("ESTA GUARDANDO");
    
opcionguardar =true;    
}


}