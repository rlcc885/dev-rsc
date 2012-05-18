/**
 * CompetenciaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class CompetenciaDTO  implements java.io.Serializable {
    private java.lang.String materia;

    private java.lang.String nivelGobierno;

    private java.lang.String submateria;

    private java.lang.String territorio;

    public CompetenciaDTO() {
    }

    public CompetenciaDTO(
           java.lang.String materia,
           java.lang.String nivelGobierno,
           java.lang.String submateria,
           java.lang.String territorio) {
           this.materia = materia;
           this.nivelGobierno = nivelGobierno;
           this.submateria = submateria;
           this.territorio = territorio;
    }


    /**
     * Gets the materia value for this CompetenciaDTO.
     * 
     * @return materia
     */
    public java.lang.String getMateria() {
        return materia;
    }


    /**
     * Sets the materia value for this CompetenciaDTO.
     * 
     * @param materia
     */
    public void setMateria(java.lang.String materia) {
        this.materia = materia;
    }


    /**
     * Gets the nivelGobierno value for this CompetenciaDTO.
     * 
     * @return nivelGobierno
     */
    public java.lang.String getNivelGobierno() {
        return nivelGobierno;
    }


    /**
     * Sets the nivelGobierno value for this CompetenciaDTO.
     * 
     * @param nivelGobierno
     */
    public void setNivelGobierno(java.lang.String nivelGobierno) {
        this.nivelGobierno = nivelGobierno;
    }


    /**
     * Gets the submateria value for this CompetenciaDTO.
     * 
     * @return submateria
     */
    public java.lang.String getSubmateria() {
        return submateria;
    }


    /**
     * Sets the submateria value for this CompetenciaDTO.
     * 
     * @param submateria
     */
    public void setSubmateria(java.lang.String submateria) {
        this.submateria = submateria;
    }


    /**
     * Gets the territorio value for this CompetenciaDTO.
     * 
     * @return territorio
     */
    public java.lang.String getTerritorio() {
        return territorio;
    }


    /**
     * Sets the territorio value for this CompetenciaDTO.
     * 
     * @param territorio
     */
    public void setTerritorio(java.lang.String territorio) {
        this.territorio = territorio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompetenciaDTO)) return false;
        CompetenciaDTO other = (CompetenciaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.materia==null && other.getMateria()==null) || 
             (this.materia!=null &&
              this.materia.equals(other.getMateria()))) &&
            ((this.nivelGobierno==null && other.getNivelGobierno()==null) || 
             (this.nivelGobierno!=null &&
              this.nivelGobierno.equals(other.getNivelGobierno()))) &&
            ((this.submateria==null && other.getSubmateria()==null) || 
             (this.submateria!=null &&
              this.submateria.equals(other.getSubmateria()))) &&
            ((this.territorio==null && other.getTerritorio()==null) || 
             (this.territorio!=null &&
              this.territorio.equals(other.getTerritorio())));
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
        if (getMateria() != null) {
            _hashCode += getMateria().hashCode();
        }
        if (getNivelGobierno() != null) {
            _hashCode += getNivelGobierno().hashCode();
        }
        if (getSubmateria() != null) {
            _hashCode += getSubmateria().hashCode();
        }
        if (getTerritorio() != null) {
            _hashCode += getTerritorio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompetenciaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "competenciaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nivelGobierno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nivelGobierno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submateria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submateria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("territorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "territorio"));
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
