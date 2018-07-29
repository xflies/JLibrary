/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class ReserveCirculatedBookException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2685843249764383249L;

	public ReserveCirculatedBookException(String isbn) {
		super("This book has circulated entities: ISBN "+isbn);
	}

}
