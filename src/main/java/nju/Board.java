package nju;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {

    private JList list;
    private DefaultListModel listModel;
    private JButton startButton;
    private JButton saveButton;
    private JButton openButton;
    private MainFrame ground;

    public Board(MainFrame ground) {
        this.ground = ground;

        startButton = new JButton("开始战斗");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.this.ground.field.startBattle();
            }
        });

        saveButton = new JButton("保存记录");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.this.ground.field.saveRecord();
            }
        });

        openButton = new JButton("回放记录");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.this.ground.field.playRecord();
            }
        });

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(list);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(270, 35));
        panel.setLayout(new GridLayout(1, 3));
        panel.add(startButton);
        panel.add(saveButton);
        panel.add(openButton);

        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, panel);
        add(listScroller);
    }

    public void clear() {
        listModel.clear();
    }

    public synchronized void addItem(String msg) {
        listModel.addElement(msg);
    }

    public void setStartButtonEnabled(boolean enable) {
        startButton.setEnabled(enable);
    }

    public void setSaveuttonEnabled(boolean enable) {
        saveButton.setEnabled(enable);
    }

    public void setOpenButtonEnabled(boolean enable) {
        openButton.setEnabled(enable);
    }

}
