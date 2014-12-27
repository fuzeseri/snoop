package com.glueball.snoop.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathDao {
	
	//List<DocumentPath> haveToIndex() throws DataAccessException;
	void updateDocumentPath(List<DocumentPath> docs) throws DataAccessException;
	void updateNewDocuments() throws DataAccessException;
	void updateModifiedDocumetns() throws DataAccessException;
	void updateDeletedDocuments() throws DataAccessException;
}
