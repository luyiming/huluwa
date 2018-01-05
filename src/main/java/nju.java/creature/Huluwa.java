package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Huluwa extends Creature {
    private COLOR color;

    public Huluwa(COLOR color, Field field) {
        super(field);
        this.color = color;

        URL loc = this.getClass().getClassLoader().getResource((this.color.ordinal() + 1) + ".png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}