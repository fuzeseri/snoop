package com.glueball.snoop.dao;

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
