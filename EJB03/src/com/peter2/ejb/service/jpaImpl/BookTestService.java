/**
 * 
 */
package com.peter2.ejb.service.jpaImpl;

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

import com.peter2.ejb.dao.IBookDao;
import com.peter2.ejb.dao.IBookEntityDao;
import com.peter2.ejb.dao.IBorrowNoteDao;
import com.peter2.ejb.dao.IReaderDao;
import com.peter2.ejb.dao.IReserveNoteDao;
import com.peter2.ejb.dao2.IReaderDao2;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.ReserveNote__;
import com.peter2.ejb.service.IBookTestService;

/**
 * @author Peter2_Weng
 *
 */
//@Stateful(name="SuperUserService")
@Stateless(name="SuperUserService")
@Remote(IBookTestService.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class BookTestService implements IBookTestService {

	/**/
	//@PersistenceContext(unitName = "Library-unit",type=PersistenceContextType.EXTENDED)
	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;
	/**/

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
	
	@Autowired
	private IReaderDao2 readerDao2;
	
	@Override
	public void test() {
	}
	
	/* (non-Javadoc)
	 * @see com.peter2.ejb.serviceImpl.IBookTestService#createBook(com.peter2.ejb.entity.Book)
	 */
	@Override
	public void createBook(Book__ book) {
		// TODO Auto-generated method stub
		bookDao.createBook((Book) book);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.serviceImpl.IBookTestService#getBook(com.peter2.ejb.entity.Book)
	 */
	@Override
	public String getBook(String isbn) {
		// TODO Auto-generated method stub
		Book book = bookDao.getBook(isbn);
		String s;
		if (book == null) {
			s = "Book not found!";
		}
		else {
			s = "ISBN: " + book.getIsbn() + " Name: " + book.getName();
		}
		
		return s;
	}

	@Override
	public BookEntity__ getBE(Integer id) {
		// TODO Auto-generated method stub
		BookEntity__ be = bookEntityDao.getBookEntity(id);
		
		return be;
	}

	@Override
	public List<?> listReaders() {
		// TODO Auto-generated method stub
		return readerDao.listReaders();
	}

	@Override
	public List<?> listBookEntities() {
		// TODO Auto-generated method stub
		return bookEntityDao.listBookEntity();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<?> listBooks() {
		// TODO Auto-generated method stub
		/**
		List<IBook> list = bookDao.listBooks();
		for (Book_ book : list) {
			System.out.println("" + book.getIsbn());
			for (BookEntity_ be: ((BookImpl)book).getEntities()) {
				System.out.println(" "+be.getId()+" "+be.getLocation());
			}
		}
		return list;
		/**/
		return bookDao.listBooks();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteReserve(Integer id) throws Exception {
		// TODO Auto-generated method stub
		ReserveNote reserve = reserveNoteDao.getReserveNote(id);
		Book book = (Book) reserve.getBook();
		Reader reader = (Reader) reserve.getReader();
				
		book.setReserveQty(book.getReserveQty()-1);
		reader.setReserveQty(reader.getReserveQty()-1);
		/**/
		bookDao.updateBook(book);
		readerDao.updateReader(reader);
		reserveNoteDao.deleteReserveNote(id);
		/**/
	}

	@Override
	public ReserveNote__ getReserveNote(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(ReserveNote.class, id);
	}

}
