package com.glueball.snoop.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface IndexedDocumentDao {

	List<String> getDeletedDocIds() throws DataAccessException;
	List<String> getModifiedDocIds() throws DataAccessException;
	void deleteByIds(List<String> ids) throws DataAccessException;
}
