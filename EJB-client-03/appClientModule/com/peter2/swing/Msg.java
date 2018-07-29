/**
 * 
 */
package com.peter2.swing;

import javax.swing.JOptionPane;

/**
 * @author Peter2_Weng
 *
 */
public final class Msg {
	private static final String WARNING = "Warning";
	private static final String ERROR = "Error";
	private static final String INFORMATION = "Information";
	private static final String CONFIRM = "Confirm";
	private static final String QUESTION = "Question";

	public static void warn(String message) {
		JOptionPane.showMessageDialog(null, message, WARNING, JOptionPane.WARNING_MESSAGE);
	}
	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message, ERROR, JOptionPane.ERROR_MESSAGE);
	}
	public static void info(String message) {
		JOptionPane.showMessageDialog(null, message, INFORMATION, JOptionPane.INFORMATION_MESSAGE);
	}
	public static boolean yesNo(String message) {
		int r = JOptionPane.showConfirmDialog(null, 
				message, 
				QUESTION, 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE);
		switch (r) {
		case JOptionPane.YES_OPTION:
			return true;
			
		case JOptionPane.NO_OPTION:
			return false;
			
		default:
			throw new RuntimeException(r+"");
		}
	}
	public static boolean okCancel(String message) {
		int r = JOptionPane.showConfirmDialog(null, 
			    message, 
			    CONFIRM, 
			    JOptionPane.OK_CANCEL_OPTION, 
			    JOptionPane.QUESTION_MESSAGE);
		switch (r) {
		case JOptionPane.OK_OPTION:
			return true;
			
		case JOptionPane.CANCEL_OPTION:
			return false;
			
		default:
			throw new RuntimeException(r+"");
		}
	}
}
