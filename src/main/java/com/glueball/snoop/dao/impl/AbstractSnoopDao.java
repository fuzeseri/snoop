package com.glueball.snoop.dao.impl;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Abstract class to inherit by the dao implementations. It contains the
 * database access handler objects.
 *
 * @author karesz
 */
public abstract class AbstractSnoopDao {

    /**
     * Spring data jdbc template.
     */
    @Autowired(required = true)
    protected JdbcTemplate jdbcTemplate;

    /**
     * Setter method of the jdbcTemplate field.
     *
     * @param pJdbcTemplate
     *            The Spring data jdbc template object.
     */
    protected void setJdbcTemplate(final JdbcTemplate pJdbcTemplate) {

        this.jdbcTemplate = pJdbcTemplate;
    }
}
