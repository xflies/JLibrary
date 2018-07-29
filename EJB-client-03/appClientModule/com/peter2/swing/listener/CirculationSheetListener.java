/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.view.CirculationSheet;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class CirculationSheetListener extends AbstractSheetListener {

	public CirculationSheetListener(MyFrame myFrame) {
		super(myFrame);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int index = ((JComboBox) e.getSource()).getSelectedIndex();

			KeeperFunction f = null;
			try {
				f = (KeeperFunction) CirculationSheet.LIST_ITEM_FUNCTION_MAP[index];
				myFrame.getControlPanel().getCurrentSheet().setFunction(f);
			} catch (Exception e1) {
				throw new UnsupportedFunctionException(f);
			}
		}
	}

}
