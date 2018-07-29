/**
 * 
 */
package com.peter2.struts1.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.peter2.struts1.Globals;

/**
 * @author Peter2_Weng
 *
 */
public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2820199357892023936L;

	private String readerName;
	private String password;
	/**
	 * @return the name
	 */
	public String getReaderName() {
		return readerName;
	}
	/**
	 * @param name the name to set
	 */
	public void setReaderName(String name) {
		this.readerName = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void reset(ActionMapping mapping, 
            HttpServletRequest req) {
		readerName = null;
		password = null;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping, 
            HttpServletRequest request) {
        
        Map<String, String> errModel = new HashMap<String, String>();
        MessageResources messageResources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
        
        if(getReaderName() == null || 
        		getReaderName().length() < 1) {
            String msg = 
               messageResources.getMessage("error.invalidReaderName");
            errModel.put("invalidUsername", msg);
        }
        
        if(getPassword() == null || 
                getPassword().length() < 1) { 
            String msg = 
               messageResources.getMessage(
                               "error.invalidPassword");
            errModel.put("invalidPassword", msg);
        }
        
        if(errModel.get("invalidUsername") == null &&
           errModel.get("invalidPassword") == null) {
            // no error happened
            // return null to proceed the Action
            return null;
        }
        else { 
            request.setAttribute("errors", errModel);
            
            // fake codes, just tell RequestProcessor
            // not to invoke Action
            ActionErrors errors = new ActionErrors(); 
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
            return errors;
        }
    }
}
