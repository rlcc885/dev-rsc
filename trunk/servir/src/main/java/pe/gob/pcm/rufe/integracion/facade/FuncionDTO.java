/**
 * FuncionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class FuncionDTO  implements java.io.Serializable {
    private pe.gob.pcm.rufe.integracion.facade.AtribucionesDTO atribuciones;

    private pe.gob.pcm.rufe.integracion.facade.CompetenciaDTO competencia;

    private java.lang.String funcion;

    public FuncionDTO() {
    }

    public FuncionDTO(
           pe.gob.pcm.rufe.integracion.facade.AtribucionesDTO atribuciones,
           pe.gob.pcm.rufe.integracion.facade.CompetenciaDTO competencia,
           java.lang.String funcion) {
           this.atribuciones = atribuciones;
           this.competencia = competencia;
           this.funcion = funcion;
    }


    /**
     * Gets the atribuciones value for this FuncionDTO.
     * 
     * @return atribuciones
     */
    public pe.gob.pcm.rufe.integracion.facade.AtribucionesDTO getAtribuciones() {
        return atribuciones;
    }


    /**
     * Sets the atribuciones value for this FuncionDTO.
     * 
     * @param atribuciones
     */
    public void setAtribuciones(pe.gob.pcm.rufe.integracion.facade.AtribucionesDTO atribuciones) {
        this.atribuciones = atribuciones;
    }


    /**
     * Gets the competencia value for this FuncionDTO.
     * 
     * @return competencia
     */
    public pe.gob.pcm.rufe.integracion.facade.CompetenciaDTO getCompetencia() {
        return competencia;
    }


    /**
     * Sets the competencia value for this FuncionDTO.
     * 
     * @param competencia
     */
    public void setCompetencia(pe.gob.pcm.rufe.integracion.facade.CompetenciaDTO competencia) {
        this.competencia = competencia;
    }


    /**
     * Gets the funcion value for this FuncionDTO.
     * 
     * @return funcion
     */
    public java.lang.String getFuncion() {
        return funcion;
    }


    /**
     * Sets the funcion value for this FuncionDTO.
     * 
     * @param funcion
     */
    public void setFuncion(java.lang.String funcion) {
        this.funcion = funcion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FuncionDTO)) return false;
        FuncionDTO other = (FuncionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.atribuciones==null && other.getAtribuciones()==null) || 
             (this.atribuciones!=null &&
              this.atribuciones.equals(other.getAtribuciones()))) &&
            ((this.competencia==null && other.getCompetencia()==null) || 
             (this.competencia!=null &&
              this.competencia.equals(other.getCompetencia()))) &&
            ((this.funcion==null && other.getFuncion()==null) || 
             (this.funcion!=null &&
              this.funcion.equals(other.getFuncion())));
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
        if (getAtribuciones() != null) {
            _hashCode += getAtribuciones().hashCode();
        }
        if (getCompetencia() != null) {
            _hashCode += getCompetencia().hashCode();
        }
        if (getFuncion() != null) {
            _hashCode += getFuncion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FuncionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atribuciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atribuciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "atribucionesDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("competencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "competenciaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "funcion"));
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
