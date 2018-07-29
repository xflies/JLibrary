/**
 * 
 */
package com.peter2.swing.controller;

import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.ejb.EJBException;
import javax.swing.JList;

import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.BorrowNote__;
import com.peter2.swing.KeeperFunction;
import com.peter2.swing.Msg;
import com.peter2.swing.UnsupportedFunctionException;
import com.peter2.swing.component.IExecutable;
import com.peter2.swing.listener.BookAdminSheetListener;
import com.peter2.swing.listener.CirculationSheetListener;
import com.peter2.swing.view.BookAdminSheet;
import com.peter2.swing.view.CirculationSheet;
import com.peter2.swing.view.MyFrame;

/**
 * @author Peter2_Weng
 *
 */
@Deprecated
public class KeeperClientController implements ActionListener, MouseListener {
	private MyFrame myFrame;
	
	public KeeperClientController(MyFrame myFrame) {
		// TODO Auto-generated constructor stub
		this.myFrame = myFrame;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/**
	 * Click Upper ToolBar or Lower Toolbar
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		KeeperFunction event = Enum.valueOf(KeeperFunction.class, e.getActionCommand());
		
		eventHandle(event);
	}

	/**
	 * Click Left ToolBar
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JList list = (JList) e.getSource();
		int index = list.locationToIndex(e.getPoint());
		
		KeeperFunction event = null;
		KeeperFunction function = null;
		switch(index) {
		case 0:	// Circulation
			list.setSelectedIndex(index+1);
			Point p0 = list.indexToLocation(index+1);
			mouseClicked(new MouseEvent(list, e.getID(), e.getWhen()+100, 
					e.getModifiers(), p0.x, p0.y, e.getClickCount(), false));
			break;
		
		case 1:	// Borrow
			event = KeeperFunction.CIRCULATION;
			function = KeeperFunction.BORROW;
			break;
			
		case 2:	// Return
			event = KeeperFunction.CIRCULATION;
			function = KeeperFunction.RETURN;
			break;
			
		case 3:	// Take On-desk
			event = KeeperFunction.CIRCULATION;
			function = KeeperFunction.TAKE;
			break;
			
		case 4:	// Book Admin
			list.setSelectedIndex(index+1);
			Point p1 = list.indexToLocation(index+1);
			mouseClicked(new MouseEvent(list, e.getID(), e.getWhen()+100, 
					e.getModifiers(), p1.x, p1.y, e.getClickCount(), false));
			break;
			
		case 5:	// Query by ID
			event = KeeperFunction.BOOKADMIN;
			function = KeeperFunction.QUERY_BY_ID;
			break;
			
		case 6:	// Query ISBN
			event = KeeperFunction.BOOKADMIN;
			function = KeeperFunction.QUERY_ISBN;
			break;
			
		case 7:	// Add Book
			event = KeeperFunction.BOOKADMIN;
			function = KeeperFunction.ADD_BOOK;
			break;
			
		default:
			throw new IllegalArgumentException();
		}
		
		if (event != null) {
			eventHandle(event);
			myFrame.getControlPanel().getCurrentSheet().setFunction(function);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void eventHandle(KeeperFunction event) {
		try {
			switch(event) {
			/**
			 * Upper ToolBar Events: change display sheet (card) of the Control Panel 
			 */
			case CONNECT:
				myFrame.connect();
				break;
				
			case BOOKADMIN:
			case CIRCULATION:
				myFrame.getControlPanel().displaySheet(event);
				break;
			
			/**
			 * Lower ToolBar Events: do Submit, Export or Clear the Control Panel
			 */
			case SUBMIT:
				try {
					if (! myFrame.isConnectionAlive()) {
						return;
					}
					Object o = myFrame.getControlPanel().getCurrentSheet().submit();
					if (o == null) return;

					KeeperFunction currentFunction;
					IExecutable<KeeperFunction> currentSheet = myFrame.getControlPanel().getCurrentSheet();
					if (currentSheet.getClass() == BookAdminSheetListener.class) {
						try {
							currentFunction = ((BookAdminSheet) currentSheet).getCurrentFunction();
							BookEntity__ bookEntity;
							Book__ book;
							switch (currentFunction) {
							case QUERY_BY_ID:
								Integer bookEntityId = (Integer) o;
								bookEntity = myFrame.getKeeperService().queryBookEntity(bookEntityId);
								currentSheet.displayData(bookEntity);
								break;
								
							case QUERY_ISBN:
								String isbn = (String) o;
								book = myFrame.getKeeperService().queryBook(isbn);
								currentSheet.displayData(book);
								break;
								
							case ADD_BOOK:
								book = (Book__) o;
								bookEntity = myFrame.getKeeperService().addBook(book);
								currentSheet.displayData(bookEntity);
								break;
								
							default:
								throw new UnsupportedFunctionException(currentFunction);
							}
						} catch (NullPointerException e) {
							// Thrown by QUERY
							e.printStackTrace();
							Msg.warn("No result");;
						}
					}
					else if (currentSheet.getClass() == CirculationSheetListener.class) {	
						Integer readerId = ((Integer []) o)[0];
						Integer bookEntityId = ((Integer []) o)[1];
						BorrowNote__ returnBorrowNote = null;
						currentFunction = ((CirculationSheet) currentSheet).getCurrentFunction();
						switch (currentFunction) {
						case BORROW:
							returnBorrowNote = myFrame.getKeeperService().borrowBook(readerId, bookEntityId);
							break;
							
						case RETURN:
							returnBorrowNote = myFrame.getKeeperService().returnBook(bookEntityId);
							break;
							
						case TAKE:
							returnBorrowNote = myFrame.getKeeperService().takeOndeskBook(readerId, bookEntityId);
							break;

						default:
							break;
						}
						currentSheet.displayData(returnBorrowNote);
						break;
						
					}
				} catch (EJBException e) {
					e.printStackTrace();
					Msg.warn("EJBException!?\r\n"+e.getMessage());
				}
				break;
				
			case CLEAR:
				myFrame.getControlPanel().getCurrentSheet().clear();
				break;
				
			default:
				System.err.println(event.toString());
				throw new UnsupportedFunctionException(event);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedFunctionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Msg.error(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Msg.error(e.getMessage());
		}
	}
}
