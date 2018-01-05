package nju.java.zhenfa;

// 衡轭阵

import nju.java.Field;
import nju.java.Position;
import nju.java.creature.Creature;

public class HengEZhen implements ZhenFa {

  private int upperX, upperY;

  public HengEZhen(int upperX, int upperY) {
    this.upperX = upperX;
    this.upperY = upperY;
  }

  public void apply(Field field, Creature[] creatures) {
    Position[][] positions = field.getPositions();
    for (int i = 0; i < creatures.length; i++) {
      if (field.inSpace(upperX + i, upperY - (i % 2)))
        creatures[i].setPosition(positions[upperX + i][upperY - (i % 2)]);
    }
  }
}