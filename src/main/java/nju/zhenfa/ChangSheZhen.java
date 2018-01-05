package nju.zhenfa;

// 长蛇阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;

public class ChangSheZhen implements ZhenFa {

  private int startX, startY;

  public ChangSheZhen(int startX, int startY) {
    this.startX = startX;
    this.startY = startY;
  }

  public void apply(Field field, Creature[] creatures) {
    Position[][] positions = field.getPositions();
    for (int i = 0; i < creatures.length; i++) {
      if (field.inSpace(startX + i, startY))
        creatures[i].setPosition(positions[startX + i][startY]);
    }
  }
}