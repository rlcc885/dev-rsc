/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_UNIDADORGANICA")
public class UnidadOrganica implements Serializable {

    public static Boolean ESTADO_BAJA = false;
    public static Boolean ESTADO_ALTA = true;
    public static String CODIGO_DEFAULT = "9999";
    public static String CUE_DEFAULT = "99999999";
    public static String SIGLA_DEFAULT = "IND";
    public static String NOMBRE_DEFAULT = "Unidad Organica indeterminada";
    public static Integer NIVEL_DEFAULT = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_UNIDADORGANICA_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @ManyToOne
    @Validate("required")
    private Entidad entidad;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Cargoxunidad> cargos = new ArrayList<Cargoxunidad>();
    @NonVisual
    private String cod_und_organica; // Código de la unidad organica
    @Validate("required")
    private String den_und_organica; //Denominación del órgano
    @NonVisual
    @Column(name = "DESCVIA")
    private String localidad;
    @ManyToOne
    private DatoAuxiliar cod_ubi_dist; //ubigeo distrito
    @ManyToOne
    private DatoAuxiliar cod_ubi_prov; //ubigeo provincia
    @ManyToOne
    private DatoAuxiliar cod_ubi_dept; //ubigeo depto
    @NonVisual
    private Boolean estado; // para manejo del borrado lógico
    private String sigla;
    @Validate("required")
    private Integer nivel;
    @ManyToOne
    private DatoAuxiliar categoriauo;
    @ManyToOne
    private DatoAuxiliar tipovia;
    @ManyToOne
    private DatoAuxiliar tipozona;
    @NonVisual
    private String desczona;
    @ManyToOne
    @Validate("required")
    private UnidadOrganica unidadorganica;

    public DatoAuxiliar getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(DatoAuxiliar cod_ubi_dist) {
        this.cod_ubi_dist = cod_ubi_dist;
    }

    public DatoAuxiliar getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(DatoAuxiliar cod_ubi_prov) {
        this.cod_ubi_prov = cod_ubi_prov;
    }

    public DatoAuxiliar getCod_ubi_dept() {
        return cod_ubi_dept;
    }

    public void setCod_ubi_dept(DatoAuxiliar cod_ubi_dept) {
        this.cod_ubi_dept = cod_ubi_dept;
    }

    public String getCod_und_organica() {
        return cod_und_organica;
    }

    public void setCod_und_organica(String cod_und_organica) {
        this.cod_und_organica = cod_und_organica;
    }

    public String getDen_und_organica() {
        return den_und_organica;
    }

    public void setDen_und_organica(String den_und_organica) {
        this.den_und_organica = den_und_organica;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public List<Cargoxunidad> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargoxunidad> cargos) {
        this.cargos = cargos;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public UnidadOrganica getUnidadorganica() {
        return unidadorganica;
    }

    public void setUnidadorganica(UnidadOrganica unidadorganica) {
        this.unidadorganica = unidadorganica;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UnidadOrganica other = (UnidadOrganica) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    public DatoAuxiliar getCategoriauo() {
        return categoriauo;
    }

    public void setCategoriauo(DatoAuxiliar categoriauo) {
        this.categoriauo = categoriauo;
    }

    public DatoAuxiliar getTipovia() {
        return tipovia;
    }

    public void setTipovia(DatoAuxiliar tipovia) {
        this.tipovia = tipovia;
    }

    public DatoAuxiliar getTipozona() {
        return tipozona;
    }

    public void setTipozona(DatoAuxiliar tipozona) {
        this.tipozona = tipozona;
    }

    public String getDesczona() {
        return desczona;
    }

    public void setDesczona(String desczona) {
        this.desczona = desczona;
    }
}
