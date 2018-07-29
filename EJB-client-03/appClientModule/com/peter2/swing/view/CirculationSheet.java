/**
 * 
 */
package com.peter2.swing.view;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.peter2.Arrayable;
import com.peter2.ejb.entity.raw.BorrowNote__;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.component.JDigitField;
import com.peter2.swing.component.JInfoTable;
import com.peter2.swing.controller.CirculationController;
import com.peter2.swing.controller.ConnectionCheckController;
import com.peter2.swing.form.CirculationForm;
import com.peter2.swing.form.FormBean;
import com.peter2.swing.form.FormField;
import com.peter2.swing.listener.CirculationSheetListener;

/**
 * @author Peter2_Weng
 *
 */
@FormBean(CirculationForm.class)
public class CirculationSheet extends AbstractExecutableSheet implements IExecutable<KeeperFunction>, IResourceBundled {

	/**
	 * 
	 */
	private static final long serialVersionUID = 318969208234226957L;

	protected enum PanelEvent {INSERT, DELETE, };
	
	/**
	 * Map for function dispatching
	 */
	private static final Map<KeeperFunction, Method> FUNCTION_METHOD_MAP = new LinkedHashMap<KeeperFunction, Method>();
	static {
		try {
			FUNCTION_METHOD_MAP.put(KeeperFunction.BORROW, CirculationSheet.class.getMethod("setFunctionAsBorrow", new Class[]{}));
			FUNCTION_METHOD_MAP.put(KeeperFunction.RETURN, CirculationSheet.class.getMethod("setFunctionAsReturn", new Class[]{}));
			FUNCTION_METHOD_MAP.put(KeeperFunction.TAKE, CirculationSheet.class.getMethod("setFunctionAsTakeOndesk", new Class[]{}));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public final static Object[] LIST_ITEM_FUNCTION_MAP = FUNCTION_METHOD_MAP.keySet().toArray();
	
	/**
	 * Definitions of Resources
	 */
	public static final String BORROW_TEXT = "borrow";
	public static final String RETURN_TEXT = "return";
	public static final String TAKE_ONDESK_TEXT = "takeOndesk";
	public static final String READER_ID_LABEL = "readerId";
	public static final String BOOK_ENTITY_ID_LABEL= "bookEntityId";
	public static final String BORROW_NOTE_ID_TEXT = "borrowNoteId";
	public static final String STATUS_TEXT = "status";
	public static final String START_DATE_TEXT = "startDate";
	public static final String EXPIRE_DATE_TEXT = "expireDate";
	public static final String RETURN_DATE_TEXT = "returnDate";
	public static final String CIRCULATION_TABLE_WIDTH = "circulationTableWidth";
	public static final String CIRCULATION_TABLE_HEIGHT = "circulationTableHeight";
	/**
	 * Resources
	 */
	private String borrowLabelText;
	private String returnLabelText;
	private String takeOndeskLabelText;
	private String readerIdLabelText;
	private String bookEntityIdLabelText;
	private String borrowNoteIdText;
	private String statusText;
	private String startDateText;
	private String expireDateText;
	private String returnDateText;
	private int tableScrollWidth;
	private int tableScrollHeight;

	/**
	 * Panel Components
	 */
	private JComboBox functionSelect;
	private JLabel readerIdLabel;
	@FormField("readerId")
	protected JDigitField readerIdDigitField;
	private JLabel bookEntityIdLabel;
	@FormField("bookEntityId")
	protected JDigitField bookEntityIdDigitField;
	private String[] tableColumns;
	private JInfoTable infoTable;
	
	public CirculationSheet(MyFrame myFrame) {
		super(myFrame);
		initResource(myFrame.getResourceBundle());
		
		setLayout(new MigLayout());
		
		listener = new CirculationSheetListener(myFrame);
		
		String[] functions = new String[] {borrowLabelText, returnLabelText, takeOndeskLabelText};
		functionSelect = new JComboBox(functions);
		functionSelect.addItemListener(listener);
		add(functionSelect, "span 2, wrap");

		readerIdLabel = new JLabel(readerIdLabelText);
		add(readerIdLabel);
		readerIdDigitField = new JDigitField();
		add(readerIdDigitField, "w 35!");
		bookEntityIdLabel = new JLabel(bookEntityIdLabelText);
		add(bookEntityIdLabel);
		bookEntityIdDigitField = new JDigitField();
		add(bookEntityIdDigitField, "w 35!, wrap");
		
		tableColumns = new String[] {borrowNoteIdText, readerIdLabelText, bookEntityIdLabelText, 
				statusText, startDateText, expireDateText, returnDateText, };
		infoTable = new JInfoTable(tableColumns);
		infoTable.setPreferredScrollableViewportSize(new Dimension(tableScrollWidth,tableScrollHeight));
		add(new JScrollPane(infoTable), "span 4");
		
		setFunction(KeeperFunction.BORROW);

		super.setController(new ConnectionCheckController(new CirculationController(myFrame)));
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException, MissingResourceException {
		borrowLabelText = rb.getString(BORROW_TEXT);
		returnLabelText = rb.getString(RETURN_TEXT);
		takeOndeskLabelText = rb.getString(TAKE_ONDESK_TEXT);
		readerIdLabelText = rb.getString(READER_ID_LABEL);
		bookEntityIdLabelText = rb.getString(BOOK_ENTITY_ID_LABEL);
		borrowNoteIdText = rb.getString(BORROW_NOTE_ID_TEXT);
		statusText = rb.getString(STATUS_TEXT);
		startDateText = rb.getString(START_DATE_TEXT);
		expireDateText = rb.getString(EXPIRE_DATE_TEXT);
		returnDateText = rb.getString(RETURN_DATE_TEXT);
		tableScrollWidth = Integer.parseInt(rb.getString(CIRCULATION_TABLE_WIDTH));
		tableScrollHeight = Integer.parseInt(rb.getString(CIRCULATION_TABLE_HEIGHT));
	}

	@Override
	public ISheetForm<KeeperFunction> submit() throws Exception {
		CirculationForm form = null;
		
		form = new CirculationForm(this);
		form.initResource(myFrame.getResourceBundle());
		form.validate(currentFunction);
		
		return form;
	}

	@Override
	public Object export() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		readerIdDigitField.clear();
		bookEntityIdDigitField.clear();
		infoTable.clearTable();
	}

	@Override
	public void displayData(Arrayable dataRow) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.displayData(dataRow);
		
		infoTable.clearTable();
		
		if (dataRow instanceof BorrowNote__) {				
			infoTable.insertRow(dataRow.toArray());
		}
		else {
			throw new IllegalArgumentException(dataRow.getClass().toString());
		}
	}

	@Override
	public void setFunction(KeeperFunction function) {
		// TODO Auto-generated method stub
		setCurrentFunction(function);
		if (function == null) {
			return;
		}
		Method method = FUNCTION_METHOD_MAP.get(function);
		try {
			method.invoke(this, new Object[]{});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setFunctionAsBorrow() {
		readerIdDigitField.setEnabled(true);
	}
	
	public void setFunctionAsReturn() {
		readerIdDigitField.setEnabled(false);
	}
	
	public void setFunctionAsTakeOndesk() {
		readerIdDigitField.setEnabled(true);
	}
}
