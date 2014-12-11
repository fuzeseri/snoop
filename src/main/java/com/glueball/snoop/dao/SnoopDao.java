package com.glueball.snoop.dao;

import org.springframework.dao.DataAccessException;

public interface SnoopDao<T> {

	public void insertOne(T doc) throws DataAccessException;
	public T findById(String Id) throws DataAccessException;
	
}