package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

/**
 * Service to acces and update the documents index statuses in the relation
 * database.
 *
 * @author karesz
 */
public interface IndexedDocumentBean {

    /**
     * Unlock the documents and update their index statuses.
     *
     * @param idocList
     *            list of IndexedDocuments.
     */
    void unLockUpdateState(final List<IndexedDocument> idocList);

    /**
     * Reads the IdexedDocuments from the relational database with non INDEXED
     * and non ERROR status.
     *
     * @param docNum
     *            maximum number of documents to return.
     * @return list of IndexedDocuments which needed to index.
     */
    List<IndexedDocument> haveToIndex(final int docNum);
}
