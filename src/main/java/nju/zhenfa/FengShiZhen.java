package nju.zhenfa;

// 锋矢阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

public class FengShiZhen implements ZhenFa {

    private int upperX, upperY;

    public FengShiZhen(int upperX, int upperY) {
        this.upperX = upperX;
        this.upperY = upperY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        if (creatures.size() < 12)
            return;

        Position[][] positions = field.getPositions();

        int cnt = 0;
        for (int i = 0; i < 6; i++) {
            if (field.inSpace(upperX, upperY + i)) {
                creatures.get(cnt).setPosition(positions[upperX][upperY + i]);
                positions[upperX][upperY + i].setCreature(creatures.get(cnt));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
                cnt++;
            }
        }

        for (int i = 1; i <= 3; i++) {
            if (field.inSpace(upperX + i, upperY + i)) {
                creatures.get(cnt).setPosition(positions[upperX + i][upperY + i]);
                positions[upperX + i][upperY + i].setCreature(creatures.get(cnt));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
                cnt++;
            }
            if (field.inSpace(upperX - i, upperY + i)) {
                creatures.get(cnt).setPosition(positions[upperX - i][upperY + i]);
                positions[upperX - i][upperY + i].setCreature(creatures.get(cnt));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(cnt).getId(), creatures.get(cnt)));
                cnt++;
            }
        }

    }
}