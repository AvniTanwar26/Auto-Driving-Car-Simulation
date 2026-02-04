package com.autodrive.simulation.model;

public class Field {

    private final int width;
    private final int height;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
