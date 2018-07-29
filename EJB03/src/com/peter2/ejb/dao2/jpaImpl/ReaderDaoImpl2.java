/**
 * 
 */
package com.peter2.ejb.dao2.jpaImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.dao2.AbstractDao;
import com.peter2.ejb.dao2.IReaderDao2;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.entity.raw.Reader__;
import com.peter2.ejb.exception.LoginFailedException;

/**
 * @author Peter2_Weng
 *
 */
public class ReaderDaoImpl2 extends AbstractDao<Reader, Integer> implements IReaderDao2 {
	
	@PersistenceContext(unitName = "Library-unit")
    private EntityManager em;
	
	public ReaderDaoImpl2() {
		super(Reader.class);
	}
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public Integer findReaderId(String readerName, String password) throws Exception  {
		// TODO Auto-generated method stub
		String jpql = " select r.id from Reader r where r.name=? and r.password=? ";
		Query query = em.createQuery(jpql);
		query.setParameter(1, readerName);
		query.setParameter(2, password);
		
		Integer readerId = null;
		
		try {
			readerId = (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			throw new LoginFailedException();
		}
		
		return readerId;
	}

	@Override
	public List<?> getReserveNotes(Reader reader, ReserveStatus status) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(" select re from ReserveNote re inner join re.reader r where r.id=? and re.status=? ", ReserveNote.class);

		query.setParameter(1, reader.id);
		query.setParameter(2, status);
		
		return query.getResultList();
	}

	@Override
	public List<?> getBorrowNotes(Reader reader, BorrowStatus status) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(" select bo from BorrowNote bo inner join bo.reader r where r.id=? and bo.status=? ", BorrowNote.class);

		query.setParameter(1, reader.id);
		query.setParameter(2, status);
		
		return query.getResultList();
	}

	@Override
	public List<?> listReader() throws Exception {
		// TODO Auto-generated method stub
		Query query = em.createQuery(" select r from Reader r ", Reader.class);
		
		return query.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateReader(Reader__ r, String newPassword)
			throws Exception {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Reader> cu = cb.createCriteriaUpdate(Reader.class);
		Root<Reader> reader = cu.from(Reader.class);

		cu.set(reader.get("mailAddr"), r.mailAddr);
		if (newPassword != null && !"".equals(newPassword)) {
			cu.set(reader.get("password"), newPassword);
		}
		
		Predicate condition1 = cb.equal(reader.get("id"), r.id);
		Predicate condition2 = cb.equal(reader.get("password"), r.password);
		cu.where(cb.and(condition1, condition2));
		
		em.createQuery(cu).executeUpdate();
		
	}

}
