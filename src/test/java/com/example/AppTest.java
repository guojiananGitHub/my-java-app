package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    private final App app = new App();

    @Test
    public void testGreet() {
        assertEquals("Hello, World!", app.greet("World"));
        assertEquals("Hello, CI/CD!", app.greet("CI/CD"));
    }

    @Test
    public void testAddNumbers() {
        assertEquals(3, app.addNumbers(1, 2));
        assertEquals(0, app.addNumbers(-1, 1));
    }
}