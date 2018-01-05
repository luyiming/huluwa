package nju.java.zhenfa;

import nju.java.Field;
import nju.java.Position;
import nju.java.creature.Creature;

// 雁行阵

public class YanXingZhen implements ZhenFa {

    private int upperX, upperY;

    public YanXingZhen(int upperX, int upperY) {
        this.upperX = upperX;
        this.upperY = upperY;
    }

    public void apply(Field field, Creature[] creatures) {
        Position[][] positions = field.getPositions();
        for (int i = 0; i < creatures.length; i++) {
            if (field.inSpace(upperX + i, upperY - i))
                creatures[i].setPosition(positions[upperX + i][upperY - i]);
        }
    }
}