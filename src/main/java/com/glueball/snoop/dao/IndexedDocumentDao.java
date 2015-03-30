package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.entity.IndexedDocument;

public interface IndexedDocumentDao extends SnoopDao<IndexedDocument> {

    List<IndexedDocument> findByShareName(final String share);

    void deleteByIds(final List<String> ids);

    long lockDocuments(final int docNum);

    List<IndexedDocument> getByLock(final long lock);

    void updateState(final List<IndexedDocument> idocList);

    void unLockUpdateState(final List<IndexedDocument> idocList);
}
