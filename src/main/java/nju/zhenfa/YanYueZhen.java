package nju.zhenfa;

import nju.Field;
import nju.Position;
import nju.creature.Creature;

import java.util.ArrayList;

// 偃月阵

public class YanYueZhen implements ZhenFa {

  private int leftX, leftY;

  public YanYueZhen(int leftX, int leftY) {
    this.leftX = leftX;
    this.leftY = leftY;
  }

  public void apply(Field field, ArrayList<? extends Creature> creatures) {
    if (creatures.size() < 19)
      return;
    Position[][] positions = field.getPositions();
    int cnt = 0;
    for (int i = 0; i < 3; i++) {
      if (field.inSpace(leftX, leftY + i))
        creatures.get(cnt++).setPosition(positions[leftX][leftY + i]);
      if (field.inSpace(leftX - 1, leftY + i))
        creatures.get(cnt++).setPosition(positions[leftX - 1][leftY + i]);
      if (field.inSpace(leftX + 1, leftY + i))
        creatures.get(cnt++).setPosition(positions[leftX + 1][leftY + i]);
    }
    for (int i = 0; i < 2; i++) {
      if (field.inSpace(leftX - 2 - i, leftY + 2 + i))
        creatures.get(cnt++).setPosition(positions[leftX - 2 - i][leftY + 2 + i]);
      if (field.inSpace(leftX + 2 + i, leftY + 2 + i))
        creatures.get(cnt++).setPosition(positions[leftX + 2 + i][leftY + 2 + i]);
    }
    for (int i = 0; i < 3; i++) {
      if (field.inSpace(leftX - 2 - i, leftY + 3 + i))
        creatures.get(cnt++).setPosition(positions[leftX - 2 - i][leftY + 3 + i]);
      if (field.inSpace(leftX + 2 + i, leftY + 3 + i))
        creatures.get(cnt++).setPosition(positions[leftX + 2 + i][leftY + 3 + i]);
    }
  }
}