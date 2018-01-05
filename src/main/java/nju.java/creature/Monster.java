package nju.java.creature;

import nju.java.Field;

public class Monster extends Creature {
    public Monster(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets);
    }

    @Override
    protected boolean canAttack(Creature creature) {
        return creature instanceof Huluwa || creature instanceof YeYe;
    }
}