/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class InvalidBookException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 972043287716540910L;

	public InvalidBookException(String isbn) {
		super("Invalid Book: ISBN "+isbn);
	}
}
