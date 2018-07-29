package com.peter2.ejb.service;


import java.util.List;

import com.peter2.ejb.entity.BookEntity;
import com.peter2.ejb.entity.ReserveNote;
import com.peter2.ejb.entity.raw.Reader__;

/**
 * @author Peter2_Weng
 *
 */
public interface IReaderService {
	/**
	 * Execute log-in action. Simply query the reader's ID if the name and password 
	 * matched in DB.
	 * @param readerName input reader name
	 * @param password input password
	 * @return reader ID when log in successfully
	 * @throws Exception if no results found or error occurs during the query  
	 */
	public Integer login(String readerName, String password) throws Exception;
	public ReserveNote reserveBook(Integer readerId, String isbn) throws Exception;
	public ReserveNote queryReserve(Integer readerId, Integer reserveNoteId) throws Exception;
	public ReserveNote cancelReserve(Integer readerId, Integer reserveNoteId) throws Exception;
	public Reader__ queryReader(Integer readerId) throws Exception;
	public List<?> queryBorrow(Integer readerId) throws Exception;
	public List<?> queryReserve(Integer readerId) throws Exception;
	public List<BookEntity> searchBook(String bookName) throws Exception;
	public Integer unsafeLogin(String readerName, String password) throws Exception;
	public void updateReader(Reader__ r, String newPassword) throws Exception;
}
