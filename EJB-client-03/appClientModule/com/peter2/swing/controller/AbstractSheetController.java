/**
 * 
 */
package com.peter2.swing.controller;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutableController;
import com.peter2.swing.view.MyFrame;

/**
 * An abstract controller that have the ability to check connection status
 * @author Peter2_Weng
 *
 */
public abstract class AbstractSheetController implements IExecutableController<KeeperFunction> {

	private MyFrame myFrame;
	
	public AbstractSheetController(MyFrame myFrame) {
		// TODO Auto-generated constructor stub
		this.myFrame = myFrame;
	}

	/**
	 * @return the myFrame
	 */
	public MyFrame getMyFrame() {
		return myFrame;
	}

	/**
	 * @param myFrame the myFrame to set
	 */
	public void setMyFrame(MyFrame myFrame) {
		this.myFrame = myFrame;
	}
	

	
}
