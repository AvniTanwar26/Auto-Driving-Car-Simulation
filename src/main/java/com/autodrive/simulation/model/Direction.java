package com.autodrive.simulation.model;

public enum Direction {
    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Turn 90 degrees to the left (counter-clockwise).
     *
     * The enum values are ordered as [N, E, S, W] with numbers [0, 1, 2, 3].
     * Going left means going to the previous value in this circle:
     * N -> W -> S -> E -> N
     *
     * "Previous" (ordinal - 1) is the same as (ordinal + 3) when we wrap every 4 steps,
     * so we use (ordinal() + 3) % 4 to keep the math non-negative.
     */
    public Direction left() {
        return values()[(ordinal() + 3) % 4];
    }

    /**
     * Turn 90 degrees to the right (clockwise).
     *
     * The enum values are ordered as [N, E, S, W] with numbers [0, 1, 2, 3].
     * Going right means going to the next value in this circle:
     * N -> E -> S -> W -> N
     *
     * "Next" is just (ordinal + 1), and % 4 makes it wrap back to N after W.
     */
    public Direction right() {
        return values()[(ordinal() + 1) % 4];
    }
}
