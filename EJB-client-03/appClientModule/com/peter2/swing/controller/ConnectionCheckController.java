/**
 * 
 */
package com.peter2.swing.controller;

import java.net.ConnectException;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutableController;
import com.peter2.swing.component.ISheetForm;

/**
 * Use Decorator Pattern to add the ability to check whether the connection is alive or not.
 * @author Peter2_Weng
 *
 */
public class ConnectionCheckController implements IExecutableController<KeeperFunction> {
	
	AbstractSheetController controller;
	
	public ConnectionCheckController(AbstractSheetController controller) {
		this.controller = controller;
	}

	@Override
	public Object doFunction(KeeperFunction function, ISheetForm<KeeperFunction> form)
			throws Exception {
		// TODO Auto-generated method stub
		checkConnection();
		return controller.doFunction(function, form);
	}

	/**
	 * Check connection only and return null. You need to implements actually functions in the subclass  
	 * and remember call super.doFunction() first in every subclass's doFunction().
	 * @param function - no use
	 * @param form - no use
	 * @return null always.
	 * @throws ConnectException thrown by checkConnection()
	 */
	public void checkConnection() throws ConnectException {
		if (! controller.getMyFrame().isConnectionAlive()) {
			throw new ConnectException("Connection refused.");
		}
	}
}
