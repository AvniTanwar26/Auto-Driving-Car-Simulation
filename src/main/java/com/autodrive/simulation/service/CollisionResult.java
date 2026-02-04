package com.autodrive.simulation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable value object describing the outcome of a collision between cars.
 */
public final class CollisionResult {

    private final int step;
    private final String position;
    private final List<String> collidedCarNames;

    public CollisionResult(int step, String position, List<String> collidedCarNames) {
        this.step = step;
        this.position = position;
        this.collidedCarNames = Collections.unmodifiableList(new ArrayList<>(collidedCarNames));
    }

    public int getStep() {
        return step;
    }

    public String getPosition() {
        return position;
    }

    public List<String> getCollidedCarNames() {
        return collidedCarNames;
    }
}

