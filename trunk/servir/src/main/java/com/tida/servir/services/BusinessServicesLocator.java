/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;

/**
 *
 * @author ale
 */
import javax.naming.NamingException;
import org.slf4j.Logger;

/**
 * BusinessServicesLocator is used to centralize all lookups of business services in JNDI. It is a global singleton
 * registered in AppModule. This version knows the formats of JNDI names assigned by OpenEJB, Glassfish, and JBoss
 * (unfortunately the EJB 3.0 specification didn't standardize them). It minimises the overhead of JNDI lookups by
 * caching the objects it looks up. If this class becomes a bottleneck, then you may need to decentralise it.
 */
public class BusinessServicesLocator extends JNDIObjectLocator implements IBusinessServicesLocator {

	protected String _ant_LaboralesServiceName;

	public BusinessServicesLocator(Logger logger) throws NamingException {
		super(logger);
		loadUpServiceNames(logger);
	}

	public IAnt_LaboralesServiceLocal getPersonServiceLocal() {
		return (IAnt_LaboralesServiceLocal) getJNDIObject(_ant_LaboralesServiceName);
	}

	public IAnt_LaboralesServiceRemote getPersonServiceRemote() {
		return (IAnt_LaboralesServiceRemote) getJNDIObject(_ant_LaboralesServiceName);
	}

	private void loadUpServiceNames(Logger logger) {

		// You wouldn't normally have to do all this work but JumpStart has to deal with many types of environment.

		EJBProviderEnum ejbProvider = EJBProviderUtil.detectEJBProvider(logger);

		if (ejbProvider == EJBProviderEnum.OPENEJB_LOCAL || ejbProvider == EJBProviderEnum.TOMCAT_OPENEJB_LOCAL) {
			_ant_LaboralesServiceName = "Ant_LaboralesServiceLocal";
		}
		else if (ejbProvider == EJBProviderEnum.OPENEJB_REMOTE) {
			_ant_LaboralesServiceName = "Ant_LaboralesServiceRemote";
		}
		else if (ejbProvider == EJBProviderEnum.GLASSFISH_LOCAL) {
			// Local interfaces in Glassfish are a bit touchy: the JNDI name used here must match the ejb-ref-name in
			// web.xml. See https://glassfish.dev.java.net/javaee5/ejb/EJB_FAQ.html
			_ant_LaboralesServiceName = "java:comp/env/Ant_LaboralesService";
		}
		else if (ejbProvider == EJBProviderEnum.GLASSFISH_REMOTE) {
			_ant_LaboralesServiceName = "com.tida.servir.services.IAnt_LaboralesServiceRemote";
		}
		else if (ejbProvider == EJBProviderEnum.JBOSS_LOCAL) {
			_ant_LaboralesServiceName = "jumpstart/Ant_LaboralesService/local";
		}
		else if (ejbProvider == EJBProviderEnum.JBOSS_REMOTE) {
			_ant_LaboralesServiceName = "jumpstart/Ant_LaboralesService/remote";
		}
		else {
			throw new IllegalStateException("Don't know how to use ejbProvider = " + ejbProvider);
		}
	}
}