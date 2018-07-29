/**
 * 
 */
package com.peter2.ejb.entity.raw;

import java.io.Serializable;

import com.peter2.Arrayable;

/**
 * @author Peter2_Weng
 *
 */
public class Reader__ implements Serializable, Arrayable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6424710106452504392L;

	public Integer id;
	public String password;	
	public String name;
	public String mailAddr;
	public Boolean activated;
	public Integer borrowQty;
	public Integer reserveQty;

	/**
	public Set<ReserveNote_> reserveNotes;
	/**/
	
	/**
	public Set<BorrowNote_> borrowNotes;
	/**/

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return new Object[]{id, password, name, mailAddr, activated, borrowQty, reserveQty};
	}
	
}
