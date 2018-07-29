/**
 * 
 */
package com.peter2.ejb.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.peter2.ejb.BookType;

/**
 * @author Peter2_Weng
 *
 */
@StaticMetamodel(Book.class)
public class Book_ {
	  public static volatile SingularAttribute<Book, String> isbn;
	  public static volatile SingularAttribute<Book, String> name;
	  public static volatile SingularAttribute<Book, BookType> type;
	  public static volatile SingularAttribute<Book, Integer> qty;
	  public static volatile SingularAttribute<Book, Integer> borrowQty;
	  public static volatile SingularAttribute<Book, Integer> ondeskQty;
	  public static volatile SingularAttribute<Book, Integer> reserveQty;
	  public static volatile SetAttribute<Book, BookEntity> entities;
}
