/**
 * 
 */
package com.peter2.swing.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.peter2.swing.KeeperFunction;
import com.peter2.swing.component.IResourceBundled;

/**
 * @author Peter2_Weng
 *
 */
public class LeftToolBar extends JPanel implements IResourceBundled {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043535069543567821L;
	
	public static final Map<KeeperFunction, String> FUNCTION_MAP = Collections.unmodifiableMap(
			new LinkedHashMap<KeeperFunction, String>() {/**
				 * 
				 */
				private static final long serialVersionUID = -3388612366685497223L;
			{
				put(KeeperFunction.CIRCULATION, UpperToolBar.CIRCULATION_BUTTON_TEXT    );
				put(KeeperFunction.BORROW,      CirculationSheet.BORROW_TEXT    );
				put(KeeperFunction.RETURN,      CirculationSheet.RETURN_TEXT    );
				put(KeeperFunction.TAKE,        CirculationSheet.TAKE_ONDESK_TEXT);
				put(KeeperFunction.BOOKADMIN,   UpperToolBar.BOOK_ADMIN_BUTTON_TEXT     );
				put(KeeperFunction.QUERY_BY_ID, BookAdminSheet.QUERY_BY_ID      );
				put(KeeperFunction.QUERY_ISBN,  BookAdminSheet.QUERY_ISBN       );
				put(KeeperFunction.ADD_BOOK,    BookAdminSheet.ADD_BOOK         );
				put(KeeperFunction.MAINTAIN,    BookAdminSheet.MAINTAIN         );
			}});
	public static final Object[] LIST_ITEM_FUNCTION_MAP = FUNCTION_MAP.keySet().toArray();

	private String[] menuContent;
	private JList listMenu;
	
	public LeftToolBar(MouseListener listener, ResourceBundle rb) {
		initResource(rb);
		
		listMenu = new JList(menuContent);
		listMenu.addMouseListener(listener);

		// Decorate JList; ListCellRenderer is used
		listMenu.setFont(listMenu.getFont().deriveFont(Font.PLAIN));
		ListCellRenderer renderer = new MyRenderer();
		Component c = renderer.getListCellRendererComponent(listMenu, "", 0, true, true);
		listMenu.setCellRenderer((ListCellRenderer) c);

		add(listMenu);
	}

	@Override
	public void initResource(ResourceBundle rb) throws NumberFormatException,
			MissingResourceException {
		// TODO Auto-generated method stub
		menuContent = new String[LIST_ITEM_FUNCTION_MAP.length];
		for (int i=0; i<LIST_ITEM_FUNCTION_MAP.length; ++i) {
			String resourceKey = FUNCTION_MAP.get(LIST_ITEM_FUNCTION_MAP[i]);
			menuContent[i] = rb.getString(resourceKey);
		}
	}
	
	/**
	 * Renderer for JList by courtesy of http://blog.csdn.net/yiyi6/article/details/26238617
	 * @author Peter2_Weng
	 *
	 */
	private class MyRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6431436208238654686L;
		private String valueIndex="0,4";
		
		@Override
		public Component getListCellRendererComponent(javax.swing.JList list,
				Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            Color background = null;
            Color foreground = null;
            Font
            font = null;
            if(valueIndex != null){
            	String[] _valueIndex = valueIndex.split(",");
                for(String v : _valueIndex) {
                    if((index+"").equals(v)) {
                        background = Color.RED;
                        foreground = Color.WHITE;
                        font = list.getFont().deriveFont(Font.BOLD);
                    }
                }
            }
  
            if(isSelected){
                background = Color.BLUE;
                foreground = new Color(0, 255, 0);
            }    
            setBackground(background);
            setForeground(foreground);
            setFont(font);
            
            return this;		
		}
	}
}
