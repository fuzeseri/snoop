package com.glueball.snoop.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.glueball.snoop.entity.DocumentPath;

public class DocumentPathExtractor implements ResultSetExtractor<DocumentPath> {

    final DocumentPath doc;

    public DocumentPathExtractor(final DocumentPath _doc) {

        this.doc = _doc;
    }

    public DocumentPath extractData(final ResultSet rs) throws SQLException, DataAccessException {

        if (rs.next()) {
            doc.setId(rs.getString("id"));
            doc.setShareName(rs.getString("share_name"));
            doc.setFileName(rs.getString("file_name"));
            doc.setUri(rs.getString("uri"));
            doc.setPath(rs.getString("path"));
            doc.setLocalPath(rs.getString("local_path"));
            doc.setLastModifiedTime(rs.getTimestamp("last_modified_time"));
            doc.setContentType(rs.getString("content_type"));
        }

        return doc;
    }
}
