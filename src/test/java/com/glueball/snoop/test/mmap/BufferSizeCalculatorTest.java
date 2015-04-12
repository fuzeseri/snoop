/**
 * 
 */
package com.glueball.snoop.test.mmap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.glueball.snoop.mmap.BufferSizeCalculator;

/**
 * @author karesz
 */
public class BufferSizeCalculatorTest {

    @Test
    public final void testBiggerThanMax() {

        final BufferSizeCalculator bsc = new BufferSizeCalculator(333, 100);

        assertEquals(100, bsc.nextBufSize());
        assertEquals(100, bsc.nextBufSize());
        assertEquals(100, bsc.nextBufSize());
        assertEquals(33, bsc.nextBufSize());
        assertEquals(0, bsc.nextBufSize());
        assertEquals(0, bsc.nextBufSize());
    }

    @Test
    public final void testSmallerThanMax() {

        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(22, 100);
        assertEquals(22, bsc1.nextBufSize());
        assertEquals(0, bsc1.nextBufSize());
        assertEquals(0, bsc1.nextBufSize());
    }

    @Test
    public final void testZeroListSize() {

        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(0, 100);
        assertEquals(0, bsc1.nextBufSize());
        assertEquals(0, bsc1.nextBufSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testZeroBufSize() {

        @SuppressWarnings("unused")
        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(33, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNegativeListSize() {

        @SuppressWarnings("unused")
        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(-122, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNegativeBufSize() {

        @SuppressWarnings("unused")
        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(22, -100);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNegativeListBufSize() {

        @SuppressWarnings("unused")
        final BufferSizeCalculator bsc1 = new BufferSizeCalculator(-22, -100);
    }

}
