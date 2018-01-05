package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Scorpion extends Monster {
    public Scorpion(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets, bulletFilePath);

        URL loc = this.getClass().getClassLoader().getResource("xiezijing.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
