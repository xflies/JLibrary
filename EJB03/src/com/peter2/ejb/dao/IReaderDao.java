package com.peter2.ejb.dao;

import java.util.List;

import com.peter2.ejb.BorrowStatus;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.entity.Reader;

@Deprecated
public interface IReaderDao {
	@Deprecated
	public Integer createReader(Reader reader);       // create object
	@Deprecated
	public Reader getReader(Integer id);         // get object by ID
	@Deprecated
	public void updateReader(Reader reader);       // update object
	@Deprecated
	public void deleteReader(Reader reader);       // delete object
	@Deprecated
	public List<?> getReserveNotes(Reader reader, ReserveStatus status);
	@Deprecated
	public List<?> getBorrowNotes(Reader reader, BorrowStatus status);
	@Deprecated
	public List<?> listReaders();           // list all objects
	@Deprecated
	public Integer findReaderId(String readerName, String password) throws Exception;
	
}
