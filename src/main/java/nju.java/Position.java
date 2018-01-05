package nju.java;

import nju.java.creature.Creature;

public class Position {
    private int x, y;

    private Creature creature;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.creature = null;
    }

    public void setCreature(Creature creature) {
        if (this.creature != null) {
            System.err.printf("duplicate creature at (%d, %d)", x, y);
            return;
        }
        this.creature = creature;
    }

    public Creature getCreature() { return creature; }

    public void clearCreature() {
        creature = null;
    }

    public boolean isEmpty() { return creature == null; }

    public boolean hasCreature() { return creature != null; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public int getX() { return x; }

    public int getY() { return y; }
}