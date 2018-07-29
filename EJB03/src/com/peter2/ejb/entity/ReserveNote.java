package com.peter2.ejb.entity;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.peter2.ObjectCloneFactory;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.Reader__;
import com.peter2.ejb.entity.raw.ReserveNote__;

/**
 * @author Peter2_Weng
 *
 */
@Entity
@Table(name="reserve_note")
public class ReserveNote extends ReserveNote__ {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7417538729080876273L;

	/**
	 * Default constructor
	 */
	public ReserveNote() {
		status = ReserveStatus.WAITING;
		reserveDate = new Date();
	}
	
	/**
	 * Getters and setters
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RESERVE_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="ISBN")
	public Book getBook() {
		return (Book) book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="READER_ID")
	public Reader getReader() {
		return (Reader) reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	public ReserveStatus getStatus() {
		return status;
	}
	public void setStatus(ReserveStatus status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RESERVE_TIME")
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRE_DATE")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	protected ReserveNote__ toReserveNote_() throws Exception {
		ReserveNote__ re = (ReserveNote__) ObjectCloneFactory.getParent(this);
		re.book = null;
		re.reader = null;

		/**
		ReserveNote_ re = new ReserveNote_();
		
		re.id = id;
		re.status = status;
		re.reserveDate = reserveDate;
		re.expireDate = expireDate;
		/**/
		
		return re;
	}

	public ReserveNote__ toReserveNote__FromReader_(Reader__ reader) throws Exception {
		ReserveNote__ re = toReserveNote_();
		
		re.book = ((Book) this.book).toBook_WithoutEntities__();
		re.reader = reader;
		
		return re;
	}

	public ReserveNote__ toReserveNote__FromBook_(Book__ book) throws Exception {
		ReserveNote__ re = toReserveNote_();
		
		re.book = book;
		re.reader = ((Reader) this.reader).toReader__();
		
		return re;
	}
	
	public ReserveNote__ toReserveNote__FromService() throws Exception {
		ReserveNote__ re = toReserveNote_();
		
		re.book = ((Book) this.book).toBook_WithoutEntities__();
		re.reader = ((Reader) this.reader).toReader__();
		
		return re;
	}
}
