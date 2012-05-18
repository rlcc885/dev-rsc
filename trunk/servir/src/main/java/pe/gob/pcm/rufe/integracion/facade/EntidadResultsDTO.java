/**
 * EntidadResultsDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class EntidadResultsDTO  implements java.io.Serializable {
    private pe.gob.pcm.rufe.integracion.facade.ComponenteIntegracionDTO componenteIntegracionDTO;

    private java.lang.String mensaje;

    public EntidadResultsDTO() {
    }

    public EntidadResultsDTO(
           pe.gob.pcm.rufe.integracion.facade.ComponenteIntegracionDTO componenteIntegracionDTO,
           java.lang.String mensaje) {
           this.componenteIntegracionDTO = componenteIntegracionDTO;
           this.mensaje = mensaje;
    }


    /**
     * Gets the componenteIntegracionDTO value for this EntidadResultsDTO.
     * 
     * @return componenteIntegracionDTO
     */
    public pe.gob.pcm.rufe.integracion.facade.ComponenteIntegracionDTO getComponenteIntegracionDTO() {
        return componenteIntegracionDTO;
    }


    /**
     * Sets the componenteIntegracionDTO value for this EntidadResultsDTO.
     * 
     * @param componenteIntegracionDTO
     */
    public void setComponenteIntegracionDTO(pe.gob.pcm.rufe.integracion.facade.ComponenteIntegracionDTO componenteIntegracionDTO) {
        this.componenteIntegracionDTO = componenteIntegracionDTO;
    }


    /**
     * Gets the mensaje value for this EntidadResultsDTO.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this EntidadResultsDTO.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EntidadResultsDTO)) return false;
        EntidadResultsDTO other = (EntidadResultsDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.componenteIntegracionDTO==null && other.getComponenteIntegracionDTO()==null) || 
             (this.componenteIntegracionDTO!=null &&
              this.componenteIntegracionDTO.equals(other.getComponenteIntegracionDTO()))) &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje())));
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
        if (getComponenteIntegracionDTO() != null) {
            _hashCode += getComponenteIntegracionDTO().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EntidadResultsDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadResultsDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componenteIntegracionDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "componenteIntegracionDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "componenteIntegracionDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensaje"));
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
