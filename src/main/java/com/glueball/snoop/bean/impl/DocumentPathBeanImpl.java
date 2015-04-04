package com.glueball.snoop.bean.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.glueball.snoop.bean.DocumentPathBean;
import com.glueball.snoop.dao.DocumentPathDao;
import com.glueball.snoop.entity.DocumentPath;

/**
 * Implementation of the DocumentPathBean service.
 *
 * @author karesz
 */
public class DocumentPathBeanImpl implements DocumentPathBean {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LogManager
            .getLogger(DocumentPathBeanImpl.class);

    /**
     * Spring data access object to access the DocumentPaths in the relational
     * database.
     */
    private DocumentPathDao dao;

    /**
     * Setter method of the dao field.
     *
     * @param pDao
     *            the DocumentPathDao instance.
     */
    @Required
    public final void setDao(final DocumentPathDao pDao) {

        this.dao = pDao;
    }

    /**
     * Initialization method.
     */
    public void init() {

        dao.createTable();
    }

    /*
     * (non-Javadoc)
     * @see com.glueball.snoop.bean.DocumentPathBean#updateDocuments(
     * java.lang.String* , java.util.List)
     */
    @Override
    @Transactional
    public void updateDocuments(final String shareName,
            final List<DocumentPath> docs) {

        dao.truncateTable();

        if (docs.size() > 100) {

            int counter = 0;
            final List<DocumentPath> chunk = new ArrayList<DocumentPath>(100);
            for (final DocumentPath doc : docs) {

                chunk.add(doc);
                if (counter++ == 100) {

                    dao.insertList(chunk);
                    counter = 0;
                    chunk.clear();
                }
            }
            dao.insertList(chunk);
        } else {

            dao.insertList(docs);
        }

        dao.updateNewDocuments(shareName);
        dao.updateModifiedDocuments(shareName);
        dao.updateDeletedDocuments(shareName);
        LOG.debug("Documents successfully updated on share : " + shareName);
    }
}
