/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.pages;

import com.tida.servir.base.GeneralPage;
import com.tida.servir.entities.*;
import helpers.Helpers;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ale
 */
public class RespuestaSolicitud  extends GeneralPage
{
    @Property
    @SessionState
    private Entidad eue;
    @Inject
    private Session session;   
    @Inject
    private PropertyAccess _access;
    @Inject
    private Request _request;
    @PageActivationContext
    private Solicitud_Acceso nuevasolicitud;
    
    @Property
    @Persist
    private LkBusquedaTrabajador trabajador;
    @Property
    @Persist 
    private String fecharesolu;
    @Property
    @Persist 
    private DatoAuxiliar bdocumentoidentidad;
    
    @Log
    public Solicitud_Acceso getNuevasolicitud() {
        return nuevasolicitud;
    }

    public void setnNevasolicitud(Solicitud_Acceso nuevasolicitud) {
        this.nuevasolicitud = nuevasolicitud;
    }
    
    // inicio de la pagina
    @SetupRender
    void initValues() {
        trabajador=(LkBusquedaTrabajador) session.load(LkBusquedaTrabajador.class, nuevasolicitud.getTrabajador().getId());                
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fecharesolu = formatoDeFecha.format(nuevasolicitud.getFec_resolucion());
        bdocumentoidentidad=nuevasolicitud.getTrabajador().getDocumentoidentidad();
    }
    
    @Log
    public com.tida.servir.services.GenericSelectModel<DatoAuxiliar> getBeandocumentoidentidad() {
        List<DatoAuxiliar> list = Helpers.getDatoAuxiliar("DOCUMENTOIDENTIDAD", null, 0, session);
        return new com.tida.servir.services.GenericSelectModel<DatoAuxiliar>(list, DatoAuxiliar.class, "valor", "id", _access);
    }
    
    @Log
    @CommitAfter
    Object onSuccessFromformAltasolicitud(){ 
        nuevasolicitud.setEstado(Boolean.TRUE);
        session.saveOrUpdate(nuevasolicitud);
        session.flush();
        helpers.Logger logger = new helpers.Logger();
        String hql = "update RSC_EVENTO set estadoevento=1 where trabajador_id='" + nuevasolicitud.getTrabajador().getId() + "' and tipoevento_id='" + logger.SOLICITUD_SANCION+ "' and tabla_id='" + nuevasolicitud.getId() + "' and estadoevento=0";
        Query query = session.createSQLQuery(hql);
        int rowCount = query.executeUpdate();
        session.flush();
        Perfilusuario permiso = new Perfilusuario();
        PerfilusuarioPK perfilusuariopk = new PerfilusuarioPK();
//        System.out.println("usuariooooooo"+usuario.getId());
        perfilusuariopk.setUsuarioId(getUsuarios().get(0).getId());
        perfilusuariopk.setPerfilId((long)515);
        permiso.setPerfilusuarioPK(perfilusuariopk);
        session.save(permiso);
        return "Alerta";
    }
    
    @Log
    public List<UsuarioTrabajador> getUsuarios(){
        Criteria c = session.createCriteria(UsuarioTrabajador.class);
        c.add(Restrictions.eq("trabajadorid", nuevasolicitud.getTrabajador().getId()));
        return c.list();
    }
    Object onCancel(){
        return "Alerta";
    }
    
    StreamResponse onActionFromReturnStreamResponse() {
		return new StreamResponse() {
			InputStream inputStream;

                    @Override
                    public void prepareResponse(Response response) {
                            File fileADescargar = new File(nuevasolicitud.getDocumento());

                            try {
                                inputStream = new FileInputStream(fileADescargar);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {
                                response.setHeader("Content-Type", "application/x-zip");
                                response.setHeader("Content-Disposition", "inline; filename="+fileADescargar.getName());
                                response.setHeader("Content-Length", "" + inputStream.available());
                            }
                            catch (IOException e) {
                                Logger.getLogger(batch_dev.class.getName()).log(Level.SEVERE, null, e);
                            }
                    }

                    @Override
                    public String getContentType() {
                            return "application/x-zip";
                    }

                    @Override
                    public InputStream getStream() throws IOException {
                            return inputStream;
                    }

            };
    }
}
