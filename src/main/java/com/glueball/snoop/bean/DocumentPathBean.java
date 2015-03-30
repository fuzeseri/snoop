package com.glueball.snoop.bean;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import com.glueball.snoop.entity.DocumentPath;

/**
 * Service to access and update the document statuses in the relational
 * database.
 * 
 * @author karesz
 *
 */
public interface DocumentPathBean {

    /**
     * Updates the files index status in the relation database in transaction.
     * 
     * @param shareName
     *            the name of the network or local file system directory.
     * @param docs
     *            list of DocumentPath instances loaded by the file system scan.
     */
    void updateDocuments(final String shareName, final List<DocumentPath> docs);
}
