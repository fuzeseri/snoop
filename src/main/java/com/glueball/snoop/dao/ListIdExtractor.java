package com.glueball.snoop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ListIdExtractor implements ResultSetExtractor {

	final List<String> idList;

	public ListIdExtractor(final List<String> _idList) {
		this.idList = _idList;
	}

	public Object extractData(final ResultSet rs) throws SQLException, DataAccessException {

		while (rs.next()) {
			idList.add(rs.getString("id"));
		}
		return rs;
	}

}
