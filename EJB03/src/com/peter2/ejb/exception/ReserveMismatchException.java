/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class ReserveMismatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8500629651881120270L;

	public ReserveMismatchException(String isbn) {
		super("The book is not ready to take or not reserved by this reader: ISBN "+isbn);
	}
}
