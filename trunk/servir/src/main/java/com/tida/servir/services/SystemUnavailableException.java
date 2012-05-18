/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;
/**
 *
 * @author ale
 */
@SuppressWarnings("serial")
public class SystemUnavailableException extends SystemException {
	String _symptom;

	/**
	 * Throw this exception when the system becomes unavailable eg. due to database connection failure.
	 */
	public SystemUnavailableException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Throw this exception when the system becomes unavailable eg. due to database connection failure.
	 */
	public SystemUnavailableException(String symptom, Throwable throwable) {
		super(throwable);
		_symptom = symptom;
	}

	@Override
	public String getMessage() {
		String msg = "SystemUnavailableException" +  _symptom;
		return msg;
	}

	public String getSymptom() {
		return _symptom;
	}

}

