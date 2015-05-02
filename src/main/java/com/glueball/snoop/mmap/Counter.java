/**
 * 
 */
package com.glueball.snoop.mmap;

/**
 * @author karesz
 */
public final class Counter {

    private int count = 0;

    public void increment() {

        count++;
    }

    public int getCount() {

        return count;
    }

    public void reset() {

        count = 0;
    }
}
