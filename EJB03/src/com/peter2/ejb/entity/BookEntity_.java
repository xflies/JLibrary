/**
 * 
 */
package com.peter2.ejb.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.peter2.ejb.BookEntityLocation;

/**
 * @author Peter2_Weng
 *
 */
@StaticMetamodel(Book.class)
public class BookEntity_ {
	public static volatile SingularAttribute<BookEntity, Integer> id;
	public static volatile SingularAttribute<BookEntity, Book> book;
	public static volatile SingularAttribute<BookEntity, BookEntityLocation>  location;
	public static volatile SingularAttribute<BookEntity, BorrowNote>  borrowNote;
}
