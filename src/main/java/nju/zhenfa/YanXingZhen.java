package nju.zhenfa;

import nju.Field;
import nju.Position;
import nju.creature.Creature;

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