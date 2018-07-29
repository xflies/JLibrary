/**
 * 
 */
package com.peter2.swing.component;

import com.peter2.Arrayable;

/**
 * Component with a data input form and a display table supporting various actions. Use a generic type that 
 * only accepts enumerated constant as action commands.
 * @author Peter2_Weng
 *
 */
public interface IExecutable<F extends Enum<?>> {
	/**
	 * Encapsulate submit() in the new architecture. This method must handle all exceptions and 
	 * do not throw any.
	 */
	public void execute() throws Exception;
	
	/**
	 * Submit data of the sheet to the service. Validate the form data before returning.
	 * @return a generic meta object contains input data of the form or null if failed to validate the form
	 * @throws Exception 
	 */
	public ISheetForm<F> submit() throws Exception;
	
	/**
	 * Export data of the table as an Excel sheet
	 */
	public Object export() throws Exception;
	
	/**
	 * Clear the sheet
	 */
	public void clear() throws Exception;
	
	/**
	 * Set ONE data row into the display table
	 * @param dataRows: Display data
	 */
	public void displayData(Arrayable dataRow) throws IllegalArgumentException;
		
	/**
	 * Set current function of the sheet
	 * @param function
	 */
	public void setFunction(F function);
}
