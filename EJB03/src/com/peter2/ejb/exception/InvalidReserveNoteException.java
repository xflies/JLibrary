/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class InvalidReserveNoteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208400573142563213L;

	public InvalidReserveNoteException(Integer reserveNoteId) {
		// TODO Auto-generated constructor stub
		super("Invalid reserve note ID: "+reserveNoteId);
	}

}
