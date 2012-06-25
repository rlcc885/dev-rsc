    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.*;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
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

/**
 *
 * @author ale
 */
public class Buscarentidad extends GeneralPage {

    @Inject
    private Session session;
    // Levantamos de la sesión tapestry el organismo informante
    @Property
    @Persist
    private Cargoxunidad cargo;
    @Property
    private Entidad_BK c;
    @Property
    @Persist
    private UnidadOrganica uo;
    @Property
    @SessionState
    private Entidad_BK entidadUE;    
    @Property
    @SessionState
    private Usuario loggedUser;
    @Inject
    private PropertyAccess _access;
       @InjectComponent
    private Zone abmZone;
    @Property
    @Persist
    private String errorBorrar;
    /*@Component(id = "formularioselectorgano")
    private Form _selectOrganoForm;*/
    @Persist
    private boolean editando;
    /*@Component(id = "formularioselectuo")
    private Form selectUoForm;*/
    @Component(id = "formularioaltacargo")
    private Form _altaForm;
    @Inject
    private Request _request;
    @Inject
    private ComponentResources _resources;


    @Property
    @Persist
    private boolean mostrar;
    @Persist
    @Property
    private String bdenoentidad;

    /*@Property
    @Persist
    private Ocupacional ocupacional;*/
    

    
    @Log
    @CommitAfter
    Object onSuccessFromFormulariobusqueda() {
        mostrar=true;
        return zonasDatos();
    }
   
    @Log
    public List<Entidad_BK> getEntidades() {
        Criteria c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.disjunction().add(Restrictions.like("denominacion", bdenoentidad + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("ñ", "n") + "%").ignoreCase()).add(Restrictions.like("denominacion", bdenoentidad.replaceAll("n", "ñ") + "%").ignoreCase()));      
        return c.list();
    }


    @Log
    Object onActionFromEditar(Entidad_BK c) {
        
        return Buscarentidad.class;
    }

    @Log
    void cargoDatos() {
//        regimengruponivel = new RegimenGrupoNivel();
//
//        regimengruponivel.setNivelRemunerativo(cargo.getNivelRemunerativo());
//        regimengruponivel.setGrupo(cargo.getGrupoOcupacional());
//        regimengruponivel.setRegimen(cargo.getRegimenlaboral());

    }



    @Log
    void onActivate() {

        if (cargo == null) {
            cargo = new Cargoxunidad();            
            editando = false;
        }

        if (uo == null) {
            editando = false;
        }
        
        //System.out.println("---------------on activate regimengruponivel "+regimengruponivel.getGrupo());
    }

    @Log
    void onActivate(Cargoxunidad c) {
        //System.out.println("------------------------------on activate con cargo");
        if (c == null) {
            c = new Cargoxunidad();            
            editando = false;
            cargo = c;
        } else {
            cargo = c;
            uo = cargo.getUnidadorganica();                  

            errorBorrar = null;
            cargoDatos();
            editando = true;
        }


    }

    /*
    Cargo onPassivate()
    {
    return cargo;
    }
     */
    @Log
    private MultiZoneUpdate zonas() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody());

        return mu;
    }

    @Log
    private MultiZoneUpdate zonasDatos() {
        MultiZoneUpdate mu;

        mu = new MultiZoneUpdate("abmZone", abmZone.getBody());
        return mu;

    }


    /*@Log
    @SetupRender
    private void setupCombos() {
    //System.out.println("=======================Estoy aca,");

    }*/

    /*
    @Log
    @SetupRender
    private void setupOcupacionales() {
    if (cargo != null) {
    cargoDatos();
    }
    }
     */
}
