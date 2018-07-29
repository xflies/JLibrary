/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.view.ControlPanel;
import com.peter2.swing.view.LeftToolBar;

/**
 * @author Peter2_Weng
 *
 */
public class LeftToolBarListener implements MouseListener {
	
	private ControlPanel controlPanel;
	
	public LeftToolBarListener(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	/**
	 * @return the controlPanel
	 */
	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	/**
	 * @param controlPanel the controlPanel to set
	 */
	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JList list = (JList) e.getSource();
		int index = list.locationToIndex(e.getPoint());
		
		try {
			KeeperFunction function = (KeeperFunction) LeftToolBar.LIST_ITEM_FUNCTION_MAP[index];
			KeeperFunction sheetKey = null;
			switch(index) {
			case 0:	// Circulation
			case 4:	// Book Admin
				// Switch sheet only because (function == null)
				list.clearSelection();
				e.consume();
			
			default: // Borrow, Return, Take on-desk, Query by ID, Query ISBN, Add Book
				// Switch sheet and function
				/**
				 * ************************************************************
				 * ************************************************************
				 */
				// Hard code
				// Specify sheet wanted to switch to
				sheetKey = index < 4 ? KeeperFunction.CIRCULATION : KeeperFunction.BOOKADMIN;
				/*
				 * ************************************************************
				 * ************************************************************
				 */
				controlPanel.displaySheet(sheetKey);
				break;			
			}
			if (function != null) {
				controlPanel.getCurrentSheet().setFunction(function);
			}
			controlPanel.displaySheet(sheetKey);
		} catch (ArrayIndexOutOfBoundsException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
