package com.peter2.ejb.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.peter2.ObjectCloneFactory;
import com.peter2.ejb.BookEntityLocation;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.BorrowNote__;

/**
 * @author Peter2_Weng
 *
 */
@Entity
@Table(name="book_entity")
public class BookEntity extends BookEntity__ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1785047419021792870L;

	public BookEntity() {
		location = BookEntityLocation.SHELF;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BE_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
    	this.id = id;
    }
    
    /**/
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="ISBN")
	public Book getBook() {
		return (Book) book;
	}
	public void setBook(Book book) {
		this.book = book;
	} 
	/**/

	@Enumerated(EnumType.STRING)
	@Column(name="LOCATION")
	public BookEntityLocation getLocation() {
		return location;
	}
	public void setLocation(BookEntityLocation location) {
		this.location = location;
	}

	/**/
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="BORROW_ID")
	public BorrowNote getBorrowNote() {
		return (BorrowNote) borrowNote;
	}
	public void setBorrowNote(BorrowNote borrowNote) {
		this.borrowNote = borrowNote;
	}

	/**/
	@Transient
	public boolean isBorrowed() {
		// return "BORROW".equals(location);
		return borrowNote != null;
	}
	@Transient
	public boolean isOndesk() {
		return "ONDESK".equals(location);
	}
	/**/
	
	protected BookEntity__ toBookEntity__() throws Exception {
		BookEntity__ c = (BookEntity__) ObjectCloneFactory.getParent(this);
		c.book = null;
		/**
		BookEntity_ c = new BookEntity_();

		c.id = id;
		c.location = location;
		/**/
		
		return c;
	}
	
	public BookEntity__ toBookEntity__FromBook__(Book__ book) throws Exception {
		BookEntity__ c = toBookEntity__();
		
		c.book = book;
		if (borrowNote != null) { 
			c.borrowNote = ((BorrowNote) borrowNote).toBorrowNote_();
		}

		return c;
	}
	
	public BookEntity__ toBookEntity__FromBorrowNote__(BorrowNote__ bo) throws Exception {
		BookEntity__ c = toBookEntity__();
		
		c.book = ((Book) book).toBook_WithoutEntities__();
		c.borrowNote = bo;

		return c;
	}
	
	public BookEntity__ toBookEntity__FromService() throws Exception {
		BookEntity__ c = toBookEntity__();
		
		c.book = ((Book) book).toBook__WithEntities__();
		if (borrowNote != null) { 
			c.borrowNote = ((BorrowNote) borrowNote).toBorrowNote_();
		}

		return c;
	}
}
