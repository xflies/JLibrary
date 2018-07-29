/**
 * 
 */
package com.peter2.swing.view;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.peter2.ejb.service.IKeeperService;
import com.peter2.swing.ConnectionStatus;
import com.peter2.swing.Globals;
import com.peter2.swing.Msg;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.MyConnection;
import com.peter2.swing.listener.LeftToolBarListener;
import com.peter2.swing.listener.LowerToolBarListener;
import com.peter2.swing.listener.UpperToolBarListener;

/**
 * View of the Keeper Client
 * @author Peter2_Weng
 *
 */
public class MyFrame extends JFrame implements IResourceBundled {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1192920428359797081L;

	private static final String FRAME_TITLE = "title"; 
	private static final String FRAME_WIDTH = "width";
	private static final String FRAME_HEIGHT = "height";
	/**
	 * Resources
	 */
	private ResourceBundle rb;
	private String titleText;
	private int frameWidth;
	private int frameHeight;
	private String msgExitConfirm;
	private String msgConnectionFail;
	private String msgNoService;
	private String msgConnectionDead;
	private String msgReconnect;
	private String msgAbortReconnect;

	/**
	 * View Components
	 */
	private UpperToolBar upperToolbar;
	private ControlPanel controlPanel;
	private LeftToolBar leftToolBar;
	private LowerToolBar lowerToolbar;
	
	/**
	 * Connection Module
	 */
	private MyConnection connection;

	/**
	 * Inner window listener
	 * @author Peter2_Weng
	 *
	 */
	private class myWindowListener extends WindowAdapter {
		@Override
    	public void windowClosing(WindowEvent e) {
    		if(Msg.yesNo(msgExitConfirm)) { System.exit(0); }
		}
	}
		
	public MyFrame() {
		try {
			Locale locale = Locale.getDefault();
			//Locale locale = new Locale("");
			rb = ResourceBundle.getBundle(Globals.RESORUCE, locale);

			initResource(rb);

			setTitle(titleText);
			setSize(frameWidth, frameHeight);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			/**/
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
		    addWindowListener(new myWindowListener());
		    /**/
			Container container = getContentPane();

			controlPanel = new ControlPanel(this);
			container.add(BorderLayout.CENTER, controlPanel);

			upperToolbar = new UpperToolBar(new UpperToolBarListener(this), rb);
			container.add(BorderLayout.NORTH, upperToolbar);
			
			leftToolBar = new LeftToolBar(new LeftToolBarListener(controlPanel), rb);
			container.add(BorderLayout.WEST, leftToolBar);
			
			lowerToolbar = new LowerToolBar(new LowerToolBarListener(this), rb);
			container.add(BorderLayout.SOUTH, lowerToolbar);
			
			connection = new MyConnection();
			
			//pack();	// Will fit the screen
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Msg.error("Resource Initialization Failed: \r\n"+e.getMessage());
			System.exit(1);
		}
		
	}
	
	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException, MissingResourceException {
		titleText = rb.getString(FRAME_TITLE);
		frameWidth = Integer.parseInt(rb.getString(FRAME_WIDTH));
		frameHeight = Integer.parseInt(rb.getString(FRAME_HEIGHT));
		msgExitConfirm = rb.getString(Globals.MSG_EXIT_CONFIRM);
		msgConnectionFail = rb.getString(Globals.MSG_CONNECTION_FAIL);
		msgNoService = rb.getString(Globals.MSG_NO_SERVICE);
		msgConnectionDead = rb.getString(Globals.MSG_CONNCETION_DEAD);
		msgReconnect = rb.getString(Globals.MSG_RECONNECT);
		msgAbortReconnect = rb.getString(Globals.MSG_ABORT_RECONNECT);
	}
	
	public void showFrame() {
		setVisible(true);
		
		if (connection.isAutoConnect()) {
			connect();
		}
	}

	/**
	 * @return the resourceBundel
	 */
	public ResourceBundle getResourceBundle() {
		return rb;
	}

	/**
	 * @return the controlPanel
	 */
	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	/**
	 * @return the connection
	 */
	public MyConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(MyConnection connection) {
		this.connection = connection;
	}

	public boolean connect() {
		upperToolbar.setConnectButtonStatus(ConnectionStatus.CONNECTING);
		boolean b = connection.connect();
		while (!b) {
			if( Msg.okCancel(msgConnectionFail) ) {
				return connect();
			} 
			else {
				break;
			}
		}
		upperToolbar.setConnectButtonStatus(b ? ConnectionStatus.CONNECTED : ConnectionStatus.CONNECT);		

		return b;
	}
	
	public boolean isConnectionAlive() {
		if (connection.getKeeperService()==null) {
			Msg.error(msgNoService);
			return false;
		}
		boolean b;
		if (! (b = connection.isConnectionAlive())) {
			Msg.error(msgConnectionDead);
		}
		while (!b) {
			if (Msg.yesNo(msgReconnect)) {
				return connect();
			}
			else {
				Msg.info(msgAbortReconnect);
				break;
			}
		}
		upperToolbar.setConnectButtonStatus(b ? ConnectionStatus.CONNECTED : ConnectionStatus.CONNECT);
		
		return b;
	}
	
	public IKeeperService getKeeperService() {
		return connection.getKeeperService();
	}
}
