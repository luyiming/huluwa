package nju.java.creature;

import nju.java.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Snake extends Creature {
    public Snake(Field field) {
        super(field);

        URL loc = this.getClass().getClassLoader().getResource("shejing1.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}