/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;

/**
 *
 * @author ale
 */
public interface IBusinessServicesLocator {

	public abstract IAnt_LaboralesServiceLocal getPersonServiceLocal();

	/**
	 * Invoked after any kind of naming or remote exception. All cached naming contexts and interfaces are discarded.
	 */
	public abstract void clear();

}

