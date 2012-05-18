/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Batch.Helpers;

/**
 *
 * @author Morgan
 */
public class RemuneracionPersonalCSV {

    String codigo_entidadUE;
    String codigo_cargo;
    String tipo_documento;
    String nro_documento;
    String codigo_concepto_remunerativo;
    String importe;

    public String getCodigo_cargo() {
        return codigo_cargo;
    }

    public void setCodigo_cargo(String codigo_cargo) {
        this.codigo_cargo = codigo_cargo;
    }

    public String getCodigo_concepto_remunerativo() {
        return codigo_concepto_remunerativo;
    }

    public void setCodigo_concepto_remunerativo(String codigo_concepto_remunerativo) {
        this.codigo_concepto_remunerativo = codigo_concepto_remunerativo;
    }

    public String getCodigo_entidadUE() {
        return codigo_entidadUE;
    }

    public void setCodigo_entidadUE(String codigo_entidadUE) {
        this.codigo_entidadUE = codigo_entidadUE;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
}
