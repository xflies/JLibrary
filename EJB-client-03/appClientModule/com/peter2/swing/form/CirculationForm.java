/**
 * 
 */
package com.peter2.swing.form;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
public class CirculationForm extends AbstractSheetForm implements IResourceBundled {

	private Integer readerId;
	private Integer bookEntityId;

	private String msgNeedBookEntityId;
	private String msgNeedCompleteInput;

	public CirculationForm(IExecutable<?> sheet) throws Exception {
		super(sheet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(KeeperFunction function) throws Exception {
		// TODO Auto-generated method stub
		
		if (bookEntityId==null) {
			Msg.error(msgNeedBookEntityId);

			throw new IllegalArgumentException();
		}

		switch (function) {
		case RETURN:
			break;
			
		case BORROW:
		case TAKE:
			if (readerId==null) {
				Msg.error(msgNeedCompleteInput);
	
				throw new IllegalArgumentException();
			}
			break;
			
		default:
			throw new UnsupportedFunctionException(function);
		}
	}
	
	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub
		msgNeedBookEntityId = rb.getString(Globals.MSG_NEED_BOOK_ENTITY_ID);
		msgNeedCompleteInput = rb.getString(Globals.MSG_NEED_COMPLETE_INPUT);		
	}

	/**
	 * @return the readerId
	 */
	public Integer getReaderId() {
		return readerId;
	}

	/**
	 * @param readerId the readerId to set
	 */
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
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

}
