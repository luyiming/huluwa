package nju.bullet;

import nju.Field;
import nju.record.RecordFactory;

import java.util.*;

public class BulletsManager implements Runnable {
    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private Field field;
    private final int updateInterval = 25; // 25 ms

    public BulletsManager(Field field) {
        this.field = field;
    }

    public synchronized void addBullet(Bullet bullet) {
        bullets.add(bullet);
        this.field.getRecordsManager().addRecord(RecordFactory.makeBulletCreateRecord(this.field.getRunningTime(), bullet.getId(), bullet.x(), bullet.y(), bullet.getAngle(), bullet.getSrc()));
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    private void oneTick() {
        for (int i = 0; i < bullets.size(); ) {
            Bullet bullet = bullets.get(i);
            bullet.setX(bullet.x() + (int)Math.round(bullet.getSpeed() * updateInterval * Math.cos(bullet.getAngle())));
            bullet.setY(bullet.y() + (int)Math.round(bullet.getSpeed() * updateInterval * Math.sin(bullet.getAngle())));
            this.field.getRecordsManager().addRecord(RecordFactory.makeBulletMoveRecord(this.field.getRunningTime(), bullet.getId(), bullet.x(), bullet.y()));
            if (bullet.x() < 0 || bullet.x() > field.getBoardWidth() || bullet.y() < 0 || bullet.y() > field.getBoardHeight()) {
                bullets.remove(i);
                this.field.getRecordsManager().addRecord(RecordFactory.makeBulletRemoveRecord(this.field.getRunningTime(), bullet.getId()));
//                System.out.println("remove bullet");
                continue;
            }
            int x = this.field.convertXtoPosition(bullet.x());
            int y = this.field.convertYtoPosition(bullet.y());
            if (this.field.inSpace(x, y) && this.field.getPositions()[x][y].hasCreature() && bullet.getCreature().canAttack(this.field.getPositions()[x][y].getCreature())) {
                this.field.getPositions()[x][y].getCreature().hurt(bullet.getDamage());
                bullets.remove(i);
                this.field.getRecordsManager().addRecord(RecordFactory.makeBulletRemoveRecord(this.field.getRunningTime(), bullet.getId()));
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
