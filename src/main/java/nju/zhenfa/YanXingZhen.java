package nju.zhenfa;

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

// 雁行阵

public class YanXingZhen implements ZhenFa {

    private int upperX, upperY;

    public YanXingZhen(int upperX, int upperY) {
        this.upperX = upperX;
        this.upperY = upperY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        Position[][] positions = field.getPositions();
        for (int i = 0; i < creatures.size(); i++) {
            if (field.inSpace(upperX - i, upperY + i)) {
                creatures.get(i).setPosition(positions[upperX - i][upperY + i]);
                positions[upperX - i][upperY + i].setCreature(creatures.get(i));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(0).getId(), creatures.get(0)));
            }
        }
    }
}