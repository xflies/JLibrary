/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.ActionEvent;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.view.ControlPanel;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class UpperToolBarListener extends AbstractFrameAccessibleListener {

	public UpperToolBarListener(MyFrame myFrame) {
		super(myFrame);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		KeeperFunction function = Enum.valueOf(KeeperFunction.class, e.getActionCommand());
		ControlPanel controlPanel = super.myFrame.getControlPanel();

		switch(function) {
		case CONNECT:
			myFrame.connect();
			break;
			
		case CIRCULATION:
		case BOOKADMIN:
			controlPanel.displaySheet(function);
			break;
			
		default:
			throw new UnsupportedFunctionException(function);
		}
	}

}
