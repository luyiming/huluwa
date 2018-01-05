package nju.java.zhenfa;

import nju.java.Field;
import nju.java.Position;
import nju.java.creature.Creature;

// 鱼鳞阵

public class YuLinZhen implements ZhenFa {

  private int upperX, upperY;

  public YuLinZhen(int upperX, int upperY) {
    this.upperX = upperX;
    this.upperY = upperY;
  }

  public void apply(Field field, Creature[] creatures) {
    Position[][] positions = field.getPositions();
    if (creatures.length > 0 && field.inSpace(upperX, upperY))
      creatures[0].setPosition(positions[upperX][upperY]);
    if (creatures.length > 1 && field.inSpace(upperX + 1, upperY + 1))
      creatures[1].setPosition(positions[upperX + 1][upperY + 1]);
    if (creatures.length > 2 && field.inSpace(upperX + 2, upperY - 2))
      creatures[2].setPosition(positions[upperX + 2][upperY - 2]);
    if (creatures.length > 3 && field.inSpace(upperX + 2, upperY))
      creatures[3].setPosition(positions[upperX + 2][upperY]);
    if (creatures.length > 4 && field.inSpace(upperX + 2, upperY + 2))
      creatures[4].setPosition(positions[upperX + 2][upperY + 2]);
    if (creatures.length > 5 && field.inSpace(upperX + 3, upperY - 3))
      creatures[5].setPosition(positions[upperX + 3][upperY - 3]);
    if (creatures.length > 6 && field.inSpace(upperX + 3, upperY - 1))
      creatures[6].setPosition(positions[upperX + 3][upperY - 1]);
    if (creatures.length > 7 && field.inSpace(upperX + 3, upperY + 1))
      creatures[7].setPosition(positions[upperX + 3][upperY + 1]);
    if (creatures.length > 8 && field.inSpace(upperX + 3, upperY + 3))
      creatures[8].setPosition(positions[upperX + 3][upperY + 3]);
    if (creatures.length > 9 && field.inSpace(upperX + 4, upperY))
      creatures[9].setPosition(positions[upperX + 4][upperY]);
  }
}