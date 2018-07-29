package com.peter2.ejb.dao;

import java.util.List;

import com.peter2.ejb.BookType;
import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.entity.Book;

/**
 * @author Peter2_Weng
 *
 */
public interface IBookDao {                  // DAO interface
	public void createBook(Book book);       // create/update (persist) object
	public Book getBook(String isbn);         // get object by ID
	public void updateBook(Book book);       // update object
	public void deleteBook(Book book);       // delete object
	public List<?> getReserveNotes(Book book, ReserveStatus status);
	public List<?> listBooks();           // list all objects
	
	public List<Book> search(String bookName);
	public Book updateBookEntity(String isbn, String bookName, BookType bookType);
}
