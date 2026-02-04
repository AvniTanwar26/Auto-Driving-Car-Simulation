package com.autodrive.simulation.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

    @Test
    void leftShouldRotateCounterClockwise() {
        assertEquals(Direction.W, Direction.N.left());
        assertEquals(Direction.S, Direction.W.left());
        assertEquals(Direction.E, Direction.S.left());
        assertEquals(Direction.N, Direction.E.left());
    }

    @Test
    void rightShouldRotateClockwise() {
        assertEquals(Direction.E, Direction.N.right());
        assertEquals(Direction.S, Direction.E.right());
        assertEquals(Direction.W, Direction.S.right());
        assertEquals(Direction.N, Direction.W.right());
    }

    @Test
    void dxDyShouldMatchUnitMovement() {
        // N(0,1): y increases
        assertEquals(0, Direction.N.dx);
        assertEquals(1, Direction.N.dy);

        // E(1,0): x increases
        assertEquals(1, Direction.E.dx);
        assertEquals(0, Direction.E.dy);

        // S(0,-1): y decreases
        assertEquals(0, Direction.S.dx);
        assertEquals(-1, Direction.S.dy);

        // W(-1,0): x decreases
        assertEquals(-1, Direction.W.dx);
        assertEquals(0, Direction.W.dy);
    }
}

