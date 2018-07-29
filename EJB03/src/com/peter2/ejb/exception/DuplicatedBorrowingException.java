package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class DuplicatedBorrowingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3269282044005559122L;
	
	public DuplicatedBorrowingException(Integer bookEntityId) {
		super("This book entity has been borrowed: #"+bookEntityId);
	}

}
