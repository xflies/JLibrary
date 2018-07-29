/**
 * 
 */
package com.peter2.swing.view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.peter2.Arrayable;
import com.peter2.ejb.BookType;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.component.IResourceBundled;
import com.peter2.swing.component.ISheetForm;
import com.peter2.swing.component.JDigitField;
import com.peter2.swing.component.JInfoTable;
import com.peter2.swing.component.JIsbnField;
import com.peter2.swing.controller.BookAdminController;
import com.peter2.swing.controller.ConnectionCheckController;
import com.peter2.swing.form.BookAdminForm;
import com.peter2.swing.form.FormBean;
import com.peter2.swing.form.FormField;
import com.peter2.swing.listener.BookAdminSheetListener;

/**
 * @author Peter2_Weng
 *
 */
@FormBean(BookAdminForm.class)
public class BookAdminSheet extends AbstractExecutableSheet implements IExecutable<KeeperFunction>, IResourceBundled {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9067535572679508811L;

	protected enum PanelEvent {INSERT, DELETE, };
	
	private static final Map<KeeperFunction, Method> FUNCTION_METHOD_MAP = new LinkedHashMap<KeeperFunction, Method>();
	static {
		try {
			FUNCTION_METHOD_MAP.put(KeeperFunction.QUERY_BY_ID, BookAdminSheet.class.getMethod("setFunctionAsQueryById", new Class[]{}));
			FUNCTION_METHOD_MAP.put(KeeperFunction.QUERY_ISBN, BookAdminSheet.class.getMethod("setFunctionAsQueryIsbn", new Class[]{}));
			FUNCTION_METHOD_MAP.put(KeeperFunction.ADD_BOOK, BookAdminSheet.class.getMethod("setFunctionAsAddBook", new Class[]{}));
			FUNCTION_METHOD_MAP.put(KeeperFunction.MAINTAIN, BookAdminSheet.class.getMethod("setFunctionAsMaintain", new Class[]{}));
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
	 * Definition of resource keys
	 */
	public static final String QUERY_BY_ID = "queryById";
	public static final String QUERY_ISBN = "queryIsbn";
	public static final String ADD_BOOK = "addBook";
	public static final String MAINTAIN = "maintain";
	public static final String ISBN = "isbn";
	public static final String BOOK_NAME = "name";
	public static final String BOOK_TYPE = "type";
	public static final String BOOK_QTY = "bookQty";
	public static final String BORROW_QTY = "borrowQty";
	public static final String ONDESK_QTY = "ondeskQty";
	public static final String RESERVE_QTY = "reserveQty";
	public static final String BOOK_ENTITY_ID = "entityId";
	public static final String BORROW_NOTE_ID = "borrowId";
	public static final String BOOK_LOCATION = "bookLocation";
	private static final String BOOKADMIN_TABLE_WIDTH = "bookAdminTableWidth";
	private static final String BOOKADMIN_TABLE_HEIGHT = "bookAdminTableWidth";
	/**
	 * Resources
	 */
	private String queryByIdText;
	private String queryIsbnText;
	private String addBookText;
	private String maintainText;
	private String isbnLabelText;
	private String bookNameLabelText;
	private String bookTypeLabelText;
	private String bookQtyLabelText;
	private String borrowQtyLabelText;
	private String ondeskQtyLabelText;
	private String reserveQtyLabelText;
	private String bookEntityIdLabelText;
	private String borrowNoteIdLabelText;
	private String bookLocationLabelText;
	private int tableScrollWidth;
	private int tableScrollHeight;
	
	/**
	 * Panel components
	 */
	private JComboBox functionSelect;
	private JLabel isbnLabel;
	@FormField("isbn")
	protected JIsbnField isbnText;
	private JLabel bookEntityIdLabel;
	@FormField("bookEntityId")
	protected JDigitField bookEntityIdText;
	private JLabel bookNameLabel;
	@FormField("bookName")
	protected JTextField bookNameText;
	private JLabel bookTypeLabel;
	@FormField("bookType")
	protected JComboBox bookTypeList;
	private JInfoTable infoTable;

	public BookAdminSheet(MyFrame myFrame) {
		super(myFrame);
		initResource(myFrame.getResourceBundle());

		setLayout(new MigLayout());
		
		BookAdminSheetListener listener = new BookAdminSheetListener(myFrame);
		super.listener = listener;
		
		String[] FUNCTIONS = {queryByIdText, queryIsbnText, addBookText, maintainText, };
		functionSelect = new JComboBox(FUNCTIONS);
		functionSelect.addItemListener(listener);
		add(functionSelect, "wrap");

		isbnLabel = new JLabel(isbnLabelText);
		add(isbnLabel);
		isbnText = new JIsbnField();
		add(isbnText, "w 140!");
		bookEntityIdLabel = new JLabel(bookEntityIdLabelText);
		add(bookEntityIdLabel);
		bookEntityIdText = new JDigitField();
		add(bookEntityIdText, "w 35!, wrap");
		bookNameLabel = new JLabel(bookNameLabelText);
		add(bookNameLabel);
		bookNameText = new JTextField();
		add(bookNameText, "w 140!");
		bookTypeLabel = new JLabel(bookTypeLabelText);
		add(bookTypeLabel);
		bookTypeList = new JComboBox(BookType.values());
		add(bookTypeList, "w 100!, wrap");

		String[] tableColumns = {isbnLabelText, bookNameLabelText, bookTypeLabelText, bookQtyLabelText, borrowQtyLabelText, ondeskQtyLabelText, 
				reserveQtyLabelText, reserveQtyLabelText, bookEntityIdLabelText, borrowNoteIdLabelText, bookLocationLabelText};
		infoTable = new JInfoTable(tableColumns);
		infoTable.setPreferredScrollableViewportSize(new Dimension(tableScrollWidth, tableScrollHeight));
		add(new JScrollPane(infoTable), "span 4");
		infoTable.addMouseListener(listener);
		/**/
		
		setFunction(KeeperFunction.QUERY_BY_ID);

		super.setController(new ConnectionCheckController(new BookAdminController(myFrame)));
	}
	
	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException, MissingResourceException {
		queryByIdText = rb.getString(QUERY_BY_ID);
		queryIsbnText = rb.getString(QUERY_ISBN);
		addBookText = rb.getString(ADD_BOOK);
		maintainText = rb.getString(MAINTAIN);
		isbnLabelText = rb.getString(ISBN);
		bookNameLabelText = rb.getString(BOOK_NAME);
		bookTypeLabelText = rb.getString(BOOK_TYPE);
		bookQtyLabelText = rb.getString(BOOK_QTY);
		borrowQtyLabelText = rb.getString(BORROW_QTY);
		ondeskQtyLabelText = rb.getString(ONDESK_QTY);
		reserveQtyLabelText = rb.getString(RESERVE_QTY);
		bookEntityIdLabelText = rb.getString(BOOK_ENTITY_ID);
		borrowNoteIdLabelText = rb.getString(BORROW_NOTE_ID);
		bookLocationLabelText = rb.getString(BOOK_LOCATION);
		tableScrollWidth = Integer.parseInt(rb.getString(BOOKADMIN_TABLE_WIDTH));
		tableScrollHeight = Integer.parseInt(rb.getString(BOOKADMIN_TABLE_HEIGHT));
	}
	
	@Override
	public ISheetForm<KeeperFunction> submit() throws Exception {
		// TODO Auto-generated method stubS
		BookAdminForm form = null;
		
		form = new BookAdminForm(this);
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
		isbnText.clear();
		bookEntityIdText.clear();
		bookNameText.setText(null);
		infoTable.clearTable();
		
		return;
	}

	@Override
	public void displayData(Arrayable dataRow) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.displayData(dataRow);
		
		infoTable.clearTable();

		if (dataRow instanceof Book__) {
			for (BookEntity__ bookEntity : ((Book__) dataRow).getEntities_()) {
				infoTable.insertRow(bookEntity.toArray());
			}
		}
		else if (dataRow instanceof BookEntity__) {
			infoTable.insertRow(dataRow.toArray());
		}
		else {
			throw new IllegalArgumentException("Can not resolve this type: "+dataRow.getClass());
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
	public void setFunctionAsQueryById() {
		bookEntityIdText.setEnabled(true);
		isbnText.setEnabled(false);
		bookNameText.setEnabled(false);
		bookTypeList.setEnabled(false);
	}
	public void setFunctionAsQueryIsbn() {
		bookEntityIdText.setEnabled(false);
		isbnText.setEnabled(true); isbnText.setEditable(true);
		bookNameText.setEnabled(false);
		bookTypeList.setEnabled(false);
	}
	public void setFunctionAsAddBook() {
		bookEntityIdText.setEnabled(false);
		isbnText.setEnabled(true); isbnText.setEditable(true);
		bookNameText.setEnabled(true);
		bookTypeList.setEnabled(true);
	}
	public void setFunctionAsMaintain() {
		bookEntityIdText.setEnabled(false);
		isbnText.setEnabled(true); isbnText.setEditable(false);
		bookNameText.setEnabled(true);
		bookTypeList.setEnabled(true);
	}
	
	public void fetchTableDate(MouseEvent event) {
		int row = infoTable.rowAtPoint(event.getPoint());
		//int col = infoTable.columnAtPoint(event.getPoint());
		
		Vector<?> dataRow = infoTable.getDataRow(row);
		isbnText.setText((String) dataRow.get(0));
		bookNameText.setText((String) dataRow.get(1));
		bookTypeList.setSelectedItem(dataRow.get(2));;
		bookEntityIdText.setValue((Integer) dataRow.get(8));
	}
}
