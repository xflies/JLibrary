/**
 * 
 */
package com.peter2.ejb.dao2;

import java.util.List;

import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.raw.Reader__;


/**
 * @author Peter2_Weng
 *
 */
public interface IReaderDao2 extends IDataAccessObject<Reader, Integer> {
	/**
	 * Find reader ID by the reader name and password. Used in log-in action.
	 * This method may have "Injection" vulnerability in the query using JPQL.
	 * @param readerName
	 * @param password
	 * @return reader ID if found
	 * @throws Exception thrown by javax.persistence.Query.getSingleResult()
	 */
	public Integer findReaderId(String readerName, String password) throws Exception;
	
	public List<?> getReserveNotes(Reader reader, ReserveStatus status);
	
	public List<?> getBorrowNotes(Reader reader, BorrowStatus status);
	
	public void updateReader(Reader__ r, String newPassword) throws Exception;
	
	/**
	 * List all readers. This method has been deprecated because it exposes too much 
	 * sensitive information and deoesn't have any authentication
	 * @return list of all readers
	 * @throws Exception
	 */
	@Deprecated
	List<?> listReader() throws Exception;
}
