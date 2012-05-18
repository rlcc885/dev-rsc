/**
 * IntegracionFacadeBeanBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public class IntegracionFacadeBeanBindingStub extends org.apache.axis.client.Stub implements pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getEntidad");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadResultsDTO"));
        oper.setReturnClass(pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "RufeException"),
                      "pe.gob.pcm.rufe.integracion.facade.FaultInfo",
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "faultInfo"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFunciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionesResultDTO"));
        oper.setReturnClass(pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "RufeException"),
                      "pe.gob.pcm.rufe.integracion.facade.FaultInfo",
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "faultInfo"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getListaGerarquica");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaResultDTO"));
        oper.setReturnClass(pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "RufeException"),
                      "pe.gob.pcm.rufe.integracion.facade.FaultInfo",
                      new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "faultInfo"), 
                      true
                     ));
        _operations[2] = oper;

    }

    public IntegracionFacadeBeanBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IntegracionFacadeBeanBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IntegracionFacadeBeanBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "atribucionesDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.AtribucionesDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "competenciaDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.CompetenciaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "componenteIntegracionDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.ComponenteIntegracionDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.EntidadDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadesPrimerNivelDTO");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "cue");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "entidadResultsDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "faultInfo");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.FaultInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.FuncionDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionesDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.FuncionDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionDTO");
            qName2 = new javax.xml.namespace.QName("", "funcion");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "funcionesResultDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "listaJerarquicaResultDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "materiaDTO");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "materia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "organismoReguladorDTO");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "organismoRegulador");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "organosDependientesDTO");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "clasificador");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "representanteDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.RepresentanteDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "representantesDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.RepresentanteDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "representanteDTO");
            qName2 = new javax.xml.namespace.QName("", "representante");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "titularDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.TitularDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "titularEntidadDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.TitularDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "titularDTO");
            qName2 = new javax.xml.namespace.QName("", "titular");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "ubicacionGeograficaDTO");
            cachedSerQNames.add(qName);
            cls = pe.gob.pcm.rufe.integracion.facade.UbicacionGeograficaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO getEntidad(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "getEntidad"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.pcm.rufe.integracion.facade.FaultInfo) {
              throw (pe.gob.pcm.rufe.integracion.facade.FaultInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO getFunciones(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "getFunciones"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.pcm.rufe.integracion.facade.FaultInfo) {
              throw (pe.gob.pcm.rufe.integracion.facade.FaultInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO getListaGerarquica(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://facade.integracion.rufe.pcm.gob.pe/", "getListaGerarquica"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO) org.apache.axis.utils.JavaUtils.convert(_resp, pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pe.gob.pcm.rufe.integracion.facade.FaultInfo) {
              throw (pe.gob.pcm.rufe.integracion.facade.FaultInfo) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
