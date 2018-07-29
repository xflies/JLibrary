/**
 * 
 */
package com.peter2.struts1.action;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.peter2.ejb.service.IBookTestService;
import com.peter2.struts1.ServiceLocator;

/**
 * @author Peter2_Weng
 *
 */
public class ListAllBooksAction extends Action {

	@EJB(beanName="SuperUserService")
	private IBookTestService service; 

	@Override     
	public ActionForward execute(
            ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) 
                 throws Exception {
		
		MessageResources messageResources = getResources(request);
		
		String helloWord = messageResources.getMessage("welcome.helloWord");
		request.setAttribute("helloWord", helloWord);
		
		service = ServiceLocator.Instance().getBookTestService();
		
		List<?> bookList = service.listBooks();
		
		request.setAttribute("bookList", bookList);
		return mapping.findForward("listAll");
	}
}
