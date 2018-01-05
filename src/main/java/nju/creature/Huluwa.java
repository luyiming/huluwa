package nju.creature;

import nju.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Huluwa extends Creature {
    private COLOR color;

    private String imagePath;

    public Huluwa(COLOR color, Field field, int speed, double bulletSpeed, double damage, int attackRange, int numOfBullets, String bulletFilePath) {
        super(field, speed, bulletSpeed, damage, attackRange, numOfBullets, bulletFilePath);
        this.color = color;

        this.imagePath = (this.color.ordinal() + 1) + ".png";
        URL loc = this.getClass().getClassLoader().getResource(imagePath);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }


    @Override
    public boolean canAttack(Creature creature) {
        return creature instanceof Monster;
    }
}