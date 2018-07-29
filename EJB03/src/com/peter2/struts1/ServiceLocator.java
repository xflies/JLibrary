/**
 * 
 */
package com.peter2.struts1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.peter2.ejb.service.IBookTestService;
import com.peter2.ejb.service.IReaderService;

/**
 * A singleton service locator to retrieve services on the EJB server
 * @author Peter2_Weng
 *
 */
public class ServiceLocator {
	
	private static ServiceLocator SERVICE_LOCATOR;
	
//	private static final String INITIAL_CONTEXT_FACTORY = "initialContextFactory";
//	private static final String PROVIDER_URL = "providerUrl";
	private static final String BOOK_TEST_SERVICE_NAME = "bookTestService";
	private static final String READER_SERVICE_NAME = "readerService";

//	private String initialContextFactory;
//	private String providerUrl;
	private String bookTestServiceName;
	private String readerServiceName;
	
	private IBookTestService bookTestService;
	private IReaderService readerService;

	private Properties props;
	
	synchronized public static ServiceLocator Instance() throws Exception {
		if (SERVICE_LOCATOR==null) {
			SERVICE_LOCATOR = new ServiceLocator();
		}
		
		return SERVICE_LOCATOR;
	}

	protected ServiceLocator() throws Exception {
		initProps();

		connect();
	}
	
	public void initProps() throws IOException {
		props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(Globals.SERVICE_PROPS);
		props.load(stream);
		stream.close();
		
//		initialContextFactory = props.getProperty(INITIAL_CONTEXT_FACTORY);
//		providerUrl = props.getProperty(PROVIDER_URL);
		bookTestServiceName = props.getProperty(BOOK_TEST_SERVICE_NAME);
		readerServiceName = props.getProperty(READER_SERVICE_NAME);
	}
	
	public void connect() throws NamingException {
//		props.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
//		props.put(Context.PROVIDER_URL, providerUrl);

		/**/
		Context context = new InitialContext(props);
		bookTestService = (IBookTestService) context.lookup(bookTestServiceName);
		readerService = (IReaderService) context.lookup(readerServiceName);
		/**/
	}
	
	public IBookTestService getBookTestService() {
		return bookTestService;
	}

	public IReaderService getReaderService() {
		return readerService;
	}
}
