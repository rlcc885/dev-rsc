/**
 * TitularDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class TitularDTO  implements java.io.Serializable {
    private java.lang.String apellidos;

    private java.lang.String cargo;

    private java.util.Calendar fechaDesde_Cargo;

    private java.util.Calendar fechaHasta;

    private java.lang.String nombre;

    private long normaAlta;

    private java.lang.Long normaBaja;

    private java.lang.String numeroDocumento;

    private java.lang.String tipoDocumento;

    private java.lang.String tipoTitular;

    public TitularDTO() {
    }

    public TitularDTO(
           java.lang.String apellidos,
           java.lang.String cargo,
           java.util.Calendar fechaDesde_Cargo,
           java.util.Calendar fechaHasta,
           java.lang.String nombre,
           long normaAlta,
           java.lang.Long normaBaja,
           java.lang.String numeroDocumento,
           java.lang.String tipoDocumento,
           java.lang.String tipoTitular) {
           this.apellidos = apellidos;
           this.cargo = cargo;
           this.fechaDesde_Cargo = fechaDesde_Cargo;
           this.fechaHasta = fechaHasta;
           this.nombre = nombre;
           this.normaAlta = normaAlta;
           this.normaBaja = normaBaja;
           this.numeroDocumento = numeroDocumento;
           this.tipoDocumento = tipoDocumento;
           this.tipoTitular = tipoTitular;
    }


    /**
     * Gets the apellidos value for this TitularDTO.
     * 
     * @return apellidos
     */
    public java.lang.String getApellidos() {
        return apellidos;
    }


    /**
     * Sets the apellidos value for this TitularDTO.
     * 
     * @param apellidos
     */
    public void setApellidos(java.lang.String apellidos) {
        this.apellidos = apellidos;
    }


    /**
     * Gets the cargo value for this TitularDTO.
     * 
     * @return cargo
     */
    public java.lang.String getCargo() {
        return cargo;
    }


    /**
     * Sets the cargo value for this TitularDTO.
     * 
     * @param cargo
     */
    public void setCargo(java.lang.String cargo) {
        this.cargo = cargo;
    }


    /**
     * Gets the fechaDesde_Cargo value for this TitularDTO.
     * 
     * @return fechaDesde_Cargo
     */
    public java.util.Calendar getFechaDesde_Cargo() {
        return fechaDesde_Cargo;
    }


    /**
     * Sets the fechaDesde_Cargo value for this TitularDTO.
     * 
     * @param fechaDesde_Cargo
     */
    public void setFechaDesde_Cargo(java.util.Calendar fechaDesde_Cargo) {
        this.fechaDesde_Cargo = fechaDesde_Cargo;
    }


    /**
     * Gets the fechaHasta value for this TitularDTO.
     * 
     * @return fechaHasta
     */
    public java.util.Calendar getFechaHasta() {
        return fechaHasta;
    }


    /**
     * Sets the fechaHasta value for this TitularDTO.
     * 
     * @param fechaHasta
     */
    public void setFechaHasta(java.util.Calendar fechaHasta) {
        this.fechaHasta = fechaHasta;
    }


    /**
     * Gets the nombre value for this TitularDTO.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this TitularDTO.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the normaAlta value for this TitularDTO.
     * 
     * @return normaAlta
     */
    public long getNormaAlta() {
        return normaAlta;
    }


    /**
     * Sets the normaAlta value for this TitularDTO.
     * 
     * @param normaAlta
     */
    public void setNormaAlta(long normaAlta) {
        this.normaAlta = normaAlta;
    }


    /**
     * Gets the normaBaja value for this TitularDTO.
     * 
     * @return normaBaja
     */
    public java.lang.Long getNormaBaja() {
        return normaBaja;
    }


    /**
     * Sets the normaBaja value for this TitularDTO.
     * 
     * @param normaBaja
     */
    public void setNormaBaja(java.lang.Long normaBaja) {
        this.normaBaja = normaBaja;
    }


    /**
     * Gets the numeroDocumento value for this TitularDTO.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this TitularDTO.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the tipoDocumento value for this TitularDTO.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this TitularDTO.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipoTitular value for this TitularDTO.
     * 
     * @return tipoTitular
     */
    public java.lang.String getTipoTitular() {
        return tipoTitular;
    }


    /**
     * Sets the tipoTitular value for this TitularDTO.
     * 
     * @param tipoTitular
     */
    public void setTipoTitular(java.lang.String tipoTitular) {
        this.tipoTitular = tipoTitular;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TitularDTO)) return false;
        TitularDTO other = (TitularDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.apellidos==null && other.getApellidos()==null) || 
             (this.apellidos!=null &&
              this.apellidos.equals(other.getApellidos()))) &&
            ((this.cargo==null && other.getCargo()==null) || 
             (this.cargo!=null &&
              this.cargo.equals(other.getCargo()))) &&
            ((this.fechaDesde_Cargo==null && other.getFechaDesde_Cargo()==null) || 
             (this.fechaDesde_Cargo!=null &&
              this.fechaDesde_Cargo.equals(other.getFechaDesde_Cargo()))) &&
            ((this.fechaHasta==null && other.getFechaHasta()==null) || 
             (this.fechaHasta!=null &&
              this.fechaHasta.equals(other.getFechaHasta()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            this.normaAlta == other.getNormaAlta() &&
            ((this.normaBaja==null && other.getNormaBaja()==null) || 
             (this.normaBaja!=null &&
              this.normaBaja.equals(other.getNormaBaja()))) &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipoTitular==null && other.getTipoTitular()==null) || 
             (this.tipoTitular!=null &&
              this.tipoTitular.equals(other.getTipoTitular())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getApellidos() != null) {
            _hashCode += getApellidos().hashCode();
        }
        if (getCargo() != null) {
            _hashCode += getCargo().hashCode();
        }
        if (getFechaDesde_Cargo() != null) {
            _hashCode += getFechaDesde_Cargo().hashCode();
        }
        if (getFechaHasta() != null) {
            _hashCode += getFechaHasta().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        _hashCode += new Long(getNormaAlta()).hashCode();
        if (getNormaBaja() != null) {
            _hashCode += getNormaBaja().hashCode();
        }
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipoTitular() != null) {
            _hashCode += getTipoTitular().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TitularDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "titularDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellidos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "apellidos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cargo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDesde_Cargo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaDesde_Cargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaHasta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaHasta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("normaAlta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "normaAlta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("normaBaja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "normaBaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
