/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.ActionEvent;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.view.ControlPanel;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class LowerToolBarListener extends AbstractFrameAccessibleListener {

	public LowerToolBarListener(MyFrame myFrame) {
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
		IExecutable<KeeperFunction> currentSheet = controlPanel.getCurrentSheet();

		try {
			switch(function) {
			case SUBMIT:
				// All exceptions will be handled in execute()
				currentSheet.execute();
				break;
				
			case EXPORT:
				currentSheet.export();
				break;
				
			case CLEAR:
				currentSheet.clear();
				break;
				
			default:
				throw new UnsupportedFunctionException(function);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
