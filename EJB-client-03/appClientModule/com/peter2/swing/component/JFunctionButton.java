/**
 * 
 */
package com.peter2.swing.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * An Decorator Pattern of JButton which use a generic type as action commands and disable 
 * setActionCommand(String).
 * @author Peter2_Weng
 *
 */
public class JFunctionButton<F extends Enum<?>> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8198713494766455397L;
	
	private JButton button;
	
	public JFunctionButton() {
		// TODO Auto-generated constructor stub
		this("");
	}

	public JFunctionButton(String text) {
		// TODO Auto-generated constructor stub
		button = new JButton(text);
		super.add(button);
	}
	
	/**
	 * 
	 * @param actionCommand
	 */
	public void setActionCommand(F actionCommand) {
		button.setActionCommand(actionCommand.toString());
	}

	public void addActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		button.addActionListener(actionListener);
	}
	
	public void setText(String text) {
		button.setText(text);
	}
	
	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}
	
}
