package com.backstreetbrogrammer.ch03_memorymodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringTest {

    @Test
    void testStringLiteral() {
        final String str1 = "Guidemy";
        final String str2 = "Guidemy";

        assertTrue(str1.equals(str2));
        assertTrue(str1 == str2);
    }

    @Test
    void testStringConstructor() {
        final String str1 = "Guidemy";
        final String str2 = new String("Guidemy");

        assertTrue(str1.equals(str2));
        assertFalse(str1 == str2);
    }

    @Test
    void testStringManualInterning() {
        final String str1 = "Guidemy";
        final String str2 = new String("Guidemy").intern();

        assertTrue(str1.equals(str2));
        assertTrue(str1 == str2);
    }
}
