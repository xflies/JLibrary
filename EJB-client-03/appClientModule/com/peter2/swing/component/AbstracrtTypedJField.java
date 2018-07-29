/**
 * 
 */
package com.peter2.swing.component;

import javax.swing.JTextField;

/**
 * A packed version of JTextField that only support a specified type in the text field. 
 * The visibility of inherited methods like getText() and setText(String text) could not 
 * be reduced, I choose to override them with dummy method that always throw  IllegalArgumentException.
 * @author Peter2_Weng
 *
 */
public abstract class AbstracrtTypedJField<T> extends JTextField implements ITypedComponent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5704893500873760183L;

	
	@Override
	public abstract T getValue();
	
	@Override
	public void setValue(T value) {
		// TODO Auto-generated method stub
		setText(value.toString());
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		setText(null);
	}
}
