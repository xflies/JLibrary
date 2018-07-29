package com.peter2.ejb.dao;

import java.util.List;

import com.peter2.ejb.BookEntityLocation;
import com.peter2.ejb.entity.BookEntity;

/**
 * @author Peter2ImplWeng
 *
 */
public interface IBookEntityDao {
	public Integer createBookEntity(BookEntity bookEntity);
	public BookEntity getBookEntity(Integer id);
	public void updateBookEntity(BookEntity bookEntity);
	public void deleteBookEntity(BookEntity bookEntity);
	public List<?> listBookEntity();
	
	public List<?> findBookEntityByLocation(String isbn, BookEntityLocation location);
	
	public List<BookEntity> search(String bookName);
}
