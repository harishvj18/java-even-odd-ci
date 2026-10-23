package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvenOddTest {
    @Test
    void positiveEvenNumberIsEven() {
        assertEquals("Even", EvenOdd.classify(2));
        assertEquals("Even", EvenOdd.classify(100));
    }

    @Test
    void positiveOddNumberIsOdd() {
        assertEquals("Odd", EvenOdd.classify(1));
        assertEquals("Odd", EvenOdd.classify(99));
    }

    @Test
    void zeroIsEven() {
        assertEquals("Even", EvenOdd.classify(0));
    }

    @Test
    void negativeEvenNumberIsEven() {
        assertEquals("Even", EvenOdd.classify(-2));
        assertEquals("Even", EvenOdd.classify(-100));
    }

    @Test
    void negativeOddNumberIsOdd() {
        assertEquals("Odd", EvenOdd.classify(-1));
        assertEquals("Odd", EvenOdd.classify(-99));
    }

    @Test
    void integerBoundariesAreHandled() {
        assertEquals("Odd", EvenOdd.classify(Integer.MAX_VALUE));
        assertEquals("Even", EvenOdd.classify(Integer.MIN_VALUE));
    }
}
