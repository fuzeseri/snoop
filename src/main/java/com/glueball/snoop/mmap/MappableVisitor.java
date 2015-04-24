package com.glueball.snoop.mmap;

/**
 * Visitor to make action on any object read from the mmap file.
 * 
 * @author karesz
 */
public interface MappableVisitor {

    void onObject(final Mappable object);

    void onStart();

    void onFinish();
}
