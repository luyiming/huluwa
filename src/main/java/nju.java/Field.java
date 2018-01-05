package nju.java;

import nju.java.bullet.Bullet;
import nju.java.bullet.BulletsManager;
import nju.java.creature.*;
import nju.java.record.Record;
import nju.java.record.RecordFactory;
import nju.java.record.RecordPlayer;
import nju.java.record.RecordsManager;

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
    private BulletsManager bulletsManager;
    private RecordsManager recordsManager;

    private Position[][] positions;

    private boolean completed = false;
    private boolean huluwaWin = true;
    private boolean minionWin = true;

    private long startTime;

    private Board board;

    ArrayList<Thread> threads = new ArrayList<Thread>();

    private ArrayList<Creature> replayCreatures;
    private ArrayList<Bullet> replayBullets;

    public Field(Board board) {
        this.board = board;

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

    public synchronized boolean inSpace(int x, int y) {
        if (x >= 0 && x < ROW && y >= 0 && y < COL)
            return true;
        else
            return false;
    }

    public synchronized Position[][] getPositions() { return positions; }

    public synchronized BulletsManager getBulletsManager() { return bulletsManager; }

    public synchronized RecordsManager getRecordsManager() {
        return recordsManager;
    }

    public int convertPositionToX(Position position) { return position.getX() * SPACE + OFFSET; }
    public int convertPositionToX(int x) { return x * SPACE + OFFSET; }

    public int convertPositionToY(Position position) { return position.getY() * SPACE + OFFSET; }
    public int convertPositionToY(int y) { return y * SPACE + OFFSET; }

    public int convertXtoPosition(int x) { return (x - OFFSET) / SPACE; }

    public int convertYtoPosition(int y) { return (y - OFFSET) / SPACE; }

    public final void initWorld() {

        background = new Background(0, 0);

        bulletsManager = new BulletsManager(this);
        recordsManager = new RecordsManager(this);

        positions = new Position[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                positions[i][j] = new Position(i, j);
            }
        }

        huluwas = new Huluwa[7];
        for (int i = 0; i < huluwas.length; i++) {
            huluwas[i] = new Huluwa(COLOR.values()[i], this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN,
                    Creature.DAMAGE_MEDIAN, Creature.ATTACK_RANGE_MEDIAN, 1, "bullet" + (i+1) + ".png");
            huluwas[i].setPosition(positions[2][i]);
            positions[2][i].setCreature(huluwas[i]);
            recordsManager.addRecord(RecordFactory.makeCreateRecord(huluwas[i].getId(), huluwas[i]));
        }

        yeye = new YeYe(this, Creature.SPEED_LOW, Creature.BULLET_SPEED_MEDIAN, Creature.DAMAGE_LOW,
                Creature.ATTACK_RANGE_FAR, 2, "bullet0.png");
        yeye.setPosition(positions[0][0]);
        positions[0][0].setCreature(yeye);
        recordsManager.addRecord(RecordFactory.makeCreateRecord(yeye.getId(), yeye));

        minions = new Minion[5];
        for (int i = 0; i < minions.length; i++) {
            minions[i] = new Minion(this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN,
                    Creature.DAMAGE_MEDIAN, Creature.ATTACK_RANGE_MEDIAN, 1, "bullet8.png");
            minions[i].setPosition(positions[7][i]);
            positions[7][i].setCreature(minions[i]);
            recordsManager.addRecord(RecordFactory.makeCreateRecord(minions[i].getId(), minions[i]));
        }

        xiezijing = new Scorpion(this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN, Creature.DAMAGE_MEDIAN,
                Creature.ATTACK_RANGE_MEDIAN, 1, "bullet8.png");
        xiezijing.setPosition(positions[9][5]);
        positions[9][5].setCreature(xiezijing);
        recordsManager.addRecord(RecordFactory.makeCreateRecord(xiezijing.getId(), xiezijing));

        snake = new Snake(this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN, Creature.DAMAGE_MEDIAN,
                Creature.ATTACK_RANGE_MEDIAN, 1, "bullet8.png");
        snake.setHealth(0.6);
        snake.setPosition(positions[9][4]);
        positions[9][4].setCreature(snake);
        recordsManager.addRecord(RecordFactory.makeCreateRecord(snake.getId(), snake));

    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList world = new ArrayList();

        world.add(background);
        if (replayMode == true) {
            world.addAll(this.replayCreatures);
            world.addAll(this.replayBullets);
        } else {
            world.add(yeye);
            world.add(snake);
            world.add(xiezijing);
            for (Huluwa a : huluwas)
                world.add(a);
            for (Minion a : minions)
                world.add(a);
            for (Bullet bullet: bulletsManager.getBullets())
                world.add(bullet);
        }

        for (int i = 0; i < world.size(); i++) {

            Thing2D item = (Thing2D) world.get(i);

            if (item instanceof Creature) {
                Creature creature = (Creature)item;
                if (creature.isDead())
                    continue;
                g.drawImage(item.getImage(), item.x(), item.y(), this);
                g.setColor(new Color(255, 0, 0));
                g.fillRect(item.x() + 10, item.y(), (int)(50 * creature.getHealth()), 4);
                g.setColor(new Color(0, 0, 0));
                g.drawRect(item.x() + 10, item.y(), 50, 4);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (!completed) {
                scoreRecord();
            }

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                if (huluwaWin && minionWin) {
                    g.drawString("Tie", 300, 100);
                } else if (huluwaWin) {
                    g.drawString("Huluwa win", 300, 100);
                } else if (minionWin) {
                    g.drawString("Minion win", 300, 100);
                }
            }

        }
    }

    @Override
    public synchronized void paint(Graphics g) {
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
                Field.this.threads.clear();
                Field.this.startTime = System.currentTimeMillis();

                Field.this.threads.add(new Thread(yeye));
                for (Huluwa huluwa : huluwas)
                    Field.this.threads.add(new Thread(huluwa));
                for (Minion minion : minions)
                    Field.this.threads.add(new Thread(minion));
                Field.this.threads.add(new Thread(xiezijing));
                Field.this.threads.add(new Thread(snake));
                Field.this.threads.add(new Thread(bulletsManager));
                for (Thread thread : Field.this.threads)
                    thread.start();
            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            } else if (key == KeyEvent.VK_C) {
                completed = true;
            } else if (key == KeyEvent.VK_X) {
//                new RecordsManager().parse("D:\\Projects\\huluwa\\sample.xml");
            } else if (key == KeyEvent.VK_P) {
                RecordPlayer recordPlayer = new RecordPlayer(Field.this);
                Field.this.getRecordsManager().parse("C:\\Users\\luyim\\Desktop\\sample.xml", recordPlayer);
                System.out.println("records size --- " + Field.this.getRecordsManager().getRecords().size());
                new Thread(recordPlayer).start();
//                new RecordsManager().exportToFile("C:\\Users\\luyim\\Desktop\\sample.xml");
            }

            repaint();
        }
    }

    private void scoreRecord() {
        this.huluwaWin = true;
        this.minionWin = true;
        for (Huluwa huluwa : huluwas) {
            if (!huluwa.isDead()) {
                this.minionWin = false;
                break;
            }
        }
        if (!yeye.isDead()) {
            this.minionWin = false;
        }

        for (Minion minion : minions) {
            if (!minion.isDead()) {
                this.huluwaWin = false;
                break;
            }
        }
        if (!snake.isDead()) {
            this.huluwaWin = false;
        }
        if (!xiezijing.isDead()) {
            this.huluwaWin = false;
        }

        if (this.huluwaWin || this.minionWin) {
            this.completed = true;
            for (Thread thread : threads) {
                thread.interrupt();
            }
            System.out.println("completed");
            this.recordsManager.exportToFile("C:\\Users\\luyim\\Desktop\\sample.xml");
        }
    }

    public long getTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void addToBoard(String msg) {
        this.board.addItem(msg);
    }

    public void restartLevel() {

        initWorld();
        if (completed) {
            completed = false;
        }
    }

    private boolean replayMode = false;

    public void setReplayThings(ArrayList<Creature> creatures, ArrayList<Bullet> bullets) {
        this.replayBullets = bullets;
        this.replayCreatures = creatures;
        this.replayMode = true;
    }

}