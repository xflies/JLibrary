/**
 * 
 */
package com.peter2.swing.component;

/**
 * Component which has connection ability to the EJB server 
 * @author Peter2_Weng
 *
 */
public interface IConnectable {
	/**
	 * Connect to the EJB server
	 * @return true if connection is build or false if failed
	 */
	boolean connect();
	
	/**
	 * Check connection
	 * @return
	 */
	boolean isConnectionAlive();
}
