package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Snake extends Monster {
    public Snake(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets);

        URL loc = this.getClass().getClassLoader().getResource("shejing1.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}