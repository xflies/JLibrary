package com.peter2.ejb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.peter2.ObjectCloneFactory;
import com.peter2.ejb.entity.raw.Reader__;

/**
 * @author Peter2_Weng
 *
 */
@Entity
@Table(name="reader")
public class Reader extends Reader__ {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4767038594047730642L;

	/**
	private Set<ReserveNoteImpl> reserveNotes;
	/**/
	
	/**/
	private Set<BorrowNote> borrowNotes;
	/**/

	// Default constructor
	public Reader() {
		activated = false;
		borrowQty = new Integer(0);
		reserveQty = borrowQty;
	}
	
	/**
	 * Getters and setters
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="READER_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="MAIL_ADDR")
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	/**/
	@Column(name="ACTIVATED")
	public Boolean getActivated() {
		return activated;
	}
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	/**/

	@Column(name="BORROW_QTY")
	public Integer getBorrowQty() {
		return borrowQty;
	}
	public void setBorrowQty(Integer borrowQty) {
		this.borrowQty = borrowQty;
	}

	@Column(name="RESERVE_QTY")
	public Integer getReserveQty() {
		return reserveQty;
	}
	public void setReserveQty(Integer reserveQty) {
		this.reserveQty = reserveQty;
	}
	
	/**
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.LAZY,mappedBy="reader")
	public Set<ReserveNote_> getReserveNotes() {
		return reserveNotes;
	}
	public void setReserveNotes(Set<ReserveNote_> reserveNotes) {
		this.reserveNotes = reserveNotes;
	}
	/**/
	
	/**/
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.LAZY,mappedBy="reader")
	public Set<BorrowNote> getBorrowNotes() {
		return borrowNotes;
	}
	public void setBorrowNotes(Set<BorrowNote> borrowNotes) {
		this.borrowNotes = borrowNotes;
	}
	/**/
	
	/**
	 * 
	 * @return
	 */
	public Reader__ toReader__() throws Exception {
		Reader__ r = (Reader__) ObjectCloneFactory.getParent(this);
		
		// Hide sensitive information
		r.password = "";

		/**
		Reader_ r = new Reader_();
		
		r.id = id;
		r.password = password;	
		r.name = name;
		r.mailAddr = mailAddr;
		r.activated = activated;
		r.borrowQty = borrowQty;
		r.reserveQty = reserveQty;
		/**/
		
		/**
		r.borrowNotes = borrowNotes;
		/**/
		
		/**
		r.reserveNotes = reserveNote;
		/**/
		
		return r;
	}
}
