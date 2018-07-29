package com.peter2.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.peter2.ObjectCloneFactory;
import com.peter2.ejb.BookType;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;

/**
 * @author Peter2_Weng
 *
 */
@Entity
@Table(name="book")
public class Book extends Book__ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6320854180984849232L;

	/**
	 * For convenience of releasing, these fields have moved to the meta parent class Book_ 
	 * and set to be public
	 */
	/**
    private String isbn;    
	private String name;
    private BookType type;
	private Integer qty;
	private Integer borrowQty;
	private Integer ondeskQty;
	private Integer reserveQty;
	/**/
	/**/
	private Set<BookEntity> entities;
	/**/
	
	/**
	private Set<ReserveNoteImpl> reserveNotes;
	/**/

	// Default constructor
	public Book() {
		type = BookType.GENERAL;
		qty = new Integer(0);
		borrowQty = new Integer(0);
		ondeskQty = new Integer(0);
		reserveQty = new Integer(0);
		entities = new HashSet<BookEntity>();
	}
	
	public Book(Book__ b) {
		this();
		isbn = b.isbn;
		name = b.name;
		type = b.type;
		if (b.qty != null)
			qty = b.qty;
		if (b.borrowQty != null)
			borrowQty = b.borrowQty;
		if (b.ondeskQty != null)
			ondeskQty = b.ondeskQty;
		if (b.reserveQty != null)
			reserveQty = b.reserveQty;
	}
	
	// Getters and setters
	@Id
	@Column(name="ISBN")
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="TYPE")
	public BookType getType() {
		return type;
	}
	public void setType(BookType type) {
		this.type = type;
	}
	
	@Column(name="QUANTITY")
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	@Column(name="BORROW_QTY")
	public Integer getBorrowQty() {
		return borrowQty;
	}
	public void setBorrowQty(Integer borrowQty) {
		this.borrowQty = borrowQty;
	}

	@Column(name="ONDESK_QTY")
	public Integer getOndeskQty() {
		return ondeskQty;
	}
	public void setOndeskQty(Integer ondeskQty) {
		this.ondeskQty = ondeskQty;
	}

	@Column(name="RESERVE_QTY")
	public Integer getReserveQty() {
		return reserveQty;
	}
	public void setReserveQty(Integer reserveQty) {
		this.reserveQty = reserveQty;
	}
	
	/**/
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="book")
	public Set<BookEntity> getEntities() {
		return entities;
	}
	
	public void setEntities(Set<BookEntity> entities) {
		this.entities  = entities;
	}
	/**/

	/**
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.EAGER,mappedBy="book")
	@OrderBy("status")
	public Set<ReserveNote_> getReserveNotes() {
		return reserveNotes;
	}

	public void setReserveNotes(Set<ReserveNote_> reserveNotes) {
		this.reserveNotes = reserveNotes;
	}
	/**/
	
	@Transient
	public boolean isReservable() {
		return qty <= borrowQty + ondeskQty;
	}
	
	/**
	 * 
	 */
	public Book__ toBook_WithoutEntities__() throws Exception {
		// TODO Auto-generated method stub
		Book__ b = (Book__) ObjectCloneFactory.getParent(this);
		b.setEntities__(null);
		/**
		Book_ b = new Book_();

		b.isbn = isbn;
		b.name = name;
		b.type = type;
		b.qty = qty;
		b.borrowQty = borrowQty;
		b.ondeskQty = ondeskQty;
		b.reserveQty = reserveQty;
		/**/
		
		return b;
	}

	public Book__ toBook__WithEntities__() throws Exception {
		// TODO Auto-generated method stub
		if (entities == null) {
			throw new Exception("Book has no entities: ISBN "+isbn);
		}
			
		Book__ b = toBook_WithoutEntities__();

		Set<BookEntity__> entities__ = new HashSet<BookEntity__>();
		for (BookEntity__ be : entities) {
			entities__.add(((BookEntity) be).toBookEntity__FromBook__(b));
		}
		b.setEntities__(entities__);
		
		return b;
	}

}
