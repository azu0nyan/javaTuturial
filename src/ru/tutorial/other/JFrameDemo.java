package ru.tutorial.other;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class JFrameDemo {




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


        JProgressBar jpb = new JProgressBar(0, 600);
        jpb.setValue(jb1.getBounds().x);
        jpb.setBounds(100, 400, 200, 50);
        jpb.setStringPainted(true);
        jf.add(jpb);

        JSlider js = new JSlider(-10, 10);
        js.setMajorTickSpacing(5);
        js.setMinorTickSpacing(1);
        js.setPaintTicks(true);
        js.setPaintLabels(true);
        js.setValue(0);
        js.setBounds(100, 450, 200, 50);
        jf.add(js);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jb1.setBounds(jb1.getBounds().x + js.getValue(), jb1.getBounds().y, 150, 50);
                jpb.setValue(jb1.getBounds().x % 600);
                jf.repaint();
            }
        }, 0, 10);


        JTextField jtf = new JTextField("2");
        jtf.setBounds(100, 50, 150, 50);
        jf.add(jtf);


        jb1.addActionListener(e -> {
            String t = jtf.getText();
            int n = Integer.parseInt(t);
            jtf.setText(String.valueOf(n * n));


        });

        for (int i = 0; i < 10; i++) {
            JButton jb2 = new JButton("" + i);
            jb2.setBounds(100 + i * 50, 200, 50, 50);
            jf.add(jb2);
            jf.repaint();
            final int number = i;
            jb2.addActionListener(e -> {
                jtf.setText(jtf.getText() + "" + number);
            });
        }

        //


        radioButtons(jf);
        jf.setVisible(true);

    }

    static void radioButtons(JFrame jf) {
        JRadioButton ob1 = new JRadioButton("ONE");
        ob1.setBounds(100, 300, 100, 50);
        ob1.setSelected(true);
        JRadioButton ob2 = new JRadioButton("TWO");
        ob2.setBounds(100, 350, 100, 50);
        ob2.setSelected(true);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(ob1);
        group.add(ob2);
        jf.add(ob1);
        jf.add(ob2);
        jf.repaint();

    }

}
