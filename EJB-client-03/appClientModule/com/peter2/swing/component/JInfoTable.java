/**
 * 
 */
package com.peter2.swing.component;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * JTable supporting row insertion and deletion by courtesy of http://stackoverflow.com/questions/3549206/how-to-add-row-in-jtable
 * @author Peter2_Weng
 *
 */
public class JInfoTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8636463205214489073L;
	
	/**
	// Default 
	public JInfoTable() {
		this.setModel(new DefaultTableModel());
	}
	/**/
	
	public JInfoTable(TableModel dm) {
		super(dm);
	}
	
	public JInfoTable(Object[][] rowData, Object[] columnNames) {
		super(new MyTableModel(rowData, columnNames));
	}

	public JInfoTable(Object[] columnNames) {
		super(new MyTableModel(new Object[][]{}, columnNames));
	}
	
	/**
	 * 
	 * @param dataRow to be inserted
	 */
	public void insertRow(Object[] dataRow) {
		((DefaultTableModel) getModel()).addRow(dataRow);
	}
	
	/**
	 * Insert data rows which will be attached to the bottom
	 * @param dataRows to be inserted
	 */
	public void insertRows(Object[][] dataRows) {
		for (Object[] row : dataRows) {
			insertRow(row);
		}
	}
	
	public void clearTable() {
		((DefaultTableModel) getModel()).getDataVector().removeAllElements();
		revalidate();
	}

	/**
	 * 
	 * @return selected row
	 */
	public Vector<?> getSelectedDataRow() {
		Vector<?> dataRow = null;
		
		int index = getSelectedRow();
		if (index != -1) {
			dataRow = (Vector<?>) ((DefaultTableModel) getModel()).getDataVector().elementAt(index);
		}
		
		return dataRow;
	}
	
	/**
	 * 
	 * @return all selected rows
	 */
	public Vector<?> getSelectedDataRows() {
		Vector<Object> dataRows = null;
		
		int[] indices = getSelectedRows();
		if (indices.length > 0) {
			dataRows = new Vector<Object>();
			for (int index : indices) {
				dataRows.add(((DefaultTableModel) getModel()).getDataVector().elementAt(index));
			}
		}
		
		return dataRows;
	}
	
	public Vector<?> getDataRow(int index) {
		Object dataRow = ((DefaultTableModel) getModel()).getDataVector().elementAt(index);
		
		return (Vector<?>) dataRow;
	}
	
}
