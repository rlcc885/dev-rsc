/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.components.Envelope;
import com.tida.servir.entities.EntidadUEjecutora;
import com.tida.servir.entities.UnidadOrganica;
import com.tida.servir.services.GenericSelectModel;
import helpers.Errores;
import helpers.Helpers;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
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
public class CambioUOEntidad extends GeneralPage{


    @Inject
    private Session session;

    @Property
    @SessionState
    private EntidadUEjecutora entidadUE;

    @Property
    @Persist
    private Integer nivelOrigen;

    @Property
    @Persist
    private Integer nivelDestino;

    @Property
    @Persist
    private UnidadOrganica uoOrigen;

    @Property
    @Persist
    private UnidadOrganica uoDestino;

    @Property
    @Persist
    private EntidadUEjecutora entidadDestino;

    @Persist
    private GenericSelectModel<UnidadOrganica> _beanUOrganicasOrigen;

    @Persist
    private GenericSelectModel<UnidadOrganica> _beanUOrganicasDestino;


    @Inject
    private PropertyAccess _access;

    @InjectComponent
    private Zone UOOrigenZone;

    @Property
    @InjectComponent
    private Zone NivelDestinoZone;

    @Property
    @InjectComponent
    private Zone UOOrigenNivelZone;

    @InjectComponent
    private Zone UODestinoZone;

    @InjectComponent
    private Zone UOChangeZone;

    @Inject
    private Request _request;

    @Inject
    private ComponentResources _resources;

    @InjectComponent
    private Envelope envelope;

    @Component(id = "formUOFusionar")
    private Form formUOFusionar;

    @Log
    public boolean getHayNivelOrigen() {
        return !(nivelOrigen == null) ;
    }

    @Log
    public boolean getHayNivelDestino() {
        return !(nivelDestino == null)&& (nivelDestino > 0);
    }


    public boolean getHayEntidadDestino() {
        return entidadDestino != null;
    }

    @Log
    public boolean getActivoSubmit() {
        boolean salida = getHayEntidadDestino() && (uoOrigen != null) &&
                ((nivelDestino ==0) || (getHayNivelDestino() && (uoDestino != null)));
        return salida;
    }

    /**
     * El parámetro first es porque la unidad origen no puede ser nivel 0, pero si la destino
     * @param eUE
     * @param first
     * @return
     */
    public List<Integer> getBeanNivel(EntidadUEjecutora eUE, Integer first){
        List<Integer> nivel = new LinkedList<Integer>();
        Integer nivelMax = 0;

        nivelMax = Helpers.maxNivelUO(eUE, session);

        for(int i=first; i <= nivelMax; i++){
            // Es mas uno porque agregamos hasta un nivel mas
            nivel.add(i);
        }

        return nivel; // nivel 0 van asociadas a las entidades directamente
    }

    @Log
    public List<Integer> getBeanNivelOrigen(){
        return getBeanNivel(entidadUE, 1);
    }

    @Log
    public List<Integer> getBeanNivelDestino(){
        return getBeanNivel(entidadDestino, 0); // las Entidades (sin uo) pueden ser válidas, luego hay de nivel 0

    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicasOrigen(){
        return _beanUOrganicasOrigen;
    }

    @Log
    public GenericSelectModel<UnidadOrganica> getBeanUOrganicasDestino(){
        return _beanUOrganicasDestino;
    }

    @Log
    @CommitAfter
    Object onSuccessFromformNivelUOOrigen(){
        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
        c.add(Restrictions.eq("nivel", nivelOrigen));
        c.add(Restrictions.eq("entidadUE", entidadUE ));
        list = c.list();
        _beanUOrganicasOrigen = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
        uoOrigen = null;
        return new MultiZoneUpdate("UOOrigenZone",UOOrigenZone.getBody())
                    .add("UOChangeZone", UOChangeZone.getBody());
    }

    @Log
    @CommitAfter
    Object onSuccessFromFormNivelUODestino(){

        List<UnidadOrganica> list;
        Criteria c = session.createCriteria(UnidadOrganica.class);
        c.add(Restrictions.ne("estado", UnidadOrganica.ESTADO_BAJA ));
        c.add(Restrictions.eq("nivel", nivelDestino));
        c.add(Restrictions.eq("entidadUE", entidadDestino ));
        list = c.list();
        _beanUOrganicasDestino = new GenericSelectModel<UnidadOrganica>(list,UnidadOrganica.class,"den_und_organica","id",_access);
        uoDestino = null;
        return new MultiZoneUpdate("UODestinoZone",UODestinoZone.getBody())
                    .add("UOChangeZone", UOChangeZone.getBody());
    }

    Object onSuccessFromformUODestino() {
        return UOChangeZone.getBody();
    }

    Object onSuccessFromFormUOOrigen() {
        return UOChangeZone.getBody();
    }


    @CommitAfter
    Object onSuccessFromFormUOChange() {
        Helpers.migrarUOBase(uoOrigen, entidadUE, entidadDestino, uoDestino, session);
        session.flush();
        uoOrigen = null;
        uoDestino = null;
        nivelOrigen = null;
        nivelDestino = null;
        envelope.setContents("Unidad Orgánica migrada exitosamente");
        return new MultiZoneUpdate("UOChangeZone",UOChangeZone.getBody())
                    .add("UODestinoZone", UODestinoZone.getBody())
                    .add("NivelDestinoZone", NivelDestinoZone.getBody())
                    .add("UOOrigenZone", UOOrigenZone.getBody())
                    .add("UOOrigenNivelZone", UOOrigenNivelZone.getBody());

    }


    @CommitAfter
    Object onSuccessFromFormUOFusionar() {
        Helpers.fusionarUOBase(uoOrigen, entidadUE, entidadDestino, uoDestino, session);
        session.flush();
        uoOrigen = null;
        uoDestino = null;
        nivelOrigen = null;
        nivelDestino = null;
        envelope.setContents("Unidad Orgánica fusionada exitosamente");
        return new MultiZoneUpdate("UOChangeZone",UOChangeZone.getBody())
                    .add("UODestinoZone", UODestinoZone.getBody())
                    .add("NivelDestinoZone", NivelDestinoZone.getBody())
                    .add("UOOrigenZone", UOOrigenZone.getBody())
                    .add("UOOrigenNivelZone", UOOrigenNivelZone.getBody());

    }


    @SetupRender
    void initValues() {
        entidadDestino = null;
    }
}
