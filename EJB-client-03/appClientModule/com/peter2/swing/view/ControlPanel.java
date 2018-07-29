/**
 * 
 */
package com.peter2.swing.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutable;

/**
 * This panel only contains control sheets and displays one sheet at one time.
 * It does not support any Controller.
 * 
 * @author Peter2_Weng
 * 
 */
public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385710329036941244L;
	
	private Map<KeeperFunction, IExecutable<KeeperFunction>> sheets;

	private IExecutable<KeeperFunction> currentSheet;
	
	private CardLayout cardLayout;

	public ControlPanel(MyFrame myFrame) {
		cardLayout = new CardLayout();
		setLayout(cardLayout);

		sheets = new HashMap<KeeperFunction, IExecutable<KeeperFunction>>();
		putSheet(new CirculationSheet(myFrame), KeeperFunction.CIRCULATION);
		putSheet(new BookAdminSheet(myFrame), KeeperFunction.BOOKADMIN);

		currentSheet = getSheet(KeeperFunction.CIRCULATION);
	}
	
	/**
	 * @return the currentSheet
	 */
	public IExecutable<KeeperFunction> getCurrentSheet() {
		return currentSheet;
	}

	/**
	 * @param currentSheet the currentSheet to set
	 */
	public void setCurrentSheet(IExecutable<KeeperFunction> currentSheet) {
		this.currentSheet = currentSheet;
	}

	public void displaySheet(KeeperFunction sheetKey) {
		cardLayout.show(this, sheetKey.toString());
		currentSheet = getSheet(sheetKey);
	}		

	public IExecutable<KeeperFunction> getSheet(KeeperFunction sheetKey) {
		return sheets.get(sheetKey);
	}
	public void putSheet(IExecutable<KeeperFunction> sheet, KeeperFunction sheetKey) {
		sheets.put(sheetKey, sheet);
		add((Component) sheet, sheetKey.toString());
	}
}
