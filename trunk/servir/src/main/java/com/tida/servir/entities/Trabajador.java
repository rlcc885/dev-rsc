package com.tida.servir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;



/**
 *
 * @author ale
 */
@Entity
@Table(name = "RSC_TRABAJADOR")
public class Trabajador implements Serializable // extends Persona 
{
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    ///cosas de la clase PERSONA

    private static final String DNI = "DNI";
    @Id
    @GeneratedValue
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
    @ManyToOne
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_dist;
    @ManyToOne
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_prov;
    @ManyToOne
    @NonVisual
    @Validate("required")
    private DatoAuxiliar cod_ubi_dept;
    @NonVisual
    private String nacionalidad;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar estadocivil;
    @NonVisual
    private String domicilioDireccion;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar cod_dom_dist;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar cod_dom_prov;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar cod_dom_dept;
    @NonVisual
    private String domicilioCodigoPostal;
    @NonVisual
    private String esSalud;
    @NonVisual
    private String grupoSanguineo;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar tipodiscapacidad;
    @NonVisual
    private Integer nroCertificadoCONADIS;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar nivelinstruccion;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar formacionprofesional;
    private String formacionInfAdicional;
    @NonVisual
    private String emergenciaNombre;
    @NonVisual
    private String emergenciaDomicilio;
    @NonVisual
    private String emergenciaTelefonos;
    @NonVisual
    private String emergenciaTelefonoAlternativo1;
    @NonVisual
    private String emergenciaTelefonoAlternativo2;
    @NonVisual
    private String regimenPensionario;
    @NonVisual
    private String emailPersonal;
    @NonVisual
    private String emailLaboral;
    @NonVisual
    private String nroRUC;
    @NonVisual
    private String codigoOSCE;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar documentoidentidad;
    @NonVisual
    private String telefonocelular;
    @NonVisual
    private String telefonofijo;
    
    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }
    
    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public DatoAuxiliar getDocumentoidentidad() {
        return documentoidentidad;
    }

    public void setDocumentoidentidad(DatoAuxiliar documentoidentidad) {
        this.documentoidentidad = documentoidentidad;
    }
    
    public DatoAuxiliar getFormacionprofesional() {
        return formacionprofesional;
    }

    public void setFormacionprofesional(DatoAuxiliar formacionprofesional) {
        this.formacionprofesional = formacionprofesional;
    }

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
        this.domicilioDireccion = domicilioDireccion;
    }

    public String getEsSalud() {
        return esSalud;
    }

    public void setEsSalud(String esSalud) {
        this.esSalud = esSalud;
    }

    public DatoAuxiliar getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(DatoAuxiliar estadocivil) {
        this.estadocivil = estadocivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

//    public DatoAuxiliar getFormacionProfesional() {
//        return formacionProfesional;
//    }
//
//    public void setFormacionProfesional(DatoAuxiliar formacionProfesional) {
//        this.formacionProfesional = formacionProfesional;
//    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    //    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public DatoAuxiliar getNivelinstruccion() {
        return nivelinstruccion;
    }

    public void setNivelinstruccion(DatoAuxiliar nivelinstruccion) {
        this.nivelinstruccion = nivelinstruccion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres != null) {
            this.nombres = nombres.trim().toUpperCase();
        }
    }

    public Integer getNroCertificadoCONADIS() {
        return nroCertificadoCONADIS;
    }

    public void setNroCertificadoCONADIS(Integer nroCertificadoCONADIS) {
        this.nroCertificadoCONADIS = nroCertificadoCONADIS;
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

    public DatoAuxiliar getTipodiscapacidad() {
        return tipodiscapacidad;
    }

    public void setTipodiscapacidad(DatoAuxiliar tipodiscapacidad) {
        this.tipodiscapacidad = tipodiscapacidad;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public DatoAuxiliar getCod_ubi_dist() {
        return cod_ubi_dist;
    }

    public void setCod_ubi_dist(DatoAuxiliar codUbiDist) {
        cod_ubi_dist = codUbiDist;
    }

    public DatoAuxiliar getCod_ubi_prov() {
        return cod_ubi_prov;
    }

    public void setCod_ubi_prov(DatoAuxiliar codUbiProv) {
        cod_ubi_prov = codUbiProv;
    }

    public DatoAuxiliar getCod_ubi_dept() {
        return cod_ubi_dept;
    }

    public void setCod_ubi_dept(DatoAuxiliar codUbiDept) {
        cod_ubi_dept = codUbiDept;
    }

    public DatoAuxiliar getCod_dom_dist() {
        return cod_dom_dist;
    }

    public void setCod_dom_dist(DatoAuxiliar codDomDist) {
        cod_dom_dist = codDomDist;
    }

    public DatoAuxiliar getCod_dom_prov() {
        return cod_dom_prov;
    }

    public void setCod_dom_prov(DatoAuxiliar codDomProv) {
        cod_dom_prov = codDomProv;
    }

    public DatoAuxiliar getCod_dom_dept() {
        return cod_dom_dept;
    }

    public void setCod_dom_dept(DatoAuxiliar codDomDept) {
        cod_dom_dept = codDomDept;
    }
//      @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    public String getFormacionInfAdicional() {
        return formacionInfAdicional;
    }

    public void setFormacionInfAdicional(String formacionInfAdicional) {
        this.formacionInfAdicional = formacionInfAdicional;
    }

    /// cosas de la clase PERSONA
    public String getEmergenciaNombre() {
        return emergenciaNombre;
    }

    public void setEmergenciaNombre(String emergenciaNombre) {
        this.emergenciaNombre = emergenciaNombre;
    }

    public String getEmergenciaDomicilio() {
        return emergenciaDomicilio;
    }

    public List<Certificacion> getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(List<Certificacion> certificaciones) {
        this.certificaciones = certificaciones;
    }

    public void setEmergenciaDomicilio(String emergenciaDomicilio) {
        this.emergenciaDomicilio = emergenciaDomicilio;
    }

    public String getEmergenciaTelefonos() {
        return emergenciaTelefonos;
    }

    public void setEmergenciaTelefonos(String emergenciaTelefonos) {
        this.emergenciaTelefonos = emergenciaTelefonos;
    }

    public String getRegimenPensionario() {
        return regimenPensionario;
    }

    public void setRegimenPensionario(String regimenPensionario) {
        this.regimenPensionario = regimenPensionario;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getEmailLaboral() {
        return emailLaboral;
    }

    public void setEmailLaboral(String emailLaboral) {
        this.emailLaboral = emailLaboral;
    }

    public String getNroRUC() {
        return nroRUC;
    }

    public void setNroRUC(String nroRUC) {
        this.nroRUC = nroRUC;
    }

    public String getCodigoOSCE() {
        return codigoOSCE;
    }

    public void setCodigoOSCE(String codigoOSCE) {
        this.codigoOSCE = codigoOSCE;
    }

    public String getEmergenciaTelefonoAlternativo1() {
        return emergenciaTelefonoAlternativo1;
    }

    public void setEmergenciaTelefonoAlternativo1(
            String emergenciaTelefonoAlternativo1) {
        this.emergenciaTelefonoAlternativo1 = emergenciaTelefonoAlternativo1;
    }

    public String getEmergenciaTelefonoAlternativo2() {
        return emergenciaTelefonoAlternativo2;
    }

    public void setEmergenciaTelefonoAlternativo2(
            String emergenciaTelefonoAlternativo2) {
        this.emergenciaTelefonoAlternativo2 = emergenciaTelefonoAlternativo2;
    }

    public List<Ant_Laborales> getAnt_Laborales() {
        return ant_Laborales;
    }

    public void setAnt_Laborales(List<Ant_Laborales> ant_Laborales) {
        this.ant_Laborales = ant_Laborales;
    }

    /*
     * Otros tabs
     */
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<Ant_Laborales> ant_Laborales = new ArrayList<Ant_Laborales>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<Publicacion> publicaciones = new ArrayList<Publicacion>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<MeritoDemerito> meritosdemeritos = new ArrayList<MeritoDemerito>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<Titulo> titulos = new ArrayList<Titulo>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<Certificacion> certificaciones = new ArrayList<Certificacion>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @ManyToMany(cascade = CascadeType.ALL,targetEntity=Familiar.class)
    public List<Familiar> familiares = new ArrayList<Familiar>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<CargoAsignado> cargosAsignados = new ArrayList<CargoAsignado>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
//  @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    public List<Curso> cursos = new ArrayList<Curso>();

    /*
     * A los legajos se acceden según los cargos @NonVisual @OneToMany(mappedBy
     * = "trabajador", cascade = CascadeType.ALL) public List<Legajo> legajos =
     * new ArrayList<Legajo>();
     */
    public List<CargoAsignado> getCargosAsignados() {
        return cargosAsignados;
    }

    public void setCargosAsignados(List<CargoAsignado> cargosAsignados) {
        this.cargosAsignados = cargosAsignados;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public List<MeritoDemerito> getMeritosdemeritos() {
        return meritosdemeritos;
    }

    public void setMeritosdemeritos(List<MeritoDemerito> meritosdemeritos) {
        this.meritosdemeritos = meritosdemeritos;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public List<Familiar> getFamiliares() {
        return familiares;
    }

    public void setFamiliares(List<Familiar> familiares) {
        this.familiares = familiares;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trabajador other = (Trabajador) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

}