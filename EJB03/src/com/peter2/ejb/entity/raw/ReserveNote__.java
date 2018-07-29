/**
 * 
 */
package com.peter2.ejb.entity.raw;

import java.io.Serializable;
import java.util.Date;

import com.peter2.Arrayable;
import com.peter2.ejb.ReserveStatus;

/**
 * @author Peter2_Weng
 *
 */
public class ReserveNote__ implements Serializable, Arrayable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2718983414245445769L;

	public Integer id;
	public Book__ book;
	public Reader__ reader;
	public ReserveStatus status;
	public Date reserveDate;
	public Date expireDate;
	
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return new Object[]{ id, book.isbn, reader.id, status, reserveDate, expireDate };
	}
}
