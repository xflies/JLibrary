/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class BlankBookNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610231513627897085L;

	public BlankBookNameException(String isbn) {
		// TODO Auto-generated constructor stub
		super("Book name must not be blank: "+isbn);
	}

}
