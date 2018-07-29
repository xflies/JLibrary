/**
 * 
 */
package com.peter2.swing.component;

/**
 * @author Peter2_Weng
 *
 */
public interface ISheetForm <F extends Enum<?>> {

	void validate(F function) throws Exception;
}
