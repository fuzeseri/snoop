/**
 * 
 */
package com.glueball.snoop.performance.util;

import java.util.Random;

/**
 * Utility class to generate random strings.
 * 
 * @author karesz
 */
public final class RandomStringUtil {

    private static final Random rnd = new Random();
    /**
     * Array of characters to use for random string.
     */
    private static final char[] characters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Generates a random string.
     *
     * @param length
     *            the length of the random string.
     * @return the generated string.
     */
    public static String generateString(final int length) {

        final char[] text = new char[length];

        for (int i = 0; i < length; i++) {

            text[i] = characters[rnd.nextInt(characters.length)];
        }
        return new String(text);
    }
}
