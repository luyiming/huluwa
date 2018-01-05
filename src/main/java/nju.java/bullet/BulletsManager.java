package nju.java.bullet;

import nju.java.Field;

import java.util.*;

public class BulletsManager implements Runnable {
    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private Field field;
    private final int updateInterval = 25; // 25 ms

    public BulletsManager(Field field) {
        this.field = field;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    private void oneTick() {
        for (int i = 0; i < bullets.size(); ) {
            Bullet bullet = bullets.get(i);
            bullet.setX(bullet.x() + (int)Math.round(bullet.getSpeed() * updateInterval * Math.cos(bullet.getAngle())));
            bullet.setY(bullet.y() + (int)Math.round(bullet.getSpeed() * updateInterval * Math.sin(bullet.getAngle())));
            if (bullet.x() < 0 || bullet.x() > field.getBoardWidth() || bullet.y() < 0 || bullet.y() > field.getBoardHeight()) {
                bullets.remove(i);
//                System.out.println("remove bullet");
                continue;
            }
            int x = this.field.convertXtoPosition(bullet.x());
            int y = this.field.convertYtoPosition(bullet.y());
            if (this.field.inSpace(x, y) && this.field.getPositions()[x][y].hasCreature() && bullet.getCreature().canAttack(this.field.getPositions()[x][y].getCreature())) {
                this.field.getPositions()[x][y].getCreature().hurt(bullet.getDamage());
                bullets.remove(i);
//                System.out.println("remove bullet");
                continue;
            }
            i++;
        }
    }

    public void run() {

        while (!Thread.interrupted()) {
            try {
                Thread.sleep(updateInterval);
                oneTick();
                this.field.repaint();
            } catch (Exception e) {

            }
        }
    }

}
