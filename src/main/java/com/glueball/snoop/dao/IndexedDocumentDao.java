package com.glueball.snoop.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentDao extends SnoopDao<IndexedDocument> {
	
	public IndexedDocument findBySum(String md5sum) throws DataAccessException;
	public Set<IndexedDocument> fullTextSearch(String search) throws DataAccessException;
	public void deleteData(final String id) throws DataAccessException;
	public void createTable() throws DataAccessException;
}
