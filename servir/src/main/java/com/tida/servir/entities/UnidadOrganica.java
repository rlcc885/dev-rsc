/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tida.servir.entities;

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
public class UnidadOrganica {

    public static Boolean ESTADO_BAJA = false;
    public static Boolean ESTADO_ALTA = true;
    public static String CODIGO_DEFAULT = "9999";
    public static String CUE_DEFAULT = "99999999";
    public static String SIGLA_DEFAULT = "IND";
    public static String NOMBRE_DEFAULT = "Unidad Organica indeterminada";
    public static Integer NIVEL_DEFAULT = 1;
    
        //    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private int id;
    @Validate("required")
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @PrimaryKeyJoinColumn
    private Entidad_BK entidad;
//    @Validate("required")
//    @OneToMany(cascade = CascadeType.ALL)
    private List<Cargoxunidad> cargos = new ArrayList<Cargoxunidad>();
    private String cod_und_organica; // Código de la unidad organica
    @Validate("required")
    private String den_und_organica; //Denominación del órgano
    private String localidad;
    private DatoAuxiliar cod_ubi_dist; //ubigeo distrito
    private DatoAuxiliar cod_ubi_prov; //ubigeo provincia
    private DatoAuxiliar cod_ubi_dept; //ubigeo depto
    private Boolean estado; // para manejo del borrado lógico
    private String cue;
    private String sigla;
    @Validate("required")
    private Integer nivel;
//    private String tipoActividad;
//    @Validate("required")    
    private DatoAuxiliar categoriauo;
    private DatoAuxiliar tipovia;
    private DatoAuxiliar tipozona;
    private String desczona;
    private UnidadOrganica unidadOrganica;

    @ManyToOne
    public DatoAuxiliar getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(DatoAuxiliar cod_ubi_dist) {
        this.cod_ubi_dist = cod_ubi_dist;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(DatoAuxiliar cod_ubi_prov) {
        this.cod_ubi_prov = cod_ubi_prov;
    }

    @ManyToOne
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

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @ManyToOne
    public Entidad_BK getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad_BK entidad) {
        this.entidad = entidad;
    }

    @OneToMany(cascade = CascadeType.ALL)
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


    @ManyToOne
    public UnidadOrganica getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(UnidadOrganica unidadorganica) {
        this.unidadOrganica = unidadorganica;
    }
    
//    private int unidadorganica_id;

//    public String getTipoActividad() {
//        return tipoActividad;
//    }
//
//    public void setTipoActividad(String tipoActividad) {
//        this.tipoActividad = tipoActividad;
//    }
    
//    public Integer getUnidadorganica_id() {
//        return unidadorganica_id;
//    }
//
//    public void setUnidadorganica_id(Integer unidadorganica_id) {
//        this.unidadorganica_id = unidadorganica_id;
//    }

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
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
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

    public String getCue() {
        return cue;
    }

    public void setCue(String cue) {
        this.cue = cue;
    }
    
    @ManyToOne
    public DatoAuxiliar getCategoriauo() {
        return categoriauo;
    }

    public void setCategoriauo(DatoAuxiliar categoriauo) {
        this.categoriauo = categoriauo;
    }
    
    @ManyToOne
    public DatoAuxiliar getTipovia() {
        return tipovia;
    }

    public void setTipovia(DatoAuxiliar tipovia) {
        this.tipovia = tipovia;
    }
    
    @ManyToOne
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
