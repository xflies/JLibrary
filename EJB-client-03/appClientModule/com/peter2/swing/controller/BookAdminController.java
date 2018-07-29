/**
 * 
 */
package com.peter2.swing.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.peter2.ejb.entity.raw.Book__;
import com.peter2.swing.Globals;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.Msg;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.form.BookAdminForm;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public class BookAdminController extends AbstractSheetController implements IResourceBundled {

	private static final Map<KeeperFunction, Method> FUNCTION_METHOD_MAP = new HashMap<KeeperFunction, Method>();
	static {
		try {
			FUNCTION_METHOD_MAP.put(KeeperFunction.QUERY_BY_ID, 
					BookAdminController.class.getMethod("doQueryById", BookAdminForm.class));
			FUNCTION_METHOD_MAP.put(KeeperFunction.QUERY_ISBN, 
					BookAdminController.class.getMethod("doQueryIsbn", BookAdminForm.class));
			FUNCTION_METHOD_MAP.put(KeeperFunction.ADD_BOOK, 
					BookAdminController.class.getMethod("doAddBook", BookAdminForm.class));
			FUNCTION_METHOD_MAP.put(KeeperFunction.MAINTAIN, 
					BookAdminController.class.getMethod("doMaintain", BookAdminForm.class));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Resources
	 */
	private String msgNoResults;
	
	public BookAdminController(MyFrame myFrame) {
		// TODO Auto-generated constructor stub
		super(myFrame);
		initResource(myFrame.getResourceBundle());
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub
		msgNoResults = rb.getString(Globals.MSG_NO_RESULTS);
	}
	
	@Override
	public Object doFunction(KeeperFunction function, ISheetForm<KeeperFunction> form) throws Exception {
		// TODO Auto-generated method stub
		Method method = FUNCTION_METHOD_MAP.get(function);

		return method.invoke(this, form);
	}
	public Object doQueryById(BookAdminForm form) throws Exception {
		Object o = null;
		Integer bookEntityId = form.getBookEntityId();

		try {
			o = getMyFrame().getKeeperService().queryBookEntity(bookEntityId);
			if (o==null) {
				Msg.info(msgNoResults);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}
	public Object doQueryIsbn(BookAdminForm form) throws Exception {
		Object o = null;
		String isbn = form.getIsbn();

		try {
			o = getMyFrame().getKeeperService().queryBook(isbn);
			if (o==null) {
				Msg.info(msgNoResults);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}
	public Object doAddBook(BookAdminForm form) throws Exception {
		Object o = null;
		Book__ newBook = new Book__();
		newBook.isbn = form.getIsbn();
		newBook.name = form.getBookName();
		newBook.type = form.getBookType();

		try {
			o = getMyFrame().getKeeperService().addBook(newBook);
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}
	public Object doMaintain(BookAdminForm form) throws Exception {
		Object o = null;

		try {
			o = getMyFrame().getKeeperService().updateBook(form.getIsbn(), form.getBookName(), form.getBookType());
		} catch (Exception e) {
			// TODO: handle exception
			Msg.error(e.getMessage());
			o = null;
		}
		return o;
	}

}
