/**
 * 
 */
package com.peter2.struts1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.peter2.ejb.service.IReaderService;
import com.peter2.struts1.ServiceLocator;
import com.peter2.struts1.form.LoginForm;

/**
 * @author Peter2_Weng
 *
 */
public class UnsafeLoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {

		boolean bLoginSuccess = false;
		String forward = null;

		try {
			LoginForm loginForm = (LoginForm) form;
			
			String readerName = loginForm.getReaderName();
 			String password = loginForm.getPassword();

			/**/

			IReaderService service = ServiceLocator.Instance().getReaderService();
			if (service == null) {
				throw new RuntimeException("Service unavailable");
			}

			if ( !"".equals(loginForm.getReaderName()) && !"".equals(loginForm.getPassword()) ) {
				Integer readerId = service.unsafeLogin(readerName, password);

				HttpSession session = request.getSession();
				session.setAttribute("login", true);
				session.setAttribute("readerId", readerId);
				session.setAttribute("readerName", readerName);

				bLoginSuccess = true;
			}
			/**/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		forward = bLoginSuccess ? "success" : "failed";

		return mapping.findForward(forward);
		
	}
}
