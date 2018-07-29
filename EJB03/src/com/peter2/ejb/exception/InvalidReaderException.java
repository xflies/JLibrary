/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class InvalidReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1223729559897363319L;
	
	public InvalidReaderException(Integer readerId) {
		super("Invalid reader: #"+readerId);
	}
}
