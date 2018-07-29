/**
 * 
 */
package com.peter2.ejb.dao.jpaImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao.IReserveNoteDao;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;

/**
 * @author Peter2ImplWeng
 *
 */
public class ReserveNoteDaoImpl implements IReserveNoteDao {

	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReserveDao#createReserve(com.peter2.ejb.entity.Reserve)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ReserveNote createReserveNote(ReserveNote reserveNote) {
		// TODO Auto-generated method stub
		entityManager.persist(reserveNote);
		
		return reserveNote;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReserveDao#getReserve(java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ReserveNote getReserveNote(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(ReserveNote.class, id);
	}

	@Override
	public ReserveNote getReserveNote(Integer reserveNoteId, Integer readerId) throws Exception {
		// TODO Auto-generated method stub
		String jpql = " select re from ReserveNote re inner join re.reader r where re.id=:reserveNoteId and r.id=:readerId ";

		Query query = entityManager.createQuery(jpql, ReserveNote.class);
		query.setParameter("reserveNoteId", reserveNoteId);
		query.setParameter("readerId", readerId);

		ReserveNote reserveNote = (ReserveNote) query.getSingleResult();
		
		return reserveNote;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReserveDao#updateReserve(com.peter2.ejb.entity.Reserve)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateReserveNote(ReserveNote reserveNote) {
		// TODO Auto-generated method stub
		entityManager.merge(reserveNote);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReserveDao#deleteReserve(com.peter2.ejb.entity.Reserve)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteReserveNote(Integer id) throws Exception {
		// TODO Auto-generated method stub
		ReserveNote reserveNote = entityManager.find(ReserveNote.class, id);
		if (reserveNote == null) {
			throw new Exception("Invalid reserve note: #"+id);
		}
		entityManager.remove(reserveNote);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReserveDao#listReserves()
	 */
	@Override
	public List<?> listReserveNotes() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select re from ReserveNote re ", BorrowNote.class);
		
		return query.getResultList();
	}

	@Override
	public ReserveNote findReserveNote(Reader reader, Book book, ReserveStatus status) throws Exception {
		// TODO Auto-generated method stub
		String jpql = " select re from ReserveNote re inner join re.book b inner join re.reader r where re.status = ?1 and b.isbn = ?2 and r.id = ?3 ";
		
		Query query = entityManager.createQuery(jpql, ReserveNote.class);
		query.setParameter(1, status);
		query.setParameter(2, book.isbn);
		query.setParameter(3, reader.id);
		List<?> list = query.getResultList();
		/**
		for (Reserve reserve : list) {
			System.out.println(" " + reserve.getId() + " " + reserve.getBook().getIsbn());
		}
		/**/
		if (list.size() > 1) {
			throw new Exception("More than one reserves of the same reader for this book: ISBN "+book.isbn);
		}
		return list.isEmpty() ? null : (ReserveNote) list.get(0);
	}

	@Override
	public ReserveNote findReserveHead(Book book) {
		// TODO Auto-generated method stub
		String jpql = "select re from ReserveNote re inner join re.book b where re.status = :status and b.isbn = :isbn order by re.reserveDate";

		Query query = entityManager.createQuery(jpql);
		query.setParameter("status", ReserveStatus.WAITING);
		query.setParameter("isbn", book.isbn);
		List<?> list = query.getResultList();
		
		if (list.size() < 1) {
			return null;
		}
		else {
			return (ReserveNote) list.get(0);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ReserveNote createReserveNote(ReserveNote reserveNote, Reader reader, Book book) {
		// TODO Auto-generated method stub
		entityManager.persist(reserveNote);
		entityManager.merge(reader);
		entityManager.merge(book);
		
		return reserveNote;
	}

}
