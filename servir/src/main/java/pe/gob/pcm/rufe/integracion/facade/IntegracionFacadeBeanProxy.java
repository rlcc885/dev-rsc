package pe.gob.pcm.rufe.integracion.facade;

public class IntegracionFacadeBeanProxy implements pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType {
  private String _endpoint = null;
  private pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType integracionFacadeBean_PortType = null;
  
  public IntegracionFacadeBeanProxy() {
    _initIntegracionFacadeBeanProxy();
  }
  
  public IntegracionFacadeBeanProxy(String endpoint) {
    _endpoint = endpoint;
    _initIntegracionFacadeBeanProxy();
  }
  
  private void _initIntegracionFacadeBeanProxy() {
    try {
      integracionFacadeBean_PortType = (new pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_ServiceLocator()).getIntegracionFacadeBeanPort();
      if (integracionFacadeBean_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)integracionFacadeBean_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)integracionFacadeBean_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (integracionFacadeBean_PortType != null)
      ((javax.xml.rpc.Stub)integracionFacadeBean_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pe.gob.pcm.rufe.integracion.facade.IntegracionFacadeBean_PortType getIntegracionFacadeBean_PortType() {
    if (integracionFacadeBean_PortType == null)
      _initIntegracionFacadeBeanProxy();
    return integracionFacadeBean_PortType;
  }
  
  public pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO getEntidad(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo{
    if (integracionFacadeBean_PortType == null)
      _initIntegracionFacadeBeanProxy();
    return integracionFacadeBean_PortType.getEntidad(arg0);
  }
  
  public pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO getFunciones(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo{
    if (integracionFacadeBean_PortType == null)
      _initIntegracionFacadeBeanProxy();
    return integracionFacadeBean_PortType.getFunciones(arg0);
  }
  
  public pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO getListaGerarquica(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo{
    if (integracionFacadeBean_PortType == null)
      _initIntegracionFacadeBeanProxy();
    return integracionFacadeBean_PortType.getListaGerarquica(arg0);
  }
  
  
}