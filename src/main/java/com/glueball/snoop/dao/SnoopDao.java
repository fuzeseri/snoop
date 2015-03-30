package com.glueball.snoop.dao;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.util.List;

/**
 * Generic interface of Spring data acces objects in the snoop. It defines a set
 * of general actions to deal with the relational data.
 *
 * @author karesz
 * @param <T>
 *            the type of entity stored in the database.
 */
public interface SnoopDao<T> {

    /**
     * Returns the object with a specific primary key.
     *
     * @param Id
     *            the primary key.
     * @return the entity object representing the row with the primary key.
     */
    T findById(final String Id);

    /**
     * Inserts an entity to the database.
     *
     * @param doc
     *            the entity to insert.
     */
    void insertOne(final T doc);

    /**
     * Batch inserts a set of entities to the database.
     *
     * @param docs
     *            the list entities to insert.
     */
    void insertList(final List<T> docs);

    /**
     * Deletes a specific entity from the database.
     *
     * @param id
     *            the primary key of the entity to delete.
     */
    void deleteById(final String id);

    /**
     * Creates the table to store the entity in the relational database if not
     * exists.
     */
    void createTable();

    /**
     * Delete all entities from the relational database.
     */
    void truncateTable();

    /**
     * Returns a list with all of the entities.
     *
     * @return the list of the entities.
     */
    List<T> selectAll();

    /**
     * Returns the number of the stored entities.
     *
     * @return the number of the entities.
     */
    long rowNum();
}
