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

public class ListIdExtractor implements ResultSetExtractor<List<String>> {

    final List<String> idList;

    public ListIdExtractor(final List<String> _idList) {

        this.idList = _idList;
    }

    @Override
    public List<String> extractData(final ResultSet rs) throws SQLException,
            DataAccessException {

        while (rs.next()) {
            idList.add(rs.getString("id"));
        }
        return idList;
    }

}
