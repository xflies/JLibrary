/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class InvalidBookEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1874599783989943537L;

	public InvalidBookEntityException(Integer bookEntityId) {
		super("Invalid book entity: #"+bookEntityId);
	}
}
