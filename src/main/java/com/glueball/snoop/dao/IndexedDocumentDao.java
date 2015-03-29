package com.glueball.snoop.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentDao {

	void deleteByIds(List<String> ids) throws DataAccessException;
	List<IndexedDocument> haveToIndex() throws DataAccessException;
	void updateState(List<IndexedDocument> idocList) throws DataAccessException;
	void unLockUpdateState(List<IndexedDocument> idocList) throws DataAccessException;
}
