package com.tida.servir.entities;

import java.util.Arrays;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

/**
 * Conceptos Remunerativos
 */
@Entity
@Table(name="CON_REMUNERATIVOS")
public class ConceptoRemunerativo {
   public static List<String> PERIODICIDADES = Arrays.asList(new String[]{"Mensual", "Semestral", "Anual", "Única vez"});

    @NonVisual
    private Long id;

    private Entidad_BK entidadUE;

    private String codigo;
    
    private String descripcion;
    
    private String clasificacion;

    private String conceptoStd;

    private String sustento_legal;
    
    private String periodicidad;
            
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Entidad_BK getEntidadUE() {
        return entidadUE;
    }

    public void setEntidadUE(Entidad_BK entidadUE) {
        this.entidadUE = entidadUE;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getConceptoStd() {
		return conceptoStd;
	}

    public void setConceptoStd(String conceptoStd) {
		this.conceptoStd = conceptoStd;
	}

	public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getSustento_legal() {
        return sustento_legal;
    }

    public void setSustento_legal(String sustento_legal) {
        this.sustento_legal = sustento_legal;
    }
    

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptoRemunerativo other = (ConceptoRemunerativo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}