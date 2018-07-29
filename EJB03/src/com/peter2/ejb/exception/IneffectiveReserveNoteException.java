/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class IneffectiveReserveNoteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2553507741671489495L;

	public IneffectiveReserveNoteException(Integer reserveNoteId) {
		// TODO Auto-generated constructor stub
		super("Status of the reserve note is not waiting or on-desked: "+reserveNoteId);
	}

}
