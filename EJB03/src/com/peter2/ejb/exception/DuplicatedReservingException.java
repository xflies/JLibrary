/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class DuplicatedReservingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9116155545866303416L;

	public DuplicatedReservingException(String isbn) {
		super("The reader has reserved this book: ISBN "+isbn);
	}

}
