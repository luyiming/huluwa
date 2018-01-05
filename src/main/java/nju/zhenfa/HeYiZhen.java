package nju.zhenfa;

import nju.Field;
import nju.Position;
import nju.creature.Creature;
import nju.record.RecordFactory;

import java.util.ArrayList;

// 鹤翼阵

public class HeYiZhen implements ZhenFa {

    private int startX, startY;

    public HeYiZhen(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        int len1 = (creatures.size() - 1) / 2;
        Position[][] positions = field.getPositions();

        if (field.inSpace(startX, startY)) {
            creatures.get(0).setPosition(positions[startX][startY]);
            positions[startX][startY].setCreature(creatures.get(0));
            field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(0).getId(), creatures.get(0)));
        }

        int i = 1;
        for (; i <= len1; i++) {
            if (field.inSpace(startX - i, startY + i)) {
                creatures.get(i).setPosition(positions[startX - i][startY + i]);
                positions[startX - i][startY + i].setCreature(creatures.get(i));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(i).getId(), creatures.get(i)));
            }
        }

        for (; i < creatures.size(); i++) {
            int newX = startX - (i - len1) ;
            int newY = startY - (i - len1);
            if (field.inSpace(newX, newY)) {
                creatures.get(i).setPosition(positions[newX][newY]);
                positions[newX][newY].setCreature(creatures.get(i));
                field.getRecordsManager().addRecord(RecordFactory.makeCreateRecord(creatures.get(i).getId(), creatures.get(i)));
            }
        }
    }
}