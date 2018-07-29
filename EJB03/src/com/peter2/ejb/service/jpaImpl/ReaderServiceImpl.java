/**
 * 
 */
package com.peter2.ejb.service.jpaImpl;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.BookEntityLocation;
import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao.IBookDao;
import com.peter2.ejb.dao.IBookEntityDao;
import com.peter2.ejb.dao.IBorrowNoteDao;
import com.peter2.ejb.dao.IReaderDao;
import com.peter2.ejb.dao.IReserveNoteDao;
import com.peter2.ejb.dao2.IReaderDao2;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BookEntity;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.entity.raw.Reader__;
import com.peter2.ejb.exception.DuplicatedReservingException;
import com.peter2.ejb.exception.IneffectiveReserveNoteException;
import com.peter2.ejb.exception.InvalidBookException;
import com.peter2.ejb.exception.InvalidReaderException;
import com.peter2.ejb.exception.InvalidReserveNoteException;
import com.peter2.ejb.exception.ReserveBorrowedBook;
import com.peter2.ejb.exception.ReserveCirculatedBookException;
import com.peter2.ejb.service.IReaderService;

/**
 * @author Peter2_Weng
 *
 */
@Stateless(name="ReaderService")
@Remote(IReaderService.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ReaderServiceImpl implements IReaderService {

	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private IBookEntityDao bookEntityDao;
	
	@Autowired
	private IReaderDao2 readerDao2;
	
	@Autowired
	private IBorrowNoteDao borrowNoteDao;
	
	@Autowired
	private IReserveNoteDao reserveNoteDao;
	
	@Autowired
	private IReaderDao readerDao;
	
	@Override
	public Integer login(String readerName, String password) throws Exception {
		// TODO Auto-generated method stub
		Integer readerId = null;
		

		readerId = readerDao2.findReaderId(readerName, password);
		
		return readerId;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.service.IReaderService#reserveBook(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ReserveNote reserveBook(Integer readerId, String isbn) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate the reserve associated with Reserve note, Book and Reader
		 */
		// Validate the reader
		Reader reader = readerDao2.find(readerId);
		if (reader == null) {
			throw new InvalidReaderException(readerId);
		}
		// Validate the book
		Book book = bookDao.getBook(isbn);
		if (book == null) {
			throw new InvalidBookException(isbn);
		}
		// Check whether the book is able to be reserved
		if (book.getQty() > book.getBorrowQty() + book.getOndeskQty()) {
			throw new ReserveCirculatedBookException(isbn);
		}
		// Check if the reader already reserved this book
		if (reserveNoteDao.findReserveNote(reader, book, ReserveStatus.WAITING) != null || 
				reserveNoteDao.findReserveNote(reader, book, ReserveStatus.ONDESK) != null) {
			throw new DuplicatedReservingException(isbn);
		}
		// Check if the reader already borrowed this book
		if (borrowNoteDao.findBorrowNote(reader, book) != null) {
			throw new ReserveBorrowedBook(isbn);
		}
		
		/**
		 * Process the reserve
		 * 
		 * 1. Create Reserve record
		 */
		ReserveNote reserveNote = new ReserveNote();
		reserveNote.setBook(book);
		reserveNote.setReader(reader);
		/**
		 * Set book and reader to reserve is critical because the original reserve.book and 
		 * reserve.reader are other copies extracted from the database and not the book and  
		 * reader updated by the following codes.
		 */		
		
		/**
		 * 2. Update Book
		 */
		book.setReserveQty(book.getReserveQty()+1);
		
		/**
		 * 3. Update Reader
		 */
		reader.setReserveQty(reader.getReserveQty()+1);

		readerDao2.update(reader);
		bookDao.updateBook(book);
		reserveNoteDao.createReserveNote(reserveNote, reader, book);
		
		return reserveNote;
	}

	@Override
	public ReserveNote queryReserve(Integer readerId, Integer reserveNoteId)
			throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate the reserve associated with Reserve note and Reader
		 */
		// Validate the reader
		Reader reader = readerDao2.find(readerId);
		if (reader == null) {
			throw new InvalidReaderException(readerId);
		}
		ReserveNote reserveNote = reserveNoteDao.getReserveNote(reserveNoteId, readerId);
		if (reserveNote == null) {
			throw new InvalidReserveNoteException(reserveNoteId);
		}
		
		return reserveNote;
	}

	@Override
	public ReserveNote cancelReserve(Integer readerId, Integer reserveNoteId)
			throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate the reserve associated with Reserve note and Reader
		 */
		// Validate the reader
		Reader reader = readerDao2.find(readerId);
		if (reader == null) {
			throw new InvalidReaderException(readerId);
		}
		ReserveNote reserveNote = reserveNoteDao.getReserveNote(reserveNoteId, readerId);
		if (reserveNote == null) {
			throw new InvalidReserveNoteException(reserveNoteId);
		}
		ReserveStatus status = reserveNote.getStatus();
		if (status!=ReserveStatus.WAITING && status!=ReserveStatus.ONDESK) {
			throw new IneffectiveReserveNoteException(reserveNoteId);
		}
		
		/**
		 * Process the canceling
		 */
		reader = reserveNote.getReader();
		reader.setReserveQty(reader.getReserveQty() - 1);
		
		Book book = reserveNote.getBook();
		book.setReserveQty(book.getReserveQty() - 1);

		reserveNote.setStatus(ReserveStatus.CANCELED);
		
		reserveNoteDao.updateReserveNote(reserveNote);
		readerDao2.update(reader);
		

		/**
		 * Choose the head of reserve waiting quene and message the reader
		 */
		if (status==ReserveStatus.ONDESK) {
			ReserveNote head = reserveNoteDao.findReserveHead(book);
			if(head != null) {
				head.setStatus(ReserveStatus.ONDESK);
				reserveNoteDao.updateReserveNote(head);
				/**
				 * Message the reader
				 */
				///////////////////////////////////////////////////////////////
			}
			else {
				/**
				 * Message the keeper to return an on-desked book to shelf
				 */
				List<?> list = bookEntityDao.findBookEntityByLocation(book.getIsbn(), BookEntityLocation.ONDESK);
				BookEntity bookEntity = (BookEntity) list.get(0);
				
				/**
				 * Message the keeper . . .
				 */
				///////////////////////////////////////////////////////////////
				
				bookEntity.setLocation(BookEntityLocation.SHELF);
				
				book = bookEntity.getBook();
				book.setOndeskQty(book.getOndeskQty() - 1);
				bookDao.updateBook(book);
			}
		}
		
		
		return reserveNote;
	}

	@Override
	public Reader__ queryReader(Integer readerId) throws Exception {
		// TODO Auto-generated method stub
		Reader reader = readerDao2.find(readerId);
		
		Reader__ reader_ = reader.toReader__();
		
		return reader_;
	}

	@Override
	public List<?> queryBorrow(Integer readerId) throws Exception {
		// TODO Auto-generated method stub
		List<?> list = null;
		
		Reader reader = readerDao2.find(readerId);
		list = readerDao2.getBorrowNotes(reader, BorrowStatus.NORMAL);
		
		return list;
	}

	@Override
	public List<?> queryReserve(Integer readerId) throws Exception {
		// TODO Auto-generated method stub
		List<?> waitingList = null;
		List<?> ondeskList = null;
		
		Reader reader = readerDao2.find(readerId);
		waitingList = readerDao2.getReserveNotes(reader, ReserveStatus.WAITING);
		ondeskList = readerDao2.getReserveNotes(reader, ReserveStatus.ONDESK);
		
		List<Object> list = new LinkedList<Object>();
		
		for (Object o: waitingList) {
			list.add(o);
		}
		for (Object o: ondeskList) {
			list.add(o);
		}
		
		return list;
	}

	@Override
	public List<BookEntity> searchBook(String bookName) throws Exception {
		// TODO Auto-generated method stub
		List<BookEntity> resultList = null;
		
		resultList = bookEntityDao.search(bookName);
		
		return resultList;
	}

	@Override
	public Integer unsafeLogin(String readerName, String password) throws Exception {
		// TODO Auto-generated method stub
		Integer readerId = null;

		readerId = readerDao.findReaderId(readerName, password);
		
		return readerId;
	}

	@Override
	public void updateReader(Reader__ r, String newPassword) throws Exception {
		// TODO Auto-generated method stub
		Integer readerId = login(r.name, r.password);
		
		// "==" and "!=" can not be used for Integers!
		if (! readerId.equals(r.id)) {
			throw new Exception("Reader name and ID are mismatched: " + r.name + " & "+ readerId);
		}
		
		readerDao2.updateReader(r, newPassword);
	}

}
