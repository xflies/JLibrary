/**
 * 
 */
package com.peter2.ejb.exception;

/**
 * @author Peter2_Weng
 *
 */
public class LoginFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755594059220264778L;

	public LoginFailedException() {
		super("Login failed. Please check your reader name or password.");
	}
}
