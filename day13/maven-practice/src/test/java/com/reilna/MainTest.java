package com.reilna;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void addShouldReturnSum() {
        int result = Main.add(2,3);

        assertEquals(5, result);
    }
}
