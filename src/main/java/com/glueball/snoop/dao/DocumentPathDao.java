package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.entity.DocumentPath;

/**
 * Spring data access object to access the DocumentPaths in the relational
 * database.
 *
 * @author karesz
 */
public interface DocumentPathDao extends SnoopDao<DocumentPath> {

    /**
     * Finds a document by the md5sum field.
     *
     * @param md5sum
     *            the md5sum string.
     * @return the matching DocumentPath object.
     */
    DocumentPath findBySum(final String md5sum);

    /**
     * Updates the index status in the relational database of the new documents
     * on a specific share.
     * 
     * @param shareName
     *            the name of the share.
     */
    void updateNewDocuments(final String shareName);

    /**
     * Updates the index status in the relational database of the modified
     * documents on a specific share.
     * 
     * @param shareName
     *            the name of the share.
     */
    void updateModifiedDocuments(final String shareName);

    /**
     * Updates the index status in the relational database of the deleted
     * documents on a specific share.
     * 
     * @param shareName
     *            the name of the share.
     */
    void updateDeletedDocuments(final String shareName);
}
