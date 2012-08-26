/**
 * IntegracionFacadeBean_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.pcm.rufe.integracion.facade;

public interface IntegracionFacadeBean_PortType extends java.rmi.Remote {
    public pe.gob.pcm.rufe.integracion.facade.EntidadResultsDTO getEntidad(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo;
    public pe.gob.pcm.rufe.integracion.facade.FuncionesResultDTO getFunciones(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo;
    public pe.gob.pcm.rufe.integracion.facade.ListaJerarquicaResultDTO getListaGerarquica(java.lang.String arg0) throws java.rmi.RemoteException, pe.gob.pcm.rufe.integracion.facade.FaultInfo;
}
