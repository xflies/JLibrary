/**
 * 
 */
package com.peter2.swing.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.peter2.swing.ConnectionStatus;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.JFunctionButton;

/**
 * @author Peter2_Weng
 *
 */
public class UpperToolBar extends JPanel implements IResourceBundled {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5377564389400426978L;
	
	/**
	 * Definition of Resource keys
	 */
	public static final String CIRCULATION_BUTTON_TEXT = "circulation";
	public static final String BOOK_ADMIN_BUTTON_TEXT = "bookAdmin";
	public static final String CONNECT_BUTTON_TEXT = "connect";
	public static final String CONNECTING_BUTTON_TEXT = "connecting";
	public static final String CONNECTED_BUTTON_TEXT = "connected";
	
	
	/**
	 * Resources
	 */
	private String circulationButtonText;
	private String bookAdminButtonText;
	private Map<ConnectionStatus, String> connectionTextMap;

	private JFunctionButton<KeeperFunction> connectButton;
	private JFunctionButton<KeeperFunction> circulationButton;
	private JFunctionButton<KeeperFunction> bookAdminButton;
	private ActionListener actionListener;

	public UpperToolBar(ActionListener actionListener, ResourceBundle rb) {
		initResource(rb);
		
		this.setActionListener(actionListener);
		
		setLayout(new FlowLayout());
		
		connectButton = new JFunctionButton<KeeperFunction>();
		connectButton.setText(connectionTextMap.get(ConnectionStatus.CONNECT));
		connectButton.setActionCommand(KeeperFunction.CONNECT);
		connectButton.addActionListener(actionListener);
		add(connectButton);

		circulationButton = new JFunctionButton<KeeperFunction>(circulationButtonText);
		circulationButton.setActionCommand(KeeperFunction.CIRCULATION);
		circulationButton.addActionListener(actionListener);
		add(circulationButton);
		
		bookAdminButton = new JFunctionButton<KeeperFunction>(bookAdminButtonText);
		bookAdminButton.setActionCommand(KeeperFunction.BOOKADMIN);
		bookAdminButton.addActionListener(actionListener);
		add(bookAdminButton);
		
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub
		circulationButtonText = rb.getString(CIRCULATION_BUTTON_TEXT);
		bookAdminButtonText = rb.getString(BOOK_ADMIN_BUTTON_TEXT);

		connectionTextMap = new LinkedHashMap<ConnectionStatus, String>();
		connectionTextMap.put(ConnectionStatus.CONNECT, rb.getString(CONNECT_BUTTON_TEXT));
		connectionTextMap.put(ConnectionStatus.CONNECTING, rb.getString(CONNECTING_BUTTON_TEXT));
		connectionTextMap.put(ConnectionStatus.CONNECTED, rb.getString(CONNECTED_BUTTON_TEXT));
	}
	
	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void setConnectButtonStatus(ConnectionStatus c) {
		boolean b = (c == ConnectionStatus.CONNECT);
		
		connectButton.setEnabled(b);
		connectButton.setText(connectionTextMap.get(c));
	}

}
