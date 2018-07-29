/**
 * 
 */
package com.peter2.swing.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.peter2.ejb.service.IKeeperService;
import com.peter2.swing.Globals;

/**
 * A connection object which can connect to EJB server
 * @author Peter2_Weng
 *
 */
public class MyConnection implements IConnectable {

	/**
	// The following lines are replaced due to a smarter configuration.
	
	private static final String INITIAL_CONTEXT_FACTORY = "INITIAL_CONTEXT_FACTORY";
	private static final String PROVIDER_URL = "PROVIDER_URL";
	/**/
	//private static final String INITIAL_CONTEXT_FACTORY = Context.INITIAL_CONTEXT_FACTORY;
	private static final String PROVIDER_URL = Context.PROVIDER_URL;
	private static final String KEEPER_SERVICE = "keeperService";
	private static final String AUTO_CONNECT = "autoConnect";

	private Properties props;
	//private String initialContextFactory;
	private String providerUrl;
	private String keeperServiceName;
	
	private IKeeperService keeperService;
	private boolean autoConnect = false;
	
	public MyConnection() throws Exception {
		initProps();
	}
	
	public void initProps() throws IOException {
		props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(Globals.CONNECTION_PROPERTIES);
		props.load(stream);
		stream.close();
		
		/**
		// The following lines are replaced due to a smarter configuration.
		
		initialContextFactory = props.getProperty(INITIAL_CONTEXT_FACTORY);
		/**/
		providerUrl = props.getProperty(PROVIDER_URL);
		keeperServiceName = props.getProperty(KEEPER_SERVICE);
		
		String autoConnect = props.getProperty(AUTO_CONNECT);
		this.autoConnect = "1".equals(autoConnect);
	}
	
	/* (non-Javadoc)
	 * @see com.peter2.swing.component.IConnectable#connect()
	 */
	@Override
	public boolean connect() {
		// TODO Auto-generated method stub

		/**
		// The following lines are deprecated due to a smarter configuration.
		
		props.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		props.put(Context.PROVIDER_URL, providerUrl);
		/**/
		/**/
		try {
			Context context = new InitialContext(props);
			keeperService = (IKeeperService) context.lookup(keeperServiceName);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Send a simple request to the URL provided by EJB server to check if the connection is alive. 
	 * @return true if the connection is alive or false when
	 */
	@Override
	public boolean isConnectionAlive() {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(providerUrl).openConnection();
			con.setRequestMethod("HEAD");
			boolean b = con.getResponseCode() == HttpURLConnection.HTTP_OK;

			return b;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(providerUrl);
			System.err.println("Connection failed.");
			return false;
		}
	}

	/**
	 * @return the keeperService
	 */
	public IKeeperService getKeeperService() {
		return keeperService;
	}

	/**
	 * @return the autoConnect
	 */
	public boolean isAutoConnect() {
		return autoConnect;
	}

}

