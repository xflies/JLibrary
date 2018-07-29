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

import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao.IReaderDao;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.exception.InvalidReaderException;

/**
 * @author Peter2ImplWeng
 *
 */
public class ReaderDaoImpl implements IReaderDao {
	
	@PersistenceContext(unitName = "Library-unit")
	EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReaderDao#createReader(com.peter2.ejb.entity.Reader)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer createReader(Reader reader) {
		// TODO Auto-generated method stub
		entityManager.persist(reader);
		
		return reader.id;
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReaderDao#getReader(java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Reader getReader(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(Reader.class, id);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReaderDao#updateReader(com.peter2.ejb.entity.Reader)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateReader(Reader reader) {
		// TODO Auto-generated method stub
		entityManager.merge(reader);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReaderDao#deleteReader(com.peter2.ejb.entity.Reader)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteReader(Reader reader) {
		// TODO Auto-generated method stub
		reader = entityManager.merge(reader);
		entityManager.remove(reader);
	}

	/* (non-Javadoc)
	 * @see com.peter2.ejb.dao.IReaderDao#listReaders()
	 */
	@Override
	public List<?> listReaders() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select r from Reader r ", Reader.class);
		
		return query.getResultList();
	}

	@Override
	public List<?> getReserveNotes(Reader reader, ReserveStatus status) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select re from ReserveNote re inner join re.reader r where r.id=? and re.status=? ", ReserveNote.class);

		query.setParameter(0, reader.id);
		query.setParameter(1, status.toString());
		
		return query.getResultList();
	}

	@Override
	public List<?> getBorrowNotes(Reader reader, BorrowStatus status) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(" select bo from BorrowNote bo inner join bo.reader r where r.id=? and bo.status=? ", BorrowNote.class);

		query.setParameter(0, reader.id);
		query.setParameter(1, status.toString());
		
		return query.getResultList();
	}

	@Override
	public Integer findReaderId(String readerName, String password)
			throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String jpql = " select r from Reader r where r.name='"+readerName+"' and r.password='"+password+"' ";
		System.err.println(jpql);
		Query query = entityManager.createQuery(jpql);
		
		List<?> list = query.getResultList();
		
		if (list.size()==0) {
			throw new InvalidReaderException(null);
		}
		
		Integer readerId = null;
				
		readerId = ((Reader) list.get(0)).getId();
		
		return readerId;
	}

}
