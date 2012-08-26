/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

import java.util.Date;

/**
 *
 * @author Morgan
 */
public class AusLicPersonalCSV {
    
    private String codigo_entidadUE;
    private String codigo_cargo;
    private String tipo_documento;
    private String nro_documento;
    private String tipo;
    private Date fecha_desde;
    private Date fecha_hasta;
    private String motivo ;

    public String getCodigo_cargo() {
        return codigo_cargo;
    }

    public void setCodigo_cargo(String codigo_cargo) {
        this.codigo_cargo = codigo_cargo;
    }

    public String getCodigoEntidadUE() {
        return codigo_entidadUE;
    }

    public void setCodigEntidadUE(String codigo_entidadUE) {
        this.codigo_entidadUE = codigo_entidadUE;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
	
}
