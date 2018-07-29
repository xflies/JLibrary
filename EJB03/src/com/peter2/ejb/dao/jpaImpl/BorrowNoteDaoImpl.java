/**
 * 
 */
package com.peter2.ejb.dao.jpaImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.dao.IBorrowNoteDao;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;

/**
 * @author Peter2ImplWeng
 *
 */
public class BorrowNoteDaoImpl implements IBorrowNoteDao {

	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IBorrowingDao#createBorrowing(com.peter2.ejb.entity.Borrowing)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer createBorrowNote(BorrowNote borrowNote) {
		// TODO Auto-generated method stub
		entityManager.persist(borrowNote);
		
		return borrowNote.id;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IBorrowingDao#getBorrowing(java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public BorrowNote getBorrowNote(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(BorrowNote.class, id);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IBorrowingDao#updateBorrowing(com.peter2.ejb.entity.Borrowing)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateBorrowNote(BorrowNote borrowNote) {
		// TODO Auto-generated method stub
		entityManager.merge(borrowNote);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IBorrowingDao#deleteBorrowing(com.peter2.ejb.entity.Borrowing)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteBorrowNote(BorrowNote borrowNote) {
		// TODO Auto-generated method stub
		borrowNote = entityManager.merge(borrowNote);
		entityManager.remove(borrowNote);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IBorrowingDao#listBorrowings()
	 */
	@Override
	public List<?> listBorrowNotes() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select bo from BorrowNote bo ", BorrowNote.class);
		
		return query.getResultList();
	}

	@Override
	public BorrowNote findBorrowNote(Reader reader, Book book) throws Exception {
		// TODO Auto-generated method stub
		String jpql = " select bo from BorrowNote bo inner join bo.reader r on r.id=? inner join bo.bookEntity be inner join be.book b where b.isbn=? and bo.status<>?";
		Query query = entityManager.createQuery(jpql, BorrowNote.class);
		
		query.setParameter(1, reader.getId());
		query.setParameter(2, book.getIsbn());
		query.setParameter(3, BorrowStatus.CLOSED);
		
		BorrowNote borrowNote = null;
		
		try {
			borrowNote = (BorrowNote) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return borrowNote;
	}

}
