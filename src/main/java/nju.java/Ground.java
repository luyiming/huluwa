package nju.java;


import javax.swing.*;
import java.awt.*;


public final class Ground extends JFrame {

    private final int OFFSET = 30;

    public Ground() {
        InitUI();
    }

    public void InitUI() {
        Field field = new Field();
        field.setPreferredSize(new Dimension(field.getBoardWidth(), field.getBoardHeight()));
        add(field);

        add(BorderLayout.EAST, new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET + 200,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }


    public static void main(String[] args) {
        Ground ground = new Ground();
        ground.setVisible(true);
    }
}