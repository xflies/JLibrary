/**
 * 
 */
package com.peter2.swing.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.Msg;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.form.CirculationForm;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class CirculationController extends AbstractSheetController {

	private static final Map<KeeperFunction, Method> FUNCTION_METHOD_MAP = new HashMap<KeeperFunction, Method>();
	static {
		try {
			FUNCTION_METHOD_MAP.put(KeeperFunction.BORROW, 
					CirculationController.class.getMethod("doBorrowBook", CirculationForm.class));
			FUNCTION_METHOD_MAP.put(KeeperFunction.RETURN, 
					CirculationController.class.getMethod("doReturnBook", CirculationForm.class));
			FUNCTION_METHOD_MAP.put(KeeperFunction.TAKE, 
					CirculationController.class.getMethod("doTakeOndesk", CirculationForm.class));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CirculationController(MyFrame myFrame) {
		// TODO Auto-generated constructor stub
		super(myFrame);
	}

	@Override
	public Object doFunction(KeeperFunction function, ISheetForm<KeeperFunction> form) throws Exception {
		// TODO Auto-generated method stub
		Method method = FUNCTION_METHOD_MAP.get(function);

		return method.invoke(this, form);
	}
	public Object doBorrowBook(CirculationForm form) throws Exception {
		Object o = null;
		Integer readerId = form.getReaderId();
		Integer bookEntityId = form.getBookEntityId();

		try {
			o = getMyFrame().getKeeperService().borrowBook(readerId, bookEntityId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Msg.error(e.getMessage());
			o = null;
		}
		
		return o;
	}
	public Object doReturnBook(CirculationForm form) throws Exception {
		Object o = null;
		Integer bookEntityId = form.getBookEntityId();
		
		try {
			o = getMyFrame().getKeeperService().returnBook(bookEntityId);
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}
	public Object doTakeOndesk(CirculationForm form) throws Exception {
		Object o = null;
		Integer readerId = form.getReaderId();
		Integer bookEntityId = form.getBookEntityId();
		
		try {
			o = getMyFrame().getKeeperService().takeOndeskBook(readerId, bookEntityId);
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}

}
