/**
 * 
 */
package com.peter2.swing.component;

import javax.swing.table.DefaultTableModel;

/**
 * @author Peter2_Weng
 *
 */
public class MyTableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7018733419092147361L;

	public MyTableModel(Object[][] rowData, Object[] columnNames) {
		// TODO Auto-generated constructor stub
		super(rowData, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
}
