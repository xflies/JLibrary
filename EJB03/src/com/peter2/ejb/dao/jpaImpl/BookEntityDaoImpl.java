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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.BookEntityLocation;
import com.peter2.ejb.dao.IBookEntityDao;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BookEntity;
import com.peter2.ejb.entity.BookEntity_;
import com.peter2.ejb.entity.Book_;

/**
 * @author Peter2ImplWeng
 *
 */
public class BookEntityDaoImpl implements IBookEntityDao {

	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer createBookEntity(BookEntity bookEntity) {
		// TODO Auto-generated method stub
		entityManager.persist(bookEntity);
		
		return bookEntity.id;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public BookEntity getBookEntity(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(BookEntity.class, id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateBookEntity(BookEntity bookEntity) {
		// TODO Auto-generated method stub 
		entityManager.merge(bookEntity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteBookEntity(BookEntity bookEntity) {
		// TODO Auto-generated method stub
		bookEntity = entityManager.merge(bookEntity);
		entityManager.remove(bookEntity);
	}

	@Override
	public List<?> listBookEntity() {
		// TODO Auto-generated method stub
		//return (List<BookEntity>) entityManager.find(" select be from BookEntity be order by be.book ");
		Query query = entityManager.createQuery(" select be from BookEntity be order by be.book ", BookEntity.class);
		
		return query.getResultList();
	}

	@Override
	public List<?> findBookEntityByLocation(String isbn,
			BookEntityLocation location) {
		// TODO Auto-generated method stub
		String jpql = " select be from BookEntity be inner join be.book b where b.isbn=:isbn and be.location=:location ";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("isbn", isbn);
		query.setParameter("location", location);
		
		List<?> list = query.getResultList();
		
		return list;
	}

	@Override
	public List<BookEntity> search(String bookName) {
		// TODO Auto-generated method stub
		/**/
		// 
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
		Root<BookEntity> bookEntity = cq.from(BookEntity.class);
		
		cq.select(bookEntity);
		
		Join<BookEntity, Book> book = bookEntity.join(BookEntity_.book);
		
		Predicate condition = "".equals(bookName) ? 
				cb.isNotNull(book.get(Book_.name)) : 
					cb.like(book.get(Book_.name), "%"+bookName+"%");
		cq.where(condition);
		
		TypedQuery<BookEntity> q = entityManager.createQuery(cq);
		List<BookEntity> allBooks = q.getResultList();

		/**/		
		return allBooks;
	}

}
