package nju.java.creature;

import nju.java.Field;

public class Monster extends Creature {
    public Monster(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets, bulletFilePath);
    }

    @Override
    public boolean canAttack(Creature creature) {
        return creature instanceof Huluwa || creature instanceof YeYe;
    }
}