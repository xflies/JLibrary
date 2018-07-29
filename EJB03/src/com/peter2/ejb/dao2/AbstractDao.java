/**
 * 
 */
package com.peter2.ejb.dao2;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * By courtesy of http://stackoverflow.com/questions/17449838/jpa-with-ejb-with-separated-dao-and-service-layers
 * @author Peter2_Weng
 *
 */
public abstract class AbstractDao<E extends Serializable, PK extends Serializable> implements IDataAccessObject<E, PK> {

	private final transient Class<E> entityClass;

	public AbstractDao(final Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public E find(final PK id) {
        final EntityManager entityManager = getEntityManager();
        return (E) entityManager.find(entityClass, id);
    }

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void create(final E entity) throws Exception {
        final EntityManager entityManager = getEntityManager();
        entityManager.persist(entity);
    }

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public E update(E entity) throws Exception {
		final EntityManager entityManager = getEntityManager();
		return entityManager.merge(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void delete(final E entity) throws Exception {
		final EntityManager entityManager = getEntityManager();
		entityManager.remove(entity);
	}
}
