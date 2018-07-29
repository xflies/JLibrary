/**
 * 
 */
package com.peter2.swing.listener;

import java.awt.event.ActionListener;

import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
public abstract class AbstractFrameAccessibleListener implements ActionListener {

	protected MyFrame myFrame;

	public AbstractFrameAccessibleListener(MyFrame myFrame) {
		// TODO Auto-generated constructor stub
		this.myFrame = myFrame;
	}
}
