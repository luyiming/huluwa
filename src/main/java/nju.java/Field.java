package nju.java;

import nju.java.creature.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Field extends JPanel {

    private final int SPACE = 70;
    private final int OFFSET = 50;
    private final int ROW = 10;
    private final int COL = 10;

    private Huluwa[] huluwas;
    private Minion[] minions;
    private Scorpion xiezijing;
    private Snake snake;
    private YeYe yeye;
    private Background background;

    private Position[][] positions;

    private boolean completed = false;

    public Field() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return 750;
    }

    public int getBoardHeight() {
        return 750;
    }

    public boolean inSpace(int x, int y) {
        if (x >= 0 && x < ROW && y >= 0 && y < COL)
            return true;
        else
            return false;
    }

    public synchronized Position[][] getPositions() { return positions; }

    public int convertPositionToX(Position position) { return position.getX() * SPACE + OFFSET; }

    public int convertPositionToY(Position position) { return position.getY() * SPACE + OFFSET; }

    public final void initWorld() {

        background = new Background(0, 0);

        positions = new Position[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                positions[i][j] = new Position(i, j);
            }
        }

        huluwas = new Huluwa[7];
        for (int i = 0; i < huluwas.length; i++) {
            huluwas[i] = new Huluwa(COLOR.values()[i], this);
            huluwas[i].setPosition(positions[2][i]);
            positions[2][i].setCreature(huluwas[i]);
        }

        yeye = new YeYe(this);
        yeye.setPosition(positions[0][0]);
        positions[0][0].setCreature(yeye);

        minions = new Minion[7];
        for (int i = 0; i < minions.length; i++) {
            minions[i] = new Minion(this);
            minions[i].setPosition(positions[7][i]);
            positions[7][i].setCreature(minions[i]);
        }

        xiezijing = new Scorpion(this);
        xiezijing.setPosition(positions[9][5]);
        positions[9][5].setCreature(xiezijing);

        snake = new Snake(this);
        snake.setPosition(positions[9][4]);
        positions[9][4].setCreature(snake);

    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList world = new ArrayList();

        world.add(background);
        world.add(yeye);
        world.add(snake);
        world.add(xiezijing);
        for (Huluwa a : huluwas)
            world.add(a);
        for (Minion a : minions)
            world.add(a);

        for (int i = 0; i < world.size(); i++) {

            Thing2D item = (Thing2D) world.get(i);

            if (item instanceof Creature) {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 100, 20);
            }

        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (completed) {
                return;
            }

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                yeye.move(-1, 0);
            } else if (key == KeyEvent.VK_RIGHT) {
                yeye.move(1, 0);
            } else if (key == KeyEvent.VK_UP) {
                yeye.move(0, -1);
            } else if (key == KeyEvent.VK_DOWN) {
                yeye.move(0, 1);
            } else if (key == KeyEvent.VK_S) {
                new Thread(yeye).start();
            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            } else if (key == KeyEvent.VK_C) {
                completed = true;
            }

            repaint();
        }
    }


    public void restartLevel() {

        initWorld();
        if (completed) {
            completed = false;
        }
    }
}