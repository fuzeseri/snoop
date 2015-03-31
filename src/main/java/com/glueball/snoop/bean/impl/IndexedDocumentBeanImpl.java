package com.glueball.snoop.bean.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.glueball.snoop.bean.IndexedDocumentBean;
import com.glueball.snoop.dao.IndexedDocumentDao;
import com.glueball.snoop.entity.IndexedDocument;

/**
 * Implementation of the IndexedDocumentBean.
 *
 * @author karesz
 */
public class IndexedDocumentBeanImpl implements IndexedDocumentBean {

    /**
     * Spring data access object to access the IndexedDocuemnts in the
     * relational database.
     */
    private IndexedDocumentDao dao;

    /**
     * Setter method of the dao field.
     *
     * @param pDao
     *            the IndexedDocumentDao instance.
     */
    @Required
    public final void setDao(final IndexedDocumentDao pDao) {

        this.dao = pDao;
    }

    /**
     * Initialization method.
     */
    public final void init() {

        dao.createTable();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.glueball.snoop.bean.IndexedDocumentBean#unLockUpdateState(java.util
     * .List)
     */
    @Override
    public final void unLockUpdateState(final List<IndexedDocument> idocList) {

        dao.unLockUpdateState(idocList);
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.bean.IndexedDocumentBean#haveToIndex(int)
     */
    @Override
    public final List<IndexedDocument> haveToIndex(final int docNum) {

        final long lock = dao.lockDocuments(docNum);
        return dao.getByLock(lock);
    }
}
