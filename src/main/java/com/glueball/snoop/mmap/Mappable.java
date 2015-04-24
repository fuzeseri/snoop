package com.glueball.snoop.mmap;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import java.io.IOException;

/**
 * Interface to specify the data type to save or load in a memory mapped file.
 *
 * @author karesz
 */
public interface Mappable {

    /**
     * Creates a Mappable object from byte array.
     *
     * @param data
     *            the byte array with the mappable data.
     * @return the Mappable object.
     */
    Mappable fromByteArray(final byte[] data);

    /**
     * Creates a byte array from the Mappable object.
     *
     * @return the byte array with the mappable data.
     * @throws IOException
     *             on eny IO error.
     */
    byte[] toByteArray() throws IOException;

    /**
     * Returns the position of the Mappable data in the memory mapped file.
     *
     * @return the position of the Mappable data in the memory mapped file.
     */
    long getPosition();

    /**
     * Setter method to set position of a new mappable data in the memory mapped
     * file.
     *
     * @param position
     *            the pisition to set.
     */
    void setPosition(final long position);

    /**
     * The size of the Mappable data in bytes.
     *
     * @return The size of the Mappable data in bytes.
     */
    int size();
}
