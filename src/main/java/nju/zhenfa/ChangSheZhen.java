package nju.zhenfa;

// 长蛇阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

public class ChangSheZhen implements ZhenFa {

    private int startX, startY;

    public ChangSheZhen(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        Position[][] positions = field.getPositions();
        for (int i = 0; i < creatures.size(); i++) {
            if (field.inSpace(startX, startY + i)) {
                creatures.get(i).setPosition(positions[startX][startY + i]);
                positions[startX][startY + i].setCreature(creatures.get(i));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(i).getId(), creatures.get(i)));
            }
        }
    }
}