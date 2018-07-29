/**
 * 
 */
package com.peter2.struts1.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.peter2.ejb.exception.InvalidReaderException;
import com.peter2.struts1.ServiceLocator;
import com.peter2.struts1.exception.LoginExpectedException;

/**
 * @author Peter2_Weng
 *
 */
public class ShowReaderStatusAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		try {
			boolean bLogin = session.getAttribute("login") != null;
			
			if (!bLogin) throw new LoginExpectedException();
			
			Integer readerId = (Integer) session.getAttribute("readerId");
			
			if (readerId==null) throw new InvalidReaderException(readerId);

			List<?> borrowList = ServiceLocator.Instance().getReaderService().queryBorrow(readerId);
			List<?> reserveList = ServiceLocator.Instance().getReaderService().queryReserve(readerId);
			
			request.setAttribute("borrowList", borrowList);
			request.setAttribute("reserveList", reserveList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return mapping.findForward("readerStatus");
	}
}
