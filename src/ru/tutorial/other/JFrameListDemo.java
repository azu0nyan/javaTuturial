package ru.tutorial.other;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class JFrameListDemo {


    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("Some title");
        jf.setUndecorated(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setSize(650, 600);

        jf.setLayout(null);

        JButton jb1 = new JButton("KNOPKA");
        jb1.setBounds(100, 100, 150, 50);
        jf.add(jb1);


        JTextField jtf = new JTextField("2");
        jtf.setBounds(100, 50, 150, 50);
        jf.add(jtf);




        java.awt.List l = new java.awt.List(5);
        l.add("List 1");
        l.add("List 2");
        l.setBounds(300, 50, 200, 500);
        jf.add(l);

        jb1.addActionListener(e -> {
            String t = jtf.getText();
            l.add(t);

        });

        jtf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                l.add(jtf.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        l.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                jb1.setText(l.getItem((Integer)e.getItem()));
            }
        });

        jf.setVisible(true);

    }


}

