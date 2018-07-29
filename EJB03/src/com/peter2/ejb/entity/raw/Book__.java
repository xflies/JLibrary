package com.peter2.ejb.entity.raw;

import java.io.Serializable;
import java.util.Set;

import com.peter2.Arrayable;
import com.peter2.ejb.BookType;

/**
 * This class is designed for releasing to client.
 * @author Peter2_Weng
 *
 */
public class Book__ implements Serializable, Arrayable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8256283243700368219L;
	
	public String isbn;    
    public String name;
    public BookType type;
    public Integer qty;
    public Integer borrowQty;
    public Integer ondeskQty;
    public Integer reserveQty;

	/**/
	public Set<BookEntity__> entities;
	/**/
	
	/**
	public Set<ReserveNote_> reserveNotes;
	/**/
	
	private Set<BookEntity__> entities_;

	/**
     * 
     */
    public Book__() {
    	isbn = null;
    	name = null;
    	type = null;
    	qty = 0;
    	borrowQty = 0;
    	ondeskQty = 0;
    	reserveQty = 0;
    };
    
    /**
     * 
     * @param isbn
     * @param name
     * @param type
     */
    public Book__(String isbn, String name, BookType type) {
    	this();
    	this.isbn = isbn;
    	this.name = name;
    	this.type = type;
    }
    
    /**
	 * @return the entities_
	 */
	public Set<BookEntity__> getEntities_() {
		return entities_;
	}

	/**
	 * @param entities_ the entities_ to set
	 */
	public void setEntities__(Set<BookEntity__> entities_) {
		this.entities_ = entities_;
	}

	@Override
    public Object[] toArray() {
    	return new Object[]{ isbn, name, type, qty, borrowQty, ondeskQty, ondeskQty, reserveQty };
    }
}
