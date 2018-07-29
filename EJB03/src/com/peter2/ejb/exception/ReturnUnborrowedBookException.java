/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class ReturnUnborrowedBookException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8320605715110205836L;

	public ReturnUnborrowedBookException(Integer bookEntityId) {
		super("Can not return an unborrowed book entity: #"+bookEntityId);
	}
}
