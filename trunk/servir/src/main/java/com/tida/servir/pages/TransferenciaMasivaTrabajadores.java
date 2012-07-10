/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.Legajo;
import com.tida.servir.entities.Entidad_BK;
import com.tida.servir.services.GenericSelectModel;
import java.util.List;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class TransferenciaMasivaTrabajadores  extends GeneralPage {
    
        
    @Inject
    private Session session;

    @Component(id = "formulariotransferencia")
    private Form formTransfer;


    @Component(id = "formularioseleccion")
    private Form formSelect;

    @Property
    @Persist
    private Entidad_BK entidadUEDestino;


    @Property
    @Persist
    private Entidad_BK entidadUEOrigen;



    @Property
    @Persist
    private Boolean seleccion;

    @Inject
    private PropertyAccess _access;

    @Property
    private long cantTrabajadores;

    public boolean getTransfer(){
        return !seleccion;
    }
    
    public GenericSelectModel<Entidad_BK> getBeanOrganismos(){

        List<Entidad_BK> list;
        Criteria c;
        c = session.createCriteria(Entidad_BK.class);
        c.add(Restrictions.ne("estado", Entidad_BK.ESTADO_BAJA ));

        list = c.list();

        return new GenericSelectModel<Entidad_BK>(list,Entidad_BK.class,"denominacion","id",_access);
       
    }

    Object onSuccessFromformularioseleccion() {
        if(entidadUEDestino.equals(entidadUEOrigen)) {
            formSelect.recordError("Los organismos origen y destino no pueden ser iguales");
            return this;
        } 

        Legajo l;
        Criteria c = session.createCriteria(Legajo.class);
        c.add(Restrictions.eq("entidadUE", entidadUEOrigen));
        cantTrabajadores = c.list().size();
        seleccion = false; // se muestra la parte de transfer y se oculta esta
        return this;
    }


    Object onSuccessFromformulariotransferencia() {
        seleccion = true;
        return this;
    }

    void onActivate(){
        if (seleccion == null) {
            seleccion = true;
        }
    }
}