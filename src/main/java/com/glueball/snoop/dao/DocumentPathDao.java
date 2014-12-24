package com.glueball.snoop.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathDao {
	
	List<DocumentPath> haveToIndex() throws DataAccessException;
	void refreshDocumentPath(List<DocumentPath> docs) throws DataAccessException;
}
