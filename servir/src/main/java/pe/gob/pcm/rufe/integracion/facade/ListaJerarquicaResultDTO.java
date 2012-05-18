/**
 * ListaJerarquicaResultDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class ListaJerarquicaResultDTO  implements java.io.Serializable {
    private pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaDTO listaJerarquica;

    private java.lang.String mensaje;

    public ListaJerarquicaResultDTO() {
    }

    public ListaJerarquicaResultDTO(
           pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaDTO listaJerarquica,
           java.lang.String mensaje) {
           this.listaJerarquica = listaJerarquica;
           this.mensaje = mensaje;
    }


    /**
     * Gets the listaJerarquica value for this ListaJerarquicaResultDTO.
     * 
     * @return listaJerarquica
     */
    public pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaDTO getListaJerarquica() {
        return listaJerarquica;
    }


    /**
     * Sets the listaJerarquica value for this ListaJerarquicaResultDTO.
     * 
     * @param listaJerarquica
     */
    public void setListaJerarquica(pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaDTO listaJerarquica) {
        this.listaJerarquica = listaJerarquica;
    }


    /**
     * Gets the mensaje value for this ListaJerarquicaResultDTO.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this ListaJerarquicaResultDTO.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaJerarquicaResultDTO)) return false;
        ListaJerarquicaResultDTO other = (ListaJerarquicaResultDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listaJerarquica==null && other.getListaJerarquica()==null) || 
             (this.listaJerarquica!=null &&
              this.listaJerarquica.equals(other.getListaJerarquica()))) &&
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
        if (getListaJerarquica() != null) {
            _hashCode += getListaJerarquica().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListaJerarquicaResultDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaResultDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaJerarquica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listaJerarquica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaDTO"));
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
