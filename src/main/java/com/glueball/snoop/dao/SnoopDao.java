package com.glueball.snoop.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface SnoopDao<T> {

	T findBySum(String md5sum) throws DataAccessException;
	T findById(final String Id) throws DataAccessException;
	void insertOne(final T doc) throws DataAccessException;
	void insertList(final List<T> docs) throws DataAccessException;
	void deleteData(final String id) throws DataAccessException;
	void createTable() throws DataAccessException;
	void deleteALL() throws DataAccessException;
	List<T> selectAll() throws DataAccessException;
	long rowNum() throws DataAccessException;	
}
