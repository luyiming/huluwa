package nju.zhenfa;

// 衡轭阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

public class HengEZhen implements ZhenFa {

    private int upperX, upperY;

    public HengEZhen(int upperX, int upperY) {
        this.upperX = upperX;
        this.upperY = upperY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        Position[][] positions = field.getPositions();
        for (int i = 0; i < creatures.size(); i++) {
            if (field.inSpace(upperX + (i % 2), upperY + i)) {
                creatures.get(i).setPosition(positions[upperX + (i % 2)][upperY + i]);
                positions[upperX + (i % 2)][upperY + i].setCreature(creatures.get(i));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(i).getId(), creatures.get(i)));
            }
        }
    }
}