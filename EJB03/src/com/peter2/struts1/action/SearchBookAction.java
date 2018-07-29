/**
 * 
 */
package com.peter2.struts1.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.peter2.struts1.ServiceLocator;

/**
 * @author Peter2_Weng
 *
 */
public class SearchBookAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String forward = "showSearch";
		
		List<?> searchResults = null;

		try {
			String bookName = request.getParameter("bookName");
			
			searchResults = ServiceLocator.Instance().getReaderService().searchBook(bookName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		request.setAttribute("searchResults", searchResults);
		return mapping.findForward(forward);
	}
}
