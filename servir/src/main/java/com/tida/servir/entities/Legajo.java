package com.tida.servir.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
@Table(name = "RSC_LEGAJO")
public class Legajo implements Serializable {
//	@Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//  @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NonVisual
    private long id;
    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
    private Entidad entidad;
    @Validate("required")
//  @ManyToOne(cascade = CascadeType.PERSIST)
    private Trabajador trabajador;
    @Validate("required")
    private String cod_legajo;
    @NonVisual
//  @OneToMany(mappedBy = "legajo", cascade = CascadeType.ALL)
    public List<ConstanciaDocumental> constanciasDocumentales = new ArrayList<ConstanciaDocumental>();

    public Legajo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Legajo other = (Legajo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @ManyToOne
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador Trabajador) {
        this.trabajador = Trabajador;
    }

    @OneToMany(mappedBy = "legajo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<ConstanciaDocumental> getConstanciasDocumentales() {
        return constanciasDocumentales;
    }

    public void setConstanciasDocumentales(List<ConstanciaDocumental> cds) {
        this.constanciasDocumentales = cds;
    }

    public String getCod_legajo() {
        return cod_legajo;
    }

    public void setCod_legajo(String cod_legajo) {
        this.cod_legajo = cod_legajo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYENTITY_SEQ")
    @SequenceGenerator(name = "MYENTITY_SEQ", sequenceName = "RSC_LEGAJO_ID_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    public long getid() {
        return id;
    }

    public void setid(long id) {
        this.id = id;
    }

    @ManyToOne
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
}
