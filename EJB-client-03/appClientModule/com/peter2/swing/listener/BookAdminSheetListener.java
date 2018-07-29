/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.view.BookAdminSheet;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class BookAdminSheetListener extends AbstractSheetListener implements MouseListener {
	
	public BookAdminSheetListener(MyFrame myFrame) {
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
				f = (KeeperFunction) BookAdminSheet.LIST_ITEM_FUNCTION_MAP[index];
				myFrame.getControlPanel().getCurrentSheet().setFunction(f);
			} catch (Exception e1) {
				throw new UnsupportedFunctionException(f);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		((BookAdminSheet) myFrame.getControlPanel().getCurrentSheet()).fetchTableDate(event);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
