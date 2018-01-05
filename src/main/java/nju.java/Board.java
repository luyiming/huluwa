package nju.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel {

    private JList list;
    private DefaultListModel listModel;
    private JButton addButton;
    JButton removeButton;

    public Board() {
        listModel = new DefaultListModel();
        listModel.addElement("Jane Doe");
        listModel.addElement("John Smith");
        listModel.addElement("Kathy Green");

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);

        addButton = new JButton("add");
        addButton.addActionListener(new AddButtonActionListener());

        removeButton = new JButton("remove");
        removeButton.addActionListener(new RemoveButtonActionListener());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.WEST, addButton);
        panel.add(BorderLayout.EAST, removeButton);

        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, panel);
        add(listScroller);
    }

    class RemoveButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            String name = employeeName.getText();
//
//            //User did not type in a unique name...
//            if (name.equals("") || alreadyInList(name)) {
//                Toolkit.getDefaultToolkit().beep();
//                employeeName.requestFocusInWindow();
//                employeeName.selectAll();
//                return;
//            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

//            listModel.insertElementAt("test", index);
            listModel.addElement("test");

//            //Reset the text field.
//            employeeName.requestFocusInWindow();
//            employeeName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }



}
