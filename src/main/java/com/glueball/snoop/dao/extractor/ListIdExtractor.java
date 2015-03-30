package com.glueball.snoop.dao.extractor;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * ResultseExtractor implementation to extract document ids to a List from
 * database query result.
 *
 * @author karesz
 */
public final class ListIdExtractor implements
        ResultSetExtractor<List<String>> {

    /**
     * The List to extract the ids to it.
     */
    final List<String> idList;

    /**
     * Constructor.
     *
     * @param pIdList
     *            The List to extract the ids to it.
     */
    public ListIdExtractor(final List<String> pIdList) {

        this.idList = pIdList;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql
     * .ResultSet)
     */
    @Override
    public List<String> extractData(final ResultSet rs) throws SQLException,
            DataAccessException {

        while (rs.next()) {
            idList.add(rs.getString("id"));
        }
        return idList;
    }
}
