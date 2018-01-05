package nju.zhenfa;

// 方円阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

public class FangYuanZhen implements ZhenFa {

    private int midX, midY;

    public FangYuanZhen(int midX, int midY) {
        this.midX = midX;
        this.midY = midY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        if (creatures.size() < 8)
            return;

        Position[][] positions = field.getPositions();

        int cnt = 0;
        if (field.inSpace(midX - 2, midY)) {
            creatures.get(cnt).setPosition(positions[midX - 2][midY]);
            positions[midX - 2][midY].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX + 2, midY)) {
            creatures.get(cnt).setPosition(positions[midX + 2][midY]);
            positions[midX + 2][midY].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX, midY - 2)) {
            creatures.get(cnt).setPosition(positions[midX][midY - 2]);
            positions[midX][midY - 2].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX, midY + 2)) {
            creatures.get(cnt).setPosition(positions[midX][midY + 2]);
            positions[midX][midY + 2].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX - 1, midY + 1)) {
            creatures.get(cnt).setPosition(positions[midX - 1][midY + 1]);
            positions[midX - 1][midY + 1].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX + 1, midY + 1)) {
            creatures.get(cnt).setPosition(positions[midX + 1][midY + 1]);
            positions[midX + 1][midY + 1].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX + 1, midY - 1)) {
            creatures.get(cnt).setPosition(positions[midX + 1][midY - 1]);
            positions[midX + 1][midY - 1].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
        if (field.inSpace(midX - 1, midY - 1)) {
            creatures.get(cnt).setPosition(positions[midX - 1][midY - 1]);
            positions[midX - 1][midY - 1].setCreature(creatures.get(cnt));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
            cnt++;
        }
    }
}