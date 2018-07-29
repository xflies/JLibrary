/**
 * 
 */
package com.peter2.swing.form;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.peter2.ejb.BookType;
import com.peter2.swing.Globals;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.Msg;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.component.IResourceBundled;

/**
 * @author Peter2_Weng
 *
 */
public class BookAdminForm extends AbstractSheetForm implements IResourceBundled {
	
	private Integer bookEntityId;
	private String isbn;
	private String bookName;
	private BookType bookType;

	private String msgNeedBookEntityId;
	private String msgNeedIsbn;
	private String msgNoBookNameWarning;

	public BookAdminForm(IExecutable<?> sheet) throws Exception {
		super(sheet);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the bookEntityId
	 */
	public Integer getBookEntityId() {
		return bookEntityId;
	}

	/**
	 * @param bookEntityId the bookEntityId to set
	 */
	public void setBookEntityId(Integer bookEntityId) {
		this.bookEntityId = bookEntityId;
	}
	
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the type
	 */
	public BookType getBookType() {
		return bookType;
	}

	/**
	 * @param type the type to set
	 */
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub

		msgNeedBookEntityId = rb.getString(Globals.MSG_NEED_BOOK_ENTITY_ID);
		msgNeedIsbn = rb.getString(Globals.MSG_NEED_ISBN);
		msgNoBookNameWarning = rb.getString(Globals.MSG_NO_BOOK_NAME_WARNING);
	}

	@Override
	public void validate(KeeperFunction function) throws Exception {
		// TODO Auto-generated method stub
		switch (function) {
		case QUERY_BY_ID:
			if (bookEntityId == null) {
				Msg.error(msgNeedBookEntityId);
				throw new IllegalArgumentException();
			}
			break;
		
		case ADD_BOOK:
			if ("".equals(bookName) && ! Msg.yesNo(msgNoBookNameWarning)) {
				throw new IllegalArgumentException();
			}

		case QUERY_ISBN:
			if ("".equals(isbn)) {
				Msg.error(msgNeedIsbn);
				throw new IllegalArgumentException();
			}
			break;
			
		case MAINTAIN:
			if ("".equals(isbn) || "".equals(bookName)) {
				throw new IllegalArgumentException();
			}
			break;
			
		default:
			throw new UnsupportedFunctionException(function);
		}
	}

}
