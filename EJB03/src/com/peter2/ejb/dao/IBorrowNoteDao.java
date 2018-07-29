package com.peter2.ejb.dao;

import java.util.List;

import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;

public interface IBorrowNoteDao {
	public Integer createBorrowNote(BorrowNote borrowNote);       // create/update (persist) object
	public BorrowNote getBorrowNote(Integer id);         // get object by ID
	public BorrowNote findBorrowNote(Reader reader, Book book) throws Exception; 
	public void updateBorrowNote(BorrowNote borrowNote);       // update object
	public void deleteBorrowNote(BorrowNote borrowNote);       // delete object
	public List<?> listBorrowNotes();           // list all objects

}
