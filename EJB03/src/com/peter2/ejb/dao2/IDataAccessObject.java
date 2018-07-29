/**
 * 
 */
package com.peter2.ejb.dao2;

import java.io.Serializable;

/**
 * @author Peter2_Weng
 *
 */
public interface IDataAccessObject<E extends Serializable, PK extends Serializable> {
    public E find(final PK id) throws Exception;
	public void create(final E entity) throws Exception;
	public E update(final E entity) throws Exception;
	public void delete(final E entity) throws Exception;
}
