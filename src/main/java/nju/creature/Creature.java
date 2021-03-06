package nju.creature;

import nju.Field;
import nju.Thing2D;
import nju.Position;
import nju.bullet.Bullet;
import nju.record.RecordFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Creature extends Thing2D implements Runnable {
    private Field field;
    private Position position;

    private double health;
    private int speed;
    private double bulletSpeed;
    private double damage;
    private int attackRange;
    private int numOfBullets;

    private String bulletFilePath;

    public static final int SPEED_HIGH = 500;
    public static final int SPEED_MEDIAN = 700;
    public static final int SPEED_LOW = 1000;

    public static final double BULLET_SPEED_HIGH = 0.6;
    public static final double BULLET_SPEED_MEDIAN = 0.4;
    public static final double BULLET_SPEED_LOW = 0.3;

    public static final double DAMAGE_HIGH = 0.3;
    public static final double DAMAGE_MEDIAN = 0.2;
    public static final double DAMAGE_LOW = 0.1;

    public static final int ATTACK_RANGE_FAR = 5;
    public static final int ATTACK_RANGE_MEDIAN = 3;
    public static final int ATTACK_RANGE_CLOSE = 2;

    public Creature(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(0, 0);

        this.field = field;
        this.health = 1.0;
        this.speed = speed;
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
        this.attackRange = attackRange;
        this.numOfBullets = numOfBullets;
        this.bulletFilePath = bulletFilePath;
    }

    public Creature(String src) {
        super(0, 0);

        this.health = 1.0;
        URL loc = this.getClass().getClassLoader().getResource(src);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    public String getImagePath() {
        return null;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void hurt(double damage) {
        this.health -= damage;
        this.field.getRecordsManager().addRecord(RecordFactory.makeHurtRecord(this.field.getRunningTime(), this.getId(), this.health));
        if (this.health < 0) {
            this.field.getRecordsManager().addRecord(RecordFactory.makeDeadRecord(this.field.getRunningTime(), this.getId()));
            this.position.clearCreature();
            this.position = null;
//            System.out.println("creature dead");
        }
    }

    public boolean isDead() {
        return health < 0;
    }


    public void setPosition(Position position) {
        this.position = position;
        this.setX(field.convertPositionToX(position));
        this.setY(field.convertPositionToY(position));
    }

    public Position getPosition() { return position; }

    public void releasePosition() { position = null; }

    protected void doMove() {
        Random rand = new Random();
        int cnt = 0;
        boolean succeed = false;
        while (succeed == false) {
            int x = 0, y = 0;
            if (rand.nextInt(2) == 0) {
                x = rand.nextInt(2) == 0 ? 1 : -1;
            } else {
                y = rand.nextInt(2) == 0 ? 1 : -1;
            }
            succeed = move(x, y);
            cnt++;
            if (cnt > 4) {
                break;
            }
        }
    }

    public boolean move(int x, int y) {
        if (isDead())
            return false;
        int newX = position.getX() + x;
        int newY = position.getY() + y;
        if (field.inSpace(newX, newY)) {
            if (!field.getPositions()[newX][newY].hasCreature()) {
                field.getPositions()[position.getX()][position.getY()].clearCreature();
                field.getPositions()[newX][newY].setCreature(this);
                setPosition(field.getPositions()[newX][newY]);
                this.field.getRecordsManager().addRecord(RecordFactory.makeMoveRecord(this.field.getRunningTime(), this.getId(),
                        this.field.convertPositionToX(newX), this.field.convertPositionToY(newY)));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean canAttack(Creature creature) {
        return true;
    }

    protected void doAttack() {
        int cnt = 0;
        out:
        for (int i = -attackRange; i <= attackRange; i++) {
            for (int j = -attackRange; j <= attackRange; j++) {
                if (i == 0 && j == 0)
                    continue;
                int x = position.getX() + i;
                int y = position.getY() + j;
                if (this.field.inSpace(x, y) && this.field.getPositions()[x][y].hasCreature() && canAttack(this.field.getPositions()[x][y].getCreature())) {
                    this.field.getBulletsManager().addBullet(new Bullet(this.field.convertPositionToX(position), this.field.convertPositionToY(position),
                            this.field.convertPositionToX(x), this.field.convertPositionToY(y), bulletSpeed, damage, bulletFilePath, this));
                    cnt++;
                    if (cnt >= numOfBullets)
                        break out;
                }
            }
        }
    }

    public void run() {
        while (!Thread.interrupted()) {
            Random rand = new Random();

            if (isDead())
                return;

            if (rand.nextInt(2) == 0)
                this.doMove();
            else
                this.doAttack();
            try {
                Thread.sleep(rand.nextInt(speed) + 300);
                this.field.repaint();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}