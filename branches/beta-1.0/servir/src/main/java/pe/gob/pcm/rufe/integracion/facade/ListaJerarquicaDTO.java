/**
 * ListaJerarquicaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class ListaJerarquicaDTO  implements java.io.Serializable {
    private java.lang.String cue;

    private pe.gob.pcm.rufe.integracion.facade.EntidadDTO entidadPrincipal;

    private java.lang.String[] entidadesPrimerNivel;

    private java.lang.String[] organosDependientes;

    public ListaJerarquicaDTO() {
    }

    public ListaJerarquicaDTO(
           java.lang.String cue,
           pe.gob.pcm.rufe.integracion.facade.EntidadDTO entidadPrincipal,
           java.lang.String[] entidadesPrimerNivel,
           java.lang.String[] organosDependientes) {
           this.cue = cue;
           this.entidadPrincipal = entidadPrincipal;
           this.entidadesPrimerNivel = entidadesPrimerNivel;
           this.organosDependientes = organosDependientes;
    }


    /**
     * Gets the cue value for this ListaJerarquicaDTO.
     * 
     * @return cue
     */
    public java.lang.String getCue() {
        return cue;
    }


    /**
     * Sets the cue value for this ListaJerarquicaDTO.
     * 
     * @param cue
     */
    public void setCue(java.lang.String cue) {
        this.cue = cue;
    }


    /**
     * Gets the entidadPrincipal value for this ListaJerarquicaDTO.
     * 
     * @return entidadPrincipal
     */
    public pe.gob.pcm.rufe.integracion.facade.EntidadDTO getEntidadPrincipal() {
        return entidadPrincipal;
    }


    /**
     * Sets the entidadPrincipal value for this ListaJerarquicaDTO.
     * 
     * @param entidadPrincipal
     */
    public void setEntidadPrincipal(pe.gob.pcm.rufe.integracion.facade.EntidadDTO entidadPrincipal) {
        this.entidadPrincipal = entidadPrincipal;
    }


    /**
     * Gets the entidadesPrimerNivel value for this ListaJerarquicaDTO.
     * 
     * @return entidadesPrimerNivel
     */
    public java.lang.String[] getEntidadesPrimerNivel() {
        return entidadesPrimerNivel;
    }


    /**
     * Sets the entidadesPrimerNivel value for this ListaJerarquicaDTO.
     * 
     * @param entidadesPrimerNivel
     */
    public void setEntidadesPrimerNivel(java.lang.String[] entidadesPrimerNivel) {
        this.entidadesPrimerNivel = entidadesPrimerNivel;
    }


    /**
     * Gets the organosDependientes value for this ListaJerarquicaDTO.
     * 
     * @return organosDependientes
     */
    public java.lang.String[] getOrganosDependientes() {
        return organosDependientes;
    }


    /**
     * Sets the organosDependientes value for this ListaJerarquicaDTO.
     * 
     * @param organosDependientes
     */
    public void setOrganosDependientes(java.lang.String[] organosDependientes) {
        this.organosDependientes = organosDependientes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaJerarquicaDTO)) return false;
        ListaJerarquicaDTO other = (ListaJerarquicaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cue==null && other.getCue()==null) || 
             (this.cue!=null &&
              this.cue.equals(other.getCue()))) &&
            ((this.entidadPrincipal==null && other.getEntidadPrincipal()==null) || 
             (this.entidadPrincipal!=null &&
              this.entidadPrincipal.equals(other.getEntidadPrincipal()))) &&
            ((this.entidadesPrimerNivel==null && other.getEntidadesPrimerNivel()==null) || 
             (this.entidadesPrimerNivel!=null &&
              java.util.Arrays.equals(this.entidadesPrimerNivel, other.getEntidadesPrimerNivel()))) &&
            ((this.organosDependientes==null && other.getOrganosDependientes()==null) || 
             (this.organosDependientes!=null &&
              java.util.Arrays.equals(this.organosDependientes, other.getOrganosDependientes())));
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
        if (getCue() != null) {
            _hashCode += getCue().hashCode();
        }
        if (getEntidadPrincipal() != null) {
            _hashCode += getEntidadPrincipal().hashCode();
        }
        if (getEntidadesPrimerNivel() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntidadesPrimerNivel());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntidadesPrimerNivel(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOrganosDependientes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrganosDependientes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOrganosDependientes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListaJerarquicaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidadPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidadPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidadesPrimerNivel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidadesPrimerNivel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "cue"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organosDependientes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organosDependientes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "clasificador"));
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
