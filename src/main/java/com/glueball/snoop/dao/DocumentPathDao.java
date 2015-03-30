package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathDao extends SnoopDao<DocumentPath> {

    // List<DocumentPath> getNewDocuments();
    // List<DocumentPath> getModifiedDocuments();
    // List<DocumentPath> getDeletedDocuments();
    DocumentPath findBySum(final String md5sum);

    void updateNewDocuments(final String shareName);

    void updateModifiedDocuments(final String shareName);

    void updateDeletedDocuments(final String shareName);
}
