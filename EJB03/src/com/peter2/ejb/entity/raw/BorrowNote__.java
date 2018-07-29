/**
 * 
 */
package com.peter2.ejb.entity.raw;

import java.io.Serializable;
import java.util.Date;

import com.peter2.Arrayable;
import com.peter2.ejb.BorrowStatus;

/**
 * @author Peter2_Weng
 *
 */
public class BorrowNote__ implements Serializable, Arrayable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2404897409243470785L;
	
	public Integer id;
	public Reader__ reader;
	public BookEntity__ bookEntity;
	public BorrowStatus status;
	public Date startDate;
	public Date expireDate;
	public Date returnDate;

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return new Object[]{ id, reader.id, bookEntity.id, status, startDate, expireDate, returnDate };
	}
}
