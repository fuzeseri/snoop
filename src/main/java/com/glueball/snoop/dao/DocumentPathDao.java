package com.glueball.snoop.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathDao extends SnoopDao<DocumentPath> {

	public DocumentPath findBySum(String md5sum) throws DataAccessException;
	public Set<DocumentPath> fullTextSearch(String search) throws DataAccessException;

}
