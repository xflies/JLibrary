/**
 * 
 */
package com.peter2.swing.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A packed version JTextField only allowing typing digits (0-9) by courtesy of http://tc.wangchao.net.cn/zhidao/detail_4846228.html
 * @author Peter2_Weng
 *
 */
public class JDigitField extends AbstracrtTypedJField<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1173526850857777058L;
	
	
	public JDigitField() {
		addKeyListener(new JDigitFieldKeyListener());
	}
	
	/**
	 * NumberFormatException will only occur by "" since JDigitField only allows typing digits.
	 * @return the input integer
	 */
	@Override
	public Integer getValue() {
		Integer n;
		
		try {
			String s = getText();
			n = Integer.parseInt(s);
		} catch (NumberFormatException e) {	// Depending on the design, this only occurs when ""
			// TODO Auto-generated catch block
			n = null;
		}
		
		return n;
	}
		
	private class JDigitFieldKeyListener implements KeyListener {
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
			boolean b = m.matches();
			if (!b) {
				e.consume();
			}
		}
	}

}
