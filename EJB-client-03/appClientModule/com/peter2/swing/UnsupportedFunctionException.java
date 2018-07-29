/**
 * 
 */
package com.peter2.swing;

/**
 * @author Peter2_Weng
 *
 */
public class UnsupportedFunctionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6780837152970090112L;

	public UnsupportedFunctionException(Enum<?> function) {
		super("Unsupported function: "+function.toString());
	}
}
