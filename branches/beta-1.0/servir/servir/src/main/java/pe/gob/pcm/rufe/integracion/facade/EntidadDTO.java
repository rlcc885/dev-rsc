/**
 * EntidadDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class EntidadDTO  implements java.io.Serializable {
    private java.lang.String clasificador;

    private java.lang.String cue;

    public EntidadDTO() {
    }

    public EntidadDTO(
           java.lang.String clasificador,
           java.lang.String cue) {
           this.clasificador = clasificador;
           this.cue = cue;
    }


    /**
     * Gets the clasificador value for this EntidadDTO.
     * 
     * @return clasificador
     */
    public java.lang.String getClasificador() {
        return clasificador;
    }


    /**
     * Sets the clasificador value for this EntidadDTO.
     * 
     * @param clasificador
     */
    public void setClasificador(java.lang.String clasificador) {
        this.clasificador = clasificador;
    }


    /**
     * Gets the cue value for this EntidadDTO.
     * 
     * @return cue
     */
    public java.lang.String getCue() {
        return cue;
    }


    /**
     * Sets the cue value for this EntidadDTO.
     * 
     * @param cue
     */
    public void setCue(java.lang.String cue) {
        this.cue = cue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EntidadDTO)) return false;
        EntidadDTO other = (EntidadDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clasificador==null && other.getClasificador()==null) || 
             (this.clasificador!=null &&
              this.clasificador.equals(other.getClasificador()))) &&
            ((this.cue==null && other.getCue()==null) || 
             (this.cue!=null &&
              this.cue.equals(other.getCue())));
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
        if (getClasificador() != null) {
            _hashCode += getClasificador().hashCode();
        }
        if (getCue() != null) {
            _hashCode += getCue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EntidadDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clasificador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clasificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cue"));
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
