package com.autodrive.simulation.model;

/**
 * Domain model representing a single car in the simulation.
 * <p>
 * This class encapsulates its internal state and exposes behaviour-oriented
 * methods so that callers do not need to manipulate fields directly.
 */
public class Car {

    private final String name;
    private int x;
    private int y;
    private Direction direction;
    private final String commands;
    private boolean active = true;

    public Car(String name, int x, int y, Direction direction, String commands) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.commands = commands;
    }

    /**
     * Execute a single movement command if the car is still active.
     */
    public void execute(char command, Field field) {
        if (!active) {
            return;
        }

        switch (command) {
            case 'L':
                direction = direction.left();
                break;
            case 'R':
                direction = direction.right();
                break;
            case 'F':
                moveForward(field);
                break;
            default:
                // Ignore unknown commands to keep behaviour predictable
                break;
        }
    }

    private void moveForward(Field field) {
        int nextX = x + direction.dx;
        int nextY = y + direction.dy;

        if (field.isInside(nextX, nextY)) {
            x = nextX;
            y = nextY;
        }
    }

    public String positionKey() {
        return x + "," + y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getCommands() {
        return commands;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public String toString() {
        return name + ", (" + x + "," + y + ") " + direction;
    }
}
