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

import com.peter2.ejb.entity.raw.ReserveNote__;
import com.peter2.ejb.exception.InvalidReaderException;
import com.peter2.ejb.exception.InvalidReserveNoteException;
import com.peter2.struts1.ServiceLocator;
import com.peter2.struts1.exception.LoginExpectedException;

/**
 * @author Peter2_Weng
 *
 */
public class ShowReserveAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String forward = "showReserve";
		
		ReserveNote__ reserveNote = null;

		try {
			HttpSession session = request.getSession();
			boolean bLogin = session.getAttribute("login") != null;
			
			if (!bLogin) throw new LoginExpectedException();
			
			Integer readerId = (Integer) session.getAttribute("readerId");
			
			if (readerId==null) throw new InvalidReaderException(readerId);
			
			Integer reserveNoteId = Integer.parseInt(request.getParameter("reserveNoteId"));
			
			if (reserveNoteId==null) throw new InvalidReserveNoteException(reserveNoteId);

			reserveNote = ServiceLocator.Instance().getReaderService().queryReserve(readerId, reserveNoteId);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		request.setAttribute("reserveNote", reserveNote);
		return mapping.findForward(forward);
	}
}
