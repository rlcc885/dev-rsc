package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;


@Entity
@Table(name = "RSC_FAMILIAR")
public class Familiar // extends Persona
{

    @NonVisual
    private Long id;
    @Validate("required")
    private DatoAuxiliar parentesco;
    private String sexo;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Validate("required")
    private String nombres;        
    @Validate("required")
    private String apellidoPaterno;
    @Validate("required")
    private String apellidoMaterno;
    @Validate("required")
    private DatoAuxiliar tipoDocumento;
    @Validate("required")
    private String nroDocumento;
    private DatoAuxiliar estadoCivil;
    private boolean agregadoTrabajador;
    private Boolean validado;
    
    private Entidad entidad;
    private Trabajador trabajador;
    

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_FAMILIAR_ID_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne
    public DatoAuxiliar getParentesco() {
        return parentesco;
    }

    public void setParentesco(DatoAuxiliar parentesco) {
        this.parentesco = parentesco;
    }
    
    //@ManyToOne
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres != null) {
            this.nombres = nombres.trim().toUpperCase();
        }
    }

    @ManyToOne
    public DatoAuxiliar getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(DatoAuxiliar tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }
    
    @ManyToOne
    public DatoAuxiliar getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(DatoAuxiliar estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    }
    
        public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    @ManyToOne(optional = false)
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    @ManyToOne(optional = false)
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

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
