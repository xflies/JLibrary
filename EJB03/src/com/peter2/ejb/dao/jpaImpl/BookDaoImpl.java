/**
 * 
 */
package com.peter2.ejb.dao.jpaImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.BookType;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao.IBookDao;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.Book_;
import com.peter2.ejb.entity.ReserveNote;

/**
 * @author Peter2ImplWeng
 *
 */
public class BookDaoImpl implements IBookDao {

	@PersistenceContext(unitName = "Library-unit")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void createBook(Book book) {           // Persist object using Template
		// TODO Auto-generated method stub
		entityManager.persist(book);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Book getBook(String isbn) {
		// TODO Auto-generated method stub
		return entityManager.find(Book.class, isbn);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateBook(Book book) {
		// TODO Auto-generated method stub
		entityManager.merge(book);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteBook(Book book) {
		// TODO Auto-generated method stub
		book = entityManager.merge(book);
		entityManager.remove(book);
	}

	@Override
	public List<?> listBooks() {
		                           // Query all objects using HibernateTemplate.find
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select b from Book b ", Book.class);
		
		return query.getResultList();
	}

	@Override
	public List<?> getReserveNotes(Book book, ReserveStatus status) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select re from ReserveNote re inner join re.book b where b.isbn=? and re.status=? ", ReserveNote.class);

		query.setParameter(0, ((Book) book).getIsbn());
		query.setParameter(1, status.toString());
		
		return query.getResultList();
	}

	@Override
	/**
	 * Using Criteria API and metamodels to search book
	 */
	public List<Book> search(String bookName) {
		// TODO Auto-generated method stub
		/**/
		// 
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> book = cq.from(Book.class);
		
		cq.select(book);

		cq.where(cb.like(book.get(Book_.name), "%"+bookName+"%"));
		
		TypedQuery<Book> q = entityManager.createQuery(cq);
		List<Book> allBooks = q.getResultList();

		/**/		
		return allBooks;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Book updateBookEntity(String isbn, String bookName, BookType bookType) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Book> cu = cb.createCriteriaUpdate(Book.class);
		Root<Book> book = cu.from(Book.class);

		cu.set(book.get(Book_.name), bookName);
		cu.set(book.get(Book_.type), bookType);
		
		Predicate condition = cb.equal(book.get(Book_.isbn), isbn);
		cu.where(condition);
		
		entityManager.createQuery(cu).executeUpdate();
		
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		cq.from(Book.class);
		cq.where(condition);
		TypedQuery<Book> q = entityManager.createQuery(cq);

		/**/		
		return q.getSingleResult();
	}

}
