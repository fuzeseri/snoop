package com.glueball.snoop.entity;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
/**
 * @author karesz
 */
public enum IndexStatus {

    /**
     * Index status of a the new document. Pre-index state.
     */
    NEW(0),

    /**
     * Index status of a modified document. Pre-index state.
     */
    MODIFIED(1),

    /**
     * Index status of a deleted document. Pre-delete state.
     */
    DELETED(2),

    /**
     * Index status of a successfully indexed document. Post-index state.
     */
    INDEXED(3),

    /**
     * Index status of of a document which manually set to reindex. Pre-index
     * state.
     */
    REINDEX(4),

    /**
     * Index status of a document if the indexing unsuccessful. Post-index
     * state.
     */
    ERROR(99);

    /**
     * Store the value as numeric.
     */
    private final int status;

    /**
     * Constructor.
     * 
     * @param pStatus
     *            numeric status.
     */
    private IndexStatus(final int pStatus) {

        this.status = pStatus;
    }

    /**
     * @return numeric status.
     */
    public short getStatus() {

        return (short) this.status;
    }
}
