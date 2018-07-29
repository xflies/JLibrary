package com.peter2.ejb.service;

import java.util.List;

import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.ReserveNote__;

/**
 * @author Peter2_Weng
 *
 */
public interface IBookTestService {
	public void createBook(Book__ book);
	public String getBook(String isbn);
	public BookEntity__ getBE(Integer id);
	public List<?> listBooks();
	public List<?> listBookEntities();
	public List<?> listReaders();
	public ReserveNote__ getReserveNote(Integer id);
	public void deleteReserve(Integer id) throws Exception;
	void test();
}
