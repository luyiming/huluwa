package nju.java.record;

import nju.java.Field;
import nju.java.bullet.Bullet;
import nju.java.creature.Creature;

import java.util.ArrayList;
import java.util.Random;

public class RecordPlayer implements Runnable {
    private  Field field;
    private ArrayList<Creature> creatures = new ArrayList<Creature>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public  RecordPlayer(Field field) {
        this.field = field;
    }

    public void addCreature(Creature creature, int id) {
        if (creature.getId() != id) {
            System.err.printf("id mismatch %d != %d\n", creature.getId(), id);
        } else {
            System.out.printf("add creature %d %d\n", creature.getId(), id);
        }
        creatures.add(creature);
    }

    Creature getCreature(int id) {
        for (Creature creature : creatures) {
            if (creature.getId() == id)
                return creature;
        }
        return null;
    }

    void addBullet(Bullet bullet, int id) {
        if (bullet.getId() != id) {
            System.err.printf("bullet id mismatch %d != %d\n", bullet.getId(), id);
        } else {
            System.out.printf("add bullet %d %d\n", bullet.getId(), id);
        }
        bullets.add(bullet);
    }

    Bullet getBullet(int id) {
        for (Bullet bullet : bullets) {
            if (bullet.getId() == id)
                return bullet;
        }
        return null;
    }

    void removeBullet(int id) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getId() == id) {
                bullets.remove(i);
                return;
            }
        }
        System.err.printf("bullet not found %d\n", id);
        return;
    }


    public void run() {
        long startTime = System.currentTimeMillis();
        long lastTime = startTime;
        int index = 0;

        while (!Thread.interrupted()) {
            out:
            while (index < field.getRecordsManager().getRecords().size()) {
                Record record = field.getRecordsManager().getRecords().get(index);
                switch (record.type) {
                    case CREATE: {
                        boolean found = false;
                        for (Creature creature : creatures) {
                            if (creature.getId() == record.id)
                                found = true;
                        }
                        if (!found) {
                            System.err.printf("creature not found %d\n", record.id);
                        }
                        index++;
                        break;
                    }
                    case MOVE: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            Creature creature = getCreature(record.id);
                            if (creature != null) {
                                creature.setX(record.x);
                                creature.setY(record.y);
                            }
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                    case HURT: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            Creature creature = getCreature(record.id);
                            if (creature != null)
                                creature.setHealth(record.health);
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                    case DEAD: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            Creature creature = getCreature(record.id);
                            if (creature != null)
                                getCreature(record.id).setHealth(-1);
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                    case BULLET_CREATE: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            Bullet bullet = new Bullet(record.x, record.y, (int)(record.x + 100 * Math.cos(record.angle)), (int)(record.y + 100 * Math.sin(record.angle)), 0, 0, record.src, null);
                            bullet.setX(record.x);
                            bullet.setY(record.y);
                            bullet.setId(record.id);
                            addBullet(bullet, record.id);
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                    case BULLET_MOVE: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            Bullet bullet = getBullet(record.id);
                            if (bullet != null) {
                                bullet.setX(record.x);
                                bullet.setY(record.y);
                            }
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                    case BULLET_REMOVE: {
                        long time = record.time;
                        if (time < System.currentTimeMillis() - startTime) {
                            removeBullet(record.id);
                            index++;
                        } else {
                            break out;
                        }
                        break;
                    }
                }
            }
            this.field.setReplayThings(creatures, bullets);
            this.field.repaint();
            if (index >= this.field.getRecordsManager().getRecords().size()) {
                return;
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                return;
            }
        }


    }


}
