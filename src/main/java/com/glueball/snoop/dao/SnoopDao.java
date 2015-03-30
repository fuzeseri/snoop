package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

public interface SnoopDao<T> {

    T findById(final String Id);

    void insertOne(final T doc);

    void insertList(final List<T> docs);

    void deleteById(final String id);

    void createTable();

    void truncateTable();

    List<T> selectAll();

    long rowNum();
}
