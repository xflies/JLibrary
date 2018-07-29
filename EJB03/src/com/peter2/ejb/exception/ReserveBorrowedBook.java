/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class ReserveBorrowedBook extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8217918543811281529L;

	public ReserveBorrowedBook(String isbn) {
		super("The reader has borrowed this book and can not reserve it: ISBN "+isbn);
	}
}
