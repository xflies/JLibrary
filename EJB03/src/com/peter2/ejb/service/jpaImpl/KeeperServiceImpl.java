/**
 * 
 */
package com.peter2.ejb.service.jpaImpl;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.peter2.ejb.BookEntityLocation;
import com.peter2.ejb.BookType;
import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao.IBookDao;
import com.peter2.ejb.dao.IBookEntityDao;
import com.peter2.ejb.dao.IBorrowNoteDao;
import com.peter2.ejb.dao.IReaderDao;
import com.peter2.ejb.dao.IReserveNoteDao;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BookEntity;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.BorrowNote__;
import com.peter2.ejb.exception.BlankBookNameException;
import com.peter2.ejb.exception.DuplicatedBorrowingException;
import com.peter2.ejb.exception.InvalidBookEntityException;
import com.peter2.ejb.exception.InvalidReaderException;
import com.peter2.ejb.exception.ReserveMismatchException;
import com.peter2.ejb.exception.ReturnUnborrowedBookException;
import com.peter2.ejb.service.IKeeperService;

/**
 * @author Peter2_Weng
 *
 */
@Stateless(name="KeeperService")
@Remote(IKeeperService.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class KeeperServiceImpl implements IKeeperService {

	@Autowired
	private IBookDao bookDao;

	@Autowired
	private IBookEntityDao bookEntityDao;
	
	@Autowired
	private IReaderDao readerDao;
	
	@Autowired
	private IBorrowNoteDao borrowNoteDao;
	
	@Autowired
	private IReserveNoteDao reserveNoteDao;
	
	@Override
	public BookEntity__ addBook(Book__ newBook) throws Exception {
		// TODO Auto-generated method stub

		/**
		 * Process the adding
		 * 
		 * 1. Check if there are the same books in the library
		 */
		Book book = bookDao.getBook(newBook.isbn);
		if (book == null) {
			if ("".equals(newBook.name)) {
				throw new BlankBookNameException(newBook.isbn);
			}
			book = new Book(newBook);
		}
		
		/**
		 * 2. Create Book Entity
		 */
		BookEntity bookEntity = new BookEntity();
		bookEntity.setBook(book);

		/**
		 * 3. Update/create Book
		 */
		book.setQty(book.getQty()+1);
		book.getEntities().add(bookEntity);
		
		/**
		 * The following line failed because "book" is detached even if 
		 * I have added transaction settings for bookDao.getBook().
		 */
		// bookDao.createBook(book);

		/**/
		if (book.getQty() == 1) {
			this.bookDao.createBook(book);
		}
		else {
			this.bookDao.updateBook(book);
		}
		/**/
		return bookEntity.toBookEntity__FromService();
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.service.IKeeperService#addBook(java.lang.String, java.lang.String)
	 */
	@Override
	public BookEntity__ addBook(String isbn, String name) throws Exception {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setIsbn(isbn);
		book.setName(name);
		
		return this.addBook(book);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.service.IKeeperService#borrowBook(com.peter2.ejb.entity.BookEntity)
	 */
	@Override
	public BorrowNote__ borrowBook(Integer readerId, Integer bookEntityId) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate this borrowing associated with Reader and Book Entity
		 */
		// Validate the reader
		Reader reader = readerDao.getReader(readerId);
		if (reader == null) {
			throw new InvalidReaderException(readerId);
		}
		// Validate this book entity
		BookEntity bookEntity = bookEntityDao.getBookEntity(bookEntityId);
		if (bookEntity == null) {
			throw new InvalidBookEntityException(bookEntityId);
		}
		// Check whether this book entity is borrowed
		if (bookEntity.isBorrowed()) {
			throw new DuplicatedBorrowingException(bookEntityId);
		}
		// Check the borrowing quota of the reader
		/**
		if (reader.getQty() > 10) {
			throw new Exception();
		} 
		/**/
		
		BorrowNote bo = this.borrowBook(reader, bookEntity);
		
		BorrowNote__ returnBorrowNote = bo.toBorrowNote__FromService();
		
		if (returnBorrowNote == null) throw new NullPointerException("returnBorrowNote");
		
		return returnBorrowNote;
	}
	
	protected BorrowNote borrowBook(Reader reader, BookEntity bookEntity) {
		/**
		 * Process a borrowing
		 * 
		 * 1. Create Borrowing record
		 */
		BorrowNote borrowNote = new BorrowNote();
		borrowNote.setReader(reader);
		borrowNote.setBookEntity(bookEntity);
		borrowNote.setStartDate(new Date());
		//borrowNote.setExpireDate(new Date());
		
		/**
		 * 2. Update Book Entity
		 */
		bookEntity.setBorrowNote(borrowNote);
		bookEntity.setLocation(BookEntityLocation.BORROW);
		
		/**
		 * 3. Update Book
		 */
		Book book = (Book) bookEntity.getBook();
		book.setBorrowQty(book.getBorrowQty()+1);
		
		/**
		 * 4. Update Reader
		 */
		reader.setBorrowQty(reader.getBorrowQty()+1);
		/**/
		for (BookEntity be : book.getEntities()) {
			if (be.isBorrowed() && be.getBorrowNote().getReader().getId() == reader.getId()) {
				be.getBorrowNote().setReader(reader);
			}
		}
		/**/
		
		readerDao.updateReader(reader);
		bookDao.updateBook(book);
						
		return borrowNote;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.service.IKeeperService#returnBook(com.peter2.ejb.entity.BookEntity)
	 */
	@Override
	public BorrowNote__ returnBook(Integer bookEntityId) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate this returning associated with Book Entity
		 */
		// Validate this book entity
		BookEntity bookEntity = bookEntityDao.getBookEntity(bookEntityId);
		if (bookEntity == null) {
			throw new InvalidBookEntityException(bookEntityId);
		}
		// Check whether this book entity is borrowed
		if (! bookEntity.isBorrowed()) {
			throw new ReturnUnborrowedBookException(bookEntityId);
		}
		
		/**
		 * Process a returning
		 * 
		 * 1. Update the Borrowing record
		 */
		BorrowNote borrowNote = (BorrowNote) bookEntity.getBorrowNote();
		borrowNote.setStatus(BorrowStatus.CLOSED);
		borrowNote.setReturnDate(new Date());
		
		/**
		 * 2. Update Book Entity
		 */
		bookEntity.setBorrowNote(null);
		
		/**
		 * 3. Update Book
		 */
		Book book = (Book) bookEntity.getBook();
		book.setBorrowQty(book.getBorrowQty()-1);
		
		/**
		 * 4. Update Reader
		 */
		Reader reader = (Reader) borrowNote.getReader();
		reader.setBorrowQty(reader.getBorrowQty()-1);
		/**/
		for (BookEntity be : book.getEntities()) {
			if (be.isBorrowed() && be.getBorrowNote().getReader().getId() == reader.getId()) {
				be.getBorrowNote().setReader(reader);
			}
		}
		/**/
		
		/**
		 * 5. Decide to keep the book on desk or return it to the shelf
		 * (By the reserve status (quantity) and the on-desk quantity)
		 */
		ReserveNote reserveHead = null;
		if (book.getOndeskQty() < book.getReserveQty()) {
			// Keep on desk
			bookEntity.setLocation(BookEntityLocation.ONDESK);
			book.setOndeskQty(book.getOndeskQty()+1);
			// Message the reader who is head of reserve queue to take this book
			reserveHead = (ReserveNote) reserveNoteDao.findReserveHead(book);
			if (reserveHead == null) {
				throw new ReserveMismatchException(book.getIsbn());
			}
			/**
			 * Set book to reserveHead is critical because the original reserveHead.book 
			 * is just extracted from the database and not the book updated by the above code.
			 */
			reserveHead.setBook(book);
			reserveHead.setStatus(ReserveStatus.ONDESK);
			// Message below . . .
			/*
			 * ****************************************************************************************
			 * ****************************************************************************************
			 * ****************************************************************************************
			 * ****************************************************************************************
			 * ****************************************************************************************
			 * ****************************************************************************************
			 */
		}
		else {
			// Return to shelf
			bookEntity.setLocation(BookEntityLocation.SHELF);
		}

		readerDao.updateReader(reader);
		bookDao.updateBook(book);
		
		if (reserveHead == null) {
			//return null;
		}
		else {
			reserveNoteDao.updateReserveNote(reserveHead);
		}
		
		BorrowNote__ returnBorrowNote = borrowNote.toBorrowNote__FromService();

		if (returnBorrowNote == null) throw new NullPointerException("returnBorrowNote");
		
		return returnBorrowNote;
	}

	@Override
	public BorrowNote__ takeOndeskBook(Integer readerId, Integer bookEntityId)
			throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Validate the taking associated with Reader, Book Entity and Book 
		 */
		// Validate this Reader
		Reader reader = readerDao.getReader(readerId);
		if (reader == null) {
			throw new InvalidReaderException(readerId);
		}
		// Validate this Book Entity
		BookEntity bookEntity = bookEntityDao.getBookEntity(bookEntityId);
		if (bookEntity == null) {
			throw new InvalidBookEntityException(bookEntityId);
		}
		// Check if the reader reserves this book
		Book book = (Book) bookEntity.getBook();
		ReserveNote reserveNote = reserveNoteDao.findReserveNote(reader, book, ReserveStatus.ONDESK);
		if (reserveNote == null) {
			throw new ReserveMismatchException(book.getIsbn());
		}
		
		/**
		 * Process the taking
		 * 
		 * 1. Update Reverse record
		 */
		reserveNote.setStatus(ReserveStatus.CLOSED);
		
		/**
		 * 2. Update Book
		 */
		book.setReserveQty(book.getReserveQty()-1);
		book.setOndeskQty(book.getOndeskQty()-1);
		/**
		 * Set book to reserveNote is critical because the original reserveNote.book 
		 * is just extracted from the database and not the book updated by the above code.
		 */
		reserveNote.setBook(book);

		/**
		 * 3. Update Reader
		 */
		reader = (Reader) reserveNote.getReader();
		reader.setReserveQty(reader.getReserveQty()-1);
		/**/
		for (BookEntity be : book.getEntities()) {
			if (be.isBorrowed() && be.getBorrowNote().getReader().getId() == reader.getId()) {
				be.getBorrowNote().setReader(reader);
			}
		}
		/**/
		
		
		readerDao.updateReader(reader);
		reserveNoteDao.updateReserveNote(reserveNote);

		/**
		 * 4. BORROW
		 */
		BorrowNote borrowNote = this.borrowBook(reader, bookEntity); 
		
		BorrowNote__ returnBorrowNote = borrowNote.toBorrowNote__FromService();

		if (returnBorrowNote == null) throw new NullPointerException("returnBorrowNote");
		
		return returnBorrowNote;
	}

	@Override
	public Book__ queryBook(String isbn) throws Exception {
		// TODO Auto-generated method stub
		Book book = bookDao.getBook(isbn);

		if (book == null) {
			return null;
		}
		else {
			Book__ b = book.toBook__WithEntities__();
			return b;
		}
	}

	@Override
	public BookEntity__ queryBookEntity(Integer bookEntityId) throws Exception {
		// TODO Auto-generated method stub
		BookEntity bookEntity = bookEntityDao.getBookEntity(bookEntityId);
		
		if(bookEntity == null) {
			return null;
		}
		else {
			return bookEntity.toBookEntity__FromService();
		}
	}

	@Override
	public Book__ updateBook(String isbn, String bookName, BookType bookType)
			throws Exception {
		// TODO Auto-generated method stub
		Book book = bookDao.updateBookEntity(isbn, bookName, bookType);
		
		if (book == null) {
			return null;
		}
		else {
			Book__ b = book.toBook__WithEntities__();
			return b;
		}
	}

}
