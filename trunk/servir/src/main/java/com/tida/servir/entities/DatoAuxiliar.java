package com.tida.servir.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */
@Entity
public class DatoAuxiliar {

    public enum TipoDeAcceso {

        SoloLectura,
        ValorEditable,
        Editable
    }
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;
//    @ManyToOne(optional = false)
    private String nombreTabla;
    private String tablaRelacion; // si lo relacionamos con otra tabla
    private long relacionCodigo; // el valor a matchear en la tabla relacio
    private String valor; // El valor del campo
    private long codigo; // el codigo definido para ese valor. Debe ser único en la tabla
    private TipoDeAcceso tipoDeAcceso;

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

    public TipoDeAcceso getTipoDeAcceso() {
        return tipoDeAcceso;
    }

    public void setTipoDeAcceso(TipoDeAcceso tipoDeAcceso) {
        this.tipoDeAcceso = tipoDeAcceso;
    }
}