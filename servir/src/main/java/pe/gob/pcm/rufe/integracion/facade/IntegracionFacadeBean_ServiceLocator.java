/**
 * IntegracionFacadeBean_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class IntegracionFacadeBean_ServiceLocator extends org.apache.axis.client.Service implements pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_Service {

    public IntegracionFacadeBean_ServiceLocator() {
    }


    public IntegracionFacadeBean_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IntegracionFacadeBean_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IntegracionFacadeBeanPort
    private java.lang.String IntegracionFacadeBeanPort_address = "http://localhost:8080/rufe-ear-rufe-negocio/IntegracionFacadeBean";

    public java.lang.String getIntegracionFacadeBeanPortAddress() {
        return IntegracionFacadeBeanPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IntegracionFacadeBeanPortWSDDServiceName = "IntegracionFacadeBeanPort";

    public java.lang.String getIntegracionFacadeBeanPortWSDDServiceName() {
        return IntegracionFacadeBeanPortWSDDServiceName;
    }

    public void setIntegracionFacadeBeanPortWSDDServiceName(java.lang.String name) {
        IntegracionFacadeBeanPortWSDDServiceName = name;
    }

    public pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType getIntegracionFacadeBeanPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IntegracionFacadeBeanPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIntegracionFacadeBeanPort(endpoint);
    }

    public pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType getIntegracionFacadeBeanPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBeanBindingStub _stub = new pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBeanBindingStub(portAddress, this);
            _stub.setPortName(getIntegracionFacadeBeanPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIntegracionFacadeBeanPortEndpointAddress(java.lang.String address) {
        IntegracionFacadeBeanPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBeanBindingStub _stub = new pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBeanBindingStub(new java.net.URL(IntegracionFacadeBeanPort_address), this);
                _stub.setPortName(getIntegracionFacadeBeanPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IntegracionFacadeBeanPort".equals(inputPortName)) {
            return getIntegracionFacadeBeanPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "IntegracionFacadeBean");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "IntegracionFacadeBeanPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IntegracionFacadeBeanPort".equals(portName)) {
            setIntegracionFacadeBeanPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
