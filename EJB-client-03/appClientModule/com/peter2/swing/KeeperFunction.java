/**
 * 
 */
package com.peter2.swing;

/**
 * @author Peter2_Weng
 *
 */
public enum KeeperFunction {
	/**
	 * Change sheet event
	 */
	CIRCULATION, BOOKADMIN, 
	
	/**
	 * Upper ToolBar event
	 */
	CONNECT,

	/**
	 * Lower ToolBar event
	 */
	SUBMIT, EXPORT, CLEAR, 

	/**
	 * Circulation sheet event
	 */
	BORROW, RETURN, TAKE, 
	
	/**
	 * BookAdmin sheet event
	 */
	QUERY_BY_ID, QUERY_ISBN, ADD_BOOK, MAINTAIN

}
