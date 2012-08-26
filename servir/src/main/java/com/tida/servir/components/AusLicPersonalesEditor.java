package com.tida.servir.components;

import com.tida.servir.entities.AusLicPersonal;
import com.tida.servir.entities.CargoAsignado;
import com.tida.servir.entities.Entidad;
import com.tida.servir.entities.Usuario;
import helpers.Errores;
import helpers.Helpers;
import helpers.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 *	Clase que maneja el TAB del editor de Remuneraciones.
 *  
 */
public class AusLicPersonalesEditor {

    @SuppressWarnings("unused")
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String _zone;
    
    @Property
    @Parameter
    private CargoAsignado actual_asignado;
    
    @Property
    @Parameter(required=false)
    private Boolean readOnly;

    @Property
    private AusLicPersonal ausLic;
    
    @Inject
    private Session session;
    
    @Component(id = "formularioauslicpersonales")
    private Form formularioAusLicPersonales;
    
    @Property
    @SessionState
    private Usuario _usuario;
    
    @Property
    @SessionState
    private Entidad _oi;
    
    @InjectComponent
    private Envelope envelope;

    public List<String> getTiposAusLic() {
        return Helpers.getValorTablaAuxiliar("TipoAusencias", session);
    }

//    public boolean getNoEditable() {
//        return !getEditable();
//    }
//
//    public boolean getEditable() {
//        if(readOnly != null) {
//            return ((!readOnly) && Permisos.puedeEscribir(_usuario, _oi) );
//        } else {
//            return Permisos.puedeEscribir(_usuario, _oi);
//        }
//    }

//    public PrimaryKeyEncoder<Long, AusLicPersonal> getEncoder() {
//        return new PrimaryKeyEncoder<Long, AusLicPersonal>()
//     {
//
//            public Long toKey(AusLicPersonal value) {
//                return value.getId();
//            }
//
//            public void prepareForKeys(List<Long> keys) {
//            }
//            
//            public AusLicPersonal toValue(Long key) {
//                return (AusLicPersonal) session.get(AusLicPersonal.class, key);
//            }
//
//            public Class<Long> getKeyType() {
//                return Long.class;
//            }
//        };
//    }

    @CommitAfter
    public Object onSuccess() {
        for (AusLicPersonal e : actual_asignado.getAusLicPersonales()) {
            if (e.getFec_hasta().before(e.getFec_desde())) {
                Logger logger = new Logger();
                logger.loguearError(session, _usuario, e.getId().toString(),
                        Logger.CODIGO_ERROR_FECHA_HASTA_PREVIA_DESDE,
                        Errores.ERROR_FECHA_HASTA_PREVIA_DESDE, Logger.TIPO_OBJETO_AUSLICPERSONAL);

                formularioAusLicPersonales.recordError(Errores.FECHA_DESDE_HASTA_COMPARACION);
                return this;
            }
        }
        
        envelope.setContents(helpers.Constantes.AUSENCIA_EXITO);
        formularioAusLicPersonales.clearErrors();
        return this;
    }

    @CommitAfter
    Object onAddRow() {
        AusLicPersonal auslic = new AusLicPersonal();
        if (actual_asignado.getAusLicPersonales() == null) {
            actual_asignado.setAusLicPersonales(new ArrayList<AusLicPersonal>());
        }
        actual_asignado.getAusLicPersonales().add(auslic);
        session.saveOrUpdate(actual_asignado);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(auslic.getId()), Logger.CODIGO_OPERACION_ALTA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_AUSLICPERSONAL);
        return auslic;
    }

    @CommitAfter
    void onRemoveRow(AusLicPersonal auslic) {
        actual_asignado.getAusLicPersonales().remove(auslic);
        session.delete(auslic);
        new Logger().loguearOperacion(session, _usuario, String.valueOf(auslic.getId()), Logger.CODIGO_OPERACION_BAJA, Logger.RESULTADO_OPERACION_OK, Logger.TIPO_OBJETO_AUSLICPERSONAL);
    }

    Object onFailure() {
        return this;
    }
}
