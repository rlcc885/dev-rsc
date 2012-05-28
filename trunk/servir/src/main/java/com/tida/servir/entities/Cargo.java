package com.tida.servir.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Cargo {

    public static String ESTADO_BAJA = "Baja";
    public static String ESTADO_ALTA = "Alta";
    public static String CODIGO_DEFAULT = "9999";
    public static String DEN_DEFAULT = "Cargo a designar";
    public static Integer CANT_DEFAULT = 1;
    public static Integer CANT_MAX = 999999;
    //	@Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//  @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private long id;
//	@PrimaryKeyJoinColumn
//	@Validate("required")
//	@ManyToOne
    /*
     * @Validate("required") private EntidadUEjecutora entidadUE;
     */
    /*
     * @Validate("required") private Integer nivel_unidad_organica;
     */
    @Validate("required")
    private UnidadOrganica und_organica;
    @Validate("required")
    private String cod_cargo;
    @Validate("required")
    private String den_cargo;
    private String estado;
    private DatoAuxiliar reg_lab_con;
    private Integer horas_x_sem;
    private Boolean personasCargo;

    /*
     * @Validate("required") private String cod_est_cargo;
     */
    private DatoAuxiliar clasificacion_funcional;
    private Boolean req_hab_profesional;
    private Boolean dec_jurada_byr;
    private String situacion_CAP;
    private Boolean presupuestado_PAP;

    /*
     * @Validate("required") private Integer plantillaremunerativa;
     */
    /*
     * private Integer ctd_ptos_ocupados_cap;
     *
     * private Integer ctd_ptos_previstos_cap;
     *
     * private Integer ctd_ptos_presupuesto_pap;
     *
     */
    private Integer ctd_puestos_total;
    private DatoAuxiliar grupoOcupacional;
    private DatoAuxiliar nivelRemunerativo;

    public Boolean getPersonasCargo() {
        return personasCargo;
    }

    public void setPersonasCargo(Boolean personasCargo) {
        this.personasCargo = personasCargo;
    }

    public String getCod_cargo() {
        return cod_cargo;
    }

    public void setCod_cargo(String cod_cargo) {
        this.cod_cargo = cod_cargo;
    }
    /*
     * @ManyToOne public DatoAuxiliar getCod_est_cargo() {
     *
     * return cod_est_cargo; }
     *
     * public void setCod_est_cargo(DatoAuxiliar cod_est_cargo) {
     * this.cod_est_cargo = cod_est_cargo; }
     */

    @ManyToOne
    public DatoAuxiliar getClasificacion_funcional() {
        return clasificacion_funcional;
    }

    public void setClasificacion_funcional(DatoAuxiliar clasificacion_funcional) {
        this.clasificacion_funcional = clasificacion_funcional;
    }

    /*
     * public EntidadUEjecutora getEntidadUE() { return entidadUE; }
     *
     * public void setEntidadUE(EntidadUEjecutora entidadUE) { this.entidadUE =
     * entidadUE;
    }
     */

    /*
     * public Integer getNivel_unidad_organica() { return nivel_unidad_organica;
     * }
     *
     * public void setNivel_unidad_organica(Integer nivel_unidad_organica) {
     * this.nivel_unidad_organica = nivel_unidad_organica; }
     */
    public Boolean getPresupuestado_PAP() {
        return presupuestado_PAP;
    }

    public void setPresupuestado_PAP(Boolean presupuestado_PAP) {
        this.presupuestado_PAP = presupuestado_PAP;
    }

    public String getSituacion_CAP() {
        return situacion_CAP;
    }

    public void setSituacion_CAP(String situacion_CAP) {
        this.situacion_CAP = situacion_CAP;
    }

    /*
     * public Integer getCtd_ptos_ocupados_cap() { return ctd_ptos_ocupados_cap;
     * }
     *
     * public void setCtd_ptos_ocupados_cap(Integer ctd_ptos_ocupados_cap) {
     * this.ctd_ptos_ocupados_cap = ctd_ptos_ocupados_cap; }
     *
     *
     * public Integer getCtd_ptos_presupuesto_pap() { return
     * ctd_ptos_presupuesto_pap; }
     *
     * public void setCtd_ptos_presupuesto_pap(Integer ctd_ptos_presupuesto_pap)
     * { this.ctd_ptos_presupuesto_pap = ctd_ptos_presupuesto_pap; }
     *
     * public Integer getCtd_ptos_previstos_cap() { return
     * ctd_ptos_previstos_cap; }
     *
     * public void setCtd_ptos_previstos_cap(Integer ctd_ptos_previstos_cap) {
     * this.ctd_ptos_previstos_cap = ctd_ptos_previstos_cap; }
     *
     *
     */
    public Integer getCtd_puestos_total() {
        return ctd_puestos_total;
    }

    public void setCtd_puestos_total(Integer ctd_puestos_total) {
        this.ctd_puestos_total = ctd_puestos_total;
    }

    public Boolean getDec_jurada_byr() {
        return dec_jurada_byr;
    }

    public void setDec_jurada_byr(Boolean dec_jurada_byr) {
        this.dec_jurada_byr = dec_jurada_byr;
    }

    public String getDen_cargo() {
        return den_cargo;
    }

    public void setDen_cargo(String den_cargo) {
        this.den_cargo = den_cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getHoras_x_sem() {
        return horas_x_sem;
    }

    public void setHoras_x_sem(Integer horas_x_sem) {
        this.horas_x_sem = horas_x_sem;
    }

    /*
     * public Integer getPlantillaremunerativa() { return plantillaremunerativa;
     * }
     *
     * public void setPlantillaremunerativa(Integer plantillaremunerativa) {
     * this.plantillaremunerativa = plantillaremunerativa; }
     */
    @ManyToOne
    public DatoAuxiliar getReg_lab_con() {
        return reg_lab_con;
    }

    public void setReg_lab_con(DatoAuxiliar reg_lab_con) {
        this.reg_lab_con = reg_lab_con;
    }

    public Boolean getReq_hab_profesional() {
        return req_hab_profesional;
    }

    public void setReq_hab_profesional(Boolean req_hab_profesional) {
        this.req_hab_profesional = req_hab_profesional;
    }

    @ManyToOne
    public UnidadOrganica getUnd_organica() {
        return und_organica;
    }

    public void setUnd_organica(UnidadOrganica und_organica) {
        this.und_organica = und_organica;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cargo other = (Cargo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @ManyToOne
    public DatoAuxiliar getNivelRemunerativo() {
        return nivelRemunerativo;
    }

    public void setNivelRemunerativo(DatoAuxiliar nivelRemunerativo) {
        this.nivelRemunerativo = nivelRemunerativo;
    }

    @ManyToOne
    public DatoAuxiliar getGrupoOcupacional() {
        return grupoOcupacional;
    }

    public void setGrupoOcupacional(DatoAuxiliar grupoOcupacional) {
        this.grupoOcupacional = grupoOcupacional;
    }
}
