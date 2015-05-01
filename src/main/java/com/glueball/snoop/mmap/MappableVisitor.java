package com.glueball.snoop.mmap;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
/**
 * Visitor to make action on any object read from the mmap file.
 * 
 * @author karesz
 */
public interface MappableVisitor<T extends Mappable> {

    /**
     * Called on every object readed from the memory mapped file.
     *
     * @param Mappable
     *            object readed from the file.
     */
    void onObject(final T object);

    /**
     * Called before starting read the file.
     */
    void onStart();

    /**
     * Called after the file read ends.
     */
    void onFinish();
}
