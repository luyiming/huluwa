package nju.zhenfa;

// 锋矢阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;

public class FengShiZhen implements ZhenFa {

  private int upperX, upperY;

  public FengShiZhen(int upperX, int upperY) {
    this.upperX = upperX;
    this.upperY = upperY;
  }

  public void apply(Field field, Creature[] creatures) {
    if (creatures.length < 12)
      return;

    Position[][] positions = field.getPositions();

    int cnt = 0;
    for (int i = 0; i < 6; i++) {
      if (field.inSpace(upperX + i, upperY))
        creatures[cnt++].setPosition(positions[upperX + i][upperY]);
    }

    for (int i = 1; i <= 3; i++) {
      if (field.inSpace(upperX + i, upperY - i))
        creatures[cnt++].setPosition(positions[upperX + i][upperY - i]);
      if (field.inSpace(upperX + i, upperY + i))
        creatures[cnt++].setPosition(positions[upperX + i][upperY + i]);
    }

  }
}