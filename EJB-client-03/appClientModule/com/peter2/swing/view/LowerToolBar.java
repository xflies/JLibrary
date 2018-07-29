/**
 * 
 */
package com.peter2.swing.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.JFunctionButton;

/**
 * @author Peter2_Weng
 *
 */
public class LowerToolBar extends JPanel implements IResourceBundled {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751387420866275425L;

	/**
	 * Definition of Resource keys
	 */
	public static final String SUBMIT_BUTTON_TEXT = "submit";
	public static final String EXPORT_BUTTON_TEXT = "export";
	public static final String CLEAR_BUTTON_TEXT = "clear";

	/**
	 * Resources
	 */
	private String submitButtonText;
	private String exportButtonText;
	private String clearButtonText;
	
	/**
	 * Panel components
	 */
	private JFunctionButton<KeeperFunction> submitButton;
	private JFunctionButton<KeeperFunction> exportButton;
	private JFunctionButton<KeeperFunction> clearButton;

	LowerToolBar(ActionListener actionListener, ResourceBundle rb) {
		initResource(rb);
		
		setLayout(new FlowLayout());

		submitButton = new JFunctionButton<KeeperFunction>(submitButtonText);
		submitButton.setActionCommand(KeeperFunction.SUBMIT);
		submitButton.addActionListener(actionListener);
		add(submitButton);
		
		exportButton = new JFunctionButton<KeeperFunction>(exportButtonText);
		exportButton.setActionCommand(KeeperFunction.EXPORT);
		exportButton.addActionListener(actionListener);
		add(exportButton);
		
		clearButton = new JFunctionButton<KeeperFunction>(clearButtonText);
		clearButton.setActionCommand(KeeperFunction.CLEAR);
		clearButton.addActionListener(actionListener);
		add(clearButton);
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub
		submitButtonText = rb.getString(SUBMIT_BUTTON_TEXT);
		exportButtonText = rb.getString(EXPORT_BUTTON_TEXT);
		clearButtonText = rb.getString(CLEAR_BUTTON_TEXT);
	}
}