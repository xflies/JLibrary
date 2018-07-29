/**
 * 
 */
package com.peter2.swing.view;

import java.net.ConnectException;

import javax.swing.JPanel;

import com.peter2.Arrayable;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.Msg;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.component.IExecutableController;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.listener.AbstractSheetListener;

/**
 * An abstract executable sheet implements execute(). It extends JPanel but does not implements 
 * any parts of JPanel.
 * @author Peter2_Weng
 *
 */
public abstract class AbstractExecutableSheet extends JPanel implements IExecutable<KeeperFunction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6636982178641422712L;

	/**
	 * Definition of resource keys
	 */

	/**
	 * Resources
	 */
	MyFrame myFrame;

	protected IExecutableController<KeeperFunction> controller;
	protected AbstractSheetListener listener;
	protected KeeperFunction currentFunction;
	
	public AbstractExecutableSheet(MyFrame myFrame) {
		this.myFrame = myFrame;
	}
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		ISheetForm<KeeperFunction> form = null;

		try {
			form = submit();
		} catch (IllegalArgumentException e) {
			System.err.println("Submition interrupted.");
			e.printStackTrace();
			return;
		}
		
		Arrayable r = null;
		try {
			r = (Arrayable) getController().doFunction(currentFunction, form);
			if (r==null) {
				return;
			}
			displayData(r);
		} catch (ConnectException e) {
			// Thrown by AbstractSheetController.checkConnection() in doFunction().
			// MyFrame.isConnectionAlive() is called and doing handles so there is nothing to do here. 
			System.err.println(e.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Msg.error(e.toString());
		}
	}

	@Override
	public void displayData(Arrayable dataRow) throws IllegalArgumentException {
		if (dataRow==null) {
			throw new IllegalArgumentException("Can not use null data row");
		}		
	}
	
	/**
	 * 
	 * @return myFrame
	 */
	public MyFrame getMyFrame() {
		return myFrame;
	}

	/**
	 * @return the controller
	 */
	public IExecutableController<KeeperFunction> getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(IExecutableController<KeeperFunction> controller) {
		this.controller = controller;
	}

	/**
	 * @return the currentFunction
	 */
	public KeeperFunction getCurrentFunction() {
		return currentFunction;
	}

	/**
	 * @param currentFunction the currentFunction to set
	 */
	public void setCurrentFunction(KeeperFunction currentFunction) {
		this.currentFunction = currentFunction;
	}

}
