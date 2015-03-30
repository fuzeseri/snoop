package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

/**
 * Spring data access object to access the IndexedDocuments in the relational
 * database.
 *
 * @author karesz
 */
public interface IndexedDocumentDao extends SnoopDao<IndexedDocument> {

    /**
     * Returns the documents related to a specific share.
     *
     * @param share
     *            the name of the share.
     * @return list of IndexedDocument objects.
     */
    List<IndexedDocument> findByShareName(final String share);

    /**
     * Deletes the given set of Indexed Documents from the relational database.
     *
     * @param ids
     *            the list of document ids to delete.
     */
    void deleteByIds(final List<String> ids);

    /**
     * Lock a given number of documents to load the to the lucene index.
     *
     * @param docNum
     *            the maximum number of documents to lock.
     * @return the generated lock id.
     */
    long lockDocuments(final int docNum);

    /**
     * Returns the set of IndexedDocument locked with the given lock id.
     *
     * @param lock
     *            the lock id.
     * @return the list of the locked IndexedDocuments.
     */
    List<IndexedDocument> getByLock(final long lock);

    /**
     * Synchronize the index status in the relational database with the status
     * of the given list of IndexedDocuments.
     *
     * @param idocList
     *            the list of the IndexedDocuments.
     */
    void updateState(final List<IndexedDocument> idocList);

    /**
     * Synchronize the index status in the relational database with the status
     * of the given list of IndexedDocuments and unlock them.
     *
     * @param idocList
     *            the list of the IndexedDocuments.
     */
    void unLockUpdateState(final List<IndexedDocument> idocList);
}
