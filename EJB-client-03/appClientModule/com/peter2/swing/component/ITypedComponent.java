/**
 * 
 */
package com.peter2.swing.component;

/**
 * Provide an interface for derivative class inherited JComponent with a specified type of 
 * its text or title. For e.g., JDigitField is a packed version of JTextField that only support 
 * integer.
 * @author Peter2_Weng
 *
 */
public interface ITypedComponent<T> {

	public T getValue();
	public void setValue(T value);
	public void clear();
}
