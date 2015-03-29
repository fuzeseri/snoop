package com.glueball.snoop.dao;

import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentDao extends SnoopDao<IndexedDocument> {

	List<IndexedDocument> findByShareName(final String share);
	void deleteByIds(final List<String> ids);
	long lockDocuments(final long docNum);
	List<IndexedDocument> getByLock(final long lock);
	void updateState(final List<IndexedDocument> idocList);
	void unLockUpdateState(final List<IndexedDocument> idocList);

//	void deleteByIds(List<String> ids) throws DataAccessException;
//	List<IndexedDocument> haveToIndex() throws DataAccessException;
//	void updateState(List<IndexedDocument> idocList) throws DataAccessException;
//	void unLockUpdateState(List<IndexedDocument> idocList) throws DataAccessException;
}