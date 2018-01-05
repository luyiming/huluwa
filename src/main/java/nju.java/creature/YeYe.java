package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class YeYe extends Creature {

    public YeYe(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets, bulletFilePath);

        URL loc = this.getClass().getClassLoader().getResource("yeye.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public String getImagePath() {
        return "yeye.png";
    }

    @Override
    public boolean canAttack(Creature creature) {
        return creature instanceof Monster;
    }
}