package nju.zhenfa;

// 衡轭阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;

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