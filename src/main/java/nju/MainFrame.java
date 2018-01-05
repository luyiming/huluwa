package nju;


import javax.swing.*;
import java.awt.*;


public final class MainFrame extends JFrame {

    private final int OFFSET = 50;

    public MainFrame() {
        InitUI();
    }

    public Board board;
    public Field field;

    public void InitUI() {
        board = new Board(this);
        field = new Field(board, this);
        field.setPreferredSize(new Dimension(field.getBoardWidth(), field.getBoardHeight()));

        add(field);
        add(BorderLayout.EAST, board);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET + board.getPreferredSize().width,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("MainFrame");
    }
}