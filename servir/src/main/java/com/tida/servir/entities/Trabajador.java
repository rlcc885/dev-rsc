package com.tida.servir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
@Table(name = "RSC_TRABAJADOR")
public class Trabajador implements Serializable // extends Persona 
{

    private static final String DNI = "DNI";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_TRABAJADOR_ID_SEQ", allocationSize = 1)
    @NonVisual
    private Long id;
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
    @ManyToOne
    @NonVisual
    private DatoAuxiliar pais;
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
    @ManyToOne
    @NonVisual
    private DatoAuxiliar gruposanguineo;
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
    private String emergenciaTelefonoAlternativo1;
    @NonVisual
    private String emergenciaTelefonoAlternativo2;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar regimenpensionario;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar sistemapensionario;
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
    @ManyToOne
    @NonVisual
    private DatoAuxiliar tipovia;
    @ManyToOne
    @NonVisual
    private DatoAuxiliar tipozona;
    @NonVisual
    private String numregimenpensionario;
    @NonVisual
    private String nombreeps;
    @NonVisual
    private Boolean eps;
    @NonVisual
    private Boolean recibepension;
    @ManyToOne
    @NonVisual
    private Entidad entidad;

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Boolean getRecibepension() {
        return recibepension;
    }

    public void setRecibepension(Boolean recibepension) {
        this.recibepension = recibepension;
    }

    public Boolean getEps() {
        return eps;
    }

    public void setEps(Boolean eps) {
        this.eps = eps;
    }

    public String getNombreeps() {
        return nombreeps;
    }

    public void setNombreeps(String nombreeps) {
        this.nombreeps = nombreeps;
    }

    public String getNumregimenpensionario() {
        return numregimenpensionario;
    }

    public void setNumregimenpensionario(String numregimenpensionario) {
        this.numregimenpensionario = numregimenpensionario;
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

    public DatoAuxiliar getGruposanguineo() {
        return gruposanguineo;
    }

    public void setGruposanguineo(DatoAuxiliar gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    //    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public DatoAuxiliar getPais() {
        return pais;
    }

    public void setPais(DatoAuxiliar pais) {
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

    public String getFormacionInfAdicional() {
        return formacionInfAdicional;
    }

    public void setFormacionInfAdicional(String formacionInfAdicional) {
        this.formacionInfAdicional = formacionInfAdicional;
    }

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

    public DatoAuxiliar getSistemapensionario() {
        return sistemapensionario;
    }

    public void setSistemapensionario(DatoAuxiliar sistemapensionario) {
        this.sistemapensionario = sistemapensionario;
    }

    public DatoAuxiliar getRegimenpensionario() {
        return regimenpensionario;
    }

    public void setRegimenpensionario(DatoAuxiliar regimenpensionario) {
        this.regimenpensionario = regimenpensionario;
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
    public List<Ant_Laborales> ant_Laborales = new ArrayList<Ant_Laborales>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
    public List<Publicacion> publicaciones = new ArrayList<Publicacion>();
//    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
//    @NonVisual
//    public List<Titulo> titulos = new ArrayList<Titulo>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
    public List<Certificacion> certificaciones = new ArrayList<Certificacion>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
    public List<Familiar> familiares = new ArrayList<Familiar>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
    public List<CargoAsignado> cargosAsignados = new ArrayList<CargoAsignado>();
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @NonVisual
    public List<Curso> cursos = new ArrayList<Curso>();

    public List<CargoAsignado> getCargosAsignados() {
        return cargosAsignados;
    }

    public void setCargosAsignados(List<CargoAsignado> cargosAsignados) {
        this.cargosAsignados = cargosAsignados;
    }

//    public List<Titulo> getTitulos() {
//        return titulos;
//    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

//    public void setTitulos(List<Titulo> titulos) {
//        this.titulos = titulos;
//    }

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