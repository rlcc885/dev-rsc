package com.tida.servir.entities;

import java.util.Date;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;

/**
 *
 * @author ale
 */

@Entity
//@Table(name = "RSC_ANT_LABORALES_BK")
public class Ant_Laborales {

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        if (empresa != null)
            this.empresa = empresa.trim();
    }

    public Date getFec_egreso() {
        return fec_egreso;
    }

    public void setFec_egreso(Date fec_egreso) {
        this.fec_egreso = fec_egreso;
    }

    public Date getFec_ingreso() {
        return fec_ingreso;
    }

    public void setFec_ingreso(Date fec_ingreso) {
        this.fec_ingreso = fec_ingreso;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ant_Laborales other = (Ant_Laborales) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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



//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private Long id;

//    @ManyToOne(optional = false)
    private Trabajador trabajador;
    private String cargo;
    private String empresa;
    private String funcion;
    private Boolean entidadPublica;
    private String area;
    private String regLaboral;
    private Boolean agregadoTrabajador;
    private Boolean validado;
    

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public String getRegLaboral() {
        return regLaboral;
    }

    public void setRegLaboral(String regLaboral) {
        this.regLaboral = regLaboral;
    }
        
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    @ManyToOne(optional = false)
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
    
    public Boolean getAgregadoTrabajador() {
        return agregadoTrabajador;
    }

    public void setAgregadoTrabajador(Boolean agregadoTrabajador) {
        this.agregadoTrabajador = agregadoTrabajador;
    }

    public Boolean getEntidadPublica() {
        return entidadPublica;
    }

    public void setEntidadPublica(Boolean entidadPublica) {
        this.entidadPublica = entidadPublica;
    }
    
   @Temporal(TemporalType.DATE)
    private Date fec_ingreso;

   @Temporal(TemporalType.DATE)
    private Date fec_egreso;
   

}
