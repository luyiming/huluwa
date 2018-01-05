package nju;

import nju.creature.*;
import nju.bullet.*;
import nju.record.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class Field extends JPanel {

    private final int SPACE = 70;
    private final int OFFSET = 50;
    private final int ROW = 10;
    private final int COL = 10;

    private ArrayList<Huluwa> huluwas;
    private ArrayList<Minion> minions;
    private Scorpion xiezijing;
    private Snake snake;
    private YeYe yeye;
    private Background background;

    private ArrayList<Creature> replayCreatures;
    private ArrayList<Bullet> replayBullets;

    private BulletsManager bulletsManager;
    private RecordsManager recordsManager;

    private Position[][] positions;

    private boolean running = false;
    private boolean completed = false;
    private boolean replayMode = false;
    private boolean huluwaWin = false;
    private boolean minionWin = false;

    private long startTime;

    private Board board;

    ArrayList<Thread> threads = new ArrayList<Thread>();

    public Field(Board board) {
        this.board = board;

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return background.getWidth();
    }

    public int getBoardHeight() {
        return background.getHeight();
    }

    public boolean inSpace(int row, int col) {
        if (row >= 0 && row < ROW && col >= 0 && col < COL)
            return true;
        else
            return false;
    }

    public synchronized Position[][] getPositions() {
        return positions;
    }

    public synchronized BulletsManager getBulletsManager() {
        return bulletsManager;
    }

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

        huluwas = new ArrayList<Huluwa>();
        for (int i = 0; i < 7; i++) {
            Huluwa huluwa = new Huluwa(COLOR.values()[i], this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN,
                    Creature.DAMAGE_MEDIAN, Creature.ATTACK_RANGE_MEDIAN, 1, "bullet" + (i+1) + ".png");
            huluwa.setPosition(positions[2][i]);
            positions[2][i].setCreature(huluwa);
            recordsManager.addRecord(RecordFactory.makeCreateRecord(huluwa.getId(), huluwa));
            huluwas.add(huluwa);
        }

        yeye = new YeYe(this, Creature.SPEED_LOW, Creature.BULLET_SPEED_MEDIAN, Creature.DAMAGE_LOW,
                Creature.ATTACK_RANGE_FAR, 2, "bullet0.png");
        yeye.setPosition(positions[0][0]);
        positions[0][0].setCreature(yeye);
        recordsManager.addRecord(RecordFactory.makeCreateRecord(yeye.getId(), yeye));

        minions = new ArrayList<Minion>();
        for (int i = 0; i < 5; i++) {
            Minion minion = new Minion(this, Creature.SPEED_MEDIAN, Creature.BULLET_SPEED_MEDIAN,
                    Creature.DAMAGE_MEDIAN, Creature.ATTACK_RANGE_MEDIAN, 1, "bullet8.png");
            minion.setPosition(positions[7][i]);
            positions[7][i].setCreature(minion);
            recordsManager.addRecord(RecordFactory.makeCreateRecord(minion.getId(), minion));
            minions.add(minion);
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

        ArrayList<Thing2D> world = new ArrayList<Thing2D>();

        world.add(background);
        if (replayMode) {
            world.addAll(this.replayCreatures);
            world.addAll(this.replayBullets);
        } else {
            world.add(yeye);
            world.add(snake);
            world.add(xiezijing);
            world.addAll(huluwas);
            world.addAll(minions);
            world.addAll(bulletsManager.getBullets());
        }

        for (int i = 0; i < world.size(); i++) {

            Thing2D item = world.get(i);

            if (item instanceof Creature) {
                Creature creature = (Creature)item;
                if (creature.isDead())
                    continue;
                g.drawImage(item.getImage(), item.x(), item.y(), this);
                // draw health bar
                g.setColor(new Color(255, 0, 0));
                g.fillRect(item.x() + 10, item.y(), (int)(50 * creature.getHealth()), 4);
                g.setColor(new Color(0, 0, 0));
                g.drawRect(item.x() + 10, item.y(), 50, 4);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (running) {
                scoreRecord();
            }

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 20));
                if (huluwaWin && minionWin) {
                    g.drawString("平局", 300, 10);
                } else if (huluwaWin) {
                    g.drawString("葫芦娃赢了", 300, 10);
                } else if (minionWin) {
                    g.drawString("蛇精赢了", 300, 10);
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

            int key = e.getKeyCode();

            if (!running) {
                if (key == KeyEvent.VK_S) {
                    if (Field.this.getRecordsManager().isEmpty()) {
                        JOptionPane.showMessageDialog(Field.this, "Current record is empty.");
                    } else {
                        JFileChooser saveFile = new JFileChooser(".");
                        int result = saveFile.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File targetFile = saveFile.getSelectedFile();
                            System.out.println(targetFile.getAbsolutePath());

                            Field.this.getRecordsManager().exportToFile(targetFile.getAbsolutePath());
                        }
                    }
                } else if (key == KeyEvent.VK_R) {
                    JFileChooser openFile = new JFileChooser(".");
                    int result = openFile.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File targetFile = openFile.getSelectedFile();
                        System.out.println(targetFile.getAbsolutePath());

                        RecordPlayer recordPlayer = new RecordPlayer(Field.this);
                        Field.this.getRecordsManager().loadRecordsFile(targetFile.getAbsolutePath(), recordPlayer);
                        Field.this.board.clear();
                        new Thread(recordPlayer).start();
                    }
                } else if (key == KeyEvent.VK_SPACE) {
                    Field.this.threads.clear();
                    restartLevel();
                    running = true;

                    Field.this.threads.add(new Thread(yeye));
                    for (Huluwa huluwa : huluwas)
                        Field.this.threads.add(new Thread(huluwa));
                    for (Minion minion : minions)
                        Field.this.threads.add(new Thread(minion));
                    Field.this.threads.add(new Thread(xiezijing));
                    Field.this.threads.add(new Thread(snake));
                    Field.this.threads.add(new Thread(bulletsManager));

                    Field.this.startTime = System.currentTimeMillis();
                    for (Thread thread : Field.this.threads)
                        thread.start();
                } else if (key == KeyEvent.VK_R) {
                    restartLevel();
                }
            } else {
                if (key == KeyEvent.VK_LEFT) {
                    yeye.move(-1, 0);
                } else if (key == KeyEvent.VK_RIGHT) {
                    yeye.move(1, 0);
                } else if (key == KeyEvent.VK_UP) {
                    yeye.move(0, -1);
                } else if (key == KeyEvent.VK_DOWN) {
                    yeye.move(0, 1);
                }
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
            this.running = false;
            for (Thread thread : threads) {
                thread.interrupt();
            }
            System.out.println("completed");
        }
    }

    public long getRunningTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void addToBoard(String msg) {
        this.board.addItem(msg);
    }

    public void restartLevel() {
        this.board.clear();

        initWorld();

        running = false;
        completed = false;
        replayMode = false;
    }

    public void startReplay(ArrayList<Creature> creatures, ArrayList<Bullet> bullets) {
        this.replayBullets = bullets;
        this.replayCreatures = creatures;
        this.replayMode = true;
    }

    public void stopReplay() {
        this.replayMode = false;
        this.replayBullets = null;
        this.replayCreatures = null;
    }

}