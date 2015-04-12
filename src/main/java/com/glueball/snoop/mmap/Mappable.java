/**
 * 
 */
package com.glueball.snoop.mmap;

import java.io.IOException;

/**
 * @author karesz
 */
public interface Mappable {

    Mappable fromByteArray(final byte[] data);

    byte[] toByteArray() throws IOException;

    long getPosition();

    void setPosition(final long position);

    int size();
}
