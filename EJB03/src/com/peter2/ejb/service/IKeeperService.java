package com.peter2.ejb.service;

import com.peter2.ejb.BookType;
import com.peter2.ejb.entity.raw.BookEntity__;
import com.peter2.ejb.entity.raw.Book__;
import com.peter2.ejb.entity.raw.BorrowNote__;

/**
 * @author Peter2_Weng
 *
 */
public interface IKeeperService {
	public BookEntity__ addBook(Book__ newBook) throws Exception;         // return book entity
	public BookEntity__ addBook(String isbn, String name) throws Exception;         // return book entity
	public BorrowNote__ borrowBook(Integer readerId, Integer bookEntityId) throws Exception;
	public BorrowNote__ returnBook(Integer bookEntityId) throws Exception;    // return reserve head
	public BorrowNote__ takeOndeskBook(Integer readerId, Integer bookEntityId) throws Exception;
	public Book__ queryBook(String isbn) throws Exception;	// return book
	public BookEntity__ queryBookEntity(Integer bookEntityId) throws Exception;	// return book entity
	public Book__ updateBook(String isbn, String bookName, BookType bookType) throws Exception; // return book
}
