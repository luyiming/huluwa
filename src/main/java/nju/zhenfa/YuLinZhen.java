package nju.zhenfa;

import nju.Field;
import nju.Position;
import nju.creature.Creature;

import java.util.ArrayList;

// 鱼鳞阵

public class YuLinZhen implements ZhenFa {

    private int upperX, upperY;

    public YuLinZhen(int upperX, int upperY) {
        this.upperX = upperX;
        this.upperY = upperY;
    }

    public void apply(Field field, ArrayList<? extends Creature> creatures) {
        Position[][] positions = field.getPositions();
        if (creatures.size() > 0 && field.inSpace(upperX, upperY))
            creatures.get(0).setPosition(positions[upperX][upperY]);
        if (creatures.size() > 1 && field.inSpace(upperX + 1, upperY + 1))
            creatures.get(1).setPosition(positions[upperX + 1][upperY + 1]);
        if (creatures.size() > 2 && field.inSpace(upperX + 2, upperY - 2))
            creatures.get(2).setPosition(positions[upperX + 2][upperY - 2]);
        if (creatures.size() > 3 && field.inSpace(upperX + 2, upperY))
            creatures.get(3).setPosition(positions[upperX + 2][upperY]);
        if (creatures.size() > 4 && field.inSpace(upperX + 2, upperY + 2))
            creatures.get(4).setPosition(positions[upperX + 2][upperY + 2]);
        if (creatures.size() > 5 && field.inSpace(upperX + 3, upperY - 3))
            creatures.get(5).setPosition(positions[upperX + 3][upperY - 3]);
        if (creatures.size() > 6 && field.inSpace(upperX + 3, upperY - 1))
            creatures.get(6).setPosition(positions[upperX + 3][upperY - 1]);
        if (creatures.size() > 7 && field.inSpace(upperX + 3, upperY + 1))
            creatures.get(7).setPosition(positions[upperX + 3][upperY + 1]);
        if (creatures.size() > 8 && field.inSpace(upperX + 3, upperY + 3))
            creatures.get(8).setPosition(positions[upperX + 3][upperY + 3]);
        if (creatures.size() > 9 && field.inSpace(upperX + 4, upperY))
            creatures.get(9).setPosition(positions[upperX + 4][upperY]);
    }
}