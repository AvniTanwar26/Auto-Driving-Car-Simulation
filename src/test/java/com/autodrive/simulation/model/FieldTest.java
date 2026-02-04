package com.autodrive.simulation.model;

import com.autodrive.simulation.model.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldTest {

    @Test
    void isInsideShouldReturnTrueForCoordinatesWithinBounds() {
        Field field = new Field(10, 10);

        assertTrue(field.isInside(0, 0));
        assertTrue(field.isInside(9, 9));
        assertTrue(field.isInside(5, 4));
    }

    @Test
    void isInsideShouldReturnFalseForCoordinatesOutsideBounds() {
        Field field = new Field(10, 10);

        assertFalse(field.isInside(-1, 0));
        assertFalse(field.isInside(0, -1));
        assertFalse(field.isInside(10, 0));
        assertFalse(field.isInside(0, 10));
        assertFalse(field.isInside(10, 10));
    }
}

