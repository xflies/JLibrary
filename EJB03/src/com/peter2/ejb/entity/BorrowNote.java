package com.peter2.ejb.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.peter2.ObjectCloneFactory;
import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.BorrowNote__;
import com.peter2.ejb.entity.raw.Reader__;

/**
 * @author Peter2_Weng
 *
 */
@Entity
@Table(name="borrow_note")
public class BorrowNote extends BorrowNote__ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8074735559839083189L;

	/**
	 * Default constructor
	 */
	public BorrowNote() {
		status = BorrowStatus.NORMAL;
		startDate = new Date();
	}
	
	/**
	 * Getters and setters
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BORROW_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="READER_ID")
	public Reader getReader() {
		return (Reader) reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	@OneToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="BE_ID")
	public BookEntity getBookEntity() {
		return (BookEntity) bookEntity;
	}
	public void setBookEntity(BookEntity bookEntity) {
		this.bookEntity = bookEntity;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	public BorrowStatus getStatus() {
		return status;
	}
	public void setStatus(BorrowStatus status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRE_DATE")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="RETURN_DATE")
	public Date getReturnDate() {
		// TODO Auto-generated method stub
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		// TODO Auto-generated method stub
		this.returnDate = returnDate;
	}

	protected BorrowNote__ toBorrowNote_() throws Exception {
		BorrowNote__ bo = (BorrowNote__) ObjectCloneFactory.getParent(this);
		bo.reader = null;
		bo.bookEntity = null;

		/**
		BorrowNote_ bo = new BorrowNote_();
		
		bo.id = id;
		bo.status = status;
		bo.startDate = startDate;
		bo.expireDate = expireDate;
		bo.returnDate = returnDate;
		/**/
		
		return bo;
	}
	
	public BorrowNote__ toBorrowNote_FromReader__(Reader__ r) throws Exception {
		BorrowNote__ bo = toBorrowNote_();

		bo.reader = r;
		bo.bookEntity = ((BookEntity) bookEntity).toBookEntity__FromBorrowNote__(bo);

		return bo;
	}
	
	public BorrowNote__ toBorrowNote__FromBookEntity__(BookEntity__ be) throws Exception {
		BorrowNote__ bo = toBorrowNote_();
		
		bo.reader = ((Reader) reader).toReader__();
		bo.bookEntity = be;
		
		return bo;
	}
	
	public BorrowNote__ toBorrowNote__FromService() throws Exception {
		BorrowNote__ bo = toBorrowNote_();
		
		bo.reader = ((Reader) reader).toReader__();
		bo.bookEntity = ((BookEntity) bookEntity).toBookEntity__FromBorrowNote__(bo);
		
		return bo;
	}
}
