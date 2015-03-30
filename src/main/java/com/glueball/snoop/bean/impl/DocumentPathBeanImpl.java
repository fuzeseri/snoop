package com.glueball.snoop.bean.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.glueball.snoop.bean.DocumentPathBean;
import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathBeanImpl implements DocumentPathBean {

    private static final Logger LOG = LogManager.getLogger(DocumentPathBeanImpl.class);

    private DocumentPathDao dao;

    @Required
    public void setDao(final DocumentPathDao dao) {

        this.dao = dao;
    }

    public void init() {

        dao.createTable();
    }

    @Override
    @Transactional
    public void updateDocuments(final String shareName, final List<DocumentPath> docs) {

        dao.truncateTable();
        dao.insertList(docs);
        dao.updateNewDocuments(shareName);
        dao.updateModifiedDocuments(shareName);
        dao.updateDeletedDocuments(shareName);
        LOG.debug("Documents successfully updated on share : " + shareName);
    }
}
