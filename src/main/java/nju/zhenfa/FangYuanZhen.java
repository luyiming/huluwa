package nju.zhenfa;

// 方円阵

import nju.Field;
import nju.Position;
import nju.creature.Creature;

public class FangYuanZhen implements ZhenFa {

  private int midX, midY;

  public FangYuanZhen(int midX, int midY) {
    this.midX = midX;
    this.midY = midY;
  }

  public void apply(Field field, Creature[] creatures) {
    if (creatures.length < 8)
      return;

    Position[][] positions = field.getPositions();

    int cnt = 0;
    if (field.inSpace(midX - 2, midY))
      creatures[cnt++].setPosition(positions[midX - 2][midY]);
    if (field.inSpace(midX + 2, midY))
      creatures[cnt++].setPosition(positions[midX + 2][midY]);
    if (field.inSpace(midX, midY - 2))
      creatures[cnt++].setPosition(positions[midX][midY - 2]);
    if (field.inSpace(midX, midY + 2))
      creatures[cnt++].setPosition(positions[midX][midY + 2]);
    if (field.inSpace(midX - 1, midY + 1))
      creatures[cnt++].setPosition(positions[midX - 1][midY + 1]);
    if (field.inSpace(midX + 1, midY + 1))
      creatures[cnt++].setPosition(positions[midX + 1][midY + 1]);
    if (field.inSpace(midX + 1, midY - 1))
      creatures[cnt++].setPosition(positions[midX + 1][midY - 1]);
    if (field.inSpace(midX - 1, midY - 1))
      creatures[cnt++].setPosition(positions[midX - 1][midY - 1]);
  }
}