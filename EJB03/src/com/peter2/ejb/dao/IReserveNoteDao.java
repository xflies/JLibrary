package com.peter2.ejb.dao;

import java.util.List;

import com.peter2.ejb.ReserveStatus;
import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.entity.ReserveNote;

public interface IReserveNoteDao {
	public ReserveNote createReserveNote(ReserveNote reserveNote);       // create object
	public ReserveNote createReserveNote(ReserveNote reserveNote, Reader reader, Book book);
	public ReserveNote getReserveNote(Integer id);         // get object by ID
	
	public ReserveNote getReserveNote(Integer reserveNoteId, Integer readerId) throws Exception;

	/**
	 * Check if the reader reserves this book. 
	 * Throw Exception if more than one non-closed reserve notes have been found.
	 * @param reader
	 * @param book
	 * @param status
	 * @return the reserve note or null if not found
	 * @throws Exception
	 */
	public ReserveNote findReserveNote(Reader reader, Book book, ReserveStatus status) throws Exception;
	
	/**
	 * Find the head from the book's reserve queue. Use when returning book.
	 * @param book
	 * @return the reserve note or null if not found
	 */
	public ReserveNote findReserveHead(Book book);       // find reserve head
	public void updateReserveNote(ReserveNote reserveNote);       // update object
	public void deleteReserveNote(Integer id) throws Exception;       // delete object
	public List<?> listReserveNotes();           // list all objects
}
