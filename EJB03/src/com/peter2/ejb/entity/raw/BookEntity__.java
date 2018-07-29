/**
 * 
 */
package com.peter2.ejb.entity.raw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.peter2.Arrayable;
import com.peter2.ejb.BookEntityLocation;

/**
 * @author Peter2_Weng
 *
 */
public class BookEntity__ implements Serializable, Arrayable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4616998945945867331L;

	public Integer id;

    /**/
    public Book__ book;
	/**/

    public BookEntityLocation location;

    /**/
    public BorrowNote__ borrowNote;
	/**/

    @Override
    public Object[] toArray() {
    	Collection<Object> c = new ArrayList<Object>(Arrays.asList(book.toArray()));
		c.add(id);
		c.add(borrowNote == null ? "" : borrowNote.id);
		c.add(location);
		
    	return c.toArray();
    }
}
