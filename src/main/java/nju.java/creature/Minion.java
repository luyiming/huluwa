package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Minion extends Monster {

    private  String imagePath;

    public Minion(Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets, bulletFilePath);

        imagePath = "hamajing.png";
        URL loc = this.getClass().getClassLoader().getResource("hamajing.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}