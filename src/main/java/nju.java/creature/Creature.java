package nju.java.creature;

import nju.java.Field;
import nju.java.Thing2D;
import nju.java.Position;

import java.util.Random;

public abstract class Creature extends Thing2D implements Runnable {
    private Field field;
    private Position position;

    public Creature(Field field) {
        super(0, 0);

        this.field = field;

    }

    public void setPosition(Position position) {
        this.position = position;
        this.setX(field.convertPositionToX(position));
        this.setY(field.convertPositionToY(position));
    }

    public Position getPosition() { return position; }

    public void releasePosition() { position = null; }

    public void randomMove() {
        Random rand = new Random();
        int x = 0, y = 0;
        if (rand.nextInt(2) == 0) {
            x = rand.nextInt(2) == 0 ? 1 : -1;
        } else {
            y = rand.nextInt(2) == 0 ? 1 : -1;
        }
        move(x, y);
    }

    public void move(int x, int y) {
        int newX = position.getX() + x;
        int newY = position.getY() + y;
        if (field.inSpace(newX, newY)) {
            if (!field.getPositions()[newX][newY].hasCreature()) {
                field.getPositions()[position.getX()][position.getY()].clearCreature();
                field.getPositions()[newX][newY].setCreature(this);
                setPosition(field.getPositions()[newX][newY]);
            }
        }
    }

    public void run() {
        while (!Thread.interrupted()) {
            Random rand = new Random();

            this.randomMove();
            try {
                Thread.sleep(rand.nextInt(1000) + 500);
                this.field.repaint();
            } catch (Exception e) {

            }
        }
    }
}