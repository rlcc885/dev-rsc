/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.DatoAuxiliar;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Sancion;
import com.tida.servir.services.GenericSelectModel;
import helpers.Helpers;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.List;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.hibernate.Session;
/**
 *
 * @author ale
 */
public class ABMSancion  extends GeneralPage
{
    @Property
    @SessionState
    private Entidad eue;
    
    @Inject
    private Session session;
    @Inject
    private PropertyAccess _access;
    @Property
    @Persist
    private boolean bestrabajador;
    @Property
    @Persist
    private DatoAuxiliar bdocidentidad;    
    @Property
    @Persist
    private String bnumerodocumento;
    @Property
    @Persist
    private String bnombres;
    @Property
    @Persist
    private String bapaterno;
    @Property
    @Persist
    private String bamaterno;
    @Property
    @Persist
    private String bentidad;
    @Property
    @Persist
    private String bpuesto;
    @Property
    @Persist
    private DatoAuxiliar bregimen;
    @Property
    @Persist
    private Sancion nuevasancion;
    // inicio de la pagina
    @SetupRender
    void inicio(){
        nuevasancion=new Sancion();
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    public GenericSelectModel<DatoAuxiliar> getBeanregimenlaboral() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("REGIMENLABORAL", null, 0, session);
        return new GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformbusqueda() {
        
      return this;
    }
    
   
    

}
