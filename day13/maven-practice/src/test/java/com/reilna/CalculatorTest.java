package com.reilna;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void addShouldReturnSum(){
        int result = Calculator.add(2,3);

        assertEquals(5, result);
    }

    @Test
    void multiplyShouldReturnMul() {
        int result = Calculator.multiply(4, 5);

        assertEquals(20, result);
    }
}
