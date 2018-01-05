package nju;


import javax.swing.*;
import java.awt.*;


public final class Ground extends JFrame {

    private final int OFFSET = 50;

    public Ground() {
        InitUI();
    }

    public void InitUI() {
        Board board = new Board();
        Field field = new Field(board);
        field.setPreferredSize(new Dimension(field.getBoardWidth(), field.getBoardHeight()));

        add(field);
        add(BorderLayout.EAST, board);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET + 200,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }
}