package com.glueball.snoop.dao;

import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathDao extends SnoopDao<DocumentPath> {

//	List<DocumentPath> getNewDocuments();
//	List<DocumentPath> getModifiedDocuments();
//	List<DocumentPath> getDeletedDocuments();
	DocumentPath findBySum(final String md5sum);

	void updateNewDocuments(final String shareName);
	void updateModifiedDocuments(final String shareName);	
	void updateDeletedDocuments(final String shareName);
}
