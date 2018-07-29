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

/**
 * @author Peter2_Weng
 *
 */
public class LogoutAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.setAttribute("login", false);
		
		session.invalidate();
		
		return mapping.findForward("logout");
	}
}
