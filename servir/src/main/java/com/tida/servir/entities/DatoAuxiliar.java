package com.tida.servir.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */
/*
@NamedNativeQueries({
    @NamedNativeQuery(name = "callSpDatoAuxiliar",
        query = "CALL SP_DATOAUXILIAR(?,:tabla)",
        resultClass = Long.class,    
        hints = {
        @QueryHint(name = "org.hibernate.callable", value = "true")
    })
})
*/
@Entity
@Table(name = "RSC_DATOAUXILIAR")
public class DatoAuxiliar implements Serializable {

    @NonVisual
    private Long id;
    private String nombreTabla;
    private Boolean flg_altatrabajador; //flag 
    private Boolean flg_creausuario;
    private String tablaRelacion; // si lo relacionamos con otra tabla
    private long relacionCodigo; // el valor a matchear en la tabla relacio
    private String valor; // El valor del campo
    private long codigo; // el codigo definido para ese valor. Debe ser único en la tabla
    private Boolean editable;
    private String abrev;

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatoAuxiliar other = (DatoAuxiliar) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public long getRelacionCodigo() {
        return relacionCodigo;
    }

    public void setRelacionCodigo(long relacionCodigo) {
        this.relacionCodigo = relacionCodigo;
    }

    public String getTablaRelacion() {
        return tablaRelacion;
    }

    public void setTablaRelacion(String tablaRelacion) {
        this.tablaRelacion = tablaRelacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getEditable() {
        return editable;
    }
    public void setEditable(Boolean editable){
        this.editable = editable;
    }
    
    public Boolean getFlg_altatrabajador() {
        return flg_altatrabajador;
    }

    public void setFlg_altatrabajador(Boolean flg_altatrabajador) {
        this.flg_altatrabajador = flg_altatrabajador;
    }

    public Boolean getFlg_creausuario() {
        return flg_creausuario;
    }

    public void setFlg_creausuario(Boolean flg_creausuario) {
        this.flg_creausuario = flg_creausuario;
    }
    

}