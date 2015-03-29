package com.glueball.snoop.bean.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.glueball.snoop.bean.IndexedDocumentBean;
import com.glueball.snoop.dao.IndexedDocumentDao;
import com.glueball.snoop.entity.IndexedDocument;

public class IndexedDocumentBeanImpl implements IndexedDocumentBean {

    private IndexedDocumentDao dao;

    @Required
    public void setDao(final IndexedDocumentDao _dao) {

	this.dao = _dao;
    }

    public void init() {

	dao.createTable();
    }

    @Override
    public void unLockUpdateState(final List<IndexedDocument> idocList) {

	dao.unLockUpdateState(idocList);
    }

    @Override
    public List<IndexedDocument> haveToIndex(final int docNum) {

	final long lock = dao.lockDocuments(docNum);
	return dao.getByLock(lock);
    }
}
