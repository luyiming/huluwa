package nju.java.creature;

import nju.java.Field;
import nju.java.bullet.Bullet;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Huluwa extends Creature {
    private COLOR color;

    public Huluwa(COLOR color, Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets);
        this.color = color;

        URL loc = this.getClass().getClassLoader().getResource((this.color.ordinal() + 1) + ".png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    protected boolean canAttack(Creature creature) {
        return creature instanceof Monster;
    }
}