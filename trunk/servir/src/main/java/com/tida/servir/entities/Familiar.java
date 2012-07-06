package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;


@Entity
@Table(name = "RSC_FAMILIAR")
public class Familiar // extends Persona
{
    /// cosas de la clase PERSONA

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        if (apellidoMaterno != null) {
            this.apellidoMaterno = apellidoMaterno.trim().toUpperCase();
        }
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        if (apellidoPaterno != null) {
            this.apellidoPaterno = apellidoPaterno.trim().toUpperCase();
        }
    }

    public String getDomicilioCodigoPostal() {
        return domicilioCodigoPostal;
    }

    public void setDomicilioCodigoPostal(String domicilioCodigoPostal) {
        this.domicilioCodigoPostal = domicilioCodigoPostal;
    }

    public String getDomicilioDireccion() {
        return domicilioDireccion;
    }

    public void setDomicilioDireccion(String domicilioDireccion) {
        if (domicilioDireccion != null) {
            this.domicilioDireccion = domicilioDireccion.trim();
        }
    }

    public String getEsSalud() {
        return esSalud;
    }

    public void setEsSalud(String esSalud) {
        this.esSalud = esSalud;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNivelInstruccion() {
        return nivelInstruccion;
    }

    public void setNivelInstruccion(String nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres != null) {
            this.nombres = nombres.trim().toUpperCase();
        }
    }
    
    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(DatoAuxiliar codUbiDist) {
        cod_ubi_dist = codUbiDist;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(DatoAuxiliar codUbiProv) {
        cod_ubi_prov = codUbiProv;
    }

    @ManyToOne
    public DatoAuxiliar getCod_ubi_dept() {
        return cod_ubi_dept;
    }

    public void setCod_ubi_dept(DatoAuxiliar codUbiDept) {
        cod_ubi_dept = codUbiDept;
    }

    @ManyToOne
    public DatoAuxiliar getCod_dom_dist() {
        return cod_dom_dist;
    }

    public void setCod_dom_dist(DatoAuxiliar codDomDist) {
        cod_dom_dist = codDomDist;
    }

    @ManyToOne
    public DatoAuxiliar getCod_dom_prov() {
        return cod_dom_prov;
    }

    public void setCod_dom_prov(DatoAuxiliar codDomProv) {
        cod_dom_prov = codDomProv;
    }

    @ManyToOne
    public DatoAuxiliar getCod_dom_dept() {
        return cod_dom_dept;
    }

    public void setCod_dom_dept(DatoAuxiliar codDomDept) {
        cod_dom_dept = codDomDept;
    }
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;
    @Validate("required")
    private String tipoDocumento;
    @Validate("required")
    private String nroDocumento;
    @Validate("required")
    private String apellidoPaterno;
    @Validate("required")
    private String apellidoMaterno;
    @Validate("required")
    private String nombres;
    @NonVisual
    private String sexo;
    @NonVisual
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @NonVisual
    private String pais;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_dist;
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_prov;
    @NonVisual
    @Validate("required")
    public DatoAuxiliar cod_ubi_dept;
    @NonVisual
    public String nacionalidad;
    @NonVisual
    public String estadoCivil;
    @NonVisual
    public String domicilioDireccion;
    @NonVisual
    public DatoAuxiliar cod_dom_dist;
    @NonVisual
    public DatoAuxiliar cod_dom_prov;
    @NonVisual
    public DatoAuxiliar cod_dom_dept;
    @NonVisual
    public String domicilioCodigoPostal;
    @NonVisual
    public String esSalud;
    @NonVisual
    public String grupoSanguineo;
    @NonVisual
    public String nivelInstruccion;
    @NonVisual
    private Boolean agregadoTrabajador;

    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    }
    
    

    /// cosas de la clase PERSONA
//	@ManyToMany(cascade = CascadeType.ALL,targetEntity=Trabajador.class)
    private Trabajador trabajador = new Trabajador();

    @ManyToOne
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    @Validate("required")
    private String parentesco;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Familiar other = (Familiar) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
