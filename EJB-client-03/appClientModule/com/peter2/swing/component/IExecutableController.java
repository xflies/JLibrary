/**
 * 
 */
package com.peter2.swing.component;

/**
 * Controller serves IExecutable. Use a generic type that only accepts enumerated constant 
 * as action commands.
 * @author Peter2_Weng
 *
 */
public interface IExecutableController<F extends Enum<?>> {

	/**
	 * 
	 * @param function
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Object doFunction(F function, ISheetForm<F> form) throws Exception;
}
