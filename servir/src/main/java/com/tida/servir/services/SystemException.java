/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tida.servir.services;

/**
 * A system exception is an exception that the application cannot recover from. They fall into two categories: (1)
 * system has become temporarily unavailable eg. because the database has stopped; and (2) system has failed with an
 * irrecoverable logic error. Typically, the system should display a special "system unavailable" page to the user and
 * notify operations immediately.
 *
 */
/**
 *
 * @author ale
 */
@SuppressWarnings("serial")
public abstract class SystemException extends RuntimeException {

	public SystemException(Throwable throwable) {
		super(throwable);
	}

	public SystemException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
