package nju.record;

import nju.creature.Creature;

public class RecordFactory {
    public static Record makeCreateRecord(int id, Creature creature) {
        Record record = new Record();
        record.type = Record.RecordType.CREATE;
        record.id = id;
        record.creature = creature;
        record.positionX = creature.getPosition().getX();
        record.positionY = creature.getPosition().getY();
        record.src = creature.getImagePath();
        return record;
    }

    public static Record makeCreateRecord(int id, Creature creature, int positionX, int positionY) {
        Record record = new Record();
        record.type = Record.RecordType.CREATE;
        record.id = id;
        record.creature = creature;
        record.positionX = positionX;
        record.positionY = positionY;
        record.src = creature.getImagePath();
        return record;
    }
    public static Record makeMoveRecord(long time, int id, int x, int y) {
        Record record = new Record();
        record.type = Record.RecordType.MOVE;
        record.id = id;
        record.time = time;
        record.x = x;
        record.y = y;
        return record;
    }
    public static Record makeHurtRecord(long time, int id, double health) {
        Record record = new Record();
        record.type = Record.RecordType.HURT;
        record.id = id;
        record.time = time;
        record.health = health;
        return record;
    }
    public static Record makeDeadRecord(long time, int id) {
        Record record = new Record();
        record.type = Record.RecordType.DEAD;
        record.id = id;
        record.time = time;
        return record;
    }
    public static Record makeBulletCreateRecord(long time, int id, int x, int y, double angle, String src) {
        Record record = new Record();
        record.type = Record.RecordType.BULLET_CREATE;
        record.id = id;
        record.time = time;
        record.x = x;
        record.y = y;
        record.angle = angle;
        record.src = src;
        return record;
    }
    public static Record makeBulletMoveRecord(long time, int id, int x, int y) {
        Record record = new Record();
        record.type = Record.RecordType.BULLET_MOVE;
        record.id = id;
        record.time = time;
        record.x = x;
        record.y = y;
        return record;
    }
    public static Record makeBulletRemoveRecord(long time, int id) {
        Record record = new Record();
        record.type = Record.RecordType.BULLET_REMOVE;
        record.id = id;
        record.time = time;
        return record;
    }
}
