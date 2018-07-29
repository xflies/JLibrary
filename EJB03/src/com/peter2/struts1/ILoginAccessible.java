/**
 * 
 */
package com.peter2.struts1;

import com.peter2.struts1.exception.LoginExpectedException;

/**
 * @author Peter2_Weng
 *
 */
public interface ILoginAccessible {

	UserInfo checkLogin() throws LoginExpectedException;
}
